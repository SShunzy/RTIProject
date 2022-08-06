/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author student
 */
public class Passagers implements Serializable
{
    public static int NbPassagers;
    public int IdPassager,IdVol,SeatNumber, NrCommande;
    public String Nom, Prénom;
    public Timestamp DdN;
    
    public Passagers( int idvol, String nom, String prenom, Timestamp ddn)
    {
        this.IdPassager = ++Passagers.NbPassagers;
        this.IdVol = idvol;
        this.Nom = nom;
        this.Prénom = prenom;
        this.DdN = ddn;
        this.SeatNumber = this.IdPassager;
        this.NrCommande = 0;
    }
    
    public Passagers( int idvol, String nom, String prenom, Timestamp ddn, int seatNumber)
    {
        this.IdPassager = ++Passagers.NbPassagers;
        this.IdVol = idvol;
        this.Nom = nom;
        this.Prénom = prenom;
        this.DdN = ddn;
        this.SeatNumber = seatNumber;
        this.NrCommande = 0;
    }
    
    public Passagers(int idPassagers , int idvol, String nom, String prenom, Timestamp ddn, int seatNumber)
    {
        this.IdPassager = idPassagers;
        this.IdVol = idvol;
        this.Nom = nom;
        this.Prénom = prenom;
        this.DdN = ddn;
        this.SeatNumber = seatNumber;
        this.NrCommande = 0;
    }
}
