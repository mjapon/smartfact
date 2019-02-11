/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "ctes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ctes.findAll", query = "SELECT c FROM Ctes c")
    , @NamedQuery(name = "Ctes.findByCtesId", query = "SELECT c FROM Ctes c WHERE c.ctesId = :ctesId")
    , @NamedQuery(name = "Ctes.findByCtesClave", query = "SELECT c FROM Ctes c WHERE c.ctesClave = :ctesClave")
    , @NamedQuery(name = "Ctes.findByCtesValor", query = "SELECT c FROM Ctes c WHERE c.ctesValor = :ctesValor")})
public class Ctes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctes_id")
    private Integer ctesId;
    @Basic(optional = false)
    @Column(name = "ctes_clave")
    private String ctesClave;
    @Basic(optional = false)
    @Column(name = "ctes_valor")
    private String ctesValor;

    public Ctes() {
    }

    public Ctes(Integer ctesId) {
        this.ctesId = ctesId;
    }

    public Ctes(Integer ctesId, String ctesClave, String ctesValor) {
        this.ctesId = ctesId;
        this.ctesClave = ctesClave;
        this.ctesValor = ctesValor;
    }

    public Integer getCtesId() {
        return ctesId;
    }

    public void setCtesId(Integer ctesId) {
        this.ctesId = ctesId;
    }

    public String getCtesClave() {
        return ctesClave;
    }

    public void setCtesClave(String ctesClave) {
        this.ctesClave = ctesClave;
    }

    public String getCtesValor() {
        return ctesValor;
    }

    public void setCtesValor(String ctesValor) {
        this.ctesValor = ctesValor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctesId != null ? ctesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ctes)) {
            return false;
        }
        Ctes other = (Ctes) object;
        if ((this.ctesId == null && other.ctesId != null) || (this.ctesId != null && !this.ctesId.equals(other.ctesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Ctes[ ctesId=" + ctesId + " ]";
    }
    
}
