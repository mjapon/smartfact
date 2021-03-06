/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.facte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import smf.controller.CatCajasJpaController;
import smf.gui.BaseFrame;
import smf.util.NumbersUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.PagosByCajaDM;
import smf.util.datamodels.rows.FilaPagoByCaja;

/**
 *
 * @author mjapon
 */
public class PagosEfectByCaja extends BaseFrame {
    
    private CatCajasJpaController catCajasCntrl;
    private PagosByCajaDM pagosByCajaDM;
    private List<JTableColumn> columns;
    private BigDecimal montoEfectivo;
    
    private FacturaVentaFrame facturaVentaFrame;
    
    /**
     * Creates new form PagosEfectByCaja
     */
    public PagosEfectByCaja() {
        initComponents();        
        
        this.catCajasCntrl = new CatCajasJpaController(em);
        initColumns();
        
    }
    
    public void init(BigDecimal montoEfectivo){
        this.montoEfectivo = montoEfectivo;
        this.pagosByCajaDM = new PagosByCajaDM(columns, catCajasCntrl, this.montoEfectivo);
        
        this.jTFTotalEfec.setText( NumbersUtil.round2ToStr(montoEfectivo) );
        
        this.jTableMain.setModel(pagosByCajaDM);        
        pagosByCajaDM.addSelectingEditor(jTableMain, 1);
        
        pagosByCajaDM.loadFromDataBase();
        pagosByCajaDM.fireTableDataChanged();
        jTableMain.updateUI();
    }
    
    public boolean setMontoEfectivoCaja(Integer catCajaId){
        return this.pagosByCajaDM.setValorEfectivoCaja(catCajaId);
    }
    
    public List<FilaPagoByCaja> getItems(){
        return pagosByCajaDM.getItems();
    }
            
    
    public int checkTotal(){        
        List<FilaPagoByCaja> pagosList = pagosByCajaDM.getItems();
        BigDecimal suma = BigDecimal.ZERO;
        for (FilaPagoByCaja item: pagosList ){
            suma = suma.add(item.getMonto());
        }
        
        
        BigDecimal resta =  NumbersUtil.round2(montoEfectivo.subtract(suma) ).abs();
        if (resta.compareTo(new BigDecimal("0.01"))>=0){
            showMsg("La suma "+ NumbersUtil.round2ToStr(suma) +" no coincide con el total en efectivo: " + NumbersUtil.round2ToStr(montoEfectivo) );
            return -1;
        }        
        return 0;
    }

    public void initColumns(){
        columns = new ArrayList<>();
        
        columns.add(
                new JTableColumn<FilaPagoByCaja>(
                        0,
                        "CAJA", 
                        "cc_id", 
                        String.class,
                        new DefGSVCol<FilaPagoByCaja>() {
                            public Object getValueAt(FilaPagoByCaja row, int rowIndex) {
                                return row.getCaja();
                            }
                        }
                )
        );
        
         columns.add(
                new JTableColumn<FilaPagoByCaja>(
                        1,
                        "MONTO", 
                        "monto", 
                        BigDecimal.class,
                        new DefGSVCol<FilaPagoByCaja>() {
                            
                            @Override
                            public boolean isCellEditable(FilaPagoByCaja row) {
                                return true;
                            }
                            
                            public Object getValueAt(FilaPagoByCaja row, int rowIndex) {
                                return NumbersUtil.round2(row.getMonto());
                            }

                            @Override
                            public void setValueAt(FilaPagoByCaja row, int rowIndex, Object value) {
                                try{
                                    row.setMonto(new BigDecimal(value.toString()));
                                }
                                catch(Throwable ex){
                                    logError(ex);
                                    row.setMonto(BigDecimal.ZERO);
                                }
                            }                            
                        }
                )
        );
        
    }

    public FacturaVentaFrame getFacturaVentaFrame() {
        return facturaVentaFrame;
    }

    public void setFacturaVentaFrame(FacturaVentaFrame facturaVentaFrame) {
        this.facturaVentaFrame = facturaVentaFrame;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTFTotalEfec = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("PAGO EN EFECTIVO: SELECCIONE CAJA");
        jPanel2.add(jLabel1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.GridLayout(5, 1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-checked.png"))); // NOI18N
        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("MONTO TOTAL:");
        jPanel4.add(jLabel2);

        jTFTotalEfec.setEditable(false);
        jTFTotalEfec.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jPanel4.add(jTFTotalEfec);

        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        if (checkTotal()==0){
            setVisible(false);
            facturaVentaFrame.doSaveAfterCheckCaja();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFTotalEfec;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
