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
public abstract class DefGSVCol<RowType> implements IGetSetValueJC<RowType>{

    /*
    @Override
    public Object getValueAt(RowType row, int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */

    @Override
    public void setValueAt(RowType row, int rowIndex, Object value) {
        
    }

    @Override
    public boolean isCellEditable(RowType row) {
        return false;
    }


   
    
    
    
}
