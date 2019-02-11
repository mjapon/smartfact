/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.HashMap;
import smf.util.CtesU;
import smf.util.datamodels.rows.FilaVenta;

/**
 *
 * @author mjapon
 */
public class MovsCVDataModel extends VentasDataModel{    
    
    public enum ColMovsCVEnum{                
        FECHAREG(0, "Fecha", String.class, "factFecreg"),
        NROFACT(1, "Nro", String.class, "factNum"),
        SUBTOTAL(2, "Subtotal", BigDecimal.class, "factSubt"),
        IVA(3, "IVA", BigDecimal.class, "factIva"),        
        TOTAL(4, "Total", BigDecimal.class, "factTotal"),      
        EFECTIVO(5, "Efectivo", BigDecimal.class, "factTotal"),
        CREDITO(6, "Crédito", BigDecimal.class, "factTotal");
        
        public final int index;
        public String desc;
        public Class klass;
        public String entityCol;
        private ColMovsCVEnum(int index, String desc, Class cclass, String entityColumn){
            this.index = index; 
            this.desc = desc;
            this.klass = cclass;
            this.entityCol = entityColumn;
        }
        public static String GET_NAME(int index){            
            ColMovsCVEnum[] values = ColMovsCVEnum.values();            
            for (ColMovsCVEnum col:values){
                if (col.index == index){
                    return col.desc;
                }
            }
            return "NODEF_INDEX"+index;
        }
        public static Class GET_CLASS(int index){
            ColMovsCVEnum[] values = ColMovsCVEnum.values();
            for (ColMovsCVEnum col:values){
                if (col.index == index){
                    return col.klass;
                }
            }
            return null;
        }
        public static String GET_ENTITY(int index){
            ColVentasEnum[] values = ColVentasEnum.values();
            for (ColVentasEnum col:values){
                if (col.index == index){
                    return col.entityCol;
                }
            }
            return null;            
        }
    };
    
    public void initForAdmin(){
         mapSort = new HashMap<>();
         mapSort.put(0, 1);
         mapSort.put(1, 0);
         mapSort.put(2, 0);
         mapSort.put(3, 0);
         mapSort.put(4, 0);
         mapSort.put(5, 0);
         mapSort.put(6, 0);
    }
    
    
    public MovsCVDataModel(){
        super();
        initForAdmin();        
    }
    
     @Override
    public String getColumnName(int column) { 
        if (column>=0 && column<MovsCVDataModel.ColMovsCVEnum.values().length){  
            
            String colName = MovsCVDataModel.ColMovsCVEnum.GET_NAME(column);
            
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
    public int getColumnCount() {
        return MovsCVDataModel.ColMovsCVEnum.values().length;
    }
    
    
     @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex>=0 && rowIndex<items.size()){
            FilaVenta filaVenta = items.get(rowIndex);
            if (columnIndex==ColMovsCVEnum.NROFACT.index){
                return filaVenta.getNro();
            }            
            else if (columnIndex==ColMovsCVEnum.SUBTOTAL.index){
                
                BigDecimal subtotal = filaVenta.getSubtotal();
                subtotal.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
                return subtotal.toPlainString();
            }
            else if (columnIndex==ColMovsCVEnum.IVA.index){
                BigDecimal valorIva = filaVenta.getIva();
                valorIva.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
                return valorIva.toPlainString();
            }
            else if (columnIndex==ColMovsCVEnum.TOTAL.index){
                BigDecimal total = filaVenta.getTotal();
                total.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
                return total.toPlainString();
            }
            else if (columnIndex==ColMovsCVEnum.FECHAREG.index){
                return filaVenta.getFechaReg();
            }
            else if (columnIndex==ColMovsCVEnum.EFECTIVO.index){
                BigDecimal efectivo = filaVenta.getEfectivo();
                efectivo.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
                return efectivo.toPlainString();
            }
            else if (columnIndex==ColMovsCVEnum.CREDITO.index){
                BigDecimal credito = filaVenta.getCredito();
                credito.setScale(CtesU.NUM_DECIM_VIEW, BigDecimal.ROUND_HALF_UP);
                return credito.toPlainString();
            }
            else{
                return "";
            }
        }
        else{
            return "";
        }
    }
    
    
}
