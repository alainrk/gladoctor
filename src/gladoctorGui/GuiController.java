/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;
import gladoctor.*;
import java.security.*;
import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 *
 * @author ste
 */
public class GuiController {
    
    private static JFrame frame;
    private static LoginPanel loginpanel;
    private static MainPanel mainpanel;
    private static NewPrescrPanel prescription;
    private static MedicalRecordPanel medicalrec;
    private static ExaminationPanel examination;
    private static OldExam oldex;
    private static addUser adduser;
    private static AttachPanel attpanel;
    private static PharmalistPanel phpanel;
    private static JPanel panel;
    private static CardLayout cl;
    private static File dir;
    
    public static void createGui(){ //Creazione iniziale
        frame = new JFrame();
        panel = new JPanel();
        loginpanel = new LoginPanel();
        mainpanel = new MainPanel();
        prescription = new NewPrescrPanel();
        medicalrec = new MedicalRecordPanel();
        examination = new ExaminationPanel();
        oldex = new OldExam();
        adduser = new addUser();
        attpanel = new AttachPanel();
        phpanel = new PharmalistPanel();
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Ci pensa il listener poco più sotto
        frame.setSize(800, 600);
        frame.setTitle("GlaDoctor");
        
        WindowListener exitListener = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame)e.getSource();
                Object[] options = {"   SI   ","   NO   "};   //Il testo dei pulsanti
                
                int n = JOptionPane.showOptionDialog(frame,
                    "Uscire?",          //Testo finestra
                    "Conferma uscita",  //Titolo finestra
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,           //Eventuale icona
                    options,        //Le varie scente
                    options[0]);    //Valore selezionato inizialmente

                if (n == JOptionPane.YES_OPTION){
                    GuiController.rmdir(GuiController.getDir());
                    System.exit(0);
                }
            }
        };
        frame.addWindowListener(exitListener);
        
        //CardLayout: come in un mazzo di carte solo una di queste è visibile per volta
        panel.setLayout(new CardLayout());
        cl = (CardLayout)(panel.getLayout());
        frame.setContentPane(panel);
        /*Aggiunta di tutti i pannelli dell'applicazione 
        * che poi andranno resi visibili all'uopo (ma sempre uno alla volta)
        */
        panel.add(loginpanel, "login");     
        panel.add(mainpanel, "mainpanel");
        panel.add(prescription, "prescr");
        panel.add(medicalrec, "mr");
        panel.add(examination, "examination");
        panel.add(oldex,"oldex");
        panel.add(adduser, "adduser");
        panel.add(attpanel, "attpanel");
        panel.add(phpanel, "phpanel");
        
        frame.setVisible(true); //Rendi visibile frame contenitore
        /* Mostra il primo pannello (login).
         * Negli altri pannelli verrà gestito l'ordine in cui 
         * verranno mostrati i vari pannelli, AVENDO CURA di 
         * pulire quelli non più utilizzati ed inizializzare
         * quelli che si stanno per usare volta per volta.
         */
        cl.show(panel, "loginpanel");
    }
    
    public static File getDir(){
        return dir;
    }
    
public static void taskFail(final JLabel label){  //Trasforma una label in una TERIBBILE label ROSSA d'errore che ti fa star male con te stesso
    label.setVisible(true);
    label.setFont(new java.awt.Font("Ubuntu", 1, 24));
    label.setForeground(new java.awt.Color(255, 3, 0));
    label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));
    label.setText("   ERRORE   ");
}
    
public static void taskSuccess(final JLabel label){  //Trasforma una label in una SUCCOSA label VERDE di conferma che ti fa orgasmare
    label.setVisible(true);
    label.setFont(new java.awt.Font("Ubuntu", 1, 24));
    label.setForeground(new java.awt.Color(3, 203, 38));
    label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 203, 38), 2, true));
    label.setText("   OK   ");
}
    public static void makedir(){
        dir = new File("javafiles");
        if(!dir.mkdir()){
            System.out.println("Creazione cartella fallita");
        } else {
            System.out.println("Cartella creata");
        }
    }
    
    public static boolean rmdir(File dir){
        {
            if (dir.isDirectory())
            {
            String[] content = dir.list();
            for (int i=0; i<content.length; i++)
            {
                boolean success = rmdir(new File(dir, content[i]));
                if (!success) { return false; }
            }
            }
            return dir.delete();
        }
        
    }
    
    public static void adminButtonsInit(){
        if( !(SystemController.currentUser.isAdmin()) ){
            medicalrec.disableAdminButtons();
            mainpanel.disableAdminButtons();
            oldex.disableAdminButtons();
        }else{
            medicalrec.enableAdminButtons();
            mainpanel.enableAdminButtons();
            oldex.enableAdminButtons();
        }
    }
    
    public static void showMainPanel(){
        mainpanel.init();
        adminButtonsInit();
        cl.show(panel, "mainpanel");
        mainpanel.giveInputFocus();
    }
    public static void showLoginPanel(){
        loginpanel.init();
        cl.show(panel, "login");
    }
    
    public static void totalGuiReset(){    //Resetta tutti i campi di tutti i pannelli (non le error label)
        loginpanel.doResetLogin();
        examination.doResetEx();
        oldex.doOldExReset();
        mainpanel.doResetMP();
        medicalrec.doResetMR();
        prescription.doResetPrescr();
        adduser.doResetAddUser();
//        attpanel.doResetAtt();
        MainPanel.medrec = null;
        SystemController.currentUser = null;
        //TODO?
    }
    
    public static void showOldPresc(Prescription presc){
        prescription.initOld(presc);
        cl.show(panel,"prescr");
    }
    
    public static void showNewPresc(Prescription presc){
        prescription.initNew(presc);
        cl.show(panel,"prescr");
    }
    
    public static void showAttPanel(){
        attpanel.init();
        cl.show(panel,"attpanel");
    }
    
    public static void showPhPanel(File tosend){
        phpanel.init(tosend);
        cl.show(panel,"phpanel");
    }
        
    public static void showaddUser(){
        adduser.init();
        cl.show(panel,"adduser");
    }
    
    public static void showMRPanel(){
        medicalrec.init();
        cl.show(panel, "mr");
    }
    
    public static void showOldEx(int id){     //Apri visita esistente
        oldex.init(id);
        cl.show(panel, "oldex");
    }
    
    public static void showNewExam(){   //Apri una nuova visita
        examination.initNew();
        cl.show(panel,"examination");
    }
    
    public static void showAttList(){
        
    }
    
    public static String SHA1(byte[] hashme){
        String result = "";
        
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        hashme = md.digest(hashme);
        for (int i=0; i < hashme.length; i++){
            result += Integer.toString( (hashme[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}