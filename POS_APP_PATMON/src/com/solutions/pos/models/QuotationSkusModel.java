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
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOTATION_SKU;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static java.lang.Math.round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class QuotationSkusModel {

    private String quotationId;
    private double quantity;
    private int skuId;
    private double priceperunit;
    private String mode;
    private String skuName;
    private double vat;
    private double total;
    //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    //Persisting quoatation skus

    public void addQuotationSku() {
        try {
            String query = "INSERT INTO " + QUOTATION_SKU + " (quotationId,skuid,quantity,priceperunit,vat,mode,regdate,last_update,userId)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getQuotationId());
            preparedStatement.setInt(2, getSkuId());
            preparedStatement.setDouble(3, getQuantity());
            preparedStatement.setDouble(4, getPriceperunit());
            preparedStatement.setDouble(5, getVat());
            preparedStatement.setString(6, getMode());
            preparedStatement.setString(7, SYSTEM_DATE);
            preparedStatement.setString(8, SYSTEM_DATE);
            preparedStatement.setString(9, USER_PROFILE);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Quotations sku could not be saved " + e.getMessage());
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
        }
    }

    //check the implementation for pk columns

    public void update(int quotationId) {
        try {
            statement.executeUpdate("UPDATE " + QUOTATION_SKU + " SET quotationid='" + getQuotationId() + "',"
                    + " skuid=" + getSkuId() + ", quantity = " + getQuantity() + ","
                    + " priceperunit=" + getPriceperunit() + ", mode = '" + getMode() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE quotationId=" + quotationId + "");
            System.out.println("Successfully updated the record");
        } catch (Exception e) {
            System.out.println("Could not update the the record for quotation sku " + e.getMessage());
        }
    }

    private static ArrayList readQuotationSkus(String quotationID) {
        ArrayList<QuotationSkusModel> readAll = new ArrayList<>();
        QuotationSkusModel quotationSku;
        try {
            resultSet = statement.executeQuery("SELECT qsku.quotationId,qsku.skuid,qsku.quantity,qsku.priceperunit,qsku.vat,qsku.mode,qsku.regdate,qsku.last_update,"
                    + "qsku.userId,sku.skuname FROM " + QUOTATION_SKU + " qsku,"
                    + " " + SKUS_REGISTRATION + " sku  where qsku.skuid = sku.skuid and quotationId = '" + quotationID + "' ");
            while (resultSet.next()) {
                quotationSku = new QuotationSkusModel();
                quotationSku.setQuotationId(resultSet.getString("quotationId"));
                quotationSku.setSkuId(resultSet.getInt("skuid"));
                quotationSku.setSkuName(resultSet.getString("skuname"));
                quotationSku.setQuantity(resultSet.getDouble("quantity"));
                quotationSku.setPriceperunit(resultSet.getDouble("priceperunit"));
                quotationSku.setTotal( (double) round(quotationSku.getQuantity()*quotationSku.getPriceperunit()*100)/100);
                quotationSku.setVat(resultSet.getDouble("vat"));
                quotationSku.setMode(resultSet.getString("mode"));
                quotationSku.setLast_update(resultSet.getString("last_update"));
                quotationSku.setDate_reg(resultSet.getString("regdate"));
                quotationSku.setUserId(resultSet.getString("userId"));
                readAll.add(quotationSku);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuotationSkusModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }

    public static TableView quotationBasketTV(String quotationID) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity", "Price", "VAT", "Total"};
        String[] property = {"skuId", "skuName", "quantity", "priceperunit", "vat", "total"};
        ArrayList<Object> model;

        if (quotationID == null) {
            model = new ArrayList();
        } else {
            model = readQuotationSkus(quotationID);
        }
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
}
