/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author mjapon
 */
public class RowFactByCaja {
    
    private Integer factId;
    private String factNum;
    private Date factFecha;
    private String cliCi;
    private String cliNombres;
    private Integer detFactId;
    private Integer artId;
    private String artNombre;
    private BigDecimal detfDesc;
    private Integer catCajaId;
    private BigDecimal detfCant;
    private BigDecimal detfPrecio;
    private BigDecimal porcDescg;
    private BigDecimal subtotal;
    private BigDecimal iva;
    
    private BigDecimal descg;
    private BigDecimal total;
    private String obsFact;
    
    /*
     "  f.fact_id,\n" +
                    "  f.fact_num,\n" +
                    "  f.fact_fecha,\n" +
                    "  cli.cli_nombres,\n" +
                    " d.detf_id,\n" +
                    " d.art_id,\n" +
                    "  a.art_nombre,\n" +
                    "  d.detf_desc,\n" +
                    "   cc.cc_id,\n" +
                    "   d.detf_cant,\n" +
                    "   d.detf_precio,\n" +
                    "   round(f.fact_descg/f.fact_total, 6) as pdescg,\n" +
                    "   (d.detf_cant*d.detf_precio)-d.detf_desc as subtotal,\n" +
                    "   CASE WHEN d.detf_iva THEN 0.12*((d.detf_cant*d.detf_precio)-d.detf_desc) ELSE 0.0 end as iva
    */

    public RowFactByCaja() {
    }

    public RowFactByCaja(Integer factId, String factNum, Date factFecha, String cliNombres, Integer detFactId, Integer artId, String artNombre, 
            BigDecimal detfDesc, Integer catCajaId, BigDecimal detfCant, BigDecimal detfPrecio, BigDecimal porcDescg, 
            BigDecimal subtotal, BigDecimal iva, BigDecimal descg, BigDecimal total) {
        this.factId = factId;
        this.factNum = factNum;
        this.factFecha = factFecha;
        this.cliNombres = cliNombres;
        this.detFactId = detFactId;
        this.artId = artId;
        this.artNombre = artNombre;
        this.detfDesc = detfDesc;
        this.catCajaId = catCajaId;
        this.detfCant = detfCant;
        this.detfPrecio = detfPrecio;
        this.porcDescg = porcDescg;
        this.subtotal = subtotal;
        this.iva = iva;
        this.descg = descg;
        this.total = total;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }

    public String getFactNum() {
        return factNum;
    }

    public void setFactNum(String factNum) {
        this.factNum = factNum;
    }

    public Date getFactFecha() {
        return factFecha;
    }

    public void setFactFecha(Date factFecha) {
        this.factFecha = factFecha;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public Integer getDetFactId() {
        return detFactId;
    }

    public void setDetFactId(Integer detFactId) {
        this.detFactId = detFactId;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getArtNombre() {
        return artNombre;
    }

    public void setArtNombre(String artNombre) {
        this.artNombre = artNombre;
    }

    public BigDecimal getDetfDesc() {
        return detfDesc;
    }

    public void setDetfDesc(BigDecimal detfDesc) {
        this.detfDesc = detfDesc;
    }

    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }

    public BigDecimal getDetfCant() {
        return detfCant;
    }

    public void setDetfCant(BigDecimal detfCant) {
        this.detfCant = detfCant;
    }

    public BigDecimal getDetfPrecio() {
        return detfPrecio;
    }

    public void setDetfPrecio(BigDecimal detfPrecio) {
        this.detfPrecio = detfPrecio;
    }

    public BigDecimal getPorcDescg() {
        return porcDescg;
    }

    public void setPorcDescg(BigDecimal porcDescg) {
        this.porcDescg = porcDescg;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescg() {
        return descg;
    }

    public void setDescg(BigDecimal descg) {
        this.descg = descg;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCliCi() {
        return cliCi;
    }

    public void setCliCi(String cliCi) {
        this.cliCi = cliCi;
    }

    public String getObsFact() {
        return obsFact;
    }

    public void setObsFact(String obsFact) {
        this.obsFact = obsFact;
    }
    
    
    
}
