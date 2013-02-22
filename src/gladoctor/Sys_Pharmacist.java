package gladoctor;

import java.util.*;

public class Sys_Pharmacist extends SystemController {
		
	// Ritornami la lista di farmacisti in una map ID - altre info
	public static Map<Integer,String> extractPharmalist(){
		String query ="SELECT id_pharma, ph_name, address FROM pharmacists";
		querydb.setQuery(query);
		String[] tuple_result;
		String[] valori_result;
		Map<Integer,String> final_result = new HashMap<Integer,String>();
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			final_result.put(Integer.parseInt(valori_result[0]), valori_result[1]+" "+valori_result[2]);
 		}
 		return final_result;
	}
	
	// Riempimi l'oggetto pharmacist che ti passo con i dati che estrai grazie all'ID
	public static Pharmacist extractPharmacist(int id,Pharmacist phTmp){
		querydb.setQuery("SELECT ph_name, mail, address, city FROM pharmacists WHERE id_pharma="+id);
		String[] tuple_result;
		String[] valori_result;
		
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			phTmp.setName(valori_result[0]);
 			phTmp.setMail(valori_result[1]);
 			phTmp.setAddress(valori_result[2]);
 			phTmp.setCity(valori_result[3]);
 		}
 		return phTmp;
	}
	
	// ti passo un ID, tu elimina il farmacista dal DB
	public static boolean removePharmacist(int ID){
		querydb.setQuery("DELETE FROM pharmacists WHERE id_pharma="+ID);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	// ti passo i dati, tu mettili nel database COME NUOVO FARMACISTA
	public static boolean storeNewPharma(String name, String address, String city, String mail){
		querydb.setQuery("INSERT INTO pharmacists VALUES "+name+", "+address+", "+city+", "+mail);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	// ti passo i dati, tu mettili nel database COME UPDATE DI UNO ESISTENTE
	public static boolean storePharma(int ID, String name, String address, String city, String mail){
		querydb.setQuery("UPDATE pharmacists SET name=\'"+name+"\', address=\'"+address+"\', city=\'"+city+"\', mail=\'"+mail+"\' WHERE id_pharma="+ID);
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
}
