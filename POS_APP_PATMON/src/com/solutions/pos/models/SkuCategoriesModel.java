/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import com.solutions.entorno.utilities.MessagesUtil;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_CATEGORIES_REGISTRATION;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class SkuCategoriesModel {
    private int skucategoryId;
    private String skuCategoryName;
    private String skuCategoryDescription;
     //Mandatory fields
    private String date_reg;
    private String last_update;
     private String userId;
     private String edit = "EDIT";

    public int getSkucategoryId() {
        return skucategoryId;
    }

    public void setSkucategoryId(int skucategoryId) {
        this.skucategoryId = skucategoryId;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getSkuCategoryName() {
        return skuCategoryName;
    }

    public void setSkuCategoryName(String skuCategoryName) {
        this.skuCategoryName = skuCategoryName;
    }

    public String getSkuCategoryDescription() {
        return skuCategoryDescription;
    }

    public void setSkuCategoryDescription(String skuCategoryDescription) {
        this.skuCategoryDescription = skuCategoryDescription;
    }

    public String getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(String date_reg) {
        this.date_reg = date_reg;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    //Persisting sku categories data
    public static void addNewSkuCategory( String skuCategoryName,String skuCategoryDescription){
              SkuCategoriesModel categories  =  new SkuCategoriesModel(); 
              categories.setSkuCategoryName(skuCategoryName);
              categories.setSkuCategoryDescription(skuCategoryDescription);
              categories.setLast_update(SYSTEM_DATE);
              categories.setDate_reg(SYSTEM_DATE);
              categories.setUserId(USER_PROFILE);
              categories.setSkucategoryId(FunctionGenerateEntityCode.generatedCode(SKUS_CATEGORIES_REGISTRATION));
             categories.addSkuCategory();
    }
  public  boolean addSkuCategory(){
      boolean add=false;
       try {
          String query = "INSERT INTO " +SKUS_CATEGORIES_REGISTRATION+ "(skucategoryname,description,regdate,last_update,userId,skucategoryId) VALUES (?, ?, ?, ?, ?, ?)";
           preparedStatement = connection.prepareStatement(query);
                  preparedStatement.setInt(6, getSkucategoryId());
                    preparedStatement.setString(1, skuCategoryName);
                    preparedStatement.setString(2, skuCategoryDescription);
                    preparedStatement.setString(3, date_reg);
                    preparedStatement.setString(4, last_update);
                    preparedStatement.setString(5, userId);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    add=true;
//        MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved SKU category details", 1, null);
       } catch (Exception e) {
           add=false;
           e.printStackTrace();
//    MessagesUtil.displayMessage("Save Operation Failed", "Failed to save SKU category details", 3, e);     
   }
       return add;
   }
   
  public boolean update(){
      boolean success = false;
       try {
        statement.executeUpdate("UPDATE " +SKUS_CATEGORIES_REGISTRATION + " SET skucategoryname='" + getSkuCategoryName() + "',"
                        + " description='" +getSkuCategoryDescription() + "',"
                        + " last_update = '" +SYSTEM_DATE + "', userid = '" +USER_PROFILE + "'"
                        + " WHERE skucategoryId=" + skucategoryId + "");
           ;
           success = true;
       } catch (Exception e) {
           success = false;
       }
      return  success;
   }
   public static  ArrayList readCategories(){
        ArrayList<SkuCategoriesModel> readAll = new ArrayList<>();
       SkuCategoriesModel categories ;
         try {
             resultSet = statement.executeQuery("SELECT skucategoryId,skucategoryname,description,regdate,last_update,userId FROM " +SKUS_CATEGORIES_REGISTRATION + " ");
            while(resultSet.next()){
              categories  = new SkuCategoriesModel();
              categories.setSkucategoryId(resultSet.getInt("skucategoryId"));
              categories.setSkuCategoryName(resultSet.getString("skucategoryname"));
              categories.setSkuCategoryDescription(resultSet.getString("description"));
              categories.setLast_update(resultSet.getString("last_update"));
              categories.setUserId(resultSet.getString("userId"));
              
              readAll.add(categories);
            }
         } catch (SQLException ex) {
             Logger.getLogger(SkuCategoriesModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }
   
   public static TableView CategoriesData() {

        TableView tv;
        String[] headers = {"ID","NAME","DESCRIPTION","#ACTION"};
        String[] property = {"skucategoryId","skuCategoryName","skuCategoryDescription","edit"};
        ArrayList<Object> model = readCategories();

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
   
    public static HashMap FetchSkuCategories(){
        HashMap<Integer,String> readAll = new HashMap<>();  
         try {
             readAll.put(0, "All Categories");
             resultSet = statement.executeQuery("SELECT skucategoryId,skuCategoryName from "+SKUS_CATEGORIES_REGISTRATION+"  ");
            while(resultSet.next()){               
                readAll.put(resultSet.getInt("skucategoryId"),resultSet.getString("skuCategoryName"));
               }
         } catch (SQLException ex) {
             MessagesUtil.displayMessage("Records Retrieval Failed", "Failed to fetch SKU Categories records", 3, ex);
         }
          return readAll;
   }
}
