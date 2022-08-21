/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import GUI.SelectFrame.SelectVolJFrame;
import database.utilities.VolsBean;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author student
 */
public class VolsJFrame extends javax.swing.JFrame {
    private VolsBean VB;
    /**
     * Creates new form AvionsJFrame
     */
    public void RefreshTable() throws SQLException
    {
        DefaultTableModel dtm = (DefaultTableModel)Tableau.getModel();
        dtm.setRowCount(0);
        int cpt=0;
        ResultSet rs = VB.Select();        
        while(rs.next())
        {
            Vector ligne = new Vector();
            int x = rs.getInt(1);
            ligne.add(String.valueOf(x));
            String Destination = rs.getString(2);
            ligne.add(Destination);
            Timestamp HD = new Timestamp(rs.getTimestamp(3).getTime());
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HD));
            Timestamp HAP = rs.getTimestamp(4);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAP));
            Timestamp HAE = rs.getTimestamp(5);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAE));
            int Avion = rs.getInt(6);
            ligne.add(String.valueOf(Avion));
            
            
            dtm.insertRow(dtm.getRowCount(), ligne);
            cpt++;
            System.out.println("Compteur = "+cpt);
        }
        if(cpt == 0)
        {
            System.out.println("Aucun résultat trouvé!! Fermeture de l'application");
            this.dispose();
        }
        CountAgentTF.setText(String.valueOf(cpt));
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
            String Destination = rs.getString(2);
            ligne.add(Destination);
            Timestamp HD = new Timestamp(rs.getTimestamp(3).getTime());
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HD));
            Timestamp HAP = rs.getTimestamp(4);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAP));
            Timestamp HAE = rs.getTimestamp(5);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAE));
            int Avion = rs.getInt(6);
            ligne.add(String.valueOf(Avion));
            
            
            dtm.insertRow(dtm.getRowCount(), ligne);
            cpt++;
            System.out.println("Compteur = "+cpt);
        }
        if(cpt == 0)
        {
            System.out.println("Aucun résultat trouvé!! Fermeture de l'application");
            this.dispose();
        }
        CountAgentTF.setText(String.valueOf(cpt));
    }
    
    public VolsJFrame() throws ClassNotFoundException, SQLException {
        initComponents();
        this.setTitle("Vols");
        int cpt = 0;
        VB = new VolsBean();
        ResultSet rs = VB.SelectCount();
        while(rs.next())
            CountAgentTF.setText(String.valueOf(rs.getInt("total")));
        
        rs = VB.Select();        
        while(rs.next())
        {
            Vector ligne = new Vector();
            int x = rs.getInt(1);
            ligne.add(String.valueOf(x));
            String Destination = rs.getString(2);
            ligne.add(Destination);
            Timestamp HD = new Timestamp(rs.getTimestamp(3).getTime());
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HD));
            Timestamp HAP = rs.getTimestamp(4);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAP));
            Timestamp HAE = rs.getTimestamp(5);
            ligne.add(new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAE));
            int Avion = rs.getInt(6);
            ligne.add(String.valueOf(Avion));
            
            
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
        rs = VB.SelectCount();
        System.out.println("SelectCount()");
        
        while(rs.next())
            CountAgentTF.setText(String.valueOf(rs.getInt("total")));
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
        CountAgentTF = new javax.swing.JTextField();
        SELECTBT = new javax.swing.JButton();
        UPDATEBT = new javax.swing.JButton();
        INSERTBT = new javax.swing.JButton();
        DELETEBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Tableau.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Destination", "Départ", "Arrivée prévue", "Arrivée éventuelle", "Avion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tableau);

        jLabel1.setText("Nombre de vols :");

        CountAgentTF.setEditable(false);
        CountAgentTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CountAgentTFActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(CountAgentTF, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UPDATEBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SELECTBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(INSERTBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DELETEBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(CountAgentTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SELECTBT)
                        .addGap(32, 32, 32)
                        .addComponent(INSERTBT)
                        .addGap(29, 29, 29)
                        .addComponent(UPDATEBT)
                        .addGap(34, 34, 34)
                        .addComponent(DELETEBT)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(62, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CountAgentTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CountAgentTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CountAgentTFActionPerformed

    private void SELECTBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SELECTBTActionPerformed
        // TODO add your handling code here:
        SelectVolJFrame SVF = new SelectVolJFrame(VB,this,false);
        SVF.show();
    }//GEN-LAST:event_SELECTBTActionPerformed

    private void UPDATEBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UPDATEBTActionPerformed
        // TODO add your handling code here:
        
        int Id = Integer.parseInt((String)Tableau.getValueAt(Tableau.getSelectedRow(), 0));
        String Destination = (String)Tableau.getValueAt(Tableau.getSelectedRow(), 1);
        Timestamp HD = (Timestamp)Tableau.getValueAt(Tableau.getSelectedRow(), 2);
        Timestamp HAP = (Timestamp)Tableau.getValueAt(Tableau.getSelectedRow(), 3);
        Timestamp HAE = (Timestamp)Tableau.getValueAt(Tableau.getSelectedRow(), 4);
        int Avion = Integer.parseInt((String)Tableau.getValueAt(Tableau.getSelectedRow(), 5));
        try {
            VB.Update(Id, "",null,null,null,0,-1,Destination,HD,HAP,HAE,Avion);
        } catch (SQLException ex) {
            Logger.getLogger(AvionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_UPDATEBTActionPerformed

    private void INSERTBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INSERTBTActionPerformed
        // TODO add your handling code here:
        SelectVolJFrame SVF = new SelectVolJFrame(VB,this,true);
        SVF.show();
    }//GEN-LAST:event_INSERTBTActionPerformed

    private void DELETEBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DELETEBTActionPerformed
        // TODO add your handling code here:
        int Id = Integer.parseInt((String)Tableau.getValueAt(Tableau.getSelectedRow(), 0));
        
        try{
            ResultSet rs = VB.Select(false, Id,"",null,null,null,-1);
            while(rs.next())
            {
                rs.updateInt(6, -1);
                rs.deleteRow();
            }
            this.RefreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(AvionsJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DELETEBTActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CountAgentTF;
    private javax.swing.JButton DELETEBT;
    private javax.swing.JButton INSERTBT;
    private javax.swing.JButton SELECTBT;
    private javax.swing.JTable Tableau;
    private javax.swing.JButton UPDATEBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
