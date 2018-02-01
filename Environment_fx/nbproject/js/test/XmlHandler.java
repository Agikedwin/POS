/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.classes;

import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author shaddie
 */
public class XmlHandler {
    
     private static NodeList dbmsNodeList, foldersNodeList, configNodeList, rmiServerNodeList, mysqldumpNodeList, ftpServerNodeList, abisNodeList;


    public static void handleSettingsTag(Document document, HashMap settings) {
        dbmsNodeList = document.getElementsByTagName("server");
       handleDBMSTag(dbmsNodeList, settings);
       
        foldersNodeList = document.getElementsByTagName("folders");
       handleFolderSettingsTag(foldersNodeList, settings);
       
       configNodeList = document.getElementsByTagName("configfiles");
        handleCongigFilesSettingsTag(configNodeList, settings);
        
         /* rmiServerNodeList = document.getElementsByTagName("rmiServer");
        handleRmiServerSettingsTag(rmiServerNodeList, settings);
        mysqldumpNodeList = document.getElementsByTagName("mysqldump");
        handleMysqldumpSettingsTag(mysqldumpNodeList, settings);
        ftpServerNodeList = document.getElementsByTagName("ftpServer");
        handleFtpServerSettingsTag(ftpServerNodeList, settings);
        abisNodeList = document.getElementsByTagName("abis");
        handleAbisSettingsTag(abisNodeList, settings);
        serverFoldersNodeList = document.getElementsByTagName("serverFolders");
       handleServerFolderSettingsTag(serverFoldersNodeList, settings);
*/
    }

    public static void handleDBMSTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("dbmsHost", getTagText("host", element));
            settings.put("dbmsPort", getTagText("port", element));
            settings.put("dbmsUser", getTagText("user", element));
            settings.put("dbmsPassword", getTagText("password", element));
            settings.put("dbmsDatabase", getTagText("database", element));
            settings.put("className", getTagText("class", element));
            settings.put("dbmsUrl", getTagText("url", element));
            settings.put("searchValue", getTagText("searchValue", element));
            
            }
    }

    public static void handleCongigFilesSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("classesConfigFile", getTagText("classes", element));
             settings.put("streamsConfigFile", getTagText("streams", element));
            }

    }

    public static void handleRmiServerSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("rmiServerHost", getTagText("host", element));
            settings.put("rmiServerPort", getTagText("port", element));
        }

    }

    public static void handleFtpServerSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("ftpServerHost", getTagText("host", element));
            settings.put("ftpServerPort", getTagText("port", element));
            settings.put("ftpServerUser", getTagText("user", element));
            settings.put("ftpServerPassword", getTagText("password", element));
        }

    }

    public static void handleFolderSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("configFolder", getTagText("config", element));
            /* 
            settings.put("facesFolder", getTagText("facesFolder", element));
            settings.put("irisesFolder", getTagText("irisesFolder", element));
            settings.put("palmsFolder", getTagText("palmsFolder", element));
            settings.put("voicesFolder", getTagText("voicesFolder", element));
            settings.put("drivingLicenseFolder", getTagText("drivingLicenseFolder", element));
            settings.put("nationalIdFolder", getTagText("nationalIdFolder", element));
            settings.put("passportFolder", getTagText("passportFolder", element));
            settings.put("workPermitFolder", getTagText("workPermitFolder", element));
            settings.put("systemLogsFolder", getTagText("systemLogsFolder", element));
            settings.put("userLogsFolder", getTagText("userLogsFolder", element));
            settings.put("settingsFolder", getTagText("settingsFolder", element));
            settings.put("databaseBackupFolder", getTagText("databaseBackupFolder", element));
            settings.put("fileBackupFolder", getTagText("fileBackupFolder", element));   
            settings.put("uploadFolder", getTagText("uploadFolder", element));
            settings.put("reportsFolder", getTagText("reportsFolder", element));
                    */ 
        }

    }

    public static void handleMysqldumpSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("mysqldumpPath", getTagText("path", element));
        }

    }
    public static void handleAbisSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("abisJarPath", getTagText("jarPath", element));
            settings.put("abisBinPath", getTagText("binPath", element));            
        }

    }
    private static String getTagText(String tagName, Element element) {
        Node nodeValue = element.getElementsByTagName(tagName).item(0).getFirstChild();
        return nodeValue == null ? "" : nodeValue.getNodeValue();
    }
}
