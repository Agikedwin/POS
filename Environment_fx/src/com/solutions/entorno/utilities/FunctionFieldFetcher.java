/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

/**
 *
 * @author shaddie
 */

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import java.io.InputStream;
import java.sql.SQLException;
public class FunctionFieldFetcher {
  
    public static Object getField(String table, String field){
        Object value=null;
        try {
            resultSet = statement.executeQuery("SELECT "+field+" FROM "+table+" ");
            if(resultSet.next())
                value = resultSet.getObject(1);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return value;
    }
     public static Object getField(String table, String field_name,String condition,String field){
        Object value=null;
        try {
            resultSet = statement.executeQuery("SELECT "+field+" FROM "+table+" Where "+field_name+"= '"+condition+"' ");
            if(resultSet.next())
                value = resultSet.getObject(1);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return value;
    }
    
     public InputStream getBlobs(String table, String field){
        InputStream value=null;
        try {
            resultSet = statement.executeQuery("SELECT "+field+" FROM "+table+" ");
            if(resultSet.next())
                value = resultSet.getBinaryStream(1);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return value;
    }
}
