/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import smf.controller.CategoriasJpaController;
import smf.entity.Catcajas;
import smf.gui.SmartFactMain;
import smf.util.datamodels.rows.FilaCategoria;

/**
 *
 * @author manuel.japon
 */
public class CatsDM extends AbstractTableModel{
    
    private List<FilaCategoria> items;
    private CategoriasJpaController dbcntrl;
    private Map<Integer, Catcajas> mapCatCajas;
    
    public enum COLUMN_CAT_ENUM{
        CODIGO(new ColumnDM(0, "Codigo", Integer.class, "", false)),
        NOMBRE(new ColumnDM(1, "Nombre", String.class, "", true)),
        CAJA(new ColumnDM(2, "Caja", String.class, "", true));
        
        public ColumnDM col;        
        private COLUMN_CAT_ENUM(ColumnDM pcol){
            this.col = pcol;
        }
        public ColumnDM getCol(){
            return col;
        }
        public static ColumnDM get(int colIndex){
            COLUMN_CAT_ENUM[] values = COLUMN_CAT_ENUM.values();            
            for (COLUMN_CAT_ENUM col:values){
                if (col.col.getIndex() == colIndex){
                    return col.col;
                }
            }
            return null;
        }
    }
    
    public CatsDM(){
        items = new ArrayList<>();
    }
    
    public void loadFromDataBase(){
        items.clear();
        List<Object[]> auxList =  dbcntrl.listarForGrid();
        auxList.stream().forEach(e->{items.add(new FilaCategoria( (Integer)e[0], (String)e[1], (Integer)e[2], (String)e[3] ));});
    }

    public CategoriasJpaController getDbcntrl() {
        return dbcntrl;
    }

    public void setDbcntrl(CategoriasJpaController dbcntrl) {
        this.dbcntrl = dbcntrl;
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_CAT_ENUM.values().length;
    }

    public FilaCategoria getValueAt(int rowIndex){
        if (rowIndex>=0 && rowIndex<items.size()){
            return items.get(rowIndex);
        }
        return null;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaCategoria fila = items.get(rowIndex);
            switch (columnIndex){
                case 0: return fila.getCodigo();
                case 1: return fila.getNombre();
                case 2: {
                    Catcajas catcajas = mapCatCajas.get(fila.getCajaId());
                    if (catcajas != null){
                        System.out.println("catcajas is not null id es:"+ fila.getCajaId());
                    }
                    return catcajas !=null ? catcajas:fila.getCajaId();
                }
            }            
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        FilaCategoria fc = getValueAt(rowIndex);
        boolean update =false;
        if (fc != null){
            System.out.println("setValueAt--->");
            if (columnIndex == COLUMN_CAT_ENUM.NOMBRE.getCol().getIndex()){
                System.out.println("setValueAt--->Nombre"+aValue);
                if (aValue != null && aValue.toString().trim().length()>0){
                    fc.setNombre(aValue.toString().trim().toUpperCase());
                    update = true;
                }
            }
            else if (columnIndex == COLUMN_CAT_ENUM.CAJA.getCol().getIndex()){
                System.out.println("setValueAt--->Caja"+aValue);
                Catcajas caja = (Catcajas)aValue;
                fc.setCajaId(caja.getCcId());
                update = true;
            }

            try{
                if (columnIndex == COLUMN_CAT_ENUM.NOMBRE.getCol().getIndex()){
                    if (update){
                        dbcntrl.actualizar(fc.getCodigo(), aValue.toString().trim().toUpperCase());
                        loadFromDataBase();
                        fireTableDataChanged();
                        SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                    }                    
                }
                else if (columnIndex == COLUMN_CAT_ENUM.CAJA.getCol().getIndex()){
                    if (update){
                        Catcajas caja = (Catcajas)aValue;
                        dbcntrl.actualizar(fc.getCodigo(), caja.getCcId());
                        loadFromDataBase();
                        fireTableDataChanged();
                        SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                    }
                }
            }
            catch(Throwable ex){
                System.out.println("Error al guardar datos en el datamodel---->"+ex.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:"+ex.getMessage());
                showInfoError(ex);
            }
            
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
    
    public void showInfoError(Throwable ex){
          System.out.println("Error:"+ex.getMessage());
          ex.printStackTrace();
      }
    

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {        
        ColumnDM colDM= COLUMN_CAT_ENUM.get(columnIndex);
        return colDM!=null? colDM.isIsEdit():false;
    }   
    
     @Override
    public String getColumnName(int column) {
        if (column>=0 && column<COLUMN_CAT_ENUM.values().length){            
            String colName = COLUMN_CAT_ENUM.get(column).getDesc();
            return colName;
            
        }
        return super.getColumnName(column); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, Catcajas> getMapCatCajas() {
        return mapCatCajas;
    }

    public void setMapCatCajas(Map<Integer, Catcajas> mapCatCajas) {
        this.mapCatCajas = mapCatCajas;
    }
     
}