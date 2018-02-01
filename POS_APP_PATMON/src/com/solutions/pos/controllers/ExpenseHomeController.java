/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.MessagesUtil;
import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import static com.solutions.entorno.utilities.SystemVariables.SYSTEM_DATE;
import static com.solutions.entorno.utilities.SystemVariables.USER_PROFILE;
import com.solutions.entorno.utilities.dialogs.NotificationType;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.UILoader;
import com.solutions.pos.controllers.utilities.FunctionGenerateEntityCode;
import static com.solutions.pos.controllers.utilities.PosVariables.ADD_EXPENDITURE;
import com.solutions.pos.models.ExpenditureRegistrationModel;
import com.solutions.pos.models.ExpendituresModel;
import com.solutions.pos.models.reports.Expenditures;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ExpenseHomeController implements Initializable {

    @FXML
    private TableView<ExpendituresModel> actualexpenseTV;

    ExpenditureRegistrationModel expenditureRegistrationModel = null;

    OnActionListener onActionListener = new OnActionListener();
    MouseActionListener mouseActionListener = new MouseActionListener();
    OnSelectionListener onSelectionListener = new OnSelectionListener();

    @FXML
    private TabPane expenseTabPane;
    @FXML
    private TextArea TxtAreaDesc;

    @FXML
    private TextField TextAmount;
    @FXML
    private Button buttonSaveExpense;
    @FXML
    private AnchorPane anchorPaneActualExpWrapper;
    @FXML
    private ComboBox comboAtualExp;
    @FXML
    private DatePicker datePaid;

    @FXML
    private Tab actualExpenseTab;

    ExpendituresModel expModel = null;
    Expenditures expenditure=null;

    private String expDatePaid = null;
    @FXML
    private TextField atualExpSearch;
    @FXML
    private TextField atualExpSearch1;
    @FXML
    private ComboBox comboMonth;
    @FXML
    private ComboBox comboYear;
    @FXML
    private AnchorPane anchorPaneExpenditureWrapper;
    @FXML
    private TableView<?> expenditureTV;
    @FXML
    private Tab expenditureTab;
    private String month, year, expense;
    @FXML
    private TextField txtTotalExpense;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        buttonSaveExpense.setOnAction(onActionListener);
        actualExpenseTab.setOnSelectionChanged(onSelectionListener);
        expenditureTab.setOnSelectionChanged(onSelectionListener);

        buttonSaveExpense.setOnAction(onActionListener);

        datePaid.setOnAction(onActionListener);
        comboAtualExp.setOnAction(onActionListener);
        comboYear.setOnAction(onActionListener);
        comboMonth.setOnAction(onActionListener);
        loadTabs();
        loadExpenditures();
    }

    private void loadTabs() {
        Tab selectedTab = expenseTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            switch (selectedTab.getText()) {
                case "Actual Expenses":
                    loadExpenses();
                    break;
            }
            switch (selectedTab.getText()) {
                case "Expenditures":
                    loadExpenditures();
                    break;

            }
        }
    }

    private void loadExpenses() {
        actualexpenseTV = ExpendituresModel.expendituresData();
        actualexpenseTV.getSelectionModel().setCellSelectionEnabled(true);
        actualexpenseTV.setOnMouseClicked(mouseActionListener);
        UILoader.loadTable(anchorPaneActualExpWrapper, actualexpenseTV);
        ObservableList<String> exp = FXCollections.observableArrayList(ExpenditureRegistrationModel.FetchExpenditures().values());
        comboAtualExp.setItems(exp);

    }

    private void loadExpenditures() {
        expenditureTV = Expenditures.expendituresData(month, year);
        expenditureTV.getSelectionModel().setCellSelectionEnabled(true);
        expenditureTV.setOnMouseClicked(mouseActionListener);
        UILoader.loadTable(anchorPaneExpenditureWrapper, expenditureTV);
        ObservableList<String> exp = FXCollections.observableArrayList(ExpenditureRegistrationModel.FetchExpenditures().values());
//        comboExpenditure.setItems(exp);
        ObservableList<String> years = FXCollections.observableArrayList(
                "2015", "2016", "2017");
        comboYear.setItems(years);
        ObservableList<String> months = FXCollections.observableArrayList(
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        comboMonth.setItems(months);
        
    }
//        private void loadActualExp(){
//          loadExpenditures();  
//        }

    class ActionEventsListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

        }
    }

    class MouseActionListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (actualexpenseTV == e.getSource()) {
                if (actualexpenseTV.getSelectionModel().getSelectedCells().get(0).getColumn() == 4) {
                    expModel = actualexpenseTV.getSelectionModel().getSelectedItem();
                    TextAmount.setText("" + expModel.getAmount());
                    TxtAreaDesc.setText("" + expModel.getDescription());
                    comboAtualExp.getSelectionModel().select(expModel.getExpenditureName());

                }
            }

        }
    }
    
    

    class OnSelectionListener implements EventHandler<Event> {

        @Override
        public void handle(Event e) {

        }

    }

    class OnActionListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if (datePaid == e.getSource()) {
                expDatePaid = ((LocalDate) datePaid.getValue()).toString();

            } else if (comboMonth == e.getSource()) {
                
                if (comboMonth.getSelectionModel().getSelectedIndex() >= 0) {
                    year=null;
                    month = comboMonth.getSelectionModel().getSelectedItem().toString();
                    expenditureTV = Expenditures.expendituresData(month, year);
                    UILoader.loadTable(anchorPaneExpenditureWrapper, expenditureTV);
                   comboYear.getSelectionModel().select("");

                }
                Expenditures exp =new Expenditures();
                    txtTotalExpense.setText(""+exp.getTotalExpenditure());
                    System.out.println("exp is :"+exp.getAmount());

            } else if (comboYear == e.getSource()) {
                if (comboYear.getSelectionModel().getSelectedIndex() >= 0) {
                    year = comboYear.getSelectionModel().getSelectedItem().toString();
                    expenditureTV = Expenditures.expendituresData(month, year);
                    UILoader.loadTable(anchorPaneExpenditureWrapper, expenditureTV);
                   Expenditures exp =new Expenditures();
                    txtTotalExpense.setText(""+exp.getTotalExpenditure());
                    
                    
                  
                }

            } else if (buttonSaveExpense == e.getSource()) {

                String actualExpId = null;

                String expense = comboAtualExp.getSelectionModel().getSelectedItem().toString();
                HashMap<String, String> readExp = ExpenditureRegistrationModel.FetchExpenditures();
                Set<String> expId = readExp.keySet();

                for (String keys : expId) {
                    if (readExp.get(keys).equals(expense)) {
                        actualExpId = keys;
                        break;

                    }

                }
                if (expModel == null) {

                    try {
                        String amountEntered = TextAmount.getText();
                        String descr = TxtAreaDesc.getText();
                        if (actualExpId == null) {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Please Select an Expense", NotificationType.ERROR);
                        } else if (amountEntered.equalsIgnoreCase("")) {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Missing Amount , Please enter amount", NotificationType.ERROR);
                            MessagesUtil.displayMessage("Missing Amount", "Please enter amount", 1, null);
                        } else if (descr.equalsIgnoreCase("")) {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Missing Description , Please enter Description", NotificationType.ERROR);
                        } else if (expDatePaid == null) {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Please Select the Date Expense Was Paid ", NotificationType.ERROR);
                        } else {
                            double amt = Double.parseDouble(amountEntered);
                            expModel = new ExpendituresModel();
                            expModel.setExpenditureid("AXP" + FunctionGenerateEntityCode.generatedCode(ADD_EXPENDITURE));
                            expModel.setAddExpenseId(actualExpId);
                            expModel.setAmount(amt);
                            expModel.setDescription(descr);
                            expModel.setDatepaid(expDatePaid);
                            expModel.setDoneBy(USER_PROFILE);
                            expModel.setLastUpdated(SYSTEM_DATE);
                            expModel.setStatus(1);
                            if (expModel.insertExpenditureModel()) {
                                DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Successfully Save the Record", NotificationType.INFORMATION);
                                loadExpenses();
                                comboAtualExp.getSelectionModel().select(0);
                                TextAmount.setText(null);
                                TxtAreaDesc.setText(null);
                            } else {

                                DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Successfully Save the Record", NotificationType.ERROR);
                            }
                            expModel = null;
                            expDatePaid = null;
                            actualExpId = null;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    if (expModel.getExpenditureid() == null) {
                        DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Please Select an Expense", NotificationType.ERROR);
                    } else if (expDatePaid == null) {
                        DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Please Select the Date Expense Was Paid ", NotificationType.ERROR);
                    } else {
                        expModel = new ExpendituresModel();
                        expModel.setAmount(Double.parseDouble(TextAmount.getText()));
                        expModel.setDescription(TxtAreaDesc.getText());
                        expModel.setDatepaid(expDatePaid);
                        expModel.setDoneBy(USER_PROFILE);
                        expModel.setLastUpdated(SYSTEM_DATE);
                        expModel.setStatus(1);
                        if (expModel.updateActualExp(expModel.getExpenditureid())) {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Successfully Updated the Expenditure", NotificationType.INFORMATION);
                            loadExpenses();
                            comboAtualExp.getSelectionModel().select(null);
                            TextAmount.setText(null);
                            TxtAreaDesc.setText(null);

                        } else {
                            DISPLAY_MESSAGE.showInfoDialog(buttonSaveExpense.getScene().getWindow(), "Failed to Update Expenditure ", NotificationType.ERROR);

                        }
                        expModel = null;
                        expDatePaid = null;
                        actualExpId = null;
                    }

                }
            }
        }
    }
}
