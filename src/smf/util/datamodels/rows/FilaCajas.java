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
public class FilaCajas {
    
    private Integer cj_id;
    private BigDecimal cj_saldoant;
    private BigDecimal cj_saldoantchanca;
    private BigDecimal cj_ventas;
    private BigDecimal cj_ventaschanca;
    private BigDecimal cj_abonoscxc;
    private BigDecimal cj_abonoscxp;
    private String cj_obsaper;
    private String cj_obscierre;
    private String cj_fecaper;
    private String cj_feccierre;
    private Integer cj_estado;
    private String estado;
    private String cj_obsanul;
    private BigDecimal cj_saldo;
    private BigDecimal cj_saldochanca;
    

    public FilaCajas(Integer cj_id, BigDecimal cj_saldoant, BigDecimal cj_ventas, BigDecimal cj_abonoscxc, BigDecimal cj_abonoscxp, String cj_obsaper, String cj_obscierre, String cj_fecaper, String cj_feccierre, Integer cj_estado, String estado, String cj_obsanul, BigDecimal cj_saldo,
            BigDecimal saldoAntChanca, BigDecimal ventaChanca, BigDecimal saldoChanca) {
        this.cj_id = cj_id;
        this.cj_saldoant = cj_saldoant;
        this.cj_ventas = cj_ventas;
        this.cj_abonoscxc = cj_abonoscxc;
        this.cj_abonoscxp = cj_abonoscxp;
        this.cj_obsaper = cj_obsaper;
        this.cj_obscierre = cj_obscierre;
        this.cj_fecaper = cj_fecaper;
        this.cj_feccierre = cj_feccierre;
        this.cj_estado = cj_estado;
        this.estado = estado;
        this.cj_obsanul = cj_obsanul;
        this.cj_saldo = cj_saldo;
        
        this.cj_saldoantchanca = saldoAntChanca;
        this.cj_ventaschanca = ventaChanca;
        this.cj_saldochanca = saldoChanca;
    }
    
    public Integer getCj_id() {
        return cj_id;
    }

    public void setCj_id(Integer cj_id) {
        this.cj_id = cj_id;
    }

    public BigDecimal getCj_saldoant() {
        return cj_saldoant;
    }

    public void setCj_saldoant(BigDecimal cj_saldoant) {
        this.cj_saldoant = cj_saldoant;
    }

    public BigDecimal getCj_ventas() {
        return cj_ventas;
    }

    public void setCj_ventas(BigDecimal cj_ventas) {
        this.cj_ventas = cj_ventas;
    }

    public BigDecimal getCj_abonoscxc() {
        return cj_abonoscxc;
    }

    public void setCj_abonoscxc(BigDecimal cj_abonoscxc) {
        this.cj_abonoscxc = cj_abonoscxc;
    }

    public BigDecimal getCj_abonoscxp() {
        return cj_abonoscxp;
    }

    public void setCj_abonoscxp(BigDecimal cj_abonoscxp) {
        this.cj_abonoscxp = cj_abonoscxp;
    }

    public String getCj_obsaper() {
        return cj_obsaper;
    }

    public void setCj_obsaper(String cj_obsaper) {
        this.cj_obsaper = cj_obsaper;
    }

    public String getCj_obscierre() {
        return cj_obscierre;
    }

    public void setCj_obscierre(String cj_obscierre) {
        this.cj_obscierre = cj_obscierre;
    }

    public String getCj_fecaper() {
        return cj_fecaper;
    }

    public void setCj_fecaper(String cj_fecaper) {
        this.cj_fecaper = cj_fecaper;
    }

    public String getCj_feccierre() {
        return cj_feccierre;
    }

    public void setCj_feccierre(String cj_feccierre) {
        this.cj_feccierre = cj_feccierre;
    }

    public Integer getCj_estado() {
        return cj_estado;
    }

    public void setCj_estado(Integer cj_estado) {
        this.cj_estado = cj_estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCj_obsanul() {
        return cj_obsanul;
    }

    public void setCj_obsanul(String cj_obsanul) {
        this.cj_obsanul = cj_obsanul;
    }

    public BigDecimal getCj_saldo() {
        return cj_saldo;
    }

    public void setCj_saldo(BigDecimal cj_saldo) {
        this.cj_saldo = cj_saldo;
    }

    public BigDecimal getCj_saldoantchanca() {
        return cj_saldoantchanca;
    }

    public void setCj_saldoantchanca(BigDecimal cj_saldoantchanca) {
        this.cj_saldoantchanca = cj_saldoantchanca;
    }

    public BigDecimal getCj_ventaschanca() {
        return cj_ventaschanca;
    }

    public void setCj_ventaschanca(BigDecimal cj_ventaschanca) {
        this.cj_ventaschanca = cj_ventaschanca;
    }

    public BigDecimal getCj_saldochanca() {
        return cj_saldochanca;
    }

    public void setCj_saldochanca(BigDecimal cj_saldochanca) {
        this.cj_saldochanca = cj_saldochanca;
    }
    
    
}
