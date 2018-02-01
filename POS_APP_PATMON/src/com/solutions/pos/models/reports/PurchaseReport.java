/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDER_ITEMS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class PurchaseReport {
   String skuname;
   int quantity;
   double unitprice;
   String supplierid;
   String porder;
   String skuid;

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }
   
    public String getPorder() {
        return porder;
    }

    public void setPorder(String porder) {
        this.porder = porder;
    }
   
    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }
   public static TableView purchaseTable(int itemCategories){
       TableView tv;
       String[]headers={"OrderId","SKU Id","Sku Name","Quantity","Unit Price","SupplierId"};
       String[] Property={"orderid","skuid","skuname","quantity","unitprice","supplierid"};
       ArrayList<Object> model=readPurchases();
       TableViewRenderer tbl = new TableViewRenderer(headers, model, Property);
        tv=tbl.getTable();
       return tv;
        
    }
   public static ArrayList readPurchases(){
         ArrayList<PurchaseReport> readAllpurchases = new ArrayList<>();
       PurchaseReport purchase ;
         try {
             String query=("SELECT poi.porderid,poi.skuid ,sku.skuname,poi.quantity ,"
                     + "poi.unitprice,po.supplierid "
                     + " FROM " +PURCHASE_ORDER_ITEMS + "  poi  JOIN  "+PURCHASE_ORDERS +" po ON "
                     + "poi.porderid=po.porderid JOIN   "+ SKUS_REGISTRATION +" sku ON poi.skuid = sku.skuid ");
//             String condition="";
//             if(ItemCategory!=0){
//            String skucategoryid = " scr.skucategoryid = "+ItemCategory+" ";
//            
//                condition= " WHERE  "+skucategoryid+" ";
//             }
            
             System.out.println(query);
            
             resultSet = statement.executeQuery(query);
            while(resultSet.next()){
              purchase  = new PurchaseReport();
               purchase.setSkuid(resultSet.getString("porderid"));
              purchase.setSkuid(resultSet.getString("skuid"));
              purchase.setSkuname(resultSet.getString("skuname"));
              purchase.setSkuname(resultSet.getString("quantity"));
              purchase.setQuantity(resultSet.getInt("unitprice"));
              purchase.setSupplierid(resultSet.getString("supplierid"));
                 
              readAllpurchases.add(purchase);
            }
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(PriceListReports.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAllpurchases;
    }
        
    
}
