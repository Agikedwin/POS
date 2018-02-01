/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers.utilities;


import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author dell
 */
public class InternalTableViewRenderer {
    
    private ArrayList<TableColumn> tableColumns = new ArrayList<>();
    private TableView<Object> tableView = new TableView<>();
    private ArrayList<Object> model = new ArrayList<>();
    
    public InternalTableViewRenderer(String[] headers, ArrayList<Object> myModel, String[] property) {
        this.model = myModel;
        ObservableList<Object> myPersons = FXCollections.observableArrayList();
        for (String header : headers) {
            tableColumns.add(new TableColumn<>(header));
        }
        
        for (int i = 0; i < tableColumns.size(); i++) {
            tableColumns.get(i).setCellValueFactory(new PropertyValueFactory(property[i]));
            
            if (i == tableColumns.size() - 1) {
                tableColumns.get(i).setCellFactory(new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
                    @Override
                    public TableCell<Object, String> call(TableColumn<Object, String> p) {
                     return new    TableCell<Object, String>(){
                         @Override
                         public void updateItem(String item , boolean empty){
                            super.updateItem(item, empty);
                            if(!isEmpty()){
                                this.setTextFill(Color.BLUE);
                                setText(item);
                                setTextAlignment(TextAlignment.CENTER);
                            }
                         }
                     };
                    }
                });
                
            }
            tableView.getColumns().add(tableColumns.get(i));
        }
        model.stream().forEach((p) -> {
            myPersons.add(p);
        });
        tableView.setItems(myPersons);
    }
    public void delete(int rowId){
        
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Object, Boolean> {
        
        final Hyperlink cellButtonDelete = new Hyperlink("Delete");
        final Hyperlink cellButtonEdit = new Hyperlink("Edit");
        final HBox hb = new HBox(cellButtonEdit);
        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
            cellButtonEdit.setOnAction((ActionEvent event) -> {             
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                 Object obj = tableView.getSelectionModel().getSelectedItem();
                
            });
            
        }
        
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }
    }
    
    public TableView getTable() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tableView;
    }

}
