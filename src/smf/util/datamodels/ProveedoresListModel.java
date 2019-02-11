/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import smf.util.datamodels.rows.ProveedorRow;

/**
 *
 * @author mjapon
 */
public class ProveedoresListModel extends DefaultListModel implements ListModel{
    
    private List<ProveedorRow> items;

    @Override
    public int getSize() {
        return items.size(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ProveedorRow getElementoAt(int index){
        return items.get(index);
    }
    
    @Override
    public Object getElementAt(int index) {
        return items.get(index).getProvName();
    }

    public List<ProveedorRow> getItems() {
        return items;
    }

    public void setItems(List<ProveedorRow> items) {
        this.items = items;
    }
    
}
