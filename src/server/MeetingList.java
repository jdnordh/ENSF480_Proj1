package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.ObjectInputStream;

import structures.*;
/**
 * A singleton class
 * @author Jordan
 *
 */
public class MeetingList {
	
	/** the list of meetings */
	private ArrayList<Meeting> meetings;
	
	/** The only instance */
	private static MeetingList onlyOne;
	
	/** Used for meetings to be scheduled */
	private boolean schedulerGo = false;

	/** Used for storing WebObjects */
	private ObjectOutputStream objectOut;
	
	/** Meeting catalog file name */
	final String objectFileName = "meetings.j";
	
	private MeetingList(){
		meetings = this.readObjectFile();
		schedulerGo = true;
	}
	
	
	// For testing purposes only
	public static void main(String [] args){
		MeetingList ml = MeetingList.getMeetingList();
		
		
		UserList ul = UserList.getUserList();
		//participants,Location, Description, MeetingIniatorPrefDates , MeetingIniator
		ArrayList<User> u = ul.getUsers();
		Meeting m = new Meeting();
		
		
		m.setMeetingInitiator(u.get(u.size() - 1));
		System.out.print("user.size: "+ u.size());
		
		//ArrayList<Participant> p = new ArrayList<Participant>();
		
		for (int i = 0; i < u.size(); i++){
			m.addParticipant(u.get(i).getUserName(), u.get(i).getName(), true);
			
			//System.out.println(u.get(i).getUserName());
		}
		
		Date d = new Date();
		d.setTime(12);
		
		for (int i = 0; i < u.size(); i++){
			DatePref date = new DatePref(new Participant(u.get(i).getUserName(), u.get(i).getName(), true));
			date.addDate(d);
			
			m.acceptMeeting(date);
		}
		
		m.setmeetingState(Meeting.waitingForFinalized);
		m.setID(19);
		
		ml.addMeeting(m);
	}
	
	
	
	/** get the only instance
	 * 
	 * @return A MeetingList
	 */
	public static MeetingList getMeetingList(){
		if (onlyOne == null) {
			onlyOne = new MeetingList();
			System.out.println("MeetingList initialized");
		}
		return onlyOne;
	}
	
	/**
	 * Get the entire list of meetings, don't use to add meetings, only for scheduling
	 * @return An ArrayList of Meetings
	 */
	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}
	
	/**
	 * Delete a meeting from the list, only id is needed
	 * @param m Meeting to be deleted
	 * @return True if meeting is deleted, false if not found
	 */
	public synchronized boolean deleteMeeting(Meeting m, User u){
		for (int i = 0; i < meetings.size(); i++){
			if (meetings.get(i).isEqualTo(m) && meetings.get(i).getMeetingInitiator().isEqualTo(u)){
				meetings.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a meeting to the list 
	 * @param m Meeting
	 * @return True if added, false if duplicate ID
	 */
	public synchronized boolean addMeeting(Meeting m){
		boolean added = true;
		//check for duplicate meeting id
		
		for (int i = 0; i < meetings.size(); i++){
			if (meetings.get(i).getID() == m.getID()){ 
				System.out.println(m.getID());
				System.out.println(meetings.get(i).getID());
				added = false;
				break;
			}
		}
				
		if (added){
			meetings.add(m);
			// write new version to file
			this.writeToObjectOutputStream();
		}
		//else System.out.println("Duplicate meeting id");
		if (added) this.setChanged(true);
		return added;
	}
	
	/**
	 * Opens and writes to an ObjectOutputStream
	 * @param WebObject object to be added to schedule
	 */
	private void writeToObjectOutputStream() {
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(objectFileName));
			objectOut = new ObjectOutputStream( fos );
			if (objectOut == null) throw new Exception();
			for (int i = 0; i < meetings.size(); i++){
				objectOut.writeObject(meetings.get(i));
			}
			
		} catch (IOException e){
			System.err.println("Error opening output file: " + objectFileName);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println( "Object output stream is null" );
		} finally {
			if (objectOut != null) {
				try {
					objectOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Reads catalog file and creates an ArrayList containing all stored web objects
	 */
	private ArrayList<Meeting> readObjectFile() {
		ArrayList<Meeting> arr = new ArrayList<Meeting>();
		try
		{
			FileInputStream fis = new FileInputStream(new File(objectFileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			// Read objects
			Meeting temp = null;
			
			while( (temp = (Meeting) ois.readObject()) != null){
				arr.add(temp);
			}

			ois.close();
			fis.close();
			
			
		} catch ( IOException ioException ) {
			//System.out.println( "Finished reading meeting file.\n" );
			//ioException.printStackTrace();
		} catch ( NoSuchElementException elementException ) {
			System.err.println( "Invalid input. Please try again." );
		} catch (Exception e) {
			System.err.println( "Object output stream is null" );
		} finally {
			if (objectOut != null) {
				try {
					objectOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return arr;
	}


	public boolean isChanged() {
		return schedulerGo;
	}


	public void setChanged(boolean changed) {
		System.out.println("schedulerGO is " + changed);
		this.schedulerGo = changed;
		//if (changed) notifyAll();
	}

	/**
	 * Decline a meeting
	 * @param p Packet that contains the correct info, see Packet constants
	 * @return True if accomplished
	 */
	public synchronized boolean declineMeeting(Packet p) {
		
		for (int i = 0; i < meetings.size(); i++){
			Meeting check = meetings.get(i);
			if (check.getID() == p.getNumber1() && 
					check.containsParticipant(p.getUsers().get(0).getUserName())){
				check.removeParticipant(p.getUsers().get(0));
				this.setChanged(true);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Accept a meeting
	 * @param p Packet with correct info
	 * @return True if accepted
	 */
	public synchronized boolean acceptMeeting(Packet p) {
		
		for (int i = 0; i < meetings.size(); i++){
			Meeting check = meetings.get(i);
			if (check.getID() == p.getNumber1() && check.containsParticipant(p.getUsers().get(0).getUserName())){
				check.acceptMeeting( p.getDates());
				this.setChanged(true);
				return true;
			}
		}
		
		return false;
	}
}
