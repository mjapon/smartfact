/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author manuel.japon
 */
public class TableHeadMouseAdapter extends MouseAdapter{
    private JTable jtable;
    private GenericDataModel dataModel;
    
    public TableHeadMouseAdapter(JTable jtable, GenericDataModel dataModel){
        this.jtable = jtable;
        this.dataModel = dataModel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int col = jtable.columnAtPoint(e.getPoint());
        try{
            for (int i=0; i<dataModel.getColumnCount();i++){
                jtable.getColumnModel().getColumn(i).setHeaderValue( dataModel.getColumnName(i) );
            }                    
            dataModel.switchSortColumn(col);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void showMsgError(Throwable ex){
        JOptionPane.showMessageDialog(null, "Algo salio mal :-( "+ex.getMessage());
        System.out.println("Excepcion:"+ex.getMessage());
        ex.printStackTrace();
    }
}