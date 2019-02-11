/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

/**
 *
 * @author mjapon
 */
public class DatosCabeceraFactura {
    
    private String nroEstFact;
    private String numFactura;    
    private Integer cliId;
    private String ci;
    private String cliente;
    private String direccion;
    private String telf;
    private String email;    
    private String fechaFactura;
    private Integer traCodigo;

    public String getNroEstFact() {
        return nroEstFact;
    }

    public void setNroEstFact(String nroEstFact) {
        this.nroEstFact = nroEstFact;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Integer getTraCodigo() {
        return traCodigo;
    }

    public void setTraCodigo(Integer traCodigo) {
        this.traCodigo = traCodigo;
    }
    
    
}
