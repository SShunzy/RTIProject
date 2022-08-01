/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Serveur;

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
public class ThreadClientBillets extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    private ConsoleServeurBillets cs;
    private Socket tacheEnCours;
    
    public ThreadClientBillets(SourceTaches st, String n, ConsoleServeurBillets cs)
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
            try
            {
                System.out.println(nom+"> Thread client avant get");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
                System.out.println(nom+"> Interruption : " + e.getMessage());
            }
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
            while(log == true)
            {
                try {
                    req = (Requete) ois.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ThreadClientBillets.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());

                log = req.createRunnable(tacheEnCours, cs);
            }
        }
    }
}
