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
 * @author mjapon
 */
@Entity
@Table(name = "caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c")
    , @NamedQuery(name = "Caja.findByCjId", query = "SELECT c FROM Caja c WHERE c.cjId = :cjId")
    , @NamedQuery(name = "Caja.findByCjEstado", query = "SELECT c FROM Caja c WHERE c.cjEstado = :cjEstado")
    , @NamedQuery(name = "Caja.findByCjUser", query = "SELECT c FROM Caja c WHERE c.cjUser = :cjUser")
    , @NamedQuery(name = "Caja.findByCjUseranul", query = "SELECT c FROM Caja c WHERE c.cjUseranul = :cjUseranul")
    , @NamedQuery(name = "Caja.findByCjFecaper", query = "SELECT c FROM Caja c WHERE c.cjFecaper = :cjFecaper")
    , @NamedQuery(name = "Caja.findByCjFecanul", query = "SELECT c FROM Caja c WHERE c.cjFecanul = :cjFecanul")
    , @NamedQuery(name = "Caja.findByCjObsaper", query = "SELECT c FROM Caja c WHERE c.cjObsaper = :cjObsaper")
    , @NamedQuery(name = "Caja.findByCjObscierre", query = "SELECT c FROM Caja c WHERE c.cjObscierre = :cjObscierre")
    , @NamedQuery(name = "Caja.findByCjObsanul", query = "SELECT c FROM Caja c WHERE c.cjObsanul = :cjObsanul")
    , @NamedQuery(name = "Caja.findByCjFeccierre", query = "SELECT c FROM Caja c WHERE c.cjFeccierre = :cjFeccierre")})
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cj_id")
    private Integer cjId;
    @Basic(optional = false)
    @Column(name = "cj_estado")
    private int cjEstado;
    @Basic(optional = false)
    @Column(name = "cj_user")
    private int cjUser;
    @Column(name = "cj_useranul")
    private Integer cjUseranul;
    @Basic(optional = false)
    @Column(name = "cj_fecaper")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cjFecaper;
    @Column(name = "cj_fecanul")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cjFecanul;
    @Column(name = "cj_obsaper")
    private String cjObsaper;
    @Column(name = "cj_obscierre")
    private String cjObscierre;
    @Column(name = "cj_obsanul")
    private String cjObsanul;
    @Column(name = "cj_feccierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cjFeccierre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cjId")
    private Collection<Cajadet> cajadetCollection;
    
    

    public Caja() {
    }

    public Caja(Integer cjId) {
        this.cjId = cjId;
    }

    public Caja(Integer cjId, int cjEstado, int cjUser, Date cjFecaper) {
        this.cjId = cjId;
        this.cjEstado = cjEstado;
        this.cjUser = cjUser;
        this.cjFecaper = cjFecaper;
    }

    public Integer getCjId() {
        return cjId;
    }

    public void setCjId(Integer cjId) {
        this.cjId = cjId;
    }

    public int getCjEstado() {
        return cjEstado;
    }

    public void setCjEstado(int cjEstado) {
        this.cjEstado = cjEstado;
    }

    public int getCjUser() {
        return cjUser;
    }

    public void setCjUser(int cjUser) {
        this.cjUser = cjUser;
    }

    public Integer getCjUseranul() {
        return cjUseranul;
    }

    public void setCjUseranul(Integer cjUseranul) {
        this.cjUseranul = cjUseranul;
    }

    public Date getCjFecaper() {
        return cjFecaper;
    }

    public void setCjFecaper(Date cjFecaper) {
        this.cjFecaper = cjFecaper;
    }

    public Date getCjFecanul() {
        return cjFecanul;
    }

    public void setCjFecanul(Date cjFecanul) {
        this.cjFecanul = cjFecanul;
    }

    public String getCjObsaper() {
        return cjObsaper;
    }

    public void setCjObsaper(String cjObsaper) {
        this.cjObsaper = cjObsaper;
    }

    public String getCjObscierre() {
        return cjObscierre;
    }

    public void setCjObscierre(String cjObscierre) {
        this.cjObscierre = cjObscierre;
    }

    public String getCjObsanul() {
        return cjObsanul;
    }

    public void setCjObsanul(String cjObsanul) {
        this.cjObsanul = cjObsanul;
    }

    public Date getCjFeccierre() {
        return cjFeccierre;
    }

    public void setCjFeccierre(Date cjFeccierre) {
        this.cjFeccierre = cjFeccierre;
    }

    @XmlTransient
    public Collection<Cajadet> getCajadetCollection() {
        return cajadetCollection;
    }

    public void setCajadetCollection(Collection<Cajadet> cajadetCollection) {
        this.cajadetCollection = cajadetCollection;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cjId != null ? cjId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.cjId == null && other.cjId != null) || (this.cjId != null && !this.cjId.equals(other.cjId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Caja[ cjId=" + cjId + " ]";
    }
    
}
