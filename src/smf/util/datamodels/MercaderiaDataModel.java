/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import smf.controller.ArticulosJpaController;
import smf.controller.KardexArtCntrl;
import smf.gui.SmartFactMain;
import smf.util.ErrorValidException;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.NumbersUtil;
import smf.util.StringUtil;

/**
 *
 * @author manuel.japon
 */
public class MercaderiaDataModel extends AbstractTableModel{
    
    private String[] columNames={
        "Nro", //0 artId
        "Codbar",//1 artCodbar
        "Articulo",//2  artNombre  
        "Prec. Compra con Iva",//3   artPrecioCompra
        "Prec. Venta",//4  artPrecio
        "Precio Mínimo",//5  artPreciomin
        "IVA",//6   artIva
        "Inventario",//7    artInv
        "Categoría"//8    
    };
    
    private Map<Integer, Integer> mapSort;
    private List<FilaArticulo> items = new ArrayList<>();
    private ArticulosJpaController controller;
    private KardexArtCntrl kardexCntrl;
    private TotalesMercaderia totales;
    
    public MercaderiaDataModel(){
        mapSort = new HashMap<>();
        mapSort.put(0, 0);
        mapSort.put(1, 0);
        mapSort.put(2, 1);
        mapSort.put(3, 0);
        mapSort.put(4, 0);
        mapSort.put(5, 0);
        mapSort.put(6, 0);
        mapSort.put(7, 0);        
        totales = new TotalesMercaderia();
    }
    
    public void clearItems(){
        if (items != null){
            items.clear();
        }
    }
    
    public boolean validar(FilaArticulo filaArt){
        if (!StringUtil.isNotEmpty(filaArt.getCodBarra()) ){
            throw  new ErrorValidException("Debe ingresar el codigo de barra");
        }
        else if (!StringUtil.isNotEmpty(filaArt.getNombre())){
            throw  new ErrorValidException("Debe ingresar el nombre");
        }        
        else if (!StringUtil.isNotEmpty(filaArt.getPrecioCompra().toString())){
            throw  new ErrorValidException("Debe ingresar el precio de compra");
        }
        else if (!StringUtil.isNotEmpty(filaArt.getPrecioVenta().toString())){
            throw  new ErrorValidException("Debe ingresar el precio de venta");
        }        
        return true;
    }
    
    public void addItem(FilaArticulo filaArt){
        this.validar(filaArt);
        items.add(filaArt);
        fireTableDataChanged();
    }    

