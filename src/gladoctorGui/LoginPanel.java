/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;
import gladoctor.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ste
 */
public class LoginPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = -6562689633395165465L;	//Controllo compatibilità
	/**
     * Creates new form Login
     */
    public LoginPanel() {
        initComponents();
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
        userField = new javax.swing.JTextField();
        passField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        doLogin = new javax.swing.JButton();
        resetLogin = new javax.swing.JButton();
        loginFail = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("Login");

        jLabel2.setText("Username:");

        passField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                login2(evt);
            }
        });

        jLabel3.setText("Password:");

        doLogin.setText("Entra");
        doLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                login(evt);
            }
        });

        resetLogin.setText("Reset");
        resetLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetLogin(evt);
            }
        });

        loginFail.setVisible(false);
        loginFail.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        loginFail.setForeground(new java.awt.Color(255, 0, 0));
        loginFail.setText("   LOGIN ERRATO");
        loginFail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gladoctorGui/logo-final.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(loginFail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(283, 283, 283)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(passField))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(9, 9, 9)
                                            .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(doLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(resetLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doLogin)
                    .addComponent(resetLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(loginFail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    @SuppressWarnings("deprecation")
	public void doLogin(){
        if (passField.getText().length() > 0 && userField.getText().length() > 0) {
            passField.setEnabled(false);
            userField.setEnabled(false);
            doLogin.setEnabled(false);
            resetLogin.setEnabled(false);
            
            if ( !(LogUser.login(userField.getText(), GuiController.SHA1(passField.getText().getBytes())) ) ){
                loginFail.setVisible(true);
                try{
                    Thread.sleep(3000);
                } catch(InterruptedException ex){
                    System.out.println(ex.getCause());
                }
                passField.setText("");
                userField.setText("");
                passField.setEnabled(true);
                userField.setEnabled(true);
                doLogin.setEnabled(true);
                resetLogin.setEnabled(true);
            }else{
                    passField.setText("");
                    userField.setText("");
                    GuiController.makedir();
                    GuiController.showMainPanel();
                }
            }
    }
    public void init(){
        doResetLogin();
        passField.setEnabled(true);
        userField.setEnabled(true);
        doLogin.setEnabled(true);
        resetLogin.setEnabled(true);
        loginFail.setVisible(false);
    }
    
    public void doResetLogin(){ //init qui si tratta solo di resettare i campi
        passField.setText("");
        userField.setText("");
    }
    private void login(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_login
        doLogin();
    }//GEN-LAST:event_login
    
    private void resetLogin(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetLogin
        doResetLogin();
    }//GEN-LAST:event_resetLogin

    private void login2(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_login2
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) doLogin();
    }//GEN-LAST:event_login2

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel loginFail;
    private javax.swing.JPasswordField passField;
    private javax.swing.JButton resetLogin;
    private javax.swing.JTextField userField;
    // End of variables declaration//GEN-END:variables
}
