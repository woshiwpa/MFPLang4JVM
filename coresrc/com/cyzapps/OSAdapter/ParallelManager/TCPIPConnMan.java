/*
 * MFP project, TCPIPConnMan.java : Designed and developed by Tony Cui in 2021
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
.*/

 package com.cyzapps.OSAdapter.ParallelManager;
import com.cyzapps.Jfcalc.ErrProcessor;
import com.cyzapps.OSAdapter.ParallelManager.ConnectObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author youxi
 */
public class TCPIPConnMan extends ConnectObject {

    public static String getIPAddress(String connPntInfoAddr) throws ErrProcessor.JFCALCExpErrException {
        String[] ipAddrPort = connPntInfoAddr.split(":");
        String ipAddr = ipAddrPort[0].trim();
        if (ipAddr.equals("localhost")) {
            ipAddr = "127.0.0.1";
        }
        Boolean isValidIPAddr = true;
        String groups[] = ipAddr.split("\\.");
        if (groups.length != 4) {
            isValidIPAddr = false;
        } else for (String part : groups) {
            try {
                int partAddr = Integer.parseInt(part);
                if (partAddr < 0 || partAddr > 255) {
                    isValidIPAddr = false;
                    break;
                }
            } catch(Exception e) {
                isValidIPAddr = false;
                break;
            }
        }
        if (!isValidIPAddr) {
            throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);            
        }
        return ipAddr;
    }
    
    public static int getIPPort(String connPntInfoAddr) throws ErrProcessor.JFCALCExpErrException {
        String[] ipAddrPort = connPntInfoAddr.split(":");
        String ipPort = "0";   // port == 0 means system will pick up an ephemeral port for you.
        if (ipAddrPort.length > 1) {
            ipPort = ipAddrPort[1].trim();
        }
        int port = 0;
        try {
            port = Integer.parseInt(ipPort);
            if (port < 0 || port > 65535) {
                throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
            }
        } catch(Exception e) {
            throw new ErrProcessor.JFCALCExpErrException(ErrProcessor.ERRORTYPES.ERROR_INVALID_PARAMETER);
        }
        return port;
    }
    
    protected Socket socket = null; // can be either client socket or server socket
    
    protected String localIPAddr = "";
    protected int localIPPort = 0;
    
    protected String remoteIPAddr = "";
    public String getRemoteIPAddr() {
        return remoteIPAddr;
    }
    protected int remoteIPClientPort = 0; // remote IP client port, for server side TCPIP Conn only
    public int getRemoteIPPort() {
        return remoteIPClientPort;
    }
    public static final int REMOTE_LISTEN_PORT = 62512;
    
    protected AtomicInteger currentCallPoint = new AtomicInteger();
    
    public TCPIPConnMan(LocalObject po, Boolean isIn, String addr, ConnectSettings config, ConnectAdditionalInfo addInfo)
                throws ErrProcessor.JFCALCExpErrException {
        super(po, isIn, addr, config, addInfo);
        localIPAddr = getIPAddress(po.getAddress());
        localIPPort = getIPPort(po.getAddress());
        remoteIPAddr = addr;
    }
    
    @Override
    public void shutdown() {
        super.shutdown();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                /* failed, but doesn't matter */
            } finally {
                socket = null;
            }
        }
    }
    
    public void activate() throws IOException {
        socket = new Socket();
        socket.bind(new InetSocketAddress(localIPAddr, localIPPort));
        Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": bind " + localIPAddr + ":" + localIPPort);

        socket.connect(new InetSocketAddress(remoteIPAddr, REMOTE_LISTEN_PORT));
        Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": connect " + remoteIPAddr + ":" + REMOTE_LISTEN_PORT);
    }
    
    public void activate(ServerSocket serverSocket) throws IOException {
        Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": Before serverSocket accept");
        socket = serverSocket.accept();
        // update remote (client) IP address and port
        InetSocketAddress remoteSocketAddr = (InetSocketAddress)socket.getRemoteSocketAddress();
        // update address, which is also remote ip address (port is not included).
        // note that remote address will be the key of this incoming connection in the inconnect map.
        address = remoteIPAddr = remoteSocketAddr.getAddress().getHostAddress();
        remoteIPClientPort = remoteSocketAddr.getPort();
        Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": After serverSocket accept");
    }

    // we dont want two sendCallRequest interlaced. So this function has to be synchronized.
    @Override
    public synchronized boolean sendCallRequest(CallObject callObj, String cmd, String cmdParam, String content) {
        ObjectOutputStream oOut = null;
        PackedCallRequestInfo packedCallRequestInfo = new PackedCallRequestInfo();
        packedCallRequestInfo.destCallPoint = callObj.remoteCallPoint;
        packedCallRequestInfo.cmd = cmd;
        packedCallRequestInfo.cmdParam = cmdParam;
        packedCallRequestInfo.content = content;
        boolean bRet = false;
        if (socket == null) {
            Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": socket is null which means it hasn't been initialized or has been destroyed, return!");
        } else {
            try {
                //TCP IP send
                oOut = new ObjectOutputStream(socket.getOutputStream());
                oOut.writeObject(packedCallRequestInfo);
                oOut.flush();
                bRet = true;
            } catch (IOException ex) {
                Logger.getLogger(TCPIPConnMan.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                /* // we shouldn't close oOut because this will shutdown socket.
                try {
                    if (oOut != null) {
                        oOut.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TCPIPConnMan.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
            }
        }
        return bRet;
    }    

    // receiveCallRequest is started when connection is created. No need to be synchronized
    // and cannot be synchronized.
    @Override
	public PackedCallRequestInfo receiveCallRequest() {
        ObjectInputStream objectInputStream = null;
        PackedCallRequestInfo info = null;
        try {
            if (socket == null) {
                Logger.getLogger(TCPIPConnMan.class.getName()).info(Thread.currentThread() + ": socket is null which means it hasn't been initialized or has been destroyed, return!");
                return null;
            }
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            info = (PackedCallRequestInfo) objectInputStream.readObject();
            // ok, now we have the call request info.
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TCPIPConnMan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /* // we shouldn't close objectInputStream because this will shutdown socket.
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TCPIPConnMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        }
        return info;
    }
}
