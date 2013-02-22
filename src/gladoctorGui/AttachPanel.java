/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;

import gladoctor.*;
import gladoctor.SystemController;
import java.io.File;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ste
 */
public class AttachPanel extends javax.swing.JPanel {

    /**
     * Creates new form AttachPanel
     */
    public AttachPanel() {
        initComponents();
    }
    
    public void init(){
        errLabel2.setVisible(false);
        loadPrescrList();
        loadAttList();
    }

    public void disableFields(){
        attAdd.setVisible(false);
        attRm.setVisible(false);
        prescrAdd.setVisible(false);
        prescrRm.setVisible(false);
        prescrMod.setVisible(false);
    }
    
    public void enableFields(){
        attAdd.setVisible(true);
        attRm.setVisible(true);
        prescrAdd.setVisible(true);
        prescrRm.setVisible(true);
        prescrMod.setVisible(true);
    }
    private void doSaveFinalEx(){
        GuiController.showMRPanel();
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
    
    private void doAttRm(){
        String torm = attList.getValueAt(attList.getSelectedRow(), 0).toString();
        if(MainPanel.currEx != null && (torm != null && !torm.equals("-1")) ){
            MainPanel.currEx.deleteAttachment(Integer.parseInt(torm));
            MainPanel.currEx = MainPanel.medrec.getMainBoard().getExaminationById(MainPanel.currEx.getID());
            loadAttList();
        }
    }
    
    private void doOpenAtt(){
        //TODO
    }
    //**************************************
    private void doPrescrAdd(){
        Prescription newPresc = MainPanel.currEx.createPrescription("prescription.txt",MainPanel.medrec.getDataSheet());
        GuiController.showNewPresc(newPresc);
        loadPrescrList();
    }
    
    private void doPrescRm(){
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
    	}
    }
    
    private void doCancelAtt(){
        MainPanel.medrec.getMainBoard().deleteExamination(MainPanel.currEx.getID());
        GuiController.showMRPanel();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        attList = new javax.swing.JTable();
        attAdd = new javax.swing.JButton();
        attRm = new javax.swing.JButton();
        prescrAdd = new javax.swing.JButton();
        prescrRm = new javax.swing.JButton();
        prescrMod = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        prescrList = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        errLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

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
        attList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        attList.getColumnModel().getColumn(0).setResizable(false);
        attList.getColumnModel().getColumn(1).setResizable(false);
        attList.setCellSelectionEnabled(false);
        attList.setRowSelectionAllowed(true);

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

        prescrAdd.setText("+");
        prescrAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prescrAdd(evt);
            }
        });

        prescrRm.setText("-");
        prescrRm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prescrRm(evt);
            }
        });

        prescrMod.setText("Modifica");
        prescrMod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prescrMod(evt);
            }
        });

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

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        errLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        errLabel2.setForeground(new java.awt.Color(255, 3, 0));
        errLabel2.setText("ERRORE");
        errLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel7.setText("Allegati e ricette");

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("Salva");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                finalSaveEx(evt);
            }
        });

        jButton2.setText("Annulla");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelAtt(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(attAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attRm, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prescrAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prescrRm, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prescrMod, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(errLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(attAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(attRm))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(prescrAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prescrRm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prescrMod))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void attAdd(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attAdd
        doAttAdd();
    }//GEN-LAST:event_attAdd

    private void attRm(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attRm
        doAttRm();
    }//GEN-LAST:event_attRm

    private void prescrAdd(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prescrAdd
        doPrescrAdd();
    }//GEN-LAST:event_prescrAdd

    private void prescrRm(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prescrRm
        doPrescRm();
    }//GEN-LAST:event_prescrRm

    private void prescrMod(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prescrMod
        // TODO add your handling code here:
    }//GEN-LAST:event_prescrMod

    private void finalSaveEx(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_finalSaveEx
        doSaveFinalEx();
    }//GEN-LAST:event_finalSaveEx

    private void cancelAtt(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelAtt
        doCancelAtt();
    }//GEN-LAST:event_cancelAtt

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attAdd;
    private javax.swing.JTable attList;
    private javax.swing.JButton attRm;
    private javax.swing.JLabel errLabel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton prescrAdd;
    private javax.swing.JTable prescrList;
    private javax.swing.JButton prescrMod;
    private javax.swing.JButton prescrRm;
    // End of variables declaration//GEN-END:variables
}
