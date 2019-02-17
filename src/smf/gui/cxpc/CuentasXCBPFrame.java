/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.cxpc;

import smf.gui.facte.DetallesFacturaFrame;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;
import smf.controller.FacturasJpaController;
import smf.controller.MovtransaccJpaController;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.EntityManagerUtil;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.datamodels.rows.FilaCXCP;
import smf.util.ParamBusquedaCXCP;
import smf.util.datamodels.AbonosDataModel;
import smf.util.datamodels.CuentaXCPDataModel;
import smf.util.datamodels.rows.FilaAbonos;

/**
 *
 * @author mjapon
 */
public class CuentasXCBPFrame extends BaseFrame {
   
    private JFormattedTextField desdeTF;
    private JFormattedTextField hastaTF;
    private CuentaXCPDataModel cPDataModel;  
    private AbonosDataModel abonosDataModel;
    private Integer clpCodigo;
    private Integer estadoCobro;    
    private FacturasJpaController facturaController;
    private MovtransaccJpaController movsController;
    private Integer tra_codigo;//3-cuentas x cobrar, 4-cuentas por pagar
    private Integer tra_codigo_fact;
    
    public CuentasXCBPFrame(Integer tra_codigo) {
        super();
        initComponents();
        
        this.tra_codigo = tra_codigo;
        
         this.tra_codigo_fact = 1;
        if (this.tra_codigo == 4){
            this.tra_codigo_fact = 2;
        }
        
        desdeTF = new JFormattedTextField(createFormatter("##/##/####"));
        hastaTF = new JFormattedTextField(createFormatter("##/##/####"));
        jPanel12.add( desdeTF );
        jPanel12.add( hastaTF );
        
        this.em = EntityManagerUtil.createEntityManagerFactory();
        this.facturaController = new FacturasJpaController(em);
        this.movsController = new MovtransaccJpaController(em);        
        
        abonosDataModel = new AbonosDataModel();
        abonosDataModel.setController(movsController);
        abonosDataModel.setTraTipo(tra_codigo);
        jTable2.setModel(abonosDataModel);
        
        cPDataModel = new CuentaXCPDataModel();
        cPDataModel.setController(facturaController); 
        
        setupTransacc();
        
        jTable1.setModel(cPDataModel);
        try{
            //ventasDataModel.loadFromDataBase();
            Integer numItems = cPDataModel.getItems().size();
            jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("("+numItems+")"));
            updateLabelTotales();
        }
        catch(Throwable ex){
            JOptionPane.showMessageDialog(null, "Error al listar:"+ ex.getMessage());
            System.out.println("Error;"+ex.getMessage());
            ex.printStackTrace();
        }        
        
