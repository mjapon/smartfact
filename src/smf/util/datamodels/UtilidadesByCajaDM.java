/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import smf.controller.CatCajasJpaController;
import smf.util.NumbersUtil;
import smf.util.datamodels.rows.FilaCatCaja;
import smf.util.datamodels.rows.FilaUtilsByCaja;

/**
 *
 * @author mjapon
 */
public class UtilidadesByCajaDM extends GenericDataModel<FilaUtilsByCaja>{
    
    private CatCajasJpaController cajasJpaController;
    private Map<Integer, BigDecimal> utilidadesMap;
    private BigDecimal total;

    public UtilidadesByCajaDM(List<JTableColumn> columns, CatCajasJpaController controller) {
        super(columns);
        this.cajasJpaController = controller;
    }
    

    @Override
    public void loadFromDataBase() {
        List<FilaCatCaja> tmpList = cajasJpaController.listForGrid();
        items.clear();
        total = BigDecimal.ZERO;
        for (FilaCatCaja item: tmpList){
            BigDecimal utilidades = BigDecimal.ZERO;
            if (utilidadesMap.containsKey(item.getCatCajaId())){
                utilidades = utilidadesMap.get(item.getCatCajaId());
            }
            FilaUtilsByCaja newRow = new FilaUtilsByCaja(item.getNombre(), NumbersUtil.round2(utilidades) );
            items.add(newRow);
            total = total.add(utilidades);
        }
    }

    @Override
    public void updateTotales() {
        
    }

    @Override
    public void persistOnDb(FilaUtilsByCaja row) {
        
    }

    @Override
    public boolean validar(FilaUtilsByCaja filaArt) {
        return true;
    }

    @Override
    public FilaUtilsByCaja getNewFromRow(Object[] dbRow) {
        return null;
    }

    public Map<Integer, BigDecimal> getUtilidadesMap() {
        return utilidadesMap;
    }

    public void setUtilidadesMap(Map<Integer, BigDecimal> utilidadesMap) {
        this.utilidadesMap = utilidadesMap;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
