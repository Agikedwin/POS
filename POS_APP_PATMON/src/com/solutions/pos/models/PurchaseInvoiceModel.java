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
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_INVOICES;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class PurchaseInvoiceModel {
 private String invoiceId;
    private String pOrderId;
    private String lastUpdated;
    private String doneBy;
    private String status;

    private PurchaseInvoiceModel() {

    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getpOrderId() {
        return pOrderId;
    }

    public void setpOrderId(String pOrderId) {
        this.pOrderId = pOrderId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
public void insertInvoiceModel(){
    String insertSQL = "INSERT INTO " + PURCHASE_INVOICES + "("
            + "            invoiceid, porderid, lastupdated, doneby, status)"
            + "    VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement=connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, invoiceId);
            preparedStatement.setString(2, pOrderId);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
        }
    }
    
      public void updateInvoiceModel(){
          String updateSQL = "UPDATE " + PURCHASE_INVOICES + ""
                  + "   SET invoiceid=?,  lastupdated=?, doneby=?, status=?"
                  + " WHERE  porderid=?";
        try {
            preparedStatement=connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, invoiceId);
            preparedStatement.setString(2, lastUpdated);
            preparedStatement.setString(3, doneBy);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, pOrderId);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
        }
    }
     public PurchaseInvoiceModel[] getPurchaseInvoice(){
         PurchaseInvoiceModel PurchaseInvoice;
         ArrayList<PurchaseInvoiceModel> pInvoice = new ArrayList<>();
         String selectSQL = "SELECT invoiceid, porderid, lastupdated, doneby, status"
                 + "  FROM " + PURCHASE_INVOICES + "";
         try {
             resultSet = statement.executeQuery(selectSQL);
             while(resultSet.next()){
               PurchaseInvoice=new PurchaseInvoiceModel();
               PurchaseInvoice.setInvoiceId(resultSet.getString("invoiceid"));
               PurchaseInvoice.setpOrderId(resultSet.getString("porderid"));
               pInvoice.add(PurchaseInvoice);
               
             }
         } catch (Exception e) {
         }
          PurchaseInvoiceModel[] pArray = new PurchaseInvoiceModel[pInvoice.size()];
        pInvoice.toArray(pArray);
        return pArray;
                
}
}