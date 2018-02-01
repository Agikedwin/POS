/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
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
 * @author sonchaba
 */
public class AllSalesReports {
    private String orderId;
    private String receiptno;
    private double total_amount;
    private double total_vat;
    private String regdate;
    private String user;
    private double buying_price;
    private double quantity;
    private static double buying_total=0;
    private static double profit = 0;
     private static double Profit_cumulative = 0;
   private static double cumulativeDailySKUSales = 0;
    private static double cumulativeDailySKUVAT = 0;

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
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(double buying_price) {
        this.buying_price = buying_price;
    }

    public  double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public  double getProfit_cumulative() {
        return Profit_cumulative;
    }

    public  void setProfit_cumulative(double Profit_cumulative) {
       this.Profit_cumulative = Profit_cumulative;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

        public static TableView dailySalesReport(String fromdate,String todate, String userid, int status ){
           TableView tv;
        String[] headers = {"ID", "Receipt No", "Amount", "Vat", "Date",  "User"};
        String[] property = {"orderId", "receiptno", "total_amount", "total_vat","regdate", "user"};
        ArrayList<Object> model = readAllSkusales(fromdate,todate, userid, status);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
     public static ArrayList readAllSkusales(String fromDate,String toDate,String userid,  int status){
        ArrayList<AllSalesReports> readAll = new ArrayList<>();
       AllSalesReports skuorder ;
         try {
             String query = "SELECT  ord.customerid,ord.orderId,ord.status,ord.receiptno,ord.regdate,ord.last_update,ord.userId,"
                     + "ord.total_amount,ord.total_vat FROM " +SKU_ORDERS + " ord ";
             String whereCondition = null;
             String betweenCodition;
               String state = " ord.status = "+status+" ";
               
               String user = " ord.userId = '"+userid+"' ";
               
               whereCondition =" WHERE "+state + "   ";
                      
                       if(userid!=null){
                         whereCondition=whereCondition+ " AND  "+user;
                       }
                     
               
               betweenCodition=null;
               if(fromDate!=null  && toDate!=null) {
                  betweenCodition  = " AND  (ord.regdate BETWEEN "+" '"+fromDate+"' AND '"+ toDate+"' "
                          + "OR ord.regdate BETWEEN "+" '"+toDate+"' AND '"+ fromDate+"')  ";
               }
               
                query=query+whereCondition;
               
             if( betweenCodition!=null){
                 query=query+betweenCodition;
             }
           cumulativeDailySKUSales =0;
            cumulativeDailySKUVAT =0;
            Profit_cumulative=0;
            buying_total=0;
             resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                
            skuorder  = new AllSalesReports();
            skuorder.setOrderId(resultSet.getString("orderId"));
            skuorder.setReceiptno(resultSet.getString("receiptno"));
            skuorder.setRegdate(resultSet.getString("regdate"));
             skuorder.setTotal_vat(resultSet.getDouble("total_vat"));
              skuorder.setTotal_amount(resultSet.getDouble("total_amount"));
            skuorder.setUser(resultSet.getString("userId")); 
//            skuorder.setBuying_price(resultSet.getDouble("buyingprice")*resultSet.getInt("quantity"));
           // skuorder.setQuantity(resultSet.getInt("quantity"));
          //  skuorder.setProfit(skuorder.getTotal_amount()-(skuorder.getBuying_price())-skuorder.getTotal_vat());
            //set sales  and VAT
            buying_total+=getBuyingPrice(fromDate,toDate);
            cumulativeDailySKUSales += skuorder.getTotal_amount();
            cumulativeDailySKUVAT +=skuorder.getTotal_vat();
            Profit_cumulative+=cumulativeDailySKUSales-buying_total;
              readAll.add(skuorder);
            }
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }
     private static double getBuyingPrice(String fromdate,String todate ){
         double buyingPrice=0;
         try {
              String query = "SELECT  buyingprice,quantity FROM "+SKU_SALES+"  WHERE regdate "
                      + "regdate BETWEEN '"+fromdate+"' AND '"+todate+"'  "
                     + " ";
              resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                double total=(resultSet.getDouble("buyingprice")*resultSet.getInt("quantity"));
                buyingPrice+=total;
                
            }
         } catch (Exception e) {
         }
         return buyingPrice;
     }
}
