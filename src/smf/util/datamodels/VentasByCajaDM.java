/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import smf.controller.FacturasJpaController;
import smf.util.ParamsBusquedaTransacc;
import smf.util.datamodels.rows.FilaVenta;
import smf.util.datamodels.rows.RowFactByCaja;

/**
 *
 * @author mjapon
 */
public class VentasByCajaDM extends GenericDataModel<RowFactByCaja>{
    
    private FacturasJpaController controller;
    private ParamsBusquedaTransacc paramsBusquedaTransacc;
    private TotalesVentasModel totalesVentasModel;

    public VentasByCajaDM(List<JTableColumn> columns, FacturasJpaController controller) {
        super(columns);
        this.controller = controller;
    }

    @Override
    public void updateTotales() {
        totalesVentasModel = new TotalesVentasModel();
        totalesVentasModel.setUtilidadesMapEfectivo(new HashMap<>());
        BigDecimal sumaIva = BigDecimal.ZERO;
        BigDecimal sumaDesc = BigDecimal.ZERO;
        BigDecimal sumaTotal = BigDecimal.ZERO;
        BigDecimal sumaEfectivo = BigDecimal.ZERO;
        BigDecimal sumaCredito = BigDecimal.ZERO;
        BigDecimal sumaSaldo = BigDecimal.ZERO;
        BigDecimal sumaUtilidades = BigDecimal.ZERO;
        
        for (RowFactByCaja rowItem: this.items){
            sumaIva = sumaIva.add(rowItem.getIva());
            sumaDesc = sumaDesc.add(rowItem.getDetfDesc());
            sumaTotal = sumaTotal.add(rowItem.getTotal());
            sumaEfectivo = sumaEfectivo.add(rowItem.getTotal());
            sumaCredito = sumaCredito.add(BigDecimal.ZERO);
            sumaSaldo = sumaSaldo.add(BigDecimal.ZERO);
            sumaUtilidades = sumaUtilidades.add(BigDecimal.ZERO);
        }
        
        totalesVentasModel.setSumaIva(sumaIva);
        totalesVentasModel.setSumaDesc(sumaDesc);
        totalesVentasModel.setSumaTotal(sumaTotal);
        totalesVentasModel.setSumaEfectivo(sumaEfectivo);
        totalesVentasModel.setSumaCredito(sumaCredito);
        totalesVentasModel.setSumaSaldo(sumaSaldo);
        totalesVentasModel.setUtilidades(sumaUtilidades);        
    }
    
    

    @Override
    public void loadFromDataBase() {
        items = controller.listarByCaja(paramsBusquedaTransacc);
        updateTotales();
    }    

    public ParamsBusquedaTransacc getParamsBusquedaTransacc() {
        return paramsBusquedaTransacc;
    }

    public void setParamsBusquedaTransacc(ParamsBusquedaTransacc paramsBusquedaTransacc) {
        this.paramsBusquedaTransacc = paramsBusquedaTransacc;
    }

    public TotalesVentasModel getTotalesVentasModel() {
        return totalesVentasModel;
    }

    public void setTotalesVentasModel(TotalesVentasModel totalesVentasModel) {
        this.totalesVentasModel = totalesVentasModel;
    }
    
    
}
