/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.classes;


import com.solutions.entorno.database.DatabaseConnection;
import com.solutions.entorno.utilities.SystemColorSettings;
import static com.solutions.entorno.utilities.SystemVariables.CONFIG_FOLDER;
import static com.solutions.entorno.utilities.SystemVariables.SETTINGS_FILE;
import static com.solutions.entorno.utilities.SystemVariables.SETTINGS_PATH;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_YEARS;
import static com.solutions.entorno.utilities.SystemVariables.THEME_COLOR;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.entorno.utilities.dates.thedate;
import com.solutions.entorno.utilities.dialogs.ShowMessages;
import java.awt.Color;
/**
 *
 * @author shaddie
 */
public class MainClass {
    private static  MainClass INSTANCE =null;
public static MainClass getInstance()
{
    if(INSTANCE==null){
         INSTANCE = new MainClass();
    }
    return INSTANCE;
}
   private  MainClass(){
       String configPath = System.getProperty("user.dir")+"/config";
       if(SETTINGS_FILE == null){
           SETTINGS_FILE  = System.getProperty("CONF","settings.xml");
       }
      SETTINGS_PATH = configPath+"/"+SETTINGS_FILE; 
       
       ActualSettings.getInstance();
       
       CONFIG_FOLDER = configPath+"/";
               
       int years[] = {2014,2015};
       for(int year : years){
           SYSTEM_YEARS.add(year); 
       }
         connection=DatabaseConnection.getConnection();
          statement=DatabaseConnection.getStatement(connection);
          SYSTEM_DATE = thedate();
           int color[]=new SystemColorSettings().SetCurrentBackground();
            THEME_COLOR = new Color(color[0],color[1],color[2]);
          com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE = new ShowMessages();
   }
   
}
