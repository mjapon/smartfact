/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;

/**
 *
 * @author manuel.japon
 */
public class TotalesMercaderia {
    
    private BigDecimal sumaPrecioCompra;
    private BigDecimal sumaPrecioVenta;
    private BigDecimal sumaPrecioVentaMin;
    private BigDecimal sumaInv;
    
    public TotalesMercaderia(){
        this.sumaPrecioCompra = BigDecimal.ZERO;
        this.sumaPrecioVenta = BigDecimal.ZERO;
        this.sumaPrecioVentaMin = BigDecimal.ZERO;
        this.sumaInv = BigDecimal.ZERO;
    }
    public TotalesMercaderia(BigDecimal sumaPrecioCompra, BigDecimal sumaPrecioVenta, BigDecimal sumaPrecioVentaMin, BigDecimal sumaInv) {
        this.sumaPrecioCompra = sumaPrecioCompra;
        this.sumaPrecioVenta = sumaPrecioVenta;
        this.sumaPrecioVentaMin = sumaPrecioVentaMin;
        this.sumaInv = sumaInv;
    }

    public BigDecimal getSumaPrecioCompra() {
        return sumaPrecioCompra;
    }

    public void setSumaPrecioCompra(BigDecimal sumaPrecioCompra) {
        this.sumaPrecioCompra = sumaPrecioCompra;
    }

    public BigDecimal getSumaPrecioVenta() {
        return sumaPrecioVenta;
    }

    public void setSumaPrecioVenta(BigDecimal sumaPrecioVenta) {
        this.sumaPrecioVenta = sumaPrecioVenta;
    }

    public BigDecimal getSumaPrecioVentaMin() {
        return sumaPrecioVentaMin;
    }

    public void setSumaPrecioVentaMin(BigDecimal sumaPrecioVentaMin) {
        this.sumaPrecioVentaMin = sumaPrecioVentaMin;
    }

    public BigDecimal getSumaInv() {
        return sumaInv;
    }

    public void setSumaInv(BigDecimal sumaInv) {
        this.sumaInv = sumaInv;
    }
}
