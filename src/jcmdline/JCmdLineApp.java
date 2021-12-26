/*
 * MFP project, JCmdLineApp.java : Designed and developed by Tony Cui in 2021
 * JCmdLineApp.java
 */
package jcmdline;

import com.cyzapps.GI2DAdapter.FlatGDIManager;
import com.cyzapps.JPlatformHW.PlatformHWManager;
import com.cyzapps.Jfcalc.Operators.CalculateOperator;
import com.cyzapps.Jfcalc.DCHelper.CurPos;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.Operators.OPERATORTYPES;
import com.cyzapps.Jfcalc.ElemAnalyzer;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import com.cyzapps.Jfcalc.ExprEvaluator;
import com.cyzapps.Jfcalc.FuncEvaluator;
import com.cyzapps.Jfcalc.FuncEvaluator.ConsoleInputStream;
import com.cyzapps.Jfcalc.FuncEvaluator.FileOperator;
import com.cyzapps.Jfcalc.FuncEvaluator.FunctionInterrupter;
import com.cyzapps.Jfcalc.FuncEvaluator.LogOutputStream;
import com.cyzapps.Jfcalc.IOLib;
import com.cyzapps.Jfcalc.MFPNumeric;
import com.cyzapps.Jmfp.ErrorProcessor;
import com.cyzapps.Jmfp.ErrorProcessor.ERRORTYPES;
import com.cyzapps.Jmfp.ErrorProcessor.JMFPCompErrException;
import com.cyzapps.Jmfp.ScriptAnalyzer;
import com.cyzapps.Jmfp.ScriptAnalyzer.FuncRetException;
import com.cyzapps.Jmfp.ScriptAnalyzer.ScriptInterrupter;
import com.cyzapps.Jmfp.ScriptAnalyzer.ScriptStatementException;
import com.cyzapps.Jmfp.Statement;
import com.cyzapps.Jmfp.Statement_variable;
import com.cyzapps.Jmfp.VariableOperator;
import com.cyzapps.Jmfp.VariableOperator.Variable;
import com.cyzapps.Jmfp.FunctionEntry;

import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Jmfp.ScriptAnalyzer.InFunctionCSManager;
import com.cyzapps.Jmfp.Statement_using;
import com.cyzapps.Jsma.AbstractExpr;
import com.cyzapps.Jsma.AbstractExpr.AbstractExprInterrupter;
import com.cyzapps.Jsma.PatternManager;
import com.cyzapps.Jsma.SMErrProcessor.JSmartMathErrException;
import com.cyzapps.Multimedia.MultimediaManager;
import com.cyzapps.MultimediaAdapter.ImageMgrJava;
import com.cyzapps.MultimediaAdapter.SoundMgrJava;
import com.cyzapps.OSAdapter.JavaRtcMMediaMan;
import com.cyzapps.OSAdapter.LangFileManager;
import com.cyzapps.OSAdapter.MFP4JavaFileMan;
import com.cyzapps.OSAdapter.ParallelManager.MFP4JavaCommMan;
import com.cyzapps.Oomfp.CitingSpaceDefinition;
import com.cyzapps.Oomfp.CitingSpaceDefinition.CheckMFPSLibMode;
import com.cyzapps.PlotAdapter.ChartOperator;
import com.cyzapps.adapter.MFPAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import jcmdline.shellman.ShellManager;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The main class of the application.
 */
public class JCmdLineApp extends SingleFrameApplication {

    public static final String STRING_APP_VERSION = "2.0.2.90";
    public static final String STRING_APP_FOLDER = getProgramFolder();
    public static final String STRING_APP_VENDOR = "cyzsoft ( cyzsoft@gmail.com )";
    public static final String STRING_APP_HOMEPAGE = "https://play.google.com/store/apps/details?id=com.cyzapps.AnMath";

    public static final String STRING_VERSION_FILE_NAME = "version";
    protected static SplashScreen mssplashScreen = null;
    protected static Rectangle2D msrectSplashTextArea;
    protected static Rectangle2D msrectSplashProgressArea;
    protected static Graphics2D msg2dSplashGraphics;
    protected static Font msfontSplashText;

    public LinkedList<Variable> mlCmdLineLocalVars = new LinkedList<Variable>();
    public Variable mvarAns = new Variable("ans", new DataClassNull());

    // note : mshellManager needs mlCmdLineLocalVars to initialize its mCmdLineVarMan,
    // so it must be placed below mlCmdLineLocalVars.
    protected ShellManager mshellManager = new ShellManager(this);

    protected static MFP4JavaFileMan mMFP4JavaFileMan = new MFP4JavaFileMan();

    protected int startupLoadSettings() {
        // now start to load settings.
    	int loadingFailed = 0;
        if (loadJsonSettings() != 0) {
            // Json Settings file loading is unsuccessful. try to load original settings file.
            try {
                if (IOLib.isExistingFile(getSettingsFileFullPath())) {
                	loadingFailed = loadSettings();
                    if (writeJsonSettings()) {
                        // now try to convert old format to new format.
                        IOLib.deleteFile(getSettingsFileFullPath(), true);
                    }
                }
            } catch(Exception e) {
            }
        }
        return loadingFailed;
    }
    protected int startupSetupFiles(LinkedList<String> listErrMsgs, int nErrorType)
    {
        if (isNewVersion()) {
            if (unzipAssets7z(mMFP4JavaFileMan.getAppBaseFullPath()
                    + LangFileManager.STRING_PATH_DIVISOR
                    + LangFileManager.STRING_ASSET_ZIP_FILE) == false) {
                listErrMsgs.add(getStringsClass().get_find_corrupted_asset_files());
                nErrorType = nErrorType | 1;
            }
            writeVersionInfo();
        } else {
            File dir = new File(mMFP4JavaFileMan.getAssetFolderFullPath());
            if ((!dir.exists()) || (!dir.isDirectory())) {
                if (unzipAssets7z(mMFP4JavaFileMan.getAppBaseFullPath()
                        + LangFileManager.STRING_PATH_DIVISOR
                        + LangFileManager.STRING_ASSET_ZIP_FILE) == false) {
                    listErrMsgs.add(getStringsClass().get_cannot_find_asset_files());
                    nErrorType = nErrorType | 1;
                }
            }
        }

        boolean bIs64 = false;
        String strBits = System.getProperty("sun.arch.data.model", "?");
        if (strBits.equals("64")) {
            bIs64 = true;
        } else if (strBits.equals("32")) {
            bIs64 = false;
        } else {
            // probably sun.arch.data.model isn't available
            // maybe not a Sun JVM?
            // try with the vm.name property
            bIs64 = (System.getProperty("java.vm.name").indexOf("64") >= 0);
        }
        try {
            if (bIs64) {
                // 64 bits
                addLibraryPath(mMFP4JavaFileMan.getAssetJNI64FolderFullPath());
            } else {
                addLibraryPath(mMFP4JavaFileMan.getAssetJNIFolderFullPath());
            }
        } catch (Exception ex) {
            listErrMsgs.add(getStringsClass().get_cannot_load_opengl_libs());
            nErrorType = nErrorType | 1;
        }

        System.setProperty("sun.awt.noerasebackground", "true");    // when plotting 2D chart, this avoid flickerring.
        return nErrorType;
    }

    protected int startupLoadLibs(LinkedList<String> listErrMsgs, int nErrorType, String scriptFileName)
    {
        // msPlatformHWMgr has to be initialized early as it is needed to analyze
        // anotations when loading lib.
        FuncEvaluator.msPlatformHWMgr = new PlatformHWManager(mMFP4JavaFileMan);
        // load functions
        if (CitingSpaceDefinition.getTopCSD().isMFPSLibEmpty(CheckMFPSLibMode.CHECK_EVERYTHING)) {  // call getTopCSD() here mscsdTOPFull will return
            // functions haven't been loaded
            MFPAdapter.clear(CheckMFPSLibMode.CHECK_EVERYTHING);
            boolean bRootFolderOK = true;
            File dir = new File(mMFP4JavaFileMan.getAppBaseFullPath());
            if ((!dir.exists()) || (!dir.isDirectory())) {
                if (!dir.mkdirs()) {
                    // cannot create app folder.
                    listErrMsgs.add(getStringsClass().get_cannot_create_app_folder());
                    nErrorType = nErrorType | 2;
                    bRootFolderOK = false;
                }
            }
            if (bRootFolderOK) {
                String strScriptFolderPath = mMFP4JavaFileMan.getScriptFolderFullPath();
                dir = new File(strScriptFolderPath);
                if ((!dir.exists()) || (!dir.isDirectory())) {
                    if (!dir.mkdirs()) {
                        // cannot create script folder.
                        listErrMsgs.add(getStringsClass().get_cannot_create_script_folder());
                        nErrorType = nErrorType | 2;
                    }
                }
                String strChartFolderPath = mMFP4JavaFileMan.getChartFolderFullPath();
                dir = new File(strChartFolderPath);
                if ((!dir.exists()) || (!dir.isDirectory())) {
                    if (!dir.mkdirs()) {
                        // cannot create chart folder.
                        listErrMsgs.add(getStringsClass().get_cannot_create_chart_folder());
                        nErrorType = nErrorType | 2;
                    }
                }
            }

            mMFP4JavaFileMan.loadPredefLibs();  // load system predefined libs and copy libs to scripts folder and create csd top sys.
            // if scriptFileName is not null, clear and load all user defined libs and the single script to rule.
            // if scriptFileName is null, clear and load all user defined libs only.
            mMFP4JavaFileMan.reloadAllUserLibs(scriptFileName);
        }
        mlCmdLineLocalVars.addLast(mvarAns);
        return nErrorType;
    }
    
