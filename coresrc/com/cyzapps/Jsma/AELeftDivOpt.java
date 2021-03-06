// MFP project, AELeftDivOpt.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.Jsma;

import java.util.LinkedList;

import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.Operators.CalculateOperator;
import com.cyzapps.Jfcalc.DCHelper.DATATYPES;
import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.Operators.OPERATORTYPES;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.ExprEvaluator;
import com.cyzapps.Jmfp.ModuleInfo;
import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Jsma.PatternManager.PatternExprUnitMap;
import com.cyzapps.Jsma.SMErrProcessor.ERRORTYPES;
import com.cyzapps.Jsma.SMErrProcessor.JSmartMathErrException;
import com.cyzapps.Jsma.UnknownVarOperator.UnknownVariable;

public class AELeftDivOpt extends AbstractExpr {

	private static final long serialVersionUID = 1L;
	// here mlistChildren and mlistOpts are not used because it leads to difficulty in merging.
    public AbstractExpr maeLeft = AEInvalid.AEINVALID, maeRight = AEInvalid.AEINVALID;
    
    public AELeftDivOpt() {
        menumAEType = ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_LEFTDIV;
        maeLeft = AEInvalid.AEINVALID;
        maeRight = AEInvalid.AEINVALID;
    }
    
    public AELeftDivOpt(AbstractExpr aeLeft, AbstractExpr aeRight) throws JSmartMathErrException    {
        setAELeftDivOpt(aeLeft, aeRight);
    }

    public AELeftDivOpt(AbstractExpr aexprOrigin) throws JFCALCExpErrException, JSmartMathErrException    {
        copy(aexprOrigin);
    }

    @Override
    public void validateAbstractExpr() throws JSmartMathErrException {
        if (menumAEType != ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_LEFTDIV)    {
            throw new JSmartMathErrException(ERRORTYPES.ERROR_INCORRECT_ABSTRACTEXPR_TYPE);
        }
    }

    private void setAELeftDivOpt(AbstractExpr aeLeft, AbstractExpr aeRight) throws JSmartMathErrException    {
        menumAEType = ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_LEFTDIV;
        maeLeft = aeLeft;
        maeRight = aeRight;
        validateAbstractExpr();
    }

    @Override
    protected void copy(AbstractExpr aexprOrigin) throws JFCALCExpErrException,
            JSmartMathErrException {
        ((AELeftDivOpt)aexprOrigin).validateAbstractExpr();
        super.copy(aexprOrigin);
        maeLeft = ((AELeftDivOpt)aexprOrigin).maeLeft;
        maeRight = ((AELeftDivOpt)aexprOrigin).maeRight;
    }

    @Override
    protected void copyDeep(AbstractExpr aexprOrigin)
            throws JFCALCExpErrException, JSmartMathErrException {
        ((AELeftDivOpt)aexprOrigin).validateAbstractExpr();
        super.copyDeep(aexprOrigin);
        maeLeft = ((AELeftDivOpt)aexprOrigin).maeLeft.cloneSelf();
        maeRight = ((AELeftDivOpt)aexprOrigin).maeRight.cloneSelf();
    }

    @Override
    public AbstractExpr cloneSelf() throws JFCALCExpErrException,
            JSmartMathErrException {
        AbstractExpr aeReturn = new AELeftDivOpt();
        aeReturn.copyDeep(this);
        return aeReturn;
    }

