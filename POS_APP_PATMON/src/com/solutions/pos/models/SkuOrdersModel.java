/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import com.solutions.entorno.utilities.MessagesUtil;
import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class SkuOrdersModel {
  private String customerid;
  private String orderid;
  private int status;
  private int receiptno;
  
    //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;
    private double total_amount;
    private double total_vat;
    private String load;
    public double getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(double total_vat) {
        this.total_vat = total_vat;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
    
    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(int receiptno) {
        this.receiptno = receiptno;
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
     //Persisting SkuOrdersModel data
    
    public  boolean addNewSkuOrder(String customer , String order, int receiptno,double vat,double total){
       
         if(customer.equals("")){
             customer = "TMP"+receiptno;
         }
       SkuOrdersModel skuorder  = new SkuOrdersModel();
            skuorder.setCustomerid(customer);
            skuorder.setOrderid(order);
            skuorder.setStatus(1);
            skuorder.setReceiptno(receiptno);
             skuorder.setTotal_vat(vat);
              skuorder.setTotal_amount(total);
            skuorder.setLast_update(SYSTEM_DATE);
            skuorder.setDate_reg(SYSTEM_DATE);
            skuorder.setUserId(USER_PROFILE); 
           
         return  skuorder.addSkuOrders();
    }
   private boolean addSkuOrders(){
       boolean success = false;
       try {
           
          String query = "INSERT INTO " +SKU_ORDERS+ " "
                  + "(customerId,orderId,status,receiptno,regdate,last_update,userId,total_amount,total_vat)"
                  + " VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
           preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, getCustomerid());
                    preparedStatement.setString(2, getOrderid());
                    preparedStatement.setInt(3, getStatus());
                    preparedStatement.setString(4, Integer.toString(getReceiptno()));
                    preparedStatement.setString(5, date_reg);
                    preparedStatement.setString(6, last_update);
                    preparedStatement.setString(7, userId);
                    preparedStatement.setDouble(8, total_amount);
                    preparedStatement.setDouble(9, total_vat);
                    preparedStatement.executeUpdate();
                  preparedStatement.close();
                  success = true;
       } catch (Exception e) {
           MessagesUtil.displayMessage("Save Operation Failed", "Failed to  saved SKU Order details", 3, e);
       }
       return success;
    }
   //check the implementation for pk columns
    public void update(String orderId){
       try {
        statement.executeUpdate("UPDATE "+ SKU_ORDERS + " SET "
                + " total_amount = "+getTotal_amount()+",total_vat = "+getTotal_vat()+","
                        + " last_update = '" +SYSTEM_DATE + "', userid = '" +USER_PROFILE + "'"
                        + " WHERE orderId='" + orderId + "'");
          // System.out.println("Successfully updated the record");
       } catch (Exception e) {
           e.printStackTrace();
          // System.out.println("Could not update the the record for sku orders "+e.getMessage());
       }
   }
    public void updateState(String orderId){
       try {
        statement.executeUpdate("UPDATE "+ SKU_ORDERS + " SET status=" +getStatus() + " WHERE orderId='" + orderId + "'");
           } catch (Exception e) {
           System.out.println("Could not update the the record for sku orders "+e.getMessage());
       }
   }
    
     public static TableView salesOrdersDetails(int status , String receiptno ) {

        TableView tv;
        String[] headers = {"Receipt No", "Total Amount", "Total VAT","Date",  "#ACTION"};
        String[] property = {"receiptno", "total_amount", "total_vat", "date_reg","load"};
        ArrayList<Object> model = readSkusOrders( status , receiptno );

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        // tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tv;
    }
     
  private static ArrayList readSkusOrders(int status , String receiptno ){
        ArrayList<SkuOrdersModel> readAll = new ArrayList<>();
       SkuOrdersModel skuorder ;
         try {
             resultSet = statement.executeQuery("SELECT customerId,orderId,status,receiptno,regdate,last_update,userId,total_amount,total_vat FROM " +SKU_ORDERS + ""
                    + " where status = "+status+" and (receiptno "+SystemVariables.SEARCH_VALUE+" '%"+receiptno+"%' "
                     + "or regdate "+SystemVariables.SEARCH_VALUE+" '%"+receiptno+"%') ORDER BY regdate DESC LIMIT 20  ");
            while(resultSet.next()){
            skuorder  = new SkuOrdersModel();
            skuorder.setCustomerid(resultSet.getString("customerId"));
            skuorder.setOrderid(resultSet.getString("orderId"));
            skuorder.setStatus(resultSet.getInt("status"));
            skuorder.setReceiptno(resultSet.getInt("receiptno"));
            skuorder.setLast_update(resultSet.getString("last_update"));
             skuorder.setDate_reg(resultSet.getString("regdate"));
             skuorder.setTotal_vat(resultSet.getDouble("total_vat"));
              skuorder.setTotal_amount(resultSet.getDouble("total_amount"));
            skuorder.setUserId(resultSet.getString("userId")); 
            skuorder.setLoad("LOAD");
              readAll.add(skuorder);
            }
         } catch (SQLException ex) {
             Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }
  
  public static SkuOrdersModel readSkusOrder(String orderId){
       
       SkuOrdersModel skuorder = new SkuOrdersModel();
         try {
             resultSet = statement.executeQuery("SELECT customerId,orderId,status,receiptno,regdate,last_update,userId,total_amount,total_vat FROM "
                     +SKU_ORDERS + " where orderid = '"+orderId+"'  ");
            while(resultSet.next()){
            skuorder.setCustomerid(resultSet.getString("customerId"));
            skuorder.setOrderid(resultSet.getString("orderId"));
            skuorder.setStatus(resultSet.getInt("status"));
            skuorder.setReceiptno(resultSet.getInt("receiptno"));
            skuorder.setLast_update(resultSet.getString("last_update"));
             skuorder.setDate_reg(resultSet.getString("regdate"));
             skuorder.setTotal_vat(resultSet.getDouble("total_vat"));
              skuorder.setTotal_amount(resultSet.getDouble("total_amount"));
            skuorder.setUserId(resultSet.getString("userId")); 
            }
         } catch (SQLException ex) {
             Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return skuorder;
   }
 
  public static ArrayList getOrders( int status){
    ArrayList savedTrans = new ArrayList();
      try {
          resultSet = statement.executeQuery("select orderId from "+ SKU_ORDERS + " where status = "+status+"");
          while(resultSet.next()){
              savedTrans.add(resultSet.getString(1));
          }
      } catch (Exception e) {
      }
    return savedTrans;  
  }
   public static void deleteSavedTransactionOrder(String orderId){
        try {
            statement.executeUpdate("DELETE from " + SKU_ORDERS + " where orderid = '"+orderId+"'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
