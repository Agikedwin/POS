/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_STOCKS;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class SkuStocksModel {
     private int skuId;
    private String skuName;
    private String dateOfPurchase;
    private double quantity;
    private double priceperunit;
    private String batchNo;
    private String supplierid;
    private int stockid;
   //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;
     private String edit="Edit";
      private String delete="Delete";
    

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPriceperunit() {
        return priceperunit;
    }

    public void setPriceperunit(double priceperunit) {
        this.priceperunit = priceperunit;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }
    
    public boolean addSkuStocks(){
        boolean success=false;
       try {
           int stockid = FunctionGenerateEntityCode.generatedCode(SKUS_STOCKS);
          String query = "INSERT INTO " +SKUS_STOCKS+ " "
                  + "(skuId,dateofpurchase,quantity,priceperunit,batchno,regdate,last_update,userId,stockid,supplierid) VALUES (?,?, ?, ?, ?, ?, ?, ?,?,?)";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, getSkuId());
                    preparedStatement.setString(2, getDateOfPurchase());
                    preparedStatement.setDouble(3, quantity);
                    preparedStatement.setDouble(4, getPriceperunit());
                    preparedStatement.setString(5, getBatchNo());
                    preparedStatement.setString(6, SYSTEM_DATE);
                    preparedStatement.setString(7, SYSTEM_DATE);
                    preparedStatement.setString(8, USER_PROFILE);
                     preparedStatement.setInt(9, stockid);
                     preparedStatement.setString(10, supplierid);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    success=true;
//                    MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved SKU Stock Details", 1, null);

       } catch (Exception e) {
           e.printStackTrace();
           success=false;
//       MessagesUtil.displayMessage("Save Operation Failed", "Failed to save SKU Stock Details", 3, e);

       }
       return success;
    }
  public void update(String skuId){
       try {
        statement.executeUpdate("UPDATE "+ SKUS_STOCKS + " SET dateofpurchase='" + getDateOfPurchase() + "',"
                        + " quantity = " +getQuantity() + ", priceperunit=" +getPriceperunit() + ","
                        + " batchno=" +getBatchNo() + ","
                        + " last_update = '" +SYSTEM_DATE + "', userid = '" +USER_PROFILE + "'"
                        + " WHERE skuId=" + skuId + "");
           System.out.println("Successfully updated the record");
       } catch (Exception e) {
           System.out.println("Could not update the the record for sku stocks "+e.getMessage());
       }
   }
  public ArrayList readSkusStocks(){
        ArrayList<SkuStocksModel> readAll = new ArrayList<>();
       SkuStocksModel stock ;
         try {
             resultSet = statement.executeQuery("SELECT skuid, dateofpurchase, quantity, priceperunit, batchno, regdate, " +
"       last_update, userid, stockid, supplierid FROM " +SKUS_STOCKS + " ");
            while(resultSet.next()){
             stock  = new SkuStocksModel();
             stock.setSkuId(resultSet.getInt(1));
             stock.setDateOfPurchase(resultSet.getString(2));
             stock.setPriceperunit(resultSet.getFloat(4));
             stock.setQuantity(resultSet.getInt(3));
              stock.setBatchNo(resultSet.getString(5));
             stock.setLast_update(resultSet.getString(5));
             stock.setUserId(resultSet.getString(6));
             stock.setStockid(resultSet.getInt(9));
             stock.setSupplierid(resultSet.getString(10));
              readAll.add(stock);
            }
         } catch (SQLException ex) {
             Logger.getLogger(SkuStocksModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }
  
   public double buyingPrice(int skuid){
        double bp=0;
         try {
             resultSet = statement.executeQuery("SELECT skuid, dateofpurchase, quantity, priceperunit, "
                     + "batchno, regdate  , last_update, userid, supplierid "
                     + "FROM " +SKUS_STOCKS + " WHERE skuid="+skuid+"  ORDER BY stockid DESC ");
           if(resultSet.next()){
             bp=resultSet.getDouble("priceperunit");
           
           }
         } catch (SQLException ex) {
             Logger.getLogger(SkuStocksModel.class.getName()).log(Level.SEVERE, null, ex);
             ex.printStackTrace();
         }
          return bp;
   }
    public static ArrayList readSkusStocksActions(int skuid){
        ArrayList<SkuStocksModel> readAll = new ArrayList<>();
       SkuStocksModel stock ;
         try {
             resultSet = statement.executeQuery("SELECT skuid, dateofpurchase, quantity, priceperunit, batchno, regdate, " +
"       last_update, userid, stockid, supplierid FROM " +SKUS_STOCKS + "  WHERE skuid="+skuid+"  ORDER BY regdate DESC ");
            while(resultSet.next()){
             stock  = new SkuStocksModel();
             stock.setSkuId(resultSet.getInt(1));
             stock.setDateOfPurchase(resultSet.getString(2));
             stock.setPriceperunit(resultSet.getFloat(4));
             stock.setQuantity(resultSet.getInt(3));
              stock.setBatchNo(resultSet.getString(5));
             stock.setLast_update(resultSet.getString(5));
             stock.setUserId(resultSet.getString(6)); 
              stock.setStockid(resultSet.getInt(9));
             stock.setSupplierid(resultSet.getString(10));
              readAll.add(stock);
            }
         } catch (SQLException ex) {
             Logger.getLogger(SkuStocksModel.class.getName()).log(Level.SEVERE, null, ex);
         }
          return readAll;
   }
     public boolean updateAction(int stockid,double quantity,double buyingPrice){
         boolean update=false;
       try {
        statement.executeUpdate("UPDATE "+ SKUS_STOCKS + " SET quantity = " +quantity + ", priceperunit=" + buyingPrice+ ","
                + " last_update = '" +SYSTEM_DATE + "', userid = '" +USER_PROFILE + "' WHERE stockid=" + stockid + "");
          update=true;
           System.out.println("updated");
       } catch (Exception e) {
           e.printStackTrace();
       }
       return update;
   }
   public static TableView SkusStockActions(int skuId) {

        TableView tv;
        //"Category Id","Category Name", "Wholesale Price",
        //"skuCategoryId","skuCatName",,"reorderLevel","wholeSalePrice","Re-order Level"
        String[] headers = {"SKU Id", "Quantity", "Buying Price", "Date Purchased", "#ACTION"};
        String[] property = {"skuId", "quantity",  "priceperunit","dateOfPurchase", "Edit"};
        ArrayList<Object> model = readSkusStocksActions(skuId);

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        // tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tv;
    }
}
