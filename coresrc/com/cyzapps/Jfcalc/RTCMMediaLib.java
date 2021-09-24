// MFP project, RTCMMediaLib.java : Designed and developed by Tony Cui in 2021

package com.cyzapps.Jfcalc;

import com.cyzapps.JGI2D.Display2D;
import com.cyzapps.Jfdatastruct.ArrayBasedDictionary;
import com.cyzapps.Jmfp.ProgContext;
import com.cyzapps.OSAdapter.ParallelManager.CommunicationManager;
import com.cyzapps.OSAdapter.RtcMMediaManager;
import com.cyzapps.Oomfp.CitingSpaceDefinition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.cyzapps.JGI2D.DisplayLib.get2DDisplay;

public class RTCMMediaLib {

    public static void call2Load(boolean bOutput) {
        if (bOutput) {
            System.out.println("Loading " + RTCMMediaLib.class.getName());
        }
    }

    public static class Initialize_rtc_mmediaFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Initialize_rtc_mmediaFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::initialize_rtc_mmedia";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 0;
            mnMinParamNum = 0;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException
        {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            FuncEvaluator.msRtcMMediaManager.initRtcMMediaMan();
            return new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPBOOL, MFPNumeric.TRUE);
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Initialize_rtc_mmediaFunction());}

    public static class Receive_rtc_mmedia_messageFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Receive_rtc_mmedia_messageFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::receive_rtc_mmedia_message";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 1;
            mnMinParamNum = 1;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            long lWaitingTime = DCHelper.lightCvtOrRetDCMFPDec(listParams.removeLast()).getDataValue().longValue();
            RtcMMediaManager.RtcMMediaEvent evt = FuncEvaluator.msRtcMMediaManager.pullEvent(lWaitingTime);
            if (evt == null) {
                return new DataClassNull();
            } else {
                DataClassString evtPeerId = new DataClassString(evt.peerId);
                DataClassSingleNum evtSessionId = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(evt.sessionIndex));
                DataClassString evtType = new DataClassString(evt.eventType);
                DataClassString evtInfo = new DataClassString(evt.eventInfo);
                DataClass[] evtElems = new DataClass[4];
                evtElems[0] = evtPeerId;
                evtElems[1] = evtSessionId;
                evtElems[2] = evtType;
                evtElems[3] = evtInfo;
                DataClass evtValue = new DataClassArray(evtElems);
                return evtValue;
            }
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Receive_rtc_mmedia_messageFunction());}

    public static class Create_rtc_media_offerFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Create_rtc_media_offerFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::create_rtc_media_offer";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 3;
            mnMinParamNum = 2;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            Map<String, String> manContraintMap = new HashMap<String, String>();
            DataClassArray mandatoryConstraints = DCHelper.lightCvtOrRetDCArray(listParams.removeLast());
            DataClass[] manConstraints = mandatoryConstraints.getDataList();
            for (int idx = 0; idx < manConstraints.length; idx ++) {
                DataClassArray thisContraint = DCHelper.lightCvtOrRetDCArray(manConstraints[idx]);
                DataClass[] thisContraintKeyValue = thisContraint.getDataList();
                if (thisContraintKeyValue.length != 2) {
                    throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
                }
                DataClassString key = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[0]);
                DataClassString value = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[1]);
                manContraintMap.put(key.getStringValue(), value.getStringValue());
            }
            Map<String, String> optContraintMap = new HashMap<String, String>();
            if (listParams.size() > 0) {
                DataClassArray optionalConstraints = DCHelper.lightCvtOrRetDCArray(listParams.removeLast());
                DataClass[] optConstraints = optionalConstraints.getDataList();
                for (int idx = 0; idx < optConstraints.length; idx ++) {
                    DataClassArray thisContraint = DCHelper.lightCvtOrRetDCArray(optConstraints[idx]);
                    DataClass[] thisContraintKeyValue = thisContraint.getDataList();
                    if (thisContraintKeyValue.length != 2) {
                        throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
                    }
                    DataClassString key = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[0]);
                    DataClassString value = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[1]);
                    optContraintMap.put(key.getStringValue(), value.getStringValue());
                }
            }
            boolean ret = FuncEvaluator.msRtcMMediaManager.createOffer(peerId, manContraintMap, optContraintMap);
            if (!ret) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            return null;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Create_rtc_media_offerFunction());}

    public static class Create_rtc_media_answerFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Create_rtc_media_answerFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::create_rtc_media_answer";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 5;
            mnMinParamNum = 4;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            String sdpType = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            String sdpContent = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            Map<String, String> manContraintMap = new HashMap<String, String>();
            DataClassArray mandatoryConstraints = DCHelper.lightCvtOrRetDCArray(listParams.removeLast());
            DataClass[] manConstraints = mandatoryConstraints.getDataList();
            for (int idx = 0; idx < manConstraints.length; idx ++) {
                DataClassArray thisContraint = DCHelper.lightCvtOrRetDCArray(manConstraints[idx]);
                DataClass[] thisContraintKeyValue = thisContraint.getDataList();
                if (thisContraintKeyValue.length != 2) {
                    throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
                }
                DataClassString key = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[0]);
                DataClassString value = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[1]);
                manContraintMap.put(key.getStringValue(), value.getStringValue());
            }
            Map<String, String> optContraintMap = new HashMap<String, String>();
            if (listParams.size() > 0) {
                DataClassArray optionalConstraints = DCHelper.lightCvtOrRetDCArray(listParams.removeLast());
                DataClass[] optConstraints = optionalConstraints.getDataList();
                for (int idx = 0; idx < optConstraints.length; idx ++) {
                    DataClassArray thisContraint = DCHelper.lightCvtOrRetDCArray(optConstraints[idx]);
                    DataClass[] thisContraintKeyValue = thisContraint.getDataList();
                    if (thisContraintKeyValue.length != 2) {
                        throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
                    }
                    DataClassString key = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[0]);
                    DataClassString value = DCHelper.lightCvtOrRetDCString(thisContraintKeyValue[1]);
                    optContraintMap.put(key.getStringValue(), value.getStringValue());
                }
            }
            boolean ret = FuncEvaluator.msRtcMMediaManager.createAnswer(peerId, sdpType, sdpContent, manContraintMap, optContraintMap);
            if (!ret) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            return null;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Create_rtc_media_answerFunction());}

    public static class Add_rtc_media_ice_candidateFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Add_rtc_media_ice_candidateFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::add_rtc_media_ice_candidate";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 2;
            mnMinParamNum = 2;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            String payLoad = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            boolean ret = FuncEvaluator.msRtcMMediaManager.addIceCandidate(peerId, payLoad);
            if (!ret) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            return null;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Add_rtc_media_ice_candidateFunction());}

    public static class Close_rtc_media_peerFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Close_rtc_media_peerFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::close_rtc_media_peer";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 1;
            mnMinParamNum = 1;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            FuncEvaluator.msRtcMMediaManager.closePeer(peerId);
            return null;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Close_rtc_media_peerFunction());}

    public static class Add_rtc_video_outputFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Add_rtc_video_outputFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::add_rtc_video_output";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 6;
            mnMinParamNum = 5;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            int left = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            int top = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            int width = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            int height = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            if (width <= 0 || height <= 0) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            boolean enableSlide = false;
            if (listParams.size() > 0) {
                enableSlide = DCHelper.lightCvtOrRetDCMFPBool(listParams.removeLast()).getDataValue().booleanValue();
            }
            int id = display.addRtcVideoOutput(left, top, width, height, enableSlide);
            return new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(id));
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Add_rtc_video_outputFunction());}

    public static class Start_local_streamFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Start_local_streamFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::start_local_stream";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 2;
            mnMinParamNum = 2;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            int videoOutputId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            if (videoOutputId < 0) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            boolean ret = display.startLocalStream(videoOutputId);
            return new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPBOOL, new MFPNumeric(ret));
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Start_local_streamFunction());}

    public static class Get_rtc_video_output_leftrightFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Get_rtc_video_output_leftrightFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::get_rtc_video_output_leftright";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 2;
            mnMinParamNum = 2;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            int videoOutputId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            if (videoOutputId < 0) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            int[] leftright = display.getRtcVideoOutputLeftRight(videoOutputId);
            DataClass[] leftrightArray = new DataClass[2];
            leftrightArray[0] = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(leftright[0]));
            leftrightArray[1] = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(leftright[1]));
            DataClassArray ret = new DataClassArray(leftrightArray);
            return ret;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Get_rtc_video_output_leftrightFunction());}

    public static class Get_rtc_video_output_countFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Get_rtc_video_output_countFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::get_rtc_video_output_output";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 1;
            mnMinParamNum = 1;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            int cnt = display.getRtcVideoOutputCount();
            DataClass ret = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(cnt));
            return ret;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Get_rtc_video_output_countFunction());}

    public static class Link_video_streamFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Link_video_streamFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::link_video_stream";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 4;
            mnMinParamNum = 4;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
            int trackId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            int videoOutputId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
            if (trackId < 0 || videoOutputId < 0) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            if (!display.linkVideoStream(peerId, trackId, videoOutputId)) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
            return null;
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Link_video_streamFunction());}

    public static class Unlink_video_streamFunction extends BuiltInFunctionLib.BaseBuiltInFunction {
        private static final long serialVersionUID = 1L;

        public Unlink_video_streamFunction() {
            mstrProcessedNameWithFullCS = "::mfp::multimedia::webrtc_lib::unlink_video_stream";
            mstrarrayFullCS = mstrProcessedNameWithFullCS.split("::");
            mnMaxParamNum = 3;
            mnMinParamNum = 2;
        }
        @Override
        public DataClass callAction(LinkedList<DataClass> listParams, LinkedList<String> listParamRawInputs, ProgContext progContext) throws ErrProcessor.JFCALCExpErrException, InterruptedException {
            if (listParams.size() < mnMinParamNum || listParams.size() > mnMaxParamNum)   {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INCORRECT_NUM_OF_PARAMETER);
            }
            Display2D display = get2DDisplay(listParams.removeLast(), false);
            if (listParams.size() == 2) {
                String peerId = DCHelper.lightCvtOrRetDCString(listParams.removeLast()).getStringValue();
                int trackId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
                boolean ret = display.unlinkVideoStream(peerId, trackId);
                DataClass unlinkedCnt = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, ret?MFPNumeric.ONE:MFPNumeric.ZERO);
                return unlinkedCnt;
            } else {
                int videoOutputId = DCHelper.lightCvtOrRetDCMFPInt(listParams.removeLast()).getDataValue().intValue();
                int ret = display.unlinkVideoStream(videoOutputId);
                DataClass unlinkedCnt = new DataClassSingleNum(DCHelper.DATATYPES.DATUM_MFPINT, new MFPNumeric(ret));
                return unlinkedCnt;
            }
        }
    }
    static {
        CitingSpaceDefinition.CSD_TOP_SYS.addMemberNoExcept(new Unlink_video_streamFunction());}
}