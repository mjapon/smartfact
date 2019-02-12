/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.reportes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import smf.controller.ReportesJpaController;
import smf.entity.Reportes;
import smf.gui.BaseFrame;
import smf.gui.SelectItemFrame;
import smf.util.FechasUtil;
import smf.util.JasperUtil;
import smf.util.ReporteRow;
import smf.util.datamodels.rows.FilaItemBuscar;

/**
 *
 * @author manuel.japon
 */
public class ReportesFrame extends BaseFrame {

    private JFormattedTextField desdeTF;
    private JFormattedTextField hastaTF;
    private ReportesJpaController repsController;
    private List<Reportes> reportesList;
    private List<ReporteRow> reporteRows;
    private Reportes reporteSel;
    private FilaItemBuscar selectedCli;
    private FilaItemBuscar selectedArt;

    private static final String PRM_FEC1 = "fec1";
    private static final String PRM_FEC2 = "fec2";
    private static final String PRM_ART = "art";
    private static final String PRM_CLI = "cli";

    /**
     * Creates new form ReportesFrame
     */
    public ReportesFrame() {
        super();
        initComponents();
        initDateFields();
        repsController = new ReportesJpaController(em);
        jPanelParams.setEnabled(false);
        jPanelParams.setVisible(false);
        this.jBtnDelArt.setVisible(false);
        this.jBtnDelCli.setVisible(false);
        loadReportes();
    }

    public void loadReportes() {
        reportesList = repsController.listAll();
        reporteRows = new ArrayList<>();
        //jPanelCenter.setLayout(new java.awt.GridLayout(reportesList.size(), 1));        
        for (Reportes reporte : reportesList) {
            addReport(reporte);
        }
    }

    public void initDateFields() {
        desdeTF = new JFormattedTextField(createFormatter("##/##/####"));
        hastaTF = new JFormattedTextField(createFormatter("##/##/####"));
        jPanelFechas.add(desdeTF);
        jPanelFechas.add(hastaTF);

        desdeTF.setText(FechasUtil.getFechaActual());
        hastaTF.setText(FechasUtil.getFechaActual());
    }

    public void unSelectOtherReports(Reportes reporte) {
        for (ReporteRow reporteRow : reporteRows) {
            if (reporteRow.getEntity().getRepId().intValue() != reporte.getRepId().intValue()) {
                reporteRow.getTextField().setBackground(new java.awt.Color(255, 255, 255));
            }
        }
    }

    public void setupParamsFields(Reportes reporte) {
        try {
            Map<String, Boolean> mapsParams = repsController.proccesParams(reporte.getRepParams());
            jPanelFechas.setVisible(true);
            jPanelFechas.setEnabled(true);
            jCBFechas.setVisible(true);

            jPanelParamCli.setVisible(true);
            jPanelParamCli.setEnabled(true);

            jPanelParamArt.setVisible(true);
            jPanelParamArt.setEnabled(true);

            int count = 0;

            if (!mapsParams.get(PRM_FEC1) && !mapsParams.get(PRM_FEC2)) {
                jPanelFechas.setVisible(false);
                jPanelFechas.setEnabled(false);
                jCBFechas.setVisible(false);
                count += 1;
            }

            if (!mapsParams.get(PRM_CLI)) {
                jPanelParamCli.setVisible(false);
                jPanelParamCli.setEnabled(false);
                count += 1;
            }

            if (!mapsParams.get(PRM_ART)) {
                jPanelParamArt.setVisible(false);
                jPanelParamArt.setEnabled(false);
                count += 1;
            }

            if (count != 3) {
                jPanelParams.setEnabled(true);
                jPanelParams.setVisible(true);
            } else {
                jPanelParams.setEnabled(false);
                jPanelParams.setVisible(false);
            }
        } catch (Throwable ex) {
            showMsgError(ex);
        }
    }

