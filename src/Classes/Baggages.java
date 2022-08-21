/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;

/**
 *
 * @author student
 */
public class Baggages implements Serializable
{
    public static int STATUS_LOADED = 1;
    public static int STATUS_NOT_LOADED = 0;
    public static int STATUS_REFUSED = 2;
    
    public int ID, loaded;
    public float weight;
    public String IdBillet,remarks;
    public boolean IsValise, isVerified,isReceived;
    
    public Baggages (int ID, String idbillet, float poids,boolean isvalise)
    {
        this.ID = ID;
        this.IdBillet = idbillet;
        this.weight = poids;
        this.IsValise= isvalise;
        this.loaded = 0;
        this.isReceived = false;
        this.isVerified = false;
        this.remarks = "";
    }
    
    public String AfficheBaggages()
    {
        String str = "ID: "+ID+" Billets: "+IdBillet+" Poids: "+weight+" Valise: "+IsValise;
        return str;
    }
}
