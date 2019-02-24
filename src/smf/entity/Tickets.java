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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mjapon
 */
@Entity
@Table(name = "tickets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tickets.findAll", query = "SELECT t FROM Tickets t")
    , @NamedQuery(name = "Tickets.findByTkId", query = "SELECT t FROM Tickets t WHERE t.tkId = :tkId")
    , @NamedQuery(name = "Tickets.findByTkMonto", query = "SELECT t FROM Tickets t WHERE t.tkMonto = :tkMonto")
    , @NamedQuery(name = "Tickets.findByTkFecreg", query = "SELECT t FROM Tickets t WHERE t.tkFecreg = :tkFecreg")
    , @NamedQuery(name = "Tickets.findByTkObs", query = "SELECT t FROM Tickets t WHERE t.tkObs = :tkObs")
    , @NamedQuery(name = "Tickets.findByTkNro", query = "SELECT t FROM Tickets t WHERE t.tkNro = :tkNro")})
public class Tickets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tk_id")
    private Integer tkId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "tk_monto")
    private BigDecimal tkMonto;
    @Basic(optional = false)
    @Column(name = "tk_fecreg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tkFecreg;
    @Column(name = "tk_obs")
    private String tkObs;
    
    @Column(name = "tk_servicios")
    private String tkServicios;
    
    @Column(name = "tk_estado")
    private Integer tkEstado;
    
    @Column(name = "fact_id")
    private Integer factId;
    
    @Basic(optional = false)
    @Column(name = "tk_nro")
    private int tkNro;
    @JoinColumn(name = "cli_id", referencedColumnName = "cli_id")
    @ManyToOne(optional = false)
    private Clientes cliId;

    public Tickets() {
    }

    public Tickets(Integer tkId) {
        this.tkId = tkId;
    }

    public Tickets(Integer tkId, BigDecimal tkMonto, Date tkFecreg, int tkNro) {
        this.tkId = tkId;
        this.tkMonto = tkMonto;
        this.tkFecreg = tkFecreg;
        this.tkNro = tkNro;
    }

    public Integer getTkId() {
        return tkId;
    }

    public void setTkId(Integer tkId) {
        this.tkId = tkId;
    }

    public BigDecimal getTkMonto() {
        return tkMonto;
    }

    public void setTkMonto(BigDecimal tkMonto) {
        this.tkMonto = tkMonto;
    }

    public Date getTkFecreg() {
        return tkFecreg;
    }

    public void setTkFecreg(Date tkFecreg) {
        this.tkFecreg = tkFecreg;
    }

    public String getTkObs() {
        return tkObs;
    }

    public void setTkObs(String tkObs) {
        this.tkObs = tkObs;
    }

    public int getTkNro() {
        return tkNro;
    }

    public void setTkNro(int tkNro) {
        this.tkNro = tkNro;
    }

    public Clientes getCliId() {
        return cliId;
    }

    public void setCliId(Clientes cliId) {
        this.cliId = cliId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkId != null ? tkId.hashCode() : 0);
        return hash;
    }

    public Integer getTkEstado() {
        return tkEstado;
    }

    public void setTkEstado(Integer tkEstado) {
        this.tkEstado = tkEstado;
    }

    public String getTkServicios() {
        return tkServicios;
    }

    public void setTkServicios(String tkServicios) {
        this.tkServicios = tkServicios;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tickets)) {
            return false;
        }
        Tickets other = (Tickets) object;
        if ((this.tkId == null && other.tkId != null) || (this.tkId != null && !this.tkId.equals(other.tkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Tickets[ tkId=" + tkId + " ]";
    }
    
}
