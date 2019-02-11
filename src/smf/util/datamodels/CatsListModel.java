/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import smf.util.datamodels.rows.CatRow;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author manuel.japon
 */
public class CatsListModel extends DefaultListModel implements ListModel{
    
    private List<CatRow> items;
    
    @Override
    public int getSize() {
        return items.size();
    }

    public CatRow getElementoAt(int index){
        return items.get(index);
    }
    
    @Override
    public Object getElementAt(int index) {
        return items.get(index).getCatName();
    }

    public List<CatRow> getItems() {
        return items;
    }

    public void setItems(List<CatRow> items) {
        this.items = items;
    }
    
}
