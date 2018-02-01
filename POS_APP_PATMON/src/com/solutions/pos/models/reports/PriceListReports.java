/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_CATEGORIES_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class PriceListReports {
   private String skucategoryid;
    private String skuCategoryName;
    private String skuid;
    private String skuname;
    private String description;
    private double retailprice;
    private double   wholesaleprice;
    private int reorderlevel;

    public String getSkucategoryid() {
        return skucategoryid;
    }

    public void setSkucategoryid(String skucategoryid) {
        this.skucategoryid = skucategoryid;
    }

    public String getSkuCategoryName() {
        return skuCategoryName;
    }

    public void setSkuCategoryName(String skuCategoryName) {
        this.skuCategoryName = skuCategoryName;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(double retailprice) {
        this.retailprice = retailprice;
    }

    public double getWholesaleprice() {
        return wholesaleprice;
    }

    public void setWholesaleprice(double wholesaleprice) {
        this.wholesaleprice = wholesaleprice;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
    }
    
     public static TableView itemsPriceListTable(int itemCategories){
       TableView tv;
       String[]headers={"Category Id","SKU Id","Category Name","Item Name","Retail Price","Wholesale Price","Reorder Level"};
       String[] Property={"skucategoryid","skuid","skuCategoryName","skuname","retailprice","wholesaleprice","reorderlevel"};
       ArrayList<Object> model=readItemsPriceList(itemCategories);
       TableViewRenderer tbl = new TableViewRenderer(headers, model, Property);
        tv=tbl.getTable();
       return tv;
        
    }
     public static ArrayList readItemsPriceList(int ItemCategory){
         ArrayList<PriceListReports> readPrices = new ArrayList<>();
       PriceListReports priceList ;
         try {
             String query=("SELECT scr.skucategoryid,sk.skuid ,scr.skuCategoryName,sk.skuname,sk.description ,"
                     + "sk.retailprice,sk.wholesaleprice,sk.reorderlevel "
                     + " FROM " +SKUS_REGISTRATION + "  sk  JOIN  "+SKUS_CATEGORIES_REGISTRATION +" scr ON "
                     + "scr.skucategoryid=sk.skucategoryid  ");
             String condition="";
             if(ItemCategory!=0){
            String skucategoryid = " scr.skucategoryid = "+ItemCategory+" ";
            
                condition= " WHERE  "+skucategoryid+" ";
             }
             query=query+condition;
             resultSet = statement.executeQuery(query);
            while(resultSet.next()){
              priceList  = new PriceListReports();
              priceList.setSkucategoryid(resultSet.getString("skucategoryid"));
              priceList.setSkuid(resultSet.getString("skuid"));
              priceList.setSkuCategoryName(resultSet.getString("skuCategoryName"));
              priceList.setSkuname(resultSet.getString("skuname"));
              priceList.setRetailprice(resultSet.getDouble("retailprice"));
              priceList.setWholesaleprice(resultSet.getDouble("wholesaleprice"));
              priceList.setReorderlevel(resultSet.getInt("reorderlevel"));
              readPrices.add(priceList);
            }
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(PriceListReports.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readPrices;
    }
        
    
}
