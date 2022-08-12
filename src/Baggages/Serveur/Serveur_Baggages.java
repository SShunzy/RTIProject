/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Serveur;

import database.utilities.BDBean;
import java.util.*;
import javax.swing.table.*;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author student
 */
public class Serveur_Baggages extends javax.swing.JFrame implements ConsoleServeurBaggages
{
    public static String MySQLConnexion = "jdbc:mysql://localhost:3306/BD_AIRPORT";
    public static String MySQLUsername = "root";
    public static String MySQLPassword = "rootmysql11";
    
    public Hashtable tableLogin = new Hashtable();
    private int portBagages;
    private int portCheckin = 32000;
    private ThreadServeurBaggages ts;
    /**
     * Creates new form Serveur_Bagages
     */
    public Serveur_Baggages() {
        Security.addProvider(new BouncyCastleProvider());
        initComponents();
        this.setTitle("Serveur");
        this.LoadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        ServeurL = new javax.swing.JLabel();
        PortL = new javax.swing.JLabel();
        ClientL = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        StartBT = new javax.swing.JButton();
        StopBT = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ClientTable = new javax.swing.JTable();
        PortTF = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ServeurL.setText("        Serveur");

        PortL.setText("port d'écoute :");

        ClientL.setBackground(new java.awt.Color(255, 255, 0));
        ClientL.setText("         Clients");

        StartBT.setText("Lancer");
        StartBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartBTActionPerformed(evt);
            }
        });

        StopBT.setText("Arrêter");
        StopBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBTActionPerformed(evt);
            }
        });

        ClientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Origine", "Requete", "Thread"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(ClientTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(PortL)
                        .addGap(48, 48, 48)
                        .addComponent(PortTF))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(StartBT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(StopBT)))
                .addGap(61, 61, 61))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(ClientL, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(ServeurL, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ServeurL, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PortL, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StartBT)
                    .addComponent(StopBT))
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientL, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StartBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartBTActionPerformed
        portBagages = Integer.parseInt(PortTF.getText());
        TraceEvenements("serveur#acquisition du port#main");
        ts = new ThreadServeurBaggages(portBagages, new ListeTachesBaggages(), this,5);
        ts.start();

    }//GEN-LAST:event_StartBTActionPerformed

    private void StopBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBTActionPerformed
        if(ts!= null)
        {
            ts.Shutdown();
            this.StopTrace();
        }
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
            java.util.logging.Logger.getLogger(Serveur_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Serveur_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Serveur_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Serveur_Baggages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Serveur_Baggages().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientL;
    private javax.swing.JTable ClientTable;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    private javax.swing.JLabel ServeurL;
    private javax.swing.JButton StartBT;
    private javax.swing.JButton StopBT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    
    public void StopTrace()
    {
        DefaultTableModel dtm = (DefaultTableModel) this.ClientTable.getModel();
        for(int i = dtm.getRowCount()-1; i >= 0; i--)
            dtm.removeRow(i);
    }
    
    @Override
    public void TraceEvenements(String commentaire) {
        Vector ligne = new Vector();
        StringTokenizer parser = new StringTokenizer(commentaire,"#");
        while (parser.hasMoreTokens())
        ligne.add(parser.nextToken());
        DefaultTableModel dtm = (DefaultTableModel)this.ClientTable.getModel();
        dtm.insertRow(dtm.getRowCount(),ligne);
    }

    @Override
    public Object getTable(String chu) 
    {
        return this.tableLogin.get(chu);
    }
    
    @Override
    public ResultSet getVols()
    {
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Baggages.MySQLConnexion, Serveur_Baggages.MySQLUsername, Serveur_Baggages.MySQLPassword);
            BD.setTable("Vols");
            BD.setColumns("*");
            BD.setCondition("");
            System.out.println("getVols()!!!");
            return BD.Select(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(Serveur_Baggages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
                
    public void LoadTable()
    {
        try {
            BDBean BD = new BDBean();
            
            BD.setConnection(Serveur_Baggages.MySQLConnexion, Serveur_Baggages.MySQLUsername, Serveur_Baggages.MySQLPassword);
            BD.setColumns("prenom, nom");
            BD.setTable("Agents");
            BD.setCondition("");
            
            ResultSet rs = BD.Select(false);
            
            this.tableLogin.clear();
            
            while(rs.next())
            {
                this.tableLogin.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Serveur_Baggages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ResultSet getBaggages(int Idvol) 
    {
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Baggages.MySQLConnexion, Serveur_Baggages.MySQLUsername, Serveur_Baggages.MySQLPassword);
            BD.setTable("Bagages inner join Billets");
            BD.setColumns("Bagages.idBagages, Bagages.idBillets, Bagages.Poids, Bagages.isValise");
            BD.setCondition("Bagages.idBillets = Billets.idBillets && Billets.IdVol = "+Idvol);
            System.out.println("getBaggages()!!!");
            return BD.Select(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(Serveur_Baggages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
