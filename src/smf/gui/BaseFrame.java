/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import smf.util.EntityManagerUtil;
import smf.util.FechasUtil;
import smf.util.datamodels.GenericDataModel;

/**
 *
 * @author mjapon
 */
public class BaseFrame extends javax.swing.JFrame {
    
    protected EntityManager em;
    protected JFrame root;

    /**
     * Creates new form BaseFrame
     */
    public BaseFrame() {
        em = EntityManagerUtil.createEntityManagerFactory();
    }
        
    public void showMsgError(Throwable ex, String msg){
        JOptionPane.showMessageDialog(null, String.format("%s: %s",msg,ex.getMessage()));
        logError(ex);
    }
    
    public void showMsgError(Throwable ex){
        JOptionPane.showMessageDialog(null, "Algo salio mal :-( "+ex.getMessage());
        logError(ex);
    }
    
    public void logError(Throwable ex){
        System.out.println(String.format("Excepcion:%s", ex.getMessage()));
        ex.printStackTrace();
    }
    
    public void logError(Throwable ex, String msg){
        System.out.println(String.format("%s: %s", msg, ex.getMessage()));
        ex.printStackTrace();
    }
    
    public void showMsg(String msg){
        JOptionPane.showMessageDialog(null,msg);
    }
    
    public void centerOnScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
    }
    
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
    
    protected void setupFechasEvent(JComboBox jComboBox, JFormattedTextField desdeTF, JFormattedTextField hastaTF){
        
        
        Integer fechaIndex = jComboBox.getSelectedIndex();
        switch (fechaIndex){
            case 0: //hoy
            {
                desdeTF.setText(FechasUtil.getFechaActual());
                hastaTF.setText(FechasUtil.getFechaActual());
                break;
            }
            case 1:{//ayer
                Calendar cal = Calendar.getInstance().getInstance();
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-1 );
                desdeTF.setText( FechasUtil.format(cal.getTime()) );
                hastaTF.setText( FechasUtil.format(cal.getTime()) );
                break;
            }
            case 2:{//Esta semana
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                desdeTF.setText( FechasUtil.format(cal.getTime()));
                
                cal.add(Calendar.DAY_OF_WEEK, 6);
                hastaTF.setText( FechasUtil.format(cal.getTime()));                
                break;
            }
            case 3:{//Este mes
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
            case 4:{//MES ANTERIOR
                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
            case 5:{//ESTE AÑO
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_MONTH, 1);
                desdeTF.setText( FechasUtil.format(cal.getTime()));

                cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
                hastaTF.setText( FechasUtil.format(cal.getTime()));
                break;
            }
        }
    }
    
    public JFrame getRoot() {
           return root;
    }

    public void setRoot(JFrame root) {
         this.root = root;
    }
    
    public void closeFrame(){
        SmartFactMain farmaApp = (SmartFactMain)this.root;
        farmaApp.logicaClosePane(this.getClass().getName());
    }
    
    public boolean showConfirmMsg(String msg){
        int response = JOptionPane.showConfirmDialog(null, msg);
        return response == JOptionPane.YES_OPTION;
    }
    
    public void updateBorderTable(JPanel panel, GenericDataModel datamodel){
        
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nro("+datamodel.getItems().size() +")"  , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        
    }
    
    public void showSystemTrayMsg(String msg){
        
        SmartFactMain.showSystemTrayMsg(msg);
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
