package hms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.util.*;

public class Pharmacist {

	//protected List<AppointmentOutcomeRecord> appointmentOutcomeRecords; 
    private String pharmacistID;
    private String name;
    private String contactNumber;

    // Constructor
    public Pharmacist() {

    }

	// 1. View Appointment Outcome Record for a patient to fulfill medications prescriptions orders from doctors
    public void viewAppointmentRecord(){
    	String apptPath1 = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointment_records.csv";
    	
    	List<String> records = new ArrayList<>();
    	
    	// Read and process appointment records
        try (BufferedReader reader = new BufferedReader(new FileReader(apptPath1))) {
            String line;

            // Skip the header line
            String header = reader.readLine();

            // Read the appointment records and store them in a list
            while ((line = reader.readLine()) != null) {
            	records.add(line);
            }
            
            System.out.println("\nAppointment Records:");
            for (int i = 0; i < records.size(); i++) {
                String record = records.get(i);
                String[] fields = record.split(",");
                System.out.println((i + 1) + ": DoctorID: " + fields[0] + ", PatientID: " + fields[1] + ", Date: " + fields[2]
                        + ", Time: " + fields[3] + ", Type of Service: " + fields[4] + ", Prescribed Medication: " + fields[5]
                        + ", Medication Status: " + fields[6] + ", Consultation notes: " + fields[7]);
            }
            System.out.println("\n");
        } catch (IOException e) {
            System.err.println("Error reading the appointment records: " + e.getMessage());
        }
    }
    
    
    // 2. Update Prescription Status
    public void updatePrescriptionStatus() {
        // Define the file paths for appointment and medicine inventory
        String apptPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Appointment_records.csv";
        String medPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";

        // Lists to store the appointment records and updated records
        List<String> appointmentRecords = new ArrayList<>();
        List<String> updatedRecords = new ArrayList<>();
        List<String> updatedInventoryRecords = new ArrayList<>();

        String prescribedMedication = null;
        String medicationStatus = null;

        // Read and process appointment records
        try (BufferedReader reader = new BufferedReader(new FileReader(apptPath))) {
            String line;

            // Skip the header line
            String header = reader.readLine();
            updatedRecords.add(header);

            // Read the appointment records and store them in a list
            while ((line = reader.readLine()) != null) {
                appointmentRecords.add(line);
                String[] fields = line.split(","); // Assuming CSV is comma-separated

                // Extract the prescribed medication and status
                prescribedMedication = fields[5]; // Prescribed medication is in column 5
                medicationStatus = fields[6]; // Medication status is in column 6

                // Add each record as is for later update if necessary
                updatedRecords.add(line);
            }

            // Start loop to display records and handle user input
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Display initial appointment records starting from index 1
                System.out.println("\nAppointment Records:");
                for (int i = 0; i < appointmentRecords.size(); i++) {
                    String record = appointmentRecords.get(i);
                    String[] fields = record.split(",");
                    System.out.println((i + 1) + ": DoctorID: " + fields[0] + ", PatientID: " + fields[1] + ", Date: " + fields[2]
                            + ", Time: " + fields[3] + ", Type of Service: " + fields[4] + ", Prescribed Medication: " + fields[5]
                            + ", Medication Status: " + fields[6] + ", Consultation notes: " + fields[7]);
                }

                // Prompt the user to enter an index or 'exit' to stop
                System.out.println("\nEnter the index of the appointment to fulfill the prescribed medication or type 'exit' to quit:");
                String userInput = scanner.nextLine();

                // Check if the user wants to exit
                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Exiting the program.\n");
                    break; // Exit the loop and method
                }

                try {
                    // Convert input to integer index (adjusting for 1-based indexing)
                    int selectedIndex = Integer.parseInt(userInput) - 1;

                    if (selectedIndex >= 0 && selectedIndex < appointmentRecords.size()) {
                        // Get the selected appointment record
                        String selectedRecord = appointmentRecords.get(selectedIndex);
                        String[] fields = selectedRecord.split(",");
                        prescribedMedication = fields[5].trim();
                        medicationStatus = fields[6].trim();

                        // Only update the status if it's "Pending"
                        if ("Pending".equalsIgnoreCase(medicationStatus)) {
                            // Update medication status to "Fulfilled"
                            fields[6] = "Dispensed"; // Update medication status
                            updatedRecords.set(selectedIndex + 1, String.join(",", fields)); // Update the specific record

                            // Write the updated appointment records back to the file
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(apptPath))) {
                                for (String record : updatedRecords) {
                                    writer.write(record);
                                    writer.newLine();
                                }
                                //System.out.println("Appointment status updated successfully.");
                            }

                            // Update the inventory for the prescribed medication
                            try (BufferedReader inventoryReader = new BufferedReader(new FileReader(medPath))) {
                                String inventoryLine;
                                updatedInventoryRecords.add(inventoryReader.readLine()); // Add header

                                // Process inventory records and reduce quantity by 1 for the prescribed medication
                                while ((inventoryLine = inventoryReader.readLine()) != null) {
                                    String[] columns = inventoryLine.split(",");
                                    String medName = columns[0].trim(); // Medicine Name
                                    int quantity = Integer.parseInt(columns[1].trim()); // Quantity

                                    // Reduce quantity by 1 for the prescribed medication
                                    if (prescribedMedication.equalsIgnoreCase(medName)) {
                                        quantity--; // Reduce by 1
                                    }
                                    
                                    // Check if stock is below 50 and update lowStock alert
                                    boolean lowStock = quantity < 50;

                                    columns[1] = String.valueOf(quantity); // Update quantity
                                    columns[2] = String.valueOf(lowStock); // Update low stock flag
                                    updatedInventoryRecords.add(String.join(",", columns)); // Add updated record
                                }

                                // Write the updated inventory back to the file
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(medPath))) {
                                    for (String record : updatedInventoryRecords) {
                                        writer.write(record);
                                        writer.newLine();
                                    }
                                    //System.out.println("Medicine inventory updated successfully.");
                                }
                            } catch (IOException | NumberFormatException e) {
                                System.err.println("Error reading or updating the Medicine List CSV: " + e.getMessage());
                            }

