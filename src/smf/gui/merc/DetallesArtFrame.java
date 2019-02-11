/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.merc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import smf.controller.ArticulosJpaController;
import smf.controller.CategoriasJpaController;
import smf.controller.ClientesJpaController;
import smf.controller.KardexArtCntrl;
import smf.controller.SecuenciasJpaController;
import smf.entity.Articulos;
import smf.entity.Categorias;
import smf.entity.Clientes;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.ArrayUtil;
import smf.util.CtesU;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.KardexArtDM;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.datamodels.rows.FilaKardexArt;

/**
 *
 * @author manuel.japon
 */
public class DetallesArtFrame extends BaseFrame {
    
    private CategoriasJpaController catsController;
    private SecuenciasJpaController secuenciasController;
    private List<Categorias> categorias;
    private ArticulosJpaController articulosController;
    private ClientesJpaController cliCntrl;
    private KardexArtCntrl kardexArtCntrl;
    
    private final String SEC_SERVS = "SEC_SERVS";
    private ParentNewArtFrame parentFrame;
    private List<Clientes> proveedores;
    private Integer artId;    
    private String[] catsArray;
    private Articulos artEdited;
    
    private List<JTableColumn> colsKardex;
    private KardexArtDM datamodel;
            

    /**
     * Creates new form DetallesArtFrame
     */
    public DetallesArtFrame(ParentNewArtFrame prFrame, Integer artId) {
        initComponents();
        parentFrame = prFrame;
        catsController = new CategoriasJpaController(em);
        secuenciasController = new SecuenciasJpaController(em);      
        articulosController = new ArticulosJpaController(em);
        cliCntrl = new ClientesJpaController(em);
        kardexArtCntrl = new KardexArtCntrl(em);
        
        this.artId = artId;
        clearForms();
        loadCats();
        setupTipoCB();
        loadProveedores();        
        loadDatosArticulo();     
        setupTableKardex();
    }
    
    public void setupTableKardex(){        
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
        datamodel.loadFromDataBase();
        jPanelKardex.setBorder(javax.swing.BorderFactory.createTitledBorder(String.format("Kárdex (%d)", datamodel.getItems().size())));
    }
    
    
    public void loadDatosArticulo(){
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
                    Clientes provFinded = proveedores.stream().filter(prov->prov.getCliId().intValue() == proveedorId.intValue()).findFirst().orElse(null);                
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
    
    public void loadProveedores(){
        try{
            proveedores = cliCntrl.listarProveedores();
            for(Clientes cliente: proveedores){
                jCBProveedor.addItem(cliente);
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    public void setupServicio(){
        jTFInventario.setText("0");
        jTFInventario.setEnabled(false);        
        jTFPrecioCompra.setText(BigDecimal.ZERO.toPlainString());
        jTFPrecioCompra.setEnabled(false);
        Integer secServ = secuenciasController.getSecuenciaValue(SEC_SERVS);
        if (secServ != null){
            jTFCodBarra.setText("SERV_"+secServ);
        }
    }
    
    public void setupBien(){
        jTFInventario.setText("0");
        jTFInventario.setEnabled(true);
        jTFCodBarra.setText("");
        jTFPrecioCompra.setText(BigDecimal.ZERO.toPlainString());
        jTFPrecioCompra.setEnabled(true);
    }
    
    public void setupTipoCB(){        
        /*jCBTipo.addItemListener((ev) -> {            
            if (ev.getStateChange()==ItemEvent.SELECTED){
                String item = (String)ev.getItem();
                if ("bien".equalsIgnoreCase(item)){
                    setupBien();
                }
                else{
                    setupServicio();
                }
            }
        });
        */
    }

    public void clearForms(){
        jTFCodBarra.setText("");
        jTFNombre.setText("");
        jTFPrecioCompra.setText("");
        jTFPrecioVenta.setText("");
        jTFPrecioMinimo.setText("");
        jTFInventario.setText("");
        //tfIVAPC.setText("");
    }
    
    public void loadCats(){
        try{
            categorias = catsController.listar();
            Stream stream = categorias.stream().map(cat -> cat.getCatName());
            catsArray = ArrayUtil.streamToStrArray(stream);
            jCBCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(catsArray));
            
            Optional<Categorias> optCat = categorias.stream().filter(cat->cat.getCatId()==-1).findFirst();
            if (optCat.isPresent()){
                jCBCategoria.setSelectedItem(optCat.get());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jCBTipo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTFCodBarra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jCBCategoria = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jCBProveedor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCBIva = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFPrecioVenta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFPrecioMinimo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFPrecioCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFInventario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTFFechaCadu = new javax.swing.JFormattedTextField();
        jPanelKardex = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel1.setText("Detalles del Artículo");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(5, 1));

        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Save_20px.png"))); // NOI18N
        jButtonSave.setText("Guardar");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonSave);

        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Close Pane_20px.png"))); // NOI18N
        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonClose);

        jPanel2.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setLayout(new java.awt.GridLayout(11, 2));

        jLabel10.setText("Tipo:");
        jPanel3.add(jLabel10);

        jCBTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BIEN", "SERVICIO" }));
        jCBTipo.setEnabled(false);
        jCBTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTipoActionPerformed(evt);
            }
        });
        jPanel3.add(jCBTipo);

