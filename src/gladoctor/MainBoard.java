/* Modulo di classe, classe scheda principale.	*
 * Federico Montori								*/
package gladoctor;

import java.lang.String;
import java.util.Map;
import java.util.Date;

public class MainBoard{
	private String fiscal_code;
	private int invalidity_percentage;
	private Map<String,String> chronic_diseases; // Coppie nomemalattia / cause ed effetti
	private Map<String,String> permanent_medicines; // Coppie nomemedicina / principioattvo
	private Map<Integer,String> examinations; // Coppie ID / data
	
	//Costruttori
	MainBoard(){
	}
	MainBoard(String fiscal_code, int invalidity_percentage, Map<String,String> chronic_diseases, Map<String,String> permanent_medicines, Map<Integer,String> examinations){
		this.fiscal_code = fiscal_code;
		this.invalidity_percentage = invalidity_percentage;
		this.chronic_diseases = chronic_diseases;
		this.permanent_medicines = permanent_medicines;
		this.examinations = examinations;
	}
	//Funzioni Get
	public String getFiscalCode(){
		return this.fiscal_code;	
	}
	public int getInvalidityPercentage(){
		return this.invalidity_percentage;	
	}	
	public Map<String,String> getChronicDiseases(){
		return this.chronic_diseases;	
	}	
	public Map<String,String> getPermanentMedicines(){
		return this.permanent_medicines;	
	}	
	public Map<Integer,String> getExaminations(){
		return this.examinations;
	}
	
	//Funzioni Set
	public void setFiscalCode(String replace){
		this.fiscal_code = replace;
	}
	public void setInvalidityPercentage(int replace){
		this.invalidity_percentage = replace;
	}
	public void setChronicDiseases(Map<String,String> replace){
		this.chronic_diseases = replace;
	}
	public void setPermanentMedicines(Map<String,String> replace){
		this.permanent_medicines = replace;
	}
	public void setExaminations(Map<Integer,String> replace){
		this.examinations = replace;
	}
	
	//Funzione che invia al sys i dati della scheda principale corrente come nuovo record per il database
	public boolean sendData(){
		return Sys_MedicalRecord.storeNewMB(this.fiscal_code, this.invalidity_percentage, this.chronic_diseases, this.permanent_medicines);
	}
	
	//Funzione che invia al sys i dati della scheda principale corrente per aggiornarli sul database
	public boolean updateData(){
		return Sys_MedicalRecord.storeMB(this.fiscal_code, this.invalidity_percentage, this.chronic_diseases, this.permanent_medicines);
	}
	
	//Funzione che crea un'istanza di visita pronte per essere riempita
	public Examination createExamination(){
		Examination exam= new Examination();
		return exam;
	}
	
	//Funzione che, al click su una visita, ritorna la classe istanziata
	public Examination getExaminationById(int ID){
		Examination exam = new Examination();
		exam = Sys_Examination.extractExamination(ID, exam);
		return exam;
	}
	
	//Funzione che cancella da database una visita e tutti i suoi allegati
	public boolean deleteExamination(int ID){
		return Sys_Examination.removeExamination(ID); //il sys dovrà provvedere a cancellare tutti gli allegati e le ricette
	}
	//funzione che restituisce una lista di tutte le malattie
	public String[] listDiseases(){
		return Sys_MedicalRecord.getDiseaseList();
	}
	//funzione che restituisce tutte le madicine
	public String[] listMedicines(){
		return Sys_MedicalRecord.getMedicineList();
	}
	//funzione che crea un nuovo collegamento fra il paziente e la malattia cronica
	public boolean addDisease(String disease){
		return Sys_MedicalRecord.storeDisease(this.fiscal_code, disease);
	}
	//funzione che crea un nuovo collegamento fra il paziente e la malattia cronica
	public boolean addMedicine(String medicine){
		return Sys_MedicalRecord.storeMedicine(this.fiscal_code, medicine);
	}
	//funzione che cancella un collegamento fra il paziente e la malattia
	public boolean deleteDisease(String disease){
		return Sys_MedicalRecord.removeDisease(this.fiscal_code, disease);
	}
	//funzione che cancella un collegamento fra il paziente e la malattia
	public boolean deleteMedicine(String medicine){
		return Sys_MedicalRecord.removeMedicine(this.fiscal_code, medicine);
	}
}