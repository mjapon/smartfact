/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import smf.util.SelectingEditor;

/**
 *
 * @author manuel.japon
 */
public abstract class GenericDataModel<RowType> extends AbstractTableModel{
    protected List<JTableColumn> columnsList;
    protected Map<Integer, Integer> mapSort;
    protected List<RowType> items;    
    
    public GenericDataModel(List<JTableColumn> columns){
        columnsList = columns;
        mapSort = new HashMap<>();
        initMapSort();
        items = new ArrayList<RowType>();
    }
    
    public abstract void loadFromDataBase();
    public void updateTotales(){
        
    }
    public void persistOnDb(RowType row){
        
    }
    public boolean validar(RowType filaArt){
        return true;
    };
    
    public RowType getNewFromRow(Object[] dbRow){
        return null;
    };
    
    public void initMapSort(){
        //Se establece sin ordenacion por defecto
        int size = columnsList.size();
        for (int i=0;i<size;i++){
            mapSort.put(i, 0);
        }        
        mapSort.put(0, 1);
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnsList.size();
    }

    @Override
    public String getColumnName(int column) {
        JTableColumn jcolumn = columnsList.get(column);
        if (jcolumn!= null){
            return jcolumn.getName();
        }
        return "";
    }
    
    public <T> T getValueAt(int rowIndex) {
         if (rowIndex>=0 && rowIndex<items.size()){
            return (T)items.get(rowIndex);
         }
         return null;
     }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            Object row = items.get(rowIndex);
            JTableColumn jcolumn = columnsList.get(columnIndex);
            return jcolumn.getValueAt(row, rowIndex);
        }
        else{
            return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            Object row = items.get(rowIndex);
            JTableColumn jcolumn = columnsList.get(columnIndex);
            jcolumn.setValueAt(row, rowIndex, aValue);
            persistOnDb((RowType)row);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            JTableColumn jcolumn = columnsList.get(columnIndex);
            if (jcolumn != null){
                Object row = items.get(rowIndex);
                return jcolumn.isCellEditable(row);
            }
        }
        return false;
    }
    
    public Integer getSorIndex(){
        int index = 2;//Por defecto se ordena por nombre
        for (int i=0; i<columnsList.size(); i++){
            if (mapSort.containsKey(i)){
                if (mapSort.get(i)!= 0){
                    index = i;
                }
            }
        }
        return index;
    }
    
    public String getSortColumn(Integer columnIndex){
        JTableColumn jcolumn = columnsList.get(columnIndex);
        if (jcolumn != null){
            return jcolumn.getDbName();
        }
        return "";
    }
    
    public void switchSortColumn(Integer column) throws Exception{
        for (int i = 0; i<columnsList.size();i++){
            if (i == column){
                Integer sortValue = mapSort.get(column);
                if (sortValue == -1){
                    mapSort.put(column, 1);
                }
                else{
                    mapSort.put(column, -1);
                }
            }
            else{
                mapSort.put(i, 0);
            }
        }
        this.loadFromDataBase();
    }
    
    public String[] getSortOrdColumn(){
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);
        return new String[]{sortcolumn,sortord};
    }
    
    public void addRow(RowType newRow){
        validar(newRow);
        items.add(newRow);
        fireTableDataChanged();
    }    

    public List<RowType> getItems() {
        return items;
    }
    
    public void logError(Throwable ex){
        System.out.println("Error en jpaController:"+ this.getClass().getName()+":"+ex.getMessage());
        ex.printStackTrace();
    }
    
    public void logErrorWithThrow(Throwable ex) throws Throwable{
        logError(ex);
        throw  ex;
    }
    
    public void showMsgError(Throwable ex){
        JOptionPane.showMessageDialog(null, "Algo salio mal :-( "+ex.getMessage());
        System.out.println("Excepcion:"+ex.getMessage());
        ex.printStackTrace();
    }
    
    /**
     * 
     * @param widthsPerc : float[] {60.0f, 20.0f, 20.0f}
     * @param jtable 
     */
    public void setupWidths(float[] widthsPerc, JTable jtable){
        int tW = jtable.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = jtable.getColumnModel();
        int ncols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < ncols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(widthsPerc[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }
    
    public void addSelectingEditor(JTable jtable, Integer columnIndex){
        TableColumn column = jtable.getColumnModel().getColumn( columnIndex );
        SelectingEditor selectingEditor = new SelectingEditor(new JTextField());
        column.setCellEditor(selectingEditor);
    }
}
