package structures;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Participant extends User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Name of Participant
     */
    private String Name;

    /**
     * Is the Participant important for this meeting
     */
    private Boolean Important;

    /**
     * The Username of the Participant linking them to the user
     */
    private String UserName;
    
    /**
     * Getters and Setters
     */
    public void setName(String n){
    	Name = n;
    }
    public String getName(){
    	return Name;
    }
    public void setImportant(Boolean x){
    	Important = x;
    }
    public Boolean isImportant(){
    	return Important;
    }
    
    public Participant(String N, String U, Boolean I) {
		super(N, U);
		Important = I;
	}
}