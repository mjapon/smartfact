/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.entity;

import java.io.Serializable;
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
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
    , @NamedQuery(name = "Empresa.findByEmpId", query = "SELECT e FROM Empresa e WHERE e.empId = :empId")
    , @NamedQuery(name = "Empresa.findByEmpRazonsocial", query = "SELECT e FROM Empresa e WHERE e.empRazonsocial = :empRazonsocial")
    , @NamedQuery(name = "Empresa.findByEmpRuc", query = "SELECT e FROM Empresa e WHERE e.empRuc = :empRuc")
    , @NamedQuery(name = "Empresa.findByEmpDireccion", query = "SELECT e FROM Empresa e WHERE e.empDireccion = :empDireccion")
    , @NamedQuery(name = "Empresa.findByEmpTelf", query = "SELECT e FROM Empresa e WHERE e.empTelf = :empTelf")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_id")
    private Integer empId;
    @Basic(optional = false)
    @Column(name = "emp_razonsocial")
    private String empRazonsocial;
    @Column(name = "emp_ruc")
    private String empRuc;
    @Column(name = "emp_direccion")
    private String empDireccion;
    @Column(name = "emp_telf")
    private String empTelf;    
    @Column(name = "emp_nombrecomercial")
    private String nombreComercial;
    @Column(name = "emp_logo")
    private String empLogo;

    public Empresa() {
    }

    public Empresa(Integer empId) {
        this.empId = empId;
    }

    public Empresa(Integer empId, String empRazonsocial) {
        this.empId = empId;
        this.empRazonsocial = empRazonsocial;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpRazonsocial() {
        return empRazonsocial;
    }

    public void setEmpRazonsocial(String empRazonsocial) {
        this.empRazonsocial = empRazonsocial;
    }

    public String getEmpRuc() {
        return empRuc;
    }

    public void setEmpRuc(String empRuc) {
        this.empRuc = empRuc;
    }

    public String getEmpDireccion() {
        return empDireccion;
    }

    public void setEmpDireccion(String empDireccion) {
        this.empDireccion = empDireccion;
    }

    public String getEmpTelf() {
        return empTelf;
    }

    public void setEmpTelf(String empTelf) {
        this.empTelf = empTelf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }
    
    public String getEmpLogo() {
        return empLogo;
    }

    public void setEmpLogo(String empLogo) {
        this.empLogo = empLogo;
    }

    @Override
    public String toString() {
        return "jj.entity.Empresa[ empId=" + empId + " ]";
    }
    
}
