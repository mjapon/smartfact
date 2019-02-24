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
public class TicketRow {
    
    private Integer ticketId;
    private Integer cliId;
    private String cliCi;
    private String cliNombres;
    private String cliDireccion;
    private String cliTelf;
    private String fechaCreacion;
    private BigDecimal monto;
    private Integer tkNro;
    private String tkObs;
    private String tkServicios;
    private Integer factId;

    public TicketRow() {
    }
    
    

    public TicketRow(Integer ticketId, Integer cliId, String cliCi, String cliNombres, String cliDireccion, String fechaCreacion, BigDecimal monto, Integer tkNro, String tkObs, String cliTelf) {
        this.ticketId = ticketId;
        this.cliId = cliId;
        this.cliCi = cliCi;
        this.cliNombres = cliNombres;
        this.cliDireccion = cliDireccion;
        this.fechaCreacion = fechaCreacion;
        this.monto = monto;
        this.tkNro = tkNro;
        this.tkObs = tkObs;
        this.cliTelf = cliTelf;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getCliCi() {
        return cliCi;
    }

    public void setCliCi(String cliCi) {
        this.cliCi = cliCi;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getTkNro() {
        return tkNro;
    }

    public void setTkNro(Integer tkNro) {
        this.tkNro = tkNro;
    }

    public String getTkObs() {
        return tkObs;
    }

    public void setTkObs(String tkObs) {
        this.tkObs = tkObs;
    }

    public String getCliTelf() {
        return cliTelf;
    }

    public void setCliTelf(String cliTelf) {
        this.cliTelf = cliTelf;
    }

    public String getTkServicios() {
        return tkServicios;
    }

    public void setTkServicios(String tkServicios) {
        this.tkServicios = tkServicios;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }
    
    
    
    
}
