/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleLUGAP;

import InterfacesRéseaux.Reponse;
import Classes.Baggages;
import Classes.Vols;
import java.io.*;
import java.sql.ResultSet;

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
    private int codeRetour;
    private String chargeUtile;
    private Vols[] TableVols;
    private Baggages[] TableBagagges;
    public ReponseLUGAP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    public ReponseLUGAP(int c, Vols[] vol)
    {
        codeRetour = c; setTableVol(vol);
    }
    
    public ReponseLUGAP(int c, Baggages[] bg)
    {
        codeRetour = c; setBaggages(bg);
    }
    
    public int getCode() { return codeRetour; }
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; } 
    
    public Vols[] getTableVol(){return TableVols;}
    public void setTableVol(Vols[] Vol){ this.TableVols = Vol;}
    
    public Baggages[] getBaggages(){return TableBagagges;}
    public void setBaggages(Baggages[] bg){this.TableBagagges = bg;}
}
