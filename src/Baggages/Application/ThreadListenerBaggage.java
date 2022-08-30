/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Baggages.Application;

import InterfacesRéseaux.ListenerReponse;
import Protocole.LUGAP.ReponseLUGAP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author student
 */
public class ThreadListenerBaggage extends Thread {
    private Socket listenerSocket;
    private ListenerReponse sourceResponse;
    private ObjectInputStream ois = null;
    private Application_Baggages parent;

    public ThreadListenerBaggage(Application_Baggages parent, Socket sock, ListenerReponse listener){
        this.parent = parent;
        this.listenerSocket = sock;
        this.ois = null;
        this.sourceResponse = listener;
    }
    
    @Override
    public void run(){
        while(!isInterrupted()){
            try {
                ois = new ObjectInputStream(this.listenerSocket.getInputStream());
                ReponseLUGAP rep = (ReponseLUGAP) ois.readObject();
                if(rep.getCode() == ReponseLUGAP.STOP_CHECKIN){
                    if(parent.SelectedVolId > 0 && (int)rep.getChargeUtile() == parent.SelectedVolId){
                        JOptionPane.showMessageDialog(parent, "Fin des opérations de check-in");
                        parent.initLogOut();
                        this.interrupt();   
                    }
                }
                else
                    sourceResponse.recordReponse(rep);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ThreadListenerBaggage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}