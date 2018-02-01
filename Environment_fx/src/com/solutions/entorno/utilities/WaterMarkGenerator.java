/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import static com.solutions.entorno.utilities.SystemFunctions.isStaleCopy;
import static com.solutions.entorno.utilities.SystemFunctions.registered;

/**
 *
 * @author shaddie
 */
public class WaterMarkGenerator  extends PdfPageEventHelper
  {
    Font FONT = new Font(Font.FontFamily.HELVETICA, 52.0F, 1, new GrayColor(0.75F));
    
  public  WaterMarkGenerator() {}
    
    public void onEndPage(PdfWriter writer, Document document)
    {
        if(!SystemFunctions.registered())
        {
     
        }
        if(isStaleCopy() || !registered()){
            ColumnText.showTextAligned(writer.getDirectContentUnder(), 1, new Phrase("TEST DOCUMENT NOT ORIGINAL.", this.FONT),
              297.5F, 421.0F, writer.getPageNumber() % 2 == 1 ? 45.0F : -45.0F);
       }
        else{
          ColumnText.showTextAligned(writer.getDirectContentUnder(), 1, new Phrase("O R I G I N A L", this.FONT),
              297.5F, 421.0F, writer.getPageNumber() % 2 == 1 ? 45.0F : -45.0F);   
        }
    }
  }
