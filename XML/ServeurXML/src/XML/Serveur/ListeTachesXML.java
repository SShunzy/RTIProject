/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML.Serveur;

import InterfacesRéseaux.SourceTaches;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author student
 */
public class ListeTachesXML implements SourceTaches
{
    private LinkedList listeTaches;
    
    public ListeTachesXML()
    {
        listeTaches = new LinkedList();
    }
    
    @Override
    public synchronized Socket getTache() throws InterruptedException
    {
        System.out.println("getTache avant wait");
        while (!existTaches()) wait();
        return (Socket)listeTaches.remove();
    }
    
    @Override
    public synchronized boolean existTaches()
    {
        return !listeTaches.isEmpty();
    }
    
    @Override
    public synchronized void recordTache (Socket r)
    {
        listeTaches.addLast(r);
        System.out.println("ListeTaches : tache dans la file");
        notify();
    }
}
