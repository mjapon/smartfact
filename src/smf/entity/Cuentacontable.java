/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manuel.japon
 */
@Entity
@Table(name = "cuentacontable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentacontable.findAll", query = "SELECT c FROM Cuentacontable c")
    , @NamedQuery(name = "Cuentacontable.findByCcId", query = "SELECT c FROM Cuentacontable c WHERE c.ccId = :ccId")
    , @NamedQuery(name = "Cuentacontable.findByCcCod", query = "SELECT c FROM Cuentacontable c WHERE c.ccCod = :ccCod")
    , @NamedQuery(name = "Cuentacontable.findByCcNombre", query = "SELECT c FROM Cuentacontable c WHERE c.ccNombre = :ccNombre")
    , @NamedQuery(name = "Cuentacontable.findByCcSigno", query = "SELECT c FROM Cuentacontable c WHERE c.ccSigno = :ccSigno")
    , @NamedQuery(name = "Cuentacontable.findByCcTipocuenta", query = "SELECT c FROM Cuentacontable c WHERE c.ccTipocuenta = :ccTipocuenta")
    , @NamedQuery(name = "Cuentacontable.findByCcTipoestado", query = "SELECT c FROM Cuentacontable c WHERE c.ccTipoestado = :ccTipoestado")
    , @NamedQuery(name = "Cuentacontable.findByCcParent", query = "SELECT c FROM Cuentacontable c WHERE c.ccParent = :ccParent")})
public class Cuentacontable implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "cc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    private BigDecimal ccId;
    @Basic(optional = false)
    @Column(name = "cc_cod")
    private String ccCod;
    @Basic(optional = false)
    @Column(name = "cc_nombre")
    private String ccNombre;
    @Basic(optional = false)
    @Column(name = "cc_signo")
    private Character ccSigno;
    @Basic(optional = false)
    @Column(name = "cc_tipocuenta")
    private Character ccTipocuenta;
    @Column(name = "cc_tipoestado")
    private Integer ccTipoestado;
    @Column(name = "cc_parent")
    private BigInteger ccParent;

    public Cuentacontable() {
    }

    public Cuentacontable(BigDecimal ccId) {
        this.ccId = ccId;
    }

    public Cuentacontable(BigDecimal ccId, String ccCod, String ccNombre, Character ccSigno, Character ccTipocuenta) {
        this.ccId = ccId;
        this.ccCod = ccCod;
        this.ccNombre = ccNombre;
        this.ccSigno = ccSigno;
        this.ccTipocuenta = ccTipocuenta;
    }

    public BigDecimal getCcId() {
        return ccId;
    }

    public void setCcId(BigDecimal ccId) {
        this.ccId = ccId;
    }

    public String getCcCod() {
        return ccCod;
    }

    public void setCcCod(String ccCod) {
        this.ccCod = ccCod;
    }

    public String getCcNombre() {
        return ccNombre;
    }

    public void setCcNombre(String ccNombre) {
        this.ccNombre = ccNombre;
    }

    public Character getCcSigno() {
        return ccSigno;
    }

    public void setCcSigno(Character ccSigno) {
        this.ccSigno = ccSigno;
    }

    public Character getCcTipocuenta() {
        return ccTipocuenta;
    }

    public void setCcTipocuenta(Character ccTipocuenta) {
        this.ccTipocuenta = ccTipocuenta;
    }

    public Integer getCcTipoestado() {
        return ccTipoestado;
    }

    public void setCcTipoestado(Integer ccTipoestado) {
        this.ccTipoestado = ccTipoestado;
    }

    public BigInteger getCcParent() {
        return ccParent;
    }

    public void setCcParent(BigInteger ccParent) {
        this.ccParent = ccParent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccId != null ? ccId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentacontable)) {
            return false;
        }
        Cuentacontable other = (Cuentacontable) object;
        if ((this.ccId == null && other.ccId != null) || (this.ccId != null && !this.ccId.equals(other.ccId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Cuentacontable[ ccId=" + ccId + " ]";
    }
    
}
