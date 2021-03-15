/*
 * MFP project, ApkBuilder.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcmdline;

import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassExtObjRef;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.DataClassString;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jmfp.AnnoType_build_asset;
import com.cyzapps.Jmfp.CompileAdditionalInfo;
import com.cyzapps.Jmfp.ErrorProcessor;
import com.cyzapps.Jmfp.FunctionEntry;
import com.cyzapps.Jmfp.ModuleInfo;
import com.cyzapps.Multimedia.ImageLib.ImageManager;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.LangFileManager.SourceDestPathMapInfo;
import com.cyzapps.adapter.MFPAdapter;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.Charsets;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CertCreator;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.DistinguishedNameValues;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;
import kellinwood.security.zipsigner.optional.PasswordObfuscator;

/**
 * Created by tony on 25/06/2017.
 */

public class ApkBuilder {
    public static final int MIN_ICON_SIZE = 128;
    public static final int LARGEST_VER_CODE = 65535;

    public static final int PKG_ID_ORIGINAL_BYTE_LEN = 40;
    public static final int VER_STR_ORIGINAL_BYTE_LEN = 20;

    public static final int MAX_FUNCTION_DESCRIPTION_LENGTH = 1024;

    public static final int MIN_PASSWORD_LENGTH = 8;

    // the format of the following four lines cannot be broken into several lines because they will be replaced by sed.
    private static final int[] PKG_ID_START_BYTES = new int[] {0x0570, 0x0a9c, 0x19f6, 0x1eea};
    private static final int VER_STR_START_BYTE = 0x059c;
    private static final int VER_CODE_START_BYTE = 0x2064;
    private static final int APP_NAME_START_BYTE = 0x1bdb4;

    public static final String STRING_SIGNATURE_FOLDER = "signkeys";
    public static final String STRING_KEYSTORE_KEY_DIV = "/";
    public static final String STRING_APK_FILE_EXTENSION = ".apk";
    public static final String STRING_APK_FOLDER = "apks";
    public static final String STRING_APK_GENERATION_TMP_FOLDER = "apk_generation_temp_folder_0041357";
    public static final String STRING_APK_UNZIPPED_FOLDER = "unzipped";
    public static final String STRING_ZIP_TO_SIGN = "Zip2Sign.zip";
    public static final String STRING_APK_ASSET_FOLDER = "assets";
    public static final String STRING_APK_USERDEF_LIB_FOLDER = "userdef_lib";
    public static final String STRING_APK_USERDEF_LIB_ZIP = "userdef_lib.zip";
    public static final String STRING_APK_ASSET_RESOURCE_ZIP = "resource.zip";
    public static final String STRING_APK_MFPAPP_CFG = "MFPApp.cfg";
    public static final String STRING_APK_ADS_CFG = "MFPApp.cfg1";
    public static final String STRING_APK_HELP_FOLDER = "help";
    public static final String STRING_APK_HELP_INDEX_HTML = "index.html";
    public static final String STRING_APK_RES_FOLDER = "res";
    public static final String STRING_APK_RES_DRAWABLES_INITIAL = "drawable";
    public static final String STRING_APK_RES_ICON_FILE_NAME = "icon.png";
    public static final String STRING_APK_MANIFEST_FILE = "AndroidManifest.xml";
    public static final String STRING_APK_RES_ARSC_FILE = "resources.arsc";

    protected static final int REQUEST_CODE_PICK_KEYSTORE_FILE = 1;
    protected static final int REQUEST_CODE_CREATE_KEYSTORE = 2;
    protected static final int REQUEST_CODE_CREATE_KEY = 3;

    protected static final int LONGEST_APP_NAME_IN_BYTES = 32;

    public static class ErrorInfo {
        public String mstrErrorSrc = "";
        public int mnErrorCode = 0;
        public Object moExtraInfo = null;

        public ErrorInfo() {
            // no error.
        }

        public ErrorInfo(String strErrorSrc, int nErrorCode) {
            mstrErrorSrc = strErrorSrc;
            mnErrorCode = nErrorCode;
        }
        public ErrorInfo(String strErrorSrc, int nErrorCode, Object oExtraInfo) {
            mstrErrorSrc = strErrorSrc;
            mnErrorCode = nErrorCode;
            moExtraInfo = oExtraInfo;
        }
    }