        jLabel2.setText("Código:");
        jPanel3.add(jLabel2);

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
        jPanel3.add(jTFCodBarra);

        jLabel8.setText("Categoría:");
        jPanel3.add(jLabel8);

        jCBCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        jPanel3.add(jCBCategoria);

        jLabel11.setText("Proveedor:");
        jPanel3.add(jLabel11);

        jPanel3.add(jCBProveedor);

        jLabel7.setText("Graba IVA:");
        jPanel3.add(jLabel7);

        jCBIva.setEnabled(false);
        jPanel3.add(jCBIva);

        jLabel3.setText("Nombre:");
        jPanel3.add(jLabel3);
        jPanel3.add(jTFNombre);

        jLabel4.setText("Precio Venta:");
        jPanel3.add(jLabel4);

        jTFPrecioVenta.setEditable(false);
        jPanel3.add(jTFPrecioVenta);

        jLabel5.setText("Precio Venta Mínimo:");
        jPanel3.add(jLabel5);

        jTFPrecioMinimo.setEditable(false);
        jPanel3.add(jTFPrecioMinimo);

        jLabel6.setText("Precio Compra (sin iva):");
        jPanel3.add(jLabel6);

        jTFPrecioCompra.setEditable(false);
        jPanel3.add(jTFPrecioCompra);

        jLabel9.setText("Inventario Actual");
        jPanel3.add(jLabel9);
        jPanel3.add(jTFInventario);

        jLabel12.setText("Fecha Caducidad (dd/mm/yyyy)");
        jPanel3.add(jLabel12);

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
        jPanel3.add(jTFFechaCadu);

        jPanel5.add(jPanel3);

        jPanelKardex.setBorder(javax.swing.BorderFactory.createTitledBorder("Kárdex"));
        jPanelKardex.setLayout(new java.awt.BorderLayout());

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

        jPanelKardex.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanelKardex);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        parentFrame.afterClose();
        setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        logicaSave();
    }//GEN-LAST:event_jButtonSaveActionPerformed
    
    public void logicaSave(){
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
            
            String tipo = Character.toString (jCBTipo.getSelectedItem().toString().charAt(0));
            Categorias selectedCat = categorias.get(jCBCategoria.getSelectedIndex());
            
            FilaArticulo auxfilaArt = new FilaArticulo(artId, codBarra, nombre, 
                    precioCompraConIva, precioVentaConIva, precioMinConIva, 
                    isIvaTrue, inventario, selectedCat.getCatName(), tipo, 
                    selectedCat.getCatId(), selectedCat.getCatCaja());
            
            Clientes proveedor = (Clientes)jCBProveedor.getSelectedItem();
            if (proveedor != null){
                auxfilaArt.setProveedorId(proveedor.getCliId());
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

            boolean valid = parentFrame.validateNewRow(filaArt);
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
                parentFrame.afterSuccessfulSaved();
                setVisible(false);
            }
            else{
                showMsg("Los datos son incorrectos, verifica el codigo de barra, nombre del articulo ..");
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }
    
    
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

    private void jCBTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBTipoActionPerformed

    private void jTFFechaCaduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFechaCaduActionPerformed

    }//GEN-LAST:event_jTFFechaCaduActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox<String> jCBCategoria;
    private javax.swing.JCheckBox jCBIva;
    private javax.swing.JComboBox<Clientes> jCBProveedor;
    private javax.swing.JComboBox<String> jCBTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelKardex;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCodBarra;
    private javax.swing.JFormattedTextField jTFFechaCadu;
    private javax.swing.JTextField jTFInventario;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFPrecioCompra;
    private javax.swing.JTextField jTFPrecioMinimo;
    private javax.swing.JTextField jTFPrecioVenta;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
