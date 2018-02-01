/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author sonchaba
 */
public class MessagesUtil {

    /*
     0 NONE
     1 INFORMATION
     2 WARNING
     3 ERROR
     4 CONFIRMATION    
     */
    private static Alert alert = null;
    private static AlertType alertType = null;

    public static String displayInputDialog(String header, String body, int defaultValue) {
        TextInputDialog dialog = new TextInputDialog("" + defaultValue);
        dialog.setTitle(header);
        dialog.setHeaderText(header);
        dialog.setContentText(body);
        String input = null;
// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            input = result.get();
        }
        return input;
    }

    public static void displayMessage(String header, String body, int messageType, Exception exception) {
        switch (messageType) {
            case 0:
                alertType = AlertType.NONE;
                break;
            case 1:
                alertType = AlertType.INFORMATION;
                break;
            case 2:
                alertType = AlertType.WARNING;
                break;
            case 3:
                alertType = AlertType.ERROR;
                break;
            case 4:
                alertType = AlertType.CONFIRMATION;
                break;
        }
        alert = new Alert(alertType);
        if (exception == null) {
            alert.setTitle(header);
            alert.setHeaderText(header);
            alert.setContentText(body);
        } else if (exception != null) {
            alert.setTitle(header);
            alert.setHeaderText(header);
            alert.setContentText(body);
// Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
        }
        alert.showAndWait();
    }

    public static int displayAlert(String header, String body) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(header);
        alert.setContentText(body);
        int option = -1;
        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            option = 1;
        }
        return option;
    }
    public static int displayMessage(String header, String body) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setContentText(body);
        int option = -1;
        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            option = 1;
        }
        return option;
    }

}
