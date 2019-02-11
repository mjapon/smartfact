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
 * @author manuel.japon
 */
@Entity
@Table(name = "unidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidades.findAll", query = "SELECT u FROM Unidades u")
    , @NamedQuery(name = "Unidades.findByUniId", query = "SELECT u FROM Unidades u WHERE u.uniId = :uniId")
    , @NamedQuery(name = "Unidades.findByUniName", query = "SELECT u FROM Unidades u WHERE u.uniName = :uniName")
    , @NamedQuery(name = "Unidades.findByUniSimbolo", query = "SELECT u FROM Unidades u WHERE u.uniSimbolo = :uniSimbolo")})
public class Unidades implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidpUnid")
    private Collection<Unidadesprecio> unidadesprecioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uni_id")
    private Integer uniId;
    @Column(name = "uni_name")
    private String uniName;
    @Column(name = "uni_simbolo")
    private String uniSimbolo;

    public Unidades() {
    }

    public Unidades(Integer uniId) {
        this.uniId = uniId;
    }

    public Integer getUniId() {
        return uniId;
    }

    public void setUniId(Integer uniId) {
        this.uniId = uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getUniSimbolo() {
        return uniSimbolo;
    }

    public void setUniSimbolo(String uniSimbolo) {
        this.uniSimbolo = uniSimbolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uniId != null ? uniId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidades)) {
            return false;
        }
        Unidades other = (Unidades) object;
        if ((this.uniId == null && other.uniId != null) || (this.uniId != null && !this.uniId.equals(other.uniId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Unidades[ uniId=" + uniId + " ]";
    }

    @XmlTransient
    public Collection<Unidadesprecio> getUnidadesprecioCollection() {
        return unidadesprecioCollection;
    }

    public void setUnidadesprecioCollection(Collection<Unidadesprecio> unidadesprecioCollection) {
        this.unidadesprecioCollection = unidadesprecioCollection;
    }
    
}
