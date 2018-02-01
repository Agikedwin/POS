/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutions.pos.controllers.utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import static com.solutions.entorno.utilities.SystemFunctions.openfile;
import static com.solutions.entorno.utilities.SystemVariables.CONFIG_FOLDER;
import com.solutions.entorno.utilities.WaterMarkGenerator;
import java.io.FileOutputStream;
import javafx.scene.control.TableView;

/**
 *
 * @author shaddie
 */
public class FunctionGenerateQuotation {
    
    public void generateReport(TableView table_to_export,String path,int index,String title){
       String FILE = CONFIG_FOLDER+"/"+path;
      
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
      int columns = table_to_export.getColumns().size();
          
       float[] colsWidth = new float[columns];     
        for (int i = 0; i < columns; i++) {
            
            colsWidth[i] = 3.0F;
                    
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
//  
//   Image img =Image.getInstance(INSTITUTION_LOGO,null);
//         img.scaleAbsolute(150, 200);
//         
//        cell = new PdfPCell();
//        cell.setBorder(Rectangle.NO_BORDER); 
//       cell.setPaddingLeft(15);
//        cell.setRowspan(5);        
//       cell.addElement(img);
//        table.addCell(cell);
//        
//        cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_NAME, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//         cell.setPaddingLeft(15);
//        table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_ADDRESS, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//    cell.setPaddingLeft(15);
//     table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_PHONE, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//             cell.setPaddingLeft(15);
//             table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_EMAIL, FontFactory.getFont("Helvetica", 12.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setPaddingLeft(15);
//           table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_MOTTO, FontFactory.getFont("Helvetica", 12.0F,Font.BOLDITALIC))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//            cell.setPaddingLeft(15);
//           table.addCell(cell);
//        
//           document.add(table);
//        document.add(new Phrase("\n"));
//        
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
    //  table.addCell(new Phrase(table_to_export.getColumnName(i), FontFactory.getFont("Helvetica", 12.0F,Font.BOLD)));
    }
//    for (int i = 0; i < table_to_export.getRowCount(); i++) {
//      for (int j = 0; j < columns; j++) {
//        try
//        {
////          table.addCell(new Phrase((String)table_to_export.getValueAt(i, j), FontFactory.getFont("Helvetica", 11.0F)));
//        }
//        catch (Exception ex) {}
//      }
//    }
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
