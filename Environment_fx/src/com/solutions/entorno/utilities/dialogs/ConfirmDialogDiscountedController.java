/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities.dialogs;

import static com.solutions.entorno.utilities.SystemVariables.DISPLAY_MESSAGE;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author shaddie
 */
public class ConfirmDialogDiscountedController implements Initializable {
    @FXML
    private ImageView ivIcon;
    @FXML
    private Label lblMessage;
    @FXML
    private ImageView ivClose;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private Button discount;
 private String discountAmount;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          getDiscount().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               // System.out.println("btn clicked"); 
               discountAmount = DISPLAY_MESSAGE.showInputDialog(getDiscount().getParent().getScene().getWindow(), "Enter Discount For This for this sale.", "1");
              
            }
        });
    }    

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public ImageView getIvClose() {
        return ivClose;
    }
    
    public Button getBtnOk() {
        return btnOk;
    }

    public Button getDiscount() {
        return discount;
    }
    
    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }
    
    
}
