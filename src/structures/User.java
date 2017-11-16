package structures;

import java.io.Serializable;

/**
 * 
 */
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * True if the user is admin, default is false
     */
    private boolean isAdmin = false;
    
    public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
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
    
    public User(){}
    
    public User(String U){
    	UserName = U;
    }
    
    /**
     * Construct a user with only username and password
     * @param U Username
     * @param P Password
     */
    public User(String U, String P){
    	UserName = U;
    	Password = P;
    }
    /**
     * Make a user with all string fields
     * @param N Name
     * @param U Username
     * @param P Password (In SHA-256 hash)
     *      */
    public User(String N , String U, String P){
    	Name = N;
    	UserName = U;
    	Password = P;
    }

    /**
     * Copy constructor
     * @param user User to be copied
     */
	public User(User user) {
		Name = new String(user.getName());
    	UserName = new String(user.getUserName());
    	Password = new String(user.getPassword());
    	isAdmin = user.isAdmin();
	}
	
	
	public boolean isEqualTo(User u) {
		if (UserName.equals(u.getUserName())) return true;
		return false;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
}