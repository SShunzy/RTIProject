
package database.utilities;

import java.io.Serializable;
import java.sql.*;

/**
 *
 * @author student
 */
public class BDBean implements Serializable
{
    private Class MyDriver;
    private Connection MyConnexion;
    private String MyTable;
    private String MyCondition;
    private String MyColumns;
    private String MyValues;
    
    public BDBean()
    {
        setTable("");
        setCondition("");
        setColumns("");
    }
    
    
    public void setDriver(String driver) throws ClassNotFoundException
    {
        this.MyDriver = Class.forName(driver);

    }
    
    public void setConnection(String adresse, String user, String Password) throws SQLException
    {
        this.MyConnexion = DriverManager.getConnection(adresse, user, Password);
    }
    
    public void setTable(String table)
    {
        this.MyTable = table;
    }
    
    public void setCondition(String Cond)
    {
        this.MyCondition = Cond;
    }
    
    public void setColumns(String col)
    {
        this.MyColumns = col;
    }
    
    public void setValues(String values)
    {
        this.MyValues = values;
    }
    
    public Class getDriver()
    {
        return this.MyDriver;
    }
    
    public Connection getConnection()
    {
        return this.MyConnexion;
    }
    
    public String getTable()
    {
        return this.MyTable;
    }
    
    public String getCondition()
    {
        return this.MyCondition;
    }
    
    public String getColumns()
    {
        return this.MyColumns;
    }
    
    public String getValues()
    {
        return this.MyValues;
    }
    
    public ResultSet Select(boolean count) throws SQLException
    {
        String colonnes = "*";
        if(!getColumns().equals(""))
        {
            colonnes = getColumns();
        }
        if(count)
        {
            colonnes = "Count(*) as total";
        }
        String query = "Select <columns> from <tables>";
        if(!getCondition().equals(""))
            query = query + " where <Cond>";
        query = query + ";";
        String SQL = query.replaceAll("<columns>", colonnes).replaceAll("<tables>", getTable()).replaceAll("<Cond>",getCondition());
        PreparedStatement pStmt = this.getConnection().prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Requête : "+SQL);
        return pStmt.executeQuery();
    }
    
    public int Update() throws SQLException    
    {
        String query = "Update <tables> set <values>";
        if(!getCondition().equals(""))
            query = query + " where <Cond>";
        String SQL = query.replaceAll("<values>", getValues()).replaceAll("<tables>", getTable()).replaceAll("<Cond>",getCondition());
        PreparedStatement pStmt = this.getConnection().prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Requête : "+SQL);
        return pStmt.executeUpdate();
    }
    
    public int Insert() throws SQLException
    {
        String query = "Insert into <tables> ";
        if(!this.getColumns().equals(""))
            query = query + "(<columns>) ";
        query = query + "values <valeurs>";
        String SQL = query.replaceAll("<tables>", getTable()).replaceAll("<columns>", getColumns()).replaceAll("<valeurs>", getValues());
        PreparedStatement pStmt = this.getConnection().prepareCall(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return pStmt.executeUpdate();
    }
    
}
