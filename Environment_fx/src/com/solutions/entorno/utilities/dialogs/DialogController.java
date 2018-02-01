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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author RESEARCH2
 */
public class DialogController implements Initializable {

    @FXML
    private ImageView ivIcon;
    @FXML
    private Label lblMessage;
    @FXML
    private ImageView ivClose;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ivClose.setOnMouseClicked((MouseEvent event) -> {

        });
    }

    public ImageView getImageView() {
        return ivClose;
    }

    private void setNotificationIcon(Image icon) {
        ivIcon.setImage(icon);
    }

    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }

    public void setNotificationType(NotificationType nType) {

        URL imageLocation = null;
        Color titleColor = null;

        switch (nType) {

            case INFORMATION:
                imageLocation = getClass().getResource("/com/solutions/entorno/utilities/resources/dialog/success.png");
                titleColor = Color.YELLOW;
                break;

            case NOTICE:
                imageLocation = getClass().getResource("/com/solutions/entorno/utilities/resources/dialog/notice.png");
                titleColor = Color.GRAY;
                break;

            case SUCCESS:
                imageLocation = getClass().getResource("/com/solutions/entorno/utilities/resources/dialog/success.png");
                titleColor = Color.GREEN;
                break;

            case WARNING:
                imageLocation = getClass().getResource("/com/solutions/entorno/utilities/resources/dialog/warning.png");
                titleColor = Color.BLUE;
                break;

            case ERROR:
                imageLocation = getClass().getResource("/com/solutions/entorno/utilities/resources/dialog/error.png");
                titleColor = Color.RED;
                break;

            case CUSTOM:
                return;
        }
        setNotificationIcon(new Image(imageLocation.toString()));
        //setTitleColor(titleColor);

    }
}
