package structures;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * 
 */
public class Packet{

	/** A request sent to the server, from the login GUI
	 * The following fields need to be filled in to make this a valid request:
	 * 
	 * type = Packet.LOGIN
	 * two entries in strings: index 0 is username, index 1 is the SHA-256 hashed password, using the following syntax:
	 * 
	 * 			String password = "password";
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance("SHA-256");
					String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
				} catch (NoSuchAlgorithmException e) {}
				
		The variable hashed_password should be assigned to index 1 of the ArrayList fields
		
		
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
	 * In both the above cases, index 0 of fields will include the users name, if you want to display it in the window title
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
	 * The following types are to be used by admin GUI exclusively
	 * The following fields should be filled out
	 * 
	 * For Packet.ADD_USER
	 * strings index 0 with username
	 * strings index 1 with SHA-256 hashed password (see above in Packet.LOGIN)
	 * 
	 * For Packet.DELETE_USER
	 * strings index 0 with username
	 */
	public static final int ADD_USER = 15;
	public static final int DELETE_USER = 16;
	
	
	/**
	 * The following type is for participants accepting a meeting
	 * The following fields should be filled out:
	 * 
	 * strings index 0 with username
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
	 * fields index 0 with username
	 * number1 with meetingId
	 */
	public static final int DELCINE_MEETING = 31;
	
	
	/**
	 * This is for getting all the users meetings, no matter the state
	 * Fill out the following:
	 * 
	 * fields index 0 with username
	 */
	public static final int REQUEST_ALL_MEETINGS = 33;
	
	
	/**
	 * This is a server response to Packet.REQUEST_ALL_MEETINGS
	 * The following fields will be populated
	 * 
	 */
	public static final int RESPONSE_ALL_MEETINGS = 34;
	
	
	
	public static void main(String [] args){
		String password = "s";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String hashed_password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
			System.out.print(hashed_password);
		} catch (NoSuchAlgorithmException e) {}
	}
	
	
	
	
    /**
     * Default constructor
     */
    public Packet(int t) {
    	type = t;
    	strings = new ArrayList<String>();
    	users = new ArrayList<User>();
    	dates = new ArrayList<DatePref>();
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
    private ArrayList<DatePref> dates;

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

	public ArrayList<DatePref> getDates() {
		return dates;
	}

	public void setDates(ArrayList<DatePref> dates) {
		this.dates = dates;
	}

	/**
	 * Add a date to the end of the current DatePref array
	 * @param date DatePref to be added
	 */
	public void addDate(DatePref date){
		dates.add(date);
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