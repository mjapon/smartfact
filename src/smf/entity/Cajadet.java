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
 * @author mjapon
 */
@Entity
@Table(name = "cajadet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cajadet.findAll", query = "SELECT c FROM Cajadet c")
    , @NamedQuery(name = "Cajadet.findByDcId", query = "SELECT c FROM Cajadet c WHERE c.dcId = :dcId")
    , @NamedQuery(name = "Cajadet.findByDcSaldoini", query = "SELECT c FROM Cajadet c WHERE c.dcSaldoini = :dcSaldoini")
    , @NamedQuery(name = "Cajadet.findByDcVentas", query = "SELECT c FROM Cajadet c WHERE c.dcVentas = :dcVentas")
    , @NamedQuery(name = "Cajadet.findByDcAboncob", query = "SELECT c FROM Cajadet c WHERE c.dcAboncob = :dcAboncob")
    , @NamedQuery(name = "Cajadet.findByDcAbonpag", query = "SELECT c FROM Cajadet c WHERE c.dcAbonpag = :dcAbonpag")
    , @NamedQuery(name = "Cajadet.findByDcAjuste", query = "SELECT c FROM Cajadet c WHERE c.dcAjuste = :dcAjuste")
    , @NamedQuery(name = "Cajadet.findByDcSaldofin", query = "SELECT c FROM Cajadet c WHERE c.dcSaldofin = :dcSaldofin")})
public class Cajadet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dc_id")
    private Integer dcId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dc_saldoini")
    private BigDecimal dcSaldoini;
    @Column(name = "dc_ventas")
    private BigDecimal dcVentas;
    @Column(name = "dc_aboncob")
    private BigDecimal dcAboncob;
    @Column(name = "dc_abonpag")
    private BigDecimal dcAbonpag;
    @Column(name = "dc_ajuste")
    private BigDecimal dcAjuste;
    @Column(name = "dc_saldofin")
    private BigDecimal dcSaldofin;
    
    @Column(name = "dc_saldoant")
    private BigDecimal dcSaldoAnt;
    
    @Column(name = "dc_compras")
    private BigDecimal dcCompras;
    
    @JoinColumn(name = "cj_id", referencedColumnName = "cj_id")
    @ManyToOne(optional = false)
    private Caja cjId;
    @JoinColumn(name = "cc_id", referencedColumnName = "cc_id")
    @ManyToOne(optional = false)
    private Catcajas ccId;

    public Cajadet() {
    }

    public Cajadet(Integer dcId) {
        this.dcId = dcId;
    }

    public Integer getDcId() {
        return dcId;
    }

    public void setDcId(Integer dcId) {
        this.dcId = dcId;
    }

    public BigDecimal getDcSaldoini() {
        return dcSaldoini;
    }

    public void setDcSaldoini(BigDecimal dcSaldoini) {
        this.dcSaldoini = dcSaldoini;
    }

    public BigDecimal getDcVentas() {
        return dcVentas;
    }

    public void setDcVentas(BigDecimal dcVentas) {
        this.dcVentas = dcVentas;
    }

    public BigDecimal getDcAboncob() {
        return dcAboncob;
    }

    public void setDcAboncob(BigDecimal dcAboncob) {
        this.dcAboncob = dcAboncob;
    }

    public BigDecimal getDcAbonpag() {
        return dcAbonpag;
    }

    public void setDcAbonpag(BigDecimal dcAbonpag) {
        this.dcAbonpag = dcAbonpag;
    }

    public BigDecimal getDcAjuste() {
        return dcAjuste;
    }

    public void setDcAjuste(BigDecimal dcAjuste) {
        this.dcAjuste = dcAjuste;
    }

    public BigDecimal getDcSaldofin() {
        return dcSaldofin;
    }

    public void setDcSaldofin(BigDecimal dcSaldofin) {
        this.dcSaldofin = dcSaldofin;
    }

    public Caja getCjId() {
        return cjId;
    }

    public void setCjId(Caja cjId) {
        this.cjId = cjId;
    }

    public Catcajas getCcId() {
        return ccId;
    }

    public void setCcId(Catcajas ccId) {
        this.ccId = ccId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcId != null ? dcId.hashCode() : 0);
        return hash;
    }

    public BigDecimal getDcSaldoAnt() {
        return dcSaldoAnt;
    }

    public void setDcSaldoAnt(BigDecimal dcSaldoAnt) {
        this.dcSaldoAnt = dcSaldoAnt;
    }

    public BigDecimal getDcCompras() {
        return dcCompras;
    }

    public void setDcCompras(BigDecimal dcCompras) {
        this.dcCompras = dcCompras;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajadet)) {
            return false;
        }
        Cajadet other = (Cajadet) object;
        if ((this.dcId == null && other.dcId != null) || (this.dcId != null && !this.dcId.equals(other.dcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Cajadet[ dcId=" + dcId + " ]";
    }
    
}