    public static boolean createApkGenerationTmpFolder(LangFileManager langFileManager) {
        String strApkGenerationFolder = getApkGenerationTmpFolderFullPath(langFileManager);
        File dir = new File(strApkGenerationFolder);
        if (!dir.exists())	{
            if (!dir.mkdirs())	{
                return false;	// cannot create destination folder
            }
        } else {
            IOLib.deleteFile(strApkGenerationFolder, true); // if dir exists, delete it. because of a bug in zipsigner, this cannot be successful.
            try {
                Thread.sleep(400);  // sleep 0.4s make sure folder is deleted.
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (!dir.exists() && !dir.mkdirs()) {    // creation failure
                return false;
            }
        }
        return true;
    }

    public static boolean deleteApkGenerationTmpFolder(LangFileManager langFileManager) {
        String strApkGenerationFolder = getApkGenerationTmpFolderFullPath(langFileManager);
        return IOLib.deleteFile(strApkGenerationFolder, true);
    }

    public static LinkedList<String> compileFunctions(CompileAdditionalInfo cai, String strFunctionName, int nFuncParamCnt, Boolean bFuncIncludeOptionalParam) throws ErrorProcessor.JMFPCompErrException {
        String strFuncName = FunctionEntry.getShrinkedName(strFunctionName.toLowerCase(Locale.US));
        int nNumOfParams = nFuncParamCnt;
        if (bFuncIncludeOptionalParam) {
            nNumOfParams = -1;
        }
        LinkedList<String> listFilePaths = null;
        try {
            listFilePaths = ModuleInfo.getReferredFilePathsAndOtherInfo(strFuncName, nNumOfParams, bFuncIncludeOptionalParam, cai);
        } catch (InterruptedException ex) {
            // interrupted exception will not be swallowed.
            ex.printStackTrace();
        }
        return listFilePaths;
    }

    public static boolean signZip(String strInputFile, String strOutputFile, String strKeyStore, String strKeyAlias, String strPassword) {
        try {
            ZipSigner signer = new ZipSigner();

            signer.addAutoKeyObserver( new Observer() {
                @Override
                public void update(Observable observable, Object o) {
                    System.out.println("Signing with key: "+o);
                }
            });

            Class<?> bcProviderClass = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
            Provider bcProvider = (Provider)bcProviderClass.newInstance();

            KeyStoreFileManager.setProvider( bcProvider);

            signer.loadProvider( "org.spongycastle.jce.provider.BouncyCastleProvider");

            if (strKeyStore != null && strKeyStore.length() > 0) {
                String alias = null;

                if (strKeyAlias == null || strKeyAlias.length() == 0) {
                    KeyStore keyStore =  KeyStoreFileManager.loadKeyStore( strKeyStore, (char[])null);
                    for (Enumeration<String> e = keyStore.aliases(); e.hasMoreElements(); ) {
                        alias = e.nextElement();
                        System.out.println("Signing with key: " + alias);
                        break;
                    }
                } else {
                    alias = strKeyAlias;
                }

                String keypw = strPassword;
                if (keypw.equals("")) {
                    keypw = null;
                }


                CustomKeySigner.signZip( signer, strKeyStore, null, alias, keypw.toCharArray(), "SHA1withRSA", strInputFile, strOutputFile);
            }
            else {
                signer.setKeymode("auto-testkey");
                signer.signZip(strInputFile, strOutputFile);
            }
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    public static ErrorInfo createAndSignApk(LangFileManager langFileManager, ImageManager imgMgr, String strFunctionName, int nFuncParamCnt,
                                             Boolean bFuncIncludeOptionalParam, String strMFPAppCfgXML, Boolean bWithoutAds, String strManual,
                                             String strSelectedIconFile, String strAppName, String strAppPkgId, String strAppVerStr, int nAppVerCode,
                                             String strApkName, String strKeyStoreName, String strKeyName, String strKeyPasswd) {
        // create tmp apk generation folder
        if (!createApkGenerationTmpFolder(langFileManager)) {
            ErrorInfo errorInfo = new ErrorInfo("apk_tmp_folder", -1); // get_cannot_delete_and_recreate_apk_tmp_folder
            return errorInfo;
        } else {
            LinkedList<String> listFilePaths = null;
            ErrorProcessor.JMFPCompErrException exUndefinedFunc = null;
            CompileAdditionalInfo cai = new CompileAdditionalInfo();
            try {
                listFilePaths = compileFunctions(cai, strFunctionName, nFuncParamCnt, bFuncIncludeOptionalParam);
            } catch (ErrorProcessor.JMFPCompErrException ex) {
                if (ex.m_se.m_enumErrorType == ErrorProcessor.ERRORTYPES.UNDEFINED_FUNCTION) {
                    exUndefinedFunc = ex;
                }
            }

            if (listFilePaths == null) {
                if (exUndefinedFunc == null) {
                    ErrorInfo errorInfo = new ErrorInfo("compilation", -1); // get_compilation_terminated_unexpectedly
                    return errorInfo;
                } else {
                    ErrorInfo errorInfo = new ErrorInfo("compilation", -2, exUndefinedFunc);    // get_undefined_function
                    return errorInfo;
                }
            }

            if (!createApkZip2Sign(langFileManager, imgMgr, listFilePaths, cai, strMFPAppCfgXML, bWithoutAds,
                    strManual, strSelectedIconFile, strAppName, strAppPkgId, strAppVerStr, nAppVerCode)) {
                ErrorInfo errorInfo = new ErrorInfo("apk_file", -1); // get_cannot_create_apk_file
                return errorInfo;
            } else {
                String strZip2SignFullPath = getZIP2SignFullPath(langFileManager);
                String strApkFile = getApkFolderFullPath(langFileManager) + LangFileManager.STRING_PATH_DIVISOR +  strApkName;
                boolean bResult = signZip(strZip2SignFullPath, strApkFile, strKeyStoreName, strKeyName, strKeyPasswd);
                deleteApkGenerationTmpFolder(langFileManager);
                ErrorInfo errorInfo = new ErrorInfo();
                if (!bResult) {
                    errorInfo.mstrErrorSrc = "apk_signer";
                    errorInfo.mnErrorCode = -100;
                }
                return errorInfo;
            }
        }
    }

    public static boolean createApkZip2Sign(LangFileManager langFileManager, ImageManager imgMgr, LinkedList<String> listFilePaths,
                                            CompileAdditionalInfo cai, String strMFPAppCfgXML, Boolean bWithoutAds, String strManual,
                                            String strSelectedIconFile, String strAppName, String strAppPkgId, String strAppVerStr, int nAppVerCode) {
        String strDestApkFullName = "";
        if (langFileManager.isOnAndroid()) {
            // copy to-be-modified apk file to tmp apk generation folder
            strDestApkFullName = getApkGenerationTmpFolderFullPath(langFileManager) + LangFileManager.STRING_PATH_DIVISOR + langFileManager.STRING_ASSET_MFP_APP_ZIP_FILE;
            if (!MFPAdapter.copyAssetFile2SD(langFileManager, LangFileManager.STRING_ASSET_MFP_APP_ZIP_FILE, strDestApkFullName)) {
                return false;	// cannot copy asset file.
            }
        } else {
            // use the apk file which has been in hard partition
            strDestApkFullName = langFileManager.getAssetFolderFullPath() + LangFileManager.STRING_PATH_DIVISOR
                    + LangFileManager.STRING_ASSET_MFP_APP_ZIP_FILE;
        }

        // unzip to-be-modified apk.
        String strDestApkUnzippedFullName = getApkUnzippedFolderFullPath(langFileManager);
        try {
            unzip(strDestApkFullName, strDestApkUnzippedFullName);
        } catch (IOException e) {
            return false;
        }

        // create user def lib files and folder
        if (!createUsrLib(langFileManager, listFilePaths)) {
            return false;
        }

        // create asset resource zip
        if (!createAssetResource(langFileManager, cai.mbuildAssetCopyCmds)) {
            return false;
        }

        // write mfpapp.cfg
        if (!createMFPAppCfg(langFileManager, strMFPAppCfgXML, bWithoutAds)) {
            return false;
        }

        // create mannual.xml
        if (!createManual(langFileManager, strManual)) {
            return false;
        }

        // change icon.pngs
        if (!changeIcons(langFileManager, strSelectedIconFile, imgMgr)) {
            return false;
        }

        // change package id, version info and app name etc.
        if (!changePkgIdVerAppName(langFileManager, strAppName, strAppPkgId, strAppVerStr, nAppVerCode)) {
            return false;
        }

        // create zip to sign
        LinkedList<String> listFiles = new LinkedList<String>();
        generateFileList(listFiles, strDestApkUnzippedFullName, new File(strDestApkUnzippedFullName));
        if (!zipFillsInFolderApache(listFiles, strDestApkUnzippedFullName, getZIP2SignFullPath(langFileManager))) {
            return false;
        }
        return true;
    }


    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + LangFileManager.STRING_PATH_DIVISOR + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        IOLib.createFile(filePath, false);	//need to create file first to ensure its parent folders are created.
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        int read = 0;
        while ((read = zipIn.read(MFPAdapter.m_sbyteBuffer)) != -1) {
            bos.write(MFPAdapter.m_sbyteBuffer, 0, read);
        }
        bos.close();
    }

    public static boolean createUsrLib(LangFileManager langFileManager, LinkedList<String> listFilePaths) {
        String strDestUsrDefLibZip = getApkUsrDefLibZipFullPath(langFileManager);
        try {
            FileOutputStream fos = new FileOutputStream(strDestUsrDefLibZip);
            ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos);
            zos.setEncoding("UTF-8");

            // output to zip:
            Map<String, String> mapUserLibs = langFileManager.mapUsrLib2CompiledLib(listFilePaths);
            for (String key : mapUserLibs.keySet()) {
                // now add strFile
                ArchiveEntry ze = new ZipArchiveEntry(mapUserLibs.get(key));

                zos.putArchiveEntry(ze);

                FileInputStream in = new FileInputStream(key);

                int len;
                while ((len = in.read(MFPAdapter.m_sbyteBuffer)) > 0) {
                    zos.write(MFPAdapter.m_sbyteBuffer, 0, len);
                }
                zos.closeArchiveEntry();
                in.close();
            }

            zos.finish();
            //remember close it
            zos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) { // string index exception etc.
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean createAssetResource(LangFileManager langFileManager, LinkedList<CompileAdditionalInfo.AssetCopyCmd> buildAssetCopyCmds) {
        String strAssetResourceZip = getApkAssetResourceZipFullPath(langFileManager);
        try {
            FileOutputStream fos = new FileOutputStream(strAssetResourceZip);
            ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos);
            zos.setEncoding("UTF-8");
            LinkedList<SourceDestPathMapInfo> srcDestPathMapList = new LinkedList<>();
            // output to zip:
            for (CompileAdditionalInfo.AssetCopyCmd acc : buildAssetCopyCmds) {
                // ok, now let check which lib the file belongs to.
                if (!acc.mstrDestTarget.equalsIgnoreCase(AnnoType_build_asset.ASSET_RESOURCE_TARGET)) {
                    continue;
                }
                langFileManager.mapAllNonDirMultiLevelChildResources(acc, srcDestPathMapList);
            }

            // now add resource files
            for (SourceDestPathMapInfo entry : srcDestPathMapList) {
                ArchiveEntry ze = new ZipArchiveEntry(entry.getDestPath());

                zos.putArchiveEntry(ze);

                InputStream in = langFileManager.readFileToInputStream(entry); // if readFileToInputStream can return, in will be valid.
                int len;
                while ((len = in.read(MFPAdapter.m_sbyteBuffer)) > 0) {
                    zos.write(MFPAdapter.m_sbyteBuffer, 0, len);
                }
                zos.closeArchiveEntry();
                in.close();
            }
            zos.finish();
            //remember close it
            zos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) { // string index exception etc.
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean createMFPAppCfg(LangFileManager langFileManager, String strMFPAppCfgXML, Boolean bWithoutAds) {
        // first step is to setup general config
        String strMFPAppCfg = getApkMFPAppCfgFullPath(langFileManager);
        IOLib.deleteFile(strMFPAppCfg, true);	// delete MFPApp.cfg

        try {
            int fd = IOLib.fOpen(strMFPAppCfg, "w", "UTF-8");
            // cannot directly use IOLib.fPrintf(fd, mstrMFPAppCfgXML, new LinkedList<DataClass>()); because like %, \ are misrecognized.
            LinkedList<DataClass> listData = new LinkedList<DataClass>();
            DataClass datum = new DataClassString(strMFPAppCfgXML);
            listData.add(datum);
            IOLib.fPrintf(fd, "%s", listData);
            IOLib.fClose(fd);
        } catch (Exception e) {
            return false;
        }

        if (langFileManager.isOnAndroid()) {
            // In android, second step is to setup ads config.
            String strMFPAppCfg1 = getApkAdsCfgFullPath(langFileManager);
            IOLib.deleteFile(strMFPAppCfg1, true);    // delete MFPApp1.cfg

            String strAdsSettingEncrypted = encryptAdsCfg(bWithoutAds, strMFPAppCfgXML);
            try {
                int fd = IOLib.fOpen(strMFPAppCfg1, "w", "UTF-8");
                // cannot directly use IOLib.fPrintf(fd, mstrMFPAppCfgXML, new LinkedList<DataClass>()); because like %, \ are misrecognized.
                LinkedList<DataClass> listData = new LinkedList<DataClass>();
                DataClass datum = new DataClassString(strAdsSettingEncrypted);
                listData.add(datum);
                IOLib.fPrintf(fd, "%s", listData);
                IOLib.fClose(fd);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    protected static String encryptAdsCfg(boolean bWithoutAds, String strMFPAppCfg) {
        int[] narrayAdsSettingKey = new int[32];
        String strReturn = "";
        for (int idx = 0; idx < narrayAdsSettingKey.length; idx ++) {
            narrayAdsSettingKey[idx] = (int)(Math.random() * 10);
            if (narrayAdsSettingKey[idx] > 9) {
                narrayAdsSettingKey[idx] = 9;
            } else if (narrayAdsSettingKey[idx] < 0) {
                narrayAdsSettingKey[idx] = 0;
            }
            strReturn += narrayAdsSettingKey[idx];
        }
        // now consider idx = 2, 9, 13, 22
        int nValue4Idx2 = narrayAdsSettingKey[narrayAdsSettingKey[2] + 2];
        int nValue4Idx9 = narrayAdsSettingKey[narrayAdsSettingKey[9] + 9];
        int nValue4Idx13 = narrayAdsSettingKey[narrayAdsSettingKey[13] + 13];
        int nValue4Idx22 = narrayAdsSettingKey[narrayAdsSettingKey[22] + 22];

        int nValueEncrypted = (nValue4Idx2 + nValue4Idx22) * (nValue4Idx9 + nValue4Idx13);
        if (!bWithoutAds) {
            nValueEncrypted += 179 + Math.random() * 53;
        }
        strReturn += nValueEncrypted;
        int nSumOfCodes = 0;
        int idx = 0;
        while(idx < strMFPAppCfg.length()) {
            nSumOfCodes += strMFPAppCfg.codePointAt(idx) % 23;
            // using this way to avoid too many loops
            if (idx < 1024) {
                idx ++;
            } else if (idx < 1048576) {
                idx += 1024;
            } else if (idx < 1073741824) {
                idx += 1048576;
            } else {
                break;
            }
        }
        int nMFPAppCfgMod = (int) (nSumOfCodes % 293);
        if (nMFPAppCfgMod >= 100) {
            strReturn = nMFPAppCfgMod + strReturn;
        } else if (nMFPAppCfgMod >= 10) {
            strReturn = "0" + nMFPAppCfgMod + strReturn;
        } else {
            strReturn = "00" + nMFPAppCfgMod + strReturn;
        }
        return strReturn;
    }

    public static boolean createManual(LangFileManager langFileManager, String strManual) {
        String strHelpIndexHtml = getApkHelpIdxHtmlFullPath(langFileManager);
        // write help/index.html
        try {
            int fd = IOLib.fOpen(strHelpIndexHtml, "w", "UTF-8");
            //write
            // cannot use
            // IOLib.fPrintf(fd, strManual, new LinkedList<DataClass>());
            // because strManual may include % which is the control character in fprintf
            DataClassString datumStr = new DataClassString(strManual);
            IOLib.fPrintf(fd, "%s", new LinkedList<DataClass>(Arrays.asList(datumStr)));
            IOLib.fClose(fd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean changeIcons(LangFileManager langFileManager, String strSelectedIconFile, ImageManager imgMgr) {
        if (strSelectedIconFile.length() == 0) {
            return true;    // this means we haven't select our own icon.
        }
        String strApkResFolderFullPath = getApkResFolderFullPath(langFileManager);
        File resDir = new File(strApkResFolderFullPath);
        String[] listResSubFolders = resDir.list();
        if (listResSubFolders == null) {
            listResSubFolders = new String[0];
        }

        DataClass imgPng = imgMgr.loadImage(strSelectedIconFile);
        if (imgPng.isNull()) {
            return false;   // strSelectedIconFile is not a valid image
        }
        int nNumOfDrawables = 0;
        for (String strSubFolder : listResSubFolders) {
            if (strSubFolder.indexOf(STRING_APK_RES_DRAWABLES_INITIAL) == 0) {
                // ok, this is a drawable string
                String strDrawableFolder = strApkResFolderFullPath + LangFileManager.STRING_PATH_DIVISOR + strSubFolder;
                File drawableDir = new File(strDrawableFolder);
                if (drawableDir.exists() && drawableDir.isDirectory()) {
                    String strIconPath = strDrawableFolder + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_RES_ICON_FILE_NAME;
                    File iconFile = new File(strIconPath);
                    if (iconFile.exists() && iconFile.isFile()) {
                        DataClass imageNewPng = new DataClassNull();
                        try {
                            DataClass image2Replace = imgMgr.loadImage(strIconPath);
                            if (image2Replace.isNull()) {
                                return false;
                            }
                            DataClassExtObjRef imgPngRef = DCHelper.lightCvtOrRetDCExtObjRef(imgPng);
                            int[] sizeImgPng = imgMgr.getImageSize(imgPngRef);
                            DataClassExtObjRef img2ReplaceRef = DCHelper.lightCvtOrRetDCExtObjRef(image2Replace);
                            int[] sizeImg2Replace = imgMgr.getImageSize(img2ReplaceRef);
                            imageNewPng = imgMgr.cloneImage(imgPngRef,
                                    0, 0, sizeImgPng[0], sizeImgPng[1], sizeImg2Replace[0], sizeImg2Replace[1]);
                            if (imageNewPng.isNull()) {
                                return false;
                            }
                        } catch (JFCALCExpErrException ex) {
                            ex.printStackTrace();
                            return false;
                        }
                        try {
                            imgMgr.saveImage(DCHelper.lightCvtOrRetDCExtObjRef(imageNewPng), "png", strIconPath);
                            nNumOfDrawables++;
                        } catch (JFCALCExpErrException ex) {
                            ex.printStackTrace();
                            return false;    // cannot write to png file.
                        }
                    }
                }
            }
        }
        if (nNumOfDrawables == 0) {
            return false;    // no png file has been generated
        }

        return true;
    }

    /**
     * Note that package id and app version string must be same as before creation. If the lengths are changed, apk file is invalid.
     * @return
     */
    public static boolean changePkgIdVerAppName(LangFileManager langFileManager, String strAppName, String strAppPkgId, String strAppVerStr, int nAppVerCode) {
        try {
            String strManifestFile = getApkManifestFileFullPath(langFileManager);
            RandomAccessFile raf = new RandomAccessFile(strManifestFile, "rw");

            // change package id
            for (int pkgIDStartByte : PKG_ID_START_BYTES) {
                raf.seek(pkgIDStartByte);
                byte[] listBytesPkgId = new byte[strAppPkgId.length() * 2];
                for (int idx = 0; idx < strAppPkgId.length(); idx++) {
                    listBytesPkgId[idx * 2] = strAppPkgId.getBytes(Charsets.UTF_8)[idx];    // UTF-8 is default charset on Android but not in JAVA.
                    listBytesPkgId[idx * 2 + 1] = 0;
                }
                raf.write(listBytesPkgId);
            }

            // change version string
            raf.seek(VER_STR_START_BYTE);
            byte[] listBytesVerStr = new byte[strAppVerStr.length() * 2];
            for (int idx = 0; idx < strAppVerStr.length(); idx ++) {
                listBytesVerStr[idx * 2] = strAppVerStr.getBytes(Charsets.UTF_8)[idx];	// UTF-8 is default charset on Android but not in JAVA.
                listBytesVerStr[idx * 2 + 1] = 0;
            }
            raf.write(listBytesVerStr);

            // change version code
            raf.seek(VER_CODE_START_BYTE);
            byte[] listBytesVerCode = new byte[2];
            listBytesVerCode[0] = (byte)nAppVerCode;
            listBytesVerCode[1] = (byte)(nAppVerCode >> 8);
            raf.write(listBytesVerCode);

            // Manifest done!
            raf.close();

            String strResArscFile = getApkResArscFileFullPath(langFileManager);
            raf = new RandomAccessFile(strResArscFile, "rw");

            // change app name, note that the first 2 types infront of app name are char len and byte len
            raf.seek(APP_NAME_START_BYTE - 2);
            byte nstrAppNameLen = (byte)strAppName.length();
            byte[] listBytesAppName = strAppName.getBytes(Charsets.UTF_8);	// App name may include UTF-8. it's default charset on Android. But not default in JAVA.
            byte nstrAppNameByteLen = (byte)listBytesAppName.length;
            byte[] listLongestAppNameBytes = new byte[LONGEST_APP_NAME_IN_BYTES + 2]; // dont forget the first two bytes to store lengths.
            listLongestAppNameBytes[0] = (byte)(nstrAppNameLen + (LONGEST_APP_NAME_IN_BYTES - nstrAppNameByteLen));
            listLongestAppNameBytes[1] = LONGEST_APP_NAME_IN_BYTES;
            for (int idx = 0; idx < LONGEST_APP_NAME_IN_BYTES; idx ++) {
                if (idx < listBytesAppName.length) {
                    listLongestAppNameBytes[idx + 2] = listBytesAppName[idx];
                } else {
                    listLongestAppNameBytes[idx + 2] = (byte)0;	// pad the byte list with 0.
                }
            }
            raf.write(listLongestAppNameBytes);

            // resources.arsc done!
            raf.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public static void generateFileList(LinkedList<String> fileList, String strSourceFolder, File node){

        //add file only
        if(node.isFile()){
            String strFilePath = node.getAbsoluteFile().toString();
            if (strFilePath.length() > strSourceFolder.length() + 1) {
                String strFileRelativePath = strFilePath.substring(strSourceFolder.length() + 1);
                fileList.add(strFileRelativePath);
            }
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(fileList, strSourceFolder, new File(node, filename));
            }
        }
    }

    /**
     * Zip all files in a folder into a zip package. In windows, because Java.util.zip is not compatiable
     * with zipsigner, have to use this function to zip.
     * @param fileList
     * @param strSourceFolder
     * @param strZipOutput output ZIP file location
     */
    public static boolean zipFillsInFolderApache(LinkedList<String> fileList, String strSourceFolder, String strZipOutput){

        try{

            FileOutputStream fos = new FileOutputStream(strZipOutput);
            ArchiveOutputStream zos = new ZipArchiveOutputStream(fos);

            // output to zip:
            for(String strFile : fileList){

                // now add strFile
                ArchiveEntry ze= new ZipArchiveEntry(strFile);
                zos.putArchiveEntry(ze);

                FileInputStream in = new FileInputStream(strSourceFolder + LangFileManager.STRING_PATH_DIVISOR + strFile);

                int len;
                while ((len = in.read(MFPAdapter.m_sbyteBuffer)) > 0) {
                    zos.write(MFPAdapter.m_sbyteBuffer, 0, len);
                }
                zos.closeArchiveEntry();
                in.close();
            }

            zos.finish();
            //remember close it
            zos.close();

            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Zip all files in a folder into a zip package
     * In windows, because Java.util.zip is not compatiable with zipsigner, cannot use this function to zip.
     * @param fileList
     * @param strSourceFolder
     * @param strZipOutput output ZIP file location
     */
    public static boolean zipFillsInFolder(LinkedList<String> fileList, String strSourceFolder, String strZipOutput){

        try{

            FileOutputStream fos = new FileOutputStream(strZipOutput);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(Deflater.BEST_COMPRESSION);

            // output to zip:
            for(String strFile : fileList){

                // now add strFile
                ZipEntry ze= new ZipEntry(strFile);
                zos.putNextEntry(ze);

                FileInputStream in = new FileInputStream(strSourceFolder + LangFileManager.STRING_PATH_DIVISOR + strFile);

                int len;
                while ((len = in.read(MFPAdapter.m_sbyteBuffer)) > 0) {
                    zos.write(MFPAdapter.m_sbyteBuffer, 0, len);
                }
                zos.closeEntry();
                in.close();
            }

            zos.finish();
            //remember close it
            zos.close();

            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static LinkedList<String> findAllKeys(LangFileManager langFileManager) {
        String strKeyFolder = getSignatureFolderFullPath(langFileManager);
        LinkedList<String> listStrs = new LinkedList<String>();
        File dir = new File(strKeyFolder);
        if (!dir.exists())	{
            if (!dir.mkdirs())	{
                return listStrs;	// cannot create destination folder
            } else {
                return listStrs;	// can create the dir, but dir is empty
            }
        } else if (!dir.isDirectory()) {
            return listStrs;	// this is a file.
        }
        // ok, now we see the dir, find out all the keys.
        String[] strFiles = dir.list();
        if (strFiles == null || strFiles.length == 0) {
            return listStrs;	// nothing found.
        }

        for (int idx = 0; idx < strFiles.length; idx ++) {
            String fileName = strKeyFolder + LangFileManager.STRING_PATH_DIVISOR + strFiles[idx];
            File file = new File(fileName);
            if (file.isFile()) {
                // ok, this is a file, not a folder.
                String alias = null;
                try {
                    ZipSigner signer = new ZipSigner();

                    signer.addAutoKeyObserver( new Observer() {
                        @Override
                        public void update(Observable observable, Object o) {
                            System.out.println("Signing with key: "+o);
                        }
                    });

                    Class<?> bcProviderClass = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
                    Provider bcProvider = (Provider)bcProviderClass.newInstance();

                    KeyStoreFileManager.setProvider( bcProvider);

                    signer.loadProvider( "org.spongycastle.jce.provider.BouncyCastleProvider");

                    KeyStore keyStore =  KeyStoreFileManager.loadKeyStore( fileName, (char[])null);
                    for (Enumeration<String> e = keyStore.aliases(); e.hasMoreElements(); ) {
                        alias = e.nextElement();
                        System.out.println("Found key: " + alias);
                        break;
                    }
                } catch(Throwable t) {
                    t.printStackTrace();
                }
                if (alias != null) {
                    // get it.
                    String strStoreKeyFullName = strFiles[idx] + STRING_KEYSTORE_KEY_DIV + alias;
                    listStrs.add(strStoreKeyFullName);
                }
            }
        }
        return listStrs;
    }

    public static class Params {
        int requestCode;
        String storePath;
        String storePass;
        String keyName;
        String keyAlgorithm;
        int keySize;
        String keyPass;
        int certValidityYears;
        String certSignatureAlgorithm;
        DistinguishedNameValues distinguishedNameValues = new DistinguishedNameValues();
    }

    public static boolean createCertificate(Params params) {
        String strEncodedStorePass = PasswordObfuscator.getInstance().encodeKeystorePassword(params.storePath, params.storePass);
        char[] storePass = PasswordObfuscator.getInstance().decodeKeystorePassword(params.storePath, strEncodedStorePass);
        String strEncodedKeyPass = PasswordObfuscator.getInstance().encodeAliasPassword(params.storePath, params.keyName, params.keyPass);
        char[] keyPass = PasswordObfuscator.getInstance().decodeAliasPassword(params.storePath, params.keyName, strEncodedKeyPass);
        boolean bCreateSucceed = false;
        try {
            Class<?> bcProviderClass = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
            Provider bcProvider = (Provider) bcProviderClass.newInstance();

            KeyStoreFileManager.setProvider(bcProvider);

            if (params.requestCode == REQUEST_CODE_CREATE_KEYSTORE) {
                CertCreator.createKeystoreAndKey(params.storePath, storePass,
                        params.keyAlgorithm, params.keySize, params.keyName, keyPass,
                        params.certSignatureAlgorithm, params.certValidityYears, params.distinguishedNameValues);
            } else if (params.requestCode == REQUEST_CODE_CREATE_KEY) {
                CertCreator.createKey(params.storePath, storePass,
                        params.keyAlgorithm, params.keySize, params.keyName, keyPass,
                        params.certSignatureAlgorithm, params.certValidityYears, params.distinguishedNameValues);
            }

            bCreateSucceed = true;
        } catch (Exception x) {
            bCreateSucceed = false;
        } finally {
            PasswordObfuscator.flush(storePass);
            PasswordObfuscator.flush(keyPass);
        }
        return bCreateSucceed;
    }

    public static String getApkFolderFullPath(LangFileManager langFileManager)	{
        return langFileManager.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_FOLDER;
    }

    public static String getAssetMFPAppZipFullPath(LangFileManager langFileManager) {
        return langFileManager.getAssetFolderFullPath()
                + LangFileManager.STRING_PATH_DIVISOR
                + LangFileManager.STRING_ASSET_MFP_APP_ZIP_FILE;
    }

    public static String getApkGenerationTmpFolderFullPath(LangFileManager langFileManager)	{
        return getApkFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_GENERATION_TMP_FOLDER;
    }

    public static String getApkUnzippedFolderFullPath(LangFileManager langFileManager)	{
        return getApkGenerationTmpFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_UNZIPPED_FOLDER;
    }

    public static String getApkAssetFolderFullPath(LangFileManager langFileManager)	{
        return getApkUnzippedFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_ASSET_FOLDER;
    }

    private static String getApkUsrDefLibZipFullPath(LangFileManager langFileManager)	{
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_USERDEF_LIB_ZIP;
    }

    public static String getApkUsrDefLibFolderFullPath(LangFileManager langFileManager)	{
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_USERDEF_LIB_FOLDER;
    }

    private static String getApkAssetResourceZipFullPath(LangFileManager langFileManager) {
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_ASSET_RESOURCE_ZIP;
    }

    public static String getApkMFPAppCfgFullPath(LangFileManager langFileManager)	{
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_MFPAPP_CFG;
    }

    public static String getZIP2SignFullPath(LangFileManager langFileManager)	{
        return getApkGenerationTmpFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_ZIP_TO_SIGN;
    }

    public static String getSignatureFolderFullPath(LangFileManager langFileManager)	{
        return langFileManager.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR + STRING_SIGNATURE_FOLDER;
    }

    public static String getApkHelpFolderFullPath(LangFileManager langFileManager)	{
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_HELP_FOLDER;
    }

    public static String getApkHelpIdxHtmlFullPath(LangFileManager langFileManager)	{
        return getApkHelpFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_HELP_INDEX_HTML;
    }

    public static String getApkAdsCfgFullPath(LangFileManager langFileManager)	{
        return getApkAssetFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_ADS_CFG;
    }

    public static String getApkResFolderFullPath(LangFileManager langFileManager)	{
        return getApkUnzippedFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_RES_FOLDER;
    }

    public static String getApkManifestFileFullPath(LangFileManager langFileManager) {
        return getApkUnzippedFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_MANIFEST_FILE;
    }

    public static String getApkResArscFileFullPath(LangFileManager langFileManager) {
        return getApkUnzippedFolderFullPath(langFileManager)
                + LangFileManager.STRING_PATH_DIVISOR + STRING_APK_RES_ARSC_FILE;
    }

}
