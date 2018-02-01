/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_STOCKS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import com.solutions.pos.models.SkuOrdersModel;
import com.solutions.pos.models.SkuStocksModel;
import static java.lang.Math.round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class SalesIntervalsModel {
     private int skuid;
    private String skucategory;
     private String skuName;
     
    private double buyingPrice;
    private double sellingPrice;
     private double quantity;
      private double profit;
      
       private double totalSales;
    private String regdate;
    private String user;

    private static double cumulativeSales = 0.0;
    private static double cumulativeStockSold = 0.0;
    private static double totalProfit=0.0;
    private static double totalStocked=0.0;

    public double getCumulativeSales() {
        return (double) round(cumulativeSales * 1000) / 1000;
    }

    public void setCumulativeSales(double cumulativeSales) {
        this.cumulativeSales = cumulativeSales;
    }

    public double cumulativeStockSold() {
        return (double) round(cumulativeStockSold * 1000) / 1000;
    }

    public void cumulativeStockSold(double cumulativeStockSold) {
        this.cumulativeStockSold = cumulativeStockSold;
    }

    public static double getTotalStocked() {
        return (double) round(totalStocked*1000)/1000;
    }

    public static void setTotalStocked(double totalStocked) {
        SalesIntervalsModel.totalStocked = totalStocked;
    }

    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkucategory() {
        return skucategory;
    }

    public void setSkucategory(String skucategory) {
        this.skucategory = skucategory;
    }

    public double getBuyingPrice() {
   
        return (double) round(buyingPrice * 1000) / 1000;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
      
       return (double) round(sellingPrice * 1000) / 1000;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getProfit() {
         return (double) round(profit * 1000) / 1000;
        
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getTotalSales() {
       return (double) round(totalSales * 1000) / 1000;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getTotalProfit() {
        return (double) round(totalProfit * 1000) / 1000 ;
         
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    
    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static TableView dailySalesReport(String date) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity Sold","Buying Price", "Selling Price", "Profit Margin"};
        String[] property = {"skuid", "skuName","quantity", "buyingPrice", "sellingPrice", "profit"};
        ArrayList<Object> model = readSkusalesIntervals(date);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
    
     public static TableView dailySalesReportByDate(String fromDate, String toDate, int status) {
        
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity Sold","Buying Price", "Selling Price", "Profit Margin"};
        String[] property = {"skuid", "skuName", "quantity","buyingPrice", "sellingPrice", "profit"};
        ArrayList<Object> model = readIntervalsByDate(fromDate,toDate,status);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    private static ArrayList readSkusalesIntervals(String interval) {
        ArrayList<SalesIntervalsModel> readAll = new ArrayList<>();
        SalesIntervalsModel invetval;
        try {
             String query = "SELECT sku_s.skuid,sku_r.skuname,sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + ""+SKUS_REGISTRATION+" sku_r, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE sku_s.skuid = sku_r.skuid"
                     + " AND sku_s.orderid = sr.orderid  "
                     + " AND sr.status=3 AND  sku_s.regdate::DATE      ";
                  String condition=interval;
                  String group=" ORDER BY sr.orderid DESC";
            cumulativeSales = 0.0;
            cumulativeStockSold = 0.0;
            totalProfit=0;
            double buying=0;
            double selling=0;
            double prof=0;
            resultSet = statement.executeQuery(query+condition+group);
            while (resultSet.next()) {
                invetval = new SalesIntervalsModel();
                invetval.setSkuid(resultSet.getInt("skuid"));
                invetval.setSkuName(resultSet.getString("skuname"));
                 invetval.setQuantity(resultSet.getDouble("quantity"));
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                
                if(invetval.getQuantity() < 1){
                   
                   selling += invetval.getSellingPrice();
                buying += invetval.getBuyingPrice();
               prof +=(invetval.getSellingPrice()-invetval.getBuyingPrice()); 
                }
                else{
                     
                    selling += invetval.getSellingPrice()*invetval.getQuantity();
                buying += invetval.getBuyingPrice()*invetval.getQuantity();
               prof +=((invetval.getSellingPrice()-invetval.getBuyingPrice())*invetval.getQuantity());
                }
                cumulativeSales = selling;
                cumulativeStockSold = buying;
               totalProfit = prof;
                readAll.add(invetval);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
    private static ArrayList readIntervalsByDate(String fromDate, String toDate, int status) {
        ArrayList<SalesIntervalsModel> readAll = new ArrayList<>();
        SalesIntervalsModel invetval;
        try {
             String query = "SELECT sku_s.skuid,sku_r.skuname,sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + ""+SKUS_REGISTRATION+" sku_r, "
                     + " " +SKU_ORDERS+ " sr "
                     + " WHERE  sku_s.skuid = sku_r.skuid AND sku_s.orderid = sr.orderid AND  "
                     + "sr.status=3 AND  sku_s.regdate BETWEEN  '"+fromDate+"'  AND '"+toDate+"'  ORDER BY sr.orderid  DESC  ";
                
            cumulativeSales = 0.0;
            cumulativeStockSold = 0.0;
            totalProfit=0;
            double buying=0;
            double selling=0;
            double prof=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new SalesIntervalsModel();
                invetval.setSkuid(resultSet.getInt("skuid"));
                invetval.setSkuName(resultSet.getString("skuname"));
                invetval.setQuantity(resultSet.getDouble("quantity"));
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                invetval.setUser(resultSet.getString("userid"));
                 if(invetval.getQuantity() < 1){
                   
                   selling += invetval.getSellingPrice();
                buying += invetval.getBuyingPrice();
               prof +=(invetval.getSellingPrice()-invetval.getBuyingPrice()); 
                }
                else{
                     
                selling += invetval.getSellingPrice()*invetval.getQuantity();
                buying += invetval.getBuyingPrice()*invetval.getQuantity();
               prof +=((invetval.getSellingPrice()-invetval.getBuyingPrice())*invetval.getQuantity());
                }
                cumulativeSales = selling;
                cumulativeStockSold = buying;
               totalProfit = prof;
               
                readAll.add(invetval);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
    
    //function to calculate the stock
   public double  totalStockSold() {
       double fetchStock=0.0, totalStock=0.0;
        try {
             String query = "SELECT sku_s.skuid,sku_r.skuname,sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + ""+SKUS_REGISTRATION+" sku_r, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE sku_s.skuid = sku_r.skuid"
                     + " AND sku_s.orderid = sr.orderid  "
                     + " AND sr.status=3      ";
                  
            
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
              
                fetchStock+=(resultSet.getDouble("quantity")*resultSet.getDouble("buyingprice"));
              }
            totalStock+=fetchStock;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (double) round(totalStock *1000)/1000;
                
                
                
    }
    
    public double  totalStock(){
       double fetchstkd=0.0,stocked=0.0;
         try {
             resultSet = statement.executeQuery("SELECT skuid, dateofpurchase, quantity, priceperunit, batchno, regdate, " +
"       last_update, userid, stockid, supplierid FROM " +SKUS_STOCKS + " ");
             
            while(resultSet.next()){
            fetchstkd+=(resultSet.getDouble("quantity")*resultSet.getDouble("priceperunit"));
            }
            stocked+=fetchstkd;
            
         } catch (SQLException ex) {
             Logger.getLogger(SkuStocksModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return (double)round(stocked * 1000)/1000;
   }
   
    public static HashMap salesIntervals() {
        HashMap<String, String> readAll = new HashMap<>();
           
            readAll.put(" = (current_date - INTERVAL '0' day) ", "Today");
            readAll.put(" = (current_date - INTERVAL '1' day)  ","Yesterday");
            readAll.put("  >= (current_date - INTERVAL '7' day) ","Last One  Week");
            readAll.put(" BETWEEN (current_date - INTERVAL '14' day) AND (current_date - INTERVAL '7' day) ","Last   Week");
            readAll.put(" >= (current_date - INTERVAL '1' month) ","Last One Month");
            readAll.put(" BETWEEN (current_date - INTERVAL '2' month) AND (current_date - INTERVAL '1' month) ","Last  Month");
            
           
            
            
            
        
        return readAll;
    }
     
}
