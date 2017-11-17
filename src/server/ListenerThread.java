package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.io.EOFException;

import structures.*;

/**
 * This thread listens to the client through the socket
 * It also computes queries 
 * 
 * @author Jordan
 *
 */
public class ListenerThread extends ShutdownThread{

	private boolean running;
	
	private Queue<Packet> queue;
	
	private ObjectInputStream in;
	
	private IOThread master;
	
	private User user;
	
	/**
	 * This thread notifies the client of new meetings that involve them
	 */
	private NotifyThread notifier;
	
	public ListenerThread(ObjectInputStream s, Queue<Packet> q, IOThread mas) {
		master = mas;
		in = s;
		queue = q;
		user = null;
		notifier = new NotifyThread(queue);
		
	}


	public void run(){
		running = true;
		
		System.out.println("ListenerThread " + this.getId() + " starting");
		
		notifier.start();
		while (running){
			try {
				//System.out.println("ListenerThread " + this.getId() + " waiting for object");
				if (in == null) {
					running = false;
					throw new CloseSocketException();
				}
				Packet p = (Packet) in.readObject();
				
				if ( !(p instanceof Packet)) throw new ClassNotFoundException("BAD REQUEST");
				
				System.out.println("ListenerThread " + this.getId() + " recieved packet type " + p.getType());
				
				// classify the packet
				
// CLOSE
				if (p.getType() == Packet.CLOSE_CONNECTION){
					running = false;
					master.shutdown();
					master.interrupt();
				}
				
// LOGIN
				else if (p.getType() == Packet.LOGIN){
					this.login(p);
				}
				// all remaining requests require user to be logged in
				else if (user != null){
// REQUEST ALL USERS
					if (p.getType() == Packet.REQUEST_ALL_USERS){
						this.sendAllUsers();
					}
// INITIATE_MEETING
					else if (p.getType() == Packet.INITIATE_MEETING){
						this.initiateMeeting(p);
					}
// ADD_USER
					else if (p.getType() == Packet.ADD_USER){
						this.addUser(p);
					}
// DELETE_USER
					else if (p.getType() == Packet.DELETE_USER && user.isAdmin()){
						this.deleteUser(p);
					}
// ACCEPT_MEETING
					else if (p.getType() == Packet.ACCEPT_MEETING){
						this.acceptMeeting(p);
					}
// DELCINE_MEETING
					else if (p.getType() == Packet.DECLINE_MEETING){
						this.declineMeeting(p);
					}
// REQUEST_ALL_MEETINGS
					else if (p.getType() == Packet.REQUEST_ALL_MEETINGS){
						this.requestAllMeetings(p);
					}
// REQUEST_ALL_LOCATIONS
					else if (p.getType() == Packet.REQUEST_ALL_LOCATIONS){
						this.getAllLocations(p);
					}
// ADD_LOCATION
					else if (p.getType() == Packet.ADD_LOCATION && user.isAdmin()){
						this.addLocation(p);;
					}
// DELETE_LOCATION
					else if (p.getType() == Packet.DELETE_LOCATION && user.isAdmin()){
						this.deleteLocation(p);;
					}
					else {
						this.sendBadRequest("User not null, but unknown request");
					}
				}
				else {
					this.sendBadRequest("Final else");
				}
				//System.out.println("ListenerThread " + this.getId() + " finished dealing with packet " + p.getType());
			} catch (SocketTimeoutException e){
				// times out to check if the thread should still be running
				//System.out.println("ListenerThread " + this.getId() + " timeout");
			} catch (SocketException e){
				// socket is ded
				//e.printStackTrace();
				this.shutdown();
				master.shutdown();
				
			}catch (CloseSocketException | EOFException e){
				//close down
				running = false;
			}catch (ClassNotFoundException | IOException e) {
				sendBadRequest("Class or IO exception");
				//e.printStackTrace();
			}catch (Exception e){
				sendBadRequest("General Exception");
				e.printStackTrace();
			}
		}
		notifier.shutdown();
		notifier.interrupt();
		System.out.println("ListenerThread " + this.getId() + " stopping");
	}

	private void requestAllMeetings(Packet p) {
		Packet r = new Packet(Packet.RESPONSE_ALL_MEETINGS);
		MeetingList ml = MeetingList.getMeetingList();
		
		r.setMeetings(ml.getMeetings());
		queue.push(r);
	}


