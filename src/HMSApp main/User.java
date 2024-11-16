package hms;

public class User {
	protected String hospitalID;
	protected String name;
	protected String password;
	protected String role;
	
	public User(String hospitalID, String name, String password, String role)
	{
		this.hospitalID = hospitalID;
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public String getHospitalID()
	{
		return hospitalID;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getRole()
	{
		return role;
	}
}
