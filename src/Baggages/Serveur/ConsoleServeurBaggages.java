/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Serveur;

import java.sql.ResultSet;
import InterfacesRéseaux.ConsoleServeur;

/**
 *
 * @author student
 */
public interface ConsoleServeurBaggages extends ConsoleServeur
{
    public Object getTable(String chu);
    public ResultSet getVols();
    public ResultSet getBaggages(int idvol);
    public boolean isAllBaggagesLoaded(int idvol);
}
