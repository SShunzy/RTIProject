/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Serveur;

import Classes.Baggages;
import java.sql.ResultSet;
import InterfacesRéseaux.ConsoleServeur;
import Protocole.LUGAP.ReponseLUGAP;

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
    public boolean checkBillets(String number, int numberAccompanying);
    public void sendBroadcastResponse(ReponseLUGAP rep);
    public boolean updateBaggages(Baggages[] baggageArray);
}
