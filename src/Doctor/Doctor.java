package hms;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.util.*;

public class Doctor extends User {
	protected List<MedicalRecord> medicalRecords;
	protected List<Appointment> appointments; 
	protected List<AppointmentOutcomeRecord> appointmentRecords; 
	//protected List<Medication> medications;
	protected Map<LocalDate, List<LocalTime>> dateTimeMap = new HashMap<>(); //associate date with a list of time
	
	public Doctor(String hospitalID, String name, String password) { 
		super(hospitalID, name, password, "Doctor");
		this.medicalRecords = new ArrayList<>();
		this.appointments = new ArrayList<>();
		this.appointmentRecords = new ArrayList<>();
		//this.medications = new ArrayList<>();
		this.dateTimeMap = new HashMap<>(); //store and retrieve available dates and times for each doctor
    }
	
	public Map<LocalDate, List<LocalTime>> getDateTimeMap() {
	        return dateTimeMap;
	}
	 
	public List<Appointment> getAppointments() {
        return appointments;
    } //needed in HMSApp
	
	
	//DOCTORS CAN VIEW THE MEDICAL RECORDS OF PATIENTS UNDER THEIR CARE
	public void viewPatientMedicalRecord() {
		Scanner sc = new Scanner(System.in);
	    
	    while (true) {
	        // Ask for the patient ID or prompt to exit
	        System.out.println("Enter the Patient ID (or type 'exit' to go back to the menu): ");
	        String patientID = sc.nextLine().trim();
	        
	        // Exit the loop if user types 'exit'
	        if (patientID.equalsIgnoreCase("exit")) {
	            System.out.println("Going back to the menu...\n");
	            break;
	        }

	        boolean patientFound = false;
	        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_records.csv";

	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            String header = reader.readLine();  // Skip the header
	            if (header != null) {
	                // Check each line for the matching patientID
	                while ((line = reader.readLine()) != null) {
	                    String[] columns = line.split(",");
	                    String recordDoctorID = columns[0].trim();   // Doctor ID from CSV
	                    String recordPatientID = columns[1].trim();

	                    // Check if the patientID matches the one passed as argument
	                    if (recordPatientID.equals(patientID) && recordDoctorID.equals(this.getHospitalID())) {
	                        patientFound = true;

	                        String name = columns[2].trim();
	                        String dob = columns[3].trim();
	                        String gender = columns[4].trim();
	                        String phoneNum = columns[5].trim();
	                        String email = columns[6].trim();
	                        String bloodType = columns[7].trim();
	                        String drugAllergy = columns[8].trim();
	                        String diagnosis = columns[9].trim();
	                        String treatment = columns[10].trim();
	                        String medication = columns[11].trim();
	                        String status = columns[12].trim();
	                    

	                        // Print out the doctor's ID and patient's medical history (diagnosis, treatment, medications)
	                        System.out.println("Doctor assigned to patient (DoctorID): " + recordDoctorID);
	                        System.out.println("PatientID: " + recordPatientID);
	                        System.out.println("Name: " + name);
	                        System.out.println("Date of Birth: " + dob);
	                        System.out.println("Gender: " + gender);
	                        System.out.println("Phone Number: " + phoneNum);
	                        System.out.println("Email Address: " + email);
	                        System.out.println("Blood Type: " + bloodType);
	                        System.out.println("Drug Allergy: " + drugAllergy);
	                        System.out.println("Past Diagnosis: " + diagnosis);
	                        System.out.println("Past Treatment: " + treatment);
	                        System.out.println("Past Medications: " + medication + "\n");
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the Patient Records CSV file: " + e.getMessage());
	        }

	        // If the patient was not found, display a message
	        if (!patientFound) {
	            System.out.println("Patient with ID " + patientID + " not found.\n");
	        }
	    }
	}
	
	//DOCTORS CAN UPDATE THE MEDICAL RECORDS OF PATIENTS BY ADDING NEW DIAGNOSES, PRESCRIPTION, AND TREATMENT PLANS
	public void update()
	{
		Scanner sc = new Scanner(System.in);
	    
	    while (true) {
	        // Prompt for the patient ID or 'exit' to break the loop
	        System.out.println("Enter the Patient ID (or type 'exit' to go back to the menu): ");
	        String patientID = sc.nextLine().trim();
	        
	        // Exit the loop if the user types 'exit'
	        if (patientID.equalsIgnoreCase("exit")) {
	            System.out.println("Going back to the menu...\n");
	            break;
	        }

	        // Initialize the medications list to store available medications
	        List<String> medications = new ArrayList<>();
	        
	        //file path for medicine list
	        String medicationListPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
	        
	        try (BufferedReader br = new BufferedReader(new FileReader(medicationListPath)))
	        {
	        	String line;
	        	String header = br.readLine();
	        	
	        	while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    String medName = columns[0].trim();
                    
                    medications.add(medName);
	        	}
	        
		    } catch (IOException e) {
		        System.out.println("Error reading the medication list file: " + e.getMessage());
		    }

	        // Update patient's diagnoses, prescriptions, and treatment plans in CSV
	        // File path for patient records
	        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_records.csv";
	        
	        // Read current patient information
	        String doctId = "";
	        String patientId = "";
	        String name = null;
            String dob = null ;
            String gender = null;
            String phoneNum = null;
            String email = null;
            String bloodType = null;
            String drugAllergy = null;
            String diagnosis = null;
            String treatment = null;
            String medication = null;
	        String patientDiagnosis = "";
	        String patientTreatmentPlan = "";
	        String patientPrescription = "";
	        String status = "";
	        boolean patientFound = false;

	        // Read the patient records CSV and look for the patientID
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] columns = line.split(",");
	                if (columns.length < 5) continue; // Skip malformed lines
	                doctId = columns[0].trim();
	                patientId = columns[1].trim();
	                name = columns[2].trim();
                    dob = columns[3].trim();
                    gender = columns[4].trim();
                    phoneNum = columns[5].trim();
                    email = columns[6].trim();
                    bloodType = columns[7].trim();
                    drugAllergy = columns[8].trim();
                    diagnosis = columns[9].trim();
                    treatment = columns[10].trim();
                    medication = columns[11].trim();
                    status = columns[12].trim();
	                
	                // If the patientID and doctorID match and status of appointment is completed, can edit patient information, else only load their information
	                if (patientId.equals(patientID) && doctId.equals(this.getHospitalID())) {
	                    patientDiagnosis = diagnosis;
	                    patientTreatmentPlan = treatment;
	                    patientPrescription = medication;
	                    patientFound = true;
	                    break;
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the patient records file: " + e.getMessage());
	        }

	        // If patient is not found
	        if (!patientFound) {
	            System.out.println("Patient ID not found in records.\n");
	            continue; // Continue the loop for another attempt
	        }

	        // Display existing patient info
	        System.out.println("Current Patient Information:");
	        System.out.println("PatientID: " + patientId);
	        System.out.println("Diagnosis: " + patientDiagnosis);
	        System.out.println("Treatment Plan: " + patientTreatmentPlan);
	        System.out.println("Prescription: " + patientPrescription);
	        System.out.println("\n");
	        
	        //if status is completed , then can update patient details
	        if (status.equalsIgnoreCase("Completed"))
	        {
		        // Prompt user to update the patient's details
		        System.out.println("Enter new diagnosis (leave blank to keep current): ");
		        String newDiagnosis = sc.nextLine().trim();
		        if (!newDiagnosis.isEmpty()) {
		            patientDiagnosis = newDiagnosis;
		        }
	
		        System.out.println("Enter new treatment plan (leave blank to keep current): ");
		        String newTreatmentPlan = sc.nextLine().trim();
		        if (!newTreatmentPlan.isEmpty()) {
		            patientTreatmentPlan = newTreatmentPlan;
		        }
		        
		        System.out.println("Enter new prescription (leave blank to keep current): ");
		        String newPrescription = sc.nextLine().trim();
		        // Loop to validate user input and ensure medication exists in the list
		        while (true) {
		            // Check if the input is empty
		            if (newPrescription.isEmpty()) {
		                // Use the previously stored value if input is blank
		                newPrescription = patientPrescription;
		                break; // Exit the loop since we've decided to keep the existing value
		            }
		            
		            // Check if the medication is in the list of valid medications
		            if (medications.contains(newPrescription)) {
		                // Medication exists, so we can update the patient's prescription
		                patientPrescription = newPrescription;
		                break; // Exit the loop once a valid medication is selected
		            } else {
		                // Invalid medication; prompt the user again
		                System.out.println("Invalid medication. Please choose from the following available medications:");
		                System.out.println(medications);  // Display available medications
		                
		                // Prompt user again for valid input
		                System.out.println("Enter a valid prescription:");
		                newPrescription = sc.nextLine().trim();
		            }
		        }
		        
		        
		        // Update the patient records CSV
		        List<String> updatedLines = new ArrayList<>();
		        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		            String line;
		            while ((line = br.readLine()) != null) {
		                String[] columns = line.split(",");
		                //if (columns.length < 5) continue; // Skip malformed lines
		                String existingPatientID = columns[1].trim();
	
		                // If we find the patient with matching patientID, update their information
		                if (existingPatientID.equals(patientID) && doctId.equals(this.getHospitalID())) {
		                    line = doctId + "," + existingPatientID + "," +  name + "," + dob + "," + gender + "," + phoneNum + "," + email + "," + bloodType + "," + drugAllergy + "," + patientDiagnosis + "," + patientTreatmentPlan + "," + patientPrescription + "," + status;
		                }
		                updatedLines.add(line);
		            }
		        } catch (IOException e) {
		            System.out.println("Error reading the patient records file: " + e.getMessage());
		        }
	
		        // Write updated patient records back to the file
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
		            for (String updatedLine : updatedLines) {
		                writer.write(updatedLine);
		                writer.newLine();
		            }
		        } catch (IOException e) {
		            System.out.println("Error writing the updated patient records file: " + e.getMessage());
		        }
	
		        // Update the patient list CSV if necessary (e.g., if patient's information changes)
		        String filePath1 = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_List.csv";
		        
		        try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
		            List<String> patientList = new ArrayList<>();
		            String line;
		            while ((line = br.readLine()) != null) {
		                String[] columns = line.split(",");
		                if (columns.length < 12) continue; // Skip malformed lines
		                String currentPatientId = columns[0].trim(); // patientId is assumed to be in the first column
		                
		                // If the patient list contains the given ID, update it (if necessary)
		                if (currentPatientId.equals(patientID)) {
		                    line = currentPatientId + "," + columns[1] + "," + columns[2] + "," + columns[3] + "," + columns[4] + "," + columns[5] + "," + columns[6] + "," + columns[7] + "," + patientDiagnosis + "," + patientTreatmentPlan + "," + patientPrescription + "," + columns[11] + "," + columns[12];
		                }
		                patientList.add(line);
		            }
	
		            // Write the updated patient list back to the file
		            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
		                for (String patientLine : patientList) {
		                    writer.write(patientLine);
		                    writer.newLine();
		                }
		            } catch (IOException e) {
		                System.out.println("Error writing the updated patient list file: " + e.getMessage());
		            }
		        } catch (IOException e) {
		            System.out.println("Error reading the patient list file: " + e.getMessage());
		        }
	
		        System.out.println("Patient records updated successfully.\n");
		    }
	    }
	}

	//DO WE NEED THIS ACTUALLY ????????
	private List<MedicalRecord> findMedicalRecordsByPatientID(String patientID) {
	    List<MedicalRecord> patientRecords = new ArrayList<>();
	    for (MedicalRecord record : medicalRecords) {
	        if (record.getPatientID().equals(patientID)) {
	            patientRecords.add(record);
	        }
	    }
	    return patientRecords;
	}
	
	//DOCTORS CAN VIEW THEIR PERSONAL SCHEDULE
	public void viewPersonalSchedule() { //displays available times with date and appointment dates and times
        System.out.println("Doctor " + this.getHospitalID() + "'s Schedule:");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        boolean hasAppointments = false;
        
        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Doctor_schedule.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                try {
                    String doctorID = values[0].trim();
                    String name = values[1].trim();
                    LocalDate date = LocalDate.parse(values[2].trim(), dateFormatter); // Parse date with dd/MM/yyyy format
                    LocalTime time = LocalTime.parse(values[3].trim()); // Parse time in HH:mm format (example: 14:30)
                    String status = values[4].trim();
                    
                    //if doctID matches with the logged in doctID
                    if (doctorID.equals(this.getHospitalID())) {    
                        // Print the schedule
                        System.out.println("Doctor ID: " + doctorID + ", Doctor's Name: " + name + ", Date: " + date.format(dateFormatter) + ", Time: " + time + ", Status: " + status);
                        hasAppointments = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line + " - " + e.getMessage());
                } 
            }
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If no appointments were found, print "Schedule is empty"
        if (!hasAppointments) {
            System.out.println("Schedule is empty.\n");
        }
    }
	
	//DOCTORS CAN SET THEIR AVAILABILITY FOR APPOINTMENTS
	public void setAvailability() {
        Scanner sc = new Scanner(System.in);
        LocalTime availableTime;
        LocalDate availableDate;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Doctor_schedule.csv";

        // First, check if the file already has a header, and only append data
        // We will just proceed to add the availability data, assuming the header already exists
        // No need to check for header if you are sure it exists.

        while (true) {
            System.out.println("Enter available date for Doctor " + getHospitalID() + " in DD/MM/YYYY (or type 'done' to finish):");
            String inputDate = sc.nextLine();
            if (inputDate.equalsIgnoreCase("done")) {
                break;
            }

            try {
                availableDate = LocalDate.parse(inputDate, dateFormatter);
                List<LocalTime> timeSlots = new ArrayList<>();
                System.out.println("Enter available times for " + availableDate.format(dateFormatter) + " in HH:MM (or type 'done' to finish):");

                while (true) {
                    String inputTime = sc.nextLine();
                    if (inputTime.equalsIgnoreCase("done")) {
                        break;
                    }
                    try {
                        availableTime = LocalTime.parse(inputTime);
                        if (!timeSlots.contains(availableTime)) {
                            timeSlots.add(availableTime);
                        } else {
                            System.out.println("Time slot already exists for this date.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid time format. Please enter time as HH:MM.");
                    }
                }

                // Sort the time slots to ensure they are in chronological order
                Collections.sort(timeSlots);

                dateTimeMap.put(availableDate, timeSlots);

                // Update CSV file after setting each date's availability (append mode)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {  // Append mode
                    for (LocalTime time : timeSlots) {
                        writer.write(getHospitalID() + "," + getName() + "," + availableDate.format(dateFormatter) + "," + time + "," + "Available" + "\n");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the CSV file.");
                    e.printStackTrace();
                }

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date as DD/MM/YYYY.");
            }
        }
        System.out.println("All available dates and times updated.\n");
    }
	
	//DOCTORS CAN ACCEPT OR DECLINE APPOINTMENT REQUESTS
	public void appointmentRequests() {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Scheduled appointments requests: ");
	    System.out.println("--------------------------------------------------------");

	    // List to store scheduled appointments
	    List<Appointment> scheduledAppointments = new ArrayList<>();
	    
	    // DateTimeFormatter for parsing date and time in CSV
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    
	    String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
	    
	    // Load appointments
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine(); // Skip header
	        
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");
	            String doctorID = columns[0].trim();
	            String doctName = columns[1].trim();
	            String patientID = columns[2].trim();
	            String dateStr = columns[3].trim();
	            String timeStr = columns[3].trim();

	            //LocalDate date = LocalDate.parse(columns[3].trim(), dateFormatter);
	            //LocalTime time = LocalTime.parse(columns[4].trim(), timeFormatter);
	            String status = columns[5].trim();
	            
	            try {
	                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
	                LocalTime time = LocalTime.parse(columns[4].trim(), timeFormatter);

	                // Add scheduled appointments for the logged-in doctor
	                if (status.equalsIgnoreCase("Scheduled") && doctorID.equals(this.getHospitalID())) {
	                    scheduledAppointments.add(new Appointment(doctorID, doctName, patientID, date, time, status));
	                }
	            } catch (DateTimeParseException e) {
	                System.out.println("Error parsing date: " + dateStr + ", Error parsing time: " + timeStr);
	                continue; // Skip problematic line
	            }

	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	        return;
	    }
	    
	    // Check if there are any appointments
	    if (scheduledAppointments.isEmpty()) {
	        System.out.println("No scheduled appointments found for Doctor ID: " + this.getHospitalID() + "\n");
	        return;
	    }
	    
	    while (true) {
	        // Display appointments
	        for (int i = 0; i < scheduledAppointments.size(); i++) {
	            Appointment appointment = scheduledAppointments.get(i);
	            System.out.println((i + 1) + ". PatientID: " + appointment.getPatientID() +
	                    ", Date: " + appointment.getDate().format(dateFormatter) +
	                    ", Time: " + appointment.getTime().format(timeFormatter));
	        }

	        // Get user input
	        System.out.print("Enter the appointment index to update (or type 'exit' to finish): ");
	        String input = sc.nextLine();
	        if (input.equalsIgnoreCase("exit")) {
	            break;
	        }

	        int index;
	        Appointment selectedAppointment = null;
	        try {
	            index = Integer.parseInt(input) - 1;
	            if (index < 0 || index >= scheduledAppointments.size()) {
	                System.out.println("Invalid index. Try again.");
	                continue;
	            }
	            selectedAppointment = scheduledAppointments.get(index);
	        } catch (NumberFormatException e) {
	            System.out.println("Please enter a valid number.");
	            continue;
	        }

	        // Confirm or cancel
	        System.out.print("Confirm or Cancel this appointment? (confirm/cancel): ");
	        String choice = sc.nextLine().trim().toLowerCase();

	        String newStatus;
	        if (choice.equals("confirm")) {
	            newStatus = "Confirmed";
	        } else if (choice.equals("cancel")) {
	            newStatus = "Cancelled";
	        } else {
	            System.out.println("Invalid choice. Try again.");
	            continue;
	        }

	        System.out.println("Appointment status updated to: " + newStatus);

	        // Update the CSV file
	        String appointmentFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
	        String patientFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_records.csv";
	        String scheduleFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Doctor_schedule.csv";

	        // Update appointment file
	        List<String> lines = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentFilePath))) {
	            String line;
	            String header = reader.readLine();
	            if (header != null) {
	                lines.add(header);
	            }

	            while ((line = reader.readLine()) != null) {
	                String[] columns = line.split(",");
	                if (columns[0].trim().equals(selectedAppointment.getDoctorID()) && columns[1].trim().equals(selectedAppointment.getDoctName()) &&
	                        columns[3].trim().equals(selectedAppointment.getDate().format(dateFormatter)) &&
	                        columns[4].trim().equals(selectedAppointment.getTime().format(timeFormatter))) {
	                    columns[5] = newStatus; // Update status
	                    line = String.join(",", columns);
	                }
	                lines.add(line);
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading appointment's file: " + e.getMessage());
	        }
	        
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentFilePath))) {
	            for (String line : lines) {
	                writer.write(line);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            System.out.println("Error writing to the appointment file: " + e.getMessage());
	        }
	        
	        // Update patient records
	        List<String> lines1 = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(patientFilePath))) {
	            String line1;
	            String header = reader.readLine();
	            if (header != null) {
	                lines1.add(header);
	            }

	            while ((line1 = reader.readLine()) != null) {
	                String[] columns = line1.split(",");
	                // Check if Patient ID matches
	                if (columns[1].trim().equals(selectedAppointment.getPatientID())) {
	                	// Handle status update based on user's choice
	                    if (newStatus.equalsIgnoreCase("Cancelled") && columns[0].trim().equals("-")) {
	                        columns[12] = "Cancelled"; // Update status to Cancelled
	                    } else if (newStatus.equalsIgnoreCase("Confirmed")) {
	                        columns[0] = selectedAppointment.getDoctorID(); // Update Doctor ID
	                        columns[12] = "Confirmed"; // Update status to Confirmed
	                    }
	                    line1 = String.join(",", columns); // Reconstruct the line
	                }
	                lines1.add(line1); // Add updated or unchanged line to the list
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the patient list's file: " + e.getMessage());
	        }

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientFilePath))) {
	            for (String line : lines1) {
	                writer.write(line);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            System.out.println("Error writing to the patient list's file: " + e.getMessage());
	        }
	        
	        // Update doctor schedule
	        List<String> lines2 = new ArrayList<>();
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFilePath))) {
	            String header = reader.readLine();
	            if (header != null) {
	                lines2.add(header); // Keep the header line
	            }

	            String line2;
	            while ((line2 = reader.readLine()) != null) {
	                String[] columns = line2.split(",");
	                String doctorID = columns[0].trim();
	                String doctName = columns[1].trim();
	                String date = columns[2].trim();
	                String time = columns[3].trim();
	                String status = columns[4].trim();

	             // Check if this entry corresponds to the canceled appointment
	                if (doctorID.equals(this.getHospitalID()) 
	                        && date.equals(selectedAppointment.getDate().format(dateFormatter)) 
	                        && time.equals(selectedAppointment.getTime().format(timeFormatter))) {
	                	if (newStatus.equalsIgnoreCase("Cancelled")) {
	                        // Skip this entry (removing the timeslot)
	                        continue;
	                    } else if (newStatus.equalsIgnoreCase("Confirmed")) {
	                        // Update status to Confirmed
	                        line2 = doctorID + "," + columns[1].trim() + "," 
	                                + selectedAppointment.getDate().format(dateFormatter) + "," 
	                                + selectedAppointment.getTime().format(timeFormatter) + "," 
	                                + "Confirmed";
	                    }
	                    // Skip adding the entry if the appointment is canceled
	                }
	                lines2.add(line2);
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the doctor's schedule CSV file: " + e.getMessage());
	            return;
	        }

	        // Write the updated lines back to the doctor's schedule file
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFilePath))) {
	            for (String line : lines2) {
	                writer.write(line);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            System.out.println("Error writing to the doctor's schedule file: " + e.getMessage());
	        }

	        // Remove the appointment from the list
	        scheduledAppointments.remove(index); 
	    }
	}
	

    //DOCTORS CAN VIEW THE LIST OF UPCOMING APPOINTMENTS
    public void viewUpcomingAppointments() {
    	// Display the accepted appointments for the logged-in doctor
        System.out.println("Upcoming Confirmed Appointments: ");
        System.out.println("--------------------------------------------------------");
    	
    	//view accepted appointments from csv file
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        
    	List<Appointment> acceptedAppointments = new ArrayList<>();
    	
    	// File path for appointments CSV
        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
        
        // Read the appointments from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String header = reader.readLine();
            
            // Loop through the lines in the CSV file
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String doctorID = columns[0].trim();
                String doctName = columns[1].trim();
                String patientID = columns[2].trim();
                LocalDate date = LocalDate.parse(columns[3].trim(), dateFormatter);
                LocalTime time = LocalTime.parse(columns[4].trim(), timeFormatter);
                String status = columns[5].trim();
                
                // Add accepted appointments for the logged-in doctor
                if (status.equalsIgnoreCase("Confirmed") && doctorID.equals(this.getHospitalID())) {
                    acceptedAppointments.add(new Appointment(doctorID, doctName, patientID, date, time, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }

        // Check if there are any accepted appointments
        if (acceptedAppointments.isEmpty()) {
            System.out.println("No upcoming confirmed appointments found for Doctor ID: " + this.getHospitalID() + "\n");
            return;
        }
        
        for (int i = 0; i < acceptedAppointments.size(); i++) {
            Appointment appointment = acceptedAppointments.get(i);
            System.out.println((i + 1) + ". DoctorID: " + appointment.getDoctorID() +
                    ", PatientID: " + appointment.getPatientID() +
                    ", Date: " + appointment.getDate().format(dateFormatter) +
                    ", Time: " + appointment.getTime().format(timeFormatter) +
                    ", Status: " + appointment.getStatus() + "\n");
        }
    } 
  
    //DOCTORS CAN UPDATE THE APPOINTMENT OUTCOME AFTER EACH COMPLETED APPOINTMENT
    public void recordAppointmentOutcome() {
    	// Display the accepted appointments for the logged-in doctor
        System.out.println("Record Confirmed Appointments Outcome: ");
        System.out.println("--------------------------------------------------------");
        
    	//display the list of accepted appts and update from the index of the appt accordingly
    	//view accepted appointments from csv file
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        
    	List<Appointment> acceptedAppointments = new ArrayList<>();
    	
    	// File path for appointments CSV
        String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
        
        // Read the appointments from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String header = reader.readLine();
            
            // Loop through the lines in the CSV file
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String doctorID = columns[0].trim();
                String doctName = columns[1].trim();
                String patientID = columns[2].trim();
                LocalDate date = LocalDate.parse(columns[3].trim(), dateFormatter);
                LocalTime time = LocalTime.parse(columns[4].trim(), timeFormatter);
                String status = columns[5].trim();
                
                // Add accepted appointments for the logged-in doctor
                if (status.equalsIgnoreCase("Confirmed") && doctorID.equals(this.getHospitalID())) {
                    acceptedAppointments.add(new Appointment(doctorID, doctName, patientID, date, time, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }

        // Initialize the medications list to store available medications
        List<String> medications = new ArrayList<>();
        
        //file path for medicine list
        String medicationListPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(medicationListPath)))
        {
        	String line;
        	String header = br.readLine();
        	
        	while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                String medName = columns[0].trim();
                
                medications.add(medName);
        	}
        
	    } catch (IOException e) {
	        System.out.println("Error reading the medication list file: " + e.getMessage());
	    }
        
        //additional functionaility
        Map<String, String> patientDrugAllergies = new HashMap<>();
        
        // File path for patient list
        String patientListPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_List.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(patientListPath))) {
            String line;
            String header = br.readLine(); // Read the header line
            
            String[] headers = header.split(",");
            //int patientIdIndex = Arrays.asList(headers).indexOf("PatientID");
            //int drugAllergyIndex = Arrays.asList(headers).indexOf("Drug Allergy");
            
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                
                //if (columns.length > drugAllergyIndex) {
                    //String patientId = columns[patientIdIndex].trim(); 
                    //String drugAllergies = columns[drugAllergyIndex].trim(); 
                    String patientID = columns[0].trim();
                    String drugAllergy = columns[7].trim();
                    
                    if (drugAllergy.equalsIgnoreCase("none")) {
                    	drugAllergy = "none";
                    }
                    
                    // Add the PatientID and Drug Allergies to the map
                    patientDrugAllergies.put(patientID, drugAllergy);
                //}
            }    
        } catch (IOException e) {
            System.out.println("Error reading the patient list file: " + e.getMessage());
        }
        
        // Check if there are any accepted appointments
        if (acceptedAppointments.isEmpty()) {
            System.out.println("No accepted appointments found for Doctor ID: " + this.getHospitalID() + "\n");
            return;
        }
        
        for (int i = 0; i < acceptedAppointments.size(); i++) {
            Appointment appointment = acceptedAppointments.get(i);
            System.out.println((i + 1) + ". DoctorID: " + appointment.getDoctorID() +
                    ", PatientID: " + appointment.getPatientID() +
                    ", Date: " + appointment.getDate().format(dateFormatter) +
                    ", Time: " + appointment.getTime().format(timeFormatter) +
                    ", Status: " + appointment.getStatus());
        }
        
        // Get user input for the appointment to update
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the appointment index to record outcome (or type 'exit' to finish): ");
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            return;  // Exit if the user wants to stop
        }

        int index;
        try {
            index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= acceptedAppointments.size()) {
                System.out.println("Invalid index. Try again.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        // Get the selected appointment
        Appointment selectedAppointment = acceptedAppointments.get(index);

        // Get the outcome details from the user
        System.out.print("Enter service provided: ");
        String serviceProvided = sc.nextLine();

        // Validate service provided input
        while (serviceProvided.trim().isEmpty()) {
            System.out.print("Service provided cannot be empty. Please enter a valid service: ");
            serviceProvided = sc.nextLine();
        }
        
        System.out.print("Enter medication name: ");
        String medicationName = sc.nextLine();
        String patientAllergy = patientDrugAllergies.get(selectedAppointment.getPatientID());
        
        // Validate medication name (optional - you can add more specific validation)
        // Loop until a valid medication name is entered
        while (true) {
            // Check if the medication is valid (case-insensitive)
            boolean isValidMedication = false;
            for (String med : medications) {
                if (med.equalsIgnoreCase(medicationName)) {
                    isValidMedication = true;
                    break;
                }
            }

            // If medication is invalid, prompt the user again
            if (!isValidMedication) {
                System.out.println("Invalid medication. Please choose from the following available medications:");
                System.out.println(medications);  // Display available medications

                // Prompt user again for valid input
                System.out.print("Enter a valid medication name: ");
                medicationName = sc.nextLine().trim();
                continue; // Restart the loop if medication is invalid
            }
            
            // If the medication is in the allergy list, prompt again
            if (medicationName.equalsIgnoreCase(patientAllergy)) {
                System.out.println("Warning: This medication (" + medicationName + ") is on the patient's allergy list. It cannot be prescribed.");
                System.out.print("Enter another medication name: ");
                medicationName = sc.nextLine().trim();
            } else {
                // If both checks pass, exit the loop
                break;
            }
        }
        
        String status = "Pending"; //default
        
        System.out.print("Enter consultation notes: ");
        String consultationNotes = sc.nextLine();
        
        // Validate consultation notes input (optional check for length)
        while (consultationNotes.trim().isEmpty()) {
            System.out.print("Consultation notes cannot be empty. Please enter valid consultation notes: ");
            consultationNotes = sc.nextLine();
        }
        
        // Prepare the appointment record for the new file
        AppointmentOutcomeRecord appointmentRecord = new AppointmentOutcomeRecord(
            selectedAppointment.getDoctorID(),
            selectedAppointment.getPatientID(),
            selectedAppointment.getDate(),
            selectedAppointment.getTime(),
            serviceProvided,
            medicationName,
            status,
            consultationNotes
        );
        
        // Add the appointment record to the appointmentRecords list
        appointmentRecords.add(appointmentRecord);
        
        //update the appointment records csv 
        String filePath1 = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointment_records.csv";
        List<String> lines = new ArrayList<>();
        
        //Read the existing file content to preserve the header and other records
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            // Read header
            String header = reader.readLine();
            if (header != null) {
                lines.add(header); // Keep the header line
            }

            // Read the rest of the file (if any) and add it to the list
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the Appointment records file: " + e.getMessage());
        }
            
        String appointmentRecordLine = appointmentRecord.getDoctID() + ","
                + appointmentRecord.getPatientID() + ","
                + appointmentRecord.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ","
                + appointmentRecord.getTime().format(DateTimeFormatter.ofPattern("HH:mm")) + ","
                + appointmentRecord.getServiceProvided() + ","
                + appointmentRecord.getMedicationName() + ","
                + appointmentRecord.getStatus() + ","
                + appointmentRecord.getConsultationNotes();
        
        // Append the new record to the list of lines
        lines.add(appointmentRecordLine);
        
        // Write all the content back to the CSV file, including the header and the new record
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
            // Write all lines to the file
            for (String fileLine : lines) {
                writer.write(fileLine);
                writer.newLine();
            }

            System.out.println("Appointment outcome recorded successfully.\n");
        } catch (IOException e) {
            System.out.println("Error writing to the Appointment records file: " + e.getMessage());
        }
        
        //update appt status as completed in appointment csv, patient records csv and doct schedule csv
        // File paths for the appointment CSV, patient records csv and the doctor's schedule CSV
        String appointmentsFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointments.csv";
        String doctorScheduleFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Doctor_schedule.csv";
        String patientRecordsFilePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Patient_records.csv";

        //UPDATE APPOINTMENTS CSV
        List<String> updatedAppointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            String header = reader.readLine(); // Read and keep the header
            if (header != null) {
                updatedAppointments.add(header);
            }

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[0].trim().equals(selectedAppointment.getDoctorID()) &&
                    columns[2].trim().equals(selectedAppointment.getPatientID()) &&
                    columns[3].trim().equals(selectedAppointment.getDate().format(dateFormatter)) &&
                    columns[4].trim().equals(selectedAppointment.getTime().format(timeFormatter))) {
                    // Update the status to 'Completed'
                    columns[5] = "Completed";
                    line = String.join(",", columns);
                }
                updatedAppointments.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the Appointments CSV: " + e.getMessage());
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentsFilePath))) {
            for (String fileLine : updatedAppointments) {
                writer.write(fileLine);
                writer.newLine();
            }
            //System.out.println("Appointment status updated to 'Completed'.");
        } catch (IOException e) {
            System.out.println("Error writing to the Appointments file: " + e.getMessage());
            return;
        }
        
        //UPDATE DOCTOR SCHEDULE CSV
        List<String> updatedDoctorSchedule = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(doctorScheduleFilePath))) {
            String line;
            String header = reader.readLine(); // Read and keep the header
            if (header != null) {
                updatedDoctorSchedule.add(header);
            }

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns[0].trim().equals(selectedAppointment.getDoctorID()) &&
                    columns[2].trim().equals(selectedAppointment.getDate().format(dateFormatter)) &&
                    columns[3].trim().equals(selectedAppointment.getTime().format(timeFormatter))) {
                    // Update the schedule to 'Completed'
                    columns[4] = "Completed";  // Assuming the status is in the 4th column
                    line = String.join(",", columns);
                }
                updatedDoctorSchedule.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the Doctor schedule CSV: " + e.getMessage());
            return;
        }

        // Write updated schedule back to the Doctor schedule CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorScheduleFilePath))) {
            for (String fileLine : updatedDoctorSchedule) {
                writer.write(fileLine);
                writer.newLine();
            }
            //System.out.println("Doctor's schedule updated to 'Completed'.");
        } catch (IOException e) {
            System.out.println("Error writing to the Doctor schedule file: " + e.getMessage());
        }
        
        //UPDATE PATIENT_RECORDS CSV
        List<String> updatedPatientRecords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(patientRecordsFilePath))) {
            String line;
            String header = reader.readLine(); // Read and keep the header
            if (header != null) {
            	updatedPatientRecords.add(header);
            }

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(","); // Split each line into columns
                // Check matching criteria: DoctorID and PatientID
                if (columns[0].trim().equals(selectedAppointment.getDoctorID()) && 
                    columns[1].trim().equals(selectedAppointment.getPatientID())) {
                	//update medication
                	columns[11] = medicationName;
                    // Update the status column (last column in this case)
                    columns[12] = "Completed";
                    line = String.join(",", columns); // Recreate the line with updated values
                }
                updatedPatientRecords.add(line); // Add the updated line to the list
            }
        } catch (IOException e) {
            System.out.println("Error reading the Doctor schedule CSV: " + e.getMessage());
            return;
        }

        // Write updated schedule back to the Doctor schedule CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientRecordsFilePath))) {
            for (String fileLine : updatedPatientRecords) {
                writer.write(fileLine);
                writer.newLine();
            }
            //System.out.println("Doctor's schedule updated to 'Completed'.");
        } catch (IOException e) {
            System.out.println("Error writing to the Patient records file: " + e.getMessage());
        }
    }
}
    	