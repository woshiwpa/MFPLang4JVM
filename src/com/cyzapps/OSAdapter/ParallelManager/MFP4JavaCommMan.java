/*
 * MFP project, MFP4JavaCommMan.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyzapps.OSAdapter.ParallelManager;

import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.Jfcalc.ErrProcessor.JFCALCExpErrException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author youxi
 */
public class MFP4JavaCommMan extends CommunicationManager {
    @Override
    public String getLocalHost(String protocolName, String additionalInfo) throws JFCALCExpErrException {
        if (protocolName.equals("TCPIP")) {
            try {
                InetAddress localhost = InetAddress.getLocalHost();
                String address = localhost.getHostAddress();
                if (additionalInfo != null && additionalInfo.trim().length() > 0) {
                    int port = Integer.parseInt(additionalInfo);
                    if (port <= 0 || port >= 65536) {
                        throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
                    }
                    address = address + ":" + port;
                }
                return address;
            } catch (UnknownHostException ex) {
                throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_UNKNOWN_HOST);
            } catch (NumberFormatException ex1) {
                throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
        } else {
            throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
        }
    }
    
    @Override
    public boolean setLocalAddess(String protocolName, String interfaceName, String[] addresses, String[][] additionalInfo) {
        return false;
    }
    
    @Override
    public Map<String, Map<String, Set<String>>> getAllAddresses(String protocolName) throws JFCALCExpErrException {
        Map<String, Map<String, Set<String>>> map2Ret = new HashMap<String, Map<String, Set<String>>>();
        if (protocolName.trim().length() == 0 || protocolName.equals("TCPIP")) {
            InetAddress candidateAddress = null;
            Map<String, Set<String>> ifaceAddrs = new HashMap<String, Set<String>>();
            // Iterate all NICs (network interface cards)..
            Enumeration ifaces;
            try {
                ifaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException ex) {
                throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INTERFACE_UNAVAILABLE);
            }
            for (; ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                String ifaceName = iface.getDisplayName();
                Set<String> addrSet = new HashSet<String>();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    String addr = inetAddr.getHostAddress();
                    addrSet.add(addr);
                }
                ifaceAddrs.put(ifaceName, addrSet);
            }
            map2Ret.put("TCPIP", ifaceAddrs);
        } else {
            throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
        }
        return map2Ret;
    }

    @Override
    public boolean generateLocal(LocalObject.LocalKey localInfo) { return true;}

    @Override
    public boolean initLocal(LocalObject.LocalKey localInfo, boolean reuseExisting) throws JFCALCExpErrException {
        // protocol and address are both case sensative
        if (localInfo.getProtocolName().equals("TCPIP")) {
            if (reuseExisting && existLocal(localInfo)) {
                return true;
            } else {
                TCPIPLocalMan tcpipLocalMan = new TCPIPLocalMan(localInfo.getLocalAddress());
                if (tcpipLocalMan.activate()){
                    allLocals.put(localInfo, tcpipLocalMan);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            throw new JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
        }
    }
    
    @Override
	public String serialize(Object sobj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(sobj);
        oos.close();
        String s = Base64.getEncoder().encodeToString(baos.toByteArray());
        return s;
	}
	
    @Override
	public Object deserialize(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o  = ois.readObject();
        ois.close();
        return o;
	}
}
