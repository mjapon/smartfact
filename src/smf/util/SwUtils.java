/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import javax.swing.JTextField;

/**
 *
 * @author manuel.japon
 */
public class SwUtils {
    
    public static boolean TF_EMPTY(JTextField textfield){
        boolean result = false;
        if (textfield !=null && textfield.getText()!=null){
            result = textfield.getText().trim().length() == 0;
        }
        return result;
    }
    
    
}
