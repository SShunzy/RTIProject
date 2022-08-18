/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Application;

import Protocole.TICKMAP.RequeteTICKMAP;
import Baggages.Application.Application_Baggages;
import java.awt.Dimension;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author student
 */
public class LoginBillets extends javax.swing.JDialog 
{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;
    Application_Billets parent;
    /**
     * Creates new form LoginBillets
     */
    public LoginBillets(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        this.parent = (Application_Billets)parent;
        initComponents();
        setVisible(true);
        this.setPreferredSize(new Dimension(400,197));
        this.setTitle("Connexion");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginL = new javax.swing.JLabel();
        PasswordL = new javax.swing.JLabel();
        LoginTF = new javax.swing.JTextField();
        AnnulerBT = new javax.swing.JButton();
        ConnexionBT = new javax.swing.JButton();
        PortL = new javax.swing.JLabel();
        PortTF = new javax.swing.JTextField();
        AdresseL = new javax.swing.JLabel();
        AdresseTF = new javax.swing.JTextField();
        PasswordPF = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        LoginL.setText("Login :");

        PasswordL.setText("Password :");

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
                        .addComponent(PasswordL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PasswordPF))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoginL)
                            .addComponent(PortL)
                            .addComponent(AdresseL))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoginTF)
                            .addComponent(PortTF)
                            .addComponent(AdresseTF))))
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
                    .addComponent(LoginL)
                    .addComponent(LoginTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordL)
                    .addComponent(PasswordPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnnulerBT)
                    .addComponent(ConnexionBT))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("Login");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AnnulerBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBTActionPerformed
        parent.dispose();
        this.dispose();
    }//GEN-LAST:event_AnnulerBTActionPerformed

    private void ConnexionBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnexionBTActionPerformed

        Security.addProvider(new BouncyCastleProvider());
        String Login = LoginTF.getText();
        String Pwd = String.copyValueOf(PasswordPF.getPassword());
        RequeteTICKMAP req = null;
        if(Login.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un Login");
        }
        else
        {
            if(Pwd.equals(""))
            {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un Password");
            }
            else
            {
                MessageDigest pwdSalted;
                try 
                {
                    pwdSalted = MessageDigest.getInstance("SHA-1","BC");
                    pwdSalted.update(Login.getBytes());
                    Timestamp today = Timestamp.from(Instant.now());
                    pwdSalted.update(today.toGMTString().getBytes());
                    req = new RequeteTICKMAP(RequeteTICKMAP.REQUEST_LOGIN, Login, pwdSalted.digest(Pwd.getBytes()), today);

                } 
                catch (NoSuchAlgorithmException ex) 
                {
                    Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (NoSuchProviderException ex) 
                {
                    Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        String adresse = AdresseTF.getText();
        int port = Integer.parseInt(PortTF.getText());
        
        if(port <= 0)
        {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un port valide");
        }
        else
        {
            this.parent.initConnexion(adresse, port);
            this.parent.sendRequeteTICKMAP(req);
            boolean isLoginOK = this.parent.checkPassword();
            if(isLoginOK) this.dispose();
        }
        
    }//GEN-LAST:event_ConnexionBTActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        parent.initLogOut();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdresseL;
    private javax.swing.JTextField AdresseTF;
    private javax.swing.JButton AnnulerBT;
    private javax.swing.JButton ConnexionBT;
    private javax.swing.JLabel LoginL;
    private javax.swing.JTextField LoginTF;
    private javax.swing.JLabel PasswordL;
    private javax.swing.JPasswordField PasswordPF;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    // End of variables declaration//GEN-END:variables
}
