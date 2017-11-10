package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import structures.Location;

/**
 * A singleton class
 * @author Jordan
 *
 */
public class LocationList {
	/** Location catalog file name */
	final String objectFileName = "locations.j";
	
	
	/*
	// For testing purposes only
	public static void main(String [] args){
		LocationList u = LocationList.getLocationList();
		Location m = new Location("Test Location1", "Calgary", "123 1st Street");
		u.addLocation(m);
		m = new Location("Test Location2", "Edmonton", "420 69th Ave");
		u.addLocation(m);
		m = new Location("Test Location3", "Vancouver", "360 No Scope Boulevard");
		u.addLocation(m);
		
		ArrayList<Location> us = u.getLocations();
		for (int i = 0; i < us.size(); i++){
			System.out.println(us.get(i).toString());
		}
		
	}
	*/
	
	/** the list of meetings */
	private ArrayList<Location> locations;
	
	/** The only instance */
	private static LocationList onlyOne;
	
	
	/** Used for storing WebObjects */
	private ObjectOutputStream objectOut;
	
	private LocationList(){
		locations = this.readObjectFile();
	}
	
	/** get the only instance
	 * 
	 * @return A MeetingList
	 */
	public static LocationList getLocationList(){
		if (onlyOne == null) {
			onlyOne = new LocationList();
			System.out.println("LocationList initialized");
		}
		return onlyOne;
	}

	/**
	 * Get all locations
	 * @return ArrayList of locations
	 */
	public ArrayList<Location> getLocations() {
		return locations;
	}
	
	
	/**
	 * Delete a location from the list
	 * @param m Location to be deleted
	 * @return True if location is deleted, false if not found
	 */
	public boolean deleteLocation(Location m){
		for (int i = 0; i < locations.size(); i++){
			if (locations.get(i).isEqualTo(m)){
				locations.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a location with a unique location
	 * @param u Location
	 * @return True if added, false if duplicate location
	 */
	public boolean addLocation(Location u){
		boolean added = true;
		//check for duplicate username
		for (int i = 0; i < locations.size(); i++){
			if (locations.get(i).isEqualTo(u)) added = false;
		}
		if (added) {
			locations.add(u);
			// write new version to file
			this.writeToObjectOutputStream();
		}
		//else System.out.println("Duplicate Locationname");
		return added;
	}
	
	/**
	 * Opens and writes to an ObjectOutputStream
	 */
	private void writeToObjectOutputStream() {
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(objectFileName));
			objectOut = new ObjectOutputStream( fos );
			if (objectOut == null) throw new Exception();
			for (int i = 0; i < locations.size(); i++){
				objectOut.writeObject(locations.get(i));
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
	 * Reads catalog file and creates an ArrayList containing all stored objects
	 */
	private ArrayList<Location> readObjectFile() {
		ArrayList<Location> arr = new ArrayList<Location>();
		try
		{
			FileInputStream fis = new FileInputStream(new File(objectFileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			// Read objects
			Location temp = null;
			
			while( (temp = (Location) ois.readObject()) != null){
				arr.add(temp);
			}

			ois.close();
			fis.close();
			
			
		} catch ( IOException ioException ) {
			System.out.println( "Finished reading location file.\n" );
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
