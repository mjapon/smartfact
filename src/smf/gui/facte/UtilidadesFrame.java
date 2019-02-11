/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.facte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import smf.controller.CatCajasJpaController;
import smf.gui.BaseFrame;
import smf.util.NumbersUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.UtilidadesByCajaDM;
import smf.util.datamodels.rows.FilaCatCaja;
import smf.util.datamodels.rows.FilaUtilsByCaja;

/**
 *
 * @author mjapon
 */
public class UtilidadesFrame extends BaseFrame {
    
    private UtilidadesByCajaDM datamodel;
    private List<JTableColumn> columns;
    private CatCajasJpaController cajasJpaController;

    /**
     * Creates new form UtilidadesFrame
     */
    public UtilidadesFrame(Map<Integer, BigDecimal> map) {
        initComponents();
        
        cajasJpaController = new CatCajasJpaController(em);
        columns = new ArrayList<>();
        
        initColumns();
        
        datamodel = new UtilidadesByCajaDM(columns, cajasJpaController);
        datamodel.setUtilidadesMap(map);
        datamodel.loadFromDataBase();
        datamodel.fireTableDataChanged();
        jTableMain.setModel(datamodel);
        jTableMain.updateUI();
        
        BigDecimal total = datamodel.getTotal();
        jTFTotal.setText(NumbersUtil.round2ToStr(total) );
        
    }
    
    private void initColumns(){
        columns.add(
          new JTableColumn(0, "CAJA", "caja", String.class,  new DefGSVCol<FilaUtilsByCaja>() {
                            public Object getValueAt(FilaUtilsByCaja row, int rowIndex) {
                                return row.getCaja();
                            }
                        })
        );
        
        columns.add(
          new JTableColumn(0, "UTILIDAD", "utilidad", String.class,  new DefGSVCol<FilaUtilsByCaja>() {
                            public Object getValueAt(FilaUtilsByCaja row, int rowIndex) {
                                return  row.getValor();
                            }
                        })
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
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTFTotal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("UTILIDADES POR CAJA");
        jPanel1.add(jLabel4);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTableMain.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
        jTableMain.setRowHeight(25);
        jScrollPane1.setViewportView(jTableMain);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("TOTAL:");
        jPanel6.add(jLabel5);

        jTFTotal.setEditable(false);
        jTFTotal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTFTotal.setPreferredSize(new java.awt.Dimension(10, 40));
        jPanel6.add(jTFTotal);

        jPanel4.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(5, 1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
