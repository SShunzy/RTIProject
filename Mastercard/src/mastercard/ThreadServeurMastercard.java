/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastercard;


import Protocole.SEBATRAP.ConsoleServeurMastercard;
import InterfacesRéseaux.*;
import java.net.*;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.*;
/**
 *
 * @author student
 */
public class ThreadServeurMastercard extends Thread
{
    private static String CertificateRepository = "/home/student/NetBeansProjects/RTIProjectGit/Certificats/";

    
    private int port;
    private int NbClients;
    private SourceTaches tachesAExecuter;
    private ConsoleServeurMastercard guiApplication;
    private SSLServerSocket SSocket = null;
    private ThreadClientMastercard[] thr;


    public ThreadServeurMastercard(int p, SourceTaches st, ConsoleServeurMastercard fs, int NC)
    {
       this.port = p; 
       this.tachesAExecuter = st;
       this.guiApplication = fs;
       this.NbClients = NC;
       thr = new ThreadClientMastercard[NbClients];
    }
    
    public void run()
    {
        try
        {
            KeyStore ServerKs = KeyStore.getInstance("JKS");
            ServerKs.load(new FileInputStream(ThreadServeurMastercard.CertificateRepository+"Mastercard.key") , "student1".toCharArray());
            
            SSLContext SslC = SSLContext.getInstance("SSLv3");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ServerKs, "student1".toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ServerKs);
            SslC.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            
            SSLServerSocketFactory SslSFac = SslC.getServerSocketFactory();
            
            this.SSocket = (SSLServerSocket) SslSFac.createServerSocket(port);

        }
        catch (IOException e)
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException ex) {
            Logger.getLogger(ThreadServeurMastercard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(ThreadServeurMastercard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(ThreadServeurMastercard.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Démarrage du pool de threads
        for (int i=0; i < this.NbClients; i++) // 3 devrait être constante ou une propriété du fichier de config
        {
            thr[i] = new ThreadClientMastercard (tachesAExecuter, "Thread du pool n°" + String.valueOf(i),guiApplication);
            thr[i].start();
        } 
        // Mise en attente du serveur
        SSLSocket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = (SSLSocket) SSocket.accept();
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
            thr[i].interrupt();
        }
        try {
            SSocket.close();
            this.interrupt();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServeurMastercard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
