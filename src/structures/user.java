package structures;


/**
 * 
 */
public class user {
	
    /**
     * main identifier
     */
    protected String Name;

    /**
     * UserName for login and to link to participants
     */
    protected String UserName;

    /**
     * The password for login
     */
    private String Password;

    /**
     * Getters and Setters
     */
    public void setName(String n){
    	Name = n;
    }
    public String getName(){
    	return Name;
    }
    public String getUserName(){
    	return UserName;
    }
    public void setUserName(String s){
    	UserName = s;
    }
    public void setPassword(String s){
    	Password = s;
    }
    public String getPassword(){
    	return Password;
    }
    public user(String N , String U, String P){
    	Name = N;
    	UserName = U;
    	Password = P;
    }
}