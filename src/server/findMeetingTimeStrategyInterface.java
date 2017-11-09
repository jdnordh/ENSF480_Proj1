package server;


/**
 * 
 */
public interface FindMeetingTimeStrategyInterface {
	
    /**This function should attempt to find a date for all unconfirmed meetings in MeetingList
     *
     * @return False if no time was found, true if time was set
     */
    public void FindMeetingTimes();

}