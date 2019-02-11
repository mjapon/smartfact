/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author mjapon
 */
public class FilaAdminCaja {
    
    private Integer nroCaja;
    private Integer catCajaId;
    private String nombreCaja;
    private Date fechaApertura;
    private Date fechaCierre;
    private Integer cjEstado;
    private String estado;
    private BigDecimal saldoAnt;
    private BigDecimal saldoIni;
    private BigDecimal sumaVentas;
    private BigDecimal sumaCompras;
    private BigDecimal sumaAbonosCob;
    private BigDecimal sumaAbonosPag;
    private BigDecimal ajuste;
    private BigDecimal saldoFin;
    private String obsApertura;
    private String obsCierre;
    /*
     "select\n" +
                    "  cj.cj_id,\n" +
                    "  cc.cc_id,\n" +
                    "  cc.cc_nombre,\n" +
                    "  cj.cj_fecaper,\n" +
                    "  cj.cj_feccierre,\n" +
                    "  case cj.cj_estado when 0 then 'Abierto'\n" +
                    "                           when 1 then 'Cerrado'\n" +
                    "                           when 2 then 'Anulado' end as estado,\n" +
                    "  cd.dc_saldoant,\n" +
                    "  cd.dc_saldoini,\n" +
                    "  cd.dc_ventas,\n" +
                    "  cd.dc_compras,\n" +
                    "  cd.dc_aboncob,\n" +
                    "  cd.dc_abonpag,\n" +
                    "  cd.dc_ajuste,\n" +
                    "  cd.dc_saldofin,\n" +
                    "  cj.cj_obsaper,\n" +
                    "  cj.cj_obscierre\n" +
                    " from cajadet cd\n" +
                    " join caja cj ON cd.cj_id = cj.cj_id\n " +
                    " join catcajas cc ON cd.cc_id = cc.cc_id\n " +
                    " where cc.cc_id =  " + params.getCatCajaId().toString() +
                    " and date(cj.cj_fecaper) >= date(?paramDesde) "+
                    " AND date(cj.cj_fecaper) <= date(?paramHasta) ";
    */

    public FilaAdminCaja(Integer nroCaja, Integer catCajaId, String nombreCaja, Date fechaApertura, Date fechaCierre, Integer cjEstado, String estado, BigDecimal saldoAnt, BigDecimal saldoIni, BigDecimal sumaVentas, BigDecimal sumaCompras, BigDecimal sumaAbonosCob, BigDecimal sumaAbonosPag, BigDecimal ajuste, BigDecimal saldoFin, String obsApertura, String obsCierre) {
        this.nroCaja = nroCaja;
        this.catCajaId = catCajaId;
        this.nombreCaja = nombreCaja;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.cjEstado = cjEstado;
        this.estado = estado;
        this.saldoAnt = saldoAnt;
        this.saldoIni = saldoIni;
        this.sumaVentas = sumaVentas;
        this.sumaCompras = sumaCompras;
        this.sumaAbonosCob = sumaAbonosCob;
        this.sumaAbonosPag = sumaAbonosPag;
        this.ajuste = ajuste;
        this.saldoFin = saldoFin;
        this.obsApertura = obsApertura;
        this.obsCierre = obsCierre;
    }

    public FilaAdminCaja() {
    }

    public Integer getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(Integer nroCaja) {
        this.nroCaja = nroCaja;
    }

    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Integer getCjEstado() {
        return cjEstado;
    }

    public void setCjEstado(Integer cjEstado) {
        this.cjEstado = cjEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSaldoAnt() {
        return saldoAnt;
    }

    public void setSaldoAnt(BigDecimal saldoAnt) {
        this.saldoAnt = saldoAnt;
    }

    public BigDecimal getSaldoIni() {
        return saldoIni;
    }

    public void setSaldoIni(BigDecimal saldoIni) {
        this.saldoIni = saldoIni;
    }

    public BigDecimal getSumaVentas() {
        return sumaVentas;
    }

    public void setSumaVentas(BigDecimal sumaVentas) {
        this.sumaVentas = sumaVentas;
    }

    public BigDecimal getSumaCompras() {
        return sumaCompras;
    }

    public void setSumaCompras(BigDecimal sumaCompras) {
        this.sumaCompras = sumaCompras;
    }

    public BigDecimal getSumaAbonosCob() {
        return sumaAbonosCob;
    }

    public void setSumaAbonosCob(BigDecimal sumaAbonosCob) {
        this.sumaAbonosCob = sumaAbonosCob;
    }

    public BigDecimal getSumaAbonosPag() {
        return sumaAbonosPag;
    }

    public void setSumaAbonosPag(BigDecimal sumaAbonosPag) {
        this.sumaAbonosPag = sumaAbonosPag;
    }

    public BigDecimal getAjuste() {
        return ajuste;
    }

    public void setAjuste(BigDecimal ajuste) {
        this.ajuste = ajuste;
    }

    public BigDecimal getSaldoFin() {
        return saldoFin;
    }

    public void setSaldoFin(BigDecimal saldoFin) {
        this.saldoFin = saldoFin;
    }

    public String getObsApertura() {
        return obsApertura;
    }

    public void setObsApertura(String obsApertura) {
        this.obsApertura = obsApertura;
    }

    public String getObsCierre() {
        return obsCierre;
    }

    public void setObsCierre(String obsCierre) {
        this.obsCierre = obsCierre;
    }
    
    
    
    
}
