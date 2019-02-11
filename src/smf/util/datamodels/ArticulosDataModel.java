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
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import smf.controller.ArticulosJpaController;
import smf.gui.ILabelEstado;
import smf.util.ErrorValidException;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.StringUtil;

/**
 *
 * @author manuel.japon
 */
public class ArticulosDataModel extends AbstractTableModel{
    
    private String[] columNames = {};
    
    private String[] columNamesForAdmin={
        "Nro", //0 art_id
        "Cod Barra",//1 art_codbar
        "Artículo",//2  art_nombre
        "Prec. Compra sin Iva",//3   art_preciocompra
        "Prec. Venta",//4  art_precio
        "Precio Mínimo",//5  art_preciomin
        "Iva",//6   art_iva
        "Inventario"//7    art_inv
    };
    
    private String[] columNamesForSelect={
        "Codbar",//0 art_codbar
        "Artículo",//1  art_nombre
        "Prec. Compra con Iva",//2   art_preciocompra
        "Prec. Venta",//3  art_precio
        "Precio Mínimo",//4  art_preciomin
        "Inventario"//5    art_inv
    };
    
    private String[] columNamesForSearchVenta={
        "Artículo",//0  art_nombre        
        "Prec. Venta",//1  art_precio
        "Inventario"//2   art_inv
    };
    
    private String[] columNamesForSearchCompra={        
        "Articulo",//0  art_nombre        
        "Prec. Compra",//1  art_preciocompra
        "Inventario"//2   art_inv
    };
    
    private Map<Integer, Integer> mapSort;          
    private List<FilaArticulo> items = new ArrayList<>();
    private ArticulosJpaController controller;
    private ILabelEstado articulosFrame;
    private int type;
    
    public void initForAdmin(){
        columNames = columNamesForAdmin;
        mapSort = new HashMap<>();
        mapSort.put(0, 0);
        mapSort.put(1, 0);
        mapSort.put(2, 1);
        mapSort.put(3, 0);
        mapSort.put(4, 0);
        mapSort.put(5, 0);
        mapSort.put(6, 0);
        mapSort.put(7, 0);
    }
    
    public void initForSelect(){
        columNames = columNamesForSelect;
        mapSort = new HashMap<>();
        mapSort.put(0, 0);
        mapSort.put(1, 1);
        mapSort.put(2, 0);
        mapSort.put(3, 0);
        mapSort.put(4, 0);
        mapSort.put(5, 0);
    }
    
    public void initForSeachVenta(){
        columNames = columNamesForSearchVenta;
        mapSort = new HashMap<>();
        mapSort.put(0, 0);
        mapSort.put(1, 1);
        mapSort.put(2, 0);
    }    
    
    public void initForSeachCompra(){
        columNames = columNamesForSearchCompra;
        mapSort = new HashMap<>();
        mapSort.put(0, 0);
        mapSort.put(1, 1);
        mapSort.put(2, 0);
    }    
    
