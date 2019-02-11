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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "secuencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secuencias.findAll", query = "SELECT s FROM Secuencias s")
    , @NamedQuery(name = "Secuencias.findBySecId", query = "SELECT s FROM Secuencias s WHERE s.secId = :secId")
    , @NamedQuery(name = "Secuencias.findBySecClave", query = "SELECT s FROM Secuencias s WHERE s.secClave = :secClave")
    , @NamedQuery(name = "Secuencias.findBySecValor", query = "SELECT s FROM Secuencias s WHERE s.secValor = :secValor")})
public class Secuencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sec_id")
    private Integer secId;
    @Basic(optional = false)
    @Column(name = "sec_clave")
    private String secClave;
    @Basic(optional = false)
    @Column(name = "sec_valor")
    private int secValor;

    public Secuencias() {
    }

    public Secuencias(Integer secId) {
        this.secId = secId;
    }

    public Secuencias(Integer secId, String secClave, int secValor) {
        this.secId = secId;
        this.secClave = secClave;
        this.secValor = secValor;
    }

    public Integer getSecId() {
        return secId;
    }

    public void setSecId(Integer secId) {
        this.secId = secId;
    }

    public String getSecClave() {
        return secClave;
    }

    public void setSecClave(String secClave) {
        this.secClave = secClave;
    }

    public int getSecValor() {
        return secValor;
    }

    public void setSecValor(int secValor) {
        this.secValor = secValor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secId != null ? secId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secuencias)) {
            return false;
        }
        Secuencias other = (Secuencias) object;
        if ((this.secId == null && other.secId != null) || (this.secId != null && !this.secId.equals(other.secId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Secuencias[ secId=" + secId + " ]";
    }
    
}
