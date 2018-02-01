/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import static com.solutions.entorno.utilities.SystemVariables.CLASS_NAME;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import java.util.logging.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.jdbc.JDBCAppender;

/**
 *
 * @author shaddie
 */
public class SystemLogGenerator {
    
    public static Logger dbLog (){
      Logger log = Logger.getLogger("databse Log");
       databaseAppender().setSql("INSERT INTO user_logs VALUES('"+USER_PROFILE+"','%d','%C','%p','%m')");
       //log.addAppender(databaseAppender());
       return log;
    }
    
    
    
    public static   JDBCAppender databaseAppender(){
        JDBCAppender dbAppender = new JDBCAppender();
        dbAppender.setDriver(CLASS_NAME);
        dbAppender.setPassword(SystemVariables.DATABASE_PASSWORD);
        dbAppender.setUser(SystemVariables.DATABASE_USER);
        dbAppender.setURL(SystemVariables.DATABASE_URL);
        dbAppender.setLayout(new PatternLayout());
        return dbAppender;
    }
    
}
