package gladoctor;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Sys_Examination extends SystemController{
	/*******funzioni di inserimento*****************/	
	/* storeNewExamination(*)->crea un nuovo record per la visita nel database
	 * prende->String date, String patient,String doctor,String disease,String notes
	 * restituisce -1 se errore, altrimenti il numero della visita immessa
	 * FIXME: sarebbe il caso di usare le eccezioni per questi metodi?
	 * */
	public static int storeNewExamination(String date, String patient, String doctor, String disease, String notes){
		/* Escamotage per ricevere indietro il numero di examination che vado a creare */
		int ID;
		querydb.setQuery("SELECT max(id) FROM examinations");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("|!|")){
			ID=1;
		}else {
	        String[] tuple_result = querydb.getTuple();
	        String[] valori_result = querydb.getValori(tuple_result[0]);
	        ID = Integer.parseInt(valori_result[0]) + 1;
        }
		/* Query di inserimento nuova examination */
		querydb.setQuery("INSERT INTO examinations VALUES ("+ID+", \'"+patient+"\',\'"+doctor+"\',\'"+date+"\',\'"+disease+"\',\'"+notes+"\')");
		querydb.mkQuery();
		System.out.println(querydb.getRawResult());
		if(querydb.getRawResult().equals("1")){
			return ID;
		}else{
			return (-1);
		}
	}
	/* storeExamination(*)->modifica un record relativo alla visita nel database
	 * prende->String date, String patient,String doctor,String disease,String notes
	 * restituisce 0 se giusto, 1 se errore
	 * */
	public static boolean storeExamination(int ID, String date, String patient, String doctor, String disease, String notes){
		querydb.setQuery("UPDATE examinations SET patient=\'"+patient+"\',doctor=\'"+doctor+"\', exam_date=\'"+date+"\',main_disease=\'"+disease+"\',notes=\'"+notes+"\' WHERE id="+ID);
        querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	/*storeNewAttachment(*)-> salva nel database un allegato TODO mettersi d'accordo bene su come
	 * prende-> name (falso del file da inserire nel DB), tipo di file ,file descriptor id visita
	 * resttuisce 
	 * */
	
	//ti passo i parametri di un allegato più l'id di chi lo ha caricato. devi assegnargli un nome vero (vedremo come fare magari con i generatori di uuid) poi caricare tutto sul db COME NUOVO ALLEGATO
	public static int storeNewAttachment(String absLocalPath, String falsename, String type, int id_exam) throws IOException{
		int truename=0;
  	/* ora querydb è per l'id dell'attach*/
		querydb.setQuery("SELECT max(id) FROM attachments");
		querydb.mkQuery();
		
		if(querydb.getRawResult().equals("|!|")){ // da sistemare
			truename=1;
		}else {
            String[] tuple_result = querydb.getTuple();
            String[] valori_result = querydb.getValori(tuple_result[0]);
            truename = Integer.parseInt(valori_result[0]) + 1;
         }
		/* ora la query è per inserire i valori di attachment con l'id che avevo preso prima*/
		querydb.setQuery("INSERT INTO attachments VALUES ("+truename+",\'"+falsename+"\', \'"+type+"\')");
		querydb.mkQuery();
		
		querydb.setQuery("INSERT INTO exam_attach VALUES ("+id_exam+", "+truename+")");
		querydb.mkQuery();

		if(querydb.getRawResult().equals("NULL")){
			return -1;
		}else{
			Storage.sendFile(absLocalPath, Integer.toString(truename));
			return truename;
		}
	}
	
	/**ti passo i parametri di un allegato e tu lo carichi sul database come UPDATE DI UNO ESISTENTE	
	 * @throws IOException 
	*/
	public static boolean storeAttachment(int truename, String falsename, String type, File fd) throws IOException {
		String path = fd.getAbsolutePath();
		String name = fd.getName();
		querydb.setQuery("UPDATE attachments SET att_name=\'"+falsename+"\',att_type=\'"+type+"\' WHERE id="+truename);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else return false;
		
	}
	
	//ti passo i parametri di una ricetta e tu la inserisci nel db COME NUOVA RICETTA	
	// "id_att" è quello di attachment (valido da ora anche per la prescription)
	public static boolean storeNewPrescription(int id_att, String jurisdiction, String pat_name, String pat_surname, String pat_address, String fiscal_code, String area_code, char priority, String prescriptions, String notes, String exemption_code, String pres_type, int total, String date){
           
		querydb.setQuery("INSERT INTO prescriptions VALUES ("+id_att+",\'"+pat_name+"\',\'"+pat_surname+"\',\'"+pat_address+"\',\'"+fiscal_code+"\',\'"+area_code+"\',\'"+jurisdiction+"\',\'"+prescriptions+"\',\'"+priority+"\',\'"+exemption_code+"\',\'"+pres_type+"\',\'"+notes+"\',\'"+date+"\',"+total+")");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
    // FIXMEDB: MI DOVETE PASSARE ANCHE L'ID DELLA PRESCRIPTION (id_prescr) sennò che devo modificare?
	//ti passo i dati di una ricetta e tu la inserisci nel db COME UPDATE DI UNA ESISTENTE	
	public static boolean storePrescription(int prescr, String jurisdiction, String pat_name, String pat_surname, String pat_address, String fiscal_code, String area_code, char priority, String prescriptions, String notes, String exemption_code, String pres_type, int total, String date){
		querydb.setQuery("UPDATE prescriptions SET jurisdicton =\'"+jurisdiction+"\',p_name =\'"+pat_name+"\', p_surname=\'"+pat_surname+"\', address =\'"+pat_address+"\', fiscal_code =\'"+fiscal_code+"\', area_code = \'"+area_code+"\', priority = \'"+priority+"\', total = "+total+", p_date =\'"+date+"\' WHERE id = "+prescr);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	//funzioni di estrazione	
	//ti passo una visita vuota e tu me la riempi coi dati di quella che ha l'id che ti passo
	public static Examination extractExamination(int ID, Examination exam){
		
		querydb.setQuery("SELECT * FROM examinations WHERE id="+ID);
		String[] tuple_result;
		String[] valori_result=null;
		querydb.mkQuery();
		if(querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")){
			return null;
		}else{
			tuple_result = querydb.getTuple();
	 		for (int i = 0; i < tuple_result.length; i++) { 		//per ogni(in questo caso una) tupla estraggo i valori
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			exam.setID(Integer.parseInt(valori_result[0]));
	 			exam.setPatient(valori_result[1]);
	 			exam.setDoctor(valori_result[2]);
				exam.setDate(valori_result[3]);
	 			exam.setDisease(valori_result[4]);
	 			exam.setNotes(valori_result[5]);
	 			exam.setAttachments(extractDoubleAtt(ID));			
	 			exam.setPrescriptions(extractDoublePres(ID));
	 			
	 		}
	 		
		}
		return exam;
	}
  
	// Questa funzione estrae dal DB tutte le coppie truename/falsename che hanno type="Prescription"
	public static Map<Integer, String> extractDoublePres (int id_exam){
		querydb.setQuery("SELECT id, att_name FROM attachments, exam_attach WHERE (att_type = 'Prescription') AND (attachments.id = exam_attach.id_attach) AND (id_exam ="+id_exam+")");
		String[] tuple_result;
		String[] valori_result;
		Map<Integer,String> final_result = new HashMap<Integer,String>();
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		if( !querydb.getRawResult().equals("NULL") ){
	 		for (int i = 0; i < tuple_result.length; i++) { 
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			final_result.put(Integer.parseInt(valori_result[0]), valori_result[1]);
	 		}
	 		return final_result;
 		}else return null;
		
	}
  
	// Questa funzione estrae dal DB tutte le coppie truename/falsename che hanno type="File" di una certa visita
	public static Map<Integer, String> extractDoubleAtt (int id_exam){
		querydb.setQuery("SELECT id, att_name FROM attachments, exam_attach WHERE (att_type = 'File') AND (attachments.id = exam_attach.id_attach) AND (id_exam ="+id_exam+")");
		String[] tuple_result;
		String[] valori_result;
		Map<Integer,String> final_result = new HashMap<Integer, String>();
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
	 	if( !querydb.getRawResult().equals("NULL") ){
	 		for (int i = 0; i < tuple_result.length; i++) { 
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			final_result.put(Integer.parseInt(valori_result[0]), valori_result[1]);
	 		}
	 		return final_result;
	 	}else return null;
	}

	//ti passo il vero nome di un allegato e tu mi riempi l'oggetto e me lo ripassi
	public static Attachment extractAttachment(int truename, Attachment attach) throws IOException{
		querydb.setQuery("SELECT * FROM attachments WHERE id="+truename);
		String[] tuple_result;
		String[] valori_result;
		querydb.mkQuery();
		if(querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")){
			return null;
		}else{
			tuple_result = querydb.getTuple();
	 		for (int i = 0; i < tuple_result.length; i++) { 		
	 			//per ogni(in questo caso una) tupla estraggo i valori
	 			valori_result = querydb.getValori(tuple_result[i]);
				attach.setTruename(Integer.parseInt(valori_result[0]));
	 			attach.setFalsename(valori_result[1]);
	 			attach.setType(valori_result[2]);
	 			// Prendo il file chiamato "truename" (indice numerico) sul server
	 			Storage.receiveFile(Integer.toString(truename));
	 			File fd = new File(Integer.toString(truename));
	 			File effective = new File("javafiles/"+attach.getFalsename());
	 			fd.renameTo(effective);
	 			attach.setFd(effective);
	 		}	
		}
		return attach;
	}
	
	//ti passo il vero nome della ricetta e tu mi riempi l'oggetto e me lo ripassi	
	public static Prescription extractPrescription(int truename, Prescription prescr){
		try{
			extractAttachment(truename, prescr);
		} catch (Exception e){
			return null;
		}
		querydb.setQuery("SELECT p_name, p_surname, address, fiscal_code, area_code, jurisdiction, prescription, priority, exemption_code, presc_type, note, date, total"+
							" FROM prescriptions "+
							"WHERE id="+truename);
		String[] tuple_result;
		String[] valori_result=null;
		querydb.mkQuery();
		if(querydb.getRawResult().equals("NULL")){
			return null;
		}else{
			tuple_result = querydb.getTuple();
	 		for (int i = 0; i < tuple_result.length; i++) {
	 			//per ogni(in questo caso una) tupla estraggo i valori
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			prescr.setPatName(valori_result[0]);
	 			prescr.setPatSurname(valori_result[1]);
	 			prescr.setPatAddress(valori_result[2]);
	 			prescr.setFiscalCode(valori_result[3]);
	 			prescr.setAreaCode(valori_result[4]);
	 			prescr.setJurisdiction(valori_result[5]);
	 			prescr.setPrescriptions(valori_result[6]);
	 			prescr.setPriority(valori_result[7].charAt(0));   
	 			prescr.setExemptionCode(valori_result[8]);
	 			prescr.setPresType(valori_result[9]);
	 			prescr.setNotes(valori_result[10]);
	 			prescr.setDate(valori_result[11]);
	 			prescr.setTotal(Integer.parseInt(valori_result[12]));
	 		}
		}
		return prescr;
		
	}
	
	/*******funzioni di rimozione*****************/	
	//ti passo il truename e tu cancelli l'allegato	
	public static boolean removeAttachment(int truename){
		querydb.setQuery("DELETE FROM attachments WHERE id = "+truename);   
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			querydb.setQuery("DELETE FROM exam_attach WHERE id_attach="+truename);
	 		querydb.mkQuery();
	 		if(querydb.getRawResult().equals("1")){
	 			return true;
	 		}else return false;
		}else{
			return false;
		}
	}
	
	//ti passo un id e tu mi cancelli dal db la visita con quell'id e tutte le sue ricette e allegati
	public static boolean removeExamination(int ID){
		querydb.setQuery("SELECT id_attach FROM exam_attach WHERE id_exam="+ID);
		querydb.mkQuery();
		String[] tuple_result = null;
		String[] valori_result = null;
		if( !querydb.getRawResult().equals("NULL") ){
			tuple_result = querydb.getTuple();
			Integer[] elenco = new Integer[tuple_result.length];
	 		for (int i = 0; i < tuple_result.length; i++) { 
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			elenco[i] = Integer.parseInt(valori_result[0]);
	 		}
	 		for (int i = 0; i < elenco.length; i++)
	 			removeAttachment(elenco[i]);
		}
 		querydb.setQuery("DELETE FROM exam_attach WHERE id_exam="+ID);
 		querydb.mkQuery();
 		
 		if(querydb.getRawResult().equals("1")){
 		querydb.setQuery("DELETE FROM examinations WHERE id="+ID);
 		querydb.mkQuery();
	 		if(querydb.getRawResult().equals("1")){
				return true;
			}else
				return false;	
 		}
 		else 
 			return false;		
	}
	
	//ti passo il truename e tu cancelli la ricetta	
	public static boolean removePrescription(int truename){
		String query="";
		//Cancello la ricetta
		query=query+"DELETE FROM prescriptions WHERE id ="+truename+";";
		querydb.setQuery(query);		
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}	
}
