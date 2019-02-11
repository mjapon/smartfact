/*     */ package smf.sv.print.util;
/*     */ 

/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;

/*     */ 
/*     */ 
/*     */ public class Ctes
/*     */ {

/*  27 */   public static String RUTA_RAIZ = "C:\\Serviestudios\\IsyplusPrint";
/*  28 */   public static String RUTA_PROPERTIES = RUTA_RAIZ + "\\params.properties";
/*  29 */   public static String RUTA_PROPERTIES_IMPRESORA = RUTA_RAIZ + "\\impresoras.properties";
/*  30 */   public static String RUTA_ICONO_SYS = RUTA_RAIZ + "\\ico\\isyplus.png";
/*  31 */   public static String NOMBRES_SYS = "Isyplus, software de impresión";
/*     */   
/*     */   public static final String KEY_DEF_PRINTER = "def_printer";
/*     */   
/*     */   public static final String KEY_DEF_PRINTER_TYPE = "def_printer_type";
/*     */   public static final String KEY_DEF_PITCH = "def_printer_pitch";
/*     */   public static final String KEY_DEF_FONT = "def_printer_font";
/*     */   public static final String KEY_DEF_SPACE = "def_printer_space";
/*     */   public static final String KEY_DEF_CONDENSED = "def_printer_condensed";
/*     */   public static final String KEY_PRINT_DIRECT = "printer_direct";
/*     */   public static final String KEY_WIDTH_PAGE = "ANCHO_PAG_MM";
/*     */   public static final String KEY_HEIGHT_PAGE = "ALTO_PAG_MM";
/*  43 */   public static String PRINTER_COD = "";
/*  44 */   public static int PRINTER_COD_USED_COUNT = 0;
/*  45 */   public static boolean PRINTER_COD_SET = false;
/*     */   
/*     */   public static void SET_RUTA_PROPERTIES(String codigoEmpresa) {
/*  48 */     if ((codigoEmpresa != null) && (codigoEmpresa.trim().length() > 0))
/*     */     {
/*  50 */       RUTA_PROPERTIES = RUTA_RAIZ + "\\params_" + String.valueOf(codigoEmpresa) + ".properties";
/*     */       try {
/*  52 */         File yourFile = new File(RUTA_PROPERTIES);
/*  53 */         if (!yourFile.exists()) {
/*  54 */           yourFile.createNewFile();
/*     */         }
/*     */       }
/*     */       catch (Throwable ex) {
/*  58 */         System.out.println("Error al tratar de cambiar la ruta del archivo de propiedades: " + ex.getMessage());
/*  59 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public static void SET_PROPERTY_VALUE(String property, String value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_2
/*     */     //   2: new 166	java/io/FileInputStream
/*     */     //   5: dup
/*     */     //   6: getstatic 88	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES	Ljava/lang/String;
/*     */     //   9: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   12: astore_2
/*     */     //   13: new 169	java/util/Properties
/*     */     //   16: dup
/*     */     //   17: invokespecial 171	java/util/Properties:<init>	()V
/*     */     //   20: astore_3
/*     */     //   21: aload_3
/*     */     //   22: aload_2
/*     */     //   23: invokevirtual 172	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   26: new 176	java/io/FileOutputStream
/*     */     //   29: dup
/*     */     //   30: getstatic 88	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES	Ljava/lang/String;
/*     */     //   33: invokespecial 178	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
/*     */     //   36: astore 4
/*     */     //   38: aload_3
/*     */     //   39: aload_0
/*     */     //   40: aload_1
/*     */     //   41: invokevirtual 179	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   44: pop
/*     */     //   45: aload_3
/*     */     //   46: aload 4
/*     */     //   48: aconst_null
/*     */     //   49: invokevirtual 183	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
/*     */     //   52: aload 4
/*     */     //   54: invokevirtual 187	java/io/FileOutputStream:close	()V
/*     */     //   57: goto +59 -> 116
/*     */     //   60: astore_3
/*     */     //   61: getstatic 61	com/serviestudios/print/util/Ctes:LOG	Lorg/apache/log4j/Logger;
/*     */     //   64: ldc -66
/*     */     //   66: aload_3
/*     */     //   67: invokevirtual 192	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   70: aload_2
/*     */     //   71: invokevirtual 196	java/io/FileInputStream:close	()V
/*     */     //   74: goto +61 -> 135
/*     */     //   77: astore 6
/*     */     //   79: getstatic 61	com/serviestudios/print/util/Ctes:LOG	Lorg/apache/log4j/Logger;
/*     */     //   82: ldc -59
/*     */     //   84: aload 6
/*     */     //   86: invokevirtual 192	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   89: goto +46 -> 135
/*     */     //   92: astore 5
/*     */     //   94: aload_2
/*     */     //   95: invokevirtual 196	java/io/FileInputStream:close	()V
/*     */     //   98: goto +15 -> 113
/*     */     //   101: astore 6
/*     */     //   103: getstatic 61	com/serviestudios/print/util/Ctes:LOG	Lorg/apache/log4j/Logger;
/*     */     //   106: ldc -59
/*     */     //   108: aload 6
/*     */     //   110: invokevirtual 192	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   113: aload 5
/*     */     //   115: athrow
/*     */     //   116: aload_2
/*     */     //   117: invokevirtual 196	java/io/FileInputStream:close	()V
/*     */     //   120: goto +15 -> 135
/*     */     //   123: astore 6
/*     */     //   125: getstatic 61	com/serviestudios/print/util/Ctes:LOG	Lorg/apache/log4j/Logger;
/*     */     //   128: ldc -59
/*     */     //   130: aload 6
/*     */     //   132: invokevirtual 192	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   135: return
/*     */     // Line number table:
/*     */     //   Java source line #65	-> byte code offset #0
/*     */     //   Java source line #67	-> byte code offset #2
/*     */     //   Java source line #68	-> byte code offset #13
/*     */     //   Java source line #69	-> byte code offset #21
/*     */     //   Java source line #70	-> byte code offset #26
/*     */     //   Java source line #71	-> byte code offset #38
/*     */     //   Java source line #72	-> byte code offset #45
/*     */     //   Java source line #73	-> byte code offset #52
/*     */     //   Java source line #74	-> byte code offset #57
/*     */     //   Java source line #75	-> byte code offset #60
/*     */     //   Java source line #76	-> byte code offset #61
/*     */     //   Java source line #80	-> byte code offset #70
/*     */     //   Java source line #81	-> byte code offset #74
/*     */     //   Java source line #82	-> byte code offset #77
/*     */     //   Java source line #83	-> byte code offset #79
/*     */     //   Java source line #78	-> byte code offset #92
/*     */     //   Java source line #80	-> byte code offset #94
/*     */     //   Java source line #81	-> byte code offset #98
/*     */     //   Java source line #82	-> byte code offset #101
/*     */     //   Java source line #83	-> byte code offset #103
/*     */     //   Java source line #85	-> byte code offset #113
/*     */     //   Java source line #80	-> byte code offset #116
/*     */     //   Java source line #81	-> byte code offset #120
/*     */     //   Java source line #82	-> byte code offset #123
/*     */     //   Java source line #83	-> byte code offset #125
/*     */     //   Java source line #86	-> byte code offset #135
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	136	0	property	String
/*     */     //   0	136	1	value	String
/*     */     //   1	116	2	in	FileInputStream
/*     */     //   20	26	3	props	Properties
/*     */     //   60	7	3	ex	Throwable
/*     */     //   36	17	4	out	java.io.FileOutputStream
/*     */     //   92	22	5	localObject	Object
/*     */     //   77	8	6	ex	Throwable
/*     */     //   101	8	6	ex	Throwable
/*     */     //   123	8	6	ex	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   2	57	60	java/lang/Throwable
/*     */     //   70	74	77	java/lang/Throwable
/*     */     //   2	70	92	finally
/*     */     //   94	98	101	java/lang/Throwable
/*     */     //   116	120	123	java/lang/Throwable
/*     */   }
/*     */   
/*     */   public static String GET_PROPERTY_VALUE(String prop)
/*     */   {
/*  89 */     InputStream is = null;
/*     */     try {
/*  91 */       Properties properties = new Properties();
/*  92 */       is = new FileInputStream(RUTA_PROPERTIES);
/*  93 */       properties.load(is);
/*  94 */       return properties.getProperty(prop, "");
/*     */     }
/*     */     catch (Throwable ex) {
/*  97 */     
/*     */     }
/*     */     finally {
/*     */       try {
/* 101 */         is.close();
/*     */       }
/*     */       catch (Throwable ex) {
/* 104 */        
/*     */       }
/*     */     }
/* 107 */     return "";
/*     */   }
/*     */   
/*     */   public static void TERMINAR_APLICACION() {
    
    System.exit(0);
/*     */   }
/*     */   
/*     */   public static String GET_HOST_NAME() {
/*     */     try {
/* 117 */       InetAddress localMachine = InetAddress.getLocalHost();
/* 118 */       String hostname = localMachine.getHostName();
/* 119 */       return hostname.replaceAll("\\s", "");
/*     */     }
/*     */     catch (Throwable ex) {
            }
/* 123 */     return "";
}

            public static void LOAD_JCOMBO_BOX(JComboBox comboBox, List<String> items)
/*     */   {
/* 128 */     comboBox.removeAllItems();
/* 129 */     for (String item : items) {
/* 130 */       comboBox.addItem(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int GET_INDEX_ITEM_FROM_LIST(String itemToFind, List<String> items) {
/* 135 */     for (String item : items) {
/* 136 */       if (item.equalsIgnoreCase(itemToFind)) {
/* 137 */         return items.indexOf(item);
/*     */       }
/*     */     }
/* 140 */     return -1;
/*     */   }
/*     */   
/*     */   public static List<String> GET_LISTA_IMPRESORAS() {
/* 144 */     PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
/* 145 */     List<String> listaImpresoras = new ArrayList();
/* 146 */     int j = printServices.length;
/* 147 */     for (int i = 0; i < j; i++) {
/* 148 */       PrintService printService = printServices[i];
/* 149 */       listaImpresoras.add(printService.getName());
/*     */     }
/* 151 */     return listaImpresoras;
/*     */   }
/*     */   
/*     */   public static List<String> GET_TIPOS_IMPRESORAS() {
/* 155 */     List<String> tiposImpresoras = new ArrayList();
/* 156 */     tiposImpresoras.add("PLAIN");
/* 157 */     tiposImpresoras.add("IBM-PROPRINTER");
/* 158 */     tiposImpresoras.add("IBM-PROPRINTER-III");
/* 159 */     tiposImpresoras.add("IBM-PROPRINTER-XL");
/* 160 */     tiposImpresoras.add("IBM-PROPRINTER-XL24");
/* 161 */     tiposImpresoras.add("IBM-PPDS");
/* 162 */     tiposImpresoras.add("EPSON-ESCP2");
/* 163 */     tiposImpresoras.add("EPSON-ESCP");
/* 164 */     tiposImpresoras.add("EPSON-9PIN-ESCP");
/* 165 */     tiposImpresoras.add("EPSON-FX850");
/* 166 */     tiposImpresoras.add("EPSON-FX1050");
/* 167 */     tiposImpresoras.add("EPSON-LQ1070");
/* 168 */     tiposImpresoras.add("EPSON-TM-T88V");
/* 169 */     tiposImpresoras.add("HP-PCL");
/* 170 */     tiposImpresoras.add("HP-PCL3");
/* 171 */     tiposImpresoras.add("HP-PCL5");
/* 172 */     tiposImpresoras.add("DIABLO");
/* 173 */     tiposImpresoras.add("PLAIN");
/* 174 */     tiposImpresoras.add("OKI-MICROLINE");
/* 175 */     tiposImpresoras.add("P-SERIES");
/* 176 */     tiposImpresoras.add("PanasonicKX_P3123");
/* 177 */     tiposImpresoras.add("PanasonicKX_P1150");
/* 178 */     tiposImpresoras.add("PRUEBA");
/* 179 */     return tiposImpresoras;
/*     */   }
/*     */   
/*     */  
/*     */   
/*     */  
/*     */   
/*     */   public static void LOAD_PREGUNTAR_PRINT(JCheckBox checkBox) {
/* 204 */     String print_direct = GET_PROPERTY_VALUE("printer_direct");
/* 205 */     if ("si".equalsIgnoreCase(print_direct)) {
/* 206 */       checkBox.setSelected(true);
/*     */     }
/*     */     else {
/* 209 */       checkBox.setSelected(false);
/*     */     }
/*     */   }
/*     */   
/*     */  
/*     */   
/*     */  
/*     */   
/*     */  
/*     */   
/*     */   public static void LOAD_IMPRESORAS_COMBO_BOX(JComboBox<String> impresorasCmbox, String impresoraDef) {
/* 241 */     List<String> impresorasList = GET_LISTA_IMPRESORAS();
/* 242 */     System.out.println("Lista de impresoras que se obtuvo es:");
/* 243 */     for (String impresora : impresorasList) {
/* 244 */       System.out.println("Impresora:" + impresora);
/*     */     }
/*     */     
/* 247 */     Integer indexImpDef = Integer.valueOf(GET_INDEX_ITEM_FROM_LIST(impresoraDef, impresorasList));
/*     */     
/* 249 */     if (indexImpDef.intValue() == -1)
/*     */     {
/* 251 */       if ((impresoraDef != null) && (impresoraDef.trim().length() > 0)) {
/* 252 */         impresorasList.add(impresoraDef);
/*     */       }
/*     */     }
/*     */     
/* 256 */     indexImpDef = Integer.valueOf(GET_INDEX_ITEM_FROM_LIST(impresoraDef, impresorasList));
/*     */     
/* 258 */     LOAD_JCOMBO_BOX(impresorasCmbox, impresorasList);
/*     */     
/*     */ 
/* 261 */     impresorasCmbox.setSelectedIndex(indexImpDef.intValue() >= 0 ? indexImpDef.intValue() : 0);
/*     */   }
/*     */   
/*     */   public static void LOAD_TIPOS_IMPRESORA_COMBO_BOX(JComboBox<String> tiposImpCmbox) {
/* 265 */     List<String> tiposImpList = GET_TIPOS_IMPRESORAS();
/* 266 */     LOAD_JCOMBO_BOX(tiposImpCmbox, tiposImpList);
/* 267 */     String tipoImpresoraDef = GET_PROPERTY_VALUE("def_printer_type");
/* 268 */     Integer indexTipoImpDef = Integer.valueOf(GET_INDEX_ITEM_FROM_LIST(tipoImpresoraDef, tiposImpList));
/* 269 */     tiposImpCmbox.setSelectedIndex(indexTipoImpDef.intValue() >= 0 ? indexTipoImpDef.intValue() : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String GET_PRINTER_NAME(String printerCod)
/*     */   {
/* 281 */     return CtesImpresoras.GET_PROPERTY_VALUE(printerCod);
/*     */   }
/*     */   
/*     */   public static void SET_DEFAULT_PRINTER(String printerCod, JComboBox<String> impresorasCmbox) {
/* 285 */     String printerName = CtesImpresoras.GET_PROPERTY_VALUE(printerCod);
/* 286 */     List<String> impresorasList = GET_LISTA_IMPRESORAS();
/* 287 */     Integer indexImpDef = Integer.valueOf(GET_INDEX_ITEM_FROM_LIST(printerName, impresorasList));
/* 288 */     impresorasCmbox.setSelectedIndex(indexImpDef.intValue() >= 0 ? indexImpDef.intValue() : 0);
/*     */   }
/*     */   
/*     */   public static void SET_PRINTER_COD(String printerCod)
/*     */   {
/* 293 */     PRINTER_COD = printerCod;
/* 294 */     PRINTER_COD_SET = true;
/*     */   }
/*     */   
/*     */   public static void CLEAR_PRINTER_COD() {
/* 298 */     PRINTER_COD = "";
/* 299 */     PRINTER_COD_SET = false;
/*     */   }
/*     */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/Ctes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */