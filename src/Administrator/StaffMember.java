package hms;

public class StaffMember extends User {
	private String name;
	private String gender;
	private int age;
	//private String role;
	private String ID;
	private boolean isDefaultPassword;

	public StaffMember(String hospitalID, String password, String role, String name, String gender, int age, boolean isDefaultPassword) {
		super(hospitalID, name, password, role); // Call the constructor of User class
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.isDefaultPassword = isDefaultPassword;
	}

	// Getter methods
	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}
	
	/*
	public String getRole() {
		return role;
	}
    */
	public String getId() {
		return ID;
	}

	public boolean getisDefaultPassword()
	{
		return isDefaultPassword;
	}
	
	// Setter methods
	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setisDefaultPassword(boolean isDefaultPassword)
	{
		this.isDefaultPassword = isDefaultPassword;
	}
	
	// Method to display staff information
	public String displayInfo() {
		return "ID: " + getHospitalID() + ", Name: " + name + ", Role: " + getRole() + ", Gender: " + gender + ", Age: "
				+ age;
	}

}