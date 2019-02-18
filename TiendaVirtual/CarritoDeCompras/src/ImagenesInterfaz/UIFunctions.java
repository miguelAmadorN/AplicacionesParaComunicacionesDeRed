package ImagenesInterfaz;

import static java.awt.Frame.ICONIFIED;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class UIFunctions {
    
    public static void closeWindow(){
        System.exit(0);
    }
    
    public static void minimizeWindow(JFrame frame){
        frame.setExtendedState(ICONIFIED);
    }    

    public static void informationMessage(String message, String window){
          JOptionPane.showMessageDialog(null, message, window, JOptionPane.INFORMATION_MESSAGE);
    }
    
   public static void warningMessage(String message, String window){
          JOptionPane.showMessageDialog(null,message,window, JOptionPane.WARNING_MESSAGE);
    }
   
   
    
}
