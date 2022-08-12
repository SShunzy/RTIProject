/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.ACMAP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author student
 */
public class RequeteACMAP implements Requete, Serializable
{
    public static int REQUEST_GET_FLIGHTS = 1;
    public static int REQUEST_IS_BAGGAGES_LOADED = 3;
    public static int REQUEST_GET_LANES = 4;
    public static int REQUEST_LOCK_LANE = 5;
    public static int REQUEST_LOGOUT = 6;

    private final int type;
    private final int idFlight;
    
    public RequeteACMAP(int type){
        this.type = type;
        this.idFlight = 0;
    }
    
    public RequeteACMAP(int type, int idFlight){
        this.type = type;
        this.idFlight = idFlight;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(type == RequeteACMAP.REQUEST_GET_FLIGHTS){
            System.out.println("Requete Get Flights");
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_IS_BAGGAGES_LOADED){
            System.out.println("Requete Is Baggages Loaded");
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_GET_LANES){
            System.out.println("Requete Get Lanes");
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_LOCK_LANE){
            System.out.println("Requete Lock Lane");
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_LOGOUT){
            System.out.println("Requete Log Out");
            return false;
        }
        else{
            System.out.println("Requete non implémentée\nFermeture du client");
            return false;
        }
    }
    
}
