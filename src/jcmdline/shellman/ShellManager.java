/*
 * MFP project, ShellManager.java : Designed and developed by Tony Cui in 2021
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcmdline.shellman;

import com.cyzapps.Jfcalc.Operators.CalculateOperator;
import com.cyzapps.Jfcalc.DCHelper.CurPos;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassNull;
import com.cyzapps.Jfcalc.Operators.OPERATORTYPES;
import com.cyzapps.Jfcalc.ExprEvaluator;
import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Jmfp.Statement;
import com.cyzapps.Jmfp.Statement_variable;
import com.cyzapps.Jmfp.VariableOperator.Variable;
import com.cyzapps.Shellman.CitingSpaceMan;
import com.cyzapps.Shellman.CmdLnVarMan;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import jcmdline.JCmdLineApp;

/**
 *
 * @author tonyc
 */
public class ShellManager {

    public static final String SHELL_MANAGER_COMMAND = "shellman";
    public static final String LIST_CITINGSPACE = "list_cs";
    public static final String ADD_CITINGSPACE = "add_cs";
    public static final String DELETE_CITINGSPACE = "delete_cs";
    public static final String CLEAR_CITINGSPACE = "clear_cs";

    public static final String LIST_CMDLNVARS = "list_var";
    public static final String ADD_VARINTOCMDLN = "add_var";
    public static final String DELETE_VARFROMCMDLN = "delete_var";
    public static final String CLEAR_VARS = "clear_var";

    public static final String CLEAR_ALL = "clear_all";
    public static final String BUILD_APK = "build_apk";
    public static final String QUIT = "quit";

    protected JCmdLineApp mApplication;

    protected CitingSpaceMan mCmdLineCSMan;

    protected CmdLnVarMan mCmdLineVarMan;

    public ShellManager(JCmdLineApp app) {
        mApplication = app;
        mCmdLineCSMan = new CitingSpaceMan();
        LinkedList<Variable> listPermVars = new LinkedList<Variable>();
        listPermVars.add(app.mvarAns);
        mCmdLineVarMan = new CmdLnVarMan(listPermVars, app.mlCmdLineLocalVars);
    }

    public void setCitingSpaces(List<String[]> listCSes) {
        mCmdLineCSMan.mAllCSes.clear();
        mCmdLineCSMan.mAllCSes.addAll(listCSes);
    }

    // note that this function will return a copy of used citing space list.
    public List<String[]> getCitingSpaces() {
        LinkedList<String[]> allCSes = new LinkedList<String[]>();
        allCSes.addAll(mCmdLineCSMan.mAllCSes);
        return allCSes;
    }

