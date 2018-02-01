/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers.utilities;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.pos.controllers.utilities.PosVariables.POS_CODES_TABLE;

/**
 *
 * @author shaddie
 */
public class FunctionGenerateEntityCode {
    public static int generatedCode(String table_name){
        int code = 0;
        try {
            resultSet = statement.executeQuery("select next_code from "+POS_CODES_TABLE+" where table_name = '"+table_name+"'");
            if(resultSet.next()){
             code=  resultSet.getInt(1);
            }
            if(code>0){
                statement.executeUpdate("update "+POS_CODES_TABLE+" set next_code = "+(code+1)+" where table_name = '"+table_name+"' ");
            }
            } catch (Exception e) {
                e.printStackTrace();
        }
        return code;
    }
}
