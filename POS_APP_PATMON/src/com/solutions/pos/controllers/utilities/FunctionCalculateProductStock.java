/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers.utilities;

import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_STOCKS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import java.sql.SQLException;

/**
 *
 * @author shaddie
 */
public class FunctionCalculateProductStock {
    
     public static int getReOrderLevel(int skuId){
          int reorderLevel = 0;
        try {
          String sql = "SELECT reorderlevel FROM " + SKUS_REGISTRATION + " WHERE skuId = "+skuId+" ";  
           resultSet = SystemVariables.statement.executeQuery(sql);
            if(resultSet.next()){
                reorderLevel = resultSet.getInt(1);
            }
        } catch (Exception e) {
            
        }
        return reorderLevel;
    }
      public static double getBuyingPrice(int skuId){
          int buyingPrice = 0;
        try {
            resultSet = statement.executeQuery("SELECT  priceperunit FROM  " +SKUS_STOCKS + " "
                    + " WHERE skuid="+skuId+" ORDER BY stockid DESC    ");
            if(resultSet.next()){
                 System.out.println("pr :"+resultSet.getDouble(1));
                  
                buyingPrice = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return buyingPrice;
    }
     
    public static double getCurrentStock(int skuId){
        return getTotalStocks(skuId)-getTotalSales(skuId);
    }
    
    public static double getTotalSales(int skuID){
        double totalSold = 0;
        try {
          String sql = "SELECT sum(quantity) FROM " + SKU_SALES + " WHERE skuId = "+skuID+" ";  
           resultSet = SystemVariables.statement.executeQuery(sql);
            if(resultSet.next()){
                totalSold = resultSet.getDouble(1);
            }
        } catch (Exception e) {
            
        }
        return totalSold;
    }
    
    public static double getTotalStocks(int skuID){
        double totaStocked = 0;
         try {
          String sql = "SELECT sum(quantity) FROM " + SKUS_STOCKS + " WHERE skuId = "+skuID+" ";  
           resultSet = SystemVariables.statement.executeQuery(sql);
            if(resultSet.next()){
                totaStocked = resultSet.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totaStocked;
    }
     public static double getPricePerUnit(int skuId){
          double pricePerUnit = 0;
        try {
          String sql = "SELECT retailprice FROM " + SKUS_REGISTRATION + " WHERE skuId = "+skuId+" ";  
           resultSet = SystemVariables.statement.executeQuery(sql);
            if(resultSet.next()){
                pricePerUnit = resultSet.getDouble(1);
            }
        } catch (Exception e) {
            
        }
        return pricePerUnit;
    }
}
