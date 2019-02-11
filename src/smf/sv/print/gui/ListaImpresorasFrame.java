/*     */ package smf.sv.print.gui;
/*     */ 
/*     */ import smf.sv.print.util.Ctes;
/*     */ import smf.sv.print.util.CtesImpresoras;

/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ public class ListaImpresorasFrame
/*     */   extends JFrame
/*     */ {
/*     */   private JComboBox<String> impresorasCmbox;
/*     */   private JPanel mainPanel;
/*     */   private JButton guardarBtn;
/*     */   private JButton cerrarBtn;

/*     */   private List<String> impresorasIsyplus;
/*     */   private List<String> impresorasRegistrarIsyplus;
/*     */   private Map<String, String> impresorasActualizarLocal;
/*     */   
/*     */   public ListaImpresorasFrame( List<String> impresorasIsyplus)
/*     */   {

/*  46 */     this.impresorasIsyplus = new ArrayList();
/*     */     
/*  48 */     this.impresorasRegistrarIsyplus = new ArrayList();
/*  49 */     this.impresorasActualizarLocal = new HashMap();
/*     */     
/*  51 */     this.impresorasIsyplus.add("0;Registrar nuevo");
/*  52 */     this.impresorasIsyplus.addAll(impresorasIsyplus);
/*  53 */     init();
/*     */   }
/*     */   
/*     */   public JComboBox<String> getNewJComboBox(String codigoImpresoraIsyplus)
/*     */   {
/*  58 */     System.out.println("getNewJComboBox------>" + (codigoImpresoraIsyplus != null ? codigoImpresoraIsyplus : "null"));
/*     */     
/*  60 */     this.impresorasCmbox = new JComboBox();
/*  61 */     Ctes.LOAD_JCOMBO_BOX(this.impresorasCmbox, this.impresorasIsyplus);
/*     */     
/*  63 */     Integer index = Integer.valueOf(0);
/*     */     
/*  65 */     if (codigoImpresoraIsyplus != null) {
/*  66 */       for (String printerIsyplus : this.impresorasIsyplus) {
/*  67 */         System.out.println("--->Printer isyplus:" + printerIsyplus);
/*  68 */         String[] codname = printerIsyplus.split(";");
/*  69 */         String cod = codname[0];
/*  70 */         if (cod.equalsIgnoreCase(codigoImpresoraIsyplus)) {
/*  71 */           index = Integer.valueOf(this.impresorasIsyplus.indexOf(printerIsyplus));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  76 */     this.impresorasCmbox.setSelectedIndex(index.intValue() >= 0 ? index.intValue() : 0);
/*  77 */     return this.impresorasCmbox;
/*     */   }
/*     */   
/*     */   public void doGuardar() {
/*  81 */     this.impresorasRegistrarIsyplus = new ArrayList();
/*  82 */     this.impresorasActualizarLocal = new HashMap();
/*     */     
/*     */ 
/*  85 */     Component[] componets = this.mainPanel.getComponents();
/*  86 */     JLabel lastLabel = null;
/*  87 */     Component[] arrayOfComponent1; int j = (arrayOfComponent1 = componets).length; for (int i = 0; i < j; i++) { Component component = arrayOfComponent1[i];
/*  88 */       System.out.println("Iterando por components ---->, class es:" + component.getClass().toString());
/*  89 */       if ((component instanceof JLabel)) {
/*  90 */         lastLabel = (JLabel)component;
/*  91 */         System.out.println("Last label text est:" + lastLabel.getText());
/*     */       }
/*     */       
/*  94 */       if ((component instanceof JComboBox)) {
/*  95 */         System.out.println("Component instance of jcombobox");
/*  96 */         JComboBox<String> jcombo = (JComboBox)component;
/*  97 */         String item = String.valueOf(jcombo.getSelectedItem());
/*  98 */         if ("0;Registrar nuevo".equalsIgnoreCase(item)) {
/*  99 */           if (lastLabel != null) {
/* 100 */             String auxImp = lastLabel.getText();
/* 101 */             this.impresorasRegistrarIsyplus.add(auxImp);
/* 102 */             System.out.println("Se agrega nueva impresora ha listado de nuevo para registro:" + auxImp);
/*     */           }
/*     */           
/*     */         }
/* 106 */         else if (lastLabel != null) {
/* 107 */           String[] partes = item.split(";");
/* 108 */           String cod_impresora = partes[0];
/* 109 */           String nom_impresora = lastLabel.getText();
/* 110 */           this.impresorasActualizarLocal.put(cod_impresora, nom_impresora);
/* 111 */           System.out.println("Se agrega nueva impresora ha mapa para actualizar en local cod_impresora:" + cod_impresora + ", impresora:" + nom_impresora);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 117 */     System.out.println("Valor para impresoras actualizar local es:" + this.impresorasActualizarLocal.entrySet().size());
/*     */     
/* 119 */     CtesImpresoras.CHECK_FILE_EXIST();
/* 120 */     for (Map.Entry<String, String> e : this.impresorasActualizarLocal.entrySet()) {
/* 121 */       CtesImpresoras.SET_PROPERTY_VALUE((String)e.getKey(), (String)e.getValue());
/*     */     }
/*     */     
/* 124 */     System.out.println("Longitud cadena de impresoras registrar isyplus es:" + this.impresorasRegistrarIsyplus.size());
/*     */     
/* 126 */     StringBuffer impresorasGuardarBuffer = new StringBuffer();
/* 127 */     for (String printer : this.impresorasRegistrarIsyplus) {
/* 128 */       impresorasGuardarBuffer.append(printer);
/* 129 */       impresorasGuardarBuffer.append(";");
/*     */     }
/*     */     
/* 132 */     String impresorasGuardar = impresorasGuardarBuffer.toString();
/* 133 */     if (impresorasGuardarBuffer.length() > 0) {
/* 134 */       impresorasGuardar = impresorasGuardarBuffer.substring(0, impresorasGuardarBuffer.length() - 1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 142 */     String webSoccetMessage = "SAVEPRINT<>" + impresorasGuardar;

/*     */     
/* 145 */     JOptionPane.showMessageDialog(null, "Los cambios han sido registrados");
/* 146 */     setVisible(false);
/*     */   }
/*     */   
/*     */   public void init() {
/* 150 */     setLayout(new BorderLayout());
/* 151 */     setFont(new Font("Arial", 0, 12));
/* 152 */     setTitle("Isyplus: Configurar impresoras ISYPLUS");
/* 153 */     setSize(700, 400);
/* 154 */     setIconImage(new ImageIcon(Ctes.RUTA_ICONO_SYS, "omt").getImage());
/*     */     
/* 156 */     List<String> impresorasList = Ctes.GET_LISTA_IMPRESORAS();
/* 157 */     int numFilas = impresorasList.size();
/*     */     
/* 159 */     this.impresorasCmbox = new JComboBox();
/* 160 */     this.mainPanel = new JPanel(new GridLayout(numFilas, 2));
/*     */     
/* 162 */     for (String impresora : impresorasList) {
/* 163 */       this.mainPanel.add(new JLabel(impresora));
/*     */       
/*     */ 
/*     */ 
/* 167 */       String codigoImpresoraProp = CtesImpresoras.GET_PRINTER_COD(impresora);
/*     */       
/* 169 */       System.out.println("Se busca impresora:" + impresora + " en impresoras.properties");
/* 170 */       System.out.println("Valor en impresoras.properties es:" + (codigoImpresoraProp != null ? codigoImpresoraProp : "null"));
/*     */       
/*     */ 
/* 173 */       JComboBox<String> comboImpresorasIsyplus = getNewJComboBox(codigoImpresoraProp);
/*     */       
/* 175 */       this.mainPanel.add(comboImpresorasIsyplus);
/*     */     }
/*     */     
/* 178 */     add(this.mainPanel, "Center");
/*     */     
/* 180 */     this.guardarBtn = new JButton("Guardar");
/* 181 */     this.cerrarBtn = new JButton("Cerrar");
/* 182 */     this.guardarBtn.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 184 */         ListaImpresorasFrame.this.doGuardar();
/*     */       }
/*     */       
/* 187 */     });
/* 188 */     final JFrame thisframe = this;
/* 189 */     this.cerrarBtn.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 191 */         thisframe.setVisible(false);
/*     */       }
/*     */       
/* 194 */     });
/* 195 */     JPanel southPanel = new JPanel(new FlowLayout());
/* 196 */     southPanel.add(this.guardarBtn);
/* 197 */     southPanel.add(this.cerrarBtn);
/* 198 */     add(southPanel, "South");
/*     */     
/* 200 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/* 201 */     setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
/*     */   }
/*     */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/gui/ListaImpresorasFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */