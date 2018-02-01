/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import static com.solutions.entorno.utilities.SystemFunctions.openfile;
import static com.solutions.entorno.utilities.SystemVariables.CONFIG_FOLDER;
import static com.solutions.entorno.utilities.SystemVariables.INSTITUTION_LOGO;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jfree.chart.JFreeChart;
/**
 *
 * @author shaddie
 */
public class ReportGeneratorJtables {

    public static PdfPTable reportHeader(float[] headerWidth, int index){
       PdfPTable table = new PdfPTable(headerWidth);
        try {
          
            if (index==1) {
                table.setWidthPercentage(100.0F);
            } else {
                table.setWidthPercentage(75.0F);
            }
            PdfPCell cell;
            
            Image img;
            
         
          
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER); 
            cell.setPaddingLeft(15);
            cell.setRowspan(5);
              if(INSTITUTION_LOGO!=null){
                 img = Image.getInstance(INSTITUTION_LOGO,null);
            img.scaleAbsolute(150, 200);
             cell.addElement(img);
            }
           
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_NAME, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_ADDRESS, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_PHONE, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_EMAIL, FontFactory.getFont("Helvetica", 12.0F,Font.BOLD))));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_MOTTO, FontFactory.getFont("Helvetica", 12.0F,Font.BOLDITALIC))));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(15);
            table.addCell(cell);
            
           
        } catch (BadElementException ex) {
            Logger.getLogger(ReportGeneratorJtables.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportGeneratorJtables.class.getName()).log(Level.SEVERE, null, ex);
        }
         return table;
    }
         public static void PdfFileWriter(JTable table_to_export,String path,int index,String title){
            try{
      String FILE = CONFIG_FOLDER+"/"+path;
     
      Rectangle rect;
      if (index==1) {
        rect = new Rectangle(700.0F, 1024.0F);
      } else {
        rect = new Rectangle(1024.0F, 700.0F);
      }
      Document document = new Document();
      document.setPageSize(rect);
     PdfWriter writer=   PdfWriter.getInstance(document, new FileOutputStream(FILE));
       writer.setPageEvent(new WaterMarkGenerator());
      document.open();
      int columns = table_to_export.getColumnCount();
       float[] colsWidth = new float[columns];
       TableColumnModel model = table_to_export.getColumnModel();
       
        for (int i = 0; i < columns; i++) {
            colsWidth[i] = model.getColumn(i).getWidth();
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
  
//   Image img =Image.getInstance(INSTITUTION_LOGO,null);
//         img.scaleAbsolute(150, 200);
         
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER); 
       cell.setPaddingLeft(15);
        cell.setRowspan(5);        
//       cell.addElement(img);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_NAME, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
         cell.setPaddingLeft(15);
        table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_ADDRESS, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
    cell.setPaddingLeft(15);
     table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_PHONE, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
             cell.setPaddingLeft(15);
             table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_EMAIL, FontFactory.getFont("Helvetica", 12.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
        cell.setPaddingLeft(15);
           table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_MOTTO, FontFactory.getFont("Helvetica", 12.0F,Font.BOLDITALIC))));
        cell.setBorder(Rectangle.NO_BORDER); 
            cell.setPaddingLeft(15);
           table.addCell(cell);
        
           document.add(table);
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
      table.addCell(new Phrase(table_to_export.getColumnName(i), FontFactory.getFont("Helvetica", 12.0F,Font.BOLD)));
    }
    for (int i = 0; i < table_to_export.getRowCount(); i++) {
      for (int j = 0; j < columns; j++) {
        try
        {
          table.addCell(new Phrase((String)table_to_export.getValueAt(i, j), FontFactory.getFont("Helvetica", 11.0F)));
        }
        catch (Exception ex) {}
      }
    }
      document.add(table);
      document.close();
      openfile(FILE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    }
            
  public static void PdfFileWriterCharts(JTable table_to_export,String path,int index,String title,JFreeChart charts[],int width,int height){
            try{
      String FILE = CONFIG_FOLDER+"/"+path;
     
      Rectangle rect;
      if (index==0) {
        rect = new Rectangle(700.0F, 1024.0F);
      } else {
        rect = new Rectangle(1024.0F, 700.0F);
      }
      Document document = new Document();
      document.setPageSize(rect);
     PdfWriter writer=   PdfWriter.getInstance(document, new FileOutputStream(FILE));
       writer.setPageEvent(new WaterMarkGenerator());
      document.open();
      
      int columns = table_to_export.getColumnCount();
       float[] colsWidth = new float[columns];
       TableColumnModel model = table_to_export.getColumnModel();
       
        for (int i = 0; i < columns; i++) {
            colsWidth[i] = model.getColumn(i).getWidth();
    }
        float[] headerWidth = new float[2];
        headerWidth[0] = 20.0F;
        headerWidth[1] = 60.F;
    PdfPTable table = new PdfPTable(headerWidth);
    table.setWidthPercentage(100.0F);
   PdfPCell cell;
  
//   Image img =Image.getInstance(INSTITUTION_LOGO,null);
//         img.scaleAbsolute(150, 200);
         
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER); 
       cell.setPaddingLeft(10);
        cell.setRowspan(5);        
//       cell.addElement(img);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_NAME, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
         cell.setPaddingLeft(15);
        table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_ADDRESS, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
    cell.setPaddingLeft(15);
     table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_PHONE, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
             cell.setPaddingLeft(15);
             table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_EMAIL, FontFactory.getFont("Helvetica", 12.0F,Font.BOLD))));
        cell.setBorder(Rectangle.NO_BORDER); 
        cell.setPaddingLeft(15);
           table.addCell(cell);
        
         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_MOTTO, FontFactory.getFont("Helvetica", 12.0F,Font.BOLDITALIC))));
        cell.setBorder(Rectangle.NO_BORDER); 
            cell.setPaddingLeft(15);
           table.addCell(cell);
        
           document.add(table);
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
    table.setWidthPercentage(100.0F);
        
    table.setHeaderRows(1);
    for (int i = 0; i < columns; i++) {
      table.addCell(new Phrase(table_to_export.getColumnName(i), FontFactory.getFont("Helvetica", 12.0F,Font.BOLD)));
    }
    for (int i = 0; i < table_to_export.getRowCount(); i++) {
      for (int j = 0; j < columns; j++) {
        try
        {
          table.addCell(new Phrase((String)table_to_export.getValueAt(i, j), FontFactory.getFont("Helvetica", 11.0F)));
        }
        catch (Exception ex) {}
      }
    }
      document.add(table);
      document.newPage();
      for(int iterate = 0;iterate<charts.length;iterate++){
     
       
        if(iterate==0){
             PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics1d = template.createGraphics(width, height,
                new DefaultFontMapper());
       Rectangle2D rectangle1d = new Rectangle2D.Double(0, -0, width,
                height);
        charts[iterate].draw(graphics1d, rectangle1d);
        graphics1d.dispose();
        contentByte.addTemplate(template, 50, height+50);
      }
        else{
             PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template1 = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template1.createGraphics(width, height,
                new DefaultFontMapper());
          Rectangle2D  rectangle2d = new Rectangle2D.Double(0,0, width,
                height);
           charts[iterate].draw(graphics2d, rectangle2d);
        graphics2d.dispose();
        contentByte.addTemplate(template1, 50, 0);
        }
         
       
      }
      
      document.close();
      openfile(FILE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    }
            
    public static  void excel_writer(JTable table_to_export,String path,String title){
      String FILE = CONFIG_FOLDER+"/"+path;
        try
    {
      TableModel model = table_to_export.getModel();
   File file= new File (FILE);
      FileWriter excel = new FileWriter(file);
    //  Image img =Image.getInstance(SystemVariables.INSTITUTION_LOGO,null);
     
      excel.write("\t\t\t"+SystemVariables.INSTITUTION_NAME+"\n");
      excel.write("\t\t\t"+SystemVariables.INSTITUTION_ADDRESS+"\n");
        excel.write("\t\t\t"+SystemVariables.INSTITUTION_PHONE+"\n");
        excel.write("\t\t\t"+SystemVariables.INSTITUTION_EMAIL+"\n");
     excel.write("\t\t\t"+SystemVariables.INSTITUTION_MOTTO+"\n");
      excel.write("\t\t\t"+title);
      excel.write("\n\n");
      for (int i = 0; i < model.getColumnCount(); i++) {
        excel.write(model.getColumnName(i) + "\t");
      }
      excel.write("\n");
      for (int i = 0; i < model.getRowCount(); i++)
      {
        for (int j = 0; j < model.getColumnCount(); j++) {
          try
          {
            excel.write(model.getValueAt(i, j).toString() + "\t");
          }
          catch (Exception ex)
          {
            excel.write(model.getValueAt(i, j) + "\t");
          }
        }
        excel.write("\n");
      }
      excel.close();
     openfile(FILE);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }  
} 
    //     public static void PdfFileWriterResize(JTable table_to_export,String path,int index,String title,String colWidth[],float defa,String SEP){
//            try{
//      String FILE = CONFIG_FOLDER+"/"+path;
//     
//      Rectangle rect;
//      if (index==1) {
//        rect = new Rectangle(700.0F, 1024.0F);
//      } else {
//        rect = new Rectangle(1024.0F, 700.0F);
//      }
//      Document document = new Document();
////      if (index==1) {
////       document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
////      } else {
////       document = new Document(PageSize.A4, 10f, 10f, 10f, 10f);
////      }
//       
//      document.setPageSize(rect);
//      
//     PdfWriter writer=   PdfWriter.getInstance(document, new FileOutputStream(FILE));
//       writer.setPageEvent(new WaterMarkGenerator());
//      document.open();
//      int columns = table_to_export.getColumnCount();
//       float[] colsWidth = new float[columns];
//       
//       ArrayList<Float> colwidth = new ArrayList();
//       ArrayList<Integer> colPos = new ArrayList();
//       for(String colvals : colWidth ){
//           String colval[] = colvals.split(SEP);
//           colPos.add(Integer.valueOf(colval[0]));
//           colwidth.add(Float.valueOf(colval[1]));
//       }
//               
//    for (int i = 0; i < columns; i++) {
//         if(colPos.contains(i))
//             colsWidth[i] = colwidth.get(colPos.indexOf(i));
//        else
//            colsWidth[i] = defa;
//    }
//    PdfPTable table = new PdfPTable(columns);
//    table.setWidthPercentage(100.0F);
//   PdfPCell cell;
//   Image img =Image.getInstance(SystemVariables.INSTITUTION_LOGO,null);
//   
//         cell = new PdfPCell(new Phrase());
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(60);
//        table.addCell(cell);
//        
//        table = new PdfPTable(colsWidth);
//        table.setWidthPercentage(100.0F);
//       cell = new PdfPCell();
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(350);
//        cell.addElement( new Chunk(img,20,-20));
//        table.addCell(cell);
//         document.add(new Paragraph());
//        cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_NAME, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(150);
//        table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_ADDRESS, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(150);
//        table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_PHONE, FontFactory.getFont("Helvetica", 14.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(150);
//        table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_EMAIL, FontFactory.getFont("Helvetica", 12.0F,Font.BOLD))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(150);
//        table.addCell(cell);
//        
//         cell = new PdfPCell(new Phrase(new Phrase(SystemVariables.INSTITUTION_MOTTO, FontFactory.getFont("Helvetica", 12.0F,Font.BOLDITALIC))));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(150);
//        table.addCell(cell);
//        document.add(table);
//        document.add(Chunk.NEWLINE);
//        
//         table = new PdfPTable(colsWidth);
//        
//         cell = new PdfPCell(new Phrase(title, FontFactory.getFont("Helvetica", 13.5F,Font.UNDERLINE)));
//        cell.setBorder(Rectangle.NO_BORDER); 
//        cell.setColspan(columns);
//        cell.setPaddingLeft(60);
//        table.addCell(cell);
//   
//           document.add(table);
//        document.add(Chunk.NEWLINE);
//        document.add(Chunk.NEWLINE);
//        table = new PdfPTable(colsWidth);
//   
//    table.setHeaderRows(1);
//    for (int i = 0; i < columns; i++) {
//      table.addCell(new Phrase(table_to_export.getColumnName(i), FontFactory.getFont("Helvetica", 12.0F,Font.BOLD)));
//    }
//    for (int i = 0; i < table_to_export.getRowCount(); i++) {
//      for (int j = 0; j < columns; j++) {
//        try
//        {
//          table.addCell(new Phrase((String)table_to_export.getValueAt(i, j), FontFactory.getFont("Helvetica", 11.0F)));
//        }
//        catch (Exception ex) {}
//      }
//    }
//      document.add(table);
//      document.close();
//      openfile(FILE);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//    }
// 
}
