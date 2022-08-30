/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Application;

import Protocole.PAYP.ReponsePAYP;
import Protocole.PAYP.RequetePAYP;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author student
 */
public class PaymentWindow extends javax.swing.JFrame {
    
    public static String CertificateRepository = "/home/student/NetBeansProjects/RTIProjectGit/Certificats/";

    private Application_Billets parent;
    private Socket cliSock;
    private String Employé,NrCommande;
    private int prix;
    /**
     * Creates new form PaymentWindow
     */
    public PaymentWindow(Application_Billets parent, String employé,String NrCommande,int prix) {
        this.parent = parent;
        this.Employé = employé;
        this.prix = prix;
        this.NrCommande = NrCommande;
        initComponents();
        this.PrixTF.setText(""+prix);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PrixL = new javax.swing.JLabel();
        NomL = new javax.swing.JLabel();
        PrixTF = new javax.swing.JTextField();
        AnnulerBT = new javax.swing.JButton();
        ConnexionBT = new javax.swing.JButton();
        PortL = new javax.swing.JLabel();
        AdresseL = new javax.swing.JLabel();
        PortTF = new javax.swing.JTextField();
        AdresseTF = new javax.swing.JTextField();
        NuméroL = new javax.swing.JLabel();
        NuméroTF = new javax.swing.JTextField();
        NomTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PrixL.setText("Prix:");

        NomL.setText("Nom:");

        PrixTF.setEditable(false);

        AnnulerBT.setText("Annuler");
        AnnulerBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerBTActionPerformed(evt);
            }
        });

        ConnexionBT.setText("Connexion");
        ConnexionBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnexionBTActionPerformed(evt);
            }
        });

        PortL.setText("Port :");

        AdresseL.setText("Adresse :");

        NuméroL.setText("Numéro:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AnnulerBT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                        .addComponent(ConnexionBT))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NomL)
                        .addGap(48, 48, 48)
                        .addComponent(NomTF))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PrixL)
                            .addComponent(PortL)
                            .addComponent(AdresseL))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PrixTF)
                            .addComponent(PortTF)
                            .addComponent(AdresseTF)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NuméroL)
                        .addGap(24, 24, 24)
                        .addComponent(NuméroTF)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdresseL)
                    .addComponent(AdresseTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PortL)
                    .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrixL)
                    .addComponent(PrixTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NomL)
                    .addComponent(NomTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NuméroL)
                    .addComponent(NuméroTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnnulerBT)
                    .addComponent(ConnexionBT))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AnnulerBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBTActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_AnnulerBTActionPerformed

    private void ConnexionBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnexionBTActionPerformed
        
        String NumCard = NuméroTF.getText();
        String Nom = NomTF.getText();
        
        Security.addProvider(new BouncyCastleProvider());
        String adresse = AdresseTF.getText();
        int port = Integer.parseInt(PortTF.getText());

        if(NumCard.isEmpty()){
            JOptionPane.showMessageDialog(this, "Veuillez entrer un numéro de carte valide");

        }
        else{
            if(Nom.isEmpty()){
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nom valide");

            }
            else{
                if(port <= 0)
                {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer un port valide");
                }
                else
                {
                    initConnexion(adresse,port);
                    String message = NumCard+"@"+Nom+"@"+prix;
                    
                    try {
                        //Cryptage du message//
                        KeyStore ks = KeyStore.getInstance("JKS");
                        ks.load(new FileInputStream(PaymentWindow.CertificateRepository+"Client.key"),"student1".toCharArray());
                        
                        Certificate certif = ks.getCertificate("PaymentKey");
                        
                        Cipher EncryptMessage = Cipher.getInstance("RSA","BC");
                        EncryptMessage.init(Cipher.ENCRYPT_MODE,certif); ;
                        
                        byte[] CryptedMessage = EncryptMessage.doFinal(message.getBytes());
                        
                        //Création du HMAC//
                        
                        KeyStore ksMac = KeyStore.getInstance("JCEKS");
                        ksMac.load(new FileInputStream(PaymentWindow.CertificateRepository+"Agents.jce"), "student1".toCharArray());
                        
                        Mac hmac = Mac.getInstance("HMAC-MD5","BC");
                        hmac.init(ksMac.getKey(this.Employé, "student1".toCharArray()));
                        
                        byte[] Signature = hmac.doFinal(message.getBytes());

                        RequetePAYP req = new RequetePAYP(RequetePAYP.REQUEST_PAYMENT,CryptedMessage,Signature);
                        
                        //Envoi de la requête//
                        
                        ObjectOutputStream oos = null;
                        if(oos == null)
                            oos = new ObjectOutputStream(cliSock.getOutputStream());
                        oos.writeObject(req); oos.flush();
                        
                        //Réception de la réponse//
                        
                        ReponsePAYP rep;
                        ObjectInputStream ois = null;
                        ois = new ObjectInputStream(cliSock.getInputStream());
                        rep = (ReponsePAYP)ois.readObject();
                        System.out.println("Réponse reçue");
                        
                        if(rep.getCode() == ReponsePAYP.PAYMENT_OK){
                            JOptionPane.showMessageDialog(this, "Paiement réussi");
                            this.parent.traitePaymentAccepted(NrCommande);
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "Paiement échoué");
                            this.parent.traitePaiementRefused(NrCommande);
                        }
                        this.dispose();
                        
                    } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException | UnrecoverableKeyException | ClassNotFoundException ex) {
                        Logger.getLogger(PaymentWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }

    }//GEN-LAST:event_ConnexionBTActionPerformed
    

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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdresseL;
    private javax.swing.JTextField AdresseTF;
    private javax.swing.JButton AnnulerBT;
    private javax.swing.JButton ConnexionBT;
    private javax.swing.JLabel NomL;
    private javax.swing.JTextField NomTF;
    private javax.swing.JLabel NuméroL;
    private javax.swing.JTextField NuméroTF;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    private javax.swing.JLabel PrixL;
    private javax.swing.JTextField PrixTF;
    // End of variables declaration//GEN-END:variables
}
