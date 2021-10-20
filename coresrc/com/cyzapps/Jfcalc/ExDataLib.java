package com.cyzapps.Jfcalc;

import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.Oomfp.CitingSpaceDefinition;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Locale;

public class ExDataLib {

    public static void call2Load(boolean bOutput) {
        if (bOutput) {
            System.out.println("Loading " + ExDataLib.class.getName());
        }
    }

    public static class Get_json_fieldFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Get_json_fieldFunction() {
            mstrProcessedNameWithFullCS = "::mfp::exdata::json::get_json_field";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 3;
            mnMinParamNum = 2;
        }
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException
        {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            DataClassString datumJSON = DCHelper.lightCvtOrRetDCString(listParams.removeLast());
            String json = datumJSON.getStringValue();
            DataClassString datumKey = DCHelper.lightCvtOrRetDCString(listParams.removeLast());
            String key = datumKey.getStringValue();
            String type = "s";  // s means string, b means boolean, f means double and d means int
            if (listParams.size() > 0) {
                DataClassString datumType = DCHelper.lightCvtOrRetDCString(listParams.removeLast());
                type = datumKey.getStringValue().toLowerCase(Locale.US);
            }

            DataClass datumRet = null;
            try {
                JSONObject obj = new JSONObject(json);
                if (type.equals("b")) {
                    Boolean val = obj.getBoolean(key);
                    datumRet = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPBOOL, val?MFPNumeric.TRUE:MFPNumeric.FALSE);
                } else if (type.equals("d")) {
                    int val = obj.getInt(key);
                    datumRet = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(val));
                } else if (type.equals("f")) {
                    String val = obj.getString(key);
                    datumRet = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPDEC, new MFPNumeric(new BigDecimal(val)));
                } else {
                    String val = obj.getString(key);
                    datumRet = new DataClassString(val);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }

            return datumRet;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Get_json_fieldFunction());}

    public static class Set_json_fieldFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Set_json_fieldFunction() {
            mstrProcessedNameWithFullCS = "::mfp::exdata::json::set_json_field";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 3;
            mnMinParamNum = 3;
        }
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException
        {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            DataClassString datumJSON = DCHelper.lightCvtOrRetDCString(listParams.removeLast());
            String json = datumJSON.getStringValue();
            DataClassString datumKey = DCHelper.lightCvtOrRetDCString(listParams.removeLast());
            String key = datumKey.getStringValue();
            DataClass datumValue = listParams.removeLast();
            String value = datumValue.toString();

            DataClass datumRet = null;
            try {
                JSONObject obj = new JSONObject(json);
                obj.put(key, value);
                datumRet = new DataClassString(obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            return datumRet;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Set_json_fieldFunction());}
}
