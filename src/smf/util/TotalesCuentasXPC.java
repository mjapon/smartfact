/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author mjapon
 */
public class TotalesCuentasXPC {
    
    private BigDecimal sumaMonto;
    private BigDecimal sumaSaldoPend;
    private Map<Integer, BigDecimal> totalByCajaMap;
    
    public TotalesCuentasXPC(){
        
    }

    public TotalesCuentasXPC(BigDecimal sumaMonto, BigDecimal sumaSaldoPend) {
        this.sumaMonto = sumaMonto;
        this.sumaSaldoPend = sumaSaldoPend;
    }

    public BigDecimal getSumaMonto() {
        return sumaMonto;
    }

    public void setSumaMonto(BigDecimal sumaMonto) {
        this.sumaMonto = sumaMonto;
    }

    public BigDecimal getSumaSaldoPend() {
        return sumaSaldoPend;
    }

    public void setSumaSaldoPend(BigDecimal sumaSaldoPend) {
        this.sumaSaldoPend = sumaSaldoPend;
    }

    public Map<Integer, BigDecimal> getTotalByCajaMap() {
        return totalByCajaMap;
    }

    public void setTotalByCajaMap(Map<Integer, BigDecimal> totalByCajaMap) {
        this.totalByCajaMap = totalByCajaMap;
    }
    
    
}
