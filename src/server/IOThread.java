package server;

import java.net.Socket;

import structures.Packet;

public class IOThread extends Thread{

	private Socket socket;
	
	private ListenerThread listener;
	
	private Queue<Packet> queue;
	
	public IOThread(Socket s){
		socket = s;
		setQueue(new Queue<Packet>());
	}
	
	public void run(){
		//TODO
	}

	public Queue<Packet> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Packet> queue) {
		this.queue = queue;
	}
}
