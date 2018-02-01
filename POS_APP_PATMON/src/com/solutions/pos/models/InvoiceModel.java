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
import static com.solutions.pos.controllers.utilities.PosVariables.INVOICES;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class InvoiceModel {
     private String invoiceId;
    private String quatationId;
    private String lastUpdated;
    private String doneBy;
    private int status;
    
    public InvoiceModel(){
        
    }

    public String getQuatationId() {
        return quatationId;
    }

    public void setQuatationId(String quatationId) {
        this.quatationId = quatationId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
    public void insertInvoiceModel(){
        String insertSQL = "INSERT INTO " + INVOICES + "("
                + "            invoiceid, quatationid, lastupdated, doneby, status)"
                + "    VALUES (?, ?, ?, ?, ?)"; 
        try {
            preparedStatement=connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, invoiceId);
            preparedStatement.setString(2, quatationId);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, doneBy);
            preparedStatement.setInt(5, status);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public void updateInvoiceModel(){
          String updateSQL = "UPDATE " + INVOICES + ""
                  + "   SET invoiceid=?, quatationid=?, lastupdated=?, doneby=?, status=?"
                  + " WHERE invoiceid=?, quatationid=?";
        try {
            preparedStatement=connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, invoiceId);
            preparedStatement.setString(2, quatationId);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setInt(4, status);
            preparedStatement.setString(5, invoiceId);
            preparedStatement.setString(6, quatationId);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public ArrayList getInvoces(){
         InvoiceModel invoice ;
         ArrayList<InvoiceModel> invoices = new ArrayList<>();
         String selectSQL = "SELECT invoiceid, quatationid, lastupdated, doneby, status"
                 + "  FROM " + INVOICES + " ";
         try {
             resultSet = statement.executeQuery(selectSQL);
             while(resultSet.next()){
               invoice=new InvoiceModel();
               invoice.setInvoiceId(resultSet.getString("invoiceid"));
               invoice.setQuatationId(resultSet.getString("quatationid"));
               invoices.add(invoice);
               
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
        
        return invoices;
     }
}
