/*
 * MFP project, CitingSpaceDefinitionTest.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.Jmfp;

import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import java.util.LinkedList;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.adapter.MFPAdapter;
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
public class CitingSpaceDefinitionTest {
    
    public CitingSpaceDefinitionTest() {
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

    
    @Test
    public void testGetCSDef1() throws Exception {
        System.out.println("GetCSDef1");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCSDef2() throws Exception {
        System.out.println("GetCSDef2");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("*", MFPAdapter.getAllCitingSpaces(null));
        String strResult = "";  // for debugging purpose.
        for (CitingSpaceDefinition csd : result) {
            strResult += csd.mstrFullNameWithCS + "\n";
        }
        assertEquals(53, result.size());
    }
    
    @Test
    public void testGetCSDef3() throws Exception {
        System.out.println("GetCSDef3");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("_*", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetCSDef4() throws Exception {
        System.out.println("GetCSDef4");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("__abcde__", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef5() throws Exception {
        System.out.println("GetCSDef5");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("__abcde", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCSDef6() throws Exception {
        System.out.println("GetCSDef6");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("::__abcde__::", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCSDef7() throws Exception {
        System.out.println("GetCSDef7");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("::__abcde__::*", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef8() throws Exception {
        System.out.println("GetCSDef8");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("::Metta::dddK::*", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetCSDef9() throws Exception {
        System.out.println("GetCSDef9");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional = new String[] {"", "__cs_in_func__"};
        listAdditionalCS.add(strarrayAdditional);
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("c*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(12, result.size());
    }
    
    @Test
    public void testGetCSDef10() throws Exception {
        System.out.println("GetCSDef10");
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("::metta::dddk::*", MFPAdapter.getAllCitingSpaces(null));
        assertEquals(3, result.size());
    }
    
    @Test
    public void testGetCSDef11() throws Exception {
        System.out.println("GetCSDef11");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional;
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        strarrayAdditional = new String[] {"", "nothing_inside"};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("nothing_inside*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef12() throws Exception {
        System.out.println("GetCSDef12");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional;
        strarrayAdditional = new String[] {"", "nothing_inside"};
        listAdditionalCS.add(strarrayAdditional);
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("nothing_ins*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef13() throws Exception {
        System.out.println("GetCSDef13");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional;
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("nothing_ins*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef14() throws Exception {
        System.out.println("GetCSDef14");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional;
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("nothing_inside*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testGetCSDef15() throws Exception {
        System.out.println("GetCSDef15");
        LinkedList<String[]> listAdditionalCS = new LinkedList<String[]>();
        String[] strarrayAdditional;
        strarrayAdditional = new String[] {""};
        listAdditionalCS.add(strarrayAdditional);
        LinkedList<CitingSpaceDefinition> result = CitingSpaceDefinition.lookupCSDef("nothing_inside::*", MFPAdapter.getAllCitingSpaces(listAdditionalCS));
        assertEquals(0, result.size());
    }
}
