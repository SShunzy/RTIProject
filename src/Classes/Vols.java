/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author student
 */
public class Vols implements Serializable
{
    public int ID, IdAvion, NbPlace, Prix;
    public String Destination,NomCompagnie;
    public Timestamp HD,HAP,HAE;
    
    public Vols(int id, String dest, Timestamp hd, Timestamp hap, Timestamp hae,int idavion, int NbPlace ,String NomCompagnie,int prix)
    {
        this.ID = id;
        this.Destination = dest;
        this.HD = hd;
        this.HAE = hae;
        this.HAP = hap;
        this.IdAvion = idavion;
        this.NbPlace = NbPlace;
        this.NomCompagnie = NomCompagnie;
        this.Prix = prix;
    }
    
    public Vols()
    {
        this.ID = 0;
        this.Destination = "";
        this.HD = null;
        this.HAE = null;
        this.HAP = null;
        this.IdAvion = 0;
        this.NbPlace = 0;
        this.NomCompagnie = "";
        this.Prix = 0;
    }
    
    public String StringTable()
    {
        String df = new SimpleDateFormat("HH:mm").format(HD);
        String retour = "VOL "+ID+" "+NomCompagnie+" - "+Destination + " "+df;
        return retour;
    }
}
