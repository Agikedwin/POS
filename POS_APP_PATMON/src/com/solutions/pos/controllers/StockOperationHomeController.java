/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import com.solutions.entorno.utilities.dialogs.NotificationType;
import com.solutions.pos.controllers.utilities.FunctionCalculateProductStock;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDERS;
import com.solutions.pos.models.PurchaseOrderItemsModel;
import com.solutions.pos.models.PurchaseOrdersModel;
import com.solutions.pos.models.SkuStocksModel;
import com.solutions.pos.models.SkusModel;
import com.solutions.pos.models.SuppliersModel;
import com.solutions.users.controllers.utilities.ProfileVariables;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import utilities.Load;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class StockOperationHomeController implements Initializable {

    //SKU Stocking CONTROLS
    @FXML
    private BorderPane borderpaneStockingWrapper;
    @FXML
    private TextField stockSearch;
    @FXML
    private Button saveStock;
    @FXML
    private TextField stockQuantity;
    @FXML
    private TextField stockPriceUnit;
    @FXML
    private TextField batchNumber;
    @FXML
    private TextField stockName;
    @FXML
    private DatePicker datePurchased;
    @FXML
    private TableView<SkusModel> skuStockingTV;
    //orders
    @FXML
    private TextField orderSearch;

    @FXML
    private BorderPane borderpaneOrderWrapper1;
    @FXML
    private BorderPane borderpaneOrderCartWrapper;
    @FXML
    private Label salesVat1;
    @FXML
    private TableView<SkusModel> skuOrdersTV;

    //Purchase Invoice CONTROLS
    @FXML
    private Label totals1;
    @FXML
    private Button saveOrder;
    @FXML
    private Button cancelOrder;
    @FXML
    private TableView skuOrderCart;
    @FXML
    private BorderPane borderpaneInvoiceWrapper1;
    @FXML
    private TableView<?> skuSalesTV11;
    @FXML
    private BorderPane borderpaneInvoiceCartWrapper1;
    @FXML
    private Label salesVat11;
    @FXML
    private Label totals11;
    @FXML
    private Button saveInvoice1;
    @FXML
    private Button canceInvoice;
    @FXML
    private TextField invoiceVat_totals1;
    @FXML
    private TextField total_invoice;

    SkusModel skuItem = null;
    @FXML
    private ComboBox supplierCb;

    String supplierID;
    @FXML
    private ComboBox purchaseCb;

    String quotID;
    @FXML
    private TabPane stockTabPane;
    @FXML
    private Tab stockingTab;
    @FXML
    private Tab skuPOrderTab;
    @FXML
    private Tab skuPInvoiceTab;
    private ObservableList<SkusModel> masterData;
    @FXML
    private TableView skuInvoiceCart;

    OnActionListener onActionListener = new OnActionListener();
    MouseActionListener mouseActionListener = new MouseActionListener();
    OnSelectionListener onSelectionListener = new OnSelectionListener();

    String dateOfPurchase = null;

    @FXML
    private ComboBox comboSupplierid;
    @FXML
    private Tab stockingActionsTab;
    @FXML
    private TextField stockActionsSearch;
    @FXML
    private TableView<SkusModel> skuStockingActionsTV;

    @FXML
    private TextField adjustQuantity;
    @FXML
    private TextField adjustPrice;
    @FXML
    private Button saveAdjust;
    @FXML
    private AnchorPane anchorPaneStockActions;
    @FXML
    private AnchorPane anchorPaneStockingActinon;
    @FXML
    private AnchorPane anchorPaneActions;
    @FXML
    private TableView<SkuStocksModel> StockingActionsTV;
    private SkusModel action = null;
    private SkuStocksModel actionStck = null;
    int stockid,skuid=0;
    @FXML
    private Label labelStockHistory;
    @FXML
    private Label labelQuantityInStock;
    @FXML
    private Label labelTotalStocked;
    @FXML
    private Label labelTotalSold;

    public StockOperationHomeController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        privileges();
        stockingTab.setOnSelectionChanged(onSelectionListener);
        stockingActionsTab.setOnSelectionChanged(onSelectionListener);
        skuPOrderTab.setOnSelectionChanged(onSelectionListener);
        skuPInvoiceTab.setOnSelectionChanged(onSelectionListener);
        loadTabs();

    }

    private void loadTabs() {

        Tab selectedTab = stockTabPane.getSelectionModel().getSelectedItem();

        if (selectedTab != null) {
            switch (selectedTab.getText()) {
                case "SKU Stocking":
                    loadStocking();
                    break;
                case "SKU Stocking Actions":
                    loadStockingAction();
                    break;
                case "Purchase Order":
                    loadPurchaseOrder();
                    break;
                case "Purchase Invoice":
                    loadPurchaseInvoice();
                    break;

            }
        }
    }

    private void loadPurchaseInvoice() {
        skuInvoiceCart = PurchaseOrderItemsModel.purchaseOrderSkus("");
        borderpaneInvoiceCartWrapper1.setCenter(skuInvoiceCart);
        ObservableList<String> quotationz = FXCollections.observableArrayList(PurchaseOrdersModel.purchaseOrdersMap(1).values());
        purchaseCb.setItems(quotationz);
        skuSalesTV11 = PurchaseOrderItemsModel.purchaseOrderSkus("");
        borderpaneInvoiceWrapper1.setCenter(skuSalesTV11);
        purchaseCb.setOnAction(onActionListener);
    }

    private void loadPurchaseOrder() {
        skuOrdersTV = SkusModel.SkusData();
        borderpaneOrderWrapper1.setCenter(skuOrdersTV);

        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        //ORDER SEARCH
        FilteredList<SkusModel> filteredOrderData = new FilteredList<>(masterData, p -> true);
        orderSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrderData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSkuname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchModel.getSkuCatName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Integer.toString(searchModel.getSkuId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SkusModel> sortedOrderData = new SortedList<>(filteredOrderData);
        sortedOrderData.comparatorProperty().bind(skuOrdersTV.comparatorProperty());
        skuOrdersTV.setItems(sortedOrderData);
        //END ORDER SEARCH
        ObservableList<String> supp = FXCollections.observableArrayList(SuppliersModel.supplierMap().values());
        supplierCb.setItems(supp);

        skuOrderCart = PurchaseOrderItemsModel.purchaseOrderSkus("");
        borderpaneOrderCartWrapper.setCenter(skuOrderCart);
        skuOrdersTV.setOnMouseClicked(mouseActionListener);
        supplierCb.setOnAction(onActionListener);
        comboSupplierid.setOnAction(onActionListener);
        saveOrder.setOnAction(onActionListener);
        skuOrderCart.setOnMouseClicked(mouseActionListener);
    }

    private void loadStocking() {
        ObservableList<String> supp = FXCollections.observableArrayList(SuppliersModel.supplierMap().values());
        comboSupplierid.setItems(supp);

        skuStockingTV = SkusModel.editSkusData();
        borderpaneStockingWrapper.setCenter(skuStockingTV);
        skuStockingTV.setOnMouseClicked(mouseActionListener);

        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        //stock  SEARCH
        FilteredList<SkusModel> filteredStockData = new FilteredList<>(masterData, p -> true);
        stockSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStockData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSkuname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchModel.getSkuCatName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Integer.toString(searchModel.getSkuId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SkusModel> sortedStockData = new SortedList<>(filteredStockData);
        sortedStockData.comparatorProperty().bind(skuStockingTV.comparatorProperty());
        skuStockingTV.setItems(sortedStockData);
        datePurchased.setOnAction(onActionListener);
        comboSupplierid.setOnAction(onActionListener);
        saveStock.setOnAction(onActionListener);
        //END QUOTATION SEARCH
    }

    private void loadStockingAction() {
        skuStockingActionsTV = SkusModel.SkusStockActions();
        Load ui = new Load();
        ui.loadTable(anchorPaneStockingActinon, skuStockingActionsTV);
        //borderpaneStockingActionWrapper.setCenter(skuStockingActionsTV);
        skuStockingActionsTV.setOnMouseClicked(mouseActionListener);
        
        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        //stock  SEARCH
        FilteredList<SkusModel> filteredStockData = new FilteredList<>(masterData, p -> true);
        stockActionsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStockData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSkuname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (searchModel.getSkuCatName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Integer.toString(searchModel.getSkuId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<SkusModel> sortedStockData = new SortedList<>(filteredStockData);
        sortedStockData.comparatorProperty().bind(skuStockingActionsTV.comparatorProperty());
        skuStockingActionsTV.setItems(sortedStockData);
        //saveStock.setOnAction(onActionListener);
        //END QUOTATION SEARCH
    }
    
    private void stockingAction(int skuid) {
        StockingActionsTV = SkuStocksModel.SkusStockActions(skuid);
        Load ui = new Load();
        ui.loadTable(anchorPaneActions, StockingActionsTV);
        //borderpaneStockingActionWrapper.setCenter(skuStockingActionsTV);
        StockingActionsTV.setOnMouseClicked(mouseActionListener);
        saveAdjust.setOnAction(onActionListener);
        
       
    }

    class OnActionListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if (purchaseCb == e.getSource()) {
                if (purchaseCb.getItems().size() > 0) {
                    String selectedquotation = purchaseCb.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = PurchaseOrdersModel.purchaseOrdersMap(1);
                    Set<String> catIds = readcats.keySet();
                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedquotation)) {
                            quotID = keys;
                            break;
                        }
                    }
                    skuSalesTV11 = PurchaseOrderItemsModel.purchaseOrderSkus(quotID);
                    borderpaneInvoiceWrapper1.setCenter(skuSalesTV11);

                }
            }
            if (saveOrder == e.getSource()) {
                if (supplierID != null) {
                    PurchaseOrdersModel p_rder = new PurchaseOrdersModel();
                    String orderID = null;
                    int orderid = 0;
                    if (orderID == null) {
                        orderid = FunctionGenerateEntityCode.generatedCode(PURCHASE_ORDERS);
                        orderID = "ORD" + orderid;
                        p_rder.setpOrderId(orderID);
                        p_rder.setSupplierId(supplierID);
                        p_rder.setDoneBy(USER_PROFILE);
                        p_rder.setLastUpdated(SystemVariables.SYSTEM_DATE);
                        p_rder.setStatus(1);
                        p_rder.insertPurchaseOrder();
                    }
                    PurchaseOrderItemsModel orderDetails;
                    ArrayList<PurchaseOrderItemsModel> orderdetailz = new ArrayList<>();
                    ObservableList recs = skuOrderCart.getItems();

                    for (Object recordz : recs) {
                        orderDetails = (PurchaseOrderItemsModel) recordz;
                        orderDetails.setpOrderId(orderID);
                        orderDetails.setDoneBy(USER_PROFILE);
                        orderDetails.setLastUpdated(SystemVariables.SYSTEM_DATE);
                        orderDetails.setStatus(1);
                        orderDetails.insertPurcaseOrderItemsModel();
                        orderdetailz.add(orderDetails);
                    }
                    orderID = null;
                    supplierID = null;
                    skuOrderCart = PurchaseOrderItemsModel.purchaseOrderSkus("");
                    borderpaneOrderCartWrapper.setCenter(skuOrderCart);
                    ObservableList<String> custs = FXCollections.observableArrayList(SuppliersModel.supplierMap().values());
                    supplierCb.setItems(custs);
                    DISPLAY_MESSAGE.showInfoDialog(skuOrderCart.getScene().getWindow(), "Successfully saved the order", NotificationType.INFORMATION);
                    supplierID = null;
                } else {
                    DISPLAY_MESSAGE.showInfoDialog(skuOrderCart.getScene().getWindow(), "Please Select Supplier", NotificationType.ERROR);
                }
            }
            if (supplierCb == e.getSource()) {
                if (supplierCb.getItems().size() > 0) {
                    String selectedCustomer = supplierCb.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = SuppliersModel.supplierMap();
                    Set<String> catIds = readcats.keySet();

                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedCustomer)) {
                            supplierID = keys;
                            break;
                        }
                    }
                }
            }
            if (comboSupplierid == e.getSource()) {
                if (comboSupplierid.getItems().size() > 0) {
                    String selectedCustomer = comboSupplierid.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = SuppliersModel.supplierMap();
                    Set<String> catIds = readcats.keySet();

                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedCustomer)) {
                            supplierID = keys;
                            break;
                        }
                        System.out.println("keyss" + supplierID);
                    }
                }
            }
            if (saveStock == e.getSource()) {
                try {

                    if (skuItem != null) {

                        if (comboSupplierid.getSelectionModel().getSelectedIndex() < 0) {
                            DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please select Supplier", NotificationType.ERROR);

                        } else if (dateOfPurchase == null) {
                            DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please select Date of Purchase", NotificationType.ERROR);
                        } else if (stockQuantity.getText().equals("")) {
                            DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please Enter Quantity of SKUs Purchased", NotificationType.ERROR);
                        } else if (stockPriceUnit.getText().equals("")) {

                            DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please Enter Price of each SKUs Unit Purchased", NotificationType.ERROR);

                        } else {
                            int skuId = skuItem.getSkuId();
                            // String skuName = skuItem.getSkuname();

                            int quantity = Integer.parseInt(stockQuantity.getText());
                            double priceperunit = Double.parseDouble(stockPriceUnit.getText());
                            String batchNo = batchNumber.getText();
                            SkuStocksModel stock = new SkuStocksModel();
                            stock.setSkuId(skuId);
                            stock.setBatchNo(batchNo);
                            stock.setDateOfPurchase(dateOfPurchase);
                            stock.setSupplierid(supplierID);
                            stock.setQuantity(quantity);
                            stock.setPriceperunit(priceperunit);
                            if (stock.addSkuStocks()) {
                                DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Successfully Added Stock", NotificationType.INFORMATION);
                                skuItem = null;
                                stockQuantity.setText(null);
                                stockPriceUnit.setText(null);
                                batchNumber.setText(null);
                                stockName.setText(null);
                            } else {
                                DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Failed to Add Stock", NotificationType.ERROR);
                            }
                            // skuInvoiceCart.getSelectionModel().getSelectedItem().
                        }
                    } else {
                        DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please click on  at least one product", NotificationType.ERROR);
                    }
                } catch (Exception ex) {

                }

            }
            if (datePurchased == e.getSource()) {
                dateOfPurchase = "" + (LocalDate) datePurchased.getValue();
            }
            
            else if(saveAdjust==e.getSource()){
                if(stockid==0){
                
                     DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please  Select an Item From The Stocking History Table to Update", NotificationType.ERROR);
              
                }
                
                if(adjustPrice.getText()==null){
                     DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please  Enter the New Price", NotificationType.ERROR);
                }
                else if(adjustQuantity.getText()==null){
                     DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Please The Correct Quantity", NotificationType.ERROR);
                }
                else {
                    double quantity=Double.parseDouble(adjustQuantity.getText());
                    double buyingPrice=Double.parseDouble(adjustPrice.getText());
             if (   actionStck.updateAction(stockid, quantity, buyingPrice)){
                 stockingAction(skuid); 
                  DISPLAY_MESSAGE.showInfoDialog(skuOrderCart.getScene().getWindow(), "Successfully updated", NotificationType.INFORMATION);
                  double inStock = FunctionCalculateProductStock.getCurrentStock(action.getSkuId());
                  labelQuantityInStock.setText("                     QUANTITY AVAILABLE IN STOCK  "+inStock);
             }
             else{
                  DISPLAY_MESSAGE.showInfoDialog(skuOrderCart.getScene().getWindow(), "Failed to Update", NotificationType.INFORMATION);
             }
                }
            }
        }

    }

    class MouseActionListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {

            if (skuOrderCart == e.getSource()) {
                PurchaseOrderItemsModel model = (PurchaseOrderItemsModel) skuOrderCart.getSelectionModel().getSelectedItem();
                if (model != null) {
                    if (DISPLAY_MESSAGE.showConfirmDialog(skuOrderCart.getScene().getWindow(), "Remove From Cart?")) {
                        skuOrderCart.getItems().remove(model);
                    }
                }
            }
            if (skuOrdersTV == e.getSource()) {
                try {
                    SkusModel model = skuOrdersTV.getSelectionModel().getSelectedItem();
                    if (model != null) {
                        PurchaseOrderItemsModel sm;
                        String qty = DISPLAY_MESSAGE.showInputDialog(skuOrdersTV.getScene().getWindow(), "Enter Number of " + model.getSkuname(), "1");
                        if (qty != null) {
                            int no = Integer.parseInt(qty);
                            sm = new PurchaseOrderItemsModel();
                            sm.setSkuId(model.getSkuId());
                            sm.setSkuName(model.getSkuname());
                            sm.setQuantity(no);
                            skuOrderCart.getItems().add(sm);
                        }
                    }
                } catch (Exception ex) {
                    DISPLAY_MESSAGE.showInfoDialog(saveStock.getScene().getWindow(), "Invalid Data Entered", NotificationType.ERROR);
                }
            }
            if (skuStockingTV == e.getSource()) {
                skuItem = skuStockingTV.getSelectionModel().getSelectedItem();
                if (skuItem != null) {
                    if (skuItem != null) {
                        SkuStocksModel sku = new SkuStocksModel();
                        sku.buyingPrice(skuItem.getSkuId());
                        stockName.setText(skuItem.getSkuname());
                        stockPriceUnit.setText("" + (sku.buyingPrice(skuItem.getSkuId())));
                    }
                }
            }
             else if (skuStockingActionsTV == e.getSource()) {
                  skuid=0;
                if (skuStockingActionsTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 2) {
                    action = skuStockingActionsTV.getSelectionModel().getSelectedItem();
                    if(action !=null){
                        skuid=action.getSkuId();
                       stockingAction(skuid); 
                       double inStock = FunctionCalculateProductStock.getCurrentStock(action.getSkuId());
                        labelQuantityInStock.setText("CURRENT STOCK:"+inStock);
                        labelStockHistory.setText("STOCKING HISTORY FOR   "+action.getSkuname());
                        adjustQuantity.setText(null);
                        adjustPrice.setText(null);
                        
                        //stock levels
                       double totalStocked=FunctionCalculateProductStock.getTotalStocks(action.getSkuId());
                       double totalsold=FunctionCalculateProductStock.getTotalSales(action.getSkuId());
                       labelTotalStocked.setText("TOTAL STOCKED :"+totalStocked);
                       labelTotalSold.setText("TOTAL SOLD :"+totalsold);
                       
                    }
                   
                  
                }
            }
            else if (StockingActionsTV == e.getSource()) {
                 stockid=0;
                if (StockingActionsTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 4) {
                    actionStck= StockingActionsTV.getSelectionModel().getSelectedItem();
                    if(actionStck !=null){
                        adjustQuantity.setText(""+actionStck.getQuantity());
                        adjustPrice.setText(""+actionStck.getPriceperunit());
                         stockid=actionStck.getStockid();
                       
                      
                    }
                   
                  
                }
            }
        }
    }

    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {
            if (stockingTab == e.getSource()) {
                loadTabs();
            } else if (stockingActionsTab == e.getSource()) {
                loadTabs();
            } else if (skuPOrderTab == e.getSource()) {
                loadTabs();
            } else if (skuPInvoiceTab == e.getSource()) {
                loadTabs();
            }
        }

    }

    private void privileges() {
        Tab tabs[] = {stockingTab, skuPOrderTab, skuPInvoiceTab,stockingActionsTab};
        for (Tab tab : tabs) {
            stockTabPane.getTabs().remove(tab);
        }

        for (Tab tab : tabs) {
            if (ProfileVariables.POS_MANAGEMENT_SUBMENUS.contains(tab.getText())) {
                stockTabPane.getTabs().add(tab);
            }
        }
    }
}
