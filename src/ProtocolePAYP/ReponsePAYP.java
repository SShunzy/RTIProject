/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolePAYP;

import InterfacesRéseaux.Reponse;
import java.io.Serializable;

/**
 *
 * @author student
 */
public class ReponsePAYP implements Reponse, Serializable
{
    public static int PAYMENT_OK = 101;
    public static int PAYMENT_KO = 202;
    
    private int codeRetour;
    
    @Override
    public int getCode() { return this.codeRetour;    }
    
}
