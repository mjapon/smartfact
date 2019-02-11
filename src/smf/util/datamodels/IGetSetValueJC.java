/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

/**
 *
 * @author manuel.japon
 */
public interface IGetSetValueJC<RType>{
    public Object getValueAt(RType row, int rowIndex);
    public void setValueAt(RType row, int rowIndex, Object value);
    public boolean isCellEditable(RType row);
}
