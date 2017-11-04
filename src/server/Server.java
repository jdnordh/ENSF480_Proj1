package server;

import java.util.*;

/**
 * 
 */
public class Server extends Thread{

    /**
     * Default constructor
     */
    public Server() {
    }

    /**
     * 
     */
    private findMeetingTimeStrategyInterface meetingTImeFinder;

    /**
     * 
     */
    private ArrayList<Users> usercatalog;

    /**
     * 
     */
    private ArrayList<Meeting> Meetingcatalog;









    /**
     * @return
     */
    private void startUp() {
        // TODO implement here
        return null;
    }

    /**
     * @param Meeting 
     * @return
     */
    protected void initateMeeting(void Meeting) {
        // TODO implement here
        return null;
    }

    /**
     * @param Username 
     * @param Password 
     * @return
     */
    protected void addUser(String Username, String Password) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    protected void notifyLoggedIn() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    protected void deleteUser() {
        // TODO implement here
        return null;
    }

    /**
     * @param meetingId 
     * @param prefDates 
     * @return
     */
    protected void participantAcceptMeeting(int meetingId, ArrayList<custDate> prefDates) {
        // TODO implement here
        return null;
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
    protected void removeParticipant(void Participant) {
        // TODO implement here
        return null;
    }

    /**
     * @param importantParticipants 
     * @return
     */
    protected void notifyImportantParticipantForLocationPref(ArrayList<participnt> importantParticipants) {
        // TODO implement here
        return null;
    }

    /**
     * @param asocket 
     * @return
     */
    protected ArrayList<Participants> returnAllUsers(Socket asocket) {
        // TODO implement here
        return null;
    }

    /**
     * @param UserName 
     * @return
     */
    protected void participantsDeclinesMeeting(void UserName) {
        // TODO implement here
        return null;
    }
    
    public static void main(String [] args){
    	System.out.println("grdg");
    }
    

}