// MFP project, CommandUI.java : Designed and developed by Tony Cui in 2021
package jcmdline;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import com.cyzapps.JPlatformHW.PlatformHWManager;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.FuncEvaluator;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jfcalc.FuncEvaluator.ConsoleInputStream;
import com.cyzapps.Jfcalc.FuncEvaluator.LogOutputStream;
import com.cyzapps.Jmfp.ErrorProcessor.JMFPCompErrException;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.Oomfp.CitingSpaceDefinition.CheckMFPSLibMode;
import com.cyzapps.adapter.MFPAdapter;
import com.cyzapps.adapter.MFPSProcessor;
import com.cyzapps.adapter.MFPSProcessor.MFPSExecInfo;
import java.awt.GraphicsEnvironment;
import java.awt.SplashScreen;
import static jcmdline.JCmdLineApp.mssplashScreen;


public class CommandUI extends JCmdLineApp {
	
    public static final String STRING_APP_VERSION = "2.0.0.84";
    
    public static final int CONSOLE_INTERACTIVE_MODE = 0;
    public static final int GUI_INTERACTIVE_MODE = 1;
    public static final int RUN_SCRIPT = 2;
    public static final int PRINT_SCRIPT_USAGE = 3;
    public static final int GET_VERSION = 4;
    public static final int GET_HELP = 5;
	
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
    	
