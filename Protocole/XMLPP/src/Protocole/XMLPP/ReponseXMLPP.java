/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.XMLPP;

import InterfacesRéseaux.Reponse;
import java.io.Serializable;

/**
 *
 * @author student
 */
public class ReponseXMLPP implements Reponse, Serializable{

    public static int PARSING_OK = 100;
    public static int PARSING_KO = 200;
    
    private int code;
    
    public ReponseXMLPP(int code){
        this.code = code;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
}
