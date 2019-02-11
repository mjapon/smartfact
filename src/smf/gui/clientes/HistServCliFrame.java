/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.clientes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import smf.controller.ClientesJpaController;
import smf.controller.FacturasJpaController;
import smf.entity.Clientes;
import smf.gui.BaseFrame;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.HistServCliDM;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.rows.RowFactByCaja;

/**
 *
 * @author mjapon
 */
public class HistServCliFrame extends BaseFrame {
    
    private Clientes cliente;
    private ClientesJpaController clientesJpaController;
    private FacturasJpaController facturasJpaController;
    private List<JTableColumn> columns;
    private HistServCliDM datamodel;
    /**
     * Creates new form ComprasClienteFrame
     */
    public HistServCliFrame(Integer cliId) {
        initComponents();
        
        clientesJpaController = new ClientesJpaController(em);
        facturasJpaController = new FacturasJpaController(em);
        
        cliente = clientesJpaController.findById(cliId);
        
        initColumns();
        datamodel = new HistServCliDM(columns, facturasJpaController, cliente.getCliId());
        
        jTableMain.setModel(datamodel);
        
        loadDatos();
    }
    
    private void initColumns(){
        columns = new ArrayList<>();
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        0, 
                        "Fecha", 
                        "fecha", 
                        String.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return FechasUtil.formatDateHour(row.getFactFecha());
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        1, 
                        "Nro Fact.", 
                        "factura", 
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
                        "Artículo/Servicio", 
                        "servicio", 
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
                        "cant", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getDetfCant());
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        4, 
                        "Precio", 
                        "precio", 
                        BigDecimal.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getDetfPrecio());
                            }
                        }
                )
        );
        
        /*
        columns.add(
                new JTableColumn<RowFactByCaja>(
                        4, 
                        "Observación", 
                        "obs", 
                        String.class,
                        new DefGSVCol<RowFactByCaja>() {
                            public Object getValueAt(RowFactByCaja row, int rowIndex) {
                                return "";
                            }
                        }
                )
        );        
        */
    }
    
    public void loadDatos(){
        try{
            datamodel.loadFromDataBase();
            datamodel.fireTableDataChanged();
            jTableMain.updateUI();
            
            jTFPersona.setText(cliente.getCliNombres());
            jTFDireccion.setText(cliente.getCliDir());
            jTFEmail.setText(cliente.getCliEmail());
            jTFTelf.setText(cliente.getCliTelf());
            
            updateBorderTable(jPanel1, datamodel);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFPersona = new javax.swing.JTextField();
        jTFDireccion = new javax.swing.JTextField();
        jTFTelf = new javax.swing.JTextField();
        jTFEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 176));
        setSize(new java.awt.Dimension(700, 176));

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 275));
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

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(2, 4));

        jLabel2.setText("Referente:");
        jPanel4.add(jLabel2);

        jLabel3.setText("Dirección:");
        jPanel4.add(jLabel3);

        jLabel4.setText("Telf:");
        jPanel4.add(jLabel4);

        jLabel5.setText("Email:");
        jPanel4.add(jLabel5);

        jTFPersona.setEditable(false);
        jPanel4.add(jTFPersona);

        jTFDireccion.setEditable(false);
        jPanel4.add(jTFDireccion);

        jTFTelf.setEditable(false);
        jPanel4.add(jTFTelf);

        jTFEmail.setEditable(false);
        jPanel4.add(jTFEmail);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Historial de compras");
        jPanel2.add(jLabel1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.GridLayout(4, 1));

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFEmail;
    private javax.swing.JTextField jTFPersona;
    private javax.swing.JTextField jTFTelf;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
