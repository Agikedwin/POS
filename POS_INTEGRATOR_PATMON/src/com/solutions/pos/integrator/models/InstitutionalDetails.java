/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.integrator.models;

import com.solutions.entorno.utilities.MessagesUtil;
import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.integrator.controllers.utilities.FunctionGetInstitutionDetails;
import static com.solutions.pos.integrator.controllers.utilities.InstitutionVariables.INSTITUTION;

/**
 *
 * @author shaddie
 */
public class InstitutionalDetails {

    private String name;
    private String town;
    private String postalAddress;
    private String slogan;
    private String phoneNo;
    private String email;
    private String karPin;
    private String etrNumber;
    private String doneBy;
    private String lastUpdated;
    private String expkey, exptym, status;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKarPin() {
        return karPin;
    }

    public void setKarPin(String karPin) {
        this.karPin = karPin;
    }

    public String getEtrNumber() {
        return etrNumber;
    }

    public void setEtrNumber(String etrNumber) {
        this.etrNumber = etrNumber;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getExpkey() {
        return expkey;
    }

    public void setExpkey(String expkey) {
        this.expkey = expkey;
    }

    public String getExptym() {
        return exptym;
    }

    public void setExptym(String exptym) {
        this.exptym = exptym;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void insertInstitutionDetails() {

        String insertSQL = "INSERT INTO " + INSTITUTION + " "
                + "(name,town,slogan,postal_address,emails ,telnos,pin,etr,"
                + "expkey,exptym,status,regdate,last_update,userid)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?,?)";

        try {

            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, getName());
            preparedStatement.setString(2, getTown());
            preparedStatement.setString(3, getSlogan());
            preparedStatement.setString(4, getPostalAddress());
            preparedStatement.setString(5, getEmail());
            preparedStatement.setString(6, getPhoneNo());
            preparedStatement.setString(7, getKarPin());
            preparedStatement.setString(8, getEtrNumber());
            preparedStatement.setString(9, getExpkey());
            preparedStatement.setString(10, getExptym());
            preparedStatement.setString(11, getStatus());
            preparedStatement.setString(12, SYSTEM_DATE);
            preparedStatement.setString(13, SYSTEM_DATE);
            preparedStatement.setString(14, USER_PROFILE);
            preparedStatement.executeUpdate();
            MessagesUtil.displayMessage("Save Operation Successful", "Successfully saved Business Details", 1, null);
        } catch (Exception e) {
            MessagesUtil.displayMessage("Save Operation Failed", "Failed to  save Business Details", 3, e);
        }
    }

    public void updateInstitutionDetails() {

        String updateSQL = "UPDATE " + INSTITUTION + " SET name=?,town=?,slogan=?,"
                + "postal_address=?,emails=?,telnos=?,pin=?,etr=?,last_update=?,userid=?";
        try {

            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, getName());
            preparedStatement.setString(2, getTown());
            preparedStatement.setString(3, getSlogan());
            preparedStatement.setString(4, getPostalAddress());
            preparedStatement.setString(5, getEmail());
            preparedStatement.setString(6, getPhoneNo());
            preparedStatement.setString(7, getKarPin());
            preparedStatement.setString(8, getEtrNumber());
            preparedStatement.setString(9, SYSTEM_DATE);
            preparedStatement.setString(10, USER_PROFILE);
            preparedStatement.executeUpdate();
            SystemVariables.INSTITUTION_NAME = FunctionGetInstitutionDetails.getInstitutionName();
            SystemVariables.INSTITUTION_ADDRESS = FunctionGetInstitutionDetails.getInstitutionAddress();
            SystemVariables.INSTITUTION_PHONE = FunctionGetInstitutionDetails.getInstitutionTelephone();
            SystemVariables.INSTITUTION_EMAIL = FunctionGetInstitutionDetails.getInstitutionEmail();
            SystemVariables.INSTITUTION_MOTTO = FunctionGetInstitutionDetails.getInstitutionMotto();
            SystemVariables.INSTITUTION_LOGO = FunctionGetInstitutionDetails.getInstitutionLogo();
            MessagesUtil.displayMessage("Update Operation Successful", "Successfully Updated Business Details", 1, null);
        } catch (Exception e) {
            MessagesUtil.displayMessage("Update Operation Failed", "Failed to  Update Business Details", 3, e);
        }
    }

    public void updateLogo() {

    }

    public InstitutionalDetails readInstitution() {
        InstitutionalDetails institution = new InstitutionalDetails();
        try {
            resultSet = statement.executeQuery("SELECT name,town,slogan,postal_address,emails ,telnos,pin,etr FROM " + INSTITUTION + "");
            if (resultSet.next()) {
                institution.setName(resultSet.getString("name"));
                institution.setTown(resultSet.getString("town"));
                institution.setPostalAddress(resultSet.getString("postal_address"));
                institution.setPhoneNo(resultSet.getString("telnos"));
                institution.setSlogan(resultSet.getString("slogan"));
                institution.setEmail(resultSet.getString("emails"));
                institution.setKarPin(resultSet.getString("pin"));
                institution.setEtrNumber(resultSet.getString("etr"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return institution;
    }
}
