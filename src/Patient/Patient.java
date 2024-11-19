package hms;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Patient class can access and view their personal information and manage their appointments
 */
public class Patient extends User {
    private String name;
    private String dob;
    private String gender;
    private String contactNumber;
    private String email;
    private String bloodType;
    private String drugAllergy;
    private String pastDiagnoses;
    private String pastTreatments;
    private String prescribedMeds;
    private boolean isDefaultPassword;
  
    private static Scanner sc = new Scanner(System.in);
    protected static List<Doctor> doctors = new ArrayList<>();
    protected ArrayList<Appointment> scheduledAppointments = new ArrayList<>();
    protected ArrayList<AppointmentOutcomeRecord> pastAppointmentOutcomes = new ArrayList<>();

    /**
     * Patient constructor
     * @param hospitalID hospitalID
     * @param name name
     * @param dob date of birth
     * @param gender gender
     * @param contactNumber contact number
     * @param email email address
     * @param bloodType blood type
     * @param drugAllergy drug allergy
     * @param pastDiagnoses past diagnoses
     * @param pastTreatments past treatments
     * @param prescribedMeds prescribed medications
     * @param password password
     * @param isDefaultPassword is default password
     */
    public Patient(String hospitalID, String name, String dob, String gender, String contactNumber, String email, String bloodType, String drugAllergy, String pastDiagnoses, String pastTreatments, String prescribedMeds, String password, boolean isDefaultPassword) {
        super(hospitalID, name, password, "Patient");
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.bloodType = bloodType;
        this.drugAllergy = drugAllergy;
        this.pastDiagnoses = pastDiagnoses;
        this.pastTreatments = pastTreatments;
        this.prescribedMeds = prescribedMeds;
        this.isDefaultPassword = isDefaultPassword;
    }

    /**
     * getName getter method returns the name of the patient
     * @return name of patient
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * setName setter method sets the name of the patient
     */
    public void setName(String name)
    {
    	this.name = name;
    }
    
    /**
     * getDob getter method returns the date of birth of the patient
     * @return date of birth
     */
    public String getDob() {
        return dob;
    }

    /**
     * setDob setter method sets the date of birth of the patient
     * @param dob date of birth
     */
    public void setDob(String dob) {
        this.dob = dob;
    }
    
    /**
     * getisDefaultPassword getter method returns the boolean value of whether the password is default
     * @return boolean value of whether the password is default
     */
    public boolean getisDefaultPassword()
	{
		return isDefaultPassword;
	}
    
    /**
     * getGender getter method returns the gender of the patient
     * @return patient's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * setGender setter method sets the gender of the patient
     * @param gender gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * getContactNumber getter method returns the contact number of the patient
     * @return the contact number of the patient
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * setContactNumber setter method sets the contact number of the patient
     * @param contactNumber contact number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    /**
     * getEmail getter method returns the email address of the patient
     * @return email address of the patient
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail setter method sets the email address of the patient
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * getBloodType getter method returns the blood type of the patient
     * @return blood type of the patient
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * setBloodType setter method sets the blood type of the patient
     * @param bloodType blood type
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * getDrugAllergy getter method returns the patient's drug allergies
     * @return patient's drug allergies
     */
    public String getDrugAllergy() {
        return drugAllergy;
    }

    /**
     * setDrugAllergy setter method sets the patient's drug allergies
     * @param drugAllergy drug allergy
     */
    public void setDrugAllergy(String drugAllergy) {
        this.drugAllergy = drugAllergy;
    }
    
    /**
     * getPastDiagnoses getter method returns the diagnosis that the doctor have diagnosed previously
     * @return patient's diagnoses
     */
    public String getPastDiagnoses() {
        return pastDiagnoses;
    }

