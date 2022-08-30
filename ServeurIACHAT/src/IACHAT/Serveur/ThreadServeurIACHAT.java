/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IACHAT.Serveur;


import InterfacesRéseaux.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class ThreadServeurIACHAT extends Thread
{
    private int port, NbClients;
    
    private ConsoleServeurIACOP guiApplication;
    private ServerSocket SSocket = null;


    public ThreadServeurIACHAT(int p, ConsoleServeurIACOP fs,int nbClient)
    {
       this.NbClients = nbClient; 
       this.port = p; 
       this.guiApplication = fs;
    }

    public void run()
    {
        try
        {
            SSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
        }  
        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = SSocket.accept();
                System.out.println("main> run de tachesencours");
                ObjectInputStream ois=null;
                Requete req = null;
                try
                {
                    ois = new ObjectInputStream(CSocket.getInputStream());
                }
                catch (IOException e)
                {
                    System.err.println("Erreur ? [" + e.getMessage() + "]");
                }  
                try {
                    req = (Requete) ois.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ThreadServeurIACHAT.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());
                req.createRunnable(CSocket, guiApplication);               
            }
            catch (IOException e)
            {
                System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); System.exit(1);
            }
        }

    }
    
    public void Shutdown()
    {
        try {
            SSocket.close();
            this.interrupt();
        }
        catch (IOException ex) {
            Logger.getLogger(ThreadServeurIACHAT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
