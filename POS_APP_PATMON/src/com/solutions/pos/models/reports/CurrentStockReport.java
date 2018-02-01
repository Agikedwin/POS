/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import com.solutions.entorno.utilities.TableViewRenderer;
import com.solutions.pos.controllers.utilities.FunctionCalculateProductStock;
import com.solutions.pos.models.SkusModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javafx.scene.control.TableView;

/**
 *
 * @author shaddie
 */
public class CurrentStockReport {
    private int skuid;
    private String skuname;
    private double bought;
    private double sold;
    private double diff;
    private int reorderLevel;
    private double deviation;
    private String purchaseRequired;
    private  double total_per_item=0.0;
    private static double total_stock_value=0.0;
    private static double  stockValue=0;
    private double pricePerUnit;

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
    
    public double getTotal_per_item() {
        return total_per_item;
    }

    public void setTotal_per_item(double total_per_item) {
        this.total_per_item = total_per_item;
    }

    public double getTotal_stock_value() {
        return total_stock_value;
    }

    public void setTotal_stock_value(double total_stock_value) {
        CurrentStockReport.total_stock_value = total_stock_value;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public double getBought() {
        return bought;
    }

    public void setBought(double bought) {
        this.bought = bought;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public String getPurchaseRequired() {
        return purchaseRequired;
    }

    public void setPurchaseRequired(String purchaseRequired) {
        this.purchaseRequired = purchaseRequired;
    }
    
    public static ArrayList getStocks(){
        ArrayList<CurrentStockReport> allStock = new ArrayList<>();
        String req ;
        CurrentStockReport stockItem;
        total_stock_value = 0.0;
        stockValue=0;
        
         HashMap<Integer, String> skus = SkusModel.FetchSkus();
                        Set<Integer> itemCatIds = skus.keySet();
                        for (Integer skuID : itemCatIds) {
                            if(skuID == 0){
                                continue;
                            }
                           stockItem = new CurrentStockReport();
                           stockItem.setSkuid(skuID);
                            stockItem.setSkuname(skus.get(skuID));
                            stockItem.setReorderLevel(FunctionCalculateProductStock.getReOrderLevel(skuID));
                            stockItem.setBought(FunctionCalculateProductStock.getTotalStocks(skuID));
                            stockItem.setSold(FunctionCalculateProductStock.getTotalSales(skuID));
                            stockItem.setDiff(stockItem.getBought()- stockItem.getSold());
                            stockItem.setPricePerUnit(FunctionCalculateProductStock.getPricePerUnit(skuID));
                            stockItem.setTotal_per_item(stockItem.getPricePerUnit()*stockItem.getDiff());
                            stockItem.setDeviation(stockItem.getDiff()-stockItem.getReorderLevel());
                            req = stockItem.getDiff()>stockItem.getReorderLevel() ? "NO" : "YES";
                            stockItem.setPurchaseRequired(req);
                            allStock.add(stockItem);
                            total_stock_value += stockItem.getTotal_per_item();
                            stockValue+=stockItem.getTotal_per_item();
                           
                        }
        return allStock;
    }
 public static TableView getCurrentStock (){
     TableView tv;
        String[] headers = {"ID", "SKU Name", "In Stock", "Unit Price","Stock x Price", "Reorder Level","Deviation","Reorder Required?"};
        String[] property = {"skuid", "skuname","diff", "pricePerUnit","total_per_item","reorderLevel","deviation","purchaseRequired"};
  
        ArrayList<Object> model = getStocks();

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
 }
}
