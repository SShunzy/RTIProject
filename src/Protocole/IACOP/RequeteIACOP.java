/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.IACOP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.net.Socket;

/**
 *
 * @author student
 */
public class RequeteIACOP implements Requete {

    public static int LOGIN_GROUP = 1;
    public static int POST_QUESTION = 2;
    public static int ANSWER_QUESTION = 3;
    public static int POST_EVENT = 4;
    
    private int code;
    private Object chargeUtile;
    private Object tagQuestion;
    private byte[] returnArray;
    
    public RequeteIACOP(int code, Object nom, byte[] digest){
        this.code = code;
        this.chargeUtile = nom;
        this.returnArray = digest;
    }
    
    public RequeteIACOP(int code, Object question, Object tag, byte[] digest){
        this.code = code;
        this.chargeUtile = question;
        this.tagQuestion = tag;
        this.returnArray = digest;
    }
    
    public RequeteIACOP(int code, Object reponse, Object tag){
        this.code = code;
        this.chargeUtile = reponse;
        this.tagQuestion = tag;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
