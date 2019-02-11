/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.cajas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import smf.controller.CatCajasJpaController;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.datamodels.CatCajasDataModel;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.rows.FilaCatCaja;


/**
 *
 * @author manuel.japon
 */
public class CatalogoCajasFrame extends BaseFrame {
    
    private CatCajasJpaController catCajasCntrl;
    private CatCajasDataModel dataModel;
    private List<JTableColumn> columns;

    /**
     * Creates new form CatalogoCajasFrame
     */
    public CatalogoCajasFrame() {
        initComponents();
        catCajasCntrl = new CatCajasJpaController(em);
        columns = new ArrayList<>();
        initColumns();
        
        dataModel = new CatCajasDataModel(columns, catCajasCntrl);
        
        jTableMain.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    checkStatusBtn();
                }
            }
        });
        
        jTableMain.setModel(dataModel);
        
    }
    
    public void checkStatusBtn(){
        int[] selectedRows = jTableMain.getSelectedRows();
        btnBorrar.setEnabled( selectedRows.length>0 );
    }
    
    public void initColumns(){
        
        columns.add(
                new JTableColumn<FilaCatCaja>(
                        0, 
                        "ID", 
                        "cc_id", 
                        Integer.class,
                        new DefGSVCol<FilaCatCaja>() {
                            public Object getValueAt(FilaCatCaja row, int rowIndex) {
                                return row.getCatCajaId();
                            }
                        }
                )
        );
        
        columns.add(new JTableColumn<FilaCatCaja>(
                        1, 
                        "Nombre", 
                        "cc_nombre", 
                        String.class,
                        new DefGSVCol<FilaCatCaja>() {
                            public Object getValueAt(FilaCatCaja row, int rowIndex) {
                                return row.getNombre();
                            }

                            @Override
                            public void setValueAt(FilaCatCaja row, int rowIndex, Object value) {
                                if (value.toString().trim().length()==0){
                                    showMsg("Nombre incorrecto");
                                    return;
                                }
                                
                                row.setNombre(value.toString());
                                try{
                                    catCajasCntrl.editar(row.getCatCajaId(), row.getNombre(), row.getObs());
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }
                                dataModel.loadFromDataBase();
                                dataModel.fireTableDataChanged();
                            }

                            @Override
                            public boolean isCellEditable(FilaCatCaja row) {
                             return true;
                            }
                        }
                )
        );
        
        columns.add(new JTableColumn<FilaCatCaja>(
                        2, 
                        "Observación", 
                        "cc_observacion", 
                        String.class,
                        new DefGSVCol<FilaCatCaja>() {
                            public Object getValueAt(FilaCatCaja row, int rowIndex) {
                                return row.getObs();
                            }

                            @Override
                            public void setValueAt(FilaCatCaja row, int rowIndex, Object value) {
                                row.setObs(value.toString());
                                
                                try{
                                    catCajasCntrl.editar(row.getCatCajaId(), row.getNombre(), row.getObs());
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }
                                
                                dataModel.loadFromDataBase();
                                dataModel.fireTableDataChanged();
                            }

                            @Override
                            public boolean isCellEditable(FilaCatCaja row) {
                                return true;
                            }
                            
                        }
                )
        );
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("TIPOS DE CAJA");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_25px.png"))); // NOI18N
        btnAdd.setText("Crear");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableMain);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(4, 1));

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-trash.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.setEnabled(false);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBorrar);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnCerrar);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnCerrarActionPerformed
    
     public void loadItems(){
        dataModel.loadFromDataBase();
        dataModel.fireTableDataChanged();
    }
     
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        int[] rows = jTableMain.getSelectedRows();
        try{
            if (showConfirmMsg("¿Segur@ que desea borrar estos registros?")){
                for(int i=0;i<rows.length;i++){
                    FilaCatCaja row = dataModel.getValueAt(rows[i]);
                    if (row.getCatCajaId() == 1){
                        showMsg("No se puede borrar la CAJA GENERAL");
                    }
                    else{
                        catCajasCntrl.eliminar(row.getCatCajaId());
                    }
                    
                }
                SmartFactMain.showSystemTrayMsg("Registros Borrados");
                loadItems();
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String nombrecat = JOptionPane.showInputDialog(this," Ingrese el nombre de la caja ");
        try{
            if (nombrecat != null){
                if (nombrecat.trim().length()>0){
                    catCajasCntrl.create(nombrecat.trim().toUpperCase(), "");
                    loadItems();
                }
                else{
                    showMsg("Nombre incorrecto");
                }
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }//GEN-LAST:event_btnAddActionPerformed
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
