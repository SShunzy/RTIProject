/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.ACMAP;

import AirTrafficControllers.Serveur.ConsoleServeurAirTrafficControllers;
import Classes.Lanes;
import Classes.Vols;
import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import Protocole.LUGAP.ReponseLUGAP;
import Protocole.LUGAP.RequeteLUGAP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class RequeteACMAP implements Requete, Serializable
{
    public static String SERVER_BAGGAGE_ADDRESS = "127.0.0.1";
    public static int PORT_BAGGAGE = 32400;
    
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
    
   private void sendReponseACMAP(Socket sock, ReponseACMAP rep)
    {
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
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(type == RequeteACMAP.REQUEST_GET_FLIGHTS){
            System.out.println("Requete Get Flights");
            this.traiteRequestGetFlights(s,(ConsoleServeurAirTrafficControllers) cs);
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_IS_BAGGAGES_LOADED){
            System.out.println("Requete Is Baggages Loaded");
            this.traiteRequestIsBaggageLoaded(s,(ConsoleServeurAirTrafficControllers) cs);
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_GET_LANES){
            System.out.println("Requete Get Lanes");
            this.traiteRequestGetLanes(s, (ConsoleServeurAirTrafficControllers) cs);
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_LOCK_LANE){
            System.out.println("Requete Lock Lane");
            return true;
        }
        else if(type == RequeteACMAP.REQUEST_LOGOUT){
            System.out.println("Requete Log Out");
            this.traiteRequeteLogOut(s,(ConsoleServeurAirTrafficControllers) cs);
            return false;
        }
        else{
            System.out.println("Requete non implémentée\nFermeture du client");
            return false;
        }
    }
    
    public void traiteRequestGetFlights(Socket sock, ConsoleServeurAirTrafficControllers cs){
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        
        ReponseACMAP rep;
        try{
            Vols[] FlightArray = (Vols[]) cs.getAvailableFlights().toArray();
            rep = new ReponseACMAP(ReponseACMAP.SEND_FLIGHTS,FlightArray);
        }
        catch(SQLException ex){
            rep = new ReponseACMAP(ReponseACMAP.NO_FLIGHT_FOUND);
        }
        this.sendReponseACMAP(sock, rep);
    }
    
    public void traiteRequestIsBaggageLoaded(Socket sock, ConsoleServeurAirTrafficControllers cs){
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        Socket SBaggages;
        ObjectOutputStream oosLUGAP = null;
        ObjectInputStream oisLUGAP = null;
        ReponseLUGAP repLUGAP = null;
        ReponseACMAP repACMAP = null;
        try {
            SBaggages = new Socket(RequeteACMAP.SERVER_BAGGAGE_ADDRESS,RequeteACMAP.PORT_BAGGAGE);
            
            oosLUGAP = new ObjectOutputStream(SBaggages.getOutputStream());
            oosLUGAP.writeObject(new RequeteLUGAP(RequeteLUGAP.REQUEST_IS_BAGGAGE_LOADED,this.idFlight)); oosLUGAP.flush();
            
            oisLUGAP = new ObjectInputStream(SBaggages.getInputStream());
            repLUGAP = (ReponseLUGAP)oisLUGAP.readObject();
            
            if(repLUGAP.getCode() == ReponseLUGAP.ALL_BAGGAGES_LOADED){
                repACMAP = new ReponseACMAP(ReponseACMAP.BAGGAGES_LOADED);
            }
            else{
                repACMAP = new ReponseACMAP(ReponseACMAP.BAGGAGES_NOT_LOADED);
            }
            this.sendReponseACMAP(sock, repACMAP);
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RequeteACMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void traiteRequestGetLanes(Socket sock, ConsoleServeurAirTrafficControllers cs){
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        
        ReponseACMAP rep;
        try{
            Lanes[] LanesArray = (Lanes[]) cs.getAvailableLanes().toArray();
            rep = new ReponseACMAP(ReponseACMAP.SEND_LANES,LanesArray);
        }
        catch(SQLException ex){
            rep = new ReponseACMAP(ReponseACMAP.NO_LANE_FREE);
        }
        this.sendReponseACMAP(sock, rep);
    }
    
      private void traiteRequeteLogOut(Socket sock, ConsoleServeurAirTrafficControllers cs)
    {
        System.out.println("RequeteLogOut");
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        try {
            // la charge utile est le nom du client //
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(ReponseACMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