    protected String startupOutputError(LinkedList<String> listErrMsgs, int nErrorType) {
        String strErrs = "";
        if (listErrMsgs.size() > 0) {
            if (listErrMsgs.size() == 1) {
                strErrs = listErrMsgs.get(0);
            } else {
                strErrs = getStringsClass().get_receive_following_errors();
                for (int idx = 0; idx < listErrMsgs.size(); idx++) {
                    strErrs += "\n" + (idx + 1) + ". " + listErrMsgs.get(idx);
                }

            }
            if ((nErrorType & 2) == 2) {
                strErrs += "\n" + getStringsClass().get_app_in_a_read_only_folder();
            } else if ((nErrorType & 1) == 1) {
                strErrs += "\n" + getStringsClass().get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb();
            }
        }
        return strErrs;
    }
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

        initSplash();

    	startupLoadSettings();

        showSplashText(getStringsClass().get_unzipping_asset_files());
        showSplashProgress(40);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // ignore it
        }

        LinkedList<String> listErrMsgs = new LinkedList<String>();
        int nErrorType = 0; // 1 means AnMath is not in a mapped partition folder (some phones' USB driver may lead to the problem)
                            // 2 means AnMath is in a read only folder.
        nErrorType = startupSetupFiles(listErrMsgs, nErrorType);

        showSplashText(getStringsClass().get_loading_functions());
        showSplashProgress(80);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // ignore it
        }
        
        nErrorType = startupLoadLibs(listErrMsgs, nErrorType, null);

        mCmdLineView = new JCmdLineView(this);
        show(mCmdLineView);
        getMainFrame().setTitle(getStringsClass().get_app_name());

        if (mssplashScreen != null && mssplashScreen.isVisible()) {   // check if we really had a spash screen
            mssplashScreen.close();   // if so we're now done with it
        }
        String strErrs = startupOutputError(listErrMsgs, nErrorType);
        if (strErrs.length() > 0) {
            // output error if there are error(s).
            JOptionPane.showMessageDialog(null, strErrs, getStringsClass().get_error(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of JCmdLineApp
     */
    public static JCmdLineApp getApplication() {
        return Application.getInstance(JCmdLineApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(JCmdLineApp.class, args);
    }

    // this inner class cannot be static because it uses member variable(s) in JCmdLineApp
    public class CmdLineConsoleInput extends ConsoleInputStream {

        @Override
        public void doBeforeInput() {
            mnCurrentSelectedInputRecord = -1;
        }

        @Override
        public String inputString() throws InterruptedException {
            if (Thread.currentThread().isInterrupted()) {
                // make sure that there is no log output after exception.
                throw new InterruptedException();
            }
            final Object objInsureInputAfterOutput = new Object();
            synchronized (objInsureInputAfterOutput) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (objInsureInputAfterOutput) {
                            objInsureInputAfterOutput.notify(); // because in synchronized block, notify should be always after wait.
                        }
                    }

                });
                objInsureInputAfterOutput.wait();
            }

            if (Thread.currentThread().isInterrupted()) {
                // make sure that there is no log output after exception.
                throw new InterruptedException();
            }
            while (true) {
                synchronized (mobjInputSync) {
                    mobjInputSync.wait();
                }
                String strInput = mCmdLineView.getInput();
                int nReturnPosition = strInput.indexOf("\n");
                if (nReturnPosition != -1) {
                    mCmdLineView.moveEditableStart2End();    //finish this input.
                    String strReturn = strInput.substring(0, nReturnPosition);
                    for (int idx = 0; idx < mliststrInputHistory.size(); idx++) {
                        if (mliststrInputHistory.get(idx).equals(strReturn)) {
                            // this input has been in the list, so take it out
                            mliststrInputHistory.remove(idx);
                            idx--;
                        }
                    }
                    if (strReturn.length() > 0) {
                        mliststrInputHistory.add(strReturn);
                    }
                    while (mliststrInputHistory.size() > msnNumberofRecords) {
                        mliststrInputHistory.removeFirst();
                    }
                    return strReturn;
                }
            }
            //return null;    // cannot reach here.
        }

        @Override
        public void doAfterInput() {

        }
    }

    // this inner class cannot be static because it uses member variable(s) in JCmdLineApp
    public class CmdLineLogOutput extends LogOutputStream {

        @Override
        public void outputString(String str) throws InterruptedException {
            if (Thread.currentThread().isInterrupted()) {
                // make sure that there is no log output after exception.
                throw new InterruptedException();
            }
            writeLock.lockInterruptibly();
            mlistOutputStrings.addLast(str);
            writeLock.unlock();
            SwingUtilities.invokeLater(mrUpdateResults);
        }
    }

    public static class CmdLineFunctionInterrupter extends FunctionInterrupter {

        @Override
        public boolean shouldInterrupt() {
            return Thread.currentThread().isInterrupted();
        }

        @Override
        public void interrupt() throws InterruptedException {
            throw new InterruptedException();
        }

    }

    public static class CmdLineScriptInterrupter extends ScriptInterrupter {

        @Override
        public boolean shouldInterrupt() {
            return Thread.currentThread().isInterrupted();
        }

        @Override
        public void interrupt() throws InterruptedException {
            throw new InterruptedException();
        }

    }

    public static class CmdLineAExprInterrupter extends AbstractExprInterrupter {

        @Override
        public boolean shouldInterrupt() {
            return Thread.currentThread().isInterrupted();
        }

        @Override
        public void interrupt() throws InterruptedException {
            throw new InterruptedException();
        }

    }

    public static class MFPFileOperator extends FileOperator {

        @Override
        public boolean outputGraphFile(String strFileName, String strFileContent) throws IOException {
            String strChartFolderPath = mMFP4JavaFileMan.getChartFolderFullPath();
            int nLastDivIdx = strFileName.lastIndexOf(LangFileManager.STRING_PATH_DIVISOR);
            File folder;
            if (nLastDivIdx > 0) {
                folder = new File(strChartFolderPath + LangFileManager.STRING_PATH_DIVISOR
                        + strFileName.substring(0, nLastDivIdx));
            } else {
                folder = new File(strChartFolderPath + LangFileManager.STRING_PATH_DIVISOR);
            }
            File file = new File(strChartFolderPath + LangFileManager.STRING_PATH_DIVISOR
                    + strFileName + LangFileManager.STRING_CHART_EXTENSION);
            folder.mkdirs();
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            osw.write(strFileContent);
            osw.flush();
            osw.close();
            return true;
        }

    }

    protected static final String STRING_SETTINGS_FILE = "settings.info";
    protected static final String STRING_JSON_SETTINGS_FILE = "settings.json";

    public static int msnNumberofRecords = 20;
    protected int mnCurrentSelectedRecord = -1;
    protected int mnCurrentSelectedInputRecord = -1;

    protected String mstr2BProcessedInput = "";    // to be processed input
    // Create runnable for posting
    protected Runnable mrUpdateResults = new Runnable() {
        public void run() {
            mCmdLineView.outputString2TxtArea(null);
        }
    };

    protected LinkedList<JMCmdRecord> mlistCmdHistory = new LinkedList<JMCmdRecord>();
    protected LinkedList<String> mliststrInputHistory = new LinkedList<String>();
    protected Thread mthreadCmd = null;

    public boolean isIdle() {
        return (mthreadCmd == null);
    }

    public boolean isWaiting() {
        return (mthreadCmd != null && mthreadCmd.getState() == Thread.State.WAITING); // timed waiting is not considered coz we dont want paste during sleep function.
    }

    public final Object mobjInputSync = new Object();
    public LinkedList<String> mlistOutputStrings = new LinkedList<String>();
    protected ReentrantReadWriteLock readwriteLock = new ReentrantReadWriteLock();
    public Lock writeLock = readwriteLock.writeLock();

    protected JCmdLineView mCmdLineView = null;
    protected JIntegrationInput mIntegrationInput = null;
    protected JDerivativeInput mDerivativeInput = null;
    protected JMultiXYChartDef mMultiXYChartDef = null;
    protected JMultiRangleChartDef mMultiRangleChartDef = null;
    protected JMultiXYZChartDef mMultiXYZChartDef = null;
    protected JApkGenInput mApkGenInput = null;
    protected JDialogSettings mdlgSettings = null;

    public class JMCmdRecord {

        public String mstrCmd = "";
        public String mstrReturn = "";
    }

    public static void initFuncEvaluatorMgrs(LogOutputStream cmdlineLogOutput, ConsoleInputStream cmdLineConsoleInput) {
        if (FuncEvaluator.msstreamLogOutput == null) {
            FuncEvaluator.msstreamLogOutput = cmdlineLogOutput;
        }
        if (FuncEvaluator.msstreamConsoleInput == null) {
            FuncEvaluator.msstreamConsoleInput = cmdLineConsoleInput;
        }
        if (FuncEvaluator.msfunctionInterrupter == null) {
            FuncEvaluator.msfunctionInterrupter = new CmdLineFunctionInterrupter();
        }
        if (FuncEvaluator.msfileOperator == null) {
            FuncEvaluator.msfileOperator = new MFPFileOperator();
        }
        if (FuncEvaluator.msgraphPlotter == null) {
            FuncEvaluator.msgraphPlotter = new ChartOperator.ChartOperatorPlotter();
        }
        if (FuncEvaluator.msgraphPlotter3D == null) {
            FuncEvaluator.msgraphPlotter3D = new ChartOperator.ChartOperatorPlotter();
        }
        if (FuncEvaluator.msGDIMgr == null) {
            FuncEvaluator.msGDIMgr = new FlatGDIManager();
        }
        if (FuncEvaluator.msMultimediaMgr == null) {
            FuncEvaluator.msMultimediaMgr = new MultimediaManager(
                    new ImageMgrJava(),
                    new SoundMgrJava()
            );
        }
        /*if (FuncEvaluator.msPlatformHWMgr == null) {
            FuncEvaluator.msPlatformHWMgr = new PlatformHWManager(
                    JCmdLineApp.mMFP4JavaFileMan
            );
        }*/  // msPlatformHWMgr has to be initialized early as it is needed to analyze anotations when loading lib.
        if (FuncEvaluator.mspm == null) {
            FuncEvaluator.mspm = new PatternManager();
            try {
                FuncEvaluator.mspm.loadPatterns(2); // load all integration patterns. Assume load patterns will not throw any exceptions.
            } catch (JSmartMathErrException ex) {
                Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JFCALCExpErrException ex) {
                Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (FuncEvaluator.msCommMgr == null) {
            FuncEvaluator.msCommMgr = new MFP4JavaCommMan();
        }
        if (FuncEvaluator.msRtcMMediaManager == null) {
            FuncEvaluator.msRtcMMediaManager = new JavaRtcMMediaMan();
        }
        if (ScriptAnalyzer.msscriptInterrupter == null) {
            ScriptAnalyzer.msscriptInterrupter = new CmdLineScriptInterrupter();
        }
        if (AbstractExpr.msaexprInterrupter == null) {
            AbstractExpr.msaexprInterrupter = new CmdLineAExprInterrupter();
        }
    }
        
    public void processInput(String strInput) {
        if (isIdle()) {   // ensure that mthreadCmd has finished
            mstr2BProcessedInput = strInput;
            mthreadCmd = new Thread(new Runnable() {
                public void run() {
                    initFuncEvaluatorMgrs(new CmdLineLogOutput(), new CmdLineConsoleInput());
                    try {
                        String[] strlistInputs = mstr2BProcessedInput.split("\\n");
                        if (strlistInputs.length == 0) {
                            strlistInputs = new String[1];
                            strlistInputs[0] = "";
                        }
                        if (strlistInputs.length <= 1) {   // only one real command in the input session or no command at all.
                            processIndividualInput(strlistInputs[0], 1);    // individual input is always the first line
                        } else {   //several commands in the input session.
                            processMultipleInputs(mstr2BProcessedInput, strlistInputs);
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Thread receive exception : " + e.toString());
                    }
                    mstr2BProcessedInput = "";
                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            /* use message to tell the main thread that I finish.
                             * Do not set mthreadCmd directly coz there might be some
                             * delayed output message which changes GUI after directly
                             * set mthreadCmd to null.
                             */
                            mCmdLineView.outputPrompt2TxtArea();
                            mthreadCmd = null;
                            // now the state is idle again, so need to changeCalcThreadIdleState
                            changeCalcThreadIdleState();
                        }

                    };
                    SwingUtilities.invokeLater(r);
                }
            });
            // now the calculation thread is not null, we can call changeCalcThreadIdle State()
            changeCalcThreadIdleState();
            mthreadCmd.start();
        }
    }

    public void processMultipleInputs(String strOriginalInput, String[] strlistInputSession) throws InterruptedException {
        // first step: scan strlistInputSession to find out if it is a session or it is a lib.
        String firstStatement = "", strReturn = "", strOutput = "";
        try {
            Boolean isInHelp = false;
            for (int idx = 0; idx < strlistInputSession.length; idx ++) {
                if (strlistInputSession[idx].matches("\\s*") || strlistInputSession[idx].matches("\\s*\\/\\/.*")) {
                    continue;   // if it is an empty line or a line starting with //
                } else if (strlistInputSession[idx].matches("\\s*(?i)help(?-i)\\s+.*")) {
                    isInHelp = true;
                } else if (strlistInputSession[idx].matches("\\s*(?i)endh(?-i)\\s+.*")) {
                    isInHelp = false;
                } else if (!isInHelp) {
                    if (strlistInputSession[idx].matches("\\s*(?i)public(?-i)\\s+.*")
                            || strlistInputSession[idx].matches("\\s*(?i)private(?-i)\\s+.*")) {
                        ErrorProcessor.ERRORTYPES e = ErrorProcessor.ERRORTYPES.ACCESS_KEYWORD_CANNOT_BE_HERE;   // public and private keywords can only be applied to function and variable
                        throw new JMFPCompErrException("", idx, idx, e);
                    } else if (strlistInputSession[idx].matches("\\s*(?i)citingspace(?-i)\\s+.*")) {
                        firstStatement = "citingspace";
                        break;
                    } else if (strlistInputSession[idx].matches("\\s*(?i)class(?-i)\\s+.*")) {
                        firstStatement = "class";
                        break;
                    } else if (strlistInputSession[idx].matches("\\s*(?i)function(?-i)\\s+.*")) {
                        firstStatement = "function";
                        break;
                    } else {
                        firstStatement = "";
                        break;
                    }
                }
            }
            if (!firstStatement.equals("")) { // this is a not session.
                MFPAdapter.loadLibCodeString(strlistInputSession, "", new String[] {"\u0000" + LangFileManager.STRING_COMMAND_INPUT_FILE_PATH});
            } else {    // this is a session.
                try {
                    FunctionEntry fe = MFPAdapter.loadSession(strlistInputSession);
                    fe.getStatementFunction().m_lCitingSpaces.addAll(mshellManager.getCitingSpaces());
                    for (Statement s : fe.getStatementLines()) {
                        s.analyze2(fe);     // analyse statement to use aexpr to replace string.
                    }
                    new CmdLineLogOutput().outputString("......" + getStringsClass().get_session_starts() + "......\n");
                    // Different from a function, an input session should not be able to read namespaces outside.
                    LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
                    lVarNameSpaces.add(mlCmdLineLocalVars);
                    ProgContext progContext = new ProgContext();
                    progContext.mstaticProgContext.setCallingFunc(fe.getStatementFunction());
                    progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;
                    InFunctionCSManager inFuncCSMgr = new InFunctionCSManager(progContext.mstaticProgContext);
                    ScriptAnalyzer sa = new ScriptAnalyzer();
                    sa.analyzeBlock(fe.getStatementLines(), fe.getStartStatementPos(), new LinkedList<Variable>(), inFuncCSMgr, progContext);
                } catch (FuncRetException e) {
                    String strarrayAnswer[] = new String[2];
                    if (e.m_datumReturn != null) {
                        try {
                            strarrayAnswer = MFPAdapter.outputDatum(e.m_datumReturn);
                            strReturn = strarrayAnswer[0];
                            strOutput = getStringsClass().get_session_returns() + " " + strarrayAnswer[1] + "\n";
                            mvarAns.setValue(e.m_datumReturn);  // assign answer to "ans" variable.
                        } catch (JFCALCExpErrException ex) {
                            strReturn = "Error";
                            strOutput = MFPAdapter.outputException(ex);
                        }
                    } else {
                        strReturn = "returns nothing";
                        strOutput = "";
                    }
                } catch (ScriptStatementException e) {
                    strReturn = "Error";
                    strOutput = MFPAdapter.outputException(e);
                }
            }
        } catch (JMFPCompErrException e) {
            strReturn = "Error";
            strOutput = MFPAdapter.outputException(e);
        } catch (Exception e) {
            // unexcepted exception
            strReturn = "Error";
            strOutput = MFPAdapter.outputException(e);
        }
        
        outputMultiLineResult(strOriginalInput, strReturn, strOutput);
    }

    public void outputMultiLineResult(String strOriginalInput, String strReturn, String strOutput) throws InterruptedException {
        JMCmdRecord record = new JMCmdRecord();
        String strOriginalInputTrimBreakLn = strOriginalInput;
        if (strOriginalInput.endsWith("\n"))
        {
            // there will be an extra \n appended to strOriginalInput, so trim last \n.
            strOriginalInputTrimBreakLn = strOriginalInput.substring(0, strOriginalInput.length() - 1);
        }
        record.mstrCmd = strOriginalInputTrimBreakLn;
        record.mstrReturn = strReturn;
        if (mlistCmdHistory.size() > msnNumberofRecords) {
            mlistCmdHistory.removeFirst();
        }
        mlistCmdHistory.addLast(record);
        mnCurrentSelectedRecord = -1;
        new CmdLineLogOutput().outputString(strOutput + "\n");
    }

    public void processIndividualInput(String strInput, int nLine) throws InterruptedException {
        String strOutput = processCmd(strInput, nLine);
        new CmdLineLogOutput().outputString(strOutput);
    }

    public String processCmd(String strCmd, int nLine) throws InterruptedException {
        if (strCmd.trim().equals("")) {
            // empty cmd
            return "";
        } else {
            String strReturn, strOutput;
            if (strCmd.trim().equalsIgnoreCase("quit")) {    // no parameter. (so that no confusion with future quit())
                strReturn = "returns nothing";
                strOutput = mshellManager.Interpreter(ShellManager.SHELL_MANAGER_COMMAND + " " + ShellManager.QUIT);
            } else if (strCmd.trim().split("\\s+").length > 0
                    && strCmd.trim().split("\\s+")[0].trim().equalsIgnoreCase("help")) {
                strReturn = "returns nothing";
                strOutput = quickhelp(strCmd);
            } else if (strCmd.trim().split("\\s+").length > 0
                    && strCmd.trim().split("\\s+")[0].trim().equalsIgnoreCase(ShellManager.SHELL_MANAGER_COMMAND)) {
                strReturn = "returns nothing";
                strOutput = mshellManager.Interpreter(strCmd);
            } else {
                Statement sCmd = new Statement(strCmd, "", nLine);  // because it is in command line, line number is 1.
                try {
                    sCmd.analyze();
                    if (sCmd.mstatementType.getType().equals(Statement_using.getTypeStr())) {
                        Statement_using su = (Statement_using) sCmd.mstatementType;
                        String strNewCmd = ShellManager.SHELL_MANAGER_COMMAND + " "
                                + ShellManager.ADD_CITINGSPACE + " "
                                + su.getFullCS();
                        strReturn = "returns nothing";
                        strOutput = mshellManager.Interpreter(strNewCmd);
                    } else if (sCmd.mstatementType.getType().equals(Statement_variable.getTypeStr())) {
                        LinkedList<Variable> listVar = new LinkedList<>();
                        Statement_variable sv = (Statement_variable) (sCmd.mstatementType);
                        for (int index1 = 0; index1 < sv.mstrVariables.length; index1++) {
                            String strVarName = sv.mstrVariables[index1];
                            if (VariableOperator.lookUpList(strVarName, mlCmdLineLocalVars) != null) {
                                ERRORTYPES e = ERRORTYPES.REDEFINED_VARIABLE;
                                throw new JMFPCompErrException(sCmd.mstrFilePath, sCmd.mnStartLineNo, sCmd.mnEndLineNo, e);   // this is not a mfps file, so file path should be "".                                             
                            }
                            // default value for a variable is null.
                            Variable var = new Variable(sv.mstrVariables[index1], new DataClassNull());
                            listVar.add(var);
                        }
                        // after verify all the variables, then assign initial values and add them into namespace.
                        // clear variable name spaces
                        LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
                        lVarNameSpaces.add(mlCmdLineLocalVars);
                        ProgContext progContext = new ProgContext();
                        progContext.mstaticProgContext.setCitingSpacesExplicitly(mshellManager.getCitingSpaces());
                        progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;
                        ExprEvaluator exprEvaluator = new ExprEvaluator(progContext);
                        String strVariableNames = "";
                        for (int index1 = 0; index1 < listVar.size(); index1++) {
                            if (sv.mstrVarValues[index1].length() != 0) {
                                CurPos c = new CurPos();
                                c.m_nPos = 0;
                                DataClass datumReturn;
                                try {
                                    datumReturn = exprEvaluator.evaluateExpression(sv.mstrVarValues[index1], c);
                                    if (datumReturn == null) {
                                        ERRORTYPES errType = ERRORTYPES.NO_VALUE_OBTAINED_FROM_EXPRESSION;
                                        throw new JMFPCompErrException(sCmd.mstrFilePath, sCmd.mnStartLineNo, sCmd.mnEndLineNo, errType);    // this is not a mfps file, so file path should be "".
                                    }
                                    DataClass datumNewValue = ExprEvaluator.evaluateTwoOperandCell(listVar.get(index1).getValue(),
                                            new CalculateOperator(OPERATORTYPES.OPERATOR_ASSIGN, 2),
                                            datumReturn);
                                    listVar.get(index1).setValue(datumNewValue);
                                } catch (JFCALCExpErrException e) {
                                    for (int idx2 = 0; idx2 < index1; idx2++) {
                                        mlCmdLineLocalVars.removeLast();    // if here is last
                                    }
                                    ERRORTYPES errType = ERRORTYPES.INVALID_EXPRESSION;
                                    throw new JMFPCompErrException(sCmd.mstrFilePath, sCmd.mnStartLineNo, sCmd.mnEndLineNo, errType, e); // this is not a mfps file, so file path should be "".
                                }
                            }
                            mlCmdLineLocalVars.addLast(listVar.get(index1));
                            strVariableNames += listVar.get(index1).getName();
                            strVariableNames += "(=";
                            strVariableNames += MFPAdapter.outputDatum(listVar.get(index1).getValue())[1];
                            if (index1 < listVar.size() - 1) {
                                strVariableNames += "), ";
                            } else {
                                strVariableNames += ")";
                            }
                        }
                        strReturn = "returns nothing";
                        strOutput = getStringsClass().get_variables_declared_shown() + strVariableNames;
                    } else {   // normal expression
                        // clear variable name spaces
                        LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
                        lVarNameSpaces.add(mlCmdLineLocalVars);
                        ProgContext progContext = new ProgContext();
                        progContext.mstaticProgContext.setCitingSpacesExplicitly(mshellManager.getCitingSpaces());
                        progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;
                        ExprEvaluator exprEvaluator = new ExprEvaluator(progContext);

                        /* evaluate the expression */
                        CurPos curpos = new CurPos();
                        curpos.m_nPos = 0;
                        DataClass datumAnswer = null;
                        String strarrayAnswer[] = new String[2];
                        datumAnswer = exprEvaluator.evaluateExpression(strCmd, curpos);
                        if (datumAnswer != null) {
                            strarrayAnswer = MFPAdapter.outputDatum(datumAnswer);
                            strReturn = strarrayAnswer[0];
                            mvarAns.setValue(datumAnswer);  // assign answer to "ans" variable.
                        } else {
                            strReturn = "returns nothing";
                        }
                        /*strOutput = strCmd
                                + ((datumAnswer == null)?(" " + getStringsClass().get_return_nothing_answer_shown()):("\n= " + strarrayAnswer[1]))
                                + "\n";*/
                        strOutput = ((datumAnswer == null) ? ("") : (strCmd + "\n= " + strarrayAnswer[1]))
                                + "\n";
                    }
                } catch (JMFPCompErrException e) {
                    strReturn = "Error";
                    strOutput = MFPAdapter.outputException(e);
                } catch (JFCALCExpErrException e) {
                    strReturn = "Error";
                    strOutput = MFPAdapter.outputException(e);
                }
            }
            JMCmdRecord record = new JMCmdRecord();
            record.mstrCmd = strCmd;
            record.mstrReturn = strReturn;
            if (mlistCmdHistory.size() > msnNumberofRecords) {
                mlistCmdHistory.removeFirst();
            }
            mlistCmdHistory.addLast(record);
            mnCurrentSelectedRecord = -1;
            return strOutput + "\n";
        }
    }

    public void interruptCmd() {
        // this function terminate a running cmd.
        if (!isIdle()) {
            if (mthreadCmd.isAlive()) {
                mthreadCmd.interrupt(); // do not use threadgroup because the group may include other application threads.
            }
            mthreadCmd = null;
            changeCalcThreadIdleState();
        }
    }

    public static String getLocalLanguage() {
        Locale l = Locale.getDefault();
        String strLanguage = l.getLanguage();
        if (strLanguage.equals(new Locale("en", "", "").getLanguage())) {
            return "English";
        } else if (strLanguage.equals(new Locale("en", "", "").getLanguage())) {
            return "French";
        } else if (strLanguage.equals(new Locale("de", "", "").getLanguage())) {
            return "German";
        } else if (strLanguage.equals(new Locale("it", "", "").getLanguage())) {
            return "Itanian";
        } else if (strLanguage.equals(new Locale("ja", "", "").getLanguage())) {
            return "Japanese";
        } else if (strLanguage.equals(new Locale("ko", "", "").getLanguage())) {
            return "Korean";
        } else if (strLanguage.equals(new Locale("es", "", "").getLanguage())) {
            return "Spanish";
        } else if (strLanguage.equals(new Locale("zh", "", "").getLanguage())) {
            if (l.getCountry().equals("TW") || l.getCountry().equals("HK")) {
                return "Traditional_Chinese";
            } else {
                return "Simplified_Chinese";
            }
        } else {
            return "";    // unknown language
        }
    }

    public static JCmdLineStrings getStringsClass() {
        String strLanguage = getLocalLanguage();
        if (strLanguage.equals("English")) {
            return new JCmdLineStrings();
        } else if (strLanguage.equals("Spanish")) {
            return new JCmdLineStringsSpanish();
        } else if (strLanguage.equals("Simplified_Chinese")) {
            return new JCmdLineStringsSChinese();
        } else if (strLanguage.equals("Traditional_Chinese")) {
            return new JCmdLineStringsTChinese();
        } else {
            return new JCmdLineStrings();
        }
    }

    public String quickhelp(String strExpression) {
        String strOutput = "";
        strExpression = strExpression.trim();
        if ((strExpression.length() >= 5 && strExpression.substring(0, 5).toLowerCase(Locale.US).equals("help ") == false)
                || (strExpression.length() == 4 && strExpression.substring(0, 4).toLowerCase(Locale.US).equals("help") == false)
                || (strExpression.length() < 4)) {
            strOutput = getStringsClass().get_error_in_analyzing_help_requirement();
        } else if (strExpression.toLowerCase(Locale.US).trim().equals("help")) {
            strOutput = getStringsClass().get_cmd_line_welcome_message() + "\n";
        } else {
            // the length of the string must be larger than 4 now.
            String strHelpReq = strExpression.substring(4).trim().toLowerCase(Locale.US);

            if (strHelpReq.equals("")) {
                strOutput = getStringsClass().get_cmd_line_welcome_message();
            } else if (strHelpReq.equals("=")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_assign_help_info();
            } else if (strHelpReq.equals("==")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_equal_help_info();
            } else if (strHelpReq.equals("(")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_parenthesis_help_info();
            } else if (strHelpReq.equals(")")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_closeparenthesis_help_info();
            } else if (strHelpReq.equals("[")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_squarebracket_help_info();
            } else if (strHelpReq.equals("]")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_closesquarebracket_help_info();
            } else if (strHelpReq.equals(",")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_comma_help_info();
            } else if (strHelpReq.equals("+")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_plus_help_info();
            } else if (strHelpReq.equals("-")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_minus_help_info();
            } else if (strHelpReq.equals("*")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_multiplication_help_info();
            } else if (strHelpReq.equals("/")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_division_help_info();
            } else if (strHelpReq.equals("\\")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_leftdivision_help_info();
            } else if (strHelpReq.equals("**")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_power_help_info();
            } else if (strHelpReq.equals("'")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_transpose_help_info();
            } else if (strHelpReq.equals("\"")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_doublequote_help_info();
            } else if (strHelpReq.equals("!")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_exclaimation_help_info();
            } else if (strHelpReq.equals("%")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_percentage_help_info();
            } else if (strHelpReq.equals("&")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_bit_and_help_info();
            } else if (strHelpReq.equals("|")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_bit_or_help_info();
            } else if (strHelpReq.equals("^")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_bit_xor_help_info();
            } else if (strHelpReq.equals("~")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_bit_not_help_info();
            } else if (strHelpReq.equals("i")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_image_i_help_info();
            } else if (strHelpReq.equals("pi")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_pi_constant_help_info();
            } else if (strHelpReq.equals("e")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_e_constant_help_info();
            } else if (strHelpReq.equals("null")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_null_constant_help_info();
            } else if (strHelpReq.equals("true")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_true_constant_help_info();
            } else if (strHelpReq.equals("false")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_false_constant_help_info();
            } else if (strHelpReq.equals("inf")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_inf_constant_help_info();
            } else if (strHelpReq.equals("infi")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_infi_constant_help_info();
            } else if (strHelpReq.equals("nan")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_nan_constant_help_info();
            } else if (strHelpReq.equals("nani")) {
                strOutput = strHelpReq + " : " + getStringsClass().get_nani_constant_help_info();
            } else if ((strOutput = MFPAdapter.getMFPKeyWordHelp(strHelpReq, getLocalLanguage())) != null) {
                // is key word.
            } else {
                strOutput = "";
                String[] strHelpReqParts = strHelpReq.split("\\(");
                if (strHelpReqParts.length == 2) {
                    boolean bRightFormat = true;
                    String strFuncName = strHelpReqParts[0].trim();
                    String strNumofParams = "";
                    int nNumofParams = 0;
                    boolean bIncludeOptionParam = false;
                    if (strHelpReqParts[1].trim().matches("[0-9]*\\s*\\.\\.\\.\\s*\\)")) {
                        strNumofParams = strHelpReqParts[1].split("\\.\\.\\.")[0].trim();
                        bIncludeOptionParam = true;
                    } else if (strHelpReqParts[1].trim().matches("[0-9]*\\s*\\)")) {
                        String[] strParts = strHelpReqParts[1].split("\\)");
                        if (strParts.length > 0) {   // if it is ")", then strParts.length = 0
                            strNumofParams = strParts[0].trim();
                        } else {
                            strNumofParams = "";
                        }
                    } else {
                        bRightFormat = false;
                    }
                    if (strNumofParams.trim().equals("")) {
                        nNumofParams = 0;
                    } else {
                        try {
                            nNumofParams = Integer.parseInt(strNumofParams.trim());
                        } catch (NumberFormatException e) {
                            bRightFormat = false;
                        }
                    }
                    if (bRightFormat) {
                        try {
                            // make it shrinked name
                            strFuncName = ElemAnalyzer.getShrinkedFuncNameStr(strFuncName, new CurPos(), "");
                        } catch (JFCALCExpErrException ex) {
                            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        strOutput = MFPAdapter.getFunctionHelp(strFuncName,
                                nNumofParams,
                                bIncludeOptionParam,
                                getLocalLanguage(),
                                mshellManager.getCitingSpaces());
                    }
                    if (strOutput == null || strOutput.trim().equals("")) {
                        strOutput = getStringsClass().get_no_quick_help_info() + " " + strHelpReq;
                    }
                } else if (strHelpReqParts.length == 1) {
                    try {
                        // make it shrinked name
                        strHelpReq = ElemAnalyzer.getShrinkedFuncNameStr(strHelpReq, new CurPos(), "");
                    } catch (JFCALCExpErrException ex) {
                        Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    strOutput = MFPAdapter.getFunctionHelp(strHelpReq, getLocalLanguage(), mshellManager.getCitingSpaces());
                    if (strOutput.equals("")) {
                        strOutput = getStringsClass().get_no_quick_help_info() + " " + strHelpReq;
                    }
                }
            }
        }
        return strOutput;
    }

    public JMCmdRecord getLastCommandInfo() {
        if (isIdle() && mlistCmdHistory.size() > 0) {
            if (mnCurrentSelectedRecord == -1) {
                mnCurrentSelectedRecord = mlistCmdHistory.size() - 1;
            } else if (mnCurrentSelectedRecord == 0) {
                mnCurrentSelectedRecord = mlistCmdHistory.size() - 1;
            } else {
                mnCurrentSelectedRecord--;
                if (mnCurrentSelectedRecord >= mlistCmdHistory.size()) {
                    mnCurrentSelectedRecord = 0;
                }
            }
            return mlistCmdHistory.get(mnCurrentSelectedRecord);
        }
        return null;
    }

    public JMCmdRecord getNextCommandInfo() {
        if (isIdle() && mlistCmdHistory.size() > 0) {
            mnCurrentSelectedRecord++;
            if (mnCurrentSelectedRecord >= mlistCmdHistory.size()) {
                mnCurrentSelectedRecord = 0;
            }
            return mlistCmdHistory.get(mnCurrentSelectedRecord);
        }
        return null;

    }

    public String getLastInput() {
        if (isWaiting() && mliststrInputHistory.size() > 0) {
            if (mnCurrentSelectedInputRecord == -1) {
                mnCurrentSelectedInputRecord = mliststrInputHistory.size() - 1;
            } else if (mnCurrentSelectedInputRecord == 0) {
                mnCurrentSelectedInputRecord = mliststrInputHistory.size() - 1;
            } else {
                mnCurrentSelectedInputRecord--;
                if (mnCurrentSelectedInputRecord >= mliststrInputHistory.size()) {
                    mnCurrentSelectedInputRecord = 0;
                }
            }
            return mliststrInputHistory.get(mnCurrentSelectedInputRecord);
        }
        return null;
    }

    public String getNextInput() {
        if (isWaiting() && mliststrInputHistory.size() > 0) {
            mnCurrentSelectedInputRecord++;
            if (mnCurrentSelectedInputRecord >= mliststrInputHistory.size()) {
                mnCurrentSelectedInputRecord = 0;
            }
            return mliststrInputHistory.get(mnCurrentSelectedInputRecord);
        }
        return null;

    }

    public void showIntegrationInput() {
        if (mIntegrationInput == null) {
            mIntegrationInput = new JIntegrationInput(getMainFrame());
            mIntegrationInput.setLocationRelativeTo(getMainFrame());
        }
        show(mIntegrationInput);
    }

    public void showDerivativeInput() {
        if (mDerivativeInput == null) {
            mDerivativeInput = new JDerivativeInput(getMainFrame());
            mDerivativeInput.setLocationRelativeTo(getMainFrame());
        }
        show(mDerivativeInput);
    }

    public void showChartDef(String strChartType) {
        if (strChartType.equalsIgnoreCase("multixy")) {
            if (mMultiXYChartDef == null) {
                mMultiXYChartDef = new JMultiXYChartDef(getMainFrame(), false);
                mMultiXYChartDef.setLocationRelativeTo(getMainFrame());
            }
            show(mMultiXYChartDef);
        } else if (strChartType.equalsIgnoreCase("multiRangle")) {
            if (mMultiRangleChartDef == null) {
                mMultiRangleChartDef = new JMultiRangleChartDef(getMainFrame(), false);
                mMultiRangleChartDef.setLocationRelativeTo(getMainFrame());
            }
            show(mMultiRangleChartDef);
        } else if (strChartType.equalsIgnoreCase("multixyz")) {
            if (mMultiXYZChartDef == null) {
                mMultiXYZChartDef = new JMultiXYZChartDef(getMainFrame(), false);
                mMultiXYZChartDef.setLocationRelativeTo(getMainFrame());
            }
            show(mMultiXYZChartDef);
        }
    }

    public void showApkGenInput() {
        mApkGenInput = new JApkGenInput(getMainFrame(), true);
        show(mApkGenInput);
    }

    public void showSettingsDlg() {
        mdlgSettings = new JDialogSettings(getMainFrame(), true);
        show(mdlgSettings);
    }

    public static String getSettingsFileFullPath() {
        return mMFP4JavaFileMan.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR
                + STRING_SETTINGS_FILE;
    }

    public static String getJsonSettingsFileFullPath() {
        return mMFP4JavaFileMan.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR
                + STRING_JSON_SETTINGS_FILE;
    }

    /**
     * write settings back to Json file.
     *
     * @param strSettings
     * @return true if write successfully, false otherwise.
     */
    public boolean writeJsonSettings() {
        JSONObject obj = new JSONObject();
        obj.put("HISTORICAL_RECORD_LENGTH", "" + msnNumberofRecords);
        obj.put("CHART_FOLDER_PATH", "" + MFP4JavaFileMan.msstrOriginalChartPath);
        obj.put("SCRIPT_FOLDER_PATH", "" + MFP4JavaFileMan.msstrOriginalScriptPath);
        obj.put("BITS_OF_PRECISION", "" + MFPAdapter.msnBitsofPrecision);
        obj.put("SCIENTIFIC_NOTATION_THRESHOLD", "" + MFPAdapter.msnBigSmallThresh);
        obj.put("PLOT_EXPRS_VARIABLE_FROM", "" + MFPAdapter.msdPlotChartVariableFrom);
        obj.put("PLOT_EXPRS_VARIABLE_TO", "" + MFPAdapter.msdPlotChartVariableTo);

        JSONArray additionalUsrLibs = new JSONArray();
        for (int idx = 0; idx < MFP4JavaFileMan.moriginaladdUsrLibs.size(); idx++) {
            JSONObject lib = new JSONObject();
            lib.put("LIB_PATH", MFP4JavaFileMan.moriginaladdUsrLibs.get(idx));
            additionalUsrLibs.add(lib);
        }

        obj.put("ADDITIONAL_USER_LIBS", additionalUsrLibs);
        try {
            File fSettings = new File(getJsonSettingsFileFullPath());
            if (!fSettings.isFile()) {
                fSettings.createNewFile();
            }
            if (fSettings.isFile()) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fSettings), "UTF-8"
                ));
                out.write(obj.toJSONString());
                out.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getProgramFolder() {
        // get jar file path
        String strJarPath = JCmdLineApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            strJarPath = URLDecoder.decode(strJarPath, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // do not decode strJarPath.
        }
        // in windows, to lower case is OK. But cannot do this in Linux.
        // so the standard is like this:
        // the path of app folder is case sensative (e.g. "C:\JCmdLine\Build")
        // however, a sub folder inside app folder is case insensative.
        return new File(strJarPath).getParentFile().getPath();//.toLowerCase(Locale.US);
    }

    protected String convt2AbsCanPath(String path) {
        String strAbsPath = path;
        if (!IOLib.isAbsolutePath(path)) {
            // convert a relative path to absolute path
            strAbsPath = mMFP4JavaFileMan.getAppBaseFullPath()
                    + LangFileManager.STRING_PATH_DIVISOR
                    + path;
        }
        String strCanPath;
        try {
            strCanPath = IOLib.getCanonicalPath(strAbsPath).trim();
        } catch (JFCALCExpErrException ex) {
            strCanPath = strAbsPath.trim();
        }
        while (strCanPath.length() >= LangFileManager.STRING_PATH_DIVISOR.length()
                && strCanPath.substring(strCanPath.length() - LangFileManager.STRING_PATH_DIVISOR.length())
                        .equals(LangFileManager.STRING_PATH_DIVISOR)) {
            // remove the last path divisor if there is
            strCanPath = strCanPath.substring(0, LangFileManager.msstrScriptFolder.length()
                    - LangFileManager.STRING_PATH_DIVISOR.length());
        }
        return strCanPath;
    }

    public int loadSettings() {
        FileInputStream fis = null;
        BufferedReader br = null;
        int loadingFailed = 0;  // 0 means successful, 1 means no settings file found, 2... means other errors.
        try {
            fis = new FileInputStream(getSettingsFileFullPath());
            br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String strLine = br.readLine();
            while (strLine != null) {
                String[] strarraySetting = strLine.split(":=");
                if (strarraySetting.length > 1) {
                    String strItem = strarraySetting[0].trim();
                    if (strItem.equalsIgnoreCase("SCRIPT_FOLDER_PATH")) {
                        MFP4JavaFileMan.msstrOriginalScriptPath = LangFileManager.msstrScriptFolder = strarraySetting[1].trim();
                        LangFileManager.msstrScriptFolder = convt2AbsCanPath(LangFileManager.msstrScriptFolder);
                    } else if (strItem.equalsIgnoreCase("CHART_FOLDER_PATH")) {
                        MFP4JavaFileMan.msstrOriginalChartPath = LangFileManager.msstrChartFolder = strarraySetting[1].trim();
                        LangFileManager.msstrChartFolder = convt2AbsCanPath(LangFileManager.msstrChartFolder);
                    } else if (strItem.equalsIgnoreCase("HISTORICAL_RECORD_LENGTH")) {
                        String strRecLen = strarraySetting[1].trim();
                        try {
                            msnNumberofRecords = Integer.parseInt(strRecLen);
                            if (msnNumberofRecords <= 10) {
                                msnNumberofRecords = 10;
                            } else if (msnNumberofRecords <= 20) {
                                msnNumberofRecords = 20;
                            } else if (msnNumberofRecords <= 50) {
                                msnNumberofRecords = 50;
                            } else {
                                msnNumberofRecords = 100;
                            }
                        } catch (NumberFormatException e) {
                            msnNumberofRecords = 20;
                        }
                    } else if (strItem.equalsIgnoreCase("BITS_OF_PRECISION")) {
                        String strBitsOfPrecision = strarraySetting[1].trim();
                        try {
                            MFPAdapter.msnBitsofPrecision = Integer.parseInt(strBitsOfPrecision);
                            if (MFPAdapter.msnBitsofPrecision < 0) {
                                MFPAdapter.msnBitsofPrecision = 8;  // default is 8.
                            }
                        } catch (NumberFormatException e) {
                            MFPAdapter.msnBitsofPrecision = 8;
                        }
                    } else if (strItem.equalsIgnoreCase("SCIENTIFIC_NOTATION_THRESHOLD")) {
                        String strBigSmallThresh = strarraySetting[1].trim();
                        try {
                            MFPAdapter.msnBigSmallThresh = Integer.parseInt(strBigSmallThresh);
                            if (MFPAdapter.msnBigSmallThresh < 0) {
                                MFPAdapter.msnBigSmallThresh = 8;
                            }
                        } catch (NumberFormatException e) {
                            MFPAdapter.msnBigSmallThresh = 8;  // default is 8.
                        }
                        MFPAdapter.mmfpNumBigThresh = MFPNumeric.pow(MFPNumeric.TEN, new MFPNumeric(MFPAdapter.msnBigSmallThresh));
                        MFPAdapter.mmfpNumSmallThresh = MFPNumeric.pow(MFPNumeric.ONE_TENTH, new MFPNumeric(MFPAdapter.msnBigSmallThresh));
                    } else if (strItem.equalsIgnoreCase("PLOT_EXPRS_VARIABLE_FROM")) {
                        String strVariableFrom = strarraySetting[1].trim();
                        try {
                            MFPAdapter.msdPlotChartVariableFrom = Double.parseDouble(strVariableFrom);
                        } catch (NumberFormatException e) {
                            MFPAdapter.msdPlotChartVariableFrom = -5.0;
                        }
                    } else if (strItem.equalsIgnoreCase("PLOT_EXPRS_VARIABLE_TO")) {
                        String strVariableTo = strarraySetting[1].trim();
                        try {
                            MFPAdapter.msdPlotChartVariableTo = Double.parseDouble(strVariableTo);
                        } catch (NumberFormatException e) {
                            MFPAdapter.msdPlotChartVariableTo = 5.0;
                        }
                    }
                }
                strLine = br.readLine();
            }
            loadingFailed = 0;
        } catch (FileNotFoundException ex) {
            // cannot open the file. Using default settings.
            loadingFailed = 1;
        } catch (IOException ex) {
            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            loadingFailed = 2;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // final verification is here.
        if (!(MFPAdapter.msdPlotChartVariableTo > MFPAdapter.msdPlotChartVariableFrom)) {
            MFPAdapter.msdPlotChartVariableFrom = -5.0;
            MFPAdapter.msdPlotChartVariableTo = 5.0;
        }
        return loadingFailed;
    }

    public int loadJsonSettings() {
        FileInputStream fis = null;
        BufferedReader br = null;
        int loadingFailed = 0;  // 0 means successful, 1 means no settings file found, 2... means other errors.
        JSONParser parser = new JSONParser();
        try {
            fis = new FileInputStream(getJsonSettingsFileFullPath());
            br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            Object obj = parser.parse(br);
            JSONObject jsonObject = (JSONObject) obj;

            String strRecLen = (String) jsonObject.get("HISTORICAL_RECORD_LENGTH");
            try {
                msnNumberofRecords = Integer.parseInt(strRecLen);
                if (msnNumberofRecords <= 10) {
                    msnNumberofRecords = 10;
                } else if (msnNumberofRecords <= 20) {
                    msnNumberofRecords = 20;
                } else if (msnNumberofRecords <= 50) {
                    msnNumberofRecords = 50;
                } else {
                    msnNumberofRecords = 100;
                }
            } catch (NumberFormatException e) {
                msnNumberofRecords = 20;
            } catch (Exception e) { // potential null exception.
                msnNumberofRecords = 20;
            }

            String strChartFolderPath = (String) jsonObject.get("CHART_FOLDER_PATH");
            if (strChartFolderPath != null) {
                MFP4JavaFileMan.msstrOriginalChartPath = LangFileManager.msstrChartFolder = strChartFolderPath.trim();
                LangFileManager.msstrChartFolder = convt2AbsCanPath(LangFileManager.msstrChartFolder);
            }

            String strScriptFolderPath = (String) jsonObject.get("SCRIPT_FOLDER_PATH");
            if (strScriptFolderPath != null) {
                MFP4JavaFileMan.msstrOriginalScriptPath = LangFileManager.msstrScriptFolder = strScriptFolderPath.trim();
                LangFileManager.msstrScriptFolder = convt2AbsCanPath(LangFileManager.msstrScriptFolder);
            }
            String strBitsOfPrecision = (String) jsonObject.get("BITS_OF_PRECISION");
            try {
                MFPAdapter.msnBitsofPrecision = Integer.parseInt(strBitsOfPrecision);
                if (MFPAdapter.msnBitsofPrecision < 0) {
                    MFPAdapter.msnBitsofPrecision = 8;  // default is 8.
                }
            } catch (NumberFormatException e) {
                MFPAdapter.msnBitsofPrecision = 8;
            } catch (Exception e) { // potential null exception.
                MFPAdapter.msnBitsofPrecision = 8;
            }

            String strBigSmallThresh = (String) jsonObject.get("SCIENTIFIC_NOTATION_THRESHOLD");
            try {
                MFPAdapter.msnBigSmallThresh = Integer.parseInt(strBigSmallThresh);
                if (MFPAdapter.msnBigSmallThresh < 0) {
                    MFPAdapter.msnBigSmallThresh = 8;  // default is 8.
                }
            } catch (NumberFormatException e) {
                MFPAdapter.msnBigSmallThresh = 8;
            } catch (Exception e) { // null exception
                MFPAdapter.msnBigSmallThresh = 8;
            }
            MFPAdapter.mmfpNumBigThresh = MFPNumeric.pow(MFPNumeric.TEN, new MFPNumeric(MFPAdapter.msnBigSmallThresh));
            MFPAdapter.mmfpNumSmallThresh = MFPNumeric.pow(MFPNumeric.ONE_TENTH, new MFPNumeric(MFPAdapter.msnBigSmallThresh));

            String strVariableFrom = (String) jsonObject.get("PLOT_EXPRS_VARIABLE_FROM");
            try {
                MFPAdapter.msdPlotChartVariableFrom = Double.parseDouble(strVariableFrom);
            } catch (NumberFormatException e) {
                MFPAdapter.msdPlotChartVariableFrom = -5.0;
            } catch (Exception e) { // null exception
                MFPAdapter.msdPlotChartVariableFrom = -5.0;
            }

            String strVariableTo = (String) jsonObject.get("PLOT_EXPRS_VARIABLE_TO");
            try {
                MFPAdapter.msdPlotChartVariableTo = Double.parseDouble(strVariableTo);
            } catch (NumberFormatException e) {
                MFPAdapter.msdPlotChartVariableTo = 5.0;
            } catch (Exception e) { // null exception
                MFPAdapter.msdPlotChartVariableTo = 5.0;
            }

            // ensure to > from.
            if (!(MFPAdapter.msdPlotChartVariableTo > MFPAdapter.msdPlotChartVariableFrom)) {
                MFPAdapter.msdPlotChartVariableFrom = -5.0;
                MFPAdapter.msdPlotChartVariableTo = 5.0;
            }

            // loop array
            JSONArray additionalUsrLibs = (JSONArray) jsonObject.get("ADDITIONAL_USER_LIBS");
            if (additionalUsrLibs != null) {
                Iterator<JSONObject> iterator = additionalUsrLibs.iterator();
                while (iterator.hasNext()) {
                    JSONObject lib = iterator.next();
                    String strLibPath = (String) lib.get("LIB_PATH");
                    if (strLibPath != null) {
                        strLibPath = strLibPath.trim();
                        MFP4JavaFileMan.moriginaladdUsrLibs.add(strLibPath);
                        try {
                            String strCanonicalLibPath = convt2AbsCanPath(strLibPath);
                            if (IOLib.isExistingFile(strCanonicalLibPath)) { // file exist.
                                if (!MFP4JavaFileMan.madditionalUsrLibs.contains(strCanonicalLibPath)
                                        && !JCmdLineApp.mMFP4JavaFileMan.getScriptFolderFullPath().equals(strCanonicalLibPath)) {
                                    // if it is not a duplicate, load it.
                                    MFP4JavaFileMan.madditionalUsrLibs.add(strCanonicalLibPath);
                                }
                            }
                        } catch (JFCALCExpErrException ex) {
                            // the path is not valid, so ignore it.
                            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.WARNING, null, ex);
                        }
                    }
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.WARNING, null, ex);
            loadingFailed = 2;
        } catch (FileNotFoundException e) {
            // cannot open the file. Using default settings.
            loadingFailed = 1;
        } catch (IOException e) {
            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.WARNING, null, e);
            loadingFailed = 2;
        } catch (Exception e) { // ClassCastException
            Logger.getLogger(JCmdLineApp.class.getName()).log(Level.WARNING, null, e);
            loadingFailed = 2;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(JCmdLineApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return loadingFailed;
    }

    public void changeCalcThreadIdleState() {
        if (isIdle()) {   // now the calculation thread is idle.
            if (mIntegrationInput != null) {
                // enable copy to command line button
                mIntegrationInput.getCopy2CmdLineBtn().setEnabled(true);
            }
            if (mDerivativeInput != null) {
                // enable copy to command line button
                mDerivativeInput.getCopy2CmdLineBtn().setEnabled(true);
            }
            if (mMultiXYChartDef != null) {
                // enable copy to command line button
                mMultiXYChartDef.getCopy2CmdLineBtn().setEnabled(true);
            }
            if (mMultiRangleChartDef != null) {
                // enable copy to command line button
                mMultiRangleChartDef.getCopy2CmdLineBtn().setEnabled(true);
            }
            if (mMultiXYZChartDef != null) {
                // enable copy to command line button
                mMultiXYZChartDef.getCopy2CmdLineBtn().setEnabled(true);
            }
            ((JCmdLineView) getMainView()).onIdleStateChange(true);
        } else {   // now the calculation thread is running.
            if (mIntegrationInput != null) {
                // enable copy to command line button
                mIntegrationInput.getCopy2CmdLineBtn().setEnabled(false);
            }
            if (mMultiXYChartDef != null) {
                // enable copy to command line button
                mMultiXYChartDef.getCopy2CmdLineBtn().setEnabled(false);
            }
            if (mMultiRangleChartDef != null) {
                // enable copy to command line button
                mMultiRangleChartDef.getCopy2CmdLineBtn().setEnabled(false);
            }
            if (mMultiXYZChartDef != null) {
                // enable copy to command line button
                mMultiXYZChartDef.getCopy2CmdLineBtn().setEnabled(false);
            }
            ((JCmdLineView) getMainView()).onIdleStateChange(false);
        }
    }

    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
    }

    public static boolean unzipAssetsZip(String strZippedAssetName) {
        Enumeration entries;
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(strZippedAssetName);

            entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    (new File(mMFP4JavaFileMan.getAssetFolderFullPath()
                            + LangFileManager.STRING_PATH_DIVISOR
                            + entry.getName())).mkdirs();
                    continue;
                } else {
                    File fEntry = new File(mMFP4JavaFileMan.getAssetFolderFullPath()
                            + LangFileManager.STRING_PATH_DIVISOR
                            + entry.getName());
                    fEntry.getParentFile().mkdirs();
                    fEntry.createNewFile(); // ensure that intermediate folders are created.
                    copyInputStream(zipFile.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(fEntry)));
                }
            }

            return true;
        } catch (IOException ioe) {
            return false;
        } finally {
            if (null != zipFile) {
                try {
                    zipFile.close();
                } catch (Exception e) {
                    
                }
            }
        }
    }

    public static boolean unzipAssets7z(String strZippedAssetName) {
        SevenZFile zipFile = null;
        SevenZArchiveEntry entry;

        try {
            zipFile = new SevenZFile(new File(strZippedAssetName));
            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    (new File(mMFP4JavaFileMan.getAssetFolderFullPath()
                            + LangFileManager.STRING_PATH_DIVISOR
                            + entry.getName())).mkdirs();
                    continue;
                } else {
                    File fEntry = new File(mMFP4JavaFileMan.getAssetFolderFullPath()
                            + LangFileManager.STRING_PATH_DIVISOR
                            + entry.getName());
                    fEntry.getParentFile().mkdirs();
                    fEntry.createNewFile(); // ensure that intermediate folders are created.
                    FileOutputStream out = new FileOutputStream(fEntry);
                    byte[] content = new byte[(int) entry.getSize()];
                    zipFile.read(content, 0, content.length);
                    out.write(content);
                    out.close();
                }
            }

            return true;
        } catch (IOException ioe) {
            return false;
        } finally {
            if (null != zipFile) {
                try {
                    zipFile.close();
                } catch (Exception e) {
                    
                }
            }
        }
    }

    public boolean isNewVersion() {
        String strVersionFileFullName = mMFP4JavaFileMan.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR
                + STRING_VERSION_FILE_NAME;
        FileReader fr = null;
        BufferedReader br = null;
        boolean bReturn = true;
        try {
            fr = new FileReader(strVersionFileFullName);
            br = new BufferedReader(fr);
            String strVer = "";
            while (strVer.trim().equals("")) {
                strVer = br.readLine();
            }
            String[] strarrayOldVersion = strVer.trim().split("\\.");
            String[] strarrayNewVersion = STRING_APP_VERSION.split("\\.");
            for (int idx = 0; idx < Math.max(strarrayOldVersion.length, strarrayNewVersion.length); idx++) {
                int nOldSubVer = 0, nNewSubVer = 0;
                if (idx < strarrayOldVersion.length) {
                    nOldSubVer = Integer.parseInt(strarrayOldVersion[idx]);
                }
                if (idx < strarrayNewVersion.length) {
                    nNewSubVer = Integer.parseInt(strarrayNewVersion[idx]);
                }
                if (nOldSubVer < nNewSubVer) {
                    bReturn = true;
                    break;
                } else if (nOldSubVer == nNewSubVer) {
                    bReturn = false;
                    continue;
                } else {   // nOldSubVer > nNewSubVer
                    bReturn = false;
                    break;
                }
            }
            br.close();
            fr.close();
        } catch (Exception ex) {
            bReturn = true;
        }
        return bReturn;
    }

    public boolean writeVersionInfo() {
        String strVersionFileFullName = mMFP4JavaFileMan.getAppBaseFullPath()
                + LangFileManager.STRING_PATH_DIVISOR
                + STRING_VERSION_FILE_NAME;
        try {
            // Create file 
            FileWriter fstream = new FileWriter(strVersionFileFullName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(STRING_APP_VERSION);
            //Close the output stream
            out.close();
            fstream.close();
            return true;
        } catch (Exception e) {
            // do nothing
            return false;
        }
    }

    /**
     * Prepare the global variables for the other splash functions
     */
    protected static void initSplash() {
        mssplashScreen = SplashScreen.getSplashScreen();
        if (mssplashScreen != null) {   // if there are any problems displaying the splash this will be null
            Dimension ssDim = mssplashScreen.getSize();
            int height = ssDim.height;  // should be 300
            int width = ssDim.width;    // should be 480
            // stake out some area for our status information
            msrectSplashTextArea = new Rectangle2D.Double(15, 265, 160, 32);
            msrectSplashProgressArea = new Rectangle2D.Double(200, 270, 260, 12);

            // create the Graphics environment for drawing status info
            msg2dSplashGraphics = mssplashScreen.createGraphics();
            msfontSplashText = new Font("Dialog", Font.PLAIN, 14);
            msg2dSplashGraphics.setFont(msfontSplashText);

            // initialize the status info
            showSplashText(getStringsClass().get_starting());
            showSplashProgress(0);
        }
    }

    /**
     * Display text in status area of Splash. Note: no validation it will fit.
     *
     * @param str - text to be displayed
     */
    public static void showSplashText(String str) {
        if (mssplashScreen != null && mssplashScreen.isVisible()) {   // important to check here so no other methods need to know if there
            // really is a Splash being displayed

            // erase the last status text
            float[] farrayHSB = Color.RGBtoHSB(113, 164, 208, null);
            Color colorBkGround = Color.getHSBColor(farrayHSB[0], farrayHSB[1], farrayHSB[2]);
            msg2dSplashGraphics.setPaint(colorBkGround);
            msg2dSplashGraphics.fill(msrectSplashTextArea);

            // draw the text
            msg2dSplashGraphics.setPaint(Color.BLACK);
            msg2dSplashGraphics.drawString(str, (int) (msrectSplashTextArea.getX() + 10), (int) (msrectSplashTextArea.getY() + 15));

            // make sure it's displayed
            mssplashScreen.update();
        }
    }

    /**
     * Display a (very) basic progress bar
     *
     * @param pct how much of the progress bar to display 0-100
     */
    public static void showSplashProgress(int pct) {
        if (mssplashScreen != null && mssplashScreen.isVisible()) {

            float[] farrayHSB = Color.RGBtoHSB(113, 164, 208, null);
            Color colorBkGround = Color.getHSBColor(farrayHSB[0], farrayHSB[1], farrayHSB[2]);
            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            msg2dSplashGraphics.setPaint(colorBkGround);
            msg2dSplashGraphics.fill(msrectSplashProgressArea);

            // draw an outline
            msg2dSplashGraphics.setPaint(colorBkGround);
            msg2dSplashGraphics.draw(msrectSplashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) msrectSplashProgressArea.getMinX();
            int y = (int) msrectSplashProgressArea.getMinY();
            int wid = (int) msrectSplashProgressArea.getWidth();
            int hgt = (int) msrectSplashProgressArea.getHeight();

            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            msg2dSplashGraphics.setPaint(Color.MAGENTA);
            msg2dSplashGraphics.fillRect(x, y + 1, doneWidth, hgt - 1);

            // make sure it's displayed
            mssplashScreen.update();
        }
    }

    /**
     * Adds the specified path to the java library path
     *
     * @param pathToAdd the path to add
     * @throws Exception
     */
    public static void addLibraryPath(String pathToAdd) throws Exception {
        // This function was using 
        // ClassLoader.class.getDeclaredField("usr_paths")
        // but this method has been discarded since java-13+
        // So I decided to add jogl-all-native-*.jar and gluegen-rt-native-*.jar
        // files in lib and only support 64 bits windows, Linux and MacOSX.
        // I still keep this function as a stub. Maybe it will be used in
        // java-17+. But if it is not used after java-17, delete it.
    }
}
