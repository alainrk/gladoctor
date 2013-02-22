/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;
import gladoctor.*;
import java.lang.String;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ste
 */
public class NewPrescrPanel extends javax.swing.JPanel {

   private static Prescription presc;
   
    public NewPrescrPanel() {
        initComponents();
    }

    public void initNew(Prescription prescr){
        errLabel2.setVisible(false);
        MainPanel.prescr = prescr;
        resetAllFields();
        loadPrescrFields();
        disableFields();
    }
    
    public void initOld(Prescription prescr){
        errLabel2.setVisible(false);
        MainPanel.prescr = prescr;
        resetAllFields();
        loadAllFields();
        disableAllFields();
    }

    private void loadAllFields(){
        patName.setText(MainPanel.prescr.getPatName());
        patSurname.setText(MainPanel.prescr.getPatSurname());
        address.setText(MainPanel.prescr.getPatAddress());
        cap.setText(MainPanel.prescr.getAreaCode());
        codFisc.setText(MainPanel.prescr.getFiscalCode());
        date.setText(MainPanel.prescr.getDate());
        exCod.setText(MainPanel.prescr.getExemptionCode());
        jur.setText(MainPanel.prescr.getJurisdiction());
        notes.setText(MainPanel.prescr.getNotes());
        pType.setText(MainPanel.prescr.getPresType());
        prio.setText(String.valueOf(MainPanel.prescr.getPriority()));
        tot.setText(Integer.toString(MainPanel.prescr.getTotal()));
        prescrArea.setText(MainPanel.prescr.getPrescriptions());
    }
    
    public void loadPrescrFields(){
        patName.setText(MainPanel.prescr.getPatName());
        patSurname.setText(MainPanel.prescr.getPatSurname());
        address.setText(MainPanel.prescr.getPatAddress());
        cap.setText(MainPanel.prescr.getAreaCode());
        codFisc.setText(MainPanel.prescr.getFiscalCode());
        date.setText(MainPanel.prescr.getDate());
        exCod.setText(MainPanel.prescr.getExemptionCode());
        jur.setText(MainPanel.prescr.getJurisdiction());
        notes.setText("Inserisci qui le tue note.");
        pType.setText("");
        prio.setText("");
        tot.setText("");
         prescrArea.setText("");
    }
    
    public void disableFields(){
        patName.setEditable(false);
        patSurname.setEditable(false);
        address.setEditable(false);
        cap.setEditable(false);
        codFisc.setEditable(false);
        date.setEditable(false);
        exCod.setEditable(false);
        jur.setEditable(false);
    }
    
    public void disableAllFields(){
        patName.setEditable(false);
        patSurname.setEditable(false);
        address.setEditable(false);
        cap.setEditable(false);
        codFisc.setEditable(false);
        date.setEditable(false);
        exCod.setEditable(false);
        jur.setEditable(false);
        notes.setEditable(false);
        pType.setEditable(false);
        prio.setEditable(false);
        tot.setEditable(false);
         prescrArea.setEditable(false);
    }
    
    public void resetAllFields(){
        patName.setText("");
        patSurname.setText("");
        address.setText("");
        cap.setText("");
        codFisc.setText("");
        date.setText("");
        exCod.setText("");
        jur.setText("");
        notes.setText("");
        pType.setText("");
        prio.setText("");
        tot.setText("");
        prescrArea.setText("");
    }
    
    public void doGoBack(){
        presc = null;
        resetAllFields();
        GuiController.showMRPanel();
    }
    
    public void doResetPrescr(){
        
    }
    
