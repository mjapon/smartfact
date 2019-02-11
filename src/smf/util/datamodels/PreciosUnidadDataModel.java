/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.UnidadesJpaController;
import smf.gui.SmartFactMain;
import smf.util.datamodels.rows.FilaUnidadPrecio;

/**
 *
 * @author mjapon
 */
public class PreciosUnidadDataModel extends GenericDataModel<FilaUnidadPrecio>{
    
    private UnidadesJpaController unidadesControler;
    private Integer artId;
    
    public PreciosUnidadDataModel(List<JTableColumn> columns, 
            UnidadesJpaController cntrl, 
            Integer artId){
        super(columns);
        unidadesControler = cntrl;
        this.artId = artId;
    }
    
    @Override
    public void loadFromDataBase() {
        items = unidadesControler.listarPrecios(this.artId);
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaUnidadPrecio row) {
        try{
            unidadesControler.updatePrecio(row);
            SmartFactMain.showSystemTrayMsg("Cambios registrados");
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }

    @Override
    public boolean validar(FilaUnidadPrecio filaArt) {
        return true;
    }

    @Override
    public FilaUnidadPrecio getNewFromRow(Object[] dbRow) {
        return null;
    }
    
    
}
