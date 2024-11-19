package hms;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * HMSApp class 
 *  Health Management System (HMS)  is an application aimed at automating the management of hospital operations, 
 * including patient management, appointment scheduling, staff management, and billing. 
 * The system is expected to facilitate efficient management of hospital resources, enhance patient care, and streamline administrative processes. 
 */
public class HMSApp {
	private static Scanner sc = new Scanner(System.in);
	private static User currentUser;
	
	protected static List<User> users = new ArrayList<>(); // Store users with hospital ID as key
	protected static List<Patient> patients = new ArrayList<>();
    protected static List<Doctor> allDoctors = new ArrayList<>();
    protected static List<Pharmacist> pharmacists = new ArrayList<>();
    protected static List<Administrator> administrators = new ArrayList<>();
    protected static List<StaffMember> staffMembers = new ArrayList<>();
    
    /**
     * default HMSApp constructor
     */
    public HMSApp() {
    	//default constructor
    }
    
    /**
     * Main program for HMS where users get to login based on their HospitalID and a default password - "password"
     * @param args name
     */
	public static void main(String[] args)
	{
		String choice; 
		
		while (true)
		{
			System.out.println("Hospital Management System (HMS)");
			System.out.println("================================");
			
			System.out.println("1. Staff Login");
			System.out.println("2. Patient Login");
			
			System.out.println("Enter choice: ");
			choice = sc.nextLine();
			
			switch (choice)
			{
				case "1":
					//staff login
					loadStaffData();
					while (true)
					{
						if (authenticateStaff())
						{
							String role = currentUser.getRole();
							System.out.println("\n");
							System.out.println("Welcome " + currentUser.getHospitalID() + " (" + role + ")");
							displayRoleMenu();
							break;
						}
						else
						{
							System.out.println("Authentication failed. Please check your credentials.");
						}
					}
					break;
					
				case "2": 
					//patient login
					loadPatientData();
					while (true)
					{
						if (authenticatePatient())
						{
							String role = currentUser.getRole();
							System.out.println("\n");
							System.out.println("Welcome " + currentUser.getHospitalID() + " (" + role + ")");
							displayRoleMenu();
							break;
						}
						else
						{
							System.out.println("Authentication failed. Please check your credentials.\n");
						}
					}
					break;
					
				default:
					System.out.println("Invalid choice. Please choose again.\n");
			}
		}
	}

	/**
	 * get the currentUser
	 * @return currentUser is the object being referenced
	 */
	public User getCurrentUser()
	{
		return currentUser;
	}
	
