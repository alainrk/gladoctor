/* Modulo di classe, classe di appoggio creazione utente.	*
 * Federico Montori											*/
package gladoctor;

import java.lang.String;

public class LogUser{
	// Funzione di login: semplicemente fa controllare al database se username a pwd inviati sono giusti e riceve dal sys l'istanza current riempita
	public static boolean login(String username, String password){
		if ( (SystemController.currentUser = Sys_User.login(username, password, new User()) ) != null)
                    return true;
                else return false;
                      
                
	}
}