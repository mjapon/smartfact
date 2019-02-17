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
public class EditFacturaDataModel extends FacturaDataModel{
    
    public EditFacturaDataModel(Integer tra_codigo) {
        super(tra_codigo);
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
            totalizarFactura();
            fireTableDataChanged();
        
        }         
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        
          if (columnIndex == ColumnaFacturaEnum.VDESC.index){
            return true;
        }
        else{
            //FilaFactura filafactura = getValueAt(rowIndex);            
            return (columnIndex == ColumnaFacturaEnum.IVA.index)
               ||(columnIndex == ColumnaFacturaEnum.PRECIOU.index)
               ||(columnIndex == ColumnaFacturaEnum.CANTIDAD.index)
               ||(columnIndex == ColumnaFacturaEnum.VDESC.index)
               ||(columnIndex == ColumnaFacturaEnum.PDESC.index);
        }
          
    }
    
    
    
    
}
