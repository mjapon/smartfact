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
public class FilaDatosCaja {
    
    private Integer catCajaId;
    private BigDecimal saldoAnt;
    private BigDecimal saldoInicial;
    private String nombreCaja;  
    
    private BigDecimal totalVentas;
    private BigDecimal totalCompras;
    private BigDecimal totalAbCobrados;
    private BigDecimal totalAbPagados;
    private BigDecimal valorAjuste;
    private BigDecimal saldoFinal;
    
    private Integer detCajaId;

    public FilaDatosCaja() {
    }

    public FilaDatosCaja(Integer catCajaId, BigDecimal saldoAnt, BigDecimal saldoInicial, String nombreCaja) {
        this.catCajaId = catCajaId;
        this.saldoAnt = saldoAnt;
        this.saldoInicial = saldoInicial;
        this.nombreCaja = nombreCaja;
    }

    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer detCajaId) {
        this.catCajaId = detCajaId;
    }

    public BigDecimal getSaldoAnt() {
        return saldoAnt;
    }

    public void setSaldoAnt(BigDecimal saldoAnt) {
        this.saldoAnt = saldoAnt;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public BigDecimal getTotalAbCobrados() {
        return totalAbCobrados;
    }

    public void setTotalAbCobrados(BigDecimal totalAbCobrados) {
        this.totalAbCobrados = totalAbCobrados;
    }

    public BigDecimal getTotalAbPagados() {
        return totalAbPagados;
    }

    public void setTotalAbPagados(BigDecimal totalAbPagados) {
        this.totalAbPagados = totalAbPagados;
    }

    public BigDecimal getValorAjuste() {
        return valorAjuste;
    }

    public void setValorAjuste(BigDecimal valorAjuste) {
        this.valorAjuste = valorAjuste;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public Integer getDetCajaId() {
        return detCajaId;
    }

    public void setDetCajaId(Integer detCajaId) {
        this.detCajaId = detCajaId;
    }

    public BigDecimal getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(BigDecimal totalCompras) {
        this.totalCompras = totalCompras;
    }
    
    
    
}
