/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import static com.solutions.entorno.utilities.SystemUIsettings.SYSTEM_UI_TABLE;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author shaddie
 */
public class SystemColorSettings extends javax.swing.JInternalFrame {

    /**
     * Creates new form SystemColorSettings
     */
    public SystemColorSettings() {
              initComponents();
       // setLocationRelativeTo(null);
        getContentPane().setBackground(SystemVariables.THEME_COLOR);
        init();
    }
   private void init(){
         try {
            statement.executeQuery("SELECT * FROM "+SYSTEM_UI_TABLE+"");
        } catch (SQLException e) {
            String sql="CREATE TABLE "+SYSTEM_UI_TABLE+"("
                        + "name varchar(100),"
                        + "type varchar(100),"
                        + "class text primary key,"
                        + "status INT)";
            try {
               statement.executeUpdate(sql);              
            } catch (SQLException ex) {
                System.out.println("Could not create "+SYSTEM_UI_TABLE+" table");
            }
        }
          try {
             String query="SELECT count(*) FROM "+SYSTEM_UI_TABLE+" WHERE type='color'";
           resultSet = statement.executeQuery(query);
           resultSet.next();
           int records = resultSet.getInt(1);
             if(records<1){
        String insert = "INSERT INTO "+SYSTEM_UI_TABLE+" VALUES" + " ('Default','color','204,204,255',1),"
                + "('User Defined','color','',0)";
     statement.executeUpdate(insert);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
 }
             
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorPallete = new javax.swing.JColorChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("System Color");

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Default");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(colorPallete, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(colorPallete, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  SetCurrentBackground();
  String color =colorPallete.getColor().toString();
   color =   color.replaceAll("java.awt.Color", "");
       color =   color.replaceAll("=", "");
       color =   color.replaceAll("r", "");
       color =   color.replaceAll("g", "");
       color =   color.replaceAll("b", "");
         color =   color.replace("[", "");
          color =   color.replace("]", "");
        SystemVariables.THEME_COLOR=colorPallete.getColor();
  try {
            statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 0  WHERE type='color' ");
            statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 1, class='"+color+"' WHERE name ='User Defined' and type='color' ");
            JOptionPane.showMessageDialog(null, "Successfully changed Background Color. Some of the Changes will take effect the next time you start the system", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
           statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 0 WHERE type='color' ");
            statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 1 WHERE name ='Default' and type='color' ");
            JOptionPane.showMessageDialog(null, "Successfully changed Background Color. Some of the Changes will take effect the next time you start the system", "Success", JOptionPane.INFORMATION_MESSAGE);
       SystemVariables.THEME_COLOR=new Color(204,204,255);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed
public int[] SetCurrentBackground(){
        String color = null;
    try {
            resultSet = statement.executeQuery("SELECT class FROM "+SYSTEM_UI_TABLE+" WHERE type='color' and status = 1");
            resultSet.next();            
            color=(resultSet.getString("class"));
        } catch (Exception e) {
            color="204,204,255";
        }
    int colorcodes[]= new int[3];
    String colors[]=color.split(",");
    colorcodes[0]= Integer.valueOf(colors[0]);
     colorcodes[1]= Integer.valueOf(colors[1]);
      colorcodes[2]= Integer.valueOf(colors[2]);
            return colorcodes;
    }
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser colorPallete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
