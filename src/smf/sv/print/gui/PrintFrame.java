/*     */ package smf.sv.print.gui;
/*     */ 
/*     */ import smf.sv.print.util.CadsUtil;
/*     */ import smf.sv.print.util.Ctes;
/*     */ import smf.sv.print.util.MatrixPrinterUtil;
/*     */ import smf.sv.print.util.SystemTrayUtil;
/*     */ import smf.sv.print.util.TextPrinterUtil;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ //import mupi.af.print.UtilidadCodBarrasImpresion;
/*     */ 
/*     */ 
/*     */ public class PrintFrame
/*     */   extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = 139042586036901262L;
/*     */   private JButton btnImprimir;
/*     */   private JButton btnCerrar;
/*     */   private JComboBox<String> impComboBox;
/*     */   private JComboBox<String> tipoImpComboBox;
/*     */   private JTextArea txtPrintTextArea;
/*     */   private MatrixPrinterUtil matrixPrinterUtil;
/*  41 */   private String dataToPrint = "";

/*  43 */   private String modoImpresion = "";
/*     */   
/*     */   public PrintFrame() {
/*  47 */     this.impComboBox = new JComboBox();
/*  48 */     this.tipoImpComboBox = new JComboBox();
/*  49 */     init();
/*     */     
/*  51 */     addComponentListener(new ComponentListener() {
/*     */       public void componentShown(ComponentEvent e) {
/*  53 */         System.out.println("Onshow-->");
/*  54 */         PrintFrame.this.reload_combos();
/*     */       }
/*     */       
/*     */       public void componentResized(ComponentEvent e) {}
/*     */       
/*     */       public void componentMoved(ComponentEvent e) {}
/*     */       
/*     */       public void componentHidden(ComponentEvent e) {} }); }
/*     */   
/*  63 */   public void reload_combos() { String impresoraDef = Ctes.GET_PROPERTY_VALUE("def_printer");
/*  64 */     if (Ctes.PRINTER_COD_SET) {
/*  65 */       System.out.println("se envia ha imprimir en impresora especifica:" + Ctes.PRINTER_COD);
/*  66 */       impresoraDef = Ctes.GET_PRINTER_NAME(Ctes.PRINTER_COD);
/*  67 */       Ctes.CLEAR_PRINTER_COD();
/*     */     }
/*     */     
/*  70 */     System.out.println("Impresora por defecto que se envia es:" + impresoraDef);
/*  71 */     Ctes.LOAD_IMPRESORAS_COMBO_BOX(this.impComboBox, impresoraDef);
/*  72 */     Ctes.LOAD_TIPOS_IMPRESORA_COMBO_BOX(this.tipoImpComboBox);
/*     */   }
/*     */   
/*     */   public void setPrinterCod(String printerCod) {
/*  76 */     Ctes.SET_DEFAULT_PRINTER(printerCod, this.impComboBox);
/*     */   }
/*     */   
/*     */   public void modoTexto() {
/*     */     try {
/*  81 */       System.out.println("Accion doPrint modoTexto----------------->");
/*     */       
/*  83 */       String impresora = this.impComboBox.getSelectedItem().toString();
/*  84 */       String tipoImpresora = this.tipoImpComboBox.getSelectedItem().toString();
/*     */       
/*  86 */       System.out.println("Impresora seleccionada:");
/*  87 */       System.out.println(impresora);
/*  88 */       System.out.println("Tipo de impresora es:");
/*  89 */       System.out.println(tipoImpresora);
/*  90 */       String texto = this.txtPrintTextArea.getText();
/*     */ 
/*  93 */       if ("PLAIN".equalsIgnoreCase(tipoImpresora)) {
/*  94 */         System.out.println("El tipo de impresora es PLAIN");
/*  95 */         TextPrinterUtil printerUtil = new TextPrinterUtil();
/*  96 */         printerUtil.imprimir(impresora, texto);
/*     */       }
/*     */       else {
/*  99 */         //this.matrixPrinterUtil = new MatrixPrinterUtil(impresora, tipoImpresora);
/* 100 */         this.matrixPrinterUtil.print(texto);
/*     */       }
/*     */       
/* 103 */       
/* 104 */       setVisible(false);
/*     */     }
/*     */     catch (Throwable ex) {
/* 107 */       System.out.println("Error al tratar de imprimir");
/* 108 */       System.out.println(ex.getMessage());
/* 109 */       ex.printStackTrace();
/* 110 */       JOptionPane.showMessageDialog(null, "Se produjo un error al tratar de imprimir:" + ex.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void modoGrafico() {
/* 115 */     System.out.println(CadsUtil.getFechaHoraActual() + ":Accion doPrint modoGrafico----------------->");
/* 116 */     String impresora = this.impComboBox.getSelectedItem().toString();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 121 */       Integer anchoPagina = Integer.valueOf(90);
/* 122 */       Integer altoPagina = Integer.valueOf(250);
/*     */       
/* 124 */       String anchoPag = Ctes.GET_PROPERTY_VALUE("ANCHO_PAG_MM");
/* 125 */       String altoPag = Ctes.GET_PROPERTY_VALUE("ALTO_PAG_MM");
/*     */       try {
/* 127 */         anchoPagina = Integer.valueOf(anchoPag);
/*     */       }
/*     */       catch (Throwable ex) {
/* 130 */         anchoPagina = Integer.valueOf(90);
/* 131 */         System.out.println("No se puedo obtener ancho de pagina por defecto se pone 90 mm");
/*     */       }
/*     */       try
/*     */       {
/* 135 */         altoPagina = Integer.valueOf(altoPag);
/*     */       }
/*     */       catch (Throwable ex) {
/* 138 */         altoPagina = Integer.valueOf(250);
/* 139 */         System.out.println("No se puedo obtener alto de pagina por defecto se pone 250 mm");
/*     */       }
/*     */       
/* 142 */       String texto = this.txtPrintTextArea.getText();
/* 143 */       //UtilidadCodBarrasImpresion utilidadCodBarrasImpresion = new UtilidadCodBarrasImpresion(impresora, anchoPagina, altoPagina);
/* 144 */       //utilidadCodBarrasImpresion.imprimir(texto);
///* 145 */       this.webSockInstance.getSystemTrayUtil().showBaloom("Imprimiendo..");
/* 146 */       this.modoImpresion = "";
/* 147 */       setVisible(false);
/*     */     }
/*     */     catch (Throwable ex) {
/* 150 */       System.out.println("Error: " + ex.getMessage());
/* 151 */       JOptionPane.showMessageDialog(null, "Se produjo un error al tratar de imprimir: " + ex.getMessage(), "ERROR AL IMPRIMIR", 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPrint() {
/* 156 */     if ("grafico".equalsIgnoreCase(this.modoImpresion)) {
/* 157 */       modoGrafico();
/*     */     }
/*     */     else {
/* 160 */       modoTexto();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setModoImpresion(String modo) {
/* 165 */     this.modoImpresion = modo;
/*     */   }
/*     */   
/*     */   public void init() {
/* 169 */     setLayout(new BorderLayout());
/* 170 */     setFont(new Font("Arial", 0, 12));
/* 171 */     setIconImage(new ImageIcon(Ctes.RUTA_ICONO_SYS, "omt").getImage());
/*     */     
/* 173 */     JPanel titlePanel = new JPanel(new FlowLayout());
/* 174 */     titlePanel.add(new JLabel("Tipo"));
/* 175 */     titlePanel.add(this.tipoImpComboBox);
/* 176 */     titlePanel.add(new JLabel("Impresora"));
/*     */     
/* 178 */     System.out.println("Se procede a agregar a panel impresora combobox");
/* 179 */     titlePanel.add(this.impComboBox);
/* 180 */     add(titlePanel, "North");
/*     */     
/* 182 */     this.txtPrintTextArea = new JTextArea(this.dataToPrint, 5, 20);
/*     */     
/* 184 */     JScrollPane scrollPane = new JScrollPane(this.txtPrintTextArea);
/* 185 */     add(scrollPane, "Center");
/*     */     
/* 187 */     this.btnImprimir = new JButton("Imprimir");
/* 188 */     this.btnImprimir.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 190 */         PrintFrame.this.doPrint();
/*     */       }
/*     */       
/* 193 */     });
/* 194 */     this.btnCerrar = new JButton("Cerrar");
/* 195 */     final JFrame thisframe = this;
/* 196 */     this.btnCerrar.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 198 */         thisframe.setVisible(false);
/*     */       }
/*     */       
/* 201 */     });
/* 202 */     JPanel southPanel = new JPanel(new FlowLayout());
/* 203 */     southPanel.add(this.btnImprimir);
/* 204 */     southPanel.add(this.btnCerrar);
/*     */     
/* 206 */     add(southPanel, "South");
/* 207 */     setSize(600, 300);
/* 208 */     setTitle("Isyplus: Imprimir");
/* 209 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/* 210 */     setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
/*     */   }
/*     */   
/*     */   public void setDataToPrint(String message) {
/* 214 */     String textprint = message.replace("<nl>", "\n").replace("<np>", "\f");
/* 215 */     this.txtPrintTextArea.setText(textprint);
/* 216 */     this.dataToPrint = textprint;
/*     */   }
/*     */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/gui/PrintFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */