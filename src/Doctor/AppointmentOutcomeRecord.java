package hms;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * AppointmentOutcomeRecord class is to record the appointment outcome record including,
 * doctorID, patientID, date of appointment, time of appointment, service provided, prescribed medication, prescribed medication status and consultaion notes 
 */
public class AppointmentOutcomeRecord {
	private String doctID;
	private String patientID;
	private LocalDate date; 
	private LocalTime time;
    private String serviceProvided;
    private String medicationName;
    private String status;
    private String consultationNotes;   
    
    /**
     * AppointmentOutcomeRecord constructor
     * @param doctID doctor's ID
     * @param patientID patient's ID
     * @param date date of appointment
     * @param time time of appointment
     * @param serviceProvided service provided by doctor
     * @param medicationName medicaion prescribed by doctor
     * @param status status of appointment
     * @param consultationNotes consultation notes written by doctor
     */
    public AppointmentOutcomeRecord(String doctID, String patientID, LocalDate date, LocalTime time, String serviceProvided, String medicationName, String status, String consultationNotes) {
    	this.doctID = doctID;
    	this.patientID = patientID;
    	this.date = date;
    	this.time = time;
        this.serviceProvided = serviceProvided;
        this.medicationName = medicationName;
        this.status = status;
        this.consultationNotes = consultationNotes;
    }

    /**
     * getDoctID getter method is to return doctorID
     * @return doctorID
     */
	public String getDoctID()
    {
    	return doctID;
    }
    
	/**
	 * setDoctID setter method is to set doctorID
	 * @param doctID is the doctor's ID
	 */
    public void setDoctID(String doctID)
    {
    	this.doctID = doctID;
    }
    
    /**
     * getPatientID getter method is to return patientID
     * @return patientID
     */
    public String getPatientID()
    {
    	return patientID;
    }
    
    /**
     * setPatientID setter method is to set the patientID
     * @param patientID is the patient's ID
     */
    public void setPatientID(String patientID)
    {
    	this.patientID = patientID;
    }
    
    /**
     * getDate getter method is to return the date of the appointment
     * @return date of appointment
     */
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * setDate setter method is to set the date of appointment
     * @param date is the date of appointment
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * getTime getter method is to return the time of appointment
     * @return time of appointment
     */
    public LocalTime getTime() {
        return time;
    }
    
    /**
     * setTime setter method is to set the time of appointment
     * @param time is the time of appointment
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    /**
     * getServiceProvided getter method is to return the name of service provided
     * @return name of service provided
     */
    public String getServiceProvided() {
        return serviceProvided;
    }
    
    /**
     * getMedicationName getter method is to return the name of the prescribed medication
     * @return the name of the prescribed medication
     */
    public String getMedicationName() {
        return medicationName;
    }
    
    /**
     * getStatus getter method is to return the prescribed medication status whether it has been dispensed already
     * @return prescribed medication status
     */
    public String getStatus() { 
        return status;
    }
    
    /**
     * getConsultationNotes getter method is to return the consultation notes that the doctor wrote
     * @return consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }
    
    /**
     * setServiceProvided setter method is to set the name of service provided
     * @param serviceProvided is the name of service provided
     */
    public void setServiceProvided(String serviceProvided) {
    	this.serviceProvided = serviceProvided;
    }
    
    /**
     * setMedicationName setter method is to set the name of the prescribed medication
     * @param medicationName is the name of the prescribed medication
     */
    public void setMedicationName(String medicationName) {
    	this.medicationName = medicationName;
    }
    
    /**
     * setStatus setter method is to set the prescribed medication status whether it has been dispensed already
     * @param status is the prescribed medication status
     */
    public void setStatus(String status) { 
        this.status = status;
    }
    
    /**
     * setConsultationNotes setter method is to set the consultation notes that the doctor wrote
     * @param notes is the consulation notes
     */
    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }
}