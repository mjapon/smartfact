/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.merc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import smf.controller.ArticulosJpaController;
import smf.controller.CategoriasJpaController;
import smf.controller.ClientesJpaController;
import smf.controller.KardexArtCntrl;
import smf.entity.Articulos;
import smf.entity.Categorias;
import smf.entity.Clientes;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.gui.merc.unid.PreciosXUnidadFrame;
import smf.util.ArrayUtil;
import smf.util.CtesU;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.SelectingEditor;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.datamodels.rows.CatRow;
import smf.util.datamodels.CatsListModel;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.KardexArtDM;
import smf.util.datamodels.MercaderiaDataModel;
import smf.util.datamodels.ProveedoresListModel;
import smf.util.datamodels.TotalesMercaderia;
import smf.util.datamodels.rows.FilaKardexArt;
import smf.util.datamodels.rows.ProveedorRow;

/**
 *
 * @author manuel.japon
 */
public class MercaderiaFrame extends BaseFrame implements ParentNewArtFrame {
    
    private MercaderiaDataModel mercaderiaDataModel;
    private ArticulosJpaController articulosController;
    private CategoriasJpaController catsController;
    private KardexArtCntrl kardexArtCntrl;
    private List<Categorias> categoriasList;
    private List<CatRow> catsList;
    private CatsListModel catsModelList;
    private ProveedoresListModel provsListModel;
    private ClientesJpaController cliCntrl;
    private Integer selectedCatIndex;    
    private List<ProveedorRow> proveedoresList;
    private String[] catsArray;
    private Articulos artEdited;
    
