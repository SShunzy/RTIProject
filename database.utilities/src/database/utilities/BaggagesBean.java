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
public class BaggagesBean 
{
    private final Class MyDriver;
    private final Connection MyConnexion;
    private final Statement MyInstruction;
    
    public BaggagesBean() throws ClassNotFoundException, SQLException
    {
        MyDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        MyConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "rootmysql11");
        MyInstruction = MyConnexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }
    
    private String WhereCond(String Instruction, int IdBaggage, String IdBillets)
    {
        int cpt = 0;
        
        if(IdBaggage>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " IdBagages = " + IdBaggage;
            cpt++;
        }
        if(!IdBillets.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " IdBillets = '" + IdBillets+"'";
            cpt++;
        }
        return Instruction;
    }
    
    public ResultSet Select() throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select * from Bagages;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet SelectCount()throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select count(*) as total from Bagages;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet Select(boolean isCount,int IdBagage, String IdBillets) throws SQLException
    {
        String Instruction;
        if(isCount)
            Instruction = "Select count(*) as total from Bagages";
        else
            Instruction = "Select * From Bagages";
        Instruction = WhereCond(Instruction, IdBagage, IdBillets);
        Instruction= Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public void Update(int IdBagages, String IdBillets, int NewIdBagages, String NewIdBillets) throws SQLException
    {
        int cpt =0;
        String Instruction = "Update Bagages Set";
        if(NewIdBagages>0)
        {
            
            Instruction = Instruction + " IdBagages = "+NewIdBagages;
            cpt++;
        }
        if(!NewIdBillets.isEmpty())
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " IdBillets = "+NewIdBillets;
            cpt++;
        }
        if(cpt == 0)
            return;
        Instruction = WhereCond(Instruction,IdBagages,IdBillets);
        Instruction = Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        pStmt.executeUpdate();
    }
    
    public void AfficheResult(ResultSet rs) throws SQLException
    {
        int IdBa, cpt = 0;
        String IdBi;
        while(rs.next())
        {
            cpt++;
            IdBa = rs.getInt(1);
            IdBi = rs.getString(2);
            System.out.println(cpt+"./ Id : "+IdBa + " / Billet : "+IdBi);
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

            BaggagesBean Test = new BaggagesBean();
            System.out.println("Test de Select");
            
            rs = Test.Select();
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() avec param");
            
            rs = Test.Select(false, 1, "");
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() Count");
            
            rs = Test.Select(true, 0, "69");
            Test.AfficheCount(rs);
            
            rs = Test.SelectCount();
            Test.AfficheCount(rs);
            
            System.out.println("Test de Update");
            
            Test.Update(1, "69", 0, "9");
            rs = Test.Select();
            Test.AfficheResult(rs);
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BilletsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
