/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import javax.swing.DefaultComboBoxModel;
import smf.entity.Articulos;

/**
 *
 * @author mjapon
 */
public class ArticuloComboBoxModel extends DefaultComboBoxModel<Articulos>{

       public ArticuloComboBoxModel(Articulos[] items){
           super(items);
       }

    @Override
    public Articulos getSelectedItem() {
        return (Articulos)super.getSelectedItem(); //To change body of generated methods, choose Tools | Templates.
    }
       


    
}
