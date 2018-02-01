/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;
//import com.solutions.entorno.utilities.SystemVariables;
//import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
//package com.solutions.pos.controllers;

import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import com.solutions.pos.controllers.utilities.FunctionCalculateProductStock;
import com.solutions.pos.models.CustomerModel;
import com.solutions.pos.models.SkuCategoriesModel;
import com.solutions.pos.models.SkusModel;
import com.solutions.pos.models.SuppliersModel;
import com.solutions.pos.models.reports.AllSalesReports;
import com.solutions.pos.models.reports.CurrentStockReport;
import com.solutions.pos.models.reports.DailySkuSalesReport;
import com.solutions.pos.models.reports.PriceListReports;
import com.solutions.pos.models.reports.PrintCurrentStockReport;
import com.solutions.pos.models.reports.PrintDailySKUSalesReport;
import com.solutions.pos.models.reports.PrintDailySalesReport;
import com.solutions.pos.models.reports.PrintPeriodicSalesReport;
import com.solutions.pos.models.reports.PrintPriceList;
import com.solutions.pos.models.reports.PrintRegisteredItemsList;
import com.solutions.pos.models.reports.ProgressReport;
import com.solutions.pos.models.reports.RegisteredItemsReport;
import com.solutions.pos.models.reports.SalesIntervalsModel;
import com.solutions.pos.models.reports.StockReport;
import com.solutions.users.controllers.utilities.ProfileVariables;
import com.solutions.users.models.User;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author shaddie
 */
public class ReportsHomeController implements Initializable {

    private TextField totalSales;
    @FXML
    private Button processDailyReport;
    @FXML
    private BorderPane borderpaneDailySalesWrapper;
    String dailyDate = null;
    String dpFromDate = null;
    String dpTodate = null;
    String user = null;
    String dailySKUDate = null;
    String fromSuppDate = null;
    String toSuppDate = null;
    String toProfitDate;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private Button processAllSales;
    @FXML
    private TableView allSalesReportTable;
    @FXML
    private BorderPane borderpaneAllSalesWrapper;
    @FXML
    private ComboBox comboAllSalesReport;
    @FXML
    private ComboBox comboSelectCategory;
    @FXML
    private BorderPane borderpaneDailySalesWrapper1;
    @FXML
    private DatePicker dailySalesDate1;
    @FXML
    private ComboBox dailySku;
    @FXML
    private TableView<?> dailySalesReport1;
    @FXML
    private Button processDailySkuReport;
    @FXML
    private TableView tableRegisteredItems;
    @FXML
    private ComboBox userSkus;

    int itemeCategory = 0;
    @FXML
    private BorderPane borderpaneRegItemsWrapper2;
    @FXML
    private ComboBox comboItemsPriceList;
    @FXML
    private BorderPane borderpanePriceListWrapper;

    @FXML
    private TabPane reportsTabPane;
    @FXML
    private Tab dailySalesReportTab;
    @FXML
    private Tab dailySKUSalesReportTab;
    @FXML
    private Tab periodicSalesReport;
    private TextField totalVAT;
    @FXML
    private Button buttonDailySalesReport;
    @FXML
    private TableView tableItemsPriceList;
    @FXML
    private Tab priceListTab;
    @FXML
    private Tab registeredItems;
    @FXML
    private Tab purchaseReportTab;
    @FXML
    private Button btnProcesspurchasereport;
    @FXML
    private Tab stockreportTab;
    @FXML
    private BorderPane borderpaneStockWrapper;
    @FXML
    private Button btnProcessStockReport;
    @FXML
    private TableView tableStockItems;
    @FXML
    private Tab currentStockreportTab;
    @FXML
    private BorderPane borderpanepOrderWrapper1;
    @FXML
    private DatePicker DatefrompInvoice;
    @FXML
    private DatePicker DatetopInvoice;
    @FXML
    private ComboBox supplierpOrders1;
    @FXML
    private Button processSupplierpOrder1;
    @FXML
    private TableView tableviewpOrderReports1;
    @FXML
    private BorderPane borderpanepOrderWrapper;
    @FXML
    private DatePicker DatefrompOrder;
    @FXML
    private DatePicker DatetopOrder;
    @FXML
    private ComboBox supplierpOrders;
    @FXML
    private Button processSupplierpOrder;
    @FXML
    private TableView tableviewpOrderReports;
    @FXML
    private TextField totalQuotation;
    @FXML
    private TextField totalVatQuotation;
    @FXML
    private BorderPane borderpaneQuatationWrapper;
    @FXML
    private DatePicker DatefromQuotation;
    @FXML
    private DatePicker DatetoQuotation;
    @FXML
    private ComboBox customerQuotation;
    @FXML
    private Button processCustomerQuotation;
    @FXML
    private TableView<?> tableviewQuatationReports;
    @FXML
    private TextField txfTotalSales;
    @FXML
    private TextField txfTotalVat;
    @FXML
    private DatePicker dailySalesdate;
    @FXML
    private ComboBox cbdailyUser;
    String userDailySales = null;
    String customerID;
    String supplierID;
    String qpFromDate = null;
    String qpTodate = null;
    String spFromDate = null;
    String spTodate = null;
    String dailySDate = null;
    @FXML
    private BorderPane borderpanePurchaseWrapper;
    @FXML
    private ComboBox<?> comboSelectCategory11;
    @FXML
    private TableView<?> tablePurchaseReport;
    @FXML
    private ComboBox comboSelectCategory1;
    @FXML
    private Button generateDailySKUSalesReport;

