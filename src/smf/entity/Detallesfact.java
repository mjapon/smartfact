/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "detallesfact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallesfact.findAll", query = "SELECT d FROM Detallesfact d")
    , @NamedQuery(name = "Detallesfact.findByDetfId", query = "SELECT d FROM Detallesfact d WHERE d.detfId = :detfId")
    , @NamedQuery(name = "Detallesfact.findByArtId", query = "SELECT d FROM Detallesfact d WHERE d.artId = :artId")
    , @NamedQuery(name = "Detallesfact.findByDetfPrecio", query = "SELECT d FROM Detallesfact d WHERE d.detfPrecio = :detfPrecio")
    , @NamedQuery(name = "Detallesfact.findByDetfCant", query = "SELECT d FROM Detallesfact d WHERE d.detfCant = :detfCant")
    , @NamedQuery(name = "Detallesfact.findByDetfIva", query = "SELECT d FROM Detallesfact d WHERE d.detfIva = :detfIva")
    , @NamedQuery(name = "Detallesfact.findByDetfDesc", query = "SELECT d FROM Detallesfact d WHERE d.detfDesc = :detfDesc")})
public class Detallesfact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detf_id")
    private Integer detfId;
    
    @Basic(optional = false)
    @Column(name = "art_id")
    private int artId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "detf_precio")
    private BigDecimal detfPrecio;
    
    @Column(name = "detf_preciocm")
    private BigDecimal detfPreciocm;
    
    @Column(name = "detf_cant")
    private BigDecimal detfCant;
    @Column(name = "detf_iva")
    private Boolean detfIva;
    @Column(name = "detf_desc")
    private BigDecimal detfDesc;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne(optional = false)
    private Facturas factId;

    public Detallesfact() {
    }

    public Detallesfact(Integer detfId) {
        this.detfId = detfId;
    }

    public Detallesfact(Integer detfId, int artId, BigDecimal detfPrecio) {
        this.detfId = detfId;
        this.artId = artId;
        this.detfPrecio = detfPrecio;
    }

    public Integer getDetfId() {
        return detfId;
    }

    public void setDetfId(Integer detfId) {
        this.detfId = detfId;
    }

    public int getArtId() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public BigDecimal getDetfPrecio() {
        return detfPrecio;
    }

    public void setDetfPrecio(BigDecimal detfPrecio) {
        this.detfPrecio = detfPrecio;
    }

    public BigDecimal getDetfPreciocm() {
        return detfPreciocm;
    }

    public void setDetfPreciocm(BigDecimal detfPreciocm) {
        this.detfPreciocm = detfPreciocm;
    }

    public BigDecimal getDetfCant() {
        return detfCant;
    }

    public void setDetfCant(BigDecimal detfCant) {
        this.detfCant = detfCant;
    }

    public Boolean getDetfIva() {
        return detfIva;
    }

    public void setDetfIva(Boolean detfIva) {
        this.detfIva = detfIva;
    }

    public BigDecimal getDetfDesc() {
        return detfDesc;
    }

    public void setDetfDesc(BigDecimal detfDesc) {
        this.detfDesc = detfDesc;
    }

    public Facturas getFactId() {
        return factId;
    }

    public void setFactId(Facturas factId) {
        this.factId = factId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detfId != null ? detfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallesfact)) {
            return false;
        }
        Detallesfact other = (Detallesfact) object;
        if ((this.detfId == null && other.detfId != null) || (this.detfId != null && !this.detfId.equals(other.detfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Detallesfact[ detfId=" + detfId + " ]";
    }
    
}
