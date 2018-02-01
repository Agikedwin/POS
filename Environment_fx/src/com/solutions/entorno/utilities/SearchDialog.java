/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author dell
 */
public class SearchDialog implements Initializable{
    @FXML
    private BorderPane borderWrapper;
    @FXML
    private TextField searchKey;
    
    private TableView table;
    
private Window parent;

private TableView tbview;
    public  SearchDialog(Window parent, TableView tbview){
       this.table =  tbview;
       this.parent = parent;
       initialize(null, null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/Search.fxml"));
            Region root = (Region) view.load();
            Stage utility = new Stage();
            utility.setTitle("Search");
            utility.initModality(Modality.WINDOW_MODAL);
            utility.initOwner(parent);
            Scene scene = new Scene(root);
            utility.setScene(scene);
            utility.setResizable(true);
            table = tbview;
//            initialize(null, null);
            utility.show();
            borderWrapper.setCenter(null);
            borderWrapper.setCenter(table);
        } catch (IOException ex) {
            Logger.getLogger(SearchDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void showDialog(Window parent, TableView tbview) {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("/com/solutions/entorno/utilities/Search.fxml"));
            Region root = (Region) view.load();
            Stage utility = new Stage();
            utility.setTitle("Search");
            utility.initModality(Modality.WINDOW_MODAL);
            utility.initOwner(parent);
            Scene scene = new Scene(root);
            utility.setScene(scene);
            utility.setResizable(true);
           table = tbview;
//            initialize(null, null);
            utility.show();
      
            
            
        } catch (IOException ioe) {
            
        }
    }

}
