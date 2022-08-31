/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML.Serveur;


import InterfacesRéseaux.*;
import Protocole.XMLPP.ConsoleServeurXMLPP;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author student
 */
public class ThreadServeurXML extends Thread
{
    private int port;
    
    private SourceTaches tachesAExecuter;
    private ConsoleServeurXMLPP guiApplication;
    private ServerSocket SSocket = null;
    private ArrayList<ThreadClientXML> thr;


    public ThreadServeurXML(int p, SourceTaches st, ConsoleServeurXMLPP fs)
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
                
                tachesAExecuter.recordTache(CSocket);
                System.out.println("Socket mis dans la file");
                
                //Création d'un thread à chaque demande//                
                int newNumberOfThread = thr.size()+1;
                ThreadClientXML ThrClient = new ThreadClientXML(tachesAExecuter, "Thread du pool n°" + String.valueOf(newNumberOfThread),guiApplication);
                ThrClient.run();
                thr.add(ThrClient);
                
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
            thr.get(i).interrupt();
        }
        try {
            SSocket.close();
            this.interrupt();
        }
        catch (IOException ex) {
            Logger.getLogger(ThreadServeurXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
