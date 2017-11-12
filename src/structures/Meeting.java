package structures;


import java.io.Serializable;
import java.util.*;


/**
 * 
 */
public class Meeting implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Default constructor
     */
    public Meeting() {
    }

    /**
     * 
     */
    private Date finalizedDate;

    /**
     * 
     */
    private ArrayList<Participant> Participants;

    /**
     * 
     */
    private Location Location;

    /**
     * 
     */
    private int ID;

    /**
     * 
     */
    private ArrayList<DatePref> preferedDates;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private int meetingState;

    /**
     * 
     */
    private ArrayList<DatePref> preferedDateParticipant;


    /**
     * Accept a meeting while inputing date preferences
     * @param username
     * @param dates
     */
    public void acceptMeeting(String username, DatePref dates){
    	//TODO
    }

    /**
     * Decline a meeting
     * @param username Username
     */
    public void declineMeeting(String username){
    	//TODO
    }

    /**
     * Find if a user is part of a meeting
     * @param username Username
     * @return True if they are part of the meeting
     */
    public boolean containsParticipant(String username){
    	//TODO
    	
    	return false;
    }


    /**
     * @param Id 
     * @param Important 
     * @return
     */
    private void addParticipant(String user, boolean i) {
        //to sever username
    	//check to see if exsits
    	//Participant p = new Participant(temp user, i);
    	//
    }

    /**
     * @return
     */
    private Void askLocationPref() {
        // TODO implement here
        return null;
    }

    /**
     * @param Participant 
     * @return
     */
    private void removeParticipant(Participant P) {
        //to server removeParticipant
    	//Participants.remove(P);
    }

    /**
     * Check if two meetings are equal via the id
     * @param m Meeting
     * @return True if id's are the same
     */
	public boolean isEqualTo(Meeting m) {
		if (m.getID() == this.getID()) return true;
		return false;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	public ArrayList<DatePref> getpreferedDateParticipant() {
		// TODO Auto-generated method stub
		return preferedDateParticipant;
	}

	public void setfinalizedDate(Date date) {
		finalizedDate = date;
	}

}