/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.TicketsJpaController;
import smf.util.ParamsBuscaTickets;
import smf.util.datamodels.rows.TicketRow;

/**
 *
 * @author mjapon
 */
public class TicketsDM extends GenericDataModel<TicketRow>{
    
    private TicketsJpaController controller;
    private ParamsBuscaTickets params;

    public TicketsDM(List<JTableColumn> columns, TicketsJpaController controller) {
        super(columns);
        this.controller = controller;
    }

    @Override
    public void loadFromDataBase() {
        items = controller.listar(params);
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(TicketRow row) {
        
    }

    @Override
    public boolean validar(TicketRow filaArt) {
        return true;
    }

    @Override
    public TicketRow getNewFromRow(Object[] dbRow) {
        return null;
    }    

    public ParamsBuscaTickets getParams() {
        return params;
    }

    public void setParams(ParamsBuscaTickets params) {
        this.params = params;
    }
    
}
