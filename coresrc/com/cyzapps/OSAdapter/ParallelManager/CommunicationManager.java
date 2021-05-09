// MFP project, CommunicationManager.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.OSAdapter.ParallelManager;

import com.cyzapps.Jfcalc.DCHelper;
import java.io.IOException;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfdatastruct.ArrayBasedDictionary;
import com.cyzapps.Jmfp.VariableOperator;
import com.cyzapps.OSAdapter.ParallelManager.LocalObject.LocalKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CommunicationManager {

    /**
     * get local host information
     * @param protocolName : protocol name must have been trimmed and upper case, i.e. WEBRTC or TCPIP
     * @param additionalParam
     * @return
     * @throws ErrProcessor.JFCALCExpErrException
     */
    public abstract String getLocalHost(String protocolName, String additionalParam) throws ErrProcessor.JFCALCExpErrException ;

    /**
     * get all addresses of the protocol
     * @param protocolName : protocol name must have been trimmed and upper case, i.e. WEBRTC or TCPIP
     * @return
     * @throws ErrProcessor.JFCALCExpErrException
     */
    public abstract Map<String, Map<String, Set<String>>> getAllAddresses(String protocolName) throws ErrProcessor.JFCALCExpErrException;
    
    // a map of remote come-in connect objects
    protected Map<LocalObject.LocalKey, LocalObject> allInLocals = new ConcurrentHashMap<LocalObject.LocalKey, LocalObject>();
    public Boolean existInLocal(LocalObject.LocalKey localKey) {
        for(Map.Entry<LocalObject.LocalKey, LocalObject> entry: allInLocals.entrySet()) {
            if (entry.getValue().matchKey(localKey)) {
                return true;
            }
        }
        return false;
    }
    public LocalObject findInLocal(LocalObject.LocalKey localKey) {
        for(Map.Entry<LocalObject.LocalKey, LocalObject> entry: allInLocals.entrySet()) {
            if (entry.getValue().matchKey(localKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
	    
    public abstract boolean initInLocal(LocalKey localInfo, boolean reuseExisting) throws ErrProcessor.JFCALCExpErrException;
	
    // a map of protocol name and protocol object.
	protected Map<LocalObject.LocalKey, LocalObject> allOutLocals = new ConcurrentHashMap<LocalObject.LocalKey, LocalObject>();
    public Boolean existOutLocal(LocalObject.LocalKey localKey) {
        for(Map.Entry<LocalObject.LocalKey, LocalObject> entry: allOutLocals.entrySet()) {
            if (entry.getValue().matchKey(localKey)) {
                return true;
            }
        }
        return false;
    }
    public LocalObject findOutLocal(LocalObject.LocalKey localKey) {
        for(Map.Entry<LocalObject.LocalKey, LocalObject> entry: allOutLocals.entrySet()) {
            if (entry.getValue().matchKey(localKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    // this set stores all return variables used by all the call blocks.
	// cannot simply use name because variables in different namespaces or blocks may have
	// same name.
	private final Set<VariableOperator.Variable> allUsedCallVariables = Collections.synchronizedSet(new HashSet<VariableOperator.Variable>());
	public Set<VariableOperator.Variable> getAllUsedCallVariables() {
        return allUsedCallVariables;
    }
    
    public CallObject getCallOutObject(String protocolName, String address, String remoteAddr, int callPoint) {
        LocalObject.LocalKey localKey = new LocalKey(protocolName, address);
        
        LocalObject localObj = findOutLocal(localKey);
        if (localObj != null) {
            if (localObj.allOutConnects.containsKey(remoteAddr)) {
                CallObject callObj = localObj.allOutConnects.get(remoteAddr).allCalls.get(callPoint);
                return callObj;
            }
        }
        return null;
    }
	
    // the incoming callobject will have the same call point as outgoing callobject. In the outgoing side, all
    // the call points are unique. But the incoming side is not necessary. Therefore, I don't provide getCallInObject
    // function
    
	// is the connect object existing?
	public ConnectObject findConnectOutObject(DataClass datumConnect) {
		try {
			DataClass datumProtocol = ArrayBasedDictionary.getArrayBasedDictValue(datumConnect, "PROTOCOL");
			DataClass datumLocalAddr = ArrayBasedDictionary.getArrayBasedDictValue(datumConnect, "LOCAL_ADDRESS");
			DataClass datumAddress = ArrayBasedDictionary.getArrayBasedDictValue(datumConnect, "ADDRESS");
			if (null != datumProtocol && null != datumLocalAddr && null != datumAddress) {
				String protocolName = DCHelper.lightCvtOrRetDCString(datumProtocol).getStringValue();
				String localAddress = DCHelper.lightCvtOrRetDCString(datumLocalAddr).getStringValue();
				LocalObject.LocalKey localKey = new LocalObject.LocalKey(protocolName, localAddress);
				LocalObject localObj = findOutLocal(localKey);
                if (localObj == null) {
                    return null;    // no local found.
                }
				String addr = DCHelper.lightCvtOrRetDCString(datumAddress).getStringValue();
				if (!localObj.allOutConnects.containsKey(addr)) {
					// no connect found
					return null;
				}
				ConnectObject conn = localObj.allOutConnects.get(addr);
				return conn;
			}
		} catch (ErrProcessor.JFCALCExpErrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    // this is client side call object.
    public CallObject createOutCallObject(DataClass datumConnect, Boolean isNotTransientCall) {
        ConnectObject connObj = findConnectOutObject(datumConnect);
        if (connObj != null) {
            CallObject callObj = connObj.createCallObject(isNotTransientCall);
            return callObj;
        }
		return null;
    }
    
    // this is client side call object.
    public CallObject removeOutCallObject(DataClass datumConnect, Integer callPoint) {
        ConnectObject connObj = findConnectOutObject(datumConnect);
        if (connObj != null) {
            CallObject callObj = connObj.removeCallObject(callPoint);
            return callObj;
        }
		return null;
    }
	    
    public abstract boolean initOutLocal(LocalKey localInfo, boolean reuseExisting) throws ErrProcessor.JFCALCExpErrException;
	
	public CallCommPack getReceivedCallRequestInfo(String callReqReturn) throws ClassNotFoundException, ClassCastException, IOException {
		Object obj = deserialize(callReqReturn);
		CallCommPack ret = (CallCommPack) obj;
		return ret;
	}

    public LocalObject removeLocal(LocalObject.LocalKey localKey, boolean isInLocal) {
        if (isInLocal) {
            return allInLocals.remove(localKey);
        } else {
            return allOutLocals.remove(localKey);
        }
    }
    
	public abstract String serialize(Object sobj) throws IOException;
	
    public abstract Object deserialize(String s) throws IOException, ClassNotFoundException;
}
