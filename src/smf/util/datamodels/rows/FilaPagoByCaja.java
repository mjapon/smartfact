/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;

/**
 * Para registro de pagos en efectivo de una factura por caja (para el caso de pagos en efectivo y credito)
 * @author mjapon
 */
public class FilaPagoByCaja {
    
    private Integer catCajaId;
    private String caja;
    private BigDecimal monto;

    public FilaPagoByCaja() {
    }

    public FilaPagoByCaja(Integer catCajaId, String caja, BigDecimal monto) {
        this.catCajaId = catCajaId;
        this.caja = caja;
        this.monto = monto;
    }
    
    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}