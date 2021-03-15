// MFP project, MFPAppConfig.java : Designed and developed by Tony Cui in 2021
package jcmdline;

import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MFPAppConfig {

    public static String escapeXMLSpecialChar(String strInput) {
        String strOutput = null;
        if (strInput != null) {
            strOutput = strInput.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("'", "&apos;")
                    .replaceAll("\"", "&quot;");
        }
        return strOutput;
    }

    public static String escapeStringSpecialChar(String strInput) {
        String strOutput = null;
        if (strInput != null) {
            strOutput = "";
            for (int idx = 0; idx < strInput.length(); idx++) {
                if (strInput.charAt(idx) == '"') {
                    strOutput += "\\\"";
                } else if (strInput.charAt(idx) == '\\') {
                    strOutput += "\\\\";
                } else {
                    strOutput += strInput.charAt(idx);
                }
            }
        }
        return strOutput;
    }

    public static String getNodeStringValue(Node node) {
        return (node.getChildNodes().getLength() == 0) ? "" : node.getChildNodes().item(0).getNodeValue();
    }

    public static class MFPAppCfgParameter {

        public String mstrPrompt = "";
        public boolean mbIsString = false;
        public boolean mbNeedsInput = true;
        public String mstrDefaultValue = "";

        public static MFPAppCfgParameter readFromXMLNode(Node nodeParam) {
            MFPAppCfgParameter param = new MFPAppCfgParameter();
            if (nodeParam != null && nodeParam.getNodeType() == Node.ELEMENT_NODE
                    && nodeParam.getNodeName().equals("parameter")) {
                NodeList listOfNodes = nodeParam.getChildNodes();
                int index = 0;
                while (index < listOfNodes.getLength()) {
                    Node node = listOfNodes.item(index);
                    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("prompt")) {
                        param.mstrPrompt = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("isString")) {
                        String strIsString = MFPAppConfig.getNodeStringValue(node);
                        if (strIsString != null && strIsString.trim().equalsIgnoreCase("true")) {
                            param.mbIsString = true;
                        } else {
                            param.mbIsString = false;
                        }
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("needsInput")) {
                        String strNeedsInput = MFPAppConfig.getNodeStringValue(node);
                        if (strNeedsInput != null && strNeedsInput.trim().equalsIgnoreCase("true")) {
                            param.mbNeedsInput = true;
                        } else {
                            param.mbNeedsInput = false;
                        }
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("defaultValue")) {
                        param.mstrDefaultValue = MFPAppConfig.getNodeStringValue(node);
                    }
                    index++;
                }
            }
            return param;
        }

        public String convertToXMLString() {
            String strXML = "<parameter>\n";
            strXML += "<prompt>" + escapeXMLSpecialChar(mstrPrompt) + "</prompt>\n";
            strXML += "<isString>" + (mbIsString ? "TRUE" : "FALSE") + "</isString>\n";
            strXML += "<needsInput>" + (mbNeedsInput ? "TRUE" : "FALSE") + "</needsInput>\n";
            strXML += "<defaultValue>" + escapeXMLSpecialChar(mstrDefaultValue) + "</defaultValue>\n";
            strXML += "</parameter>\n";
            return strXML;
        }
    }

    public static class FunctionInfo {

        public String mstrName = "";
        public String mstrDescription = "";
        public boolean mbIncludesOptionalParam = false;
        public LinkedList<MFPAppCfgParameter> mlistParams = new LinkedList<MFPAppCfgParameter>();

        public static FunctionInfo readFromXMLNode(Node nodeFunctionInfo) {
            FunctionInfo functionInfo = new FunctionInfo();
            if (nodeFunctionInfo != null && nodeFunctionInfo.getNodeType() == Node.ELEMENT_NODE
                    && nodeFunctionInfo.getNodeName().equals("functionInfo")) {
                NodeList listOfNodes = nodeFunctionInfo.getChildNodes();
                int index = 0;
                while (index < listOfNodes.getLength()) {
                    Node node = listOfNodes.item(index);
                    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("name")) {
                        functionInfo.mstrName = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("description")) {
                        functionInfo.mstrDescription = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("includesOptionalParam")) {
                        String strIncludesOptionalParam = MFPAppConfig.getNodeStringValue(node);
                        if (strIncludesOptionalParam != null && strIncludesOptionalParam.trim().equalsIgnoreCase("true")) {
                            functionInfo.mbIncludesOptionalParam = true;
                        } else {
                            functionInfo.mbIncludesOptionalParam = false;
                        }
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("parameters")) {
                        functionInfo.mlistParams = new LinkedList<MFPAppCfgParameter>();
                        NodeList listOfParams = node.getChildNodes();
                        int idx1 = 0;
                        while (idx1 < listOfParams.getLength()) {
                            Node nodeParam = listOfParams.item(idx1);
                            if (nodeParam.getNodeType() == Node.ELEMENT_NODE && nodeParam.getNodeName().equals("parameter")) {
                                MFPAppCfgParameter param = MFPAppCfgParameter.readFromXMLNode(nodeParam);
                                functionInfo.mlistParams.add(param);
                            }
                            idx1++;
                        }
                    }
                    index++;
                }
            }
            return functionInfo;
        }

        public String convertToXMLString() {
            String strXML = "<functionInfo>\n";
            strXML += "<name>" + escapeXMLSpecialChar(mstrName) + "</name>\n";
            strXML += "<description>" + escapeXMLSpecialChar(mstrDescription) + "</description>\n";
            strXML += "<includesOptionalParam>" + (mbIncludesOptionalParam ? "TRUE" : "FALSE") + "</includesOptionalParam>\n";
            strXML += "<parameters>\n";
            for (int idx = 0; idx < mlistParams.size(); idx++) {
                strXML += mlistParams.get(idx).convertToXMLString();
            }
            strXML += "</parameters>\n";
            strXML += "</functionInfo>\n";
            return strXML;
        }
    }

    public String mstrApplicationName = "";

    public String mstrApplicationPkgName = "";

    public String mstrApplicationWorkingFolder = "";

    public String mstrApplicationVerStr = "1.0.0.0001";

    public int mnApplicationVerCode = 1;

    public FunctionInfo mfunctionInfo = new FunctionInfo();

    public String mstrHelpInfo = "";

    public static MFPAppConfig readMFPAppCfgFromXML(InputStream is) {
        Document doc;
        MFPAppConfig mfpAppConfig = new MFPAppConfig();
        if (is != null) {
            try {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                doc = docBuilder.parse(is);

                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfApplicationChildren = doc.getElementsByTagName("application").item(0).getChildNodes();
                for (int idx = 0; idx < listOfApplicationChildren.getLength(); idx++) {
                    Node node = listOfApplicationChildren.item(idx);
                    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("applicationName")) {
                        mfpAppConfig.mstrApplicationName = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("applicationPkgName")) {
                        mfpAppConfig.mstrApplicationPkgName = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("applicationWorkingFolder")) {
                        mfpAppConfig.mstrApplicationWorkingFolder = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("applicationVersionStr")) {
                        mfpAppConfig.mstrApplicationVerStr = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("applicationVersionCode")) {
                        String strAppVerCode = MFPAppConfig.getNodeStringValue(node);
                        try {
                            mfpAppConfig.mnApplicationVerCode = Integer.parseInt(strAppVerCode);
                        } catch (NumberFormatException e) {
                            // do nothing.
                        }
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("helpInfo")) {
                        mfpAppConfig.mstrHelpInfo = MFPAppConfig.getNodeStringValue(node);
                    } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("functionInfo")) {
                        mfpAppConfig.mfunctionInfo = FunctionInfo.readFromXMLNode(node);
                    }
                }
            } catch (Exception e) {
            }
        }

        return mfpAppConfig;
    }

    public String convertToXMLString() {
        String strXML = "<application>\n";
        strXML += "<applicationName>" + escapeXMLSpecialChar(mstrApplicationName) + "</applicationName>\n";
        strXML += "<applicationPkgName>" + escapeXMLSpecialChar(mstrApplicationPkgName) + "</applicationPkgName>\n";
        strXML += "<applicationWorkingFolder>" + escapeXMLSpecialChar(mstrApplicationWorkingFolder) + "</applicationWorkingFolder>\n";
        strXML += "<applicationVersionStr>" + escapeXMLSpecialChar(mstrApplicationVerStr) + "</applicationVersionStr>\n";
        strXML += "<applicationVersionCode>" + mnApplicationVerCode + "</applicationVersionCode>\n";
        strXML += mfunctionInfo.convertToXMLString();
        strXML += "<helpInfo>" + escapeXMLSpecialChar(mstrHelpInfo) + "</helpInfo>\n";
        strXML += "</application>\n";
        return strXML;
    }

    public boolean hasNothing2Input() {
        for (int idx = 0; idx < mfunctionInfo.mlistParams.size(); idx++) {
            if (mfunctionInfo.mlistParams.get(idx).mbNeedsInput) {
                return false;
            }
        }
        return true;
    }

    public boolean hasNothing2ShowOrInput() {
        if (mfunctionInfo != null && mfunctionInfo.mstrDescription != null
                && mfunctionInfo.mstrDescription.trim().length() > 0) {
            return false;
        }
        return hasNothing2Input();
    }

    public String getDefaultCmdStr() {
        String str2Run = mfunctionInfo.mstrName + "(";
        LinkedList<String> listAllParams = new LinkedList<String>();
        for (int idx = 0; idx < mfunctionInfo.mlistParams.size(); idx++) {
            MFPAppCfgParameter param = mfunctionInfo.mlistParams.get(idx);
            if (!mfunctionInfo.mbIncludesOptionalParam || idx != mfunctionInfo.mlistParams.size() - 1) {
                String strThisParam = "";
                if (param.mbIsString) {
                    strThisParam += "\"";
                    strThisParam += escapeStringSpecialChar(param.mstrDefaultValue);
                    strThisParam += "\"";
                } else {
                    strThisParam += param.mstrDefaultValue;
                }
                strThisParam = strThisParam.trim();
                listAllParams.add(strThisParam);
            }
        }
        for (int idx = 0; idx < listAllParams.size(); idx++) {
            str2Run += (idx > 0) ? (", " + listAllParams.get(idx)) : listAllParams.get(idx);
        }
        str2Run += ")";
        return str2Run;
    }
}
