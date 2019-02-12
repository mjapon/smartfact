/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.facte;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import smf.controller.CtesJpaController;
import smf.controller.FacturasJpaController;
import smf.entity.Facturas;
import smf.gui.BaseFrame;
import smf.util.DatosCabeceraFactura;
import smf.util.EntityManagerUtil;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaFactura;
import smf.util.NumbersUtil;
import smf.util.PrintFactUtil;
import smf.util.TotalesFactura;
import smf.util.datamodels.DetallesFactDataModel;
/**
 *
 * @author mjapon
 */
public class DetallesFacturaFrame extends BaseFrame {

    private Integer factId;
    private FacturasJpaController facturaController;
    private DetallesFactDataModel dataModel;
    private Facturas factura;
    private Integer tra_codigo;
    
    private final CtesJpaController ctesController;
    
    /**
     * Creates new form DetallesFacturaFrame
     * @param factId
     */
    public DetallesFacturaFrame(Integer factId) {
        super();
        initComponents();
        
        facturaController = new FacturasJpaController(em);
        ctesController = new CtesJpaController(em);
        
        this.factId = factId;
        try{
            this.tra_codigo = facturaController.getTraCodigo(factId);
        }catch(Throwable ex){
            logError(ex);
        }
        
        dataModel = new DetallesFactDataModel(this.tra_codigo);
        List<Object[]> detalles = facturaController.listarDetalles(factId);
        factura = facturaController.buscar(factId);
        
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("NUM ARTÍCULOS:"+detalles.size()));
        
        NumbersUtil.round2ToStr(BigDecimal.ONE);
        
        this.jLabel1.setText(" Detalles del Comprobante Nro: "+ factura.getFactNum());
        this.labelFecha.setText(FechasUtil.format(factura.getFactFecha()));
        this.labelNro.setText( factura.getFactNum() );
        this.labelSubtotal.setText( NumbersUtil.round2ToStr(factura.getFactSubt()) );
        this.labelDescuento.setText( NumbersUtil.round2ToStr(factura.getFactDesc()) );
        this.labelDescuentoG.setText( factura.getFactDescg().toPlainString() );
        this.labelIva.setText( factura.getFactIva().toPlainString() );
        this.labelTotal.setText( factura.getFactTotal().toPlainString() );
        this.labelCliente.setText( factura.getCliId().getCliNombres() );
        this.labelCiruc.setText( factura.getCliId().getCliCi() );
        this.labelDireccion.setText( factura.getCliId().getCliDir() );
        this.labelTelf.setText( factura.getCliId().getCliTelf() );
        this.labelEmail.setText( factura.getCliId().getCliEmail() );
        
        if (this.tra_codigo != null && this.tra_codigo == 1 ){
            this.jLabelUtilidad.setText("Utilidad:");
            this.labelUtilidad.setText( NumbersUtil.round2ToStr(factura.getFactUtilidad())  );
        }
        else{
            this.jLabelUtilidad.setText("");
            this.labelUtilidad.setText("");
        }
        
        dataModel.loadItems(detalles);
        
        jTable1.setModel(dataModel); 
        
        float[] widths = {10.0f, 30.0f,5.0f,10.0f,10.0f,10.0f,10.0f,5.0f,10.0f};
        dataModel.setupWidths(widths, jTable1);
        
        dataModel.setJtable(jTable1);
        
        dataModel.fireTableDataChanged();
        
        List<Object[]> pagosList  = facturaController.getPagos(factId);
        
        efectivoValLbl.setText("0.0");
        creditoValLbl.setText("0.0");
        
