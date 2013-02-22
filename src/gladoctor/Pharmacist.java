/* Questa classe si occupa della gestione del singolo farmacista. 
 * Viene istanziata da Pharmalist nel momento in cui viene scaricato
 * il record di dati corrispondente alla scelta del medico per l'invio 
 * mail.
 */
package gladoctor;

//import javax.mail.*; 		// Sono librerie non di default, vanno scaricate http://java.sun.com/products/javamail/
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import java.io.*;
import java.net.*;
import java.util.*;

public class Pharmacist{
	private int ID;
	private String name;
	private String address;
	private String city;
	private String mail;

	public Pharmacist(){
		/* Costruttore vuoto */
	}

	public Pharmacist(int id, String name, String address, String city, String mail){
		this.name = name;
		this.address = address;
		this.city = city;
		this.mail = mail;
		this.ID = id;
	}
	
	public String getName(){
		return this.name;	
	}
	
	public String getAddress(){
		return this.address;	
	}

	public String getCity(){
		return this.city;	
	}

	public String getMail(){
		return this.mail;	
	}
	
	public int getID(){
		return this.ID;
	}

	public void setName(String n){
		this.name = n;
	}
	
	public void setAddress(String addr){
		this.address = addr;
	}
	
	public void setCity(String c){
		this.city = c;
	}
	
	public void setMail(String m){
		this.mail = m;
	}

	/* ADMIN method - Aggiunge un farmacista dal DB */
	/* Ti passo tutti i dati, tranne id_pharma ovviamente, quello è sequenziali e devi fare la query sull'id massimo presente e mettere quello incrementato di uno */
	public boolean sendData(){
		return Sys_Pharmacist.storeNewPharma(this.name, this.address, this.city, this.mail);
	}
	
	/* ADMIN */
	public boolean updateData(){
		/* Modifica dati farmacista con questi, cercando a seconda dell'id */
		return Sys_Pharmacist.storePharma(this.ID, this.name, this.address, this.city, this.mail);
	}

	/* Mi viene passata una ricetta e do l'ordine di inviarla */
	public boolean sendMail(Prescription presc){
		
		/*String smtpHost = "out.google.com"; // Adesso non so esattamente quale sia
		String fromAddr = "gladoctor@gmail.com"; // ce lo dovremmo fare
		String toAddr = this.mail;
		String text = null;
		
		FileInputStream reader = null;
		try {
			reader = new FileInputStream(presc.getFd());
									} catch (FileNotFoundException e1) {
												e1.printStackTrace();
											}
		InputStreamReader stream = new InputStreamReader(reader);
		BufferedReader buffer = new BufferedReader(stream);
		try {
			String tmp = null;
			while ((tmp = buffer.readLine()) != null){
				text = text + "\n" + tmp;
			}	
		} catch(Exception e){
			return false;
		}
		
		try {
			Properties props = System.getProperties();

            props.put("mail.smtp.host", smtpHost);

            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromAddr));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));

            message.setSubject("Invio ricetta per il paziente " + presc.getPatName() + " " + presc.getPatSurname());
            message.setText(text);
            

            Transport.send(message);
		} catch(Exception e){
			return false;
		}*/
		return true;
	}
}
