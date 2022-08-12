/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.PAYP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import Payment.Serveur.ConsoleServeurPayment;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author student
 */
public class RequetePAYP implements Requete, Serializable
{
    private static String CertificateRepository = "/home/student/NetBeansProjects/RTIProjectGit/Certificats/";
    
    public static int REQUEST_PAYMENT = 1;
    
    private int type;
    private byte[] CryptedMessage;
    private byte[] Signature;
    
    public RequetePAYP(int type, byte[] message, byte[] signature)
    {
        this.type = type;
        this.CryptedMessage = message;
        this.Signature = signature;
    }
    
    private void sendReponsePAYP(Socket sock, ReponsePAYP rep)
    {
        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(rep); oos.flush();
        }
        catch (IOException e)
        {
            System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
        }
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) 
    {
        if(type == RequetePAYP.REQUEST_PAYMENT){
            System.out.println("Requete Payment");
            this.traiteRequetePayment(s,(ConsoleServeurPayment) cs);
            return false;
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
        
        Security.addProvider(new BouncyCastleProvider());

        try {
            
            //Comparaison des HMACs//
            
            KeyStore ksMac = KeyStore.getInstance("JCEKS");
            ksMac.load(new FileInputStream(RequetePAYP.CertificateRepository+"Agents.jce"),"student1".toCharArray());
            
            Mac HmacEmployé = Mac.getInstance("HMAC-MD5","BC");
            HmacEmployé.init(ksMac.getKey("lionelthys", "student1".toCharArray()));
            
            //Décryptage du message//
            
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(RequetePAYP.CertificateRepository+"Payment.key") , "student1".toCharArray());

            Cipher DecryptMessage = Cipher.getInstance("RSA");
            PrivateKey pk = (PrivateKey) ks.getKey("PaymentKey", "student1".toCharArray());
            System.out.println("private key = "+pk);
            DecryptMessage.init(Cipher.DECRYPT_MODE,pk);

            String Message = new String(DecryptMessage.doFinal(this.CryptedMessage));
                
            ReponsePAYP rep;
            if(MessageDigest.isEqual(HmacEmployé.doFinal(Message.getBytes()), this.Signature)){
                System.out.println("Message authentifié");

                String[] MessageElements = Message.split("@");
                System.out.println("Payment> Numéro de carte reçu: "+MessageElements[0]);
                System.out.println("Payment> Nom reçu: "+MessageElements[1]);
                System.out.println("Payment> Prix reçu: "+MessageElements[2]);
                
                boolean random = new SecureRandom().nextBoolean();
                if(random){
                    System.out.println("Paiement réussi");
                    rep = new ReponsePAYP(ReponsePAYP.PAYMENT_OK);
                }
                else{
                    System.out.println("Paiement échoué");
                    rep = new ReponsePAYP(ReponsePAYP.PAYMENT_KO);
                }
            }
            else{
                System.out.println("Message NON authentifié\n Abandon de la commande!!");
                rep = new ReponsePAYP(ReponsePAYP.PAYMENT_KO);
            }
            
            this.sendReponsePAYP(sock, rep);

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | NoSuchPaddingException | UnrecoverableKeyException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException ex) {
            Logger.getLogger(RequetePAYP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
