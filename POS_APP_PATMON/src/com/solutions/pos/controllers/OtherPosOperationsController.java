/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import com.solutions.entorno.utilities.dialogs.NotificationType;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.UILoader;
import com.solutions.pos.models.SkuOrdersModel;
import com.solutions.pos.models.SkuSalesModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author sonchaba
 */
public class OtherPosOperationsController implements Initializable {

    @FXML
    private TabPane otherPosOperationsTabbedPane;
    @FXML
    private Tab reprintSalesReceiptsTab;
    @FXML
    private BorderPane borderpaneInvoiceWrapper;
    @FXML
    private TextField receiptSearch;
    @FXML
    private TableView<SkuOrdersModel> salesReceiptsReprintTV;
    @FXML
    private Label salesVat11;
    @FXML
    private Label totals11;
    @FXML
    private Button reprintReceipt;
    @FXML
    private TextField receiptReprintTotalVAT;
    @FXML
    private TextField receiptReprintTotalSales;
    @FXML
    private TableView<SkuSalesModel> receiptItemsReprintingTV;
    @FXML
    private Tab skuSalesTab;
    @FXML
    private BorderPane borderpaneSalesWrapper;
    @FXML
    private TextField salesSearch;
    @FXML
    private TableView<?> skuSalesTV;
    @FXML
    private BorderPane borderpaneBasketWrapper;
    @FXML
    private ComboBox<?> cbSavedTransactions;
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
    private Button cancel_sku_sales;
    @FXML
    private TextField vat_totals;
    @FXML
    private TextField total_sales;
    @FXML
    private Button saveTransaction;
    @FXML
    private TableView<?> skuSalesShoppingCart;
    @FXML
    private Tab skuUpwardSalesTab;
    @FXML
    private BorderPane borderpaneSalesWrapper1;
    @FXML
    private TextField salesUpwardSearch;
    @FXML
    private TableView<?> skuUpwardSalesTV;
    @FXML
    private BorderPane borderpaneBasketWrapper1;
    @FXML
    private ComboBox<?> cbUpwardSavedTransactions;
    @FXML
    private Label salesVat2;
    @FXML
    private Label totals2;
    @FXML
    private TextField amountInUpward;
    @FXML
    private TextField changeUpward;
    @FXML
    private Button saveShoppingUpward;
    @FXML
    private Button cancel_sku_salesUpward;
    @FXML
    private TextField Upward_vat_totals;
    @FXML
    private TextField Upward_total_sales;
    @FXML
    private Button saveTransactionUpward;
    @FXML
    private TableView<?> skuUpwardSalesShoppingCart;
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

    OnActionListener onActionListener = new OnActionListener();
    MouseActionListener mouseActionListener = new MouseActionListener();
    OnSelectionListener onSelectionListener = new OnSelectionListener();
    OnKeyListener onKeyListener = new OnKeyListener();
    SkuOrdersModel skusOrdersModel = null;
    SkuSalesModel skuSalesModel = null;
    private double initialCost = 0;
    
    @FXML
    private AnchorPane skuSalesReturnAnchorPane;
    @FXML
    private BorderPane skuSalesReturnBorderPane;
    @FXML
    private TextField balanceToCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        skuSalesTab.setOnSelectionChanged(onSelectionListener);
        skuSalesReturnTab.setOnSelectionChanged(onSelectionListener);
        skuUpwardSalesTab.setOnSelectionChanged(onSelectionListener);
        reprintSalesReceiptsTab.setOnSelectionChanged(onSelectionListener);
        loadTabs();

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

    private void loadTabs() {
        Tab selectedTab = otherPosOperationsTabbedPane.getSelectionModel().getSelectedItem();
        if(selectedTab != null){
        switch (selectedTab.getText()) {
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

    class OnActionListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if(saveSalesReturn == e.getSource()){
                SkuSalesModel salesDetails;
                ArrayList<SkuSalesModel> receiptDetails = new ArrayList<>();
                ObservableList lst = skuSalesReturnCart.getItems();
                for (Object record : lst) {
                    salesDetails = (SkuSalesModel) record;
                    if(salesDetails.getQuantity()<0){
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
                                int no = Integer.parseInt(qty);
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
                                    balanceToCustomer.setText(""+ (Double.parseDouble(balanceToCustomer.getText())+ (-1* skuSale.getTotal())));
                                   
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
        }

    }

    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {
            loadTabs();
        }

    }
    
}