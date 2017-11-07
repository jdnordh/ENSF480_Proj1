
import java.util.*;

/**
 * 
 */
public class Meeting {

    /**
     * Default constructor
     */
    public Meeting() {
    }

    /**
     * 
     */
    private Date finalizedDate;

    /**
     * 
     */
    private ArrayList<Participant> Participants;

    /**
     * 
     */
    private Location Location;

    /**
     * 
     */
    private int ID;

    /**
     * 
     */
    private ArrayList<Date> preferedDates;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private int meetingState;

    /**
     * 
     */
    private ArrayList<datePref> preferedDateParticipant;







    /**
     * @param Id 
     * @param Important 
     * @return
     */
    private void addParticipant(String user, boolean i) {
        //to sever username
    	//check to see if exsits
    	//Participant p = new Participant(temp user, i);
    	//
    }

    /**
     * @return
     */
    private Void askLocationPref() {
        // TODO implement here
        return null;
    }

    /**
     * @param Participant 
     * @return
     */
    private void removeParticipant(Participant P) {
        //to server removeParticipant
    	//Participants.remove(P);
    }

}