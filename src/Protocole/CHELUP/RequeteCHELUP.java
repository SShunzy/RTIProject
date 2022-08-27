/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole.CHELUP;

import Baggages.Serveur.ConsoleServeurBaggages;
import InterfacesRéseaux.ConsoleServeur;
import InterfacesRéseaux.Requete;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class RequeteCHELUP implements Requete {

    public byte[] chaineCaractere;
    
    public RequeteCHELUP(byte[] chaineCaractere)
    {
        this.chaineCaractere = chaineCaractere;
    }
    
    @Override
    public boolean createRunnable(Socket s, ConsoleServeur cs) {
        if(this.chaineCaractere.length == 0) System.out.println("Requete non valide");
        else traiteRequete(s, (ConsoleServeurBaggages)cs);
        return false;
    }
    
    private void traiteRequete(Socket s, ConsoleServeurBaggages cs){
        String Request = new String(this.chaineCaractere);
        System.out.println("Request = "+Request);
        
        String[] token = Request.split("#");
        String Response;
        
        boolean isBilletValide = cs.checkBillets(token[0], Integer.parseInt(token[1]));
        if(isBilletValide) Response = "BILLET_OK@";
        else Response = "BILLET_KO@";
        
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.write(Response.getBytes(),0,Response.length());dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(RequeteCHELUP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
