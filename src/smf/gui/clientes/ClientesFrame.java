/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.clientes;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import smf.controller.ClientesJpaController;
import smf.entity.Clientes;
import smf.gui.BaseFrame;
import smf.gui.SmartFactMain;
import smf.gui.facte.FacturaVentaFrame;
import smf.util.EstadoAPP;
import smf.util.FechasUtil;
import smf.util.datamodels.ClientesDM;
import smf.util.datamodels.DefGSVCol;
import smf.util.datamodels.JTableColumn;

/**
 *
 * @author mjapon
 */
public class ClientesFrame extends BaseFrame {
    
    private ClientesDM clientesDM;
    private ClientesJpaController controller;
    private List<JTableColumn> columns;
    private Integer tipo;
    /**
     * Creates new form ClientesFrame
     */
    public ClientesFrame(Integer tipo) {        
        this.tipo = tipo;
        initComponents();
        controller = new ClientesJpaController(em);
        initColumn();
        
        clientesDM = new ClientesDM(columns, controller, tipo);
        jTableMain.setModel(clientesDM);
        
        jTableMain.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTableMain.columnAtPoint(e.getPoint());
                try{
                    for (int i=0; i<clientesDM.getColumnCount();i++){
                        jTableMain.getColumnModel().getColumn(i).setHeaderValue( clientesDM.getColumnName(i) );
                    }                    
                    clientesDM.switchSortColumn(col);
                }
                catch(Throwable ex){
                    showMsgError(ex);
                }
            }
        });
        
        jTableMain.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    btnHistServ.setEnabled( jTableMain.getSelectedRows().length>0 );
                    btnFacturar.setEnabled( jTableMain.getSelectedRows().length>0 );
                }
            }
        });
        
        
         String titulo = "";
        switch(tipo){
            case 1: {titulo="LISTA DE CLIENTES"; break;}
            case 2: {titulo="LISTA DE PROVEEDORES"; break;}
            default:{titulo="";break;}
        }
        
        jLabelTit.setText(titulo);
        
    }
    
    private void initColumn(){
        columns = new ArrayList<>();
        
        columns.add(new JTableColumn<Clientes>(
                        0, 
                        "Ci/Ruc", 
                        "cliCi", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliCi();
                            }

                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    String newCi = String.valueOf(value).trim().toUpperCase();
                                    if (newCi.length()>0){                                        
                                        if (!row.getCliCi().equalsIgnoreCase(newCi)){
                                            if ( controller.findByCi(newCi)!= null){
                                                showMsg("Ya existe un referente con el numero de ci/ruc ingresado, no se puede actualizar");
                                            }
                                            else{
                                                row.setCliCi(String.valueOf(value).trim().toUpperCase() );
                                                controller.edit(row);
                                                SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                            }
                                        }
                                    }
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();                                
                            }
                            
                        }
                )
        );        
        
        columns.add(new JTableColumn<Clientes>(
                        1, 
                        "Nombres", 
                        "cliNombres", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliNombres();
                            }

                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    row.setCliNombres(String.valueOf(value).trim().toUpperCase() );
                                    controller.edit(row);
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();

                            }
                        }
                )
        );
        
        columns.add(new JTableColumn<Clientes>(
                        2, 
                        "Apellidos", 
                        "cliApellidos", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliApellidos();
                            }

                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    row.setCliApellidos(String.valueOf(value).trim().toUpperCase() );
                                    controller.edit(row);
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();

                            }
                        }
                )
        );
        
        columns.add(new JTableColumn<Clientes>(
                        3, 
                        "Dirección", 
                        "cliDir", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliDir();
                            }

                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    row.setCliDir( String.valueOf(value).trim().toUpperCase() );
                                    controller.edit(row);
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();
                            }
                        }
                )
        );
        
         columns.add(new JTableColumn<Clientes>(
                        4, 
                        "Teléfono", 
                        "cliTelf", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliTelf();
                            }
                            
                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    row.setCliTelf(String.valueOf(value).trim().toUpperCase() );
                                    controller.edit(row);
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();
                            }
                            
                        }
                )
        );
          columns.add(new JTableColumn<Clientes>(
                        5, 
                        "Email", 
                        "cliEmail", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliEmail();
                            }                            
                            @Override
                            public boolean isCellEditable(Clientes row) {
                                return true;
                            }

                            @Override
                            public void setValueAt(Clientes row, int rowIndex, Object value) {
                                try{
                                    row.setCliEmail(String.valueOf(value).trim().toUpperCase() );
                                    controller.edit(row);
                                    SmartFactMain.showSystemTrayMsg("Los cambios han sido registrados");
                                }
                                catch(Throwable ex){
                                    showMsgError(ex);
                                }                
                                clientesDM.loadFromDataBase();
                                clientesDM.fireTableDataChanged();
                            }
                        }
                )
        );
         
         columns.add(
                new JTableColumn<Clientes>(
                        6, 
                        "Fecha Registro", 
                        "cliFechareg", 
                        String.class,
                        new DefGSVCol<Clientes>() {
                            public Object getValueAt(Clientes row, int rowIndex) {
                                return row.getCliFechareg()!=null? FechasUtil.format(row.getCliFechareg()):"" ;
                            }
                        }
                )
        );        
        
    }
    
    public void buscar(){
        try{
            String filtro = jTFFiltro.getText();
            clientesDM.setFiltro(filtro);
            
            clientesDM.loadFromDataBase();
            clientesDM.fireTableDataChanged();
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
        jLabelTit = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTFFiltro = new javax.swing.JTextField();
        jBtnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnHistServ1 = new javax.swing.JButton();
        btnHistServ = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

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

        jLabelTit.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelTit.setText("LISTA DE PERSONAS");
        jPanel2.add(jLabelTit, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTFFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFFiltroActionPerformed(evt);
            }
        });
        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });
        jPanel4.add(jTFFiltro, java.awt.BorderLayout.CENTER);

        jBtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-search.png"))); // NOI18N
        jBtnBuscar.setText("Buscar");
        jBtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarActionPerformed(evt);
            }
        });
        jPanel4.add(jBtnBuscar, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.GridLayout(8, 1));

        btnHistServ1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnHistServ1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Plus_25px.png"))); // NOI18N
        btnHistServ1.setText("Nuevo");
        btnHistServ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistServ1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnHistServ1);

        btnHistServ.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnHistServ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-view_details.png"))); // NOI18N
        btnHistServ.setText("Historia");
        btnHistServ.setEnabled(false);
        btnHistServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistServActionPerformed(evt);
            }
        });
        jPanel3.add(btnHistServ);

        btnFacturar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnFacturar.setText("Facturar");
        btnFacturar.setEnabled(false);
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });
        jPanel3.add(btnFacturar);

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
        //this.setVisible(false);
        SmartFactMain farmaApp = (SmartFactMain)this.root;
        farmaApp.logicaClosePane(this.getClass().getName()+String.valueOf(tipo));

    }//GEN-LAST:event_btnCloseActionPerformed

    private void jBtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_jBtnBuscarActionPerformed

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        buscar();
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        
        
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try{
            buscar();
        }
        catch(Throwable ex){
            showMsgError(ex);
        }        
    }//GEN-LAST:event_formWindowOpened

    private void btnHistServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistServActionPerformed
        try{
            int[] rows = jTableMain.getSelectedRows();            
            Clientes cliente = clientesDM.getValueAt(rows[0]);
            HistServCliFrame histServCliFrame = new HistServCliFrame(cliente.getCliId());
            histServCliFrame.setPreferredSize(new Dimension(800, 500));
            histServCliFrame.pack();
            histServCliFrame.centerOnScreen();
            histServCliFrame.setVisible(true);
        }
        catch(Throwable ex){
            showMsgError(ex);
        }        
    }//GEN-LAST:event_btnHistServActionPerformed

    private void jTFFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFFiltroActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        
        try{
            EstadoAPP estadoApp =SmartFactMain.estadosApp.get(FacturaVentaFrame.class.getName()+"1");
            FacturaVentaFrame fvframe = (FacturaVentaFrame)estadoApp.getFrame();     

            int[] rows = jTableMain.getSelectedRows();
            Clientes cliente = clientesDM.getValueAt(rows[0]);

            if (cliente.getCliCi().trim().length()>0){
                fvframe.loadDatosCli(cliente.getCliCi());
                ((SmartFactMain)root).logicaOpenPane(estadoApp);
            }        
            else{
                showMsg("No se puede crear factura, no tiene asignado CI/RUC");
            }
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
        
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void btnHistServ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistServ1ActionPerformed
        NewPersonaFrame newPersonaFrame = new NewPersonaFrame(this, this.tipo);
        newPersonaFrame.centerOnScreen();
        newPersonaFrame.setVisible(true);
    }//GEN-LAST:event_btnHistServ1ActionPerformed
    
    public void afterCloseNewPersona(){
        
        try{
            System.out.println("afterCloseNewPersona----->");
            buscar();
        }
        catch(Throwable ex){
            showMsgError(ex);
        }
       
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnHistServ;
    private javax.swing.JButton btnHistServ1;
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JLabel jLabelTit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTable jTableMain;
    // End of variables declaration//GEN-END:variables
}
