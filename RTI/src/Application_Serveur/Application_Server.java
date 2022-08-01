/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application_Serveur;

import ProtocoleLUGAP.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author student
 */
public class Application_Server extends javax.swing.JFrame 
{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;

    public Application_Server()
    {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Connexion = new javax.swing.JDialog();
        LoginL = new javax.swing.JLabel();
        PasswordL = new javax.swing.JLabel();
        LoginTF = new javax.swing.JTextField();
        AnnulerBT = new javax.swing.JButton();
        ConnexionBT = new javax.swing.JButton();
        PasswordPF = new javax.swing.JPasswordField();
        PortL = new javax.swing.JLabel();
        PortTF = new javax.swing.JTextField();
        AdresseL = new javax.swing.JLabel();
        AdresseTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        VolsTable = new javax.swing.JTable();

        LoginL.setText("Login :");

        PasswordL.setText("Password : ");

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

        AdresseL.setText("Adresse:");

        javax.swing.GroupLayout ConnexionLayout = new javax.swing.GroupLayout(Connexion.getContentPane());
        Connexion.getContentPane().setLayout(ConnexionLayout);
        ConnexionLayout.setHorizontalGroup(
            ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnexionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ConnexionLayout.createSequentialGroup()
                        .addComponent(AnnulerBT)
                        .addGap(61, 61, 61)
                        .addComponent(ConnexionBT))
                    .addGroup(ConnexionLayout.createSequentialGroup()
                        .addComponent(PasswordL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PasswordPF, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(ConnexionLayout.createSequentialGroup()
                        .addComponent(AdresseL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AdresseTF))
                    .addGroup(ConnexionLayout.createSequentialGroup()
                        .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoginL)
                            .addComponent(PortL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoginTF)
                            .addComponent(PortTF))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConnexionLayout.setVerticalGroup(
            ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnexionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdresseL)
                    .addComponent(AdresseTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PortL)
                    .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoginL)
                    .addComponent(LoginTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordL)
                    .addComponent(PasswordPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ConnexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnnulerBT)
                    .addComponent(ConnexionBT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConnexionBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnexionBTActionPerformed

        String Login = LoginTF.getText();
        String Pwd = String.copyValueOf(PasswordPF.getPassword());
        RequeteLUGAP req = null;
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
                req = new RequeteLUGAP(RequeteLUGAP.REQUEST_LOGIN, Login);
            }
        }
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
        
        try
        {
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);oos.flush();
        } 
        catch (IOException ex) 
        {
            System.err.println("Erreur réseau ? [" + ex.getMessage() + "]");
        }
        
        ReponseLUGAP rep = null;
        
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseLUGAP)ois.readObject();
            System.out.println(" *** Reponse reçue : " + rep.getChargeUtile());
        } 
        catch (IOException ex) 
        {
            System.out.println("--- erreur IO = " + ex.getMessage());        
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("--- erreur sur la classe = " + ex.getMessage());        
        }
    }//GEN-LAST:event_ConnexionBTActionPerformed

    private void AnnulerBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBTActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_AnnulerBTActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdresseL;
    private javax.swing.JTextField AdresseTF;
    private javax.swing.JButton AnnulerBT;
    private javax.swing.JDialog Connexion;
    private javax.swing.JButton ConnexionBT;
    private javax.swing.JLabel LoginL;
    private javax.swing.JTextField LoginTF;
    private javax.swing.JLabel PasswordL;
    private javax.swing.JPasswordField PasswordPF;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    private javax.swing.JTable VolsTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
