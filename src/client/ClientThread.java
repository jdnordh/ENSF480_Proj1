package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import server.CloseSocketException;
import server.ShutdownThread;
import structures.Packet;

public class ClientThread extends ShutdownThread{

	private boolean running;
	
	private ObjectInputStream in;
	
	private ClientGUIFunctionality master;
	
	public ClientThread(ObjectInputStream ois, ClientGUIFunctionality m){
		master = m;
		in = ois;
	}
	
	public void run(){
		running = true;
		System.out.println("ClientThread " + this.getId() + " running");
		
		while (running){
			try {
				if (in == null) {
					running = false;
					throw new CloseSocketException();
				}
				Packet p = (Packet) in.readObject();
				
				if ( !(p instanceof Packet)) throw new ClassNotFoundException("BAD REQUEST");
				
				System.out.println("ClientThread " + this.getId() + " recieved packet type " + p.getType());
				
				
				
			} catch (SocketTimeoutException e){
				// timeout, check if still running
			} catch (SocketException e){
				this.shutdown();
				
			}catch (CloseSocketException | EOFException e){
				//close down
				running = false;
			}catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("ClientThread " + this.getId() + " stopping");
		
	}

	@Override
	public void shutdown() {
		running = false;
	}
}
