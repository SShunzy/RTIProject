package database.utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author student
 */
public class AvionBean 
{
    private final Class MyDriver;
    private final Connection MyConnexion;
    private final Statement MyInstruction;
    
    public AvionBean() throws ClassNotFoundException, SQLException
    {
        MyDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        MyConnexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "rootmysql11");
        MyInstruction = MyConnexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    }
    
    private String WhereCond(String Instruction, int IdAvions,boolean checkOperability, boolean isOperationnal)
    {
        int cpt = 0;
        if(IdAvions>0)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " idAvions = " + IdAvions;
            cpt++;
        }
        if(checkOperability)
        {
            if(cpt ==0)
                Instruction = Instruction + " where";
            else
                Instruction = Instruction + " and";
            Instruction = Instruction + " isOperationnal = " + isOperationnal;
            cpt++;
        }
         return Instruction;
    }
    
    public ResultSet Select() throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select * from Avions;",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet SelectCount()throws SQLException
    {
        PreparedStatement pStmt = MyConnexion.prepareStatement("select count(*) as total from Avions;",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public ResultSet Select(boolean isCount,int IdAvion,boolean checkOperability, boolean isOperationnal) throws SQLException
    {
        String Instruction;
        if(isCount)
            Instruction = "Select count(*) as total from Avions";
        else
            Instruction = "Select * From Avions";
        Instruction = WhereCond(Instruction, IdAvion, checkOperability, isOperationnal);
        Instruction= Instruction + ";";
        System.out.println("Instruction = " + Instruction);
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeQuery();
    }
    
    public void Update(int IdAvion, boolean checkOperability, boolean isOperationnal, int NewIdAvion, boolean changeOperability, boolean NewisOperationnal) throws SQLException
    {
        int cpt =0;
        String Instruction = "Update Avions Set";     
        
        if(NewIdAvion>0)
        {
            Instruction = Instruction + " IdAvions = "+NewIdAvion;
            cpt++;
        }
        
        if(changeOperability)
        {
            if(cpt>0)
                Instruction = Instruction + ",";
            Instruction = Instruction +" isOperationnal = "+NewisOperationnal;
            cpt++;
        }
        if(cpt == 0)
            return;
        Instruction = this.WhereCond(Instruction, IdAvion, checkOperability, isOperationnal);
        Instruction = Instruction + ";";
        PreparedStatement pStmt = MyConnexion.prepareStatement(Instruction,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pStmt.executeUpdate();
    }
    
    public void AfficheResult(ResultSet rs) throws SQLException
    {
        int IdA,cpt = 0;
        boolean isOp;
        while(rs.next())
        {
            cpt++;
            IdA = rs.getInt(1);
            isOp = rs.getBoolean(2);
            System.out.println(cpt+"./ Id : "+IdA + " / Opérationnel : "+isOp);
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

            AvionBean Test = new AvionBean();
            System.out.println("Test de Select");
            
            rs = Test.Select();
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() avec param");
            
            rs = Test.Select(false, 1, false, true);
            Test.AfficheResult(rs);
            
            System.out.println("Test de Select() Count");
            
            rs = Test.Select(true, 1, false, false);
            Test.AfficheCount(rs);
            
            rs = Test.SelectCount();
            Test.AfficheCount(rs);
            
            System.out.println("Test de Update");
            
            Test.Update(2, false, true, 0, true, true);
            rs = Test.Select();
            Test.AfficheResult(rs);
            
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BilletsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
