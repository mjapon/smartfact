/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import smf.entity.Catcajas;

/**
 *
 * @author mjapon
 */
public class CajaComboBoxCellEditor extends DefaultCellEditor{
    
    public CajaComboBoxCellEditor(Catcajas[] cajas){
        super(new JComboBox<Catcajas>(cajas));
    }
    
    
}
