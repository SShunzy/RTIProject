/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleTICKMAP;

import Billets.Serveur.ConsoleServeurBillets;
import Classes.Passagers;
import Classes.Vols;
import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.KeyStore.SecretKeyEntry;
import java.security.cert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.*;
import javax.crypto.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author student
 */
public class RequeteTICKMAP implements Requete,Serializable
{
    public static String CertificateRepository = "/home/student/NetBeansProjects/RTIProjectGit/Certificats/";
    public static int REQUEST_LOGIN = 1;
    public static int REQUEST_LOGOUT = 2;
    public static int REQUEST_CERTIFICATE = 3;
    public static int REQUEST_TEST_CERTIFICATE = 4;
    public static int REQUEST_ADD_PASSENGERS = 5;
    public static int PAYMENT_REFUSED = 6;
    
    private int type;
    private String Login;
    private byte[] Message;
    private byte[] ClientKey;
    private AlgorithmParameters param;
    private Timestamp dateDigest;
    
    public RequeteTICKMAP(int type, String chu, byte[] digest, Timestamp date)
    {
        this.type=type;
        this.Login = chu;
        this.Message = digest;
        this.dateDigest = date;
    }
    
    public RequeteTICKMAP(int type, byte[] Message, byte[] ClientKey, AlgorithmParameters parameters)
    {
        this.type = type;
        this.Message = Message;
        this.ClientKey = ClientKey;
        this.param = parameters;
    }
    
    public RequeteTICKMAP(int type, String chu)
    {
        this.type = type;
        this.Login = chu;
    }
    
    public RequeteTICKMAP(int type, byte[] message)
    {
        this.type = type;
        this.Message = message;
    }
    
    public RequeteTICKMAP(int type)
    {
        this.type = type;
    }
    
    public String getLogin()
    {
        return Login;
    }
    
    public byte[] getDateDigest(){
        return this.dateDigest.toGMTString().getBytes();
    }
    
