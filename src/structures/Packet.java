package structures;
import java.util.ArrayList;

/**
 * 
 */
public class Packet{

    /**
     * Default constructor
     */
    public Packet() {
    }

    /**
     * 
     */
    private int type;

    /**
     * 
     */
    private ArrayList<String> fields;

    /**
     * 
     */
    private ArrayList<Object> users;

    /**
     * 
     */
    private ArrayList<Object> dates;

    /**
     * 
     */
    private ArrayList<Object> locations;

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

	public ArrayList<Object> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Object> users) {
		this.users = users;
	}

	public ArrayList<Object> getDates() {
		return dates;
	}

	public void setDates(ArrayList<Object> dates) {
		this.dates = dates;
	}

	public ArrayList<Object> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Object> locations) {
		this.locations = locations;
	}

	public ArrayList<Object> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<Object> meetings) {
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

	/**
     * 
     */
    private ArrayList<Object> meetings;

    /**
     * 
     */
    private int Number1;

    /**
     * 
     */
    private int number2;



}