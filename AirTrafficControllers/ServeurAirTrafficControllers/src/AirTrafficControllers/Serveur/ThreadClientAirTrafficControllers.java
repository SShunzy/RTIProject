/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AirTrafficControllers.Serveur;

import Protocole.ACMAP.ConsoleServeurAirTrafficControllers;
import InterfacesRéseaux.Requete;
import InterfacesRéseaux.SourceTaches;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class ThreadClientAirTrafficControllers extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    private ConsoleServeurAirTrafficControllers cs;
    private Socket tacheEnCours;
    
    public ThreadClientAirTrafficControllers(SourceTaches st, String n, ConsoleServeurAirTrafficControllers cs)
    {
        this.tachesAExecuter = st;
        this.nom = n;
        this.cs = cs;
    }
    
    @Override
    public void run()
    {
        while (!isInterrupted())
        {
            System.out.println("Debut du "+nom);
            try
            {
                System.out.println(nom+"> Thread client avant get");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
                System.out.println(nom+"> Interruption : " + e.getMessage());
            }
            if(tacheEnCours != null){
                boolean log = true;
                System.out.println(nom+"> run de tachesencours");
                ObjectInputStream ois=null;
                Requete req = null;
                try
                {
                    ois = new ObjectInputStream(tacheEnCours.getInputStream());
                }
                catch (IOException e)
                {
                    System.err.println("Erreur ? [" + e.getMessage() + "]");
                }  
                while(log == true && !isInterrupted())
                {
                    try {
                        req = (Requete) ois.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(ThreadClientAirTrafficControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());

                    log = req.createRunnable(tacheEnCours, cs);
                }
            }
            System.out.println("Fin du "+nom);
            this.interrupt();
        }
    }
}
