package server;

import structures.Packet;

public class InputOutputThread extends Thread{

	private Queue<Packet> queue;
	
	public InputOutputThread(Queue<Packet> q){
		queue = q;
	}
	
	public void run(){
		
	}
}
