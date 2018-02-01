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
import com.solutions.pos.models.SkuCategoriesModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class RegisteredItemsReport {
    private String skucategoryid;
    private String skuCategoryName;
    private String skuid;
    private String skuname;
    private String description;

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
    
    public static TableView registeredItemsTable(int itemCategories){
       TableView tv;
     
       String[]headers={"Category Id","Category Name","Item Name","Description"};
       String[] Property={"skucategoryid","skuid","skuCategoryName","skuname","description"};
       ArrayList<Object> model=readRegisteredItems(itemCategories);
       TableViewRenderer tbl = new TableViewRenderer(headers, model, Property);
        tv=tbl.getTable();
       return tv;
        
    }
    
    public static ArrayList readRegisteredItems(int ItemCategory){
         ArrayList<RegisteredItemsReport> readAll = new ArrayList<>();
       RegisteredItemsReport regItems ;
         try {
             String query=("SELECT scr.skucategoryid,sk.skuid ,scr.skuCategoryName,sk.skuname,sk.description"
                     + " FROM " +SKUS_CATEGORIES_REGISTRATION + "  scr  JOIN  "+SKUS_REGISTRATION +" sk ON "
                     + "scr.skucategoryid=sk.skucategoryid  ");
             String condition="";
             if(ItemCategory!=0){
            String skucategoryid = " scr.skucategoryid = "+ItemCategory+" ";
            
                condition= " WHERE  "+skucategoryid+" ";
             }
             query=query+condition;
             resultSet = statement.executeQuery(query);
            while(resultSet.next()){
              regItems  = new RegisteredItemsReport();
              regItems.setSkucategoryid(resultSet.getString("skucategoryid"));
              regItems.setSkuid(resultSet.getString("skuid"));
              regItems.setSkuCategoryName(resultSet.getString("skuCategoryName"));
              regItems.setSkuname(resultSet.getString("skuname"));
              regItems.setDescription(resultSet.getString("description"));
              
              readAll.add(regItems);
            }
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(SkuCategoriesModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
    }
        
    
 
}
