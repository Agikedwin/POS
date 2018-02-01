/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.awt.event.KeyEvent;

/**
 *
 * @author shaddie
 */
public class SystemValidations {
    public static void alphaValidation(KeyEvent evt){
        char c= evt.getKeyChar();
             if(!Character.isAlphabetic(c) && c!='\'' && !Character.isWhitespace(c) ){
              evt.consume();
               }
     }
    public static void phoneValidation(KeyEvent evt){
        char c= evt.getKeyChar();
             if(!Character.isDigit(c) && c!='+' && !Character.isWhitespace(c) ){
              evt.consume();
               }
     }
    public static void numericValidation(KeyEvent evt){
        char c= evt.getKeyChar();
        if(!Character.isDigit(c)&& !Character.isWhitespace(c) ){
          evt.consume();
        }
    }
    public static boolean emailValidation(String email){
        boolean valid = false;
        if(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            valid = true;
        }
        return valid;
    }
    public static boolean phoneValidation(String phoneno){
        boolean valid = false;
        if(phoneno.matches("[0-9 +][0-9]*")){
            valid = true;
        }
        return valid;
    }
    public static boolean passwordValidation(String password){
       
                 boolean valid = false;
        if(password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")){
            valid = true;
        }
        return valid;
    }
    public static boolean usernameValidation(String username){
       
                 boolean valid = false;
        if(username.matches("^[a-z0-9_-]{3,15}$")){
            valid = true;
        }
        return valid;
    }
}
