/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.SEBATRAP;

import InterfacesRéseaux.ConsoleServeur;

/**
 *
 * @author student
 */
public interface ConsoleServeurMastercard extends ConsoleServeur 
{
    public boolean isLoadedEnough(String numéro, String nom, double prix);
    public int doPayment(String numéro, String nom, double prix);

}
