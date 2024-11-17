package hms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends StaffMember {
	protected List<StaffMember> staffMembers;
	private List<Appointment> allAppointments;
	private Inventory inventory;
	private Doctor doctor;
	protected List<Doctor> allDoctors;

	Scanner scanner = new Scanner(System.in);

	public Administrator() {
		super("0000", "password", "admin name", null, null, 0000, true);
		this.staffMembers = new ArrayList<>();
		this.allAppointments = new ArrayList<>();
		this.inventory = new Inventory();
		this.allDoctors = new ArrayList<>();
		loadStaffData();
	}

	public Administrator(String ID, String password, String name) {
		super(ID, password, "Administrator", null, null, 0000, true);
		this.staffMembers = new ArrayList<>();
		this.allAppointments = new ArrayList<>();
		this.inventory = new Inventory();
		this.allDoctors = new ArrayList<>();
	}

	// Method to add a new staff member
	public void addStaff() {
		String newName = "";
	    // Loop to ensure the name is unique
	    while (true) {
	        System.out.println("What is the name of the staff to be added: ");
	        newName = scanner.nextLine().trim();

	        // Check if a staff member with the same name (case insensitive) already exists
	        boolean nameExists = false;
	        for (int i = 0; i < staffMembers.size(); i++) {
	            if (staffMembers.get(i).getName().equalsIgnoreCase(newName)) {
	                nameExists = true;
	                break; // Exit the loop if name already exists
	            }
	        }

	        if (nameExists) {
	            System.out.println("Staff member with this name already exists! Please enter a new name.");
	        } else {
	            break; // Name is unique, exit the loop
	        }
	    }

	    // Gather and validate role input
	    String role = "";
	    while (true) {
	        System.out.println("Enter the role of the new staff member (doctor, administrator, pharmacist): ");
	        role = scanner.nextLine().trim().toLowerCase();
	        if (role.equals("doctor") || role.equals("administrator") || role.equals("pharmacist")) {
	            break;  // Valid role
	        } else {
	            System.out.println("Invalid role. Please enter either 'doctor', 'administrator', or 'pharmacist'.");
	        }
	    }

	    // Gather and validate ID format based on role
	    String id = "";
	    while (true) {
	        System.out.println("Enter the ID of the new staff member: ");
	        id = scanner.nextLine().trim();

	        // Check if ID already exists
	        boolean idExists = false;
	        for (int i = 0; i < staffMembers.size(); i++) {
	            if (staffMembers.get(i).getHospitalID().equals(id)) {
	                idExists = true;
	                break;
	            }
	        }
	        if (idExists) {
	            System.out.println("Staff member with this ID already exists! Please enter a unique ID.");
	            continue;  // Ask for ID again
	        }

	        // Validate ID format based on role
	        if (role.equals("doctor") && id.startsWith("D0") && id.length() == 4) {
	            break;
	        } else if (role.equals("administrator") && id.startsWith("A0") && id.length() == 4) {
	            break;
	        } else if (role.equals("pharmacist") && id.startsWith("P0") && id.length() == 4) {
	            break;
	        } else {
	            System.out.println("Invalid ID format. Please ensure the ID starts with 'D0' for doctor, 'A0' for administrator, or 'P0' for pharmacist, and is 4 characters long.");
	        }
	    }

	    // Gather and validate gender input
	    String gender = "";
	    while (true) {
	        System.out.println("Enter the gender of the new staff member (male or female): ");
	        gender = scanner.nextLine().trim().toLowerCase();
	        if (gender.equals("male") || gender.equals("female")) {
	            break;  // Valid gender
	        } else {
	            System.out.println("Invalid gender. Please enter either 'male' or 'female'.");
	        }
	    }

	    // Gather and validate age input
	    int age = -1;
	    while (age < 18 || age > 100) {
	        System.out.println("Enter the age of the new staff member (must be between 18 and 100): ");
	        if (scanner.hasNextInt()) {
	            age = scanner.nextInt();
	            scanner.nextLine();  // Consume the newline character
	            if (age < 18 || age > 100) {
	                System.out.println("Invalid age. Age must be between 18 and 100.");
	            }
	        } else {
	            System.out.println("Invalid input. Please enter a valid age.");
	            scanner.nextLine();  // Consume the invalid input
	        }
	    }

	    boolean isDefaultPassword = true;  // Default password when adding a new user/staff

	    // Create and add the new staff member
	    StaffMember newStaff = new StaffMember(id, "password", role, newName, gender, age, isDefaultPassword);
	    staffMembers.add(newStaff);

	    // Append the new staff member's details to the CSV file
	    String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Staff_List.csv";
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
	        writer.append(id).append(",");
	        writer.append(newName).append(",");
	        writer.append(role).append(",");
	        writer.append(gender).append(",");
	        writer.append(String.valueOf(age)).append(",");
	        writer.append("password").append(",");
	        writer.append("TRUE").append("\n");

	        System.out.println("Staff member added successfully.");
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the CSV file.");
	        e.printStackTrace();
	    }
	}
	

	// Method to update a staff member's details
	public void updateStaffInfo() {
	    // Step 1: Get the staff ID from the user
	    System.out.println("Enter the ID of the staff to be updated: ");
	    String staffId = scanner.nextLine().trim().toUpperCase();

	    // Check if the staff exists with the provided ID
	    StaffMember staffToUpdate = null;
	    for (StaffMember staff : staffMembers) {
	        if (staff.getHospitalID().equals(staffId)) {
	            staffToUpdate = staff;
	            break;
	        }
	    }

	    // Step 2: If staff not found, display error and exit
	    if (staffToUpdate == null) {
	        System.out.println("Staff member with ID " + staffId + " not found.");
	        return;  // Exit the method if staff member does not exist
	    }

	    // Step 3: Get new name and validate
	    String newName = "";
	    while (true) {
	        System.out.println("Enter the new name of the staff to be updated: ");
	        newName = scanner.nextLine().trim();
	        if (newName.isEmpty()) {
	            System.out.println("Name cannot be empty. Please enter a valid name.");
	        } else {
	            break;
	        }
	    }

	    // Step 4: Get new role and validate
	    String newRole = "";
	    while (true) {
	        System.out.println("Enter the new role of the staff to be updated (doctor, administrator, pharmacist): ");
	        newRole = scanner.nextLine().trim().toLowerCase();
	        if (newRole.equals("doctor") || newRole.equals("administrator") || newRole.equals("pharmacist")) {
	            break;  // Valid role
	        } else {
	            System.out.println("Invalid role. Please enter 'doctor', 'administrator', or 'pharmacist'.");
	        }
	    }

	    // Step 5: Validate gender (if updated)
	    String newGender = staffToUpdate.getGender();
	    System.out.println("Current gender is: " + newGender + ". Do you want to update it? (yes/no): ");
	    String updateGender = scanner.nextLine().trim().toLowerCase();

	    if (updateGender.equals("yes")) {
	        while (true) {
	            System.out.println("Enter the new gender of the staff to be updated (male, female): ");
	            newGender = scanner.nextLine().trim().toLowerCase();
	            if (newGender.equals("male") || newGender.equals("female")) {
	                break;
	            } else {
	                System.out.println("Invalid gender. Please enter 'male' or 'female'.");
	            }
	        }
	    }

	    // Step 6: Validate age (if updated)
	    int newAge = staffToUpdate.getAge();
	    System.out.println("Current age is: " + newAge + ". Do you want to update it? (yes/no): ");
	    String updateAge = scanner.nextLine().trim().toLowerCase();

	    if (updateAge.equals("yes")) {
	        while (true) {
	            System.out.println("Enter the new age of the staff to be updated (between 18 and 100): ");
	            if (scanner.hasNextInt()) {
	                newAge = scanner.nextInt();
	                scanner.nextLine();  // Consume the newline character after age input
	                if (newAge >= 18 && newAge <= 100) {
	                    break;
	                } else {
	                    System.out.println("Invalid age. Age must be between 18 and 100.");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a valid age.");
	                scanner.nextLine();  // Consume the invalid input
	            }
	        }
	    }

	    // Step 7: Update the staff details in the list
	    staffToUpdate.setName(newName);
	    staffToUpdate.setRole(newRole);
	    staffToUpdate.setGender(newGender);
	    staffToUpdate.setAge(newAge);

	    System.out.println("Staff member updated: " + staffToUpdate.getName());

	    // Step 8: Update the CSV file with the new details
	    List<String> updatedLines = new ArrayList<>();
	    String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Staff_List.csv";

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        // Read the header line first (keep it unchanged)
	        String header = reader.readLine();
	        updatedLines.add(header);

	        // Read the rest of the lines and update the matching row
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");  // Assuming columns are separated by commas
	            String hospitalID = columns[0]; // HospitalID is the first column

	            if (hospitalID.equals(staffId)) {
	                // If this is the row to be updated, replace its values with the new data
	                updatedLines.add(hospitalID + "," + newName + "," + newRole + "," + newGender + "," + newAge + "," + staffToUpdate.getPassword() + "," + staffToUpdate.getisDefaultPassword());
	            } else {
	                // If not, add the row unchanged
	                updatedLines.add(line);
	            }
	        }

	        // Write the updated rows back to the CSV file
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	            for (String updatedLine : updatedLines) {
	                writer.write(updatedLine + "\n");  // Write each line with a newline at the end
	            }

	            //System.out.println("CSV file updated successfully.");
	        }

	    } catch (IOException e) {
	        System.out.println("Error updating the CSV file: " + e.getMessage());
	    }
	}
	
	
	public void removeStaff() {
		System.out.println("Existing Staff IDs:");
		for (int i = 0; i < staffMembers.size(); i++) {
			StaffMember staff = staffMembers.get(i);
			System.out.println(staff.getHospitalID());
		}

		System.out.println("What is the ID of the staff to be removed: ");
		String id = scanner.nextLine();

		StaffMember staff = findStaffById(id);
		if (staff == null) {
			System.out.println("Staff with id: " + id + " not found");
			return;
		} else {
			String name = staff.getName();
			staffMembers.remove(staff);
			System.out.println("Staff member removed: " + name);
			
			//update csv file - rewrite the staff members in the list, excluding the one removed
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Staff_List.csv"))) {
	            // Write the header (if necessary)
	            writer.write("StaffID,Name,Role,Gender,Age\n");  // Replace with your actual column names

	            // Re-write the staff members in the list, excluding the one removed
	            for (StaffMember staff1 : staffMembers) {
	                writer.write(staff1.getHospitalID() + "," + staff1.getName() + "," + staff1.getRole() + "," + staff1.getGender() + "," + staff1.getAge() + "," + staff1.getPassword() + "," + staff1.getisDefaultPassword() + "\n");
	            }
			} catch (IOException e) {
	            System.out.println("Error updating the CSV file: " + e.getMessage());
	        }
			return;
		}
	}

	public void displayStaff() {
		// Check if staffMembers is not null and has members
		if (staffMembers == null || staffMembers.isEmpty()) {
			System.out.println("No staff members available.");
			return; // Exit if there are no staff members
		}

		System.out.println("============================== Staff Members ==============================");
		for (int i = 0; i < staffMembers.size(); i++) {
			// Print the information of each staff member
			System.out.println(staffMembers.get(i).displayInfo());
		}
		System.out.println("===========================================================================\n");
	}

	public StaffMember findStaffById(String id) {
		for (int i = 0; i < staffMembers.size(); i++) {
			StaffMember staff = staffMembers.get(i);
			if (staff.getHospitalID().equals(id)) {
				return staff;
			}
		}
		return null;
	}

	public void viewAndManageHospitalStaff() {
		int choice;
		do {
			System.out.println("=== Hospital Staff Management ===");
			System.out.println("1. View Staff");
			System.out.println("2. Add Staff");
			System.out.println("3. Remove Staff");
			System.out.println("4. Update Staff Information");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			switch (choice) {
				case 1:
					displayStaff();
					break;
				case 2:
					addStaff();
					break;
				case 3:
					removeStaff();
					break;
				case 4:
					updateStaffInfo();
					break;
				case 5:
					System.out.println("Exiting staff management...\n");
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
			}
		} while (choice != 5);
	}

	public void viewAppointmentDetails() {
		//view all appointments from appointments
		//if appointment is completed, get outcome record from appointment record
		String filePathAppt = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
		 
		List<String> allAppointments = new ArrayList<>();
        
        // Read appointments and filter completed ones
        try (BufferedReader appointmentReader = new BufferedReader(new FileReader(filePathAppt))) {
            String line;
            
            // Read header (if any)
            String header = appointmentReader.readLine(); 
            
            // Read the appointment records
            while ((line = appointmentReader.readLine()) != null) {
                String[] columns = line.split(","); // Assuming CSV is comma-separated
                
                if (columns.length < 6) continue; // Skip invalid rows
                
                String doctorID = columns[0].trim();
                String doctName = columns[1].trim();
                String patientID = columns[2].trim();
                String date = columns[3].trim();
                String time = columns[4].trim();  
                String status = columns[5].trim(); 

                // Collect appointment details
                String appointmentDetails = "Doctor ID: " + doctorID + ", Doctor's Name: " + doctName + ", Patient ID: " + patientID +
                        ", Date: " + date + ", Time: " + time + ", Appointment Status: " + status;

                // Check if the appointment is completed
                if ("completed".equalsIgnoreCase(status)) {
                    // Fetch outcome record if the appointment is completed
                    String outcomeDetails = getAppointmentOutcome(doctorID, patientID, date, time);
                    appointmentDetails += ", Outcome: " + outcomeDetails;
                }
                
             // Add the appointment details to the list
                allAppointments.add(appointmentDetails);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointments file: " + e.getMessage());
        }
        
        // Display completed appointments with outcome records
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (String appointment : allAppointments) {
                System.out.println(appointment);
            }
        }
        System.out.println("\n");
    }
		
	public String getAppointmentOutcome(String doctorID, String patientID, String date, String time)
	{
		String outcome = "";  // Initialize outcome as an empty string

	    String filePathRecords = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointment_records.csv";

	    // Read the appointment records from the file
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePathRecords))) {
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");  // Assuming CSV is comma-separated

	            // Ensure the line has the necessary columns (at least 8 columns for this example)
	            if (columns.length < 8) continue;

	            // Extract the relevant details from the record
	            String recordDoctorID = columns[0].trim();
	            String recordPatientID = columns[1].trim();
	            String recordDate = columns[2].trim();
	            String recordTime = columns[3].trim();
	            String service = columns[4].trim();
	            String meds = columns[5].trim();
	            String status = columns[6].trim();
	            String notes = columns[7].trim();

	            // Check if this record matches the doctorID, patientID, date, and time
	            if (recordDoctorID.equals(doctorID) && recordPatientID.equals(patientID) &&
	                recordDate.equals(date) && recordTime.equals(time)) {

	                // Append the outcome details for the matching record
	                outcome = "Type of Service: " + service + ", Medication Name: " + meds + 
	                          ", Status: " + status + ", Consultation Notes: " + notes;
	                break;  // Once we find the matching record, we can stop reading further
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the appointment records file: " + e.getMessage());
	    }

	    return outcome;  // Return the outcome or an empty string if no match was found
	}
	

	public void viewAndManageMedicalInventory() {
		int choice;
		do {
			System.out.println("=== Medical Inventory Management ===");
			System.out.println("1. View Inventory");
			System.out.println("2. Add Medication");
			System.out.println("3. Update Medication Quantity");
			System.out.println("4. Remove Medication");
			System.out.println("5. Update low stock level alert");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline left-over

			switch (choice) {
				case 1:
					displayInventory();
					break;
				case 2:
					inventory.addMedication();
					break;
				case 3:
					inventory.updateMedicationQuantity();
					break;
					
				case 4:
					inventory.removeMedication();
					break;
				case 5:
					viewAndUpdateLowStockAlerts();
					break;
				
				case 6:
					System.out.println("Exiting inventory management...\n");
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
			}
		} while (choice != 6);
	}

	private void viewAndUpdateLowStockAlerts() {
		String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
	    List<String[]> inventoryData = new ArrayList<>();
	    
	    // Read the file and collect the data
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine(); // Skip the header
	        while ((line = reader.readLine()) != null) {
	            inventoryData.add(line.split(","));
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	        return;
	    }

	    // Display all medications in the inventory
	    System.out.println("=== All Medications ===");
	    for (int i = 0; i < inventoryData.size(); i++) {
	        String[] record = inventoryData.get(i);
	        String medicationName = record[0];
	        String quantity = record[1];
	        String lowStockAlert = record[2]; // True or False
	        String replenishmentRequest = record[3]; // Assuming column 3 stores replenishment request status
	        System.out.println((i + 1) + ". Medication: " + medicationName + " | Quantity: " + quantity +
	                " | Low Stock Alert: " + lowStockAlert + " | Replenishment Request: " + replenishmentRequest);
	    }

	    // Let user input the index of the medication to update
	    Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.print("\nEnter the index of the medication to update the Low Stock Alert (or type 'exit' to cancel): ");
	        String input = scanner.nextLine().trim();

	        if (input.equalsIgnoreCase("exit")) {
	            break;  // Exit the loop if user types 'exit'
	        }

	        try {
	            int index = Integer.parseInt(input) - 1;  // Convert input to 0-based index

	            // Check if index is valid
	            if (index >= 0 && index < inventoryData.size()) {
	                String[] selectedMedicine = inventoryData.get(index);
	                String currentLowStockAlert = selectedMedicine[2];

	                // Toggle Low Stock Alert (true to false or false to true)
	                if (currentLowStockAlert.equalsIgnoreCase("true")) {
	                    selectedMedicine[2] = "false";  // Update to false
	                    System.out.println("Low Stock Alert for " + selectedMedicine[0] + " updated to: false");
	                } else {
	                    selectedMedicine[2] = "true";  // Update to true
	                    System.out.println("Low Stock Alert for " + selectedMedicine[0] + " updated to: true");
	                }

	                // Write the updated inventory data back to the file
	                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	                    writer.write("Name,Quantity,LowStockAlert,ReplenishmentRequest\n");  // Write header

	                    // Write the updated inventory data
	                    for (String[] record : inventoryData) {
	                        writer.write(String.join(",", record));
	                        writer.newLine();
	                    }
	                } catch (IOException e) {
	                    System.out.println("Error writing to the CSV file: " + e.getMessage());
	                }
	                //System.out.println("Inventory updated successfully.");
	            } else {
	                System.out.println("Invalid index, please try again.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid index number or 'exit' to cancel.");
	        }
	    }
	}


	public void approveReplenishmentRequest() {
		String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
	    List<String[]> inventoryData = new ArrayList<>();

	    // Read the file and collect the data
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine(); // Skip the header
	        while ((line = reader.readLine()) != null) {
	            inventoryData.add(line.split(","));
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	        return;
	    }

	    // Display medications with "Submitted" replenishment request
	    System.out.println("The following medications have requested replenishment: ");
	    List<String[]> requestedMedicines = new ArrayList<>();
	    int displayedIndex = 1;  // Start displaying from 1
	    for (int i = 0; i < inventoryData.size(); i++) {
	        String[] record = inventoryData.get(i);
	        String medicationName = record[0];
	        String replenishmentRequest = record[3];  // "Submitted" or "Approved"

	        // Display only medications with "Submitted" replenishment request status
	        if ("Submitted".equalsIgnoreCase(replenishmentRequest)) {
	            System.out.println(displayedIndex + ". Medication: " + medicationName + " | Replenishment Request: " + replenishmentRequest);
	            requestedMedicines.add(record); // Add the record to a list for later approval
	            displayedIndex++;  // Increment the displayed index for the next item
	        }
	    }

	    // If no replenishment requests, inform the user and exit
	    if (requestedMedicines.isEmpty()) {
	        System.out.println("No medications have requested replenishment.\n");
	        return;
	    }

	    // Allow user to approve a replenishment request
	    Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.print("\nEnter the index of the medication to approve (or type 'exit' to cancel): ");
	        String input = scanner.nextLine().trim();

	        if (input.equalsIgnoreCase("exit")) {
	            break;  // Exit the loop if the user types 'exit'
	        }

	        try {
	            int index = Integer.parseInt(input) - 1;  // Convert input to 0-based index

	            // Check if the index is valid
	            if (index >= 0 && index < requestedMedicines.size()) {
	                String[] selectedMedicine = requestedMedicines.get(index);
	                selectedMedicine[3] = "Approved";  // Update the replenishment request to "Approved"

	                // Increase the quantity of the approved medication
	                int quantity = Integer.parseInt(selectedMedicine[1]);
	                selectedMedicine[1] = String.valueOf(quantity + 50);  // Add 50 units

	                System.out.println("Replenishment request for " + selectedMedicine[0] + " has been approved.");
	                System.out.println("Inventory for " + selectedMedicine[0] + " is now updated: " + selectedMedicine[1] + " units.\n");

	                // Update the low stock alert based on the new quantity
	                int updatedQuantity = Integer.parseInt(selectedMedicine[1]);
	                if (updatedQuantity < 50) {
	                    selectedMedicine[2] = "true";  // Low stock alert is true if quantity is below 50
	                } else {
	                    selectedMedicine[2] = "false";  // Low stock alert is false if quantity is above 50
	                }

	                // Write the updated inventory data back to the file
	                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	                    writer.write("Name,Quantity,LowStockAlert,ReplenishmentRequest\n");  // Write header
	                    for (String[] record : inventoryData) {
	                        writer.write(String.join(",", record));
	                        writer.newLine();
	                    }
	                    //System.out.println("Inventory updated successfully.");
	                    break;  // Exit after approving one request (or continue if you want multiple approvals)
	                } catch (IOException e) {
	                    System.out.println("Error writing to the CSV file: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Invalid index, please try again.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid index number or 'exit' to cancel.");
	        }
	    }
	}
	

	private void displayInventory() {
		String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";

	    System.out.println("=== Inventory ===");
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        int index = 1;

	        // Read and skip the header line
	        reader.readLine();

	        // Read the rest of the lines and display medication details
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");

	            if (columns.length >= 4) {
	                String name = columns[0];
	                int quantity = Integer.parseInt(columns[1]);
	                boolean lowStockAlert = Boolean.parseBoolean(columns[2]);
	                String replenishmentRequest = columns[3];

	                // Display medication details
	                System.out.println(index + ". Name: " + name + " | Quantity: " + quantity +
	                        " | Low Stock Alert: " + lowStockAlert +
	                        " | Replenishment Request: " + replenishmentRequest);
	                index++;
	            }
	        }
	        System.out.println("\n");
	    } catch (IOException e) {
	        System.out.println("Error reading the inventory file: " + e.getMessage());
	    }
	}

	private void loadStaffData() {
		String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Staff_List.csv";
		//String filePath = "/Users/tiffany/coding school things/SC2002/ASSIGNMENT/Staff_List.csv";
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
				//System.out.println(member.getRole() + " " + data[2] + " " + role);
				staffMembers.add(member);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}