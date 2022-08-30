/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AirTrafficControllers.Application;

import Classes.Vols;
import Protocole.ACMAP.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author student
 */
public class Application_AirTrafficControllers extends javax.swing.JFrame {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8000;
    
    private Socket cliSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Vols[] VolsArray;
    /**
     * Creates new form Application_AirTrafficControllers
     */
    public Application_AirTrafficControllers() {
        initComponents();
        this.initConnexion(SERVER_ADDRESS,SERVER_PORT);
        this.loadVolsArray();
        this.setVisible(true);
        this.setTitle("Application Air Traffic Controllers");
    }
    
    private void initConnexion(String adresse, int port)
    {
        try
        {
            cliSock = new Socket(adresse,port);
            System.out.println(cliSock.getInetAddress().toString());
        } 
        catch (UnknownHostException ex) 
        {
            System.err.println("Erreur ! Host non trouvé [" + ex + "]");
        } 
        catch (IOException ex) 
        {
            System.err.println("Erreur ! Pas de connexion? [" + ex + "]");
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

        SelectBT = new javax.swing.JButton();
        StopBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        VolsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        SelectBT.setText("Sélectionner");
        SelectBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectBTActionPerformed(evt);
            }
        });

        StopBT.setText("Arrêter");
        StopBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBTActionPerformed(evt);
            }
        });

        VolsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vols"
            }
        ));
        jScrollPane1.setViewportView(VolsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(StopBT, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SelectBT)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StopBT)
                    .addComponent(SelectBT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectBTActionPerformed
        // TODO add your handling code here:
        if(VolsTable.getSelectedRow()== -1)
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un vol");
        else
        {
            SelectedVol_AirTrafficControllers VolDialog = new SelectedVol_AirTrafficControllers(this,this.VolsArray[VolsTable.getSelectedRow()]);
            this.setVisible(false);
            VolDialog.setVisible(true);
        }
    }//GEN-LAST:event_SelectBTActionPerformed

    private void StopBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBTActionPerformed
        System.out.println("Déconnexion en cours");
        this.initLogOut();
        // TODO add your handling code here:
    }//GEN-LAST:event_StopBTActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.initLogOut();
    }//GEN-LAST:event_formWindowClosing

    public void initLogOut(){
        RequeteACMAP req = new RequeteACMAP(RequeteACMAP.REQUEST_LOGOUT);
        this.sendRequeteACMAP(req);
        this.dispose();  
    }
    
    public void loadVolsArray(){
        RequeteACMAP req = new RequeteACMAP(RequeteACMAP.REQUEST_GET_FLIGHTS);
        this.sendRequeteACMAP(req);
        ReponseACMAP rep = this.getResponseACMAP();
        
        if(rep.getCode() == ReponseACMAP.SEND_FLIGHTS){
            this.VolsArray = (Vols[]) rep.getArray();
            this.LoadTableGUI();
        }
        else{
            JOptionPane.showMessageDialog(this, "Aucun Vol n'a été trouvé");
        }
    }
    
    private void LoadTableGUI(){
        DefaultTableModel dtm = (DefaultTableModel)this.VolsTable.getModel();
        for(int i = dtm.getRowCount(); i > 0; i--)
        {
            dtm.removeRow(i-1);
        }
        
        for(int i = 0; i < this.VolsArray.length; i++)
        {
            String Vol = this.VolsArray[i].StringTable();
            System.out.println(Vol);
            Vector ligne = new Vector();
            ligne.add(Vol);
            dtm.addRow(ligne);
        }
    }
    
    public void sendRequeteACMAP(RequeteACMAP requete)
    {
        try {
            if(oos == null)
                oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(requete); oos.flush();
            System.out.println("Requête envoyée");
        } catch (IOException ex) {
            Logger.getLogger(Application_AirTrafficControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public ReponseACMAP getResponseACMAP()
    {
        ReponseACMAP rep = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseACMAP)ois.readObject();
            System.out.println("Réponse reçue");
        }
        catch (IOException ex) 
        {
            System.err.println("--- erreur IO = " + ex.getMessage());        
        } 
        catch (ClassNotFoundException ex) 
        {
            System.err.println("--- erreur sur la classe = " + ex.getMessage());        
        }
        return rep;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application_AirTrafficControllers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_AirTrafficControllers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_AirTrafficControllers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_AirTrafficControllers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application_AirTrafficControllers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SelectBT;
    private javax.swing.JButton StopBT;
    private javax.swing.JTable VolsTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}