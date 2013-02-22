package gladoctor;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Sys_MedicalRecord extends SystemController{
		
	//************Metodi di rimozione *****************//
	
	/*
	 * removeMR(MedicalRecord MR) -> rimuove dal database MR, MB e DS associati al codice fiscale del paziente
	 * prende-> MR
	 * restituisce->0 se giusto, 1 se errore
	 * */
    
	public static boolean removeMR(String fiscal_code){
		
		
		//ELIMINO DataSheet dal database
		boolean DS_result = removeDS(fiscal_code);
		if(!DS_result){
			return false;
		}
		//ELIMINO MainBoard dal database
		boolean MB_result = removeMB(fiscal_code);
		if(!MB_result){
			return false;
		}
		//ELIMINO MedicalRecord dal database
		querydb.setQuery("DELETE FROM medical_record WHERE fiscal_code=\'"+fiscal_code+"\'");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	} 
	/* removeDS(DataSheet DS)-> funzione di appoggio per eliminare il DS passato
	 * prende->DS
	 * restituisce->0 se giusto, 1 se errore
	 * */
	public static boolean removeDS(String fiscal_code){
		querydb.setQuery("DELETE FROM data_sheet WHERE fiscal_code=\'"+fiscal_code+"\'");
		
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
		
	}
	
	/* removeMB(MainBoard MB)-> funzione di appoggio per eliminare il MB passato
	 * prende->MB
	 * restituisce->0 se giusto, 1 se errore
	 * */
	
	public static boolean removeMB(String fiscal_code){
		
		querydb.setQuery("DELETE FROM main_board WHERE fiscal_code=\'"+fiscal_code+"\'");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	//*************** Metodi di estrazione************************//
	/*	extractMR(MedicalRecord MR, DataSheet DS, MainBoard MB, String fiscal_code)-> prende dal database i dati relativi al MR associato al codice fiscale
	 * prende-> DS (vuoto), codice_fiscale
	 * restituisce -> DS (pieno)
	 * */
	public static MedicalRecord extractMR(MedicalRecord MR, DataSheet DS, MainBoard MB, String fiscal_code) throws ParseException{
		querydb.setQuery("SELECT * FROM medical_record WHERE fiscal_code=\'"+fiscal_code+"\'");
		String[] tuple_result;
		String[] valori_result;
		
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		
 		MR.setDataSheet(extractDS(DS, fiscal_code));
 		MR.setMainBoard(extractMB(MB, fiscal_code));
 		
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			MR.setFiscalCode(valori_result[0]);
 			MR.setCreator(valori_result[1]);
 		}
 		
 		
 		
		return MR;
	}
	
	/*	extractDS(DataSheet DS, String fisical_code)-> prende dal database i dati relativi al DS associato al codice fiscale
	 * prende-> DS (vuoto), codice_fiscale
	 * restituisce -> DS (pieno)
	 * */
	public static DataSheet extractDS(DataSheet DS, String fiscal_code) {
		querydb.setQuery("SELECT * FROM data_sheet WHERE fiscal_code=\'"+fiscal_code+"\'");
		String[] tuple_result;
		String[] valori_result;
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			DS.setName(valori_result[1]);
 			DS.setSurname(valori_result[2]);
 			DS.setDateBorn(valori_result[3]); 
 			DS.setPlaceBorn(valori_result[4]);
 			DS.setAddress(valori_result[5]);
 			DS.setCityLiving(valori_result[6]);
 			DS.setOccupation(valori_result[7]);
 			DS.setTelephoneNumber(valori_result[8]);
 			DS.setExemptionCode(valori_result[9]);
 			DS.setAreaCode(valori_result[10]);
 		}
 		return DS;
	}
	
	/*	extractMB(MainBoard MB, String fisical_code)-> prende dal database i dati relativi al MB associato al codice fiscale	 
	 * prende-> MB (vuoto), codice_fiscale
	 * restituisce -> MB (pieno)
	 * */
	public static MainBoard extractMB(MainBoard MB, String fiscal_code){
		querydb.setQuery("SELECT * FROM main_board WHERE fiscal_code=\'"+fiscal_code+"\'");
		String[] tuple_result;
		String[] valori_result;
		
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			MB.setInvalidityPercentage(Integer.parseInt(valori_result[1]));
 			MB.setFiscalCode(fiscal_code);
 			Map<String, String> tmpMap = new HashMap<String, String>();
 			Map<Integer, String> tmpMap1 = new HashMap<Integer, String>();
 			tmpMap = extractChronicDisease(fiscal_code);
 			//Si può anche essere sani e non avere bisogno di farmaci.
 			if (tmpMap == null) ;
 			else MB.setChronicDiseases(tmpMap);
 			tmpMap = null;
 			tmpMap = extractPermanentMedicine(fiscal_code);
 			if (tmpMap == null) ;
 			else MB.setPermanentMedicines(tmpMap);
 			tmpMap = null;
 			tmpMap1 = extractExaminations(fiscal_code);
 			if (tmpMap1 == null) ;
 			else MB.setExaminations(tmpMap1);
 		}
 		return MB;
	}
	
	/* extractCrhonicDisease(String fiscal_code)-> prende dal database la Map di malattia-causa/effetto, funzione ausiliaria
	 * prende->codice_fiscale del paziente
	 * restituisce -> Map<malattia,causa effetto> 
	 * */
	public static Map<String,String> extractChronicDisease(String fiscal_code){
		querydb.setQuery("SELECT disease.* FROM patient_chronicDes, disease WHERE (patient_chronicDes.fiscal_code=\'"+fiscal_code+"\' AND patient_chronicDes.disease_name = disease.dis_name)");
		String[] tuple_result;
		String[] valori_result;
		Map<String,String> final_result = new HashMap<String, String>();
 		querydb.mkQuery();
 		//Bisogna valutare che si può anche essere sani!!!!!!!!!
 		if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")) 	return null;
 		tuple_result = querydb.getTuple();
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			final_result.put(valori_result[0], valori_result[1]+" "+valori_result[2]);
 		}
 		return final_result;
 	}
	
	/*	extractPermanentMedicine(String fiscal_code)->prende dal database la Map di nome medicina-principio attivo, funzione ausiliaria
	 * prende->codice_fiscale del paziente
	 * restituisce-> Map<med_name, active_principle> 
	 * */
	public static Map<String,String> extractPermanentMedicine(String fiscal_code){
		querydb.setQuery("SELECT m_name, active_principle FROM patient_medicinePerm, medicine  WHERE (patient_medicinePerm.fiscal_code=\'"+fiscal_code+"\' AND patient_medicinePerm.medicine_name = medicine.m_name)");					
		String[] tuple_result;
		String[] valori_result;
		Map<String,String> final_result = new HashMap<String, String>();
 		querydb.mkQuery();
 		if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")) 	return null;
 		tuple_result = querydb.getTuple();
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			final_result.put(valori_result[0], valori_result[1]);
 		}
 		return final_result;
 	}
	/*extractPermanentMedicine(String fiscal_code)->prende dal database la Map di id-data delle examination, funzione ausiliaria
	 * prende->codice_fiscale del paziente
	 * restituisce-> Map<id, data> 
	 * */
	public static Map<Integer,String> extractExaminations(String fiscal_code){
		querydb.setQuery("SELECT id, exam_date FROM examinations WHERE patient=\'"+fiscal_code+"\'");					
		String[] tuple_result;
		String[] valori_result;
		Map<Integer,String> final_result = new HashMap<Integer, String>();
 		querydb.mkQuery();
 		if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")) 	return null;
 		tuple_result = querydb.getTuple();
 		for (int i = 0; i < tuple_result.length; i++) { 
 			
 			valori_result = querydb.getValori(tuple_result[i]);
 			final_result.put(Integer.parseInt(valori_result[0]), valori_result[1]);
 		}
 		return final_result;
 	}
	/* extractMRSearch(*)->cerca nel db tutte le MR che hanno nome, cognome, propritario o sostituo uguali a quelli passati
	 * prende-> String fiscal_code, String substitute_of, String qname (paziente), String qsurname (paziente)
	 * restituisce-> Map<fiscal_code (paziente), nome+cognome (paziente)>
	 * */
	public static Map<String,String> extractMRSearch(String fiscal_code, String substitute_of, String qsurname){
		if (SystemController.currentUser.isAdmin())
			querydb.setQuery("SELECT  data_sheet.fiscal_code, data_sheet.p_name, data_sheet.p_surname FROM data_sheet, medical_record WHERE (data_sheet.fiscal_code = medical_record.fiscal_code) AND (data_sheet.p_surname = \'"+qsurname+"\')");
		else
			querydb.setQuery("SELECT  data_sheet.fiscal_code, data_sheet.p_name, data_sheet.p_surname FROM data_sheet, medical_record WHERE (data_sheet.fiscal_code = medical_record.fiscal_code) AND (data_sheet.p_surname = \'"+qsurname+"\') AND (doctor=\'"+fiscal_code+"\' OR doctor =\'"+substitute_of+"\')");
		String[] tuple_result;
		String[] valori_result;
		HashMap<String,String> final_result = new HashMap<String,String>();
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		if( !(querydb.getRawResult().equals("NULL")) ){
	 		for (int i = 0; i < tuple_result.length; i++) { 
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			final_result.put(valori_result[0], valori_result[1]+" "+valori_result[2]);
	 		}
 		}
	 	return final_result;
	}
	
	//****************Metodi di inserimento******************//
	/*storeNewMR(String fiscal_code, String creator)-> inserisce nel database una nuova MedicalRecord
	 * prende -> fiscal_code, createor
	 * restituisce->0 se giusto, 1 se errore
	 * */
	public static boolean storeNewMR(String fiscal_code, String creator){
		querydb.setQuery("INSERT INTO medical_record VALUES (\'"+fiscal_code+"\',\'"+creator+"\')");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*storeNewMB(String fiscal_code, String creator)-> inserisce nel database una nuova DataSheet
	 * prende -> fiscal_code
	 * restituisce->0 se giusto, 1 se errore
	 * FIXME ci sono tutti gli attributi a parte fiscal_code sono inutili
	 * */
	public static boolean storeNewMB(String fiscal_code, int invalidity_percentage, Map<String, String> chronic_diseases, Map<String, String> permanent_medicines){
		/* prima query inserisco i dati relativi alla tabella main_board */
		String query="INSERT INTO main_board VALUES (\'"+fiscal_code+"\',\'"+invalidity_percentage+"\')";
                
		//FIXME ci sono tutte ste query che secondo me non servono a una sega perche quando ne crei uno nuova è tutto vuoto!
	
		/*seconda query inserisco i dati relativi alle malattie croniche*/
		
		//query= query+"; INSERT INTO patient_chronicDes VALUES";
		
		/*for (Map.Entry<String,String> entry : chronic_diseases.entrySet ())
		{
			query = query + " (\'" + fiscal_code + "\',"\' + entry.getKey() + "\')";
		}*/
		
		/*terza query inserisco i dati relativi alle medicine permanenti*/
		
		//query = query + "; INSERT INTO patient_medicinePerm VALUES";
		
		/*for (Map.Entry<String,String> entry1 : permanent_medicines.entrySet())
		{
			query = query + " (" + entry1.getKey() + "," + entry1.getKey() + ")";
		}*/
		querydb.setQuery(query);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*storeNewDS(String fiscal_code, String creator)-> inserisce nel database una nuova MainBoard
	 * prende ->fiscal_code, name, surname, date_born, place_born, address, area_code, city_living, occupation,telephone_number,exemption_code
	 * restituisce->0 se giusto, 1 se errore
	 * FIXME gli attributi exemption_code e area code sono inutili perche non trovan riscontro nel database!!!!
	 */
	public static boolean storeNewDS(String fiscal_code, String name, String surname, String date_born, String place_born, String address, String area_code, String city_living, String occupation, String telephone_number, String exemption_code){
		querydb.setQuery( "INSERT INTO data_sheet  VALUES (\'"+fiscal_code+"\',\'"+name+"\',\'"+surname+"\',\'"+date_born+"\',\'"+place_born+"\',\'"+address+"\',\'"+city_living+"\',\'"+occupation+"\',\'"+telephone_number+"\',\'"+exemption_code+"\',\'"+area_code+"\')");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*storeMR(String fiscal_code,String creator)-> aggiorna i dati realtivi ad una MR sul database
	 * prende-> fiscal_code, creator
	 * restituisce-> restituisce->0 se giusto, 1 se errore
	 * */
	public static boolean storeMR(String fiscal_code,String creator){ 
		/*prima query modifico i valori di MR*/
		querydb.setQuery("UPDATE medical_record SET doctor=\'"+creator+"\' WHERE fiscal_code=\'"+fiscal_code+"\'");
		
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*storeMB(*)-> aggiorna i dati realtivi ad una MB sul database
	 * prende-> String fiscal_code,int invalidity_percentage, Map<String, String> chronic_diseases, Map<String, String> permanent_medicines, Map<String, String> examinations
	 * restituisce-> restituisce->0 se giusto, 1 se errore
	 * */
	public static boolean storeMB(String fiscal_code,int invalidity_percentage, Map<String, String> chronic_diseases, Map<String, String> permanent_medicines){
		String query="UPDATE main_board SET invalid_percentage="+invalidity_percentage+" WHERE fiscal_code=\'"+fiscal_code+"\'";								
		
		/*if(chronic_diseases != null){
			for (Map.Entry<String,String> entry : chronic_diseases.entrySet ())
				query= query+"; UPDATE patient_chronicDes SET disease_name=\'"+entry.getKey()+"\' WHERE fiscal_code=\'"+fiscal_code+"\'";
		}
		
		/*terza query inserisco i dati relativi alle medicine permanenti*/
		
		/*	if(permanent_medicines != null){
			for (Map.Entry<String,String> entry : permanent_medicines.entrySet ())
				query = query + "; UPDATE patient_medicinePerm SET medicine_name=\'"+entry.getKey()+"\' WHERE fiscal_code=\'"+fiscal_code+"\'";
		}
		/*quarta query inserisco i dati relativi alle visite*/

		querydb.setQuery(query);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*storeMR(String fiscal_code,String creator)-> aggiorna i dati realtivi ad una MR sul database
	 * prende-> String fiscal_code, String name, String surname, String date_born, String place_born,String address, int area_code, String city_living, String occupation,int telephone_number, String exemption_code
	 * restituisce-> restituisce->0 se giusto, 1 se errore
	 * */
	public static boolean storeDS(String fiscal_code, String name, String surname, String date_born, String place_born, String address, String area_code, String city_living, String occupation, String telephone_number, String exemption_code){
		querydb.setQuery("UPDATE data_sheet SET area_code=\'"+area_code+"\' , exemption_code=\'"+exemption_code+"\' , p_name=\'"+name +"\' , p_surname=\'"+surname +"\' , date_born=\'"+date_born+"\' , place_born=\'"+place_born+"\' , address=\'"+address+"\' , city=\'"+city_living +"\' , occupation=\'"+occupation+"\' , telephone=\'"+ telephone_number+"\' WHERE fiscal_code=\'"+fiscal_code+"\'");								 
		querydb.mkQuery();							 
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}

        public static String getCreator(String fiscal_code){
            querydb.setQuery("SELECT doctor FROM medical_record WHERE fiscal_code=\'"+fiscal_code+"\'");
            String[] tuple_result;
            String[] valori_result;
            querydb.mkQuery();
            tuple_result = querydb.getTuple(); 		
            // Deve necessariamente essere univoco
            valori_result = querydb.getValori(tuple_result[0]);
            return valori_result[0];
          }
      //prende la lista di tutte le medicine dal database
    	public static String[] getMedicineList(){
    		querydb.setQuery("SELECT m_name FROM medicine");
    		String[] tuple_result;
    		String[] valori_result = new String[1];
    		querydb.mkQuery();
    		String[] finalArray;
    		if(querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")){
    		return null;
    		}else{
    		tuple_result = querydb.getTuple();
    		finalArray = new String[tuple_result.length];
    		for (int i = 0; i < tuple_result.length; i++) {
				//per ogni(in questo caso una) tupla estraggo i valori
				valori_result = querydb.getValori(tuple_result[i]);
				finalArray[i] = valori_result[0];
			}
    			
    		}
    		return finalArray;
    			
    	}
    	//prende la lista di tutte le malattie dal database
    	public static String[] getDiseaseList(){
    		querydb.setQuery("SELECT dis_name FROM disease");
    		String[] tuple_result;
    		String[] valori_result = new String[1];
    		querydb.mkQuery();
    		String[] finalArray;
    		if(querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:")){
    		return null;
    		}else{
    		tuple_result = querydb.getTuple();
    		finalArray = new String[tuple_result.length];
    			for (int i = 0; i < tuple_result.length; i++) {
    				//per ogni(in questo caso una) tupla estraggo i valori
    				valori_result = querydb.getValori(tuple_result[i]);
    				finalArray[i] = valori_result[0];
    			}
    			
    		}
    		return finalArray;
    			
    	}
    	//aggiorna il collegamento sul database fra paziente e malattia cronica
    	public static boolean storeDisease(String fiscode, String disease){
    		querydb.setQuery("INSERT INTO patient_chronicDes VALUES (\'"+fiscode+"\',\'"+disease+"\')");
    		querydb.mkQuery();
    		if(querydb.getRawResult().equals("1")){
    			return true;
    		}else{
    			return false;
    		}
    	}
    	//aggiorna il collegamento sul database fra paziente e medicina permanente
    	public static boolean storeMedicine(String fiscode, String medicine){
    		querydb.setQuery("INSERT INTO patient_medicinePerm VALUES (\'"+fiscode+"\',\'"+medicine+"\')");
    		querydb.mkQuery();
    		if(querydb.getRawResult().equals("1")){
    			return true;
    		}else{
    			return false;
    		}
    	}
    	//elimina il collegamento sul database fra paziente e malattia cronica
    	public static boolean removeDisease(String fiscode, String disease){
    		querydb.setQuery("DELETE FROM patient_chronicDes WHERE fiscal_code=\'"+fiscode+"\' AND disease_name=\'"+disease+"\'");
    		querydb.mkQuery();
    		if(querydb.getRawResult().equals("1")){
    			return true;
    		}else{
    			return false;
    		}
    	}
    	//elimina il collegamento sul database fra paziente e medicina permanente
    	public static boolean removeMedicine(String fiscode, String medicine){
    		querydb.setQuery("DELETE FROM patient_medicinePerm WHERE fiscal_code=\'"+fiscode+"\' AND medicine_name=\'"+medicine+"\'");
    		querydb.mkQuery();
    		if(querydb.getRawResult().equals("1")){
    			return true;
    		}else{
    			return false;
    		}
    	}
}