                            // Remove the fulfilled appointment from the list
                            appointmentRecords.remove(selectedIndex);
                            System.out.println("The appointment's prescribed medication has been fulfilled.\n");

                        } else {
                            System.out.println("This appointment's prescribed medication is already fulfilled.\n");
                        }
                    } else {
                        System.out.println("Invalid index selected. Please try again.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid index or type 'exit' to quit.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading or writing the appointment records: " + e.getMessage());
        }
    }
    

    // 3. View Medication Inventory
    public void viewMedicationInventory() {
    	String medPath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
    
    	List<String> inventoryRecords = new ArrayList<>();
    
    	try (BufferedReader inventoryReader = new BufferedReader(new FileReader(medPath))) {
            String inventoryLine;
            inventoryReader.readLine(); //header
            
            // Process inventory records and reduce quantity by 1 for the prescribed medication
            while ((inventoryLine = inventoryReader.readLine()) != null) {
            	inventoryRecords.add(inventoryLine);
            }
            
            System.out.println("\nInventory of Medications:");
            for (int i = 0; i < inventoryRecords.size(); i++) {
                String record = inventoryRecords.get(i);
                String[] fields = record.split(",");
                System.out.println((i + 1) + ": Medicine Name: " + fields[0] + ", Quantity: " + fields[1] + ", Low Stock Alert: " + fields[2]  + ", Replenishment request: " + fields[3]);
            }
            System.out.println("\n");
        } catch (IOException e) {
            System.err.println("Error reading the medicine list records: " + e.getMessage());
        }

    
    }

    // 4. Submit Replenishment Request to administrators when stock levels are low
    public void submitReplenishmentRequest(){
    	String medPath1 = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
        
        List<String[]> inventoryData = new ArrayList<>(); // To store inventory records
        List<Integer> lowStockIndices = new ArrayList<>(); // To track low stock indices
        
        try (BufferedReader inventoryReader = new BufferedReader(new FileReader(medPath1))) {
            String inventoryLine;
            String header = inventoryReader.readLine(); // Read header
            
            // Process inventory records
            while ((inventoryLine = inventoryReader.readLine()) != null) {
                String[] columns = inventoryLine.split(",");
                inventoryData.add(columns); // Store the inventory data for later use
            }
            
            // Display the medicines with low stock
            Scanner scanner = new Scanner(System.in);
            System.out.println("List of Medicines with Low Stock:");
            int index = 1;
            
            // Iterate over the inventory data to find medicines with low stock
            for (int i = 0; i < inventoryData.size(); i++) {
                String[] record = inventoryData.get(i);
                boolean lowStock = Boolean.parseBoolean(record[2].trim()); // Check low stock status
                
                // Only display medicines with low stock
                if (lowStock) {
                    System.out.println(index + ". Medicine name: " + record[0] + ", Quantity: " + record[1] + ", Low Stock: " + record[2] + ", Request Status: " + record[3]);
                    lowStockIndices.add(i); // Add the index of the low-stock item
                    index++;
                }
            }
            
            // Let user choose the index to submit replenishment request
            while (true) {
                System.out.print("\nEnter the index of the medicine to submit for replenishment (or type 'exit' to stop): ");
                String input = scanner.nextLine().trim();
                
                if (input.equalsIgnoreCase("exit")) {
                    break; // Exit the loop if user types "exit"
                }
                
                try {
                    int selectedIndex = Integer.parseInt(input) - 1;
                    
                    // Ensure selected index is valid and corresponds to a low-stock item
                    if (selectedIndex >= 0 && selectedIndex < lowStockIndices.size()) {
                        int inventoryIndex = lowStockIndices.get(selectedIndex); // Get the corresponding index in inventoryData
                        String[] record = inventoryData.get(inventoryIndex);
                        record[3] = "Submitted"; // Update request status to "Submitted"
                        
                        // Inform the user about the submitted request
                        System.out.println("Replenishment request submitted for " + record[0]);

                        // Update the record in the inventoryData list
                        inventoryData.set(inventoryIndex, record);

                        // Remove this item from the lowStockIndices list to avoid re-selection
                        lowStockIndices.remove(selectedIndex);
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid index number or 'exit' to quit.");
                }
            }
            
            // Write the updated records back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(medPath1))) {
                writer.write(header);  // Write the header
                writer.newLine();
                
                // Write all updated records back to the CSV file
                for (String[] record : inventoryData) {
                    writer.write(String.join(",", record)); // Write each record
                    writer.newLine();
                }
                System.out.println("Replenishment requests have been submitted where necessary.\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing to the medicine list records: " + e.getMessage());
        }
    }
    
    /*
    // Getters and Setters
    public String getPharmacistID() {
        return pharmacistID;
    }

    public void setPharmacistID(String pharmacistID) {
        this.pharmacistID = pharmacistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }*/
}

