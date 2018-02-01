/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.models.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.solutions.entorno.utilities.ReportGeneratorJtables;
import static com.solutions.entorno.utilities.SystemFunctions.openfile;
import com.solutions.entorno.utilities.SystemVariables;
import com.solutions.entorno.utilities.WaterMarkGenerator;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author shaddie
 */
public class PrintDailySKUSalesReport {
    
    public static void generateReport(TableView table_to_export ,String path,String title, String totalSales,String totalVat){
       String FILE = SystemVariables.REPORT_FOLDER+path;
       String columnNames[] = { "SKU Name", "Quantity","Price per Unit", "Sub-Total", "VAT",  "Date"};

      int index = 2;
      try{
      Rectangle rect;
      if (index==1) {
        rect = new Rectangle(700.0F, 1024.0F);
      } else {
        rect = new Rectangle(1024.0F, 700.0F);
      }
      Document document = new Document();
      document.setPageSize(rect);
     PdfWriter writer=   PdfWriter.getInstance(document, new FileOutputStream(FILE));
       writer.setPageEvent((PdfPageEvent) new WaterMarkGenerator());
      document.open();
      int columns = columnNames.length;
          
       float[] colsWidth = new float[columns];     
        for (int i = 0; i < columns; i++) {
              if(i==0){
                colsWidth[i] = 8.0F;
            }
            else{
            colsWidth[i] = 3.0F;
            }
                    
    }

     float[] headerWidth = new float[2];
        headerWidth[0] = 20.0F;
        headerWidth[1] = 60.F;
    PdfPTable table = new PdfPTable(headerWidth);
     if (index==1) {
       table.setWidthPercentage(100.0F);
      } else {
       table.setWidthPercentage(75.0F);
      }
    
    
   PdfPCell cell;
  
           document.add(ReportGeneratorJtables.reportHeader(headerWidth, index));
        document.add(new Phrase("\n"));
        
         table = new PdfPTable(colsWidth);
        
         cell = new PdfPCell(new Phrase(title, FontFactory.getFont("Helvetica", 13.5F,Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER); 
        cell.setColspan(columns);
        cell.setPaddingLeft(60);
        table.addCell(cell);
   
           document.add(table);
            document.add(new Phrase("\n"));
        
         table = new PdfPTable(colsWidth);
        table = new PdfPTable(colsWidth);
   
    table.setHeaderRows(1);
    for (int i = 0; i < columns; i++) {
     table.addCell(new Phrase(columnNames[i], FontFactory.getFont("Helvetica", 12.0F,Font.BOLD)));
    }
          ObservableList<Object> list = table_to_export.getItems();
          DailySkuSalesReport record ;
    for (Object dailySales : list) {
    record = (DailySkuSalesReport)dailySales;

    
 //table.addCell(new Phrase(""+record.getSkuId(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(record.getSkuname(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(""+record.getQuantity(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(""+record.getPriceperunit(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(""+record.getTotal_amount(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(""+record.getTotal_vat(), FontFactory.getFont("Helvetica", 11.0F)));
 table.addCell(new Phrase(record.getRegdate(), FontFactory.getFont("Helvetica", 11.0F)));
// table.addCell(new Phrase(record.get, FontFactory.getFont("Helvetica", 11.0F)));
    }
      document.add(table);
       document.add(new Phrase("\n"));
      table = new PdfPTable(headerWidth);
      
       cell = new PdfPCell(new Phrase("TOTAL SALES:", FontFactory.getFont("Helvetica", 13.5F,Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER); 
        //cell.setColspan(columns);
        cell.setPaddingLeft(60);
        table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(totalSales, FontFactory.getFont("Helvetica", 13.5F,Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER); 
       // cell.setColspan(columns);
        cell.setPaddingLeft(60);
        table.addCell(cell);
         cell = new PdfPCell(new Phrase("TOTAL VAT:", FontFactory.getFont("Helvetica", 13.5F,Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER); 
        //cell.setColspan(columns);
        cell.setPaddingLeft(60);
        table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(totalVat, FontFactory.getFont("Helvetica", 13.5F,Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER); 
      //  cell.setColspan(columns);
        cell.setPaddingLeft(60);
        table.addCell(cell); 
        
      document.add(table);
      document.close();
      openfile(FILE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }  
    }
}
