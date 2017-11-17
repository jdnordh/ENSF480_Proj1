package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import structures.User;

/**
 * A singleton class
 * @author Jordan
 *
 */
public class UserList {
	/** User catalog file name */
	final String objectFileName = "users.j";
	
	
	
	 //For testing purposes only
	public static void main(String [] args){
		UserList u = UserList.getUserList();
		User m = new User("Test User", "tester", "123456");
		m.setAdmin(true);
		u.addUser(m);
		
		ArrayList<User> us = u.getUsers();
		for (int i = 0; i < us.size(); i++){
			System.out.println(us.get(i).getUserName());
		}
		
		if (u.login(m) != null) System.out.println("I'm in!");
	}
	
	
	/** the list of meetings */
	private ArrayList<User> users;
	
	/** The only instance */
	private static UserList onlyOne;
	
	
	/** Used for storing WebObjects */
	private ObjectOutputStream objectOut;
	
	private UserList(){
		users = this.readObjectFile();
	}
	
	/** get the only instance
	 * 
	 * @return A MeetingList
	 */
	public static UserList getUserList(){
		if (onlyOne == null) {
			onlyOne = new UserList();
			System.out.println("UserList initialized");
		}
		return onlyOne;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	/** 
		
	 * Attempt to login a user
	 * @param u User
	 * @return A login code, see above
	 */
	public User login(User u){
		User l = null;
		for (int i = 0; i < users.size(); i++){
			if(u.getUserName().equals(users.get(i).getUserName())) {
				if (u.getPassword().equals(users.get(i).getPassword())){
					l = new User(users.get(i));
				}
			}
		}
		return l;
	}
	
	/**
	 * Delete a user from the list, only id is needed
	 * @param m User to be deleted
	 * @return True if user is deleted, false if not found
	 */
	public boolean deleteUser(User m){
		for (int i = 0; i < users.size(); i++){
			if (users.get(i).isEqualTo(m)){
				users.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a user with a unique username
	 * @param u User
	 * @return True if added, false if duplicate username
	 */
	public boolean addUser(User u){
		boolean added = true;
		//check for duplicate username
		for (int i = 0; i < users.size(); i++){
			if (users.get(i).isEqualTo(u)) added = false;
		}
		if (added) {
			users.add(u);
			// write new version to file
			this.writeToObjectOutputStream();
		}
		//else System.out.println("Duplicate Username");
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
			for (int i = 0; i < users.size(); i++){
				objectOut.writeObject(users.get(i));
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
	private ArrayList<User> readObjectFile() {
		ArrayList<User> arr = new ArrayList<User>();
		try
		{
			FileInputStream fis = new FileInputStream(new File(objectFileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			// Read objects
			User temp = null;
			
			while( (temp = (User) ois.readObject()) != null){
				arr.add(temp);
			}

			ois.close();
			fis.close();
			
			
		} catch ( IOException ioException ) {
			//System.out.println( "Finished reading user file" );
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
