/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.math.BigDecimal;
import java.util.List;
import smf.util.datamodels.rows.FilaFactura;



/**
 *
 * @author mjapon
 */
public class DetallesFactDataModel extends FacturaDataModel{
    
    
    public DetallesFactDataModel(Integer tra_codigo){
        super(tra_codigo);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //return super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
        
        return false;
    }
    
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
        }         
    }
}
