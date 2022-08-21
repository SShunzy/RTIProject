/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.SelectFrame;
import GUI.VolsJFrame;
import database.utilities.VolsBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class SelectVolJFrame extends javax.swing.JFrame {

    private VolsBean VB;
    private VolsJFrame VJF;
    private boolean Insert;
    /**
     * Creates new form SelectAvionJFrame
     */
    public SelectVolJFrame(VolsBean VB, VolsJFrame VJF, boolean Insert) {
        initComponents();
        this.VB = VB;
        this.VJF = VJF;
        this.Insert = Insert;
        this.setTitle("Sélection de vols");
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
        PrenomL = new javax.swing.JLabel();
        PosteL = new javax.swing.JLabel();
        HeureAPTF = new javax.swing.JTextField();
        DestinationTF = new javax.swing.JTextField();
        HeureDTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        HeureAETF = new javax.swing.JTextField();
        AvionTF = new javax.swing.JTextField();
        MinDTF = new javax.swing.JTextField();
        YearDTF = new javax.swing.JTextField();
        MinAPTF = new javax.swing.JTextField();
        YearAPTF = new javax.swing.JTextField();
        MinAETF = new javax.swing.JTextField();
        YearAETF = new javax.swing.JTextField();
        DayDTF = new javax.swing.JTextField();
        MoisDTF = new javax.swing.JTextField();
        DayAPTF = new javax.swing.JTextField();
        MoisAPTF = new javax.swing.JTextField();
        DayAETF = new javax.swing.JTextField();
        MoisAETF = new javax.swing.JTextField();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        IdL.setText("Id:");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        NomL.setText("Destination:");

        PrenomL.setText("Départ:");

        PosteL.setText("Arrivee Prevue:");

        HeureAPTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HeureAPTFActionPerformed(evt);
            }
        });

        HeureDTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HeureDTFActionPerformed(evt);
            }
        });

        jLabel1.setText("Arrivee Eventuelle:");

        jLabel2.setText("Avion:");

        YearDTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearDTFActionPerformed(evt);
            }
        });

        YearAETF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearAETFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(IdL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IdTF))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25)
                        .addComponent(AvionTF))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(NomL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DestinationTF))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(PosteL)
                            .addComponent(PrenomL))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(DayAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DayDTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DayAETF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MoisDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(YearDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(HeureDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MinDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(MoisAETF)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(YearAETF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(HeureAETF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(MinAETF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(MoisAPTF)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(YearAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(HeureAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(MinAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, 0)))))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IdL, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(IdTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DestinationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NomL))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PrenomL)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(HeureDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MinDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DayDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MoisDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(YearDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PosteL)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(YearAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MinAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(HeureAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MoisAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DayAPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DayAETF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MoisAETF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(YearAETF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(HeureAETF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MinAETF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(AvionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            ResultSet rs;
            Calendar c = Calendar.getInstance();
            int Id,Avion;
            if(IdTF.getText().isEmpty())
                Id = 0;
            else
                Id = Integer.parseInt(IdTF.getText());
            Timestamp HAP=null,HAE=null,HD=null;
            if( !DayAPTF.getText().isEmpty() && !MoisAPTF.getText().isEmpty() && !HeureAPTF.getText().isEmpty() && !MinAPTF.getText().isEmpty() && !YearAPTF.getText().isEmpty())
            {
                System.out.println("date entrée : "+HeureAPTF.getText()+":"+MinAPTF.getText()+" "+DayAPTF.getText()+"/"+Integer.parseInt(MoisAPTF.getText())+"/"+YearAPTF.getText());
                HAP = new Timestamp(Integer.parseInt(YearAPTF.getText())-1900,Integer.parseInt(MoisAPTF.getText())-1,Integer.parseInt(DayAPTF.getText()),Integer.parseInt(HeureAPTF.getText()),Integer.parseInt(MinAPTF.getText()),0,0);
                System.out.println("Date Calendar : "+ new SimpleDateFormat("HH:mm dd/MM/yyyy ").format(HAP));

            }
            if(!DayAETF.getText().isEmpty() && !MoisAETF.getText().isEmpty() && !HeureAETF.getText().isEmpty() && !MinAETF.getText().isEmpty() && !YearAETF.getText().isEmpty())
            {
                c.set(Integer.parseInt(YearAETF.getText()),Integer.parseInt(MoisAETF.getText()),Integer.parseInt(DayAETF.getText()),Integer.parseInt(HeureAETF.getText()),Integer.parseInt(MinAETF.getText()),0);
                HAE = new Timestamp(c.getTimeInMillis());
            }
            if(!DayDTF.getText().isEmpty() && !MoisDTF.getText().isEmpty() && !HeureDTF.getText().isEmpty() && !MinDTF.getText().isEmpty() && !YearDTF.getText().isEmpty())
                HD = new Timestamp(new GregorianCalendar(Integer.parseInt(YearDTF.getText()),Integer.parseInt(MoisDTF.getText()),Integer.parseInt(DayDTF.getText()),Integer.parseInt(HeureDTF.getText()),Integer.parseInt(MinDTF.getText()),0).getTimeInMillis());
            if(AvionTF.getText().isEmpty())
                Avion = -1;
            else
                Avion = Integer.parseInt(AvionTF.getText());
            if(Insert)
            {

                if(Id == 0)
                    System.out.println("L'id n'est pas valide!!");
                else
                {
                    ResultSet rsCompte = VB.Select(true,Id,"",null,null,null,-1);
                    int Compte = 0;
                    while(rsCompte.next())
                        Compte = rsCompte.getInt("total");
                    System.out.println("Compte = "+Compte);
                    if(Compte > 0)
                        System.out.println("L'id n'est pas valide!!");
                    else
                    {
                        rs = VB.Select();
                        rs.moveToInsertRow();
                        rs.updateInt(1,Id);
                        rs.updateString(2,DestinationTF.getText());
                        rs.updateTimestamp(4,HAP);
                        rs.updateTimestamp(3,HD);
                        rs.updateTimestamp(5, HAE);
                        rs.updateInt(6,Avion);
                        rs.insertRow();
                        VJF.RefreshTable(rs);
                    }
                }
                this.dispose();                  
            }
            else
            {
                
                rs = VB.Select(false, Id,DestinationTF.getText(),HD, HAP,HAE,Avion);
                VJF.RefreshTable(rs);
                this.dispose();
                // TODO add your handling code here:
            }
        } catch (SQLException ex) {
            Logger.getLogger(SelectVolJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void HeureAPTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HeureAPTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HeureAPTFActionPerformed

    private void YearDTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearDTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearDTFActionPerformed

    private void HeureDTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HeureDTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HeureDTFActionPerformed

    private void YearAETFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearAETFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearAETFActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AvionTF;
    private javax.swing.JTextField DayAETF;
    private javax.swing.JTextField DayAPTF;
    private javax.swing.JTextField DayDTF;
    private javax.swing.JTextField DestinationTF;
    private javax.swing.JTextField HeureAETF;
    private javax.swing.JTextField HeureAPTF;
    private javax.swing.JTextField HeureDTF;
    private javax.swing.JLabel IdL;
    private javax.swing.JTextField IdTF;
    private javax.swing.JTextField MinAETF;
    private javax.swing.JTextField MinAPTF;
    private javax.swing.JTextField MinDTF;
    private javax.swing.JTextField MoisAETF;
    private javax.swing.JTextField MoisAPTF;
    private javax.swing.JTextField MoisDTF;
    private javax.swing.JLabel NomL;
    private javax.swing.JLabel PosteL;
    private javax.swing.JLabel PrenomL;
    private javax.swing.JTextField YearAETF;
    private javax.swing.JTextField YearAPTF;
    private javax.swing.JTextField YearDTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
