/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.merc.unid;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import smf.controller.UnidadesJpaController;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.NumbersUtil;
import smf.util.StringUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.PreciosUnidadDataModel;
import smf.util.datamodels.TableHeadMouseAdapter;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.datamodels.rows.FilaUnidad;
import smf.util.datamodels.rows.FilaUnidadPrecio;

/**
 *
 * @author manuel.japon
 */
public class PreciosXUnidadFrame extends BaseFrame {
    
    private List<FilaUnidad> unidadesList;
    private List<FilaUnidadPrecio> precios;
    private UnidadesJpaController unidadesController;
    private FilaArticulo selectedArt;
    private PreciosUnidadDataModel dataModel;
    private List<JTableColumn> columns;
    private Integer modo;//1-Administracion, 2-Seleccion
    private IListenerSelectUnity listenerSelectUnity;
    
    /**
     * Creates new form PreciosXUnidadFrame
     */
    public PreciosXUnidadFrame( FilaArticulo selectedArt, Integer modo ) {
        initComponents();
        this.modo = modo;
        unidadesController = new UnidadesJpaController(em);
        this.selectedArt = selectedArt;
        loadUnidades();
        initColumns();
        dataModel = new PreciosUnidadDataModel(columns, unidadesController, this.selectedArt.getArtId());
        jTable1.setModel(dataModel);
        loadPrecios();
        
        jTable1.getTableHeader().addMouseListener(new TableHeadMouseAdapter(jTable1, dataModel));
        
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    endisBtns();
                }
            }
        });
        jLabelTitle.setText("Precios por unidad para:"+selectedArt.getNombre());
        
        if (modo==1){
            jPanelNewForm.setVisible(true);
            jBtnCrear.setEnabled(true);
            jBtnSelect.setEnabled(false);
            jBtnBorrar.setEnabled(true);
            jPanelPrecioCred.setVisible(false);
        }
        else{
            jPanelNewForm.setVisible(false);
            jBtnCrear.setEnabled(false);
            jBtnCrear.setVisible(false);
            jBtnSelect.setEnabled(true);
            jBtnBorrar.setEnabled(false);
            jPanelPrecioCred.setVisible(true);
            //System.out.println("Valor del predio es:"+ selectedArt.getPrecioVentaConIva().toPlainString());            
            System.out.println("El valor del precio de venta es:");
            jTFPCred.setText(NumbersUtil.round2ToStr(selectedArt.getPrecioVenta()));
            
            System.out.println("El precio actual del articulo es:");
            System.out.println(selectedArt.getPrecioVentaConIva());
            //Aqui se debe mostrar para poder cambiar el precio 
        }
    }

    public IListenerSelectUnity getListenerSelectUnity() {
        return listenerSelectUnity;
    }

    public void setListenerSelectUnity(IListenerSelectUnity listenerSelectUnity) {
        this.listenerSelectUnity = listenerSelectUnity;
    }

   
    
    
    public void endisBtns(){
        boolean isEnable = jTable1.getSelectedRows().length>0;
        jBtnBorrar.setEnabled(isEnable);
    }
    
    public void initColumns(){
        //a.uni_id, a.uni_name, a.uni_simbolo
        columns = new ArrayList<JTableColumn>();
        columns.add(
                new JTableColumn<FilaUnidadPrecio>(
                        0, 
                        "Unidad", 
                        "uni_name", 
                        Integer.class,
                        new DefGSVCol<FilaUnidadPrecio>() {
                            public Object getValueAt(FilaUnidadPrecio row, int rowIndex) {
                                return row.getUnidad();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaUnidadPrecio>(
                        1, 
                        "Precio Normal", 
                        "unidp_precioventa", 
                        String.class,
                        new DefGSVCol<FilaUnidadPrecio>() {
                            public Object getValueAt(FilaUnidadPrecio row, int rowIndex) {
                                if (row.isIsIva()){
                                    return NumbersUtil.round2(row.getPrecioNormalConIva());
                                }
                                return NumbersUtil.round2(row.getPrecioNormal());
                            }
                            public boolean isCellEditable(FilaUnidadPrecio row) {
                                return true;
                            }
                            public void setValueAt(FilaUnidadPrecio row, int rowIndex, Object value) {
                                if (row.isIsIva()){
                                    row.setPrecioNormal(FilaArticulo.getPrecioSinIvaUtil(new BigDecimal(value.toString())) );
                                }
                                else{
                                    row.setPrecioNormal(new BigDecimal(String.valueOf(value)));
                                }
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<FilaUnidadPrecio>(
                        2, 
                        "Precio Mínimo",
                        "unidp_preciomin", 
                        String.class,
                        new DefGSVCol<FilaUnidadPrecio>() {
                            public Object getValueAt(FilaUnidadPrecio row, int rowIndex) {
                                if (row.isIsIva()){
                                    return NumbersUtil.round2(row.getPrecioMinConIva());
                                }
                                return NumbersUtil.round2(row.getPrecioMin());
                            }
                            public boolean isCellEditable(FilaUnidadPrecio row) {
                                return true;
                            }
                            public void setValueAt(FilaUnidadPrecio row, int rowIndex, Object value) {
                                if (row.isIsIva()){
                                    row.setPrecioMin(FilaArticulo.getPrecioSinIvaUtil(new BigDecimal(value.toString())) );
                                }
                                else{
                                    row.setPrecioMin(new BigDecimal(String.valueOf(value)));
                                }
                            }
                        }
                )
        );
    }
    
    public void loadUnidades(){
        unidadesList = unidadesController.listar();
        unidadesList.stream().forEach(filaUnidad -> jCBUnidad.addItem(filaUnidad.getUnidName()) );
    }
    
    public void filterUnidades(){
        precios.stream().forEach( filaPrecio -> { 
            unidadesList.stream().filter(filaUnidad -> filaUnidad.getUnidId()!= filaPrecio.getUnidId() );
        });
    }
    
    public void loadPrecios(){
        dataModel.loadFromDataBase();
        dataModel.fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jBtnBorrar = new javax.swing.JButton();
        jBtnSelect = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanelNewForm = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCBUnidad = new javax.swing.JComboBox<>();
        jTFPrecioVenta = new javax.swing.JTextField();
        jTFPrecioMin = new javax.swing.JTextField();
        jBtnCrear = new javax.swing.JButton();
        jPanelPrecioCred = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTFPCred = new javax.swing.JTextField();
        jBtnOkPrecioCred = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
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

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-trash.png"))); // NOI18N
        jBtnBorrar.setText("Borrar");
        jBtnBorrar.setEnabled(false);
        jBtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBorrarActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnBorrar);

        jBtnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-checked.png"))); // NOI18N
        jBtnSelect.setText("Elegir");
        jBtnSelect.setEnabled(false);
        jBtnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSelectActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnSelect);

        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jBtnSalir.setText("Cerrar");
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnSalir);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTitle.setText("Precios por unidad para:");
        jPanel1.add(jLabelTitle);

        jPanel4.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanelNewForm.setLayout(new java.awt.GridLayout(2, 3));

        jLabel2.setText("Unidad");
        jPanelNewForm.add(jLabel2);

        jLabel3.setText("Precio Venta");
        jPanelNewForm.add(jLabel3);

        jLabel4.setText("Precio Min.");
        jPanelNewForm.add(jLabel4);

        jPanelNewForm.add(jCBUnidad);
        jPanelNewForm.add(jTFPrecioVenta);
        jPanelNewForm.add(jTFPrecioMin);

        jPanel5.add(jPanelNewForm, java.awt.BorderLayout.CENTER);

        jBtnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_25px.png"))); // NOI18N
        jBtnCrear.setText("Agregar");
        jBtnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCrearActionPerformed(evt);
            }
        });
        jPanel5.add(jBtnCrear, java.awt.BorderLayout.EAST);

        jLabel1.setText("PRECIO HA CRÉDITO:");

        jTFPCred.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTFPCredMousePressed(evt);
            }
        });
        jTFPCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFPCredActionPerformed(evt);
            }
        });
        jTFPCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFPCredKeyPressed(evt);
            }
        });

        jBtnOkPrecioCred.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-checked.png"))); // NOI18N
        jBtnOkPrecioCred.setText("Aceptar");
        jBtnOkPrecioCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOkPrecioCredActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPrecioCredLayout = new javax.swing.GroupLayout(jPanelPrecioCred);
        jPanelPrecioCred.setLayout(jPanelPrecioCredLayout);
        jPanelPrecioCredLayout.setHorizontalGroup(
            jPanelPrecioCredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrecioCredLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFPCred, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnOkPrecioCred)
                .addContainerGap())
        );
        jPanelPrecioCredLayout.setVerticalGroup(
            jPanelPrecioCredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrecioCredLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelPrecioCredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFPCred, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jBtnOkPrecioCred))
                .addGap(2, 2, 2))
        );

        jPanel5.add(jPanelPrecioCred, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        
        setVisible(false);
        
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCrearActionPerformed
        try{
            String unityName = (String)jCBUnidad.getSelectedItem();
            Optional<FilaUnidad> optionalResult = unidadesList.stream().filter(e -> e.getUnidName().equalsIgnoreCase(unityName)).findFirst();
            if (optionalResult.isPresent()){
                FilaUnidad selectedUnity = optionalResult.get();
                
                if (StringUtil.isEmpty(jTFPrecioVenta.getText()) || StringUtil.isEmpty(jTFPrecioMin.getText())){
                    showMsg("Debe ingresar los precios");
                    return;
                }
                
                BigDecimal precioNormalInput = new BigDecimal(jTFPrecioVenta.getText());
                BigDecimal precioMinInput = new BigDecimal(jTFPrecioMin.getText());
                
                //Verificar si el producto graba iva
                BigDecimal precioNormal = precioNormalInput;
                BigDecimal precioMin = precioMinInput;
                if (selectedArt.isIva()){
                    precioNormal = FilaArticulo.getPrecioSinIvaUtil(precioNormalInput);
                    precioMin = FilaArticulo.getPrecioSinIvaUtil(precioMinInput);
                }
                
                if (unidadesController.yaExistePrecio(selectedArt.getArtId(), selectedUnity.getUnidId())){
                    showMsg("Ya esta registrado el precio para esta unidad");
                    return;
                }
                unidadesController.registrarPrecio( 
                        new FilaUnidadPrecio(selectedArt.getArtId(), 
                                selectedUnity.getUnidId(), 
                                precioNormal, 
                                precioMin,
                                selectedArt.isIva()
                        )
                );
                loadPrecios();
                SmartFactMain.showSystemTrayMsg("El precio ha sido registrado");
            }
            else{
                showMsg("No pude recuperar unidad seleccionada");
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }//GEN-LAST:event_jBtnCrearActionPerformed

    private void jBtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBorrarActionPerformed
        try{
            if (showConfirmMsg("¿Esta segur@?")){
                for (int row=0;row<jTable1.getSelectedRows().length;row++){
                    int rowindex = jTable1.getSelectedRows()[row];
                    FilaUnidadPrecio fila = dataModel.getValueAt(rowindex);
                    unidadesController.deletePrecio( fila.getFilaId() );
                }
            }
            loadPrecios();
            SmartFactMain.showSystemTrayMsg(" Los registros han sido borrados");
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }//GEN-LAST:event_jBtnBorrarActionPerformed

    private void jBtnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSelectActionPerformed
        try{
            int indexRow = jTable1.getSelectedRow();
            listenerSelectUnity.doSelect( dataModel.getValueAt(indexRow) );
            setVisible(false);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
        
        
    }//GEN-LAST:event_jBtnSelectActionPerformed

    private void jBtnOkPrecioCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOkPrecioCredActionPerformed
       
        logicaPrecio();
    }//GEN-LAST:event_jBtnOkPrecioCredActionPerformed

    public void logicaPrecio(){
         try{
            listenerSelectUnity.doChangePrecio(new BigDecimal(this.jTFPCred.getText()));
            setVisible(false);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    
    private void jTFPCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFPCredActionPerformed
        
        
        
        
    }//GEN-LAST:event_jTFPCredActionPerformed

    private void jTFPCredMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFPCredMousePressed
        
        
        
        
    }//GEN-LAST:event_jTFPCredMousePressed

    private void jTFPCredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFPCredKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            logicaPrecio();
        }
        
    }//GEN-LAST:event_jTFPCredKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBorrar;
    private javax.swing.JButton jBtnCrear;
    private javax.swing.JButton jBtnOkPrecioCred;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jBtnSelect;
    private javax.swing.JComboBox<String> jCBUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelNewForm;
    private javax.swing.JPanel jPanelPrecioCred;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFPCred;
    private javax.swing.JTextField jTFPrecioMin;
    private javax.swing.JTextField jTFPrecioVenta;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
