/*
 * MFP project, LangFileManagerTest.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.OSAdapter;

import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.AnnoType_build_asset;
import com.cyzapps.Jmfp.CompileAdditionalInfo;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.adapter.MFPAdapter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LangFileManagerTest {
    public static MFP4JavaFileMan mfpFileMan = new MFP4JavaFileMan();
    public static final String FILE_PATH_SEPERATOR = System.getProperty("file.separator");
    public static final Boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
    public LangFileManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        MFP4JavaFileMan.msstrAppFolder = IOLib.getCanonicalPath(".." + LangFileManager.STRING_PATH_DIVISOR + "run_test" + LangFileManager.STRING_PATH_DIVISOR + "MFPFileManTest");
        // next step : load scripts and folder and additional scripts folders.
        MFPAdapter.getUsrLibFiles(mfpFileMan, MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER);
        // madditionalUsrLibs should only include canonical path
        MFP4JavaFileMan.madditionalUsrLibs.add(IOLib.getCanonicalPath(MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + "manual_scripts"));
        MFP4JavaFileMan.madditionalUsrLibs.add(IOLib.getCanonicalPath(MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + "parallel" + LangFileManager.STRING_PATH_DIVISOR + "examples.mfps"));
        MFP4JavaFileMan.madditionalUsrLibs.add(IOLib.getCanonicalPath(MFP4JavaFileMan.msstrAppFolder + LangFileManager.STRING_PATH_DIVISOR + "math libs" + LangFileManager.STRING_PATH_DIVISOR + "Examples.mfps"));
        for (String strPath : MFP4JavaFileMan.madditionalUsrLibs) {
            MFPAdapter.getUsrLibFiles(mfpFileMan, strPath);
        }
        MFPAdapter.loadUsrLib(mfpFileMan);    // load all user defined libs.
    }
    
    @AfterClass
    public static void tearDownClass() {
        MFPAdapter.clear(CitingSpaceDefinition.CheckMFPSLibMode.CHECK_EVERYTHING);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMapUsrLib2CompiledLib() throws IOException {
        LinkedList<String> files = new LinkedList<String>();
        // test case 1: path in main script folder
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "scripts" + FILE_PATH_SEPERATOR + "自定义内置函数" + FILE_PATH_SEPERATOR + "开方.mfps");
        // test case 2: path in additional script folder
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "manual_scripts" + FILE_PATH_SEPERATOR + "2d games" + FILE_PATH_SEPERATOR + "hungry_snake" + FILE_PATH_SEPERATOR + "hugry_snake.mfps");
        // test case 3: path == additional script lib file
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "parallel" + FILE_PATH_SEPERATOR + "examples.mfps");
        // test case 4: path not equal to any of above, but it includes main script folder path
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "scripts_b.mfps");
        // test case 5: path not equal to any of above, but it includes additional script lib folder
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "manual_scripts bc" + FILE_PATH_SEPERATOR + "kdw.mfps");
        // test case 6: path not equal to any of above, but it includes additional script lib file
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "parallel" + FILE_PATH_SEPERATOR + "examples.mfpsj" + FILE_PATH_SEPERATOR + "abc.mfps");
        // test case 7: path not equal to any of above
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "two" + FILE_PATH_SEPERATOR + "why is this.mfps");
        // test case 8: big/small case wrong (lib wrong, see math libs)
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "math libs" + FILE_PATH_SEPERATOR + "examples.mfps");
        // test case 9: big/small case wrong (file wrong, not lib wrong)
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "manual_scripts" + FILE_PATH_SEPERATOR + "algorithms" + FILE_PATH_SEPERATOR + "examples.MFPS");
        // test case 10: not exist file
        files.add("K:" + FILE_PATH_SEPERATOR + "non-exist" + FILE_PATH_SEPERATOR + "5234.mfps");
        // test case 11: not exist file, but in a scripts lib, and include . and ..
        files.add(MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "scripts" + FILE_PATH_SEPERATOR + "non-exist" + FILE_PATH_SEPERATOR + "." + FILE_PATH_SEPERATOR + "why" + FILE_PATH_SEPERATOR + ".." + FILE_PATH_SEPERATOR + "5234.mfps");
        Map<String, String> mapUserLibs = mfpFileMan.mapUsrLib2CompiledLib(files);
        String appFolderReplaced = MFP4JavaFileMan.msstrAppFolder.replace(':', '$');
        assertEquals("scripts" + FILE_PATH_SEPERATOR + "自定义内置函数" + FILE_PATH_SEPERATOR + "开方.mfps", mapUserLibs.get(files.get(0)));
        assertEquals("scripts1" + FILE_PATH_SEPERATOR + "2d games" + FILE_PATH_SEPERATOR + "hungry_snake" + FILE_PATH_SEPERATOR + "hugry_snake.mfps", mapUserLibs.get(files.get(1)));
        assertEquals("scripts2" + FILE_PATH_SEPERATOR + "examples.mfps", mapUserLibs.get(files.get(2)));
        assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + appFolderReplaced + FILE_PATH_SEPERATOR + "scripts_b.mfps", mapUserLibs.get(files.get(3)));
        assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + appFolderReplaced + FILE_PATH_SEPERATOR + "manual_scripts bc" + FILE_PATH_SEPERATOR + "kdw.mfps", mapUserLibs.get(files.get(4)));
        assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + appFolderReplaced + FILE_PATH_SEPERATOR + "parallel" + FILE_PATH_SEPERATOR + "examples.mfpsj" + FILE_PATH_SEPERATOR + "abc.mfps", mapUserLibs.get(files.get(5)));
        assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + appFolderReplaced + FILE_PATH_SEPERATOR + "two" + FILE_PATH_SEPERATOR + "why is this.mfps", mapUserLibs.get(files.get(6)));
        if (IS_WINDOWS) {
            // in windows, file name case insensitive
            assertEquals("scripts3" + FILE_PATH_SEPERATOR + "examples.mfps", mapUserLibs.get(files.get(7)));
            assertEquals("scripts1" + FILE_PATH_SEPERATOR + "algorithms" + FILE_PATH_SEPERATOR + "examples.mfps", mapUserLibs.get(files.get(8)));
            // in windows, K: means it is a partition and it is an absolute file path
            assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + "K$" + FILE_PATH_SEPERATOR + "non-exist" + FILE_PATH_SEPERATOR + "5234.mfps", mapUserLibs.get(files.get(9)));
        } else {
            // in Linux, file name case sensitive
            assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + appFolderReplaced + FILE_PATH_SEPERATOR + "math libs" + FILE_PATH_SEPERATOR + "examples.mfps", mapUserLibs.get(files.get(7)));
            assertEquals("scripts1" + FILE_PATH_SEPERATOR + "algorithms" + FILE_PATH_SEPERATOR + "examples.MFPS", mapUserLibs.get(files.get(8)));
            // in Linux, / is not in front of K:, so it is a relative path to current directory.
            assertEquals("scriptsUnknown" + FILE_PATH_SEPERATOR + System.getProperty("user.dir") + FILE_PATH_SEPERATOR + "K$" + FILE_PATH_SEPERATOR + "non-exist" + FILE_PATH_SEPERATOR + "5234.mfps", mapUserLibs.get(files.get(9)));
        }
        assertEquals("scripts" + FILE_PATH_SEPERATOR + "non-exist" + FILE_PATH_SEPERATOR + "5234.mfps", mapUserLibs.get(files.get(10)));
    }

    @Test
    public void testMapAllNonDirMultiLevelChildResources() throws IOException {
        LinkedList<LangFileManager.SourceDestPathMapInfo> srcDestPathMapList = new LinkedList<LangFileManager.SourceDestPathMapInfo>();
        CompileAdditionalInfo.AssetCopyCmd acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = MFP4JavaFileMan.msstrAppFolder + FILE_PATH_SEPERATOR + "manual_scripts" + FILE_PATH_SEPERATOR + "2d games" + FILE_PATH_SEPERATOR + "gemgem" + FILE_PATH_SEPERATOR + "gem1.png";
        acc.mnSrcZipType = 0;
        acc.mstrSrcZipPath = "";
        acc.mstrSrcZipEntry = "";
        acc.mstrDestPath = "F:\\manual_scripts\\2d games" + FILE_PATH_SEPERATOR + "gemgem" + FILE_PATH_SEPERATOR + "gem1.png";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.getFirst().getDestPath(), "F:\\manual_scripts\\2d games" + FILE_PATH_SEPERATOR + "gemgem" + FILE_PATH_SEPERATOR + "gem1.png");
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts/2d games/gemgem/gem1.png";
        acc.mnSrcZipType = 0;
            acc.mstrSrcZipPath = "";
        acc.mstrSrcZipEntry = "";
        acc.mstrDestPath = "F:/manual_scripts/2d games/gemgem/gem1.png";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        if (IS_WINDOWS) {
            assertEquals(srcDestPathMapList.size(), 1);
        } else {
            assertEquals(srcDestPathMapList.size(), 2);
        }
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts/2d games/";
        acc.mnSrcZipType = 0;
        acc.mstrSrcZipPath = "";
        acc.mstrSrcZipEntry = "";
        acc.mstrDestPath = "F:/manual_scripts/2d games";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        if (IS_WINDOWS) {
            assertEquals(srcDestPathMapList.size(), 48);
        } else {
            assertEquals(srcDestPathMapList.size(), 49);
        }
        int startWithFSlash = 0, startWithBSlash = 0;
        for (LangFileManager.SourceDestPathMapInfo srcDestPathMap : srcDestPathMapList) {
            if (srcDestPathMap.mstrDestPath.startsWith("F:/manual_scripts/2d games")) {
                startWithFSlash ++;
            }
            if (srcDestPathMap.mstrDestPath.startsWith("F:\\manual_scripts\\2d games")) {
                startWithBSlash ++;
            }
        }
        if (IS_WINDOWS) {
            assertEquals(startWithFSlash, 47);
        } else {
            assertEquals(startWithFSlash, 48);
        }
        assertEquals(startWithBSlash, 1);
        srcDestPathMapList.clear();
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts/parallel";
        acc.mnSrcZipType = 0;
        acc.mstrSrcZipPath = "";
        acc.mstrSrcZipEntry = "";
        acc.mstrDestPath = "F:/manual_scripts/parallel/";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts/parallel/";
        acc.mnSrcZipType = 0;
        acc.mstrSrcZipPath = "";
        acc.mstrSrcZipEntry = "";
        acc.mstrDestPath = "F:/manual_scripts/parallel";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 2);
        srcDestPathMapList.clear();
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = "";
        acc.mnSrcZipType = 2;   // normal zip file
        acc.mstrSrcZipPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts.zip";
        acc.mstrSrcZipEntry = "manual_scripts/2d games/gemgem/gem1.png";
        acc.mstrDestPath = "F:/manual_scripts/2d games/gemgem/gem1.png";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 1);
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = "";
        acc.mnSrcZipType = 2;   // normal zip file
        acc.mstrSrcZipPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts.zip";
        acc.mstrSrcZipEntry = "manual_scripts/2d games/gemgem/";
        acc.mstrDestPath = "F:/manual_scripts/2d games/gemgem";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 17);
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = "";
        acc.mnSrcZipType = 2;   // normal zip file
        acc.mstrSrcZipPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts.zip";
        // this entry doesn't exist. But it starts with a valid zip folder entry
        acc.mstrSrcZipEntry = "manual_scripts/algorithmsexamples.mfps";
        acc.mstrDestPath = "F:/manual_scripts/algorithms/examples.mfps";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 17);
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = "";
        acc.mnSrcZipType = 2;   // normal zip file
        acc.mstrSrcZipPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts.zip";
        acc.mstrSrcZipEntry = "manual_scripts/2d games/gemgem";
        acc.mstrDestPath = "F:/manual_scripts/2d games/gemgem/";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 17);
        acc = new CompileAdditionalInfo.AssetCopyCmd();
        acc.mstrSrcPath = "";
        acc.mnSrcZipType = 2;   // normal zip file
        acc.mstrSrcZipPath = MFP4JavaFileMan.msstrAppFolder + "/manual_scripts.zip";
        // this entry doesn't exist. But it starts with a valid zip folder entry
        acc.mstrSrcZipEntry = "manual_scripts/algorithms/examples.mfps";    // this entry is valid
        acc.mstrDestPath = "F:/manual_scripts/algorithms/examples.mfps";
        mfpFileMan.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
        assertEquals(srcDestPathMapList.size(), 18);
    }
}
