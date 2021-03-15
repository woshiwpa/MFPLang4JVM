// MFP project, BlockAnalyzer.java : Designed and developed by Tony Cui in 2021
package jcmdline;

import com.cyzapps.Jmfp.Statement;
import com.cyzapps.Jmfp.Statement_endf;
import com.cyzapps.Jmfp.Statement_function;
import com.cyzapps.Jmfp.ErrorProcessor.ERRORTYPES;
import com.cyzapps.Jmfp.ErrorProcessor.JMFPCompErrException;
import com.cyzapps.Jmfp.FunctionEntry;

import java.util.LinkedList;

public class BlockAnalyzer {

	protected LinkedList<Statement> mlistBlockStack = new LinkedList<Statement>();
	
	protected LinkedList<String> mlAllLines = new LinkedList<String>();
		
	protected LinkedList<Statement> mlAllStatements = new LinkedList<Statement>();
	
	protected Boolean mbInHelpBlock = false;
	
	public BlockAnalyzer() {
		mlistBlockStack = new LinkedList<Statement>();
		mlAllLines = new LinkedList<String>();
		mlAllStatements = new LinkedList<Statement>();
		mbInHelpBlock = false;
	}
	
	public void reset() {
		mlistBlockStack.clear();
		mlAllLines.clear();
		mlAllStatements.clear();
		mbInHelpBlock = false;
	}
	
	public boolean isEmpty() {
		return mlAllStatements.size() == 0;
	}
	
	/**
	 * Note that only if isBlockStart returns true, str and statements are filled in BlockAnalyzer.
	 * @param str
	 * @return
	 */
	public boolean isBlockStart(String str) {
		String strSessionName = "";
        Statement sBlockStart = new Statement(str, strSessionName, 1);
        if (sBlockStart.isFinishedStatement() == false) {
        	// this is an incomplete statement
        	Statement sSessionStart = new Statement("function session_function()", strSessionName, 0);
        	try {
        		sSessionStart.analyze();
        	} catch (JMFPCompErrException e) {
        		// will not be here.
        	}
            mlAllStatements.add(sSessionStart);
            mlAllLines.add(str);
            mlAllStatements.add(sBlockStart);
            return true;
        } else {
	        try    {
	        	sBlockStart.analyze();
	        } catch (Exception e)   {
	            return false;	// The statement cannot be analyzed. So definitely it is not start of a block.
	        }
	        if (sBlockStart.mstatementType != null &&
	        		(sBlockStart.mstatementType.getType().equals("for")
	        		|| sBlockStart.mstatementType.getType().equals("while")
	        		|| sBlockStart.mstatementType.getType().equals("do")
	        		|| sBlockStart.mstatementType.getType().equals("select")
	        		|| sBlockStart.mstatementType.getType().equals("if")
	        		|| sBlockStart.mstatementType.getType().equals("try")
	        		|| sBlockStart.mstatementType.getType().equals("solve")
	        		|| sBlockStart.mstatementType.getType().equals("call"))
	        		) {
	        	mlistBlockStack.add(sBlockStart);
	        	Statement sSessionStart = new Statement("function session_function()", strSessionName, 0);
	        	try {
	        		sSessionStart.analyze();
	        	} catch (JMFPCompErrException e) {
	        		// will not be here.
	        	}
	            mlAllStatements.add(sSessionStart);
	            mlAllLines.add(str);
	            mlAllStatements.add(sBlockStart);
	        	return true;
	        } else {
	        	return false;
	        }
        }
	}
	
