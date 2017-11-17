package server;

import java.util.ArrayList;

import structures.Meeting;
import structures.Packet;
import structures.Participant;
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
							
							//TODO check if needed
							if (m.get(i).containsParticipant(user.getUserName())) {
								if (m.get(i).getmeetingState() == Meeting.Finalized || 
									m.get(i).getmeetingState() == Meeting.waitingForDates ||
									m.get(i).getmeetingState() == Meeting.waitingForLocationPref || 
									m.get(i).getmeetingState() == Meeting.waitingForFinalized) {
									p = new Packet(Packet.NOTIFY_NEW_MEETING);
									break;
								}
								else if (m.get(i).getmeetingState() == Meeting.DATEOUTSIDERANGE){
									//TODO send notification to non important people
									ArrayList<Participant> parts = m.get(i).getParticipants();
									
									//Check if the user is important
									for (int j = 0; j < parts.size(); j++){
										if (parts.get(j).isEqualTo(user) && !parts.get(i).isImportant()){
											p = new Packet(Packet.NOTIFY_MEETING_DROP_REQUEST);
											break;
										}
									}
								}
								else if (m.get(i).getmeetingState() == Meeting.NODATEFOUND){
									if (m.get(i).getMeetingInitiator().isEqualTo(user)){
										p = new Packet(Packet.NOTIFY_NO_MEETING_TIME);
									}
								}
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
