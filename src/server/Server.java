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

/**
 * 
 */
public class Server extends Thread{

	public final static String NAME = "localhost";

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
    			
    		} catch (SocketException e){
    			// problem with socket
    			// clean up
    			for (int i = 0; i < threads.size(); i++){
    				if (!threads.get(i).isAlive()) threads.remove(i);
    			}
    			e.printStackTrace();
    		} catch (SocketTimeoutException e){
    			// clean up
    			for (int i = 0; i < threads.size(); i++){
    				if (!threads.get(i).isAlive()) threads.remove(i);
    			}
    			//System.out.println("Timeout");
    			
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

    
    
    
    

//
//    /**
//     * @param Username 
//     * @param Password 
//     * @return
//     */
//    protected void addUser(String Username, String Password) {
//    }
//
//    /**
//     * @return
//     */
//    protected void notifyLoggedIn() {
//    }
//
//    /**
//     * @return
//     */
//    protected void deleteUser() {
//    }
//
//    /**
//     * @param meetingId 
//     * @param prefDates 
//     * @return
//     */
//    protected void participantAcceptMeeting(int meetingId, ArrayList<DatePref> prefDates) {
//    }
//
//    /**
//     * @return
//     */
//    protected ArrayList<Meeting> returnAllMeetings() {
//        return null;
//    }
//
//    /**
//     * @param Participant 
//     * @return
//     */
//    protected void removeParticipant(Participant p) {
//    }
//
//    /**
//     * @param importantParticipants 
//     * @return
//     */
//    protected void notifyImportantParticipantForLocationPref(ArrayList<Participant> importantParticipants) {
//    }
//
//    /**
//     * @param asocket 
//     * @return
//     */
//    protected ArrayList<Participant> returnAllUsers(Socket asocket) {
//        return null;
//    }
//
//    /**
//     * @param UserName 
//     * @return
//     */
//    protected void participantsDeclinesMeeting(String s) {
//    }
    
    
    
    public void shutdown(){
    	running = false;
    }
    
    public static void main(String [] args){
    	int serverPort = 16386;
		
		FindMeetingTimeStrategyInterface strat = new FindMeetingTimeStrategy1();
		
		Server server = new Server(Server.NAME, serverPort, strat);
		
		server.start();
		System.out.println("Type \"quit\" to stop");

		Scanner in = new Scanner(System.in);
		while ( !in.next().equalsIgnoreCase("quit") );
		in.close();
		
		System.out.println("\nShutting down the server...");
		server.shutdown();
    }
}