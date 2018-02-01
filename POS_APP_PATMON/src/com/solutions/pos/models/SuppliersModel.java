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
import com.solutions.entorno.utilities.TableViewRenderer;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import static com.solutions.pos.controllers.utilities.PosVariables.SUPPLIERS;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TableView;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class SuppliersModel {

    private String supplierId;
    private String supplirName;
    private int idno;

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }
    private String phoneNo;
    private String address;
    private String gender;
    private String Company;
    private String email;
    private String lastUpdated;
    private String regdate;
    private String doneBy;
    private int status;

    private SuppliersModel() {

    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplirName() {
        return supplirName;
    }

    public void setSupplirName(String supplirName) {
        this.supplirName = supplirName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void addNewSupplier(String supplierName, int idNo, String phone, String address,
            String gender, String email, String company) {
        SuppliersModel Suppliers = new SuppliersModel();
        Suppliers.setSupplierId(Suppliers.generateSupplierNo());
        Suppliers.setSupplirName(supplierName);
        Suppliers.setIdno(idNo);
        Suppliers.setPhoneNo(phone);
        Suppliers.setCompany(company);
        Suppliers.setAddress(address);
        Suppliers.setEmail(email);
        Suppliers.setRegdate(SYSTEM_DATE);
        Suppliers.setLastUpdated(SYSTEM_DATE);
        Suppliers.setDoneBy(USER_PROFILE);
        Suppliers.setStatus(1);
        Suppliers.addSupplier();
    }

    private void addSupplier() {

        String insertSQL = "INSERT INTO " + SUPPLIERS + "("
                + "            supplierid, supliername, idno, phoneno, gender, company, email,  lastupdated,regdate, doneby, status,address)"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, supplierId);
            preparedStatement.setString(2, supplirName);
            preparedStatement.setInt(3, idno);
            preparedStatement.setString(4, phoneNo);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, Company);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, regdate);
            preparedStatement.setString(9, lastUpdated);
            preparedStatement.setString(10, doneBy);
            preparedStatement.setInt(11, status);
            preparedStatement.setString(12, address);
            preparedStatement.executeUpdate();
//            MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved Supplier details", 1, null);
        } catch (Exception e) {
//            MessagesUtil.displayMessage("Save Operation Failed", "Failed to save Supplier details", 3, e);
        }
    }

    private void updateSupplier() {

        String updateSQL = "UPDATE " + SUPPLIERS + " "
                + "   SET supplierid=?, supliername=?, idno=?, phoneno=?, gender=?, company=?, email=?, lastupdated=?, doneby=?, status=?,address = ?"
                + " WHERE supplierid=?";
        try {
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, supplirName);
            preparedStatement.setInt(2, idno);
            preparedStatement.setString(3, phoneNo);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, Company);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, lastUpdated);
            preparedStatement.setString(8, doneBy);
            preparedStatement.setInt(9, status);
            preparedStatement.setString(10, address);
            preparedStatement.setString(11, supplierId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed To Update", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static ArrayList getSuppliers() {
        SuppliersModel Suppliers;

        ArrayList<SuppliersModel> supp = new ArrayList<>();
        String selectSQL = "SELECT supplierid, supliername, idno, phoneno, gender, company,address, email, "
                + "       lastupdated, doneby, status,regdate"
                + "  FROM " + SUPPLIERS + " ";
        try {
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                Suppliers = new SuppliersModel();
                Suppliers.setSupplierId(resultSet.getString("supplierid"));
                Suppliers.setSupplirName(resultSet.getString("supliername"));
                Suppliers.setIdno(resultSet.getInt("idno"));
                Suppliers.setPhoneNo(resultSet.getString("phoneno"));
                Suppliers.setCompany(resultSet.getString("company"));
                Suppliers.setAddress(resultSet.getString("address"));
                Suppliers.setEmail(resultSet.getString("email"));
                Suppliers.setRegdate(resultSet.getString("regdate"));
                Suppliers.setLastUpdated(resultSet.getString("lastupdated"));
                Suppliers.setDoneBy(resultSet.getString("doneby"));
                Suppliers.setStatus(resultSet.getInt("status"));
                supp.add(Suppliers);
            }
        } catch (Exception e) {
            MessagesUtil.displayMessage("Fetching Data Failed", "Failed to load  Supplier Information", 3, e);
        }
        return supp;

    }

    public static TableView suppliersData() {

        TableView tv;
        String[] headers = {"ID", "NAME", "ID NO", "PHONE NO", "ADDRESS", "EMAIL", "Company"};
        String[] property = {"supplierId", "supplirName", "idno", "phoneNo", "address", "email", "Company"};
        ArrayList<Object> model = getSuppliers();

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    private String generateSupplierNo() {

        String custNo = null;
        int code = FunctionGenerateEntityCode.generatedCode(SUPPLIERS);
        if (code <= 0) {
            MessagesUtil.displayMessage("code generation Failed", "Failed to generate code", 3, null);
        }
        custNo = "CUS" + code;
        return custNo;
    }

    public static HashMap supplierMap() {
        HashMap<String, String> supplier = new HashMap();
        ArrayList<SuppliersModel> allSupplier = getSuppliers();
        for (SuppliersModel cust : allSupplier) {
            supplier.put(cust.getSupplierId(),   cust.getSupplirName()  );
        }
        return supplier;
    }
}
