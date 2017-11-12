package structures;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class DatePref implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default constructor
     */
    public DatePref() {
    }

    /**
     *  The Participant that is selecting the prefered dates
     */
    private Participant Partic;

    /**
     *  array of dates that the participant would like the meeting to be on
     */
    private ArrayList<Date> prefDates;

    
    
    public void addDate(Date date){
    	prefDates.add(date);
    }
    
}