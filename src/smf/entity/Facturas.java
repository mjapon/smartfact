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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturas.findAll", query = "SELECT f FROM Facturas f")
    , @NamedQuery(name = "Facturas.findByFactId", query = "SELECT f FROM Facturas f WHERE f.factId = :factId")
    , @NamedQuery(name = "Facturas.findByFactNum", query = "SELECT f FROM Facturas f WHERE f.factNum = :factNum")
    , @NamedQuery(name = "Facturas.findByFactPtoemi", query = "SELECT f FROM Facturas f WHERE f.factPtoemi = :factPtoemi")
    , @NamedQuery(name = "Facturas.findByFactEstab", query = "SELECT f FROM Facturas f WHERE f.factEstab = :factEstab")
    , @NamedQuery(name = "Facturas.findByFactSubt", query = "SELECT f FROM Facturas f WHERE f.factSubt = :factSubt")
    , @NamedQuery(name = "Facturas.findByFactIva", query = "SELECT f FROM Facturas f WHERE f.factIva = :factIva")
    , @NamedQuery(name = "Facturas.findByFactTotal", query = "SELECT f FROM Facturas f WHERE f.factTotal = :factTotal")
    , @NamedQuery(name = "Facturas.findByFactFecha", query = "SELECT f FROM Facturas f WHERE f.factFecha = :factFecha")
    , @NamedQuery(name = "Facturas.findByFactFecreg", query = "SELECT f FROM Facturas f WHERE f.factFecreg = :factFecreg")
    , @NamedQuery(name = "Facturas.findByUserId", query = "SELECT f FROM Facturas f WHERE f.userId = :userId")})
public class Facturas implements Serializable {

    @Basic(optional = false)
    @Column(name = "fact_valido")
    private int factValido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factId")
    private Collection<Pagosfact> pagosfactCollection;
    @JoinColumn(name = "tra_id", referencedColumnName = "tra_id")
    @ManyToOne(optional = false)
    private Transacciones traId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factIdRel")
    private Collection<Movtransacc> movtransaccCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fact_id")
    private Integer factId;
    @Basic(optional = false)
    @Column(name = "fact_num")
    private String factNum;
    @Column(name = "fact_ptoemi")
    private Integer factPtoemi;
    @Column(name = "fact_estab")
    private Integer factEstab;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fact_subt")
    private BigDecimal factSubt;
    @Column(name = "fact_iva")
    private BigDecimal factIva;
    @Column(name = "fact_total")
    private BigDecimal factTotal;
    @Column(name = "fact_fecha")
    @Temporal(TemporalType.DATE)
    private Date factFecha;
    @Basic(optional = false)
    @Column(name = "fact_fecreg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date factFecreg;
    
    @Column(name = "fact_desc")
    private BigDecimal factDesc;
    
    @Column(name = "fact_descg")
    private BigDecimal factDescg;
    
    @Column(name = "fact_utilidad")
    private BigDecimal factUtilidad;
    
    @Column(name = "user_id")
    private Integer userId;
    @JoinColumn(name = "cli_id", referencedColumnName = "cli_id")
    @ManyToOne(optional = false)
    private Clientes cliId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factId")
    private List<Detallesfact> detallesfactList;
    
    
    @Column(name = "caja_id")
    private Integer cajaId;
    
    @Column(name = "fact_subt12")
    private BigDecimal factSubt12;
    
    public Facturas() {
    }

    public Facturas(Integer factId) {
        this.factId = factId;
    }

    public Facturas(Integer factId, String factNum, Date factFecreg) {
        this.factId = factId;
        this.factNum = factNum;
        this.factFecreg = factFecreg;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }

    public String getFactNum() {
        return factNum;
    }

    public void setFactNum(String factNum) {
        this.factNum = factNum;
    }

    public Integer getFactPtoemi() {
        return factPtoemi;
    }

    public void setFactPtoemi(Integer factPtoemi) {
        this.factPtoemi = factPtoemi;
    }

    public Integer getFactEstab() {
        return factEstab;
    }

    public void setFactEstab(Integer factEstab) {
        this.factEstab = factEstab;
    }

    public BigDecimal getFactSubt() {
        return factSubt;
    }

    public void setFactSubt(BigDecimal factSubt) {
        this.factSubt = factSubt;
    }

    public BigDecimal getFactIva() {
        return factIva;
    }

    public void setFactIva(BigDecimal factIva) {
        this.factIva = factIva;
    }

    public BigDecimal getFactTotal() {
        return factTotal;
    }

    public void setFactTotal(BigDecimal factTotal) {
        this.factTotal = factTotal;
    }

    public Date getFactFecha() {
        return factFecha;
    }

    public void setFactFecha(Date factFecha) {
        this.factFecha = factFecha;
    }

    public Date getFactFecreg() {
        return factFecreg;
    }

    public void setFactFecreg(Date factFecreg) {
        this.factFecreg = factFecreg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Clientes getCliId() {
        return cliId;
    }

    public void setCliId(Clientes cliId) {
        this.cliId = cliId;
    }

    public BigDecimal getFactDesc() {
        return factDesc;
    }

    public void setFactDesc(BigDecimal factDesc) {
        this.factDesc = factDesc;
    }

    public Integer getFactValido() {
        return factValido;
    }

    public void setFactValido(Integer factValido) {
        this.factValido = factValido;
    }

    @XmlTransient
    public List<Detallesfact> getDetallesfactList() {
        return detallesfactList;
    }

    public void setDetallesfactList(List<Detallesfact> detallesfactList) {
        this.detallesfactList = detallesfactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factId != null ? factId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturas)) {
            return false;
        }
        Facturas other = (Facturas) object;
        if ((this.factId == null && other.factId != null) || (this.factId != null && !this.factId.equals(other.factId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Facturas[ factId=" + factId + " ]";
    }

    @XmlTransient
    public Collection<Pagosfact> getPagosfactCollection() {
        return pagosfactCollection;
    }

    public void setPagosfactCollection(Collection<Pagosfact> pagosfactCollection) {
        this.pagosfactCollection = pagosfactCollection;
    }

    public Transacciones getTraId() {
        return traId;
    }

    public void setTraId(Transacciones traId) {
        this.traId = traId;
    }

    public BigDecimal getFactDescg() {
        return factDescg;
    }

    public void setFactDescg(BigDecimal factDescg) {
        this.factDescg = factDescg;
    }

    public BigDecimal getFactUtilidad() {
        return factUtilidad;
    }

    public void setFactUtilidad(BigDecimal factUtilidad) {
        this.factUtilidad = factUtilidad;
    }
    
    

    /*
    public Integer getTra_traId() {
        return tra_traId;
    }

    public void setTra_traId(Integer tra_traId) {
        this.tra_traId = tra_traId;
    }
    */
    

    @XmlTransient
    public Collection<Movtransacc> getMovtransaccCollection() {
        return movtransaccCollection;
    }

    public void setMovtransaccCollection(Collection<Movtransacc> movtransaccCollection) {
        this.movtransaccCollection = movtransaccCollection;
    }

    public Integer getCajaId() {
        return cajaId;
    }

    public void setCajaId(Integer cajaId) {
        this.cajaId = cajaId;
    }

    public BigDecimal getFactSubt12() {
        return factSubt12;
    }

    public void setFactSubt12(BigDecimal factSubt12) {
        this.factSubt12 = factSubt12;
    }
    
    
}
