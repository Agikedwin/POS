/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import com.solutions.entorno.utilities.SystemFunctions;
import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.INSTITUTION_ETR;
import static com.solutions.entorno.utilities.SystemVariables.INSTITUTION_KRA;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.entorno.utilities.TableViewRenderer;
import com.solutions.entorno.utilities.dates;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import com.solutions.users.controllers.utilities.FunctionGetUserDetails;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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
public class SkuSalesModel {

    private String orderId;
    private int skuId;
    private double quantity;
    private double priceperunit;
    private String mode;
    private double vat;
    private double total;
    private double buyingPrice;

    //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;
    private String skuName;
private String return_items;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getReturn_items() {
        return return_items;
    }

    public void setReturn_items(String return_items) {
        this.return_items = return_items;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
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

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void addSkuSales() {
        try {
            String query = "INSERT INTO " + SKU_SALES + " VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getOrderId());
            preparedStatement.setInt(2, getSkuId());
            preparedStatement.setDouble(3, getQuantity());
            preparedStatement.setDouble(4, getPriceperunit());
            preparedStatement.setDouble(5, vat);
            preparedStatement.setString(6, getMode());
            preparedStatement.setString(7, SYSTEM_DATE);
            preparedStatement.setString(8, SYSTEM_DATE);
            preparedStatement.setString(9, USER_PROFILE);
            preparedStatement.setDouble(10, getBuyingPrice());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Skus sales details could not be saved " + e.getMessage());
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Error Encountered", JOptionPane.ERROR_MESSAGE);
        }
    }

    //check the implementation for pk columns
    public void update(String skuId) {
        try {
            statement.executeUpdate("UPDATE " + SKU_SALES + " SET orderid=" + getOrderId() + ","
                    + " skuid = " + getSkuId() + ", quantity=" + getQuantity() + ","
                    + " priceperunit = " + getPriceperunit() + ", mode='" + getMode() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skuId=" + skuId + "");
            System.out.println("Successfully updated the record");
        } catch (Exception e) {
            System.out.println("Could not update the the record for sku sales " + e.getMessage());
        }
    }

    private static ArrayList readSkusSales(String orderId) {
        ArrayList<SkuSalesModel> readAll = new ArrayList<>();
        SkuSalesModel skusales;
        try {
            resultSet = statement.executeQuery("SELECT ss.*,skr.skuname FROM " + SKU_SALES + " ss, " + SKUS_REGISTRATION + " skr"
                    + " where ss.skuid=skr.skuid and ss.orderid = '" + orderId + "' ");
            while (resultSet.next()) {
                skusales = new SkuSalesModel();
                skusales.setOrderId(resultSet.getString("orderId"));
                skusales.setSkuName(resultSet.getString("skuname"));
                skusales.setSkuId(resultSet.getInt("skuId"));
                skusales.setQuantity(resultSet.getInt("quantity"));
                skusales.setPriceperunit(resultSet.getDouble("priceperunit"));
                skusales.setVat(resultSet.getDouble("vat"));
                skusales.setTotal((double) round(skusales.getPriceperunit() * skusales.getQuantity() * 100) / 100);
                skusales.setReturn_items("Return Item");
                readAll.add(skusales);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkuSalesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readAll;
    }
public static TableView goodsReturnTV(String orderId) {
        TableView tv;
        String[] headers = {"SKU Id", "SKU Name", "Quantity", "Price", "VAT", "Total","#ACTION"};
        String[] property = {"skuId", "skuName", "quantity", "priceperunit", "vat", "total","return_items"};
        ArrayList<Object> model;

        if (orderId == null) {
            model = new ArrayList();
        } else {
            model = readSkusSales(orderId);
        }
        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
    public static TableView shoppingBasketTV(String orderId) {
        TableView tv;
        String[] headers = {"SKU Id", "SKU Name", "Quantity", "Price", "VAT", "Total"};
        String[] property = {"skuId", "skuName", "quantity", "priceperunit", "vat", "total"};
        ArrayList<Object> model;

        if (orderId == null) {
            model = new ArrayList();
        } else {
            model = readSkusSales(orderId);
        }
        TableViewRenderer tbl = new TableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }

    public void printSalesReceipt(ArrayList<SkuSalesModel> model,
            double totalAmount, double vat, int receiptno, double balance, double paid) {
        String path = SystemVariables.REPORT_FOLDER + "\\receipts.txt";
        PrintWriter out = null;
        try {

            out = new PrintWriter(new FileOutputStream(path));

            //pharmacy name
            
             out.println("");
            out.println("  " + SystemVariables.INSTITUTION_NAME);
            out.println("  Tel. " + SystemVariables.INSTITUTION_PHONE);
            out.println("  email:" + SystemVariables.INSTITUTION_EMAIL);
            out.println("  P.O Box " + SystemVariables.INSTITUTION_ADDRESS);
            //receipt type
            out.println("");
            out.println("\t Cash Sale");
            if (INSTITUTION_KRA != null) {
                out.println(" PIN: " + SystemVariables.INSTITUTION_KRA);
            }
            if (INSTITUTION_ETR != null) {
                out.println("ETR NO: " + SystemVariables.INSTITUTION_ETR);
            }
            //date
            out.println("\t " + SYSTEM_DATE + " " + dates.thetime());
            //time
            out.println();
            //headers
            out.print("ITEM ");

            out.print("\t QTY  ");

            out.print("\t PRICE  ");

            out.println(" AMOUNT  ");
            //line
            out.println("-----------------------------------------");

            for (SkuSalesModel skuSale : model) {
                out.print(" " + skuSale.getSkuId());
                out.print("       ");
                out.print("         " + skuSale.getQuantity() + " X " + skuSale.getPriceperunit());
                out.print("");
                out.println();
                out.print(" " + skuSale.getSkuName());
                out.print("       ");
                out.print("" + skuSale.getTotal() +" A");
                out.print("");
                out.println();
            }
            out.println("-----------------------------------");

            out.print(" Total\t\t");

            out.println("" + totalAmount);

            out.print(" Vat Amount\t");

            out.println("" + vat);

              //out.print("\tVAT 16%\t\t");
            //  out.println(""+vattot*0.16);
            out.print(" Paid Amount\t");

            out.println("" + paid);

            out.print(" Balance\t\t");

            out.println("" + balance);

           out.println(" A = VATABLE, Z=Zero Rated,  E=Exempted");

            out.println("\t---------------------------------");

            out.println("\tYou were served by: " + FunctionGetUserDetails.getFullName(USER_PROFILE));

            out.println("\tDesigned by Broadmax System Partners");

            out.println("\tReceipt#" + receiptno);
            out.println("\t" + SYSTEM_DATE + " " + dates.thetime());
            out.println("\n\n\n");
            out.println("-----------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
        SystemFunctions.PrintDocuments(path);
    }

    public static void deleteSavedTransactionSKUs(String orderId) {
        try {
            statement.executeUpdate("DELETE from " + SKU_SALES + " where orderid = '" + orderId + "'");
        } catch (Exception e) {
        }
    }
}
