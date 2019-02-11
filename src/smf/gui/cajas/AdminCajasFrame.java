/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smf.gui.cajas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import smf.controller.CajaJpaController;
import smf.controller.CatCajasJpaController;
import smf.gui.BaseFrame;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.ParamsBuscaCaja;
import smf.util.datamodels.AdminCajaDM;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.rows.FilaAdminCaja;
import smf.util.datamodels.rows.FilaCatCaja;
/**
 *
 * @author mjapon
 */
public class AdminCajasFrame extends BaseFrame {
    
    private JFormattedTextField desdeTF;
    private JFormattedTextField hastaTF;
    private CajaJpaController cajaController;
    private CatCajasJpaController catCajaJpaController;
    private List<JTableColumn> columns;
    //private CajaJpaController cajaJpaController;
    
    //private CajasDataModel cajasDataModel;
    private AdminCajaDM adminCajaDM;

    /** Creates new form AdminCajasFrame */
    public AdminCajasFrame() {
        initComponents();
        cajaController = new CajaJpaController(em);
        catCajaJpaController = new CatCajasJpaController(em);
        setupFechas();        
        initColumns();
        init();
        loadCajas();
    }
    
    private void initColumns(){
        
        columns = new  ArrayList<>();
        
         columns.add(
                new JTableColumn<FilaAdminCaja>(
                        0, 
                        "Nro", 
                        "cc_id", 
                        Integer.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return row.getNroCaja();
                            }
                        }
                )
        );
         
         columns.add(
                new JTableColumn<FilaAdminCaja>(
                        1, 
                        "Caja", 
                        "nombre", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return row.getNombreCaja();
                            }
                        }
                )
        );
         
        columns.add(
                new JTableColumn<FilaAdminCaja>(
                        2, 
                        "Fecha Apertura", 
                        "fechaaper", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return row.getFechaApertura()!=null?FechasUtil.formatDateHour(row.getFechaApertura()):"";
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaAdminCaja>(
                        3, 
                        "Fecha Cierre", 
                        "fechacierre", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return row.getFechaCierre()!=null?FechasUtil.formatDateHour(row.getFechaCierre()):"";
                            }
                        }
                )
        );
        
        
         columns.add(
                new JTableColumn<FilaAdminCaja>(
                        4, 
                        "Estado", 
                        "estado", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return row.getEstado();
                            }
                        }
                )
        );
         
          columns.add(
                new JTableColumn<FilaAdminCaja>(
                        5, 
                        "Saldo Anterior", 
                        "saldoant", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSaldoAnt());
                            }
                        }
                )
        );
          
          columns.add(
                new JTableColumn<FilaAdminCaja>(
                        6, 
                        "Saldo Inicial", 
                        "saldoini", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSaldoIni());
                            }
                        }
                )
        );
          
           columns.add(
                new JTableColumn<FilaAdminCaja>(
                        7, 
                        "Ventas", 
                        "ventas", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSumaVentas());
                            }
                        }
                )
        );
           
           columns.add(
                new JTableColumn<FilaAdminCaja>(
                        8, 
                        "Compras", 
                        "compras", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSumaCompras());
                            }
                        }
                )
        );
           
            columns.add(
                new JTableColumn<FilaAdminCaja>(
                        9, 
                        "Abonos Cob.", 
                        "abonoscob", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSumaAbonosCob());
                            }
                        }
                )
        );
            
            columns.add(
                new JTableColumn<FilaAdminCaja>(
                        10, 
                        "Abonos Pag.", 
                        "abonospag", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSumaAbonosPag());
                            }
                        }
                )
        );
            
            
            columns.add(
                new JTableColumn<FilaAdminCaja>(
                        11, 
                        "Ajuste", 
                        "ajuste", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getAjuste());
                            }
                        }
                )
        );
            
            
            columns.add(
                new JTableColumn<FilaAdminCaja>(
                        12, 
                        "Saldo Final", 
                        "saldofin", 
                        String.class,
                        new DefGSVCol<FilaAdminCaja>() {
                            public Object getValueAt(FilaAdminCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getSaldoFin());
                            }
                        }
                )
        );  
        
        
        
        /*
              0:"  cj.cj_id,\n" +
                1:"  cc.cc_id,\n" +
                2"  cc.cc_nombre,\n" +
                3"  cj.cj_fecaper,\n" +
                4"  cj.cj_feccierre,\n" +
                5"  cj.cj_estado,\n" +
                6"  case cj.cj_estado as estado,\n" +
                7"  cd.dc_saldoant,\n" +
                8"  cd.dc_saldoini,\n" +
                9"  cd.dc_ventas,\n" +
                10"  cd.dc_compras,\n" +
                11"  cd.dc_aboncob,\n" +
                12"  cd.dc_abonpag,\n" +
                13"  cd.dc_ajuste,\n" +
                14"  cd.dc_saldofin,\n" +
                15"  cj.cj_obsaper,\n" +
                16"  cj.cj_obscierre\n" +
                */
                
       
        
        
    }
    
    public void setupFechas(){
        desdeTF = new JFormattedTextField(createFormatter("##/##/####"));
        desdeTF.setFont(new java.awt.Font("Arial", 0, 16));
        
        hastaTF = new JFormattedTextField(createFormatter("##/##/####"));
        hastaTF.setFont(new java.awt.Font("Arial", 0, 16));
        
        jPanel12.add( desdeTF );
        jPanel12.add( hastaTF );
    }
    
    public void loadCajas(){
        List<FilaCatCaja> catCajasList = catCajaJpaController.listForGrid();
        
        FilaCatCaja[] cajasv = new FilaCatCaja[catCajasList.size()];
        int i = 0;
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
    
    public void init(){
        desdeTF.setText(FechasUtil.getFechaActual());
        hastaTF.setText(FechasUtil.getFechaActual());
        
        adminCajaDM = new AdminCajaDM(columns, cajaController);
        
        jTable1.setModel(adminCajaDM);
        
        
        jTable1.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTable1.columnAtPoint(e.getPoint());
                String name = jTable1.getColumnName(col);
            }
        });
        
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getFirstIndex()>=0){
                    jButtonAnular.setEnabled(false);
                    btnDetalles.setEnabled(false);
                    try{
                        int row = jTable1.getSelectedRow();
                        if (row>-1){
                            FilaAdminCaja  filacaja = adminCajaDM.getValueAt(row);
                            if (filacaja.getCjEstado() != 2){
                                jButtonAnular.setEnabled(true);
                            }
                            btnDetalles.setEnabled(true);
                        }
                    }
                    catch(Throwable ex){
                        System.out.println("Error en selection listener:"+ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                else{
                    jButtonAnular.setEnabled(false);
                }
            }
        });
        
        jTable1.updateUI();
        adminCajaDM.fireTableDataChanged();
    }
    
    public void doAnular(){
        int row = this.jTable1.getSelectedRow();
        if (row>-1){
            FilaAdminCaja fila = this.adminCajaDM.getValueAt(row);        
            if (fila != null){
                try {
                    if (fila.getCjEstado()  == 1 ){
                        showMsg("No se puede anular esta caja, ya ha sido cerrada");
                    }
                    else{
                        if (showConfirmMsg("¿Seguro que desea anular este registro?")){
                            this.cajaController.anularCaja(fila.getNroCaja());
                            showMsg("La caja ha sido anulada");
                            adminCajaDM.loadFromDataBase();
                            adminCajaDM.fireTableDataChanged();
                        }
                    }
                    
                } catch (Throwable ex) {
                    showMsgError(ex);
                }
            }
        }
        else{
            showMsg("Elija el registro que desea anular");
        }
    }
    
    public void logicaBuscar(){
        try{
            Date fechaDesde = FechasUtil.parse(desdeTF.getText());
            Date fechaHasta = FechasUtil.parse(hastaTF.getText());
            
            FilaCatCaja catCaja = (FilaCatCaja)this.jCBCaja.getSelectedItem();            
            ParamsBuscaCaja params = new ParamsBuscaCaja(fechaDesde, fechaHasta, catCaja.getCatCajaId());
            
            adminCajaDM.setParams(params);
            adminCajaDM.loadFromDataBase();
            adminCajaDM.fireTableDataChanged();
        }
        catch(Throwable ex){
            showMsgError(ex);
        }    
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jCBCaja = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButtonAnular = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Administración de cajas");
        jPanel5.add(jLabel1);

        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoy", "Ayer", "Esta Semana", "Este Mes", "Mes Anterior", "Este Año" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jPanel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel12.setLayout(new java.awt.GridLayout(2, 2));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel2.setText("Desde:");
        jPanel12.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel3.setText("Hasta:");
        jPanel12.add(jLabel3);

        jButtonBuscar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jCBCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jCBCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(5, 1));

        jButtonAnular.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-cancel_subscription.png"))); // NOI18N
        jButtonAnular.setText("Anular");
        jButtonAnular.setEnabled(false);
        jButtonAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnularActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonAnular);

        btnDetalles.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-view_details.png"))); // NOI18N
        btnDetalles.setText("Detalles");
        btnDetalles.setEnabled(false);
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        jPanel3.add(btnDetalles);

        jButtonCerrar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButtonCerrar.setText("Cerrar");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonCerrar);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
        setupFechasEvent(jComboBox1, desdeTF, hastaTF);
        logicaBuscar();

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        
        logicaBuscar();
        
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        
        this.closeFrame();
        
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jButtonAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnularActionPerformed
    //    if (showConfirmMsg("¿Seguro que desea anular este registro?")){
            doAnular();
      //  }
    }//GEN-LAST:event_jButtonAnularActionPerformed

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        showDetallesFrame();
    }//GEN-LAST:event_btnDetallesActionPerformed

    private void jCBCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCajaActionPerformed
        logicaBuscar();
    }//GEN-LAST:event_jCBCajaActionPerformed
    
    
     private void showDetallesFrame(){
         try{
             int row = this.jTable1.getSelectedRow();
            if (row>-1){
                FilaAdminCaja filacaja = this.adminCajaDM.getValueAt(row);  

                CierrCajaFrame cajaFrame = new CierrCajaFrame(filacaja.getNroCaja());
                cajaFrame.centerOnScreen();
                cajaFrame.loadDatosCajaId();

                cajaFrame.setVisible(true);
            }    
            else{
                JOptionPane.showMessageDialog(this, "Debe seleccionar la caja");
            }
         }
         catch(Throwable ex){
             showMsgError(ex);
         }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton jButtonAnular;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JComboBox<String> jCBCaja;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
