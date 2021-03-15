/*
 * MFP project, FunctionEntryTest.java : Designed and developed by Tony Cui in 2021
 */
package com.cyzapps.Jmfp;

import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import java.util.Locale;
import com.cyzapps.Jfcalc.DCHelper.DATATYPES;
import com.cyzapps.Jfcalc.MFPNumeric;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassSingleNum;
import com.cyzapps.Jfcalc.DataClassString;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.ModuleInfo.ReferenceUnit;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.adapter.MFPAdapter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.junit.Ignore;
import java.util.LinkedList;
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
public class FunctionEntryTest {
    
    public FunctionEntryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "FunctionEntryTest");

        MFPAdapter.getSysLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSETS_FOLDER + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSET_SCRIPT_LIB_FOLDER);
        MFPAdapter.loadSysLib(mfpFileMan);    // load pre-defined lib.
        
        MFPAdapter.getUsrLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER);
        MFPAdapter.loadUsrLib(mfpFileMan);    // load user defined lib.
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

    /**
     * Test of getReferredFuncEntries1 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    // test case 1.
    @Test
    public void testGetReferredFuncEntries1() throws Exception {
        System.out.println("getReferredFuncEntries1");
        
        String strStartFunc = "test_compile";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, true, new LinkedList<>(), null);

        // TODO review the generated test code and remove the default call to fail.
        assertEquals(11, listAllReferredFuncs.size());
        int fd = IOLib.fOpen(MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + "testGetReferredFuncDefs1.txt", "r");
        
        // because it is in unit test, do not do any exception check here
        for (int idx = 0; idx < listAllReferredFuncs.size(); idx ++) {
            String[] strarrayFuncParts = IOLib.fReadLine(fd).split(",");
            String strActualModuleName = listAllReferredFuncs.get(idx).getFunctionEntry().getAbsNameWithCS();
            String strExpectedModuleName = strarrayFuncParts[0];
            assertTrue("actual function name is " + strActualModuleName + ", while expected function name is " + strExpectedModuleName,
                    strActualModuleName.equalsIgnoreCase(strExpectedModuleName));
            String strMinParamCnt = String.valueOf(listAllReferredFuncs.get(idx).getFunctionEntry().getMinNumParam());
            String strMaxParamCnt = String.valueOf(listAllReferredFuncs.get(idx).getFunctionEntry().getMaxNumParam());
            String strExpectedParamMin = strarrayFuncParts[1];
            String strExpectedParamMax = strarrayFuncParts[2];
            assertTrue("function " + strActualModuleName + " min param cnt is " + strMinParamCnt + " max param cnt is " + strMaxParamCnt
                    + ", while expected param min is " + strExpectedParamMin + " expected param max is " + strExpectedParamMax,
                    strMinParamCnt.equals(strExpectedParamMin) && strMaxParamCnt.equals(strExpectedParamMax) );
        }
        IOLib.fClose(fd);
    }

    /**
     * Test of getReferredFuncEntries2 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    // test case2.
    @Test
    public void testGetReferredFuncEntries2() throws Exception {
        System.out.println("getReferredFuncEntries2");
        
        String strStartFunc = "shu_mian_f1203";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, true, new LinkedList<>(), null);
        int fd = IOLib.fOpen(MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + "all_functions_dump.txt", "w");
        for (int idx = 0; idx < listAllReferredFuncs.size(); idx ++) {
            LinkedList<DataClass> listData = new LinkedList<>();
            DataClass datum0 = new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(listAllReferredFuncs.get(idx).getFunctionEntry().getMinNumParam()));
            listData.addFirst(datum0);
            if (listAllReferredFuncs.get(idx).getFunctionEntry().isIncludeOptParam()) {
                DataClass datum1 = new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(listAllReferredFuncs.get(idx).getFunctionEntry().getMaxNumParam()));
                listData.addFirst(datum1);
                IOLib.fPrintf(fd, listAllReferredFuncs.get(idx).getFunctionEntry().getAbsNameWithCS() + ",%d,%d\n",  listData);
            } else {
                IOLib.fPrintf(fd, listAllReferredFuncs.get(idx).getFunctionEntry().getAbsNameWithCS() + ",%d\n",  listData);
            }
        }
        IOLib.fClose(fd);
        assertEquals(368, listAllReferredFuncs.size()); // was 378, however, functions without endf are ignored.
    }

    /**
     * Test of getReferredFuncEntries3 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    // test case2.
    @Test
    public void testGetReferredFuncEntries3() throws Exception {
        System.out.println("getReferredFuncEntries3");
        
        String strStartFunc = "shu_mian_f1203";
        int nNumOfParams = 1;   // no such a function.
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        assertEquals(0, listAllReferredFuncs.size());
    }

    /**
     * Test of getReferredFuncEntries4 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    // test case4.
    @Test
    public void testGetReferredFuncEntries4() throws Exception {
        System.out.println("getReferredFuncEntries4");
        
        String strStartFunc = "shu_mian_f1203";
        int nNumOfParams = 0;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        assertEquals(368, listAllReferredFuncs.size()); // was 378, however, functions without endf are ignored.
    }

    /**
     * Test of getReferredFuncEntries method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testGetReferredFuncEntries5() throws Exception {
        System.out.println("getReferredFuncEntries5");
        String strStartFunc = "";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList expResult = null;
        LinkedList result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of testGetReferredFuncEntriesPaths1 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths1() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths1");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink1";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(9, listAllReferredFuncs.size());
        assertEquals(3, listAllReferredPaths.size());
    }
    
    /**
     * Test of testGetReferredFuncEntriesPaths2 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths2() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths2");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink2";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(8, listAllReferredFuncs.size());
        assertEquals(3, listAllReferredPaths.size());
    }

    /**
     * Test of testGetReferredFuncEntriesPaths3 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths3() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths3");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink3";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(12, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }

    /**
     * Test of testGetReferredFuncEntriesPaths4 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths4() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths4");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink4";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(1, listAllReferredFuncs.size());
        assertEquals(1, listAllReferredPaths.size());
    }

    /**
     * Test of testGetReferredFuncEntriesPaths5 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths5() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths5");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink5";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(2, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }

    /**
     * Test of testGetReferredFuncEntriesPaths6 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths6() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths6");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink6";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(1, listAllReferredFuncs.size());
        assertEquals(1, listAllReferredPaths.size());
    }

    /**
     * Test of testGetReferredFuncEntriesPaths7 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncEntriesPaths7() throws Exception {
        System.out.println("testGetReferredFuncEntriesPaths7");
        
        String strStartFunc = "::metta::dddk::testcompulsorylink7";
        int nNumOfParams = -1;
        LinkedList<ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, true, null);
        assertEquals(3, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }

    /**
     * Test of getReferredFuncFilePaths1 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncFilePaths1() throws Exception {
        System.out.println("getReferredFuncFilePaths1");
        String strStartFunc = "shu_mian_f1203";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList result = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, bOptionalParam, null);
        // depends on which folder, result size can be 88 or 94 because some functions are duplicate.
        boolean resultSizeRight = false;
        if (result.size() == 88 || result.size() == 89) { // function declaration without endf is ignored.
            resultSizeRight = true;
        }
        //dumpAllPaths(result, "f:\\tmp\\FEresults.txt");
        assertEquals(true, resultSizeRight);
    }

    /**
     * Test of getReferredFuncFilePaths2 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncFilePaths2() throws Exception {
        System.out.println("getReferredFuncFilePaths2");
        String strStartFunc = "bing_tiao_hou311_320";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<String> result = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, bOptionalParam, null);
        assertEquals(5, result.size());
        String strScriptFolder = MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER;
        String path0 = strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "梳棉" + LangFileManager.STRING_PATH_DIVISOR + "FA1203.mfps";
        String path1 = strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "细纱" + LangFileManager.STRING_PATH_DIVISOR + "细纱总牵伸" + LangFileManager.STRING_PATH_DIVISOR + "513系数排序.mfps";
        String path2 = strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "细纱" + LangFileManager.STRING_PATH_DIVISOR + "细纱总牵伸" + LangFileManager.STRING_PATH_DIVISOR + "513系数排序(预设).mfps";
        String path3 = strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "细纱" + LangFileManager.STRING_PATH_DIVISOR + "细纱513牙齿表.mfps";
        String path4 = strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "自定义内置函数" + LangFileManager.STRING_PATH_DIVISOR + "多维排序.mfps";

        boolean foundPath0 = false;
        for (String s : result) {
            if (s.equalsIgnoreCase(path0)) {
                foundPath0 = true;
                break;
            }
        }
        assertTrue(foundPath0);

        boolean foundPath1 = false;
        for (String s : result) {
            if (s.equalsIgnoreCase(path1)) {
                foundPath1 = true;
                break;
            }
        }
        assertTrue(foundPath1);

        boolean foundPath2 = false;
        for (String s : result) {
            if (s.equalsIgnoreCase(path2)) {
                foundPath2 = true;
                break;
            }
        }
        assertTrue(foundPath2);

        boolean foundPath3 = false;
        for (String s : result) {
            if (s.equalsIgnoreCase(path3)) {
                foundPath3 = true;
                break;
            }
        }
        assertTrue(foundPath3);

        boolean foundPath4 = false;
        for (String s : result) {
            if (s.equalsIgnoreCase(path4)) {
                foundPath4 = true;
                break;
            }
        }
        assertTrue(foundPath4);
    }

    /**
     * Test of getReferredFuncFilePaths3 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncFilePaths3() throws Exception {
        System.out.println("getReferredFuncFilePaths3");
        String strStartFunc = "bing_tiao_hou322";
        int nNumOfParams = -1;
        boolean bOptionalParam = false;
        LinkedList result = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, bOptionalParam, null);
        assertEquals(19, result.size());
    }

    /**
     * Test of getReferredFuncFilePaths4 method, of class FunctionEntry.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetReferredFuncFilePaths4() throws Exception {
        System.out.println("getReferredFuncFilePaths4");
        String strStartFunc = "xiao_shu_dian";
        int nNumOfParams = -1;
        boolean bOptionalParam = true;  // optional parameter at this moment is useless in getReferredFuncFilePaths.
        LinkedList<String> result = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, bOptionalParam, null);
        assertEquals(5, result.size());
        String strScriptFolder = MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER;
        assertTrue(result.get(4).equalsIgnoreCase(strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "biao.mfps"));
        assertTrue(result.get(2).equalsIgnoreCase(strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "自定义内置函数" + LangFileManager.STRING_PATH_DIVISOR + "多维排序.mfps"));
        assertTrue(result.get(1).equalsIgnoreCase(strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "细纱" + LangFileManager.STRING_PATH_DIVISOR + "细纱总牵伸" + LangFileManager.STRING_PATH_DIVISOR + "513系数排序.mfps"));
        assertTrue(result.get(3).equalsIgnoreCase(strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "细纱" + LangFileManager.STRING_PATH_DIVISOR + "细纱513牙齿表.mfps"));
        assertTrue(result.get(0).equalsIgnoreCase(strScriptFolder + LangFileManager.STRING_PATH_DIVISOR + "工艺计算" + LangFileManager.STRING_PATH_DIVISOR + "梳棉" + LangFileManager.STRING_PATH_DIVISOR + "FA1203.mfps"));
    }
    
    @Test
    public void testInFuncCSRefAnalyzer1() throws Exception {
        System.out.println("InFuncCSRefAnalyzer1");
        String strStartFunc = "dfghj";
        int nNumOfParams = 2;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(7, result.size());
    }
    
    @Test
    public void testInFuncCSRefAnalyzer2() throws Exception {
        System.out.println("InFuncCSRefAnalyzer2");
        String strStartFunc = "testhelpblock";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(1, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer3() throws Exception {
        System.out.println("InFuncCSRefAnalyzer3");
        String strStartFunc = "testhelpblockmain";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(2, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer4() throws Exception {
        System.out.println("InFuncCSRefAnalyzer4");
        String strStartFunc = "testexceptionthrowmain";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(2, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer5() throws Exception {
        System.out.println("InFuncCSRefAnalyzer5");
        String strStartFunc = "testexceptionthrow";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(1, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer6() throws Exception {
        System.out.println("InFuncCSRefAnalyzer6");
        String strStartFunc = "testincompletemain";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(1, result.size()); // was 6, but as the blocks are incomplete while function is complete, change to 1.
    }

    @Test
    public void testInFuncCSRefAnalyzer7() throws Exception {
        System.out.println("InFuncCSRefAnalyzer7");
        String strStartFunc = "testForWhileTryRef".toLowerCase(Locale.US);
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(7, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer8() throws Exception {
        System.out.println("InFuncCSRefAnalyzer8");
        String strStartFunc = "testSelectCaseRef".toLowerCase(Locale.US);
        int nNumOfParams = 1;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(13, result.size());
    }

    @Test
    public void testInFuncCSRefAnalyzer9() throws Exception {
        System.out.println("InFuncCSRefAnalyzer9");
        String strStartFunc = "testincompleteif";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(5, result.size()); // was 9, but as the blocks are incomplete while function is complete, change to 5.
    }

    @Test
    public void testInFuncCSRefAnalyzer10() throws Exception {
        System.out.println("InFuncCSRefAnalyzer10");
        String strStartFunc = "testincompleteifmain";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(6, result.size());    // was 10, but as the blocks are incomplete while function is complete, change to 6.
    }

    @Test
    public void testInFuncCSRefAnalyzer11() throws Exception {
        System.out.println("InFuncCSRefAnalyzer11");
        String strStartFunc = "testincompletefunc";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(0, result.size()); // was 5, but as the function is incomplete, should be zero.
    }
    
    @Test
    public void testInFuncCSRefAnalyzer12() throws Exception {
        System.out.println("InFuncCSRefAnalyzer12");
        String strStartFunc = "testforcatch";
        int nNumOfParams = 0;
        boolean bOptionalParam = false;
        LinkedList<ReferenceUnit> result = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, bOptionalParam, new LinkedList<>(), null);
        assertEquals(4, result.size());
    }
    
    public void dumpAllFunctionEntries(LinkedList<FunctionEntry> result, String fileName) throws ErrProcessor.JFCALCExpErrException {
        int fd = IOLib.fOpen(fileName, "w+");
        for(FunctionEntry fe: result) {
            LinkedList<DataClass> outputParams = new LinkedList<DataClass>();
            outputParams.addFirst(new DataClassString(fe.getAbsNameWithCS()));
            outputParams.addFirst(new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(fe.getMinNumParam())));
            outputParams.addFirst(new DataClassSingleNum(DATATYPES.DATUM_MFPINT, new MFPNumeric(fe.getMaxNumParam())));
            IOLib.fPrintf(fd, "%s\t%d\t%d\n", outputParams);
        }
        IOLib.fClose(fd);
    }
    
    public void dumpAllPaths(LinkedList<String> result, String fileName) throws ErrProcessor.JFCALCExpErrException, UnsupportedEncodingException, FileNotFoundException, IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        try {
            for(String path: result) {
                out.write(path + "\n");
            }
        } finally {
            out.close();
        }
    }
}
