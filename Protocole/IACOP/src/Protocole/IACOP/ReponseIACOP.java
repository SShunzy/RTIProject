/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.IACOP;

import InterfacesRéseaux.Reponse;
import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author student
 */
public class ReponseIACOP implements Reponse, Serializable {

    public static int LOGIN_OK = 101;
    public static int LOGIN_KO = 102;
    
    private int code;
    private InetAddress address;
    private int port;
    
    public ReponseIACOP(int code){
        this.code = code;
    }
    
    public ReponseIACOP(int code, InetAddress address, int port){
        this.code = code;
        this.address = address;
        this.port = port;
    }
    
    @Override
    public int getCode() {
        return this.code;
    }
   
    
    public InetAddress getAdresse(){
        return this.address;
    }
    
    public int getPort(){
        return this.port;
    }
    
}
