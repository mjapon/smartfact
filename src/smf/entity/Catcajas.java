/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manuel.japon
 */
@Entity
@Table(name = "catcajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catcajas.findAll", query = "SELECT c FROM Catcajas c order by c.ccId asc")
    , @NamedQuery(name = "Catcajas.findByCcId", query = "SELECT c FROM Catcajas c WHERE c.ccId = :ccId")
    , @NamedQuery(name = "Catcajas.findByCcNombre", query = "SELECT c FROM Catcajas c WHERE c.ccNombre = :ccNombre")
    , @NamedQuery(name = "Catcajas.findByCcObservacion", query = "SELECT c FROM Catcajas c WHERE c.ccObservacion = :ccObservacion")
    , @NamedQuery(name = "Catcajas.findByCcFechacreacion", query = "SELECT c FROM Catcajas c WHERE c.ccFechacreacion = :ccFechacreacion")})
public class Catcajas implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccId")
    private Collection<Pagosfactcaja> pagosfactcajaCollection;

    @Basic(optional = false)
    @Column(name = "cc_status")
    private int ccStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccId")
    private Collection<Cajadet> cajadetCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cc_id")
    private Integer ccId;
    @Column(name = "cc_nombre")
    private String ccNombre;
    @Column(name = "cc_observacion")
    private String ccObservacion;
    @Column(name = "cc_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ccFechacreacion;
    

    public Catcajas() {
    }

    public Catcajas(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public String getCcNombre() {
        return ccNombre;
    }

    public void setCcNombre(String ccNombre) {
        this.ccNombre = ccNombre;
    }

    public String getCcObservacion() {
        return ccObservacion;
    }

    public void setCcObservacion(String ccObservacion) {
        this.ccObservacion = ccObservacion;
    }

    public Date getCcFechacreacion() {
        return ccFechacreacion;
    }

    public void setCcFechacreacion(Date ccFechacreacion) {
        this.ccFechacreacion = ccFechacreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccId != null ? ccId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catcajas)) {
            return false;
        }
        Catcajas other = (Catcajas) object;
        if ((this.ccId == null && other.ccId != null) || (this.ccId != null && !this.ccId.equals(other.ccId))) {
            return false;
        }
        return true;
    }

    public Integer getCcStatus() {
        return ccStatus;
    }

    public void setCcStatus(Integer ccStatus) {
        this.ccStatus = ccStatus;
    }

    @Override
    public String toString() {
        //return "jj.entity.Catcajas[ ccId=" + ccId + " ]";
        return ccNombre;
    }
    

    @XmlTransient
    public Collection<Cajadet> getCajadetCollection() {
        return cajadetCollection;
    }

    public void setCajadetCollection(Collection<Cajadet> cajadetCollection) {
        this.cajadetCollection = cajadetCollection;
    }

    @XmlTransient
    public Collection<Pagosfactcaja> getPagosfactcajaCollection() {
        return pagosfactcajaCollection;
    }

    public void setPagosfactcajaCollection(Collection<Pagosfactcaja> pagosfactcajaCollection) {
        this.pagosfactcajaCollection = pagosfactcajaCollection;
    }
    
}
