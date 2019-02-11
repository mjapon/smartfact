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
public class ParamsBuscaTickets {
    
    private Date desde;
    private Date hasta;
    private String filtro;
    
    public ParamsBuscaTickets(Date desde, Date hasta, String filtro) {
        this.desde = desde;
        this.hasta = hasta;
        this.filtro = filtro;
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

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    
    
    
    
    
}
