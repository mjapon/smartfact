/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author mjapon
 */
public class TotalesVentasModel {
    
    private BigDecimal sumaIva = BigDecimal.ZERO;
    private BigDecimal sumaDesc= BigDecimal.ZERO;
    private BigDecimal sumaTotal= BigDecimal.ZERO;
    private BigDecimal utilidades = BigDecimal.ZERO;
    private BigDecimal sumaEfectivo = BigDecimal.ZERO;
    private BigDecimal sumaCredito = BigDecimal.ZERO;
    private BigDecimal sumaSaldo = BigDecimal.ZERO;
    
    private Map<Integer,BigDecimal> utilidadesMapEfectivo; 
    private Map<Integer,BigDecimal> utilidadesMapCredito; 
    
    private Map<Integer,BigDecimal> sumasCajaMap;

    public TotalesVentasModel() {
       
    }

    public BigDecimal getSumaIva() {
        return sumaIva;
    }

    public void setSumaIva(BigDecimal sumaIva) {
        this.sumaIva = sumaIva;
    }

    public BigDecimal getSumaDesc() {
        return sumaDesc;
    }

    public void setSumaDesc(BigDecimal sumaDesc) {
        this.sumaDesc = sumaDesc;
    }

    public BigDecimal getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(BigDecimal sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public BigDecimal getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(BigDecimal utilidades) {
        this.utilidades = utilidades;
    }

    public BigDecimal getSumaEfectivo() {
        return sumaEfectivo;
    }

    public void setSumaEfectivo(BigDecimal sumaEfectivo) {
        this.sumaEfectivo = sumaEfectivo;
    }

    public BigDecimal getSumaCredito() {
        return sumaCredito;
    }

    public void setSumaCredito(BigDecimal sumaCredito) {
        this.sumaCredito = sumaCredito;
    }
    public BigDecimal getSumaSaldo() {
        return sumaSaldo;
    }
    public void setSumaSaldo(BigDecimal sumaSaldo) {
        this.sumaSaldo = sumaSaldo;
    }

    public Map<Integer, BigDecimal> getSumasCajaMap() {
        return sumasCajaMap;
    }

    public void setSumasCajaMap(Map<Integer, BigDecimal> sumasCajaMap) {
        this.sumasCajaMap = sumasCajaMap;
    }

    public Map<Integer, BigDecimal> getUtilidadesMapEfectivo() {
        return utilidadesMapEfectivo;
    }

    public void setUtilidadesMapEfectivo(Map<Integer, BigDecimal> utilidadesMapEfectivo) {
        this.utilidadesMapEfectivo = utilidadesMapEfectivo;
    }

    public Map<Integer, BigDecimal> getUtilidadesMapCredito() {
        return utilidadesMapCredito;
    }

    public void setUtilidadesMapCredito(Map<Integer, BigDecimal> utilidadesMapCredito) {
        this.utilidadesMapCredito = utilidadesMapCredito;
    }
    
    
     
    
}
