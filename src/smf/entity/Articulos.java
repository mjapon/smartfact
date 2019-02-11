/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByArtId", query = "SELECT a FROM Articulos a WHERE a.artId = :artId")
    , @NamedQuery(name = "Articulos.findByArtNombre", query = "SELECT a FROM Articulos a WHERE a.artNombre = :artNombre")
    , @NamedQuery(name = "Articulos.findByArtCodbar", query = "SELECT a FROM Articulos a WHERE a.artCodbar = :artCodbar")
    , @NamedQuery(name = "Articulos.findByArtPrecio", query = "SELECT a FROM Articulos a WHERE a.artPrecio = :artPrecio")
    , @NamedQuery(name = "Articulos.findByArtPreciomin", query = "SELECT a FROM Articulos a WHERE a.artPreciomin = :artPreciomin")
    , @NamedQuery(name = "Articulos.findByArtInv", query = "SELECT a FROM Articulos a WHERE a.artInv = :artInv")
    , @NamedQuery(name = "Articulos.findByProvId", query = "SELECT a FROM Articulos a WHERE a.provId = :provId")
    , @NamedQuery(name = "Articulos.findByArtIva", query = "SELECT a FROM Articulos a WHERE a.artIva = :artIva")
    , @NamedQuery(name = "Articulos.findByUnidId", query = "SELECT a FROM Articulos a WHERE a.unidId = :unidId")})
public class Articulos implements Serializable {

    @Column(name = "art_inv")
    private BigDecimal artInv;
    @Basic(optional = false)
    @Column(name = "art_tipo")
    private String artTipo;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kaArtid")
    private Collection<Kardexart> kardexartCollection;
    */
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidpArtid")
    private Collection<Unidadesprecio> unidadesprecioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "art_id")
    private Integer artId;
    @Column(name = "art_nombre")
    private String artNombre;
    @Column(name = "art_codbar")
    private String artCodbar;
    @Column(name = "art_preciocompra")
    private BigDecimal artPrecioCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "art_precio")
    private BigDecimal artPrecio;
    @Column(name = "art_preciomin")
    private BigDecimal artPreciomin;
    @Column(name = "prov_id")
    private Integer provId;
    @Column(name = "art_iva")
    private Boolean artIva;
    @Column(name = "unid_id")
    private Integer unidId;
    @Column(name = "cat_id")
    private Integer catId;
    
    @Column(name = "art_feccrea")
    @Temporal(TemporalType.DATE)    
    private Date fechaCreacion;
    
    @Column(name = "art_feccadu")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;

    public Articulos() {
    }

    public Articulos(Integer artId) {
        this.artId = artId;
    }

    public Articulos(Integer artId, BigDecimal artPrecio) {
        this.artId = artId;
        this.artPrecio = artPrecio;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getArtNombre() {
        return artNombre;
    }

    public void setArtNombre(String artNombre) {
        this.artNombre = artNombre;
    }

    public String getArtCodbar() {
        return artCodbar;
    }

    public void setArtCodbar(String artCodbar) {
        this.artCodbar = artCodbar;
    }

    public BigDecimal getArtPrecio() {
        return artPrecio;
    }

    public BigDecimal getArtPrecioCompra() {
        return artPrecioCompra;
    }

    public void setArtPrecioCompra(BigDecimal artPrecioCompra) {
        this.artPrecioCompra = artPrecioCompra;
    }

    public void setArtPrecio(BigDecimal artPrecio) {
        this.artPrecio = artPrecio;
    }

    public BigDecimal getArtPreciomin() {
        return artPreciomin;
    }

    public void setArtPreciomin(BigDecimal artPreciomin) {
        this.artPreciomin = artPreciomin;
    }

    public BigDecimal getArtInv() {
        return artInv;
    }

    public void setArtInv(BigDecimal artInv) {
        this.artInv = artInv;
    }

    public Integer getProvId() {
        return provId;
    }

    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    public Boolean getArtIva() {
        return artIva;
    }

    public void setArtIva(Boolean artIva) {
        this.artIva = artIva;
    }

    public Integer getUnidId() {
        return unidId;
    }

    public void setUnidId(Integer unidId) {
        this.unidId = unidId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getArtTipo() {
        return artTipo;
    }

    public void setArtTipo(String artTipo) {
        this.artTipo = artTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artId != null ? artId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.artId == null && other.artId != null) || (this.artId != null && !this.artId.equals(other.artId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "jj.entity.Articulos[ artId=" + artId + " ]";        
        return artNombre!=null?artNombre.toUpperCase():"";
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }    
    
    /*
    @XmlTransient
    public Collection<Kardexart> getKardexartCollection() {
        return kardexartCollection;
    }

    public void setKardexartCollection(Collection<Kardexart> kardexartCollection) {
        this.kardexartCollection = kardexartCollection;
    }
    */

    @XmlTransient
    public Collection<Unidadesprecio> getUnidadesprecioCollection() {
        return unidadesprecioCollection;
    }

    public void setUnidadesprecioCollection(Collection<Unidadesprecio> unidadesprecioCollection) {
        this.unidadesprecioCollection = unidadesprecioCollection;
    }
   
}