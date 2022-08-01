/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import GUI.SelectFrame.SelectAvionJFrame;
import database.utilities.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author student
 */
public class AvionsJFrame extends javax.swing.JFrame {
    private AvionBean AB;
    /**
     * Creates new form AvionsJFrame
     */
    public void RefreshTable() throws SQLException
    {
        DefaultTableModel dtm = (DefaultTableModel)Tableau.getModel();
        dtm.setRowCount(0);
        int cpt=0;
        ResultSet rs = AB.Select();        
        while(rs.next())
        {
            Vector ligne = new Vector();
            int x = rs.getInt(1);
            ligne.add(String.valueOf(x));
            boolean Op = rs.getBoolean(2);
            ligne.add(Op);
            
            
            dtm.insertRow(dtm.getRowCount(), ligne);
            cpt++;
            System.out.println("Compteur = "+cpt);
        }
        if(cpt == 0)
        {
            System.out.println("Aucun résultat trouvé!! Fermeture de l'application");
            this.dispose();
        }
        CountAvionTF.setText(String.valueOf(cpt));
    }
    
    public void RefreshTable(ResultSet rs) throws SQLException
    {
        DefaultTableModel dtm = (DefaultTableModel)Tableau.getModel();
        dtm.setRowCount(0);
        int cpt=0;
        while(rs.next())
        {
            Vector ligne = new Vector();
            int x = rs.getInt(1);
            ligne.add(String.valueOf(x));
            boolean Op = rs.getBoolean(2);
            ligne.add(Op);
            
            
            dtm.insertRow(dtm.getRowCount(), ligne);
            cpt++;
            System.out.println("Compteur = "+cpt);
        }
        if(cpt == 0)
        {
            System.out.println("Aucun résultat trouvé!! Fermeture de l'application");
            this.dispose();
        }
        CountAvionTF.setText(String.valueOf(cpt));
    }
    
    public AvionsJFrame() throws ClassNotFoundException, SQLException {
        initComponents();
        this.setTitle("Avions");
        int cpt = 0;
        AB = new AvionBean();
        ResultSet rs = AB.SelectCount();
        while(rs.next())
            CountAvionTF.setText(String.valueOf(rs.getInt("total")));
        
        rs = AB.Select();        
        while(rs.next())
        {
            Vector ligne = new Vector();
            int x = rs.getInt(1);
            ligne.add(String.valueOf(x));
            boolean Op = rs.getBoolean(2);
            ligne.add(Op);
            
            DefaultTableModel dtm = (DefaultTableModel)Tableau.getModel();
            dtm.insertRow(dtm.getRowCount(), ligne);
            cpt++;
            System.out.println("Compteur = "+cpt);
        }
        if(cpt == 0)
        {
            System.out.println("Aucun résultat trouvé!! Fermeture de l'application");
            this.dispose();
        }
        rs = AB.SelectCount();
        System.out.println("SelectCount()");
        
        while(rs.next())
            CountAvionTF.setText(String.valueOf(rs.getInt("total")));
        this.show();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Tableau = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        CountAvionTF = new javax.swing.JTextField();
        SELECTBT = new javax.swing.JButton();
        UPDATEBT = new javax.swing.JButton();
        INSERTBT = new javax.swing.JButton();
        DELETEBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Tableau.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Opérationnel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tableau);

        jLabel1.setText("Nombre d'avions :");

        CountAvionTF.setEditable(false);
        CountAvionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CountAvionTFActionPerformed(evt);
            }
        });

        SELECTBT.setText("SELECT");
        SELECTBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SELECTBTActionPerformed(evt);
            }
        });

        UPDATEBT.setText("UPDATE");
        UPDATEBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UPDATEBTActionPerformed(evt);
            }
        });

        INSERTBT.setText("INSERT");
        INSERTBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INSERTBTActionPerformed(evt);
            }
        });

        DELETEBT.setText("DELETE");
        DELETEBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DELETEBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(CountAvionTF, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UPDATEBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SELECTBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(INSERTBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DELETEBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(CountAvionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SELECTBT)
                        .addGap(30, 30, 30)
                        .addComponent(INSERTBT)
                        .addGap(32, 32, 32)
                        .addComponent(UPDATEBT)
                        .addGap(34, 34, 34)
                        .addComponent(DELETEBT)
                        .addGap(91, 91, 91))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CountAvionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CountAvionTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CountAvionTFActionPerformed

    private void SELECTBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SELECTBTActionPerformed
        // TODO add your handling code here:
        SelectAvionJFrame SAF = new SelectAvionJFrame(AB,this, false);
        SAF.show();
    }//GEN-LAST:event_SELECTBTActionPerformed

    private void UPDATEBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UPDATEBTActionPerformed

        int Id = Integer.parseInt((String)Tableau.getValueAt(Tableau.getSelectedRow(), 0));
        boolean Op = (boolean)Tableau.getValueAt(Tableau.getSelectedRow(), 1);
        
        try {
            AB.Update(Id, false, false, 0, true, Op);
        } catch (SQLException ex) {
            Logger.getLogger(AvionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UPDATEBTActionPerformed

    private void INSERTBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INSERTBTActionPerformed
        // TODO add your handling code here:
        SelectAvionJFrame SAF = new SelectAvionJFrame(AB,this, true);
        SAF.show();
    }//GEN-LAST:event_INSERTBTActionPerformed

    private void DELETEBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DELETEBTActionPerformed
        // TODO add your handling code here:
        int Id = Integer.parseInt((String)Tableau.getValueAt(Tableau.getSelectedRow(), 0));
        
        try{
            ResultSet rs = AB.Select(false, Id, false,false);
            while(rs.next())
                rs.deleteRow();
            this.RefreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(AvionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_DELETEBTActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CountAvionTF;
    private javax.swing.JButton DELETEBT;
    private javax.swing.JButton INSERTBT;
    private javax.swing.JButton SELECTBT;
    private javax.swing.JTable Tableau;
    private javax.swing.JButton UPDATEBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
