/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.UnidadesJpaController;
import smf.util.datamodels.rows.FilaUnidad;

/**
 *
 * @author manuel.japon
 */
public class UnidadesDataModel extends GenericDataModel<FilaUnidad>{
    
    private UnidadesJpaController unidadesControler;
    
    public UnidadesDataModel(List<JTableColumn> columns, UnidadesJpaController controller) {
        super(columns);
        unidadesControler = controller;
    }
    
    @Override
    public void loadFromDataBase() {
        items = unidadesControler.listar();
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaUnidad row) {
        
    }

    @Override
    public boolean validar(FilaUnidad filaArt) {
        return true;
    }

    @Override
    public FilaUnidad getNewFromRow(Object[] dbRow) {
        return null;
    }
}