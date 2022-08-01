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
public class BilletsBean {
    
    private final Class MyDriver;
    private final Connection MyConnexion;
    private final Statement MyInstruction;
    
    public BilletsBean() throws ClassNotFoundException, SQLException
    {
        MyDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        MyConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "rootmysql11");
        MyInstruction = MyConnexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }
    
    private String WhereCond(String Instruction, String IdBillets, int IdVol, int NbPassagers)
    {
        int cpt = 0;
        if(!IdBillets.isEmpty())
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " IdBillets = '" + IdBillets+"'";
            cpt++;
        }
        if(IdVol>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " idVol = " + IdVol;
            cpt++;
        }
        if(NbPassagers>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " NbPassagers = " + NbPassagers;
            cpt++;
        }
         return Instruction;
    }
    
    public ResultSet Select() throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select * from Billets;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet SelectCount()throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select count(*) as total from Billets;",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet Select(boolean isCount,String IdBillets, int IdVol, int NbPassagers) throws SQLException
    {
        String Instruction;
        if(isCount)
            Instruction = "Select count(*) as total from Billets";
        else
            Instruction = "Select * From Billets";
        Instruction = WhereCond(Instruction, IdBillets, IdVol, NbPassagers);
        Instruction= Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public void Update(String IdBillets, int IdVol, int NbPassagers, String NewIdBillets,int NewIdVol, int NewNbPassagers) throws SQLException
    {
        int cpt =0;
        String Instruction = "Update Billets Set";
        
        if(!NewIdBillets.isEmpty())
        {
            Instruction = Instruction + " IdBillets = "+NewIdBillets;
            cpt++;
        }
        
        if(NewIdVol>0)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction + " IdVol = "+IdVol;
            cpt++;
        }
        
        if(NewNbPassagers>=0)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" NbPassagers = "+NewNbPassagers;
            cpt++;
        }
        if(cpt == 0)
            return;
        Instruction = WhereCond(Instruction,IdBillets, IdVol,NbPassagers);
        Instruction = Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        pStmt.executeUpdate();
    }
    
    public void AfficheResult(ResultSet rs) throws SQLException
    {
        String IdB; 
        int IdV, NbP,cpt =0;
        while(rs.next())
        {
            cpt++;
            IdB = rs.getString(1);
            IdV = rs.getInt(2);
            NbP = rs.getInt(3);
            System.out.println(cpt+"./ Id : "+IdB + " / Vol : "+IdV + " / Passagers : "+ NbP);
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

            BilletsBean Test = new BilletsBean();
            System.out.println("Test de Select");
            
            rs = Test.Select();
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() avec param");
            
            rs = Test.Select(false, "", 2, 5);
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() Count");
            
            rs = Test.Select(true,"69", 1, 4);
            Test.AfficheCount(rs);
            
            rs = Test.SelectCount();
            Test.AfficheCount(rs);
            
            System.out.println("Test de Update");
            
            Test.Update("9", 0, -1, "", 0, 5);
            rs = Test.Select();
            Test.AfficheResult(rs);
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BilletsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
