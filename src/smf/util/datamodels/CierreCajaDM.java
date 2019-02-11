/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smf.controller.CajaJpaController;
import smf.entity.Caja;
import smf.util.NumbersUtil;
import smf.util.TotalesCuentasXPC;
import smf.util.datamodels.rows.FilaDatosCaja;

/**
 *
 * @author mjapon
 */
public class CierreCajaDM extends GenericDataModel<FilaDatosCaja>{
    
    private CajaJpaController cajaCntrl;
    private Caja caja;
    
    private TotalesVentasModel totalesVentas;
    private TotalesVentasModel totalesCompras;
    
    private TotalesCuentasXPC totalesCXC;
    private TotalesCuentasXPC totalesCXP;

    public CierreCajaDM(List<JTableColumn> columns, CajaJpaController controller){
        super(columns);
        cajaCntrl = controller;
    }
    
    @Override
    public void loadFromDataBase() {
        items = cajaCntrl.getDatosAperturaCaja(caja, false);
        
        Map<Integer, BigDecimal> sumasVentasMap = totalesVentas.getSumasCajaMap();
        Map<Integer, BigDecimal> sumasComprasMap = totalesCompras.getSumasCajaMap();
        Map<Integer, BigDecimal> sumasCxC = totalesCXC.getTotalByCajaMap();
        Map<Integer, BigDecimal> sumasCxP = totalesCXP.getTotalByCajaMap();
        
        if (sumasVentasMap == null){
            sumasVentasMap = new HashMap<>();
        }
        
        if (sumasComprasMap == null){
            sumasComprasMap= new HashMap<>();
        }
        
        if (sumasCxC == null){
            sumasCxC = new HashMap<>();
        }
        
        if (sumasCxP == null){
            sumasCxP = new HashMap<>();
        }
        
        //setear los valores del dia
        for (FilaDatosCaja fila: items){
            Integer catCajaId = fila.getCatCajaId();
            if (sumasVentasMap.containsKey(catCajaId)){
                fila.setTotalVentas( NumbersUtil.round2( sumasVentasMap.get(catCajaId) ) );
            }
            else{
                fila.setTotalVentas( BigDecimal.ZERO );
            }            
            
            if (sumasComprasMap.containsKey(catCajaId)){
                fila.setTotalCompras( NumbersUtil.round2( sumasComprasMap.get(catCajaId) ) );
            }
            else{
                fila.setTotalCompras( BigDecimal.ZERO );
            }
            
            if (sumasCxC.containsKey(catCajaId)){
               fila.setTotalAbCobrados(NumbersUtil.round2( sumasCxC.get(catCajaId) ) );
            }
            else{
                fila.setTotalAbCobrados(BigDecimal.ZERO );
            }
            
            if (sumasCxP.containsKey(catCajaId)){
               fila.setTotalAbPagados(NumbersUtil.round2( sumasCxP.get(catCajaId) ) );
            }
            else{
                fila.setTotalAbPagados(BigDecimal.ZERO );
            }      
            
            fila.setValorAjuste(BigDecimal.ZERO);
            
            BigDecimal saldoFinal = 
             fila.getSaldoInicial().add(fila.getTotalVentas()).subtract(fila.getTotalCompras()).add(fila.getTotalAbCobrados()).subtract(fila.getTotalAbPagados()) ;
            
            fila.setSaldoFinal(NumbersUtil.round2(saldoFinal) );            
            
        }
        
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaDatosCaja row) {
        
    }

    @Override
    public boolean validar(FilaDatosCaja filaArt) {
        return true;
    }

    @Override
    public FilaDatosCaja getNewFromRow(Object[] dbRow) {
       return null;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public TotalesVentasModel getTotalesVentas() {
        return totalesVentas;
    }

    public void setTotalesVentas(TotalesVentasModel totalesVentas) {
        this.totalesVentas = totalesVentas;
    }

    public TotalesVentasModel getTotalesCompras() {
        return totalesCompras;
    }

    public void setTotalesCompras(TotalesVentasModel totalesCompras) {
        this.totalesCompras = totalesCompras;
    }

    public TotalesCuentasXPC getTotalesCXC() {
        return totalesCXC;
    }

    public void setTotalesCXC(TotalesCuentasXPC totalesCXC) {
        this.totalesCXC = totalesCXC;
    }

    public TotalesCuentasXPC getTotalesCXP() {
        return totalesCXP;
    }

    public void setTotalesCXP(TotalesCuentasXPC totalesCXP) {
        this.totalesCXP = totalesCXP;
    }
    
    
}