        if (pagosList != null){
            for (Object[] filaPago: pagosList){
                BigDecimal monto = (BigDecimal)filaPago[1]; 
                BigDecimal saldo = (BigDecimal)filaPago[2]; 
                //Integer spId =  (Integer)filaPago[4];
                Integer fpId =  (Integer)filaPago[5];
                String observ  = (String)filaPago[3];
                if (fpId == 1){
                    efectivoValLbl.setText( NumbersUtil.round(monto, 2).toPlainString() );
                }
                else if (fpId == 2){                   
                    creditoValLbl.setText(NumbersUtil.round(monto, 2).toPlainString());
                    String texto = NumbersUtil.round(saldo, 2).toPlainString();
                    if (monto.compareTo(BigDecimal.ZERO)>0){                        
                        //creditoValLbl.setForeground(Color.orange);
                        
                        if (saldo.compareTo(BigDecimal.ZERO)>0){
                            texto = NumbersUtil.round(saldo, 2).toPlainString()+"[PEND]";
                            
                            saldoValLbl.setForeground(Color.red);
                        }
                        else{
                            texto = NumbersUtil.round(saldo, 2).toPlainString()+"[CANCELADO]";
                            saldoValLbl.setForeground(Color.green);
                        }
                    }                  
                    else{
                        //creditoValLbl.setForeground(Color.BLACK);
                    }
                    saldoValLbl.setText( texto );
                    jTextAreaObs.setText(observ);                    
                    if ( monto.compareTo(BigDecimal.ZERO)>0 && saldo.compareTo(BigDecimal.ZERO)>0 ){
                        this.labelUtilidad.setText( this.labelUtilidad.getText() +"[CRED] " );
                    }                    
                }
            }            
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
        jButtonPrint = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelNro = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelSubtotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelDescuento = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelDescuentoG = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelIva = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        labelCliente = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        labelCiruc = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        labelDireccion = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        labelTelf = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaObs = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabelUtilidad = new javax.swing.JLabel();
        efectivoValLbl = new javax.swing.JLabel();
        creditoValLbl = new javax.swing.JLabel();
        saldoValLbl = new javax.swing.JLabel();
        labelUtilidad = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridLayout(1, 1));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel1.setText("Detalles del Comprobante Nro:");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(6, 1));

        jButtonPrint.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-print.png"))); // NOI18N
        jButtonPrint.setText("Imprimir");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonPrint);

        jButtonSalir.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButtonSalir.setText("Cerrar");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSalir);

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        jPanel6.setLayout(new java.awt.GridLayout(7, 2));

        jLabel2.setText("Fecha:");
        jPanel6.add(jLabel2);

        labelFecha.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelFecha.setText("fechaValue");
        jPanel6.add(labelFecha);

        jLabel3.setText("Nro:");
        jPanel6.add(jLabel3);

        labelNro.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelNro.setText("nroValue");
        jPanel6.add(labelNro);

        jLabel4.setText("SubTotal:");
        jPanel6.add(jLabel4);

        labelSubtotal.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelSubtotal.setText("subtotalValue");
        jPanel6.add(labelSubtotal);

        jLabel5.setText("Descuento:");
        jPanel6.add(jLabel5);

        labelDescuento.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelDescuento.setText("descuentoValue");
        jPanel6.add(labelDescuento);

        jLabel16.setText("Descuento Global:");
        jPanel6.add(jLabel16);

        labelDescuentoG.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelDescuentoG.setText("descuentoValue");
        jPanel6.add(labelDescuentoG);

        jLabel6.setText("IVA:");
        jPanel6.add(jLabel6);

        labelIva.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelIva.setText("ivaValue");
        jPanel6.add(labelIva);

        jLabel7.setText("Total:");
        jPanel6.add(jLabel7);

        labelTotal.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelTotal.setText("totalValue");
        jPanel6.add(labelTotal);

        jPanel4.add(jPanel6);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.GridLayout(6, 1));

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel8.setText("Referente:");
        jPanel9.add(jLabel8, java.awt.BorderLayout.WEST);

        labelCliente.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelCliente.setText("clienteValue");
        jPanel9.add(labelCliente, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel9);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel9.setText("CI/RUC:");
        jPanel10.add(jLabel9, java.awt.BorderLayout.WEST);

        labelCiruc.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelCiruc.setText("cirucValue");
        jPanel10.add(labelCiruc, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel10);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel10.setText("Dirección:");
        jPanel13.add(jLabel10, java.awt.BorderLayout.WEST);

        labelDireccion.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelDireccion.setText("direccionValue");
        jPanel13.add(labelDireccion, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel13);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel11.setText("Telf:");
        jPanel14.add(jLabel11, java.awt.BorderLayout.WEST);

        labelTelf.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelTelf.setText("telfValue");
        jPanel14.add(labelTelf, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel12.setText("Email:");
        jPanel15.add(jLabel12, java.awt.BorderLayout.WEST);

        labelEmail.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        labelEmail.setText("emailValue");
        jPanel15.add(labelEmail, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel15);

        jPanel7.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel11.setLayout(new java.awt.GridLayout(1, 1));

        jTextAreaObs.setColumns(20);
        jTextAreaObs.setRows(5);
        jTextAreaObs.setEnabled(false);
        jScrollPane2.setViewportView(jTextAreaObs);

        jPanel11.add(jScrollPane2);

        jPanel7.add(jPanel11, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel7);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "NumElements", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.GridLayout(2, 4));

        jLabel13.setText("Efectivo:");
        jPanel8.add(jLabel13);

        jLabel14.setText("Crédito:");
        jPanel8.add(jLabel14);

        jLabel15.setText("Saldo:");
        jPanel8.add(jLabel15);

        jLabelUtilidad.setText("Utilidad:");
        jPanel8.add(jLabelUtilidad);

        efectivoValLbl.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jPanel8.add(efectivoValLbl);

        creditoValLbl.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jPanel8.add(creditoValLbl);

        saldoValLbl.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jPanel8.add(saldoValLbl);

        labelUtilidad.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        labelUtilidad.setText("emailValue");
        jPanel8.add(labelUtilidad);

        jPanel3.add(jPanel8, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        Map<String,Object> datosFactura = facturaController.getDetallesFactura(factId);
        
        DatosCabeceraFactura cabecera = (DatosCabeceraFactura)datosFactura.get("cabecera");
        TotalesFactura totales = (TotalesFactura)datosFactura.get("totales");
        List<FilaFactura> detalles = (List<FilaFactura>)datosFactura.get("detalles");
        
        PrintFactUtil.imprimir(ctesController, cabecera, totales, detalles, factId);  
        
        setVisible(false);
        
    }//GEN-LAST:event_jButtonPrintActionPerformed

  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel creditoValLbl;
    private javax.swing.JLabel efectivoValLbl;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelUtilidad;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaObs;
    private javax.swing.JLabel labelCiruc;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelDescuento;
    private javax.swing.JLabel labelDescuentoG;
    private javax.swing.JLabel labelDireccion;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelIva;
    private javax.swing.JLabel labelNro;
    private javax.swing.JLabel labelSubtotal;
    private javax.swing.JLabel labelTelf;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelUtilidad;
    private javax.swing.JLabel saldoValLbl;
    // End of variables declaration//GEN-END:variables
}
