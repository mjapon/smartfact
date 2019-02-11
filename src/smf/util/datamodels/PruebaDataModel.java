/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import smf.controller.ArticulosJpaController;
import smf.gui.SmartFactMain;
import smf.util.datamodels.rows.FilaArticulo;

/**
 *
 * @author manuel.japon
 */
public class PruebaDataModel extends GenericDataModel<FilaArticulo> {

    private ArticulosJpaController controller;

    public PruebaDataModel(List<JTableColumn> columns, ArticulosJpaController cntrl) {
        super(columns);
        this.controller = cntrl;
    }
    
    @Override
    public FilaArticulo getNewFromRow(Object[] dbRow) {

        FilaArticulo filaArticulo = new FilaArticulo(
                (Integer) dbRow[0],
                (String) dbRow[2],
                (String) dbRow[1],
                (BigDecimal) dbRow[3],
                (BigDecimal) dbRow[4],
                (BigDecimal) dbRow[5],
                (boolean) dbRow[8],
                (BigDecimal) dbRow[6],
                (String) dbRow[10],
                (String) dbRow[11],
                0,
                (Integer)dbRow[12]
        );
        return filaArticulo;
    }

    @Override
    public void loadFromDataBase() {
        try {
            String[] sortValues = getSortOrdColumn();
            List<Object[]> arts = controller.listarRaw(sortValues[0], sortValues[1]);
            items = arts.stream().map(row -> getNewFromRow(row)).collect(Collectors.toList());
            updateTotales();
            fireTableDataChanged();
        } catch (Throwable ex) {
            logError(ex);
        }
    }

    @Override
    public void updateTotales() {
        System.out.println("update totales");
    }

    @Override
    public void persistOnDb(FilaArticulo row) {
        try {
            if (row.getArtId() > 0) {
                controller.actualizarArticulo(row);
                updateTotales();
                fireTableDataChanged();

                //if (updated){
                SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                //}
            }
        } catch (Throwable ex) {
            showMsgError(ex);
        }
    }

    @Override
    public boolean validar(FilaArticulo filaArt) {
        return true;
    }
}