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
public class ParamsBusquedaTransacc {
    
    private Date desde;
    private Date hasta;
    private Integer cliId;
    private Integer artId;
    
    private String sortColumn;
    private String sortOrder;
    private Integer traCodigo;
    private String filtro;
    private boolean usarFechaHora;
    private boolean byCat;
    private Integer catCajaId;

    public ParamsBusquedaTransacc() {
    }
    
    public void initForTransacc(Date desde, Date hasta, Integer traCodigo){
        setDesde(desde);
        setHasta(hasta);
        setTraCodigo(traCodigo);
        setArtId(0);
        setCliId(0);
        setUsarFechaHora(true);
            
    }
    

    public ParamsBusquedaTransacc(Date desde, Date hasta, Integer cliId, 
            Integer artId, String sortColumn, 
            String sortOrder, Integer traCodigo,
            String filtro) {
        this.desde = desde;
        this.hasta = hasta;
        this.cliId = cliId;
        this.artId = artId;
        this.sortColumn = sortColumn;
        this.sortOrder = sortOrder;
        this.traCodigo = traCodigo;
        this.filtro = filtro;
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

    public Integer getTraCodigo() {
        return traCodigo;
    }

    public void setTraCodigo(Integer traCodigo) {
        this.traCodigo = traCodigo;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public boolean isUsarFechaHora() {
        return usarFechaHora;
    }

    public void setUsarFechaHora(boolean usarFechaHora) {
        this.usarFechaHora = usarFechaHora;
    }

    public boolean isByCat() {
        return byCat;
    }

    public void setByCat(boolean byCat) {
        this.byCat = byCat;
    }

    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }
    
    
    
}