    ActionEventsListener actionEventsListener;
    @FXML
    private TextField dailySkuSalesTotals;
    @FXML
    private TextField dailySKUVatTotal;
    @FXML
    private Button generatePeriodicSalesReport;
    @FXML
    private TextField periodicSKUVatTotal;
    @FXML
    private TextField periodicSkuSalesTotals;
    @FXML
    private Button printPriceList;
    @FXML
    private Button printRegisteredItems;
    @FXML
    private TableView dailySalesReportTV;
    @FXML
    private Button generateDailySalesReport;
    @FXML
    private Button buttonDailySalesReport1;
    @FXML
    private BorderPane borderpaneStockWrapper1;
    @FXML
    private Tab quotationReportTab;
    @FXML
    private Tab purchaseOrderReport;
    @FXML
    private Tab purchaseinvoiceReport;
    @FXML
    private TableView tableCurrentStockItems;
    @FXML
    private TextField stockSearch;
    private ObservableList<CurrentStockReport> masterData;

    CurrentStockReport searchModel = new CurrentStockReport();
    @FXML
    private TextField currentStockTotal;
    @FXML
    private TextField currentStockVAT;
    @FXML
    private TextField currentStockNet;
    @FXML
    private Button processCurrentStockReport;
    @FXML
    private ComboBox comboInvervals;
    @FXML
    private TextField txtTotalProfit;
    @FXML
    private DatePicker dateToProfitDate;
    @FXML
    //progre report variables
    private TextField dailySales5dayAgo;
    @FXML
    private TextField dayProfit5dayAgo;
    @FXML
    private TextField dailySales4dayAgo;
    @FXML
    private TextField dayProfit4dayAgo;
    @FXML
    private TextField dailySales3dayAgo;
    @FXML
    private TextField dayProfit3dayAgo;
    @FXML
    private TextField dailySales2dayAgo;
    @FXML
    private TextField dayProfit2dayAgo;
    @FXML
    private TextField dailySalesYesterday;
    @FXML
    private TextField dayProfitYesteday;
    @FXML
    private TextField dailySalesToday;
    @FXML
    private TextField dailySalesGrandTotal;
    @FXML
    private TextField dayProfitToday;
    @FXML
    private TextField dayProfitDrandTotal;
    @FXML
    private TextField monthlySales3monthsAgo;
    @FXML
    private TextField monthlyProfit3MonthsAgo;
    @FXML
    private TextField monthlySales2monthsAgo;
    @FXML
    private TextField monthlyProfit2MonthsAgo;
    @FXML
    private TextField monthlySalesLastMonth;
    @FXML
    private TextField monthlyProfitLastMonth;
    @FXML
    private TextField monthlySalesThisMonth;
    @FXML
    private TextField monthlyProfitThisMonth;
    @FXML
    private TextField monthlyProfitGrandTotal;
    @FXML
    private Tab progressReportTab;
    @FXML
    private TextField monthlySalesGrandTotal;
    
