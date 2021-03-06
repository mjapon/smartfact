/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.contab;

import javax.swing.SwingUtilities;
import smf.controller.CuentacontableJpaController;
import smf.gui.BaseFrame;
import smf.util.SwUtils;
import smf.util.tree.CCNodeItem;
import smf.util.tree.CntContabNode;

/**
 *
 * @author manuel.japon
 */
public class NewCuentaContableFrame extends BaseFrame {

    private PlanCuentasFrame parentFrame;
    private CntContabNode parentNode;
    private CuentacontableJpaController ccController;
    /**
     * Creates new form NewCuentaContableFrame
     */
    public NewCuentaContableFrame(PlanCuentasFrame parent, CntContabNode parentNode) {
        initComponents();        
        this.parentFrame = parent;
        this.parentNode = parentNode;
        ccController = new CuentacontableJpaController(em);
        load();
    }
    
    public void load(){
        CCNodeItem ccparent = this.parentNode.getCuentacontable();
        String cuentaPadre = String.format("%s (%s)", ccparent.getNombre(),
                ccparent.getCodigo()
        );
        jLabelCuentaPadre.setText( cuentaPadre );
        jTFCodParent.setText( ccparent.getCodigo()+"." );
        jTFCodigo.setText("");
        jRBTCDetalle.setSelected(true);
        jTFNombre.setText("");
                
        try{
            String max = ccController.getNext(parentNode.getCuentacontable().getIdDb().intValue());
            jTFCodigo.setText(max);
        }
        catch(Throwable ex){
            logError(ex);
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

        jLabel1 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelCuentaPadre = new javax.swing.JLabel();
        jTFCodigo = new javax.swing.JTextField();
        jTFNombre = new javax.swing.JTextField();
        jRBTCTotal = new javax.swing.JRadioButton();
        jRBTCDetalle = new javax.swing.JRadioButton();
        jTFCodParent = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jBtnSave = new javax.swing.JButton();
        jBtnCerrar = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTitle.setText("Crear cuenta contable");
        jPanel1.add(jLabelTitle);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jLabel2.setText("Cuenta padre:");

        jLabel3.setText("Código:");

        jLabel4.setText("Nombre:");

        jLabel5.setText("Tipo:");

        jLabelCuentaPadre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        buttonGroup1.add(jRBTCTotal);
        jRBTCTotal.setText("TOTAL");

        buttonGroup1.add(jRBTCDetalle);
        jRBTCDetalle.setText("DETALLE");

        jTFCodParent.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFNombre)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTFCodParent, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jRBTCTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRBTCDetalle)))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addComponent(jLabelCuentaPadre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelCuentaPadre)
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFCodParent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRBTCTotal)
                        .addComponent(jRBTCDetalle)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(4, 1));

        jBtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Save_20px.png"))); // NOI18N
        jBtnSave.setText("Guardar");
        jBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnSave);

        jBtnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smf/gui/icons/Close Pane_20px.png"))); // NOI18N
        jBtnCerrar.setText("Cerrar");
        jBtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCerrarActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnCerrar);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveActionPerformed
        try{
            if (SwUtils.TF_EMPTY(jTFCodigo)){
                showMsg("Debe ingresar el código de la cuenta contable");
                return;
            }
            else if (SwUtils.TF_EMPTY(jTFNombre)){
                showMsg("Debe ingresar el nombre de la cuenta contable");
                return;
            }
            
            CCNodeItem ccparent = this.parentNode.getCuentacontable();
            String cod = String.format("%s.%s", ccparent.getCodigo(),jTFCodigo.getText());
            String nombre = jTFNombre.getText().trim().toUpperCase();
            Character tipo = 'D';
            if (jRBTCTotal.isSelected()){
                tipo = 'T';
            }
            ccController.crear(ccparent.getIdDb(), cod, nombre, tipo );
            parentFrame.initTree();
            parentFrame.expandPath(cod);
            setVisible(false);
            showSystemTrayMsg(" Guardado exitoso ");
        }
        catch(Throwable ex){
            showMsgError(ex);
        }        
    }//GEN-LAST:event_jBtnSaveActionPerformed

    private void jBtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCerrarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jBtnCerrarActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        
        
        
    }//GEN-LAST:event_formFocusGained

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        System.out.println("Windows activate event-->");
        SwingUtilities.invokeLater(() -> {
            System.out.println(" focus-------> ");
            this.jTFNombre.requestFocus();
        });
        
    }//GEN-LAST:event_formWindowActivated
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtnCerrar;
    private javax.swing.JButton jBtnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelCuentaPadre;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRBTCDetalle;
    private javax.swing.JRadioButton jRBTCTotal;
    private javax.swing.JTextField jTFCodParent;
    private javax.swing.JTextField jTFCodigo;
    private javax.swing.JTextField jTFNombre;
    // End of variables declaration//GEN-END:variables
}
