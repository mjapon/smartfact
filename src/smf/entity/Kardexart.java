/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manuel.japon
 */
@Entity
@Table(name = "kardexart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kardexart.findAll", query = "SELECT k FROM Kardexart k")
    , @NamedQuery(name = "Kardexart.findByKaId", query = "SELECT k FROM Kardexart k WHERE k.kaId = :kaId")
    , @NamedQuery(name = "Kardexart.findByKaFechareg", query = "SELECT k FROM Kardexart k WHERE k.kaFechareg = :kaFechareg")
    , @NamedQuery(name = "Kardexart.findByKaUser", query = "SELECT k FROM Kardexart k WHERE k.kaUser = :kaUser")
    , @NamedQuery(name = "Kardexart.findByKaAccion", query = "SELECT k FROM Kardexart k WHERE k.kaAccion = :kaAccion")
    , @NamedQuery(name = "Kardexart.findByKaValorant", query = "SELECT k FROM Kardexart k WHERE k.kaValorant = :kaValorant")
    , @NamedQuery(name = "Kardexart.findByKaValordesp", query = "SELECT k FROM Kardexart k WHERE k.kaValordesp = :kaValordesp")})
public class Kardexart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ka_id")
    private Integer kaId;
    @Basic(optional = false)
    @Column(name = "ka_fechareg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kaFechareg;
    @Column(name = "ka_user")
    private Integer kaUser;
    @Column(name = "ka_accion")
    private String kaAccion;
    @Column(name = "ka_valorant")
    private BigDecimal kaValorant;
    @Column(name = "ka_valordesp")
    private BigDecimal kaValordesp;    
    @Column(name = "ka_cantidad")
    private BigDecimal kaCantidad;    
    @Column(name = "ka_refid")
    private Integer kaRefid;
    
    @Column(name = "ka_artid")
    private Integer kaArtId;
    
    @Column(name = "ka_tipo")
    private Integer katipo;
    
    /*
    @JoinColumn(name = "ka_artid", referencedColumnName = "art_id")
    @ManyToOne(optional = false)
    private Articulos kaArtid;
    */

    public Kardexart() {
    }

    public Kardexart(Integer kaId) {
        this.kaId = kaId;
    }

    public Kardexart(Integer kaId, Date kaFechareg) {
        this.kaId = kaId;
        this.kaFechareg = kaFechareg;
    }

    public Integer getKaId() {
        return kaId;
    }

    public void setKaId(Integer kaId) {
        this.kaId = kaId;
    }

    public Date getKaFechareg() {
        return kaFechareg;
    }

    public void setKaFechareg(Date kaFechareg) {
        this.kaFechareg = kaFechareg;
    }

    public Integer getKaUser() {
        return kaUser;
    }

    public void setKaUser(Integer kaUser) {
        this.kaUser = kaUser;
    }

    public String getKaAccion() {
        return kaAccion;
    }

    public void setKaAccion(String kaAccion) {
        this.kaAccion = kaAccion;
    }

    /*
    public Articulos getKaArtid() {
        return kaArtid;
    }

    public void setKaArtid(Articulos kaArtid) {
        this.kaArtid = kaArtid;
    }
    */

    public Integer getKaArtId() {
        return kaArtId;
    }

    public void setKaArtId(Integer kaArtId) {
        this.kaArtId = kaArtId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kaId != null ? kaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kardexart)) {
            return false;
        }
        Kardexart other = (Kardexart) object;
        if ((this.kaId == null && other.kaId != null) || (this.kaId != null && !this.kaId.equals(other.kaId))) {
            return false;
        }
        return true;
    }

    public BigDecimal getKaValorant() {
        return kaValorant;
    }

    public void setKaValorant(BigDecimal kaValorant) {
        this.kaValorant = kaValorant;
    }

    public BigDecimal getKaValordesp() {
        return kaValordesp;
    }

    public void setKaValordesp(BigDecimal kaValordesp) {
        this.kaValordesp = kaValordesp;
    }

    public BigDecimal getKaCantidad() {
        return kaCantidad;
    }

    public void setKaCantidad(BigDecimal kaCantidad) {
        this.kaCantidad = kaCantidad;
    }

    public Integer getKaRefid() {
        return kaRefid;
    }
    
    public void setKaRefid(Integer kaRefid) {
        this.kaRefid = kaRefid;
    }

    public Integer getKatipo() {
        return katipo;
    }

    public void setKatipo(Integer katipo) {
        this.katipo = katipo;
    }
    
    
    
    @Override
    public String toString() {
        return "jj.entity.Kardexart[ kaId=" + kaId + " ]";
    }
    
    
    
}
