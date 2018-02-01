/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOATATIONS;
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
public class QuotationReports {

    private String quotationId;
    private int quantity;
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
    private static double cumulativeQuotations = 0.0;
    private static double cumulativeVATQuotation = 0.0;

    public double getCumulativeQuotations() {
        return cumulativeQuotations;
    }

    public void setCumulativeQuotations(double cumulativeQuotations) {
        this.cumulativeQuotations = cumulativeQuotations;
    }

    public double getCumulativeVATQuotation() {
        return cumulativeVATQuotation;
    }

    public void setCumulativeVATQuotation(double cumulativeVATQuotation) {
        this.cumulativeVATQuotation = cumulativeVATQuotation;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    private static ArrayList readQuotationSkus(String quotationID) {
        ArrayList<QuotationReports> readAll = new ArrayList<>();
        QuotationReports quotationSku;
        try {
            resultSet = statement.executeQuery("SELECT qsku.quotationId,qsku.skuid,qsku.quantity,qsku.priceperunit,qsku.vat,qsku.mode,qsku.regdate,qsku.last_update,"
                    + "qsku.userId,sku.skuname FROM " + QUOTATION_SKU + " qsku,"
                    + " " + SKUS_REGISTRATION + " sku  where qsku.skuid = sku.skuid and quotationId = '" + quotationID + "' ");
            while (resultSet.next()) {
                quotationSku = new QuotationReports();
                quotationSku.setQuotationId(resultSet.getString("quotationId"));
                quotationSku.setSkuId(resultSet.getInt("skuid"));
                quotationSku.setSkuName(resultSet.getString("skuname"));
                quotationSku.setQuantity(resultSet.getInt("quantity"));
                quotationSku.setPriceperunit(resultSet.getDouble("priceperunit"));
                quotationSku.setTotal((double) round(quotationSku.getQuantity() * quotationSku.getPriceperunit() * 100) / 100);
                quotationSku.setVat(resultSet.getDouble("vat"));
                quotationSku.setMode(resultSet.getString("mode"));
                quotationSku.setLast_update(resultSet.getString("last_update"));
                quotationSku.setDate_reg(resultSet.getString("regdate"));
                quotationSku.setUserId(resultSet.getString("userId"));
                readAll.add(quotationSku);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuotationReports.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }

    private static ArrayList readQuotation(String dateFrom, String DateTo, String customerid) {
        ArrayList<QuotationReports> readAll = new ArrayList<>();
        QuotationReports quotationRe;
        try {
            cumulativeQuotations = 0.0;
            cumulativeVATQuotation = 0.0;
            resultSet = statement.executeQuery("SELECT q.skuid,r.skuname,sum(q.quantity),q.priceperunit,SUM(q.vat),SUM(q.quantity*q.priceperunit)"
                    + " FROM " + QUOTATION_SKU + " q "
                    + " JOIN " + SKUS_REGISTRATION + " r ON q.skuid = r.skuid "
                    + " JOIN " + QUOATATIONS + " s ON  q.quotationid = s.quotationid "
                    + " where s.customerid = '" + customerid + "' AND (q.regdate BETWEEN '" + dateFrom + "' AND '" + DateTo + "'  ) "
                    + " GROUP BY q.skuid,q.priceperunit,r.skuname ");
            while (resultSet.next()) {
                quotationRe = new QuotationReports();
                quotationRe.setSkuId(resultSet.getInt(1));
                quotationRe.setSkuName(resultSet.getString(2));
                quotationRe.setQuantity(resultSet.getInt(3));
                quotationRe.setPriceperunit(resultSet.getDouble(4));
                quotationRe.setVat(resultSet.getDouble(5));
                quotationRe.setTotal(resultSet.getDouble(6));
                cumulativeQuotations += quotationRe.getTotal();
                cumulativeVATQuotation += quotationRe.getVat();
                readAll.add(quotationRe);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuotationReports.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }

    public static TableView quotationReportTV(String dateFrom, String DateTo, String customerid) {
        TableView tv;
        String[] headers = {"SKU ID", "SKU Name", "Quantity", "Price", "VAT", "Total"};
        String[] property = {"skuId", "skuName", "quantity", "priceperunit", "vat", "total"};
        ArrayList<Object> model;
        model = readQuotation(dateFrom, DateTo, customerid);
//        if (quotationID == null) {
//            model = new ArrayList();
//        } else {
//            model = readQuotationSkus(quotationID);
//        }
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        return tv;
    }
}
