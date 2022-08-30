/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastercard;

import Protocole.SEBATRAP.ConsoleServeurMastercard;
import InterfacesRéseaux.Requete;
import InterfacesRéseaux.SourceTaches;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
//
/**
 *
 * @author student
 */
public class ThreadClientMastercard extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    private ConsoleServeurMastercard cs;
    private SSLSocket tacheEnCours;
    
    public ThreadClientMastercard(SourceTaches st, String n, ConsoleServeurMastercard cs)
    {
        this.tachesAExecuter = st;
        this.nom = n;
        this.cs = cs;
    }
    
    public Socket getTacheEnCours(){
        return this.tacheEnCours;
    }
    
    @Override
    public void run()
    {
        while (!isInterrupted())
        {
            tacheEnCours = null;
            try
            {
                System.out.println(nom+"> Thread client avant get");
                tacheEnCours = (SSLSocket) tachesAExecuter.getTache();
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
                DataInputStream dis = null;
                try
                {
                    ois = new ObjectInputStream(tacheEnCours.getInputStream());
                    System.out.println("ois créé");

                }
                catch (IOException e)
                {
                                    }  
                while(log == true && !isInterrupted())
                { 
                    try {
                        req = (Requete) ois.readObject();
                    } catch (IOException ex ) {
                        Logger.getLogger(ThreadClientMastercard.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ThreadClientMastercard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(req != null){
                        System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());

                        log = req.createRunnable(tacheEnCours, cs);
                    }
                }   
            }
        }            
    }
}