    @Override
    public String getColumnName(int column) { 
        if (column>=0 && column<columNames.length){
            String colName = columNames[column];
            
            if (mapSort.containsKey(column)){
                Integer sortValue = mapSort.get(column);
                switch(sortValue){
                    //case 0:{break;}
                    case 1:{colName = colName+" (+)"; break;}
                    case -1:{colName = colName+" (-)";break;}
                }
            }            
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
        return this.columNames.length;
    }
    
    public Integer getSorIndex(){
        int index = 2;//Por defecto se ordena por nombre
        for (int i=0; i<columNames.length; i++){
            if (mapSort.containsKey(i)){
                if (mapSort.get(i)!= 0){
                    index = i;
                }
            }
        }        
        return index;
    }
    
    public String getSortColumnForAdmin(Integer column){
        String columnName = "artNombre";       
        switch(column){
            case 0:{
                columnName = "art_id";
                break;
            }
            case 1:{
                columnName = "art_codbar";
                break;
            }
            case 2:{
                columnName = "art_nombre";
                break;
            }
            case 3:{
                columnName = "art_preciocompra";
                break;
            }
            case 4:{
                columnName = "art_precio";
                break;
            }
            case 5:{
                columnName = "art_preciomin";
                break;
            }
            case 6:{
                columnName = "art_iva";
                break;
            }
            case 7:{
                columnName = "art_inv";
                break;
            }
            case 8:{
                columnName = "cat_name";
                break;
            }
        }
        return columnName;
    }
    
    public String getSortColumn(Integer column){           
        return getSortColumnForAdmin(column);
    }
    
    public void switchSortColumn(Integer column) throws Exception{
        for (int i = 0; i<columNames.length;i++){
            if (i == column){
                Integer sortValue = mapSort.get(column);
                if (sortValue!=null){
                    if (sortValue == -1){
                        mapSort.put(column, 1);
                    }
                    else{
                        mapSort.put(column, -1);
                    }
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
    
    public FilaArticulo getNewFromRow(Object[] art){
         FilaArticulo filaArticulo = new FilaArticulo(
                    (Integer)art[0],
                    (String)art[2],
                    (String)art[1],
                    (BigDecimal)art[3],
                    (BigDecimal)art[4],
                    (BigDecimal)art[5],
                    (boolean)art[8],
                    (BigDecimal)art[6],
                    (String)art[10],
                    (String)art[11],
                    0,
                 (Integer)art[12]
            );
         return filaArticulo;
    }
    
    public void updateTotales(){
        BigDecimal sumaPrecioVenta = items.stream()
                .map(FilaArticulo::getPrecioVentaIvaMult)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal sumaPrecioCompra = items.stream()
                .map(FilaArticulo::getPrecioCompraMult)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal sumaPrecioVentaMin = items.stream()
                .map(FilaArticulo::getPrecioVentaMinIvaMult)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal sumaInv = items.stream()
                .map(FilaArticulo::getInventario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        totales.setSumaInv(NumbersUtil.round(sumaInv,2));
        totales.setSumaPrecioCompra(NumbersUtil.round(sumaPrecioCompra,2));
        totales.setSumaPrecioVenta(NumbersUtil.round(sumaPrecioVenta,2));
        totales.setSumaPrecioVentaMin(NumbersUtil.round(sumaPrecioVentaMin,2));
    }
    
    public void loadFromDataBase() throws Exception{
        String[] sortValues = getSortOrdColumn();
        List<Object[]> arts = controller.listarRaw(sortValues[0], sortValues[1]);
        items = arts.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        updateTotales();
        fireTableDataChanged();
    }
    
    public void loadFromDbCaducados()throws Exception{
        String[] sortValues = getSortOrdColumn();
        List<Object[]> arts = controller.listarRawCaducados(sortValues[0], sortValues[1]);
        items = arts.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        updateTotales();
        fireTableDataChanged();
    }
    
    public void loadFromDataBaseCat(Integer codCat)throws Exception{
        String[] sortValues = getSortOrdColumn();
        List<Object[]> arts = controller.listarRawCat(sortValues[0], sortValues[1], codCat);        
        items = arts.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        updateTotales();
        fireTableDataChanged();        
    }
    
    public void loadFromDataBaseProv(Integer provId)throws Exception{
        String[] sortValues = getSortOrdColumn();
        List<Object[]> arts = controller.listarRawProv(sortValues[0], sortValues[1], provId);
        items = arts.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        updateTotales();
        fireTableDataChanged();
    }
    
    public void loadFromDataBaseFilter(String filtro)throws Exception{
        String[] sortValues = getSortOrdColumn();
        List<Object[]> arts = controller.listarRaw(sortValues[0], sortValues[1], filtro);
        items = arts.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        updateTotales();
        fireTableDataChanged();        
    }
     
     public void saveAllRecords(){
        try{//actualizar en la base de datos
            for(FilaArticulo filafactura: items){
                if (filafactura.getArtId()>0){
                    controller.actualizarArticulo(filafactura);
                }                
            }
            JOptionPane.showMessageDialog(null,"Guardado");
            loadFromDataBase();
        }
        catch(Throwable ex){
            showInfoError(ex);
            JOptionPane.showMessageDialog(null, "ERRO:"+ex.getMessage());
        }
    }
     
     public void showInfoError(Throwable ex){
          System.out.println("Error:"+ex.getMessage());
          ex.printStackTrace();
      }
      
      @Override
      public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filafactura = items.get(rowIndex);
            boolean updated = false;
            
            switch (columnIndex){
                case 1: {
                    updated = !aValue.toString().equalsIgnoreCase(filafactura.getCodBarra());
                    filafactura.setCodBarra(aValue.toString());
                    break;
                }
                case 2: {
                    updated = !aValue.toString().equalsIgnoreCase(filafactura.getNombre());
                    filafactura.setNombre(aValue.toString());
                    break;
                }
                case 3: {
                    updated = !aValue.toString().equalsIgnoreCase(filafactura.getPrecioCompra().toPlainString());
                    BigDecimal precioCompra = new BigDecimal(aValue.toString());
                    BigDecimal precioCompraSinIva = filafactura.getPrecioSinIva(precioCompra);
                    filafactura.setPrecioCompra( precioCompraSinIva );
                    break;
                }
                case 4:{
                    BigDecimal precioVenta = new BigDecimal(aValue.toString());
                    BigDecimal precioVentaSinIva = filafactura.getPrecioSinIva(precioVenta);
                    updated = precioVentaSinIva.toPlainString().equalsIgnoreCase(filafactura.getPrecioVenta().toPlainString());
                    filafactura.setPrecioVenta( precioVentaSinIva );
                    break;
                }
                case 5: {
                    BigDecimal precioMin = new BigDecimal(aValue.toString());
                    BigDecimal precioMinSinIva = filafactura.getPrecioSinIva(precioMin);
                    updated = !precioMinSinIva.toPlainString().equalsIgnoreCase(filafactura.getPrecioMin().toPlainString());
                    filafactura.setPrecioMin( precioMinSinIva );
                    break;
                }
                case 6: {       
                    /*
                    boolean isIva = (Boolean)aValue;
                    updated = filafactura.isIva()!=isIva;
                    filafactura.setIva(isIva);
                    */
                    break;
                }
                    
                case 7: {                    
                    updated = !aValue.toString().equalsIgnoreCase(filafactura.getInventario().toPlainString());
                    filafactura.setInventario(new BigDecimal(aValue.toString()));
                    break;
                }
                /*
                case 8: {
                    filafactura.setCategoria(aValue.toString());
                    break;
                }
                */
            }
            try{
                if (filafactura.getArtId()>0){
                    controller.actualizarArticulo(filafactura);
                    updateTotales();
                    fireTableDataChanged();
                    if (updated){
                        SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                    }
                }
            }
            catch(Throwable ex){
                showInfoError(ex);
                JOptionPane.showMessageDialog(null, "ERRO:"+ex.getMessage());
            }
            
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    
     public FilaArticulo getValueAt(int rowIndex) {
         if (rowIndex>=0 && rowIndex<items.size()){
            return items.get(rowIndex);              
         }
         return null;
     }
      
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filaArticulo = items.get(rowIndex);
            switch (columnIndex){
                case 0: return filaArticulo.getArtId();
                case 1: return filaArticulo.getCodBarra();
                case 2: return filaArticulo.getNombre();
                case 3: {
                    BigDecimal precioCompra = filaArticulo.getPrecioCompraConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioCompra;
                }
                case 4:{
                    BigDecimal precioVentaIva = filaArticulo.getPrecioVentaConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioVentaIva;
                }
                case 5: {
                    BigDecimal precioMinIva = filaArticulo.getPrecioMinConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioMinIva;                    
                }
                case 6: /*return filaArticulo.isIva();*/ return filaArticulo.isIva()?"SI":"NO";                
                case 7: return NumbersUtil.round2(filaArticulo.getInventario());
                case 8: return filaArticulo.getCategoria();
                default: return "";
            }
        }
        else{
            return "";
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 1: return false;
            case 2: return true;
            case 3: return true;
            case 4: return true;
            case 5: return true;
            case 6: return true;
            case 7: {//Para el caso de inventario solo bienes pueden ser editados
                FilaArticulo row = getFila(rowIndex);
                if (row.getTipo()!=null && row.getTipo().equalsIgnoreCase("B") ){
                    return true;
                }
                return false;
            }
            case 8: return false;
            default: return false;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
         switch(columnIndex){
            case 0: return Integer.class;//cantidad
            case 1: return String.class;//iva
            case 2: return String.class;//precioUnitario            
            case 3: return BigDecimal.class;
            case 4: return BigDecimal.class;
            case 5: return BigDecimal.class;//precioUnitario            
            //case 6: return Boolean.class;
            case 6: return String.class;
            case 7: return BigDecimal.class;//precioUnitario                        
            case 8: return String.class;//precioUnitario
            default: return Object.class;
        }
    }
    
    public FilaArticulo getFila(int rowIndex){
        return items.get(rowIndex);
    }
    
     public List<FilaArticulo> getItems() {
        return items;
    }

    public void setItems(List<FilaArticulo> items) {
        this.items = items;
    }

    public ArticulosJpaController getController() {
        return controller;
    }

    public void setController(ArticulosJpaController controller) {
        this.controller = controller;
    }

    public TotalesMercaderia getTotales() {
        return totales;
    }

    public void setTotales(TotalesMercaderia totales) {
        this.totales = totales;
    }   

    public KardexArtCntrl getKardexCntrl() {
        return kardexCntrl;
    }

    public void setKardexCntrl(KardexArtCntrl kardexCntrl) {
        this.kardexCntrl = kardexCntrl;
    }
    
    
}