/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.merc.unid;

import java.math.BigDecimal;
import smf.util.datamodels.rows.FilaFactura;
import smf.util.datamodels.rows.FilaUnidadPrecio;

/**
 *
 * @author mjapon
 */
public interface IListenerSelectUnity {
    
    public void doSelect(FilaUnidadPrecio filaUnidadPrecio);
    
    public void doChangeDescuento(FilaFactura filaFactura);
    
    public void doChangePrecio(BigDecimal newPrecio);
    
}
