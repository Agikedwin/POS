/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author shaddie
 */
public class TableCellRenderer extends DefaultTableCellRenderer{
    int maxScore = 0;
    
    public TableCellRenderer(int maxScore){
        this.maxScore = maxScore;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    String no = (String)table.getValueAt(row, column);
      try
      {
        int score = Integer.parseInt(no);
        if (score > maxScore) {
          cellComponent.setBackground(Color.RED);
        }
      }
      catch (Exception e) {}
    return cellComponent;
  }
}
