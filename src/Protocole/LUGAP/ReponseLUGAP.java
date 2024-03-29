/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.LUGAP;

import InterfacesRéseaux.Reponse;
import Classes.Baggages;
import Classes.Vols;
import java.io.*;

/**
 *
 * @author student
 */
public class ReponseLUGAP implements Reponse, Serializable
{
    public static int LOGIN_OK = 201;
    public static int LOGIN_NOT_FOUND = 501;
    public static int WRONG_PASSWORD = 401;
    public static int VOLS_FOUND = 601;
    public static int VOLS_NULL = 801;
    public static int BAGGAGES_SEND = 901;
    public static int ALL_BAGGAGES_LOADED = 1001;
    public static int NOT_ALL_BAGGAGES_LOADED = 1002;
    
    private int codeRetour;
    private String chargeUtile;
    private Object[] returnArray;
    public ReponseLUGAP(int c){
        codeRetour = c;
    }
    public ReponseLUGAP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    public ReponseLUGAP(int c, Object[] vol)
    {
        codeRetour = c; setReturnArray(vol);
    }
  
    
    public int getCode() { return codeRetour; }
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; } 
    
    public Object[] getReturnArray(){return this.returnArray;}
    public void setReturnArray(Object[] obj){ this.returnArray = obj;}
}
