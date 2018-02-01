package com.solutions.pos.models.reports.TestCharts;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

 
public class TestPieChart extends Application {
  static double  sp=0;
  static double bp=0;
  static double qnty=0;
public  TestPieChart(){
    readTodaytSales(); 
}
    
    @Override public void start(Stage stage) {
        
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);
        
 
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Buing Price",sp),
                new PieChart.Data("Selling Price", bp),
                new PieChart.Data("Quantity", qnty),
                new PieChart.Data("Sp", bp),
                new PieChart.Data("pb",sp));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Sales");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
    
     public static void readTodaytSales() {
       
       
             
                bp =10;
                sp=30;
                qnty=40;
               
          
        
    }
 
    public static void main(String[] args) {
        launch(args);
          
    }
}