     OnSelectionListener onSelectionListener = new OnSelectionListener();
    @FXML
    private ComboBox comboStockingSupplier;
    @FXML
    private DatePicker supplyFromDate;
    @FXML
    private DatePicker supplyToDate;
    @FXML
    private TextField txtTotalStockCost;

    public ReportsHomeController() {
        this.masterData = FXCollections.observableArrayList(CurrentStockReport.getStocks());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionEventsListener = new ActionEventsListener();
        ObservableList<String> skus = FXCollections.observableArrayList(SkusModel.FetchSkus().values());
        ObservableList<String> users = FXCollections.observableArrayList(User.FetchUsers().values());
        ObservableList<String> itemsReg = FXCollections.observableArrayList(SkuCategoriesModel.FetchSkuCategories().values());
        ObservableList<String> supplis = FXCollections.observableArrayList(SuppliersModel.supplierMap().values());
        ObservableList<String> custs = FXCollections.observableArrayList(CustomerModel.customerMap().values());
        ObservableList<String> intervals = FXCollections.observableArrayList(SalesIntervalsModel.salesIntervals().values());

        privileges();
        loadTabs();
        
        
        progressReportTab.setOnSelectionChanged(onSelectionListener);
        
        //daily sku sales
        
        comboInvervals.setOnAction(actionEventsListener);
        dailySalesReport1 = DailySkuSalesReport.dailySalesReport("date", "user", 0);
        borderpaneDailySalesWrapper1.setCenter(dailySalesReport1);
        DailySkuSalesReport dskur = new DailySkuSalesReport();
        dailySkuSalesTotals.setText("" + dskur.getCumulativeDailySKUSales());
        dailySKUVatTotal.setText("" + dskur.getCumulativeDailySKUVAT());
        generateDailySKUSalesReport.setOnAction(actionEventsListener);
         processCurrentStockReport.setOnAction(actionEventsListener);
         comboSelectCategory1.setOnAction(actionEventsListener);
         
         comboStockingSupplier.setOnAction(actionEventsListener);
         stockreportTab.setOnSelectionChanged(onSelectionListener);
         
         btnProcessStockReport.setOnAction(actionEventsListener);
         supplyFromDate.setOnAction(actionEventsListener);
         supplyToDate.setOnAction(actionEventsListener);
         
        processDailySkuReport.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (dailySKUDate == null) {
//                    dialog.showInfoDialog(processDailySkuReport.getParent().getScene().getWindow(), "Date Required. Please select date", NotificationType.ERROR);
//                   // MessagesUtil.displayMessage("Date Required", "Please select date", 1, null);
                } else {
                    int sku = 0;
                    if (dailySku.getSelectionModel().getSelectedIndex() == 0) {
                        sku = 0;
                    } else {
                        if (dailySku.getItems().size() > 0) {
                            String selectedSku = dailySku.getSelectionModel().getSelectedItem().toString();
                            HashMap<Integer, String> readcats = SkusModel.FetchSkus();
                            Set<Integer> catIds = readcats.keySet();

                            for (int keys : catIds) {
                                if (readcats.get(keys).equals(selectedSku)) {
                                    sku = keys;
                                    break;
                                }
                            }
                        }
                    }
                    String user = null;
                    if (userSkus.getSelectionModel().getSelectedIndex() == 0) {
                        user = null;
                    } else {
                        if (userSkus.getItems().size() > 0) {
                            String selectedUser = userSkus.getSelectionModel().getSelectedItem().toString();
                            HashMap<String, String> readcats = User.FetchUsers();
                            Set<String> catIds = readcats.keySet();

                            for (String keys : catIds) {
                                if (readcats.get(keys).equals(selectedUser)) {
                                    user = keys;
                                    break;
                                }
                            }
                        }
                    }

                    DailySkuSalesReport dskur = new DailySkuSalesReport();
                    dailySalesReport1 = DailySkuSalesReport.dailySalesReport(dailySKUDate, user, sku);
                    borderpaneDailySalesWrapper1.setCenter(dailySalesReport1);
                    dailySkuSalesTotals.setText("" + dskur.getCumulativeDailySKUSales());
                    dailySKUVatTotal.setText("" + dskur.getCumulativeDailySKUVAT());
                }
            }
        });
        dailySalesDate1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dailySKUDate = "" + (LocalDate) dailySalesDate1.getValue();
            }
        });
        dailySku.setItems(skus);
        dailySku.getSelectionModel().select(0);
        userSkus.setItems(users);
        userSkus.getSelectionModel().select(0);
        //periodic sales
        processAllSales.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AllSalesReports allSalesReports = new AllSalesReports();
                allSalesReportTable = AllSalesReports.dailySalesReport(dpFromDate, dpTodate, user, 3);
                borderpaneAllSalesWrapper.setCenter(allSalesReportTable);
                periodicSkuSalesTotals.setText("" + allSalesReports.getCumulativeDailySKUSales());
                periodicSKUVatTotal.setText("" + allSalesReports.getCumulativeDailySKUVAT());
            }
        });
        fromDate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dpFromDate = "" + (LocalDate) fromDate.getValue();
            }
        });
        toDate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dpTodate = "" + (LocalDate) toDate.getValue();
            }
        });
        comboAllSalesReport.setItems(users);
        comboAllSalesReport.getSelectionModel().select(0);
        comboInvervals.setItems(intervals);
        comboAllSalesReport.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (comboAllSalesReport.getItems().size() > 0) {
                    if (comboAllSalesReport.getSelectionModel().getSelectedIndex() == 0) {
                        user = null;
                    } else {

                        String selectedUser = comboAllSalesReport.getSelectionModel().getSelectedItem().toString();
                        HashMap<String, String> readcats = User.FetchUsers();
                        Set<String> catIds = readcats.keySet();

                        for (String keys : catIds) {
                            if (readcats.get(keys).equals(selectedUser)) {
                                user = keys;
                                break;
                            }
                        }
                    }

                }
