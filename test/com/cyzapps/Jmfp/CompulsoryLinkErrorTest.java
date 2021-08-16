/*
 * MFP project, CompulsoryLinkErrorTest.java : Designed and developed by Tony Cui in 2021
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
public class CompulsoryLinkErrorTest {
    
    public CompulsoryLinkErrorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "CompulsoryLinkErrorTest");

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
    public void testInvalidName() throws Exception {
        // if there is an invalid name inside a member function. Reading referred units from the function
        // will fail. But the class itself is still a valid referred units. Other member functions are not
        // affected.
        System.out.println("InvalidName");
        String strStartFunc = "aaaa::bbb::def::ClassWithNoPublicFunc2".toLowerCase(Locale.US);
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(2, listAllReferredUnits.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testGetAllReferredUnits() throws Exception {
        System.out.println("GetAllReferredUnits");
        String strStartFunc = "*";
        int nNumOfParams = -1;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        /*
        ::testmembervarcallparamserver(0)
        ::testmembervarcallparamclient(0)
        ::aaaa::bbb::def::a_new_class::memberfunc(3)
        ::aaaa::bbb::def::a_new_class::inner_class::__init__(2)
        ::aaaa::bbb::def::a_new_class::inner_class::classstatic(2)
        ::aaaa::bbb::def::a_new_class::inner_class::getprivatea(1)
        ::aaaa::bbb::def::a_new_class::inner_class::setprivatea(2)
        ::aaaa::bbb::def::classwithnopublicfunc::innerclassnopublicfunc::__init__(2)
        ::aaaa::bbb::def::classwithnopublicfunc2::mymemberfunc(4)
        ::aaaa::bbb::def::classwithnopublicfunc2::mymemberfunc2(4)
        ::aaaa::bbb::def::classwithnopublicfunc2::innerclassnopublicfunc::__init__(2)
        ::aaaa::cccc::staticfunc(2)
        ::incompletecls::more::a_new_class::inner_class::newsqr(2)
        ::incompletecls::more::b_new_class::inner_class::newsqr(2)
        ::aaab::bbb::def::classwithpublicfunc::publicmemberfunc(4)
        ::aaaa::bbb::def::a_new_class
        ::aaaa::bbb::def::classwithnopublicfunc2
        ::aaac::bbb::def::classbold
        ::aaaa::bbb::def::classwithnopublicfunc
        ::aaaa::bbb::def::a_new_class::inner_class
        ::aaaa::bbb::def::classwithnopublicfunc::innerclassnopublicfunc
        ::aaab::bbb::def::classwithpublicfunc
        ::aaaa::bbb::def::classwithnopublicfunc2::innerclassnopublicfunc
        ::incompletecls::more::b_new_class
        ::incompletecls::more::a_new_class
        ::incompletecls::more::a_new_class::inner_class
        ::incompletecls::more::b_new_class::inner_class
        */
        //printReferredUnits(listAllReferredUnits);
        assertEquals(27, listAllReferredUnits.size());
        assertEquals(4, listAllReferredPaths.size());
    }
    
    @Test
    public void testNoParentDefined() throws Exception {
        System.out.println("NoParentDefined");
        String strStartFunc = "aaab::bbb::def::ClassWithPublicFunc".toLowerCase(Locale.US);
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(2, listAllReferredUnits.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testMemberFunctionFine() throws Exception {
        System.out.println("MemberFunctionFine");
        String strStartFunc = "aaab::bbb::def::ClassWithPublicFunc::publicMemberFunc".toLowerCase(Locale.US);
        int nNumOfParams = 4;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(2, listAllReferredUnits.size());
        assertEquals(2, listAllReferredPaths.size());
    }
    
    @Test
    public void testMemberFunctionInvalidName() throws Exception {
        System.out.println("MemberFunctionInvalidName");
        String strStartFunc = "aaab::bbb::def::ClassWithPublicFunc2::myMemberFunc".toLowerCase(Locale.US);
        int nNumOfParams = 4;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(0, listAllReferredUnits.size());
        assertEquals(0, listAllReferredPaths.size());
    }
    
    @Test
    public void testPrivateFunctionEntry() throws Exception {
        System.out.println("PrivateFunctionEntry");
        String strStartFunc = "aaab::bbb::def::ClassWithPublicFunc2::innerClassNoPublicFunc::myMemberFunc".toLowerCase(Locale.US);
        int nNumOfParams = 2;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        LinkedList<String> listAllReferredPaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strStartFunc, nNumOfParams, false, null);
        assertEquals(0, listAllReferredUnits.size());
        assertEquals(0, listAllReferredPaths.size());
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
