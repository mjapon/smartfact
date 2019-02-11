/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;

/**
 *
 * @author manuel.japon
 */
public class DMEnums {
    
    
     public enum ColVentasEnum{        
        NROFACT(0, "Nro", String.class, "factNum"),
        FECHAREG(1, "Fecha", String.class, "factFecreg"),
        CLIENTE(2, "Cliente", String.class, "cliId.cliNombres"),
        CIRUC(3, "CI/RUC", String.class, "cliId.cliCi"),
        SUBTOTAL(4, "Subtotal", BigDecimal.class, "factSubt"),
        IVA(5, "IVA", BigDecimal.class, "factIva"),
        DESCUENTO(6, "Descuento", BigDecimal.class,"factDesc"),
        TOTAL(7, "Total", BigDecimal.class, "factTotal"),      
        EFECTIVO(8, "Efectivo", BigDecimal.class, "factTotal"),
        CREDITO(9, "Crédito", BigDecimal.class, "factTotal"),
        SALDO(10, "Saldo Pend", BigDecimal.class, "factTotal");
        
        public final int index;
        public String desc;
        public Class klass;
        public String entityCol;
        private ColVentasEnum(int index, String desc, Class cclass, String entityColumn){
            this.index = index; 
            this.desc = desc;
            this.klass = cclass;
            this.entityCol = entityColumn;
        }
        public static String GET_NAME(int index){            
            ColVentasEnum[] values = ColVentasEnum.values();            
            for (ColVentasEnum col:values){
                if (col.index == index){
                    return col.desc;
                }
            }
            return "NODEF_INDEX"+index;
        }
        public static Class GET_CLASS(int index){
            ColVentasEnum[] values = ColVentasEnum.values();
            for (ColVentasEnum col:values){
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
    
}
