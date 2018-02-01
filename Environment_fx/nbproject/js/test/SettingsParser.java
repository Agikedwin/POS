/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.classes;

import static com.solutions.entorno.utilities.SystemVariables.SETTINGS_PATH;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
/**
 *
 * @author shaddie
 */
public class SettingsParser {
    
    private HashMap<String, String> settings;
    
    public  SettingsParser(){
      init();
        setSettings();
    }
    public static SettingsParser getInstance() {
        return SettingsParserHolder.INSTANCE;
    }

    private static class SettingsParserHolder {

        private static final SettingsParser INSTANCE = new SettingsParser();
    }

    public HashMap getSettings() {
        return settings;
    }

    private void init() {
        settings = new HashMap<>();
        settings.put("dbmsHost", "");
        settings.put("dbmsPort", "");
        settings.put("dbmsUser", "");
        settings.put("dbmsPassword", "");
        settings.put("dbmsDatabase", "");
        settings.put("className", "");
        settings.put("dbmsUrl", "");
        settings.put("searchValue","");
        
         settings.put("configFolder", "");
         
         
         settings.put("classesConfigFile","");
          settings.put("streamsConfigFile","");
        /*
        settings.put("emailSSLPort", "");
        settings.put("rmiServerHost", "");
        settings.put("rmiServerPort", "");
        settings.put("ftpServerHost", "");
        settings.put("ftpServerPort", "");
        settings.put("abisJarPath","");
        settings.put("abisBinPath","");
        settings.put("ftpServerUser", "");
        settings.put("ftpServerPassword", "");
        */
        //get system folders
       
        /*
        settings.put("facesFolder", "");
        settings.put("palmsFolder", "");
        settings.put("voicesFolder", "");
        settings.put("irisesFolder", "");
        settings.put("drivingLicenseFolder", "");
        settings.put("nationalIdFolder", "");
        settings.put("passportFolder", "");
        settings.put("workPermitFolder", "");
        settings.put("systemLogsFolder", "");
        settings.put("userLogsFolder", "");
        settings.put("settingsFolder", "");
        settings.put("fileBackupFolder", "");
        settings.put("databaseBackupFolder", "");
        settings.put("uploadFolder", "");
        settings.put("reportsFolder", "");
*/

    }

    private void setSettings() {
        try {
            File settingsFile = new File(SETTINGS_PATH);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(settingsFile);
            document.getDocumentElement().normalize();
            XmlHandler.handleSettingsTag(document, getSettings());            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error encountered", JOptionPane.ERROR_MESSAGE);
        }
    }

}
