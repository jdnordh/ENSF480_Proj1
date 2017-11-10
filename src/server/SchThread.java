package server;

import structures.*;

public class SchThread extends ShutdownThread{
	
	boolean running;
	
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
			
				//wait while no new meetings
				while (!ml.isChanged()){
					wait();
				}
			
				strategy.FindMeetingTimes();
				ml.setChanged(false);
				
			} catch (InterruptedException e){
				
			}
		}
		
	}
	
	public void shutdown(){
		running = false;
	}
}