    private List<JTableColumn> colsKardex;
    private KardexArtDM datamodel;
    /**
     * Creates new form MercaderiaFrame
     */
    public MercaderiaFrame() {
        super();
        initComponents();
        
        articulosController = new ArticulosJpaController(em);
        catsController = new CategoriasJpaController(em);
        cliCntrl = new ClientesJpaController(em);
        kardexArtCntrl = new KardexArtCntrl(em);
        
        mercaderiaDataModel = new MercaderiaDataModel();
        mercaderiaDataModel.setController(articulosController);
        
        jTableArts.setModel(mercaderiaDataModel);
                
        jTableArts.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableArts.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<mercaderiaDataModel.getColumnCount();i++){
                        jTableArts.getColumnModel().getColumn(i).setHeaderValue( mercaderiaDataModel.getColumnName(i) );
                    }                    
                    mercaderiaDataModel.switchSortColumn(col);
                }
                catch(Throwable ex){
                    showMsgError(ex);
                }
            }
        });
        
        jTableArts.getModel().addTableModelListener( new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateTotales();
                clearForms();
            }
        } );
        
        jTableArts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean enabled = e.getFirstIndex()>=0;
                btnBorrar.setEnabled(enabled);
                jMoverBtn.setEnabled(enabled);
                jBtnPrecios.setEnabled(enabled);
                //Cargar detalles del articulo
                try{
                    int row = jTableArts.getSelectedRow();
                    if (row>-1){
                    FilaArticulo filart = mercaderiaDataModel.getValueAt(row);
                        if (filart!=null){
                            loadDetallesArt(filart.getArtId());
                        }
                    }
                }
                catch(Throwable ex){
                    showMsgError(ex);
                }
            }
        });
        
        SelectingEditor selectingEditor = new SelectingEditor(new JTextField());
        //2,3,4,5,7
        jTableArts.getColumnModel().getColumn(2).setCellEditor(selectingEditor);
        jTableArts.getColumnModel().getColumn(3).setCellEditor(selectingEditor);
        jTableArts.getColumnModel().getColumn(4).setCellEditor(selectingEditor);
        jTableArts.getColumnModel().getColumn(5).setCellEditor(selectingEditor);
        jTableArts.getColumnModel().getColumn(7).setCellEditor(selectingEditor);
        
        jListCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jListCategorias.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 1) {
                    try{
                        int index = list.locationToIndex(evt.getPoint());
                        selectedCatIndex =  index;
                        CatRow selectedCat = catsList.get(index);
                        jEditCatBtn.setEnabled(selectedCat.getCatId()>0);
                        jDeleteCatBtn.setEnabled(selectedCat.getCatId()>0);
                        mercaderiaDataModel.loadFromDataBaseCat(selectedCat.getCatId());
                        updateTotales();
                        updateLabelBorder();
                    }
                    catch(Throwable ex){
                        showMsgError(ex);
                    }
                }
            }
        });
        
        jListProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 1) {
                    try{
                        int index = list.locationToIndex(evt.getPoint());
                        ProveedorRow selectedProv = proveedoresList.get(index);
                        mercaderiaDataModel.loadFromDataBaseProv(selectedProv.getProvId());
                        updateTotales();
                        updateLabelBorder();
                    }
                    catch(Throwable ex){
                        showMsgError(ex);
                    }
                }
            }
        });
        
        loadCats();
        loadProveedores();
        initColsKardex();
    }
    
    public void updateTotales(){
        TotalesMercaderia totalesMerc = mercaderiaDataModel.getTotales();
        jTFTotalPC.setText(totalesMerc.getSumaPrecioCompra().toPlainString());
        jTFTotalPV.setText(totalesMerc.getSumaPrecioVenta().toPlainString());
        jTFTotalPM.setText(totalesMerc.getSumaPrecioVentaMin().toPlainString());
        jTFTotalInv.setText(totalesMerc.getSumaInv().toPlainString());
    }
    
    public void loadArtsCaducados(){
        try{
            mercaderiaDataModel.loadFromDbCaducados();            
            updateTotales();
            btnBorrar.setEnabled(false);
            jMoverBtn.setEnabled(false);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void reloadArts(){
        try{
            mercaderiaDataModel.loadFromDataBase();            
            updateTotales();
            btnBorrar.setEnabled(false);
            jMoverBtn.setEnabled(false);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void updateLabelEstado(String label){
        this.jStatusLabel.setText(label);
    }
    
    public void filtroFocus(){
        SwingUtilities.invokeLater(() -> {
            this.filtroTF.requestFocus();
        });
    }
    
    public void updateLabelBorder(){
        if (mercaderiaDataModel.getItems() != null){
            jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                String.valueOf( "Nro Registros("+ mercaderiaDataModel.getItems().size() + ")" )
            ));
        }
    }
    
    public void updateArticulos(){
        try{
            mercaderiaDataModel.loadFromDataBase();
            updateTotales();
            updateLabelBorder();
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        mercaderiaDataModel.fireTableDataChanged();
    }
    
    public void doFilter(){
        String filtro = this.filtroTF.getText().trim();
        if (filtro.length()>0){
            try{
                mercaderiaDataModel.loadFromDataBaseFilter(filtro);
                updateTotales();
                updateLabelBorder();
            }
            catch(Throwable ex){
                showMsgError(ex);
            }
        }
    }
    
    @Override
    public void afterSuccessfulSaved() {
        updateArticulos();
    }

    @Override
    public void afterClose() {
        //Do nothing
    }

    @Override
    public boolean validateNewRow(FilaArticulo filaArt) {
        return mercaderiaDataModel.validar(filaArt);
    }   
    
    public void logicaSaveArtEdited(){
        try{
            Integer artId = artEdited.getArtId();
            String codBarra =  jTFCodBarra.getText();
            String nombre = jTFNombre.getText();
            
            BigDecimal valorIvaPC = BigDecimal.ZERO;
            BigDecimal precioCompra = new BigDecimal(this.jTFPrecioCompra.getText());
            boolean isIvaTrue = jCBIva.isSelected();
            
            if (isIvaTrue){
                valorIvaPC = precioCompra.multiply(new BigDecimal(CtesU.IVA)).setScale(2, RoundingMode.HALF_UP);
            }
            
            BigDecimal precioCompraIva = new BigDecimal(jTFPrecioCompra.getText().trim()).add(valorIvaPC);
            BigDecimal precioCompraConIva = precioCompraIva;
            BigDecimal precioVentaConIva = new BigDecimal(jTFPrecioVenta.getText().trim());
            BigDecimal precioMinConIva = new BigDecimal(jTFPrecioMinimo.getText().trim());
            BigDecimal inventario = new BigDecimal(jTFInventario.getText().trim());

            boolean genCodbar = false;
            if (genCodbar){
                codBarra = "GEN";
            }
            
            String tipo = Character.toString(jCBTipo.getSelectedItem().toString().charAt(0));
            Categorias selectedCat = categoriasList.get(jCBCategoria.getSelectedIndex());            
            
            FilaArticulo auxfilaArt = new FilaArticulo(artId, codBarra, nombre, 
                    precioCompraConIva, precioVentaConIva, precioMinConIva, 
                    isIvaTrue, inventario, selectedCat.getCatName(), tipo, 
                    selectedCat.getCatId(), selectedCat.getCatCaja());
            
            ProveedorRow proveedor = (ProveedorRow)jCBProveedor.getSelectedItem();
            if (proveedor != null){
                auxfilaArt.setProveedorId(proveedor.getProvId());
            }
            
            FilaArticulo filaArt  = new FilaArticulo(artId,
                    codBarra, 
                    nombre,
                    auxfilaArt.getPrecioCompraSinIva(),
                    auxfilaArt.getPrecioVentaSinIva(),
                    auxfilaArt.getPrecioMinSinIva(),
                    isIvaTrue,
                    inventario, selectedCat.getCatName(),tipo,
                    selectedCat.getCatId(), selectedCat.getCatCaja());
            
            filaArt.setProveedorId(auxfilaArt.getProveedorId());

            boolean valid = validateNewRow(filaArt);
            if (valid){
                //Verificar codigo de barra y nombre de articulo
                /*if (articulosController.yaExisteBarcode(codBarra)){
                    showMsg("El código de barra:"+codBarra+" ya está registrado");
                    return;
                }
                else*/ if (!nombre.trim().equalsIgnoreCase(artEdited.getArtNombre().trim()) && articulosController.yaExisteNombre(nombre)){
                    showMsg("El artículo de nombre:"+nombre+" ya está registrado");
                    return;
                }
                
                if (this.jTFFechaCadu.getText().trim().length()>0){
                    filaArt.setFechaCaducidad(this.jTFFechaCadu.getText().trim());
                }
                
                articulosController.actualizarArticuloForEdit(filaArt);
                
                SmartFactMain.showSystemTrayMsg("El artículo fue registrado exitosamente");
                afterSuccessfulSaved();
            }
            else{
                showMsg("Los datos son incorrectos, verifica el codigo de barra, nombre del articulo ..");
            }
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

        jPanelNorth = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelCenter = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jEditCatBtn = new javax.swing.JButton();
        jDeleteCatBtn = new javax.swing.JButton();
        jCrearCatBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListCategorias = new javax.swing.JList<>();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListProveedores = new javax.swing.JList<>();
        jBtnCaducados = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        filtroTF = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableArts = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTFTotalPC = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTFTotalPV = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTFTotalPM = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTFTotalInv = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelDetArt = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jCBTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTFCodBarra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jCBCategoria = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jCBProveedor = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jCBIva = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFPrecioVenta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFPrecioMinimo = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTFPrecioCompra = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTFInventario = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTFFechaCadu = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jButtonSaveArt = new javax.swing.JButton();
        jPanelKardexArt = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanelBtns = new javax.swing.JPanel();
        jCrearArtBtn = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jMoverBtn = new javax.swing.JButton();
        jBtnPrecios = new javax.swing.JButton();
        jCerrarBtn = new javax.swing.JButton();
        jPanelSouth = new javax.swing.JPanel();
        jStatusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelNorth.setPreferredSize(new java.awt.Dimension(100, 35));
        jPanelNorth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("MERCADERÍA");
        jPanelNorth.add(jLabel1);

        getContentPane().add(jPanelNorth, java.awt.BorderLayout.NORTH);

        jPanelCenter.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(150, 504));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Categorías");
        jPanel4.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanel6.setMinimumSize(new java.awt.Dimension(80, 33));
        jPanel6.setPreferredSize(new java.awt.Dimension(80, 33));
        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        jEditCatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/edit_25px.png"))); // NOI18N
        jEditCatBtn.setToolTipText("Crear nueva categoría");
        jEditCatBtn.setEnabled(false);
        jEditCatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditCatBtnActionPerformed(evt);
            }
        });
        jPanel6.add(jEditCatBtn);

        jDeleteCatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Trash_25px.png"))); // NOI18N
        jDeleteCatBtn.setToolTipText("Crear nueva categoría");
        jDeleteCatBtn.setEnabled(false);
        jDeleteCatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteCatBtnActionPerformed(evt);
            }
        });
        jPanel6.add(jDeleteCatBtn);

        jCrearCatBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_25px.png"))); // NOI18N
        jCrearCatBtn.setToolTipText("Crear nueva categoría");
        jCrearCatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCrearCatBtnActionPerformed(evt);
            }
        });
        jPanel6.add(jCrearCatBtn);

        jPanel4.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel13.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jListCategorias.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jListCategorias);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel13);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Proveedores");
        jPanel15.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jListProveedores.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(jListProveedores);

        jPanel17.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel17, java.awt.BorderLayout.CENTER);

        jBtnCaducados.setText("Caducados");
        jBtnCaducados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCaducadosActionPerformed(evt);
            }
        });
        jPanel14.add(jBtnCaducados, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel14);

        jPanelCenter.add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        filtroTF.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        filtroTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroTFActionPerformed(evt);
            }
        });
        filtroTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtroTFKeyReleased(evt);
            }
        });
        jPanel3.add(filtroTF, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel16.setLayout(new java.awt.BorderLayout());

        jTableArts.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTableArts.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableArts.setRowHeight(25);
        jScrollPane2.setViewportView(jTableArts);

        jPanel16.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.GridLayout(1, 1));

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel12.setText("TOTAL PRECIO COMPRA:");
        jPanel9.add(jLabel12);

        jTFTotalPC.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalPCActionPerformed(evt);
            }
        });
        jPanel9.add(jTFTotalPC);

        jPanel8.add(jPanel9);

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel13.setText("TOTAL PRECIO VENTA:");
        jPanel10.add(jLabel13);

        jTFTotalPV.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalPV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalPVActionPerformed(evt);
            }
        });
        jPanel10.add(jTFTotalPV);

        jPanel8.add(jPanel10);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel14.setText("TOTAL PRECIO MIN:");
        jPanel11.add(jLabel14);

        jTFTotalPM.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalPMActionPerformed(evt);
            }
        });
        jPanel11.add(jTFTotalPM);

        jPanel8.add(jPanel11);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel15.setText("TOTAL INV:");
        jPanel12.add(jLabel15);

        jTFTotalInv.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTFTotalInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFTotalInvActionPerformed(evt);
            }
        });
        jPanel12.add(jTFTotalInv);

        jPanel8.add(jPanel12);

        jPanel16.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel7.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel18.setPreferredSize(new java.awt.Dimension(5, 200));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanelDetArt.setLayout(new java.awt.GridLayout(1, 3, 60, 40));

        jPanel20.setLayout(new java.awt.GridLayout(4, 2));

        jLabel10.setText("Tipo:");
        jPanel20.add(jLabel10);

        jCBTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BIEN", "SERVICIO" }));
        jCBTipo.setEnabled(false);
        jCBTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTipoActionPerformed(evt);
            }
        });
        jPanel20.add(jCBTipo);

        jLabel4.setText("Código:");
        jPanel20.add(jLabel4);

        jTFCodBarra.setEditable(false);
        jTFCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFCodBarraActionPerformed(evt);
            }
        });
        jTFCodBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFCodBarraKeyPressed(evt);
            }
        });
        jPanel20.add(jTFCodBarra);

        jLabel8.setText("Categoría:");
        jPanel20.add(jLabel8);

        jCBCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        jPanel20.add(jCBCategoria);

        jLabel11.setText("Proveedor:");
        jPanel20.add(jLabel11);

        jPanel20.add(jCBProveedor);

        jPanelDetArt.add(jPanel20);

        jPanel21.setLayout(new java.awt.GridLayout(4, 2));

        jLabel7.setText("Graba IVA:");
        jPanel21.add(jLabel7);
        jPanel21.add(jCBIva);

        jLabel5.setText("Nombre:");
        jPanel21.add(jLabel5);
        jPanel21.add(jTFNombre);

        jLabel6.setText("Precio Venta:");
        jPanel21.add(jLabel6);

        jTFPrecioVenta.setEditable(false);
        jPanel21.add(jTFPrecioVenta);

        jLabel9.setText("Precio Venta Mínimo:");
        jPanel21.add(jLabel9);

        jTFPrecioMinimo.setEditable(false);
        jPanel21.add(jTFPrecioMinimo);

        jPanelDetArt.add(jPanel21);

        jPanel22.setLayout(new java.awt.GridLayout(4, 2));

        jLabel16.setText("Precio Compra (sin iva):");
        jPanel22.add(jLabel16);

        jTFPrecioCompra.setEditable(false);
        jPanel22.add(jTFPrecioCompra);

        jLabel17.setText("Inventario Actual");
        jPanel22.add(jLabel17);
        jPanel22.add(jTFInventario);

        jLabel18.setText("Fecha Caducidad (dd/mm/yyyy)");
        jPanel22.add(jLabel18);

        try {
            jTFFechaCadu.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTFFechaCadu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFFechaCaduActionPerformed(evt);
            }
        });
        jPanel22.add(jTFFechaCadu);
        jPanel22.add(jLabel19);

        jButtonSaveArt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Save_15px.png"))); // NOI18N
        jButtonSaveArt.setText("Guardar");
        jButtonSaveArt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSaveArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveArtActionPerformed(evt);
            }
        });
        jPanel22.add(jButtonSaveArt);

        jPanelDetArt.add(jPanel22);

        jTabbedPane1.addTab("Detalles", jPanelDetArt);

        jPanelKardexArt.setLayout(new java.awt.BorderLayout());

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
        jTableMain.setRowHeight(20);
        jScrollPane4.setViewportView(jTableMain);

        jPanelKardexArt.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Kárdex", jPanelKardexArt);

        jPanel18.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel18, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanelCenter.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelBtns.setLayout(new java.awt.GridLayout(10, 1));

        jCrearArtBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_32px.png"))); // NOI18N
        jCrearArtBtn.setText("Nuevo");
        jCrearArtBtn.setToolTipText("Crear nuevo artículo de venta");
        jCrearArtBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCrearArtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCrearArtBtnActionPerformed(evt);
            }
        });
        jPanelBtns.add(jCrearArtBtn);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-trash.png"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.setEnabled(false);
        btnBorrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanelBtns.add(btnBorrar);

        jMoverBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-change.png"))); // NOI18N
        jMoverBtn.setText("Mover");
        jMoverBtn.setEnabled(false);
        jMoverBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMoverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMoverBtnActionPerformed(evt);
            }
        });
        jPanelBtns.add(jMoverBtn);

        jBtnPrecios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-price_tag_euro.png"))); // NOI18N
        jBtnPrecios.setText("Precios");
        jBtnPrecios.setEnabled(false);
        jBtnPrecios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBtnPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPreciosActionPerformed(evt);
            }
        });
        jPanelBtns.add(jBtnPrecios);

        jCerrarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Close Pane_20px.png"))); // NOI18N
        jCerrarBtn.setText("Cerrar");
        jCerrarBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCerrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCerrarBtnActionPerformed(evt);
            }
        });
        jPanelBtns.add(jCerrarBtn);

        getContentPane().add(jPanelBtns, java.awt.BorderLayout.EAST);

        jPanelSouth.setLayout(new java.awt.BorderLayout());
        jPanelSouth.add(jStatusLabel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelSouth, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCrearCatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCrearCatBtnActionPerformed
        String nombrecat = JOptionPane.showInputDialog(this," Ingrese el nombre de la categoría ");
        try{
            if (nombrecat != null){
                if (nombrecat.trim().length()>0){
                    jStatusLabel.setText("");
                    catsController.crear(nombrecat.trim().toUpperCase());
                    jStatusLabel.setText("Categoría creada");
                    loadCats();
                }
                else{
                    showMsg("Nombre incorrecto");
                }
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }        
    }//GEN-LAST:event_jCrearCatBtnActionPerformed
    
    public void showDeleteCat(Integer catIndex){
        try{
            CatRow selectedCat = catsList.get(catIndex);
            if (selectedCat.getCatId()>0){
                String currentName = selectedCat.getCatName().trim().toUpperCase();                
                int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar la categoría:"+currentName+"?");
                if (resp == JOptionPane.YES_OPTION){
                    catsController.borrar(selectedCat.getCatId());             
                    showMsg("Borrado correctamente");
                    loadCats();
                }
            }
        }
         catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void showEditCatName(Integer catIndex){
        try{
            CatRow selectedCat = catsList.get(catIndex);
            if (selectedCat.getCatId()>0){
                String currentName = selectedCat.getCatName().trim().toUpperCase();
                String newName = JOptionPane.showInputDialog("Ingrese el nuevo nombre de la categoría", currentName);
                if (newName!=null){
                    if (newName.trim().length()>0){
                        if (!newName.trim().toUpperCase().equalsIgnoreCase(currentName)){                        
                            catsController.actualizar(selectedCat.getCatId(), newName);                    
                            showMsg("Actualizado correctamente");
                            loadCats();
                        }
                    }
                    else{
                        showMsg("Nombre incorrecto");
                    }
                }
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    private void jCerrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCerrarBtnActionPerformed
        SmartFactMain farmaApp = (SmartFactMain)this.root;
        farmaApp.logicaClosePane(this.getClass().getName());
        
    }//GEN-LAST:event_jCerrarBtnActionPerformed

    private void filtroTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroTFActionPerformed
        //doFilter();
    }//GEN-LAST:event_filtroTFActionPerformed

    private void filtroTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtroTFKeyReleased
       doFilter();
    }//GEN-LAST:event_filtroTFKeyReleased
    public void doDelete(){
        int[] rows = this.jTableArts.getSelectedRows();
        if (rows.length>0){
            int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar este(os) artículo(s)?");
            if (resp == JOptionPane.YES_OPTION){
                try{
                    for ( int row: rows ){
                        FilaArticulo filart = this.mercaderiaDataModel.getValueAt(row);
                        if (filart != null){
                            this.articulosController.destroy(filart.getArtId());
                        }
                    }                    
                    reloadArts();
                    
                    showMsg("Artículo(s) borrado");
                }
                catch(Throwable ex){
                    showMsgError(ex);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Elija un articulo para borrar");
        }
    }
    
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        doDelete();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jMoverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMoverBtnActionPerformed
        try{
            Stream stream = catsList.stream().filter(cat->cat.getCatId()>0).map(cat -> cat.getCatName());
            String[] catsArray = ArrayUtil.streamToStrArray(stream);

            String catSelected = (String)JOptionPane.showInputDialog(null, "Selecione la nueva categoría",
                "Cambio de categoría", JOptionPane.QUESTION_MESSAGE, null, catsArray, catsArray[0]);
            
            if (catSelected !=null){
                Optional<CatRow> catRowFinded = catsList.stream().filter(cat->cat.getCatName().equalsIgnoreCase(catSelected)).findFirst();
                if (catRowFinded.isPresent()){
                    CatRow newCatSelected = catRowFinded.get();
                    int[] rows = this.jTableArts.getSelectedRows();
                    for(int rowIndexSel: rows){
                        FilaArticulo filaArt = mercaderiaDataModel.getFila(rowIndexSel);
                        articulosController.cambiarCategoria(filaArt.getArtId(), newCatSelected.getCatId());
                    }
                }
                reloadArts();
                SmartFactMain.showSystemTrayMsg("Operación Exitosa");
           }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }//GEN-LAST:event_jMoverBtnActionPerformed

    private void jEditCatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditCatBtnActionPerformed
        if (selectedCatIndex!=null){
            showEditCatName(selectedCatIndex);
        }
    }//GEN-LAST:event_jEditCatBtnActionPerformed

    private void jCrearArtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCrearArtBtnActionPerformed
        NewArtFrame newArtFrame = new NewArtFrame(this);
        newArtFrame.centerOnScreen();
        newArtFrame.setVisible(true);
    }//GEN-LAST:event_jCrearArtBtnActionPerformed

    private void jTFTotalPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalPCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalPCActionPerformed

    private void jTFTotalPVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalPVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalPVActionPerformed

    private void jTFTotalPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalPMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalPMActionPerformed

    private void jTFTotalInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFTotalInvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFTotalInvActionPerformed

    private void jBtnPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPreciosActionPerformed
        int row = this.jTableArts.getSelectedRow();
        if (row>-1){
            FilaArticulo filaArticulo = this.mercaderiaDataModel.getFila(row);
            if (filaArticulo!= null){
                PreciosXUnidadFrame preciosXUnidadFrame = new PreciosXUnidadFrame(filaArticulo,1);
                preciosXUnidadFrame.centerOnScreen();
                preciosXUnidadFrame.setVisible(true);
            }
        }
        
    }//GEN-LAST:event_jBtnPreciosActionPerformed

    private void jDeleteCatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteCatBtnActionPerformed
                
        if (selectedCatIndex!=null){
            showDeleteCat(selectedCatIndex);
        }
        
    }//GEN-LAST:event_jDeleteCatBtnActionPerformed

    private void jBtnCaducadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCaducadosActionPerformed
        
        loadArtsCaducados();
                
    }//GEN-LAST:event_jBtnCaducadosActionPerformed

    private void jCBTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBTipoActionPerformed

    private void jTFCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFCodBarraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFCodBarraActionPerformed

    private void jTFCodBarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFCodBarraKeyPressed

        /*
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            String barcode = jTFCodBarra.getText();
            if (barcode.trim().length()>0){
                if (this.articulosController.yaExisteBarcode(barcode)){
                    JOptionPane.showMessageDialog(null, "Ya esta registrado el artículo con código de barra:"+ barcode+", ingrese otro");
                }
                else{
                    jTFNombre.requestFocus();
                }
            }
        }
        */

    }//GEN-LAST:event_jTFCodBarraKeyPressed

    private void jTFFechaCaduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFechaCaduActionPerformed

    }//GEN-LAST:event_jTFFechaCaduActionPerformed

    private void jButtonSaveArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveArtActionPerformed
        try{
            logicaSaveArtEdited();
            clearForms();
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }//GEN-LAST:event_jButtonSaveArtActionPerformed
    public void loadCats(){
        try{
            categoriasList = catsController.listar();
            catsList = catsController.parseCatList(categoriasList);
            catsModelList = new CatsListModel();
            catsModelList.setItems(catsList);
            jListCategorias.setModel(catsModelList);
            
            //Para la parte de edicion del articulo
            try{                
                Stream stream = categoriasList.stream().map(cat -> cat.getCatName());
                catsArray = ArrayUtil.streamToStrArray(stream);
                jCBCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(catsArray));

                Optional<Categorias> optCat = categoriasList.stream().filter(cat->cat.getCatId()==-1).findFirst();
                if (optCat.isPresent()){
                    jCBCategoria.setSelectedItem(optCat.get());
                }
            }
            catch(Throwable ex){
                showMsgError(ex);
            }
            
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void loadProveedores(){
        try{
            List<Clientes> auxProvsList = cliCntrl.listarProveedores();
            proveedoresList = cliCntrl.parseProveedoresList(auxProvsList);
            provsListModel = new ProveedoresListModel();
            provsListModel.setItems(proveedoresList);
            jListProveedores.setModel(provsListModel);
            
            //Se carga lista de proveedores en el combobox
            try{
                for(ProveedorRow proveedor: proveedoresList){
                    jCBProveedor.addItem(proveedor);
                }
            }
            catch(Throwable ex){
                showMsgError(ex);
            }
             
         }
         catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    
    
    //Metodos para el formulario
    
    public void clearForms(){
        jTFCodBarra.setText("");
        jTFNombre.setText("");
        jTFPrecioCompra.setText("");
        jTFPrecioVenta.setText("");
        jTFPrecioMinimo.setText("");
        jTFInventario.setText("");
        jTFFechaCadu.setText("");
        //tfIVAPC.setText("");
    }
    
    
    public void loadDetallesArt(Integer artId){
        clearForms();
        loadDatosArticulo(artId);
        loadKardexArt(artId);
    }
    
     public void loadDatosArticulo(Integer artId){
        try{
            artEdited = articulosController.findArticulos(artId);        
            if (artEdited == null){
                showMsg(" NO pude recuperar la información del artículo ");
            }            
            else{
                String artTipo = artEdited.getArtTipo(); 
                String codBar = artEdited.getArtCodbar();
                Integer categoriaId = artEdited.getCatId();
                Integer proveedorId = artEdited.getProvId();
                boolean grabaIva = artEdited.getArtIva();
                String nombreArt = artEdited.getArtNombre();
                BigDecimal precioVenta = artEdited.getArtPrecio();
                BigDecimal precioVentaMin = artEdited.getArtPreciomin();
                BigDecimal precioCompra = artEdited.getArtPrecioCompra();
                BigDecimal invActual = artEdited.getArtInv();
                
                if ("B".equalsIgnoreCase(artTipo)){
                    jCBTipo.setSelectedIndex(0);
                }
                else{
                    jCBTipo.setSelectedIndex(1);
                }
                
                //caregoria
                Categorias catFinded = this.catsController.findCategorias(categoriaId);
                if (catFinded != null){
                    
                    //jCBCategoria.setSelectedItem(catFinded);
                    
                    String catNameFinded = catFinded.getCatName();
                    
                    String catNameF = Arrays.stream(catsArray).filter(catname->catname.equalsIgnoreCase(catNameFinded)).findFirst().orElse(null);
                    
                    if (catNameF!=null){
                        jCBCategoria.setSelectedItem(catNameF);
                    }
                    else{
                        jCBCategoria.setSelectedItem(null);
                    }                    
                }
                
                //proveedor
                jCBProveedor.setSelectedItem(null);
                if (proveedorId != null){
                    ProveedorRow provFinded = proveedoresList.stream().filter(prov->prov.getProvId().intValue() == proveedorId.intValue()).findFirst().orElse(null);                
                    if (provFinded != null){
                        jCBProveedor.setSelectedItem(provFinded);
                    }
                }
                
                this.jTFCodBarra.setText(codBar);
                this.jCBIva.setSelected(grabaIva);
                this.jTFNombre.setText(nombreArt);
                if (artEdited.getArtIva()){
                    this.jTFPrecioVenta.setText(NumbersUtil.round2ToStr(FilaArticulo.getPrecioConIvaUtil(precioVenta)));
                    this.jTFPrecioMinimo.setText(NumbersUtil.round2ToStr(FilaArticulo.getPrecioConIvaUtil(precioVentaMin)));
                    this.jTFPrecioCompra.setText(NumbersUtil.round2ToStr(FilaArticulo.getPrecioConIvaUtil(precioCompra)));                
                }
                else{
                    this.jTFPrecioVenta.setText(NumbersUtil.round2ToStr(precioVenta));
                    this.jTFPrecioMinimo.setText(NumbersUtil.round2ToStr(precioVentaMin));
                    this.jTFPrecioCompra.setText(NumbersUtil.round2ToStr(precioCompra)); 
                }
                
                this.jTFInventario.setText(NumbersUtil.round2ToStr(invActual));
                
                if (artEdited.getFechaCaducidad() != null){
                    this.jTFFechaCadu.setText(FechasUtil.format(artEdited.getFechaCaducidad()));
                } 
                else{
                    this.jTFFechaCadu.setText("");
                }
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }        
    }
     
     public void initColsKardex(){
         colsKardex = new ArrayList<>();
        colsKardex.add(
                new JTableColumn(0, "Fecha", String.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return row.getFechaRegistro();
                        }
                    }        
                )
        );
        colsKardex.add(
                new JTableColumn(1, "Acción", String.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return row.getConcepto();
                        }
                    }        
                )
        );
        
        colsKardex.add(
                new JTableColumn(2, "Cantidad", BigDecimal.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return NumbersUtil.round2(row.getCantidad());
                        }
                    }        
                )
        );
        
         colsKardex.add(
                new JTableColumn(3, "Valor anterior", String.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return NumbersUtil.round2(row.getValorAnt());
                        }
                    }        
                )
        );
         
         colsKardex.add(
                new JTableColumn(4, "Valor actual", String.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return NumbersUtil.round2(row.getValorAct());
                        }
                    }        
                )
        );
         
         colsKardex.add(
                new JTableColumn(5, "Referencia", String.class, 
                    new DefGSVCol<FilaKardexArt>() {
                        @Override
                        public Object getValueAt(FilaKardexArt row, int rowIndex) {
                            return row.getReferencia();
                        }
                    }        
                )
        );
         
        datamodel = new KardexArtDM(colsKardex, kardexArtCntrl);
        jTableMain.setModel(datamodel);
     }
     
     public void loadKardexArt(Integer artId){
         datamodel.setArtId(artId);
        datamodel.loadFromDataBase();        
        jPanelKardexArt.setBorder(javax.swing.BorderFactory.createTitledBorder(String.format("Kárdex (%d)", datamodel.getItems().size())));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JTextField filtroTF;
    private javax.swing.JButton jBtnCaducados;
    private javax.swing.JButton jBtnPrecios;
    private javax.swing.JButton jButtonSaveArt;
    private javax.swing.JComboBox<String> jCBCategoria;
    private javax.swing.JCheckBox jCBIva;
    private javax.swing.JComboBox<ProveedorRow> jCBProveedor;
    private javax.swing.JComboBox<String> jCBTipo;
    private javax.swing.JButton jCerrarBtn;
    private javax.swing.JButton jCrearArtBtn;
    private javax.swing.JButton jCrearCatBtn;
    private javax.swing.JButton jDeleteCatBtn;
    private javax.swing.JButton jEditCatBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListCategorias;
    private javax.swing.JList<String> jListProveedores;
    private javax.swing.JButton jMoverBtn;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBtns;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelDetArt;
    private javax.swing.JPanel jPanelKardexArt;
    private javax.swing.JPanel jPanelNorth;
    private javax.swing.JPanel jPanelSouth;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jStatusLabel;
    private javax.swing.JTextField jTFCodBarra;
    private javax.swing.JFormattedTextField jTFFechaCadu;
    private javax.swing.JTextField jTFInventario;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFPrecioCompra;
    private javax.swing.JTextField jTFPrecioMinimo;
    private javax.swing.JTextField jTFPrecioVenta;
    private javax.swing.JTextField jTFTotalInv;
    private javax.swing.JTextField jTFTotalPC;
    private javax.swing.JTextField jTFTotalPM;
    private javax.swing.JTextField jTFTotalPV;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableArts;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables

   
}
