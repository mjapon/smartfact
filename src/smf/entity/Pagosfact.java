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
@Table(name = "pagosfact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagosfact.findAll", query = "SELECT p FROM Pagosfact p")
    , @NamedQuery(name = "Pagosfact.findByPgfId", query = "SELECT p FROM Pagosfact p WHERE p.pgfId = :pgfId")
    , @NamedQuery(name = "Pagosfact.findByPgfMonto", query = "SELECT p FROM Pagosfact p WHERE p.pgfMonto = :pgfMonto")
    , @NamedQuery(name = "Pagosfact.findByPgfSaldo", query = "SELECT p FROM Pagosfact p WHERE p.pgfSaldo = :pgfSaldo")
    , @NamedQuery(name = "Pagosfact.findByPgfObs", query = "SELECT p FROM Pagosfact p WHERE p.pgfObs = :pgfObs")
    , @NamedQuery(name = "Pagosfact.findByPgfFecreg", query = "SELECT p FROM Pagosfact p WHERE p.pgfFecreg = :pgfFecreg")})
public class Pagosfact implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pgfId")
    private Collection<Pagosfactcaja> pagosfactcajaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pgf_id")
    private Integer pgfId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "pgf_monto")
    private BigDecimal pgfMonto;
    @Column(name = "pgf_saldo")
    private BigDecimal pgfSaldo;
    @Column(name = "pgf_obs")
    private String pgfObs;
    
    @Column(name = "pgf_fecreg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pgfFecreg;
    @JoinColumn(name = "sp_id", referencedColumnName = "sp_id")
    @ManyToOne(optional = false)
    private Estadospago spId;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne(optional = false)
    private Facturas factId;
    @JoinColumn(name = "fp_id", referencedColumnName = "fp_id")
    @ManyToOne(optional = false)
    private Formaspago fpId;

    public Pagosfact() {
    }

    public Pagosfact(Integer pgfId) {
        this.pgfId = pgfId;
    }

    public Pagosfact(Integer pgfId, BigDecimal pgfMonto) {
        this.pgfId = pgfId;
        this.pgfMonto = pgfMonto;
    }

    public Integer getPgfId() {
        return pgfId;
    }

    public void setPgfId(Integer pgfId) {
        this.pgfId = pgfId;
    }

    public BigDecimal getPgfMonto() {
        return pgfMonto;
    }

    public void setPgfMonto(BigDecimal pgfMonto) {
        this.pgfMonto = pgfMonto;
    }

    public BigDecimal getPgfSaldo() {
        return pgfSaldo;
    }

    public void setPgfSaldo(BigDecimal pgfSaldo) {
        this.pgfSaldo = pgfSaldo;
    }

    public String getPgfObs() {
        return pgfObs;
    }

    public void setPgfObs(String pgfObs) {
        this.pgfObs = pgfObs;
    }

    public Date getPgfFecreg() {
        return pgfFecreg;
    }

    public void setPgfFecreg(Date pgfFecreg) {
        this.pgfFecreg = pgfFecreg;
    }

    public Estadospago getSpId() {
        return spId;
    }

    public void setSpId(Estadospago spId) {
        this.spId = spId;
    }

    public Facturas getFactId() {
        return factId;
    }

    public void setFactId(Facturas factId) {
        this.factId = factId;
    }

    public Formaspago getFpId() {
        return fpId;
    }

    public void setFpId(Formaspago fpId) {
        this.fpId = fpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pgfId != null ? pgfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagosfact)) {
            return false;
        }
        Pagosfact other = (Pagosfact) object;
        if ((this.pgfId == null && other.pgfId != null) || (this.pgfId != null && !this.pgfId.equals(other.pgfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Pagosfact[ pgfId=" + pgfId + " ]";
    }

    @XmlTransient
    public Collection<Pagosfactcaja> getPagosfactcajaCollection() {
        return pagosfactcajaCollection;
    }

    public void setPagosfactcajaCollection(Collection<Pagosfactcaja> pagosfactcajaCollection) {
        this.pagosfactcajaCollection = pagosfactcajaCollection;
    }
    
}
