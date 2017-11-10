package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
	private Queue<Meeting> meetingsToBeScheduled;
	
	/** 
	 * Get the queue of meetings
	 * @return Queue<Meetings>
	 */
	public Queue<Meeting> getMeetingsToBeScheduled() {
		return meetingsToBeScheduled;
	}

	/** Used for storing WebObjects */
	private ObjectOutputStream objectOut;
	
	/** Meeting catalog file name */
	final String objectFileName = "meetings.j";
	
	private MeetingList(){
		meetings = this.readObjectFile();
	}
	
	
	// For testing purposes only
	public static void main(String [] args){
		MeetingList u = MeetingList.getMeetingList();
		Meeting m = new Meeting();
		m.setID(1234);
		u.addMeeting(m);
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
	 * Add a meeting to be scheduled
	 * @param m Meeting
	 */
	public void addMeetingToBeScheduled(Meeting m){
		meetingsToBeScheduled.push(m);
	}
	
	/**
	 * Get the entire list of meetings
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
	public boolean deleteMeeting(Meeting m){
		for (int i = 0; i < meetings.size(); i++){
			if (meetings.get(i).isEqualTo(m)){
				meetings.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean addMeeting(Meeting m){
		boolean added = true;
		
		//check for duplicate meeting id
		for (int i = 0; i < meetings.size(); i++){
			if (meetings.get(i).isEqualTo(m)) added = false;
		}
		
		if (added){
			meetings.add(m);
			// write new version to file
			this.writeToObjectOutputStream();
		}
		//else System.out.println("Duplicate meeting id");
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
}