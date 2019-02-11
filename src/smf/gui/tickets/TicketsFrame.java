/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.tickets;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import smf.controller.CtesJpaController;
import smf.controller.TicketsJpaController;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.ParamsBuscaTickets;
import smf.util.PrintFactUtil;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;
import smf.util.datamodels.TicketsDM;
import smf.util.datamodels.rows.TicketRow;

/**
 *
 * @author mjapon
 */
public class TicketsFrame extends BaseFrame {

    private JFormattedTextField desdeTF;
    private JFormattedTextField hastaTF;
    
    private TicketsDM ticketsDM;
    private List<JTableColumn> columns;
    private TicketsJpaController controller;
    private CtesJpaController ctesJpaController;
     
    /**
     * Creates new form TicketsFrame
     */
    public TicketsFrame() {
        initComponents();
        setupFechas();
        initColumns();
        
        controller = new TicketsJpaController(em);
        ctesJpaController = new CtesJpaController(em);
        ticketsDM = new TicketsDM(columns, controller);
        jTableMain.setModel(ticketsDM);
        jTableMain.updateUI();
        
        jTableMain.getSelectionModel().addListSelectionListener((e) -> {
            int[] rows =  jTableMain.getSelectedRows();
            jButtonImprimir.setEnabled( rows.length>0 );
            jButtonAnular.setEnabled( rows.length>0 );
        });
   
        jTableMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    //showDetallesFrame();
                    //System.out.println("dbl clic event-->");
                }
            }
        });
        
    }

    public void initColumns(){        
        columns = new ArrayList<>();
        
        columns.add(
                new JTableColumn<TicketRow>(
                        0, 
                        "Nro", 
                        "nro", 
                        Integer.class,
                        new DefGSVCol<TicketRow>() {
                            public Object getValueAt(TicketRow row, int rowIndex) {
                                return row.getTkNro();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<TicketRow>(
                        1, 
                        "CI", 
                        "ci", 
                        Integer.class,
                        new DefGSVCol<TicketRow>() {
                            public Object getValueAt(TicketRow row, int rowIndex) {
                                return row.getCliCi();
                            }
                        }
                )
        );
        
        columns.add(
                new JTableColumn<TicketRow>(
                        2, 
                        "Nombres", 
                        "nombres", 
                        Integer.class,
                        new DefGSVCol<TicketRow>() {
                            public Object getValueAt(TicketRow row, int rowIndex) {
                                return row.getCliNombres();
                            }
                        }
                )
        );
        
         columns.add(
                new JTableColumn<TicketRow>(
                        3, 
                        "Monto", 
                        "monto", 
                        Integer.class,
                        new DefGSVCol<TicketRow>() {
                            public Object getValueAt(TicketRow row, int rowIndex) {
                                return NumbersUtil.round2ToStr(row.getMonto());
                            }
                        }
                )
        );
         
         columns.add(
                new JTableColumn<TicketRow>(
                        4, 
                        "Fecha", 
                        "fecha", 
                        Integer.class,
                        new DefGSVCol<TicketRow>() {
                            public Object getValueAt(TicketRow row, int rowIndex) {
                                return row.getFechaCreacion();
                            }
                        }
                )
        );
        
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
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonCrear = new javax.swing.JButton();
        jButtonImprimir = new javax.swing.JButton();
        jButtonAnular = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTableMain.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
        jScrollPane1.setViewportView(jTableMain);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(700, 80));

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

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(19, 19, 19))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("TICKETS");
        jPanel2.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setPreferredSize(new java.awt.Dimension(123, 264));
        jPanel3.setLayout(new java.awt.GridLayout(6, 1));

        jButtonCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_25px.png"))); // NOI18N
        jButtonCrear.setText("Crear");
        jButtonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonCrear);

        jButtonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-print.png"))); // NOI18N
        jButtonImprimir.setText("Imprimir");
        jButtonImprimir.setEnabled(false);
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonImprimir);

        jButtonAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-cancel_subscription.png"))); // NOI18N
        jButtonAnular.setText("Anular");
        jButtonAnular.setEnabled(false);
        jButtonAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnularActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonAnular);

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
    
    public void logicaBuscar(){
        try{            
            Date desde = FechasUtil.parse(desdeTF.getText());
            Date hasta = FechasUtil.parse(hastaTF.getText());
            
            String filtro = "";
            ParamsBuscaTickets params = new ParamsBuscaTickets(desde, hasta, filtro);
            
            ticketsDM.setParams(params);
            
            ticketsDM.loadFromDataBase();
            ticketsDM.fireTableDataChanged();
            jTableMain.updateUI();
            
            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nro("+ticketsDM.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
            
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (ticketsDM != null){
            logicaBuscar();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButtonCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearActionPerformed
        try{
            NewTicketFrame frame = new NewTicketFrame();
            frame.setPreferredSize(new Dimension(500,400));
            frame.pack();
            frame.centerOnScreen();
            frame.setTicketsFrame(this);
            frame.setVisible(true);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
    }//GEN-LAST:event_jButtonCrearActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        closeFrame();
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        try{
            int[] rows= jTableMain.getSelectedRows();
            if (rows.length>0){
                //if (showConfirmMsg("¿Esta seguro/a?")){
                    TicketRow fil = this.ticketsDM.getValueAt(rows[0]);
                    if (fil!=null){
                        String textToPrint = controller.getTicketTxT(fil);
                        PrintFactUtil.imprimirTicketJasper(ctesJpaController, fil.getTicketId());
                    }
                //}
            }
            else{
                
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }//GEN-LAST:event_jButtonImprimirActionPerformed

    private void jButtonAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnularActionPerformed
        
        try{
            
            int[] rows= jTableMain.getSelectedRows();
            if (rows.length>0){
                //if (showConfirmMsg("¿Esta seguro/a?")){
                TicketRow fil = this.ticketsDM.getValueAt(rows[0]);

                if (showConfirmMsg("¿Seguro que desea anular el ticket?")){
                    controller.anularTicket(fil.getTicketId());
                    
                    SmartFactMain.showSystemTrayMsg("El ticket ha sido anulado");
                    logicaBuscar();
                }
            }
                    
                    
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
         
        
    }//GEN-LAST:event_jButtonAnularActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButtonAnular;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JButton jButtonCrear;
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
