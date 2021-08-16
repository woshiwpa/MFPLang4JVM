/*
 * MFP project, MFPAdapterTest.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.adapter;

import com.cyzapps.adapter.MFPSProcessor.MFPSExecInfo;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Jfcalc.DCHelper.CurPos;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import java.util.LinkedList;
import com.cyzapps.Jmfp.VariableOperator.Variable;
import com.cyzapps.Jfcalc.ExprEvaluator;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.ErrorProcessor;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tonyc
 */
public class MFPAdapterTest {
    
    public MFPAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "MFPAdapterTest");
        MFPAdapter.getUsrLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER);
        MFPAdapter.loadUsrLib(mfpFileMan);    // load user defined lib.

        MFPAdapter.getSysLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSETS_FOLDER + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSET_SCRIPT_LIB_FOLDER);
        MFPAdapter.loadSysLib(mfpFileMan);    // load pre-defined lib.
        // hide this function because we want to analyse statements on the fly
        //MFPAdapter.analyseStatements(); // analyse the statements (using abstractexpr instead of string).
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        MFPAdapter.clear(CitingSpaceDefinition.CheckMFPSLibMode.CHECK_EVERYTHING);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUndefinedFunctionExOutput() {
        String strScripts = MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER;
        // clear variable name spaces
        LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
        ProgContext progContext = new ProgContext();
        progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;
        ExprEvaluator exprEvaluator = new ExprEvaluator(progContext);
        try {

            /* evaluate the expression */
            CurPos curpos = new CurPos();
            curpos.m_nPos = 0;
            exprEvaluator.evaluateExpression("aaaa()", curpos);
        } catch (JFCALCExpErrException ex) {
            String strActualEx = MFPAdapter.outputException(ex);
            String strExpectedEx = "Function cannot be properly be evaluated!\nIn function aaaa :\n"
                    + "\t" + strScripts + LangFileManager.STRING_PATH_DIVISOR + "exthrow.mfps Line 2 : Invalid expression\n"
                    + "Undefined function!\n";
            assertTrue(strActualEx.equals(strExpectedEx));
        } catch (InterruptedException ex) {
            Logger.getLogger(MFPAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            /* evaluate the expression */
            CurPos curpos = new CurPos();
            curpos.m_nPos = 0;
            exprEvaluator.evaluateExpression("aaaa1()", curpos);
        } catch (JFCALCExpErrException ex) {
            String strActualEx = MFPAdapter.outputException(ex);
            String strExpectedEx = "Function cannot be properly be evaluated!\nIn function aaaa1 :\n"
                    + "\t" + strScripts + LangFileManager.STRING_PATH_DIVISOR + "exthrow.mfps Lines 6-7 : Invalid expression\n"
                    + "Undefined function!\n";
            assertTrue(strActualEx.equals(strExpectedEx));
        } catch (InterruptedException ex) {
            Logger.getLogger(MFPAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testFunctionHelpBlock() {
        assertTrue(MFPAdapter.getFunctionHelp("::aaakkk::aaakkk1", "", new LinkedList<String[]>()).equals("::aaakkk::aaakkk1(0) :\n"));
        assertTrue(MFPAdapter.getFunctionHelp("::aaakkk::aaakkk2", "", new LinkedList<String[]>()).equals("::aaakkk::aaakkk2(0) :\n  this is help block 3\n"));
        assertTrue(MFPAdapter.getFunctionHelp("::aaakkk3", "", new LinkedList<String[]>()).equals("::aaakkk3(0) :\n  this is help block 5\n"));
        assertTrue(MFPAdapter.getFunctionHelp("::aaakkk4", "", new LinkedList<String[]>()).equals("::aaakkk4(0) :\n"));
    }
    
    @Test
    public void testParseScriptParams() {
        String strOutput = "";
        MFPSExecInfo mfpsExecInfo = new MFPSExecInfo();
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],#i,\"5.42#33\",@ + 88, #-7i)");
        String[] strarrayFileParams = {"2 8", "[16.9,-7i]*", "\"\\", "pi/pi", "abc"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],[16.9,-7i]*i,\"5.42#33\",\"\\\"\\\\\" + 88, pi/pi-7i)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],#i,\"5.42#33\",@ + 88, #-7i)");
        strarrayFileParams = new String[]{"2 8", "[16.9,-7i]*", "\"\\"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput == null);
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f ");
        strarrayFileParams = new String[]{"2 8", "[16.9,-7i]*", "\"\\", "pi/pi", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\",\"[16.9,-7i]*\",\"\\\"\\\\\",\"pi/pi\",\"abc\",\"@...\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo(" f ");
        strarrayFileParams = new String[]{"2 8"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f()");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f( )");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@...)");
        strarrayFileParams = new String[]{"17", "e/e"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"17\", \"e/e\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@...)");
        strarrayFileParams = new String[]{"17"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"17\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@...)");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(...)");
        strarrayFileParams = new String[]{"17", "e/e"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"17\", \"e/e\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(...)");
        strarrayFileParams = new String[]{"17"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"17\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(...)");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(#...)");
        strarrayFileParams = new String[]{"17", "e/e"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(17, e/e)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(#...)");
        strarrayFileParams = new String[]{"17"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(17)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(#...)");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f( #...)");
        strarrayFileParams = new String[]{"17", "e/e"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(17, e/e)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f( #... )");
        strarrayFileParams = new String[]{"17"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(17)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f( #... )");
        strarrayFileParams = new String[]{};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f()"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@, #*3, [2,\"\\\"@\\\"\",3], #)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\", [16.9, -7i]*3, [2,\"\\\"@\\\"\",3], \"\\)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(#,@,\"a\")");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(2 8,\"[16.9, -7i]\",\"a\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, @...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42#33\",[16.9, -7i]i, \"\\\"\\\\\", \"pi/pi\", \"abc\", \"@...\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("cs  :: f     ([3,2i,@],\"5.42#33\",#i, @...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("cs  :: f([3,2i,\"2 8\"],\"5.42#33\",[16.9, -7i]i, \"\\\"\\\\\", \"pi/pi\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42\"#33\",#i, @...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42\"[16.9, -7i]33\",#i, \"\\\"\\\\\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42\"\"\\\"#33\\\"\",#i, @...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42\"\"\\\"#33\\\"\",[16.9, -7i]i)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, @...)");
        strarrayFileParams = new String[]{"2 8"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput == null);
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@, #*3, [2,\"\\\\ @...  \", @,3], #,...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\", [16.9, -7i]*3, [2,\"\\\\ @...  \", \"\\\"\\\\\",3], pi/pi, \"abc\", \"@...\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@, #*3, [2,\"\\\\ @...  \", @,3], #,...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi", "abc"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\", [16.9, -7i]*3, [2,\"\\\\ @...  \", \"\\\"\\\\\",3], pi/pi, \"abc\")"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@, #*3, [2,\"\\\\ @...  \", @,3], #,...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\", "pi/pi"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f(\"2 8\", [16.9, -7i]*3, [2,\"\\\\ @...  \", \"\\\"\\\\\",3], pi/pi)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f(@, #*3, [2,\"\\\\ @...  \", @,3], #,...)");
        strarrayFileParams = new String[]{"2 8", "[16.9, -7i]", "\"\\"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput == null);
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, #...)");
        strarrayFileParams = new String[]{"2 8", "99", "pi/pi+i", "[5,\"44\",68.44i]", "abc", "@..."};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42#33\",99i, pi/pi+i, [5,\"44\",68.44i], abc, @...)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, #...)");
        strarrayFileParams = new String[]{"2 8", "99", "pi/pi+i"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42#33\",99i, pi/pi+i)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, #...)");
        strarrayFileParams = new String[]{"2 8", "99"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput.equals("f([3,2i,\"2 8\"],\"5.42#33\",99i)"));
        
        mfpsExecInfo = MFPSProcessor.getExecInfo("f([3,2i,@],\"5.42#33\",#i, #...)");
        strarrayFileParams = new String[]{"2 8"};
        strOutput = MFPAdapter.parseScriptParams(strarrayFileParams, mfpsExecInfo);
        assertTrue(strOutput == null);
    }
    
    @Test
    public void testLoadLibCodeString_Good() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(result);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            Logger.getLogger(MFPAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_NoEndClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_CLASS);
        }
    }
    
    @Test
    public void testLoadLibCodeString_NoEndfEndClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ExtraEndf() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ExtraEndClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endclass",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ExtraEndfEndClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endclass",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ExtraEndfEndClass2() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "endf",
            "public function __init__(self, a)",
            "endclass",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);   // it doesn't import any class
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ReorderedFunctionEndf() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "endf",
            "public function __init__(self, a)",
            "endclass",
            "self.a = a*2",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_InterlacedFunctionClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "endclass",
            "self.a = a*2",
            "endf",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_ReorderedClassEndclass() {
        String[] codes = new String[] {
            "endclass",
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "endf",
            "self.a = a*2",
            "endclass",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }

    @Test
    public void testLoadLibCodeString_InterlacedFunctionClass2() {
        String[] codes = new String[] {
            "function jjj(a)",
            "return a*2",
            "class abcd : object",
            "variable self a =7",
            "endf",
            "endclass",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);   // it doesn't import any class
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }

    @Test
    public void testLoadLibCodeString_NestedFunction() {
        String[] codes = new String[] {
            "function jjj(a)",
            "return a*2",
            "function jjjj(b)",
            "print(b)",
            "endf",
            "endf",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);   // although it is true, it doesn't import the inside function.
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }

    @Test
    public void testLoadLibCodeString_NestedFunction2() {
        String[] codes = new String[] {
            "function jjj(a)",
            "function jjjj(b)",
            "print(b)",
            "endf",
            "return a*2",
            "endf",
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);   // although it is true, it doesn't import the inside function.
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }

    @Test
    public void testLoadLibCodeString_NestedClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "class child",
			"variable self b = 9",
			"public function calc(self)",
			"self.b = 11",
			"return self.b",
			"endf",
			"endclass",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }

    @Test
    public void testLoadLibCodeString_InterlacedNestedClassFunction() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "class child",
			"variable self b = 9",
			"public function calc(self)",
			"self.b = 11",
			"return self.b",
			"endf",
			"endclass",
            "endclass",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);   // it doesn't import anything.
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }

    @Test
    public void testLoadLibCodeString_InterlacedNestedClassFunction2() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "class child",
			"variable self b = 9",
			"public function calc(self)",
			"self.b = 11",
			"return self.b",
			"endf",
			"endf",
            "endclass",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);   // It actually can import both abcd and abcd child
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }

    @Test
    public void testLoadLibCodeString_ClassInsideFunction() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "class child",
			"variable self b = 9",
            "public function getb(self)",
            "return 2*self.b",
            "endf",
			"endclass",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);   // although it can import both class and function, function including a class cannot run properly.
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_InterlacedFunctionClassFollowedByFunction() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "endclass",
            "self.a = a*2",
            "endf",
            "function aaa()",
            "endf"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_InterlacedFunctionClassFollowedByClass() {
        String[] codes = new String[] {
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "endclass",
            "self.a = a*2",
            "endf",
            "class bbb",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_InterlacedFunctionClass2FollowedByClass() {
        String[] codes = new String[] {
            "function __init__(self, a)",
            "class abcd : object",
            "variable self a =7",
            "endf",
            "self.a = a*2",
            "endclass",
            "class bbb",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_InterlacedFunctionClass2FollowedByFunction() {
        String[] codes = new String[] {
            "function __init__(self, a)",
            "class abcd : object",
            "variable self a =7",
            "endf",
            "self.a = a*2",
            "endclass",
            "function aaa()",
            "endf"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            //assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.INCOMPLETE_FUNCTION);
            assertTrue(false);
        }
    }
    
    @Test
    public void testLoadLibCodeString_HelpBlock() {
        String[] codes = new String[] {
            "help",
            "class abcd : object",
            "variable self a =7",
            "endf",
            "self.a = a*2",
            "endclass",
            "function aaa()",
            "endh"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_BatchCode() {
        String[] codes = new String[] {
            "variable a =7",
            "a = a*2"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_PureHelpLines() {
        String[] codes = new String[] {
            "//variable a =7",
            "//a = a*2"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(false);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.NO_VALID_DEFINITION);
        }
    }
    
    @Test
    public void testLoadLibCodeString_HelpLinesFollowedByNormalCodes() {
        String[] codes = new String[] {
            "//variable a =7",
            "//a = a*2",
            "class abcd : object",
            "variable self a =7",
            "public function __init__(self, a)",
            "self.a = a*2",
            "endf",
            "endclass"
        };
        String path = "";
        String[] pathSpaceArray = new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH};
        try {
            boolean result = MFPAdapter.loadLibCodeString(codes, path, pathSpaceArray);
            assertTrue(true);
        } catch (ErrorProcessor.JMFPCompErrException ex) {
            assertTrue(false);
        }
    }
}
