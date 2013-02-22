/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;

import javax.swing.text.*;

/**
 *
 * @author ste
 */
/* Usa questa classe per impostare il limite di caratteri ad un jtextfield, così:
 * textfield.setDocument (new JTextFieldLimit(16));
 * 
 */
    
public class JTextFieldLimit extends PlainDocument {
	private static final long serialVersionUID = -2167683106613422213L;
private int limit;
  // optional uppercase conversion
  //private boolean toUppercase = false;
  
  JTextFieldLimit(int limit) {
   super();
   this.limit = limit;
   }
   
  JTextFieldLimit(int limit, boolean upper) {
   super();
   this.limit = limit;
   //toUppercase = upper;
   }
        @Override
  public void insertString
    (int offset, String  str, AttributeSet attr)
      throws BadLocationException {
   if (str == null) return;

   if ((getLength() + str.length()) <= limit) {
     //if (toUppercase) str = str.toUpperCase();
     super.insertString(offset, str, attr);
     }
   }
}
