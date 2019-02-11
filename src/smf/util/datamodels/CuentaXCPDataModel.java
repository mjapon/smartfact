/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import smf.controller.FacturasJpaController;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaCXCP;
import smf.util.ParamBusquedaCXCP;
import smf.util.TotalesCuentasXPC;

/**
 *
 * @author mjapon
 */
public class CuentaXCPDataModel extends AbstractTableModel{
    
    protected List<FilaCXCP> items = new ArrayList<>();
    protected TotalesCuentasXPC totales;
    protected JTable jtable;
    
    protected Map<Integer, Integer> mapSort;     
    protected FacturasJpaController controller;    
    protected ParamBusquedaCXCP params;
    
    //pgfFecreg
    
    public enum ColumnaCXCPEnum{        
        FECHAFACT(0, "Fecha Factura", String.class, "pgfFecreg"),
        NROFACT(1, "Nro Factura", String.class, "factNum"),
        MONTO(2, "Monto", BigDecimal.class, "pgfMonto"),
        ESTADO(3, "Estado", String.class, "estadoDesc"),
        VALORPEND(4, "Valor Pendiente", BigDecimal.class, "pgfSaldo"),
        REFERENTE(5, "Referente", String.class, "cliNombres"),
        OBSERVACION(6, "Observación", String.class, "pgfObs");
        
        public final int index;
        public String desc;
        public Class klass;
        public String entityCol;
        
        private ColumnaCXCPEnum(int index, String desc, Class cclass, String entityColumn){
            this.index = index; 
            this.desc = desc;
            this.klass = cclass;
            this.entityCol = entityColumn;
        }
        public static String GET_NAME(int index){            
            ColumnaCXCPEnum[] values = ColumnaCXCPEnum.values();            
            for (ColumnaCXCPEnum col:values){
                if (col.index == index){
                    return col.desc;
                }
            }
            return "NODEF_INDEX"+index;
        }
        public static Class GET_CLASS(int index){
            ColumnaCXCPEnum[] values = ColumnaCXCPEnum.values();
            for (ColumnaCXCPEnum col:values){
                if (col.index == index){
                    return col.klass;
                }
            }
            return null;
        }        
        
        public static String GET_ENTITY(int index){
            CuentaXCPDataModel.ColumnaCXCPEnum[] values = CuentaXCPDataModel.ColumnaCXCPEnum.values();
            for (CuentaXCPDataModel.ColumnaCXCPEnum col:values){
                if (col.index == index){
                    return col.entityCol;
                }
            }
            return null;            
        }
    };
    
    public CuentaXCPDataModel(){
        super();
        totales = new TotalesCuentasXPC();
        mapSort = new HashMap<>();
        mapSort.put(0, 1);
        mapSort.put(1, 0);
        mapSort.put(2, 0);
        mapSort.put(3, 0);
        mapSort.put(4, 0);        
        mapSort.put(5, 0);        
    }
    
    public void removeItem(int rowIndex){
        
    }
    
