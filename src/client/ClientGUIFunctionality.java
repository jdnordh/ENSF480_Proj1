package client;

import java.util.*;

import structures.Meeting;
import structures.Packet;
import structures.User;



/**
 * 
 */
public interface ClientGUIFunctionality {

    /**
     * 
     */
    void getAllMeetings();

    /**
     * 
     */
    void initateMeeting();

    /**
     * 
     */
    void acceptMeeting();

    /**
     * 
     */
    void declineMeeting();

    /**
     * 
     */
    void addLocationPref();

    /**
     * @return
     */
     void Update();
     public void getThreadPacket(Packet P);

	void removeMeeting(Meeting m);
     
     

}