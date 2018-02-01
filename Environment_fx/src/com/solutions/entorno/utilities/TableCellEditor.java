/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author shaddie
 */
public class TableCellEditor extends DefaultCellEditor{

    public TableCellEditor() {
        super(new JTextField());
    getComponent().addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent evt){
         char c= evt.getKeyChar();
        if(!Character.isDigit(c)&& !Character.isWhitespace(c) )
            {
          evt.consume();
        }
      }
    });     
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column){
    JTextField com=(JTextField)super.getTableCellEditorComponent(table,value,isSelected,row,column);
    com.setText((String)value);
    return com;
  }
}
