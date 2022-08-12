/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.LUGAP;

import Baggages.Serveur.ConsoleServeurBaggages;
import Classes.Baggages;
import Classes.Vols;
import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class RequeteLUGAP implements Requete, Serializable
{
    public static int REQUEST_LOGIN = 1;
    public static int REQUEST_VOLS = 2;
    public static int REQUEST_BAGGAGES = 3;
    public static int REQUEST_LOGOUT = 4;
    private int type;
    private String chargeUtile;
    private Vols VolSelect;
    private byte[] Password;
    private Socket socketClient;

    public RequeteLUGAP(int t)
    {
        type = t;
    }
    
    public RequeteLUGAP(int t, String chu, byte[] pwd)
    {
        type = t; setChargeUtile(chu); setPassword(pwd);
    }
    public RequeteLUGAP(int t, String chu, Socket s, byte[] pwd)
    {
        type = t; setChargeUtile(chu); socketClient =s;setPassword(pwd);
    }
    
    public RequeteLUGAP(int t, Vols vol)
    {
        type = t; setVol(vol);
    }
            
    public boolean createRunnable (Socket s, ConsoleServeur cs)
    {
        if (type==REQUEST_LOGIN)
        {
            try {
                this.traiteRequeteLogin(s, (ConsoleServeurBaggages)cs);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RequeteLUGAP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(RequeteLUGAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        else if (type == REQUEST_VOLS)
        {
            this.traiteRequeteVols(s, (ConsoleServeurBaggages)cs);
            return true;
        }
        else if (type == REQUEST_BAGGAGES)
        {
            this.traiteRequeteBaggage(s, (ConsoleServeurBaggages)cs);
            return true;
        }
        
        else if (type == REQUEST_LOGOUT)
        {
            this.traiteRequeteLogOut(s, (ConsoleServeurBaggages)cs);
            return false;
        }
    else return false;
    }
    
    public int getType(){return type;}

    private void traiteRequeteLogin(Socket sock, ConsoleServeurBaggages cs) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        // la charge utile est le nom du client
        String Pwd = (String)cs.getTable(getChargeUtile());
        
        cs.TraceEvenements(adresseDistante+"#Login de "+
        getChargeUtile()+"#"+Thread.currentThread().getName());
        ReponseLUGAP rep;
        if (Pwd != null)
        {
            System.out.println("Login trouvé pour " + getChargeUtile());
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            md.update(getChargeUtile().getBytes());
            if(MessageDigest.isEqual(getPassword(), md.digest(Pwd.getBytes())))
            {
                rep = new ReponseLUGAP(ReponseLUGAP.LOGIN_OK, getChargeUtile());
            }
            else
            {
                rep = new ReponseLUGAP(ReponseLUGAP.WRONG_PASSWORD, getChargeUtile());
            }
        }
        else
        {
            System.out.println("Login non trouvé pour " + getChargeUtile());
            Pwd="?@?";
            rep = new ReponseLUGAP(ReponseLUGAP.LOGIN_NOT_FOUND, getChargeUtile());
        }
        // Construction d'une réponse

        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(rep); oos.flush();
        }
        catch (IOException e)
        {
            System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
        }
    }
    
    private void traiteRequeteVols(Socket sock, ConsoleServeurBaggages cs)
    {
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        // la charge utile est le nom du client
        ResultSet rs = cs.getVols();
        cs.TraceEvenements(adresseDistante+"# Recherche des Vols#"+Thread.currentThread().getName());
        ReponseLUGAP rep;
        Vols[] VolArray;
        try {
            if(rs.last()){
                VolArray = new Vols[rs.getRow()];
                rs.beforeFirst();
            }
            else VolArray = new Vols[0];
            
            int i = 0;
            try {
                while(VolArray.length != 0 && rs.next())
                {
                    VolArray[i] = new Vols(rs.getInt(1),rs.getString(2),rs.getTimestamp(3),rs.getTimestamp(4),rs.getTimestamp(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getInt(9));
                    i++;
                }
                // Construction d'une réponse
            } catch (SQLException ex) {
                System.err.println("Erreur ? [" + ex.getMessage() + "]");
            }
            if(VolArray.length == 0){
                rep = new ReponseLUGAP(ReponseLUGAP.VOLS_NULL, VolArray);
                System.out.println("Pas de vol trouvé");
            }
            else{
                rep = new ReponseLUGAP(ReponseLUGAP.VOLS_FOUND, VolArray);
                System.out.println(VolArray.length+" vols trouvé");
            }
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep); 
                oos.flush();
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
         catch (SQLException ex) {
            Logger.getLogger(RequeteLUGAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getChargeUtile() { return chargeUtile; } 
    public void setChargeUtile(String chargeUtile)
    {
        this.chargeUtile = chargeUtile;
    } 
    
    public byte[] getPassword(){return Password;}
    public void setPassword(byte[] pwd)
    {
        this.Password = pwd;
    }
    
    public Vols getVol(){return VolSelect;}
    public void setVol(Vols vol)
    {
        this.VolSelect = vol;
    }
    
    public void traiteRequeteLogOut(Socket sock, ConsoleServeurBaggages cs)
    {
        System.out.println("RequeteLogOut");
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        try {
            // la charge utile est le nom du client
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(RequeteLUGAP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void traiteRequeteBaggage(Socket sock, ConsoleServeurBaggages cs)
    {
        System.out.println("RequeteBagages");
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        // la charge utile est le nom du client
        ResultSet rs = cs.getBaggages(getVol().ID);
        Baggages[] BagTable = new Baggages[getVol().NbPlace+10];
        BagTable[0] = null;
        int i = 0;
        try {
            while(rs.next())
            {
                BagTable[i] = new Baggages(rs.getInt(1),rs.getString(2),rs.getFloat(3), rs.getBoolean(4));
                System.out.println(BagTable[i].AfficheBaggages());
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequeteLUGAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(i+ " résultats");
        
        cs.TraceEvenements(adresseDistante+"# Requete Bagages#"+Thread.currentThread().getName());
        ReponseLUGAP rep = new ReponseLUGAP(ReponseLUGAP.BAGGAGES_SEND, BagTable);
        // Construction d'une réponse
            for(int j = 0; rep.getBaggages()[j] != null; j++)
                System.out.println(rep.getBaggages()[j].AfficheBaggages());
        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(rep); oos.flush();
        }
        catch (IOException e)
        {
            System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
        }
    }   
}
