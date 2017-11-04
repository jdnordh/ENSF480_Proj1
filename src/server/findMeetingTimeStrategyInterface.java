package server;


import structures.*;

/**
 * 
 */
public interface FindMeetingTimeStrategyInterface {

    /**This function should attempt to find a date for the meeting
     * @param Meeting 
     * @return False if no time was found, true if time was set
     */
    public boolean FindMeetingTime(Meeting meeting);

}