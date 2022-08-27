/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Serveur;

import InterfacesRéseaux.Requete;
import InterfacesRéseaux.SourceTaches;
import Protocole.CHELUP.RequeteCHELUP;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//
/**
 *
 * @author student
 */
public class ThreadClientBaggages extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    private ConsoleServeurBaggages cs;
    private Socket tacheEnCours;
    
    public ThreadClientBaggages(SourceTaches st, String n, ConsoleServeurBaggages cs)
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
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
                System.out.println(nom+"> Interruption : " + e.getMessage());
            }
            if(tacheEnCours != null){
                boolean log = true,isAlreadyRead = false;
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
                    isAlreadyRead = true;
                    try {
                        dis = new DataInputStream(tacheEnCours.getInputStream());
                        System.out.println("dis créé");
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadClientBaggages.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
                while(log == true)
                {
                    if(isAlreadyRead){
                        try {
                            System.out.println("isAlreadyRead");
                            byte[] param = new byte[70];

                            int numberReads = 0;
                            boolean isAtTheEnd = false;
                            System.out.println("param créé");
                            while(!isAtTheEnd && numberReads < 70){
                                byte entry;
                                entry = dis.readByte();
                                System.out.println("entry = "+entry);
                                if(entry != 0x40){
                                    param[numberReads] = entry;
                                    numberReads++;
                                }
                                else{
                                    System.out.println("Fin du message reçue");
                                    isAtTheEnd = true;
                                }
                            }

                           //int numberReads = dis.read(param,0,5);
                            System.out.println("param = "+new String(param)+"\nNumber of reads:"+numberReads);
                            req = new RequeteCHELUP(param);
                        } catch (IOException ex) {
                            Logger.getLogger(ThreadClientBaggages.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        try {
                            req = (Requete) ois.readObject();
                        } catch (IOException ex ) {
                            Logger.getLogger(ThreadClientBaggages.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ThreadClientBaggages.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
