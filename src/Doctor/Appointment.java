package hms;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Appointment class is to update the status of the appointment scheduled between a patient and a doctor
 */
public class Appointment {
    private String patientID;  
    private String doctName;
    private String doctorID;
    private LocalDate date;
    private LocalTime time;         
    private AppointmentOutcomeRecord outcomeRecord; 
    private String status; 
    
    /**
     * Constructor 
     * @param doctorID doctor's ID
     * @param doctName doctor's name
     * @param patientID patient's ID
     * @param date date of appointment
     * @param time time of appointment
     * @param status status of appointment
     */
    public Appointment(String doctorID, String doctName, String patientID, LocalDate date, LocalTime time, String status) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.doctName = doctName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    /**
     * getPatientID getter method returns the patientID
     * @return patientID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * getDoctorID getter method returns the doctorID
     * @return doctorID
     */
    public String getDoctorID() {
        return doctorID;
    }
    
    /**
     * getDoctName getter method returns the doctor's name
     * @return doctor's name
     */
    public String getDoctName() {
    	return doctName;
    }

    /**
     * getDate getter method returns the date of appointment
     * @return date of appointment
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * getTime getter method returns the time of appointment
     * @return time of appointment
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * getOutcomeRecord getter method returns the appointment outcome record
     * @return appointment outcome record
     */
    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord;
    }
    
    /**
     * getStatus getter method returns the status of the appointment (eg. Scheduled/Confirmed/Completed/Cancelled)
     * @return status of appointment
     */
    public String getStatus() { 
    	return status; 
    }

    /**
     * setDate setter method is to set the date of appointment 
     * @param date is the date of appointment
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * setTime setter method is to set the time of the appointment
     * @param time is the time of appointment
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * setOutcomeRecord setter method is to set the appointment outcome record
     * @param outcomeRecord is the appointment outcome record
     */
    public void setOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }
    
    /**
     * updateStatus setter method is to set the updated appointment status
     * @param newStatus is the updated appointment status
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}