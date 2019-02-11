/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.util.Date;

/**
 *
 * @author mjapon
 */
public class ParamsBuscaCaja {
    
    private Date desde;
    private Date hasta;
    private Integer catCajaId;

    public ParamsBuscaCaja() {
        
    }

    public ParamsBuscaCaja(Date desde, Date hasta, Integer catCajaId) {
        this.desde = desde;
        this.hasta = hasta;
        this.catCajaId = catCajaId;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }
    
    
    
}
