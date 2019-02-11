/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.form;

import java.math.BigDecimal;

/**
 *
 * @author manuel.japon
 */
public class AbonoCXCPForm {
    
    private BigDecimal movMonto;
    private String movObserv;
    private Integer movValido;
    private Integer factIdRel;
    private Integer traId;
    private Integer pgfId;

    public AbonoCXCPForm(BigDecimal movMonto, String movObserv, Integer movValido, Integer factIdRel, Integer traId, Integer pgfId) {
        this.movMonto = movMonto;
        this.movObserv = movObserv;
        this.movValido = movValido;
        this.factIdRel = factIdRel;
        this.traId = traId;
        this.pgfId = pgfId;
    }

    public BigDecimal getMovMonto() {
        return movMonto;
    }

    public void setMovMonto(BigDecimal movMonto) {
        this.movMonto = movMonto;
    }

    public String getMovObserv() {
        return movObserv;
    }

    public void setMovObserv(String movObserv) {
        this.movObserv = movObserv;
    }

    public Integer getMovValido() {
        return movValido;
    }

    public void setMovValido(Integer movValido) {
        this.movValido = movValido;
    }

    public Integer getFactIdRel() {
        return factIdRel;
    }

    public void setFactIdRel(Integer factIdRel) {
        this.factIdRel = factIdRel;
    }

    public Integer getTraId() {
        return traId;
    }

    public void setTraId(Integer traId) {
        this.traId = traId;
    }

    public Integer getPgfId() {
        return pgfId;
    }

    public void setPgfId(Integer pgfId) {
        this.pgfId = pgfId;
    }   
            
}
