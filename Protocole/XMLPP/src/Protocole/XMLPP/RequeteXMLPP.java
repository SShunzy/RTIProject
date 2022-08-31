/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.XMLPP;

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
public class RequeteXMLPP implements Requete, Serializable {

    public static int REQUEST_PARSE = 1;
    
    private int code;
    private String XmlString;
    
    public RequeteXMLPP(int code,String xml){
        this.code = code;
        this.XmlString = xml;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(this.code == REQUEST_PARSE){
            System.out.println("Requête Parse");
            this.traiteRequestParse(s, (ConsoleServeurXMLPP)cs);
            return false;
        }
        else{
            System.out.println("Requête non implémentée!\nFermeture du client");
            return false;
        }
    }
    
    private void traiteRequestParse(Socket sock, ConsoleServeurXMLPP cs){
        int response = cs.parseXMLToFlight(XmlString);
        ReponseXMLPP rep = null;
        if(response == 1) rep = new ReponseXMLPP(ReponseXMLPP.PARSING_OK);
        else rep = new ReponseXMLPP(ReponseXMLPP.PARSING_KO);
        
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(rep);oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(RequeteXMLPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
