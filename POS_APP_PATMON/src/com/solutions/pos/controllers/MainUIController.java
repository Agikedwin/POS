/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers;

import com.solutions.entorno.utilities.SystemVariables;
import com.solutions.pos.controllers.utilities.Clock;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.EXPENSE;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.OTHER_OPERATIONS;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.REPORTS;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.SALES;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.SETUP;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.STOCK;
import static com.solutions.pos.controllers.utilities.FXMLLoaderUtil.UILoader;
import com.solutions.users.controllers.utilities.FunctionGetUserDetails;
import static com.solutions.users.controllers.utilities.ProfileVariables.POS_MANAGEMENT_MENU;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import utilities.Load;

public class MainUIController implements Initializable {

    @FXML
    private Button linkSalesOperations;
    private BorderPane borderpaneWrapper;
    @FXML
    private Button linksetup;
    @FXML
    private Button linkStockOperation;
    @FXML
    private Button linkExpense;
    @FXML
    private Button report;
    @FXML
    private AnchorPane anchorArea;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lbl;
    @FXML
    private Label lblUser;
    Load view = new Load();
    @FXML
    private Label lblBusiness;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Clock clock = new Clock();
        lblTime = clock.bindToTime(lblTime);
        lblDate = clock.bindToDate(lblDate);
        lblUser.setText(FunctionGetUserDetails.getFullName(SystemVariables.USER_PROFILE));
        lblBusiness.setText(SystemVariables.INSTITUTION_NAME);
        // view.loadView(anchorArea, "/com/solutions/pos/views/SalesOperationsHome.fxml");
        UILoader.loadView(anchorArea, SALES);

        Button button[] = {linkSalesOperations, linkStockOperation, linkExpense, linksetup, report};
        for (Button but : button) {
          but.setVisible(false);
        }
        for (Button but : button) {
            if (POS_MANAGEMENT_MENU.contains(but.getText())) {
                but.setVisible(true);
            }

        }
        linkSalesOperations.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UILoader.loadView(anchorArea, SALES);
            }
        });
        linksetup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UILoader.loadView(anchorArea, SETUP);
            }
        });
        linkStockOperation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UILoader.loadView(anchorArea, STOCK);
            }
        });
        linkExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UILoader.loadView(anchorArea, EXPENSE);
            }
        });
        report.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UILoader.loadView(anchorArea, REPORTS);
            }
        });
    }

}
