/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;

/**
 *
 * @author manuel.japon
 */
public class FilaUnidadPrecio {
    
    private Integer filaId;
    private Integer artId;
    private Integer unidId;
    private BigDecimal precioNormal;
    private BigDecimal precioMin;
    private String articulo;
    private String unidad;
    private boolean isIva;

    public FilaUnidadPrecio(Integer filaId, Integer artId, Integer unidId, BigDecimal precioNormal, BigDecimal precioMin, String articulo, String unidad, Boolean isIva) {
        this.filaId = filaId;
        this.artId = artId;
        this.unidId = unidId;
        this.precioNormal = precioNormal;
        this.precioMin = precioMin;
        this.articulo = articulo;
        this.unidad = unidad;
        this.isIva = isIva;
    }
    
    public FilaUnidadPrecio(Integer artId, Integer unidId, BigDecimal precioNormal, BigDecimal precioMin, Boolean isIva){
        this.artId = artId;
        this.unidId = unidId;
        this.precioNormal = precioNormal;
        this.precioMin = precioMin;
        this.isIva = isIva;
    }

    public Integer getFilaId() {
        return filaId;
    }

    public void setFilaId(Integer filaId) {
        this.filaId = filaId;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public Integer getUnidId() {
        return unidId;
    }

    public void setUnidId(Integer unidId) {
        this.unidId = unidId;
    }

    public BigDecimal getPrecioNormal() {
        return precioNormal;
    }
    
    public BigDecimal getPrecioNormalSinIva(){
        return FilaArticulo.getPrecioSinIvaUtil(precioNormal);
    }
    public BigDecimal getPrecioNormalConIva(){
        return FilaArticulo.getPrecioConIvaUtil(precioNormal);
    }

    public void setPrecioNormal(BigDecimal precioNormal) {
        this.precioNormal = precioNormal;
    }

    public BigDecimal getPrecioMin() {
        return precioMin;
    }
    public BigDecimal getPrecioMinSinIva(){
        return FilaArticulo.getPrecioSinIvaUtil(precioMin);
    }
    public BigDecimal getPrecioMinConIva(){
        return FilaArticulo.getPrecioConIvaUtil(precioMin);
    }

    public void setPrecioMin(BigDecimal precioMin) {
        this.precioMin = precioMin;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public boolean isIsIva() {
        return isIva;
    }

    public void setIsIva(boolean isIva) {
        this.isIva = isIva;
    }
    
    
}
