/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolePAYP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import Payment.Serveur.ConsoleServeurPayment;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author student
 */
public class RequetePAYP implements Requete, Serializable
{
    public static int REQUEST_PAYMENT = 1;
    
    
    private int type;
    
    public RequetePAYP(int type)
    {
        this.type = type;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) 
    {
        if(type == RequetePAYP.REQUEST_PAYMENT){
            System.out.println("Requete Payment");
            this.traiteRequetePayment(s,(ConsoleServeurPayment) cs);
            return true;
        }
        else
        {
            System.out.println("Requete non implémentée. Fermeture du client");
            return false;
        }
    }
    
    private void traiteRequetePayment(Socket sock, ConsoleServeurPayment cs)
    {
         // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
    }
    
    
    
}
