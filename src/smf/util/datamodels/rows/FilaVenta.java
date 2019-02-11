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
public class FilaVenta {    
    private Integer ventaId;
    private String nro;
    private String cliente;
    private String ciruc;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal descuento;
    private BigDecimal total;
    
    private BigDecimal efectivo;
    private BigDecimal credito;
    private BigDecimal saldo;
    //private Integer catCajaId;
    
    private BigDecimal utilidad;
    
    private String fechaReg;

    public FilaVenta(Integer ventaId, String nro, String cliente, String ciruc, 
            BigDecimal iva, BigDecimal descuento, BigDecimal total, 
            BigDecimal subtotal, String fechaReg, BigDecimal efectivo, BigDecimal credito, BigDecimal utilidad) {
        this.ventaId = ventaId;
        this.nro = nro;
        this.cliente = cliente;
        this.ciruc = ciruc;
        this.iva = iva;
        this.descuento = descuento;
        this.total = total;
        this.fechaReg = fechaReg;
        this.subtotal = subtotal;
        this.efectivo = efectivo;
        this.credito = credito;
        this.utilidad = utilidad;
    }

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCiruc() {
        return ciruc;
    }

    public void setCiruc(String ciruc) {
        this.ciruc = ciruc;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(String fechaReg) {
        this.fechaReg = fechaReg;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }
    
    /*
    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }
    */
    
}
