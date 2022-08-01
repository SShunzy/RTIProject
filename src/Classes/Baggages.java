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
    public int ID;
    public float Poids;
    public String IdBillet;
    public boolean IsValise;
    
    public Baggages (int ID, String idbillet, float poids,boolean isvalise)
    {
        this.ID = ID;
        this.IdBillet = idbillet;
        this.Poids = poids;
        this.IsValise= isvalise;
    }
    
    public String AfficheBaggages()
    {
        String str = "ID: "+ID+" Billets: "+IdBillet+" Poids: "+Poids+" Valise: "+IsValise;
        return str;
    }
}
