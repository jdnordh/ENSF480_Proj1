package server;

import java.util.ArrayList;

import structures.Meeting;
import structures.Packet;
import structures.User;

/**
 * 
 * @author Jordan
 *
 */
public class NotifyThread extends ShutdownThread{
	
	private boolean running;
	
	private Queue<Packet> queue;
	
	private User user;
	
	/**
	 * Set the user of this thread
	 * @param user User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public NotifyThread(Queue<Packet> q) {
		queue = q;
		user = null;
	}
	
	public void run(){
		//TODO test
		running = true;
		System.out.println("Scheduler thread starting");
		
		MeetingList ml = MeetingList.getMeetingList();
		while (running) {
			try {
				System.out.println("NotifierThread " + this.getId() + " is waiting ");
				synchronized(ml){
					while (!ml.isChanged()) ml.wait();
					
					System.out.println("NotifierThread " + this.getId() + " is in action ");
					
					ArrayList<Meeting> m = ml.getMeetings();
					
					Packet p = null;
					
					if (user != null){
						for (int i = 0; i < m.size(); i++){
							if (m.get(i).containsParticipant(user.getUserName())) {
								p = new Packet(Packet.NEW_MEETING);
								break;
							}
						}
					}
					
					if (p != null) queue.push(p);
					
				}
			} catch (InterruptedException e){
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void shutdown(){
		running = false;
	}
	
}
