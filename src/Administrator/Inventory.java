package hms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Inventory class stores the list of medications in the inventory
 */
public class Inventory extends Medication {
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * medications is a list
	 */
	public List<Medication> medications;

	/**
	 * Inventory constructor
	 */
	public Inventory()
	{
		super("Medication",0,false,"-");
		medications = new ArrayList<>();
	}
	
	/**
	 * Inventory constructor
	 * @param name medication name
	 * @param quantity medication quantity
	 * @param lowStockAlert low stock alert
	 * @param request replenishment request
	 */
	public Inventory(String name, int quantity, boolean lowStockAlert, String request) {
		super(name,quantity,lowStockAlert,request);
		medications = new ArrayList<>();
	}

	/**
	 * addMedication method adds a new medication to the medication inventory
	 */
	public void addMedication() {
		String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Medicine_List.csv";

		String name = "";
	    boolean isValidName = false;

	    // Loop until a unique name is entered or the user chooses to exit
	    while (!isValidName) {
	        System.out.println("Enter the name of the medication (or type 'exit' to cancel): ");
	        name = sc.nextLine().trim();

	        // Check if the user wants to exit
	        if (name.equalsIgnoreCase("exit")) {
	            System.out.println("Operation canceled. No medication was added.\n");
	            return;  // Exit the method if user types "exit"
	        }

	        // Check if medication with the same name already exists in the CSV file
	        boolean exists = false;
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            reader.readLine(); // Skip the header line

	            while ((line = reader.readLine()) != null) {
	                String[] columns = line.split(",");
	                if (columns.length > 0 && columns[0].equalsIgnoreCase(name)) {
	                    exists = true;
	                    break;  // Exit the loop if the name already exists
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the CSV file: " + e.getMessage());
	        }

	        if (exists) {
	            // If the medication already exists, inform the user and ask for a new name
	            System.out.println("This medication already exists in the inventory. Please enter a different name.");
	        } else {
	            // If the medication name is unique, break the loop
	            isValidName = true;
	        }
	    }

	    // Now proceed to gather the other details and add the medication
	    System.out.println("Enter the amount of the medication: ");
	    int quantity = sc.nextInt();
	    sc.nextLine();  // Consume the newline character after the integer input

	    // Set low stock alert based on the quantity
	    boolean alert = quantity < 50; // If quantity is less than 50, set alert to true, otherwise false

	    // Create the new medication and add it to the list
	    Medication med = new Medication(name, quantity, alert, "-"); // Default replenishment request is "-"
	    medications.add(med);

	    // Update the CSV file by appending the new medication details
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // 'true' to append
	        writer.write(name + "," + quantity + "," + alert + "," + "-");
	        writer.newLine(); // Add a new line at the end of the entry
	    } catch (IOException e) {
	        System.out.println("An error occurred while updating the CSV file.");
	        e.printStackTrace();
	    }
		System.out.println("Medication Name " + name + " added. \n");
	}

	/**
	 * removeMedication method is to remove the medication from the medication inventory
	 */
	public void removeMedication() {
		// Display existing medications from the file
	    System.out.println("Existing Medications:");

	    String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Medicine_List.csv";
	    List<Medication> medicationListFromFile = new ArrayList<>();

	    // Read medications from the file and display them
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine();  // Skip the header line

	        int index = 1;  // Index starts at 1 for user-friendly display
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length >= 4) {
	                String name = columns[0];
	                int quantity = Integer.parseInt(columns[1]);
	                boolean lowStockAlert = Boolean.parseBoolean(columns[2]);
	                String replenishmentRequested = columns[3].trim();

	                // Create a new Medication object and add it to the list
	                Medication medication = new Medication(name, quantity, lowStockAlert, replenishmentRequested);
	                medicationListFromFile.add(medication);

	                // Display the medication details
	                System.out.println(index + ". " + name + " | Quantity: " + quantity + " | Low Stock Alert: " + lowStockAlert + " | Replenishment Request: " + replenishmentRequested);
	                index++;
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	    }

	    // Let user input the index of the medication to remove
	    System.out.print("\nEnter the index of the medication to remove (or type 'exit' to cancel): ");
	    String input = sc.nextLine().trim();

	    if (input.equalsIgnoreCase("exit")) {
	        System.out.println("Removal process canceled.\n");
	        return;
	    }

