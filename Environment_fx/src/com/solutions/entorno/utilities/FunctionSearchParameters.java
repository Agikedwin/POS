/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

/**
 *
 * @author MARK
 */
public class FunctionSearchParameters {
    public static final JTextField jtext = new JTextField(30);
  static String text;
   Image searchImg= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/solutions/main/utilities/icons/search32.png"));

     public static JDialog showSearchDialogXY(JTable jtable,JComponent relative,int x_coordinate,int y_coordinate,JFrame parent,int WIDTH,int HEIGHT){
    JPanel jpanels = new JPanel();
   
        JPanel jpanelsearch = new JPanel();
        jpanelsearch.setLayout(new FlowLayout(0, 7, 0));
        
        jpanelsearch.add(jtext);
       JPanel jpaneltable = new JPanel();
        jpaneltable.setLayout(new BorderLayout());
        
        JTableHeader header = jtable.getTableHeader();
        jpaneltable.add(header,BorderLayout.NORTH);
       jpaneltable.add(jtable,BorderLayout.CENTER);
       jpanels.setLayout(new BorderLayout());
       
       JPanel searclose = new JPanel(new BorderLayout());
//       searclose.add(jpanelclose,BorderLayout.NORTH);
       searclose.add(jpanelsearch,BorderLayout.CENTER);
       
       jpanels.add(searclose,BorderLayout.NORTH);
        jpanels.add(jpaneltable,BorderLayout.CENTER);
        
       final  JDialog jinf = new JDialog(parent, "Search");
       // jinf.setUndecorated(true);
         x_coordinate = x_coordinate+ relative.getLocation().x;
         y_coordinate = y_coordinate+ relative.getLocation().y;
        jinf.setLocation(x_coordinate,y_coordinate);
            jinf.setSize(WIDTH,HEIGHT);
        jinf.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
       jpanels.setVisible(true);
       jinf.add(jpanels);
      jinf.setIconImage(new FunctionSearchParameters().searchImg);
    return jinf;
    }
}
