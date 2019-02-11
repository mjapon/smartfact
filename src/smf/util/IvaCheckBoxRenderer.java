/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author manuel.japon
 */
public class IvaCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
  public IvaCheckBoxRenderer(boolean defValue) {
    super("", defValue);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
      
    /*
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    */
    
    setForeground(table.getForeground());
    setBackground(table.getBackground());
    
    //setSelectedItem(value);
    return this;
  }
    
}
