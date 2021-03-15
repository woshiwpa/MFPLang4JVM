// MFP project, LocalObject.java : Designed and developed by Tony Cui in 2021
package com.cyzapps.OSAdapter.ParallelManager;

import com.cyzapps.Jfcalc.DCHelper;
import com.cyzapps.Jfcalc.DataClass;
import com.cyzapps.Jfcalc.DataClassArray;
import com.cyzapps.Jfcalc.DataClassSingleNum;
import com.cyzapps.Jfcalc.DataClassString;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.MFPNumeric;
import com.cyzapps.Jfdatastruct.ArrayBasedDictionary;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LocalObject {
	protected String protocolName;
	public String getProtocolName() {
		return protocolName;
	}
	
	protected String address;
	public String getAddress() {
		return address;
	}
	
    protected boolean isDown;
    public boolean isShutdown() {
        return isDown;
    }
    
	public static class LocalKey implements Serializable {
		private final String protocolName;
        public String getProtocolName() {
            return protocolName;
        }
		private final String localAddress;
        public String getLocalAddress() {
            return localAddress;
        }
		
		public LocalKey(String protocol, String localAddr) {
			protocolName = protocol;
			localAddress = localAddr;
		}
		
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof LocalKey)) return false;
	        LocalKey key = (LocalKey) o;
	        return protocolName.equals(key.protocolName) && localAddress.equals(key.localAddress);
	    }

	    @Override
	    public int hashCode() {
	        int result = protocolName.hashCode();
	        result = 31 * result + localAddress.hashCode();
	        return result;
	    }
        
        public DataClass toDataClass() {
            DataClassArray retValue = ArrayBasedDictionary.createArrayBasedDict();
            try {
                DataClass protocolData = new DataClassString(protocolName);
                retValue = ArrayBasedDictionary.setArrayBasedDictValue(retValue, "ProtocolName", protocolData);
                DataClass addressData = new DataClassString(localAddress);
                retValue = ArrayBasedDictionary.setArrayBasedDictValue(retValue, "LocalAddress", addressData);
            } catch (ErrProcessor.JFCALCExpErrException ex) {
                Logger.getLogger(LocalObject.class.getName()).log(Level.SEVERE, null, ex);
                // this will never happen.
            }
            return retValue;
        }
	}
	
	// a map of going out connect address and connect object.
	public final Map<String, ConnectObject> allOutConnects = new ConcurrentHashMap<String, ConnectObject>();
	
	// it is a stupid idea to track address of client because several clients may share the same address if
    // using Nat and it may change in a mobile network.
	public final Map<String, ConnectObject> allInConnects = new ConcurrentHashMap<String, ConnectObject>();
	
    // make SandBoxMessage immutable so that it is thread-safe.
    public static final class SandBoxMessage {
        private final LocalKey interfaceInfo;
        private final String connectId;
        private final int callId;
        private final DataClass message;
        
        public SandBoxMessage(LocalKey interfInfo, String cntId, int callObjId, DataClass msg) {
            interfaceInfo = interfInfo;
            connectId = cntId;
            callId = callObjId;
            message = msg;
        }
        
        public LocalKey getInterfaceInfo() {
            return interfaceInfo;
        }
        
        public String getConnectId() {
            return connectId;
        }
        
        public int getCallId() {
            return callId;
        }
        
        public DataClass getMessage() {
            return message;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof SandBoxMessage)) {
                return false;
            } else if (interfaceInfo == null && ((SandBoxMessage) o).interfaceInfo != null) {
                return false;
            } else if ((interfaceInfo == null || interfaceInfo.equals(((SandBoxMessage) o).interfaceInfo))
                    && connectId.equals(((SandBoxMessage) o).connectId)
                    && (callId == ((SandBoxMessage) o).callId || callId == -1 || ((SandBoxMessage) o).callId == -1)) {
                return true;    // this means we come from the call object. callId == -1 means it is a wild card match
            } else {
                return false;   // this means we do not come from the same call object.
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.interfaceInfo);
            hash = 17 * hash + Objects.hashCode(this.connectId);
            hash = 17 * hash + this.callId;
            // do not use message to calculate hash code.
            // hash = 17 * hash + Objects.hashCode(this.message);
            return hash;
        }
    }
    
    public final BlockingQueue<SandBoxMessage> sandBoxIncomingMessageQueue = new LinkedBlockingQueue<SandBoxMessage>();    
    
    // Should we use an executor for accept loop as the program will never quit?
    public final ExecutorService exService = Executors.newSingleThreadExecutor();
    
	public LocalObject(String nameOfProtocol, String addr) {
		protocolName = nameOfProtocol;
		address = addr;
        isDown = false;
	}

    // some protocols, like TCPIP may change address when client is moving so matchKey
    // function needs different implementations.
    public abstract Boolean matchKey(LocalKey key);
    // this function will also copy the source files to remote. The source files
	// are defined in the required libs.
    // returns null means succeed, otherwise fail.
	public abstract void connect(String remote, boolean reuseExisting) throws ErrProcessor.JFCALCExpErrException, IOException;
    
    public abstract void listen() throws ErrProcessor.JFCALCExpErrException, IOException;
    
    public abstract void accept() throws ErrProcessor.JFCALCExpErrException, IOException;
	
    // this function shuts down all the connections and server of the local.
    public void shutdown() {
        isDown = true;
        for(ConnectObject connObj : allInConnects.values()) {
            connObj.shutdown();
        }
        allInConnects.clear();
        for(ConnectObject connObj : allOutConnects.values()) {
            connObj.shutdown();
        }
        allOutConnects.clear();
        
        exService.shutdownNow();
    }
    
    public final LocalKey getLocalKey() {
        return new LocalKey(protocolName, address);
    }
}
