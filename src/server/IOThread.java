package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import structures.Packet;
import structures.User;

/**
 * This class sends packets through the socket to the client
 * @author Jordan
 *
 */
public class IOThread extends Thread{

	/** 
	 * A variable to tell the thread to run
	 */
	private boolean running;
	
	/**
	 * Socket to output to
	 */
	private Socket socket;
	
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
	
	public IOThread(Socket s, FindMeetingTimeStrategyInterface sch){
		socket = s;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		setQueue(new Queue<Packet>());
		listener = new ListenerThread(socket, queue, this, sch);
	}
	
	public void shutdown(){
		running = false;
	}
	
	public void run(){
		//TODO check if working??
		listener.start();
		
		while (running){
			try {
				while (queue.isEmpty())
					wait();
			} catch (InterruptedException e) {
				// check for shutdown
			}
			while (!queue.isEmpty()){
				try {
					out.writeObject(queue.pop());
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// shuter down boys
		listener.shutdown();
		try {
			out.writeObject(new Packet(Packet.CLOSE_CONNECTION));
			out.flush();
			socket.close();
		} catch (Exception e) {}
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
