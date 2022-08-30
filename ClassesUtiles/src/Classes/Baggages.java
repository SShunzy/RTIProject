/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author student
 */
public class Baggages implements Serializable
{
    public static int STATUS_LOADED = 1;
    public static int STATUS_NOT_LOADED = 0;
    public static int STATUS_REFUSED = 2;
    
    public int ID, loadedState;
    public float weight;
    public String IdBillet,remarks;
    public boolean IsValise, isVerified,isReceived;
    
    public Baggages (int ID, String idbillet, float poids,boolean isvalise,boolean isreceived, int loadstate, boolean isverified, String remark)
    {
        this.ID = ID;
        this.IdBillet = idbillet;
        this.weight = poids;
        this.IsValise= isvalise;
        this.isReceived = isreceived;
        this.loadedState = loadstate;
        this.isVerified = isverified;
        this.remarks = remark;
    }
    
    public Baggages (ResultSet rs) throws SQLException{
        this.ID             = rs.getInt(1);
        this.IdBillet       = rs.getString(2);
        this.weight         = rs.getFloat(3);
        this.IsValise       = rs.getBoolean(4);
        this.isReceived     = rs.getBoolean(5);
        this.loadedState    = rs.getInt(6);
        this.isVerified     = rs.getBoolean(7);
        this.remarks        = rs.getString(8);
    }
    
    public String AfficheBaggages()
    {
        String str = "ID: "+ID+" Billets: "+IdBillet+" Poids: "+weight+" Valise: "+IsValise;
        return str;
    }
}