	/**
	 * loadStaffData is a function to read the Staff data including StaffID, Name, Role etc. from the CSV file
	 */
	private static void loadStaffData() {
		String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Staff_List.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); // Skip header line
			while ((line = br.readLine()) != null) {
				
				String[] data = line.split(",");
				
				String staffId = data[0].trim();
				String name = data[1].trim();
				String role = data[2].trim();
				String gender = data[3].trim();
				int age = Integer.parseInt(data[4].trim());
				String password = data[5].trim();
				boolean isDefaultPassword = Boolean.parseBoolean(data[6].trim());

				// Create a staff member and add to the list
				StaffMember member = new StaffMember(staffId, password, role, name, gender, age, isDefaultPassword);
				
				try {
					staffMembers.add(member);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loadPatientData is a function to read the Patient data including PatientID, Name, Date of Birth etc. from the CSV file
	 */
	private static void loadPatientData()
	{
		String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); // Skip header line
			

			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				
				String patientId = data[0].trim();
				String name = data[1].trim();
				String dob = data[2].trim();
				String gender = data[3].trim();
				String phonenum = data[4].trim();
				String email = data[5].trim();
				String bloodtype = data[6].trim();
				String drugAllergy = data[7].trim();
				String pastDiagnoses = data[8].trim();
				String pastTreatments = data[9].trim();
				String meds = data[10].trim();
				String password = data[11].trim();
				boolean isDefaultPassword = Boolean.parseBoolean(data[12].trim());
				
				Patient patient = new Patient(patientId, name, dob, gender, phonenum, email, bloodtype, drugAllergy, pastDiagnoses, pastTreatments, meds, password, isDefaultPassword);
				patients.add(patient);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * authenticateStaff is a function to validate that the Staff exists 
	 * @return whether staff exists or not
	 */
	public static boolean authenticateStaff()
	{
		System.out.println("Enter Staff ID:");
		String staffID = sc.nextLine();
		
		// Check if staffID exists in the staffMembers list
	    StaffMember member = null;
	    for (StaffMember currentMember : staffMembers) {
	        if (currentMember.getHospitalID().equals(staffID)) {
	            member = currentMember;
	            break;
	        }
	    }
	    
	    // If the staff member doesn't exist, return false
	    if (member == null) {
	        System.out.println("Error: Staff ID does not exist.");
	        return false;
	    }
	    
	    // If the staff member exists, ask for the password
	    System.out.println("Enter password:");
	    String password = sc.nextLine();
	    
	    // Check if the entered password matches the stored password
	    if (member.getPassword().equals(password)) {
	        currentUser = member;
	        return true; // Authentication successful
	    } else {
	        System.out.println("Error: Incorrect password.");
	        return false; // Password is incorrect
	    }
	}
	
	
	/**
	 * authenticatePatient is a function to validate that the Patient exists,
	 * else a new Patient record will be created
	 * @return whether patient exists or not
	 */
	public static boolean authenticatePatient()
	{
		if (patients.isEmpty())
		{
			loadPatientData();
		}
		
		System.out.println("Enter Patient ID:");
		String patientID = sc.nextLine().trim();
	    
		// Check if staffID exists in the staffMembers list
	    Patient patient = null;
	    for (Patient currentPatient : patients) {
	        if (currentPatient.getHospitalID().equals(patientID)) {
	        	patient = currentPatient;
	            break;
	        }
	    }
	    
	    // If the patient doesn't exist, create new patient
	    if (patient == null) 
	    {
	        System.out.println("Creating new patient.");
	        
	        //patient id is patientID
	        
	        System.out.println("Enter your name:");
            String name = sc.nextLine();

            //validate dob
            String dob = "";
            while (true) {
                System.out.println("Enter your Date of Birth (e.g., DD/MM/YYYY):");
                dob = sc.nextLine().trim();

                // Check if the date format is DD/MM/YYYY
                if (dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    // Extract day, month, year from the input string
                    String[] dateParts = dob.split("/");
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);

                    // Check if the month is valid (1-12)
                    if (month >= 1 && month <= 12) {
                        // Basic check for valid day based on the month
                        if ((month == 2 && day >= 1 && day <= 29) ||  // Feb (leap year included here for simplicity)
                            (month != 2 && day >= 1 && day <= 31)) {
                            //System.out.println("Valid Date of Birth: " + dob);
                            break;  // Valid date, exit loop
                        } else {
                            System.out.println("Invalid day for this month. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid month. Please enter a month between 1 and 12.");
                    }
                } else {
                    System.out.println("Invalid format. Please use DD/MM/YYYY format.");
                }
            }

         // Validate Gender (Male/Female)
            String gender = "";
            while (true) {
                System.out.println("Enter your gender (e.g., Male/Female):");
                gender = sc.nextLine().trim();
                if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                    break;
                } else {
                    System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                }
            }

            // Validate Contact Number (10 digits, can adjust length as needed)
            String contactNumber = "";
            while (true) {
                System.out.println("Enter your contact number (e.g., 87654321):");
                contactNumber = sc.nextLine().trim();
                if (contactNumber.matches("\\d{8}")) { // You can adjust the length here
                    break;
                } else {
                    System.out.println("Invalid contact number. Please enter a 8-digit number.");
                }
            }

            // Validate Email using regular expression
            String email = "";
            while (true) {
                System.out.println("Enter your email (e.g., example@domain.com):");
                email = sc.nextLine().trim();
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                    break;
                } else {
                    System.out.println("Invalid email format. Please enter a valid email (e.g., example@domain.com).");
                }
            }

            // Validate Blood Type (A+, O-, AB+, etc.)
            String bloodType = "";
            while (true) {
                System.out.println("Enter your blood type (e.g., A+, O-, AB+, etc.):");
                bloodType = sc.nextLine().trim();
                if (bloodType.matches("^(A|B|AB|O)[+-]$")) {
                    break;
                } else {
                    System.out.println("Invalid blood type. Please enter a valid blood type (e.g., A+, O-, AB+).");
                }
            }

            String drugAllergy = "";
            while(true) {
            	System.out.println("Enter any drug allergy, enter None if nil:");
                drugAllergy = sc.nextLine().trim();
                if (drugAllergy.matches("(?i)(Ibuprofen|Amoxicillin|Paracetamol|Panadol)")) {
                	break;
                }
                else if (drugAllergy.matches("(?i)(None)")) {
                	break;
                }
                else {
                	System.out.println("Medication not provided in Hospital");
                	drugAllergy = "None";
                	break;
                }
            }
            
            // After validation, proceed with the rest of the logic (e.g., storing data)
            System.out.println("Patient details validated successfully!");
            
            // Create a new Patient object with the collected information
            //Default password, past diagnoses, past treatments, prescribed medications and isDefaultPassword is password, - , - , - and true respectively
            Patient newPatient = new Patient(patientID, name, dob, gender, contactNumber, email, bloodType, drugAllergy, "-" , "-" , "-" , "password", true);
            patients.add(newPatient); // Add the new patient to the list

            // Authenticate the newly created patient
            currentUser = newPatient;
            System.out.println("New patient account created and authenticated successfully.");
            
            //update csv
            String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
    		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
    			writer.append(patientID).append(",");
    	        writer.append(name).append(",");
    	        writer.append(dob).append(",");
    	        writer.append(gender).append(",");
    	        writer.append(contactNumber).append(",");
    	        writer.append(email).append(",");
    	        writer.append(bloodType).append(",");
    	        writer.append(drugAllergy).append(",");
    	        writer.append("-").append(","); //default past diagnoses
    	        writer.append("-").append(","); //default prescribed treatments
    	        writer.append("-").append(","); //default prescribed medications
    	        writer.append("password").append(","); //default password
    	        writer.append("TRUE").append(("\n")); //password is default hence true
    	        
    	        //System.out.println("New Patient added to CSV file: " + patientID);
    		} catch (IOException e) {
    		    System.out.println("An error occurred while writing to the CSV file.");
    		    e.printStackTrace();
    		}    		
            return true;
	    } else {
		    // If the patient exists, ask for the password
		    System.out.println("Enter password:");
		    String password = sc.nextLine().trim();
		    
		    // Check if the entered password matches the stored password
		    if (patient.getPassword().equals(password)) {
		        currentUser = patient;
		        
		        return true; // Authentication successful
		    } else {
		    	System.out.println("Error: Incorrect password.");
		    	return false;
		    }
        }
	}
	
	/**
	 * handleFirstLoginStaff is a function to allow Staff to change their default password after the first login
	 */
	//handle first login to allow password change
	public static void handleFirstLoginStaff()
	{
		// Check if the user has the default password
	    if (currentUser.getPassword().equals("password") && ((StaffMember) currentUser).getisDefaultPassword()) {
	        System.out.println("You must change your default password.");
	        changePassword(); // Use the general method for changing the password
	    }
	}
	
	/**
	 * handleFirstLoginPatient is a function to allow Patient to change their default password after the first login
	 */
	//handle first login to allow password change
	public static void handleFirstLoginPatient()
	{
		// Check if the user has the default password
	    if (currentUser.getPassword().equals("password") && ((Patient) currentUser).getisDefaultPassword()) {
	        System.out.println("You must change your default password.");
	        changePassword(); // Use the general method for changing the password
	    }  
	}
		
	/**
	 * changePassword method allows user to change their password
	 */
	public static void changePassword() {
	    String currentPassword;
	    String newPassword;
	    String newPassword1;
	    boolean valid = false;

	    // Prompt user to enter their current password
	    System.out.print("Enter your current password: ");
	    currentPassword = sc.nextLine().trim();

	    // Validate the current password
	    if (!currentUser.getPassword().equals(currentPassword)) {
	        System.out.println("Error: Current password is incorrect.");
	        return;
	    }

	    // Prompt for the new password
	    do {
	        System.out.print("Enter new password (must be at least 8 characters, include uppercase, lowercase, number, and symbol): ");
	        newPassword = sc.nextLine().trim();

	        // Check if new password is the same as old password
	        if (newPassword.equals(currentPassword))
	        {
	        	System.out.println("Error: New password cannot be the same as old password. Please try again.");
	        	continue; //restart the loop to prompt for a new password again
	        }
	        
	        // Validate the new password
	        if (newPassword.length() < 8 || 
	            !newPassword.matches(".*[A-Z].*") || 
	            !newPassword.matches(".*[a-z].*") || 
	            !newPassword.matches(".*\\d.*") || 
	            !newPassword.matches(".*[@$!%*?&_].*")
	        ) {
	            System.out.println("Error: Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.");
	            continue;
	        }
	        
	        // Prompt for re-entering the new password
	        System.out.print("Re-enter new password: ");
	        newPassword1 = sc.nextLine().trim();

	        // Check if passwords match
	        if (!newPassword.equals(newPassword1)) {
	            System.out.println("Error: Passwords do not match. Please try again.");
	        } else {
	            currentUser.setPassword(newPassword);
	            valid = true;
	        }
	    } while (!valid);

	    // Update the CSV file based on the type of the current user
	    String filePath;
	    if (currentUser instanceof StaffMember) {
	        filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Staff_List.csv";
	    	((StaffMember) currentUser).setisDefaultPassword(false);
	    } else if (currentUser instanceof Patient) {
	        filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
	    	((Patient) currentUser).setisDefaultPassword(false);
	    } else {
	        System.out.println("Error: Unknown user type.");
	        return;
	    }

	    // Update the CSV file
	    List<String> updatedLines = new ArrayList<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        // Read the header line and add it unchanged
	        String header = reader.readLine();
	        if (header != null) {
	            updatedLines.add(header);
	        }

	        // Read the rest of the lines and update the matching user
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");

	            // Determine if this is the current user
	            String userID = columns[0].trim();
	            if (userID.equals(currentUser.getHospitalID())) {
	                // Update password and default password flag
	                if (currentUser instanceof StaffMember) {
	                    columns[5] = currentUser.getPassword(); // Update password
	                    columns[6] = "FALSE"; // Update default password flag
	                } else if (currentUser instanceof Patient) {
	                    columns[11] = currentUser.getPassword(); // Update password
	                    columns[12] = "FALSE"; // Update default password flag
	                }
	                updatedLines.add(String.join(",", columns)); // Add updated line
	            } else {
	                updatedLines.add(line); // Add unchanged line
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	        return;
	    }

	    // Write the updated lines back to the CSV file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        for (String updatedLine : updatedLines) {
	            writer.write(updatedLine);
	            writer.newLine();
	        }
	    } catch (IOException e) {
	        System.out.println("Error writing to the CSV file: " + e.getMessage());
	    }

	    System.out.println("Password updated successfully!\n");
	}

		
	

	/**
	 * findUserByHospitalID is a function to validate that the user is found by their HospitalID
	 * @param hospitalID hospitalID
	 * @return the user that is found by the HospitalID
	 */
	public static User findUserByHospitalID(String hospitalID) {
        for (User user : users) {
            if (user.getHospitalID().equals(hospitalID)) {
                return user;
            }
        }
        return null; // Return null if user not found
    }
	
	/**
	 * getRoleByHospitalID function finds the relevant role 
	 * @param hospitalID hospitalID
	 * @return the role associated with hospitalID
	 */
	public static String getRoleByHospitalID(String hospitalID)
	{
		if (hospitalID.length() < 2)
		{
			System.out.println("Hospital ID must be at least 2 characters long.");
			return null;
		}
		
		String prefix = hospitalID.substring(0,2);
		switch(prefix)
		{
			case "PA":
				return "Patient";
			case "D0":
				return "Doctor";
			case "P0":
				return "Pharmacist";
			case "A0":
				return "Administrator";
			default: 
				return null;
		}
	}
	
	/**
	 * displayRoleMenu is a function that displays role specific menu 
	 */
	public static void displayRoleMenu()
	{
		String role = currentUser.getRole();
		System.out.println("You are logged in as a " + role);
		int choice; 
		
		switch (role)
		{
			case "Patient":
				Patient patient = null;
				for (Patient p : patients)
				{
					if(p.getHospitalID().equals(currentUser.getHospitalID()))
					{
						patient = p;
						break;
					}
					
				}
				
				if (patient != null)
				{
					do {
						System.out.println("Patient's Menu:");
						System.out.println("================================");
						System.out.println("(1) View Medical Record");
						System.out.println("(2) Update Personal Information");
						System.out.println("(3) View Available Appointment Slots");
						System.out.println("(4) Schedule an Appointment");
						System.out.println("(5) Reschedule an Appointment");
						System.out.println("(6) Cancel an Appointment");
						System.out.println("(7) View Scheduled Appointments");
						System.out.println("(8) View Past Appointment Outcome Records");
						System.out.println("(9) Change password");
						System.out.println("(10) Logout");
						
						System.out.println("Enter the number of your choice: ");
						choice = sc.nextInt(); 
						sc.nextLine(); //consume newline
						
						switch(choice)
						{
	                		case 1:
	                			patient.viewMedicalRecord();
	                    		break;
	                    		
	                		case 2:
	                			patient.updateContactInfo(patient);
	                    		break;
	                    		
	                		case 3:
	                			patient.viewAvailableAppointments();
	                    		break;
	                    		
	                		case 4:
	                			patient.scheduleAppointment();
	                    		break;
	                    		
	                		case 5:
	                			patient.rescheduleAppointment(allDoctors);
	                    		break;
	                    		
	                		case 6:
	                			patient.cancelAppointment();
	                    		break;
	                    		
	                		case 7:
	                			patient.viewScheduledAppointments();
	                    		break;
	                    		
	                		case 8:
	                			patient.viewPastAppointmentOutcomes();
	                    		break;
	                    		
	                		case 9:
	                			//change password
	                			if (currentUser instanceof Patient)
	        					{
	        						if (((Patient) currentUser).getisDefaultPassword()) 
									{
										handleFirstLoginPatient();
									}
	        						//else , password is not default but if user want to change password again, they can do so
	        					    else{
	        					    	changePassword();
	        					    }	
	                			}
	                			break;
	                			
	                		case 10:
	                    		System.out.println("Logging out...\n");
	                    		break;
	                    		
	                		default:
	                    		System.out.println("Invalid option.\n");
	                    		break;
							}
					} while (choice != 10);
					break;
				}
				else
				{
					System.out.println("Patient not found");
				}
				break;
			
		
			case "Doctor":

				Doctor doctor = new Doctor(currentUser.getHospitalID(),currentUser.getName(),currentUser.getPassword());
				allDoctors.add(doctor);
				do {
					System.out.println("Doctor's Menu:");
					System.out.println("================================");
					System.out.println("(1) View Patient Medical Records");
					System.out.println("(2) Update Patient Medical Records");
					System.out.println("(3) View Personal Schedule");
					System.out.println("(4) Set Availability for Appointments");
					System.out.println("(5) Accept or Decline Appointment Requests");
					System.out.println("(6) View Upcoming Appointments");
					System.out.println("(7) Record Appointment Outcome");
					System.out.println("(8) Change password");
					System.out.println("(9) Logout");
					
					System.out.println("Enter the number of your choice: ");
					choice = sc.nextInt(); 
					sc.nextLine();
					
					switch(choice)
					{
						case 1: 
							doctor.viewPatientMedicalRecord();
							break;

						case 2: 
							doctor.update(); 
							break;
							
						case 3: 
							doctor.viewPersonalSchedule(); 
							break;

						case 4: 
							doctor.setAvailability(); 
							break;

						case 5:
							doctor.appointmentRequests();
							break;

						case 6: 
							doctor.viewUpcomingAppointments(); 
							break;

						case 7:
							doctor.recordAppointmentOutcome(); 
						    break;
						   
						case 8:
							//change password
							if (currentUser instanceof StaffMember)
					        {
					        	if (((StaffMember) currentUser).getisDefaultPassword()) 
					        	{
	            		            handleFirstLoginStaff();
					        	}
					        	//else , password is not default but if user want to change password again, they can do so
					    	    else{
					    	    	changePassword();
					    	    }	
					        }
                			break;
							
						case 9:
							System.out.println("Logging out...\n");
							break;

						default:
			                System.out.println("Invalid choice. Please try again.\n");
			                break;
					}
				} while (choice != 9);
				break;
				
			
			case "Pharmacist": 
				Pharmacist pharmacist =  new Pharmacist();
				pharmacists.add(pharmacist);
				
				do {
					System.out.println("Pharmacist' Menu:");
					System.out.println("================================");
					System.out.println("(1) View Appointment Outcome Record");
					System.out.println("(2) Update Prescription Status");
					System.out.println("(3) View Medical Inventory");
					System.out.println("(4) Submit Replenishment Request");
					System.out.println("(5) Change password");
					System.out.println("(6) Logout");
					
					System.out.println("Enter the number of your choice: ");
					choice = sc.nextInt(); 
					sc.nextLine();
					
					switch(choice)
					{
						case 1: //view appointment outcome record
							pharmacist.viewAppointmentRecord(); 
							break;
							
						case 2: //update prescription status
							pharmacist.updatePrescriptionStatus();
							break;
							
						case 3: //view medical inventory
							pharmacist.viewMedicationInventory(); 
							break;
							
						case 4: //submit replenishment request
							pharmacist.submitReplenishmentRequest(); 
							break;
							
						case 5:
							//change password
							if (currentUser instanceof StaffMember)
					        {
					        	if (((StaffMember) currentUser).getisDefaultPassword()) 
					        	{
	            		            handleFirstLoginStaff();
					        	}
					        	//else , password is not default but if user want to change password again, they can do so
        					    else{
        					    	changePassword();
        					    }
					        }
							break;
							
						case 6:
							System.out.println("Logging out...");
							break;
							
						default:
			                System.out.println("Invalid choice. Please try again.\n");
			                break;
					}
				} while (choice != 6);
				break;
				
				
			case "Administrator":
				Administrator administrator =  new Administrator();
				administrators.add(administrator);
				
				do {
					System.out.println("Administrator' Menu:");
					System.out.println("================================");
					System.out.println("(1) View and Manage Hospital Staff");
					System.out.println("(2) View Appointment Details");
					System.out.println("(3) View and Manage Medication Inventory");
					System.out.println("(4) Approve Replenishment Requests");
					System.out.println("(5) Change password");
					System.out.println("(6) Logout");
					
					System.out.println("Enter the number of your choice: ");
					choice = sc.nextInt(); 
					sc.nextLine();
					
					switch(choice)
					{
						case 1: //view and manage hospital staff
							administrator.viewAndManageHospitalStaff();
							break;
							
						case 2: //view appointment details
							administrator.viewAppointmentDetails(); 
							break;
							
						case 3: //view and manage medical inventory
							administrator.viewAndManageMedicalInventory(); 
							break;
							
						case 4: //approve replenishment requests
							administrator.approveReplenishmentRequest(); 
							break;
							
						case 5:
							//change password
							if (currentUser instanceof StaffMember)
					        {
					        	if (((StaffMember) currentUser).getisDefaultPassword()) 
					        	{
	            		            handleFirstLoginStaff();
					        	}
					        	//else , password is not default but if user want to change password again, they can do so
        					    else{
        					    	changePassword();
        					    }
					        }
							break;
							
						case 6:
							System.out.println("Logging out...");
							break;
							
						default:
			                System.out.println("Invalid choice. Please try again.");
			                break;
					}
				} while (choice != 6);
				break;
				
			default:
				break;
		}
	}
	
}
