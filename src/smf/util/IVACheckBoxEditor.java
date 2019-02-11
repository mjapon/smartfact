/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
/**
 *
 * @author manuel.japon
 */
public class IVACheckBoxEditor extends DefaultCellEditor {
    
  public IVACheckBoxEditor(boolean value) {
    super(new JCheckBox("", value) );
  }
}
