/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.SelectFrame;
import GUI.AvionsJFrame;
import database.utilities.AvionBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class SelectAvionJFrame extends javax.swing.JFrame {

    private ResultSet rs;
    private AvionBean AB;
    private AvionsJFrame AJF;
    private boolean Insert;
    /**
     * Creates new form SelectAvionJFrame
     */
    public SelectAvionJFrame(AvionBean AB, AvionsJFrame AJF, boolean Insert) {
        try {
            initComponents();
            this.AB = AB;
            this.AJF = AJF;
            this.Insert = Insert;
            this.setTitle("Sélection d'avions");
            if(this.Insert)
                this.Check.setVisible(false);
            rs = this.AB.Select();
        } catch (SQLException ex) {
            Logger.getLogger(SelectAvionJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IdTF = new javax.swing.JTextField();
        Check = new javax.swing.JCheckBox();
        Op = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Check.setText("Check?");
        Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActionPerformed(evt);
            }
        });

        Op.setText("Opérationnel?");

        jLabel1.setText("Id:");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Op)
                            .addComponent(Check))
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IdTF)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(Check)
                .addGap(18, 18, 18)
                .addComponent(Op)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int Id;
        if(IdTF.getText().isEmpty())
            Id = 0;
        else
            Id = Integer.parseInt(IdTF.getText());
        if(Insert)
        {
            try {
                if(Id == 0)
                    System.out.println("L'id n'est pas valide!!");
                else
                {
                    ResultSet rsCompte = AB.Select(true, Id, false, false);
                    int Compte = 0;
                    while(rsCompte.next())
                        Compte = rsCompte.getInt("total");
                    System.out.println("Compte = "+Compte);
                    if(Compte > 0)
                        System.out.println("L'id n'est pas valide!!");
                    else
                    {
                        rs = AB.Select();
                        rs.moveToInsertRow();
                        rs.updateInt(1,Id);
                        rs.updateBoolean(2, Op.isSelected());
                        rs.insertRow();
                        AJF.RefreshTable(rs);
                    }
                }
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(SelectAvionJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
        }
        else  //Cas de Sélection//
        {
            try {
                
                rs = AB.Select(false,Id , Check.isSelected(), Op.isSelected());
                AJF.RefreshTable(rs);
                this.dispose();
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Logger.getLogger(SelectAvionJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Check;
    private javax.swing.JTextField IdTF;
    private javax.swing.JCheckBox Op;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
