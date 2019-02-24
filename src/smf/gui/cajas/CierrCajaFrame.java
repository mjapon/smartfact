/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.cajas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import smf.controller.CajaJpaController;
import smf.controller.FacturasJpaController;
import smf.entity.Caja;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.gui.facte.DetallesFacturaFrame;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.ParamBusquedaCXCP;
import smf.util.ParamsBusquedaTransacc;
import smf.util.SelectingEditor;
import smf.util.datamodels.CierreCajaDM;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.MovsCVDataModel;
import smf.util.datamodels.MovsCXCPDataModel;
import smf.util.datamodels.rows.FilaCXCP;
import smf.util.datamodels.rows.FilaDatosCaja;
import smf.util.datamodels.rows.FilaVenta;

/**
 *
 * @author mjapon
 */
public class CierrCajaFrame extends BaseFrame {

    private FacturasJpaController facturaController;
    private CajaJpaController cajaJpaController;
    
    private MovsCVDataModel movsVentasDataModel;    
    private MovsCVDataModel movsComprasDataModel;
    private MovsCXCPDataModel movsCXCDataModel;
    private MovsCXCPDataModel movsCXPDataModel;
    
    private CierreCajaDM cierreCajaDM;
    
    private Integer idCajaSel;    
    
    private List<JTableColumn> columns;
    
    /**
     * Creates new form CierrCajaFrame
     */
    public CierrCajaFrame(Integer idCajaSel) {
        initComponents();
        
        this.idCajaSel = idCajaSel;
        facturaController = new FacturasJpaController(em);
        cajaJpaController = new CajaJpaController(em);
        
        setupTableVentas();
        setupTableCompras();
        setupTableAbonosCobrados();
        setupTableAbonosPagados();
        
        columns = new ArrayList<>();
        
        setupTableResumenDia();
        cierreCajaDM = new CierreCajaDM(columns, cajaJpaController);
        jTableResumenDia.setModel(cierreCajaDM);
        
        
        TableColumn colMonto = jTableResumenDia.getColumnModel().getColumn( 6 );
        SelectingEditor selectingEditor = new SelectingEditor(new JTextField());
        colMonto.setCellEditor(selectingEditor);        
    }
    
    public void setupTableVentas(){
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
    }
    
    public void setupTableCompras(){
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
        
    }
    