    public void doPrint(){
       makefile();
                        try {
            MainPanel.prescr.print();
                        } catch (Exception ex) {
                            Logger.getLogger(NewPrescrPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
       doGoBack();
    }
    
    public void doMail(){
        makefile();
        File tosend = MainPanel.currEx.getAttachmentById(MainPanel.prescr.getTruename());
        GuiController.showPhPanel(tosend);
        
    }
    
    private void makefile(){
        if(pType.getText().length() > 0 && prio.getText().length() == 1 && tot.getText().length() > 0 && prescrArea.getText().length() > 0){
            if(notes.getText().equals("") || notes.getText().equals("Inserisci le tue note."))
                    MainPanel.prescr.setNotes("-");
            else MainPanel.prescr.setNotes(notes.getText());
            MainPanel.prescr.setPresType(pType.getText());
            MainPanel.prescr.setPriority(prio.getText().charAt(0));
            MainPanel.prescr.setTotal(Integer.parseInt(tot.getText()));
            MainPanel.prescr.setPrescriptions(prescrArea.getText());
            MainPanel.currEx.deletePrescription(MainPanel.prescr.getTruename());
            MainPanel.currEx.sendPrescriptionData(MainPanel.prescr);
        } else
            System.out.println("Inserisci i campi giusti");    
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jur = new javax.swing.JTextField();
        patName = new javax.swing.JTextField();
        patSurname = new javax.swing.JTextField();
        codFisc = new javax.swing.JTextField();
        cap = new javax.swing.JTextField();
        prio = new javax.swing.JTextField();
        exCod = new javax.swing.JTextField();
        pType = new javax.swing.JTextField();
        tot = new javax.swing.JTextField();
        date = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        prescrArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        errLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nuova ricetta");

        jLabel2.setText("Giurisdizione:");

        jLabel3.setText("Nome paziente:");

        jLabel4.setText("Cognome paziente:");

        jLabel5.setText("Cod. fiscale:");

        jLabel6.setText("CAP:");

        jLabel7.setText("Priorita':");

        jLabel8.setText("Cod. esenzione:");

        jLabel9.setText("Tipo ricetta:");

        jLabel10.setText("Totale:");

        jLabel11.setText("Note:");

        jLabel12.setText("Data:");

        jLabel13.setText("Indirizzo:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        notes.setColumns(20);
        notes.setRows(5);
        jScrollPane1.setViewportView(notes);

        jLabel14.setText("Ricetta:");

        prescrArea.setColumns(20);
        prescrArea.setRows(5);
        jScrollPane2.setViewportView(prescrArea);

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("<- Indietro");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goBack(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton2.setText("Stampa");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                print(evt);
            }
        });

        jButton3.setText("Invia mail");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mail(evt);
            }
        });

        errLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        errLabel2.setForeground(new java.awt.Color(255, 3, 0));
        errLabel2.setText("ERRORE");
        errLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(exCod)
                                            .addComponent(pType)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(63, 63, 63)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(prio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cap, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(codFisc)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jLabel10)
                                                .addGap(90, 90, 90)
                                                .addComponent(tot))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jur, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addGap(285, 285, 285)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(patSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(38, 38, 38)
                                                .addComponent(patName, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(29, 29, 29)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel13))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel11))))
                                        .addGap(15, 15, 15))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(patName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(patSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(codFisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(prio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(exCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(pType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(tot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void goBack(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBack
        doGoBack();
    }//GEN-LAST:event_goBack

    private void print(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_print
        doPrint();
    }//GEN-LAST:event_print

    private void mail(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mail
        doMail();
    }//GEN-LAST:event_mail

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address;
    private javax.swing.JTextField cap;
    private javax.swing.JTextField codFisc;
    private javax.swing.JTextField date;
    private javax.swing.JLabel errLabel2;
    private javax.swing.JTextField exCod;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jur;
    private javax.swing.JTextArea notes;
    private javax.swing.JTextField pType;
    private javax.swing.JTextField patName;
    private javax.swing.JTextField patSurname;
    private javax.swing.JTextArea prescrArea;
    private javax.swing.JTextField prio;
    private javax.swing.JTextField tot;
    // End of variables declaration//GEN-END:variables
}
