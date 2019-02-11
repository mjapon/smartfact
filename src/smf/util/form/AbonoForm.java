/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.form;

import java.math.BigDecimal;

/**
 *
 * @author mjapon
 */
public class AbonoForm {
    
    private Integer pagoId;
    private BigDecimal monto;
    private String observacion;
    private Integer factId;
    private Integer traId;
    private Integer cajaId;
    

    public AbonoForm(Integer pagoId, BigDecimal monto, String observacion, 
            Integer factId, Integer traId, Integer cajaId) {
        this.pagoId = pagoId;
        this.monto = monto;
        this.observacion = observacion;
        this.factId = factId;
        this.traId = traId;
        this.cajaId = cajaId;
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }

    public Integer getTraId() {
        return traId;
    }

    public void setTraId(Integer traId) {
        this.traId = traId;
    }

    public Integer getCajaId() {
        return cajaId;
    }

    public void setCajaId(Integer cajaId) {
        this.cajaId = cajaId;
    }
    
}
