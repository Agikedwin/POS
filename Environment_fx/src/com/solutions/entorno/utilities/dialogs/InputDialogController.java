/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities.dialogs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author RESEARCH2
 */
public class InputDialogController implements Initializable {

    @FXML
    private ImageView ivIcon;
    @FXML
    private Label lblMessage;
    @FXML
    private ImageView ivClose;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Button getBtnOk() {
        return btnOk;
    }

    public ImageView getIvClose() {
        return ivClose;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public String getValue() {
        return txtInput.getText();
    }
public void setInitialValue(String value){
    txtInput.setText(value);
}
    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }

}
