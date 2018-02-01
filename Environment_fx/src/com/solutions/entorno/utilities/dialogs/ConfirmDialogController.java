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
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author RESEARCH2
 */
public class ConfirmDialogController implements Initializable {

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
   

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    public ImageView getIvClose() {
        return ivClose;
    }
    
    public Button getBtnOk() {
        return btnOk;
    }
    
    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }
    
}
