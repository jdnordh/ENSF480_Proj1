package server;

import java.util.ArrayList;
import structures.*;
/**
 * A singleton class
 * @author Jordan
 *
 */
public class MeetingList {
	
	private ArrayList<Meeting> meetings;
	
	private MeetingList onlyOne;
	
	private MeetingList(){
		//TODO
		
		// read from file
	}
	
	/** get the only instance
	 * 
	 * @return A MeetingList
	 */
	public MeetingList getMeetingList(){
		if (onlyOne == null) 
			onlyOne = new MeetingList();
		return onlyOne;
	}

	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}
	
	public boolean deleteMeeting(Meeting m){
		for (int i = 0; i < meetings.size(); i++){
			if (meetings.get(i).isEqualTo(m)){
				
			}
		}
		return false;
	}
	
	public void addMeeting(Meeting m){
		meetings.add(m);
		//TODO write new version to file
		
		
	}
}
