/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.ACMAP;

import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author student
 */
public class RequeteACMAP implements Requete, Serializable
{

    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
