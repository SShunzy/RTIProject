/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolePAYP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author student
 */
public class RequetePAYP implements Requete, Serializable
{
    private int type;
    
    public RequetePAYP(int type)
    {
        this.type = type;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) 
    {
        if(type == 1){
            return true;
        }
        else
            return false;
    }
    
}
