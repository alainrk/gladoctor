/* Modulo di classe, classe allegato.			*
 * Federico Montori								*/
package gladoctor;

import java.io.*;
import java.awt.Desktop;

public class Attachment{
	private int truename;
	private String falsename;
	private String type;
	private File fd;
	
	//Costruttori
	Attachment(){
		//Costruttore vuoto
	}
	Attachment(String falsename, String type){ //Questo costruttore ridotto viene usato soltanto quando istanziamo una ricetta
		this.falsename = falsename;
		this.type = type;
	}
	Attachment(int truename, String falsename, String type, File fd){ 
		this.truename = truename;											
		this.falsename = falsename;
		this.type = type;
		this.fd = fd;
	}
	
	//Funzioni Get
	public int getTruename(){
		return this.truename;
	}
	public String getFalsename(){
		return this.falsename;
	}
	public String getType(){
		return this.type;
	}
	public File getFd(){
		return this.fd;
	}

	//Funzioni Set
	public void setTruename(int replace){
		this.truename = replace;
	}
	public void setFalsename(String replace){
		this.falsename = replace;
	}
	public void setType(String replace){
		this.type = replace;
	}
	public void setFd(File replace){
		this.fd = replace;
	}
	
	//Funzione che invia al sys l'aggiornamento dell'allegato corrente per il database
	/*public boolean updateData(){
		return Sys_Examination.storeAttachment(this.truename,this.falsename,this.type,this.fd);
	}*/
	
	//Funzione che stampa il file utilizzando la stampante di default del sistema operativo
	public boolean print() throws Exception{
		//verifico che la funzionalità Desktop sia supportata
	    if (!java.awt.Desktop.isDesktopSupported()){
	    	return false;
	    }
	    try{
	    	Desktop desk = java.awt.Desktop.getDesktop(); //ottengo un'istanza del Desktop corrente
	      	desk.print(this.fd); //mando in stampa il file
	      	return true;
	    }
	    catch (Exception e){
	    	throw new Exception(e); 
	    }
	}
}