/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.MessagesUtil;
import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import com.solutions.entorno.utilities.dialogs.NotificationType;
import static com.solutions.pos.controllers.utilities.PosVariables.VAT_VALUES;
import com.solutions.pos.models.CustomerModel;
import com.solutions.pos.models.ExpenditureRegistrationModel;
import com.solutions.pos.models.SkuCategoriesModel;
import com.solutions.pos.models.SkusModel;
import com.solutions.pos.models.SuppliersModel;
import com.solutions.users.controllers.utilities.ProfileVariables;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class SetupHomeController implements Initializable {

    //SKU Categories CONTROLS
    @FXML
    private BorderPane borderpaneCategoriesWrapper;
    @FXML
    private TextField categoriesSearch;
    @FXML
    private TextField skuName;
    @FXML
    private Button saveCategory;
    @FXML
    private TextArea categoryDescription;

    //SKU Registration CONTROLS
    @FXML
    private BorderPane borderpaneskuRegWrapper;
    @FXML
    private Button saveSkuRegistration;
    @FXML
    private TextField retailPrice;
    @FXML
    private TextField wholesalePrice;
    @FXML
    private ComboBox vat;
    @FXML
    private TextField reorderLevel;
    @FXML
    private TextField skuRegName;
    @FXML
    private TextArea skuRegDescription;
    @FXML
    private Tab skuRegTab;
    @FXML
    private TableView<SkusModel> skuTV;
    @FXML
    private BorderPane skuRegBorder;
    @FXML
    private ComboBox cbSkuCategories;

    //Customer Registration CONTROLS
    @FXML
    private BorderPane borderpaneCustomerWrapper;
    @FXML
    private TextField customerSearch;
    @FXML
    private Button saveCustomer;
    @FXML
    private TextField customerIdno;
    @FXML
    private TextField customerPhoneNo;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerEmail;
    @FXML
    private TextField customerName;
    @FXML
    private Tab CustomerRegTab;

    //Supplier Registration CONTROLS
    @FXML
    private BorderPane borderpaneSupplierWrapper;
    @FXML
    private TextField supplierSearch;
    @FXML
    private Button saveSupplier;
    @FXML
    private TextField supplierIDNO;
    @FXML
    private TextField supplierPhoneNO;
    @FXML
    private TextField supplierAddress;
    @FXML
    private TextField supplierEmail;
    @FXML
    private TextField supplierName;
    @FXML
    private TextField supplierCompany;
    @FXML
    private Tab supplierRegTab;

    //Expense Registration CONTROLS
    @FXML
    private BorderPane borderpaneExpesnseWrapper;
    @FXML
    private TextField expenseSearch;
    @FXML
    private TextField expenseName;
    @FXML
    private Button saveExpense;
    @FXML
    private TextArea expenseDescription;
    @FXML
    private Tab expenseRegTab;
    @FXML
    private TabPane setupTab;
    @FXML
    private Tab skuCategoriestab;
    private ObservableList<SkuCategoriesModel> masterData;
    private ObservableList<SkusModel> skumasterData;
    private ObservableList<CustomerModel> customermasterData;
    private ObservableList<SuppliersModel> suppliermasterData;
    private ObservableList<ExpenditureRegistrationModel> expemasterData;
    @FXML
    private TableView<SkuCategoriesModel> categoriesTV;
    @FXML
    private TableView customerTV;
    @FXML
    private TableView supplierTV;
    @FXML
    private TableView <ExpenditureRegistrationModel>expenseTV;

    OnActionListener onActionListener = new OnActionListener();
    MouseActionListener mouseActionListener = new MouseActionListener();
    OnSelectionListener onSelectionListener = new OnSelectionListener();
    // 
    SkusModel skumodel = null;
    SkuCategoriesModel categoryModel = null;
    ExpenditureRegistrationModel expenseReg=null;

    @FXML
    private TextField searchSkus;

    public SetupHomeController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        privileges();
        skuCategoriestab.setOnSelectionChanged(onSelectionListener);
        skuRegTab.setOnSelectionChanged(onSelectionListener);
        CustomerRegTab.setOnSelectionChanged(onSelectionListener);
        supplierRegTab.setOnSelectionChanged(onSelectionListener);
        expenseRegTab.setOnSelectionChanged(onSelectionListener);
        
        expenseTV.setOnMouseClicked(mouseActionListener);
        loadTabs();
    }

    private void loadTabs() {
        Tab selectedTab = setupTab.getSelectionModel().getSelectedItem();
        if(selectedTab != null){
        switch (selectedTab.getText()) {
            case "SKU Categories ":
                loadCategories();
                break;
            case "SKU Registration":
                loadSKU();
                break;
            case "Customer Registration":
                loadCustomers();
                break;
            case "Supplier Registration":
                loadSuppliers();
                break;
            case "Expense Registration":
                loadExpenses();
                break;
        }
        }
    }

    private void loadCategories() {
        this.masterData = FXCollections.observableArrayList(SkuCategoriesModel.readCategories());
        categoriesTV = SkuCategoriesModel.CategoriesData();
        categoriesTV.getSelectionModel().setCellSelectionEnabled(true);
        categoriesTV.setOnMouseClicked(mouseActionListener);
        borderpaneCategoriesWrapper.setCenter(categoriesTV);
        //CATEGORIES SEARCH
        FilteredList<SkuCategoriesModel> filteredCategoriesData = new FilteredList<>(masterData, p -> true);
        categoriesSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCategoriesData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSkuCategoryName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SkuCategoriesModel> sortedCatagoriesData = new SortedList<>(filteredCategoriesData);
        sortedCatagoriesData.comparatorProperty().bind(categoriesTV.comparatorProperty());
        categoriesTV.setItems(sortedCatagoriesData);
        //END CATEGORIES SEARCH
        saveCategory.setOnAction(onActionListener);
    }

    private void loadSKU() {
        ObservableList<String> skuCat = FXCollections.observableArrayList(SkuCategoriesModel.FetchSkuCategories().values());
        cbSkuCategories.setItems(skuCat);
        cbSkuCategories.getSelectionModel().select(0);

        ObservableList<String> vatable = FXCollections.observableArrayList(VAT_VALUES.values());
        vat.setItems(vatable);
        vat.getSelectionModel().select(0);

        skuTV = SkusModel.editSkusData();
        skuTV.setOnMouseClicked(mouseActionListener);
        skuTV.getSelectionModel().setCellSelectionEnabled(true);
        borderpaneskuRegWrapper.setCenter(skuTV);
        this.skumasterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        //SALES SERACH         
        FilteredList<SkusModel> filteredSkusData = new FilteredList<>(skumasterData, p -> true);
        searchSkus.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSkusData.setPredicate(searchSkuModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchSkuModel.getSkuname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchSkuModel.getSkuCatName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchSkuModel.getEdit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Integer.toString(searchSkuModel.getSkuId()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SkusModel> sortedSkusData = new SortedList<>(filteredSkusData);
        sortedSkusData.comparatorProperty().bind(skuTV.comparatorProperty());
        skuTV.setItems(sortedSkusData);
        //end search

        //  borderpaneskuRegWrapper.setCenter(SkusModel.SkusData());
        saveSkuRegistration.setOnAction(onActionListener);
    }

    private void loadCustomers() {
        this.suppliermasterData = FXCollections.observableArrayList(SuppliersModel.getSuppliers());
        customerTV = CustomerModel.CustomersData();
        borderpaneCustomerWrapper.setCenter(customerTV);
        this.customermasterData = FXCollections.observableArrayList(CustomerModel.CustomerInfo());
        //CUSTOMER SEARCH
        FilteredList<CustomerModel> filteredCustomerData = new FilteredList<>(customermasterData, p -> true);
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomerData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getCustomerName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CustomerModel> sortedCustomerData = new SortedList<>(filteredCustomerData);
        sortedCustomerData.comparatorProperty().bind(customerTV.comparatorProperty());
        customerTV.setItems(sortedCustomerData);
        //END CUSTOMER SEARCH
        saveCustomer.setOnAction(onActionListener);
        saveSupplier.setOnAction(onActionListener);
    }

    private void loadSuppliers() {
        supplierTV = SuppliersModel.suppliersData();
        borderpaneSupplierWrapper.setCenter(supplierTV);
        //SUPPLIER SEARCH
        FilteredList<SuppliersModel> filteredSupplierData = new FilteredList<>(suppliermasterData, p -> true);
        supplierSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSupplierData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSupplirName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SuppliersModel> sortedSupplierData = new SortedList<>(filteredSupplierData);
        sortedSupplierData.comparatorProperty().bind(supplierTV.comparatorProperty());
        supplierTV.setItems(sortedSupplierData);
        //END SUPPLIER SEARCH
    }

    private void loadExpenses() {
        this.expemasterData = FXCollections.observableArrayList(ExpenditureRegistrationModel.ExpenditureData());
        expenseTV = ExpenditureRegistrationModel.ExpenditureRegData();
        borderpaneExpesnseWrapper.setCenter(expenseTV);
        expenseTV.setOnMouseClicked(mouseActionListener);
        //TABLE SERACH
        FilteredList<ExpenditureRegistrationModel> filteredData = new FilteredList<>(expemasterData, p -> true);
        expenseSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getExpenditureName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchModel.getExpenditureId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ExpenditureRegistrationModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(expenseTV.comparatorProperty());
        expenseTV.setItems(sortedData);
        //END TABLE SERACH        
        saveExpense.setOnAction(onActionListener);
    }

    class OnActionListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if (saveExpense == e.getSource()) {
                try {
                    String expenditureName = expenseName.getText();
                    String description = expenseDescription.getText();
                    ExpenditureRegistrationModel.addNewExpenseType(expenditureName, description);
                     DISPLAY_MESSAGE.showInfoDialog(saveCustomer.getScene().getWindow(), "Successfully Saved Expense Type details", NotificationType.INFORMATION);
                    
                    borderpaneExpesnseWrapper.setCenter(ExpenditureRegistrationModel.ExpenditureRegData());
                    expenseName.setText(null);
                    expenseDescription.setText(null);
                } catch (Exception ex) {
                    
                    DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "Failed to save Expense Type details", NotificationType.ERROR);
              
                }
            }
            if (saveSupplier == e.getSource()) {
                try {
                    String supplierNames = supplierName.getText();
                    int idNo = Integer.parseInt(supplierIDNO.getText());
                    String phone = supplierPhoneNO.getText();
                    String address = supplierAddress.getText();
                    String gender = null;
                    String email = supplierEmail.getText();
                    String companys = supplierCompany.getText();
                    SuppliersModel.addNewSupplier(supplierNames, idNo, phone, address, gender, email, companys);
                    DISPLAY_MESSAGE.showInfoDialog(saveCustomer.getScene().getWindow(), "Successfully Saved Supplier details", NotificationType.INFORMATION);
                    supplierName.setText(null);
                    supplierIDNO.setText(null);
                    supplierPhoneNO.setText(null);
                    supplierAddress.setText(null);
                    supplierEmail.setText(null);
                    supplierCompany.setText(null);
                    borderpaneSupplierWrapper.setCenter(SuppliersModel.suppliersData());
                } catch (Exception ex) {
                  
                     DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "Failed to save Supplier details", NotificationType.ERROR);
                }
            }
            if (saveCustomer == e.getSource()) {
                try {
                    String customerNames = customerName.getText();
                    int idNo = Integer.parseInt(customerIdno.getText());
                    String phone = customerPhoneNo.getText();
                    String address = customerAddress.getText();
                    String gender = null;
                    String email = customerEmail.getText();
                    CustomerModel.addNewCustomer(customerNames, idNo, phone, address, gender, email);
                    DISPLAY_MESSAGE.showInfoDialog(saveCustomer.getScene().getWindow(), "Successfully Saved Customer details", NotificationType.INFORMATION);
                    customerName.setText(null);
                    customerIdno.setText(null);
                    customerPhoneNo.setText(null);
                    customerAddress.setText(null);
                    customerEmail.setText(null);
                    borderpaneCustomerWrapper.setCenter(CustomerModel.CustomersData());

                } catch (Exception ex) {
                   
                    DISPLAY_MESSAGE.showInfoDialog(saveCustomer.getScene().getWindow(), "Failed to save Customer details", NotificationType.ERROR);
                }
            }
            if (saveCategory == e.getSource()) {
                try {
                    String skuCatName = skuName.getText();
                    String skuCatDesc = categoryDescription.getText();
                    if (skuCatName.equals("")) {
                        MessagesUtil.displayMessage("Rquired Field!", "Field ", 1, null);
                        DISPLAY_MESSAGE.showInfoDialog(saveCategory.getScene().getWindow(), "Please Enter Category Name", NotificationType.ERROR);
                    } else {
                        if (categoryModel == null) {
                            SkuCategoriesModel.addNewSkuCategory(skuCatName, skuCatDesc);
                            DISPLAY_MESSAGE.showInfoDialog(saveCategory.getScene().getWindow(), "Successfully Svaed Category", NotificationType.INFORMATION);
                        } else {
                            categoryModel.setSkuCategoryName(skuCatName);
                            categoryModel.setSkuCategoryDescription(skuCatDesc);
                            if (categoryModel.update()) {
                                DISPLAY_MESSAGE.showInfoDialog(saveCategory.getScene().getWindow(), "Successfully updated Category", NotificationType.INFORMATION);
                            } else {
                                DISPLAY_MESSAGE.showInfoDialog(saveCategory.getScene().getWindow(), "Failed to Update Category", NotificationType.ERROR);
                            }
                        }
                        // categoriesTV.setItems(masterData);
                        categoriesTV = SkuCategoriesModel.CategoriesData();
                        categoriesTV.setOnMouseClicked(mouseActionListener);
                        categoriesTV.getSelectionModel().setCellSelectionEnabled(true);
                        borderpaneCategoriesWrapper.setCenter(categoriesTV);
                        categoryModel = null;
                        skuName.setText(null);
                        categoryDescription.setText(null);
                    }
                } catch (Exception ex) {
                   
                    DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "Failed to save SKU category details", NotificationType.ERROR);
                }
            }
            if (saveSkuRegistration == e.getSource()) {

                try {
                    int skucategoryId = 0;
                    String skuCatName = cbSkuCategories.getSelectionModel().getSelectedItem().toString();
                    HashMap<Integer, String> readcats = SkuCategoriesModel.FetchSkuCategories();
                    Set<Integer> catIds = readcats.keySet();

                    for (int keys : catIds) {
                        if (readcats.get(keys).equals(skuCatName)) {
                            skucategoryId = keys;
                            break;
                        }
                    }
                    if (skucategoryId > 0) {
                        String vatString = vat.getSelectionModel().getSelectedItem().toString();
                        readcats = VAT_VALUES;
                        catIds = readcats.keySet();
                        int vatValue = 0;
                        for (int keys : catIds) {
                            if (readcats.get(keys).equals(vatString)) {
                                vatValue = keys;
                                break;
                            }
                        }
                        String skuname = skuRegName.getText();
                        String description = skuRegDescription.getText();
                        double retailprice = Double.parseDouble(retailPrice.getText());
                        double wholesaleprice = Double.parseDouble(wholesalePrice.getText());

                        int reorderlevel = Integer.parseInt(reorderLevel.getText());
                        if (skumodel == null) {
                            SkusModel.addNewSku(skucategoryId, skuname, description, retailprice, wholesaleprice, vatValue, reorderlevel);
                         DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "Successfully Saved SKU", NotificationType.INFORMATION);
                        } else {
                            skumodel.setSkuCategoryId(skucategoryId);
                            skumodel.setSkuname(skuname);
                            skumodel.setDescription(description);
                            skumodel.setRetailPrice(retailprice);
                            skumodel.setWholeSalePrice(wholesaleprice);
                            skumodel.setVat(vatValue);
                            skumodel.setReorderLevel(reorderlevel);
                            skumodel.update();
                        }
                        skumodel = null;
                        skuTV = SkusModel.editSkusData();
                        skuTV.getSelectionModel().setCellSelectionEnabled(true);
                        borderpaneskuRegWrapper.setCenter(skuTV);
                        skuTV.setOnMouseClicked(mouseActionListener);
                    } else {
                        
                         DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "please select Sku category", NotificationType.ERROR);
                    }
                } catch (Exception ex) {
                     DISPLAY_MESSAGE.showInfoDialog(saveSkuRegistration.getScene().getWindow(), "Failed to save SKU category details", NotificationType.ERROR);
                }
            }
        }

    }

    class MouseActionListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (skuTV == e.getSource()) {
                if (skuTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 3) {
                    skumodel = skuTV.getSelectionModel().getSelectedItem();
                    cbSkuCategories.getSelectionModel().select(skumodel.getSkuCatName());
                    skuRegName.setText(skumodel.getSkuname());
                    skuRegDescription.setText(skumodel.getDescription());
                    retailPrice.setText("" + skumodel.getRetailPrice());
                    wholesalePrice.setText("" + skumodel.getWholeSalePrice());
                    reorderLevel.setText("" + skumodel.getReorderLevel());
                    vat.getSelectionModel().select(skumodel.getVatable());
                }
            }
               if (categoriesTV == e.getSource()) {
                if (categoriesTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 3) {
                    categoryModel = categoriesTV.getSelectionModel().getSelectedItem();
                    skuName.setText(categoryModel.getSkuCategoryName());
                    categoryDescription.setText(categoryModel.getSkuCategoryDescription());
                }
            }
            if(expenseTV==e.getSource()){
                if(expenseTV.getSelectionModel().getSelectedCells().get(0).getColumn()==3){
                     
                  expenseReg=expenseTV.getSelectionModel().getSelectedItem();
                  boolean conf=DISPLAY_MESSAGE.showConfirmDialog(saveSkuRegistration.getScene().getWindow(), "Do You Want To Delete the Expense ?");
                  if(conf){
                      System.out.println("here...2");
                     expenseReg.deleteExpense(expenseReg.getExpenditureId());
                             System.out.println("here exp  "+expenseReg.getExpenditureId());
                     loadExpenses();
                  }
                  
                  
                
                }
                
            }
        }

    }

    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {
            if (skuCategoriestab == e.getSource()) {
                loadTabs();
            }
            if (skuRegTab == e.getSource()) {
                loadTabs();
            }
            if (CustomerRegTab == e.getSource()) {
                loadTabs();
            }
            if (supplierRegTab == e.getSource()) {
                loadTabs();
            }
            if (expenseRegTab == e.getSource()) {
                loadTabs();
            }
        }

    }

    private void privileges() {
        Tab tabs[] = {skuCategoriestab, skuRegTab, CustomerRegTab, supplierRegTab, expenseRegTab};
        for (Tab tab : tabs) {
            setupTab.getTabs().remove(tab);
        }
        for (Tab tab : tabs) {
            if (ProfileVariables.POS_MANAGEMENT_SUBMENUS.contains(tab.getText())) {
                setupTab.getTabs().add(tab);
            }
        }
    }

}
