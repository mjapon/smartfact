/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author mjapon
 */
public class DescComboBoxRenderer extends JComboBox implements TableCellRenderer {
    private JComboBox jComboBox;
    public DescComboBoxRenderer(String[] items) {
        super(items);
        setEditable(true);
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected,
            boolean hasFocus, int row, int column) {
        if (isSelected) {
          setForeground(table.getSelectionForeground());
          super.setBackground(table.getSelectionBackground());
        } else {
          setForeground(table.getForeground());
          setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
      }
}