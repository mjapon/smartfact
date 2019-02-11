/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "transacciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transacciones.findAll", query = "SELECT t FROM Transacciones t")
    , @NamedQuery(name = "Transacciones.findByTraId", query = "SELECT t FROM Transacciones t WHERE t.traId = :traId")
    , @NamedQuery(name = "Transacciones.findByTraNombre", query = "SELECT t FROM Transacciones t WHERE t.traNombre = :traNombre")})
public class Transacciones implements Serializable {

    @Basic(optional = false)
    @Column(name = "tra_inv")
    private int traInv;
    @Basic(optional = false)
    @Column(name = "tra_precio")
    private int traPrecio;
    @Column(name = "tra_tipo")
    private Character traTipo;
    @Column(name = "tra_mask")
    private String traMask;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tra_id")
    private Integer traId;
    @Basic(optional = false)
    @Column(name = "tra_nombre")
    private String traNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "traId")
    private Collection<Facturas> facturasCollection;
    @OneToOne(mappedBy = "traId")
    private Movtransacc movtransacc;

    public Transacciones() {
    }

    public Transacciones(Integer traId) {
        this.traId = traId;
    }

    public Transacciones(Integer traId, String traNombre) {
        this.traId = traId;
        this.traNombre = traNombre;
    }

    public Integer getTraId() {
        return traId;
    }

    public void setTraId(Integer traId) {
        this.traId = traId;
    }

    public String getTraNombre() {
        return traNombre;
    }

    public void setTraNombre(String traNombre) {
        this.traNombre = traNombre;
    }

    @XmlTransient
    public Collection<Facturas> getFacturasCollection() {
        return facturasCollection;
    }

    public void setFacturasCollection(Collection<Facturas> facturasCollection) {
        this.facturasCollection = facturasCollection;
    }

    public Movtransacc getMovtransacc() {
        return movtransacc;
    }

    public void setMovtransacc(Movtransacc movtransacc) {
        this.movtransacc = movtransacc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (traId != null ? traId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacciones)) {
            return false;
        }
        Transacciones other = (Transacciones) object;
        if ((this.traId == null && other.traId != null) || (this.traId != null && !this.traId.equals(other.traId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Transacciones[ traId=" + traId + " ]";
    }

    public int getTraInv() {
        return traInv;
    }

    public void setTraInv(int traInv) {
        this.traInv = traInv;
    }

    public int getTraPrecio() {
        return traPrecio;
    }

    public void setTraPrecio(int traPrecio) {
        this.traPrecio = traPrecio;
    }

    public Character getTraTipo() {
        return traTipo;
    }

    public void setTraTipo(Character traTipo) {
        this.traTipo = traTipo;
    }

    public String getTraMask() {
        return traMask;
    }

    public void setTraMask(String traMask) {
        this.traMask = traMask;
    }
    
}