        CommandUI cmdUI = new CommandUI();
    	MFP4JavaFileMan.msstrAppFolder = CommandUI.getProgramFolder();	// set program folder.
    	if (!cmdUI.analyseCommand(args)) {
    		return;	// invalid command
    	} else if (cmdUI.moperatingType == PRINT_SCRIPT_USAGE) {
    		String strFileInfo = null;
    		try {
	    		MFPSProcessor mfpsProcessor = new MFPSProcessor(cmdUI.mstrScriptFileName);
	    		strFileInfo = mfpsProcessor.getFileInfo();
    		} catch(IOException e) {
    			// do nothing, because we will complain soon.
    		}
    		if (strFileInfo == null) {
    			System.out.println(getStringsClass().get_cannot_open_file());
    		} else {
    			System.out.println(cmdUI.mstrScriptFileName + " :");
    			System.out.println(strFileInfo);
    		}
    		return;
    	} else if (cmdUI.moperatingType == GET_VERSION) {
    		System.out.println(STRING_APP_VERSION);
    		return;
    	} else if (cmdUI.moperatingType == GET_HELP) {
    		System.out.println(getStringsClass().get_command_UI_help());
    		return;
    	} else if (cmdUI.moperatingType == CONSOLE_INTERACTIVE_MODE) {
    		// interactive mode
            if (!GraphicsEnvironment.isHeadless()) {
                mssplashScreen = SplashScreen.getSplashScreen();
                if (mssplashScreen != null) {   // if there are any problems displaying the splash this will be null
                    mssplashScreen.close(); // as we are in command line, close splash box.
                }
            }
	        cmdUI.commandLineStartUp();
            cmdUI.showInputPrompt();
            cmdUI.reactWithUser();
            return;
    	} else if (cmdUI.moperatingType == GUI_INTERACTIVE_MODE) {
    		// interactive mode
	        launch(JCmdLineApp.class, args);
            return;
    	} else if (cmdUI.moperatingType == RUN_SCRIPT) {
            if (!GraphicsEnvironment.isHeadless()) {
                mssplashScreen = SplashScreen.getSplashScreen();
                if (mssplashScreen != null) {   // if there are any problems displaying the splash this will be null
                    mssplashScreen.close(); // as we are in command line, close splash box.
                }
            }
    		LangFileManager.mbOutputCSDebugInfo = false;
    		cmdUI.commandLineStartUp();
    		MFPSExecInfo mfpsExecInfo = null;
    		String strExpr = "";
    		boolean bFileCanBeOpened = true;
    		try {
				MFPSProcessor mfpsProcessor = new MFPSProcessor(cmdUI.mstrScriptFileName);
				mfpsExecInfo = mfpsProcessor.getExecInfo();
			} catch (IOException e) {
				bFileCanBeOpened = false;
			}
    		
    		if (!bFileCanBeOpened) {
    			System.out.println(getStringsClass().get_cannot_open_file());
    		} else if (mfpsExecInfo == null) {
    			System.out.println(getStringsClass().get_invalid_entry_function());
    		} else {
    			strExpr = MFPAdapter.parseScriptParams(cmdUI.mstrarrayFileParams, mfpsExecInfo);
    			if (strExpr != null) {
	    			System.out.println(getStringsClass().get_call() + " " + strExpr + " ...");
	    			if (cmdUI.mBufferedReader == null) {
	    				try {
							cmdUI.mBufferedReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// if not support UTF-8, use default encoding.
							cmdUI.mBufferedReader = new BufferedReader(new InputStreamReader(System.in));
						}
	    	    	}
	    			cmdUI.processInput(strExpr);
    			} else {
    				System.out.println(getStringsClass().get_invalid_number_of_parameters());
    			}
    		}
    		System.exit(0);
    	} else {
    		System.out.println(getStringsClass().get_invalid_command_option());
    		return;
    	}
    }

	protected static final String NEXT_INPUT_PROMPT = "$>";
	
	protected static final String NEXT_LINE_PROMPT = "~~";
	
	protected BufferedReader mBufferedReader = null;
	
	protected BlockAnalyzer mBlockAnalyzer = new BlockAnalyzer();

	protected String mstrScriptFileName = "";

	protected String[] mstrarrayFileParams = new String[0];

	protected int moperatingType = 0;	// 0 means do interactive mode, 1 means run script, 2 means get file help, 3 means version, 4 means help.
    
	public Boolean analyseCommand(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            // non gui mode
            moperatingType = CONSOLE_INTERACTIVE_MODE;
        } else {
            // gui mode
            moperatingType = GUI_INTERACTIVE_MODE;
        }
		
    	for (int idx = 0; idx < args.length; idx ++) {
    		if (args[idx].equals("-L") || args[idx].equals("/L")) {
    			idx ++;
    			if (idx == args.length) {
    				System.out.println(getStringsClass().get_no_lib_path());
    				return false;
    			} else {
    				String strLibPath = args[idx];
	    			try {
						// all lib path must be converted to canonical. Note that here we need not
	    				// to worry about relative path because all the relative paths are relative
	    				// to working folder, not binary folder.
    					strLibPath = IOLib.getCanonicalPath(strLibPath);
    			        while (strLibPath.length() >= LangFileManager.STRING_PATH_DIVISOR.length()
    			                && strLibPath.substring(strLibPath.length() - LangFileManager.STRING_PATH_DIVISOR.length())
    			                    .equals(LangFileManager.STRING_PATH_DIVISOR))    {
    			            // remove the last path divisor if there is
    			        	strLibPath = strLibPath.substring(0, LangFileManager.msstrScriptFolder.length()
    			                                                - LangFileManager.STRING_PATH_DIVISOR.length());
    			        }
    					if (!MFP4JavaFileMan.madditionalUsrLibs.contains(strLibPath)) {
    						MFP4JavaFileMan.madditionalUsrLibs.add(strLibPath);
    					}
    					continue;
					} catch (JFCALCExpErrException e) {
						System.out.println(strLibPath + " : " + getStringsClass().get_invalid_lib_path());
	    				return false;
					}
    			}
    		} else if (args[idx].equals("-f") || args[idx].equals("/f")) {
    			idx ++;
    			if (idx == args.length) {
    				System.out.println(getStringsClass().get_no_script_file_name());
    				return false;
    			} else {
    				mstrScriptFileName = args[idx];
    				mstrarrayFileParams = new String[args.length - idx - 1];
    				System.arraycopy(args, idx+1, mstrarrayFileParams, 0, args.length - idx - 1);
    				moperatingType = RUN_SCRIPT;
    				return true;
    			}
    		} else if (args[idx].equals("-i") || args[idx].equals("/i")) {
    			idx ++;
    			if (idx == args.length) {
    				System.out.println(getStringsClass().get_no_script_file_name());
    				return false;
    			} else {
    				mstrScriptFileName = args[idx];
    				moperatingType = PRINT_SCRIPT_USAGE;
    				return true;
    			}
    		} else if (args[idx].equals("-v") || args[idx].equals("/v")) {
    			moperatingType = GET_VERSION;	// version.
				return true;
    		} else if (args[idx].equals("-h") || args[idx].equals("/h")) {
    			moperatingType = GET_HELP;	// help.
				return true;
    		} else if (args[idx].equals("-c") || args[idx].equals("/c")) {
                if (moperatingType == GUI_INTERACTIVE_MODE) {
                    moperatingType = CONSOLE_INTERACTIVE_MODE;	// we just want to run console mode.
                } // else we do nothing because other modes are all console based.
				return true;
    		} else if (args[idx].equals("-g") || args[idx].equals("/g")) {
                if (GraphicsEnvironment.isHeadless()) {
                    System.out.println(getStringsClass().get_gui_not_supported());
                    return false;
                } else {
                    if (moperatingType == CONSOLE_INTERACTIVE_MODE) {
                        moperatingType = GUI_INTERACTIVE_MODE;	// we just want to run GUI mode.
                    }
                    return true;
                } // else we do nothing because other modes are all console based.
    		} else {
    			System.out.println(args[idx] + " : " + getStringsClass().get_invalid_command_option());
				return false;
    		}
    	}
    	return true;
	}
	
    public static String getProgramFolder() {
        // get jar file path
        String strJarPath = CommandUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            strJarPath = URLDecoder.decode(strJarPath, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // do not decode strJarPath.
        }
        // in windows, to lower case is OK. But cannot do this in Linux.
        // so the standard is like this:
        // the path of app folder is case sensative (e.g. "C:\JCmdLine\Build")
        // however, a sub folder inside app folder is case insensative.
        return new File(strJarPath).getParentFile().getPath();//.toLowerCase(Locale.US);
    }
	
    /**
     * At startup to initialize system.
     */
    protected void commandLineStartUp() {
        if (moperatingType != RUN_SCRIPT) {	// run script do not output verbose info.
        	System.out.println(getStringsClass().get_starting());
        }
   	
        // before load settings, put cmd line param user libs in another list.
    	LinkedList<String> listCmdParamUsrLibs = new LinkedList<String>();
    	listCmdParamUsrLibs.addAll(MFP4JavaFileMan.madditionalUsrLibs);
    	MFP4JavaFileMan.madditionalUsrLibs.clear();
    	//MFP4JavaFileMan.msstrAppFolder = System.getProperty("user.dir"); // settings file is always located in a specific folder
    	int loadingFailed = startupLoadSettings();
        if (loadingFailed == 1 && moperatingType != RUN_SCRIPT) {	// run script do not output verbose info.
        	System.out.println(getStringsClass().get_cannot_find_settings_file_use_default());
        }
        // add cmd line param user libs back. Filter off duplicates.
        listCmdParamUsrLibs.stream().filter((strLibPath) -> (!MFP4JavaFileMan.madditionalUsrLibs.contains(strLibPath)
                && !JCmdLineApp.mMFP4JavaFileMan.getScriptFolderFullPath().equals(strLibPath))).forEachOrdered((strLibPath) -> {
                    // if it is not a duplicate, load it.
                    MFP4JavaFileMan.madditionalUsrLibs.add(strLibPath);
        });

        if (moperatingType != RUN_SCRIPT) {	// run script do not output verbose info.
        	System.out.println(getStringsClass().get_unzipping_asset_files());
        }

        LinkedList<String> listErrMsgs = new LinkedList<String>();
        int nErrorType = 0; // 1 means AnMath is not in a mapped partition folder (some phones' USB driver may lead to the problem)
                            // 2 means AnMath is in a read only folder.
        nErrorType = startupSetupFiles(listErrMsgs, nErrorType);

        if (moperatingType != RUN_SCRIPT) {	// run script do not output verbose info.
        	System.out.println(getStringsClass().get_loading_functions());
        }
        
        nErrorType = startupLoadLibs(listErrMsgs, nErrorType, (moperatingType == RUN_SCRIPT)? mstrScriptFileName : null);
        
        String strErrs = startupOutputError(listErrMsgs, nErrorType);
        if (strErrs.length() > 0) {
            // output error if there are error(s).
            System.out.println(getStringsClass().get_warning());
            System.out.println(strErrs);
        }
    }
    
	public void showInputPrompt() {
		System.out.print(NEXT_INPUT_PROMPT);
	}

	public void showNewLinePrompt() {
		System.out.print(NEXT_LINE_PROMPT);
	}
	
    public void reactWithUser()  {
    	if (mBufferedReader == null) {
    		try {
	    		mBufferedReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// if not support UTF-8, use default encoding.
				mBufferedReader = new BufferedReader(new InputStreamReader(System.in));
			}
    	}
    	
    	// make sure that writing to screen always happen in the main thread.
    	// However, reading input can happen in different thread.
    	while (true) {
    		// step 1: read command.
    		String strInput;
			try {
				strInput = mBufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;	// jump out
			}
			
			// step 2: process command.
			if (strInput != null) {	// ctrl-Z or ctrl-D may lead to a null strINput. In windows, ctrl-C also.
				if (mBlockAnalyzer.isEmpty()) {
					if (mBlockAnalyzer.isBlockStart(strInput)) {
						showNewLinePrompt();	// start to input block
						continue;
					} else {
						processInput(strInput);
				        mBlockAnalyzer.reset();
				        showInputPrompt();
					}
				} else {	// we have been in block.
					boolean bBlockCnt = false;
					try {
						bBlockCnt = mBlockAnalyzer.isBlockContinue(strInput);
						if (bBlockCnt) {
							showNewLinePrompt();	// continue to input block
							continue;
						} else {
							processInput(null);
					        mBlockAnalyzer.reset();
					        showInputPrompt();
						}
					} catch (JMFPCompErrException e) {
						// the input is not valid.
						System.out.println(MFPAdapter.outputException(e));	// output exception.
						mBlockAnalyzer.reset();
						showInputPrompt();
						continue;
					}
				}
			} else {
				continue;
			}
       	}
		if (mBufferedReader != null) {
			try {
				mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            	mBufferedReader = null;
            }
		}
		mBlockAnalyzer.reset();
    }
        
    // this inner class cannot be static because it uses member variable(s) in JCmdLineApp
    public class SysConsoleInput extends ConsoleInputStream    {
        public void doBeforeInput() {
        }
        
        public String inputString() throws InterruptedException    {
            String strInput = "";
            try {
            	// mBufferedReader cannot be null
            	strInput = mBufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strInput;
        }
        
        public void doAfterInput()  {
            
        }
    }
    
    // this inner class cannot be static because it uses member variable(s) in JCmdLineApp
    public class SysCmdLineLogOutput extends LogOutputStream    {
    	@Override
        public void outputString(String str) throws InterruptedException    {
    		System.out.print(str);
        }
    }

    @Override
    public void processInput(String strInput)   {
        initFuncEvaluatorMgrs(new SysCmdLineLogOutput(), new SysConsoleInput());
        try    {
        	if (strInput != null) {
        		processIndividualInput(strInput, 1);    // individual input is always the first line
        	} else {
        		processMultipleInputs(mBlockAnalyzer.getWholeString(), mBlockAnalyzer.getAllStrings());
        	}
        } catch(InterruptedException e)    {
            System.err.println("Thread receive exception : " + e.toString());
        }
    }
    
    @Override
    public void outputMultiLineResult(String strOriginalInput, String strReturn, String strOutput) throws InterruptedException {
        new SysCmdLineLogOutput().outputString(strOutput + "\n");
    }
    
    @Override
    public void processIndividualInput(String strInput, int nLine) throws InterruptedException    {
        String strOutput = processCmd(strInput, nLine);
        new SysCmdLineLogOutput().outputString(strOutput);
    }
}
