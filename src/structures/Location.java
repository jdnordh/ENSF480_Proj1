package structures;

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

    /**
     * Construct a location
     * @param string Name
     * @param string2 City
     * @param string3 Address
     */
    public Location(String string, String string2, String string3) {
		Name = string;
		City = string2;
		Address = string3;
	}
    
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
    
    /**
     * Check if two locations are equal
     * @param l Location
     * @return True if equal
     */
	public boolean isEqualTo(Location l) {
		if (this.Name.equals(l.getName())){
			if (this.City.equals(l.getCity())){
				if (this.Address.equals(l.getAddress()))
					return true;
			}
		}
		return false;
	}
    
	/**
	 * Return the string of this location, for printing
	 */
	public String toString(){
		String r = "";
		r += Name + ", ";
		r += Address + ", ";
		r += City;
		return r;
	}
    
}