/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author shaddie
 */
public class ImageResizerClass {
   public ImageResizerClass(){
    
   } 
 public static void getInstance(String inputpath, String outputpath, int width, int height){
     try {
         File in = new File(inputpath);
         BufferedImage inImg = ImageIO.read(in);
         BufferedImage outImg = new BufferedImage(width,height,inImg.getType());
         Graphics2D graphics = outImg.createGraphics();
         graphics.drawImage(inImg, 0, 0, width, height, null);
         graphics.dispose();
         String format = outputpath.substring(outputpath.lastIndexOf(".")+1);
         ImageIO.write(outImg, format, new File(outputpath));
     } catch (IOException ex) {
         ex.printStackTrace();
           }
 }
 public static Image getImage(InputStream in){
     Image img= null;
     try {
          img= ImageIO.read(in);
       } catch (IOException ex) {
           ex.printStackTrace();
       }
     return img;
 }
 
 }
