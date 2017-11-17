package structures;


import java.awt.Component;
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
    
    //participants,Location, Description, MeetingIniatorPrefDates , MeetingIniator
    public Meeting(ArrayList<Participant> p ,Location L, String D , ArrayList<Date> meetingIniatorPref, User U) {
    	ID = IDcounter++;
    	preferedDates = meetingIniatorPref;
    	Location = L;
    	description = D;
    	meetingState = 0;
    	Participants = p;
    	meetingState =2;
    	preferedDateParticipant = new ArrayList<DatePref>();
    	setMeetingInitiator(U);
    }
    //without location
    public Meeting(ArrayList<Participant> p,ArrayList<Location> Ls ,String D , ArrayList<Date> meetingIniatorPref, User U) {
    	ID = IDcounter++;
    	preferedDates = meetingIniatorPref;
    	description = D;
    	meetingState = 0;
    	LocationPref = Ls;
    	Participants = p;
    	meetingState = 1;
    	preferedDateParticipant = new ArrayList<DatePref>();
    	setMeetingInitiator(U);
    }
   
    /**
     * 
     */
    private Date finalizedDate;
    private User meetingInitiator;
    /**
     * 
     */
    private ArrayList<Participant> Participants;

    /**
     * 
     */
    private Location Location;
    
    private ArrayList<Location> LocationPref;
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
    public void addParticipant(String user,String name , boolean i) {
    	Participant p = new Participant(user,name, i);
    	Participants.add(p);
    }
 
    /**
     * @param Participant 
     * @return
     */
    public void removeParticipant(User user) {
    	Participants.remove(user);
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
	public ArrayList<Participant> getParticipants() {
		return Participants;
	}
	public void setfinalizedDate(Date c) {
		finalizedDate = c;
	}
	public int getmeetingState(){return meetingState;}
	
	public ArrayList<Location> getLocations(){
		return LocationPref;
	}
	
	public static final int waitingForDates = 2;
	public static final int empty = 0;
	public static final int waitingForLocationPref = 1;
	public static final int waitingForFinalized = 3;
	public static final int Finalized = 4;
	public static final int serverPickLocationPref = 5;
	public static final int NODATEFOUND = 6;
	public static final int DATEOUTSIDERANGE = 7;
	public static final int MEETINGCANCELED  = 10;
	
	public boolean containsParticipant(String userName) {
		for(int i = 0 ; i < Participants.size(); i++){
			if(Participants.get(i).getUserName() == userName)
				return true;
		}
		return false;
	}
	public void setmeetingState(int x) {meetingState = x;}
	public void setLocation(Location l) {Location = l;	}

	public User getMeetingInitiator() {
		return meetingInitiator;
	}

	public void setMeetingInitiator(User meetingInitiator) {
		this.meetingInitiator = meetingInitiator;
	}

	public Location getLocation() {
		return Location;
	}

	public String getDescription() {
		return description;
	}

	public Date getFinalizedDate() {
		return finalizedDate;
	}

	public void setDescription(String d) {
		description = d;
	}
	public String toString(){
		String s = ID+meetingInitiator.getUserName();
		return s;
	}

	public ArrayList<Date> getPreferedDates() {return preferedDates;}
	
	
}