    /**
     * @param type : 0-->select 1-->admin  2-->search
     */
    public ArticulosDataModel(int type){
        super();
        this.type = type;        
        switch(type){
            case 0:{//for select
                initForSelect();
                break;
            }
            case 1:{//for admin
                initForAdmin();
                break;
            }
            case 2:{//for seach
                initForSeachVenta();
                break;
            }
            case 3:{//for search compra
                initForSeachCompra();
                break;
            }
        }        
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
        String columnName = "art_nombre";       
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
        }
        return columnName;
    }
    
    public String getSortColumnForSelect(Integer column){
        String columnName = "art_nombre";
        switch(column){
            case 0:{
                columnName = "art_codbar";
                break;
            }
            case 1:{
                columnName = "art_nombre";
                break;
            }
            case 2:{
                columnName = "art_preciocompra";
                break;
            }
            case 3:{
                columnName = "art_precio";
                break;
            }
            case 4:{
                columnName = "art_preciomin";
                break;
            }
            case 5:{
                columnName = "art_inv";
                break;
            }
        }
        return columnName;
    }
    
    public String getSortColumnForSearchVenta(Integer column){
        String columnName = "art_nombre";       
        switch(column){
            case 0:{
                columnName = "art_nombre";
                break;
            }
            case 1:{
                columnName = "art_precio";
                break;
            }
            case 2:{
                columnName = "art_inv";
                break;
            }
        }
        return columnName;
    }
    public String getSortColumnForSearchCompra(Integer column){
        String columnName = "art_nombre";
        switch(column){
            case 0:{
                columnName = "art_nombre";
                break;
            }
            case 1:{
                columnName = "art_preciocompra";
                break;
            }
            case 2:{
                columnName = "art_inv";
                break;
            }
        }
        return columnName;
    }
    
    
    public String getSortColumn(Integer column){           
        switch(type){
            case 0:{return getSortColumnForSelect(column);}
            case 1:{return getSortColumnForAdmin(column);}
            case 2:{return getSortColumnForSearchVenta(column);}
            case 3:{return getSortColumnForSearchCompra(column);}
        }
        return "";  
    }
    
    public void switchSortColumn(Integer column) throws Exception{    
        
        for (int i = 0; i<columNames.length;i++){
            
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
    
    public void loadFromDataBase() throws Exception{
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);        
        
        List<Object[]> arts = controller.listarRaw(sortcolumn, sortord);
        
        items.clear();
        arts.stream().map((art) -> new FilaArticulo(
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
        )).forEachOrdered((filaArticulo) -> {
            items.add(filaArticulo);
        });        
        
        if (this.articulosFrame != null){
            this.articulosFrame.disableBtnBorar();
        }        
        fireTableDataChanged();
    }
    
    public void loadFromDataBaseCat(Integer codCat)throws Exception{
        
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);
        
        List<Object[]> arts = controller.listarRaw(sortcolumn, sortord);
        
        items.clear();
        for(Object[] art: arts){            
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
            
            items.add(filaArticulo);
        }
        
        if (this.articulosFrame != null){
            this.articulosFrame.disableBtnBorar();
        }        
        fireTableDataChanged();
        
    }
    
    public void loadFromDataBaseFilter(String filtro)throws Exception{
        int sortIndex = getSorIndex();
        int sortorder = mapSort.get(sortIndex);
        
        String sortord = sortorder==-1?"desc":"asc";
        String sortcolumn = getSortColumn(sortIndex);
        
        List<Object[]> arts = controller.listarRaw(sortcolumn, sortord, filtro);        
        items.clear();
        
        for(Object[] art: arts){            
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
            //filaArticulo.setCatCajaId((Integer)art[12]);
            
            items.add(filaArticulo);
        }    
        if (this.articulosFrame != null){
            this.articulosFrame.disableBtnBorar();
        }
        
        fireTableDataChanged();        
    }    
    
    public void saveAllRecords(){
        try{                //actualizar en la base de datos
            for(FilaArticulo filafactura: items){
                if (filafactura.getArtId()>0){
                    controller.actualizarArticulo(filafactura);
                }
                if (this.articulosFrame != null){
                    articulosFrame.updateLabelEstado("Operación Exitosa-->");
                }
            }
            
            JOptionPane.showMessageDialog(null,"Guardado");
            if (this.articulosFrame != null){
                articulosFrame.updateLabelEstado("Guardado");
            }
            loadFromDataBase();

        }
        catch(Throwable ex){            
            if (this.articulosFrame != null){
                articulosFrame.updateLabelEstado("ERRO:"+ex.getMessage());
            }
            
            JOptionPane.showMessageDialog(null, "ERRO:"+ex.getMessage());
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filafactura = items.get(rowIndex);
            switch (columnIndex){                
                case 1: {
                    filafactura.setCodBarra(aValue.toString());
                    break;
                }
                case 2: {
                    filafactura.setNombre(aValue.toString());
                    break;
                }
                case 3: {
                    BigDecimal precioCompra = new BigDecimal(aValue.toString());
                    filafactura.setPrecioCompra( precioCompra );
                    
                    break;
                }
                case 4:{
                    BigDecimal precioVenta = new BigDecimal(aValue.toString());
                    BigDecimal precioVentaSinIva = filafactura.getPrecioSinIva(precioVenta);                    
                    filafactura.setPrecioVenta( precioVentaSinIva );
                    break;
                }
                case 5: {
                    BigDecimal precioMin = new BigDecimal(aValue.toString());
                    BigDecimal precioMinSinIva = filafactura.getPrecioSinIva(precioMin);
                    filafactura.setPrecioMin( precioMinSinIva );
                    break;
                }
                case 6: {
                    boolean isIva = "SI".equalsIgnoreCase(aValue.toString());
                    filafactura.setIva(isIva);
                    break;
                }
                    
                case 7: {
                    filafactura.setInventario(new BigDecimal(aValue.toString()));
                    break;
                }
            }      
            
            try{
                if (filafactura.getArtId()>0){
                    controller.actualizarArticulo(filafactura);
                }
                if (this.articulosFrame != null){
                    articulosFrame.updateLabelEstado("Operación Exitosa-->");
                }
                
            }
            catch(Throwable ex){
                if (this.articulosFrame != null){
                    articulosFrame.updateLabelEstado("ERRO:"+ex.getMessage());
                }
                
                JOptionPane.showMessageDialog(null, "ERRO:"+ex.getMessage());
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    
    public FilaArticulo getValueAt(int rowIndex){
        if (rowIndex>=0 && rowIndex<items.size()){
            return items.get(rowIndex);            
        }
        return null;
    }

    public Object getValueAtForAdmin(int rowIndex, int columnIndex){
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filaArticulo = items.get(rowIndex);            
            switch (columnIndex){
                case 0: return filaArticulo.getArtId();
                case 1: return filaArticulo.getCodBarra();
                case 2: return filaArticulo.getNombre();
                case 3: {
                    BigDecimal precioCompra = filaArticulo.getPrecioCompra().setScale(4, RoundingMode.HALF_UP);
                    return precioCompra;
                }
                case 4:{                    
                    //BigDecimal precioVenta = filaArticulo.getPrecioVenta().setScale(4, RoundingMode.HALF_UP);
                    BigDecimal precioVentaIva = filaArticulo.getPrecioVentaConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioVentaIva;
                }
                case 5: {                    
                    //BigDecimal precioMin = filaArticulo.getPrecioMin().setScale(4, RoundingMode.HALF_UP);
                    BigDecimal precioMinIva = filaArticulo.getPrecioMinConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioMinIva;                    
                }
                case 6: return filaArticulo.isIva()?"SI":"NO";                
                case 7: return filaArticulo.getInventario();
                default: return "";
            }
        }
        else{
            return "";
        }        
    }
    
    public Object getValueAtForSelect(int rowIndex, int columnIndex){
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filaArticulo = items.get(rowIndex);            
            switch (columnIndex){
                case 0: return filaArticulo.getCodBarra();
                case 1: return filaArticulo.getNombre();
                case 2: {
                    BigDecimal precioCompra = filaArticulo.getPrecioCompra().setScale(4, RoundingMode.HALF_UP);
                    return precioCompra;
                }
                case 3:{                    
                    if (this.type == 3){//Compra
                        BigDecimal precioCompra = filaArticulo.getPrecioCompra().setScale(4, RoundingMode.HALF_UP);
                        return precioCompra;
                    }
                    else{
                        BigDecimal precioVentaIva = filaArticulo.getPrecioVentaConIva().setScale(4, RoundingMode.HALF_UP);
                        return precioVentaIva;
                    }
                    //BigDecimal precioVenta = filaArticulo.getPrecioVenta().setScale(4, RoundingMode.HALF_UP);                    
                }
                case 4: {
                    //BigDecimal precioMin = filaArticulo.getPrecioMin().setScale(4, RoundingMode.HALF_UP);
                    BigDecimal precioMinIva = filaArticulo.getPrecioMinConIva().setScale(4, RoundingMode.HALF_UP);
                    return precioMinIva;                    
                }
                case 5: return filaArticulo.getInventario();
                default: return "";
            }
        }
        else{
            return "";
        }        
    }
    
    public Object getValueAtForSearch(int rowIndex, int columnIndex){
         if (rowIndex>=0 && rowIndex<items.size()){
            FilaArticulo filaArticulo = items.get(rowIndex);            
            switch (columnIndex){                
                case 0: return filaArticulo.getNombre();                
                case 1:{                    
                    if (this.type == 3){//Compra
                        BigDecimal precioCompra = filaArticulo.getPrecioCompra().setScale(4, RoundingMode.HALF_UP);
                        return precioCompra;
                    }
                    else{
                        BigDecimal precioVentaIva = filaArticulo.getPrecioVentaConIva().setScale(4, RoundingMode.HALF_UP);
                        return precioVentaIva;
                    }
                }
                case 2: return filaArticulo.getInventario();
                default: return "";
            }
        }
        else{
            return "";
        }        
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        
        switch(type){
            case 0:{
                return getValueAtForSelect(rowIndex, columnIndex);
            }
            case 1:{
                return getValueAtForAdmin(rowIndex, columnIndex);
            }
            case 2:{
                return getValueAtForSearch(rowIndex, columnIndex);
            }
            case 3:{
                return getValueAtForSearch(rowIndex, columnIndex);
            }
        }
        return getValueAtForAdmin(rowIndex, columnIndex);        
    }
    
    public boolean isCellEditableForAdmin(int rowIndex, int columnIndex) {        
        switch(columnIndex){
            case 1: return false;
            case 2: return true;
            case 3: return true;
            case 4: return true;
            case 5: return true;
            case 6: return true;
            case 7: return true;
            default: return false;
        }
    }
    
    public boolean isCellEditableForSelect(int rowIndex, int columnIndex) {               
        return false;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { 
        
        switch(type){
            case 0:{
                return false;
            }
            case 1:{
                return isCellEditableForAdmin(rowIndex, columnIndex);
            }
            case 2:{
                //return isCellEditableForAdmin(rowIndex, columnIndex);
                return false;
            }
            case 3:{
                return false;
            }
        }
        
        return false;
        
    }
    
    public Class<?> getColumnClassForAdmin(int columnIndex) {        
        switch(columnIndex){
            case 0: return Integer.class;//cantidad
            case 1: return String.class;//iva
            case 2: return String.class;//precioUnitario            
            case 3: return BigDecimal.class;
            case 4: return BigDecimal.class;
            case 5: return BigDecimal.class;//precioUnitario            
            case 6: return String.class;
            case 7: return BigDecimal.class;//precioUnitario                        
            default: return Object.class;
        }
    }
    
    public Class<?> getColumnClassForSelect(int columnIndex) {
        switch(columnIndex){
            case 0: return String.class;//cantidad
            case 1: return String.class;//iva
            case 2: return BigDecimal.class;//precioUnitario            
            case 3: return BigDecimal.class;
            case 4: return BigDecimal.class;
            case 5: return BigDecimal.class;//precioUnitario                                            
            default: return Object.class;
        }
    }
    
    public Class<?> getColumnClassForSearch(int columnIndex){
         switch(columnIndex){
            case 0: return String.class;//articulo
            case 1: return BigDecimal.class;//precioventa
            case 2: return BigDecimal.class;//inventario
            default: return Object.class;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(type){
            case 0:{
                return getColumnClassForSelect(columnIndex);
            }
            case 1:{
                return getColumnClassForAdmin(columnIndex);
            }
            case 2:{
                return getColumnClassForSearch(columnIndex);
            }
            case 3:{
                return getColumnClassForSearch(columnIndex);
            }
        }
        
        return getColumnClassForAdmin(columnIndex);
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

    public ILabelEstado getArticulosFrame() {
        return articulosFrame;
    }

    public void setArticulosFrame(ILabelEstado articulosFrame) {
        this.articulosFrame = articulosFrame;
    }
}