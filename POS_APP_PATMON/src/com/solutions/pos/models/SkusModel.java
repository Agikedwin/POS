/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import com.solutions.entorno.utilities.ImageResizerClass;
import com.solutions.entorno.utilities.MessagesUtil;
import static com.solutions.entorno.utilities.SystemVariables.CONFIG_FOLDER;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import static com.solutions.entorno.utilities.SystemVariables.connection;
import static com.solutions.entorno.utilities.SystemVariables.preparedStatement;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_CATEGORIES_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.VAT_VALUES;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * @author AND
 */
public class SkusModel {

    private int skuCategoryId;
    private String skuCatName;
    private int skuId;
    private String skuname;
    private String description;
    private String sku_photo;
    private double retailPrice;
    private double wholeSalePrice;
    private int vat;
    private String vatable;
    private int reorderLevel;

    //Mandatory fields
    private String date_reg;
    private String last_update;
    private String userId;
    private String edit = "Edit";
      private String view = "View";
     private String sellItem = "Sell";
    
    String input;
    InputStream skuImageStream;

    public String getInput() {
        return input;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public InputStream getSkuImageStream() {
        return skuImageStream;
    }

    public void setSkuImageStream(InputStream skuImageStream) {
        this.skuImageStream = skuImageStream;
    }

    public int getSkuCategoryId() {
        return skuCategoryId;
    }

    public void setSkuCategoryId(int skuCategoryId) {
        this.skuCategoryId = skuCategoryId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkuCatName() {
        return skuCatName;
    }

    public void setSkuCatName(String skuCatName) {
        this.skuCatName = skuCatName;
    }

    public String getSku_photo() {
        return sku_photo;
    }

    public void setSku_photo(String sku_photo) {
        this.sku_photo = sku_photo;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getVatable() {
        return vatable;
    }

    public void setVatable(String vatable) {
        this.vatable = vatable;
    }

    public double getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(double wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
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

    public String getSellItem() {
        return sellItem;
    }

    public void setSellItem(String sellItem) {
        this.sellItem = sellItem;
    }

    public static boolean addNewSku(int skucategoryId, String skuname, String description, double retailprice, double wholesaleprice,
            int vat, int reorderlevel) {
        SkusModel product = new SkusModel();
        product.setSkuId(FunctionGenerateEntityCode.generatedCode(SKUS_REGISTRATION));
        product.setSkuCategoryId(skucategoryId);
        product.setSkuname(skuname);
        product.setDescription(description);
        product.setRetailPrice(retailprice);
        product.setWholeSalePrice(wholesaleprice);
        product.setVat(vat);
        product.setReorderLevel(reorderlevel);
        product.setLast_update(SYSTEM_DATE);
        product.setUserId(USER_PROFILE);
        product.setDate_reg(SYSTEM_DATE);
        try {
            product.addSku();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SkusModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void addSku() throws SQLException {
        try {
             String query = "INSERT INTO " + SKUS_REGISTRATION + " (skuid,skucategoryId,skuname,"
                + "description,retailprice,wholesaleprice,vat,reorderlevel,regdate,last_update,userId) VALUES (?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, getSkuId());
        preparedStatement.setInt(2, getSkuCategoryId());
        preparedStatement.setString(3, getSkuname());
        preparedStatement.setString(4, getDescription());
        preparedStatement.setDouble(5, getRetailPrice());
        preparedStatement.setDouble(6, getWholeSalePrice());
        preparedStatement.setInt(7, getVat());
        preparedStatement.setInt(8, getReorderLevel());
        preparedStatement.setString(9, date_reg);
        preparedStatement.setString(10, last_update);
        preparedStatement.setString(11, userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
 }

    public void update() {
        try {
            statement.executeUpdate("UPDATE " + SKUS_REGISTRATION + " SET skucategoryId=" + getSkuCategoryId() + ","
                    + " skuname = '" + getSkuname() + "', description='" + getDescription() + "',"
                    + " sku_photo = '" + getSku_photo() + "', retailprice='" + getRetailPrice() + "',"
                    + " wholesaleprice = '" + getWholeSalePrice() + "', vat='" + getVat() + "',"
                    + " reorderlevel='" + getReorderLevel() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skuId=" + skuId + "");
           
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void updatePrice() {
        try {
            statement.executeUpdate("UPDATE " + SKUS_REGISTRATION + " SET "
                    + " retailprice='" + getRetailPrice() + "',"
                    + " wholesaleprice = '" + getWholeSalePrice() + "',"
                    + " reorderlevel='" + getReorderLevel() + "',"
                    + " last_update = '" + SYSTEM_DATE + "', userid = '" + USER_PROFILE + "'"
                    + " WHERE skuId=" + skuId + "");

            
        } catch (Exception e) {
           
        }
    }

    private static ArrayList readSkus() {
        ArrayList<SkusModel> readAll = new ArrayList<>();
        SkusModel product;
        try {
            resultSet = statement.executeQuery("SELECT sku_r.*,sku_c.skucategoryname FROM " + SKUS_REGISTRATION + " sku_r,"
                    + " " + SKUS_CATEGORIES_REGISTRATION + " sku_c  where sku_r.skucategoryId= sku_c.skucategoryId ORDER BY sku_r.skuid DESC ");
            while (resultSet.next()) {
                product = new SkusModel();
                product.setSkuId(resultSet.getInt(1));
                product.setSkuCategoryId(resultSet.getInt(2));
                product.setSkuname(resultSet.getString(3));
                product.setDescription(resultSet.getString(4));
                product.setSku_photo(resultSet.getString(5));
                product.setRetailPrice(resultSet.getFloat(6));
                product.setWholeSalePrice(resultSet.getFloat(7));
                product.setVatable(VAT_VALUES.get(resultSet.getInt(8)));
                product.setReorderLevel(resultSet.getInt(9));
                product.setLast_update(resultSet.getString(10));
                product.setUserId(resultSet.getString(11));
                product.setSkuCatName(resultSet.getString("skucategoryname"));
                readAll.add(product);
            }
        } catch (SQLException ex) {
            
        }
        return readAll;
    }

    public static ArrayList readSkusSearch() {
        ArrayList<SkusModel> readAll = new ArrayList<>();
        SkusModel product;
        try {
            resultSet = statement.executeQuery("SELECT sku_r.*,sku_c.skucategoryname FROM " + SKUS_REGISTRATION + " sku_r,"
                    + " " + SKUS_CATEGORIES_REGISTRATION + " sku_c  where sku_r.skucategoryId= sku_c.skucategoryId ");
            while (resultSet.next()) {
                product = new SkusModel();
                product.setSkuId(resultSet.getInt(1));
                product.setSkuCategoryId(resultSet.getInt(2));
                product.setSkuname(resultSet.getString(3));
                product.setDescription(resultSet.getString(4));
                product.setSku_photo(resultSet.getString(5));
                product.setRetailPrice(resultSet.getFloat(6));
                product.setWholeSalePrice(resultSet.getFloat(7));
                product.setVatable(VAT_VALUES.get(resultSet.getInt(8)));
                product.setReorderLevel(resultSet.getInt(9));
                product.setLast_update(resultSet.getString(10));
                product.setUserId(resultSet.getString(11));
                product.setSkuCatName(resultSet.getString("skucategoryname"));
                // product.setEdit("EDIT");
                readAll.add(product);
            }
        } catch (SQLException ex) {
            MessagesUtil.displayMessage("Fetching Data Failed", "Failed to load  SKUs", 3, ex);
        }
        return readAll;
    }

    public static TableView SkusData() {

        TableView tv;
        //"Category Id","Category Name", "Wholesale Price",
        //"skuCategoryId","skuCatName",,"reorderLevel","wholeSalePrice","Re-order Level"
        String[] headers = {"SKU Id", "SKU Name", "Retail Price", "Action"};
        String[] property = {"skuId", "skuname", "retailPrice", "sellItem"};
        ArrayList<Object> model = readSkus();

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        // tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tv;
    }

    public static TableView editSkusData() {

        TableView tv;
        //"Category Id","Category Name", "Wholesale Price",
        //"skuCategoryId","skuCatName",,"reorderLevel","wholeSalePrice","Re-order Level"
        String[] headers = {"SKU Id", "SKU Name", "Retail Price", "#ACTION"};
        String[] property = {"skuId", "skuname", "retailPrice", "edit"};
        ArrayList<Object> model = readSkus();

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        // tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tv;
    }
    
     public static TableView SkusStockActions() {

        TableView tv;
        //"Category Id","Category Name", "Wholesale Price",
        //"skuCategoryId","skuCatName",,"reorderLevel","wholeSalePrice","Re-order Level"
        String[] headers = {"SKU Id", "SKU Name",  "#ACTION"};
        String[] property = {"skuId", "skuname",  "view"};
        ArrayList<Object> model = readSkus();

        InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);
        tv = tbl.getTable();
        // tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tv;
    }

    public static HashMap FetchSkus() {
        HashMap<Integer, String> readAll = new HashMap<>();
        try {
            resultSet = statement.executeQuery("SELECT skuid,skuname from " + SKUS_REGISTRATION + "  ");
            readAll.put(0, "ALL SKUS");
            while (resultSet.next()) {
                readAll.put(resultSet.getInt("skuid"), resultSet.getString("skuname"));
            }
        } catch (SQLException ex) {
            MessagesUtil.displayMessage("Records Retrieval Failed", "Failed to fetch SKU records", 3, ex);
        }
        return readAll;
    }

    private boolean saveSkuImage() {
        boolean success = false;
        try {
            if (input != null) {
                String output = CONFIG_FOLDER + "/temp_image.jpg";
                new ImageResizerClass().getInstance(input, output, 300, 300);
                File ufile = new File(output);
                FileInputStream fs = new FileInputStream(ufile);
                preparedStatement = connection.prepareStatement("UPDATE " + SKUS_REGISTRATION + " SET sku_photo_col=? WHERE skuid = " + skuId + "");
                preparedStatement.setBinaryStream(1, fs, ufile.length());
                preparedStatement.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return success;
    }
}
