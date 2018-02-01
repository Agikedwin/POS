/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import com.solutions.pos.models.SkuOrdersModel;
import static java.lang.Math.round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ProgressReport {
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
    private static double cumulativeVAT = 0.0;
    private static double totalProfit=0.0;
    
     private static double dailySales5dayAgo;
     private static double dayProfit5dayAgo;
     
    private static double dailySales4dayAgo;
    private static double dayProfit4dayAgo;
     private static double dailySales3dayAgo;
    private static double  dayProfit3dayAgo;
 private static double dailySales2dayAgo;
    private static double dayProfit2dayAgo;
     private static double dailySalesYesterday;
     private static double dayProfitYesteday;
     private static double  dailySalesToday;
     private static double dailySalesGrandTotal;
    private static double dayProfitToday;
     private static double dayProfitDrandTotal;
     private static double monthlySales3monthsAgo;
     private static double monthlyProfit3MonthsAgo;
    private static double monthlySales2monthsAgo;
     private static double monthlyProfit2MonthsAgo;
     private static double monthlySalesLastMonth;
   private static double monthlyProfitLastMonth;
     private static double monthlySalesThisMonth;
     private static double monthlyProfitThisMonth;
    private static double monthlyProfitGrandTotal;
    
    //buying prices
     private static double dailyBuying4dayAgo;
       private static double dailyBuying5dayAgo;
     private static double dailyBuying3dayAgo;
 private static double dailyBuying2dayAgo;
     private static double dailyBuyingYesterday;
     private static double  dailyBuyingToday;
     private static double monthlyBuying3monthsAgo;
    private static double monthlyBuying2monthsAgo;
     private static double monthlyBuyingLastMonth;
     private static double monthlyBuyingThisMonth;

    public double getCumulativeSales() {
        return (double) round(cumulativeSales * 1000) / 1000;
    }

    public void setCumulativeSales(double cumulativeSales) {
        this.cumulativeSales = cumulativeSales;
    }

    public double getCumulativeVAT() {
        return (double) round(cumulativeVAT * 1000) / 1000;
    }

    public void setCumulativeVAT(double cumulativeVAT) {
        this.cumulativeVAT = cumulativeVAT;
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

    public  double getDailySales5dayAgo() {
     
         return (double) round(dailySales5dayAgo * 1000) / 1000;
    }

    public  void setDailySales5dayAgo(double dailySales5dayAgo) {
        this.dailySales5dayAgo = dailySales5dayAgo;
    }

    public  double getDayProfit5dayAgo() {
       return (double) round(dayProfit5dayAgo * 1000) / 1000;
       
    }

    public  void setDayProfit5dayAgo(double dayProfit5dayAgo) {
        this.dayProfit5dayAgo = dayProfit5dayAgo;
    }

    public  double getDailySales4dayAgo() {
        return (double) round(dailySales4dayAgo * 1000) / 1000;
        
    }

    public  void setDailySales4dayAgo(double dailySales4dayAgo) {
        this.dailySales4dayAgo = dailySales4dayAgo;
    }

    public  double getDayProfit4dayAgo() {
       return (double) round(dayProfit4dayAgo * 1000) / 1000;
    }

    public  void setDayProfit4dayAgo(double dayProfit4dayAgo) {
        this.dayProfit4dayAgo = dayProfit4dayAgo;
    }

    public  double getDailySales3dayAgo() {
        return (double) round(dailySales3dayAgo * 1000) / 1000;
        
    }

    public  void setDailySales3dayAgo(double dailySales3dayAgo) {
        this.dailySales3dayAgo = dailySales3dayAgo;
    }

    public  double getDayProfit3dayAgo() {
        return (double) round(dayProfit3dayAgo * 1000) / 1000;
    }

    public  void setDayProfit3dayAgo(double dayProfit3dayAgo) {
        this.dayProfit3dayAgo = dayProfit3dayAgo;
    }

    public  double getDailySales2dayAgo() {
        return (double) round(dailySales2dayAgo * 1000) / 1000; 
    }

    public  void setDailySales2dayAgo(double dailySales2dayAgo) {
        this.dailySales2dayAgo = dailySales2dayAgo;
    }

    public  double getDayProfit2dayAgo() {
        return (double) round(dayProfit2dayAgo * 1000) / 1000; 
    }

    public  void setDayProfit2dayAgo(double dayProfit2dayAgo) {
        this.dayProfit2dayAgo = dayProfit2dayAgo;
    }

    public  double getDailySalesYesterday() {
         return (double) round(dailySalesYesterday * 1000) / 1000; 
       
    }

    public  void setDailySalesYesterday(double dailySalesYesterday) {
        this.dailySalesYesterday = dailySalesYesterday;
    }

    public  double getDayProfitYesteday() {
       return (double) round(dayProfitYesteday * 1000) / 1000;  
        
    }

    public  void setDayProfitYesteday(double dayProfitYesteday) {
        this.dayProfitYesteday = dayProfitYesteday;
    }

    public  double getDailySalesToday() {
         return (double) round(dailySalesToday * 1000) / 1000;  
    }

    public  void setDailySalesToday(double dailySalesToday) {
        this.dailySalesToday = dailySalesToday;
    }

    public  double getDailySalesGrandTotal() {
        return (double) round(dailySalesGrandTotal * 1000) / 1000;   
        
    }

    public  void setDailySalesGrandTotal(double dailySalesGrandTotal) {
        this.dailySalesGrandTotal = dailySalesGrandTotal;
    }

    public  double getDayProfitToday() {
        return (double) round(dayProfitToday * 1000) / 1000;   
    }

    public  void setDayProfitToday(double dayProfitToday) {
        this.dayProfitToday = dayProfitToday;
    }

    public  double getDayProfitDrandTotal() {
        return (double) round(dayProfitDrandTotal * 1000) / 1000;   
        
    }

    public  void setDayProfitDrandTotal(double dayProfitDrandTotal) {
        this.dayProfitDrandTotal = dayProfitDrandTotal;
    }

    public  double getMonthlySales3monthsAgo() {
       return (double) round(monthlySales3monthsAgo * 1000) / 1000; 
       
    }

    public  void setMonthlySales3monthsAgo(double monthlySales3monthsAgo) {
        this.monthlySales3monthsAgo = monthlySales3monthsAgo;
    }

    public  double getMonthlyProfit3MonthsAgo() {
        return (double) round(monthlyProfit3MonthsAgo * 1000) / 1000; 
       
    }

    public  void setMonthlyProfit3MonthsAgo(double monthlyProfit3MonthsAgo) {
       monthlyProfit3MonthsAgo = monthlyProfit3MonthsAgo;
    }

    public  double getMonthlySales2monthsAgo() {
       
         return (double) round(monthlySales2monthsAgo * 1000) / 1000; 
    }

    public  void setMonthlySales2monthsAgo(double monthlySales2monthsAgo) {
        this.monthlySales2monthsAgo = monthlySales2monthsAgo;
    }

    public  double getMonthlyProfit2MonthsAgo() {
         return (double) round(monthlyProfit2MonthsAgo * 1000) / 1000; 
    }

    public  void setMonthlyProfit2MonthsAgo(double monthlyProfit2MonthsAgo) {
        this.monthlyProfit2MonthsAgo = monthlyProfit2MonthsAgo;
    }

    public  double getMonthlySalesLastMonth() {
        return (double) round(monthlySalesLastMonth * 1000) / 1000; 
        
    }

    public  void setMonthlySalesLastMonth(double monthlySalesLastMonth) {
        this.monthlySalesLastMonth = monthlySalesLastMonth;
    }

    public  double getMonthlyProfitLastMonth() {
        return (double) round(monthlyProfitLastMonth * 1000) / 1000; 
        
    }

    public  void setMonthlyProfitLastMonth(double monthlyProfitLastMonth) {
        this.monthlyProfitLastMonth = monthlyProfitLastMonth;
    }

    public  double getMonthlySalesThisMonth() {
         return (double) round(monthlySalesThisMonth * 1000) / 1000; 
    }

    public  void setMonthlySalesThisMonth(double monthlySalesThisMonth) {
        this.monthlySalesThisMonth = monthlySalesThisMonth;
    }

    public  double getMonthlyProfitThisMonth() {
         return (double) round(monthlyProfitThisMonth * 1000) / 1000; 
    }

    public  void setMonthlyProfitThisMonth(double monthlyProfitThisMonth) {
        this.monthlyProfitThisMonth = monthlyProfitThisMonth;
    }

    public  double getMonthlyProfitGrandTotal() {
         return (double) round(monthlyProfitGrandTotal * 1000) / 1000; 
        
    }

    public  void setMonthlyProfitGrandTotal(double monthlyProfitGrandTotal) {
        this.monthlyProfitGrandTotal = monthlyProfitGrandTotal;
    }

    public  double getDailyBuying4dayAgo() {
        return (double) round(dailyBuying4dayAgo * 1000) / 1000; 
       
    }

    public  void setDailyBuying4dayAgo(double dailyBuying4dayAgo) {
        this.dailyBuying4dayAgo = dailyBuying4dayAgo;
    }

    public  double getDailyBuying5dayAgo() {
         return (double) round(dailyBuying5dayAgo * 1000) / 1000; 
    }

    public  void setDailyBuying5dayAgo(double dailyBuying5dayAgo) {
        this.dailyBuying5dayAgo = dailyBuying5dayAgo;
    }

    public  double getDailyBuying3dayAgo() {
        return (double) round(dailyBuying3dayAgo * 1000) / 1000; 
       
    }

    public  void setDailyBuying3dayAgo(double dailyBuying3dayAgo) {
        this.dailyBuying3dayAgo = dailyBuying3dayAgo;
    }

    public  double getDailyBuying2dayAgo() {
         return (double) round(dailyBuying2dayAgo * 1000) / 1000; 
        
    }

    public  void setDailyBuying2dayAgo(double dailyBuying2dayAgo) {
        this.dailyBuying2dayAgo = dailyBuying2dayAgo;
    }

    public  double getDailyBuyingYesterday() {
        return (double) round(dailyBuyingYesterday * 1000) / 1000; 
        
    }

    public  void setDailyBuyingYesterday(double dailyBuyingYesterday) {
        this.dailyBuyingYesterday = dailyBuyingYesterday;
    }

    public  double getDailyBuyingToday() {
          return (double) round(dailyBuyingToday * 1000) / 1000; 
        
    }

    public  void setDailyBuyingToday(double dailyBuyingToday) {
        this.dailyBuyingToday = dailyBuyingToday;
    }

    public  double getMonthlyBuying3monthsAgo() {
         return (double) round(monthlyBuying3monthsAgo * 1000) / 1000;
    }

    public  void setMonthlyBuying3monthsAgo(double monthlyBuying3monthsAgo) {
        this.monthlyBuying3monthsAgo = monthlyBuying3monthsAgo;
    }

    public  double getMonthlyBuying2monthsAgo() {
         return (double) round(monthlyBuying2monthsAgo * 1000) / 1000;
    }

    public  void setMonthlyBuying2monthsAgo(double monthlyBuying2monthsAgo) {
        this.monthlyBuying2monthsAgo = monthlyBuying2monthsAgo;
    }

    public  double getMonthlyBuyingLastMonth() {
         return (double) round(monthlyBuyingLastMonth * 1000) / 1000;
    }

    public  void setMonthlyBuyingLastMonth(double monthlyBuyingLastMonth) {
        this.monthlyBuyingLastMonth = monthlyBuyingLastMonth;
    }

    public  double getMonthlyBuyingThisMonth() {
         return (double) round(monthlyBuyingThisMonth * 1000) / 1000;
    }

    public  void setMonthlyBuyingThisMonth(double monthlyBuyingThisMonth) {
        this.monthlyBuyingThisMonth = monthlyBuyingThisMonth;
    }

    
    

    public static ArrayList readTodaytSales() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '0' day)   ";
                  
            dailySalesToday = 0.0;
            dailyBuyingToday = 0.0;
            dayProfitToday=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                
                invetval = new ProgressReport();
               invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                System.out.println("qunt "+qnty);
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 dailySalesToday+=invetval.getSellingPrice();
                 System.out.println("Quantity: "+dailySalesToday);
                dailyBuyingToday += invetval.getBuyingPrice();
               dayProfitToday +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
    
     public static ArrayList readYesterdaySales() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '1' day)   ";
                  
            dailySalesYesterday = 0.0;
            dailyBuyingYesterday = 0.0;
            dayProfitYesteday=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
                invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 dailySalesYesterday+=invetval.getSellingPrice();
                dailyBuyingYesterday += invetval.getBuyingPrice();
               dayProfitYesteday +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    } public static ArrayList read2DaysAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '2' day)   ";
                  
            dailySales2dayAgo = 0.0;
            dailyBuying2dayAgo = 0.0;
            dayProfit2dayAgo=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
              invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 monthlySalesLastMonth+=invetval.getSellingPrice();
                monthlyBuyingThisMonth += invetval.getBuyingPrice();
               dayProfit2dayAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
     public static ArrayList read3DaysAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '3' day)   ";
                  
            dailySales3dayAgo = 0.0;
            dailyBuying3dayAgo= 0.0;
            dayProfit3dayAgo=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
               invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 dailySales3dayAgo+=invetval.getSellingPrice();
                dailyBuying3dayAgo += invetval.getBuyingPrice();
               dayProfit3dayAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
      public static ArrayList read4DaysAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '4' day)   ";
                  
            dailySales4dayAgo = 0.0;
            dailyBuying4dayAgo= 0.0;
            dayProfit4dayAgo=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
              invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 dailySales4dayAgo+=invetval.getSellingPrice();
                dailyBuying4dayAgo += invetval.getBuyingPrice();
               dayProfit4dayAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
       public static ArrayList read5DaysAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   = (current_date - INTERVAL '5' day)   ";
                  
            dailySales5dayAgo = 0.0;
            dailyBuying5dayAgo= 0.0;
            dayProfit5dayAgo=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
              invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 dailySales5dayAgo+=invetval.getSellingPrice();
                dailyBuying5dayAgo += invetval.getBuyingPrice();
               dayProfit5dayAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
    
    public static ArrayList readThisMonth() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE  >= (current_date - INTERVAL '1' month)  ";
                  
            monthlySalesThisMonth = 0.0;
            monthlyBuyingThisMonth= 0.0;
            monthlyProfitThisMonth=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
                invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 monthlySalesThisMonth+=invetval.getSellingPrice();
                monthlyBuyingThisMonth += invetval.getBuyingPrice();
               monthlyProfitThisMonth +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
     public static ArrayList readLastMonth() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   BETWEEN (current_date - INTERVAL '2' month) AND (current_date - INTERVAL '1' month)   ";
                  
           monthlySalesLastMonth = 0.0;
            monthlyBuyingLastMonth= 0.0;
            monthlyProfitLastMonth=0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
              invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                monthlySalesLastMonth+=invetval.getSellingPrice();
                monthlyBuyingLastMonth += invetval.getBuyingPrice();
               monthlyProfitLastMonth +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
     public double dailySalesGrandTotal(){
         double grandTotal=0;
         grandTotal=getDailySalesToday()+getDailySalesYesterday()+getDailySales2dayAgo()+getDailySales3dayAgo()+
         getDailySales4dayAgo()+getDailySales5dayAgo();
         
         return  (double) round(grandTotal* 1000) / 1000 ;
     }
     
     public double dailyProfitGrandTotal(){
         double grandTotal=0;
         
         grandTotal=getDayProfitToday()+getDayProfitYesteday()+getDayProfit2dayAgo()+getDayProfit3dayAgo()+
                    getDayProfit4dayAgo()+getDayProfit5dayAgo();
         
       
          return  (double) round(grandTotal* 1000) / 1000 ;
     }
     
     public double monthlySalesGrandTotal(){
         double grandTotal=0;
         grandTotal=getMonthlySalesThisMonth()+getMonthlySalesLastMonth()+getMonthlySales2monthsAgo()+getMonthlySales3monthsAgo();
        return  (double) round(grandTotal* 1000) / 1000 ;
     }
     
     public double monthlyProfitGrandTotal(){
         double grandTotal=0;
         
         grandTotal=getMonthlyProfitThisMonth()+getMonthlyProfitLastMonth()+getMonthlyProfit2MonthsAgo()+getMonthlyProfit3MonthsAgo();
                    
        return  (double) round(grandTotal * 1000) / 1000 ;
     }
     
    public static ArrayList read2MonthAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE   BETWEEN (current_date - INTERVAL '3' month) AND (current_date - INTERVAL '2' month)   ";
                  
            monthlySales2monthsAgo = 0.0;
            monthlyBuying2monthsAgo = 0.0;
            monthlyProfit2MonthsAgo =0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
               invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 monthlySales2monthsAgo+=invetval.getSellingPrice();
                monthlyBuying2monthsAgo += invetval.getBuyingPrice();
               monthlyProfit2MonthsAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
    public static ArrayList read3MonthAgo() {
        ArrayList<ProgressReport> readAll = new ArrayList<>();
        ProgressReport invetval=null;
        try {
             String query = "SELECT sku_s.quantity,sku_s.quantity,sku_s.quantity,sku_s.priceperunit,sku_s.buyingprice,sku_s.vat,sku_s.regdate,sku_s.userid"
                     + " FROM " +SKU_SALES + " sku_s, "
                     + " " +SKU_ORDERS+ " sr "
                     + "WHERE  sku_s.orderid = sr.orderid  AND sr.status=3"
                     + " AND  sku_s.regdate::DATE  BETWEEN (current_date - INTERVAL '4' month) AND (current_date - INTERVAL '3' month)   ";
                  
            monthlySales3monthsAgo = 0.0;
            monthlyBuying3monthsAgo = 0.0;
            monthlyProfit3MonthsAgo =0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                invetval = new ProgressReport();
               invetval.setBuyingPrice(resultSet.getDouble("quantity"));
                double qnty=resultSet.getDouble("quantity");
                if(qnty<1)
                {
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice"));
                invetval.setSellingPrice(resultSet.getDouble("priceperunit"));
                }
                else{
                invetval.setBuyingPrice(resultSet.getDouble("buyingprice")*qnty);
                invetval.setSellingPrice(resultSet.getDouble("priceperunit")*qnty); 
                }
               
                invetval.setProfit(invetval.getSellingPrice()-invetval.getBuyingPrice());
                 monthlySales3monthsAgo+=invetval.getSellingPrice();
                monthlyBuying3monthsAgo += invetval.getBuyingPrice();
               monthlyProfit3MonthsAgo +=(invetval.getSellingPrice()-invetval.getBuyingPrice());
                readAll.add(invetval);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
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
