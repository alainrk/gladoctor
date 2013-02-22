package gladoctor;

import java.io.BufferedInputStream;	//TODO: pulire magari...
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class QueryDB{
	private String query;
	private URL url;
        private String rawResult;	//risultato query (settato direttamente in mkquery)

	public QueryDB(){
            try {this.url = new URL("http://ingsw1202.web.cs.unibo.it/lis.php");}
            catch (Exception e){System.out.println(e.getMessage()+" "+e.getCause());}
            this.rawResult="";
	}

	public void setQuery (String query){
                // Pulisco query
                this.query = "";
		this.query = query; 
	}	
	
	/*	funzione che effettua la richiesta post con la query settata precedentemente 
	 * 
	 * */
	public void mkQuery(){  
		try {
                    // Pulisco result
                    rawResult = "";
		    String urldata = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(this.query, "UTF-8"); 
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(urldata);
		    wr.flush();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String buffer;
		    
		    while ((buffer = rd.readLine()) != null) {
		    	this.rawResult = this.rawResult + buffer;
		    }
		 
		    wr.close();
		    rd.close();
		    
		 	} catch (Exception e) {}
	}

	public String getRawResult(){
            return this.rawResult;
        }
	public String[] getTuple(){
            String[] tuple;
            tuple = this.rawResult.split("\\|\\!\\|");
            return tuple;
	}
	
	public String[] getValori(String tuple){
            String[] valori;
            valori = tuple.split("\\|\\:\\|");
            return valori;
	}
}
