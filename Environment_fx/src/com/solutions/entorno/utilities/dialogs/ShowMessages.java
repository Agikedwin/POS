/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities.dialogs;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Karanja
 */
public class ShowMessages {

    int discountOrRemove = -1;

    public void loadAnchorPane(AnchorPane ap, String a) {
        try {
            AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource(a));
            ap.getChildren().setAll(p);

        } catch (IOException e) {

        }
    }

    public void showInfoDialog(Window parent, String msg, NotificationType type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/dialogs/Dialog.fxml"));
            Region root = (Region) fxmlLoader.load();
            DialogController uc = fxmlLoader.<DialogController>getController();
            Stage utilityStage = new Stage(StageStyle.UNDECORATED);
            uc.setMessage(msg);
            uc.setNotificationType(type);
            uc.getImageView().setOnMouseClicked((MouseEvent event) -> {
                utilityStage.close();
            });

            utilityStage.setScene(new Scene(root));
            utilityStage.initModality(Modality.APPLICATION_MODAL);
            utilityStage.initOwner(parent);
            utilityStage.centerOnScreen();
            utilityStage.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public String showInputDialog(Window parent, String msg, String defaultValue) {
        StringProperty input = new SimpleStringProperty();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/dialogs/InputDialog.fxml"));
            Region root = (Region) fxmlLoader.load();
            InputDialogController uc = fxmlLoader.<InputDialogController>getController();
            Stage utilityStage = new Stage(StageStyle.UNDECORATED);
            uc.setInitialValue(defaultValue);
            uc.setMessage(msg);
            uc.getBtnCancel().setOnAction((ActionEvent event) -> {
                utilityStage.close();
            });
            uc.getIvClose().setOnMouseClicked((MouseEvent event) -> {
                utilityStage.close();
            });
            uc.getBtnOk().setOnAction((ActionEvent event) -> {
                input.setValue(uc.getValue());
                utilityStage.close();
            });
            utilityStage.setScene(new Scene(root));
            utilityStage.initModality(Modality.APPLICATION_MODAL);
            utilityStage.initOwner(parent);
            utilityStage.centerOnScreen();
            utilityStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return input.get();
    }

    public boolean showConfirmDialog(Window parent, String msg) {
        BooleanProperty b = new SimpleBooleanProperty();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/dialogs/ConfirmDialog.fxml"));
            Region root = (Region) fxmlLoader.load();
            ConfirmDialogController uc = fxmlLoader.<ConfirmDialogController>getController();
            Stage utilityStage = new Stage(StageStyle.UNDECORATED);
            uc.setMessage(msg);
            uc.getBtnCancel().setOnAction((ActionEvent event) -> {
                utilityStage.close();
            });
            uc.getIvClose().setOnMouseClicked((MouseEvent event) -> {
                utilityStage.close();
            });
            uc.getBtnOk().setOnAction((ActionEvent event) -> {
                b.set(true);
                utilityStage.close();
            });
            utilityStage.setScene(new Scene(root));
            utilityStage.initModality(Modality.APPLICATION_MODAL);
            utilityStage.initOwner(parent);
            utilityStage.centerOnScreen();
            utilityStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return b.get();
    }

    public boolean showConfirmMe(Window w, String s) {

        return true;
    }

    public boolean showConfirmAdjust(Window parent, String msg) {
        BooleanProperty b = new SimpleBooleanProperty();
        b.set(false);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/dialogs/ConfirmDialogAdjust.fxml"));
            Region root = (Region) fxmlLoader.load();
            ConfirmDialogAdjustController uc = fxmlLoader.<ConfirmDialogAdjustController>getController();
            Stage utilityStage = new Stage(StageStyle.UNDECORATED);
//            uc.setMessage(msg);
            uc.getBtnCancel().setOnAction((ActionEvent event) -> {
                utilityStage.close();
            });
            uc.getIvClose().setOnMouseClicked((MouseEvent event) -> {
                utilityStage.close();
            });
            uc.getBtnOk().setOnAction((ActionEvent event) -> {

                b.set(true);
                System.out.println("DEBUG: ok status ss " + b.get());
                utilityStage.close();
            });
            utilityStage.setScene(new Scene(root));
            utilityStage.initModality(Modality.APPLICATION_MODAL);
            utilityStage.initOwner(parent);
            utilityStage.centerOnScreen();
            utilityStage.showAndWait();
        } catch (IOException ex) {
             System.out.println("Error: " + ex.getMessage());
        }
        return b.get();
    }

    public int showConfirmDialogDiscounted(Window parent, String msg) {
        discountOrRemove = -1;
        //  BooleanProperty b = new SimpleBooleanProperty();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/dialogs/ConfirmDialogDiscounted.fxml"));
            Region root = (Region) fxmlLoader.load();
            ConfirmDiscountController uc = fxmlLoader.<ConfirmDiscountController>getController();
            Stage utilityStage = new Stage(StageStyle.UNDECORATED);
            uc.setMessage(msg);
            uc.getBtnCancel().setOnAction((ActionEvent event) -> {
                discountOrRemove = -1;
                utilityStage.close();
            });
            uc.getIvClose().setOnMouseClicked((MouseEvent event) -> {
                discountOrRemove = -1;
                utilityStage.close();
            });

            uc.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    discountOrRemove = 1;
                    utilityStage.close();
                }
            });
            uc.getDiscount().setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    discountOrRemove = 2;
                    utilityStage.close();
                }
            });
            utilityStage.setScene(new Scene(root));
            utilityStage.initModality(Modality.APPLICATION_MODAL);
            utilityStage.initOwner(parent);
            utilityStage.centerOnScreen();
            utilityStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());

        }
        return discountOrRemove;
    }

    public double[] getNodeLocation(Node n) {
        double[] location = new double[2];
        Point2D nodeBounds = n.localToScene(0.0, 0.0);
        double posX = nodeBounds.getX() + n.getScene().getX() + n.getScene().getWindow().getX();
        double posY = nodeBounds.getY() + n.getScene().getY() + n.getScene().getWindow().getY();
        location[0] = posX;
        location[1] = posY;
        return location;
    }

}
