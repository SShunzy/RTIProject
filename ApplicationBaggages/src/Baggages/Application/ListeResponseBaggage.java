/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Application;

import InterfacesRéseaux.ListenerReponse;
import InterfacesRéseaux.Reponse;
import java.util.LinkedList;

/**
 *
 * @author student
 */
public class ListeResponseBaggage implements ListenerReponse {
    
    private LinkedList listeResponse;
    
    public ListeResponseBaggage(){
        this.listeResponse = new LinkedList();
    }

    @Override
    public synchronized Reponse getResponse()  throws InterruptedException {
        System.out.println("getResponse avant wait");
        while (!existResponse()) wait();
        return (Reponse)listeResponse.remove();
    }

    @Override
    public synchronized boolean existResponse() {
        return !listeResponse.isEmpty();
    }

    @Override
    public synchronized void recordReponse(Reponse rep) {
        listeResponse.addLast(rep);
        System.out.println("ListeResponse : Reponse dans la file");
        notify();
    }
    
}
