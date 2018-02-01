/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities;

import com.solutions.entorno.utilities.dialogs.ShowMessages;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author shaddie
 */
public class SystemVariables {
        //name of database to connect to
 public static String DATABASE2_NAME = null;
    //name of database to connect to
 public static String DATABASE_NAME = null;
 //database username
 public static String DATABASE_USER = null;
 //database password
 public static String DATABASE_PASSWORD = null;
  //database host
 public static String DATABASE_HOST = null;
 //database port
 public static String DATABASE_PORT = null;
 //database class name
 public static String CLASS_NAME = null;
 
 //database url
 public static String DATABASE_URL = null;
 
 public static String CONFIG_FOLDER = null;
 
  public static String REPORT_FOLDER = null;
 
  public static String CLASSES_CONFIG_FILE = null;
  
  public static String STREAMS_CONFIG_FILE = null;
 
 public static Connection connection = null;
 
 public static Statement statement = null;
 
 public static ResultSet resultSet = null;
 
 public static PreparedStatement preparedStatement = null;
 
 public static String SYSTEM_DATE = null;
 
 public static String SYSTEM_TIME = null;
  
 public static String SETTINGS_PATH = null;
 
  public static String SETTINGS_FILE= null;
 
 public static String SEARCH_VALUE = null;
 
 public static ArrayList<Integer> SYSTEM_YEARS = new ArrayList<>();
 
 public static ArrayList<Integer> SYSTEM_CLASSES = new ArrayList<>();
 
  public static ArrayList<Object> SYSTEM_CLASSES_NAMES = new ArrayList<>();
 
 public static ArrayList<Object> SYSTEM_STREAMS = new ArrayList<>();
 
 public static ArrayList<String> SYSTEM_PUPIL_CATEGORIES = new ArrayList<>();
 
public static ArrayList<String> SYSTEM_STUDENT_CATEGORIES = new ArrayList<>();
  
 public static Color THEME_COLOR = null;
  
 public static String USER_PROFILE = null;
    
  public static int OPEN_YEAR;
     
  public static int OPEN_TERM;
  
  public static String OPENING_DATE;
  
  public static String CLOSING_DATE;
  
  public static String INSTITUTION_NAME = null;
 
  public static String INSTITUTION_MOTTO = null;
  
  public static String INSTITUTION_ADDRESS = null;
  
  public static String INSTITUTION_EMAIL = null;
  
  public static String INSTITUTION_PHONE = null;
  
  public static Image INSTITUTION_LOGO = null; 
  
  public static String INSTITUTION_KRA = null;
 
  public static String INSTITUTION_ETR = null;
  
   public static ShowMessages DISPLAY_MESSAGE= null;
   
   public static Image SYSTEM_IMAGE = null;
}
