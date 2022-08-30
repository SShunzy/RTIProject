/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.TICKMAP;

import Classes.Passagers;
import Classes.Vols;
import InterfacesRéseaux.Reponse;
import java.io.Serializable;
import java.security.cert.Certificate;

/**
 *
 * @author student
 */
public class ReponseTICKMAP implements Reponse, Serializable
{
    public static int LOGIN_OK = 201;
    public static int LOGIN_NOT_FOUND = 101;
    public static int WRONG_PASSWORD = 301;
    public static int CERTIFICATE_SENT = 401;
    public static int MASTER_SECRET = 501;
    public static int VOL_FOUND = 601;
    public static int NO_VOL_FOUND = 701;
    public static int VOL_FULL = 801;
    public static int PASSENGERS_ADDED = 901;
    
    private int codeRetour;
    private String chargeUtile;
    private Certificate certificat;
    private Vols[] vols;
    private byte[] cryptedMessage;
    
    public ReponseTICKMAP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    
    public ReponseTICKMAP(int c, byte[] cryptedMessage){
        codeRetour = c; this.cryptedMessage = cryptedMessage;
    }
    
    public ReponseTICKMAP(int c, Certificate certificat)
    {
        codeRetour = c; this.certificat = certificat;
    }
    
    public ReponseTICKMAP(int c, Vols[] vols)
    {
        codeRetour = c; this.vols = vols;
    }
    
    public ReponseTICKMAP(int c)
    {
        this.codeRetour = c;
    }
    
    public byte[] getCryptedMessage(){return this.cryptedMessage;}
    public Certificate getCertificate(){ return certificat;}
    public Vols[] getVols(){ return vols; }
    
    @Override
    public int getCode() { return codeRetour; }
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; } 
}