    /**
     * setPastDiagnoses setter method sets the diagnosis that the doctor have diagnosed
     * @param pastDiagnoses past diagnoses
     */
    public void setPastDiagnoses(String pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    /**
     * getPastTreatments getter method returns the past treatments the doctor have done on the patient
     * @return past treatments
     */
    public String getPastTreatments() {
        return pastTreatments;
    }

    /**
     * setPastTreatments setter method sets the treatments the doctor have done on the patient
     * @param pastTreatments past treatments
     */
    public void setPastTreatments(String pastTreatments) {
        this.pastTreatments = pastTreatments;
    }
    
    /**
     * getPrescribedMeds getter method returns the medications that the doctor have prescribed
     * @return the medications that the doctor have prescribed
     */
    public String getPrescribedMeds() {
        return prescribedMeds;
    }

    /**
     * setPrescribedMeds setter method sets the medications that the doctor have prescribed
     * @param prescribedMeds prescribed medications
     */
    public void setPrescribedMeds(String prescribedMeds) {
        this.prescribedMeds = prescribedMeds;
    }

    /**
     * setisDefaultPassword setter method of whether the password is default
     * @param isDefaultPassword default password
     */
    public void setisDefaultPassword(boolean isDefaultPassword)
	{
		this.isDefaultPassword = isDefaultPassword;
	}
    
    /**
     * viewMedicalRecord method allows patient to view their medical record including,
     * patientID, name, date of birth, gender, contact information, blood type, past diagnosis and treatments
     */
    public void viewMedicalRecord() {
    	//read from patient list csv file 
    	List<String> updatedLines = new ArrayList<>();
        String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Read the header line first and keep it unchanged
            String header = reader.readLine();
            updatedLines.add(header);

            // Read the rest of the lines and update the matching row
            while ((line = reader.readLine()) != null) {
            	String[] data = line.split(",");  // Assuming columns are separated by commas
                
            	String patientID = data[0]; // Assuming HospitalID is the first column
                String name = data[1].trim();
				String dob = data[2].trim();
				String gender = data[3].trim();
				String phonenum = data[4].trim();
				String email = data[5].trim();
				String bloodType = data[6].trim();
				String drugAllergy = data[7].trim();
				String pastDiagnoses = data[8].trim();
				String pastTreatments = data[9].trim();
				String meds = data[10].trim();
				
				if (patientID.equals(this.getHospitalID()))
				{
					System.out.println("Patient Medical Record:");
					System.out.println("--------------------------------------------------------------");
			        System.out.println("ID: " + patientID);
			        System.out.println("Name: " + name);
			        System.out.println("Date of Birth: " + dob);
			        System.out.println("Gender: " + gender);
			        System.out.println("Contact Number: " + phonenum);
			        System.out.println("Email: " + email);
			        System.out.println("Blood Type: " + bloodType);
			        System.out.println("Drug Allergy: " + drugAllergy);
			        System.out.println("Past Diagnoses : " + pastDiagnoses);
			        System.out.println("Past Treatments: " + pastTreatments);
			        System.out.println("Prescibed Medications: " + meds);
			        break;
				}
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateContactInfo method allows patient to update their non-medical personal information such as
     * email address and contact number
     * @param patient patient
     */
    // Method to update contact information
    public void updateContactInfo(Patient patient) {
        System.out.println("Update Personal Information:");
        System.out.println("--------------------------------------------------------------");
        
        // Validate contact number
        String contactNumber = "";
        while (true) {
            System.out.print("Enter new contact number: ");
            contactNumber = sc.nextLine().trim();
            
            // Check if the contact number is a valid number (just digits) and 10 digits long
            if (contactNumber.matches("^\\d{8}$")) {
                break;  // Exit loop if valid
            } else {
                System.out.println("Invalid contact number. Please enter a 8-digit number.");
            }
        }
        
        // Validate email address
        String email = "";
        while (true) {
            System.out.print("Enter new email address: ");
            email = sc.nextLine().trim();
            
            // Check if the email follows a basic email pattern
            if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                break;  // Exit loop if valid
            } else {
                System.out.println("Invalid email address. Please enter a valid email.");
            }
        }
        
        
        patient.setContactNumber(contactNumber);
        patient.setEmail(email);
           
        System.out.println("Personal information updated successfully.\n");
        
        //update patient list csv file
        List<String> updatedLines = new ArrayList<>();
        String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Read the header line first and keep it unchanged
            String header = reader.readLine();
            updatedLines.add(header);

            // Read the rest of the lines and update the matching row
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");  // Assuming columns are separated by commas
                String hospitalID = columns[0]; // Assuming HospitalID is the first column
                boolean isDefaultPassword = Boolean.parseBoolean(columns[12]);
                
                if (hospitalID.equals(this.getHospitalID())) {
                    // If this is the row to be updated, replace its values with the new contact info
                	updatedLines.add(hospitalID + "," + columns[1] + "," + columns[2] + "," + columns[3] + "," + contactNumber + "," + email + "," + columns[6] + "," + columns[7] + "," + columns[8] + "," + columns[9] + "," + columns[10] + "," + columns[11] + "," + isDefaultPassword);
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
        
        //update patient records csv 
        List<String> updatedLines1 = new ArrayList<>();
        String filePath1 = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_records.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            
            // Read the header line first and keep it unchanged
            String header = reader.readLine();
            updatedLines1.add(header);

            // Read the rest of the lines and update the matching row
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");  // Assuming columns are separated by commas
                String hospitalID = columns[1]; // Assuming HospitalID is the second column
                //boolean isDefaultPassword = Boolean.parseBoolean(columns[9]);
                
                if (hospitalID.equals(this.getHospitalID())) {
                    // If this is the row to be updated, replace its values with the new contact info
                    updatedLines1.add(columns[0] + "," + hospitalID  + "," + columns[2] + "," + columns[3] + "," +  columns[4] + "," + contactNumber + "," + email + "," + columns[7] + "," + columns[8] + "," + columns[9] + "," + columns[10] + "," + columns[11] + "," + columns[12]);
                } else {
                    // If not, add the row unchanged
                    updatedLines1.add(line);
                }
            }

            // Write the updated rows back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
                for (String updatedLine : updatedLines1) {
                    writer.write(updatedLine + "\n");  // Write each line with a newline at the end
                }
                //System.out.println("CSV file updated successfully.");
            }

        } catch (IOException e) {
            System.out.println("Error updating the CSV file: " + e.getMessage());
        }
    }

