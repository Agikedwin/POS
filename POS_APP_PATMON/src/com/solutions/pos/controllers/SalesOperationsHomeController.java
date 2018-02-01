/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.MessagesUtil;
import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import com.solutions.entorno.utilities.dialogs.NotificationType;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.UILoader;
import com.solutions.pos.controllers.utilities.FunctionCalculateProductStock;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import static com.solutions.pos.controllers.utilities.PosVariables.INVOICES;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOATATIONS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import com.solutions.pos.models.CustomerModel;
import com.solutions.pos.models.InvoiceModel;
import com.solutions.pos.models.InvoiceSkuModel;
import com.solutions.pos.models.QuotationSkusModel;
import com.solutions.pos.models.QuotationsModel;
import com.solutions.pos.models.SkuOrdersModel;
import com.solutions.pos.models.SkuSalesModel;
import com.solutions.pos.models.SkusModel;
import com.solutions.users.controllers.utilities.ProfileVariables;
import static java.lang.Math.round;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class SalesOperationsHomeController implements Initializable {

    //SKU Prices CONTROLS
    @FXML
    private BorderPane borderpaneSKUPricesWrapper;
    @FXML
    private TextField skuPricesSearch;
    @FXML
    private Button saveSKUPrices;
    @FXML
    private TextField retailPrice;
    @FXML
    private TextField wholesalePrice;
    @FXML
    private TextField vat;
    @FXML
    private TextField reorderLevel;
    @FXML
    private TextField skuName;
    @FXML
    private TextArea description;
    @FXML
    private Label photo;
    @FXML
    private TableView skuPricesTV;

    //SKU Sales CONTROLS
    @FXML
    private TextField salesSearch;
    @FXML
    private BorderPane borderpaneSalesWrapper;
    @FXML
    private BorderPane borderpaneBasketWrapper;
    @FXML
    private Label salesVat;
    @FXML
    private Label totals;
    @FXML
    private TextField amountIn;
    @FXML
    private TextField change;
    @FXML
    private Button saveShopping;
    @FXML
    private Tab skuSalesTab;
    @FXML
    private TableView skuSalesShoppingCart;
    @FXML
    private TableView<SkusModel> skuSalesTV;
    @FXML
    private TextField vat_totals;
    @FXML
    private TextField total_sales;
    @FXML
    private Button cancel_sku_sales;
    @FXML
    private Tab skuPricesTab;
    @FXML
    private ComboBox cbSavedTransactions;
    @FXML
    private Button saveTransaction;

    String orderId;
    int orders;
    //quotation 
    @FXML
    private TableView skuQuotationTV;
    @FXML
    private TableView skuQuotationCart;
    @FXML
    private Tab skuQuatationTab;
    @FXML
    private BorderPane borderpaneQuatationCartWrapper;
    @FXML
    private Button cancel_sku_quatation;
    @FXML
    private TextField quatationVat_totals;
    @FXML
    private TextField total_quatations;
    @FXML
    private TextField quatationSearch;
    @FXML
    private Button saveQuatation;
    @FXML
    private BorderPane borderpaneQuatationWrapper;
    @FXML
    private ComboBox customerDetails;
    //invoice
    @FXML
    private Tab skuInvoiceTab;
    @FXML
    private Button cancel_sku_invoice;
    @FXML
    private TextField invoiceVat_totals;
    @FXML
    private TextField total_invoice;
    @FXML
    private TableView skuInvoiceCart;
    @FXML
    private TableView skuInvoiceTV;
    @FXML
    private ComboBox cbQuotationsList;
    @FXML
    private BorderPane borderpaneInvoiceWrapper;
    @FXML
    private BorderPane borderpaneInvoiceCartWrapper;
    @FXML
    private Button saveInvoice;

    SkusModel themodel = null;
    QuotationsModel quotationsModel = null;
    String customerID;
    String quotID;
    @FXML
    private TabPane salesTabedPane;
    @FXML
    private Label salesVat1;
    @FXML
    private Label totals1;
    @FXML
    private Label salesVat11;
    @FXML
    private Label totals11;

    private ObservableList<SkusModel> masterData;
    SkusModel searchModel = new SkusModel();

    @FXML
    private TextField invoiceSearch;
    @FXML
    private Button generateInvoice;

    OnActionListener onActionListener = new OnActionListener();
    MouseActionListener mouseActionListener = new MouseActionListener();
    OnSelectionListener onSelectionListener = new OnSelectionListener();
    OnKeyListener onKeyListener = new OnKeyListener();
    @FXML
    private Tab reprintSalesReceiptsTab;
    @FXML
    private TextField receiptSearch;
    @FXML
    private TableView<SkuOrdersModel> salesReceiptsReprintTV;

    @FXML
    private Button reprintReceipt;
    @FXML
    private TextField receiptReprintTotalVAT;
    @FXML
    private TextField receiptReprintTotalSales;
    @FXML
    private TableView<SkuSalesModel> receiptItemsReprintingTV;

    @FXML
    private Tab skuSalesReturnTab;
    @FXML
    private BorderPane borderpaneInvoiceWrapper1;
    @FXML
    private TextField SalesReturnSearch;
    @FXML
    private TableView<SkuOrdersModel> skuSalesReturnTV;
    @FXML
    private Label salesVat111;
    @FXML
    private Button saveSalesReturn;
    @FXML
    private TextField SalesReturnVat_totals;
    @FXML
    private TextField total_SalesReturn;
    @FXML
    private TableView<SkuSalesModel> skuSalesReturnCart;

    @FXML
    private AnchorPane reprintAchorPane;
    @FXML
    private AnchorPane receiptItemsAnchor;
    @FXML
    private BorderPane receiptItemsBorderPane;

    SkuOrdersModel skusOrdersModel = null;
    SkuSalesModel skuSalesModel = null;
    private double initialCost = 0;

    @FXML
    private AnchorPane skuSalesReturnAnchorPane;
    @FXML
    private BorderPane skuSalesReturnBorderPane;
    @FXML
    private TextField balanceToCustomer;
    @FXML
    private BorderPane borderpaneGRWrapper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        privileges();
        skuSalesTab.setOnSelectionChanged(onSelectionListener);
        skuQuatationTab.setOnSelectionChanged(onSelectionListener);
        skuInvoiceTab.setOnSelectionChanged(onSelectionListener);
        skuPricesTab.setOnSelectionChanged(onSelectionListener);
        skuSalesReturnTab.setOnSelectionChanged(onSelectionListener);
        reprintSalesReceiptsTab.setOnSelectionChanged(onSelectionListener);

        loadTabs();

    }

    private void loadTabs() {
        Tab selectedTab = salesTabedPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            switch (selectedTab.getText()) {
                case "SKU Sales":
                    loadSales();
                    break;
                case "SKU Sales Quotation":
                    loadQuotation();
                    break;
                case "SKU Sales Invoice":
                    loadInvoices();
                    break;
                case "Adjust SKU Prices":
                    loadPrices();
                    break;
                case "Re-print Sales Receipt ":
                    loadReceipts();
                    break;
                case "SKU Sales Goods Return":
                    loadSalesReturn();
                    break;
                case "SKU Discounted Sales":
                    //  loadInvoices();
                    break;
                case "SKU Upward  Adjustment Sales":
                    // loadPrices();
                    break;
            }
        }
    }

    private void loadReceipts() {
        receiptSearch.setOnKeyTyped(onKeyListener);
        reprintReceipt.setOnAction(onActionListener);
        receiptSearch.setText("");
        salesReceiptsReprintTV = SkuOrdersModel.salesOrdersDetails(3, "randomtext");
        salesReceiptsReprintTV.setOnMouseClicked(mouseActionListener);
        salesReceiptsReprintTV.getSelectionModel().setCellSelectionEnabled(true);
        UILoader.loadTable(reprintAchorPane, salesReceiptsReprintTV);

        receiptItemsReprintingTV = SkuSalesModel.shoppingBasketTV("");
        receiptItemsBorderPane.setCenter(receiptItemsReprintingTV);
        receiptReprintTotalVAT.setText("" + 0);
        receiptReprintTotalSales.setText("" + 0);
    }

    private void loadSalesReturn() {
        SalesReturnSearch.setOnKeyTyped(onKeyListener);
        reprintReceipt.setOnAction(onActionListener);
        SalesReturnSearch.setText("");
        skuSalesReturnTV = SkuOrdersModel.salesOrdersDetails(3, "randomtext");
        skuSalesReturnTV.setOnMouseClicked(mouseActionListener);
        skuSalesReturnTV.getSelectionModel().setCellSelectionEnabled(true);
        UILoader.loadTable(skuSalesReturnAnchorPane, skuSalesReturnTV);

        skuSalesReturnCart = SkuSalesModel.goodsReturnTV("");
        skuSalesReturnBorderPane.setCenter(skuSalesReturnCart);
        SalesReturnVat_totals.setText("" + 0);
        total_SalesReturn.setText("" + 0);

        saveSalesReturn.setOnAction(onActionListener);
    }

    private void loadSales() {
        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        skuSalesTV = SkusModel.SkusData();
        borderpaneSalesWrapper.setCenter(skuSalesTV);
        //SALES SERACH         
        FilteredList<SkusModel> filteredData = new FilteredList<>(masterData, p -> true);
        salesSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(searchModel -> {
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
        SortedList<SkusModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(skuSalesTV.comparatorProperty());
        skuSalesTV.setItems(sortedData);
        //END TABLE SERACH
        ObservableList<String> orderz = FXCollections.observableArrayList(SkuOrdersModel.getOrders(1));
        cbSavedTransactions.setItems(orderz);
        skuSalesShoppingCart = SkuSalesModel.shoppingBasketTV("");
        borderpaneBasketWrapper.setCenter(skuSalesShoppingCart);
        skuSalesTV.setOnMouseClicked(mouseActionListener);
        cancel_sku_sales.setOnAction(onActionListener);
        cbSavedTransactions.setOnAction(onActionListener);
        saveTransaction.setOnAction(onActionListener);
        saveShopping.setOnAction(onActionListener);
        skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);
    }

    private void loadQuotation() {
        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        skuQuotationTV = SkusModel.SkusData();
        borderpaneQuatationWrapper.setCenter(skuQuotationTV);

        ObservableList<String> custs = FXCollections.observableArrayList(CustomerModel.customerMap().values());
        customerDetails.setItems(custs);
        // customerDetails.getSelectionModel().selectFirst();
        customerDetails.setOnAction(onActionListener);
        //skuQuotationCart = QuotationSkusModel.quotationBasketTV("");
        borderpaneQuatationCartWrapper.setCenter(skuQuotationCart);
        //QUOTATION SEARCH
        FilteredList<SkusModel> filteredQuotationData = new FilteredList<>(masterData, p -> true);
        quatationSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredQuotationData.setPredicate(searchModel -> {
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
        SortedList<SkusModel> sortedQuotationData = new SortedList<>(filteredQuotationData);
        sortedQuotationData.comparatorProperty().bind(skuQuotationTV.comparatorProperty());
        skuQuotationTV.setItems(sortedQuotationData);
        //END QUOTATION SEARCH
        skuQuotationTV.setOnMouseClicked(mouseActionListener);
        cancel_sku_quatation.setOnAction(onActionListener);
        saveQuatation.setOnAction(onActionListener);
        skuQuotationCart.setOnMouseClicked(mouseActionListener);

    }

    private void loadInvoices() {
        ObservableList<String> quotationz = FXCollections.observableArrayList(QuotationsModel.quotationMap(1).values());
        cbQuotationsList.setItems(quotationz);
        skuInvoiceTV = QuotationSkusModel.quotationBasketTV("");
        borderpaneInvoiceWrapper.setCenter(skuInvoiceTV);
        cbQuotationsList.setOnAction(onActionListener);
        generateInvoice.setOnAction(onActionListener);
        cancel_sku_invoice.setOnAction(onActionListener);
        saveInvoice.setOnAction(onActionListener);
    }

    private void loadPrices() {
        skuPricesTV = SkusModel.editSkusData();
        borderpaneSKUPricesWrapper.setCenter(skuPricesTV);
        this.masterData = FXCollections.observableArrayList(SkusModel.readSkusSearch());
        //PRICES SEARCH
        FilteredList<SkusModel> filteredPricesData = new FilteredList<>(masterData, p -> true);
        skuPricesSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPricesData.setPredicate(searchModel -> {
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
        SortedList<SkusModel> sortedPricesData = new SortedList<>(filteredPricesData);
        sortedPricesData.comparatorProperty().bind(skuPricesTV.comparatorProperty());
        skuPricesTV.setItems(sortedPricesData);
        // END PRICES SEARCH
        skuPricesTV.setOnMouseClicked(mouseActionListener);
        saveSKUPrices.setOnAction(onActionListener);
    }

    class OnActionListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            if (saveSalesReturn == e.getSource()) {
                SkuSalesModel salesDetails;
                ArrayList<SkuSalesModel> receiptDetails = new ArrayList<>();
                ObservableList lst = skuSalesReturnCart.getItems();
                for (Object record : lst) {
                    salesDetails = (SkuSalesModel) record;
                    if (salesDetails.getQuantity() < 0) {
                        salesDetails.addSkuSales();
                    }
                    receiptDetails.add(salesDetails);
                }
                skusOrdersModel.update(skusOrdersModel.getOrderid());
                SkuSalesModel skm = new SkuSalesModel();
                skm.printSalesReceipt(receiptDetails, skusOrdersModel.getTotal_amount(), skusOrdersModel.getTotal_vat(), skusOrdersModel.getReceiptno(), initialCost - skusOrdersModel.getTotal_amount(), initialCost);
            }
            if (reprintReceipt == e.getSource()) {
                ArrayList<SkuSalesModel> receiptDetails = new ArrayList<>();
                ObservableList<SkuSalesModel> receiptDetailItems = receiptItemsReprintingTV.getItems();

                receiptDetailItems.stream().forEach((record) -> {
                    receiptDetails.add((SkuSalesModel) record);
                });

                SkuSalesModel salesRect = new SkuSalesModel();
                salesRect.printSalesReceipt(receiptDetails, skusOrdersModel.getTotal_amount(), skusOrdersModel.getTotal_vat(), skusOrdersModel.getReceiptno(), 0, skusOrdersModel.getTotal_amount());
                loadReceipts();
            }

            if (saveInvoice == e.getSource()) {
                String invoiceID = "INV" + FunctionGenerateEntityCode.generatedCode(INVOICES);
                InvoiceModel inv = new InvoiceModel();
                inv.setInvoiceId(invoiceID);
                inv.setQuatationId(quotID);
                inv.setDoneBy(USER_PROFILE);
                inv.setLastUpdated(SYSTEM_DATE);
                inv.setStatus(1);
                inv.insertInvoiceModel();
                InvoiceSkuModel invoice;
                ObservableList lst = skuInvoiceCart.getItems();
                for (Object record : lst) {
                    invoice = (InvoiceSkuModel) record;
                    invoice.setStatus(1);
                    invoice.setDoneBy(USER_PROFILE);
                    invoice.setLastUpdated(SYSTEM_DATE);
                    invoice.setRegdate(SYSTEM_DATE);
                    invoice.setInvoiceId(invoiceID);
                    invoice.insertInvoiceskuModel();
                }
                quotationsModel.setStatus(2);
                quotationsModel.update();
                quotationsModel = null;
                invoiceVat_totals.setText(null);
                total_invoice.setText(null);
                skuQuotationCart = QuotationSkusModel.quotationBasketTV("");
                borderpaneQuatationCartWrapper.setCenter(skuQuotationCart);
                skuInvoiceCart = InvoiceSkuModel.quotationBasketTV("");
                borderpaneInvoiceCartWrapper.setCenter(skuInvoiceCart);
                ObservableList<String> quotationzlist = FXCollections.observableArrayList(QuotationsModel.quotationMap(1).values());
                cbQuotationsList.setItems(quotationzlist);
                DISPLAY_MESSAGE.showInfoDialog(skuInvoiceCart.getScene().getWindow(), "Invoice successfully generated", NotificationType.INFORMATION);
            }
            if (cancel_sku_invoice == e.getSource()) {
                quotationsModel = null;
                invoiceVat_totals.setText(null);
                total_invoice.setText(null);
                skuQuotationCart = QuotationSkusModel.quotationBasketTV("");
                borderpaneQuatationCartWrapper.setCenter(skuQuotationCart);
                skuInvoiceCart = InvoiceSkuModel.quotationBasketTV("");
                borderpaneInvoiceCartWrapper.setCenter(skuInvoiceCart);
                ObservableList<String> quotationz = FXCollections.observableArrayList(QuotationsModel.quotationMap(1).values());
                cbQuotationsList.setItems(quotationz);
            }
            if (generateInvoice == e.getSource()) {
                try {
                    if (quotationsModel != null) {
                        QuotationSkusModel quoteSku;
                        ArrayList<InvoiceSkuModel> invoiceSkus = new ArrayList();
                        ObservableList<InvoiceSkuModel> invoiceObs = FXCollections.observableArrayList();
                        InvoiceSkuModel invoice = null;
                        ObservableList lst = skuInvoiceTV.getItems();
                        for (Object record : lst) {
                            quoteSku = (QuotationSkusModel) record;
                            invoice = new InvoiceSkuModel();
                            invoice.setSkuId(quoteSku.getSkuId());
                            invoice.setQuantity(quoteSku.getQuantity());
                            invoice.setUnitPrice(quoteSku.getPriceperunit());
                            invoice.setStatus(1);
                            invoice.setVat(quoteSku.getVat());
                            invoice.setTotal(quoteSku.getTotal());
                            invoice.setSkuname(quoteSku.getSkuName());
                            invoiceSkus.add(invoice);
                            invoiceObs.add(invoice);
                        }
                        skuInvoiceCart.setItems(invoiceObs);
                    } else {
                        DISPLAY_MESSAGE.showInfoDialog(skuInvoiceCart.getScene().getWindow(), "please select quotation", NotificationType.ERROR);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (saveSKUPrices == e.getSource()) {
                try {
                    if (themodel != null) {
                        double retailprice = Double.parseDouble(retailPrice.getText());
                        double wholesaleprice = Double.parseDouble(wholesalePrice.getText());
                        int reorderlevel = Integer.parseInt(reorderLevel.getText());
                        themodel.setSkuname(skuName.getText());
                        themodel.setDescription(description.getText());
                        themodel.setRetailPrice(retailprice);
                        themodel.setWholeSalePrice(wholesaleprice);
                        themodel.setReorderLevel(reorderlevel);
                        themodel.updatePrice();
                        loadPrices();
                        DISPLAY_MESSAGE.showInfoDialog(saveSKUPrices.getParent().getScene().getWindow(), "Successfully Updated SKU Price", NotificationType.SUCCESS);
                        //themodel.update();
                    }
                } catch (Exception ex) {
                    DISPLAY_MESSAGE.showInfoDialog(saveSKUPrices.getParent().getScene().getWindow(), "Failed to  Update SKU details", NotificationType.ERROR);
                }
            }
            if (skuQuotationCart == e.getSource()) {

            }
            if (customerDetails == e.getSource()) {
                if (customerDetails.getItems().size() > 0) {
                    String selectedCustomer = customerDetails.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = CustomerModel.customerMap();
                    Set<String> catIds = readcats.keySet();

                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedCustomer)) {
                            customerID = keys;
                            break;
                        }
                    }
                }
            }
            if (cbQuotationsList == e.getSource()) {
                if (cbQuotationsList.getItems().size() > 0) {
                    String selectedquotation = cbQuotationsList.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = QuotationsModel.quotationMap(1);
                    Set<String> catIds = readcats.keySet();
                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedquotation)) {
                            quotID = keys;
                            break;
                        }
                    }
                    skuInvoiceTV = QuotationSkusModel.quotationBasketTV(quotID);
                    borderpaneInvoiceWrapper.setCenter(skuInvoiceTV);
                    quotationsModel = QuotationsModel.readQuotation(quotID, 1);
                    invoiceVat_totals.setText("" + quotationsModel.getTotal_vat());
                    total_invoice.setText("" + quotationsModel.getTotal_amount());
                }
            }
            if (saveQuatation == e.getSource()) {
                try {
                    if (customerID != null) {
                        double totalamnt = (double) round(Double.parseDouble(total_quatations.getText()) * 100) / 100;
                        double totalvat = (double) round(Double.parseDouble(quatationVat_totals.getText()) * 100) / 100;
                        QuotationsModel qouts = new QuotationsModel();
                        String quotationID = null;
                        int quotation = 0;
                        if (quotationID == null) {
                            quotation = FunctionGenerateEntityCode.generatedCode(QUOATATIONS);
                            quotationID = "QUOT" + quotation;
                            qouts.addNewQuotation(customerID, quotationID, totalvat, totalamnt);
                        }
                        QuotationSkusModel quotationDetails;
                        ArrayList<QuotationSkusModel> quotationDetailz = new ArrayList<>();
                        ObservableList recs = skuQuotationCart.getItems();

                        for (Object recordz : recs) {
                            quotationDetails = (QuotationSkusModel) recordz;
                            quotationDetails.setMode("retail");
                            quotationDetails.setQuotationId(quotationID);
                            quotationDetails.addQuotationSku();
                            quotationDetailz.add(quotationDetails);
                        }
                        quotationID = null;
                        customerID = null;
                        skuQuotationCart = QuotationSkusModel.quotationBasketTV("");
                        borderpaneQuatationCartWrapper.setCenter(skuQuotationCart);
                        quatationVat_totals.setText("0");
                        total_quatations.setText("0");
//                    ObservableList<String> custs = FXCollections.observableArrayList(CustomerModel.customerMap().values());
//                    customerDetails.setItems(custs);
                        DISPLAY_MESSAGE.showInfoDialog(skuQuotationCart.getScene().getWindow(), "Quotation Completed Successfully", NotificationType.INFORMATION);
                    } else {
                        DISPLAY_MESSAGE.showInfoDialog(skuQuotationCart.getScene().getWindow(), "Customer Required! Please Select Customer", NotificationType.INFORMATION);
                    }
                } catch (Exception ee) {
                }
            }
            if (cancel_sku_quatation == e.getSource()) {
                quotationsModel = null;
                invoiceVat_totals.setText(null);
                total_invoice.setText(null);
                skuQuotationCart = QuotationSkusModel.quotationBasketTV("");
                borderpaneQuatationCartWrapper.setCenter(skuQuotationCart);
                skuInvoiceCart = InvoiceSkuModel.quotationBasketTV("");
                borderpaneInvoiceCartWrapper.setCenter(skuInvoiceCart);
                ObservableList<String> quotationz = FXCollections.observableArrayList(QuotationsModel.quotationMap(1).values());
                cbQuotationsList.setItems(quotationz);
                quatationVat_totals.setText("0");
                total_quatations.setText("0");
                skuQuotationTV.setOnMouseClicked(mouseActionListener);
                cancel_sku_quatation.setOnAction(onActionListener);
                saveQuatation.setOnAction(onActionListener);
                skuQuotationCart.setOnMouseClicked(mouseActionListener);
            }
            if (saveShopping == e.getSource()) {
                try {
                    double totalamnt = (double) round(Double.parseDouble(total_sales.getText()) * 100) / 100;
                    String amnountpaid = DISPLAY_MESSAGE.showInputDialog(skuSalesShoppingCart.getScene().getWindow(), "Enter total Amount Paid by the Customer", Double.toString(totalamnt));
                    if (amnountpaid != null) {
                        double amountPaid = (double) round(Double.parseDouble(amnountpaid) * 100) / 100;

                        double totalvat = (double) round(Double.parseDouble(vat_totals.getText()) * 100) / 100;
                        double balance = (double) round((amountPaid - totalamnt) * 100) / 100;
                        if (balance >= 0) {
                            amountIn.setText(amnountpaid);
                            change.setText("" + balance);
                            int receiptNo;
                            SkuOrdersModel order = new SkuOrdersModel();
                            if (orderId == null) {
                                orders = FunctionGenerateEntityCode.generatedCode(SKU_ORDERS);
                                orderId = "ORD" + orders;
                                receiptNo = orders;
                                order.addNewSkuOrder("", orderId, receiptNo, totalvat, totalamnt);
                            } else {
                                order.setOrderid(orderId);
                                order.setTotal_amount(totalamnt);
                                order.setTotal_vat(totalvat);
                                order.update(orderId);
                                receiptNo = orders;
                                SkuSalesModel.deleteSavedTransactionSKUs(orderId);
                            }

                            SkuSalesModel salesDetails;
                            ArrayList<SkuSalesModel> receiptDetails = new ArrayList<>();
                            ObservableList lst = skuSalesShoppingCart.getItems();
                            for (Object record : lst) {
                                salesDetails = (SkuSalesModel) record;
                                //get buying price
                                double buyingPrice = 0;
                                if (salesDetails.getQuantity() < 1) {
                                    buyingPrice = FunctionCalculateProductStock.getBuyingPrice(salesDetails.getSkuId()) * salesDetails.getQuantity();

                                } else {
                                    buyingPrice = FunctionCalculateProductStock.getBuyingPrice(salesDetails.getSkuId());

                                }

                                //set buying price
                                salesDetails.setBuyingPrice(buyingPrice);
                                //end
                                salesDetails.setMode("retail");
                                salesDetails.setOrderId(orderId);
                                salesDetails.addSkuSales();

                                receiptDetails.add(salesDetails);

                            }
                            order.setStatus(3);
                            order.setOrderid(orderId);
                            order.updateState(orderId);
                            SkuSalesModel salesRect = new SkuSalesModel();
                            salesRect.printSalesReceipt(receiptDetails, totalamnt, totalvat, receiptNo, balance, amountPaid);

                            boolean nxtcust = DISPLAY_MESSAGE.showConfirmDialog(skuSalesShoppingCart.getScene().getWindow(), "Transaction Completed Successfully. \n Next Customer?");
                            skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);
                            while (!nxtcust) {
                                nxtcust = DISPLAY_MESSAGE.showConfirmDialog(skuSalesShoppingCart.getScene().getWindow(), "Transaction Completed Successfully. \n Next Customer?");
                            }
                            if (nxtcust) {
                                orderId = null;
                                skuSalesShoppingCart = SkuSalesModel.shoppingBasketTV("");
                                borderpaneBasketWrapper.setCenter(skuSalesShoppingCart);
                                vat_totals.setText("0");
                                total_sales.setText("0");
                                amountIn.setText("0");
                                change.setText("0");
                                ObservableList<String> orderz = FXCollections.observableArrayList(SkuOrdersModel.getOrders(1));
                                cbSavedTransactions.setItems(orderz);
                                cancel_sku_sales.setOnAction(onActionListener);

                                skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);
                            }
                        } else {
                            DISPLAY_MESSAGE.showInfoDialog(skuSalesShoppingCart.getScene().getWindow(), "The Paid amount is less than the total cost of the items", NotificationType.ERROR);
                        }
                    }
                } catch (Exception ex) {
                }
            }
            if (saveTransaction == e.getSource()) {
                try {
                    double totalamnt = Double.parseDouble(total_sales.getText());
                    double totalvat = Double.parseDouble(vat_totals.getText());
                    int receiptNo;
                    SkuOrdersModel order = new SkuOrdersModel();
                    if (orderId == null) {
                        orders = FunctionGenerateEntityCode.generatedCode(SKU_ORDERS);
                        orderId = "ORD" + orders;
                        receiptNo = orders;
                        order.addNewSkuOrder("", orderId, receiptNo, totalvat, totalamnt);
                    } else {
                        order.setTotal_amount(totalamnt);
                        order.setTotal_vat(totalvat);
                        order.update(orderId);
                        // receiptNo = orders;
                    }
                    SkuSalesModel salesDetails;
                    ObservableList lst = skuSalesShoppingCart.getItems();
                    for (Object record : lst) {
                        salesDetails = (SkuSalesModel) record;
                        salesDetails.setMode("retail");
                        salesDetails.setOrderId(orderId);
                        salesDetails.addSkuSales();
                    }
                    DISPLAY_MESSAGE.showInfoDialog(skuSalesShoppingCart.getScene().getWindow(), "Successfully saved Transaction details\n"
                            + "Transaction No: " + orderId, NotificationType.INFORMATION);
                    skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);

                    ObservableList<String> orderz = FXCollections.observableArrayList(SkuOrdersModel.getOrders(1));
                    cbSavedTransactions.setItems(orderz);
                    orderId = null;
                    skuSalesShoppingCart = SkuSalesModel.shoppingBasketTV("");
                    borderpaneBasketWrapper.setCenter(skuSalesShoppingCart);
                    vat_totals.setText("0");
                    total_sales.setText("0");
                    amountIn.setText("0");
                    change.setText("0");
                    skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);
                } catch (Exception ex) {
                }
            }
            if (cbSavedTransactions == e.getSource()) {
                if (cbSavedTransactions.getItems().size() > 0) {
                    orderId = cbSavedTransactions.getSelectionModel().getSelectedItem().toString();
                    SkuOrdersModel orderDetails = SkuOrdersModel.readSkusOrder(orderId);
                    total_sales.setText("" + orderDetails.getTotal_amount());
                    vat_totals.setText("" + orderDetails.getTotal_vat());
                    orders = orderDetails.getReceiptno();
                    skuSalesShoppingCart = SkuSalesModel.shoppingBasketTV(orderId);
                    borderpaneBasketWrapper.setCenter(skuSalesShoppingCart);
                    if (SkuSalesModel.shoppingBasketTV(orderId).getItems() != null);
                }
            }
            if (cancel_sku_sales == e.getSource()) {
                skuSalesShoppingCart = SkuSalesModel.shoppingBasketTV("");
                borderpaneBasketWrapper.setCenter(skuSalesShoppingCart);
                vat_totals.setText("0");
                total_sales.setText("0");
                amountIn.setText("0");
                change.setText("0");
                skuSalesShoppingCart.setOnMouseClicked(mouseActionListener);
            }
        }

    }

    class MouseActionListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {

            if (salesReceiptsReprintTV == e.getSource()) {
                if (salesReceiptsReprintTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 4) {
                    skusOrdersModel = salesReceiptsReprintTV.getSelectionModel().getSelectedItem();
                    receiptItemsReprintingTV = SkuSalesModel.shoppingBasketTV(skusOrdersModel.getOrderid());
                    receiptItemsBorderPane.setCenter(receiptItemsReprintingTV);
                    receiptReprintTotalVAT.setText("" + skusOrdersModel.getTotal_vat());
                    receiptReprintTotalSales.setText("" + skusOrdersModel.getTotal_amount());
                }
            }

            if (skuSalesReturnTV == e.getSource()) {
                if (skuSalesReturnTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 4) {
                    skusOrdersModel = skuSalesReturnTV.getSelectionModel().getSelectedItem();
                    skuSalesReturnCart = SkuSalesModel.goodsReturnTV(skusOrdersModel.getOrderid());
                    skuSalesReturnCart.getSelectionModel().setCellSelectionEnabled(true);
                    skuSalesReturnCart.setOnMouseClicked(mouseActionListener);
                    skuSalesReturnBorderPane.setCenter(skuSalesReturnCart);
                    SalesReturnVat_totals.setText("" + skusOrdersModel.getTotal_vat());
                    total_SalesReturn.setText("" + skusOrdersModel.getTotal_amount());
                    balanceToCustomer.setText("0");
                    initialCost = skusOrdersModel.getTotal_amount();
                }
            }

            if (skuSalesReturnCart == e.getSource()) {
                if (skuSalesReturnCart.getSelectionModel().getSelectedCells().get(0).getColumn() == 6) {
                    skuSalesModel = skuSalesReturnCart.getSelectionModel().getSelectedItem();
                    int selectedItem = skuSalesReturnCart.getSelectionModel().getSelectedIndex();
                    boolean confirmReturn = DISPLAY_MESSAGE.showConfirmDialog(skuSalesReturnCart.getScene().getWindow(), "Do you want to return Item: " + skuSalesModel.getSkuName() + "?");
                    if (confirmReturn) {
                        String qty = DISPLAY_MESSAGE.showInputDialog(skuSalesReturnCart.getScene().getWindow(), "Enter No of Items to return", Double.toString(skuSalesModel.getQuantity()));
                        if (qty != null) {
                            try {
                                double no = Double.parseDouble(qty);
                                if (no > 0 && no <= skuSalesModel.getQuantity()) {
                                    //create new item
                                    SkuSalesModel skuSale = new SkuSalesModel();
                                    skuSale.setDate_reg(SYSTEM_DATE);
                                    skuSale.setLast_update(SYSTEM_DATE);
                                    skuSale.setMode("retail");
                                    skuSale.setOrderId(skuSalesModel.getOrderId());
                                    skuSale.setPriceperunit(skuSalesModel.getPriceperunit());
                                    skuSale.setQuantity(-1 * no);
                                    skuSale.setSkuId(skuSalesModel.getSkuId());
                                    skuSale.setSkuName(skuSalesModel.getSkuName());
                                    skuSale.setTotal(skuSale.getPriceperunit() * skuSale.getQuantity());
                                    skuSale.setVat(skuSale.getTotal() * 0.16);
                                    skuSale.setUserId(SystemVariables.USER_PROFILE);
                                    skuSalesReturnCart.getItems().add(skuSale);
                                    //update existing record
                                    skuSalesModel.setQuantity(skuSalesModel.getQuantity() + skuSale.getQuantity());
                                    skuSalesModel.setTotal(skuSalesModel.getTotal() + skuSale.getTotal());
                                    //skuSalesModel.setVat(skuSalesModel.getVat() - skuSale.getVat());
                                    skuSalesReturnCart.getItems().set(selectedItem, skuSalesModel);
                                    //populate fields
                                    skusOrdersModel.setTotal_amount(skusOrdersModel.getTotal_amount() + skuSale.getTotal());
                                    skusOrdersModel.setTotal_vat(skusOrdersModel.getTotal_vat() + skuSale.getVat());
                                    SalesReturnVat_totals.setText("" + skusOrdersModel.getTotal_vat());
                                    total_SalesReturn.setText("" + skusOrdersModel.getTotal_amount());
                                    balanceToCustomer.setText("" + (Double.parseDouble(balanceToCustomer.getText()) + (-1 * skuSale.getTotal())));

                                } else if (no <= 0) {
                                    DISPLAY_MESSAGE.showInfoDialog(skuSalesReturnCart.getParent().getScene().getWindow(), "Items returned cannot be less that 1 (one).", NotificationType.ERROR);

                                } else if (no > skuSalesModel.getQuantity()) {
                                    DISPLAY_MESSAGE.showInfoDialog(skuSalesReturnCart.getParent().getScene().getWindow(), "You cannot return more good(s)  " + no + " than the bought good(s)  " + skuSalesModel.getQuantity() + "", NotificationType.ERROR);

                                }
                            } catch (Exception ex) {
                                DISPLAY_MESSAGE.showInfoDialog(skuSalesReturnCart.getParent().getScene().getWindow(), "Invalid Data input", NotificationType.ERROR);

                            }
                        }

                    }
                }
            }
            if (skuPricesTV == e.getSource()) {
                SkusModel model = (SkusModel) skuPricesTV.getSelectionModel().getSelectedItem();
                if (model != null) {
                    retailPrice.setText("" + model.getRetailPrice());
                    wholesalePrice.setText("" + model.getWholeSalePrice());
                    vat.setText(model.getVatable());
                    reorderLevel.setText("" + model.getReorderLevel());
                    skuName.setText("" + model.getSkuname());
                    description.setText("" + model.getDescription());
                    themodel = model;
                }
            }
            if (skuQuotationTV == e.getSource()) {
                try {

                    SkusModel model = (SkusModel) skuQuotationTV.getSelectionModel().getSelectedItem();
                    if (model != null) {
                        String qty = DISPLAY_MESSAGE.showInputDialog(skuQuotationTV.getParent().getScene().getWindow(), "Enter Number of " + model.getSkuname() + " for this Quotation.", "1");
                        if (qty != null) {
                            double no = Double.parseDouble(qty);
                            QuotationSkusModel sm = new QuotationSkusModel();
                            sm.setSkuId(model.getSkuId());
                            sm.setSkuName(model.getSkuname());
                            sm.setQuantity(no);
                            if (model.getVatable().equals("VATABLE")) {
                                sm.setPriceperunit((double) round(model.getRetailPrice() * 100) / 100);
                                sm.setVat((double) round((model.getRetailPrice() * 0.16) * 100) / 100);
                            } else {
                                sm.setPriceperunit(model.getRetailPrice());
                                sm.setVat(0);
                            }
                            sm.setTotal((double) round((sm.getQuantity() * sm.getPriceperunit()) * 100) / 100);
                            skuQuotationCart.getItems().add(sm);
                            quatationVat_totals.setText("" + ((double) round(Double.parseDouble(quatationVat_totals.getText()) + sm.getQuantity() * sm.getVat()) * 100) / 100);
                            total_quatations.setText("" + (Double.parseDouble(total_quatations.getText()) + sm.getTotal()));
                        }
                    }
                } catch (Exception ex) {
                    DISPLAY_MESSAGE.showInfoDialog(skuQuotationTV.getParent().getScene().getWindow(), "Invalid Data input", NotificationType.ERROR);

                }
            }
            if (skuSalesShoppingCart == e.getSource()) {
                if (skuSalesShoppingCart.getItems().size() > 0) {
//                    boolean option = DISPLAY_MESSAGE.showConfirmDialog(skuSalesShoppingCart.getScene().getWindow(), "Remove from Cart?");
//                    if (option) {
//                        SkuSalesModel model = (SkuSalesModel) skuSalesShoppingCart.getSelectionModel().getSelectedItem();
//                        skuSalesShoppingCart.getItems().remove(model);
//                        total_sales.setText("" + ((double) round((Double.parseDouble(total_sales.getText()) - model.getTotal()) * 100) / 100));
//                        vat_totals.setText("" + ((double) round((Double.parseDouble(vat_totals.getText()) - (model.getVat() * model.getQuantity())) * 100) / 100));
//                    }
                    int option = DISPLAY_MESSAGE.showConfirmDialogDiscounted(skuSalesShoppingCart.getScene().getWindow(), "Remove from Cart?");

                    if (option == 1) {
                        SkuSalesModel model = (SkuSalesModel) skuSalesShoppingCart.getSelectionModel().getSelectedItem();
                        skuSalesShoppingCart.getItems().remove(model);
                        total_sales.setText("" + ((double) round((Double.parseDouble(total_sales.getText()) - model.getTotal()) * 100) / 100));
                        vat_totals.setText("" + ((double) round((Double.parseDouble(vat_totals.getText()) - (model.getVat() * model.getQuantity())) * 100) / 100));
                    } else if (option == 2) {

                        SkuSalesModel model = (SkuSalesModel) skuSalesShoppingCart.getSelectionModel().getSelectedItem();
                        int selectedRow = skuSalesShoppingCart.getSelectionModel().getSelectedIndex();
                        //boolean confirmAdust = DISPLAY_MESSAGE.showConfirmDialog(skuSalesShoppingCart.getScene().getWindow(), "Remove from Cart?");
                        boolean confirmAdust = DISPLAY_MESSAGE.showConfirmAdjust(skuSalesShoppingCart.getScene().getWindow(), "Do you want to Give Disscount:  ?");
                        
                        String outPutTxt = null;

                        if (confirmAdust) {
                            outPutTxt = "Discout";
                        } else {
                            outPutTxt = "Inflate the Price Of";
                        }
                        double discountAwarded = 0;
                        String discount = DISPLAY_MESSAGE.showInputDialog(skuSalesShoppingCart.getScene().getWindow(), " " + outPutTxt + "  " + model.getSkuName() + " By", "0");
                        if (confirmAdust) {
                            discountAwarded = Double.parseDouble(discount);

                        } else {
                            discountAwarded = 0 - Double.parseDouble(discount);

                        }

                        try {

                            if (discountAwarded != 0) {
                                //  skuSalesShoppingCart.getItems().remove(model);
                                total_sales.setText("" + ((double) round((Double.parseDouble(total_sales.getText()) - model.getTotal()) * 100) / 100));
                                vat_totals.setText("" + ((double) round((Double.parseDouble(vat_totals.getText()) - (model.getVat() * model.getQuantity())) * 100) / 100));
                                if (model.getQuantity() < 1) {
                                    model.setPriceperunit(model.getPriceperunit() - discountAwarded);
                                    model.setTotal(model.getPriceperunit());
                                    model.setVat(model.getVat());
                                    skuSalesShoppingCart.getItems().set(selectedRow, model);
                                    total_sales.setText("" + ((double) round((Double.parseDouble(total_sales.getText()) + model.getTotal()) * 100) / 100));
                                    //note tax set to zero here
                                    vat_totals.setText("" + ((double) round((Double.parseDouble(vat_totals.getText()) + (model.getVat() * model.getQuantity())) * 100) / 100));

                                } else {
                                    model.setPriceperunit(model.getPriceperunit() - discountAwarded);
                                    model.setTotal(model.getPriceperunit() * model.getQuantity());
                                    model.setVat(model.getVat());
                                    skuSalesShoppingCart.getItems().set(selectedRow, model);
                                    total_sales.setText("" + ((double) round((Double.parseDouble(total_sales.getText()) + model.getTotal()) * 100) / 100));
                                    //note tax set to zero here
                                    vat_totals.setText("" + ((double) round((Double.parseDouble(vat_totals.getText()) + (model.getVat() * model.getQuantity())) * 100) / 100));
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            DISPLAY_MESSAGE.showInfoDialog(skuSalesTV.getParent().getScene().getWindow(), "Invalid Input.", NotificationType.ERROR);

                        }

                    }
                }
            }
            if (skuSalesTV == e.getSource()) {
               
                try {
                     

                    if (skuSalesTV.getItems().size() > 0) {
                        SkusModel model = (SkusModel) skuSalesTV.getSelectionModel().getSelectedItem();
                        double inStock = FunctionCalculateProductStock.getCurrentStock(model.getSkuId());
                       double buyingPrice = FunctionCalculateProductStock.getBuyingPrice(model.getSkuId()) ;
                        if(skuSalesTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 3){
                        if (model != null) {
                           
                            if (inStock > 0) {
                                String qty = DISPLAY_MESSAGE.showInputDialog(skuSalesTV.getParent().getScene().getWindow(), "Enter Number of " + model.getSkuname() + " for this sale.", "1");

                                if (qty != null) {
                                    double noOfItems = Double.parseDouble(qty);
                                    if (inStock >= noOfItems) {
                                        int reorderLevel = model.getReorderLevel();
                                        if (inStock <= reorderLevel) {

                                            DISPLAY_MESSAGE.showInfoDialog(skuSalesTV.getParent().getScene().getWindow(), "Stock Warning. The stock level for " + model.getSkuname() + " is below the reorder level.\n"
                                                    + " Consider Re-stocking as soon as possible.", NotificationType.WARNING);
                                        }

                                        SkuSalesModel sm = new SkuSalesModel();
                                        sm.setSkuId(model.getSkuId());
                                        sm.setSkuName(model.getSkuname());
                                        sm.setQuantity(noOfItems);
                                        if (model.getVatable().equals("VATABLE")) {
                                            if (noOfItems < 1) {
                                                sm.setPriceperunit((double) round(model.getRetailPrice() * noOfItems * 100) / 100);
                                                sm.setVat((double) round((model.getRetailPrice() * 0.16) * noOfItems * 100) / 100);
                                            } else {
                                                sm.setPriceperunit((double) round(model.getRetailPrice() * 100) / 100);
                                                sm.setVat((double) round((model.getRetailPrice() * 0.16) * 100) / 100);
                                            }

                                        } else {
                                            if (noOfItems < 1) {
                                                sm.setPriceperunit(model.getRetailPrice() * noOfItems);
                                                sm.setVat(0);
                                            } else {
                                                sm.setPriceperunit(model.getRetailPrice());
                                                sm.setVat(0);
                                            }

                                        }
                                        if (noOfItems < 1) {
                                            sm.setTotal((double) round((sm.getPriceperunit()) * 100) / 100);
                                            skuSalesShoppingCart.getItems().add(sm);
                                            vat_totals.setText("" + ((double) round(Double.parseDouble(vat_totals.getText()) + sm.getQuantity() * sm.getVat()) * 100) / 100);
                                            total_sales.setText("" + (Double.parseDouble(total_sales.getText()) + sm.getTotal()));
                                        } else {
                                            sm.setTotal((double) round((sm.getQuantity() * sm.getPriceperunit()) * 100) / 100);
                                            skuSalesShoppingCart.getItems().add(sm);
                                            vat_totals.setText("" + ((double) round(Double.parseDouble(vat_totals.getText()) + sm.getQuantity() * sm.getVat()) * 100) / 100);
                                            total_sales.setText("" + (Double.parseDouble(total_sales.getText()) + sm.getTotal()));

                                        }

                                    }//dispensing more than what is in  stock
                                    else {
                                        DISPLAY_MESSAGE.showInfoDialog(skuSalesTV.getParent().getScene().getWindow(), "Inadequate Stock " + model.getSkuname() + " has less stock. Please add product to stock then proceed", NotificationType.NOTICE);

                                    }
                                }
                            }//check stock
                            else {
                                DISPLAY_MESSAGE.showInfoDialog(skuSalesTV.getParent().getScene().getWindow(), "No Stock " + model.getSkuname() + " has no stock. Please add product to stock then proceed", NotificationType.ERROR);
                            }
                        }
                        
                }
                else{
                          double profit=  model.getRetailPrice()-(buyingPrice);
                     StringBuilder itemSummary = new StringBuilder("\n"
                            + "Item Name:                   "+model.getSkuname()+"\n \n"
                            + "Quantity in Stock:           "+inStock+"\n\n"
                            + "Buying Price:                "+buyingPrice+" \n\n"
                             + "Selling Price:              "+model.getRetailPrice()+"\n\n"
                             + "Profit Margin:              "+profit+"\n\n "
                            + "");
                     MessagesUtil.displayAlert("SUMMARY ", itemSummary.toString());
                   
                }
                    }
                } catch (Exception ex) {
                    DISPLAY_MESSAGE.showInfoDialog(skuSalesTV.getParent().getScene().getWindow(), "Invalid Input.", NotificationType.ERROR);
                }
            }
        }

    }

    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {
            loadTabs();
        }

    }

    class OnKeyListener implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent e) {
            if (receiptSearch == e.getSource()) {
                salesReceiptsReprintTV = SkuOrdersModel.salesOrdersDetails(3, receiptSearch.getText());
                salesReceiptsReprintTV.setOnMouseClicked(mouseActionListener);
                salesReceiptsReprintTV.getSelectionModel().setCellSelectionEnabled(true);
                UILoader.loadTable(reprintAchorPane, salesReceiptsReprintTV);
            }
            if (SalesReturnSearch == e.getSource()) {
                skuSalesReturnTV = SkuOrdersModel.salesOrdersDetails(3, receiptSearch.getText());
                skuSalesReturnTV.setOnMouseClicked(mouseActionListener);
                skuSalesReturnTV.getSelectionModel().setCellSelectionEnabled(true);
                UILoader.loadTable(skuSalesReturnAnchorPane, skuSalesReturnTV);
            }
        }

    }

    private void privileges() {
        Tab tabs[] = {skuSalesTab, skuQuatationTab, skuInvoiceTab, skuPricesTab, skuSalesReturnTab, reprintSalesReceiptsTab};
        for (Tab tab : tabs) {
            salesTabedPane.getTabs().remove(tab);
        }
        for (Tab tab : tabs) {
            if (ProfileVariables.POS_MANAGEMENT_SUBMENUS.contains(tab.getText())) {
                salesTabedPane.getTabs().add(tab);
            }
        }
    }
}
