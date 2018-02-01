/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import com.solutions.entorno.utilities.MessagesUtil;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_CATEGORIES_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_STOCKS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class StockReport {
    
   private int skuid;
   private String skuName;
   private String skuCategory;
   private int skuCategoryid;
   private String dateofpurchase;
   private double quantity;
   private String batchno;
   private String regdate;
   private double Buying_Price;
   private double selling_Price;
   private  double profit;
   private double cost;
   private static double totalCost=0;
    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public int getSkuCategoryid() {
        return skuCategoryid;
    }

    public void setSkuCategoryid(int skuCategoryid) {
        this.skuCategoryid = skuCategoryid;
    }

    public String getDateofpurchase() {
        return dateofpurchase;
    }

    public void setDateofpurchase(String dateofpurchase) {
        this.dateofpurchase = dateofpurchase;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getBuying_Price() {
        return Buying_Price;
    }

    public void setBuying_Price(double Buying_Price) {
        this.Buying_Price = Buying_Price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getSelling_Price() {
        return selling_Price;
    }

    public void setSelling_Price(double selling_Price) {
        this.selling_Price = selling_Price;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCategory() {
        return skuCategory;
    }

    public void setSkuCategory(String skuCategory) {
        this.skuCategory = skuCategory;
    }
    
     public static TableView StockPreview(int cat){
           TableView tv;
        String[] headers = {"Skuid","Skuname", "Skucategory", "Quantity",  "Buying Price","Purchase Date"};
        String[] property = {"skuid","skuName", "skuCategory", "quantity", "Buying_Price", "dateofpurchase"};
        ArrayList<Object> model = readCurrentStock(cat);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
     public static TableView StockSupply(String supid,String from,String to){
           TableView tv;
        String[] headers = {"Skuid","Skuname", "Skucategory", "Quantity",  "Buying Price","Purchase Date"};
        String[] property = {"skuid","skuName", "skuCategory", "quantity", "Buying_Price", "dateofpurchase"};
        ArrayList<Object> model = readStockSupply(supid, from,to);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
     private static ArrayList readCurrentStock(int category){
       ArrayList<StockReport> stockReport = new ArrayList<>();
        StockReport stck;
         try {
             String query = "SELECT stock.skuid,sku.skuname,cat.skucategoryname,stock.quantity,stock.priceperunit,stock.dateofpurchase,stock.batchno"
                     + " FROM " + SKUS_STOCKS +" stock "
                     + " , " + SKUS_REGISTRATION + " sku ,"
                     + " " + SKUS_CATEGORIES_REGISTRATION + " cat"
                     + " WHERE stock.skuid = sku.skuid  AND  sku.skucategoryid = cat.skucategoryid AND cat.skucategoryid ="+category+" "
                     + "AND stock.skuid=(SELECT MAX(stock.skuid) )";
             resultSet = statement.executeQuery(query);
             while(resultSet.next()){
                stck = new StockReport();
                 stck.setSkuid(resultSet.getInt("skuid"));
                stck.setSkuName(resultSet.getString("skuname"));
                stck.setSkuCategory(resultSet.getString("skucategoryname"));
                stck.setQuantity(resultSet.getDouble("quantity"));
                stck.setBuying_Price(resultSet.getDouble("priceperunit"));
                stck.setDateofpurchase(resultSet.getString("dateofpurchase"));
                stck.setBatchno(resultSet.getString("batchno"));
                
                stockReport.add(stck);
             }
         } catch (Exception e) {
             Logger.getLogger(StockReport.class.getName()).log(Level.SEVERE, null, e);
         }
         return stockReport;
     }
     private static ArrayList readStockSupply(String supid,String from,String to){
       ArrayList<StockReport> stockReport = new ArrayList<>();
        StockReport stck;
         try {
             String query = "SELECT stock.skuid,sku.skuname,cat.skucategoryname,stock.quantity,stock.priceperunit,stock.dateofpurchase,stock.batchno"
                     + " FROM " + SKUS_STOCKS +" stock "
                     + " , " + SKUS_REGISTRATION + " sku ,"
                     + " " + SKUS_CATEGORIES_REGISTRATION + " cat"
                     + " WHERE stock.skuid = sku.skuid  AND  sku.skucategoryid = cat.skucategoryid  ";
             
             String supplierId=" AND stock.supplierid ='"+supid+"'  ";
             String dates=" AND stock.dateofpurchase BETWEEN  '"+from+"' AND  '"+to+"'  ";
             String orderby="  ORDER BY stock.dateofpurchase DESC   ";
             if(from==null && to==null){
                  resultSet = statement.executeQuery(query+supplierId+orderby);
                  
             }
             else if(from!=null && to!=null && supid!=null ){
               resultSet = statement.executeQuery(query+supplierId+dates+orderby);
                
             }
             totalCost=0;
             while(resultSet.next()){
                stck = new StockReport();
                 stck.setSkuid(resultSet.getInt("skuid"));
                stck.setSkuName(resultSet.getString("skuname"));
                stck.setSkuCategory(resultSet.getString("skucategoryname"));
                stck.setQuantity(resultSet.getDouble("quantity"));
                stck.setBuying_Price(resultSet.getDouble("priceperunit"));
                stck.setDateofpurchase(resultSet.getString("dateofpurchase"));
                stck.setBatchno(resultSet.getString("batchno"));
                totalCost+=stck.getBuying_Price();
                
                stockReport.add(stck);
             }
         } catch (Exception e) {
             Logger.getLogger(StockReport.class.getName()).log(Level.SEVERE, null, e);
         }
         return stockReport;
     }
     
      public static TableView StockSuppliers(String supplierid,String fromDate,String toDate){
           TableView tv;
        String[] headers = {"Skuid","Skuname",  "Quantity", "Buying Price",  "Cost" ,"Purchase Date"};
        String[] property = {"skuid","skuName",  "quantity", "Buying_Price", "cost" ,"dateofpurchase"};
        ArrayList<Object> model = readStockSuppliers(supplierid,fromDate,toDate );

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
     private static ArrayList readStockSuppliers(String supplierid,String fromDate,String toDate){
       ArrayList<StockReport> stockReport = new ArrayList<>();
        StockReport stck;
         try {
             String query = "SELECT cat.skucategoryid ,stock.skuid,sku.skuname,cat.skucategoryname,stock.quantity,stock.dateofpurchase,stock.priceperunit,sku.retailprice"
                     + " FROM " + SKUS_STOCKS +" stock "
                     + " , " + SKUS_REGISTRATION + " sku ,"
                     + " " + SKUS_CATEGORIES_REGISTRATION + " cat"
                     + " WHERE stock.skuid = sku.skuid  AND  sku.skucategoryid = cat.skucategoryid "
                     + " AND stock.supplierid='"+supplierid+"'  ";
             
             String  betweenCodition=null;
             
               if(fromDate!=null  && toDate!=null) {
                  betweenCodition  = " AND  (stock.regdate BETWEEN "+" '"+fromDate+"' AND '"+ toDate+"' "
                          + "OR stock.regdate BETWEEN "+" '"+toDate+"' AND '"+ fromDate+"')  ";
               }
               
                query=query;
               
             if( betweenCodition!=null){
                 query=query+betweenCodition;
             }
             resultSet = statement.executeQuery(query);
             totalCost=0;
             while(resultSet.next()){
                stck = new StockReport();
                 stck.setSkuid(resultSet.getInt("skuid"));
                  stck.setSkuCategoryid(resultSet.getInt("skucategoryid"));
                stck.setSkuName(resultSet.getString("skuname"));
                stck.setSkuCategory(resultSet.getString("skucategoryname"));
                stck.setQuantity(resultSet.getDouble("quantity"));
                stck.setDateofpurchase(resultSet.getString("dateofpurchase"));
                stck.setBuying_Price(resultSet.getDouble("priceperunit"));
                stck.setSelling_Price(resultSet.getDouble("retailprice"));
                stck.setProfit(resultSet.getDouble("retailprice")-resultSet.getDouble("priceperunit"));
                stck.setCost((resultSet.getDouble("priceperunit"))*(resultSet.getDouble("quantity")));
                totalCost+=stck.getCost();
                 System.out.println("get cost  "+totalCost);
                
                
                stockReport.add(stck);
             }
         } catch (Exception e) {
             Logger.getLogger(StockReport.class.getName()).log(Level.SEVERE, null, e);
         }
         return stockReport;
     }
     
      public void updatePrices(int skuid,int catId) {
        try {
            statement.executeUpdate("UPDATE " + SKUS_REGISTRATION + " SET "
                    + " retailprice=" + getSelling_Price() + ",wholesaleprice=" + getBuying_Price() + ", "
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skuId=" + skuid + "");
            
            statement.executeUpdate("UPDATE " + SKUS_STOCKS + " SET "
                    + " priceperunit='" + getBuying_Price() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skuId=" + skuid + "");
            
             statement.executeUpdate("UPDATE " + SKUS_CATEGORIES_REGISTRATION + " SET "
                    + " skucategoryname='" + getSkuCategory() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skucategoryid=" + catId + "");
             System.out.println("cat  "+getSkuCategory());
             
               System.out.println("catid  "+catId);



            MessagesUtil.displayMessage("Update Operation Successful", "Successfully Updated SKU details", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtil.displayMessage("Update Operation Failed", "Failed to  Update SKU details", 3, e);
        }
    }
}
