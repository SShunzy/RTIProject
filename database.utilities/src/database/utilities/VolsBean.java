package database.utilities;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
public class VolsBean 
{
    private final Class MyDriver;
    private final Connection MyConnexion;
    private final Statement MyInstruction;
    
    public VolsBean() throws ClassNotFoundException, SQLException
    {
        MyDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        MyConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "rootmysql11");
        MyInstruction = MyConnexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }
    
    private String WhereCond(String Instruction, int IdVol, String Destination, Timestamp HAP, Timestamp HD, Timestamp HAE, int IdAvion)
    {
        int cpt = 0;
        if(IdVol>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " idVols = " + IdVol;
            cpt++;
        }
        if(!Destination.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " Destination = '" + Destination+"'";
            cpt++;
        }
        if(HAP != null)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " HeureArriveePrevue = " + HAP;
            cpt++;
        }
        if(HD != null)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " HeureDepart = " + HD;
            cpt++;
        }
        if(HAE != null)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " HeureArriveeEventuelle = " + HAE;
            cpt++;
        }
        if(IdAvion>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " idAvion = " + IdAvion;
            cpt++;
        }
        return Instruction;
    }
    
    public ResultSet Select() throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select * from Vols;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet SelectCount()throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select count(*) as total from Vols;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet Select(boolean isCount, int IdVol, String Destination, Timestamp HAP, Timestamp HD, Timestamp HAE, int IdAvion) throws SQLException
    {
        String Instruction;
        if(isCount)
            Instruction = "Select count(*) as total from Vols";
        else
            Instruction = "Select * From Vols";
        Instruction = this.WhereCond(Instruction, IdVol, Destination, HAP, HD, HAE, IdAvion);
        Instruction= Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public void Update( int IdVol, String Destination, Timestamp HAP, Timestamp HD, Timestamp HAE, int IdAvion, int NewIdVol, String NewDest, Timestamp NewHAP, Timestamp NewHD, Timestamp NewHAE, int NewIdAvion ) throws SQLException
    {
        int cpt =0;
        String Instruction = "Update Vols Set";
        
        if(NewIdVol>0)
        {
            Instruction = Instruction + " IdVols = "+NewIdVol;
            cpt++;
        }
        
        if(!NewDest.isEmpty())
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " Destination = '"+NewDest+"'";
            cpt++;
        }
        
        if(NewHAP != null)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" HeureArriveePrevue = "+NewHAP;
            cpt++;
        }
        if(NewHD != null)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" HeureDepart = "+NewHD;
            cpt++;
        }
        if(NewHAE != null)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" HeureArriveeEventuelle = "+NewHAE;
            cpt++;
        }
        if(NewIdAvion>0)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" idAvion = "+NewIdAvion;
            cpt++;
        }
        if(cpt == 0)
            return;
        Instruction = this.WhereCond(Instruction, IdVol, Destination, HAP, HD, HAE, IdAvion);
        Instruction = Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        pStmt.executeUpdate();
    }
    
    public void AfficheResult(ResultSet rs) throws SQLException
    {
        String Dest; 
        int IdV, IdA,cpt =0;
        Timestamp HD, HAP, HAE;
        while(rs.next())
        {
            cpt++;
            IdV = rs.getInt(1);
            Dest = rs.getString(2);
            HD = rs.getTimestamp(3);
            HAP = rs.getTimestamp(4);
            HAE = rs.getTimestamp(5);
            IdA = rs.getInt(6);
            System.out.println(cpt+"./ Id : "+IdV + " / Destination : "+Dest + " / Heure Départ : "+ HD + " / Heure Arrivée Prévue : "+HAP + " / Heure Arrivée Eventuelle : "+ HAE+ " / Avion : "+IdA);
        }
        if(cpt == 0)
            System.out.println("Aucun résultat");
    }
    
    public void AfficheCount(ResultSet rs) throws SQLException
    {
        while(rs.next())
        {
            System.out.println("Compte = "+rs.getInt("total"));
        }
    }
    
    public static void main(String[] args)
    {
        try 
        {
            ResultSet rs;

            VolsBean Test = new VolsBean();
            System.out.println("Test de Select");
            
            rs = Test.Select();
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() avec param");
            
            rs = Test.Select(false, 0, "", null, null, null, 1);
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() Count");
            
            rs = Test.Select(true, 1, "Paris", null, null, null, 0);
            Test.AfficheCount(rs);
            
            System.out.println("Test de SelectCount()");
            
            rs = Test.SelectCount();
            Test.AfficheCount(rs);
            
            System.out.println("Test de Update");
            
            Test.Update(0, "Paris", null, null, null, 0, 0, "Tenerife", null, null, null, 0);
            rs = Test.Select();
            Test.AfficheResult(rs);
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BilletsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
