package hms;

/**
 * Medication class stores the medication name, quantity, low stock alert and replenishment request of
 * the medications that exits in the medication inventory
 */
public class Medication {
	private String name;
	private int quantity;
	private boolean lowStockAlert;
	private String replenishmentRequested;

	/**
	 * Medication constructor
	 * @param name name of medication
	 * @param quantity quantity of medication
	 * @param lowStockAlert low stock alert
	 * @param request replenishment request
	 */
	public Medication(String name, int quantity, boolean lowStockAlert, String request) {
		this.name = name;
		this.quantity = quantity;
		this.lowStockAlert = lowStockAlert;
		this.replenishmentRequested = request;
	}

	/**
	 * getName getter method returns the name of the medication
	 * @return name of medication
	 */
	public String getName() {
		return name;
	}

	/**
	 * getQuantity getter method returns the quantity of the medication
	 * @return quantity of medication
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * setName setter method sets the name of the medication
	 * @param name medication name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * updateQuantity setter method sets the new quantity of the medication
	 * @param newQuantity medication quantity
	 */
	public void updateQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}

	/**
	 * setQuantity setter method sets the initial quantity of the medication
	 * @param newQuantity medication quantity
	 */
	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}

	/**
	 * isLowStockAlert getter method returns the boolean value of whether the medication is low in stock
	 * @return whether it is low stock or not
	 */
	public boolean isLowStockAlert() {
		return lowStockAlert;
	}

	/**
	 * setLowStockAlert setter method sets the low stock alert of the medication
	 * @param t low stock value
	 */
	public void setLowStockAlert(boolean t) {
		this.lowStockAlert = t;
	}

	/**
	 * isReplenishmentRequested getter method returns the request for replenishment
	 * @return replenishment request
	 */
	public String isReplenishmentRequested() {
		return replenishmentRequested;
	}

	/**
	 * setReplenishmentRequested setter method sets the request for replenishment
	 * @param request replenishment request
	 */
	public void setreplenishmentRequest(String request) {
		this.replenishmentRequested = request;
	}
	
}