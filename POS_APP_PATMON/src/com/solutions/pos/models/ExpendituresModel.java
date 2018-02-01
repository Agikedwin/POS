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
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.ADD_EXPENDITURE;
import static com.solutions.pos.controllers.utilities.PosVariables.EXPENDITUTE_REG;
import java.util.ArrayList;
import javafx.scene.control.TableView;


/**
 *
 * @author DELL
 */
public class ExpendituresModel {
private String     addExpenseId;
private String expenditureid,expenditureName,datepaid ;
private Double amount ;
private String description ;
private String lastUpdated ;
private String doneBy ;
private int status ;
private String edit="Edit";

public ExpendituresModel(){
    
}

    public String getExpenditureid() {
        return expenditureid;
    }

    public void setExpenditureid(String expenditureid) {
        this.expenditureid = expenditureid;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAddExpenseId() {
        return addExpenseId;
    }

    public String getExpenditureName() {
        return expenditureName;
    }

    public void setExpenditureName(String expenditureName) {
        this.expenditureName = expenditureName;
    }

    public void setAddExpenseId(String addExpenseId) {
        this.addExpenseId = addExpenseId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDatepaid() {
        return datepaid;
    }

    public void setDatepaid(String datepaid) {
        this.datepaid = datepaid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }
    
public boolean insertExpenditureModel() {
boolean insert=false;
    String insertSQL = "INSERT INTO " + ADD_EXPENDITURE + "("
            + "            actualexpenseid, expenditureid, amount, description,datepaid, lastupdated, doneby, status)"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?,?)";
    try {
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, addExpenseId);
            preparedStatement.setString(2, expenditureid);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, datepaid);
            preparedStatement.setString(6, lastUpdated);
            preparedStatement.setString(7, doneBy);
            preparedStatement.setInt(8, status);
            preparedStatement.executeUpdate();
insert=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return insert;
    }

    public boolean updateActualExp(String expid) {
boolean update=false;
       try {
            statement.executeUpdate("UPDATE " + ADD_EXPENDITURE + " " +
"   SET  amount="+getAmount()+", description='"+getDescription()+"',datepaid='"+getDatepaid()+"',"
                + " lastupdated='"+getLastUpdated()+"', doneby='"+getDoneBy()+"', status="+getStatus()+" "
                    + "WHERE  expenditureid='"+expid+"'");
        
               update=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update;
    }

    public static  ArrayList readExpenditures() {
       ArrayList<ExpendituresModel> exp = new ArrayList<>();
         ExpendituresModel expenditure;
        String selectSQL = "SELECT  ex.expenditureid,er.expenditureName, ex.amount, ex.description, ex.lastupdated, ex.doneby, ex.status" +
"  FROM " + ADD_EXPENDITURE + " ex ,"
                + " "+EXPENDITUTE_REG+" er  WHERE er.expenditureid=ex.actualexpenseid";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                expenditure = new ExpendituresModel();
                expenditure.setExpenditureid(resultSet.getString("expenditureid"));
                expenditure.setExpenditureName(resultSet.getString("expenditureName"));
                expenditure.setAmount(resultSet.getDouble("amount"));
                expenditure.setDescription(resultSet.getString("description"));
                expenditure.setLastUpdated(resultSet.getString("description"));
                expenditure.setDoneBy(resultSet.getString("doneby"));
                expenditure.setStatus(resultSet.getInt("status"));
                exp.add(expenditure);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return exp;

    }
    public static TableView expendituresData() {

        TableView tv;
        String[] headers = {"Expense ID", "Expense Name", "Description", "Amount" ,"ACTION"};
        String[] property = {"expenditureid", "expenditureName", "description", "amount", "edit"};
        ArrayList<Object> model = readExpenditures();

       InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    
}
