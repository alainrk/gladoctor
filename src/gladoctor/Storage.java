package gladoctor;

//import java.awt.Desktop;
import java.io.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.net.*;
import java.net.URLConnection;

public class Storage {
  //String filename;
  //static String path;
  //String type;
  //private URL url;
  //static String fixedpath = "files/";

  //Nome del file
  //public void setFilename(String fileName){
  //this.filename = fileName;
  //}

  //Path assoluto del file
  //public void setPath(String path){
  //this.path = path;
  //}

  //public void setType(String type){
  //this.type=type;
  //}

  // INVIO di un file locale ad un server mediante HTTP
  public static String sendFile(String localAbsPath, String filename) throws IOException {
  // Inizializzo la connessione
  URL url = new URL("http://ingsw1202.web.cs.unibo.it/storeFile.php?filename="+filename);
  URLConnection conn = url.openConnection();
  HttpURLConnection connection = (HttpURLConnection) conn;
  connection.setDoOutput(true);
  connection.setRequestMethod("POST");
  connection.setRequestProperty("Content-type", "application/pdf");

  // Inizializzo i due stream (File per il file e Data output per l'invio)
  DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
  FileInputStream fis = new FileInputStream(new File(localAbsPath));

  // Metto in un buffer da massimo 10 MB il mio file che risiede in locale
  byte[] buffer = new byte[10485760];
  int bytesRead;
  System.out.println("Waiting for upload...");
  while ((bytesRead = fis.read(buffer)) != -1) {
  dos.write(buffer, 0, bytesRead);
  }
  // Scarico gli stream e chiudo
  dos.flush();
  dos.close();
  fis.close();

  // Ricevo la risposta dal server in un buffer
  BufferedInputStream br2 = new BufferedInputStream(connection.getInputStream());
  String response = null;
  byte[] buffer2 = new byte[1024];
  while ((bytesRead = br2.read(buffer2)) != -1) {
    response = new String(buffer2, 0, bytesRead);
  }
    br2.close();
    System.out.println("Upload OK");
    return response;
  }

  // RICEZIONE di un file che risiede in remoto mediante HTTP
  public static void receiveFile(String name) throws IOException {

    try {
        URL url = new URL("http://ingsw1202.web.cs.unibo.it/"+name);

        // Uso una combinazione random di user-agent
        String userAgent = "Firefox/10.00 (Linux Ubuntu 10.04; U; en)";
        System.out.println("Waiting for download...");
        /* Lo salvo in un file locale con lo stesso nome */
        getByUrl(url, name, userAgent);
        System.out.println("Download ok!");

    } catch (Exception e) {
        System.err.println(e);
        }
    }

  // Si occupa dello scaricamento del file da un URL
  public static void getByUrl(URL url, String localFilename, String UA)
    throws IOException {
        // Setto gli stream
        InputStream inStream = null;
        FileOutputStream outStreamFile = null;

        try {
            // Apro la connessione con l'url datomi e setto lo user-agent come fossi browser
            URLConnection conn = url.openConnection();
            if (UA != null)
                conn.setRequestProperty("User-Agent", UA);

            // Carico il payload
            inStream = conn.getInputStream();
            outStreamFile = new FileOutputStream(localFilename);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStreamFile.write(buffer, 0, len);
            } 
        // Chiudo e pulisco
        } catch(IOException e){ 
        	System.out.println("File receive error: "+e);
        }try {if (inStream != null) {inStream.close();}
        		}
        catch(IOException e){System.out.println("File receive error: "+e);
        }finally {
                if (outStreamFile != null) {
                    outStreamFile.close();
                }
            }
        }
  }

/********************
  // Invia e riceve file di massimo 10MB di qualsiasi estensione, TODO: Gestire nomi ed estensioni!
  public static void main(String[] args) throws IOException{
  // Invio del file indirizzato da [Absolute PathName] - [Nome con cui salvare nel server]
  sendFile("/home/narko/Scrivania/WGETJava.jar", "WGETJava.jar");
  // Ricezione del file specificato da nome cui è salvato nel server
  receiveFile("WGETJava.jar");
  }
***********************/

 //END Storage
