/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.List;
import smf.controller.CatCajasJpaController;
import smf.util.datamodels.rows.FilaPagoByCaja;

/**
 *
 * @author mjapon
 */
public class PagosByCajaDM extends GenericDataModel<FilaPagoByCaja>{
    
    private CatCajasJpaController catCajasController;
    private BigDecimal montoEfectivo;

    public PagosByCajaDM(List<JTableColumn> columns, CatCajasJpaController cntrl,
            BigDecimal montoEfectivo) {
        super(columns);
        this.catCajasController = cntrl;
        this.montoEfectivo = montoEfectivo;
    }

    @Override
    public void loadFromDataBase() {
        items = catCajasController.listForGridPagosFact(montoEfectivo);
    }
    
    public boolean setValorEfectivoCaja(Integer catCajaId){
        boolean pagoSet = false;
        for (FilaPagoByCaja filaPago: items){
            if (filaPago.getCatCajaId().intValue() == catCajaId.intValue() ){
                pagoSet = true;
                filaPago.setMonto(montoEfectivo);
            }
        }
        
        if (pagoSet){
            //asegurarse que el resto de cajas tengan valor cero
            items.stream().filter((filaPago) -> (filaPago.getCatCajaId().intValue() != catCajaId.intValue() )).forEachOrdered((filaPago) -> {
                filaPago.setMonto(BigDecimal.ZERO);
            });
        }
              
        return pagoSet;
    }
    
}
