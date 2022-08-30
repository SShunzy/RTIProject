/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.SEBATRAP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class RequeteSEBATRAP implements Requete, Serializable{

    public static int REQUEST_PAYMENT = 1;
    
    private final int code;
    private final String numéro;
    private final String nom;
    private final double prix;
    
    public RequeteSEBATRAP(int code,String numéro, String nom, double prix){
        this.code = code;
        this.numéro = numéro;
        this.nom = nom;
        this.prix = prix;
    }
    
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(this.code == RequeteSEBATRAP.REQUEST_PAYMENT){
            System.out.println("Requête Payment");
            this.traiteRequestPayment(s,(ConsoleServeurMastercard) cs);
            return false;
        }
        else{
            System.out.println("Requete non implémentée. Fermeture du client");
            return false;
        }
    }
    
    private void traiteRequestPayment(Socket s, ConsoleServeurMastercard cs){
        ReponseSEBATRAP rep = null;
        boolean isLoadedEnough = cs.isLoadedEnough(numéro, nom, prix);
        if(isLoadedEnough){
            cs.doPayment(numéro, nom, prix);
            rep = new ReponseSEBATRAP(ReponseSEBATRAP.PAYMENT_OK);
        }
        else{
            rep = new ReponseSEBATRAP(ReponseSEBATRAP.PAYMENT_KO);
        }
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(RequeteSEBATRAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