	    try {
	        int indexToRemove = Integer.parseInt(input) - 1;  // Convert to 0-based index
	        if (indexToRemove >= 0 && indexToRemove < medicationListFromFile.size()) {
	            // Get the medication to be removed
	            Medication medicationToRemove = medicationListFromFile.get(indexToRemove);
	            medicationListFromFile.remove(indexToRemove);
	            
	            System.out.println("Medication " + medicationToRemove.getName() + " removed.\n");

	            // Rewrite the CSV file by including all remaining medications
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	                // Write the header
	                writer.write("Name,Quantity,LowStockAlert,ReplenishmentRequest\n");

	                // Re-write the medications in the list, excluding the one removed
	                for (Medication med : medicationListFromFile) {
	                    writer.write(med.getName() + "," + med.getQuantity() + "," + med.isLowStockAlert() + "," + med.isReplenishmentRequested() + "\n");
	                }

	            } catch (IOException e) {
	                System.out.println("Error updating the CSV file: " + e.getMessage());
	            }

	        } else {
	            System.out.println("Invalid index. Please try again.");
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input. Please enter a valid index number.");
	    }
	}
	
	/**
	 * updateMedicationQuantity is to update the quantity of the medication in the medication inventory
	 */
	public void updateMedicationQuantity() {
		// Load medications from the file
	    String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Medicine_List.csv";
	    List<Medication> medicationListFromFile = new ArrayList<>();

	    // Read medications from the file and display them
	    System.out.println("Existing Medications:");
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine(); // Skip the header line

	        int index = 1; // Index starts at 1 for user-friendly display
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length >= 4) {
	                String name = columns[0];
	                int quantity = Integer.parseInt(columns[1]);
	                boolean lowStockAlert = Boolean.parseBoolean(columns[2]);
	                String replenishmentRequested = columns[3];

	                // Create a new Medication object and add it to the list
	                Medication medication = new Medication(name, quantity, lowStockAlert, replenishmentRequested);
	                medicationListFromFile.add(medication);

	                // Display the medication details
	                System.out.println(index + ". " + name + " | Quantity: " + quantity + " | Low Stock Alert: " + lowStockAlert + " | Replenishment Request: " + replenishmentRequested);
	                index++;
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the CSV file: " + e.getMessage());
	        return; // Exit if file read fails
	    }

	    // Prompt user to select a medication by index
	    System.out.print("\nEnter the index of the medication quantity to update (or type 'exit' to cancel): ");
	    String input = sc.nextLine().trim();

	    if (input.equalsIgnoreCase("exit")) {
	        System.out.println("Update process canceled.\n");
	        return;
	    }

	    try {
	        int indexToUpdate = Integer.parseInt(input) - 1; // Convert to 0-based index
	        if (indexToUpdate >= 0 && indexToUpdate < medicationListFromFile.size()) {
	            // Get the medication to update
	            Medication medicationToUpdate = medicationListFromFile.get(indexToUpdate);
	            System.out.println("You selected: " + medicationToUpdate.getName());

	            // Ask user for the new quantity
	            System.out.print("Enter the new quantity for " + medicationToUpdate.getName() + ": ");
	            int newQuantity = sc.nextInt();
	            medicationToUpdate.setQuantity(newQuantity); // Update the medication's quantity

	            // Update the Low Stock Alert based on the new quantity
	            medicationToUpdate.setLowStockAlert(newQuantity < 50);

	            // Rewrite the updated medications back to the file
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	                // Write the header
	                writer.write("Name,Quantity,LowStockAlert,ReplenishmentRequest\n");

	                // Write all medications, including the updated one
	                for (Medication med : medicationListFromFile) {
	                    writer.write(med.getName() + "," + med.getQuantity() + "," + med.isLowStockAlert() + "," + med.isReplenishmentRequested() + "\n");
	                }

	                System.out.println("Updated " + medicationToUpdate.getName() + " quantity to " + newQuantity + "\n");
	            } catch (IOException e) {
	                System.out.println("Error updating the CSV file: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Invalid index. Please try again.");
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input. Please enter a valid index number.");
	    }
	}

	/**
	 * loadMedications method is to load the medications in the Medicine_list csv that exists in the medication inventory
	 */
	public void loadMedications()
	{
		String filePath = "/Users/glyni/OneDrive/Desktop/SC2002 project/Medicine_List.csv"; 
		
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line

            // Iterate through each line in the CSV file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Skip lines with insufficient data
                if (data.length < 4) {
                    continue; // Skip this iteration and go to the next line
                }
                
                // Parse the medication details
                String medicationName = data[0].trim();
                int quantity = Integer.parseInt(data[1].trim());
                boolean lowStockAlert = Boolean.parseBoolean(data[2].trim());
                String request = data[3].trim();
                
                //add to medications list
                // Check if the medication already exists
                if (findMedicationByName(medicationName) == null) {
                	Medication med = new Medication(medicationName, quantity, lowStockAlert,request);
                    medications.add(med);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * viewInventory returns the list of medications that exists in the medication inventory
	 * @return the list of medications
	 */
	public List<Medication> viewInventory() {
		return medications;
    }

	/**
	 * findMedicationByName finds the medication that exists in the medication inventory by its name
	 * @param name is the medication name
	 * @return the medication if found
	 */
	public Medication findMedicationByName(String name) {
		for (Medication medication : medications) {
			if (medication.getName().equals(name)) {
				return medication; // Return the medication if found
			}
		}
		return null; // Return null if not found
	}
}