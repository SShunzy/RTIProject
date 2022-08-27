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
    public boolean createRunnable(Socket s, ConsoleServeur cs);
}