    public void addReport(Reportes reporte) {

        javax.swing.JTextField jTextField = new javax.swing.JTextField();

        jTextField.setEditable(false);
        jTextField.setBackground(new java.awt.Color(255, 255, 255));
        jTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField.setText(reporte.getRepNombre());
        jTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reporteSel = reporte;
                jTextField.setBackground(new java.awt.Color(153, 204, 255));
                unSelectOtherReports(reporte);
                setupParamsFields(reporte);
            }
        });
        
        ReporteRow reporteRow = new ReporteRow(reporte, jTextField);

        reporteRows.add(reporteRow);
        jPanelCenter.add(jTextField);
    }

    public void doSelectItem(FilaItemBuscar selectedRow, String tipo) {
        if ("A".equalsIgnoreCase(tipo)) {
            selectedArt = selectedRow;
            jTFArticulo.setText(selectedArt.getDescItem());
            this.jBtnDelArt.setVisible(true);
        } else if ("C".equalsIgnoreCase(tipo)) {
            selectedCli = selectedRow;
            jTFCliente.setText(selectedCli.getDescItem());
            this.jBtnDelCli.setVisible(true);
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

        jPanelCenter = new javax.swing.JPanel();
        jPanelNorth = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelSouth = new javax.swing.JPanel();
        jPanelParams = new javax.swing.JPanel();
        jPanelParamFechas = new javax.swing.JPanel();
        jPanelFechas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCBFechas = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanelParamCli = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTFCliente = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jBtnBuscarCli = new javax.swing.JButton();
        jBtnDelCli = new javax.swing.JButton();
        jPanelParamArt = new javax.swing.JPanel();
        jTFArticulo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jBtnBuscarArt = new javax.swing.JButton();
        jBtnDelArt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonCerrar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reportes");
        setMinimumSize(new java.awt.Dimension(303, 600));

        jPanelCenter.setLayout(new java.awt.GridLayout(4, 3));
        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelNorth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Reportes");
        jPanelNorth.add(jLabel1);

        getContentPane().add(jPanelNorth, java.awt.BorderLayout.NORTH);

        jPanelSouth.setLayout(new java.awt.BorderLayout());

        jPanelParams.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros"));
        jPanelParams.setLayout(new java.awt.GridLayout(3, 1));

        jPanelParamFechas.setLayout(new java.awt.GridLayout(1, 3));

        jPanelFechas.setLayout(new java.awt.GridLayout(2, 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Desde:");
        jPanelFechas.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Hasta:");
        jPanelFechas.add(jLabel3);

        jPanelParamFechas.add(jPanelFechas);

        jCBFechas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jCBFechas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoy", "Ayer", "Esta Semana", "Este Mes", "Mes Anterior", "Este Año" }));
        jCBFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBFechasActionPerformed(evt);
            }
        });
        jPanelParamFechas.add(jCBFechas);
        jPanelParamFechas.add(jLabel6);

        jPanelParams.add(jPanelParamFechas);

        jPanelParamCli.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Cliente:");
        jPanelParamCli.add(jLabel4, java.awt.BorderLayout.WEST);

        jTFCliente.setEditable(false);
        jPanelParamCli.add(jTFCliente, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jBtnBuscarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Search_16px.png"))); // NOI18N
        jBtnBuscarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarCliActionPerformed(evt);
            }
        });
        jPanel1.add(jBtnBuscarCli);

        jBtnDelCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Delete_16px.png"))); // NOI18N
        jBtnDelCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDelCliActionPerformed(evt);
            }
        });
        jPanel1.add(jBtnDelCli);

        jPanelParamCli.add(jPanel1, java.awt.BorderLayout.EAST);

        jPanelParams.add(jPanelParamCli);

        jPanelParamArt.setLayout(new java.awt.BorderLayout());

        jTFArticulo.setEditable(false);
        jPanelParamArt.add(jTFArticulo, java.awt.BorderLayout.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Artículo");
        jPanelParamArt.add(jLabel5, java.awt.BorderLayout.WEST);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jBtnBuscarArt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Search_16px.png"))); // NOI18N
        jBtnBuscarArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarArtActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnBuscarArt);

        jBtnDelArt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Delete_16px.png"))); // NOI18N
        jBtnDelArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDelArtActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnDelArt);

        jPanelParamArt.add(jPanel3, java.awt.BorderLayout.EAST);

        jPanelParams.add(jPanelParamArt);

        jPanelSouth.add(jPanelParams, java.awt.BorderLayout.CENTER);

        jButtonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/icons8-close_pane_filled.png"))); // NOI18N
        jButtonCerrar.setText("Cerrar");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Combo Chart_32px.png"))); // NOI18N
        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButtonCerrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCerrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jPanelSouth.add(jPanel2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanelSouth, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBFechasActionPerformed
        setupFechasEvent(jCBFechas, desdeTF, hastaTF);
    }//GEN-LAST:event_jCBFechasActionPerformed

    private void jBtnBuscarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarCliActionPerformed
        try {
            SelectItemFrame selectItemFrame = new SelectItemFrame("C", this);
            selectItemFrame.centerOnScreen();
            selectItemFrame.setVisible(true);
        } catch (Throwable ex) {
            showMsgError(ex);
        }
    }//GEN-LAST:event_jBtnBuscarCliActionPerformed

    private void jBtnBuscarArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarArtActionPerformed

        try {
            SelectItemFrame selectItemFrame = new SelectItemFrame("A", this);
            selectItemFrame.centerOnScreen();
            selectItemFrame.setVisible(true);
        } catch (Throwable ex) {
            showMsgError(ex);
        }

    }//GEN-LAST:event_jBtnBuscarArtActionPerformed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            //showMsg("Generacion de reporte reporte sel:" + (reporteSel !=null ? reporteSel.getRepNombre(): "") );
            Date desde = FechasUtil.parse(desdeTF.getText());
            Date hasta = FechasUtil.parse(hastaTF.getText());

            String pdescparams = String.format("DESDE: %s HASTA: %s", desdeTF.getText(), hastaTF.getText());

            Map<String, Object> params = new HashMap<>();

            params.put("pdesde", desde);
            params.put("phasta", hasta);
            params.put("pdescparams", pdescparams);
            
            
            if (reporteSel != null){                
                String abrRep = reporteSel.getRepAbr();
                if ("CUENTASXCOBRAR".equalsIgnoreCase(abrRep)
                        || "CUENTASXPAGAR".equalsIgnoreCase(abrRep)) {
                    String pwhere = "";
                    if (selectedCli != null) {
                        pdescparams = "CLIENTE:" + selectedCli.getDescItem();
                        //pwhere, pcliid
                        params.put("pcliid", selectedCli.getIdItem());
                        params.put("pdescparams", pdescparams);
                        pwhere = " and f.cli_id = $P{pcliid} ";
                    } else {
                        params.put("pdescparams", "");
                        pwhere = " ";
                    }
                    params.put("pwhere", pwhere);
                } else if ("VENTASLIST".equalsIgnoreCase(abrRep)
                        || "COMPRASLIST".equalsIgnoreCase(abrRep)) {

                    String pwhere = "";
                    if (selectedCli != null) {
                        pdescparams = "CLIENTE:" + selectedCli.getDescItem();
                        //pwhere, pcliid
                        params.put("pcliid", selectedCli.getIdItem());
                        params.put("pdescparams", pdescparams);
                        pwhere = " and f.cli_id = $P{pcliid} ";
                    } else {
                        pdescparams = String.format("DESDE: %s HASTA: %s", desdeTF.getText(), hastaTF.getText());
                        params.put("pdescparams", pdescparams);
                        pwhere = " and f.fact_fecha BETWEEN  $P{pdesde}  AND  $P{phasta} ";
                    }
                    params.put("pwhere", pwhere);
                }                
            }           
            

            JasperUtil.showReporte(params, reporteSel.getRepPath());
        } catch (Throwable ex) {
            logError(ex);
            showMsgError(ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBtnDelCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDelCliActionPerformed
        this.selectedCli = null;
        this.jTFCliente.setText("");
        this.jBtnDelCli.setVisible(false);
    }//GEN-LAST:event_jBtnDelCliActionPerformed

    private void jBtnDelArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDelArtActionPerformed
        this.selectedArt = null;
        this.jTFArticulo.setText("");
        this.jBtnDelArt.setVisible(false);
    }//GEN-LAST:event_jBtnDelArtActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBuscarArt;
    private javax.swing.JButton jBtnBuscarCli;
    private javax.swing.JButton jBtnDelArt;
    private javax.swing.JButton jBtnDelCli;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JComboBox<String> jCBFechas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelFechas;
    private javax.swing.JPanel jPanelNorth;
    private javax.swing.JPanel jPanelParamArt;
    private javax.swing.JPanel jPanelParamCli;
    private javax.swing.JPanel jPanelParamFechas;
    private javax.swing.JPanel jPanelParams;
    private javax.swing.JPanel jPanelSouth;
    private javax.swing.JTextField jTFArticulo;
    private javax.swing.JTextField jTFCliente;
    // End of variables declaration//GEN-END:variables
}
