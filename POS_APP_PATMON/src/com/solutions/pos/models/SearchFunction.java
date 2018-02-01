/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author dell
 */
public class SearchFunction {
    public void Search(ObservableList masterData,Object myModel,TableView tableView, TextField searchField,String para1,String para2){
      // this.modelz = model;
         FilteredList<Object> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Object -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
//                String lowerCaseFilter = newValue.toLowerCase();
//                if (para1.toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else if (para2.toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                }
                return false;
            });
        });
        SortedList<Object> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);   
        }
    
}
