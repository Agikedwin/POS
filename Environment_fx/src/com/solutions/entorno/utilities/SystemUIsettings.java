/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.solutions.entorno.utilities;

import com.solutions.entorno.classes.MainClass;
import static com.solutions.entorno.utilities.SystemVariables.THEME_COLOR;
import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author shaddie
 */
public class SystemUIsettings extends javax.swing.JInternalFrame {

   public static final String SYSTEM_UI_TABLE = "System_environment";
   private ArrayList<String> classes = new ArrayList();
   private int  selected_row;
    public SystemUIsettings() {
        initComponents();
        //setLocationRelativeTo(null); 
        MainClass.getInstance();
        getContentPane().setBackground(THEME_COLOR);
        init();
       loadUI(); 
    }
    private void loadUI(){
       try {
           String query="SELECT count(*) FROM "+SYSTEM_UI_TABLE+" WHERE type='laf'";
           resultSet = statement.executeQuery(query);
           resultSet.next();
           int records = resultSet.getInt(1);
           String columns[] ={"LOOK AND FEEL NAME"}; 
           systemUI.setModel(TableModelRenderer.getTableRenderer(columns, records));
           query="SELECT * FROM "+SYSTEM_UI_TABLE+"  WHERE type='laf'";
           resultSet = statement.executeQuery(query);
           int i = 0;
           String classnames;
           while(resultSet.next()){
               systemUI.setValueAt(resultSet.getString("name"), i, 0);
               classnames = resultSet.getString("class");
               classes.add(classnames);
               i++;
           }
           } catch (SQLException ex) {
           Logger.getLogger(SystemUIsettings.class.getName()).log(Level.SEVERE, null, ex);
       }
        
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
             String query="SELECT count(*) FROM "+SYSTEM_UI_TABLE+" WHERE type='laf'";
           resultSet = statement.executeQuery(query);
           resultSet.next();
           int records = resultSet.getInt(1);
             if(records<1){
                   String insert = "INSERT INTO "+SYSTEM_UI_TABLE+" VALUES"
                       + " ('ACRYL','laf','com.jtattoo.plaf.acryl.AcrylLookAndFeel',0),"
                           + "('LUNA','laf','com.jtattoo.plaf.luna.LunaLookAndFeel',0),"
                       + "('MINT','laf','com.jtattoo.plaf.mint.MintLookAndFeel',0),"
                       + "('MCWIN','laf','com.jtattoo.plaf.mcwin.McWinLookAndFeel',0),"
                       + "('HIFI','laf','com.jtattoo.plaf.hifi.HiFiLookAndFeel',0),"
                       + "('GRAPHITE','laf','com.jtattoo.plaf.graphite.GraphiteLookAndFeel',0),"
                       + "('FAST','laf','com.jtattoo.plaf.fast.FastLookAndFeel',0),"
                       + "('BERNSTEIN','laf','com.jtattoo.plaf.bernstein.BernsteinLookAndFeel',0),"
                       + "('ALUMINIUM','laf','com.jtattoo.plaf.aluminium.AluminiumLookAndFeel',0),"
                       + "('AERO','laf','com.jtattoo.plaf.aero.AeroLookAndFeel',0),"
                       + "('NOIRE','laf','com.jtattoo.plaf.noire.NoireLookAndFeel',0),"
                       + "('SMART','laf','com.jtattoo.plaf.smart.SmartLookAndFeel',0),"
                       + "('TEXTURE','laf','com.jtattoo.plaf.texture.TextureLookAndFeel',0),"
                       + "('LIQUID','laf','com.birosoft.liquid.LiquidLookAndFeel',0),"
                       + "('QUAQUA','laf','ch.randelshofer.quaqua.QuaquaLookAndFeel',0),"
                       + "('Goodies Windows','laf','com.jgoodies.looks.windows.WindowsLookAndFeel',0),"
                       + "('Goodies Plastic','laf','com.jgoodies.looks.plastic.PlasticLookAndFeel',0),"
                       + "('Goodies Plastic 3D','laf','com.jgoodies.looks.plastic.Plastic3DLookAndFeel',0),"
                       + "('Goodies Plastic XP','laf','com.jgoodies.looks.plastic.PlasticXPLookAndFeel',0),"
                       + "('SKIN','laf','com.l2fprod.gui.plaf.skin.SkinLookAndFeel',0),"
                       + "('METAL','laf','javax.swing.plaf.metal.MetalLookAndFeel',0),"
                       + "('WINDOWS','laf','com.sun.java.swing.plaf.windows.WindowsLookAndFeel',0),"
                       + "('MOTIF','laf','com.sun.java.swing.plaf.motif.MotifLookAndFeel',0),"
                       + "('NIMBUS','laf','com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel',0),"
                       + "('SYNTHECTIC','laf','javax.swing.plaf.synth.SynthLookAndFeel',0),"
                       + "('DEFAULT','laf','javax.swing.plaf.nimbus.NimbusLookAndFeel',1)";
               statement.executeUpdate(insert);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        systemUI = new javax.swing.JTable();
        save = new javax.swing.JButton();

        setTitle("Look and Feel");

        systemUI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        systemUI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                systemUIMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(systemUI);

        save.setText("SAVE ");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void systemUIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_systemUIMouseClicked
           selected_row = systemUI.getSelectedRow();
    }//GEN-LAST:event_systemUIMouseClicked

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 0  WHERE type='laf' ");
            statement.executeUpdate("UPDATE "+SYSTEM_UI_TABLE+" set status = 1 WHERE class ='"+classes.get(selected_row)+"'  ");
            JOptionPane.showMessageDialog(null, "Successfully changed UI Look and Feel. Changes will take effect the next time you restart the system", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        } catch (Exception e) {
        }
  
    }//GEN-LAST:event_saveActionPerformed

    public String SetCurrentLaF(){
        String laf= null;
        try {
            resultSet = statement.executeQuery("SELECT class FROM "+SYSTEM_UI_TABLE+" WHERE type='laf' and status = 1");
            resultSet.next();            
            laf=(resultSet.getString("class"));
        } catch (Exception e) {
        }
        return laf;
    }
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton save;
    private javax.swing.JTable systemUI;
    // End of variables declaration//GEN-END:variables
}
