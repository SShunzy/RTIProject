/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.IACOP;

import IACHAT.Serveur.ConsoleServeurIACOP;
import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MulticastSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class RequeteIACOP implements Requete, Serializable{

    public static int LOGIN_GROUP = 1;
    public static int POST_QUESTION = 2;
    public static int ANSWER_QUESTION = 3;
    public static int POST_EVENT = 4;
    
    private int code;
    private String message;
    private int tagQuestion;
    private String nom;
    private byte[] digest;
    private Timestamp date;
    
    public RequeteIACOP(int code, String nom, byte[] digest){
        this.code = code;
        this.nom = nom;
        this.digest = digest;
    }
    
    public RequeteIACOP(int code, String message, int tag, byte[] digest){
        this.code = code;
        this.message = message;
        this.tagQuestion = tag;
        this.digest = digest;
    }
    
    public RequeteIACOP(int code,String nom, Timestamp date, byte[]digest){
        this.code = code;
        this.nom = nom;
        this.date = date;
        this.digest = digest;
    }
    
    public RequeteIACOP(int code, String message, int tag){
        this.code = code;
        this.message = message;
        this.tagQuestion = tag;
    }
    
    public RequeteIACOP(int code, String message, int tag,String nom, byte[] digest){
        this.code = code;
        this.message = message;
        this.tagQuestion = tag;
        this.nom = nom;
        this.digest = digest;
    }
    
    public RequeteIACOP(byte[] byteArray){
        String requeteString = new String(byteArray);
        String[] stringArray = requeteString.split("\n\r");
        
        this.code = Integer.parseInt(stringArray[0]);
        this.message = stringArray[1];
        this.tagQuestion = Integer.parseInt(stringArray[2]);
        this.nom = stringArray[3];
        this.digest = stringArray[4].getBytes();
    }
    
    @Override
    public String toString(){
        return code+"\n\r"+message+"\n\r"+tagQuestion+"\n\r"+nom+"\n\r"+digest;
    }
    
    public byte[] toByteArray(){
        return toString().getBytes();
    }
    

    
    public void createRunnable(MulticastSocket s, ConsoleServeur cs) {
        
        try {
            MessageDigest integrityMessage;
            integrityMessage = MessageDigest.getInstance("SHA-1","BC");
            integrityMessage.update(String.valueOf(tagQuestion).getBytes());
            integrityMessage.update(nom.getBytes());
            if(MessageDigest.isEqual(digest,integrityMessage.digest(message.getBytes()))){
                System.out.println("Message identifié");
                cs.TraceEvenements(this.toString());
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(RequeteIACOP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(code == RequeteIACOP.LOGIN_GROUP){
            this.traiteRequeteLoginGroup(s,(ConsoleServeurIACOP) cs);
            return false;
        }
        else{
            System.out.println("Requete non implémentée\nFermeture du client");
            return false;
        }
    }
    
    private void traiteRequeteLoginGroup(Socket s, ConsoleServeurIACOP cs){                
        ReponseIACOP rep;
        if(cs.isClientValid(nom, date, digest)){
            rep = new ReponseIACOP(ReponseIACOP.LOGIN_OK,cs.getAddress(),cs.getPort());

        }
        else{
            rep = new ReponseIACOP(ReponseIACOP.LOGIN_KO);
        }
        ObjectOutputStream oos;
        System.out.println("Envoi de l'adresse:"+rep.getAdresse()+" et du port:"+rep.getPort());
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);oos.flush();

        } catch (IOException ex) {
            Logger.getLogger(RequeteIACOP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