	/**
	 * Is the block still continuing?
	 * @param str : the statement.
	 * @return
	 * @throws JMFPCompErrException 
	 */
	public boolean isBlockContinue(String str) throws JMFPCompErrException {
		mlAllLines.add(str);
		if (str.trim().length() == 0) {
			return true;	// ignore blank line.
		}
		
		String strSessionName = "";
        Statement sLast = mlAllStatements.getLast();
        Statement sLine = new Statement(str, strSessionName, mlAllLines.size());
        Statement sCurrent = sLine;
        if (!mbInHelpBlock && !sLast.isFinishedStatement())    {
            // this statement needs to be appended to last one (only if not in a help block)
            sLast.concatenate(sLine);
            sCurrent = sLast;
            mlAllStatements.removeLast();	// remove last because it will be added back.
        }
        if (!mbInHelpBlock && !sCurrent.isFinishedStatement())    {
        	// the statement hasn't been finished yet.
        	mlAllStatements.addLast(sCurrent);
        	return true;
        } else {
	        try {
	            sCurrent.analyze();	// have to analyze sCurrent even in help block because we need to know where is endh
	        } catch(JMFPCompErrException e)    {
	            // analyzing might trigger some exceptions which should be ignored.
	            sCurrent.meAnalyze = e;
	        } catch (Exception e)   {
	            // analyzing might trigger some exceptions which should be ignored.
	            sCurrent.meAnalyze = e;
	        }
	        
	        boolean bAdded = false;
            if (!mbInHelpBlock)	{
                mlAllStatements.add(sCurrent);
                bAdded = true;
                if (sCurrent.mstatementType != null
                        && sCurrent.mstatementType.getType().equals("help"))	{
                    mbInHelpBlock = true;
                }
            } else if (sCurrent.mstatementType != null
                        && sCurrent.mstatementType.getType().equals("endh"))	{
                mlAllStatements.add(sCurrent);
                bAdded = true;
                mbInHelpBlock = false;
            }
            // is the statement valid?
            if (bAdded && sCurrent.meAnalyze != null)    {
                if (sCurrent.meAnalyze instanceof JMFPCompErrException) {
                    throw new JMFPCompErrException(sCurrent.mstrFilePath, sCurrent.mnEndLineNo, sCurrent.mnEndLineNo,
                            ((JMFPCompErrException)sCurrent.meAnalyze).m_se.m_enumErrorType);
                } else  {
                    throw new JMFPCompErrException(sCurrent.mstrFilePath, sCurrent.mnEndLineNo, sCurrent.mnEndLineNo,
                            ERRORTYPES.INVALID_EXPRESSION, sCurrent.meAnalyze);
                }
            }
            
            // ok, now let's check if it is the end of the block.
            if (sCurrent.mstatementType != null &&
	        		(sCurrent.mstatementType.getType().equals("for")
	        		|| sCurrent.mstatementType.getType().equals("while")
	        		|| sCurrent.mstatementType.getType().equals("do")
	        		|| sCurrent.mstatementType.getType().equals("select")
	        		|| sCurrent.mstatementType.getType().equals("if")
	        		|| sCurrent.mstatementType.getType().equals("try")
	        		|| sCurrent.mstatementType.getType().equals("solve")
	        		|| sCurrent.mstatementType.getType().equals("call"))
	        		) {
            	mlistBlockStack.add(sCurrent);
            } else if (sCurrent.mstatementType != null &&
            		(sCurrent.mstatementType.getType().equals("next")
            		|| sCurrent.mstatementType.getType().equals("loop")
            		|| sCurrent.mstatementType.getType().equals("until")
            		|| sCurrent.mstatementType.getType().equals("ends")
            		|| sCurrent.mstatementType.getType().equals("endif")
            		|| sCurrent.mstatementType.getType().equals("endtry")
            		|| sCurrent.mstatementType.getType().equals("slvreto")
            		|| sCurrent.mstatementType.getType().equals("endcall"))
            		) {
            	// is the end block statement paired?	
            	if (mlistBlockStack.size() > 0 && 
            			((mlistBlockStack.getLast().mstatementType.getType().equals("for") // mlistBlockStack.getLast().mstatementType mustnot be null.
	            				&& sCurrent.mstatementType.getType().equals("next"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("while")
	            				&& sCurrent.mstatementType.getType().equals("loop"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("do")
	            				&& sCurrent.mstatementType.getType().equals("until"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("select")
	            				&& sCurrent.mstatementType.getType().equals("ends"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("if")
	            				&& sCurrent.mstatementType.getType().equals("endif"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("try")
	            				&& sCurrent.mstatementType.getType().equals("endtry"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("solve")
	            				&& sCurrent.mstatementType.getType().equals("slvreto"))
	            		|| (mlistBlockStack.getLast().mstatementType.getType().equals("call")
	            				&& sCurrent.mstatementType.getType().equals("endcall"))
            		)) {
            		mlistBlockStack.removeLast();	// ok, it is paired.
            	} else {
            		throw new JMFPCompErrException(sCurrent.mstrFilePath, sCurrent.mnEndLineNo, sCurrent.mnEndLineNo,
                            ERRORTYPES.CANNOT_FIND_BEGINNING_OF_BLOCK);
            	}
            }
            
            if (mlistBlockStack.size() == 0) {
            	// the block is finished.
            	Statement sSessionEnd = new Statement("endf", strSessionName, sCurrent.mnEndLineNo + 1);
            	sSessionEnd.analyze();
            	mlAllStatements.add(sSessionEnd);
            	return false;
            } else {
            	return true;
            }
        }
	}
	
	public Statement[] getAllStatements() {
		return mlAllStatements.toArray(new Statement[0]);
	}
	
	public String[] getAllStrings() {
		return mlAllLines.toArray(new String[0]);
	}
	
	public String getWholeString() {
		String strOutput = "";
		for (String str : mlAllLines) {
			strOutput += str + "\n";
		}
		return strOutput;
	}
	
	public FunctionEntry getFunctionEntry() {
		if (mlAllStatements.size() == 0) {
			return null;
		} else {
			FunctionEntry functionEntry = new FunctionEntry(new String[0], (Statement_function)(mlAllStatements.getFirst().mstatementType),
	        		0, (Statement_endf)(mlAllStatements.getLast().mstatementType), mlAllStatements.size() - 1, new int[0], mlAllLines.toArray(new String[0]),
	        		mlAllStatements.toArray(new Statement[0]));
	        return functionEntry;
		}
	}
}
