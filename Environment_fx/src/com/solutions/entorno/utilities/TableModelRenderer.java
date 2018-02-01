package com.solutions.entorno.utilities;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author shaddie
 */
public class TableModelRenderer{
    public  TableModelRenderer(){
        
    }
    public static  DefaultTableModel getTableRenderer (String [] cols,int rows){
        return new DefaultTableModel(cols,rows){
           @Override
           public boolean isCellEditable(int row,int col){
               return false;
           }     
           };
    }    
    public static  DefaultTableModel getTableRenderer (String [] cols,int rows,int editable){
        final int edit=editable;
        return new DefaultTableModel(cols,rows){
           @Override
           public boolean isCellEditable(int row,int col){
              if(col==edit)
                  return true;
              else
              return false;
           }     
           };
    }
    public static void resizeColumns(JTable table,int defaultValue, String others[],String sep){
      TableColumn column;
      ArrayList<Integer> col = new ArrayList();
      ArrayList<Integer> width = new ArrayList();
      for(String other : others){
          String value[] = other.split(sep);
          col.add(Integer.parseInt(value[0]));
          width.add(Integer.parseInt(value[1]));
      }
     for (int i = 0; i < table.getColumnCount(); i++) {
    column = table.getColumnModel().getColumn(i);
    if (col.contains(i)) {
        column.setPreferredWidth(width.get(col.indexOf(i))); //third column is bigger
    } else {
        column.setPreferredWidth(defaultValue);
    }
}
    }
}
