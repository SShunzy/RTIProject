/*
 * Requete.java
 */
package InterfacesRéseaux;

import java.net.*;

/**
 *
 * @author student
 */
public interface Requete 
{
    public Runnable createRunnable(Socket s, ConsoleServeur cs);
}
