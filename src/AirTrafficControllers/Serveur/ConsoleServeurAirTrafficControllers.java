/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AirTrafficControllers.Serveur;

import Classes.Vols;
import InterfacesRéseaux.ConsoleServeur;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author student
 */
public interface ConsoleServeurAirTrafficControllers extends ConsoleServeur 
{
    public ArrayList<Vols> getAvailableFlights() throws SQLException; 
}
