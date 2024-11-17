package hms;

import java.time.LocalDate;

/**
 * MedicalRecord class allows doctors to view Patient Medical Records and update Patient Medical Records 
 */
public class MedicalRecord {
	 private String patientID;
	 private String diagnosis;
	 private String prescriptions;
	 private String treatmentPlans;
	 private LocalDate date;
	 
	 /**
	  * Medical record constructor
	  * @param patientID patient's ID
	  * @param diagnosis patient's diagnosis
	  * @param treatmentPlans patient's treatment plans
	  * @param prescriptions patient's prescribed medications
	  * @param date patient's date of appointment
	  */
	 public MedicalRecord(String patientID, String diagnosis, String treatmentPlans, String prescriptions, LocalDate date) {
	        this.patientID = patientID;
	        this.diagnosis = diagnosis;
	        this.prescriptions = prescriptions;
	        this.treatmentPlans = treatmentPlans;
	        this.date = date;
	 }
	 
	 /**
	  * getPatientID getter method returns the patientID
	  * @return patientID
	  */
	 public String getPatientID() {
		 return patientID;
	 }
	 
	 /**
	  * getDiagosis getter method returns the diagnosis the doctor have diagnosed
	  * @return diagnosis
	  */
	 public String getDiagnosis() {
		 return diagnosis;
	 }
	 
	 /**
	  * getPrescriptions getter method returns the medications the doctor have prescribed
	  * @return prescribed medications
	  */
	 public String getPrescriptions() {
		 return prescriptions;
	 }
	 
	 /**
	  * getTreatmentPlans getter method returns the treatment plans the doctor have done on the patient
	  * @return treatment plans
	  */
	 public String getTreatmentPlans() {
		 return treatmentPlans;
	 }

	 /**
	  * getDate getter method returns the date of appointment
	  * @return date of appointment
	  */
	 public LocalDate getDate() {
		 return date;
	 }

	 /**
	  * setDiagnosis setter method sets the diagnosis the doctor have diagnosed the patient
	  * @param diagnosis diagonsis by doctor
	  */
	 public void setDiagnosis (String diagnosis) {
		 this.diagnosis = diagnosis;
	 }
	 
	 /**
	  * setPrescriptions setter method sets the medications the doctor have prescribed
	  * @param prescriptions prescribed medications from doctor
	  */
	 public void setPrescriptions(String prescriptions) {
		 this.prescriptions = prescriptions;
	 }
	 
	 /**
	  * setTreatmentPlans setter method sets the treatment plans the doctor has
	  * @param treatmentPlans treatment plans from doctor
	  */
	 public void setTreatmentPlans(String treatmentPlans) {
		 this.treatmentPlans = treatmentPlans;
	 }
	 
	 /**
	  * setDate setter method sets the date of appointment
	  * @param date date of appointment
	  */
	 public void setDate(LocalDate date) {
		 this.date = date;
	 }
}