/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.CatCajasJpaController;
import smf.util.datamodels.rows.FilaCatCaja;
/**
 *
 * @author manuel.japon
 */
public class CatCajasDataModel extends GenericDataModel<FilaCatCaja>{
    
    private CatCajasJpaController catCajasCntrl;
    
    public CatCajasDataModel(List<JTableColumn> columns, CatCajasJpaController controller) {
        super(columns);
        catCajasCntrl = controller;
    }
    
     @Override
    public void loadFromDataBase() {
        items = catCajasCntrl.listForGrid();
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaCatCaja row) {
        
    }

    @Override
    public boolean validar(FilaCatCaja filaArt) {
        return true;
    }

    @Override
    public FilaCatCaja getNewFromRow(Object[] dbRow) {
        return null;
    }
    
}
