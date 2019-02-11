/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaCXCP;
import smf.util.TotalesCuentasXPC;

/**
 *
 * @author mjapon
 */
public class MovsCXCPDataModel extends CuentaXCPDataModel{
    
    public enum ColumMovsCXCPEnum{        
        FECHAABONO(0, "Fecha", String.class, "movFechareg"),
        NROFACT(1, "Nro Factura", String.class, "factNum"),
        MONTOFACT(2, "Monto Factura", BigDecimal.class, "factTotal"),
        ABONO(3, "Abono", BigDecimal.class, "movMonto"),
        SALDOPEND(4, "Saldo Pendiente", BigDecimal.class, "pgfSaldo");
        public final int index;
        public String desc;
        public Class klass;
        public String entityCol;
        
        private ColumMovsCXCPEnum(int index, String desc, Class cclass, String entityColumn){
            this.index = index; 
            this.desc = desc;
            this.klass = cclass;
            this.entityCol = entityColumn;
        }
        public static String GET_NAME(int index){            
            ColumMovsCXCPEnum[] values = ColumMovsCXCPEnum.values();            
            for (ColumMovsCXCPEnum col:values){
                if (col.index == index){
                    return col.desc;
                }
            }
            return "NODEF_INDEX"+index;
        }
        public static Class GET_CLASS(int index){
            ColumMovsCXCPEnum[] values = ColumMovsCXCPEnum.values();
            for (ColumMovsCXCPEnum col:values){
                if (col.index == index){
                    return col.klass;
                }
            }
            return null;
        }        
        
        public static String GET_ENTITY(int index){
            MovsCXCPDataModel.ColumMovsCXCPEnum[] values = MovsCXCPDataModel.ColumMovsCXCPEnum.values();
            for (MovsCXCPDataModel.ColumMovsCXCPEnum col:values){
                if (col.index == index){
                    return col.entityCol;
                }
            }
            return null;
        }
    };
    
    private Integer traCodigo;
    
    public MovsCXCPDataModel(Integer traCodigo){        
        this.traCodigo = traCodigo;        
        totales = new TotalesCuentasXPC();
        mapSort = new HashMap<>();
        mapSort.put(0, 1);
        mapSort.put(1, 0);
        mapSort.put(2, 0);
        mapSort.put(3, 0);
        mapSort.put(4, 0);
    }
    
    
     public void switchSortColumn(Integer column) throws Exception{    
        
        for (int i = 0; i<ColumMovsCXCPEnum.values().length;i++){            
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
    
    
     @Override
    public String getColumnName(int column) {
        if (column>=0 && column<ColumMovsCXCPEnum.values().length){            
            String colName = ColumMovsCXCPEnum.GET_NAME(column);
            return colName;
            
        }
        return super.getColumnName(column); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }   
    
    @Override
    public int getColumnCount() {
        return ColumMovsCXCPEnum.values().length;
    }
    
     @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaCXCP fila = items.get(rowIndex);            
            if (columnIndex==ColumMovsCXCPEnum.FECHAABONO.index){
                return fila.getFecha();
            }
            else if (columnIndex==ColumMovsCXCPEnum.NROFACT.index){
                return fila.getNumFactura();
            }
            else if (columnIndex==ColumMovsCXCPEnum.MONTOFACT.index){
                return fila.getMontoFactura();
            }
            else if (columnIndex==ColumMovsCXCPEnum.ABONO.index){
                return fila.getMonto();
            }
            else if (columnIndex==ColumMovsCXCPEnum.SALDOPEND.index){
                return fila.getDeuda();
            }           
            else{
                return "";
            }
        }
        else{
            return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class klass = ColumMovsCXCPEnum.GET_CLASS(columnIndex);
        if (klass != null){
            return klass;
        }
        return Object.class;
        
    }
    
     public Integer getSorIndex(){
        int index = 0;//Por defecto se ordena por nombre
        for (int i=0; i<ColumMovsCXCPEnum.values().length; i++){
            if (mapSort.containsKey(i)){
                if (mapSort.get(i)!= 0){
                    index = i;
                }
            }
        }        
        return index;
    }
    
    public String getSortColumn(Integer column){       
        String colName = ColumMovsCXCPEnum.GET_ENTITY(column);
        return colName;       
    }
    
    
    public void loadFromDataBase() throws Exception{
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);
        
        this.params.setSortColumn(sortcolumn);
        this.params.setSortOrder(sortord);
        
        List<Object[]> cuentasXCpList = controller.listarMovsAbonos(this.params);        
        items.clear();
        
        for(Object[] item: cuentasXCpList){
            Date fechaAbono = (Date)item[0];
            String numFactura = (String)item[1];
            BigDecimal montoFactura = (BigDecimal)item[2];
            BigDecimal montoAbono = (BigDecimal)item[3];
            BigDecimal saldoAbono = (BigDecimal)item[4];
            Integer factId = (Integer)item[5];        
            Integer movId = (Integer)item[6];
            
            FilaCXCP fila = new FilaCXCP(factId, numFactura, montoAbono, saldoAbono, 
                    "", "", FechasUtil.format(fechaAbono), 0, 
                    "", montoFactura, movId);
            
            items.add(fila);
        }
        
        totalizar();                
        fireTableDataChanged();
    }    
}
