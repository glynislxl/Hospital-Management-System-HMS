package hms;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Medication {
	private String name;
	private int quantity;
	private boolean lowStockAlert;
	private String replenishmentRequested;
	//Aprivate Inventory inventory = new Inventory();

	Scanner scanner = new Scanner(System.in);

	// Constructor
	public Medication(String name, int quantity, boolean lowStockAlert, String request) {
		this.name = name;
		this.quantity = quantity;
		this.lowStockAlert = lowStockAlert;
		this.replenishmentRequested = request;
		//this.inventory = new Inventory();
	}

	// Getters and setters for all properties
	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}

	public String toString() {
		return "Medication{" +
				"name='" + name + '\'' +
				", quantity=" + quantity + '\'' +
				"lowstockalert=" + lowStockAlert +
				'}';
	}

	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}

	public boolean isLowStockAlert() {
		return lowStockAlert;
	}

	public void setLowStockAlert(boolean t) {
		lowStockAlert = t;
	}

	/*public void updateLowStockAlert(String name) {
		System.out.println("Enter the name of the medication you would like to update: ");
		String name = scanner.nextLine();
		
		Medication med = inventory.findMedicationByName(name);

	    // Check if the medication was found
	    if (med == null) {
	        System.out.println("Medication with the name '" + name + "' not found.");
	        return;
	    }

	    // Ask for the new low stock alert status
	    System.out.println("If stock is low, enter 't' for true, else enter 'f' for false: ");
	    String alert = scanner.nextLine().toLowerCase();  // Convert to lowercase for easier comparison

	    // Validate the input and update the lowStockAlert
	    if (alert.equals("t")) {
	        med.setLowStockAlert(true);
	        System.out.println("Low stock alerted for " + name);
	    } else if (alert.equals("f")) {
	        med.setLowStockAlert(false);
	        System.out.println("Low stock unalerted for " + name);
	    } else {
	        System.out.println("Invalid input! Please enter 't' or 'f'.");
	        return;
	    }

	    // Update the CSV file
	    String filePath = "/Users/glyni/OneDrive/Desktop/uni/modules/Y2S1/SC2002/project/Medicine_List.csv";
	    List<String> updatedLines = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        
	        // Read the header first and add it to the updatedLines list
	        String header = reader.readLine();  // Read header
	        updatedLines.add(header);  // Keep the header

	        // Iterate through the lines in the file and update the relevant line
	        while ((line = reader.readLine()) != null) {
	            String[] columns = line.split(",");
	            String medName = columns[0];  // Assuming the name is in the first column

	            if (medName.equals(med.getName())) {
	                // Update the line with the new lowStockAlert value
	                updatedLines.add(med.getName() + "," + med.getQuantity() + "," + med.isLowStockAlert() + "\n");
	            } else {
	                updatedLines.add(line);  // Add unchanged line
	            }
	        }

	        // Overwrite the CSV file with the updated content
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	            for (String updatedLine : updatedLines) {
	                writer.write(updatedLine);
	                writer.newLine();
	            }
	            System.out.println("CSV file successfully updated.");
	        }

	    } catch (IOException e) {
	        System.out.println("Error updating the CSV file: " + e.getMessage());
	    }
	}*/

	public String isReplenishmentRequested() {
		return replenishmentRequested;
	}

	public void setreplenishmentRequest(String request) {
		replenishmentRequested = request;
	}
}