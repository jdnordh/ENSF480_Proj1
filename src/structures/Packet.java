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
	 * two entries in fields: index 0 is username, index 1 is the SHA-256 hashed password, using the following syntax:
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
	 */
	public static final int RESPONSE_ALL_USERS = 11;
	
	/**
	 * The following type is a request from the client to initiate a meeting
	 */
	public static final int INITIATE_MEETING = 3;
	
	
	public static void main(String [] args){
		String password = "password";
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
    }

    /**
     * The type of the packet, see above static finals for types
     */
    private int type;

    /**
     * An array of strings
     */
    private ArrayList<String> fields;

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

	public ArrayList<String> getFields() {
		return fields;
	}

	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<DatePref> getDates() {
		return dates;
	}

	public void setDates(ArrayList<DatePref> dates) {
		this.dates = dates;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
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