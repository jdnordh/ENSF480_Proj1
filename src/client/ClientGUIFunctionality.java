package client;

import java.util.*;

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
     public static void getThreadPacket(Packet P){
    	 
     }
     
     

}