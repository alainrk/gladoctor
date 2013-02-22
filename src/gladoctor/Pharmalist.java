// Questa classe si occupa della gestione dei farmacisti che vengono visti come una semplice 
// lista di array da due campi [Nome (visualizzato) e Identificatore].

package gladoctor;

import java.lang.String;
import java.util.*;
import java.lang.Integer;
 
public class Pharmalist{	
	// Lista di array da 2 campi: Nome e ID, verrà poi scelto un ID e quindi scaricato il farmacista in Pharmacist
	private Map<Integer,String> phList;
		/* Utilizzo:
			String ph = new String[2];
			ph[0] = 'nome';
			ph[1] = 'ID';
			phList.add(ph);
		*/

	public Pharmalist(){
		// Dammi una Map con id_pharma e stringa fatta così: [$ph_name, $address, $city] XXX io ho fatto in modo che sia una stringa unica, poi al massimo possimao modificarlo (uniformità)
		this.phList = Sys_Pharmacist.extractPharmalist();		
	}
	
	public Map<Integer,String> getList(){
		return this.phList;	
	}
	
	/* Gli viene chiesto (dalla GUI) un farmacista dato un ID, lui lo prende e lo consegna 
	 * al chiamante come oggetto Pharmacist già riempito.
	 */
	public Pharmacist getPharmacistById(int id){
		Pharmacist phTmp = new Pharmacist();
		/* Ti passo il pharma_id, e il farmacista da riempire, tu ci metti i dati */ 
		phTmp = Sys_Pharmacist.extractPharmacist(id, phTmp); //XXX uniformità get -> extract
		return phTmp;
	}
	
	/* ADMIN method - Cancella un farmacista dal DB */
	public boolean deletePharmacist(Pharmacist pharma){ //XXX uniformità passi l'oggetto e qui sotto estrai ID
		/* Ti passo l'id_pharma e mi fai un delete su DB */
		return Sys_Pharmacist.removePharmacist(pharma.getID());
	}
	
	public Pharmacist createPharmacist(){ //TODO ma non si può semplicemente fare new pharmacist senza fare un'altra maledetta funzione?
		Pharmacist pharma;
		return pharma = new Pharmacist();
	}
}
