package hms;

/**
 * StaffMember class entends from user
 */
public class StaffMember extends User {
	private String name;
	private String gender;
	private int age;
	private String ID;
	private boolean isDefaultPassword;

	/**
	 * StaffMember constructor 
	 * @param hospitalID hospitalID
	 * @param password password
	 * @param role role
	 * @param name name
	 * @param gender gender
	 * @param age age
	 * @param isDefaultPassword is default password
	 */
	public StaffMember(String hospitalID, String password, String role, String name, String gender, int age, boolean isDefaultPassword) {
		super(hospitalID, name, password, role); // Call the constructor of User class
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.isDefaultPassword = isDefaultPassword;
	}

	// Getter methods
	/**
	 * getName getter method returns the name
	 * @return name of staff
	 */
	public String getName() {
		return name;
	}

	/**
	 * getGender getter method returns the gender
	 * @return gender of staff
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * getAge getter method returns the age of the staff
	 * @return age of staff
	 */
	public int getAge() {
		return age;
	}
    
	/**
	 * getId getter method returns the id of staff
	 * @return id of staff
	 */
	public String getId() {
		return ID;
	}

	/**
	 * getisDefaultPassword getter method returns the boolean value of whether password is default
	 * @return boolean value of whether password is default
	 */
	public boolean getisDefaultPassword()
	{
		return isDefaultPassword;
	}
	
	// Setter methods
	/**
	 * setName setter method sets the name of staff
	 * @param name name of staff
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setGender setter method sets the gender of staff
	 * @param gender gender of staff
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * setAge setter method sets the age of staff
	 * @param age age of staff
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * setID setter method sets the id of staff
	 * @param ID id of staff
	 */
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * setisDefaultPassword setter method sets the boolean value of whether password is default or not
	 * @param isDefaultPassword boolean value of whether password is default or not
	 */
	public void setisDefaultPassword(boolean isDefaultPassword)
	{
		this.isDefaultPassword = isDefaultPassword;
	}
	
	// Method to display staff information\
	/**
	 * displayInfo displays staff info
	 * @return staff info
	 */
	public String displayInfo() {
		return "ID: " + getHospitalID() + ", Name: " + name + ", Role: " + getRole() + ", Gender: " + gender + ", Age: "
				+ age;
	}

}