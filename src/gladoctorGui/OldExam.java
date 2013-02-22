/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;
import gladoctor.*;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
/**
 *
 * @author ste
 */
public class OldExam extends javax.swing.JPanel {

    /**
     * Creates new form OldExam
     */
    public OldExam() {
        initComponents();
    }
    
    public void init(int id){
        MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(id);
        errLabel2.setVisible(false);
        patName.setDocument (new JTextFieldLimit(16));
        docName.setDocument (new JTextFieldLimit(16));
        bDay.setDocument (new JTextFieldLimit(2));
        bMonth.setDocument (new JTextFieldLimit(2));
        bYear.setDocument (new JTextFieldLimit(4));
        
        docName.setText(MainPanel.currEx.getDoctor());
        patName.setText(MainPanel.currEx.getPatient());
        String temp[] = MainPanel.currEx.getDate().split("-");
        bDay.setText(temp[2]);
        bMonth.setText(temp[1]);
        bYear.setText(temp[0]);
        
        notes.setText(MainPanel.currEx.getNotes());
        loadCronDisList();
        disList.setSelectedItem(makeObj(MainPanel.currEx.getDisease()));
        loadAttList();
        loadPrescrList();
        disableFields();
                modEx.setText("Modifica");                                   //Cambia testo pulsante
                modEx.setFont(new java.awt.Font("Ubuntu", 0, 15));           //e stile
                modEx.removeMouseListener(modEx.getMouseListeners()[0]);  //Cambia evento associato
                modEx.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                modOldEx(evt);
                }
                });
                modEx.repaint();
                modEx.revalidate();
    }
    private Object makeObj(final String item){
            return new Object() {@Override
            public String toString() { return item; } };
        }
    
   public void enableFields(){
        notes.setEditable(true);
        disList.setEnabled(true);
        attAdd.setVisible(true);
        attRm.setVisible(true);
        prescrAdd.setVisible(true);
        prescrRm.setVisible(true);
    }
   public void disableAdminButtons(){
       modEx.setVisible(false);
   }
   
      public void enableAdminButtons(){
       modEx.setVisible(true);
   }
      
    public void disableFields(){
        patName.setEditable(false);
        docName.setEditable(false);
        bDay.setEditable(false);
        bMonth.setEditable(false);
        bYear.setEditable(false);
        notes.setEditable(false);
        disList.setEnabled(false);
    }
    private void loadCronDisList(){ //carica la lista generica di malattie
        String[] temp = MainPanel.medrec.getMainBoard().listDiseases(); //TODO: mettere il controllo d'errore ogni volta che si fanno chiamate del genere!!! E controllare che non ci siano valori nulli che vengono fuori dagli strati bassi del programma
        for(int i=0; i<temp.length; i++){
            disList.addItem(makeObj(temp[i]));
        }
        disList.repaint();
    }
    //******************************************
    private void doAttAdd(){
        int retval = -2;
        //Create a file chooser
        JFileChooser fc = new JFileChooser();
        retval = fc.showSaveDialog(null);
        if (retval == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            if(MainPanel.currEx.uploadFile(file.getAbsolutePath(), file.getName())){
                GuiController.taskSuccess(errLabel2);
                MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(MainPanel.currEx.getID());
                loadAttList();
            }else GuiController.taskFail(errLabel2);
        }
    }
    
    public void doOldExReset(){
        patName.setText("");
        docName.setText("");
        bDay.setText("");
        bMonth.setText("");
        bYear.setText("");
        notes.setText("");
    }
    
    private void doSaveExFields(){
        MainPanel.currEx.setDisease(disList.getSelectedItem().toString());
        if(notes.getText().equals("") || notes.getText().equals("Inserisci qui le tue note."))    
            MainPanel.currEx.setNotes("-");
        else MainPanel.currEx.setNotes(notes.getText());
        
        if(MainPanel.currEx.updateData()){
                GuiController.taskSuccess(errLabel2);
                /* Riblocca i campi, ricarica il medrec, riblocco i campi, ricarico i campi, reimposta tasto a modifica */
                disableFields();
               
                MainPanel.medrec = SystemController.currentUser.getMRById(MainPanel.medrec.getFiscalCode());
                init(MainPanel.currEx.getID());
                modEx.setText("Modifica");                                   //Cambia testo pulsante
                modEx.setFont(new java.awt.Font("Ubuntu", 0, 15));           //e stile
                modEx.removeMouseListener(modEx.getMouseListeners()[0]);  //Cambia evento associato
                modEx.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                modOldEx(evt);
                }
                });
                modEx.repaint();
                modEx.revalidate();
            }else{
                GuiController.taskFail(errLabel2);                  
        }
    }
    
    private void doAttRm(){
        String torm = attList.getValueAt(attList.getSelectedRow(), 0).toString();
        if(MainPanel.currEx != null && (torm != null && !torm.equals("-1")) ){
            MainPanel.currEx.deleteAttachment(Integer.parseInt(torm));
            MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(MainPanel.currEx.getID());
            loadAttList();
        }
    }
    
    private void doOpenAtt(){
        String topen = attList.getValueAt(attList.getSelectedRow(), 0).toString();
        if(MainPanel.currEx != null && (topen != null && !topen.equals("-1")) ){
        File fd = MainPanel.currEx.getAttachmentById(Integer.parseInt(topen));
           if(fd != null){ 
                Desktop desktop = Desktop.getDesktop();
                try{
                    desktop.open(fd); 
                } catch(Exception e){
                    System.out.println(e);
                }
           }
        }
    }
    //**************************************
    private void doOpenPresc(){
        String topen = prescrList.getValueAt(prescrList.getSelectedRow(), 0).toString();
        Prescription newPresc = MainPanel.currEx.getPrescriptionById(Integer.parseInt(topen));
        GuiController.showOldPresc(newPresc);
    }
    
    private void doPrescrAdd(){
        Prescription newPresc = MainPanel.currEx.createPrescription("prescription.txt",MainPanel.medrec.getDataSheet());
        GuiController.showNewPresc(newPresc);
        MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(MainPanel.currEx.getID());
        loadPrescrList();
    }
    
    private void doPrescrRm(){
        String torm = prescrList.getValueAt(prescrList.getSelectedRow(), 0).toString();
         if(MainPanel.currEx != null && (torm != null && !torm.equals("-1")) ){
            MainPanel.currEx.deletePrescription(Integer.parseInt(torm));
            MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(MainPanel.currEx.getID());
            loadPrescrList();
         }
    }
    
    private void loadAttList(){
    	if(MainPanel.currEx.getAttachments() != null){
	        Map<Integer,String> atts;
	        DefaultTableModel listmodel = (DefaultTableModel) attList.getModel();
	        listmodel.setRowCount(0);
	        attList.setModel(listmodel);
	        attList.repaint();
                
                atts = MainPanel.currEx.getAttachments();
	        if(atts != null){
	            Integer[] keys = new Integer[atts.size()];
	            String[] values = new String[atts.size()];
	            keys = atts.keySet().toArray(keys);
	            values = atts.values().toArray(values);
	
	            for (int j = 0; j < atts.size(); j++){
	                String[] data = new String[2];
	
	                data[0] = Integer.toString(keys[j]);
	                data[1] = values[j];
	                listmodel.addRow(data);
	            }
	            attList.setModel(listmodel);
	            attList.repaint();
	        }else GuiController.taskFail(errLabel2);
    	} else {
            DefaultTableModel listmodel = (DefaultTableModel) attList.getModel();
            listmodel.setRowCount(0);
            attList.setModel(listmodel);
            attList.repaint();
        }
    }
    
    private void loadPrescrList(){
    	if(MainPanel.currEx.getPrescriptions() != null){
	        Map<Integer,String> prescrs;
	        DefaultTableModel listmodel = (DefaultTableModel) prescrList.getModel();
	        listmodel.setRowCount(0);
	        prescrList.setModel(listmodel);
	        prescrList.repaint();
                
                prescrs = MainPanel.currEx.getPrescriptions();
	        if(prescrs != null){
	            Integer[] keys = new Integer[prescrs.size()];
	            String[] values = new String[prescrs.size()];
	            keys = prescrs.keySet().toArray(keys);
	            values = prescrs.values().toArray(values);
	
	            for (int j = 0; j < prescrs.size(); j++){
	                String[] data = new String[2];
	
	                data[0] = Integer.toString(keys[j]);
	                data[1] = values[j];
	                listmodel.addRow(data);
	            }
	            prescrList.setModel(listmodel);
	            prescrList.repaint();
	        }else GuiController.taskFail(errLabel2);
    	} else {
            DefaultTableModel listmodel = (DefaultTableModel) prescrList.getModel();
            listmodel.setRowCount(0);
            prescrList.setModel(listmodel);
            prescrList.repaint();
        }
    }
    
    private void doBackToMr(){
        doOldExReset();
        MainPanel.currEx = null;
        GuiController.showMRPanel();
    }
    
    private void doModOldEx(){
        enableFields();
        
        modEx.setText("Salva modifiche");                            //Cambia testo pulsante
        modEx.setFont(new java.awt.Font("Ubuntu", 1, 15));           //e stile
        modEx.removeMouseListener(modEx.getMouseListeners()[0]);  //Cambia evento associato
        modEx.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveExFields(evt);
            }
        });
        modEx.repaint();
        modEx.revalidate();
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
        jLabel5 = new javax.swing.JLabel();
        bDay = new javax.swing.JTextField();
        bMonth = new javax.swing.JTextField();
        bYear = new javax.swing.JTextField();
        docName = new javax.swing.JTextField();
        patName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        disList = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        errLabel2 = new javax.swing.JLabel();
        attAdd = new javax.swing.JButton();
        attRm = new javax.swing.JButton();
        attOpen = new javax.swing.JButton();
        prescrOpen = new javax.swing.JButton();
        prescrRm = new javax.swing.JButton();
        prescrAdd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        modEx = new javax.swing.JButton();
        backToMr = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        attList = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        prescrList = new javax.swing.JTable();

        jLabel1.setText("Nome medico:");

        jLabel5.setText("Data:");

        jLabel2.setText("Nome paziente:");

        jLabel4.setText("Patologia riscontrata:");

        disList.setModel(new javax.swing.DefaultComboBoxModel());

        notes.setColumns(20);
        notes.setRows(5);
        jScrollPane1.setViewportView(notes);

        jLabel6.setText("Note:");

        errLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        errLabel2.setForeground(new java.awt.Color(255, 3, 0));
        errLabel2.setText("ERRORE");
        errLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));

        attAdd.setText("+");
        attAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attAdd(evt);
            }
        });

        attRm.setText("-");
        attRm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attRm(evt);
            }
        });

        attOpen.setText("Apri");
        attOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attOpenopenAtt(evt);
            }
        });

        prescrOpen.setText("Apri");
        prescrOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openPresc(evt);
            }
        });

        prescrRm.setText("-");
        prescrRm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prescrRm(evt);
            }
        });

        prescrAdd.setText("+");
        prescrAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prescrAdd(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        modEx.setText("Modifica");
        modEx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modOldEx(evt);
            }
        });

        backToMr.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        backToMr.setText("<- Indietro");
        backToMr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToMr(evt);
            }
        });

        attList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Allegati"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attList.setColumnSelectionAllowed(true);
        attList.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(attList);
        attList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        attList.getColumnModel().getColumn(0).setResizable(false);
        attList.getColumnModel().getColumn(1).setResizable(false);
        attList.setCellSelectionEnabled(false);
        attList.setRowSelectionAllowed(true);

        prescrList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ricette"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        prescrList.setColumnSelectionAllowed(true);
        prescrList.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(prescrList);
        prescrList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        prescrList.getColumnModel().getColumn(0).setResizable(false);
        prescrList.getColumnModel().getColumn(1).setResizable(false);
        prescrList.setCellSelectionEnabled(false);
        prescrList.setRowSelectionAllowed(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(attOpen)
                                    .addComponent(attAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(attRm, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(prescrOpen)
                                    .addComponent(prescrRm, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(prescrAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(backToMr, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(modEx, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(errLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(312, 312, 312))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(749, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bDay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bYear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(docName, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patName, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disList, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(bYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(docName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(patName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(disList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(jSeparator2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(attAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(attRm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(attOpen)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(prescrAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prescrRm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prescrOpen)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modEx)
                    .addComponent(backToMr, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void saveExFields(java.awt.event.MouseEvent evt){
        doSaveExFields();
    }
    private void attAdd(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attAdd
        doAttAdd();
    }//GEN-LAST:event_attAdd

    private void attRm(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attRm
        doAttRm();
    }//GEN-LAST:event_attRm

    private void attOpenopenAtt(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attOpenopenAtt
        doOpenAtt();
    }//GEN-LAST:event_attOpenopenAtt

    private void prescrRm(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prescrRm
        doPrescrRm();
    }//GEN-LAST:event_prescrRm

    private void prescrAdd(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prescrAdd
        doPrescrAdd();
    }//GEN-LAST:event_prescrAdd

    private void modOldEx(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modOldEx
        doModOldEx();
    }//GEN-LAST:event_modOldEx

    private void backToMr(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToMr
        doBackToMr();
    }//GEN-LAST:event_backToMr

    private void openPresc(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openPresc
        doOpenPresc();
    }//GEN-LAST:event_openPresc

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attAdd;
    private javax.swing.JTable attList;
    private javax.swing.JButton attOpen;
    private javax.swing.JButton attRm;
    private javax.swing.JTextField bDay;
    private javax.swing.JTextField bMonth;
    private javax.swing.JTextField bYear;
    private javax.swing.JButton backToMr;
    private javax.swing.JComboBox disList;
    private javax.swing.JTextField docName;
    private javax.swing.JLabel errLabel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton modEx;
    private javax.swing.JTextArea notes;
    private javax.swing.JTextField patName;
    private javax.swing.JButton prescrAdd;
    private javax.swing.JTable prescrList;
    private javax.swing.JButton prescrOpen;
    private javax.swing.JButton prescrRm;
    // End of variables declaration//GEN-END:variables
}
