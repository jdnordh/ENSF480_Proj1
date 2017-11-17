package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.*;

import structures.User;

/**
 * 
 */
public class Server extends Thread{

	public final static String NAME = "localhost";

	public final static int PORT = 16386;
	
	private boolean running;
	
	private ServerSocket server;
	
	private ArrayList<ShutdownThread> threads;
	
	private FindMeetingTimeStrategyInterface scheduler;
	
	private int port;

	/**
	 * Construct a server
	 * @param port Port number
	 * @param s Meeting scheduler strategy
	 */
    public Server(String name, int sport, FindMeetingTimeStrategyInterface s) {
    	scheduler = s;
    	port = sport;
    	threads = new ArrayList<ShutdownThread>();
    	try{
    		InetAddress a = InetAddress.getByName(name);
			server = new ServerSocket(port, 50, a);
    		running = true;
    	} catch (Exception e){
    		System.out.println("Server could not be created.\nExiting...\n");
    		System.exit(1);
    	}
    	System.out.println("Server constructed with port " + port);
    }

  

    public void run(){
    	System.out.println("Server is running on port " + port);
    	
    	//start the scheduler thread
    	SchThread s = new SchThread(scheduler);
		s.start();
		threads.add(s);
    	
    	while (running){
    		try {
    			server.setSoTimeout(100);
    			Socket temp = server.accept();
    			temp.setSoTimeout(100);
    			ObjectOutputStream oos = new ObjectOutputStream(temp.getOutputStream());
    			ObjectInputStream ois = new ObjectInputStream(temp.getInputStream());
    			
    			
    			System.out.println("Incoming connection from: " + temp.toString());
    			//spawn a thread to handle the connection
    			IOThread newthread = new IOThread(oos, ois);
    			newthread.start();
    			threads.add(newthread);
    			System.err.println("Server threads: " + threads.size());
    		} catch (SocketException e){
    			// problem with socket
    			// clean up
    			for (int i = 0; i < threads.size(); i++){
    				if (threads.get(i).getState() == Thread.State.TERMINATED){
    					System.err.println("Server removed Thread: " + threads.get(i).getId());
    					threads.remove(i);
    					System.err.println("Server threads: " + threads.size());
    				}
    			}
    			e.printStackTrace();
    		} catch (SocketTimeoutException e){
    			// clean up
    			for (int i = 0; i < threads.size(); i++){
    				if (threads.get(i).getState() == Thread.State.TERMINATED){
    					System.err.println("Server removed Thread: " + threads.get(i).getId());
    					threads.remove(i);
    					System.err.println("Server threads: " + threads.size());
    				}
    			}
    			
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (int i = 0; i < threads.size(); i++){
    		threads.get(i).shutdown();
    		threads.get(i).interrupt();
    	}
    	System.out.println("Server is closing...");
    }
    
    
    
    public void shutdown(){
    	running = false;
    }
    
    public static void main(String [] args){
		
		FindMeetingTimeStrategyInterface strat = new FindMeetingTimeStrategy1();
		
		Server server = new Server(Server.NAME, Server.PORT, strat);
		
		server.start();
		System.out.println("Type \"quit\" or \"stop\" to stop");
		System.out.println("Type \"info\" to get current server info");
		System.out.println("Type \"users\" to get current users info");
		Scanner in = new Scanner(System.in);
		String read = "";
		while ( !read.equalsIgnoreCase("quit") && !read.equalsIgnoreCase("stop")){
			read = in.next();
			if (read.equalsIgnoreCase("info")) server.info();
			else if (read.equalsIgnoreCase("users")) server.printUsers();
		}
		in.close();
		
		System.out.println("\nShutting down the server...");
		server.shutdown();
    }


/**
 * Print the users out
 */
	private void printUsers() {
		UserList ul = UserList.getUserList();
		ArrayList<User> u = ul.getUsers();
		System.err.println("\nUsers:");
		System.err.println("Name		Username	Password				Admin");
		for (int i = 0; i < u.size(); i++){
			System.err.println(u.get(i).getName() + "\t" + u.get(i).getUserName() + 
					"\t" + u.get(i).getPassword() + "\t\t\t" + u.get(i).isAdmin());
		}
	}

	private void info() {
		try {
			System.err.println(server.toString());
			System.err.println("Server timeout: " + server.getSoTimeout() + " ms");
			System.err.println("Server threads: " + threads.size());
		} catch (Exception e){}
	}
}