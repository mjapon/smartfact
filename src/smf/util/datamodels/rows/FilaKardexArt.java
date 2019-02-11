/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;

/**
 *
 * @author manuel.japon
 */
public class FilaKardexArt {
    
    private String fechaRegistro;
    private String concepto;
    private String referencia;
    private BigDecimal cantidad;
    private BigDecimal valorAnt;
    private BigDecimal valorAct;

    public FilaKardexArt(String fechaRegistro, String concepto, String referencia, BigDecimal cantidad, BigDecimal valorAnt, BigDecimal valorAct) {
        this.fechaRegistro = fechaRegistro;
        this.concepto = concepto;
        this.referencia = referencia;
        this.cantidad = cantidad;
        this.valorAnt = valorAnt;
        this.valorAct = valorAct;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorAnt() {
        return valorAnt;
    }

    public void setValorAnt(BigDecimal valorAnt) {
        this.valorAnt = valorAnt;
    }

    public BigDecimal getValorAct() {
        return valorAct;
    }

    public void setValorAct(BigDecimal valorAct) {
        this.valorAct = valorAct;
    }
}