        jTable1.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTable1.columnAtPoint(e.getPoint());                
                try{
                    for (int i=0; i<cPDataModel.getColumnCount();i++){
                        jTable1.getColumnModel().getColumn(i).setHeaderValue( cPDataModel.getColumnName(i) );
                    }                    
                    cPDataModel.switchSortColumn(col);                    
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        jTable1.updateUI();
        cPDataModel.fireTableDataChanged();        
        
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);                
                if (mouseEvent.getClickCount() == 1) {                    
                    
                }                
                if (mouseEvent.getClickCount() == 2) {                    
                    showDetallesFrame();
                }
            }
        });
        
        jTable1.getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                updateStatusBtn();
            }
        });
        
        
        Date minDate =  null;        
        try{
            minDate = facturaController.getMinDateCXCP(this.tra_codigo_fact.toString());
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de obtener min date para cuenta xcp"+ex.getMessage());
            ex.printStackTrace();
        }        
        
        if (minDate!=null){
            this.desdeTF.setText( FechasUtil.format(minDate) );
        }
        else{
            this.desdeTF.setText( FechasUtil.getFechaActual() );
        }
        
        this.hastaTF.setText( FechasUtil.getFechaActual() ); 
        
        jTable2.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTable2.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<abonosDataModel.getColumnCount();i++){
                        jTable2.getColumnModel().getColumn(i).setHeaderValue( abonosDataModel.getColumnName(i) );
                    }                    
                    abonosDataModel.switchSortColumn(col);                    
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        jTable2.getSelectionModel().addListSelectionListener((e)->{
            if (!e.getValueIsAdjusting()) {
                updateStatusBtnAnul();
            }
        });
        
        jTable2.updateUI();
        abonosDataModel.fireTableDataChanged();        
    }
    
    public void updateStatusBtnAnul(){
        boolean isEnabled = jTable2.getSelectedRows().length>0;        
        jButtonAnular.setEnabled(false);
        if (isEnabled){
            FilaAbonos fila = abonosDataModel.getValueAt(jTable2.getSelectedRow());
            if (!"ANULADO".equalsIgnoreCase(fila.getEstado())){
                jButtonAnular.setEnabled(true);
            }
        }
    }
    
    public void updateStatusBtn(){
        boolean isEnabled = jTable1.getSelectedRows().length>0;
        jButtonVerFact.setEnabled(isEnabled);
        jButtonAbonar.setEnabled(isEnabled);
        
        try{      
            if (isEnabled){
                int rowCXCP = jTable1.getSelectedRow();
                if (rowCXCP>-1){
                    FilaCXCP filaCXCP = cPDataModel.getValueAt(rowCXCP);
                    abonosDataModel.setPgfId(filaCXCP.getCodPago());
                    jLabelHeadAbon.setText("LISTA DE ABONOS DE LA FACTURA:"+filaCXCP.getNumFactura());
                    abonosDataModel.loadFromDataBase(); 
                }
            }
            
        }
        catch(Throwable ex){
            System.out.println("Error al listar abonos:"+ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar abonos:"+ex.getMessage());
        }
    }
    
    public void setupTransacc(){
        if(this.tra_codigo == 3){
            this.jLabelTitulo.setText("CUENTAS X COBRAR");
        }
        else if (this.tra_codigo == 4){
            this.jLabelTitulo.setText("CUENTAS X PAGAR");
        }
    }
    
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
    
    
    public void updateLabelTotales(){        
        
        List<FilaCXCP> items =  cPDataModel.getItems();
        
        BigDecimal sumaMonto = BigDecimal.ZERO;
        BigDecimal sumaValorPend = BigDecimal.ZERO;
        
        for (FilaCXCP fila: items){
            sumaMonto = sumaMonto.add (fila.getMonto());
            sumaValorPend = sumaValorPend.add (fila.getDeuda());
        }
            
        jTFSumaMonto.setColumns(NumbersUtil.round2ToStr( sumaMonto ).length());
        jTFSumaMonto.setText(NumbersUtil.round2ToStr( sumaMonto ) );
        
        jTFSumaValorPend.setColumns( NumbersUtil.round2ToStr( sumaValorPend ).length() );
        jTFSumaValorPend.setText( NumbersUtil.round2ToStr( sumaValorPend ) );
       
    }
    
    public void showDetallesFrame(){
        //System.out.println("show detalles--->");
        
        int row = this.jTable1.getSelectedRow();
        if (row>-1){
            FilaCXCP filart = this.cPDataModel.getValueAt(row);            
            DetallesFacturaFrame detallesFacturaFrame = new DetallesFacturaFrame(filart.getCodFactura());
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            detallesFacturaFrame.setSize(900, 500);
            detallesFacturaFrame.setLocation(dim.width/2-detallesFacturaFrame.getSize().width/2, dim.height/2-detallesFacturaFrame.getSize().height/2);
            detallesFacturaFrame.setVisible(true);
        }    
        else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar la factura");
        }
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
        jLabelTitulo = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jCmbEstado = new javax.swing.JComboBox<>();
        jTextFieldFiltro = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabelHeadAbon = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTFSumaMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFSumaValorPend = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonVerFact = new javax.swing.JButton();
        jButtonAbonar = new javax.swing.JButton();
        jButtonAnular = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabelTitulo.setText("CUENTAS X COBRAR");

        jPanel12.setLayout(new java.awt.GridLayout(2, 2));

        jLabel3.setText("Desde:");
        jPanel12.add(jLabel3);

        jLabel4.setText("Hasta:");
        jPanel12.add(jLabel4);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoy", "Ayer", "Esta Semana", "Este Mes", "Mes Anterior", "Este Año" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jCmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendiente de Pago", "Cancelado", "Todos" }));
        jCmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbEstadoActionPerformed(evt);
            }
        });

        jTextFieldFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelTitulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.GridLayout(1, 1));

        jLabelHeadAbon.setText("LISTA DE ABONOS:");
        jPanel6.add(jLabelHeadAbon);

        jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

        jTable2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setRowHeight(30);
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setBottomComponent(jPanel5);

        jPanel7.setLayout(new java.awt.BorderLayout());

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
        jTable1.setRowHeight(30);
        jScrollPane1.setViewportView(jTable1);

        jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setText("SUMA  MONTO:");
        jPanel8.add(jLabel2);

        jTFSumaMonto.setEditable(false);
        jTFSumaMonto.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jPanel8.add(jTFSumaMonto);

        jLabel5.setText("SUMA VALOR PEND:");
        jPanel8.add(jLabel5);

        jTFSumaValorPend.setEditable(false);
        jTFSumaValorPend.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jPanel8.add(jTFSumaValorPend);

        jPanel7.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setTopComponent(jPanel7);

        jPanel2.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jLabel1.setText("totales");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel4.setLayout(new java.awt.GridLayout(5, 1));

        jButtonVerFact.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonVerFact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-visible.png"))); // NOI18N
        jButtonVerFact.setText("Ver Factura");
        jButtonVerFact.setEnabled(false);
        jButtonVerFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerFactActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonVerFact);

        jButtonAbonar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonAbonar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-cash_in_hand.png"))); // NOI18N
        jButtonAbonar.setText("Abonar");
        jButtonAbonar.setEnabled(false);
        jButtonAbonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbonarActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonAbonar);

        jButtonAnular.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-cancel_subscription.png"))); // NOI18N
        jButtonAnular.setText("Anular");
        jButtonAnular.setEnabled(false);
        jButtonAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnularActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonAnular);

        jButtonClose.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonClose);

        getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void restoreDefaultsDividerLocation() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {                
                jSplitPane1.setDividerLocation( 0.6 );
            }
        });
        logicaBuscar();
        
    }
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        
        SmartFactMain farmaApp = (SmartFactMain)this.root;        
        farmaApp.logicaClosePane(this.getClass().getName()+this.tra_codigo);
        
        
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonAbonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbonarActionPerformed
        int row = this.jTable1.getSelectedRow();
        if (row>-1){
            FilaCXCP fila = (FilaCXCP)this.cPDataModel.getValueAt(row);
            abonosFrame = new AbonosFrame(fila, this.tra_codigo);
            abonosFrame.setParentFrame(this);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            abonosFrame.setLocation(dim.width/2-abonosFrame.getSize().width/2, dim.height/2-abonosFrame.getSize().height/2);
            abonosFrame.setVisible(true);
        }
    }//GEN-LAST:event_jButtonAbonarActionPerformed

    private void jButtonVerFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerFactActionPerformed
        showDetallesFrame();        
    }//GEN-LAST:event_jButtonVerFactActionPerformed

    private void jCmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbEstadoActionPerformed
        logicaBuscar();
    }//GEN-LAST:event_jCmbEstadoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (cPDataModel != null){
            logicaBuscar();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        setupFechasEvent(jComboBox1, desdeTF, hastaTF);
        logicaBuscar();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextFieldFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroKeyReleased
        logicaBuscar();
    }//GEN-LAST:event_jTextFieldFiltroKeyReleased

    private void jButtonAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnularActionPerformed
        try{
            int row = this.jTable2.getSelectedRow();
            if (row>-1){                
                String obs = JOptionPane.showInputDialog(null, "Ingrese la observación para la anulación");
                if (obs !=null){
                    FilaAbonos fila = abonosDataModel.getValueAt(row);
                    movsController.anularAbono(fila.getMovId(), "ABONO ANULADO");
                    abonosDataModel.loadFromDataBase(); 
                    JOptionPane.showMessageDialog(null, "El abono ha sido anulado");
                    
                    logicaBuscar();
                }
            }
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de anular el abono:"+ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al tratar de anular el abono:"+ex.getMessage());
        }
    }//GEN-LAST:event_jButtonAnularActionPerformed
     
    public void logicaBuscar(){
        ParamBusquedaCXCP paramsBusqueda = new ParamBusquedaCXCP();
        
        paramsBusqueda.setEstadoPago(2);
        paramsBusqueda.setCliId(0);
        paramsBusqueda.setFiltro( jTextFieldFiltro.getText().trim() );
        
        Integer tra_codigo = 1;
        if (this.tra_codigo == 4){
            tra_codigo = 2;
        }
        paramsBusqueda.setTra_codigo(tra_codigo);
        
        try{
            paramsBusqueda.setDesde( FechasUtil.parse(this.desdeTF.getText()) );
            paramsBusqueda.setHasta( FechasUtil.parse(this.hastaTF.getText()) );
            
            int statePagoIndex = this.jCmbEstado.getSelectedIndex();
            int statePago = 0;
            if (statePagoIndex == 0){
                statePago = 2;
            }
            else if (statePagoIndex == 1){
                statePago = 1;
            }
            paramsBusqueda.setEstadoPago(statePago);
        }
        catch(Throwable ex){
            System.out.println("Error al parsear:"+ex.getMessage());
            ex.printStackTrace();
        }
        
        try{
            cPDataModel.setParams(paramsBusqueda);
            cPDataModel.loadFromDataBase();            
                        
            Integer numItems = cPDataModel.getItems().size();
            jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("("+numItems+")"));            
            updateLabelTotales();            
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }

    public JFrame getRoot() {
        return root;
    }

    public void setRoot(JFrame root) {
        this.root = root;
    }
    
    
    private AbonosFrame abonosFrame;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButtonAbonar;
    private javax.swing.JButton jButtonAnular;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonVerFact;
    private javax.swing.JComboBox<String> jCmbEstado;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelHeadAbon;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTFSumaMonto;
    private javax.swing.JTextField jTFSumaValorPend;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextFieldFiltro;
    // End of variables declaration//GEN-END:variables
}
