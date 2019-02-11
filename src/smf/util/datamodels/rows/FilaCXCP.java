/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;

/**
 *
 * @author mjapon
 */
public class FilaCXCP {
    
    private Integer codFactura;
    private String fechaFactura;
    private String numFactura;
    private BigDecimal montoFactura;
    private BigDecimal monto;
    private BigDecimal deuda;
    private String referente;
    private String observacion;
    private String fecha;
    private Integer codPago;
    private String estadoDesc;
    private Integer movId;

    public FilaCXCP(Integer codFactura, String numFactura, BigDecimal monto, 
            BigDecimal deuda, String referente, String obs, String fecha, 
            Integer codPago, String estadoDesc, BigDecimal montoFactura, Integer movId) {
        this.codFactura = codFactura;
        this.numFactura = numFactura;
        this.monto = monto;
        this.deuda = deuda;
        this.referente = referente;
        this.observacion = obs;
        this.fecha = fecha;
        this.codPago = codPago;
        this.estadoDesc = estadoDesc;
        this.montoFactura = montoFactura;
        this.movId = movId;
    }   
    

    public Integer getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(Integer codFactura) {
        this.codFactura = codFactura;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCodPago() {
        return codPago;
    }

    public void setCodPago(Integer codPago) {
        this.codPago = codPago;
    }

    public String getEstadoDesc() {
        return estadoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public BigDecimal getMontoFactura() {
        return montoFactura;
    }

    public void setMontoFactura(BigDecimal montoFactura) {
        this.montoFactura = montoFactura;
    }    

    public Integer getMovId() {
        return movId;
    }

    public void setMovId(Integer movId) {
        this.movId = movId;
    }
    
}
