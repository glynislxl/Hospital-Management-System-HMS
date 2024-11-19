package hms;

/**
 * User class contains the hospitalID, name, password and role
 */
public class User {
	protected String hospitalID;
	protected String name;
	protected String password;
	protected String role;
	
	/**
	 * User constructor 
	 * @param hospitalID hospistalID
	 * @param name name
	 * @param password password
	 * @param role role
	 */
	public User(String hospitalID, String name, String password, String role)
	{
		this.hospitalID = hospitalID;
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	/**
	 * getHospitalID getter method returns the hospitalID
	 * @return hospitalID
	 */
	public String getHospitalID()
	{
		return hospitalID;
	}
	
	/**
	 * getName getter method returns the name
	 * @return name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * setName setter method sets the name
	 * @param name name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * getPassword getter method returns the password
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * setPassword setter method sets the password
	 * @param password password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * getRole getter method returns the role
	 * @return role
	 */
	public String getRole()
	{
		return role;
	}
	
	/**
	 * setRole setter method sets the role
	 * @param role role
	 */
	public void setRole(String role)
	{
		this.role = role;
	}
}
