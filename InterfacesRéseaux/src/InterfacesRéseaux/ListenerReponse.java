/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacesRéseaux;

/**
 *
 * @author student
 */
public interface ListenerReponse {
    public Reponse getResponse() throws InterruptedException;
    public boolean existResponse();
    public void recordReponse(Reponse rep);
}
