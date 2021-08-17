/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.Oomfp;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.ModuleInfo;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.adapter.MFPAdapter;
import org.junit.Ignore;
import java.util.LinkedList;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tony_
 */
public class MFPClassTest {
    
    public MFPClassTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "MFPClassTest");

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
     * Test of same name class and citingspace.
     * @throws java.lang.Exception
     */
    // test case 1.
    @Test
    public void testSameNameCsCls() throws Exception {
        System.out.println("test same name citingspace and class");
        
        String strStartFunc = "test_same_name_cls_cs";
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);

        // if you have two citingspaces with same name, they merge
        long cfunc1Cnt = listAllReferredUnits.stream()
                .filter(r -> r.getFunctionEntry() != null)
                .map(r -> r.getFunctionEntry().getAbsNameWithCS())
                .filter(s -> s.equals("::c::func1"))
                .count();
        assertEquals(1, cfunc1Cnt);

        long cfunc2Cnt = listAllReferredUnits.stream()
                .filter(r -> r.getFunctionEntry() != null)
                .map(r -> r.getFunctionEntry().getAbsNameWithCS())
                .filter(s -> s.equals("::c::func2"))
                .count();
        assertEquals(1, cfunc2Cnt);
        
        // if you have two classes with same name, new one replace the old one
        long aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equals("::b"))
                .count();
        assertEquals(1, aclassCnt);
        
        long bclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equals("::b"))
                .count();
        assertEquals(1, bclassCnt);
        
        MFPClassDefinition bClassDef = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equals("::b"))
                .collect(Collectors.toList()).get(0);
        
        long bfunc1Cnt = bClassDef.mlistFuncMembers.stream().filter(f -> f.getAbsNameWithCS().equals("::b::func1")).count();
        assertEquals(0, bfunc1Cnt);
        long bstaticfunc1Cnt = bClassDef.mlistFuncMembers.stream().filter(f -> f.getAbsNameWithCS().equals("::b::static_func1")).count();
        assertEquals(0, bstaticfunc1Cnt);
        long bfunc2Cnt = bClassDef.mlistFuncMembers.stream().filter(f -> f.getAbsNameWithCS().equals("::b::func2")).count();
        assertEquals(1, bfunc2Cnt);
        long bstaticfunc2Cnt = bClassDef.mlistFuncMembers.stream().filter(f -> f.getAbsNameWithCS().equals("::b::static_func2")).count();
        assertEquals(1, bstaticfunc2Cnt);
    }

    /**
     * Test of self reference
     * @throws java.lang.Exception
     */
    // test case 2.
    @Test
    public void testSelfRef() throws Exception {
        System.out.println("test self reference");
        
        String strStartFunc = "test_self_ref";
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        
        long aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestSelfVariableRefer"))
                .count();
        assertEquals(1, aclassCnt);
        
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestSelfFuncRefer"))
                .count();
        assertEquals(1, aclassCnt);
        
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestSelfStaticFuncRefer"))
                .count();
        assertEquals(1, aclassCnt);

    }

    /**
     * Test of variable loop reference
     * @throws java.lang.Exception
     */
    // test case 3.
    @Test
    public void testVariableLoopRef() throws Exception {
        System.out.println("test variable loop reference");
        
        String strStartFunc = "test_variable_loop_ref";
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        assertEquals(5, listAllReferredUnits.size());
        
        long aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer1"))
                .count();
        assertEquals(1, aclassCnt);
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer2"))
                .count();
        assertEquals(1, aclassCnt);
        long afuncCnt = listAllReferredUnits.stream()
                .filter(r -> r.getFunctionEntry() != null)
                .map(r -> r.getFunctionEntry())
                .filter(f -> f.getAbsNameWithCS().equalsIgnoreCase("::TestLoopRefer2::staticF"))
                .count();
        assertEquals(1, afuncCnt);
    }

    /**
     * Test of function loop reference
     * @throws java.lang.Exception
     */
    // test case 4.
    @Test
    public void testFunctionLoopRef() throws Exception {
        System.out.println("test function loop reference");
        
        String strStartFunc = "test_function_loop_ref";
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        assertEquals(3, listAllReferredUnits.size());
        
        long aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer3"))
                .count();
        assertEquals(1, aclassCnt);
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer4"))
                .count();
        assertEquals(1, aclassCnt);
    }

    /**
     * Test of static function reference
     * @throws java.lang.Exception
     */
    // test case 5.
    @Test
    public void testStaticFunctionRef() throws Exception {
        System.out.println("test static function reference");
        
        String strStartFunc = "test_static_func_ref";
        int nNumOfParams = 0;
        LinkedList<ModuleInfo.ReferenceUnit> listAllReferredUnits
            = ModuleInfo.getReferenceUnits(strStartFunc, nNumOfParams, false, new LinkedList<>(), null);
        assertEquals(4, listAllReferredUnits.size());
        
        long aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer5"))
                .count();
        assertEquals(1, aclassCnt);
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer4"))
                .count();
        assertEquals(1, aclassCnt);
        aclassCnt = listAllReferredUnits.stream()
                .filter(r -> r.getClassDefinition() != null)
                .map(r -> r.getClassDefinition())
                .filter(c -> c.mstrFullNameWithCS.equalsIgnoreCase("::TestLoopRefer3"))
                .count();
        assertEquals(1, aclassCnt);
    }
}

