/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;

import gladoctor.SystemController;
import java.util.Map;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import gladoctor.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ste
 */
public class PharmalistPanel extends javax.swing.JPanel {

    /**
     * Creates new form PharmalistPanel
     */
    public PharmalistPanel() {
        initComponents();
    }
    
    private Pharmalist phl;

    private void loadPhList(){
        if(MainPanel.prescr != null){
	        Map<Integer,String> list;
                
	        DefaultTableModel listmodel = (DefaultTableModel) phList.getModel();
	        listmodel.setRowCount(0);
	        phList.setModel(listmodel);
	        phList.repaint();
                
                phl = SystemController.currentUser.getPharmalist();
                list = phl.getList();
                
	        if(list != null){
	            Integer[] keys = new Integer[list.size()];
	            String[] values = new String[list.size()];
	            keys = list.keySet().toArray(keys);
	            values = list.values().toArray(values);
	
	            for (int j = 0; j < list.size(); j++){
	                String[] data = new String[2];
	
	                data[0] = Integer.toString(keys[j]);
	                data[1] = values[j];
	                listmodel.addRow(data);
	            }
	            phList.setModel(listmodel);
	            phList.repaint();
	        }else GuiController.taskFail(errLabel2);
    	} else {
            DefaultTableModel listmodel = (DefaultTableModel) phList.getModel();
            listmodel.setRowCount(0);
            phList.setModel(listmodel);
            phList.repaint();
        }
    }
    
    public void init(File tosend){
        errLabel2.setVisible(false);
        loadPhList();
    }
    
    private void doGoBack(){
        
        GuiController.showNewPresc(MainPanel.prescr);
    }
    private void doSendMail(){
        String mambo = phList.getValueAt(phList.getSelectedRow(), 0).toString();
        Pharmacist tosend = phl.getPharmacistById(Integer.parseInt(mambo));
        //tosend.sendMail(MainPanel.prescr); //MAIL AL FARMACISTA
                        JOptionPane.showOptionDialog(null,
                    "Mail inviata a "+tosend.getMail(),          //Testo finestra
                    "Mail",  //Titolo finestra
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,           //Eventuale icona
                    null,        //Le varie scente
                    null);    //Valore selezionato inizialmente
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        phList = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        errLabel2 = new javax.swing.JLabel();

        phList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        phList.setColumnSelectionAllowed(true);
        phList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(phList);
        phList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        phList.getColumnModel().getColumn(0).setResizable(false);
        phList.getColumnModel().getColumn(1).setResizable(false);

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("<- Indietro");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goBack(evt);
            }
        });

        jButton2.setText("Invia");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendMail(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("Scegli farmacista");

        errLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        errLabel2.setForeground(new java.awt.Color(255, 3, 0));
        errLabel2.setText("ERRORE");
        errLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(errLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                .addComponent(errLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void goBack(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBack
        doGoBack();
    }//GEN-LAST:event_goBack

    private void sendMail(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendMail
        doSendMail();
    }//GEN-LAST:event_sendMail

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errLabel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable phList;
    // End of variables declaration//GEN-END:variables
}
