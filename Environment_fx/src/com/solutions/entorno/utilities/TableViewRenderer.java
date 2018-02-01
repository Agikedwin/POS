/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.entorno.utilities;

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

/**
 *
 * @author dell
 */
public class TableViewRenderer {
    
    private ArrayList<TableColumn> tableColumns = new ArrayList<>();
    private TableView<Object> tableView = new TableView<>();
    private ArrayList<Object> model = new ArrayList<>();
    
    public TableViewRenderer(String[] headers, ArrayList<Object> myModel, String[] property) {
        this.model = myModel;
        ObservableList<Object> myPersons = FXCollections.observableArrayList();
        for (String header : headers) {
            tableColumns.add(new TableColumn<>(header));
            
        }
         for (int i = 0; i < tableColumns.size(); i++) {             
            tableColumns.get(i).setCellValueFactory(new PropertyValueFactory(property[i]));
            
//            if (i == tableColumns.size() - 1) {
//                tableColumns.get(i).setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
//                    @Override
//                    public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
//                        return new ButtonCell(tableView);
//                    }
//                });
//                
//            }
            
            tableView.getColumns().add(tableColumns.get(i));
            //tableView.getColumns().get(i).setMinWidth();
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
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);
        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
//            cellButtonDelete.setOnAction((ActionEvent t) -> {
//                int row = getTableRow().getIndex();
//                tableView.getSelectionModel().select(row);
//                Object pm = tableView.getSelectionModel().getSelectedItem();                
//                Alert alert = new Alert(AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation Delete");
//                alert.setHeaderText("Do you want to Delete");
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.get() == ButtonType.OK) {
//                   // pm.;
//                } else {
//                   // ... user chose CANCEL or closed the dialog
//                }
//            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {             
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                 Object pm = tableView.getSelectionModel().getSelectedItem();
                // md.showDialog(tableView.getScene().getWindow(), pm);
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

//    private TableView biuldTable(Class<T> model){
//        TableView<model> tv = new TableView<model>();
//        return tv;
//    }
}
