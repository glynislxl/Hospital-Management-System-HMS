package hms;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


//Medical Record Management: View Patient Medical Records & update patient medical records 
public class MedicalRecord {
	 private String patientID;
	 private String diagnosis;
	 private String prescriptions;
	 private String treatmentPlans;
	 private LocalDate date; 
	 private List<String> drugAllergies;
	 
	 public MedicalRecord(String patientID, String diagnosis, String treatmentPlans, String prescriptions, LocalDate date, List<String> drugAllergies) {
	        this.patientID = patientID;
	        this.diagnosis = diagnosis;
	        this.prescriptions = prescriptions;
	        this.treatmentPlans = treatmentPlans;
	        this.date = date;
	        this.drugAllergies = new ArrayList<>(drugAllergies);
	 }
	 
	 public String getPatientID() {
		 return patientID;
	 }
	 
	 public String getDiagnosis() {
		 return diagnosis;
	 }
	 
	 public String getPrescriptions() {
		 return prescriptions;
	 }
	 
	 public String getTreatmentPlans() {
		 return treatmentPlans;
	 }

	 public LocalDate getDate() {
		 return date;
	 }

	 public List<String> getDrugAllergies() {
		 return drugAllergies;
	 }

	 public void setDiagnosis (String diagnosis) {
		 this.diagnosis = diagnosis;
	 }
	 
	 public void setPrescriptions(String prescriptions) {
		 this.prescriptions = prescriptions;
	 }
	 
	 public void setTreatmentPlans(String treatmentPlans) {
		 this.treatmentPlans = treatmentPlans;
	 }
	 
	 public void setDate(LocalDate date) {
		 this.date = date;
	 }

	 
	 public void setDrugAllergies(List<String> allergies) {
		 this.drugAllergies = allergies;
	 }
	 
	 public String toString() {
		    return "------------------------------------" + "\n" +
		           "Date of Record: " + date +  "\n" +
		           "Diagnosis: " + diagnosis + "\n" +
		           "Prescriptions: " + prescriptions + "\n" +
		           "Treatment Plans: " + treatmentPlans;
	 }
}