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
@Table(name = "formaspago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formaspago.findAll", query = "SELECT f FROM Formaspago f")
    , @NamedQuery(name = "Formaspago.findByFpId", query = "SELECT f FROM Formaspago f WHERE f.fpId = :fpId")
    , @NamedQuery(name = "Formaspago.findByFpNombre", query = "SELECT f FROM Formaspago f WHERE f.fpNombre = :fpNombre")})
public class Formaspago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fp_id")
    private Integer fpId;
    @Basic(optional = false)
    @Column(name = "fp_nombre")
    private String fpNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fpId")
    private Collection<Pagosfact> pagosfactCollection;

    public Formaspago() {
    }

    public Formaspago(Integer fpId) {
        this.fpId = fpId;
    }

    public Formaspago(Integer fpId, String fpNombre) {
        this.fpId = fpId;
        this.fpNombre = fpNombre;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public String getFpNombre() {
        return fpNombre;
    }

    public void setFpNombre(String fpNombre) {
        this.fpNombre = fpNombre;
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
        hash += (fpId != null ? fpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formaspago)) {
            return false;
        }
        Formaspago other = (Formaspago) object;
        if ((this.fpId == null && other.fpId != null) || (this.fpId != null && !this.fpId.equals(other.fpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Formaspago[ fpId=" + fpId + " ]";
    }
    
}
