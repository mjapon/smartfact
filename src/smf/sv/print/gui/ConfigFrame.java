/*     */ package smf.sv.print.gui;
/*     */ 
/*     */ import smf.sv.print.util.Ctes;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigFrame
/*     */   extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = -1951754723955497380L;
/*     */   private JPanel mainPanel;
/*     */   private JComboBox<String> impresorasCmbox;
/*     */   private JComboBox<String> tiposImpCmbox;
/*     */   private JComboBox<String> tiposLetra;
/*     */   private JComboBox<String> tiposPitch;
/*     */   private JComboBox<String> tiposEspacioLineas;
/*     */   private JTextField impresoraDefTextField;
/*     */   private JTextField anchoPagTextField;
/*     */   private JTextField altoPagTextField;
/*     */   private JPanel impresoraDefPanel;
/*     */   private JCheckBox printDirectCheckBox;
/*     */   private JCheckBox condensedCheckBox;
/*     */   private JButton guardarBtn;
/*     */   private JButton cerrarBtn;
/*     */   
/*     */   public ConfigFrame()
/*     */   {
/*  52 */     init();
/*     */     
/*  54 */     addComponentListener(new ComponentListener() {
/*     */       public void componentShown(ComponentEvent e) {
/*  56 */         System.out.println("Onshow config-->");
/*  57 */         ConfigFrame.this.initCombos();
/*  58 */         ConfigFrame.this.loadDatosTipoPrint();
/*  59 */         ConfigFrame.this.loadPrintDirect();
/*     */       }
/*     */       
/*     */ 
/*     */       public void componentResized(ComponentEvent e) {}
/*     */       
/*     */ 
/*     */       public void componentMoved(ComponentEvent e) {}
/*     */       
/*     */       public void componentHidden(ComponentEvent e) {}
/*     */     });
/*     */   }
/*     */   
/*     */   public void do_guardar()
/*     */   {
/*  74 */     Ctes.SET_PROPERTY_VALUE("def_printer", this.impresoraDefTextField.getText());
/*  75 */     Ctes.SET_PROPERTY_VALUE("def_printer_type", this.tiposImpCmbox.getSelectedItem().toString());
/*  76 */     Ctes.SET_PROPERTY_VALUE("def_printer_font", this.tiposLetra.getSelectedItem().toString());
/*  77 */     Ctes.SET_PROPERTY_VALUE("def_printer_pitch", this.tiposPitch.getSelectedItem().toString());
/*  78 */     Ctes.SET_PROPERTY_VALUE("def_printer_space", this.tiposEspacioLineas.getSelectedItem().toString());
/*  79 */     Ctes.SET_PROPERTY_VALUE("ANCHO_PAG_MM", this.anchoPagTextField.getText());
/*  80 */     Ctes.SET_PROPERTY_VALUE("ALTO_PAG_MM", this.altoPagTextField.getText());
/*     */     
/*  82 */     if (this.printDirectCheckBox.isSelected()) {
/*  83 */       Ctes.SET_PROPERTY_VALUE("printer_direct", "si");
/*     */     }
/*     */     else {
/*  86 */       Ctes.SET_PROPERTY_VALUE("printer_direct", "no");
/*     */     }
/*     */     
/*  89 */     if (this.condensedCheckBox.isSelected()) {
/*  90 */       Ctes.SET_PROPERTY_VALUE("def_printer_condensed", "si");
/*     */     }
/*     */     else {
/*  93 */       Ctes.SET_PROPERTY_VALUE("def_printer_condensed", "no");
/*     */     }
/*     */     
/*  96 */     JOptionPane.showMessageDialog(null, "Los cambios han sido registrados");
/*  97 */     setVisible(false);
/*     */   }
/*     */   
/*     */   public void initCombos() {
/* 101 */     String impresoraDef = Ctes.GET_PROPERTY_VALUE("def_printer");
/* 102 */     Ctes.LOAD_IMPRESORAS_COMBO_BOX(this.impresorasCmbox, impresoraDef);
/* 103 */     Ctes.LOAD_TIPOS_IMPRESORA_COMBO_BOX(this.tiposImpCmbox);
/* 104 */     this.impresoraDefTextField.setText(impresoraDef);
/*     */     
/* 106 */     String altoDef = Ctes.GET_PROPERTY_VALUE("ALTO_PAG_MM");
/* 107 */     String anchoDef = Ctes.GET_PROPERTY_VALUE("ANCHO_PAG_MM");
/*     */     
/* 109 */     this.altoPagTextField.setText(altoDef);
/* 110 */     this.anchoPagTextField.setText(anchoDef);
/*     */   }
/*     */   
/*     */   public void loadDatosTipoPrint() {
/*     */     try {
/* 115 */       String tipoImpresora = this.tiposImpCmbox.getSelectedItem().toString();
/* 116 */       //Ctes.LOAD_TIPOS_LETRA_COMBO_BOX(this.tiposLetra, tipoImpresora);
/* 117 */       //Ctes.LOAD_PITCH_COMBO_BOX(this.tiposPitch, tipoImpresora);
/* 118 */       //Ctes.LOAD_INTERSPACE_COMBO_BOX(this.tiposEspacioLineas, tipoImpresora);
/*     */       
/* 120 */       String condensed = Ctes.GET_PROPERTY_VALUE("def_printer_condensed");
/* 121 */       if ("si".equalsIgnoreCase(condensed)) {
/* 122 */         this.condensedCheckBox.setSelected(true);
/*     */       }
/*     */       else {
/* 125 */         this.condensedCheckBox.setSelected(false);
/*     */       }
/*     */     }
/*     */     catch (Throwable ex) {
/* 129 */       System.out.println("Error al tratar de cargar datos de tipo de impresora");
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadPrintDirect() {
/* 134 */     String printDirect = Ctes.GET_PROPERTY_VALUE("printer_direct");
/* 135 */     if ("si".equalsIgnoreCase(printDirect)) {
/* 136 */       this.printDirectCheckBox.setSelected(true);
/*     */     }
/*     */     else {
/* 139 */       this.printDirectCheckBox.setSelected(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void init() {
/* 144 */     setLayout(new BorderLayout());
/* 145 */     setFont(new Font("Arial", 0, 12));
/* 146 */     setTitle("Isyplus: Configurar valores por defecto");
/* 147 */     setSize(700, 400);
/* 148 */     setIconImage(new ImageIcon(Ctes.RUTA_ICONO_SYS, "omt").getImage());
/*     */     
/* 150 */     this.impresorasCmbox = new JComboBox();
/* 151 */     this.tiposImpCmbox = new JComboBox();
/* 152 */     this.tiposPitch = new JComboBox();
/* 153 */     this.tiposLetra = new JComboBox();
/* 154 */     this.tiposEspacioLineas = new JComboBox();
/* 155 */     this.printDirectCheckBox = new JCheckBox();
/* 156 */     this.condensedCheckBox = new JCheckBox();
/* 157 */     this.anchoPagTextField = new JTextField();
/* 158 */     this.altoPagTextField = new JTextField();
/*     */     
/* 160 */     this.mainPanel = new JPanel(new GridLayout(10, 2));
/* 161 */     this.mainPanel.add(new JLabel("Impresora por defecto:"));
/* 162 */     this.mainPanel.add(this.impresorasCmbox);
/* 163 */     this.impresoraDefTextField = new JTextField();
/*     */     
/* 165 */     this.mainPanel.add(new JLabel("--"));
/* 166 */     this.mainPanel.add(this.impresoraDefTextField);
/*     */     
/*     */ 
/*     */ 
/* 170 */     this.mainPanel.add(new JLabel("Tipo de impresora:"));
/* 171 */     this.mainPanel.add(this.tiposImpCmbox);
/*     */     
/* 173 */     this.mainPanel.add(new JLabel("Tipo de letra:"));
/* 174 */     this.mainPanel.add(this.tiposLetra);
/*     */     
/* 176 */     this.mainPanel.add(new JLabel("Pitch (A mayor valor menor espacio entre letras):"));
/* 177 */     this.mainPanel.add(this.tiposPitch);
/*     */     
/* 179 */     this.mainPanel.add(new JLabel("Espacio entre líneas:"));
/* 180 */     this.mainPanel.add(this.tiposEspacioLineas);
/*     */     
/* 182 */     this.mainPanel.add(new JLabel("Condensado:"));
/* 183 */     this.mainPanel.add(this.condensedCheckBox);
/*     */     
/* 185 */     this.mainPanel.add(new JLabel("Ancho de Página (mm):"));
/* 186 */     this.mainPanel.add(this.anchoPagTextField);
/*     */     
/* 188 */     this.mainPanel.add(new JLabel("Alto de Página (mm):"));
/* 189 */     this.mainPanel.add(this.altoPagTextField);
/*     */     
/* 191 */     this.mainPanel.add(new JLabel("Imprimir sin preguntar:"));
/* 192 */     this.mainPanel.add(this.printDirectCheckBox);
/*     */     
/* 194 */     this.tiposImpCmbox.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 196 */         System.out.println("tiposImpComboBox action performed--->");
/* 197 */         ConfigFrame.this.loadDatosTipoPrint();
/*     */       }
/*     */       
/* 200 */     });
/* 201 */     this.impresorasCmbox.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 203 */         System.out.println("impresorascombobos action performed--->");
/* 204 */         ConfigFrame.this.impresoraDefTextField.setText(String.valueOf(ConfigFrame.this.impresorasCmbox.getSelectedItem()));
/*     */       }
/*     */       
/* 207 */     });
/* 208 */     add(this.mainPanel, "Center");
/*     */     
/* 210 */     this.guardarBtn = new JButton("Guardar");
/* 211 */     this.cerrarBtn = new JButton("Cerrar");
/* 212 */     this.guardarBtn.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 214 */         ConfigFrame.this.do_guardar();
/*     */       }
/*     */       
/* 217 */     });
/* 218 */     final JFrame thisframe = this;
/* 219 */     this.cerrarBtn.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 221 */         thisframe.setVisible(false);
/*     */       }
/*     */       
/* 224 */     });
/* 225 */     JPanel southPanel = new JPanel(new FlowLayout());
/* 226 */     southPanel.add(this.guardarBtn);
/* 227 */     southPanel.add(this.cerrarBtn);
/*     */     
/* 229 */     add(southPanel, "South");
/*     */     
/* 231 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/* 232 */     setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
/*     */   }
/*     */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/gui/ConfigFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */