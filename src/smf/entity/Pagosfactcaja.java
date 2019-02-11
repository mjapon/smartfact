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
@Table(name = "pagosfactcaja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagosfactcaja.findAll", query = "SELECT p FROM Pagosfactcaja p")
    , @NamedQuery(name = "Pagosfactcaja.findByPgcjId", query = "SELECT p FROM Pagosfactcaja p WHERE p.pgcjId = :pgcjId")
    , @NamedQuery(name = "Pagosfactcaja.findByPgcjMonto", query = "SELECT p FROM Pagosfactcaja p WHERE p.pgcjMonto = :pgcjMonto")})
public class Pagosfactcaja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pgcj_id")
    private Integer pgcjId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pgcj_monto")
    private BigDecimal pgcjMonto;
    @JoinColumn(name = "cc_id", referencedColumnName = "cc_id")
    @ManyToOne(optional = false)
    private Catcajas ccId;
    @JoinColumn(name = "pgf_id", referencedColumnName = "pgf_id")
    @ManyToOne(optional = false)
    private Pagosfact pgfId;

    public Pagosfactcaja() {
    }

    public Pagosfactcaja(Integer pgcjId) {
        this.pgcjId = pgcjId;
    }

    public Integer getPgcjId() {
        return pgcjId;
    }

    public void setPgcjId(Integer pgcjId) {
        this.pgcjId = pgcjId;
    }

    public BigDecimal getPgcjMonto() {
        return pgcjMonto;
    }

    public void setPgcjMonto(BigDecimal pgcjMonto) {
        this.pgcjMonto = pgcjMonto;
    }

    public Catcajas getCcId() {
        return ccId;
    }

    public void setCcId(Catcajas ccId) {
        this.ccId = ccId;
    }

    public Pagosfact getPgfId() {
        return pgfId;
    }

    public void setPgfId(Pagosfact pgfId) {
        this.pgfId = pgfId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pgcjId != null ? pgcjId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagosfactcaja)) {
            return false;
        }
        Pagosfactcaja other = (Pagosfactcaja) object;
        if ((this.pgcjId == null && other.pgcjId != null) || (this.pgcjId != null && !this.pgcjId.equals(other.pgcjId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Pagosfactcaja[ pgcjId=" + pgcjId + " ]";
    }
    
}
