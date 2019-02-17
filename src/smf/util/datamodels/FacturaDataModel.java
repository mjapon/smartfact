/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import smf.entity.Articulos;
import smf.gui.SmartFactMain;
import smf.gui.facte.IFacturaEdit;
import smf.util.CtesU;
import smf.util.datamodels.rows.FilaFactura;
import smf.util.TotalesFactura;
/**
 *
 * @author mjapon
 */
public class FacturaDataModel extends AbstractTableModel{
    
    public enum ColumnaFacturaEnum{
        CODBAR(0, "Codbar", String.class),
        ARTICULO(1, "Artículo", String.class),
        CANTIDAD(2, "Cant", Double.class),
        PRECIOU(3, "Prec U.", BigDecimal.class),
        PDESC(4, "Desc%", BigDecimal.class),
        VDESC(5, "Desc", BigDecimal.class),
        SUBTOTAL(6, "Subtotal", BigDecimal.class),
        IVA(7, "Iva",String.class),
        TOTAL(8, "Total", BigDecimal.class);
        
        public final int index;
        public String desc;
        public Class klass;
        private ColumnaFacturaEnum(int index, String desc, Class cclass){
            this.index = index; 
            this.desc = desc;
            this.klass = cclass;
        }
        public static String GET_NAME(int index){            
            ColumnaFacturaEnum[] values = ColumnaFacturaEnum.values();            
            for (ColumnaFacturaEnum col:values){
                if (col.index == index){
                    return col.desc;
                }
            }
            return "NODEF_INDEX"+index;
        }
        public static Class GET_CLASS(int index){
            ColumnaFacturaEnum[] values = ColumnaFacturaEnum.values();
            for (ColumnaFacturaEnum col:values){
                if (col.index == index){
                    return col.klass;
                }
            }
            return null;
        }
    };
    
    protected IFacturaEdit frame;
    protected List<FilaFactura> items = new ArrayList<>();
    protected TotalesFactura totalesFactura;
    protected JTable jtable;
    protected Integer tra_codigo;
    
    public FacturaDataModel(Integer tra_codigo){
        super();
        totalesFactura = new TotalesFactura();
        this.tra_codigo = tra_codigo;
    }
    
    public void removeItem(int rowIndex){
        items.remove(rowIndex);
        totalizarFactura();
        fireTableDataChanged();
    }
        
    /**
     * Metodo para precargar datos, se una en la edicion de una factura*/
    public void loadItems(List<Object[]> detallesList){
        
        for (Object[] item: detallesList){
            
            FilaFactura filafactura = new FilaFactura(1,
                (Integer)item[1],
                (String)item[7],
                (String)item[6],
                ((BigDecimal)item[3]).doubleValue(),
                (BigDecimal)item[2],
                    (Boolean)item[4],
                (BigDecimal)item[8],
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                    (String)item[11],
                    0
            );
            
            filafactura.setDescuento(item[5]!=null?(BigDecimal)item[5]:BigDecimal.ZERO);
        
            filafactura.updateTotales();
            items.add(filafactura);
            totalizarFactura();
            fireTableDataChanged();
        
        }         
    }
    
