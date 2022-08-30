/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.IACOP;

import InterfacesRéseaux.ConsoleServeur;
import java.net.InetAddress;
import java.sql.Timestamp;

/**
 *
 * @author student
 */
public interface ConsoleServeurIACOP extends ConsoleServeur {
    
    public boolean isClientValid(String login, Timestamp date, byte[] pwdSalted);
    public InetAddress getAddress();
    public int getPort();
}
