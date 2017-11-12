package server;

import structures.Meeting;

/**
 * 
 */
public interface FindMeetingTimeStrategyInterface {
	
    /**This function should attempt to find a date for all unconfirmed meetings in MeetingList
     *
     * It should search through all meetings in MeetingList, and find those with status unscheduled
     *
     */
  

	public void FindMeetingTimes(Meeting m);

}