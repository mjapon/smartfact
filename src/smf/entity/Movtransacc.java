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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "movtransacc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movtransacc.findAll", query = "SELECT m FROM Movtransacc m")
    , @NamedQuery(name = "Movtransacc.findByMovId", query = "SELECT m FROM Movtransacc m WHERE m.movId = :movId")
    , @NamedQuery(name = "Movtransacc.findByMovMonto", query = "SELECT m FROM Movtransacc m WHERE m.movMonto = :movMonto")
    , @NamedQuery(name = "Movtransacc.findByMovObserv", query = "SELECT m FROM Movtransacc m WHERE m.movObserv = :movObserv")
    , @NamedQuery(name = "Movtransacc.findByMovValido", query = "SELECT m FROM Movtransacc m WHERE m.movValido = :movValido")
    , @NamedQuery(name = "Movtransacc.findByMovFechareg", query = "SELECT m FROM Movtransacc m WHERE m.movFechareg = :movFechareg")})
public class Movtransacc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mov_id")
    private Integer movId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "mov_monto")
    private BigDecimal movMonto;
    @Column(name = "mov_observ")
    private String movObserv;
    
    @Column(name = "pgf_id")
    private Integer pgfId;
    
    @Column(name = "mov_valido")
    private Integer movValido;
    @Column(name = "mov_fechareg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date movFechareg;
    @JoinColumn(name = "fact_id_rel", referencedColumnName = "fact_id")
    @ManyToOne(optional = false)
    private Facturas factIdRel;
    @JoinColumn(name = "tra_id", referencedColumnName = "tra_id")
    @OneToOne
    private Transacciones traId;
    
    @Column(name = "cj_id")
    private Integer cjId;

    public Movtransacc() {
    }

    public Movtransacc(Integer movId) {
        this.movId = movId;
    }

    public Movtransacc(Integer movId, BigDecimal movMonto) {
        this.movId = movId;
        this.movMonto = movMonto;
    }

    public Integer getMovId() {
        return movId;
    }

    public void setMovId(Integer movId) {
        this.movId = movId;
    }

    public BigDecimal getMovMonto() {
        return movMonto;
    }

    public void setMovMonto(BigDecimal movMonto) {
        this.movMonto = movMonto;
    }

    public String getMovObserv() {
        return movObserv;
    }

    public void setMovObserv(String movObserv) {
        this.movObserv = movObserv;
    }

    public Integer getMovValido() {
        return movValido;
    }

    public void setMovValido(Integer movValido) {
        this.movValido = movValido;
    }

    public Date getMovFechareg() {
        return movFechareg;
    }

    public void setMovFechareg(Date movFechareg) {
        this.movFechareg = movFechareg;
    }

    public Facturas getFactIdRel() {
        return factIdRel;
    }

    public void setFactIdRel(Facturas factIdRel) {
        this.factIdRel = factIdRel;
    }

    public Transacciones getTraId() {
        return traId;
    }

    public void setTraId(Transacciones traId) {
        this.traId = traId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movId != null ? movId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movtransacc)) {
            return false;
        }
        Movtransacc other = (Movtransacc) object;
        if ((this.movId == null && other.movId != null) || (this.movId != null && !this.movId.equals(other.movId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Movtransacc[ movId=" + movId + " ]";
    }

    public Integer getPgfId() {
        return pgfId;
    }

    public void setPgfId(Integer pgfId) {
        this.pgfId = pgfId;
    }

    public Integer getCjId() {
        return cjId;
    }

    public void setCjId(Integer cjId) {
        this.cjId = cjId;
    }

   
    
}
