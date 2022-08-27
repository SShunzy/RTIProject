/*
 *  SourceTaches.java   
 */

package InterfacesRéseaux;

import java.net.Socket;

/**
 *
 * @author student
 */
public interface SourceTaches 
{
    public Socket getTache() throws InterruptedException;
    public boolean existTaches();
    public void recordTache (Socket r);
}
