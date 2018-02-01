/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.security.*;

import javax.crypto.*;

import sun.misc.*;
import com.solutions.entorno.database.Database2Connection;
import static com.solutions.entorno.utilities.dates.getTimeMills;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author shaddie
 */
public class SystemFunctions {
    
    public static int widths(){
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      return screenSize.width;
      }
      public static int heights(){
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      return screenSize.height;
      }
      public static Dimension dimension(){
         return Toolkit.getDefaultToolkit().getScreenSize();
      }
       public static void PrintDocuments(String filename){
                try {
            FileInputStream  textStream = new FileInputStream(filename);
            
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc mydoc = new SimpleDoc(textStream, flavor, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(
                    flavor, aset);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
                  if(services.length != 0) {               
                if(defaultService == null) {                    
                } else {
                    //print using default
                    DocPrintJob job = defaultService.createPrintJob();
                    job.print(mydoc, aset);
                    }                
            } 
            else {
                PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultService, flavor, aset);
                
                if (service != null)
                {
                    DocPrintJob job = service.createPrintJob();
                    job.print(mydoc, aset);
                }
                else{
                    
                }
                }
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), " Error Encountered", JOptionPane.ERROR_MESSAGE);
        }
        catch(PrintException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), " Error Encountered", JOptionPane.ERROR_MESSAGE);
        }
        }
        public static String OneWayDataSecurity(String inputString){    
        String outputString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputString.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            outputString = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), " Error Encountered", JOptionPane.ERROR_MESSAGE);
        }  
          return outputString;
      }
         public static boolean registered(){
            boolean reg=false;
            Connection con = new Database2Connection().getConnectionvalue();
        Statement stm =  new Database2Connection().getStatementvalue(con);
        ResultSet rs = null;
               try {                   
                  rs=stm.executeQuery("SELECT * FROM studentstable");
                   rs.next();
                   String state=rs.getString(2);
                   if(state.equals("c0d83f0b82a6b30de8811e69e6d95c61"))
            {
                   reg=true;
            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    
                }
               finally{
                try {
                    rs.close();
                    stm.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SystemFunctions.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
               return reg;
        }
         
         public static File pathname(){
         return new File((System.getenv("USERPROFILE"))+("\\AppData\\Roaming\\NetBeans\\java\\solutions"));
     }
       public static File reportspath(String folder){
         return new File((System.getenv("USERPROFILE"))+("\\My Documents\\"+folder));
     }
         
     public static int returnRecordCount(){
       int rec = -1;
        Connection con = new Database2Connection().getConnectionvalue();
        Statement stm =  new Database2Connection().getStatementvalue(con);
         ResultSet rs =null;
         try {
          rs = stm.executeQuery("Select count(*) from studentstable");
            rs.next();
            rec = rs.getInt(1);
        } catch (SQLException ex) {
         }
         finally{
             try {
                 rs.close();
                  stm.close();
                 con.close();
                } catch (Exception e) {
             }
         }
      return rec;  
     }
     public static String getSerialNumber()
     {
         String no=null;
    try {
    Process process = Runtime.getRuntime().exec("WMIC BIOS GET SerialNumber"); 
    process.getOutputStream().close(); //Closing output stream of the process
    String s = null;
   
    int n=0;
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    while ((s = reader.readLine()) != null) {
        n++;
        if(n==3){
        no=s;
        }
    } 
  } catch (IOException e) {
   e.printStackTrace(); 
  }
  return no;
     }
      public static boolean recordCountPopulator(){
       boolean rec =false;
        Connection con = new Database2Connection().getConnectionvalue();
        Statement stm =  new Database2Connection().getStatementvalue(con);
         try {           
           stm.executeUpdate("INSERT INTO studentstable VALUES('"+encrypt(getTimeMills())+"','946003f97ccc52d5d3b54ac0ec31bbfc','f6a32eccddd45a8e92bef3df98a4a87b')");
           rec = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
         }
         finally{
             try {
                  stm.close();
                 con.close();
             } catch (Exception e) {
             }
         }
      return rec;  
     }
      public static String encrypt(String Data) {
      String encryptedValue = null;
          try {          
          String ALGO = "AES";
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
       encryptedValue = new BASE64Encoder().encode(encVal);
            } catch (Exception e) {
          }
        return encryptedValue;
    }

   public  static String decrypt(String encryptedData)  {
       String decryptedValue = null;
         try{
              String ALGO = "AES";
         Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        decryptedValue = new String(decValue);            
          } catch (Exception e) {
          }
        return decryptedValue;
    }
    public static Key generateKey()  {
     String ALGO = "AES";
       Key key = null;
       try{
           
    byte[] keyValue =  new byte[] { 'W', 'e', 'S', 'P', 'e', 'K', 't',
'S', '3', 'c', '%','e', 't', 'D', '@', 'y'  };          
     key = new SecretKeySpec(keyValue, ALGO);
       } catch (Exception e) {
          }
        return key;
}
    public static boolean isStaleCopy(){
        boolean stale = true;
        if(expiry_check.expiry()<=15 && expiry_check.expiry()>=0 )
        {
         stale = false;
        }  
        return stale;
    }
    public static void openfile(String path){
          if (Desktop.isDesktopSupported()) {
                    try {                        
                        File myFile = new File(path);
                        Desktop.getDesktop().open(myFile);                        
                    } catch (IOException ex) {  
                        ex.printStackTrace();
                      }
                }
      }
}
