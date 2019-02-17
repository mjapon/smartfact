/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.facte;

import java.math.BigDecimal;

/**
 *
 * @author mjapon
 */
public interface IFacturaEdit {
    
    public void updateLabelsTotales();
    public BigDecimal getDescuentoGlobal();
    public void setValueDescGlobal(BigDecimal value);
    
    
}
