/*
 *  SourceTaches.java   
 */

package InterfacesRéseaux;

/**
 *
 * @author student
 */
public interface SourceTaches 
{
    public Runnable getTache() throws InterruptedException;
    public boolean existTaches();
    public void recordTache (Runnable r);
}
