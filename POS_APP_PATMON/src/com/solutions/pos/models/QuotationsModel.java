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
import static com.solutions.pos.controllers.utilities.PosVariables.CUSTOMERS;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOATATIONS;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AND
 */
public class QuotationsModel {

    private String quotationId;
    private String customerId;
     private String customerName;
    private int status;
    //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;
    private double total_amount;
    private double total_vat;

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    //Persisting quotation data
    public boolean addNewQuotation(String customer, String order,double vat, double total) {
        QuotationsModel quotation = new QuotationsModel();
        quotation.setCustomerId(customer);
        quotation.setQuotationId(order);
        quotation.setStatus(1);
        quotation.setTotal_vat(vat);
        quotation.setTotal_amount(total);
        quotation.setLast_update(SYSTEM_DATE);
        quotation.setDate_reg(SYSTEM_DATE);
        quotation.setUserId(USER_PROFILE);
        return quotation.addQuotation();
    }

    private boolean addQuotation() {
        boolean success = false;
        try {
            String query = "INSERT INTO " + QUOATATIONS + ""
                    + "(quotationId,customerId,status,total_amount,total_vat,regdate,last_update,userId) VALUES (?,?,?,?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, quotationId);
            preparedStatement.setString(2, customerId);
            preparedStatement.setInt(3, getStatus());
            preparedStatement.setDouble(4, total_amount);
            preparedStatement.setDouble(5, total_vat);
            preparedStatement.setString(6, SYSTEM_DATE);
            preparedStatement.setString(7, SYSTEM_DATE);
            preparedStatement.setString(8, USER_PROFILE);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            success = true;
        } catch (Exception e) {
            System.out.println("Quotations could not be saved " + e.getMessage());
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    public void update() {
        try {
            statement.executeUpdate("UPDATE " + QUOATATIONS + " SET "
                    + " status=" + getStatus() + ","
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE quotationId='" + quotationId + "'");
            System.out.println("Successfully updated the record");
        } catch (Exception e) {
            System.out.println("Could not update the the record for quotation " + e.getMessage());
        }
    }
public static QuotationsModel readQuotation(String quotationID,int status){
        QuotationsModel Quotations = new QuotationsModel();
        try {
            resultSet = statement.executeQuery("SELECT qt.quotationId,qt.customerId,qt.status,qt.total_amount,qt.total_vat,qt.regdate,"
                    + "qt.last_update,qt.userId, cu.customername FROM " + QUOATATIONS + " qt, "
                    + " "+ CUSTOMERS + "  cu where  qt.customerId = cu.customerId and qt.status ="+status+"  and"
                    + "  quotationId = '"+quotationID+"'");
            if (resultSet.next()) {              
                Quotations.setQuotationId(resultSet.getString("quotationId"));
                Quotations.setCustomerId(resultSet.getString("customerId"));
                 Quotations.setCustomerName(resultSet.getString("customername"));
                Quotations.setStatus(resultSet.getInt("status"));
                Quotations.setLast_update(resultSet.getString("last_update"));
                Quotations.setUserId(resultSet.getString("userId"));
                Quotations.setTotal_vat(resultSet.getDouble("total_vat"));
                Quotations.setTotal_amount(resultSet.getDouble("total_amount"));
                
            }
            else{
                MessagesUtil.displayMessage(quotationID+" not found", "Quotation "+quotationID+" was not found", status, null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuotationsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Quotations;
   }
    private static ArrayList readQuotations(int status) {
        ArrayList<QuotationsModel> readAll = new ArrayList<>();
        QuotationsModel Quotations;
        try {
            resultSet = statement.executeQuery("SELECT qt.quotationId,qt.customerId,qt.status,qt.total_amount,qt.total_vat,qt.regdate,"
                    + "qt.last_update,qt.userId, cu.customername FROM " + QUOATATIONS + " qt, "
                    + " "+ CUSTOMERS + "  cu where  qt.customerId = cu.customerId and qt.status ="+status+"");
            while (resultSet.next()) {
                Quotations = new QuotationsModel();
                Quotations.setQuotationId(resultSet.getString("quotationId"));
                Quotations.setCustomerId(resultSet.getString("customerId"));
                 Quotations.setCustomerName(resultSet.getString("customername"));
                Quotations.setStatus(resultSet.getInt("status"));
                Quotations.setLast_update(resultSet.getString("last_update"));
                Quotations.setUserId(resultSet.getString("userId"));
                Quotations.setTotal_vat(resultSet.getDouble("total_vat"));
                Quotations.setTotal_amount(resultSet.getDouble("total_amount"));
                readAll.add(Quotations);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuotationsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
      public static HashMap quotationMap(int status){
        HashMap<String,String> customers = new HashMap(); 
        ArrayList<QuotationsModel> allCustomers = readQuotations(status);
        for(QuotationsModel cust : allCustomers){
            customers.put(cust.getQuotationId(), cust.getQuotationId()+" "+cust.getCustomerName());
        }
        return customers;
    }
}
