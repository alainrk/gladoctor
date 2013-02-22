/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gladoctorGui;
import gladoctor.*;
import javax.swing.*;

public class ClientGUI{
    public static SystemController systemcontroller = new SystemController();
    public static GuiController guicontroller = new GuiController();
    
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args){

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("swing.boldMetal", Boolean.FALSE);
            }
            catch(UnsupportedLookAndFeelException ex) {ex.printStackTrace();}
            catch (IllegalAccessException ex){ex.printStackTrace();}
            catch (InstantiationException ex){ex.printStackTrace();}
            catch (ClassNotFoundException ex){ex.printStackTrace();}
            
            //No bold!!! TODO
            //Schedule a job for the event dispatch thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
                public void run(){
                    GuiController.createGui();
                }
            });
        }
}