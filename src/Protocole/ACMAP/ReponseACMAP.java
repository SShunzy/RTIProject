/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.ACMAP;

import InterfacesRéseaux.Reponse;
import java.io.Serializable;

/**
 *
 * @author student
 */
public class ReponseACMAP implements Reponse, Serializable
{
    public static int SEND_FLIGHTS = 101;
    public static int NO_FLIGHT_FOUND = 102;
    public static int CHECKIN_OFF_SENT = 201;
    public static int BAGGAGES_LOADED = 301;
    public static int BAGGAGES_NOT_LOADED = 302;
    public static int SEND_LANES = 401;
    public static int NO_LANE_FREE = 402;
    public static int LOCK_LANE_OK = 501;
    public static int LOCK_LANE_KO = 502;
    public static int UNLOCK_LANE_OK = 601;
    public static int UNLOCK_LANE_KO = 602;

    
    private final int CodeRetour;
    private final Object[] returnArray; 
    
    public ReponseACMAP(int code){
        this.CodeRetour = code;
        this.returnArray = null;
    }
    
    public ReponseACMAP(int code, Object[] array)
    {
        this.CodeRetour = code;
        this.returnArray = array;
    }
    
    @Override
    public int getCode() {
        return this.CodeRetour;
    }
    
    public Object[] getArray(){
        return this.returnArray;
    }
    
}
