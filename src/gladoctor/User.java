/* Modulo di classe, classe utente.			*
 * Federico Montori							*/

package gladoctor;

import java.lang.String;
import java.util.Map;

public class User {
	private String username;
	private String password;
	private String fiscal_code;
	private String name;
	private String surname;
	private String substitute_of;
	private boolean isAdmin;
	
	// Costruttori
	User(){
		
	}
        
	User(String username, String password, String fiscal_code, String name, String surname, String substitute_of, boolean isAdmin){
		this.username = username;
		this.password = password;
		this.fiscal_code = fiscal_code;
		this.name = name;
		this.surname = surname;
		this.substitute_of = substitute_of;
		this.isAdmin = isAdmin;
	}
	
	//Funzioni Get
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public String getFiscalCode(){
		return this.fiscal_code;
	}
	public String getName(){
		return this.name;
	}
	public String getSurname(){
		return this.surname;
	}
	public String getSubstituteOf(){
		return this.substitute_of;
	}
	public boolean isAdmin(){
		return this.isAdmin;
	}
	
	//Funzioni Set
	public void setUsername(String replace){
		this.username = replace;
	}
	public void setPassword(String replace){
		this.password = replace;
	}
	public void setFiscalCode(String replace){
		this.fiscal_code = replace;
	}
	public void setName(String replace){
		this.name = replace;
	}
	public void setSurname(String replace){
		this.surname = replace;
	}
	public void setSubstituteOf(String replace){
		this.substitute_of = replace;
	}
	public void setAdmin(boolean replace){
		this.isAdmin = replace;
	}	
	
	//Funzione che crea un nuovo oggetto utente pronto per essere riempito
	public User createUser(){
		User doctor = new User();
		return doctor;
	}

	//Funzione che cancella un utente dal database
	public boolean deleteUser(String fiscode){
		return Sys_User.removeUser(fiscode);
	}

	//Funzione che restituisce tutti i dati di un utente istanziandolo
	public User getUserById(String code){ 
		User temp = new User();
		return Sys_User.extractUser(code, temp);
	} 
	
	//Funzione che invia al sys i dati dell'utente corrente come nuovo utente per il database
	public boolean sendData(){
		return Sys_User.storeNewDoctor(this.username, this.password, this.fiscal_code, this.name, this.surname, this.substitute_of, this.isAdmin);
	}
	
	//Funzione che invia al sys l'aggiornamento dell'utente corrente per il database
	public boolean updateData(){
		return Sys_User.storeDoctor(this.username, this.password, this.fiscal_code, this.name, this.surname, this.substitute_of, this.isAdmin);
	}
	
	//Funzione che invia un cambio password al sys e ritorna 1 in caso di successo
	public boolean changePwd(String old_pwd, String new_pwd){
		return Sys_User.switchPwd(this.username, old_pwd, new_pwd);
	}
	
	// Funzione che crea una nuova cartella clinica (vuota)
	public MedicalRecord createMR(){
		return new MedicalRecord();
	}
	
	// Funzione che chiama una ricerca di cartelle cliniche da sys per nome e cognome. Restituisce una lista di coppie codice fiscale / nome+cognome
	public Map<String,String> searchMR(String qsurname){
		return Sys_MedicalRecord.extractMRSearch(this.fiscal_code, this.substitute_of, qsurname);
	}
	
	// Funzione che, al click su una cartella clinica restituisce tutti i suoi dati istanziando una classe (deve inserire i dati anche di DS e MB)
	public MedicalRecord getMRById(String code){ 
		MedicalRecord MR = new MedicalRecord();
        DataSheet DS = new DataSheet();
        MainBoard MB = new MainBoard();
        try{
        	Sys_MedicalRecord.extractMR(MR, DS, MB, code);
        } catch (Exception e){
        	return null;
        }
		return MR;
	}
	
	// Funzione che cancella dal database una cartella clinica
	public boolean deleteMR(MedicalRecord MR){
		return Sys_MedicalRecord.removeMR(MR.getFiscalCode()); // IMPORTANTE il sys deve eliminare anche DS e MB	
	}	

	// Funzione che restituisce una lista di coppie codice fiscale / username
	public Map<String,String> getUserList(){
		return Sys_User.extractDocList();
	}
	
	// Funzione che invoca il sys per settare il campo substitute_of di un certo doctor all'utente corrente
	public boolean enableSubstitute(String doctor){
                    return Sys_User.setSubstitute(this.fiscal_code, doctor);
	}
	// Estensione che invoca il sys per cercare il sostituto del medico corrente e annullare il suo campo substitute_of
	public boolean killSubstitute(){
		
                    return Sys_User.delSubstitute(this.fiscal_code);
            
	}
        
        public String getSubstNameSurname(String fiscode){
            return Sys_User.getNameSurname(fiscode);
        }
                
	// Prendiamo la lista di farmacisti
	public Pharmalist getPharmalist(){
		Pharmalist list = new Pharmalist();
		return list;
	}
	
	public void logout(){
		// La funzione più enigmatica della terra... la implementiamo?
	}
}