    public byte[] getDigest()
    {
        return Message;
    }

    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) 
    {
        if(type == REQUEST_LOGIN)
        {
            System.out.println("Requête Log In");
            try {
                traiteRequeteLogin(s,(ConsoleServeurBillets)cs);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        else if(type == REQUEST_CERTIFICATE)
        {
            System.out.println("Requête Certificat");
            try {
                this.traiteRequeteCertificat(s, (ConsoleServeurBillets)cs);
            } catch (KeyStoreException | NoSuchProviderException | IOException | NoSuchAlgorithmException | CertificateException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        else if(type == REQUEST_TEST_CERTIFICATE)
        {
            System.out.println("Requête Test Cretificat");
            try {
                this.traiteRequeteTestCertificat(s,(ConsoleServeurBillets) cs);
            } catch (KeyStoreException | NoSuchAlgorithmException | IOException | CertificateException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnrecoverableKeyException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }        
        else if(type == REQUEST_ADD_PASSENGERS)
        {
            System.out.println("Requête Passengers");
            this.traiteRequeteAddPassagers(s, (ConsoleServeurBillets)cs);
            return true;
        }
        else if(type == PAYMENT_REFUSED){
            System.out.println("Requête Paiement Refusé");
            this.traitePaymentRefused(s,(ConsoleServeurBillets) cs);
            return true;
        }
        else if(type == REQUEST_LOGOUT)
        {
            System.out.println("Requête Log Out");
            this.traiteRequeteLogOut(s,(ConsoleServeurBillets) cs);
            return false;
        }
        else
        {
            System.out.println("Requete non implémentée. Fermeture du client");
            return false;
        }
       
    }
    
    private SecretKey getSessionKey(Socket sock) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException
    {       
        SecretKeyEntry entry = null;
        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(new FileInputStream("/home/student/Certificats/Sessions.jce") , "student1".toCharArray());
        entry = (SecretKeyEntry) ks.getEntry(sock.getInetAddress().toString(), new KeyStore.PasswordProtection(sock.getInetAddress().toString().toCharArray()));
        System.out.println("clé secrète = "+entry.getSecretKey().toString());
        return entry.getSecretKey();
    }
    
    private void setSessionKey(Socket sock,SecretKey sessionKey) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException
    {
        if(sessionKey != null)
        {
            SecretKeyEntry entry = new SecretKeyEntry(sessionKey);
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(new FileInputStream("/home/student/Certificats/Sessions.jce") , "student1".toCharArray());
            ks.setEntry(sock.getInetAddress().toString(), entry, new KeyStore.PasswordProtection(sock.getInetAddress().toString().toCharArray())); 
            ks.store(new FileOutputStream("/home/student/Certificats/Sessions.jce") , "student1".toCharArray());
        }
    }
    
    private void sendReponseTICKMAP(Socket sock, ReponseTICKMAP rep)
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
    
    private void traiteRequeteLogin(Socket sock, ConsoleServeurBillets cs) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        // la charge utile est le nom du client
        String Pwd = (String)cs.getTable(getLogin());
        
        cs.TraceEvenements(adresseDistante+"#Login de "+
        getLogin()+"#"+Thread.currentThread().getName());
        ReponseTICKMAP rep;
        if (Pwd != null)
        {
            Security.addProvider(new BouncyCastleProvider());
            System.out.println("Login trouvé pour " + getLogin());
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            md.update(getLogin().getBytes());
            md.update(this.getDateDigest());
            if(MessageDigest.isEqual(getDigest(), md.digest(Pwd.getBytes())))
            {
                System.out.println("Login OK");
                rep = new ReponseTICKMAP(ReponseTICKMAP.LOGIN_OK, getLogin());
            }
            else
            {
                System.out.println("Login KO");
                rep = new ReponseTICKMAP(ReponseTICKMAP.WRONG_PASSWORD, getLogin());
            }
        }
        else
        {
            System.out.println("Login non trouvé pour " + getLogin());
            Pwd="?@?";
            rep = new ReponseTICKMAP(ReponseTICKMAP.LOGIN_NOT_FOUND, getLogin());
        }
        // Construction d'une réponse //
        this.sendReponseTICKMAP(sock, rep);
       
    }
    
    private void traiteRequeteCertificat(Socket sock, ConsoleServeurBillets cs) throws KeyStoreException, NoSuchProviderException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException
    {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(RequeteTICKMAP.CertificateRepository+"Serveur.key") , "student1".toCharArray());

        java.security.cert.Certificate certif = ks.getCertificate("TB");                                       //Le certificat a comme alias tb comme on l'a définis avec Keytool IUI
        
        ReponseTICKMAP rep = new ReponseTICKMAP(ReponseTICKMAP.CERTIFICATE_SENT,certif);
        this.sendReponseTICKMAP(sock, rep);
    }
    
    private void traiteRequeteTestCertificat(Socket sock, ConsoleServeurBillets cs) throws FileNotFoundException, KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException
    {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(RequeteTICKMAP.CertificateRepository+"Serveur.key") , "student1".toCharArray());
        
        Cipher DecryptClient = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher DecryptServeur = Cipher.getInstance("RSA");
        DecryptServeur.init(Cipher.UNWRAP_MODE, ks.getKey("TB", "student1".toCharArray()));
        
        SecretKey SessionKey = (SecretKey) DecryptServeur.unwrap(ClientKey,"AES", Cipher.SECRET_KEY);
        
        boolean isContained = false;
        Enumeration en = ks.aliases();
        String aliasCourant = null;
        Vector vectaliases = new Vector();
        while (en.hasMoreElements()) vectaliases.add(en.nextElement());
        Object[] aliases = vectaliases.toArray();
        //OU : String[] aliases = (String []) (vectaliases.toArray(new String[0]));
        for (int i = 0; i < aliases.length; i++)
        {
            aliasCourant = (String)aliases[i];
            X509Certificate certif = (X509Certificate)ks.getCertificate(aliasCourant);
            PublicKey cléPublique = certif.getPublicKey();
            if(cléPublique == SessionKey)
            {
                isContained = true;
                i = aliases.length;
            }
        }
        if(!isContained)
        {
            //ks.setKeyEntry("Client "+ks.size(), SessionKey, "student1".toCharArray(),null);
        }
        
        this.setSessionKey(sock, SessionKey);
        try {
            SessionKey = this.getSessionKey(sock);
        } catch (UnrecoverableEntryException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DecryptClient.init(Cipher.DECRYPT_MODE, SessionKey,param);
        String MessageDecrypt = new String(DecryptClient.doFinal(Message),"UTF8");
        System.out.println("Message Décrypté :" + MessageDecrypt);
        
        cs.setSessionKey(SessionKey);
        this.traiteRequeteVols(sock, cs);
        
    }
    
    private void traiteRequeteLogOut(Socket sock, ConsoleServeurBillets cs)
    {
        System.out.println("RequeteLogOut");
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequete : adresse distante = " + adresseDistante);
        try {
            // la charge utile est le nom du client //
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void traiteRequeteVols(Socket sock, ConsoleServeurBillets cs)
    {
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequeteVols : adresse distante = " + adresseDistante);
        // la charge utile est le nom du client
        ResultSet rs = cs.getVols();
        cs.TraceEvenements(adresseDistante+"# Recherche des Vols#"+Thread.currentThread().getName());
        ReponseTICKMAP rep;
        Vols[] VolArray;
        try {
            int i = 0;
            if(rs.last()){
                VolArray = new Vols[rs.getRow()];
                rs.beforeFirst();
            }
            else VolArray = new Vols[0];
            System.out.println("Test: "+VolArray.length);
            try {
                while(VolArray.length != 0 && rs.next())
                {
                    VolArray[i] = new Vols(rs.getInt(1),rs.getString(2),rs.getTimestamp(3),rs.getTimestamp(4),rs.getTimestamp(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getInt(9));
                    i++;
                }
                // Construction d'une réponse
            } catch (SQLException ex) {
                System.err.println("Erreur ? [" + ex.getMessage() + "]");
            }
            if(VolArray.length == 0){
                rep = new ReponseTICKMAP(ReponseTICKMAP.NO_VOL_FOUND, VolArray);
                System.out.println("Pas de vol trouvé");
            }
            else{
                rep = new ReponseTICKMAP(ReponseTICKMAP.VOL_FOUND, VolArray);
                System.out.println(VolArray.length+" vols trouvé");
            }
            this.sendReponseTICKMAP(sock, rep);
        } catch (SQLException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void traiteRequeteAddPassagers(Socket sock, ConsoleServeurBillets cs)
    {
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequetePassagers : adresse distante = " + adresseDistante);
        ArrayList<Passagers> PassagersArray = new ArrayList<Passagers>();
        boolean isVolFull = false;
        ReponseTICKMAP rep = null;
        cs.TraceEvenements(adresseDistante+"# Recherche des Passagers#"+Thread.currentThread().getName());
        
        String MessageDecrypt = "";
        Cipher DecryptRequete;
        try {
            DecryptRequete = Cipher.getInstance("AES/ECB/PKCS5Padding");
            DecryptRequete.init(Cipher.DECRYPT_MODE, this.getSessionKey(sock),param);
            MessageDecrypt = new String(DecryptRequete.doFinal(Message),"UTF8");
            System.out.println("Message Décrypté = "+MessageDecrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | KeyStoreException | IOException | CertificateException | UnrecoverableEntryException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] passagersString = MessageDecrypt.split("#");
        String[] passagersValues = new String[1];
        for(int i = 0; i<passagersString.length; i++)
        {
            passagersValues = passagersString[i].split(",");
            try {
                PassagersArray.add(new Passagers(Integer.parseInt(passagersValues[0]),passagersValues[1],passagersValues[2], new Timestamp(new SimpleDateFormat("dd-MM-yyyy").parse(passagersValues[3]).getTime())));
            } catch (ParseException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Vols selectedVol = new Vols();
        ResultSet VolRs = cs.getSelectedVol(Integer.parseInt(passagersValues[0]));
        try {
            while(VolRs.next())
            {
                selectedVol = new Vols(VolRs.getInt(1),VolRs.getString(2),VolRs.getTimestamp(3),VolRs.getTimestamp(4),VolRs.getTimestamp(5),VolRs.getInt(6),VolRs.getInt(7),VolRs.getString(8),VolRs.getInt(9));
                System.out.println("Selected Vol = "+selectedVol.ID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResultSet rs = cs.getCountPassengers(Integer.parseInt(passagersValues[0]));
        try {
            while(rs.next())
            {
                if( rs.getInt(1) + PassagersArray.size() > selectedVol.NbPlace ) isVolFull = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("isVolFull = "+isVolFull);
        
        if(isVolFull)
        {
            rep = new ReponseTICKMAP(ReponseTICKMAP.VOL_FULL);
        }
        else
        {
            PassagersArray = cs.insertPassengers(PassagersArray);
            int prix = selectedVol.Prix * PassagersArray.size();
            
            String returnString = prix + "@" + PassagersArray.get(0).NrCommande + "@";
            for(int i = 0; i < PassagersArray.size(); i++){
                if(i > 0)
                    returnString +="#";
                returnString += PassagersArray.get(i).SeatNumber;
            }
            
            System.out.println("ReturnString = "+returnString);
            
            Cipher EncryptClient;
            try {
                EncryptClient = Cipher.getInstance("AES/ECB/PKCS5Padding");
                EncryptClient.init(Cipher.ENCRYPT_MODE, this.getSessionKey(sock));
                byte[] encryptString = EncryptClient.doFinal(returnString.getBytes());
                rep = new ReponseTICKMAP(ReponseTICKMAP.PASSENGERS_ADDED,encryptString);
                   
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | KeyStoreException | IOException | CertificateException | UnrecoverableEntryException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.sendReponseTICKMAP(sock, rep);   
        }
    }
    
    private void traitePaymentRefused(Socket sock, ConsoleServeurBillets cs)
    {
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteRequetePassagers : adresse distante = " + adresseDistante);
        ArrayList<Passagers> PassagersArray = new ArrayList<Passagers>();
        cs.TraceEvenements(adresseDistante+"# Suppression des passagers#"+Thread.currentThread().getName());
        
        String MessageDecrypt = "";
        Cipher DecryptRequete;
        try {
            DecryptRequete = Cipher.getInstance("AES/ECB/PKCS5Padding");
            DecryptRequete.init(Cipher.DECRYPT_MODE, this.getSessionKey(sock),param);
            MessageDecrypt = new String(DecryptRequete.doFinal(Message),"UTF8");
            System.out.println("Message Décrypté = "+MessageDecrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | KeyStoreException | IOException | CertificateException | UnrecoverableEntryException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int NuméroCommandeASupprimer = Integer.parseInt(MessageDecrypt);
        cs.removePassengers(NuméroCommandeASupprimer);
        
    }
}