    @Override
    public int[] recalcAExprDim(boolean bUnknownAsSingle) throws JSmartMathErrException,
            JFCALCExpErrException {
        validateAbstractExpr();

        int[] narrayDimReturn = maeLeft.recalcAExprDim(bUnknownAsSingle);
        int[] narrayOprd = maeRight.recalcAExprDim(bUnknownAsSingle);
        if (narrayDimReturn.length == 0)    {    // left operand is a number, not a matrix
            narrayDimReturn = narrayOprd;
        } else    {
            if (narrayDimReturn.length - 1 > narrayOprd.length)    {
                throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_ARRAY_DIM_DOES_NOT_MATCH);
            }
            for (int idx1 = 0; idx1 < narrayDimReturn.length - 1; idx1 ++)    {
                if (narrayDimReturn[idx1] != narrayOprd[idx1])    {
                    throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_ARRAY_DIM_DOES_NOT_MATCH);
                }
            }
            int[] narrayLeftOprd = narrayDimReturn;
            narrayDimReturn = new int[narrayOprd.length - narrayLeftOprd.length + 2];
            for (int idx1 = 1; idx1 <= narrayOprd.length - narrayLeftOprd.length + 1; idx1 ++)    {
                narrayDimReturn[idx1] = narrayOprd[idx1 + narrayLeftOprd.length - 2];
            }
            narrayDimReturn[0] = narrayLeftOprd[narrayLeftOprd.length - 1];
        }
        return narrayDimReturn;
    }

    // note that progContext can be null. If null, it is unconditional equal.
    @Override
    public boolean isEqual(AbstractExpr aexpr, ProgContext progContext) throws JFCALCExpErrException {
        if (menumAEType != aexpr.menumAEType)    {
            return false;
        } else if (maeLeft.isEqual(((AELeftDivOpt)aexpr).maeLeft, progContext) == false)    {
            return false;
        } else if (maeRight.isEqual(((AELeftDivOpt)aexpr).maeRight, progContext) == false)    {
            return false;
        } else    {
            return true;
        }
    }
    
    @Override
    public int getHashCode() throws JFCALCExpErrException {
        int hashRet = menumAEType.hashCode()
                + maeLeft.getHashCode() * 13 + maeRight.getHashCode() * 19;
        return hashRet;
    }

    @Override
    public boolean isPatternMatch(AbstractExpr aePattern,
                                LinkedList<PatternExprUnitMap> listpeuMapPseudoFuncs,
                                LinkedList<PatternExprUnitMap> listpeuMapPseudoConsts,
                                LinkedList<PatternExprUnitMap> listpeuMapUnknowns,
                                boolean bAllowConversion, ProgContext progContext) throws JFCALCExpErrException, JSmartMathErrException, InterruptedException  {
        if (aePattern.menumAEType == ABSTRACTEXPRTYPES.ABSTRACTEXPR_VARIABLE)   {
            // unknown variable
            for (int idx = 0; idx < listpeuMapUnknowns.size(); idx ++)  {
                if (listpeuMapUnknowns.get(idx).maePatternUnit.isEqual(aePattern, progContext))    {
                    if (isEqual(listpeuMapUnknowns.get(idx).maeExprUnit, progContext))   {
                        // this unknown variable has been mapped to an expression and the expression is the same as this
                        return true;
                    } else  {
                        // this unknown variable has been mapped to an expression but the expression is not the same as this
                        return false;
                    }
                }
            }
            // the aePattern is an unknown variable and it hasn't been mapped to some expressions before.
            PatternExprUnitMap peuMap = new PatternExprUnitMap(this, aePattern);
            listpeuMapUnknowns.add(peuMap);
            return true;
        }
        if (!(aePattern instanceof AELeftDivOpt))   {
            return false;
        }
        if (maeLeft.isPatternMatch(((AELeftDivOpt)aePattern).maeLeft, listpeuMapPseudoFuncs, listpeuMapPseudoConsts, listpeuMapUnknowns, bAllowConversion, progContext) == false) {
            return false;
        }
        if (maeRight.isPatternMatch(((AELeftDivOpt)aePattern).maeRight, listpeuMapPseudoFuncs, listpeuMapPseudoConsts, listpeuMapUnknowns, bAllowConversion, progContext) == false) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean isKnownValOrPseudo() {
        if (!maeLeft.isKnownValOrPseudo())    {
            return false;
        }
        if (!maeRight.isKnownValOrPseudo())    {
            return false;
        }
        return true;
    }
    
    // note that the return list should not be changed.
    @Override
    public LinkedList<AbstractExpr> getListOfChildren()    {
        LinkedList<AbstractExpr> listChildren = new LinkedList<AbstractExpr>();
        listChildren.add(maeLeft);
        listChildren.add(maeRight);
        return listChildren;
    }

    @Override
    public AbstractExpr copySetListOfChildren(LinkedList<AbstractExpr> listChildren)  throws JFCALCExpErrException, JSmartMathErrException {
        if (listChildren == null || listChildren.size() != 2) {
            throw new JSmartMathErrException(ERRORTYPES.ERROR_INVALID_ABSTRACTEXPR);            
        }
        AELeftDivOpt aeReturn = new AELeftDivOpt();
        aeReturn.copy(this);
        aeReturn.maeLeft = listChildren.getFirst();
        aeReturn.maeRight = listChildren.getLast();
        aeReturn.validateAbstractExpr();
        return aeReturn;
    }
    
    // this function replaces children who equal aeFrom to aeTo and
    // returns the number of children that are replaced.
    @Override
    public AbstractExpr replaceChildren(LinkedList<PatternExprUnitMap> listFromToMap, boolean bExpr2Pattern,
            LinkedList<AbstractExpr> listReplacedChildren, ProgContext progContext) throws JFCALCExpErrException, JSmartMathErrException    {
        AELeftDivOpt aeReturn = new AELeftDivOpt();
        aeReturn.copy(this);
        for (int idx = 0; idx < listFromToMap.size(); idx ++)    {
            AbstractExpr aeFrom = bExpr2Pattern?listFromToMap.get(idx).maeExprUnit:listFromToMap.get(idx).maePatternUnit;
            AbstractExpr aeTo = bExpr2Pattern?listFromToMap.get(idx).maePatternUnit:listFromToMap.get(idx).maeExprUnit;
            if (aeReturn.maeLeft.isEqual(aeFrom, progContext))    {
                aeReturn.maeLeft = aeTo;    // no need to clone coz AbstractExpr is immutable
                listReplacedChildren.add(aeReturn.maeLeft);
                break;
            }
        }
        
        for (int idx = 0; idx < listFromToMap.size(); idx ++)    {
            AbstractExpr aeFrom = bExpr2Pattern?listFromToMap.get(idx).maeExprUnit:listFromToMap.get(idx).maePatternUnit;
            AbstractExpr aeTo = bExpr2Pattern?listFromToMap.get(idx).maePatternUnit:listFromToMap.get(idx).maeExprUnit;
            if (aeReturn.maeRight.isEqual(aeFrom, progContext))    {
                aeReturn.maeRight = aeTo;    // no need to clone coz AbstractExpr is immutable
                listReplacedChildren.add(aeReturn.maeRight);
                break;
            }
        }
        return aeReturn;
    }

    @Override
    public AbstractExpr distributeAExpr(SimplifyParams simplifyParams, ProgContext progContext) throws JFCALCExpErrException, JSmartMathErrException    {
        validateAbstractExpr();
        if (maeRight instanceof AEPosNegOpt)    {
            AEPosNegOpt aeRight = new AEPosNegOpt();
            aeRight.copy(maeRight);
            for (int idx = 0; idx < aeRight.mlistChildren.size(); idx ++)    {
                AELeftDivOpt aeNewChild = new AELeftDivOpt(maeLeft, aeRight.mlistChildren.get(idx));
                aeRight.mlistChildren.set(idx, aeNewChild);
            }
            return aeRight;
        }
        return this;
    }

    // avoid to do any overhead work.
    @Override
    public DataClass evaluateAExprQuick(LinkedList<UnknownVariable> lUnknownVars, ProgContext progContext)
            throws InterruptedException, JSmartMathErrException, JFCALCExpErrException {
        validateAbstractExpr(); // still needs to do some basic validation.
        DataClass datumLeft = maeLeft.evaluateAExprQuick(lUnknownVars, progContext);
        DataClass datumRight = maeRight.evaluateAExprQuick(lUnknownVars, progContext);
        DataClass datum = ExprEvaluator.evaluateTwoOperandCell(datumLeft, new CalculateOperator(OPERATORTYPES.OPERATOR_LEFTDIVIDE, 2), datumRight);
        return datum;        
    }
    
    // avoid to do any overhead work.
    @Override
    public AbstractExpr evaluateAExpr(LinkedList<UnknownVariable> lUnknownVars, ProgContext progContext)
            throws InterruptedException, JSmartMathErrException, JFCALCExpErrException {
        validateAbstractExpr(); // still needs to do some basic validation.
        AbstractExpr aeLeft = maeLeft.evaluateAExpr(lUnknownVars, progContext);
        AbstractExpr aeRight = maeRight.evaluateAExpr(lUnknownVars, progContext);
        if (aeLeft instanceof AEConst && aeRight instanceof AEConst) {
            DataClass datum = ExprEvaluator.evaluateTwoOperandCell(((AEConst)aeLeft).getDataClassRef(),
                                                                new CalculateOperator(OPERATORTYPES.OPERATOR_LEFTDIVIDE, 2),
                                                                ((AEConst)aeRight).getDataClassRef());
            return new AEConst(datum);        
        } else {
            return new AELeftDivOpt(aeLeft, aeRight);
        }
    }
    
    @Override
    public AbstractExpr simplifyAExpr(LinkedList<UnknownVariable> lUnknownVars, SimplifyParams simplifyParams, ProgContext progContext)
            throws InterruptedException, JSmartMathErrException,
            JFCALCExpErrException {
        validateAbstractExpr();
        
        AbstractExpr aeLeft = maeLeft.simplifyAExpr(lUnknownVars, simplifyParams, progContext);
        AbstractExpr aeRight = maeRight.simplifyAExpr(lUnknownVars, simplifyParams, progContext);
        
        LinkedList<AbstractExpr> listChildren = new LinkedList<AbstractExpr>();
        LinkedList<CalculateOperator> listOpts = new LinkedList<CalculateOperator>();
        listChildren.add(aeLeft);
        listChildren.add(aeRight);
        listOpts.add(new CalculateOperator(OPERATORTYPES.OPERATOR_DIVIDE, 2));
        listOpts.add(new CalculateOperator(OPERATORTYPES.OPERATOR_MULTIPLY, 2));
        AbstractExpr aeReturn = new AEMulDivOpt(listChildren, listOpts);
        aeReturn = aeReturn.distributeAExpr(simplifyParams, progContext);
        return aeReturn;
    }

    @Override
    public boolean needBracketsWhenToStr(ABSTRACTEXPRTYPES enumAET, int nLeftOrRight)  {    
        // null means no opt, nLeftOrRight == -1 means on left, == 0 means on both, == 1 means on right
        if ((enumAET.getValue() >= ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_POWER.getValue()
                    && enumAET.getValue() <= ABSTRACTEXPRTYPES.ABSTRACTEXPR_INDEX.getValue())
                || ((enumAET.getValue() ==  ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_MULTIPLYDIV.getValue()
                    || enumAET.getValue() ==  ABSTRACTEXPRTYPES.ABSTRACTEXPR_BOPT_LEFTDIV.getValue())
                && nLeftOrRight <= 0))    {
            return true;
        }
        return false;
    }
    
    @Override
    public int compareAExpr(AbstractExpr aexpr) throws JSmartMathErrException, JFCALCExpErrException {
        if (menumAEType.getValue() < aexpr.menumAEType.getValue())    {
            return 1;
        } else if (menumAEType.getValue() > aexpr.menumAEType.getValue())    {
            return -1;
        } else    {
            int nReturn = maeLeft.compareAExpr(((AELeftDivOpt)aexpr).maeLeft);
            if (nReturn == 0)    {
                nReturn = maeRight.compareAExpr(((AELeftDivOpt)aexpr).maeRight);
            }
            return nReturn;
        }
    }
    
    // identify if it is very, very close to 0 or zero array. Assume the expression has been simplified most
    @Override
    public boolean isNegligible(ProgContext progContext) throws JSmartMathErrException, JFCALCExpErrException    {
        validateAbstractExpr();
        return maeRight.isNegligible(progContext);
    }
    
    // output the string based expression of any abstract expression type.
    @Override
    public String output()    throws JFCALCExpErrException, JSmartMathErrException {
        validateAbstractExpr();
        boolean bLeftNeedBracketsWhenToStr = false, bRightNeedBracketsWhenToStr = false;
        bLeftNeedBracketsWhenToStr = maeLeft.needBracketsWhenToStr(menumAEType, 1);
        bRightNeedBracketsWhenToStr = maeRight.needBracketsWhenToStr(menumAEType, -1);
        
        String strOutput = "";
        if (bLeftNeedBracketsWhenToStr) {
            strOutput += "(" + maeLeft.output() + ")";
        } else  {
            strOutput += maeLeft.output();
        }
        strOutput += "\\";
        if (bRightNeedBracketsWhenToStr)    {
            strOutput += "(" + maeRight.output() + ")";
        } else  {
            strOutput += maeRight.output();
        }
        return strOutput;
    }

    @Override
    public String outputWithFlag(int flag, ProgContext progContextNow)    throws JFCALCExpErrException, JSmartMathErrException {
        validateAbstractExpr();
        boolean bLeftNeedBracketsWhenToStr = false, bRightNeedBracketsWhenToStr = false;
        bLeftNeedBracketsWhenToStr = maeLeft.needBracketsWhenToStr(menumAEType, 1);
        bRightNeedBracketsWhenToStr = maeRight.needBracketsWhenToStr(menumAEType, -1);
        
        String strOutput = "";
        if (bLeftNeedBracketsWhenToStr) {
            strOutput += "(" + maeLeft.outputWithFlag(flag, progContextNow) + ")";
        } else  {
            strOutput += maeLeft.outputWithFlag(flag, progContextNow);
        }
        strOutput += "\\";
        if (bRightNeedBracketsWhenToStr)    {
            strOutput += "(" + maeRight.outputWithFlag(flag, progContextNow) + ")";
        } else  {
            strOutput += maeRight.outputWithFlag(flag, progContextNow);
        }
        return strOutput;
    }

    @Override
    public AbstractExpr convertAEVar2AExprDatum(LinkedList<String> listVars, boolean bNotConvertVar, LinkedList<String> listCvtedVars) throws JSmartMathErrException, JFCALCExpErrException {
        AbstractExpr aeLeft = (maeLeft instanceof AEConst)?maeLeft:maeLeft.convertAEVar2AExprDatum(listVars, bNotConvertVar, listCvtedVars);
        AbstractExpr aeRight = (maeRight instanceof AEConst)?maeRight:maeRight.convertAEVar2AExprDatum(listVars, bNotConvertVar, listCvtedVars);
        return new AELeftDivOpt(aeLeft, aeRight);
    }

    @Override
    public AbstractExpr convertAExprDatum2AExpr() throws JSmartMathErrException {
        AbstractExpr aeLeft = maeLeft, aeRight = maeRight;
        if (maeLeft instanceof AEConst
                && DCHelper.isDataClassType(((AEConst)maeLeft).getDataClassRef(), DATATYPES.DATUM_ABSTRACT_EXPR)) {
            try {
                aeLeft = DCHelper.lightCvtOrRetDCAExpr(((AEConst)maeLeft).getDataClassRef()).getAExpr();
            } catch (JFCALCExpErrException e) {
                // cast to DataClassAExpr will be ok coz the type has been checked. So will not be here.
                e.printStackTrace();
            }
        }
        if (maeRight instanceof AEConst
                && DCHelper.isDataClassType(((AEConst)maeRight).getDataClassRef(), DATATYPES.DATUM_ABSTRACT_EXPR)) {
            try {
                aeRight = DCHelper.lightCvtOrRetDCAExpr(((AEConst)maeRight).getDataClassRef()).getAExpr();
            } catch (JFCALCExpErrException e) {
                // cast to DataClassAExpr will be ok coz the type has been checked. So will not be here.
                e.printStackTrace();
            }
        }
        return new AELeftDivOpt(aeLeft, aeRight);
    }

    @Override
    public int getVarAppearanceCnt(String strVarName) {
        int nCnt = maeLeft.getVarAppearanceCnt(strVarName);
        nCnt += maeRight.getVarAppearanceCnt(strVarName);
        return nCnt;
    }

    @Override
    public LinkedList<ModuleInfo> getReferredModules(ProgContext progContext) {
        LinkedList<AbstractExpr> listChildren = new LinkedList<AbstractExpr>();
        listChildren.add(maeLeft);
        listChildren.add(maeRight);
        return this.getReferredFunctionsFromAExprs(listChildren, progContext);
    }
    
}
