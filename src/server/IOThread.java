package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import structures.Packet;
import structures.User;

/**
 * This class sends packets through the socket to the client
 * @author Jordan
 *
 */
public class IOThread extends ShutdownThread{

	/** 
	 * A variable to tell the thread to run
	 */
	private boolean running;
	
	/**
	 * Object stream to output to
	 */
	private ObjectOutputStream out;
	
	/**
	 * The thread that listens for the clients packets
	 */
	private ListenerThread listener;
	
	/**
	 * The queue of packets to send to the client
	 */
	private Queue<Packet> queue;
	
	/**
	 * The user that is connected to the socket
	 */
	private User user;
	
	public IOThread(ObjectOutputStream oos, ObjectInputStream ois){
		out = oos;
		setQueue(new Queue<Packet>());
		listener = new ListenerThread(ois, queue, this);
	}
	
	public void shutdown(){
		running = false;
	}
	
	public void run(){
		
		System.out.println("IOThread " + this.getId() + " starting");
		
		listener.start();
		running = true;
		while (running){
			try {
				// wait while queue is empty
				synchronized(queue){
					System.out.println("IOThread " + this.getId() + " waiting");
					while (queue.isEmpty()) queue.wait();
				}
			} catch (InterruptedException e) {
				// check for shutdown
			}
			while (!queue.isEmpty()){
				try {
					Packet p = queue.pop();
					System.out.println("IOThread " + this.getId() + " sending packet type " + p.getType());
					out.writeObject(p);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// shutter down boys
		listener.shutdown();
		try {
			System.out.println("IOThread " + this.getId() + " sending packet type 3300");
			out.writeObject(new Packet(Packet.CLOSE_CONNECTION));
			out.flush();
		} catch (Exception e) {}
		System.out.println("IOThread " + this.getId() + " stopping");
	}

	public Queue<Packet> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Packet> queue) {
		this.queue = queue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
