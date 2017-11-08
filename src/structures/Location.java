
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Location implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String Name;

    /**
     * 
     */
    private String City;

    /**
     * 
     */
    private String Address;

    
    public void setName(String n){
    	Name = n;
    }
    public String getName(){
    	return Name;
    }
    public void setCity(String c){
    	City = c;
    }
    public String getCity(){
    	return City;
    }
    public void setAddress(String a){
    	Address = a;
    }
    public String getAddress(){
    	return Address;
    }
}