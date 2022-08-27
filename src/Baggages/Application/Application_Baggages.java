/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Application;

import Protocole.LUGAP.ReponseLUGAP;
import Protocole.LUGAP.RequeteLUGAP;
import Classes.Baggages;
import Classes.Vols;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



/**
 *
 * @author student
 */
public class Application_Baggages extends javax.swing.JFrame 
{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock;
    private Vols[] rs;
    private ListeResponseBaggage listeResponse;
    private ThreadListenerBaggage TLB;

    public Application_Baggages()
    {
        Security.addProvider(new BouncyCastleProvider());
        initComponents();
        this.setTitle("Vols");
        JDialog d = this.Connexion;
        d.setTitle("Connexion");
        this.setVisible(false);
        d.setSize(420, 420);
        d.setVisible(true);
        listeResponse = new ListeResponseBaggage();
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
        StopBT = new javax.swing.JButton();
        SelectBT = new javax.swing.JButton();

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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
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

        StopBT.setText("Arrêter");
        StopBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBTActionPerformed(evt);
            }
        });

        SelectBT.setText("Sélectionner");
        SelectBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectBTActionPerformed(evt);
            }
        });

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
                MessageDigest pwdSalted;
                try {
                    pwdSalted = MessageDigest.getInstance("SHA-1","BC");
                    pwdSalted.update(Login.getBytes());
                    req = new RequeteLUGAP(RequeteLUGAP.REQUEST_LOGIN, Login, pwdSalted.digest(Pwd.getBytes()));

                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ois = null; oos = null; cliSock = null;
        String adresse = AdresseTF.getText();
        int port = Integer.parseInt(PortTF.getText());
        try
        {
            cliSock = new Socket(adresse,port);
            System.out.println(cliSock.getInetAddress().toString());
            TLB = new ThreadListenerBaggage(this,cliSock,listeResponse); TLB.start();

        } 
        catch (UnknownHostException ex) 
        {
            System.err.println("Erreur ! Host non trouvé [" + ex + "]");
        } 
        catch (IOException ex) 
        {
            System.err.println("Erreur ! Pas de connexion? [" + ex + "]");
        }
        
        this.SendRequeteLUGAP(req);
        
        ReponseLUGAP rep = this.getReponseLUGAP();
        System.out.println(" *** Reponse reçue : " + rep.getChargeUtile());
        if(rep.getCode() == ReponseLUGAP.LOGIN_OK)
        {
            System.out.println("Bon Password");
            req = new RequeteLUGAP(RequeteLUGAP.REQUEST_GET_VOLS);
            this.SendRequeteLUGAP(req);
            ReponseLUGAP rep2 = this.getReponseLUGAP();
            if(rep2.getCode() == ReponseLUGAP.VOLS_FOUND)
            {
                System.out.println(" *** Reponse reçue : " + rep.getCode());

                System.out.println("Vols found");
                rs = (Vols[])rep2.getReturnArray();
                try {
                    LoadTable();
                } catch (SQLException ex) {
                    Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.Connexion.dispose();
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
        else if(rep.getCode() == ReponseLUGAP.WRONG_PASSWORD)
        {
            JOptionPane.showMessageDialog(this, "Le Password est incorrect!");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Le Login est incorrect!");
        } 
        
    }//GEN-LAST:event_ConnexionBTActionPerformed

    public void SendRequeteLUGAP(RequeteLUGAP req){
        try {
            if(oos == null)
                oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req); oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ReponseLUGAP getReponseLUGAP(){
        ReponseLUGAP rep = null;
        try {
            rep = (ReponseLUGAP) this.listeResponse.getResponse();
        } catch (InterruptedException ex) {
            Logger.getLogger(Application_Baggages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rep;
    }
    
    private void AnnulerBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerBTActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_AnnulerBTActionPerformed

    public void initLogOut(){
        RequeteLUGAP req = new RequeteLUGAP(RequeteLUGAP.REQUEST_LOGOUT);
        this.SendRequeteLUGAP(req);
        TLB.interrupt();
        this.dispose();
    }
    
    private void StopBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBTActionPerformed
        // TODO add your handling code here:
        this.initLogOut();
    }//GEN-LAST:event_StopBTActionPerformed

    private void SelectBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectBTActionPerformed
        // TODO add your handling code here:
        if(VolsTable.getSelectedRow()== -1)
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un vol");
        else
        {
            System.out.println("Select non nul : "+VolsTable.getSelectedRow()+". "+rs[VolsTable.getSelectedRow()].StringTable());
            RequeteLUGAP req = new RequeteLUGAP(RequeteLUGAP.REQUEST_GET_BAGGAGES,rs[VolsTable.getSelectedRow()]);
            this.SendRequeteLUGAP(req);
            System.out.println("RequeteEnvoyee");
            ReponseLUGAP rep = this.getReponseLUGAP();
            System.out.println("Reponse Reçue : "+ rep.getCode());
            Baggages[] tableBaggage = (Baggages[])rep.getReturnArray();
            for(int i = 0; tableBaggage[i] != null; i++)
                System.out.println(tableBaggage[i].AfficheBaggages());
            
            AffichageBaggages BD = new AffichageBaggages(this, false, tableBaggage,this);
            BD.setVisible(true);
        }
    }//GEN-LAST:event_SelectBTActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.initLogOut();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(Application_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application_Baggages AS = new Application_Baggages();
                AS.setVisible(false);                
            }
        });
    }

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
    private javax.swing.JButton SelectBT;
    private javax.swing.JButton StopBT;
    private javax.swing.JTable VolsTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void LoadTable() throws SQLException
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

}

