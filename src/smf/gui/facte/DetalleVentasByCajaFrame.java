/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.facte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import smf.controller.CatCajasJpaController;
import smf.controller.FacturasJpaController;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.ParamsBusquedaTransacc;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.TotalesVentasModel;
import smf.util.datamodels.VentasByCajaDM;
import smf.util.datamodels.rows.FilaCatCaja;
import smf.util.datamodels.rows.RowFactByCaja;

/**
 *
 * @author mjapon
 */
public class DetalleVentasByCajaFrame extends BaseFrame {

    private Integer tra_codigo;
    private FacturasJpaController facturasController;
    private CatCajasJpaController catCajaJpaController;
    private JFormattedTextField desdeTF;
     private JFormattedTextField hastaTF;
     private VentasByCajaDM ventasByCajaDM;
     
     private List<JTableColumn> columns;
     
    /**
     * Creates new form DetalleVentasByCajaFrame
     */
    public DetalleVentasByCajaFrame(Integer tra_codigo) {
        initComponents();
        
        this.tra_codigo = tra_codigo;
        this.facturasController = new FacturasJpaController(em);
        this.catCajaJpaController = new CatCajasJpaController(em);
        
        setupFechas();
        loadCajas();
        initColumn();
        
        ventasByCajaDM = new  VentasByCajaDM(columns, facturasController);
        jTableMain.setModel(ventasByCajaDM);
        
    }
    
     public void setupFechas(){
        desdeTF = new JFormattedTextField(createFormatter("##/##/####"));
        desdeTF.setFont(new java.awt.Font("Arial", 0, 16));
        
        hastaTF = new JFormattedTextField(createFormatter("##/##/####"));
        hastaTF.setFont(new java.awt.Font("Arial", 0, 16));
        
        jPanel12.add( desdeTF );
        jPanel12.add( hastaTF );
        
        desdeTF.setText(FechasUtil.getFechaActual());
        hastaTF.setText(FechasUtil.getFechaActual());
        
    }
    
    public void initColumn(){
        
        columns = new ArrayList<>();
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        0, 
                        "Fecha", 
                        String.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return FechasUtil.format(row.getFactFecha());
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        1, 
                        "Nro", 
                        String.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return row.getFactNum();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        2, 
                        "Artículo", 
                        String.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return row.getArtNombre();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        3, 
                        "Cantidad", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return row.getDetfCant();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        4, 
                        "Precio", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return NumbersUtil.round2(row.getDetfPrecio());
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        5, 
                        "Descuento", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return NumbersUtil.round2(row.getDetfDesc());
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        6, 
                        "Total", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return NumbersUtil.round2(row.getTotal()) ;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTFTotal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jCBCaja = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel6.setText("TOTAL");
        jPanel6.add(jLabel6);
        jPanel6.add(jTFTotal);

        jPanel1.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(5, 1));

        btnClose.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        btnClose.setText("Cerrar");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel3.add(btnClose);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("DETALLE DE VENTAS");
        jPanel2.add(jLabel1);

        jPanel4.add(jPanel2, java.awt.BorderLayout.NORTH);

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jPanel12.setLayout(new java.awt.GridLayout(2, 2));

        jLabel2.setText("Desde:");
        jPanel12.add(jLabel2);

        jLabel3.setText("Hasta:");
        jPanel12.add(jLabel3);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoy", "Ayer", "Esta Semana", "Este Mes", "Mes Anterior", "Este Año" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel4.setText("Caja:");

        jCBCaja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(380, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jCBCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(649, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jCBCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        //this.setVisible(false);
        SmartFactMain farmaApp = (SmartFactMain)this.root;
        farmaApp.logicaClosePane(this.getClass().getName()+this.tra_codigo);

    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (ventasByCajaDM != null){
            logicaBuscar();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed
    
    private void logicaBuscar(){
        try{
            ParamsBusquedaTransacc paramsBusquedaTransacc = new ParamsBusquedaTransacc();
            paramsBusquedaTransacc.setTraCodigo(tra_codigo);
            paramsBusquedaTransacc.setDesde(FechasUtil.parse(desdeTF.getText()));
            paramsBusquedaTransacc.setHasta(FechasUtil.parse(hastaTF.getText()));
            
            FilaCatCaja catCaja = (FilaCatCaja)this.jCBCaja.getSelectedItem();
            paramsBusquedaTransacc.setCatCajaId(catCaja.getCatCajaId());
            
            ventasByCajaDM.setParamsBusquedaTransacc(paramsBusquedaTransacc);
            ventasByCajaDM.loadFromDataBase();
            ventasByCajaDM.fireTableDataChanged();
            jTableMain.updateUI();
            
            TotalesVentasModel totalesModel = ventasByCajaDM.getTotalesVentasModel();
            
//            this.jTFTotalDesc.setText( NumbersUtil.round2ToStr(totalesModel.getSumaEfectivo()) );
            this.jTFTotal.setText( NumbersUtil.round2ToStr(totalesModel.getSumaTotal()) );
            
            updateBorderTable(jPanel1, ventasByCajaDM);
            
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        setupFechasEvent(jComboBox1, desdeTF, hastaTF);
        logicaBuscar();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    public void loadCajas(){
        List<FilaCatCaja> catCajasList = catCajaJpaController.listForGrid();
        
        FilaCatCaja[] cajasv = new FilaCatCaja[catCajasList.size()+1];
        int i = 1;
        cajasv[0] = new FilaCatCaja(0, "TODOS", "");
        for (FilaCatCaja fila: catCajasList){
            cajasv[i] = fila;
            i++;
        }
        
        ComboBoxModel cmbmodel = new DefaultComboBoxModel(cajasv);
        
        jCBCaja.setModel(cmbmodel );
        jCBCaja.updateUI();
        
        jCBCaja.addItemListener(e -> {
            FilaCatCaja c = (FilaCatCaja) e.getItem();
          });
        
    }
    private void jCBCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCajaActionPerformed
        logicaBuscar();
    }//GEN-LAST:event_jCBCajaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<String> jCBCaja;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
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
