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
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import com.solutions.pos.models.SkuOrdersModel;
import static java.lang.Math.round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author MARK
 */
public class DailySkuSalesReport {
   
    private int skuId;
    private String skuname;
    private double quantity;
    private double priceperunit;
    private double total_amount;
    private double vat;
    private double total_vat;
    private String regdate;
    private double buyingPrice;
    
    private static double cumulativeDailySKUSales = 0;
    private static double cumulativeDailySKUVAT = 0;
     private static double profitCumulative = 0;
    
     private double buyingPriceTotal;
     private static double profitTotal;
    
    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(double priceperunit) {
        this.priceperunit = priceperunit;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(double total_vat) {
        this.total_vat = total_vat;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getProfit() {
        return buyingPriceTotal;
    }

    public void setProfit(double profit) {
        this.buyingPriceTotal = profit;
    }
public double getCumulativeDailySKUSales() {
        return (double) round(cumulativeDailySKUSales*1000)/1000;
    }

    public void setCumulativeDailySKUSales(double cumulativeDailySKUSales) {
        this.cumulativeDailySKUSales = cumulativeDailySKUSales;
    }

    public double getCumulativeDailySKUVAT() {
        return (double) round(cumulativeDailySKUVAT*1000)/1000;
    }

    public void setCumulativeDailySKUVAT(double cumulativeDailySKUVAT) {
        this.cumulativeDailySKUVAT = cumulativeDailySKUVAT;
    }

   

    public double getBuyingPriceTotal() {
        return buyingPriceTotal;
    }

    public void setBuyingPriceTotal(double buyingPriceTotal) {
        this.buyingPriceTotal = buyingPriceTotal;
    }

    public double getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(double profitTotal) {
        this.profitTotal = profitTotal;
    }

    public  double getProfitCumulative() {
        return profitCumulative;
    }

    public  void setProfitCumulative(double profitCumulative) {
        this.profitCumulative = profitCumulative;
    }

    
   
    
    public static TableView dailySalesReport(String date, String userid, int status ){
           TableView tv;
        String[] headers = {"SkuName", "Quantity", "Unit Price", "Sub-total","VAT", "Date"};
        String[] property = {"skuname", "quantity", "priceperunit", "total_amount", "total_vat","regdate"};
        ArrayList<Object> model = readSkusales(date, userid, status);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
     private static ArrayList readSkusales(String date, String userid, int skuId){
        ArrayList<DailySkuSalesReport> readAll = new ArrayList<>();
       DailySkuSalesReport skuorder ;
         try {
             String query = "SELECT sku_r.skuname,sku_s.quantity,sku_s.priceperunit,sku_s.vat,sku_s.regdate,sku_s.buyingprice"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + ""+SKUS_REGISTRATION+" sku_r, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE sku_s.skuid = sku_r.skuid"
                     + " AND sku_s.orderid = sr.orderid "
                     + " AND sr.status = 3";
             String whereCondition = "";
             String dates = " sku_s.regdate = '"+date+"' ";
             String user = " sku_s.userId = '"+userid+"' ";
            
             if(skuId > 0){
                   whereCondition = " AND sku_s.skuid = "+skuId+"";
               }
               if(date!=null && userid==null){
                  whereCondition  = whereCondition+" AND "+ dates;
               }
               else if(userid!=null && date == null){
                    whereCondition  = whereCondition+" AND "+ user;
               }
               else if(date!=null && userid!=null){
                    whereCondition  = whereCondition+" AND "+ dates +" AND "+user;
               }
               
             if(whereCondition!=null){
                 query=query+whereCondition;
             }
             profitCumulative=0;
             cumulativeDailySKUSales=0;
             cumulativeDailySKUVAT=0;
             profitTotal=0;
             resultSet = statement.executeQuery(query);
            while(resultSet.next()){
            skuorder  = new DailySkuSalesReport();
            //skuorder.setSkuId(resultSet.getInt("skuid"));
            skuorder.setSkuname(resultSet.getString("skuname"));
            skuorder.setPriceperunit(resultSet.getDouble("priceperunit"));
            skuorder.setQuantity(resultSet.getDouble("quantity"));
            skuorder.setVat(resultSet.getDouble("vat"));
            skuorder.setRegdate(resultSet.getString("regdate"));
            skuorder.setBuyingPrice(resultSet.getDouble("buyingprice"));
            skuorder.setTotal_vat((double)round(skuorder.getQuantity()*skuorder.getVat()*1000)/1000);
            skuorder.setTotal_amount((double)round(skuorder.getPriceperunit()*skuorder.getQuantity()*1000)/1000);
            skuorder.setBuyingPriceTotal((double)round(skuorder.getBuyingPrice()*skuorder.getQuantity()*1000)/1000);
            
            cumulativeDailySKUSales+= skuorder.getTotal_amount();
            cumulativeDailySKUVAT+=  skuorder.getTotal_vat();
            
           skuorder.setProfitCumulative(skuorder.getTotal_amount()-skuorder.getBuyingPriceTotal()-skuorder.getTotal_vat());
           
           profitTotal+= skuorder.getProfitCumulative();
               
            readAll.add(skuorder);
            }
         
            
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }

    
}
