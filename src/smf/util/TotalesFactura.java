/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.math.BigDecimal;

/**
 *
 * @author mjapon
 */
public class TotalesFactura {
    
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private BigDecimal descuento;
    private BigDecimal descuentoGlobal;
    private BigDecimal subtotal12;
    
    public TotalesFactura(){
        subtotal = BigDecimal.ZERO;
        iva = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        descuento = BigDecimal.ZERO;
        descuentoGlobal = BigDecimal.ZERO;
        subtotal12 = BigDecimal.ZERO;
    }

    public TotalesFactura(BigDecimal subtotal, BigDecimal iva, BigDecimal total, 
            BigDecimal descuento, BigDecimal descGlobal) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.descuento = descuento;
        this.descuentoGlobal = descGlobal;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getDescuentoGlobal() {
        return descuentoGlobal;
    }

    public void setDescuentoGlobal(BigDecimal descuentoGlobal) {
        this.descuentoGlobal = descuentoGlobal;
    }
    
    public void encerar(){
        subtotal = BigDecimal.ZERO;
        iva = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        descuento = BigDecimal.ZERO;
        descuentoGlobal = BigDecimal.ZERO;
        subtotal12 = BigDecimal.ZERO;
    }

    public BigDecimal getSubtotal12() {
        return subtotal12;
    }

    public void setSubtotal12(BigDecimal subtotal12) {
        this.subtotal12 = subtotal12;
    }
}