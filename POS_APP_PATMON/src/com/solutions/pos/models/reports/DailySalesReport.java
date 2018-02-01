/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import com.solutions.pos.models.SkuOrdersModel;
import static java.lang.Math.round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author shaddie
 */
public class DailySalesReport {

    private String orderId;
    private String receiptno;
    private double total_amount;
    private double total_vat;
    private String regdate;
    private String user;

    private static double cumulativeSales = 0.0;
    private static double cumulativeVAT = 0.0;

    public double getCumulativeSales() {
        return (double) round(cumulativeSales * 1000) / 1000;
    }

    public void setCumulativeSales(double cumulativeSales) {
        this.cumulativeSales = cumulativeSales;
    }

    public double getCumulativeVAT() {
        return (double) round(cumulativeVAT * 1000) / 1000;
    }

    public void setCumulativeVAT(double cumulativeVAT) {
        this.cumulativeVAT = cumulativeVAT;
    }

    public DailySalesReport() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(double total_vat) {
        this.total_vat = total_vat;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static TableView dailySalesReport(String date, String userid, int status) {
        TableView tv;
        String[] headers = {"ID", "Receipt No", "Amount", "Vat", "Date", "User"};
        String[] property = {"orderId", "receiptno", "total_amount", "total_vat", "regdate", "user"};
        ArrayList<Object> model = readSkusales(date, userid, status);

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    private static ArrayList readSkusales(String date, String userid, int status) {
        ArrayList<DailySalesReport> readAll = new ArrayList<>();
        DailySalesReport skuorder;
        try {
            String query = "SELECT customerid,orderId,status,receiptno,regdate,"
                    + "last_update,userId,total_amount,total_vat FROM " + SKU_ORDERS + ""
                    + " ";
            String whereCondition = null;
            String dates = " regdate = '" + date + "' ";
            String user = " userId = '" + userid + "' ";
            String state = " status = " + status + " ";

            whereCondition = " WHERE " + state;
            if (date != null && userid == null) {
                whereCondition = whereCondition + " AND " + dates;
            } else if (userid != null && date == null) {
                whereCondition = whereCondition + " AND " + user;
            } else if (date != null && userid != null) {
                whereCondition = whereCondition + " AND " + dates + " AND " + user;
            }
            if (whereCondition != null) {
                query = query + whereCondition;
            }
            cumulativeSales = 0.0;
            cumulativeVAT = 0.0;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                skuorder = new DailySalesReport();
                skuorder.setOrderId(resultSet.getString("orderId"));
                skuorder.setReceiptno(resultSet.getString("receiptno"));
                skuorder.setRegdate(resultSet.getString("regdate"));
                skuorder.setTotal_vat(resultSet.getDouble("total_vat"));
                skuorder.setTotal_amount(resultSet.getDouble("total_amount"));
                cumulativeSales += skuorder.getTotal_amount();
                cumulativeVAT += skuorder.getTotal_vat();
                skuorder.setUser(resultSet.getString("userId"));
                readAll.add(skuorder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkuOrdersModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
}
