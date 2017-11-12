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
    	Participants = new ArrayList<Participant>();
    	preferedDates = new ArrayList<Date>();
    	preferedDateParticipant = new ArrayList<DatePref>();
    	ID = IDcounter++;
    	meetingState = 0;
    }
    //Meeting intiated with Location
    public Meeting(ArrayList<Participant> p ,Location L, String D , ArrayList<Date> meetingIniatorPref) {
    	ID = IDcounter++;
    	preferedDates = meetingIniatorPref;
    	Location = L;
    	description = D;
    	meetingState = 0;
    	Participants = p;
    	meetingState =2;
    	preferedDateParticipant = new ArrayList<DatePref>();
    }
    //without location
    public Meeting(ArrayList<Participant> p ,String D , ArrayList<Date> meetingIniatorPref) {
    	ID = IDcounter++;
    	preferedDates = meetingIniatorPref;
    	description = D;
    	meetingState = 0;
    	Participants = p;
    	meetingState = 1;
    	preferedDateParticipant = new ArrayList<DatePref>();
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
    private static int IDcounter = 0;
    private static int ID;

    /**
     * 
     */
    private ArrayList<Date> preferedDates;

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
     * @param dates
     */
    public void acceptMeeting(DatePref dates){
    	preferedDateParticipant.add(dates);
    }
    /**
     * 
     * @param Important 
     * @return
     */
    private void addParticipant(String user,String name , boolean i) {
    	Participant p = new Participant(user,name, i);
    	Participants.add(p);
    }
 
    /**
     * @param Participant 
     * @return
     */
    private void removeParticipant(Participant P) {
    	Participants.remove(P);
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
		return preferedDateParticipant;
	}

	public void setfinalizedDate(Date date) {
		finalizedDate = date;
	}
	public static final int waitingForDates = 2;
	public static final int empty = 0;
	public static final int waitingForLocationPref = 1;
	public static final int waitingForFinalized = 3;
	public static final int Finalized = 4;
}