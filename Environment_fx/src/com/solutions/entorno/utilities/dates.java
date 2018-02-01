/*
 * To change this license header, choose License HeaderesultSet in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
public class dates {
       public static String thedate(){
        
        String serverdate=null;
        try {
          resultSet=statement.executeQuery("SELECT CURRENT_DATE as dat");
            resultSet.next();
            serverdate=resultSet.getString("dat");
            } catch (Exception e) {
           
        }
       
        return serverdate;
    }
    
    public static String thetime(){
      String servertime=null;
        try {
           

  
            resultSet=statement.executeQuery("SELECT CURRENT_TIME(0) as tym");
            resultSet.next();
            servertime=resultSet.getString("tym");
           } catch (Exception e) {
           
        }
        return servertime;  
    } 
    public static String  getTimeMills(){
        return SYSTEM_DATE;
    } 
    public static String convertDateFormat(Date date)
    {
        String converted = null; 
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
               converted = format.format(date);
           
           return converted;
    }
}
