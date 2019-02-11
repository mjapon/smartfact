/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.CajaJpaController;
import smf.util.ParamsBuscaCaja;
import smf.util.datamodels.rows.FilaAdminCaja;

/**
 *
 * @author mjapon
 */
public class AdminCajaDM extends GenericDataModel<FilaAdminCaja>{
    
    private CajaJpaController cajaJpaController;
    private ParamsBuscaCaja params;

    public AdminCajaDM(List<JTableColumn> columns, CajaJpaController cajaJpaController) {
        super(columns);
        this.cajaJpaController = cajaJpaController;
    }

    @Override
    public void loadFromDataBase() {
        items = cajaJpaController.listar(params);
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaAdminCaja row) {
        
    }

    @Override
    public boolean validar(FilaAdminCaja filaArt) {
        return true;   
    }

    @Override
    public FilaAdminCaja getNewFromRow(Object[] dbRow) {
        return null;
    }

    public ParamsBuscaCaja getParams() {
        return params;
    }

    public void setParams(ParamsBuscaCaja params) {
        this.params = params;
    }
    
    
    
}
