/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AirTrafficControllers.Serveur;


import InterfacesRéseaux.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class ThreadServeurAirTrafficControllers extends Thread
{
    private int port;
    
    private SourceTaches tachesAExecuter;
    private ConsoleServeurAirTrafficControllers guiApplication;
    private ServerSocket SSocket = null;
    private ArrayList<ThreadClientAirTrafficControllers> thr;


    public ThreadServeurAirTrafficControllers(int p, SourceTaches st, ConsoleServeurAirTrafficControllers fs)
    {
       this.port = p; 
       this.tachesAExecuter = st;
       this.guiApplication = fs;
       thr = new ArrayList<>();
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
                //Création d'un thread à chaque demande//
                
                int newNumberOfThread = thr.size()+1;
                thr.add(new ThreadClientAirTrafficControllers(tachesAExecuter, "Thread du pool n°" + String.valueOf(newNumberOfThread),guiApplication));
                
                
                tachesAExecuter.recordTache(CSocket);
                System.out.println("Socket mis dans la file");
                guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString() + "#accept#thread serveur");
            }
            catch (IOException e)
            {
                System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); System.exit(1);
            }
        }

    }
    
    public void Shutdown()
    {
        for(int i = 0 ; i < thr.size(); i++){
            thr.get(i).stop();
        }
        try {
            SSocket.close();
            this.stop();
        }
        catch (IOException ex) {
            Logger.getLogger(ThreadServeurAirTrafficControllers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
