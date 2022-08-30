/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IACHAT.Application;

import InterfacesRéseaux.ConsoleServeur;
import Protocole.IACOP.ConsoleServeurIACOP;
import Protocole.IACOP.RequeteIACOP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class ThreadListenerIACHAT extends Thread {
    private final MulticastSocket McastSocket;
    private final ConsoleServeur cs;
    
    public ThreadListenerIACHAT(MulticastSocket sock, ConsoleServeur cs){
        this.McastSocket = sock;
        this.cs = cs;
    }
    
    @Override
    public void run(){
        while(!isInterrupted()){
            byte[] buf = new byte[1500];
            int length = 1500;
            DatagramPacket dataPack = new DatagramPacket(buf,length);
            
            try {
                this.McastSocket.receive(dataPack);
                
                RequeteIACOP req = new RequeteIACOP(dataPack.getData());
                req.createRunnable(this.McastSocket, (ConsoleServeurIACOP) cs);
            } catch (IOException ex) {
                Logger.getLogger(ThreadListenerIACHAT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Fin du thread");
        try {
            McastSocket.leaveGroup(McastSocket.getInetAddress());
        } catch (IOException ex) {
            Logger.getLogger(ThreadListenerIACHAT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
