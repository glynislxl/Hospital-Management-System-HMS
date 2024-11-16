package hms;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String patientID;  
    private String doctName;
    private String doctorID;
    private LocalDate date;
    private LocalTime time;         
    private AppointmentOutcomeRecord outcomeRecord; 
    private String status; 
    
    public enum AppointmentStatus {
        COMPLETED, CONFIRMED, CANCELLED, PENDING
    }

    public Appointment(String doctorID, String doctName, String patientID, LocalDate date, LocalTime time, String status) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.doctName = doctName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }
    
    public String getDoctName() {
    	return doctName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord;
    }
    
    public String getStatus() { 
    	return status; 
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }
    
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String toString() {
        return "Appointment {" +
                "patientID= '" + patientID + '\'' +
                ", doctorID= '" + doctorID + '\'' +
                ", Date= '" + date + '\'' +
                ", time= " + time +
                ", status= " + status + '\'' +
                '}';
    }
}