/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author mjapon
 */
public class FacturaModelListener implements TableModelListener{

    @Override
    public void tableChanged(TableModelEvent e) {
        /*
        System.out.println("TableChangedFired------>firstrow, column");
        System.out.println(e.getFirstRow());
        System.out.println(e.getColumn());
        */
    }
    
}