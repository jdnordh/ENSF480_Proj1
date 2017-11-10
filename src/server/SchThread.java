package server;

import structures.*;

/**
 * 
 * @author Jordan
 *
 */
public class SchThread extends ShutdownThread{
	
	private boolean running;
	
	private FindMeetingTimeStrategyInterface strategy;
	
	public SchThread(FindMeetingTimeStrategyInterface strat){
		strategy = strat;
	}
	
	public void run(){
		//TODO test
		running = true;
		System.out.println("Scheduler thread starting");
		
		MeetingList ml = MeetingList.getMeetingList();
		while (running) {
			try {
				sleep(1000);
				//wait while no new meetings
			
				if (ml.isChanged()) strategy.FindMeetingTimes();
				ml.setChanged(false);
				
			} catch (InterruptedException e){
				
			}
		}
		
	}
	
	public void shutdown(){
		running = false;
	}
	
}
