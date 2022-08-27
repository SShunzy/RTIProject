/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.IACOP;

import InterfacesRéseaux.Reponse;

/**
 *
 * @author student
 */
public class ReponseIACOP implements Reponse {

    public static int LOGIN_OK = 101;
    public static int LOGIN_KO = 102;
    public static int RESPONSE_TO_TAG = 201;
    
    private int code;
    private Object chargeUtile,tag;
    
    public ReponseIACOP(int code, Object charge, Object tag){
        this.code = code;
        this.chargeUtile = charge;
        this.tag = tag;
    }
    
    @Override
    public int getCode() {
        return this.code;
    }
    
    public Object getChargeUtile(){
        return this.chargeUtile;
    }
    
    public Object getTag(){
        return this.tag;
    }
    
    public Object getAdresse(){
        return this.chargeUtile;
    }
    
    public Object getPort(){
        return this.tag;
    }
    
}
