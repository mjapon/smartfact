/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.FacturasJpaController;
import smf.util.datamodels.rows.RowFactByCaja;

/**
 *
 * @author mjapon
 */
public class HistServCliDM extends GenericDataModel<RowFactByCaja>{
    
    private FacturasJpaController controller;
    private Integer cliId;
    
    public HistServCliDM(List<JTableColumn> columns, FacturasJpaController cntrl, Integer cliId) {
        super(columns);
        this.controller  = cntrl;
        this.cliId = cliId;
    }

    @Override
    public void loadFromDataBase() {
        items = controller.listarHistByCli(cliId, 1);
    }
    
    
}
