/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author shaddie
 */
public class OneValueCsvFileReader {
   
    public static OneValueCsvFileReader getInstance()
    {
      return OneValueCsvFileReaderHolder.INSTANCE;  
    }
    
    private static class OneValueCsvFileReaderHolder{
      private static final OneValueCsvFileReader INSTANCE = new OneValueCsvFileReader();
    }
    
    private OneValueCsvFileReader(){
        
    }
    public ArrayList<Object> oneReaderCsv(String path){
		
		ArrayList<Object> loadValues=new ArrayList();
		BufferedReader br_values = null;
		String values_line = "";
		try {
		br_values = new BufferedReader(new FileReader(path));				
               while ((values_line = br_values.readLine()) != null)
         {
            if(values_line==null)
                    continue;
            else{			        	
        loadValues.add(values_line);
            }
                          }
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), path, JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (br_values != null) {
				try {
					br_values.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return loadValues;
	}
}
