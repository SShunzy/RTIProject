/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Payment.Serveur;


import InterfacesRéseaux.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class ThreadServeurPayment extends Thread
{
    private int port, NbClients;
    
    private SourceTaches tachesAExecuter;
    private ConsoleServeurPayment guiApplication;
    private ServerSocket SSocket = null;
    private ThreadClientPayment[] thr;


    public ThreadServeurPayment(int p, SourceTaches st, ConsoleServeurPayment fs,int nbClient)
    {
       this.NbClients = nbClient; 
       this.port = p; 
       this.tachesAExecuter = st;
       this.guiApplication = fs;
       thr = new ThreadClientPayment[NbClients];
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
        // Démarrage du pool de threads
        for (int i=0; i < this.NbClients; i++) // 3 devrait être constante ou une propriété du fichier de config
        {
            thr[i] = new ThreadClientPayment (tachesAExecuter, "Thread du pool n°" + String.valueOf(i),guiApplication);
            thr[i].start();
        }      
        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = SSocket.accept();
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
        for(int i = 0; i < NbClients; i++)
        {
            thr[i].stop();
        }
        try {
            SSocket.close();
            this.stop();
        }
        catch (IOException ex) {
            Logger.getLogger(ThreadServeurPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
