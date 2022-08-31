/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML.Application;


import Protocole.XMLPP.ReponseXMLPP;
import Protocole.XMLPP.RequeteXMLPP;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 *
 * @author student
 */
public class ApplicationXML extends javax.swing.JFrame 
{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;

    public ApplicationXML()
    {
        initComponents();
        this.setTitle("Parse");
        this.setVisible(true);
        this.FileChooser = null;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FileChooser = new javax.swing.JFileChooser();
        ConnexionBT = new javax.swing.JButton();
        AdresseL = new javax.swing.JLabel();
        PortL = new javax.swing.JLabel();
        PortTF = new javax.swing.JTextField();
        FichierBT = new javax.swing.JButton();
        AdresseTF = new javax.swing.JTextField();
        FichierL = new javax.swing.JLabel();
        AnnulerBT = new javax.swing.JButton();
        FichierTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ConnexionBT.setText("Connexion");
        ConnexionBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnexionBTActionPerformed(evt);
            }
        });

        AdresseL.setText("Adresse:");

        PortL.setText("Port :");

        FichierBT.setText("Sélectionner Fichier");
        FichierBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FichierBTActionPerformed(evt);
            }
        });

        AdresseTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdresseTFActionPerformed(evt);
            }
        });

        FichierL.setText("Fichier:");

        AnnulerBT.setText("Annuler");
        AnnulerBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(AnnulerBT)
                            .addGap(61, 61, 61)
                            .addComponent(ConnexionBT))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(AdresseL)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(AdresseTF, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FichierL)
                                .addComponent(PortL))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FichierTF)
                                .addComponent(PortTF))))
                    .addComponent(FichierBT, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdresseL)
                    .addComponent(AdresseTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PortL)
                    .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FichierL)
                    .addComponent(FichierTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FichierBT)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnnulerBT)
                    .addComponent(ConnexionBT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConnexionBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnexionBTActionPerformed

        ois = null; oos = null; cliSock = null;
        String adresse = AdresseTF.getText();
        int port = Integer.parseInt(PortTF.getText());
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
        if(this.FichierTF.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fichier");
        }
        else{
            byte[] contenuFichier = new byte[15000];
            try {
                FileInputStream fis = new FileInputStream(this.FichierTF.getText());
                fis.read(contenuFichier);
            } catch (FileNotFoundException ex) {
                System.err.println("Erreur de fichier : ["+ex.getMessage()+"]");System.exit(-1);
            } catch (IOException ex) {
                Logger.getLogger(ApplicationXML.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(contenuFichier.length > 0){
                RequeteXMLPP req = new RequeteXMLPP(RequeteXMLPP.REQUEST_PARSE,new String(contenuFichier));
                ReponseXMLPP rep = null;
                try {
                    oos = new ObjectOutputStream(cliSock.getOutputStream());
                    oos.writeObject(req); oos.flush();
                    
                    ois = new ObjectInputStream(cliSock.getInputStream());
                    rep = (ReponseXMLPP) ois.readObject();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationXML.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ApplicationXML.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(rep == null) JOptionPane.showMessageDialog(this, "Erreur dans la lecture de la réponse");
                else if(rep.getCode() == ReponseXMLPP.PARSING_OK){
                    JOptionPane.showMessageDialog(this, "Le parsing s'est bien déroulé");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Erreur lors du parsing du fichier XML");
                }
                
            }
        }
        
    }//GEN-LAST:event_ConnexionBTActionPerformed

    
    private void AnnulerBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBTActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_AnnulerBTActionPerformed
  
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            this.cliSock.close();
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void AdresseTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdresseTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdresseTFActionPerformed

    private void FichierBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichierBTActionPerformed
        // TODO add your handling code here:
        this.FileChooser = new JFileChooser();
        this.FileChooser.setFileFilter(new FileNameExtensionFilter("XML Files","xml"));
        int returnVal = FileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.FichierTF.setText(FileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_FichierBTActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)
    {
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
            java.util.logging.Logger.getLogger(ApplicationXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ApplicationXML AS = new ApplicationXML();
                AS.setVisible(true);                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdresseL;
    private javax.swing.JTextField AdresseTF;
    private javax.swing.JButton AnnulerBT;
    private javax.swing.JButton ConnexionBT;
    private javax.swing.JButton FichierBT;
    private javax.swing.JLabel FichierL;
    private javax.swing.JTextField FichierTF;
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    // End of variables declaration//GEN-END:variables


}

