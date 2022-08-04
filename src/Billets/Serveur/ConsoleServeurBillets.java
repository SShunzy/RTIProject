/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Billets.Serveur;

import Classes.Passagers;
import InterfacesRéseaux.ConsoleServeur;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.crypto.SecretKey;

/**
 *
 * @author student
 */
public interface ConsoleServeurBillets extends ConsoleServeur
{
    public void setSessionKey(SecretKey sk);
    public SecretKey getSecretKey();
    public Object getTable(String chu);
    public ResultSet getVols();
    public ResultSet getCountPassengers(int idVol);
    public int[] insertPassengers(ArrayList<Passagers> passagers);
    public int getMaxIdPassengers();
    public ResultSet getSelectedVol(int idVol);
}
