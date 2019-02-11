/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.IFindItem;
import smf.util.datamodels.rows.FilaItemBuscar;

/**
 *
 * @author manuel.japon
 */
public class ItemBuscarDM extends GenericDataModel<FilaItemBuscar>{
    
    private IFindItem controller;
    private String filtro;    
    
    public ItemBuscarDM(List<JTableColumn> columns, IFindItem controller) {
        super(columns);
        this.controller = controller;
    }

    @Override
    public void loadFromDataBase() {
        String[] sorts = getSortOrdColumn();
        items = controller.listarFindItem(filtro,sorts[0],sorts[1]);
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}
