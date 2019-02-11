/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.CajaJpaController;
import smf.entity.Caja;
import smf.util.datamodels.rows.FilaDatosCaja;

/**
 *
 * @author mjapon
 */
public class AperturaCajaDM extends GenericDataModel<FilaDatosCaja>{
    
    private CajaJpaController cajaCntrl;
    private Caja cajaAnt;
    
    public AperturaCajaDM(List<JTableColumn> columns, CajaJpaController controller){
        super(columns);
        cajaCntrl = controller;
    }

    @Override
    public void loadFromDataBase() {
        items = cajaCntrl.getDatosAperturaCaja(cajaAnt, true);
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

    public Caja getCajaAnt() {
        return cajaAnt;
    }

    public void setCajaAnt(Caja cajaAnt) {
        this.cajaAnt = cajaAnt;
    }
    
}
