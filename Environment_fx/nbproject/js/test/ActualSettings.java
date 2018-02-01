/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.classes;

import static com.solutions.entorno.utilities.SystemVariables.CLASSES_CONFIG_FILE;
import java.util.HashMap;
import static com.solutions.entorno.utilities.SystemVariables.CLASS_NAME;
import static com.solutions.entorno.utilities.SystemVariables.CONFIG_FOLDER;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_HOST;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_NAME;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_PASSWORD;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_PORT;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_URL;
import static com.solutions.entorno.utilities.SystemVariables.DATABASE_USER;
import static com.solutions.entorno.utilities.SystemVariables.SEARCH_VALUE;
import static com.solutions.entorno.utilities.SystemVariables.STREAMS_CONFIG_FILE;
/**
 *
 * @author shaddie
 */
public class ActualSettings {

    private ActualSettings() {
        HashMap<String, String> settings = SettingsParser.getInstance().getSettings(); 
        
        DATABASE_HOST = settings.get("dbmsHost");
        DATABASE_PORT = settings.get("dbmsPort");
        DATABASE_USER = settings.get("dbmsUser");
        DATABASE_PASSWORD = settings.get("dbmsPassword");
        DATABASE_NAME = settings.get("dbmsDatabase");
        CLASS_NAME = settings.get("className");
        DATABASE_URL = settings.get("dbmsUrl");
        SEARCH_VALUE = settings.get("searchValue");
        
        CONFIG_FOLDER = settings.get("configFolder");
        
        CLASSES_CONFIG_FILE = settings.get("classesConfigFile");
        STREAMS_CONFIG_FILE = settings.get("streamsConfigFile");
        }
    
    public static ActualSettings getInstance() {
        return SettingsHolder.INSTANCE;
    }

    private static class SettingsHolder {
        private static final ActualSettings INSTANCE = new ActualSettings();
    }
}
