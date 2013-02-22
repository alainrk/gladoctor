package gladoctor;

import java.util.*;

public class Sys_User extends SystemController{
	/* LOGIN -> log_user(String username, String password); 
	 * prende -> username e pass;
	 * restituisce -> User pieno;  
	 */
         //Restituisce NULL in caso di problema o login fallito
	public static User login(String username, String password, User current){
            String[] tuple_result;
            String[] valori_result;
            String query ="SELECT fiscal_code, username, d_name, d_surname, admin, substitute_of FROM users WHERE username = \'"+username+"\' AND password=\'"+password+"\'";
            //aggiustare hash e continuare con il resto dell'ineterfaccia
             
            querydb.setQuery(query);
            querydb.mkQuery();
            if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:") )
		return null;
            else{
		tuple_result = querydb.getTuple();
		for (int i = 0; i < tuple_result.length; i++) {
                    //per ogni(in questo caso una) tupla estraggo i valori
                    valori_result = querydb.getValori(tuple_result[i]);
                    
                    current.setFiscalCode(valori_result[0]);
                    current.setUsername(valori_result[1]);
                    current.setName(valori_result[2]);
                    current.setSurname(valori_result[3]);
                    // Is admin?
                    if(valori_result[4].equals("1")){
                            current.setAdmin(true);
                    }else{
                            current.setAdmin(false);
                    }
                    // 0 means No substitute now
                    current.setSubstituteOf(valori_result[5]);
                }
            }
            
        return current;
	}

        public static boolean removeUser (String fiscal_code){

                querydb.setQuery("DELETE FROM users WHERE fiscal_code=\'"+fiscal_code+"\'");   
                querydb.mkQuery();
        		if(querydb.getRawResult().equals("1")){
        			return true;
        		}else{
        			return false;
        		}
        }
        
	public static int logout(){ //***servirà?!?!**//
		//TODO: invalidare il token di sessione
		return 1;
	}
	