    /**
     * viewAvailableAppointments method allows patients to view available appointment slots with doctors
     */
    // Method to view available appointment slots
    public void viewAvailableAppointments()
    {
        while (true) {
            // Prompt the user for the doctor ID
            System.out.println("Enter the doctor ID (or type 'exit' to go back to the menu): ");
            String doctID = sc.nextLine().trim();
            
            // Exit the loop if user types 'exit'
            if (doctID.equalsIgnoreCase("exit")) {
                System.out.println("Going back to the menu...\n");
                break;
            }

            System.out.println("Available Appointment Slots for Doctor ID: " + doctID);
            System.out.println("--------------------------------------------------------------");

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Doctor_schedule.csv";
            
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                br.readLine(); // Skip header line
                
                boolean found = false; // Flag to track if any available slots are found
                
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    
                    try {
                        String doctorID = values[0].trim();
                        String name = values[1].trim();
                        LocalDate date = LocalDate.parse(values[2].trim(), dateFormatter); // Parse date with dd/MM/yyyy format
                        String timeStr = values[3].trim();
                        if (timeStr.length() == 4) {
                            timeStr = "0" + timeStr;
                        }

                        LocalTime time = LocalTime.parse(timeStr, timeFormatter);  // Parse time
                        String status = values[4].trim();
                        
                        // Print the schedule only if the slot is marked as "Available"
                        if (doctorID.equals(doctID) && status.equalsIgnoreCase("Available")) {
                            System.out.println("Doctor's Name: " + name + ", Date: " + date.format(dateFormatter) + ", Time: " + time);
                            found = true;
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Error parsing line: " + line + " - " + e.getMessage());
                    }
                }
                
                // If no available appointments are found
                if (!found) {
                    System.out.println("No available appointment slots for Doctor ID: " + doctID + "\n");
                }
                
            } catch (IOException e) {
                System.out.println("Error reading the CSV file: " + e.getMessage());
            }
        }
    }

    /**
     * scheduleAppointment method allows patients to schedule appointments with doctors based on doctor's availability
     */
    //Method to schedule appointment with doctor 
    public void scheduleAppointment() {
        System.out.println("Schedule an Appointment");
        System.out.println("--------------------------------------------------------------");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        //add patient details to patient records csv also
		String patientRecordsPath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_records.csv";
		String patientListPath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_List.csv";
		
		//read from patient list file to get the patient details
		try (BufferedReader reader = new BufferedReader(new FileReader(patientListPath))){
			String line;
            reader.readLine(); // Skip header line (if any)
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns.length < 12) continue; // Skip incomplete rows

                // Extract patient details from Patient_List.csv
                String patientID = columns[0].trim();
                String name = columns[1].trim();
                String dob = columns[2].trim(); // Ensure this is in the correct format
                String gender = columns[3].trim();
                String contactNumber = columns[4].trim();
                String email = columns[5].trim();
                String bloodType = columns[6].trim();
                String drugAllergy = columns[7].trim();
                String pastDiagnoses = columns[8].trim();
                String pastTreatments = columns[9].trim();
                String prescribedMeds = columns[10].trim();
                
                if (patientID.equalsIgnoreCase(this.getHospitalID()))
                {
                	//append patient details to patient records file
            		try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientRecordsPath,true))) {
            			writer.append("-").append(","); //default doctorid is "-" until doctor accept appt then update accordingly
            			writer.append(patientID).append(",");
            			writer.append(name).append(",");
            	        writer.append(dob).append(",");
            	        writer.append(gender).append(",");
            	        writer.append(contactNumber).append(",");
            	        writer.append(email).append(",");
            	        writer.append(bloodType).append(",");
            	        writer.append(drugAllergy).append(",");
            	        writer.append(pastDiagnoses).append(","); //past diagnoses
            	        writer.append(pastTreatments).append(","); //prescribed treatments
            	        writer.append(prescribedMeds).append(","); //prescribed medications
            	        writer.append("-").append("\n"); //default appointment status
            	        
            	        //System.out.println("New Patient added to CSV file: " + patientID);
            		} catch (IOException e) {
            		    System.out.println("An error occurred while writing to the CSV file.");
            		    e.printStackTrace();
            		} 
                }
                
            }
		}catch (IOException e) {
		    System.out.println("An error occurred while writing to the CSV file.");
		    e.printStackTrace();
		}
		
		
        String filePathSchedule = "/Users/glyni/OneDrive/Desktop/SC2002 project/Doctor_schedule.csv";
        
        boolean apptScheduled = false;

        while (!apptScheduled) {
            // Get doctor ID from user
            String doctID = null;
            String doctName = null;
            while (doctID == null) {
                System.out.print("Enter the doctor ID (or type 'exit' to go back to the menu): ");
                doctID = sc.nextLine().trim();

                // If user wants to exit and go back to the menu
                if (doctID.equalsIgnoreCase("exit")) {
                    System.out.println("Returning to the main menu...\n");
                    return;  // Exit the method and return to menu
                }

                // Check if doctor is available or not
                boolean doctorAvailable = false;
                try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule))) {
                    String line1;
                    while ((line1 = br.readLine()) != null) {
                        String[] values = line1.split(",");
                        if (values.length < 5) continue; // Skip malformed lines
                        String existingDoctorID = values[0].trim();
                        doctName = values[1].trim();
                        String status = values[4].trim();

                        if (existingDoctorID.equals(doctID) && status.equalsIgnoreCase("Available")) {
                            doctorAvailable = true; // Doctor has available slots
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading the doctor schedule file: " + e.getMessage());
                }
                
                if (!doctorAvailable) {
                    // No available slots for this doctor ; ask for doctID again
                    System.out.println("This doctor does not have any available slots. Please choose another doctor.\n");
                    doctID = null; // Ask again for doctor ID
                }
            }
            
            // Display available slots directly from the CSV
            List<String> availableSlots = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule))) {
                String line2;
                br.readLine(); //header
                
                while ((line2 = br.readLine()) != null) {
                    String[] values = line2.split(",");
                    if (values.length < 5) continue; // Skip malformed lines
                    String existingDoctorID = values[0].trim();
                    LocalDate dateStr = LocalDate.parse(values[2].trim(),dateFormatter);
                     
                    // Handle time format, ensuring leading zeros if needed
                    String timeStr = values[3].trim();
                    if (timeStr.length() == 4) {  // If it's a 4-char time like '9:30', add leading zero
                        timeStr = "0" + timeStr;
                    }
                    String status = values[4].trim();

                    if (existingDoctorID.equals(doctID) && status.equalsIgnoreCase("Available")) {
                    	String formattedDate = dateStr.format(dateFormatter);
                        String slot = "Date: " + formattedDate + ", Time: " + timeStr;
                        availableSlots.add(slot);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the doctor schedule file: " + e.getMessage());
            } 
    		
            // Display available slots
            System.out.println("Available slots for doctor " + doctID + " (" + doctName + ") :");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i));
            }

            // Get user's slot choice
            int slotChoice = -1;
            while (slotChoice < 1 || slotChoice > availableSlots.size()) {
                System.out.print("Enter the number corresponding to the appointment slot: ");
                try {
                    slotChoice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // Get the selected slot
            String selectedSlot = availableSlots.get(slotChoice - 1);
            String[] slotParts = selectedSlot.split(", ");
            String selectedDate = slotParts[0].replace("Date:", "").trim();;
            String selectedTime = slotParts[1].replace("Time:", "").trim();;
            
            // Parse selected date and time
            LocalDate apptDate = LocalDate.parse(selectedDate, dateFormatter);
            LocalTime apptTime = LocalTime.parse(selectedTime, timeFormatter);
            
            String date = apptDate.format(dateFormatter);
            String time = apptTime.format(timeFormatter);
            
            // Check for duplicate appointment in Appointments.csv
            String filePathAppointments = "/Users/glyni/OneDrive/Desktop/SC2002 project/Appointments.csv";
            boolean isDuplicate = false;
            
            try (BufferedReader br = new BufferedReader(new FileReader(filePathAppointments))) {
                String line3;
                br.readLine(); // Skip header

                while ((line3 = br.readLine()) != null) {
                    String[] columns = line3.split(",");
                    if (columns.length < 6) continue; // Skip malformed lines
                    String existingDoctorID = columns[0].trim();
                    String date1 = columns[3].trim();
                    String time1 = columns[4].trim();
                    String status = columns[5];

					// Check if there's already a scheduled appointment with this doctor, date, and time
                    if (existingDoctorID.equals(doctID) && date1.equals(date) && time1.equals(time) && status.equals("Scheduled")) {
                        isDuplicate = true;
                        break;
                    }
                                       
                }
            } catch (IOException e) {
                System.out.println("Error reading the appointments file: " + e.getMessage());
            }

            if (isDuplicate) {
                System.out.println("This appointment slot has already been scheduled. Please choose another date or time.\n");
                continue;
            }

            // Update doctor's availability in Doctor_schedule.csv
            String filePathSchedule1 = "/Users/glyni/OneDrive/Desktop/SC2002 project/Doctor_schedule.csv";
            boolean doctorAvailable = false;
            List<String> updatedLines = new ArrayList<>();
            String header = "";
            String name = null;
            
            try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule1))) {
                String line4;
                header = br.readLine(); // Read and store the header
                updatedLines.add(header); // Add header to updated lines

                while ((line4 = br.readLine()) != null) {
                    String[] values = line4.split(",");
                    if (values.length < 4) continue; // Skip malformed lines
                    String existingDoctorID = values[0].trim();
                    name = values[1].trim();
                    String existingDateStr = values[2].trim();
                    String existingTimeStr = values[3].trim();
                    String status = values[4].trim();

                    // Check if the exact date, time, and availability match for scheduling
                    if (existingDoctorID.equals(doctID) 
                            && existingDateStr.equals(apptDate.format(dateFormatter))
                            && existingTimeStr.equals(apptTime.format(timeFormatter))
                            && status.equalsIgnoreCase("Available")) {

                        // Only update the status of the selected date and time
                        updatedLines.add(existingDoctorID + "," + name + "," + existingDateStr + "," + existingTimeStr + ",Scheduled");
                        doctorAvailable = true;
                    } else {
                        // Keep other lines unchanged
                        updatedLines.add(line4);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the doctor schedule file: " + e.getMessage());
            }

            // Write the updated lines back to the file (assuming you want to save the changes)
            if (doctorAvailable) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathSchedule1))) {
                    for (String updatedLine : updatedLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error writing the updated schedule file: " + e.getMessage());
                }
            }

            // If doctor is available, update the doctor schedule and add to appointments
            if (doctorAvailable) {
                // Write updated doctor schedule to CSV file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathSchedule1))) {
                    for (String updatedLine : updatedLines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to the doctor schedule CSV file: " + e.getMessage());
                }

                // Append new appointment details to Appointments.csv
                String status = "Scheduled";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathAppointments, true))) {
                    writer.write(doctID + "," + name + "," + this.getHospitalID() + "," + apptDate.format(dateFormatter) + "," 
                            + apptTime.format(timeFormatter) + "," + status);
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Error writing to the appointments CSV file: " + e.getMessage());
                }

                // Create and add the new appointment to the list
                Appointment newAppointment = new Appointment(doctID, name, this.getHospitalID(), apptDate, apptTime, status);
                scheduledAppointments.add(newAppointment);
                System.out.println("Appointment scheduled successfully for " + this.getHospitalID() + " on " 
                        + apptDate.format(dateFormatter) + " at " + apptTime.format(timeFormatter) + "\n");

                apptScheduled = true; // Exit the loop
            } else {
                System.out.println("The selected appointment slot is not available. Please choose another date or time.\n");
            }
        }
    }

    /**
     * rescheduleAppointment method allows patients to reschedule their appointments with doctors
     * @param doctors doctor
     */
    //Method to reschedule appointment
    public void rescheduleAppointment(List<Doctor> doctors) {
        System.out.println("Reschedule an Appointment:");
        System.out.println("------------------------");

        String filePathAppointments = "/Users/glyni/OneDrive/Desktop/SC2002 project/Appointments.csv";
        String filePathSchedule = "/Users/glyni/OneDrive/Desktop/SC2002 project/Doctor_schedule.csv";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Appointment> scheduledAppointments = new ArrayList<>();

        // Load scheduled appointments from Appointments.csv
        try (BufferedReader br = new BufferedReader(new FileReader(filePathAppointments))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length < 6) continue;

                String doctorID = columns[0].trim();
                String doctName = columns[1].trim();
                String patientID = columns[2].trim();
                String dateStr = columns[3].trim();
	            String timeStr = columns[4].trim();
	            String status = columns[5].trim();
	            
	            // Add scheduled appointments for the logged-in doctor
	            try {
	                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
	                LocalTime time = LocalTime.parse(timeStr, timeFormatter);
	                
	             // Check if this appointment matches the current patient's ID and the status is "Scheduled"
                    if (patientID.equalsIgnoreCase(this.getHospitalID()) && status.equalsIgnoreCase("Scheduled")) {
                        scheduledAppointments.add(new Appointment(doctorID, doctName, patientID, date, time, status));
                    }

                } catch (DateTimeParseException e) {
                    //System.out.println("Error parsing date: " + dateStr + " for appointment: " + line);
                    continue;  // Skip this row if date parsing fails
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointments file: " + e.getMessage());
            return;
        }

        // Print out the scheduled appointments
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments found.\n");
            return;
        }

        // Start a loop where the user can keep choosing until they exit
        while (true) {
            System.out.println("Scheduled Appointments:");
            for (int i = 0; i < scheduledAppointments.size(); i++) {
                Appointment appointment = scheduledAppointments.get(i);
                System.out.println((i + 1) + ". Doctor ID: " + appointment.getDoctorID() +
                					", Doctor's Name: " + appointment.getDoctName() +
                                   ", Patient ID: " + appointment.getPatientID() +
                                   ", Date: " + appointment.getDate().format(dateFormatter) +
                                   ", Time: " + appointment.getTime().format(timeFormatter));
            }

            // Let the user choose which appointment to reschedule or exit
            System.out.print("Enter the index of the appointment you want to reschedule or 'exit' to quit: ");
            String input = sc.nextLine().trim();

            // Check if user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting reschedule menu.");
                break;
            }

            // Try parsing the index input
            int index = -1;
            try {
                index = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid index or 'exit'.");
                continue;
            }

            // Validate the index
            if (index < 1 || index > scheduledAppointments.size()) {
                System.out.println("Invalid appointment index. Please try again.");
                continue;
            }

            Appointment appointmentToReschedule = scheduledAppointments.get(index - 1);

            // Prompt user for a new doctor ID
            System.out.print("Enter the new doctor ID for the rescheduled appointment: ");
            String newDoctorID = sc.nextLine().trim();

            // Display available appointment slots for the new doctor
            System.out.println("Available appointment slots for Doctor ID " + newDoctorID + ":");

            List<String> availableSlots = new ArrayList<>();
            int index1 = 0;
            // Read the doctor schedule from the CSV file and display available slots
            try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule))) {
                String line;
                br.readLine(); //header
                
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length < 4) continue;

                    String doctorID = columns[0].trim();
                    String doctName = columns[1].trim();
                    String status = columns[4].trim();

                    if (doctorID.equalsIgnoreCase(newDoctorID) && status.equalsIgnoreCase("available")) {
                        String availableDate = columns[2].trim();  // Date in the schedule file
                        String availableTime = columns[3].trim();  // Time in the schedule file
                        availableSlots.add(availableDate + " " + availableTime);
                        index1++;
                        System.out.println(index1 + ". Doctor's Name: " + doctName + ", Date: " + availableDate + ", Time: " + availableTime);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the doctor schedule file: " + e.getMessage());
                return;
            }

            // Let the user choose a new appointment slot
            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for this doctor.");
                continue;
            }

            System.out.print("Enter the index of the new appointment slot: ");
            input = sc.nextLine().trim();

            int slotIndex = -1;
            try {
                slotIndex = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid slot index. Please try again.");
                continue;
            }

            if (slotIndex < 0 || slotIndex >= availableSlots.size()) {
                System.out.println("Invalid slot index. Please try again.");
                continue;
            }

            // Get the selected slot
            String selectedSlot = availableSlots.get(slotIndex);
            System.out.println(selectedSlot);
            String[] slotParts = selectedSlot.split(" ");
            String selectedDate = slotParts[0].trim();
            String selectedTime = slotParts[1].trim();
           
            // Parse selected date and time
            LocalDate apptDate = LocalDate.parse(selectedDate, dateFormatter);
            LocalTime apptTime = LocalTime.parse(selectedTime, timeFormatter);
            
            String date = apptDate.format(dateFormatter);
            String time = apptTime.format(timeFormatter);
            
            String doctName = null;

            // Update the appointment in Appointments.csv
            List<String> updatedLines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePathAppointments))) {
                String line;
                boolean appointmentFound = false;

                updatedLines.add(br.readLine()); // Add header

                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length >= 5) {
                        String doctorID = columns[0].trim();
                        doctName = columns[1].trim();
                        String patientID = columns[2].trim();
                        //String date1 = columns[3].trim();
                        //String time1 = columns[4].trim();
                        
                        String dateStr = columns[3].trim();
                        String timeStr = columns[4].trim();

                        LocalDate fileDate = LocalDate.parse(dateStr, dateFormatter);
                        LocalTime fileTime = LocalTime.parse(timeStr, timeFormatter);

                        // Compare the appointment details
                        if (doctorID.equalsIgnoreCase(appointmentToReschedule.getDoctorID()) &&
                                patientID.equalsIgnoreCase(appointmentToReschedule.getPatientID()) &&
                                fileDate.equals(appointmentToReschedule.getDate()) &&
                                fileTime.equals(appointmentToReschedule.getTime())) {
                            // Skip this line (remove the old appointment)
                            appointmentFound = true;
                            continue; //dont add the old appt to the updatedLines
                        }
                    }
                        
                    updatedLines.add(line); // Otherwise, keep the line
                    
                }

                if (!appointmentFound) {
                    System.out.println("Appointment not found to reschedule.");
                    return;
                }

                // Create the new appointment line
                String newAppointmentLine = newDoctorID + "," + doctName + "," +
                        appointmentToReschedule.getPatientID() + "," +
                        date.toString() + "," + time.toString() + ",Scheduled";

                // Add the new appointment to the updated list
                updatedLines.add(newAppointmentLine);

                // Write the updated list back to the file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathAppointments))) {
                    for (String updatedLine : updatedLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                    System.out.println("Appointment successfully rescheduled. \n");
                }
            } catch (IOException e) {
                System.out.println("Error updating the appointments file: " + e.getMessage());
            }

            // Update the doctor schedule: set old slot to 'available' and new slot to 'scheduled'
            List<String> updatedScheduleLines = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule))) {
                String line = br.readLine(); // Read header
                updatedScheduleLines.add(line);

                // Process each line in the schedule file
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    String doctorID = columns[0].trim();
                    LocalDate slotDate = LocalDate.parse(columns[2].trim(), dateFormatter);
                    LocalTime slotTime = LocalTime.parse(columns[3].trim(), timeFormatter);
                    String status = columns[4].trim();

                    // Update old appointment slot to 'Available'
                    if (doctorID.equalsIgnoreCase(appointmentToReschedule.getDoctorID()) &&
                        slotDate.equals(appointmentToReschedule.getDate()) &&
                        slotTime.equals(appointmentToReschedule.getTime()) &&
                        status.equalsIgnoreCase("Scheduled")) {
                        columns[4] = "Available";
                    }

                    // Update new appointment slot to 'Scheduled'
                    if (doctorID.equalsIgnoreCase(newDoctorID) &&
                        slotDate.equals(apptDate) &&
                        slotTime.equals(apptTime) &&
                        status.equalsIgnoreCase("Available")) {
                        columns[4] = "Scheduled";
                    }

                    // Add the updated row back to the list
                    updatedScheduleLines.add(String.join(",", columns));
                }

                // Write all lines back to the schedule file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathSchedule))) {
                    for (String updatedLine : updatedScheduleLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                }
                //System.out.println("Doctor's schedule updated successfully. \n");
                
            break;

            } catch (IOException e) {
                System.out.println("Error reading or updating the doctor schedule file: " + e.getMessage());
            }
        }
    }
   
    /**
     * cancelAppointment method allows patients to cancel their scheduled appointments with doctors
     */
    // Method to cancel an appointment
    public void cancelAppointment() {
        // File paths
        String filePathAppointments = "/Users/glyni/OneDrive/Desktop/SC2002 project/Appointments.csv";
        String filePathSchedule = "/Users/glyni/OneDrive/Desktop/SC2002 project/Doctor_schedule.csv";
        String patientRecordsPath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Patient_records.csv";
        List<Appointment> scheduledAppointments = new ArrayList<>();

        // Define the correct date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        boolean hasAppointments = false; // Flag to check whether there are appointments
        
        // Read from appointment csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathAppointments))) {
            String line;
            reader.readLine(); // Read and ignore the header
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");  // Split CSV line by commas

                // Ensure that the row has the correct number of columns
                if (columns.length < 6) continue;

                String doctorID = columns[0].trim();  // Assuming doctor ID is in the first column (index 0)
                String doctName = columns[1].trim();
                String patientID = columns[2].trim();  // Assuming patient ID is in the second column (index 1)
                String dateStr = columns[3].trim();
	            String timeStr = columns[4].trim();
	            String status = columns[5].trim();
	            
	            // Add scheduled appointments for the logged-in doctor
	            try {
	                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
	                LocalTime time = LocalTime.parse(timeStr, timeFormatter);
	                
	             // Check if this appointment matches the current patient's ID and the status is "Scheduled"
                    if (patientID.equals(this.getHospitalID()) && status.equalsIgnoreCase("Scheduled")) {
                        scheduledAppointments.add(new Appointment(doctorID, doctName, patientID, date, time, status));
                        hasAppointments = true;  // Mark that an appointment was found
                    }

                } catch (DateTimeParseException e) {
                    //System.out.println("Error parsing date: " + dateStr + " for appointment: " + line);
                    continue;  // Skip this row if date parsing fails
                }
	                
            }

            // If no appointments were found, notify the user
            if (!hasAppointments) {
                System.out.println("No scheduled appointments found for this patient.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }

        // Display scheduled appointments
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments found.\n");
            return;
        }

        System.out.println("Your Scheduled Appointments:");
        System.out.println("--------------------------------------------------------------");

        // Print the list of scheduled appointments with their index
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment appointment = scheduledAppointments.get(i);
            String doctorID = appointment.getDoctorID();
            String doctName = appointment.getDoctName();
            String date = appointment.getDate().format(dateFormatter);
            String time = appointment.getTime().format(timeFormatter);
            String status = appointment.getStatus();

            System.out.println((i + 1) + ". Doctor ID: " + doctorID + ", Doctor's Name: " + doctName + ", Date: " + date + ", Time: " + time + ", Status: " + status);
        }

        // Prompt user to select an appointment to cancel
        boolean validAppointment = false;
        Appointment selectedAppointment = null;

        while (!validAppointment) {
            System.out.print("Enter the number corresponding to the appointment to cancel: ");
            int appointmentIndex = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Validate the index
            if (appointmentIndex < 1 || appointmentIndex > scheduledAppointments.size()) {
                System.out.println("Invalid index. Please try again.");
                continue;
            }

            // Get the selected appointment
            selectedAppointment = scheduledAppointments.get(appointmentIndex - 1);
            validAppointment = true;
        }

        // Confirm cancellation
        System.out.println("You have selected the following appointment to cancel:");
        System.out.println("Doctor ID: " + selectedAppointment.getDoctorID() + ", Doctor's Name: " + selectedAppointment.getDoctName() + ", Date: " + selectedAppointment.getDate().format(dateFormatter) + ", Time: " + selectedAppointment.getTime());
        System.out.print("Are you sure you want to cancel this appointment? (y/n): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            // Remove from scheduledAppointments list
            scheduledAppointments.remove(selectedAppointment);

            // Update Appointments.csv: change the status of the canceled appointment
            List<String> updatedAppointments = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePathAppointments))) {
                String line;
                String header = br.readLine(); // Save header
                updatedAppointments.add(header); // Add the header to the list

                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length < 6) continue;

                    String doctorID = columns[0].trim();
                    String date = columns[3].trim();
                    String time = columns[4].trim();
                    String status = columns[5].trim();

                    // If the appointment matches the one selected, update the status to "Canceled"
                    if (doctorID.equals(selectedAppointment.getDoctorID()) 
                        && date.equals(selectedAppointment.getDate().format(dateFormatter))
                        && time.equals(selectedAppointment.getTime().format(timeFormatter)) 
                        && status.equalsIgnoreCase("Scheduled")) {
                        columns[5] = "Cancelled"; // Update the status
                        line = String.join(",", columns); // Rebuild the line with updated status
                    }

                    // Add the line (modified or unmodified) to the updated list
                    updatedAppointments.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading the appointments file: " + e.getMessage());
                return;
            }

            // Write the updated appointments back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathAppointments))) {
                for (String updatedLine : updatedAppointments) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to the appointments file: " + e.getMessage());
            }
            

            // Update Doctor_schedule.csv: update the doctor's availability status
            List<String> updatedDoctorSchedule = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePathSchedule))) {
                String line;
                String header = br.readLine(); // Save header
                updatedDoctorSchedule.add(header);

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length < 5) continue;

                    String doctorID = values[0].trim();
                    String doctName = values[1].trim();
                    String date = values[2].trim();
                    String time = values[3].trim();

                    // If the doctor's schedule matches the appointment, mark as "Available"
                    if (doctorID.equals(selectedAppointment.getDoctorID()) && date.equals(selectedAppointment.getDate().format(dateFormatter))
                            && time.equals(selectedAppointment.getTime().format(timeFormatter))) {
                        updatedDoctorSchedule.add(doctorID + "," + doctName + "," +  date + "," + time + ",Available");
                    } else {
                        updatedDoctorSchedule.add(line); // Keep the rest unchanged
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the doctor schedule file: " + e.getMessage());
            }

            // Write the updated doctor schedule back to Doctor_schedule.csv
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePathSchedule))) {
                for (String updatedLine : updatedDoctorSchedule) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing the updated doctor schedule file: " + e.getMessage());
            }

            
            //remove appointment from patient records csv 
            List<String> updatedPatientRecords = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(patientRecordsPath))) {
                String line;
                String header = br.readLine(); // Save header
                updatedPatientRecords.add(header);
                
                while ((line = br.readLine()) != null) {
	                String[] columns = line.split(",");
	                String doctorID = columns[0].trim();
	                String patientID = columns[1].trim();
	                
	                // Check if the doctor ID is "-" (or some other criteria to identify the row to remove) 
	                // and the patient ID matches the selected appointment's patient ID
	                if (doctorID.equals("-") && patientID.equals(selectedAppointment.getPatientID())) {
	                    // If it matches, we skip this row (i.e., do not add it to the updatedPatientRecords list)
	                    continue; // This will effectively remove the row
	                }
	                updatedPatientRecords.add(line); // Add updated or unchanged line to the list
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the patient list's file: " + e.getMessage());
	        }

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientRecordsPath))) {
	            for (String line : updatedPatientRecords) {
	                writer.write(line);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            System.out.println("Error writing to the patient list's file: " + e.getMessage());
	        }
            System.out.println("Appointment has been successfully canceled.\n");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

   /**
    * viewScheduledAppointments method allows patients to view the appointments
    */
    // Method to view scheduled appointments
    public void viewScheduledAppointments()
    {
    	System.out.println("Your Scheduled Appointments:");
    	System.out.println("--------------------------------------------------------------");
    	
    	boolean hasAppointments = false; //flag to check whether there are appointments
    	
    	String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Appointments.csv";
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");  // Split CSV line by commas
                String doctorID = columns[0];  // Assuming doctor ID is in the first column (index 0)
                String doctName = columns[1];
                String patientID = columns[2];  // Assuming patient ID is in the second column (index 1)
                String appointmentDate = columns[3];  // Assuming appointment date is in the third column (index 2)
                String appointmentTime = columns[4];  // Assuming appointment time is in the fourth column (index 3)
                String status = columns[5];  // Assuming status is in the fifth column (index 4)

                // Check if this appointment matches the current patient's ID
                if (patientID.equals(this.getHospitalID())) {  // Replace with the actual method to get current patient ID
                    // Print the appointment details
                    System.out.println("Doctor ID: " + doctorID + ", Doctor's Name: " + doctName + ", Date: " + appointmentDate + ", Time: " + appointmentTime + ", Status: " + status);
                    hasAppointments = true;  // Mark that an appointment was found
                }
            }
            if (hasAppointments == false)
            {
            	System.out.println("No Scheduled Appointments.");
            }
            System.out.println("\n");
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * viewPastAppointmentOutcomes method allows patients to view thier past appointment outcomes
     */
    // Method to view past appointment outcomes
    public void viewPastAppointmentOutcomes() {
        System.out.println("Past Appointment Outcomes:");
        System.out.println("--------------------------------------------------------------");
        
        String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Appointment_records.csv";
        
        boolean appointmentFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); //skip header
            
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                
                // Check if the line has the necessary columns
                if (columns.length < 8) continue;
                String doctorID = columns[0].trim();
                String patientID = columns[1].trim();
                String date = columns[2].trim();
                String time = columns[3].trim();
                String service = columns[4].trim();
                String meds = columns[5].trim();
                String status = columns[6].trim();
                String notes = columns[7].trim();

                // Display the details of the patient's past appointment outcomes
                if (patientID.equals(this.getHospitalID()))
                {
                	System.out.println("Doctor ID: " + doctorID);
                    System.out.println("Patient ID: " + patientID);
                    System.out.println("Date of Appointment: " + date);
                    System.out.println("Time of Appointment: " + time);
                    System.out.println("Type of Service: " + service);
                    System.out.println("Medication Name: " + meds);
                    System.out.println("Status: " + status);
                    System.out.println("Consultation Notes: " + notes);
                    System.out.println("--------------------------------------------------------------\n");
                    
                    appointmentFound = true;
                }
                
            }
            
        } catch (IOException e) {
            System.out.println("Error reading the patient records file: " + e.getMessage());
        }

        if (!appointmentFound) {
            System.out.println("No past appointment outcomes found.\n");
        }
    }
}