    public void addItem(Articulos articulo, Integer catCajaId){
        BigDecimal precio = articulo.getArtPrecio();
        if (this.tra_codigo == 2){//Factura de compra
            precio = articulo.getArtPrecioCompra();
        }
        
        FilaFactura filafactura = new FilaFactura(1,
                articulo.getArtId(),
                articulo.getArtCodbar(),
                articulo.getArtNombre(),
                new Double("1"),
                precio,
                articulo.getArtIva(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                articulo.getArtPreciomin(),
                articulo.getArtPrecioCompra(),
                articulo.getArtTipo(),
                catCajaId
            );
        
        filafactura.updateTotales();
        items.add(filafactura);
        totalizarFactura();
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        if (column>=0 && column<ColumnaFacturaEnum.values().length){            
            String colName = ColumnaFacturaEnum.GET_NAME(column);
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
        return ColumnaFacturaEnum.values().length;
    }

     @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        if (columnIndex == ColumnaFacturaEnum.VDESC.index){
            return true;
        }
        else{
            FilaFactura filafactura = getValueAt(rowIndex);            
            return (columnIndex == ColumnaFacturaEnum.IVA.index)
               ||(
                    (columnIndex == ColumnaFacturaEnum.PRECIOU.index)&&(tra_codigo==2)
                    ||
                    (filafactura!=null?filafactura.getTipo().equalsIgnoreCase("S"):false)
                )
               ||(columnIndex == ColumnaFacturaEnum.CANTIDAD.index)
               ||(columnIndex == ColumnaFacturaEnum.VDESC.index)
               ||(columnIndex == ColumnaFacturaEnum.PDESC.index);
        }
        
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaFactura filafactura = items.get(rowIndex);            
            
            if (columnIndex == ColumnaFacturaEnum.CANTIDAD.index) {
                filafactura.setCantidad(Double.valueOf(aValue.toString()));
            }
            else if (columnIndex == ColumnaFacturaEnum.IVA.index){
                filafactura.setIsIva("SI".equalsIgnoreCase(aValue.toString()));
            }
            else if(columnIndex == ColumnaFacturaEnum.PRECIOU.index){
                filafactura.setPrecioUnitario(new BigDecimal(aValue.toString()));
            }
            else if(columnIndex == ColumnaFacturaEnum.VDESC.index) {
                if (aValue!= null && aValue.toString().trim().length()>0){
                    filafactura.setDescuento(new BigDecimal(aValue.toString()));
                }
                else{
                    filafactura.setDescuento(BigDecimal.ZERO);
                }
            }
            else if (columnIndex == ColumnaFacturaEnum.PDESC.index){
                filafactura.setDescuentoPorc(new BigDecimal(aValue.toString()));
            }
           
            filafactura.updateTotales();
            filafactura.calcDescFromPorc();
            totalizarFactura();
            fireTableCellUpdated(rowIndex, columnIndex);
            fireTableDataChanged();
            
            frame.updateLabelsTotales();
        }
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    
    public FilaFactura getValueAt(int rowIndex){
        FilaFactura filafactura = items.get(rowIndex);
        return filafactura;
    }
            
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaFactura filafactura = items.get(rowIndex);              
            /*
            if (columnIndex==ColumnaFacturaEnum.NRO.index){
                return filafactura.getNumFila();
            }
            else 
            */
            if (columnIndex==ColumnaFacturaEnum.CODBAR.index){
                return filafactura.getCodBarra();
            }
            else if (columnIndex==ColumnaFacturaEnum.ARTICULO.index){
                return filafactura.getNombreArt();
            }
            else if (columnIndex==ColumnaFacturaEnum.CANTIDAD.index){
                return filafactura.getCantidad();
            }
            else if (columnIndex==ColumnaFacturaEnum.PRECIOU.index){
                return filafactura.getPrecioUnitario();
            }
            else if (columnIndex==ColumnaFacturaEnum.PDESC.index){
                return filafactura.getDescuentoPorc();
            }
            else if (columnIndex==ColumnaFacturaEnum.VDESC.index){
                return filafactura.getDescuento();
            }
            else if (columnIndex==ColumnaFacturaEnum.SUBTOTAL.index){
                return filafactura.getSubtotal();
            }
            else if (columnIndex==ColumnaFacturaEnum.IVA.index){
                return filafactura.isIsIva()?"SI":"NO";
                //return filafactura.isIsIva();
            }
            else if (columnIndex==ColumnaFacturaEnum.TOTAL.index){                
                BigDecimal total = filafactura.getTotal();                    
                return total.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
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
        Class klass = ColumnaFacturaEnum.GET_CLASS(columnIndex);
        if (klass != null){
            return klass;
        }
        return Object.class;
        
    }    
    
    public void totalizarFactura(){
        totalesFactura.encerar();
        for(FilaFactura fila: items){
            fila.updateTotales();
            totalesFactura.setSubtotal( totalesFactura.getSubtotal().add(fila.getSubtotal()) );
            totalesFactura.setIva(totalesFactura.getIva().add(fila.getValorIva()));
            totalesFactura.setTotal(totalesFactura.getTotal().add(fila.getTotal()));
            totalesFactura.setDescuento(totalesFactura.getDescuento().add(fila.getDescuento()));
            if (fila.isIsIva()){
                totalesFactura.setSubtotal12(totalesFactura.getSubtotal12().add(fila.getSubtotal()));
            }
        }        
        //Redondear ha 2 decimales
        totalesFactura.setSubtotal(totalesFactura.getSubtotal().setScale(2, BigDecimal.ROUND_HALF_UP));
        totalesFactura.setIva(totalesFactura.getIva().setScale(2, BigDecimal.ROUND_HALF_UP) );
        totalesFactura.setTotal( totalesFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP) );
        totalesFactura.setDescuento(totalesFactura.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP) );
        
        //Restar el descuento general
        BigDecimal globalDiscount = frame.getDescuentoGlobal();
        BigDecimal totalFactura = totalesFactura.getTotal();
        
        if (totalFactura.compareTo(globalDiscount)>=0){
            totalesFactura.setDescuentoGlobal(globalDiscount);
            BigDecimal newTotal = totalFactura.subtract(globalDiscount);
            totalesFactura.setTotal(newTotal);
        }
        else{
            totalesFactura.setDescuentoGlobal(BigDecimal.ZERO);
            frame.setValueDescGlobal(totalesFactura.getDescuentoGlobal());
            SmartFactMain.showSystemTrayMsg("El valor del descuente global es incorrecto");
        }
        
        frame.updateLabelsTotales();
    }

    public List<FilaFactura> getItems() {
        return items;
    }

    public void setItems(List<FilaFactura> items) {
        this.items = items;
    }

    public TotalesFactura getTotalesFactura() {
        return totalesFactura;
    }

    public void setTotalesFactura(TotalesFactura totalesFactura) {
        this.totalesFactura = totalesFactura;
    }

    public IFacturaEdit getFrame() {
        return frame;
    }

    public void setFrame(IFacturaEdit frame) {
        this.frame = frame;
    }
    
    public void encerarTotales(){
        this.totalesFactura.encerar();
    }

    public JTable getJtable() {
        return jtable;
    }

    public void setJtable(JTable jtable) {
        this.jtable = jtable;
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
}