	/* extractUser -> extractUser(String code, User newUser); 
	 * prende -> codice fiscale del medico, oggetto istanziato vuoto da riempire;
	 * restituisce -> User pieno;  
	 */
	public static User extractUser(String code, User newUser){
		querydb.setQuery("SELECT * FROM users WHERE fiscal_code=\'"+code+"\'");
		String[] tuple_result;
		String[] valori_result;
		
		querydb.mkQuery();
		if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:") ){
			return newUser;      
		}else{
			tuple_result = querydb.getTuple();
	 		for (int i = 0; i < tuple_result.length; i++) {
	 			//per ogni(in questo caso una) tupla estraggo i valori
	 			valori_result = querydb.getValori(tuple_result[i]);
	 			newUser.setFiscalCode(valori_result[0]);
	 			newUser.setUsername(valori_result[1]);
	 			newUser.setName(valori_result[2]);
	 			newUser.setSurname(valori_result[3]);
	 			if(valori_result[5].equals("1")){
	 				newUser.setAdmin(true);
	 			}else{
	 				newUser.setAdmin(false);
	 			}
	 			if(valori_result[6].equals("NULL")){		//controllare i valori NULL dal database!!!
	 				newUser.setSubstituteOf(null);
	 			}else{
	 				newUser.setSubstituteOf(valori_result[6]);
	 			}
	 		}	 		
		}
		return newUser;
	}
	
	/* CREAZIONE NUOVO MEDICO, nel caso di sostituzione ->  new_doc_data(String PC, String name, String surname, String subof);
	 * prende -> fiscal_code, username, name, username, subof;
	 * restituisce -> 0 se è stato salvato nel database, 1 se errore;
	 */
	public static boolean storeNewDoctor(String username, String password, String fiscal_code, String name, String surname, String substitute_of, boolean isAdmin){
		int admin;
		if (isAdmin) admin=1;
		else admin=0;

		querydb.setQuery("INSERT INTO users VALUES (\'"+fiscal_code+"\', \'"+username+"\',\'"+name+"\', \'"+surname+"\',\'"+password+"\',"+admin+",\'"+substitute_of+"\')");

		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
			
	}
	
	/*	MODIFICA MEDICO ESISTENTE. -> store_doc_data(String username, String passwd, String[] DCD);
	 *  prende -> username,pass e un array con campi:[0]=>fiscal_code,[1]=>name,[2]=>surname, [3]=>sub_of;
	 *  restituisce-> 0 se sono state apportate le modifiche richieste, 1 se errore;
	 */
	public static boolean storeDoctor(String username, String password, String fiscal_code, String name, String surname, String substitute_of, boolean isAdmin){
		int admin;
		if (isAdmin) admin=1;
		else admin=0;

		querydb.setQuery("UPDATE users " + "SET personal_code=\'"+fiscal_code+"\', username=\'"+username+"\', password=\'"+password+"\', name=\'"+name+", \'surname=\'"+surname+", admin="+admin+", \'sub_of=\'"+substitute_of+"\' WHERE fiscal_code=\'"+fiscal_code+"\'");
                
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}		
	}
	
	/* LISTA DOTTORI -> extract_doc_list()
	 * prende->niente
	 * restituisce-> Map <nome, codice_fiscale>
	 */
	public static Map<String,String> extractDocList(){
                String[] tuple_result;
		String[] valori_result;
		querydb.setQuery("SELECT fiscal_code, d_name FROM users");
		Map<String,String> final_result = new HashMap<String,String>();
        
 		querydb.mkQuery();
 		tuple_result = querydb.getTuple();
 		
 		if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:") ){
			return null;      
		}else{
 		for (int i = 0; i < tuple_result.length; i++) { 
 			valori_result = querydb.getValori(tuple_result[i]);
 			final_result.put(valori_result[0], valori_result[1]);
 		}
 		return final_result;
		}
 	}
	
	/* Set Sostituto -> setSubstitute(String fiscal_code, String doctor)
	 * prende-> codice fiscale (del medico da sosttituire), doctor (del sostituto)
	 * restituisce-> 0 se sono state apportate le modifiche richieste, 1 se errore;
	 * */
	public static boolean setSubstitute(String fiscal_code, String doctor){
		querydb.setQuery("UPDATE users SET substitute_of=\'"+fiscal_code+"\' WHERE fiscal_code=\'"+doctor+"\'");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
	
	/*switchPwd(*)->cambia la pass di un medico nel database
	 * prende-> username, vecchia pass, nuova pass
	 * restituisce-> 0 se sono state apportate le modifiche richieste, 1 se errore;
	 * */
	public static boolean switchPwd(String username, String old_pwd, String new_pwd){
		querydb.setQuery("UPDATE users SET password=\'"+new_pwd+"\' WHERE username=\'"+username+"\' AND password=\'"+old_pwd+"\'");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
        //Toglie il sostituto dell'utente identificato dal codice fiscale
        public static boolean delSubstitute(String fiscal_code){
		querydb.setQuery("UPDATE users SET substitute_of=\'0\' WHERE substitute_of=\'"+fiscal_code+"\'");
		querydb.mkQuery();
		if(querydb.getRawResult().equals("1")){
			return true;
		}else{
			return false;
		}
	}
        //Ottieni nome e cognome in un'unica stringa a partire da un codice fiscale
        public static String getNameSurname(String fiscode){
            String[] tuple_result;
            String[] valori_result;
            querydb.setQuery("SELECT d_name,d_surname FROM users WHERE fiscal_code=\'"+fiscode+"\'");

            querydb.mkQuery();
            tuple_result = querydb.getTuple();

            if (querydb.getRawResult().equals("NULL") || querydb.getRawResult().subSequence(0, 13).equals("Invalid query:") ){
                    return null;      
            }else{
                   valori_result = querydb.getValori(tuple_result[0]);
            return (valori_result[0]+" "+valori_result[1]);
            }
        }
}
