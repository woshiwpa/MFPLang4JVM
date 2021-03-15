/*
 * MFP project, ExprEvaluatorTest.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.Jfcalc;

import com.cyzapps.Jfcalc.DCHelper.CurPos;
import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Jmfp.VariableOperator.Variable;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tony
 */
public class ExprEvaluatorTest {
    
    public ExprEvaluatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAndOr() throws InterruptedException {
        try {
            DataClass datum1 = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, MFPNumeric.ONE);
            DataClass datum2 = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, MFPNumeric.TWO);
            DataClass[] datumAArray = new DataClass[] { datum1, datum2};
            DataClass datumA = new DataClassArray(datumAArray);
            DataClass datum3 = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(3));
            DataClass datum4 = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(4));
            DataClass datum5 = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(5));
            DataClass[] datumFArray = new DataClass[] { datum3, datum4, datum5};
            DataClass datumF = new DataClassArray(datumFArray);
            DataClass datumG = new DataClassString("Hello");

            Variable varA = new Variable("a", datumA);  // a is [1,2]
            Variable varB = new Variable("b");  // b is null
            Variable varF = new Variable("f", datumF);  // f is [1,2,3]
            Variable varG = new Variable("g", datumG);  // g is "Hello"

            LinkedList<Variable> lVarNameSpace = new LinkedList<Variable>();
            lVarNameSpace.add(varA);
            lVarNameSpace.add(varB);
            lVarNameSpace.add(varF);
            lVarNameSpace.add(varG);

            LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
            lVarNameSpaces.add(lVarNameSpace);

            ProgContext progContext = new ProgContext();
            progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;

            ExprEvaluator exprEvaluator = new ExprEvaluator(progContext);
            DataClass datum;
            datum = exprEvaluator.evaluateExpression("and(b != null, b[3] == 9)", new CurPos());
            assertEquals(false, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("or(b != null, b[3] == 9)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_INVALID_DATA_TYPE, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("or(b != null, a != null, a[4] == \"Hello\")", new CurPos());
            assertEquals(true, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("and(a != null, size(a)[0] > 1, size(a[1])[0] == 2, a[0] == 1)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_WRONG_INDEX, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("and(b != null, d == 7)", new CurPos());
            assertEquals(false, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("or(b != null, d == 7)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_UNDEFINED_VARIABLE, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("and(size(a) == [2], a[1] > 7, evaluate(\"d+7\"))", new CurPos());
            assertEquals(false, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("and(size(a) == [2], evaluate(\"d+7\"))", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER, e.m_se.m_enumErrorType);
            }

            try {
                datum = exprEvaluator.evaluateExpression("or(size(a) == [2], strlen(\"abc) == 3)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_CANNOT_FIND_CLOSE_QUATATION_MARK_FOR_STRING, e.m_se.m_enumErrorType);
            }

            try {
                datum = exprEvaluator.evaluateExpression("or(size(a) == [2], size(b) == []])", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_INVALID_CHARACTER, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("and(size(f) != [3], aaaaaaa(g))", new CurPos());
            assertEquals(false, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("or(size(f) != [3], aaaaaaa(g))", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_UNDEFINED_FUNCTION, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("and(size(f) != [3], print(g))", new CurPos());
            assertEquals(false, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());
            
            try {
                datum = exprEvaluator.evaluateExpression("or(size(f) != [3], print(g))", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_VOID_DATA, e.m_se.m_enumErrorType);
            }
            
            try {
                datum = exprEvaluator.evaluateExpression("and(size(f) != [3], 37 = 9)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_CANNOT_ASSIGN_VALUE_TO_ANYTHING_EXCEPT_VARIALBE_USE_EQUAL_INSTEAD, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("or(size(f) >=3, g/2)", new CurPos());
            assertEquals(true, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());
            
            try {
                datum = exprEvaluator.evaluateExpression("and(size(f) >=3, g/2)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_CANNOT_CHANGE_DATATYPE_FROM_STRING, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("or(size(f) >=3, [1,2,3]*[[4,5,6],[7,8,9]])", new CurPos());
            assertEquals(true, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());
            
            try {
                datum = exprEvaluator.evaluateExpression("and(size([[4,5,6],[7,8,9]]) == [2, 3], or(size(f) >= 3, g/2,))", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_NO_EXPRESSION, e.m_se.m_enumErrorType);
            }
            
            try {
                datum = exprEvaluator.evaluateExpression("and(size(f) >=3, [1,2,3]*[[4,5,6],[7,8,9]])", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_ARRAY_DIM_DOES_NOT_MATCH, e.m_se.m_enumErrorType);
            }

            datum = exprEvaluator.evaluateExpression("and(size([[4,5,6],[7,8,9]]) == [2, 3], or(size(f) >= 3, g/2))", new CurPos());
            assertEquals(true, DCHelper.lightCvtOrRetDCMFPBool(datum).getDataValue().booleanValue());

            try {
                datum = exprEvaluator.evaluateExpression("or(g/2)", new CurPos());
                assertEquals(false, true);  // will not be here.
            } catch (ErrProcessor.JFCALCExpErrException e) {
                assertEquals(ErrProcessor.ERRORTYPES.ERROR_CANNOT_CHANGE_DATATYPE_FROM_STRING, e.m_se.m_enumErrorType);
            }
            
        } catch (Exception e) {
            assertEquals(false, true);  // this means something wrong.
        }
    }
}
