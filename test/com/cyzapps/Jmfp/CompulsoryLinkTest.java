/*
 * MFP project, CompulsoryLinkTest.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.Jmfp;

import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import java.util.LinkedList;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.adapter.MFPAdapter;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tony
 */
public class CompulsoryLinkTest {
    
    public CompulsoryLinkTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "CompulsoryLinkTest");

        MFPAdapter.getSysLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSETS_FOLDER + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_ASSET_SCRIPT_LIB_FOLDER);
        MFPAdapter.loadSysLib(mfpFileMan);    // load pre-defined lib. But actually there is no predefined lib file.
        
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
    
    @Test
    public void testinCSA_CSB() throws Exception {
        System.out.println("inCSA_CSB");
        String strStartFunc = "::cs_a::cs_b::incsa_csb";
        int nNumOfParams = 3;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(3, listAllReferredFuncs.size());
        assertEquals(1, listAllReferredPaths.size());
    }
    
    @Test
    public void testinCSA_CSB_CSC() throws Exception {
        System.out.println("inCSA_CSB_CSC");
        String strStartFunc = "::cs_a::cs_b::cs_c::incsa_csb_csc";
        int nNumOfParams = -1;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(4, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testinCSD() throws Exception {
        System.out.println("inCSD");
        String strStartFunc = "::cs_d::incsd";
        int nNumOfParams = -1;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(3, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testinCSD_CSG_CSH_CSI() throws Exception {
        System.out.println("inCSD_CSG_CSH_CSI");
        String strStartFunc = "::cs_d::cs_g::cs_h::cs_i::incsd_csg_csh_csi";
        int nNumOfParams = 4;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(6, listAllReferredFuncs.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testinTopCS3() throws Exception {
        System.out.println("inTopCS3");
        String strStartFunc = "intopcs3";
        int nNumOfParams = 2;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(57, listAllReferredFuncs.size());
        assertEquals(7, listAllReferredPaths.size());
    }
    
    @Test
    public void testinTopCS4() throws Exception {
        System.out.println("inTopCS4");
        String strStartFunc = "intopcs4";
        int nNumOfParams = 2;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredFuncs
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, true, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(1, listAllReferredFuncs.size());
        assertEquals(1, listAllReferredPaths.size());
    }
    
    @Test
    public void testCompileASingleFile() throws Exception {
        System.out.println("CompileASingleFile");
        String strStartFunc = "incompleteCls::more::B_new_class::inner_class".toLowerCase(Locale.US);
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(1, listAllReferredUnits.size());
        assertEquals(1, listAllReferredPaths.size());
    }
    
    @Test
    public void testGetAllReferredUnits() throws Exception {
        System.out.println("GetAllReferredUnits");
        String strStartFunc = "*";
        int nNumOfParams = -1;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(57, listAllReferredUnits.size());
        assertEquals(7, listAllReferredPaths.size());
    }
    
    @Test
    public void testPrivateMemberFuncAndInnerClass() throws Exception {
        System.out.println("PrivateMemberFuncAndInnerClass");
        String strStartFunc = "aaaa::bbb::def::ClassWithNoPublicFunc".toLowerCase(Locale.US);
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        /* By calling printReferredUnits(listAllReferredUnits),
        we get 11 referred units are
        ::aaa::grandchildclass::__init__(2)
        ::aaaa::bbb::def::a_new_class::inner_class::classstatic(2)
        ::aaaa::cccc::staticfunc(2)
        ::ee::childclass::setb(2)
        ::aaa::grandchildclass
        ::aaaa::bbb::def::a_new_class
        ::aaaa::bbb::def::a_new_class::inner_class
        ::aaaa::bbb::def::classwithnopublicfunc
        ::aaaa::testclass
        ::aaac::bbb::def::classbold
        ::ee::childclass
        */
        assertEquals(11, listAllReferredUnits.size());
        /* By calling printAllStrings(listAllReferredPaths), we get the files are:
        D:\Development\NetBeansProjs\JCmdLine\run_test\CompulsoryLinkTest\scripts\testclass.mfps
        D:\Development\NetBeansProjs\JCmdLine\run_test\CompulsoryLinkTest\scripts\testclass2.mfps
        D:\Development\NetBeansProjs\JCmdLine\run_test\CompulsoryLinkTest\scripts\testclass3_pureclass.mfps
        D:\Development\NetBeansProjs\JCmdLine\run_test\CompulsoryLinkTest\scripts\testclass5_purebareclass.mfps
        */
        assertEquals(4, listAllReferredPaths.size());
    }
    
    public static void printReferredUnits(LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits) {
        for (ModuleInfo.ReferenceUnit unit : listAllReferredUnits) {
            if (null != unit.getFunctionEntry()) {
                System.out.println(unit.getFunctionEntry().getAbsNameWithCS()
                        + "(" + unit.getFunctionEntry().getMinNumParam()
                        + ((unit.getFunctionEntry().getMinNumParam() == unit.getFunctionEntry().getMaxNumParam()) ? "":("->" + unit.getFunctionEntry().getMaxNumParam()))
                        + ")");
            } else if (null != unit.getClassDefinition()) {
                System.out.println(unit.getClassDefinition().selfTypeDef.toString());
            }
        }
    }
    
    public static void printAllStrings(LinkedList<String> listStrs) {
        for (String str: listStrs) {
            System.out.println(str);
        }
    }
}
