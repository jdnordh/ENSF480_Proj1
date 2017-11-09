package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import structures.*;

/**
 * This thread listens to the client through the socket
 * It also computes queries 
 * 
 * @author Jordan
 *
 */
public class ListenerThread extends Thread{

	private boolean running;
	
	private Socket socket;
	
	private Queue<Packet> queue;
	
	private ObjectInputStream in;
	
	private IOThread master;
	
	private User user;
	
	public ListenerThread(Socket s, Queue<Packet> q, IOThread mas) {
		master = mas;
		socket = s;
		queue = q;
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void run(){
		running = true;
		
		while (running){
			try {
				
				Packet p = (Packet) in.readObject();
				
				if ( !(p instanceof Packet)) throw new ClassNotFoundException("BAD REQUEST");
				
				//TODO classify the packet
				
			} catch (ClassNotFoundException | IOException e) {
				sendBadRequest();
				e.printStackTrace();
			}
			/*
			catch (InterruptedException e){
				
			} 
			*/
			catch (Exception e){
				sendBadRequest();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send a bad request packet back to the client
	 */
	private void sendBadRequest(){
		Packet p = new Packet(Packet.BAD_REQUEST);
		queue.push(p);
	}
	
	public void shutdown() {
		running = false;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
