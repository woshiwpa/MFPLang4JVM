/*
 * MFP project, MFP4JavaFileMan.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.OSAdapter;

import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.CompileAdditionalInfo;
import static com.cyzapps.OSAdapter.LangFileManager.STRING_DEFAULT_SCRIPT_FOLDER;
import static com.cyzapps.OSAdapter.LangFileManager.STRING_PATH_DIVISOR;
import com.cyzapps.OSAdapter.ParallelManager.CallObject;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.Oomfp.CitingSpaceDefinition.CheckMFPSLibMode;
import com.cyzapps.adapter.FunctionNameMapper;
import com.cyzapps.adapter.MFPAdapter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcmdline.JCmdLineApp;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

/**
 *
 * @author tony
 */
public class MFP4JavaFileMan extends LangFileManager {

    public static String msstrAppFolder = JCmdLineApp.STRING_APP_FOLDER;    // do not make it final so that configurable in JUnit test.

    public static LinkedList<String> madditionalUsrLibs = new LinkedList<String>();

    // the following variables store original paths (i.e. if it is a relative path, it is not converted to absolute).
    public static String msstrOriginalScriptPath = "";
    public static String msstrOriginalChartPath = "";
    public static LinkedList<String> moriginaladdUsrLibs = new LinkedList<String>();