//
//                dailySalesReportTV = DailySalesReport.dailySalesReport(dailyDate, user, 3);
//                borderpaneDailySalesWrapper.setCenter(dailySalesReportTV);

            }
        });
        generatePeriodicSalesReport.setOnAction(actionEventsListener);
        //price list
        tableItemsPriceList = PriceListReports.itemsPriceListTable(-1);
        borderpanePriceListWrapper.setCenter(tableItemsPriceList);
        comboItemsPriceList.setItems(itemsReg);
        comboItemsPriceList.getSelectionModel().select(0);
        comboSelectCategory1.setItems(itemsReg);
        comboSelectCategory1.getSelectionModel().select(0);
        comboItemsPriceList.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (comboItemsPriceList.getSelectionModel().getSelectedIndex() == 0) {
                    itemeCategory = 0;
                } else {
                    if (comboItemsPriceList.getItems().size() > 0) {
                        String selectedPriceList = comboItemsPriceList.getSelectionModel().getSelectedItem().toString();
                        HashMap<Integer, String> readPrice = SkuCategoriesModel.FetchSkuCategories();
                        Set<Integer> itemCatIds = readPrice.keySet();
                        for (Integer keys : itemCatIds) {
                            if (readPrice.get(keys).equals(selectedPriceList)) {
                                itemeCategory = keys;
                                break;
                            }
                        }
                    }
                }
                tableItemsPriceList = PriceListReports.itemsPriceListTable(itemeCategory);
                borderpanePriceListWrapper.setCenter(tableItemsPriceList);

            }
        });
        printPriceList.setOnAction(actionEventsListener);
        //registered items
        tableRegisteredItems = RegisteredItemsReport.registeredItemsTable(-1);
        borderpaneRegItemsWrapper2.setCenter(tableRegisteredItems);
        comboSelectCategory.setItems(itemsReg);
        comboSelectCategory.getSelectionModel().select(0);
        
        comboSelectCategory.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (comboSelectCategory.getSelectionModel().getSelectedIndex() == 0) {
                    itemeCategory = 0;
                } else {
                    if (comboSelectCategory.getItems().size() > 0) {
                        String selectedRegItems = comboSelectCategory.getSelectionModel().getSelectedItem().toString();
                        HashMap<Integer, String> readItems = SkuCategoriesModel.FetchSkuCategories();
                        Set<Integer> itemCatIds = readItems.keySet();
                        for (Integer keys : itemCatIds) {
                            if (readItems.get(keys).equals(selectedRegItems)) {
                                itemeCategory = keys;
                                break;
                            }
                        }
                    }
                }
                tableRegisteredItems = RegisteredItemsReport.registeredItemsTable(itemeCategory);
                borderpaneRegItemsWrapper2.setCenter(tableRegisteredItems);

            }
        });

        printRegisteredItems.setOnAction(actionEventsListener);
        //daily sales report
        cbdailyUser.setItems(users);
        cbdailyUser.getSelectionModel().select(0);
