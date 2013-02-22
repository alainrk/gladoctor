/* Modulo di classe, classe ricetta medica.		*
 * Federico Montori								*/

package gladoctor;

import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.lang.Object;
import java.util.Date;
import java.io.*;

public class Prescription extends Attachment{
	
	private String jurisdiction;
	private String pat_name;
	private String pat_surname;
	private String pat_address;
	private String fiscal_code;
	private String area_code;
	private char priority;
	private String prescriptions;
	private String notes;
	private String exemption_code;
	private String pres_type;
	private int total;
	private String date;
	
	//Costruttori
	Prescription(){
		//Costruttore vuoto
	}
	Prescription(String falsename, DataSheet patient){ // Costruttore per la precompilazione
		super(falsename, "Prescription"); 
		this.jurisdiction = "GLaDOS Bologna - Project Gladoctor - Please Replace!"; // La giurisdizione deve essere cambiata a seconda del luogo
		this.pat_name = patient.getName();
		this.pat_surname = patient.getSurname();
		this.fiscal_code = patient.getFiscalCode();
		this.pat_address = patient.getAddress();
		this.area_code = patient.getAreaCode();
		this.exemption_code = patient.getExemptionCode();
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
	Prescription(int truename, String falsename, String type, File fd, String jurisdiction, String pat_name, String pat_surname, String pat_address, String fiscal_code, String area_code, char priority, String prescriptions, String notes, String exemption_code, String pres_type, int total, String date) {
		super(truename, falsename, type, fd);
		this.jurisdiction = jurisdiction;
		this.pat_name = pat_name;
		this.pat_surname = pat_surname;
		this.pat_address = pat_address;
		this.fiscal_code = fiscal_code;
		this.area_code = area_code;
		this.priority = priority;
		this.prescriptions = prescriptions;
		this.notes = notes;
		this.exemption_code = exemption_code;
		this.pres_type = pres_type;
		this.total = total;
		this.date = date;
	}
	
	//Funzioni Get
	public String getJurisdiction(){
		return this.jurisdiction;
	}
	public String getPatName(){
		return this.pat_name;
	}
	public String getPatSurname(){
		return this.pat_surname;
	}
	public String getPatAddress(){
		return this.pat_address;
	}
	public String getFiscalCode(){
		return this.fiscal_code;
	}
	public String getAreaCode(){
		return this.area_code;
	}
	public char getPriority(){
		return this.priority;
	}
	public String getPrescriptions(){
		return this.prescriptions;
	}
	public String getNotes(){
		return this.notes;
	}
	public String getExemptionCode(){
		return this.exemption_code;
	}
	public String getPresType(){
		return this.pres_type;
	}
	public int getTotal(){
		return this.total;
	}
	public String getDate(){
		return this.date;
	}
	
	//Funzioni Set
	public void setJurisdiction(String replace){
		this.jurisdiction = replace;
	}
	public void setPatName(String replace){
		this.pat_name = replace;
	}
	public void setPatSurname(String replace){
		this.pat_surname = replace;
	}
	public void setPatAddress(String replace){
		this.pat_address = replace;
	}
	public void setFiscalCode(String replace){
		this.fiscal_code = replace;
	}
	public void setAreaCode(String replace){
		this.area_code = replace;
	}
	public void setPriority(char replace){
		this.priority = replace;
	}
	public void setPrescriptions(String replace){
		this.prescriptions = replace;
	}
	public void setNotes(String replace){
		this.notes = replace;
	}
	public void setExemptionCode(String replace){
		this.exemption_code = replace;
	}
	public void setPresType(String replace){
		this.pres_type = replace;
	}
	public void setTotal(int replace){
		this.total = replace;
	}
	public void setDate(String replace){
		this.date = replace;
	}
	
	//Funzione che invia al sys l'aggiornamento della ricetta corrente per il database
	/*public boolean updateData(){
		super.updateData();
		return Sys_Examination.storePrescription(super.getTruename(), this.jurisdiction, this.pat_name, this.pat_surname, this.pat_address, this.fiscal_code, this.area_code, this.priority, this.prescriptions, this.notes, this.exemption_code, this.pres_type, this.total, this.date);
	}*/
	
	//Funzione che prende in input un file vuoto e lo riempie con i campi della ricetta corrente
	public File compile(File temp){
		FileOutputStream temp_stream;
		try{
			temp_stream = new FileOutputStream(temp);	//Percorso in uscita dell'output
		} catch(Exception e){
			return null;
		}
		PrintStream temp_printer = new PrintStream(temp_stream);	//Printer
		//Stampiamo tutti i campi della ricetta (già istanziata) sul file temp
		temp_printer.println("Giurisdizione: " + this.jurisdiction);
		temp_printer.println("Nome: " + this.pat_name);
		temp_printer.println("Cognome: " + this.pat_surname);
		temp_printer.println("Indirizzo: " + this.pat_address);
		temp_printer.println("Codice Fiscale: " + this.fiscal_code);
		temp_printer.println("Codice CAP: " + this.area_code);
		temp_printer.println("Priorita': " + this.priority);
		temp_printer.println("Prescrizioni: "+this.prescriptions);
		temp_printer.println("Note: " + this.notes);
		temp_printer.println("Codice d'Esenzione: " + this.exemption_code);
		temp_printer.println("Tipo di prescrizione: " + this.pres_type);
		temp_printer.println("Totale: " + this.total);
		temp_printer.println("");
		temp_printer.println("Data: " + this.date + "                                Firma del Medico");	
		
		return temp;
	}
}