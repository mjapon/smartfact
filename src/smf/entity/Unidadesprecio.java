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
 * @author manuel.japon
 */
@Entity
@Table(name = "unidadesprecio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidadesprecio.findAll", query = "SELECT u FROM Unidadesprecio u")
    , @NamedQuery(name = "Unidadesprecio.findByUnidpId", query = "SELECT u FROM Unidadesprecio u WHERE u.unidpId = :unidpId")
    , @NamedQuery(name = "Unidadesprecio.findByUnidpPrecioventa", query = "SELECT u FROM Unidadesprecio u WHERE u.unidpPrecioventa = :unidpPrecioventa")
    , @NamedQuery(name = "Unidadesprecio.findByUnidpPreciomin", query = "SELECT u FROM Unidadesprecio u WHERE u.unidpPreciomin = :unidpPreciomin")})
public class Unidadesprecio implements Serializable {

    @JoinColumn(name = "unidp_unid", referencedColumnName = "uni_id")
    @ManyToOne(optional = false)
    private Unidades unidpUnid;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "unidp_id")
    private Integer unidpId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unidp_precioventa")
    private BigDecimal unidpPrecioventa;
    @Column(name = "unidp_preciomin")
    private BigDecimal unidpPreciomin;
    @JoinColumn(name = "unidp_artid", referencedColumnName = "art_id")
    @ManyToOne(optional = false)
    private Articulos unidpArtid;

    public Unidadesprecio() {
    }

    public Unidadesprecio(Integer unidpId) {
        this.unidpId = unidpId;
    }

    public Integer getUnidpId() {
        return unidpId;
    }

    public void setUnidpId(Integer unidpId) {
        this.unidpId = unidpId;
    }

    public BigDecimal getUnidpPrecioventa() {
        return unidpPrecioventa;
    }

    public void setUnidpPrecioventa(BigDecimal unidpPrecioventa) {
        this.unidpPrecioventa = unidpPrecioventa;
    }

    public BigDecimal getUnidpPreciomin() {
        return unidpPreciomin;
    }

    public void setUnidpPreciomin(BigDecimal unidpPreciomin) {
        this.unidpPreciomin = unidpPreciomin;
    }

    public Articulos getUnidpArtid() {
        return unidpArtid;
    }

    public void setUnidpArtid(Articulos unidpArtid) {
        this.unidpArtid = unidpArtid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unidpId != null ? unidpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidadesprecio)) {
            return false;
        }
        Unidadesprecio other = (Unidadesprecio) object;
        if ((this.unidpId == null && other.unidpId != null) || (this.unidpId != null && !this.unidpId.equals(other.unidpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Unidadesprecio[ unidpId=" + unidpId + " ]";
    }

    public Unidades getUnidpUnid() {
        return unidpUnid;
    }

    public void setUnidpUnid(Unidades unidpUnid) {
        this.unidpUnid = unidpUnid;
    }
    
}
