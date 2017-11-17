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
		System.out.println("SchedulerThread " + this.getId() + " is starting ");
		
		MeetingList ml = MeetingList.getMeetingList();
		while (running) {
			try {
				//System.out.println("SchedulerThread " + this.getId() + " is waiting ");
				synchronized(ml){
					//while (!ml.isChanged()) ml.wait();
					sleep(1000);
					//System.out.println("SchedulerThread " + this.getId() + " is in action ");
					
					//TODO fix the call
					
					//ml.setChanged(false);
					strategy.FindMeetingTimes();
					
				}
			} catch (InterruptedException e){
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("SchedulerThread " + this.getId() + " is stopped ");
	}
	
	public void shutdown(){
		running = false;
	}
	
	// THE FOLLOWING IS FOR TESTING ONLY
	
	
	public static void main(String [] args){
		FindMeetingTimeStrategyInterface f = new FindMeetingTimeStrategy1();
		SchThread s = new SchThread(f);
		
		s.start();
		try {
			s.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
