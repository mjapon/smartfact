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
public class FilaAbonos {
    
    private String fechaAbono;
    private BigDecimal monto;
    private String estado;
    private String observacion;
    private Integer movId;

    public FilaAbonos(Integer movId, String fechaAbono, BigDecimal monto, String estado, String observacion) {
        this.movId = movId;
        this.fechaAbono = fechaAbono;
        this.monto = monto;
        this.estado = estado;
        this.observacion = observacion;
    }

    public String getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(String fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getMovId() {
        return movId;
    }

    public void setMovId(Integer movId) {
        this.movId = movId;
    }
}