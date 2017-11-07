package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import structures.*;

/**
 * 
 */
public class Server extends Thread{

	private ServerSocket server;

	private Queue<Packet> queue;
	
	private ArrayList<InputOutputThread> threads;
	
    /**
     * Default constructor
     */
    public Server() {
    	try{
    		server = new ServerSocket(65535);
    	} catch (Exception e){}
    }

    /**
     * 
     */
    private FindMeetingTimeStrategyInterface scheduler;

    /**
     * 
     */
    private ArrayList<User> userCatalog;

    /**
     * 
     */
    private ArrayList<Meeting> meetingCatalog;


    public void run(){
    	
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
    
    public static void main(String [] args){
    	System.out.println("grdg");
    }
    

}