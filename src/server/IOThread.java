package server;

import java.net.Socket;

import structures.Packet;
import structures.User;

/**
 * This class sends packets through the socket to the client
 * @author Jordan
 *
 */
public class IOThread extends Thread{

	private boolean running;
	
	private Socket socket;
	
	private ListenerThread listener;
	
	private Queue<Packet> queue;
	
	private User user;
	
	public IOThread(Socket s){
		socket = s;
		setQueue(new Queue<Packet>());
		listener = new ListenerThread(socket, queue, this);
	}
	
	public void shutdown(){
		running = false;
	}
	
	public void run(){
		//TODO
		listener.start();
		
		while (running){
			
		}
		listener.shutdown();
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
