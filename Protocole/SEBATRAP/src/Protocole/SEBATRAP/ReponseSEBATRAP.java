/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.SEBATRAP;

import InterfacesRéseaux.Reponse;
import java.io.Serializable;

/**
 *
 * @author student
 */
public class ReponseSEBATRAP implements Reponse, Serializable{

    public static int PAYMENT_OK = 1;
    public static int PAYMENT_KO = 2;
    
    private int code;
    
    public ReponseSEBATRAP(int code){
        this.code = code;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
}