    public String Interpreter(String strInput) {
        String strOutput = "";
        if (strInput.length() <= SHELL_MANAGER_COMMAND.length() + 1) {
            strOutput = JCmdLineApp.getStringsClass().get_invalid_shellman_command();
        } else {
            String strPureInput = strInput.substring(SHELL_MANAGER_COMMAND.length() + 1).trim();
            if (strPureInput.equalsIgnoreCase(LIST_CITINGSPACE)) {
                // this is list cs
                strOutput = listCitingSpace();
            } else if (strPureInput.length() > ADD_CITINGSPACE.length()
                    && (strPureInput.substring(0, ADD_CITINGSPACE.length() + 1).equalsIgnoreCase(ADD_CITINGSPACE + " ")
                    || strPureInput.substring(0, ADD_CITINGSPACE.length() + 1).equalsIgnoreCase(ADD_CITINGSPACE + "\t"))) {
                strOutput = addCitingSpace(strPureInput.substring(ADD_CITINGSPACE.length()));
            } else if (strPureInput.equalsIgnoreCase(ADD_CITINGSPACE)) {
                strOutput = JCmdLineApp.getStringsClass().get_shellman_command_need_cs_parameter();
            } else if (strPureInput.length() > DELETE_CITINGSPACE.length()
                    && (strPureInput.substring(0, DELETE_CITINGSPACE.length() + 1).equalsIgnoreCase(DELETE_CITINGSPACE + " ")
                    || strPureInput.substring(0, DELETE_CITINGSPACE.length() + 1).equalsIgnoreCase(DELETE_CITINGSPACE + "\t"))) {
                strOutput = deleteCitingSpace(strPureInput.substring(DELETE_CITINGSPACE.length()));
            } else if (strPureInput.equalsIgnoreCase(DELETE_CITINGSPACE)) {
                strOutput = JCmdLineApp.getStringsClass().get_shellman_command_need_cs_parameter();
            } else if (strPureInput.equalsIgnoreCase(CLEAR_CITINGSPACE)) {
                mCmdLineCSMan.clearCitingSpace();
            } else if (strPureInput.equalsIgnoreCase(LIST_CMDLNVARS)) {
                strOutput = mCmdLineVarMan.listAllCmdLnVars();
            } else if (strPureInput.length() > ADD_VARINTOCMDLN.length()
                    && (strPureInput.substring(0, ADD_VARINTOCMDLN.length() + 1).equalsIgnoreCase(ADD_VARINTOCMDLN + " ")
                    || strPureInput.substring(0, ADD_VARINTOCMDLN.length() + 1).equalsIgnoreCase(ADD_VARINTOCMDLN + "\t"))) {
                String strParams = strPureInput.substring(ADD_VARINTOCMDLN.length()).trim();
                String strStatement = Statement_variable.getTypeStr() + " " + strParams;
                Statement sTemp = new Statement(strStatement, "", 0);   // a temporary statement.
                boolean bValid = true;
                try {
                    sTemp.analyze();
                } catch (Exception e) {
                    bValid = false;
                }

                if (bValid && !sTemp.mstatementType.getType().equals(Statement_variable.getTypeStr())) {
                    bValid = false; // not a variable statement.
                }
                Statement_variable sv = (Statement_variable) (sTemp.mstatementType);
                if (bValid && sv.mstrVariables.length != 1) {
                    bValid = false; // different from varaible statement, only one variable can be allowed.
                }
                Variable var = null;
                if (bValid) {
                    var = new Variable(sv.mstrVariables[0], new DataClassNull());   // null is default value.
                    if (sv.mstrVarValues[0].length() > 0) {
                        LinkedList<LinkedList<Variable>> lVarNameSpaces = new LinkedList<LinkedList<Variable>>();
                        lVarNameSpaces.add(mApplication.mlCmdLineLocalVars);
                        ProgContext progContext = new ProgContext();
                        progContext.mstaticProgContext.setCitingSpacesExplicitly(getCitingSpaces());
                        progContext.mdynamicProgContext.mlVarNameSpaces = lVarNameSpaces;
                        ExprEvaluator exprEvaluator = new ExprEvaluator(progContext);
                        CurPos c = new CurPos();
                        c.m_nPos = 0;
                        DataClass datumValue;
                        try {
                            datumValue = exprEvaluator.evaluateExpression(sv.mstrVarValues[0], c);
                            if (datumValue == null) {
                                bValid = false;
                            }
                            DataClass datumNewValue = ExprEvaluator.evaluateTwoOperandCell(var.getValue(),
                                    new CalculateOperator(OPERATORTYPES.OPERATOR_ASSIGN, 2),
                                    datumValue);
                            var.setValue(datumNewValue);
                        } catch (Exception e) {
                            bValid = false;
                        }
                    }
                }
                if (bValid) {
                    mCmdLineVarMan.addVarIntoCmdLn(var);
                    strOutput = JCmdLineApp.getStringsClass().get_variable_has_been_added();
                } else {
                    strOutput = JCmdLineApp.getStringsClass().get_invalid_command_option();
                }
            } else if (strPureInput.equalsIgnoreCase(ADD_VARINTOCMDLN)) {
                strOutput = JCmdLineApp.getStringsClass().get_shellman_command_need_parameter();
            } else if (strPureInput.length() > DELETE_VARFROMCMDLN.length()
                    && (strPureInput.substring(0, DELETE_VARFROMCMDLN.length() + 1).equalsIgnoreCase(DELETE_VARFROMCMDLN + " ")
                    || strPureInput.substring(0, DELETE_VARFROMCMDLN.length() + 1).equalsIgnoreCase(DELETE_VARFROMCMDLN + "\t"))) {
                if (mCmdLineVarMan.deleteVarFromCmdLn(strPureInput.substring(DELETE_VARFROMCMDLN.length()).trim().toLowerCase(Locale.US))) {
                    strOutput = JCmdLineApp.getStringsClass().get_variable_has_been_deleted();
                } else {
                    strOutput = JCmdLineApp.getStringsClass().get_variable_not_exist_or_cannot_delete();
                }
            } else if (strPureInput.equalsIgnoreCase(DELETE_VARFROMCMDLN)) {
                strOutput = JCmdLineApp.getStringsClass().get_shellman_command_need_parameter();
            } else if (strPureInput.equalsIgnoreCase(CLEAR_VARS)) {
                mCmdLineVarMan.clearCmdLnVars();
            } else if (strPureInput.equalsIgnoreCase(CLEAR_ALL)) {
                mCmdLineCSMan.clearCitingSpace();
                mCmdLineVarMan.clearCmdLnVars();
            } else if (strPureInput.equalsIgnoreCase(BUILD_APK)) {
                mApplication.showApkGenInput();
            } else if (strPureInput.equalsIgnoreCase(QUIT)) {
                System.exit(0);
            } else {
                strOutput = JCmdLineApp.getStringsClass().get_invalid_shellman_command();
            }
        }
        return strOutput;
    }

    public String listCitingSpace() {
        String strOutput = JCmdLineApp.getStringsClass().get_list_citingspace_info() + "\n";
        strOutput += mCmdLineCSMan.listAllCitingSpaces();
        return strOutput;
    }

    public String addCitingSpace(String strCS) {
        String strOutput = (mCmdLineCSMan.addCitingSpace(strCS) < 0)
                ? JCmdLineApp.getStringsClass().get_add_citingspace_failed()
                : JCmdLineApp.getStringsClass().get_add_citingspace_succeeded2();
        return strOutput;
    }

    public String deleteCitingSpace(String strCS) {
        String strOutput = mCmdLineCSMan.deleteCitingSpace(strCS)
                ? JCmdLineApp.getStringsClass().get_delete_citingspace_succeeded()
                : JCmdLineApp.getStringsClass().get_delete_citingspace_failed();
        return strOutput;
    }
}
