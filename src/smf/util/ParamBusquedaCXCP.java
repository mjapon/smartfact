/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.util.Date;

/**
 *
 * @author mjapon
 */
public class ParamBusquedaCXCP {
    
    private Date desde;
    private Date hasta;
    private Integer cliId;
    private Integer artId;
    private Integer estadoPago;
    
    private String sortColumn;
    private String sortOrder;
    private Integer tra_codigo;
    
    private String filtro;
    private Integer cajaId;
    private boolean findByCaja;

    public ParamBusquedaCXCP() {
        
    }
    
    public void initForTransacc(Date desde, Date hasta, Integer traCodigo){
        setDesde(desde);
        setHasta(hasta);
        setTra_codigo(traCodigo);
        setArtId(0);
        setCliId(0);
    }

    public ParamBusquedaCXCP(Date desde, Date hasta, Integer cliId, Integer artId, 
            String sortColumn, String sortOrder, Integer estadoPago, Integer tra_codigo) {
        this.desde = desde;
        this.hasta = hasta;
        this.cliId = cliId;
        this.artId = artId;
        this.sortColumn = sortColumn;
        this.sortOrder = sortOrder;
        this.estadoPago = estadoPago;
        this.tra_codigo= tra_codigo;
    }
    

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(Integer estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Integer getTra_codigo() {
        return tra_codigo;
    }

    public void setTra_codigo(Integer tra_codigo) {
        this.tra_codigo = tra_codigo;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getCajaId() {
        return cajaId;
    }

    public void setCajaId(Integer cajaId) {
        this.cajaId = cajaId;
    }

    public boolean isFindByCaja() {
        return findByCaja;
    }

    public void setFindByCaja(boolean findByCaja) {
        this.findByCaja = findByCaja;
    }
    
    
    
    
}
