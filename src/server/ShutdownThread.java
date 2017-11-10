package server;

/**
 * A generic thread that has a synchronized shutdown function
 * @author Jordan
 *
 */
public abstract class ShutdownThread extends Thread{
	public abstract void shutdown();
}
