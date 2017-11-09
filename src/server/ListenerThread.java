package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import structures.*;

/**
 * This thread listens to the client through the socket
 * It also computes queries 
 * 
 * @author Jordan
 *
 */
public class ListenerThread extends Thread{

	private boolean running;
	
	private Socket socket;
	
	private Queue<Packet> queue;
	
	private ObjectInputStream in;
	
	private IOThread master;
	
	private User user;
	
	public ListenerThread(Socket s, Queue<Packet> q, IOThread mas) {
		master = mas;
		socket = s;
		queue = q;
		try {
			socket.setSoTimeout(100);
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void run(){
		running = true;
		
		while (running){
			try {
				
				Packet p = (Packet) in.readObject();
				
				if ( !(p instanceof Packet)) throw new ClassNotFoundException("BAD REQUEST");
				
				//TODO classify the packet
				
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
// REQUEST ALL USERS
				else if (p.getType() == Packet.REQUEST_ALL_USERS){
					this.sendAllUsers();
				}
// INITIATE MEETING
				else if (p.getType() == Packet.INITIATE_MEETING){
					this.initiateMeeting(p);
				}
				
				
			} catch (SocketTimeoutException e){
				// times out to check if the thread should still be running
				
			} catch (ClassNotFoundException | IOException e) {
				sendBadRequest();
				e.printStackTrace();
			}
			catch (Exception e){
				sendBadRequest();
				e.printStackTrace();
			}
		}
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
	public static final int INVALID = 0;
	public static final int USER = 1;
	public static final int ADMIN = 2;
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
		}
		queue.push(response);
	}


	/**
	 * Send a bad request packet back to the client
	 */
	private void sendBadRequest(){
		Packet p = new Packet(Packet.BAD_REQUEST);
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
