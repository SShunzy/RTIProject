/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.SelectFrame;
import GUI.BilletsJFrame;
import database.utilities.BilletsBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class SelectBilletJFrame extends javax.swing.JFrame {

    private BilletsBean BB;
    private BilletsJFrame BJF;
    private boolean Insert;
    /**
     * Creates new form SelectAvionJFrame
     */
    public SelectBilletJFrame(BilletsBean BB, BilletsJFrame BJF, boolean Insert) {
        initComponents();
        this.BB = BB;
        this.BJF = BJF;
        this.Insert = Insert;
        this.setTitle("Sélection de billets");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        IdTF = new javax.swing.JTextField();
        IdL = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        NomL = new javax.swing.JLabel();
        VolTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PassagersTF = new javax.swing.JTextField();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        IdL.setText("Id:");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        NomL.setText("Vol:");

        VolTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolTFActionPerformed(evt);
            }
        });

        jLabel1.setText("Passagers:");

        PassagersTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PassagersTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PassagersTF))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(NomL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(VolTF))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(IdL)
                                .addGap(23, 23, 23)
                                .addComponent(IdTF, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VolTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NomL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(PassagersTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            ResultSet rs;
            String Id = IdTF.getText();
            int Vol=-1, Passager=-1;
            if(!VolTF.getText().isEmpty())
                Vol = Integer.parseInt(VolTF.getText());
            if(!PassagersTF.getText().isEmpty())
                Passager = Integer.parseInt(PassagersTF.getText());
            if(Insert)
            {

                if(Id.isEmpty())
                    System.out.println("L'id n'est pas valide!!");
                else
                {
                    ResultSet rsCompte = BB.Select(true,Id,-1,-1);
                    int Compte = 0;
                    while(rsCompte.next())
                        Compte = rsCompte.getInt("total");
                    System.out.println("Compte = "+Compte);
                    if(Compte > 0)
                        System.out.println("L'id n'est pas valide!!");
                    else
                    {
                        rs = BB.Select();
                        rs.moveToInsertRow();
                        rs.updateString(1,Id);
                        rs.updateInt(2, Vol);
                        rs.updateInt(3, Passager);       
                        rs.insertRow();
                        BJF.RefreshTable(rs);
                    }
                }
                this.dispose();                  
            }
            else
            {

                rs = BB.Select(false, Id,Vol,Passager);
                BJF.RefreshTable(rs);
                this.dispose();
                // TODO add your handling code here:
            }
        } catch (SQLException ex) {
            Logger.getLogger(SelectBilletJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PassagersTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PassagersTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PassagersTFActionPerformed

    private void VolTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VolTFActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IdL;
    private javax.swing.JTextField IdTF;
    private javax.swing.JLabel NomL;
    private javax.swing.JTextField PassagersTF;
    private javax.swing.JTextField VolTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
