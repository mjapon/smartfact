/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByCliId", query = "SELECT c FROM Clientes c WHERE c.cliId = :cliId")
    , @NamedQuery(name = "Clientes.findByCliNombres", query = "SELECT c FROM Clientes c WHERE c.cliNombres = :cliNombres")
    , @NamedQuery(name = "Clientes.findByCliApellidos", query = "SELECT c FROM Clientes c WHERE c.cliApellidos = :cliApellidos")
    , @NamedQuery(name = "Clientes.findByCliCi", query = "SELECT c FROM Clientes c WHERE c.cliCi = :cliCi")
    , @NamedQuery(name = "Clientes.findByCliFechareg", query = "SELECT c FROM Clientes c WHERE c.cliFechareg = :cliFechareg")
    , @NamedQuery(name = "Clientes.findByCliDir", query = "SELECT c FROM Clientes c WHERE c.cliDir = :cliDir")
    , @NamedQuery(name = "Clientes.findByCliTelf", query = "SELECT c FROM Clientes c WHERE c.cliTelf = :cliTelf")
    , @NamedQuery(name = "Clientes.findByCliEmail", query = "SELECT c FROM Clientes c WHERE c.cliEmail = :cliEmail")
    , @NamedQuery(name = "Clientes.findByCliMovil", query = "SELECT c FROM Clientes c WHERE c.cliMovil = :cliMovil")})
public class Clientes implements Serializable {

    @Basic(optional = false)
    @Column(name = "cli_tipo")
    private int cliTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliId")
    private Collection<Tickets> ticketsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cli_id")
    private Integer cliId;
    @Column(name = "cli_nombres")
    private String cliNombres;
    @Column(name = "cli_apellidos")
    private String cliApellidos;
    @Column(name = "cli_ci")
    private String cliCi;
    @Column(name = "cli_fechareg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cliFechareg;
    @Column(name = "cli_dir")
    private String cliDir;
    @Column(name = "cli_telf")
    private String cliTelf;
    @Column(name = "cli_email")
    private String cliEmail;
    @Column(name = "cli_movil")
    private String cliMovil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliId")
    private List<Facturas> facturasList;

    public Clientes() {
    }

    public Clientes(Integer cliId) {
        this.cliId = cliId;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public String getCliApellidos() {
        return cliApellidos;
    }

    public void setCliApellidos(String cliApellidos) {
        this.cliApellidos = cliApellidos;
    }

    public String getCliCi() {
        return cliCi;
    }

    public void setCliCi(String cliCi) {
        this.cliCi = cliCi;
    }

    public Date getCliFechareg() {
        return cliFechareg;
    }

    public void setCliFechareg(Date cliFechareg) {
        this.cliFechareg = cliFechareg;
    }

    public String getCliDir() {
        return cliDir;
    }

    public void setCliDir(String cliDir) {
        this.cliDir = cliDir;
    }

    public String getCliTelf() {
        return cliTelf;
    }

    public void setCliTelf(String cliTelf) {
        this.cliTelf = cliTelf;
    }

    public String getCliEmail() {
        return cliEmail;
    }

    public void setCliEmail(String cliEmail) {
        this.cliEmail = cliEmail;
    }

    public String getCliMovil() {
        return cliMovil;
    }

    public void setCliMovil(String cliMovil) {
        this.cliMovil = cliMovil;
    }

    @XmlTransient
    public List<Facturas> getFacturasList() {
        return facturasList;
    }

    public void setFacturasList(List<Facturas> facturasList) {
        this.facturasList = facturasList;
    }
    
    public Integer getCliTipo() {
        return cliTipo;
    }

    public void setCliTipo(Integer cliTipo) {
        this.cliTipo = cliTipo;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }
    
    public String chkNull(String str){
        return str!=null?str:"";
    }

    @Override
    public String toString() {
        //return "jj.entity.Clientes[ cliId=" + cliId + " ]";
        return chkNull(this.cliNombres) + " " + chkNull(this.cliApellidos);
    }

    @XmlTransient
    public Collection<Tickets> getTicketsCollection() {
        return ticketsCollection;
    }

    public void setTicketsCollection(Collection<Tickets> ticketsCollection) {
        this.ticketsCollection = ticketsCollection;
    }
    
}
