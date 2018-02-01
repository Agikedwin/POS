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
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.EXPENDITUTE_REG;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TableView;



/**
 *
 * @author DELL
 */
public class ExpenditureRegistrationModel {

    private String expenditureId;
    private String expenditureName;
    private String description;
    private String lastUpdated;
    private String regdate;
    private String doneBy;
   private String delete="Delete";

    private int status;
    ArrayList<String> data = new ArrayList<>();

    public ExpenditureRegistrationModel() {
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public String getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(String expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getExpenditureName() {
        return expenditureName;
    }

    public void setExpenditureName(String expenditureName) {
        this.expenditureName = expenditureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
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

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public static void addNewExpenseType(String expenditureName, String description) {
        ExpenditureRegistrationModel expenditureReg = new ExpenditureRegistrationModel();
        expenditureReg.setExpenditureId(expenditureReg.generateExpenseNo());
        expenditureReg.setExpenditureName(expenditureName);
        expenditureReg.setDescription(description);
        expenditureReg.setStatus(1);
        expenditureReg.setRegdate(SYSTEM_DATE);
        expenditureReg.setLastUpdated(SYSTEM_DATE);
        expenditureReg.setDoneBy(USER_PROFILE);
        expenditureReg.addExpenseReg();
    }

    private void addExpenseReg() {

        String insertSQL = "INSERT INTO " + EXPENDITUTE_REG + " ("
                + "            expenditureid, expenditurename, description, lastupdated, doneby,  status,regdate)"
                + "    VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, expenditureId);
            preparedStatement.setString(2, expenditureName);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, lastUpdated);
            preparedStatement.setString(5, doneBy);
            preparedStatement.setInt(6, status);
            preparedStatement.setString(7, regdate);
            preparedStatement.executeUpdate();
//            MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved Expense Type details", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
//            MessagesUtil.displayMessage("Save Operation Failed", "Failed to save Expense Type details", 3, e);

        }
    }

    public void updateExpeditureRegModel() {

        String updateSQL = "UPDATE" + EXPENDITUTE_REG + " "
                + "   SET expenditureid=?, expenditurename=?, description=?, lastupdated=?, doneby=?, status=?"
                + " WHERE expenditureid=? ";
        try {
            preparedStatement = connection.prepareStatement(updateSQL);
           

            preparedStatement.setString(1, expenditureName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, lastUpdated);
            preparedStatement.setString(4, doneBy);
            preparedStatement.setInt(5, status);
            preparedStatement.setString(6, expenditureId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
        }
        
    }
public boolean deleteExpense(String expId){
    boolean expense=false;
     String delete="DELETE FROM "+ EXPENDITUTE_REG + "  WHERE expenditureid='"+expId+"'";
    try {
       
        preparedStatement = connection.prepareStatement(delete);
         preparedStatement.executeUpdate();
         expense=true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return expense;
}
    public static ArrayList ExpenditureRegistration() {
        ExpenditureRegistrationModel expenditureReg, exName;
        ArrayList<String> listArray = new ArrayList<String>();
        ArrayList<ExpenditureRegistrationModel> eReg = new ArrayList<>();

        String selectSQL = "SELECT expenditureid, expenditurename, description, lastupdated, doneby, status "
                + "  FROM " + EXPENDITUTE_REG + " ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                expenditureReg = new ExpenditureRegistrationModel();
                exName = new ExpenditureRegistrationModel();
                expenditureReg.setExpenditureId(resultSet.getString("expenditureid"));
                expenditureReg.setExpenditureName(resultSet.getString("expenditurename"));
                expenditureReg.setDescription(resultSet.getString("description"));
                listArray.add(resultSet.getString("expenditurename"));
                eReg.add(expenditureReg);
                exName.setData(listArray);

            }
        } catch (Exception e) {
        }
        return eReg;

    }

    public static ArrayList ExpenditureData() {
        ExpenditureRegistrationModel expenditureReg;
        ArrayList<ExpenditureRegistrationModel> eReg = new ArrayList<>();
        String selectSQL = "SELECT expenditureid, expenditurename, description, lastupdated, doneby, status "
                + "  FROM " + EXPENDITUTE_REG + " ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                expenditureReg = new ExpenditureRegistrationModel();
                expenditureReg.setExpenditureId(resultSet.getString("expenditureid"));
                expenditureReg.setExpenditureName(resultSet.getString("expenditurename"));
                expenditureReg.setDescription(resultSet.getString("description"));
                eReg.add(expenditureReg);
            }
        } catch (Exception e) {
        }
        return eReg;

    }

    public ArrayList ExpenditureModelData() {
        ExpenditureRegistrationModel expenditureReg;
        ArrayList<ExpenditureRegistrationModel> eReg = new ArrayList<>();
        String selectSQL = "SELECT expenditureid, expenditurename, description, lastupdated, doneby, status "
                + "  FROM " + EXPENDITUTE_REG + " ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                expenditureReg = new ExpenditureRegistrationModel();
                expenditureReg.setExpenditureId(resultSet.getString("expenditureid"));
                expenditureReg.setExpenditureName(resultSet.getString("expenditurename"));
                expenditureReg.setDescription(resultSet.getString("description"));
                eReg.add(expenditureReg);
            }
        } catch (Exception e) {
        }
        return eReg;

    }

    public static TableView ExpenditureRegData() {

        TableView tv;
        String[] headers = {"ID", "Name", "Description", "ACTION"};
        String[] property = {"expenditureId", "expenditureName", "description", "delete"};
        ArrayList<Object> model = ExpenditureRegistration();

       InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    private String generateExpenseNo() {
        String custNo = null;
        try {
            resultSet = statement.executeQuery("SELECT count(*) FROM " + EXPENDITUTE_REG + " ");
            resultSet.next();
            int no = 1 + resultSet.getInt(1);
            custNo = "EXP00" + no;
        } catch (SQLException e) {
            MessagesUtil.displayMessage("Save Operation Failed", "Failed to save Expense Type details", 3, e);
        } finally {

        }
        return custNo;
    }

    public Object ExpenditureModelDataget() {
        ExpenditureRegistrationModel expenditureReg;
        ArrayList<ExpenditureRegistrationModel> eReg = new ArrayList<>();

        String selectSQL = "SELECT expenditureid, expenditurename, description, lastupdated, doneby, status "
                + "  FROM " + EXPENDITUTE_REG + " ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                expenditureReg = new ExpenditureRegistrationModel();
                expenditureReg.setExpenditureId(resultSet.getString("expenditureid"));
                expenditureReg.setExpenditureName(resultSet.getString("expenditurename"));
                expenditureReg.setDescription(resultSet.getString("description"));
                eReg.add(expenditureReg);
            }
        } catch (Exception e) {
        }
        return eReg;

    }
    
    public static HashMap FetchExpenditures() {
        HashMap<String, String> readAll = new HashMap<>();
        try {
            resultSet = statement.executeQuery("SELECT expenditureid, expenditurename FROM " + EXPENDITUTE_REG + "  ");

            while (resultSet.next()) {
                readAll.put(resultSet.getString("expenditureid"), resultSet.getString("expenditurename"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return readAll;
    }
}
