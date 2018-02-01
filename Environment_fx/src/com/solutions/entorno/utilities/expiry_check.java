/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;


import com.solutions.entorno.database.Database2Connection;
import static com.solutions.entorno.utilities.SystemFunctions.decrypt;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ONCHABA
 */
public class expiry_check {
  
    public static long expiry(){       
        long explevel=0;
         Connection con = new Database2Connection().getConnectionvalue();
        Statement st =  new Database2Connection().getStatementvalue(con);
        try {
           
               ResultSet rs =st.executeQuery("SELECT * FROM studentstable");
             rs.next(); 
             String expdata =decrypt(rs.getString(1));
             DateFormat format = new SimpleDateFormat("yy-M-dd", Locale.ENGLISH);
            Date expday = format.parse(expdata);
            resultSet=statement.executeQuery("SELECT CURRENT_DATE as dat");
            resultSet.next();
            Date tday=resultSet.getDate("dat");
            explevel=(tday.getTime()-expday.getTime())/(1000*60*60*24);
           } catch (SQLException ex) {
           ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(expiry_check.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
                st.close();
            } catch (Exception e) {
            }
        }
        return explevel;
    }
}