    @Override
    public String[] list(String path, boolean bAndroidAsset) throws IOException {
        File file = new File(path);
        try {
            String[] strarrayList = file.list();    // if strarrayList is null, it means an IO exception.
            if (strarrayList == null) {
                throw new IOException("Invalid file path : " + path);
            }
            return strarrayList;
        } catch (SecurityException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream open(String path, boolean bAndroidAsset) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (SecurityException e) {
            throw new IOException(e.getMessage(), e);
        } // it may also throw a FileNotFoundException, which is a type of IOException.
        return inputStream;
    }

    /**
     * This function read a specific entry from a zipped file.
     * @param strZipFile : asset zip file.
     * @param strZippedFileEntryPath : path of zipped file entry in the zip. Note that it cannot be a folder
     * @param bAndroidAsset
     * @return : InputStream
     * @throws java.io.IOException
     */
    @Override
    public InputStream openZippedFileEntry(String strZipFile, String strZippedFileEntryPath, boolean bAndroidAsset) throws IOException {
        ArchiveInputStream ais = null;

        try {
            File fZippedFileEntry = new File(strZippedFileEntryPath);
            ais = new ArchiveStreamFactory().createArchiveInputStream("zip", new FileInputStream(strZipFile));

            ZipArchiveEntry entry = null;
            while ((entry = (ZipArchiveEntry) ais.getNextEntry()) != null) {
                File fEntry = new File(entry.getName());
                if (fZippedFileEntry.compareTo(fEntry) == 0) {
                    if (entry.isDirectory()) {
                        // we cannot extract a dictionary
                        return null;
                    } else {
                        return ais;
                    }
                }
            }
        } catch (ArchiveException ioe) {
            throw new IOException("ArchiveException thrown");
        }
        return null;
    }

    @Override
    public boolean isDirectory(String path, boolean bAndroidAsset) {
        File file = new File(path);
        return file.isDirectory();
    }

    @Override
    public Boolean isOnAndroid() {
        return false;
    }

    @Override
    public Boolean isMFPApp() {
        return false;
    }

    @Override
    public String getAssetFilePath(String assetFile) {
        // because java version of SCP doesn't have asset, return null.
        return null;
    }
    
    @Override
    public String getAppBaseFullPath() {
        return msstrAppFolder;
    }
    
    @Override
    public LinkedList<String> getAdditionalUserLibs() {
        Long thisThreadId = Thread.currentThread().getId();
        if (CallObject.msmapThreadId2SessionInfo.containsKey(thisThreadId)) {
            // this is a sandbox session
            String outputFolder = CallObject.msmapThreadId2SessionInfo.get(thisThreadId).getLibPath();
            LinkedList<String> additionalNames = CallObject.msmapThreadId2SessionInfo.get(thisThreadId).getAdditionalLibNames();
            LinkedList<String> additionalUserLibs = new LinkedList<String>();
            for (String additionalName: additionalNames) {
                additionalUserLibs.add(outputFolder + STRING_PATH_DIVISOR + additionalName);
            }
            return additionalUserLibs;
        } else {
            return madditionalUsrLibs;
        }
    }

    /**
     * @param listFilePaths : a list of source FILE paths, not folder path. Also, path divisor has been correctly set
     * to be LangFileManager.STRING_PATH_DIVISOR. Moreover, a file path must be relative path if it is in a user defined
     * lib folder or it is a user defined lib file. Otherwise, a file path can be absolute or relative.
     * @return : map from source file paths to compiled source path (in the apk package asset or in remote call sandbox)
     * @throws IOException 
     */
    @Override
    public Map<String, String> mapUsrLib2CompiledLib(LinkedList<String> listFilePaths) throws IOException {
        // keep in mind that listFilePaths is a list of source FILE paths, not folder path.
        Map<String, String> mappedUsrLibPaths = new HashMap<String, String>();
        String strDefaultUsrLib = getScriptFolderFullPath();
        try {
            strDefaultUsrLib = IOLib.getCanonicalPath(strDefaultUsrLib);
        } catch (ErrProcessor.JFCALCExpErrException e) {
            // if cannot get canonical path, use original path.
        }
        LinkedList<String> listAllUsrLibs = new LinkedList<String>();
        listAllUsrLibs.add(strDefaultUsrLib);
        listAllUsrLibs.addAll(getAdditionalUserLibs()); // all lib paths are canonical.
        // additional usr lib's path has been canonical.
        for (String strFileIdx : listFilePaths) {
            // ok, now let check which lib the file belongs to.
            String strFile = strFileIdx;
            try {
                // here, if path includes .. or ., it will be resolved.
                // if path is a symbolic link, it will always be converted
                // to real path.
                strFile = IOLib.getCanonicalPath(strFileIdx);
            } catch (Exception e) {
                throw new IOException(e);   // if the file cannot been found, definitely something wrong.
            }
            String strRelativePath = strFile;
            int nLibId = -1; // lib Id is 0 means default script folder, >=1 means additional script folder.
            int idx = listAllUsrLibs.size() - 1;
            for (; idx >= 0; idx--) {
                if (strFile.equals(listAllUsrLibs.get(idx)) // case that strFile == listAllUsrLibs.get(idx)
                        || (strFile.indexOf(listAllUsrLibs.get(idx)) == 0 // cast that strFile must be longer than listAllUsrLibs.get(idx)
                            && (strFile.substring(listAllUsrLibs.get(idx).length() - LangFileManager.STRING_PATH_DIVISOR.length(),
                                                    listAllUsrLibs.get(idx).length()).equals(LangFileManager.STRING_PATH_DIVISOR)
                                || strFile.substring(listAllUsrLibs.get(idx).length(),
                                                    listAllUsrLibs.get(idx).length() +  LangFileManager.STRING_PATH_DIVISOR.length())
                                                    .equals(LangFileManager.STRING_PATH_DIVISOR)
                                )   // we need to make sure strFile is a file in listAllUsrLibs.get(idx) if listAllUsrLibs.get(idx) is a FOLDER
                            )
                    ) {
                    nLibId = idx;   // get it. It is the lib.
                    if (strFile.equals(listAllUsrLibs.get(idx))) {
                        // listAllUsrLibs.get(idx) is a file.
                        strRelativePath = strFile.substring(strFile.lastIndexOf(LangFileManager.STRING_PATH_DIVISOR));
                    } else {
                        // listAllUsrLibs.get(idx) is a folder.
                        strRelativePath = strFile.substring(listAllUsrLibs.get(idx).length());
                    }
                    while (strRelativePath.indexOf(LangFileManager.STRING_PATH_DIVISOR) == 0) {
                        strRelativePath = strRelativePath.substring(LangFileManager.STRING_PATH_DIVISOR.length());
                    }
                    break;
                }
            }
            // first of all, we need to replace : in the windows path.
            // however, we don't look after cross platform path separator char, i.e. \ or /
            // and we also assume the path doesn't include forbidden characters which are
            // >, <, :, ", |, ? and *. Developers should keep this in mind. : is different
            // because Windows path has it anyway.
            strRelativePath = strRelativePath.replace(':', '$');
            switch (nLibId) {
                case -1:
                    strRelativePath = STRING_DEFAULT_SCRIPT_FOLDER + "Unknown" + LangFileManager.STRING_PATH_DIVISOR + strRelativePath;
                    break;
                case 0:
                    // main lib folder
                    strRelativePath = STRING_DEFAULT_SCRIPT_FOLDER + LangFileManager.STRING_PATH_DIVISOR + strRelativePath;
                    break;
                default:
                    strRelativePath = STRING_DEFAULT_SCRIPT_FOLDER + nLibId + LangFileManager.STRING_PATH_DIVISOR + strRelativePath;
                    break;
            }
            mappedUsrLibPaths.put(strFileIdx, strRelativePath);
        }
        return mappedUsrLibPaths;
    }

    @Override
    public void loadPredefLibs() {
        MFPAdapter.clear(CheckMFPSLibMode.CHECK_EVERYTHING);    // clear all MFP libs.
        
        MFPAdapter.getSysLibFiles(this, getAssetLibFolderFullPath());
        MFPAdapter.loadSysLib(this);    // load pre-defined lib.
        MFPAdapter.loadInternalFuncInfo(this, getAssetInterFuncInfoPath());
        MFPAdapter.loadMFPKeyWordsInfo(this, getAssetMFPKeyWordsInfoPath());

        MFPAdapter.copyAsset2SD(this, getAssetLibFolderFullPath(), getExampleFolderFullPath(), 0);

        // load system function (i.e. built-in and predefined) mappers.
        // This must be done before user defined functions are loaded.
        FunctionNameMapper.loadSysFuncInvertMap();
        FunctionNameMapper.loadSysFunc2FullCSMap();

        // now we save a copy of citing space definition sys and the saved copy will be csd Top full (for main entity).
        CitingSpaceDefinition.createTopCSDFull();

    }
    
    @Override
    public void mapAllNonDirMultiLevelChildResources(CompileAdditionalInfo.AssetCopyCmd acc, LinkedList<SourceDestPathMapInfo> srcDestPathMapList) {
        if (acc.isPlainPath()) {
            File f = new File(acc.mstrSrcPath);
            if (f.isFile()) {
                boolean bAddedEntry = false;
                for (SourceDestPathMapInfo sdpmi : srcDestPathMapList) {
                    if (0 == IOLib.comparePath(sdpmi.mstrSrcPath, acc.mstrSrcPath)
                            && 0 == IOLib.comparePath(sdpmi.mstrDestPath, acc.mstrDestPath)) {
                        bAddedEntry = true;
                        break;
                    }
                }
                if (!bAddedEntry) {
                    srcDestPathMapList.add(new SourceDestPathMapInfo(acc.mstrSrcPath, acc.mstrDestPath));
                }
            } else if (f.isDirectory()) {
                File[] childFiles = f.listFiles();
                for (File fChild : childFiles) {
                    if (fChild.isFile()) {
                        String strName = fChild.getName();
                        boolean bAddedEntry = false;
                        String strSrcPath = acc.mstrSrcPath.endsWith(LangFileManager.STRING_PATH_DIVISOR) ? (acc.mstrSrcPath + strName) : (acc.mstrSrcPath + LangFileManager.STRING_PATH_DIVISOR + strName);
                        String strDestPath = acc.mstrDestPath.endsWith(LangFileManager.STRING_PATH_DIVISOR) ? (acc.mstrDestPath + strName) : (acc.mstrDestPath + LangFileManager.STRING_PATH_DIVISOR + strName);
                        for (SourceDestPathMapInfo sdpmi : srcDestPathMapList) {
                            if (0 == IOLib.comparePath(sdpmi.mstrSrcPath, strSrcPath)
                                    && 0 == IOLib.comparePath(sdpmi.mstrDestPath, strDestPath)) {
                                bAddedEntry = true;
                                break;
                            }
                        }
                        if (!bAddedEntry) {
                            srcDestPathMapList.add(new SourceDestPathMapInfo(strSrcPath, strDestPath));
                        }
                    } else if (fChild.isDirectory()) {
                        String strName = fChild.getName();
                        CompileAdditionalInfo.AssetCopyCmd accChild = new CompileAdditionalInfo.AssetCopyCmd();
                        accChild.mstrSrcPath = acc.mstrSrcPath + LangFileManager.STRING_PATH_DIVISOR + strName;
                        accChild.mstrDestTarget = acc.mstrDestTarget;
                        accChild.mstrDestPath = acc.mstrDestPath + LangFileManager.STRING_PATH_DIVISOR + strName;
                        mapAllNonDirMultiLevelChildResources(accChild, srcDestPathMapList);
                    }
                }
            }
        } else if (acc.isNormalZip()) {
            ArchiveInputStream ais = null;

            try {
                ais = new ArchiveStreamFactory().createArchiveInputStream("zip", new FileInputStream(acc.mstrSrcZipPath));

                ZipArchiveEntry entry = null;
                while ((entry = (ZipArchiveEntry) ais.getNextEntry()) != null) {
                    if (entry.getName().startsWith(acc.mstrSrcZipEntry) // the entry is a child node of acc.mstrSrcZipEntry
                        && (entry.getName().equals(acc.mstrSrcZipEntry)
                            || acc.mstrSrcZipEntry.endsWith("/")
                            || entry.getName().substring(acc.mstrSrcZipEntry.length()).startsWith("/"))) {
                        // we can compare zip entry directly without caring about seperator
                        // because zip entry's seperator is always forward slash.
                        if (entry.isDirectory()) {
                            // this is a dictionary
                            continue;
                        } else {
                            // this is a file
                            boolean bAddedEntry = false;
                            for (SourceDestPathMapInfo sdpmi : srcDestPathMapList) {
                                if (0 == IOLib.comparePath(sdpmi.mstrSrcZipPath, acc.mstrSrcZipPath)
                                        && sdpmi.mstrSrcZipEntry.equals(entry.getName())
                                        && sdpmi.mnSrcZipType == acc.mnSrcZipType) {
                                    // this entry has been added, no need to add again.
                                    bAddedEntry = true;
                                    break;
                                }
                            }
                            if (!bAddedEntry) {
                                String strEntryPathRelativePart = entry.getName().substring(acc.mstrSrcZipEntry.length());
                                String strEntryDestFullPath = "";
                                if ((strEntryPathRelativePart.startsWith("/") && !acc.mstrDestPath.endsWith("/"))
                                    || (!strEntryPathRelativePart.startsWith("/") && acc.mstrDestPath.endsWith("/"))) {
                                    strEntryDestFullPath = acc.mstrDestPath + strEntryPathRelativePart;
                                } else if (strEntryPathRelativePart.startsWith("/") && acc.mstrDestPath.endsWith("/")) {
                                    strEntryDestFullPath = acc.mstrDestPath + strEntryPathRelativePart.substring(1); // remove "/" from beginning of strEntryPathRelativePart
                                } else {
                                    strEntryDestFullPath = acc.mstrDestPath + "/" + strEntryPathRelativePart;   // add "/" which is missing.
                                }
                                srcDestPathMapList.add(new SourceDestPathMapInfo(acc.mnSrcZipType, acc.mstrSrcZipPath, entry.getName(), strEntryDestFullPath));
                            }
                        }
                    }
                }
            } catch (ArchiveException ae) {
            } catch (IOException ioe) {
            }
        }
    }
    
    public void reloadAllUserLibs(String scriptFile2Run) {
        MFPAdapter.clear(CheckMFPSLibMode.CHECK_USER_DEFINED_ONLY); // clear all existing user libs
        
        // user lib must be loaded after system lib (i.e. predefined functions)
        MFPAdapter.getUsrLibFiles(this, getScriptFolderFullPath());
        for (String strPath : MFP4JavaFileMan.madditionalUsrLibs) {
            MFPAdapter.getUsrLibFiles(this, strPath);
        }
        if (scriptFile2Run != null) {
            boolean isPartOfAnExistingLib = false;
            String striptPathCanonical = scriptFile2Run;
            // do not throw exception even if we cannot getCanonicalPath for a file
            // we can just go to next file. Allow the exception been printed for
            // further debugging.
            try {
                striptPathCanonical = IOLib.getCanonicalPath(scriptFile2Run);
            } catch (JFCALCExpErrException ex) {
                Logger.getLogger(MFP4JavaFileMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            LinkedList<String> listLibPathsCanonical = new LinkedList<String>();
            try {
                listLibPathsCanonical.add(IOLib.getCanonicalPath(getScriptFolderFullPath()));
            } catch (JFCALCExpErrException ex) {
                Logger.getLogger(MFP4JavaFileMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String strPath : MFP4JavaFileMan.madditionalUsrLibs) {
                try {
                    listLibPathsCanonical.add(IOLib.getCanonicalPath(strPath));
                } catch (JFCALCExpErrException ex) {
                    Logger.getLogger(MFP4JavaFileMan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (String strPath : listLibPathsCanonical) {                
                if (new File(strPath).isDirectory()) {
                    if (striptPathCanonical.startsWith(strPath)) {
                        isPartOfAnExistingLib = true;
                        break;
                    }
                } else {
                    if (striptPathCanonical.equals(strPath)) {
                        isPartOfAnExistingLib = true;
                        break;
                    }
                }
            }
            if (!isPartOfAnExistingLib) {   // now we can ensure that there is no duplicate
                // load user lib file (only a single file) only if we are runing script.
                // do not check mstrScriptFileName != null && mstrScriptFileName.trim().length() > 0. If unsuccessful, unsuccessful.
                MFPAdapter.getUsrLibFiles(this, scriptFile2Run);
            }
        }
        MFPAdapter.loadUsrLib(this);    // load user defined lib.
        // hide this function because we want to analyse statements on the fly
        //MFPAdapter.analyseStatements(); // analyse the statements (using abstractexpr instead of string).
    }

}
