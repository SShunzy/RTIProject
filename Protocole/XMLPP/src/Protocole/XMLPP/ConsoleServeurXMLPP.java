/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.XMLPP;

import InterfacesRéseaux.ConsoleServeur;

/**
 *
 * @author student
 */
public interface ConsoleServeurXMLPP extends ConsoleServeur{
    
    public int parseXMLToFlight(String XMLString);
}
