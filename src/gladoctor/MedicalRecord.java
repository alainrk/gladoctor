/* Modulo di classe, classe cartella clinica.	*
 * Federico Montori								*/
package gladoctor;

public class MedicalRecord {
	private String fiscal_code;
	private String creator;
	private DataSheet datasheet;
	private MainBoard mainboard;
	
	//Costruttori
	MedicalRecord(){
		this.creator = SystemController.currentUser.getFiscalCode();
		this.datasheet = new DataSheet();
		this.mainboard = new MainBoard();
	}
        
        //Ho il CF e mi servono i dati di UN paziente particolare
       public MedicalRecord(String fiscal_code){
		this.fiscal_code = fiscal_code;
		this.datasheet = new DataSheet();
                //Devo scaricare i dati del paziente, prima setto CF, visto che uso costruttore vuoto
                datasheet.setFiscalCode(fiscal_code);
		this.mainboard = new MainBoard();
		this.datasheet = Sys_MedicalRecord.extractDS(this.datasheet, fiscal_code);
		this.mainboard = Sys_MedicalRecord.extractMB(this.mainboard, fiscal_code);
                this.creator = Sys_MedicalRecord.getCreator(this.fiscal_code);
	}
	
	//Funzioni di Get
	public String getFiscalCode(){
		return this.fiscal_code;
	}
	public String getCreator(){
		return this.creator;
	}
	public DataSheet getDataSheet(){
		return this.datasheet;
	}
	public MainBoard getMainBoard(){
		return this.mainboard;
	}
	
	//Funzioni Set
	public void setFiscalCode(String replace){
		this.fiscal_code = replace;
		this.datasheet.setFiscalCode(replace);
		this.mainboard.setFiscalCode(replace);
	}
	public void setCreator(String replace){
		this.creator = replace;
	}
	public void setDataSheet(DataSheet replace){
		this.datasheet = replace;
	}
	public void setMainBoard(MainBoard replace){
		this.mainboard = replace;
	}
	
	//Funzione che invia al sys i dati della cartella clinica corrente come nuovo record per il database
	public boolean sendData(){
		return (this.datasheet.sendData() && this.mainboard.sendData() &&
				Sys_MedicalRecord.storeNewMR(this.fiscal_code, this.creator));
	}
	
	//Funzione che invia al sys i dati della cartella clinica corrente per aggiornarli sul database	
	public boolean updateData(){
		return (this.datasheet.updateData() && this.mainboard.updateData() &&
				Sys_MedicalRecord.storeMR(this.fiscal_code, this.creator));
	}
}