    public void addItem(FilaCXCP item){        
        items.add(item);
        totalizar();
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        if (column>=0 && column<ColumnaCXCPEnum.values().length){            
            String colName = ColumnaCXCPEnum.GET_NAME(column);
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
        return ColumnaCXCPEnum.values().length;
    }

     @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {   
        
       return false;
        
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
        
    public FilaCXCP getValueAt(int rowIndex) {        
        FilaCXCP fila = items.get(rowIndex);
        return fila;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaCXCP fila = items.get(rowIndex);
            /*
            if (columnIndex==ColumnaFacturaEnum.NRO.index){
                return filafactura.getNumFila();
            }
            else 
            */
            if (columnIndex==ColumnaCXCPEnum.FECHAFACT.index){
                return fila.getFecha();
            }
            else if (columnIndex==ColumnaCXCPEnum.NROFACT.index){
                return fila.getNumFactura();
            }
            else if (columnIndex==ColumnaCXCPEnum.MONTO.index){
                return fila.getMonto();
            }
            else if (columnIndex==ColumnaCXCPEnum.VALORPEND.index){
                return fila.getDeuda();
            }
            else if (columnIndex==ColumnaCXCPEnum.REFERENTE.index){
                return fila.getReferente();
            }
            else if (columnIndex==ColumnaCXCPEnum.OBSERVACION.index){
                return fila.getObservacion();
            }
            else if (columnIndex==ColumnaCXCPEnum.ESTADO.index){
                return fila.getEstadoDesc();
            }
            else{
                return "";
            }
        }
        else{
            return "";
        }
    }
    
    public void loadItems(List<Object[]> items){        
        for (Object[] item: items){            
            Integer facturaId = (Integer)item[0];
            String numFactura = (String)item[1];
            Date fechafactura = (Date)item[2];
            BigDecimal monto = (BigDecimal)item[3];
            BigDecimal deuda = (BigDecimal)item[8];
            String referente = (String)item[4];
            String obs = (String)item[9];   
            Integer codPago = (Integer)item[0]; 
            String estadoDesc = (String)item[11];
            FilaCXCP fila = new FilaCXCP(facturaId, numFactura, monto, deuda, referente, 
                    obs, FechasUtil.format(fechafactura), codPago, estadoDesc, BigDecimal.ZERO, 0);
            addItem(fila);
        }
        
        fireTableDataChanged();
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class klass = ColumnaCXCPEnum.GET_CLASS(columnIndex);
        if (klass != null){
            return klass;
        }
        return Object.class;
        
    }    
    
    public void switchSortColumn(Integer column) throws Exception{    
        
        for (int i = 0; i<CuentaXCPDataModel.ColumnaCXCPEnum.values().length;i++){
            
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
    
    public Integer getSorIndex(){
        int index = 0;//Por defecto se ordena por nombre
        for (int i=0; i<CuentaXCPDataModel.ColumnaCXCPEnum.values().length; i++){
            if (mapSort.containsKey(i)){
                if (mapSort.get(i)!= 0){
                    index = i;
                }
            }
        }        
        return index;
    }
    
    public String getSortColumn(Integer column){       
        String colName = CuentaXCPDataModel.ColumnaCXCPEnum.GET_ENTITY(column);
        return colName;       
    }
    
    public void loadFromDataBase() throws Exception{
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);
        
        this.params.setSortColumn(sortcolumn);
        this.params.setSortOrder(sortord);
        
        List<Object[]> cuentasXCpList = controller.listarCuentasXCP(this.params);        
        items.clear();
        
        for(Object[] item: cuentasXCpList){            
            Integer facturaId = (Integer)item[0];
            String numFactura = (String)item[1];
            Date fechafactura = (Date)item[2];
            BigDecimal monto = (BigDecimal)item[3];
            BigDecimal deuda = (BigDecimal)item[8];
            String referente = (String)item[4];
            String obs = (String)item[9];      
            Integer codPago = (Integer)item[10];
            
            String estadoDesc = (String)item[11];
            
            FilaCXCP fila = new FilaCXCP(facturaId, numFactura, monto, deuda, 
                    referente, obs, FechasUtil.format(fechafactura), codPago, 
                    estadoDesc, BigDecimal.ZERO, 0);
            
            items.add(fila);
        }
        
        totalizar();
        fireTableDataChanged();
    }    
    
    public void totalizar(){
        BigDecimal sumaAbonos = BigDecimal.ZERO;
        List<Integer> movsIdList = new ArrayList<>();
        for (FilaCXCP fila: this.items){
            sumaAbonos = sumaAbonos.add( fila.getMonto() );
            movsIdList.add(fila.getMovId());
        }
        
        if (this.getTotalesFactura() == null){
            this.setTotalesFactura(new TotalesCuentasXPC());
        }
        
        TotalesCuentasXPC totales = this.getTotalesFactura();
        
        totales.setSumaMonto(sumaAbonos);
        
        Map<Integer, BigDecimal> totalesByCajaMap = new HashMap<>();
        totales.setTotalByCajaMap(totalesByCajaMap);
        
        if (movsIdList.size()>0){
            totales.setTotalByCajaMap( controller.getMovsAbonosMap(movsIdList) );
        }        
        
    }

    public List<FilaCXCP> getItems() {
        return items;
    }

    public void setItems(List<FilaCXCP> items) {
        this.items = items;
    }

    public TotalesCuentasXPC getTotalesFactura() {
        return totales;
    }

    public void setTotalesFactura(TotalesCuentasXPC totales) {
        this.totales = totales;
    }
    
    public JTable getJtable() {
        return jtable;
    }

    public void setJtable(JTable jtable) {
        this.jtable = jtable;
    }

    public ParamBusquedaCXCP getParams() {
        return params;
    }

    public void setParams(ParamBusquedaCXCP params) {
        this.params = params;
    }

    public FacturasJpaController getController() {
        return controller;
    }

    public void setController(FacturasJpaController controller) {
        this.controller = controller;
    }
    
}
