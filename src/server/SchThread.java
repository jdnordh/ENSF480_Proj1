package server;


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
				System.out.println("SchedulerThread " + this.getId() + " is waiting ");
				synchronized(ml){
					while (!ml.isChanged()) ml.wait();
				}
				System.out.println("SchedulerThread " + this.getId() + " is in action ");
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
