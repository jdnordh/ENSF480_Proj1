package structures;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 
 */
public class Packet implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** A request sent to the server, from the login GUI
	 * The following fields need to be filled in to make this a valid request:
	 * 
	 * type = Packet.LOGIN
	 * user index 0 = User - including username, SHA-256 hashed password, using the following syntax:
	 * 
	 * 			String password = "password";
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance("SHA-256");
					String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
				} catch (NoSuchAlgorithmException e) {}
				
		
		
		***** You must import the following files for the hash:
		import java.nio.charset.StandardCharsets;
		import java.security.MessageDigest;
		import java.security.NoSuchAlgorithmException;
	 */
	public static final int LOGIN = 2;
	
	
	/**
	 * The following types are responses from a login request
	 * They are sent from the server to the client
	 * 
	 * If returned Packet.LOGIN_CONFIRM_USER, open the client GUI
	 * 
	 * Else if returned Packet.LOGIN_CONFIRM_ADMIN, open admin GUI
	 * 
	 * In both the above cases, a user will be returned in users index 0 and will include the users name, if you want to display it in the window title
	 * 
	 * Else if Packet.LOGIN_DENY, password and/or username were incorrect
	 */
	public static final int LOGIN_CONFIRM_USER = 20;
	public static final int LOGIN_CONFIRM_ADMIN = 21;
	public static final int LOGIN_DENY = 22;
	
	
	/**
	 * This request asks for a list of all users
	 * It can be used for adding participants to a meeting, or deleting users in admin GUI
	 * 
	 * The following fields must be filled out for a complete request:
	 * 
	 * type = Packet.REQUEST_ALL_USERS
	 * 
	 */
	public static final int REQUEST_ALL_USERS = 1;
	
	
	/**
	 * This is a response from the server when requested for all users
	 * The contents will contain:
	 * 
	 * type = Packet.RESPONSE_ALL_USERS
	 * ArrayList<User> users is populated, with all users in the system
	 * 
	 * *** Make sure to not read from the list if it is empty ****
	 * 
	 * Each user should have a username and a name, but only the name needs to be displayed in client gui
	 * 
	 * If deleting users in admin GUI, show usernames
	 */
	public static final int RESPONSE_ALL_USERS = 11;
	
	/**
	 * The following type is a request from the client to initiate a meeting
	 * The following fields must be filled out:
	 * 
	 * type = Packet.INITIATE_MEETING
	 * meetings has a Meeting at index 0
	 * 
	 * The meeting should have all relevant fields filled out
	 */
	public static final int INITIATE_MEETING = 3;
	
	/**
	 * The server will send this packet back to the client to confirm that the meeting was received
	 */
	public static final int INITIATE_MEETING_CONFIRM = 35;
	
	/**
	 * The following types are to be used by admin GUI exclusively
	 * The following fields should be filled out
	 * 
	 * For Packet.ADD_USER
	 * users index 0 with username, hashed password, and name, and isAdmin
	 * 
	 * For Packet.DELETE_USER
	 * users index 0 with username
	 */
	public static final int ADD_USER = 15;
	public static final int DELETE_USER = 16;
	
	/**
	 * The following are response codes from the server after a request to add or delete a user
	 */
	public static final int ADD_USER_CONFIRM = 115;
	public static final int DELETE_USER_CONFIRM = 116;
	public static final int ADD_USER_DENY = 215;
	public static final int DELETE_USER_DENY = 216;
	
	/**
	 * The following type is for participants accepting a meeting
	 * The following fields should be filled out:
	 * 
	 * users index 0 with username
	 * number1 with meetingId
	 * dates with all date preferences
	 * 
	 * If the participant is important also include:
	 * locations with location preferences
	 */
	public static final int ACCEPT_MEETING = 30;
	
	/**
	 * The following is used to decline a meeting
	 * Fill out the following:
	 * 
	 * users index 0 with username
	 * number1 with meetingId
	 */
	public static final int DECLINE_MEETING = 31;
	
	/**
	 * The servers response to accept or decline of a meeting
	 * 
	 * This confirms that the action went through
	 */
	public static final int ACCEPT_OR_DECLINE_RESPONSE = 32;
	
	/**
	 * This is for getting all the users meetings, no matter the state
	 * Fill out the following:
	 * 
	 * users index 0 with username
	 */
	public static final int REQUEST_ALL_MEETINGS = 33;
	
	
	/**
	 * This is a server response to Packet.REQUEST_ALL_MEETINGS
	 * The following fields will be populated
	 * 
	 * Meetings will all meetings associated with that user
	 */
	public static final int RESPONSE_ALL_MEETINGS = 34;
	
	/**
	 * This packet will be returned if the request was not formatted properly
	 */
	public static final int BAD_REQUEST = 90000;
	
	/**
	 * This request will get you all of the locations available to have meetings
	 * Fill out these fields:
	 * 
	 * type = Packet.REQUEST_ALL_LOCATIONS
	 */
	public static final int REQUEST_ALL_LOCATIONS = 40;
	
	/**
	 * The servers response to all locations request
	 * These fields will be populated:
	 * 
	 * locations with all locations
	 * 
	 */
	public static final int RESPONSE_ALL_LOCATIONS = 41;
	
	/**
	 * This request is for admin only to add a location
	 * 
	 * Fill out these:
	 * 
	 * type = Packet.ADD_LOCATION
	 * locations index 0 with the location to add
	 * 
	 */
	public static final int ADD_LOCATION = 42;
	
	/**
	 * This request is for admin only to delete a location
	 * 
	 * Fill out these:
	 * 
	 * type = Packet.DELETE_LOCATION
	 * locations index 0 with the location to delete
	 * 
	 */
	public static final int DELETE_LOCATION = 43;
	
	/**
	 * This is the servers response to ADD_LOCATION
	 * 
	 * If the added location was a duplicate DENY is sent
	 * else CONFIRM is sent
	 */
	public static final int ADD_LOCATION_CONFIRM = 44;
	public static final int ADD_LOCATION_DENY = 45;
	
	/**
	 * This is the servers response to DELETE_LOCATION
	 * 
	 * If the deleted location was not found DENY is sent
	 * else CONFIRM is sent
	 */
	public static final int DELETE_LOCATION_CONFIRM = 46;
	public static final int DELETE_LOCATION_DENY = 47;
	
	/**
	 * This packet can be sent to either the client or the server, initiating a connection close
	 * Fields to fill out:
	 * 
	 * type = Packet.CLOSE_CONNECTION
	 * 
	 * If sent to the server, the server will send an identical packet back
	 */
	public static final int CLOSE_CONNECTION = 3300;
	
	/**
	 * This packet is sent from the server to the client when a new meeting appears that they are a part of
	 * 
	 * The client can either be told to refresh the meetings list, or do it automatically
	 */
	public static final int NEW_MEETING = 500;
	
	/**
	 * This packet is sent from the server to the client when a user should drop a meeting
	 * 
	 * The client can either be told to refresh the meetings list, or do it automatically
	 */
	public static final int NEW_MEETING_DROP_REQUEST = 501;
	
    /**
     * Default constructor
     */
    public Packet(int t) {
    	type = t;
    	strings = new ArrayList<String>();
    	users = new ArrayList<User>();
    	dates = new DatePref();
    	locations = new ArrayList<Location>();
    	meetings = new ArrayList<Meeting>();
    }

    /**
     * The type of the packet, see above static finals for types
     */
    private int type;

    /**
     * An array of strings
     */
    private ArrayList<String> strings;

    /**
     * An array of users
     */
    private ArrayList<User> users;

    /**
     * An array of DatePrefs
     */
    private DatePref dates;

    /**
     * An array of Locations
     */
    private ArrayList<Location> locations;


	/**
     * An array of Meetings
     */
    private ArrayList<Meeting> meetings;

    /**
     * First number field
     */
    private int Number1;

    /**
     * Second number field
     */
    private int number2;
    
    public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<String> getStrings() {
		return strings;
	}

	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}

	/**
	 * Add a string to the end of the current strings array
	 * @param s String to add
	 */
	public void addString(String s){
		strings.add(s);
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	/**
	 * Add a user to the end of the current user array
	 * @param user User to add
	 */
	public void addUser(User user){
		users.add(user);
	}

	public DatePref getDates() {
		return dates;
	}

	public void setDates(DatePref dates) {
		this.dates = dates;
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	/**
	 * Add a location to the end of the current Location array
	 * @param l Location to add
	 */
	public void addLocation(Location l){
		locations.add(l);
	}
	
	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}

	/**
	 * Add a meeting the end of the current Meetings array
	 * @param m
	 */
	public void addMeeting(Meeting m){
		meetings.add(m);
	}
	
	public int getNumber1() {
		return Number1;
	}

	public void setNumber1(int number1) {
		Number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

}