	private void deleteLocation(Packet p) {
		LocationList ll = LocationList.getLocationList();
		boolean deleted = ll.deleteLocation(p.getLocations().get(0));
		
		Packet r = null;
		if (deleted){
			r = new Packet(Packet.DELETE_LOCATION_CONFIRM);
		}
		else {
			r = new Packet(Packet.DELETE_LOCATION_DENY);
		}
		queue.push(r);
	}


	private void addLocation(Packet p) {
		LocationList ll = LocationList.getLocationList();
		boolean added = ll.addLocation(p.getLocations().get(0));
		
		Packet r = null;
		if (added){
			r = new Packet(Packet.ADD_LOCATION_CONFIRM);
		}
		else {
			r = new Packet(Packet.ADD_LOCATION_DENY);
		}
		queue.push(r);
	}


	private void getAllLocations(Packet p) {
		LocationList ll = LocationList.getLocationList();
		
		Packet r = new Packet(Packet.RESPONSE_ALL_LOCATIONS);
		r.setLocations(ll.getLocations());
		
		queue.push(r);
	}


	private void declineMeeting(Packet p) {
		// Double value function:
		// Initial decline
		// Or remove after already accepting
		MeetingList ml = MeetingList.getMeetingList();
		ml.declineMeeting(p);
		
		Packet r = new Packet(Packet.ACCEPT_OR_DECLINE_RESPONSE);
		queue.push(r);
	}


	private void acceptMeeting(Packet p) {
		MeetingList ml = MeetingList.getMeetingList();
		ml.acceptMeeting(p);
		
		Packet r = new Packet(Packet.ACCEPT_OR_DECLINE_RESPONSE);
		queue.push(r);
	}


	private void deleteUser(Packet p) {
		UserList ul = UserList.getUserList();
		boolean deleted = ul.deleteUser(p.getUsers().get(0));
		
		Packet r = null;
		if (deleted){
			r = new Packet(Packet.DELETE_USER_CONFIRM);
		}
		else {
			r = new Packet(Packet.DELETE_USER_DENY);
		}
		queue.push(r);
	}


	/**
	 * Add user to userlist
	 * @param p Packet
	 */
	private void addUser(Packet p) {
		System.out.println("adding User");
		UserList ul = UserList.getUserList();
		boolean added = ul.addUser(p.getUsers().get(0));
		
		Packet r = null;
		if (added){
			r = new Packet(Packet.ADD_USER_CONFIRM);
		}
		else {
			r = new Packet(Packet.ADD_USER_DENY);
		}
		queue.push(r);
	}


	/**
	 * Make a meeting
	 * @param p Packet from client
	 */
	private void initiateMeeting(Packet p) {
		Packet response = new Packet(Packet.INITIATE_MEETING_CONFIRM);

		MeetingList m = MeetingList.getMeetingList();
		m.addMeeting(p.getMeetings().get(0));
		
		queue.push(response);
	}


	/**
	 * Send all users to the client
	 */
	private void sendAllUsers() {
		Packet response = new Packet(Packet.RESPONSE_ALL_USERS);
		UserList ul = UserList.getUserList();
		response.setUsers(ul.getUsers());
		
		queue.push(response);
	}


	/**
	 * Send a login response
	 * 
	 * 
	 * @param log Login type
	 */
	private void login(Packet p) {
		Packet response;
		UserList ul = UserList.getUserList();
		User log = ul.login(p.getUsers().get(0));
		
		if (log == null){
			response = new Packet(Packet.LOGIN_DENY);
		}
		else {
			user = log;
			if (log.isAdmin()){
				response = new Packet(Packet.LOGIN_CONFIRM_ADMIN);
			}
			else {
				response = new Packet(Packet.LOGIN_CONFIRM_USER);
			}
			response.addUser(log);
			notifier.setUser(log);
		}
		queue.push(response);
	}


	/**
	 * Send a bad request packet back to the client
	 */
	private void sendBadRequest(String debug){
		Packet p = new Packet(Packet.BAD_REQUEST);
		System.out.println("ListenerThread " + this.getId() + " sending bad request\nDebug: " + debug);
		queue.push(p);
	}
	
	public void shutdown() {
		running = false;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
