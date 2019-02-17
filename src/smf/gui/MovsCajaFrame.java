/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui;

import smf.gui.facte.DetallesFacturaFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import smf.controller.FacturasJpaController;
import smf.util.EntityManagerUtil;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaCXCP;
import smf.util.datamodels.rows.FilaVenta;
import smf.util.NumbersUtil;
import smf.util.ParamBusquedaCXCP;
import smf.util.ParamsBusquedaTransacc;
import smf.util.TotalesCuentasXPC;
import smf.util.datamodels.MovsCVDataModel;
import smf.util.datamodels.MovsCXCPDataModel;
import smf.util.datamodels.TotalesVentasModel;

/**
 *
 * @author mjapon
 */
public class MovsCajaFrame extends BaseFrame {
    
    private JFormattedTextField desdeTF;
    private JFormattedTextField hastaTF;
    
    private MovsCVDataModel movsVentasDataModel;    
    private MovsCVDataModel movsComprasDataModel;    
    private MovsCXCPDataModel movsCXCDataModel;
    private MovsCXCPDataModel movsCXPDataModel;
    private FacturasJpaController facturaController;

    /**
     * Creates new form MovsCajaFrame
     */
    public MovsCajaFrame() {
        super();
        initComponents();
        em = EntityManagerUtil.createEntityManagerFactory();
        facturaController = new FacturasJpaController(em);        
        
        desdeTF = new JFormattedTextField(createFormatter("##/##/####"));
        hastaTF = new JFormattedTextField(createFormatter("##/##/####"));
        
        jPanel13.add( desdeTF );
        jPanel13.add( hastaTF );
        
        this.desdeTF.setText( FechasUtil.getFechaActual() );
        this.hastaTF.setText( FechasUtil.getFechaActual() );
        
        //Configuracion de movimientos de venta
        movsVentasDataModel = new MovsCVDataModel();
        movsVentasDataModel.setController(facturaController);
        jTableVentas.setModel(movsVentasDataModel);        
        jTableVentas.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableVentas.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsVentasDataModel.getColumnCount();i++){
                        jTableVentas.getColumnModel().getColumn(i).setHeaderValue( movsVentasDataModel.getColumnName(i) );
                    }                    
                    movsVentasDataModel.switchSortColumn(col);                    
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        jTableVentas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(1);
                }
            }
        });
        jTableVentas.updateUI();
        
         //Configuracion de movimientos de compra
        movsComprasDataModel = new MovsCVDataModel();
        movsComprasDataModel.setController(facturaController);
        jTableCompras.setModel(movsComprasDataModel);        
        jTableCompras.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableCompras.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsComprasDataModel.getColumnCount();i++){
                        jTableCompras.getColumnModel().getColumn(i).setHeaderValue( movsComprasDataModel.getColumnName(i) );
                    }                    
                    movsComprasDataModel.switchSortColumn(col);                    
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        jTableCompras.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(2);
                }
            }
        });
        jTableCompras.updateUI();
        
        //Configuracion de cuentas x cobrar
        movsCXCDataModel = new MovsCXCPDataModel(3);
        movsCXCDataModel.setController(facturaController);
        jTableCtasXCobrar.setModel(movsCXCDataModel);
        jTableCtasXCobrar.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableCtasXCobrar.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsCXCDataModel.getColumnCount();i++){
                        jTableCtasXCobrar.getColumnModel().getColumn(i).setHeaderValue( movsCXCDataModel.getColumnName(i) );
                    }                    
                    movsCXCDataModel.switchSortColumn(col);
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        jTableCtasXCobrar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(3);
                }
            }
        });
        jTableCtasXCobrar.updateUI();        
        
        //Configuracion de cuentas x pagar
        movsCXPDataModel = new MovsCXCPDataModel(4);
        movsCXPDataModel.setController(facturaController);
        jTableCtasXPagar.setModel(movsCXPDataModel);
        jTableCtasXPagar.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableCtasXPagar.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsCXPDataModel.getColumnCount();i++){
                        jTableCtasXPagar.getColumnModel().getColumn(i).setHeaderValue( movsCXPDataModel.getColumnName(i) );
                    }                    
                    movsCXPDataModel.switchSortColumn(col);
                }
                catch(Throwable ex){
                    JOptionPane.showMessageDialog(null, "Error en sort:"+ex.getMessage());
                    System.out.println("Error en sort:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        jTableCtasXPagar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(4);
                }
            }
        });
        jTableCtasXPagar.updateUI();
        
    }
    
    public void showDetallesFacturaFrame(Integer traCodigo){
        //System.out.println("Select action");
        int row = this.jTableVentas.getSelectedRow();
        if (traCodigo == 2){
            row = this.jTableCompras.getSelectedRow();
        }
        else if (traCodigo == 3){
            row = this.jTableCtasXCobrar.getSelectedRow();
        }
        else if (traCodigo == 4){
            row = this.jTableCtasXPagar.getSelectedRow();
        }
        
        if (row>-1){            
            if (traCodigo == 1 || traCodigo ==2){                
                FilaVenta filart = null;                
                if (traCodigo == 1){
                    filart = this.movsVentasDataModel.getValueAt(row);
                }
                else if (traCodigo == 2){
                    filart = this.movsComprasDataModel.getValueAt(row);
                }                
                DetallesFacturaFrame detallesFacturaFrame = new DetallesFacturaFrame(filart.getVentaId());            
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                detallesFacturaFrame.setLocation((dim.width/2)-(this.getSize().width/2), (dim.height/2)-(this.getSize().height/2));            
                detallesFacturaFrame.setSize(900, 500);
                detallesFacturaFrame.setVisible(true);
            }
            else if (traCodigo == 3 || traCodigo ==4){                
                FilaCXCP filaCXCP = null;
                if (traCodigo == 3){
                    filaCXCP = this.movsCXCDataModel.getValueAt(row);
                }
                else if (traCodigo == 4){
                    filaCXCP = this.movsCXPDataModel.getValueAt(row);
                }
                
                DetallesFacturaFrame detallesFacturaFrame = new DetallesFacturaFrame(filaCXCP.getCodFactura());
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                detallesFacturaFrame.setLocation((dim.width/2)-(this.getSize().width/2), (dim.height/2)-(this.getSize().height/2));            
                detallesFacturaFrame.setSize(900, 500);
                detallesFacturaFrame.setVisible(true);
            }
        }    
        else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar la factura");
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnCerrar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVentas = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalCreditoVTF = new javax.swing.JTextField();
        totalEfectivoVTF = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalCxCTF = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCtasXCobrar = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCompras = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        totalEfectivoCTF = new javax.swing.JTextField();
        totalCreditoCTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCtasXPagar = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        totalCxPTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        saldoCajaTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(918, 100));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel7.setText("MOVIMIENTOS DE CAJA");

        jPanel13.setLayout(new java.awt.GridLayout(2, 2));

        jLabel8.setText("Desde:");
        jPanel13.add(jLabel8);

        jLabel9.setText("Hasta:");
        jPanel13.add(jLabel9);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoy", "Ayer", "Esta Semana", "Este Mes", "Mes Anterior", "Este Año" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(728, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(35, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTableVentas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableVentas);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(443, 80));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setText("(+)TOTAL EFECTIVO:");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel2.setText("TOTAL CRÉDITO:");

        totalCreditoVTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalCreditoVTF.setEnabled(false);

        totalEfectivoVTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalEfectivoVTF.setForeground(new java.awt.Color(0, 204, 0));
        totalEfectivoVTF.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalEfectivoVTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalCreditoVTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalEfectivoVTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(totalCreditoVTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Cuentas x Cobrar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(458, 50));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("(+)TOTAL:");

        totalCxCTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalCxCTF.setForeground(new java.awt.Color(0, 204, 0));
        totalCxCTF.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalCxCTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 151, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalCxCTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel10, java.awt.BorderLayout.SOUTH);

        jTableCtasXCobrar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableCtasXCobrar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableCtasXCobrar);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(2, 1));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel7.setLayout(new java.awt.BorderLayout());

        jTableCompras.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableCompras);

        jPanel7.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel11.setPreferredSize(new java.awt.Dimension(458, 80));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setText("(-)TOTAL EFECTIVO:");

        totalEfectivoCTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalEfectivoCTF.setForeground(new java.awt.Color(255, 102, 0));
        totalEfectivoCTF.setEnabled(false);

        totalCreditoCTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalCreditoCTF.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel5.setText("TOTAL CRÉDITO:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalEfectivoCTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalCreditoCTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(totalEfectivoCTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(totalCreditoCTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel11, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Cuentas x Pagar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel8.setLayout(new java.awt.BorderLayout());

        jTableCtasXPagar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableCtasXPagar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTableCtasXPagar);

        jPanel8.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel12.setPreferredSize(new java.awt.Dimension(458, 50));

        totalCxPTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        totalCxPTF.setForeground(new java.awt.Color(255, 102, 0));
        totalCxPTF.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 0));
        jLabel6.setText("(-)TOTAL:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalCxPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 155, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalCxPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel12, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel8);

        jPanel2.add(jPanel4);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel14.setPreferredSize(new java.awt.Dimension(940, 35));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel10.setText("SALDO EN CAJA: (VENTAS+CUENTAS X COBRAR - COMPRAS - CUENTAS X PAGAR)");

        saldoCajaTF.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        saldoCajaTF.setEnabled(false);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saldoCajaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(saldoCajaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel14, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        Integer fechaIndex = this.jComboBox1.getSelectedIndex();
        switch (fechaIndex){
            case 0: //hoy
            {
                this.desdeTF.setText(FechasUtil.getFechaActual());
                this.hastaTF.setText(FechasUtil.getFechaActual());
                break;
            }
            case 1:{//ayer
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-1 );
                this.desdeTF.setText( FechasUtil.format(cal.getTime()) );
                this.hastaTF.setText( FechasUtil.format(cal.getTime()) );
                break;
            }
            case 2:{//Esta semana
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                this.desdeTF.setText( FechasUtil.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_WEEK, 6);
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                break;
            }
            case 3:{//Este mes
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                this.desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                this.hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
            case 4:{//MES ANTERIOR
                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                this.desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                this.hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
            case 5:{//ESTE AÑO
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_MONTH, 1);
                this.desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                this.hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
        }

        logicaBuscar();

    }//GEN-LAST:event_jComboBox1ActionPerformed

     public void updateLabelTotales(){
        TotalesVentasModel totalesVentasModel =  this.movsVentasDataModel.getTotalesVentasModel();
        if (totalesVentasModel != null){            
            totalEfectivoVTF.setText( totalesVentasModel.getSumaEfectivo().toPlainString() );
            totalCreditoVTF.setText( totalesVentasModel.getSumaCredito().toPlainString() );
        }
        
        TotalesVentasModel totalesComprasModel = this.movsComprasDataModel.getTotalesVentasModel();
        if (totalesComprasModel != null){            
            totalEfectivoCTF.setText( totalesComprasModel.getSumaEfectivo().toPlainString() );
            totalCreditoCTF.setText( totalesComprasModel.getSumaCredito().toPlainString() );
        }
        
        TotalesCuentasXPC totalesCuentasXC = this.movsCXCDataModel.getTotalesFactura();
        if (totalesCuentasXC != null){
            totalCxCTF.setText(totalesCuentasXC.getSumaMonto().toPlainString());
        }        
        
        TotalesCuentasXPC totalesCuentasXP = this.movsCXPDataModel.getTotalesFactura();
        if (totalesCuentasXP != null){
            totalCxPTF.setText(totalesCuentasXP.getSumaMonto().toPlainString());
        }
        
        BigDecimal saldoCaja = BigDecimal.ZERO;
         
        saldoCaja = totalesVentasModel.getSumaEfectivo().add(totalesCuentasXC.getSumaMonto()).subtract(totalesCuentasXP.getSumaMonto());
        
        this.saldoCajaTF.setText( NumbersUtil.round(saldoCaja, 2).toPlainString() );
        
    }
     
    public void logicaBuscar(){
        //System.out.println("logica buscar----->");
        try{
            Date desde = FechasUtil.parse(this.desdeTF.getText());
            Date hasta = FechasUtil.parse(this.hastaTF.getText());
            
            //Ventas
            ParamsBusquedaTransacc paramsVentas = new ParamsBusquedaTransacc();
            paramsVentas.setDesde(desde);
            paramsVentas.setHasta(hasta);
            paramsVentas.setTraCodigo(1);            
            paramsVentas.setArtId(0);
            paramsVentas.setCliId(0);
            
            movsVentasDataModel.setParams(paramsVentas);
            movsVentasDataModel.loadFromDataBase();
            jTableVentas.updateUI();
            movsVentasDataModel.fireTableDataChanged();            
            jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas("+movsVentasDataModel.getItems().size() +")" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
                        
            //Compras
            ParamsBusquedaTransacc paramsCompras = new ParamsBusquedaTransacc();
            paramsCompras.setDesde(desde);
            paramsCompras.setHasta(hasta);
            paramsCompras.setTraCodigo(2);
            paramsCompras.setArtId(0);
            paramsCompras.setCliId(0);
            
            movsComprasDataModel.setParams(paramsCompras);
            movsComprasDataModel.loadFromDataBase();
            jTableCompras.updateUI();
            movsComprasDataModel.fireTableDataChanged();            
            jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras("+movsComprasDataModel.getItems().size() +")" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            //Cuentas x cobrar
            ParamBusquedaCXCP paramsCXC = new ParamBusquedaCXCP();
            paramsCXC.setDesde(desde);
            paramsCXC.setHasta(hasta);
            paramsCXC.setTra_codigo(3);
            paramsCXC.setArtId(0);
            paramsCXC.setCliId(0);
            movsCXCDataModel.setParams(paramsCXC);
            movsCXCDataModel.loadFromDataBase();
            jTableCtasXCobrar.updateUI();
            movsCXCDataModel.fireTableDataChanged();
            jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Cuentas x Cobrar("+movsCXCDataModel.getItems().size() +")" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            //Cuentas x pagar
            ParamBusquedaCXCP paramsCXP = new ParamBusquedaCXCP();
            paramsCXP.setDesde(desde);
            paramsCXP.setHasta(hasta);
            paramsCXP.setTra_codigo(4);
            paramsCXP.setArtId(0);
            paramsCXP.setCliId(0);
            movsCXPDataModel.setParams(paramsCXP);
            movsCXPDataModel.loadFromDataBase();
            jTableCtasXPagar.updateUI();
            movsCXPDataModel.fireTableDataChanged();
            jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Cuentas x Pagar("+movsCXPDataModel.getItems().size() +")" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            updateLabelTotales();            
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        logicaBuscar();
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        SmartFactMain farmaApp = (SmartFactMain)this.root;
        farmaApp.logicaClosePane(this.getClass().getName());
    }//GEN-LAST:event_btnCerrarActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableCompras;
    private javax.swing.JTable jTableCtasXCobrar;
    private javax.swing.JTable jTableCtasXPagar;
    private javax.swing.JTable jTableVentas;
    private javax.swing.JTextField saldoCajaTF;
    private javax.swing.JTextField totalCreditoCTF;
    private javax.swing.JTextField totalCreditoVTF;
    private javax.swing.JTextField totalCxCTF;
    private javax.swing.JTextField totalCxPTF;
    private javax.swing.JTextField totalEfectivoCTF;
    private javax.swing.JTextField totalEfectivoVTF;
    // End of variables declaration//GEN-END:variables
}