//        DailySalesReport salesReport = new DailySalesReport();
//        dailySalesReportTV = DailySalesReport.dailySalesReport("date", "user", 0);
        String date = "2015-11-02";
        SalesIntervalsModel intv = new SalesIntervalsModel();
//         dailySalesReportTV = SalesIntervalsModel.dailySalesReport(date);
        borderpaneDailySalesWrapper.setCenter(dailySalesReportTV);

//        txfTotalSales.setText("Kshs. " + salesReport.getCumulativeSales());
//        txfTotalVat.setText("Kshs. " + salesReport.getCumulativeVAT());
        //profits
        dailySalesdate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dailyDate = "" + (LocalDate) dailySalesdate.getValue();
            }
        });
        
        dateToProfitDate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                toProfitDate = "" + (LocalDate) dateToProfitDate.getValue();
            }
        });
        processDailyReport.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
             SalesIntervalsModel salesInt = new SalesIntervalsModel();
             
                    dailySalesReportTV = SalesIntervalsModel.dailySalesReportByDate(dailyDate, toProfitDate, 3);
                    borderpaneDailySalesWrapper.setCenter(dailySalesReportTV);
                    txfTotalSales.setText("Kshs. " + salesInt.getCumulativeSales());
//                    txfTotalVat.setText("Kshs. " + salesInt.getCumulativeVAT());
                     txtTotalProfit.setText("Ksh "+intv.getTotalProfit());
