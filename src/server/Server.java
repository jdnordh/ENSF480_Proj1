package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

import structures.*;

/**
 * 
 */
public class Server extends Thread{

	private boolean running;
	
	private ServerSocket server;
	
	private ArrayList<Thread> threads;
	
	private FindMeetingTimeStrategyInterface scheduler;
	
	private Queue<Meeting> meetingsToBeScheduled;
	
	private int port;

	/**
	 * Construct a server
	 * @param port Port number
	 * @param s Meeting scheduler strategy
	 */
    public Server(int sport, FindMeetingTimeStrategyInterface s) {
    	setScheduler(s);
    	port = sport;
    	meetingsToBeScheduled = new Queue<Meeting>();
    	try{
    		server = new ServerSocket(port);
    		running = true;
    	} catch (Exception e){
    		System.out.println("Server could not be created.\nExiting...\n");
    		System.exit(1);
    	}
    	System.out.println("Server constructed with port " + port);
    }

  

    public void run(){
    	System.out.println("Server is running on port " + port);
    	while (running){
    		try {
    			server.setSoTimeout(100);
    			Socket temp = server.accept();
    			
    			//spawn a thread to handle the connection
    			IOThread newthread = new IOThread(temp, scheduler);
    			newthread.start();
    			threads.add(newthread);
    			
    		} catch (SocketException e){
    			//TODO clean up
    			for (int i = 0; i < threads.size(); i++){
    				if (!threads.get(i).isAlive()) threads.remove(i);
    			}
    			
    			// if any meetings need to be scheduled, do it
    			if (!meetingsToBeScheduled.isEmpty()){
    				//spawn new scheduler thread
    				SchThread s = new SchThread(meetingsToBeScheduled.pop());
    				s.start();
    				threads.add(s);
    			}
    			
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println("Server is closing...");
    }

    /**
     * @param Meeting 
     * @return
     */
    protected void initateMeeting(Meeting m) {
        // TODO implement here
    }

    /**
     * @param Username 
     * @param Password 
     * @return
     */
    protected void addUser(String Username, String Password) {
        // TODO implement here
    }

    /**
     * @return
     */
    protected void notifyLoggedIn() {
        // TODO implement here
    }

    /**
     * @return
     */
    protected void deleteUser() {
        // TODO implement here
    }

    /**
     * @param meetingId 
     * @param prefDates 
     * @return
     */
    protected void participantAcceptMeeting(int meetingId, ArrayList<DatePref> prefDates) {
        // TODO implement here
    }

    /**
     * @return
     */
    protected ArrayList<Meeting> returnAllMeetings() {
        // TODO implement here
        return null;
    }

    /**
     * @param Participant 
     * @return
     */
    protected void removeParticipant(Participant p) {
        // TODO implement here
    }

    /**
     * @param importantParticipants 
     * @return
     */
    protected void notifyImportantParticipantForLocationPref(ArrayList<Participant> importantParticipants) {
        // TODO implement here
    }

    /**
     * @param asocket 
     * @return
     */
    protected ArrayList<Participant> returnAllUsers(Socket asocket) {
        // TODO implement here
        return null;
    }

    /**
     * @param UserName 
     * @return
     */
    protected void participantsDeclinesMeeting(String s) {
        // TODO implement here
    }
    
    public void shutdown(){
    	running = false;
    }
    
    public static void main(String [] args){
    	int serverPort = 16386;
		
		FindMeetingTimeStrategyInterface strat = new FindMeetingTimeStrategy1();
		
		Server server = new Server(serverPort, strat);
		
		server.start();
		System.out.println("Type \"quit\" to stop");

		Scanner in = new Scanner(System.in);
		while ( !in.next().equalsIgnoreCase("quit") );
		in.close();
		
		System.out.println("\nShutting down the server...");
		server.shutdown();
    }



	public FindMeetingTimeStrategyInterface getScheduler() {
		return scheduler;
	}



	public void setScheduler(FindMeetingTimeStrategyInterface scheduler) {
		this.scheduler = scheduler;
	}
    

}