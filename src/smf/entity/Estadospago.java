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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "estadospago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadospago.findAll", query = "SELECT e FROM Estadospago e")
    , @NamedQuery(name = "Estadospago.findBySpId", query = "SELECT e FROM Estadospago e WHERE e.spId = :spId")
    , @NamedQuery(name = "Estadospago.findBySpNombre", query = "SELECT e FROM Estadospago e WHERE e.spNombre = :spNombre")})
public class Estadospago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sp_id")
    private Integer spId;
    @Basic(optional = false)
    @Column(name = "sp_nombre")
    private String spNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spId")
    private Collection<Pagosfact> pagosfactCollection;

    public Estadospago() {
    }

    public Estadospago(Integer spId) {
        this.spId = spId;
    }

    public Estadospago(Integer spId, String spNombre) {
        this.spId = spId;
        this.spNombre = spNombre;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getSpNombre() {
        return spNombre;
    }

    public void setSpNombre(String spNombre) {
        this.spNombre = spNombre;
    }

    @XmlTransient
    public Collection<Pagosfact> getPagosfactCollection() {
        return pagosfactCollection;
    }

    public void setPagosfactCollection(Collection<Pagosfact> pagosfactCollection) {
        this.pagosfactCollection = pagosfactCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spId != null ? spId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadospago)) {
            return false;
        }
        Estadospago other = (Estadospago) object;
        if ((this.spId == null && other.spId != null) || (this.spId != null && !this.spId.equals(other.spId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Estadospago[ spId=" + spId + " ]";
    }
    
}
