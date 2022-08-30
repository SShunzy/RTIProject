/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.TICKMAP;

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
    public int getCountPassengers(int idVol);
    public ArrayList<Passagers> insertPassengers(String username, ArrayList<Passagers> passagers);
    public void removePassengers(int NrCommande);
    public ResultSet getSelectedVol(int idVol);
    public void setCartPayed(int NrCommande);
}
