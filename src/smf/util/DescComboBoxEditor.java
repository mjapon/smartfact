/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author mjapon
 */
public class DescComboBoxEditor extends DefaultCellEditor {    
    private JComboBox combobox;
    public DescComboBoxEditor(String[] items) {
        super( new JComboBox(items) );
        combobox = (JComboBox)this.editorComponent;
        combobox.setEditable(true);
    }
  
    public DescComboBoxEditor(JComboBox combo) {
        super( combo );
    }
  
    public void updateCombo(String[] values){
        this.combobox.removeAllItems();
        for (String value: values){
            this.combobox.addItem(value);
        }
    }
    
    public void setSelectedItem(Object value){
        this.combobox.setSelectedItem(value);
        
    }
}