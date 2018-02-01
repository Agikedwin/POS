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
import static com.solutions.pos.controllers.utilities.PosVariables.CUSTOMERS;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class CustomerModel {

    private String customerId;
    private String customerName;
    private int idNo;
    private String phone;
    private String address;
    private String gender;
    private String email;
    private int status;

    //Mandatory fields
    private String date_reg;
    private String last_update;

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
    private String userId;

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

    public int getIdNo() {
        return idNo;
    }

    public void setIdNo(int idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static ArrayList CustomerInfo() {
        ArrayList<CustomerModel> readAll = new ArrayList<>();
        CustomerModel customerModel;
        try {
            resultSet = statement.executeQuery("SELECT customerId,customername,idNo,phoneNo,"
                    + "address,gender,email,status,regdate,last_update,userId  FROM " + CUSTOMERS + " ");
            while (resultSet.next()) {
                customerModel = new CustomerModel();
                customerModel.setCustomerId(resultSet.getString("customerId"));
                customerModel.setCustomerName(resultSet.getString("customername"));
                customerModel.setIdNo(resultSet.getInt("idNo"));
                customerModel.setPhone(resultSet.getString("phoneNo"));
                customerModel.setAddress(resultSet.getString("address"));
                customerModel.setGender(resultSet.getString("gender"));
                customerModel.setEmail(resultSet.getString("email"));
                customerModel.setStatus(resultSet.getInt("status"));
                customerModel.setDate_reg(resultSet.getString("regdate"));
                customerModel.setLast_update(resultSet.getString("last_update"));
                customerModel.setUserId(resultSet.getString("userId"));
                readAll.add(customerModel);
            }
        } catch (SQLException ex) {
            MessagesUtil.displayMessage("Fetching Data Failed", "Failed to load  Customer Information", 3, ex);
        }
        return readAll;
    }

    public static TableView CustomersData() {

        TableView tv;
        String[] headers = {"ID", "NAME", "ID NO", "PHONE NO", "ADDRESS",  "EMAIL"};
        String[] property = {"customerId", "customerName", "idNo", "phone","address", "email"};
        ArrayList<Object> model = CustomerInfo();

        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
public static void addNewCustomer( String customerName, int idNo, String phone, String address,
     String gender,String email){
     CustomerModel customerModel = new CustomerModel();
                customerModel.setCustomerId(customerModel.generateCustomerNo());
                customerModel.setCustomerName(customerName);
                customerModel.setIdNo(idNo);
                customerModel.setPhone(phone);
                customerModel.setAddress(address);
                customerModel.setGender(gender);
                customerModel.setEmail(email);
                customerModel.setStatus(1);
                customerModel.setDate_reg(SYSTEM_DATE);
                customerModel.setLast_update(SYSTEM_DATE);
                customerModel.setUserId(USER_PROFILE);
                customerModel.addCustomer();
}
    private  void addCustomer() {
        try {
            String query = "INSERT INTO " + CUSTOMERS + " "
                    + "(customerId,customername,idNo,phoneNo,address,gender,email,status,regdate,last_update,userId ) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerId);
            preparedStatement.setString(2, customerName);
            preparedStatement.setInt(3, idNo);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, email);
            preparedStatement.setInt(8,getStatus());
            preparedStatement.setString(9, date_reg);
            preparedStatement.setString(10, last_update);
            preparedStatement.setString(11, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
//     MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved Customer details", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
//                 MessagesUtil.displayMessage("Save Operation Failed", "Failed to save Customer details", 3, e);     
        }
    }

    // Updating records for customers

    public void update(String customerid) {
        try {
            statement.executeUpdate("UPDATE " + CUSTOMERS + " SET customername='" + getCustomerName() + "',"
                    + " idno=" + getIdNo() + ", phoneno= '" + getPhone() + "',"
                    + " address='" + getAddress() + "', "
                    + " gender='" + getGender() + "',"
                    + " email='" + getEmail() + "', status= " + getStatus() + ","
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE customerId='" + customerid + "'");
           MessagesUtil.displayMessage("Update Operation Successful", "Successfully Updated Customer details", 1, null);
        } catch (Exception e) {
        MessagesUtil.displayMessage("Update Operation Failed", "Failed to Update Customer details", 3, e);     
        }
    }

    private String generateCustomerNo() {
        String custNo = null;
        int code  = FunctionGenerateEntityCode.generatedCode(CUSTOMERS);
        if(code<=0){
            MessagesUtil.displayMessage("code generation Failed", "Failed to generate code", 3, null); 
        }
            custNo = "CUS" + code;
        return custNo;
    }

    public String getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(String date_reg) {
        this.date_reg = date_reg;
    }
    
    public static HashMap customerMap(){
        HashMap<String,String> customers = new HashMap(); 
        ArrayList<CustomerModel> allCustomers = CustomerInfo();
        for(CustomerModel cust : allCustomers){
            customers.put(cust.getCustomerId(), cust.getIdNo()+" "+cust.getCustomerName()+" "+cust.getPhone());
        }
        return customers;
    }
}
