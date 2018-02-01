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
public class PurchaseOrderReports {

    private String skuName;
    private int quantity;
    private int skuId;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }
    private static ArrayList readPurchaseOrders(String dateFrom, String DateTo, String supplierid) {
        ArrayList<PurchaseOrderReports> readAll = new ArrayList<>();
        PurchaseOrderReports pOrder;
        try {
            resultSet = statement.executeQuery("SELECT i.skuid,r.skuname,i.quantity "
                    + " FROM " + PURCHASE_ORDER_ITEMS + " i "
                    + " JOIN " + SKUS_REGISTRATION + " r ON i.skuid = r.skuid "
                    + " JOIN " + PURCHASE_ORDERS + " o ON  i.porderid = o.porderid "
                    + " where o.supplierid = '" + supplierid + "' AND (i.lastupdated BETWEEN '" + dateFrom + "' AND '" + DateTo + "'  ) "
                    + "  ");
            while (resultSet.next()) {
                pOrder = new PurchaseOrderReports();
                pOrder.setSkuId(resultSet.getInt(1));
                pOrder.setSkuName(resultSet.getString(2));
                pOrder.setQuantity(resultSet.getInt(3));
                readAll.add(pOrder);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(PurchaseOrderReports.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }

    public static TableView purchaseOrderReportTV(String dateFrom, String DateTo, String supplierid) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity"};
        String[] property = {"skuId", "skuName", "quantity", };
        ArrayList<Object> model;
        model = readPurchaseOrders(dateFrom, DateTo, supplierid);
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        return tv;
    }
}