//                dailyDate = null;
        generateDailySalesReport.setOnAction(actionEventsListener);

        processCurrentStockReport.setOnAction(actionEventsListener);

        buttonDailySalesReport1.setOnAction(actionEventsListener);
            }
        });
       
        
    
   
    
    }
    
    private void loadTabs() {
        Tab selectedTab = reportsTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            switch (selectedTab.getText()) {
                case "Procress Report":
                    loadProgressReport();
                    break;
            }
            switch (selectedTab.getText()) {
                case "Stocking Report":
                    loadStockingReport();
                    break;
            }
             switch (selectedTab.getText()) {
                case "Current Stock Report":
                    loadCurrentStock();
                    break;
            }
        }
    }
    
    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {
            loadTabs();
        }

    }
    private void loadStockingReport(){
        ObservableList<String> supp = FXCollections.observableArrayList(SuppliersModel.supplierMap().values());
      comboStockingSupplier.setItems(supp);  
       comboStockingSupplier.setOnAction(actionEventsListener);
    }
    private void loadCurrentStock(){
       
        
                FunctionCalculateProductStock fst= new FunctionCalculateProductStock();
               
                
                tableCurrentStockItems = CurrentStockReport.getCurrentStock();
                CurrentStockReport stockReport = new CurrentStockReport();
                 stockReport.getStocks();
                borderpaneStockWrapper1.setCenter(tableCurrentStockItems);
                    
        FilteredList<CurrentStockReport> filteredData = new FilteredList<>(masterData, p -> true);
        stockSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(searchModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (searchModel.getSkuname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Integer.toString(searchModel.getSkuid()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CurrentStockReport> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableCurrentStockItems.comparatorProperty());
        tableCurrentStockItems.setItems(sortedData);
          
           SalesIntervalsModel intv = new SalesIntervalsModel();
                borderpaneStockWrapper1.setCenter(tableCurrentStockItems);
                currentStockTotal.setText("" + stockReport.getStockValue());
                currentStockVAT.setText("" + (stockReport.getTotal_stock_value() * 0.16));
               // currentStockNet.setText("" + (stockReport.getTotal_stock_value() - (stockReport.getTotal_stock_value() * .16)));
               currentStockNet.setText("Kshs. " + (intv.totalStock()-intv.totalStockSold()));
               
           processCurrentStockReport.setOnAction(actionEventsListener);
        
    }
    
     private void loadProgressReport(){
        ProgressReport progress=new ProgressReport();
        progress.readTodaytSales();
         progress.readYesterdaySales();
          progress.read2DaysAgo();
           progress.read3DaysAgo();
            progress.read4DaysAgo();
             progress.read5DaysAgo();
             
        dailySalesToday.setText(""+progress.getDailySalesToday());
        dayProfitToday.setText(""+progress.getDayProfitToday());
        
        dailySalesYesterday.setText(""+progress.getDailySalesYesterday());
        dayProfitYesteday.setText(""+progress.getDayProfitYesteday());
        
        dailySales2dayAgo.setText(""+progress.getDailySales2dayAgo());
        dayProfit2dayAgo.setText(""+progress.getDayProfit2dayAgo());
        
        dailySales3dayAgo.setText(""+progress.getDailySales3dayAgo());
        dayProfit3dayAgo.setText(""+progress.getDayProfit3dayAgo());
        
        dailySales4dayAgo.setText(""+progress.getDailySales4dayAgo());
        dayProfit4dayAgo.setText(""+progress.getDayProfit4dayAgo());
        
        dailySales5dayAgo.setText(""+progress.getDailySales5dayAgo());
        dayProfit5dayAgo.setText(""+progress.getDayProfit5dayAgo());
        
        dailySalesGrandTotal.setText(""+progress.dailySalesGrandTotal());
        dayProfitDrandTotal.setText(""+progress.dailyProfitGrandTotal());
        
        //MONTHS
        progress.readThisMonth(); 
         progress.readLastMonth(); 
          progress.read2MonthAgo(); 
           progress.read3MonthAgo(); 
        
        
        monthlySalesThisMonth.setText(""+progress.getMonthlySalesThisMonth());
        monthlyProfitThisMonth.setText(""+progress.getMonthlyProfitThisMonth());
        
        monthlySalesLastMonth.setText(""+progress.getMonthlySalesLastMonth());
        monthlyProfitLastMonth.setText(""+progress.getMonthlyProfitLastMonth());
        
        monthlySales2monthsAgo.setText(""+progress.getMonthlySales2monthsAgo());
        monthlyProfit2MonthsAgo.setText(""+progress.getMonthlyProfit2MonthsAgo());
        
        monthlySales3monthsAgo.setText(""+progress.getMonthlySales3monthsAgo());
        monthlyProfit3MonthsAgo.setText(""+progress.getMonthlyProfit3MonthsAgo());
        
        //
        monthlySalesGrandTotal.setText(""+progress.monthlySalesGrandTotal());
        monthlyProfitGrandTotal.setText(""+progress.monthlyProfitGrandTotal());
        
        
        progress=null;
        
    }
       

        //Quatation Report
//        customerQuotation.setItems(custs);
//        customerQuotation.setOnAction(new EventHandler() {
//            @Override
//            public void handle(Event event) {
//                if (customerQuotation.getItems().size() > 0) {
//                    String selectedCustomer = customerQuotation.getSelectionModel().getSelectedItem().toString();
//                    HashMap<String, String> readcats = CustomerModel.customerMap();
//                    Set<String> catIds = readcats.keySet();
//                    for (String keys : catIds) {
//                        if (readcats.get(keys).equals(selectedCustomer)) {
//                            customerID = keys;
//                            break;
//                        }
//                    }
//                }
//            }
//        });
//        DatefromQuotation.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                qpFromDate = "" + (LocalDate) DatefromQuotation.getValue();
//            }
//        }
//        );
//
//        DatetoQuotation.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                qpTodate = "" + (LocalDate) DatetoQuotation.getValue();
//            }
//        }
//        );
//        processCustomerQuotation.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
////                if (customerQuotation.getSelectionModel().getSelectedIndex() == 0) {
////                    customer = null;
////                } else {
////                    if (customerQuotation.getItems().size() > 0) {
////                        String selectedUser = dailyUser.getSelectionModel().getSelectedItem().toString();
////                        HashMap<String, String> readcats = User.FetchUsers();
////                        Set<String> catIds = readcats.keySet();
////
////                        for (String keys : catIds) {
////                            if (readcats.get(keys).equals(selectedUser)) {
////                                user = keys;
////                                break;
////                            }
////                        }
////                    }
////                }
//                QuotationReports qReports = new QuotationReports();
//                tableviewQuatationReports = QuotationReports.quotationReportTV(qpFromDate, qpTodate, customerID);
//                borderpaneQuatationWrapper.setCenter(tableviewQuatationReports);
//                totalQuotation.setText("Kshs. " + qReports.getCumulativeQuotations());
//                totalVatQuotation.setText("Kshs. " + qReports.getCumulativeVATQuotation());
//
//            }
//        });
//
//        //Purchase Order Reports
//        supplierpOrders.setItems(supplis);
//        supplierpOrders.setOnAction(new EventHandler() {
//            @Override
//            public void handle(Event event) {
//                if (supplierpOrders.getItems().size() > 0) {
//                    String selectedSupplier = supplierpOrders.getSelectionModel().getSelectedItem().toString();
//                    HashMap<String, String> readsaplliers = SuppliersModel.supplierMap();
//                    Set<String> catIds = readsaplliers.keySet();
//                    for (String keys : catIds) {
//                        if (readsaplliers.get(keys).equals(selectedSupplier)) {
//                            supplierID = keys;
//                            break;
//                        }
//                    }
//                }
//            }
//        });
//        DatefrompOrder.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                spFromDate = "" + (LocalDate) DatefrompOrder.getValue();
//            }
//        }
//        );
//
//        DatetopOrder.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                spTodate = "" + (LocalDate) DatetopOrder.getValue();
//            }
//        });
//        processSupplierpOrder.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                PurchaseOrderReports pReports = new PurchaseOrderReports();
//                tableviewpOrderReports = PurchaseOrderReports.purchaseOrderReportTV(spFromDate, spTodate, supplierID);
//                borderpanepOrderWrapper.setCenter(tableviewpOrderReports);
//            }
//        });
        //Daily Sales
//        ObservableList<String> users = FXCollections.observableArrayList(User.FetchUsers().values());
//        cbdailyUser.setItems(users);
//        cbdailyUser.getSelectionModel().select(0);
//        dailySalesdate.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                dailySDate = "" + (LocalDate) dailySalesdate.getValue();
//            }
//        });
    

    private void privileges() {

        Tab tabs[] = {dailySalesReportTab, dailySKUSalesReportTab, periodicSalesReport, priceListTab, registeredItems,
            stockreportTab, currentStockreportTab,progressReportTab};
        for (Tab tab : tabs) {
            reportsTabPane.getTabs().remove(tab);
        }
        for (Tab tab : tabs) {
            if (ProfileVariables.POS_MANAGEMENT_SUBMENUS.contains(tab.getText())) {
                reportsTabPane.getTabs().add(tab);
            }
        }

        Tab hideTabs[] = {purchaseReportTab, quotationReportTab, purchaseOrderReport, purchaseinvoiceReport};

        for (Tab tab : hideTabs) {
            reportsTabPane.getTabs().remove(tab);
        }
    }

    class ActionEventsListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if (comboInvervals == e.getSource()) {
                String intervals = comboInvervals.getSelectionModel().getSelectedItem().toString();
                HashMap<String, String> readInt = SalesIntervalsModel.salesIntervals();
                Set<String> id = readInt.keySet();

                for (String keys : id) {
                    if (readInt.get(keys).equals(intervals)) {
                        String getId = keys;

                        dailySalesReportTV = SalesIntervalsModel.dailySalesReport(getId);
//                jobsTV=Job.definedJobs()
                        SalesIntervalsModel intv = new SalesIntervalsModel();
                        borderpaneDailySalesWrapper.setCenter(dailySalesReportTV);

                        comboInvervals.setOnAction(actionEventsListener);

                        txfTotalSales.setText("Kshs. " + intv.getCumulativeSales());
                      // txfTotalVat.setText("Kshs. " + intv.getCumulativeVAT());
                        txtTotalProfit.setText("Ksh "+intv.getTotalProfit());
                        //profits
                        break;

                    }

                }
            }
        else    if( comboStockingSupplier ==e.getSource()||btnProcessStockReport==e.getSource()){
               
                if (comboStockingSupplier.getItems()!=null) {
                    String selectedCustomer = comboStockingSupplier.getSelectionModel().getSelectedItem().toString();
                    HashMap<String, String> readcats = SuppliersModel.supplierMap();
                    Set<String> catIds = readcats.keySet();

                    for (String keys : catIds) {
                        if (readcats.get(keys).equals(selectedCustomer)) {
                          supplierID = keys;
                            break;
                        }
                  
                    }
                }
                        
                tableStockItems = StockReport.StockSupply(supplierID,fromSuppDate,toSuppDate);
                 StockReport stp=new StockReport();
                borderpaneStockWrapper.setCenter(tableStockItems);
                txtTotalStockCost.setText(""+stp.getTotalCost());
                supplierID=null;
                fromSuppDate=null;
                toSuppDate=null;
                
                
            }
         else   if(supplyFromDate==e.getSource()){
                 fromSuppDate = "" + (LocalDate) supplyFromDate.getValue();
            }
         else   if(supplyToDate==e.getSource()){
                 toSuppDate = "" + (LocalDate) supplyToDate.getValue();
            }
//            if(btnProcessStockReport==e.getSource()  ){
//                
//                tableStockItems = StockReport.StockSupply(supplierID,fromSuppDate,toSuppDate);
//                StockReport stp=new StockReport();
//                borderpaneStockWrapper.setCenter(tableStockItems);
//            }

       else     if (generateDailySKUSalesReport == e.getSource()) {
                PrintDailySKUSalesReport.generateReport(dailySalesReport1, "daily sku sales" + SYSTEM_DATE + ".pdf", "Daily SKU Sales Summary ", dailySkuSalesTotals.getText(), dailySKUVatTotal.getText());
            }
        else    if (generatePeriodicSalesReport == e.getSource()) {
                PrintPeriodicSalesReport.generateReport(allSalesReportTable, "periodic sales" + SYSTEM_DATE + ".pdf", "Periodic Sales Summary ", periodicSkuSalesTotals.getText(), periodicSKUVatTotal.getText());

            }

        else    if (printPriceList == e.getSource()) {
                PrintPriceList.generateReport(tableItemsPriceList, "Price List" + SYSTEM_DATE + ".pdf", "Price List");
            }

        else    if (printRegisteredItems == e.getSource()) {
                PrintRegisteredItemsList.generateReport(tableRegisteredItems, "Registered SKUs" + SYSTEM_DATE + ".pdf", "List of Registered SKUs");
            }

         else   if (generateDailySalesReport == e.getSource()) {
                PrintDailySalesReport.generateReport(dailySalesReportTV, "daily salesReport" + SYSTEM_DATE + ".pdf", "Daily Sales Report", txfTotalSales.getText(), txfTotalVat.getText());
            }
        else    if (buttonDailySalesReport1 == e.getSource()) {
                PrintCurrentStockReport.generateReport(tableCurrentStockItems, "Current Stock " + SYSTEM_DATE + ".pdf", "Current Stock as at " + SYSTEM_DATE);
            }
        else    if (processCurrentStockReport == e.getSource()) {
              
              loadCurrentStock();
            }
         else   if(comboSelectCategory1==e.getSource()){
                
                if (comboSelectCategory1.getSelectionModel().getSelectedIndex() == 0) {
                    itemeCategory = 0;
                } else {
                    if (comboSelectCategory1.getItems().size() > 0) {
                        String selectedRegItems = comboSelectCategory1.getSelectionModel().getSelectedItem().toString();
                        HashMap<Integer, String> readItems = SkuCategoriesModel.FetchSkuCategories();
                        Set<Integer> itemCatIds = readItems.keySet();
                        for (Integer keys : itemCatIds) {
                            if (readItems.get(keys).equals(selectedRegItems)) {
                                itemeCategory = keys;
                                break;
                            }
                        }
                    }
                }
                tableStockItems = StockReport.StockPreview(itemeCategory);
                borderpaneStockWrapper.setCenter(tableStockItems);
                txtTotalStockCost.setText(null);
                
            }
            

        }
    }

}
