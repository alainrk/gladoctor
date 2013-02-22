/* Modulo di classe, classe scheda anagrafica.	*
 * Federico Montori								*/
package gladoctor;

import java.lang.String;
import java.util.Date;

public class DataSheet {
	private String fiscal_code;
	private String name;
	private String surname;
	private String date_born;
	private String place_born;
	private String address;
	private String area_code; // CAP
	private String city_living;
	private String occupation;
	private String telephone_number;
	private String exemption_code; // TODO documentarsi su cos'è
	
	//Costruttori
	DataSheet(){
		// Costruttore vuoto per il pre-riempimento del form
	}
	DataSheet(String fiscal_code, String name, String surname, String date_born, String place_born, String address, String area_code, String city_living, String occupation, String telephone_number, String exemption_code){
		this.fiscal_code = fiscal_code;
		this.name = name;
		this.surname = surname;
		this.date_born = date_born;
		this.place_born = place_born;
		this.address = address;
		this.area_code = area_code;
		this.city_living = city_living;
		this.occupation = occupation;
		this.telephone_number = telephone_number;
		this.exemption_code = exemption_code;
	}

	// Funzioni Get
	public String getFiscalCode(){
		return this.fiscal_code;
	}
	public String getName(){
		return this.name;
	}
	public String getSurname(){
		return this.surname;
	}
	public String getDateBorn(){
		return this.date_born;
	}
	public String getPlaceBorn(){
		return this.place_born;
	}
	public String getAddress(){
		return this.address;
	}
	public String getAreaCode(){
		return this.area_code;
	}
	public String getCityLiving(){
		return this.city_living;
	}
	public String getOccupation(){
		return this.occupation;
	}
	public String getTelephoneNumber(){
		return this.telephone_number;
	}
	public String getExemptionCode(){
		return this.exemption_code;
	}
	
	// Funzioni Set
	public void setFiscalCode(String replace){ // Questa funzione non dovrebbe mai essere chiamata direttamente
		this.fiscal_code = replace;
	}
	public void setName(String replace){
		this.name = replace;
	}
	public void setSurname(String replace){
		this.surname = replace;
	}
	public void setDateBorn(String replace){
		this.date_born = replace;
	}
	public void setPlaceBorn(String replace){
		this.place_born = replace;
	}
	public void setAddress(String replace){
		this.address = replace;
	}
	public void setAreaCode(String replace){
		this.area_code = replace;
	}
	public void setCityLiving(String replace){
		this.city_living = replace;
	}
	public void setOccupation(String replace){
		this.occupation = replace;
	}
	public void setTelephoneNumber(String replace){
		this.telephone_number = replace;
	}
	public void setExemptionCode(String replace){
		this.exemption_code = replace;
	}
	
	// Funzione che invia al sys i dati della scheda anagrafica corrente come nuovo record per il database
	public boolean sendData(){
		return Sys_MedicalRecord.storeNewDS(this.fiscal_code, this.name, this.surname, this.date_born, this.place_born, this.address, this.area_code, this.city_living, this.occupation, this.telephone_number, this.exemption_code);
	}
	
	// Funzione che invia al sys i dati della scheda anagrafica corrente per aggiornarli sul database
	public boolean updateData(){
		return Sys_MedicalRecord.storeDS(this.fiscal_code, this.name, this.surname, this.date_born, this.place_born, this.address, this.area_code, this.city_living, this.occupation, this.telephone_number, this.exemption_code);
	}
}
