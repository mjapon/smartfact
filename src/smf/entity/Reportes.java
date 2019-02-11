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
@Table(name = "reportes")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Reportes.findAll", query = "SELECT r FROM Reportes r order by r.repId")
    , @NamedQuery(name = "Reportes.findByRepId", query = "SELECT r FROM Reportes r WHERE r.repId = :repId")
    , @NamedQuery(name = "Reportes.findByRepNombre", query = "SELECT r FROM Reportes r WHERE r.repNombre = :repNombre")
    , @NamedQuery(name = "Reportes.findByRepParams", query = "SELECT r FROM Reportes r WHERE r.repParams = :repParams")
    , @NamedQuery(name = "Reportes.findByRepPath", query = "SELECT r FROM Reportes r WHERE r.repPath = :repPath")})
public class Reportes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rep_id")
    private Integer repId;
    @Basic(optional = false)
    @Column(name = "rep_nombre")
    private String repNombre;
    @Column(name = "rep_params")
    private String repParams;
    @Basic(optional = false)
    @Column(name = "rep_path")
    private String repPath;
    
    @Column(name = "rep_abr", nullable = false)
    private String repAbr;

    public Reportes() {
    }

    public Reportes(Integer repId) {
        this.repId = repId;
    }

    public Reportes(Integer repId, String repNombre, String repPath) {
        this.repId = repId;
        this.repNombre = repNombre;
        this.repPath = repPath;
    }

    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public String getRepNombre() {
        return repNombre;
    }

    public void setRepNombre(String repNombre) {
        this.repNombre = repNombre;
    }

    public String getRepParams() {
        return repParams;
    }

    public void setRepParams(String repParams) {
        this.repParams = repParams;
    }

    public String getRepPath() {
        return repPath;
    }

    public void setRepPath(String repPath) {
        this.repPath = repPath;
    }

    public String getRepAbr() {
        return repAbr;
    }

    public void setRepAbr(String repAbr) {
        this.repAbr = repAbr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repId != null ? repId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reportes)) {
            return false;
        }
        Reportes other = (Reportes) object;
        if ((this.repId == null && other.repId != null) || (this.repId != null && !this.repId.equals(other.repId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jj.entity.Reportes[ repId=" + repId + " ]";
    }
    
}
