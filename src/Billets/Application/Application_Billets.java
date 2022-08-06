/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Application;

import Baggages.Application.Application_Baggages;
import Classes.Vols;
import ProtocoleTICKMAP.ReponseTICKMAP;
import ProtocoleTICKMAP.RequeteTICKMAP;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author student
 */
public class Application_Billets extends javax.swing.JFrame 
{
    public static String CertificateRepository = "/home/student/NetBeansProjects/RTIProjectGit/Certificats/";
    
    public SecretKey SessionKey;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;
    private Vols[] rs;
    /**
     * Creates new form Application_Billets
     */
    public Application_Billets() 
    {
        initComponents();
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
            ChoixVolDialog VolDialog = new ChoixVolDialog(this, true, rs[VolsTable.getSelectedRow()]);
            VolDialog.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_SelectBTActionPerformed

    private void StopBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBTActionPerformed
        System.out.println("Déconnexion en cours");
        this.initLogOut();
              // TODO add your handling code here:
    }//GEN-LAST:event_StopBTActionPerformed

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
            java.util.logging.Logger.getLogger(Application_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                Application_Billets AB = new Application_Billets();
                AB.setTitle("Application Billets");
                AB.setVisible(false);
                new LoginBillets(AB,true);
            }
        });
    }
    
    public void initLogOut(){
        RequeteTICKMAP req = new RequeteTICKMAP(RequeteTICKMAP.REQUEST_LOGOUT);
        this.sendRequeteTICKMAP(req);
        this.dispose();  
    }
    
    public void initConnexion(String adresse, int port)
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
    
    public byte[] DecryptMessage(byte[] cryptedMessage) throws InvalidKeyException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher DecryptClient = Cipher.getInstance("AES/ECB/PKCS5Padding");
        DecryptClient.init(Cipher.DECRYPT_MODE, this.SessionKey);
        return DecryptClient.doFinal(cryptedMessage);
    }

     byte[] EncryptMessage(byte[] unCryptedMessage) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher EncryptClient = Cipher.getInstance("AES/ECB/PKCS5Padding");
        EncryptClient.init(Cipher.ENCRYPT_MODE, this.SessionKey);
        return EncryptClient.doFinal(unCryptedMessage);
    }

    public void setTableVol(Vols[] VolArray)
    {
        rs = VolArray;
        LoadTableGUI();
    }
    
    public void LoadTableGUI()
    {
        DefaultTableModel dtm = (DefaultTableModel)this.VolsTable.getModel();
        for(int i = dtm.getRowCount(); i > 0; i--)
        {
            dtm.removeRow(i);
        }
        
        for(int i = 0; i < rs.length; i++)
        {
            String Vol = rs[i].StringTable();
            System.out.println(Vol);
            Vector ligne = new Vector();
            ligne.add(Vol);
            dtm.addRow(ligne);
        }
    }
    
    public void sendRequeteTICKMAP(RequeteTICKMAP requete)
    {
        try {
            if(oos == null)
                oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(requete); oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Application_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkPassword()
    {
        ReponseTICKMAP rep = this.getReponseTICKMAP();
        
        System.out.println(" *** Reponse reçue : " + rep.getChargeUtile());
        if(rep.getCode() == ReponseTICKMAP.LOGIN_OK)
        {
            System.out.println("Bon Password");
            this.traiteCertificate();
            return true;
        }
        else if(rep.getCode() == ReponseTICKMAP.WRONG_PASSWORD)
        {
            JOptionPane.showMessageDialog(this, "Le Password est incorrect!");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Le Login est incorrect!");
        } 
        return false;
    }
    
    public ReponseTICKMAP getReponseTICKMAP()
    {
        ReponseTICKMAP rep = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseTICKMAP)ois.readObject();
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
    
    private boolean CheckCertificate(KeyStore ks,Certificate certif) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException
    {
        
        String ServeurAlias = null;
        ServeurAlias = ks.getCertificateAlias(certif);
        if(ServeurAlias == null)
        {
            System.out.println("Le certificat n'est pas valide");
            return false;
        }
        else
        {
            System.out.println("Le certificat est valide");
            return true;
        }
    }
    
    private void traiteCertificate()
    {
        
        RequeteTICKMAP req = new RequeteTICKMAP(RequeteTICKMAP.REQUEST_CERTIFICATE,"");
        try
        {
            //oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);oos.flush();
        } 
        catch (IOException ex) 
        {
            System.err.println("Erreur réseau ? [" + ex.getMessage() + "]");
        }
        ReponseTICKMAP rep = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseTICKMAP)ois.readObject();
        }
        catch (IOException ex) 
        {
            System.err.println("Erreur réseau ? [" + ex.getMessage() + "]");
        }
        catch (ClassNotFoundException ex) 
        {
            System.out.println("--- erreur sur la classe = " + ex.getMessage());        
        }
        if(rep.getCode() == ReponseTICKMAP.CERTIFICATE_SENT)
        {
            try
            {
                KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(new FileInputStream(Application_Billets.CertificateRepository+"Client.key"),"student1".toCharArray());
                
                if(this.CheckCertificate(ks,rep.getCertificate()))
                {
                    System.out.println("Bon Certificat!");
                    
                    KeyGenerator kg = KeyGenerator.getInstance("AES", "BC");
                    SecureRandom random = new SecureRandom();
                    kg.init(random);
                    SecretKey SessionKey = kg.generateKey();                   
                    
                    Cipher EncryptClient = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    EncryptClient.init(Cipher.ENCRYPT_MODE, SessionKey);
                    Cipher EncryptServeur = Cipher.getInstance("RSA");
                    EncryptServeur.init(Cipher.WRAP_MODE, rep.getCertificate());

                    byte[] message = EncryptClient.doFinal("hello".getBytes());
                    byte[] WrappedSessionKey = EncryptServeur.wrap(SessionKey);
                    AlgorithmParameters param = EncryptClient.getParameters();
                    
                    RequeteTICKMAP req2 = new RequeteTICKMAP(RequeteTICKMAP.REQUEST_TEST_CERTIFICATE,message,WrappedSessionKey,param);
                    try
                    {
                        //oos = new ObjectOutputStream(cliSock.getOutputStream());
                        oos.writeObject(req2);oos.flush();
                    }
                    catch (IOException ex) 
                    {
                        System.err.println("Erreur réseau ? [" + ex.getMessage() + "]");
                    }
                    this.SessionKey = SessionKey;
                    ReponseTICKMAP rep2 = null;
                    try
                    {
                        ois = new ObjectInputStream(cliSock.getInputStream());
                        rep2 = (ReponseTICKMAP)ois.readObject();
                    }
                    catch (IOException ex) 
                    {
                        System.err.println("Erreur réseau ? [" + ex.getMessage() + "]");
                    }
                    catch (ClassNotFoundException ex) 
                    {
                        System.out.println("--- erreur sur la classe = " + ex.getMessage());        
                    }
                    if(rep2.getCode() == ReponseTICKMAP.VOL_FOUND)
                    {
                        this.setTableVol(rep2.getVols());
                        this.setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Il n'y a aucun vol, l'application va se fermer");
                        try {
                            cliSock.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.dispose();
                    }                   
                }
                else
                {
                    System.out.println("Certificat non reconnu!");
                }
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    private void traitePayment(int prix, String NrCarte, String NomPropriétaire)
    {
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SelectBT;
    private javax.swing.JButton StopBT;
    private javax.swing.JTable VolsTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
