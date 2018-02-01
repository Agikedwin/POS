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
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDER_ITEMS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import java.util.ArrayList;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class PurchaseOrderItemsModel {

    private String pOrderId;
    private int skuId;
    private int quantity;
     private String skuName;
    private String lastUpdated;
    private String doneBy;
    private int status;

    public PurchaseOrderItemsModel() {

    }

    public int getSkuId() {
        return skuId;
    }

    public String getpOrderId() {
        return pOrderId;
    }

    /**
     * @return the skuName
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * @param skuName the skuName to set
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setpOrderId(String pOrderId) {
        this.pOrderId = pOrderId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void insertPurcaseOrderItemsModel() {
        String insertSQL = "INSERT INTO " + PURCHASE_ORDER_ITEMS + "("
                + "            porderid, skuid, quantity, lastupdated, doneby,status)"
                + "    VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, pOrderId);
            preparedStatement.setInt(2, skuId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setString(4, lastUpdated);
            preparedStatement.setString(5, doneBy);
            preparedStatement.setInt(6, status);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void updatePurchaseOrderItems() {
        String updateSQL = "UPDATE " + PURCHASE_ORDER_ITEMS + ""
                + "   SET  quantity=?, lastupdated=?, "
                + "       doneby=? "
                + " WHERE porderid=?, skuid=?";
        try {
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, lastUpdated);
            preparedStatement.setString(3, doneBy);
            preparedStatement.setString(4, pOrderId);
            preparedStatement.setInt(5, skuId);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }

    private static ArrayList getPurchaseOrderItems(String orderId) {
        PurchaseOrderItemsModel PurchaseOrderItems;
        ArrayList<PurchaseOrderItemsModel> pItems = new ArrayList<>();
        String selectSQL = "SELECT po.porderid, po.skuid, po.quantity,sku_r.skuname "
                + "  FROM " + PURCHASE_ORDER_ITEMS + " po,"+SKUS_REGISTRATION + " sku_r  WHERE "
                + " po.skuid = sku_r.skuid and po.porderid = '"+orderId+"'";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                PurchaseOrderItems = new PurchaseOrderItemsModel();
                PurchaseOrderItems.setpOrderId(resultSet.getString("porderid"));
                PurchaseOrderItems.setSkuId(resultSet.getInt("skuid"));
                PurchaseOrderItems.setQuantity(resultSet.getInt("quantity"));
                PurchaseOrderItems.setSkuName(resultSet.getString("skuname"));
                pItems.add(PurchaseOrderItems);

            }
        } catch (Exception e) {
        }
        return pItems;

    }
    public static TableView purchaseOrderSkus(String orderId) {

        TableView tv;
        String[] headers = {"SKU Id","SKU Name","Quantity"};
        String[] property = {"skuId","skuName","quantity"};
        ArrayList<Object> model = getPurchaseOrderItems(orderId);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        
        return tv;
    }
}
