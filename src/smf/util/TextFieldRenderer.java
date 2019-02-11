/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author mjapon
 */
public class TextFieldRenderer extends JTextField implements TableCellRenderer{
    
    public TextFieldRenderer(){
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        /*
        //this.tablecell2_column = column;
        //this.value = value;
        //this.setFont(null);
        System.out.println(">>tablecell2>>");
        if (value != null) {
            setText(value.toString());
        }

        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }

            private void jTextField1FocusLost(FocusEvent evt) {
                //setText(value.toString());
                // new setResults().getbyclass(jTable1, ClassCombo.getSelectedItem().toString(), acadYr, term, subjectCombo.getSelectedItem().toString());
                //JOptionPane.showMessageDialog(currentCell, "focus");
            }
        });
        */
        
        return this;
    }
}
