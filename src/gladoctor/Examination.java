/* Modulo di classe, classe visita medica.		*
 * Federico Montori								*/
package gladoctor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class Examination{
	private int ID;			//Identificatore univoco di ogni visita, viene istanziato soltanto dopo l'estrazione dal database
	private String date;
	private String patient;
	private String doctor;
	private String disease;
	private String notes;
	private Map<Integer,String> prescriptions; 	//Lista di ricette identificate da coppie falso nome / vero nome
	private Map<Integer,String> attachments; 	//Lista di allegati (non ricette) identificati da coppie vero nome / falso nome
	static String fixedpath = "javafiles"; 		//Viene utilizzato per creare files temporanei, TODO cambiarlo anche a seconda del sistema operativo
	
	//Costruttori
	Examination(){
		//Costruttore di classe vuota
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		this.date = df.format(today);
	}
	Examination(int ID, String date, String patient, String doctor, String disease, String notes, Map<Integer,String> prescriptions, Map<Integer,String> attachments){
		this.ID = ID;
		this.date = date;
		this.patient = patient;
		this.doctor = doctor;
		this.disease = disease;
		this.notes = notes;
		this.prescriptions = prescriptions;
		this.attachments = attachments;
	}
	//Funzioni Get
	public int getID(){
		return this.ID;
	}
	public String getDate(){
		return this.date;
	}
	public String getPatient(){
		return this.patient;
	}
	public String getDoctor(){
		return this.doctor;
	}
	public String getDisease(){
		return this.disease;
	}
	public String getNotes(){
		return this.notes;
	}
	public Map<Integer,String> getPrescriptions(){
		return this.prescriptions;
	}
	public Map<Integer,String> getAttachments(){
		return this.attachments;
	}
	
	//Funzioni Set
	public void setID(int replace){
		this.ID = replace;
	}
	public void setDate(String replace){
		this.date = replace;
	}
	public void setPatient(String replace){
		this.patient = replace;
	}
	public void setDoctor(String replace){
		this.doctor = replace;
	}
	public void setDisease(String replace){
		this.disease = replace;
	}
	public void setNotes(String replace){
		this.notes = replace;
	}
	public void setPrescriptions(Map<Integer,String> replace){
		this.prescriptions = replace;
	}
	public void setAttachments(Map<Integer,String> replace){
		this.attachments = replace;
	}

	//Funzione che invia al sys i dati della visita corrente come nuovo record per il database (non altera le liste degli allegati e delle ricette)
	public int sendData(){
		return Sys_Examination.storeNewExamination(date,patient,doctor,disease,notes);
	}
	
	//Funzione che invia al sys l'aggiornamento della visita corrente per il database (non altera le liste degli allegati e delle ricette)
	public boolean updateData(){
		return Sys_Examination.storeExamination(this.ID, this.date,this.patient,this.doctor,this.disease,this.notes);
	}

	//Funzione che, al click su un elemento della lista degli allegati restituisce un oggetto Attachment
	public File getAttachmentById(int truename){
		Attachment attach = new Attachment();
		try {
			attach = Sys_Examination.extractAttachment(truename, attach);
			if (attach == null){
				return null;
			} else return attach.getFd();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	//Funzione che, al click su un elemento della lista delle ricette restituisce un oggetto Prescription
	public Prescription getPrescriptionById(int truename){
		Prescription prescr = new Prescription();
		return Sys_Examination.extractPrescription(truename, prescr);
	}	

	//Funzione che fa caricare il file al sys, il quale restituisce il nome vero; poi la funzione aggiunge la coppia alla lista degli allegati.
	public boolean uploadFile(String abspath, String falsename){
		String type = "File";
		int truename;
		try{
			if ((truename = Sys_Examination.storeNewAttachment(abspath,falsename, type, this.ID)) >= 0){
				//attachments.put(truename, falsename);
				return true;
			} else {
				return false;
			}
		} catch(Exception e){
			System.out.println(e);
			return false;
		}
	}

	//Funzione che crea una nuova ricetta prendendo in input una scheda anagrafica perchè venga precompilata
	public Prescription createPrescription(String falsename, DataSheet data){
		Prescription prescr = new Prescription(falsename, data);
		prescr.setType("Prescription");
		return prescr;
	}

	//Funzione che, una volta pronti i campi di una ricetta (ad eccezione di truename), crea un file, lo compila, ed invia tutto al database
	public boolean sendPrescriptionData(Prescription prescr){
		int truename;
		File temp = new File(fixedpath,"temporary"); // Per ora è solo un file descriptor
		try{
			temp.createNewFile(); // File fisico creato
		} catch (Exception e){
			return false;
		}
		prescr.setFd(temp); // Associo questo file all'oggetto ricetta
		prescr.compile(temp); // Riempio il file
		// Mandiamo i campi della superclasse e della classe al sys
		try{
			if ((truename = Sys_Examination.storeNewAttachment(prescr.getFd().getAbsolutePath(), prescr.getFalsename(), prescr.getType(), this.ID)) != 0) 
				if (Sys_Examination.storeNewPrescription(truename, prescr.getJurisdiction(), prescr.getPatName(), prescr.getPatSurname(), prescr.getPatAddress(), prescr.getFiscalCode(), prescr.getAreaCode(), prescr.getPriority(), prescr.getPrescriptions(), prescr.getNotes(), prescr.getExemptionCode(), prescr.getPresType(), prescr.getTotal(), prescr.getDate())){
				//prescriptions.put(truename, prescr.getFalsename()); // Aggiungiamo la coppia alla lista
			} else return false;
		} catch(Exception e){
			return false;
		}
		temp.delete(); // Ora che il file non serve più, lo cancelliamo 
		return true;
	} 
	
	//Funzione che invoca il sys per rimuovere dal server l'allegato o la prescrizione in input e, in caso di successo, rimuoverlo dalla lista
	public boolean deleteAttachment(int truename){
		return Sys_Examination.removeAttachment(truename);
	}
	
	//Funzione che invoca il sys per rimuovere dal server l'allegato o la prescrizione in input e, in caso di successo, rimuoverlo dalla lista
		public boolean deletePrescription(int truename){
			if ( Sys_Examination.removePrescription(truename) ){
				 return Sys_Examination.removeAttachment(truename);
			} else return false;	
		}
	
	
}