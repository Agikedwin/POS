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
import static com.solutions.pos.controllers.utilities.PosVariables.INVOICESKU;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static java.lang.Math.round;
import java.util.ArrayList;
import javafx.scene.control.TableView;

/**
 *
 * @author DELL
 */
public class InvoiceSkuModel {

    private String invoiceId;
    private int skuId;
    private double quantity;
     private String skuname;
    private double unitPrice;
    private double total ;
   private double vat;
    private String lastUpdated;
     private String regdate;
    private String doneBy;
    private int status;

    public InvoiceSkuModel() {

    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the skuname
     */
    public String getSkuname() {
        return skuname;
    }

    /**
     * @param skuname the skuname to set
     */
    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the vat
     */
    public double getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(double vat) {
        this.vat = vat;
    }

    /**
     * @return the regdate
     */
    public String getRegdate() {
        return regdate;
    }

    /**
     * @param regdate the regdate to set
     */
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public void insertInvoiceskuModel() {

        String insertSQL = "INSERT INTO " + INVOICESKU + " ("
                + "            invoiceid, skuid, quantity, unitprice, lastupdated, doneby, status,regdate,vat)"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, invoiceId);
            preparedStatement.setInt(2, skuId);
            preparedStatement.setDouble(3, quantity);
            preparedStatement.setDouble(4, unitPrice);
            preparedStatement.setString(5, lastUpdated);
            preparedStatement.setString(6, doneBy);
            preparedStatement.setInt(7, status);
            preparedStatement.setString(8, regdate);
            preparedStatement.setDouble(9, getVat());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void updateInvoiceModel() {

        String updateSQL = "UPDATE " + INVOICESKU + " "
                + "   SET  quantity=?, unitprice=?, lastupdated=?, doneby=?, status=?"
                + " WHERE invoiceid=?, skuid=? ";
        try {
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setDouble(1, quantity);
            preparedStatement.setDouble(2, unitPrice);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, doneBy);
            preparedStatement.setInt(5, status);
            preparedStatement.setString(6, invoiceId);
            preparedStatement.setInt(7, skuId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
    }

    private static ArrayList getInvoiceSkulModel( String invoiceID) {
        InvoiceSkuModel invoiceSku;

        ArrayList<InvoiceSkuModel> iSku = new ArrayList<>();
        String selectSQL = "SELECT inv.invoiceid, inv.skuid, inv.quantity, inv.unitprice, inv.lastupdated, inv.doneby, "
                + "inv.status,sku.skuname "
                + "  FROM " + INVOICESKU + " inv " + SKUS_REGISTRATION + " sku  where inv.skuid = sku.skuid and  invoiceid = '"+invoiceID+"'";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                invoiceSku = new InvoiceSkuModel();
                invoiceSku.setInvoiceId(resultSet.getString("invoiceid"));
                invoiceSku.setSkuId(resultSet.getInt("skuid"));
                invoiceSku.setSkuname(resultSet.getString("skuname"));
                invoiceSku.setQuantity(resultSet.getInt("quantity"));
                invoiceSku.setUnitPrice(resultSet.getInt("unitprice"));
                invoiceSku.setTotal((double) round(invoiceSku.getQuantity()*invoiceSku.getUnitPrice()*100)/100);
                invoiceSku.setLastUpdated(resultSet.getString("lastupdated"));
                invoiceSku.setDoneBy(resultSet.getString("doneby"));
                invoiceSku.setStatus(resultSet.getInt("status"));
                iSku.add(invoiceSku);

            }
        } catch (Exception e) {
        }
        return iSku;
    }
     public static TableView quotationBasketTV(String quotationID) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity", "Price", "VAT", "Total"};
        String[] property = {"skuId", "skuname", "quantity", "unitPrice", "vat", "total"};
          ArrayList<Object> model;

        if (quotationID == null) {
            model = new ArrayList();
        } else {
            model = getInvoiceSkulModel(quotationID);
        }
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
}
