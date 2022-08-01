/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleLUGAP;

import InterfacesRéseaux.Reponse;
import java.io.*;

/**
 *
 * @author student
 */
public class ReponseLUGAP implements Reponse, Serializable
{
    public static int LOGIN_OK = 201;
    public static int ID_NOT_FOUND = 501;
    public static int KEY_GENERATED = 202;
    public static int WRONG_PASSWORD = 401;
    private int codeRetour;
    private String chargeUtile;
    public ReponseLUGAP(int c, String chu)
    {
        codeRetour = c; setChargeUtile(chu);
    }
    public int getCode() { return codeRetour; }
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile) { this.chargeUtile = chargeUtile; } 
}
