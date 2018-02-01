/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDERS;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class PurchaseOrdersModel {
 private String pOrderId;
  private String supplierId;
   private String  lastUpdated ;
    private String  doneBy ;
     private int  status;

     public PurchaseOrdersModel(){

     }

    public String getpOrderId() {
        return pOrderId;
    }

    public void setpOrderId(String pOrderId) {
        this.pOrderId = pOrderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
       public void insertPurchaseOrder() {

           String updateSQL = "INSERT INTO " + PURCHASE_ORDERS + "("
                   + "            porderid, supplierid, lastupdated, doneby, status)"
                   + "    VALUES (?, ?, ?, ?, ?)";
           try {
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, pOrderId);
            preparedStatement.setString(2, supplierId);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, doneBy);
            preparedStatement.setInt(5, status);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }
public void updatePurchaseOrder() {

    String updateSQL = "UPDATE " + PURCHASE_ORDERS + " "
            + "   SET porderid=?, supplierid=?, lastupdated=?, doneby=?, status=?"
            + " WHERE porderid=?, supplierid=?";
        try {
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, pOrderId);
            preparedStatement.setString(2, supplierId);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, doneBy);
            preparedStatement.setInt(5, status);
             preparedStatement.setString(6, pOrderId);
            preparedStatement.setString(7, supplierId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }
    public static ArrayList getPurchaseOrders(int status) {
        PurchaseOrdersModel purchaseOrders;
        ArrayList<PurchaseOrdersModel> pOrders = new ArrayList<>();
        String selectSQL = "SELECT porderid, supplierid, lastupdated, doneby, status"
                + "  FROM  " + PURCHASE_ORDERS + " where status ="+status+" ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                purchaseOrders = new PurchaseOrdersModel();
                purchaseOrders.setpOrderId(resultSet.getString("porderid"));
                purchaseOrders.setSupplierId(resultSet.getString("supplierid"));
                purchaseOrders.setLastUpdated(resultSet.getString("lastupdated"));
                purchaseOrders.setDoneBy(resultSet.getString("doneby"));
                purchaseOrders.setStatus(resultSet.getInt("status"));

                pOrders.add(purchaseOrders);
                System.out.println(""+resultSet.getString("porderid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pOrders;

    }
     public static HashMap purchaseOrdersMap(int status){
        HashMap<String,String> pOrders = new HashMap(); 
        ArrayList<PurchaseOrdersModel> allorders = getPurchaseOrders(status);
        for(PurchaseOrdersModel cust : allorders){
            pOrders.put(cust.getpOrderId(), cust.getpOrderId()+" ");
        }
        return pOrders;
    }
      public static TableView purchaseOrderBasketTV(String pOrderId) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity", "Price", "VAT", "Total"};
        String[] property = {"skuId", "skuName", "quantity", "priceperunit", "vat", "total"};
        ArrayList<Object> model;

        if (pOrderId == null) {
            model = new ArrayList();
        } else {
            model = getPurchaseOrders(1);
        }
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
}
