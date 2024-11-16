package hms;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentOutcomeRecord {
	private String doctID;
	private String patientID;
	private LocalDate date; 
	private LocalTime time;
    private String serviceProvided;
    private String medicationName;
    private String status;
    private String consultationNotes;   
    
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

	public String getDoctID()
    {
    	return doctID;
    }
    
    public void setDoctID(String doctID)
    {
    	this.doctID = doctID;
    }
    
    public String getPatientID()
    {
    	return patientID;
    }
    
    public void setPatientID(String patientID)
    {
    	this.patientID = patientID;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public String getServiceProvided() {
        return serviceProvided;
    }
    
    public String getMedicationName() {
        return medicationName;
    }
    
    public String getStatus() { 
        return status;
    }
    
    public String getConsultationNotes() {
        return consultationNotes;
    }
    
    public void setServiceProvided(String serviceProvided) {
    	this.serviceProvided = serviceProvided;
    }
    
    public void setMedicationName(String medicationName) {
    	this.medicationName = medicationName;
    }
    
    public void setStatus(String status) { 
        this.status = status;
    }
    
    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }

    public String toString() {
        return "Date: " + date + ", Service: " + serviceProvided + ", Medication: " + medicationName + " (" + status + "), Notes: " + consultationNotes;
    }
}