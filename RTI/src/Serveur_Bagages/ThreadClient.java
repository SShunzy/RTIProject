/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur_Bagages;

import InterfacesRéseaux.SourceTaches;

/**
 *
 * @author student
 */
public class ThreadClient extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    
    private Runnable tacheEnCours;
    
    public ThreadClient(SourceTaches st, String n)
    {
        this.tachesAExecuter = st;
        this.nom = n;
    }
    
    public void run()
    {
        while (!isInterrupted())
        {
            try
            {
                System.out.println(nom+"> Thread client avant get");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
                System.out.println(nom+"> Interruption : " + e.getMessage());
            }
                System.out.println(nom+"> run de tachesencours");
                tacheEnCours.run();    
        }
    }
}