    public void setupTableAbonosCobrados(){
        movsCXCDataModel = new MovsCXCPDataModel(3);
        movsCXCDataModel.setController(facturaController);
        jTableCxC.setModel(movsCXCDataModel);
        jTableCxC.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableCxC.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsCXCDataModel.getColumnCount();i++){
                        jTableCxC.getColumnModel().getColumn(i).setHeaderValue( movsCXCDataModel.getColumnName(i) );
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
        jTableCxC.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(3);
                }
            }
        });
        jTableCxC.updateUI();        
    }
    
    public void setupTableAbonosPagados(){
        
        //Configuracion de cuentas x pagar
        movsCXPDataModel = new MovsCXCPDataModel(4);
        movsCXPDataModel.setController(facturaController);
        jTableCxP.setModel(movsCXPDataModel);
        jTableCxP.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableCxP.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<movsCXPDataModel.getColumnCount();i++){
                        jTableCxP.getColumnModel().getColumn(i).setHeaderValue( movsCXPDataModel.getColumnName(i) );
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
        jTableCxP.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    showDetallesFacturaFrame(4);
                }
            }
        });
        jTableCxP.updateUI();
        
    }    
    
    public void showDetallesFacturaFrame(Integer traCodigo){
        System.out.println("Select action");
        int row = this.jTableVentas.getSelectedRow();
        if (traCodigo == 2){
            row = this.jTableCompras.getSelectedRow();
        }
        else if (traCodigo == 3){
            row = this.jTableCxC.getSelectedRow();
        }
        else if (traCodigo == 4){
            row = this.jTableCxP.getSelectedRow();
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
    
    public void setupTableResumenDia(){
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        0, 
                        "CAJA", 
                        "caja", 
                        String.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getNombreCaja();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        1, 
                        "SALDO INICIAL(+)", 
                        "saldoInicial", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return NumbersUtil.round2(row.getSaldoInicial() );
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        2, 
                        "VENTAS(+)", 
                        "ventas", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getTotalVentas();
                            }
                        }
                )
        );
        
         columns.add(
                new JTableColumn<FilaDatosCaja>(
                        3, 
                        "COMPRAS(-)", 
                        "compras", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getTotalCompras();
                            }
                        }
                )
        );
         
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        4, 
                        "ABN COBRADOS(+)", 
                        "abocob", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getTotalAbCobrados();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        5, 
                        "ABN PAGADOS(-)", 
                        "abopag", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getTotalAbPagados();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        6, 
                        "AJUSTE(+/-)", 
                        "ajuste", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getValorAjuste();
                            }

                            @Override
                            public boolean isCellEditable(FilaDatosCaja row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(FilaDatosCaja row, int rowIndex, Object value) {
                                try{
                                    row.setValorAjuste(new BigDecimal(value.toString()));
                                    BigDecimal saldoFinal = row.getSaldoInicial()
                                                            .add(row.getTotalVentas())
                                                            .subtract(row.getTotalCompras())
                                                            .add(row.getTotalAbCobrados())
                                                            .subtract(row.getTotalAbPagados());
                                    
                                    BigDecimal newSaldoFinal = saldoFinal.add(row.getValorAjuste());
                                    row.setSaldoFinal( newSaldoFinal );
                                    
                                    cierreCajaDM.fireTableDataChanged();                                    
                                }
                                catch(Throwable ex){
                                    row.setValorAjuste(BigDecimal.ZERO);
                                    showMsgError(ex);
                                }
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaDatosCaja>(
                        7, 
                        "TOTAL", 
                        "total", 
                        BigDecimal.class,
                        new DefGSVCol<FilaDatosCaja>() {
                            public Object getValueAt(FilaDatosCaja row, int rowIndex) {
                                return row.getSaldoFinal();
                            }
                        }
                )
        );
        
    }
    
    public void loadLastOpenedCaja(){
        if (this.idCajaSel == null || this.idCajaSel == 0){
            Caja theCaja = cajaJpaController.getLastOpenedCaja();
            if (theCaja != null){
                this.idCajaSel = theCaja.getCjId();
                loadDatosCaja(theCaja);
            }
            else{
                showMsg(" No existe apertura de caja, primero debe apertura la caja para poder cerrarla!!! ");
                setVisible(false);
            }
        }
    }
    
    public void loadDatosCaja(Caja caja){
        try{
            Date desde = caja.getCjFecaper();
            Date hasta = new Date();
            if (idCajaSel > 0){
                hasta = caja.getCjFeccierre();
            }            
            
            if (caja.getCjEstado() == 0){
                jBtnCerrarCaja.setEnabled(true);
                jLabelFC.setVisible(false);
                jTFFechaCierre.setVisible(false);
            }
            else{
                jBtnCerrarCaja.setEnabled(false);
                jLabelFC.setVisible(true);
                jTFFechaCierre.setVisible(true);
                jTFFechaCierre.setText( FechasUtil.formatDateHour(hasta) );
                
            }
            String estado = "ABIERTO";
            switch(caja.getCjEstado()){
                case 0:{estado="ABIERTO"; break;}
                case 1:{estado="CERRADO"; break;}
                case 2:{estado="ANULADO"; break;}
                default:{estado="";break;}
            }
            
            jTFEstado.setText( estado );
            
            //Load ventas
            ParamsBusquedaTransacc paramsVentas = new ParamsBusquedaTransacc();
            paramsVentas.initForTransacc(desde, hasta, 1);
            movsVentasDataModel.setParams(paramsVentas);
            movsVentasDataModel.loadFromDataBase();
            Map<Integer, BigDecimal> mapVentasByCaja = movsVentasDataModel.getTotalesVentasModel().getSumasCajaMap(); 
            
            jTableVentas.updateUI();
            jPanelVentas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas("+movsVentasDataModel.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            //Load compras
            ParamsBusquedaTransacc paramsCompras = new ParamsBusquedaTransacc();
            paramsCompras.initForTransacc(desde, hasta, 2);
            movsComprasDataModel.setParams(paramsCompras);
            movsComprasDataModel.loadFromDataBase();
            Map<Integer, BigDecimal> mapComprasByCaja = movsComprasDataModel.getTotalesVentasModel().getSumasCajaMap(); 
            jTableCompras.updateUI();
            jPanelCompras.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras("+movsComprasDataModel.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            //Load abonos cobrados
            ParamBusquedaCXCP paramsCXC = new ParamBusquedaCXCP();
            paramsCXC.initForTransacc(desde, hasta, 3);
            movsCXCDataModel.setParams(paramsCXC);
            movsCXCDataModel.loadFromDataBase();
            Map<Integer, BigDecimal> mapSumaMovsCXCByCaja = movsCXCDataModel.getTotalesFactura().getTotalByCajaMap();
            jTableCxC.updateUI();            
            jPanelCXC.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Cobrados("+movsCXCDataModel.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            //load abonos pagados
            ParamBusquedaCXCP paramsCXP = new ParamBusquedaCXCP();
            paramsCXP.initForTransacc(desde, hasta, 4);
            movsCXPDataModel.setParams(paramsCXP);
            movsCXPDataModel.loadFromDataBase();
            Map<Integer, BigDecimal> mapSumaMovsCXPByCaja = movsCXPDataModel.getTotalesFactura().getTotalByCajaMap();
            jTableCxP.updateUI();
            jPanelCxP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos Pagados("+movsCXPDataModel.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
            cierreCajaDM.setCaja(caja);
            cierreCajaDM.setTotalesVentas(movsVentasDataModel.getTotalesVentasModel());
            cierreCajaDM.setTotalesCompras(movsComprasDataModel.getTotalesVentasModel());
            cierreCajaDM.setTotalesCXC(movsCXCDataModel.getTotalesFactura());
            cierreCajaDM.setTotalesCXP(movsCXPDataModel.getTotalesFactura());
            
            jTFTotalVGrid.setText( NumbersUtil.round2ToStr( movsVentasDataModel.getTotalesVentasModel().getSumaEfectivo() ) );
            jTFTotalCGrid.setText( NumbersUtil.round2ToStr( movsComprasDataModel.getTotalesVentasModel().getSumaEfectivo() ) );
            
            jTFTotalACGrid.setText( NumbersUtil.round2ToStr( movsCXCDataModel.getTotalesFactura().getSumaMonto() ) );
            jTFTotalAPGrid.setText( NumbersUtil.round2ToStr( movsCXPDataModel.getTotalesFactura().getSumaMonto() ) );
            
            cierreCajaDM.loadFromDataBase();
            jTableResumenDia.updateUI();
            
            jTFFechaApertura.setText( FechasUtil.formatDateHour(caja.getCjFecaper()) );
            jTAObsApertura.setText( caja.getCjObsaper() );
            jTFNroCaja.setText( caja.getCjId().toString() );
            
        }
        catch(Throwable ex){
            showMsgError(ex);
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
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableResumenDia = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanelVentas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVentas = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTFTotalVGrid = new javax.swing.JTextField();
        jPanelCompras = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableCompras = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTFTotalCGrid = new javax.swing.JTextField();
        jPanelCXC = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCxC = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTFTotalACGrid = new javax.swing.JTextField();
        jPanelCxP = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCxP = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTFTotalAPGrid = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTFNroCaja = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTFEstado = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTFFechaApertura = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabelFC = new javax.swing.JLabel();
        jTFFechaCierre = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTAObsApertura = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTAObsCierre = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonUpdVal = new javax.swing.JButton();
        jBtnCerrarCaja = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SmartFact: Cierre de Caja");
        setMinimumSize(new java.awt.Dimension(266, 200));
        setPreferredSize(new java.awt.Dimension(1100, 800));
        setSize(new java.awt.Dimension(266, 160));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(685, 120));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen del Día", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13))); // NOI18N

        jTableResumenDia.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableResumenDia.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableResumenDia.setRowHeight(28);
        jScrollPane4.setViewportView(jTableResumenDia);

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel7.setLayout(new java.awt.GridLayout(2, 2));

        jPanelVentas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas del día", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13))); // NOI18N
        jPanelVentas.setPreferredSize(new java.awt.Dimension(454, 500));
        jPanelVentas.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(454, 200));

        jTableVentas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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

        jPanelVentas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel11.setText("TOTAL EFECTIVO:");
        jPanel9.add(jLabel11);

        jTFTotalVGrid.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalVGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalVGridActionPerformed(evt);
            }
        });
        jPanel9.add(jTFTotalVGrid);

        jPanelVentas.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanelVentas);

        jPanelCompras.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras del día", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13))); // NOI18N
        jPanelCompras.setPreferredSize(new java.awt.Dimension(454, 500));
        jPanelCompras.setLayout(new java.awt.BorderLayout());

        jScrollPane7.setPreferredSize(new java.awt.Dimension(454, 200));

        jTableCompras.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
        jScrollPane7.setViewportView(jTableCompras);

        jPanelCompras.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel14.setText("TOTAL EFECTIVO:");
        jPanel14.add(jLabel14);

        jTFTotalCGrid.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalCGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalCGridActionPerformed(evt);
            }
        });
        jPanel14.add(jTFTotalCGrid);

        jPanelCompras.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanelCompras);

        jPanelCXC.setBorder(javax.swing.BorderFactory.createTitledBorder("Abonos Cobrados"));
        jPanelCXC.setPreferredSize(new java.awt.Dimension(454, 200));
        jPanelCXC.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(454, 200));

        jTableCxC.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableCxC.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableCxC);

        jPanelCXC.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel12.setText("TOTAL:");
        jPanel11.add(jLabel12);

        jTFTotalACGrid.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalACGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalACGridActionPerformed(evt);
            }
        });
        jPanel11.add(jTFTotalACGrid);

        jPanelCXC.add(jPanel11, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanelCXC);

        jPanelCxP.setBorder(javax.swing.BorderFactory.createTitledBorder("Abonos Pagados"));
        jPanelCxP.setPreferredSize(new java.awt.Dimension(454, 500));
        jPanelCxP.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(454, 200));

        jTableCxP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableCxP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableCxP);

        jPanelCxP.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel13.setText("TOTAL:");
        jPanel12.add(jLabel13);

        jTFTotalAPGrid.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalAPGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalAPGridActionPerformed(evt);
            }
        });
        jPanel12.add(jTFTotalAPGrid);

        jPanelCxP.add(jPanel12, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanelCxP);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(0, 130));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel18.setLayout(new java.awt.GridLayout(2, 2));

        jPanel8.setLayout(new java.awt.GridLayout(1, 2));

        jLabel3.setText("Nro Caja:");
        jPanel8.add(jLabel3);

        jTFNroCaja.setEditable(false);
        jPanel8.add(jTFNroCaja);

        jPanel18.add(jPanel8);

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        jLabel6.setText("Estado:");
        jPanel10.add(jLabel6);

        jTFEstado.setEditable(false);
        jPanel10.add(jTFEstado);

        jPanel18.add(jPanel10);

        jPanel13.setLayout(new java.awt.GridLayout(1, 2));

        jLabel2.setText("Fecha de Apertura:");
        jPanel13.add(jLabel2);

        jTFFechaApertura.setEditable(false);
        jPanel13.add(jTFFechaApertura);

        jPanel18.add(jPanel13);

        jPanel15.setLayout(new java.awt.GridLayout(1, 2));

        jLabelFC.setText("Fecha de Cierre:");
        jPanel15.add(jLabelFC);

        jTFFechaCierre.setEditable(false);
        jPanel15.add(jTFFechaCierre);

        jPanel18.add(jPanel15);

        jPanel6.add(jPanel18, java.awt.BorderLayout.NORTH);

        jPanel19.setLayout(new java.awt.GridLayout(1, 2));

        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Obs Apertura:");
        jPanel16.add(jLabel4, java.awt.BorderLayout.NORTH);

        jTAObsApertura.setEditable(false);
        jTAObsApertura.setColumns(20);
        jTAObsApertura.setRows(5);
        jTAObsApertura.setEnabled(false);
        jScrollPane5.setViewportView(jTAObsApertura);

        jPanel16.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel16);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel5.setText("Obs Cierre:");
        jPanel17.add(jLabel5, java.awt.BorderLayout.NORTH);

        jTAObsCierre.setColumns(20);
        jTAObsCierre.setRows(5);
        jScrollPane6.setViewportView(jTAObsCierre);

        jPanel17.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel17);

        jPanel6.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("CIERRE DE CAJA");
        jPanel2.add(jLabel1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setMinimumSize(new java.awt.Dimension(100, 264));
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 0));
        jPanel3.setLayout(new java.awt.GridLayout(6, 1));

        jButtonUpdVal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButtonUpdVal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-connection_sync.png"))); // NOI18N
        jButtonUpdVal.setText("Actualizar");
        jButtonUpdVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdValActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonUpdVal);

        jBtnCerrarCaja.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jBtnCerrarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Save_20px.png"))); // NOI18N
        jBtnCerrarCaja.setText("Cerrar Caja");
        jBtnCerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCerrarCajaActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnCerrarCaja);

        jButtonCerrar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButtonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButtonCerrar.setText("Cancelar");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonCerrar);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUpdValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdValActionPerformed
        try{
            Caja caja = cajaJpaController.findById(this.idCajaSel);
            loadDatosCaja(caja);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }//GEN-LAST:event_jButtonUpdValActionPerformed
    
    public void loadDatosCajaId(){
         Caja caja = cajaJpaController.findById(this.idCajaSel);
         loadDatosCaja(caja);
    }
    
    private void jBtnCerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCerrarCajaActionPerformed
        try{
            if (showConfirmMsg("¿Esta seguro, ya no podra realizar ventas?")){
                cajaJpaController.cerrarCaja(this.idCajaSel, jTAObsCierre.getText(), cierreCajaDM.getItems());
                SmartFactMain.showSystemTrayMsg("La caja a sido cerrada exitosamente");
                setVisible(false);
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }

    }//GEN-LAST:event_jBtnCerrarCajaActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jTFTotalVGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalVGridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalVGridActionPerformed

    private void jTFTotalACGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalACGridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalACGridActionPerformed

    private void jTFTotalAPGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalAPGridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalAPGridActionPerformed

    private void jTFTotalCGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalCGridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalCGridActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCerrarCaja;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JButton jButtonUpdVal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelFC;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelCXC;
    private javax.swing.JPanel jPanelCompras;
    private javax.swing.JPanel jPanelCxP;
    private javax.swing.JPanel jPanelVentas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTAObsApertura;
    private javax.swing.JTextArea jTAObsCierre;
    private javax.swing.JTextField jTFEstado;
    private javax.swing.JTextField jTFFechaApertura;
    private javax.swing.JTextField jTFFechaCierre;
    private javax.swing.JTextField jTFNroCaja;
    private javax.swing.JTextField jTFTotalACGrid;
    private javax.swing.JTextField jTFTotalAPGrid;
    private javax.swing.JTextField jTFTotalCGrid;
    private javax.swing.JTextField jTFTotalVGrid;
    private javax.swing.JTable jTableCompras;
    private javax.swing.JTable jTableCxC;
    private javax.swing.JTable jTableCxP;
    private javax.swing.JTable jTableResumenDia;
    private javax.swing.JTable jTableVentas;
    // End of variables declaration//GEN-END:variables
}
