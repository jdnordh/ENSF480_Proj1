package server;

import structures.*;

public class SchThread extends ShutdownThread{
	
	boolean running;
	
	private FindMeetingTimeStrategyInterface strategy;
	
	public SchThread(FindMeetingTimeStrategyInterface strat){
		strategy = strat;
	}
	
	public void run(){
		//TODO
		running = true;
		
		System.out.println("Scheduler thread starting");
		
		MeetingList ml = MeetingList.getMeetingList();
		while (running) {
			
			try {
			
				Queue<Meeting> queue = ml.getMeetingsToBeScheduled();
			
				//wait while queue is empty
				while (queue.isEmpty()){
					wait();
				}
			
				
				
			} catch (InterruptedException e){
				
			}
		}
		
	}
	
	public void shutdown(){
		running = false;
	}
}
