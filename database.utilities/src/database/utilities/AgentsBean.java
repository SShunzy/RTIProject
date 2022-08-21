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
public class AgentsBean 
{
    private final Class MyDriver;
    private final Connection MyConnexion;
    private final Statement MyInstruction;
    
    public AgentsBean() throws ClassNotFoundException, SQLException
    {
        MyDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        MyConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "rootmysql11");
        MyInstruction = MyConnexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }
    
    private String WhereCond(String Instruction, int IdAgent, String Nom, String Prenom, String Poste)
    {
        int cpt = 0;
        
        if(IdAgent>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " IdAgents = " + IdAgent;
            cpt++;
        }
        if(!Nom.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " Nom = '" + Nom+"'";
            cpt++;
        }
        if(!Prenom.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " Prenom = '" + Prenom+"'";
            cpt++;
        }
        if(!Poste.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " Poste = '" + Poste+"'";
            cpt++;
        }
        return Instruction;
    }
    
    public ResultSet Select() throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select * from Agents;",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet SelectCount()throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select count(*) as total from Agents;",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet Select(boolean isCount,int IdAgent, String Nom, String Prenom, String Poste) throws SQLException
    {
        String Instruction;
        if(isCount)
            Instruction = "Select count(*) as total from Agents";
        else
            Instruction = "Select * From Agents";
        Instruction = WhereCond(Instruction, IdAgent,Nom,Prenom,Poste);
        Instruction= Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public void Update(int IdAgent, String Nom, String Prenom, String Poste, int NewIdAgent, String NewNom, String NewPrenom, String NewPoste) throws SQLException
    {
        int cpt =0;
        String Instruction = "Update Agents Set";
        if(NewIdAgent>0)
        {
            
            Instruction = Instruction + " IdAgents = "+NewIdAgent;
            cpt++;
        }
        if(!NewNom.isEmpty())
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " Nom = '"+NewNom+"'";
            cpt++;
        }
        if(!NewPrenom.isEmpty())
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " Prenom = '"+NewPrenom+"'";
            cpt++;
        }
        if(!NewPoste.isEmpty())
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " Poste = '"+NewPoste+"'";
            cpt++;
        }
        if(cpt == 0)
            return;
        Instruction = WhereCond(Instruction,IdAgent,Nom,Prenom,Poste);
        Instruction = Instruction + ";";
        System.out.println("Instruction = "+Instruction);
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pStmt.executeUpdate();
    }
    
    public void AfficheResult(ResultSet rs) throws SQLException
    {
        int IdA, cpt = 0;
        String Nom,Prenom,Poste;
        while(rs.next())
        {
            cpt++;
            IdA = rs.getInt(1);
            Nom = rs.getString(2);
            Prenom = rs.getString(3);
            Poste = rs.getString(4);
            System.out.println(cpt+"./ Id : "+IdA + " / Nom : "+Nom+" / Prénom : "+Prenom+" / Poste :"+Poste);
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

            AgentsBean Test = new AgentsBean();
            System.out.println("Test de Select");
            
            rs = Test.Select();
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() avec param");
            
            rs = Test.Select(false,1,"","","");
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() Count");
            
            rs = Test.Select(true, 0,"Thys","","");
            Test.AfficheCount(rs);
            
            rs = Test.SelectCount();
            Test.AfficheCount(rs);
            
            System.out.println("Test de Update");
            
            Test.Update(2,"Thys","","",0,"Thonus","","");
            rs = Test.Select();
            Test.AfficheResult(rs);
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BilletsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
