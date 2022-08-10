/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Serveur;

import Classes.Passagers;
import database.utilities.BDBean;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author student
 */
public class Serveur_Billets extends javax.swing.JFrame implements ConsoleServeurBillets
{
    public static String MySQLConnexion = "jdbc:mysql://localhost:3306/BD_AIRPORT";
    public static String MySQLUsername = "root";
    public static String MySQLPassword = "rootmysql11";

    
    public Hashtable tableLogin = new Hashtable();
    private SecretKey SessionKey;
    private int portBillets;
    private ThreadServeurBillets ts;
    private static int NbClient = 10;
    /**
     * Creates new form Serveur_Billets
     */
    public Serveur_Billets() 
    {
        this.setTitle("Serveur Billets");
        initComponents();
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

        jSeparator1 = new javax.swing.JSeparator();
        StartBT = new javax.swing.JButton();
        StopBT = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ClientTable = new javax.swing.JTable();
        PortTF = new javax.swing.JTextField();
        ServeurL = new javax.swing.JLabel();
        PortL = new javax.swing.JLabel();
        ClientL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        ServeurL.setText("        Serveur");

        PortL.setText("port d'écoute :");

        ClientL.setBackground(new java.awt.Color(255, 255, 0));
        ClientL.setText("         Clients");

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
        portBillets = Integer.parseInt(PortTF.getText());
        TraceEvenements("serveur#acquisition du port#main");
        ts = new ThreadServeurBillets(portBillets, new ListeTachesBillets(), this, NbClient);
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
    public static void main(String args[]) 
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
            java.util.logging.Logger.getLogger(Serveur_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Serveur_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Serveur_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Serveur_Billets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Serveur_Billets().setVisible(true);
            }
        });
    }

    @Override
    public void TraceEvenements(String commentaire) 
    {
    Vector ligne = new Vector();
        StringTokenizer parser = new StringTokenizer(commentaire,"#");
        while (parser.hasMoreTokens())
        ligne.add(parser.nextToken());
        DefaultTableModel dtm = (DefaultTableModel)this.ClientTable.getModel();
        dtm.insertRow(dtm.getRowCount(),ligne);    }

    @Override
    public Object getTable(String chu) 
    {
        return this.tableLogin.get(chu);
    }

    
    public void StopTrace()
    {
        DefaultTableModel dtm = (DefaultTableModel) this.ClientTable.getModel();
        for(int i = dtm.getRowCount()-1; i >= 0; i--)
            dtm.removeRow(i);
    }
    
    public void LoadTable()
    {
        try {
            BDBean BD = new BDBean();
            
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
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
            System.err.println("Erreur ? [" + ex.getMessage() + "]");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientL;
    private javax.swing.JTable ClientTable;
    private javax.swing.JLabel PortL;
    private javax.swing.JTextField PortTF;
    private javax.swing.JLabel ServeurL;
    private javax.swing.JButton StartBT;
    private javax.swing.JButton StopBT;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setSessionKey(SecretKey sessionKey) 
    {
        SessionKey = sessionKey;
    }

    @Override
    public SecretKey getSecretKey() {
        return SessionKey;
    }

    @Override
    public ResultSet getVols() 
    {
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Vols");
            BD.setColumns("*");
            BD.setCondition("");
            System.out.println("getVols()!!!");
            return BD.Select(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getCountPassengers(int idVol) {
        int count = 0;
        try{
            ResultSet rs = null;
            if(idVol > 0){
                try {
                    BDBean BD = new BDBean();
                    BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
                    BD.setTable("Passagers");
                    BD.setColumns("*");
                    BD.setCondition("Passagers.idVols = "+idVol);
                    System.out.println("getCountPassengers()!!!");
                    rs =  BD.Select(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
            else{
                try {
                    BDBean BD = new BDBean();
                    BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
                    BD.setTable("Passagers");
                    BD.setColumns("*");
                    System.out.println("getCountPassengers()!!!");
                    rs = BD.Select(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(rs != null){
                while(rs.next()){
                    count = rs.getInt(1);
                }
            }   
        }
        catch (SQLException ex){
            Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE,null,ex);
        }
        return count;
    }

    @Override
    public ArrayList<Passagers> insertPassengers(String username ,ArrayList<Passagers> passagers) {
        int maxId=this.getMaxIdPassengers();
        int maxSeatNumber = this.getCountPassengers(passagers.get(0).IdVol);
        int maxNrCommande = this.createCommand(username, passagers.get(0).IdVol, passagers.size());
        String insertValues = "";
        for(int i = 0; i < passagers.size(); i++)
        {
            passagers.get(i).SeatNumber = maxSeatNumber + i +1;
            passagers.get(i).IdPassager += maxId;
            passagers.get(i).NrCommande = maxNrCommande;
            insertValues = insertValues + "( "+passagers.get(i).IdPassager+" ,"+passagers.get(i).IdVol+" ,'"+passagers.get(i).Nom+"' ,'"+passagers.get(i).Prénom+"' ,'"+passagers.get(i).DdN+"' ,"+passagers.get(i).SeatNumber+" ,"+passagers.get(i).NrCommande+")";
            if(i<passagers.size()-1)
                insertValues = insertValues +",";
        }
        System.out.println("insertValues = "+insertValues);
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Passagers");
            BD.setColumns("idPassagers, idVols, Nom, Prénom, DateDeNaissance,NuméroSiege,NuméroCommande");
            BD.setValues(insertValues);
            System.out.println("insertPassengers()!!!");
            BD.Insert();
        } 
        catch (SQLException ex) {
        Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0 ; i < passagers.size(); i++)
            System.out.println(i+">Places = "+passagers.get(i).SeatNumber);
        return passagers;
    }
    
    public int getMaxNrCommande(){
        int maxNrCommande = 0;
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Cart");
            BD.setColumns("MAX(NuméroCommande)");
            ResultSet rs = BD.Select(false);
            while(rs.next())
                maxNrCommande = rs.getInt(1);
        } 
        catch (SQLException ex) {
        Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("maxId = "+maxNrCommande);
        return maxNrCommande;
    }

    public int getMaxIdPassengers() {
        int maxId = 0;
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Passagers");
            BD.setColumns("MAX(idPassagers)");
            System.out.println("getPassagers()!!!");
            ResultSet rs = BD.Select(false);
            while(rs.next())
                maxId = rs.getInt(1);
        } 
        catch (SQLException ex) {
        Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("maxId = "+maxId);
        return maxId;
    }

    @Override
    public ResultSet getSelectedVol(int idVol) {
        if(idVol > 0){
            try {
                BDBean BD = new BDBean();
                BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
                BD.setTable("Vols");
                BD.setColumns("*");
                BD.setCondition("Vols.idVols = "+idVol);
                System.out.println("getVols()!!!");
                return BD.Select(false);
            } catch (SQLException ex) {
                Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                BDBean BD = new BDBean();
                BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
                BD.setTable("Vols");
                BD.setColumns("*");
                System.out.println("getVols()!!!");
                return BD.Select(false);
            } catch (SQLException ex) {
                Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }    
    
    private int createCommand(String username, int idVol, int quantity){
        int maxNrCommande = this.getMaxNrCommande()+1;
        String insertValues = "( "+maxNrCommande+" ,"+idVol+" ,"+quantity+" ,"+false+" ,'"+username+"')";

        System.out.println("insertValues = "+insertValues);
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Cart");
            BD.setColumns("NuméroCommande, idVols, Quantité, isPayed, username");
            BD.setValues(insertValues);
            System.out.println("insertCommande()");
            BD.Insert();
            return maxNrCommande;
        } 
        catch (SQLException ex) {
            Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public void removePassengers(int NrCommande) {
        
        try {
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Passagers");
            BD.setCondition("NuméroCommande = "+NrCommande);
            BD.Delete();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override    
    public void setCartPayed(int NrCommande){
        try{
            BDBean BD = new BDBean();
            BD.setConnection(Serveur_Billets.MySQLConnexion,Serveur_Billets.MySQLUsername ,Serveur_Billets.MySQLPassword );
            BD.setTable("Cart");
            BD.setValues("isPayed = true");
            BD.setCondition("NuméroCommande = "+NrCommande);
            BD.Update();
        }
        catch (SQLException ex){
            Logger.getLogger(Serveur_Billets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
