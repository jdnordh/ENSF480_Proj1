package server;

import structures.Meeting;

/**
 * 
 */
public interface FindMeetingTimeStrategyInterface {
	
    /**This function should attempt to find a date for all unconfirmed meetings in MeetingList
     *
     * It should search through all meetings in MeetingList, and find those with status unscheduled, but 
     *
     * @return False if no time was found, true if time was set
     */
    public void FindMeetingTimes();

}