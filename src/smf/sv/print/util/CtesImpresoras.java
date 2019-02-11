/*     */ package smf.sv.print.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;

/*     */ 
/*     */ 
/*     */ 
/*     */ public class CtesImpresoras
/*     */ {

/*     */   
/*     */   public static void CHECK_FILE_EXIST() {
/*     */     try {
/*  21 */       File yourFile = new File(Ctes.RUTA_PROPERTIES_IMPRESORA);
/*  22 */       if (!yourFile.exists()) {
/*  23 */         yourFile.createNewFile();
/*     */       }
/*     */     }
/*     */     catch (Throwable ex) {
/*  27 */       System.out.println("Error al tratar de crear archivo impresoras.properties:" + ex.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public static void SET_PROPERTY_VALUE(String property, String value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_2
/*     */     //   2: new 84	java/io/FileInputStream
/*     */     //   5: dup
/*     */     //   6: getstatic 36	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES_IMPRESORA	Ljava/lang/String;
/*     */     //   9: invokespecial 86	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   12: astore_2
/*     */     //   13: getstatic 50	java/lang/System:out	Ljava/io/PrintStream;
/*     */     //   16: new 56	java/lang/StringBuilder
/*     */     //   19: dup
/*     */     //   20: ldc 87
/*     */     //   22: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   25: aload_0
/*     */     //   26: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   29: ldc 89
/*     */     //   31: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   34: getstatic 36	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES_IMPRESORA	Ljava/lang/String;
/*     */     //   37: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   40: ldc 91
/*     */     //   42: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   45: aload_1
/*     */     //   46: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   49: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   52: invokevirtual 73	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   55: new 93	java/util/Properties
/*     */     //   58: dup
/*     */     //   59: invokespecial 95	java/util/Properties:<init>	()V
/*     */     //   62: astore_3
/*     */     //   63: aload_3
/*     */     //   64: aload_2
/*     */     //   65: invokevirtual 96	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   68: new 100	java/io/FileOutputStream
/*     */     //   71: dup
/*     */     //   72: getstatic 36	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES_IMPRESORA	Ljava/lang/String;
/*     */     //   75: invokespecial 102	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
/*     */     //   78: astore 4
/*     */     //   80: aload_3
/*     */     //   81: aload_0
/*     */     //   82: aload_1
/*     */     //   83: invokevirtual 103	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   86: pop
/*     */     //   87: aload_3
/*     */     //   88: aload 4
/*     */     //   90: aconst_null
/*     */     //   91: invokevirtual 107	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
/*     */     //   94: aload 4
/*     */     //   96: invokevirtual 111	java/io/FileOutputStream:close	()V
/*     */     //   99: goto +140 -> 239
/*     */     //   102: astore_3
/*     */     //   103: getstatic 50	java/lang/System:out	Ljava/io/PrintStream;
/*     */     //   106: new 56	java/lang/StringBuilder
/*     */     //   109: dup
/*     */     //   110: ldc 114
/*     */     //   112: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   115: aload_3
/*     */     //   116: invokevirtual 61	java/lang/Throwable:getMessage	()Ljava/lang/String;
/*     */     //   119: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   122: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   125: invokevirtual 73	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   128: aload_3
/*     */     //   129: invokevirtual 116	java/lang/Throwable:printStackTrace	()V
/*     */     //   132: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   135: ldc 114
/*     */     //   137: aload_3
/*     */     //   138: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   141: aload_2
/*     */     //   142: invokevirtual 123	java/io/FileInputStream:close	()V
/*     */     //   145: goto +139 -> 284
/*     */     //   148: astore 6
/*     */     //   150: getstatic 50	java/lang/System:out	Ljava/io/PrintStream;
/*     */     //   153: new 56	java/lang/StringBuilder
/*     */     //   156: dup
/*     */     //   157: ldc 124
/*     */     //   159: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   162: aload 6
/*     */     //   164: invokevirtual 61	java/lang/Throwable:getMessage	()Ljava/lang/String;
/*     */     //   167: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   170: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   173: invokevirtual 73	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   176: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   179: ldc 124
/*     */     //   181: aload 6
/*     */     //   183: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   186: goto +98 -> 284
/*     */     //   189: astore 5
/*     */     //   191: aload_2
/*     */     //   192: invokevirtual 123	java/io/FileInputStream:close	()V
/*     */     //   195: goto +41 -> 236
/*     */     //   198: astore 6
/*     */     //   200: getstatic 50	java/lang/System:out	Ljava/io/PrintStream;
/*     */     //   203: new 56	java/lang/StringBuilder
/*     */     //   206: dup
/*     */     //   207: ldc 124
/*     */     //   209: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   212: aload 6
/*     */     //   214: invokevirtual 61	java/lang/Throwable:getMessage	()Ljava/lang/String;
/*     */     //   217: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   220: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   223: invokevirtual 73	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   226: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   229: ldc 124
/*     */     //   231: aload 6
/*     */     //   233: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   236: aload 5
/*     */     //   238: athrow
/*     */     //   239: aload_2
/*     */     //   240: invokevirtual 123	java/io/FileInputStream:close	()V
/*     */     //   243: goto +41 -> 284
/*     */     //   246: astore 6
/*     */     //   248: getstatic 50	java/lang/System:out	Ljava/io/PrintStream;
/*     */     //   251: new 56	java/lang/StringBuilder
/*     */     //   254: dup
/*     */     //   255: ldc 124
/*     */     //   257: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   260: aload 6
/*     */     //   262: invokevirtual 61	java/lang/Throwable:getMessage	()Ljava/lang/String;
/*     */     //   265: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   268: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   271: invokevirtual 73	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   274: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   277: ldc 124
/*     */     //   279: aload 6
/*     */     //   281: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   284: return
/*     */     // Line number table:
/*     */     //   Java source line #32	-> byte code offset #0
/*     */     //   Java source line #34	-> byte code offset #2
/*     */     //   Java source line #35	-> byte code offset #13
/*     */     //   Java source line #37	-> byte code offset #55
/*     */     //   Java source line #38	-> byte code offset #63
/*     */     //   Java source line #39	-> byte code offset #68
/*     */     //   Java source line #40	-> byte code offset #80
/*     */     //   Java source line #41	-> byte code offset #87
/*     */     //   Java source line #42	-> byte code offset #94
/*     */     //   Java source line #43	-> byte code offset #99
/*     */     //   Java source line #44	-> byte code offset #102
/*     */     //   Java source line #45	-> byte code offset #103
/*     */     //   Java source line #46	-> byte code offset #128
/*     */     //   Java source line #47	-> byte code offset #132
/*     */     //   Java source line #51	-> byte code offset #141
/*     */     //   Java source line #52	-> byte code offset #145
/*     */     //   Java source line #53	-> byte code offset #148
/*     */     //   Java source line #54	-> byte code offset #150
/*     */     //   Java source line #55	-> byte code offset #176
/*     */     //   Java source line #49	-> byte code offset #189
/*     */     //   Java source line #51	-> byte code offset #191
/*     */     //   Java source line #52	-> byte code offset #195
/*     */     //   Java source line #53	-> byte code offset #198
/*     */     //   Java source line #54	-> byte code offset #200
/*     */     //   Java source line #55	-> byte code offset #226
/*     */     //   Java source line #57	-> byte code offset #236
/*     */     //   Java source line #51	-> byte code offset #239
/*     */     //   Java source line #52	-> byte code offset #243
/*     */     //   Java source line #53	-> byte code offset #246
/*     */     //   Java source line #54	-> byte code offset #248
/*     */     //   Java source line #55	-> byte code offset #274
/*     */     //   Java source line #58	-> byte code offset #284
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	285	0	property	String
/*     */     //   0	285	1	value	String
/*     */     //   1	239	2	in	FileInputStream
/*     */     //   62	26	3	props	Properties
/*     */     //   102	36	3	ex	Throwable
/*     */     //   78	17	4	out	java.io.FileOutputStream
/*     */     //   189	48	5	localObject	Object
/*     */     //   148	34	6	ex	Throwable
/*     */     //   198	34	6	ex	Throwable
/*     */     //   246	34	6	ex	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   2	99	102	java/lang/Throwable
/*     */     //   141	145	148	java/lang/Throwable
/*     */     //   2	141	189	finally
/*     */     //   191	195	198	java/lang/Throwable
/*     */     //   239	243	246	java/lang/Throwable
/*     */   }
/*     */   
/*     */   public static String GET_PROPERTY_VALUE(String prop)
/*     */   {
/*  61 */     InputStream is = null;
/*     */     try {
/*  63 */       Properties properties = new Properties();
/*  64 */       is = new FileInputStream(Ctes.RUTA_PROPERTIES_IMPRESORA);
/*  65 */       properties.load(is);
/*  66 */       String printerFinded = properties.getProperty(prop, "");
/*  67 */       System.out.println("Impresora encontrada es:" + printerFinded);
/*  68 */       return printerFinded;
/*     */     }
/*     */     catch (Throwable ex) {
/*  71 */       System.out.println("Error al trater de leer archivo:" + ex.getMessage());
/*  72 */       ex.printStackTrace();
/*  73 */       
/*     */     }
/*     */     finally {
/*     */       try {
/*  77 */         is.close();
/*     */       }
/*     */       catch (Throwable ex) {
/*  80 */       
/*     */       }
/*     */     }
/*  83 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<String> READ_ALL_PRINTERS()
/*     */   {
/*  91 */     List<String> allPrintersList = new ArrayList();
/*     */     
/*  93 */     InputStream is = null;
/*     */     try {
/*  95 */       Properties properties = new Properties();
/*  96 */       is = new FileInputStream(Ctes.RUTA_PROPERTIES_IMPRESORA);
/*  97 */       properties.load(is);
/*  98 */       Set<Object> keys = properties.keySet();
/*  99 */       for (Object key : keys) {
/* 100 */         String printerCod = String.valueOf(key);
/* 101 */         String printerName = String.valueOf(properties.get(key));
/* 102 */         allPrintersList.add(printerCod + ";" + printerName);
/*     */       }
/* 104 */       return allPrintersList;
/*     */     }
/*     */     catch (Throwable ex) {
/* 107 */      
/*     */     }
/*     */     finally {
/*     */       try {
/* 111 */         is.close();
/*     */       }
/*     */       catch (Throwable ex) {
/* 114 */         
/*     */       }
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public static void SAVE_IMPRESORAS(String messageWebSocket) {
/* 121 */     System.out.println("SAVE_IMPRESORAS----->");
/* 122 */     System.out.println("Mensaje que llega es:");
/* 123 */     System.out.println(messageWebSocket);
/* 124 */     String[] splitMessage = messageWebSocket.split("<<");
/*     */     
/* 126 */     System.out.println("Valor de splitMessage lengt es:" + splitMessage.length);
/* 127 */     CHECK_FILE_EXIST();
/*     */     
/* 129 */     String impresoras = splitMessage[1];
/* 130 */     String[] printersList = impresoras.split("<>");
/* 131 */     String[] arrayOfString1; int j = (arrayOfString1 = printersList).length; for (int i = 0; i < j; i++) { String printer = arrayOfString1[i];
/* 132 */       String[] codName = printer.split(";");
/* 133 */       String printerCodigo = codName[0];
/* 134 */       String printerName = codName[1];
/* 135 */       System.out.println("Lo que se envia a grabar en impresoras.properties es:" + printerCodigo + ", printerName:" + printerName);
/* 136 */       SET_PROPERTY_VALUE(printerCodigo, printerName);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public static String GET_PRINTER_COD(String impresora)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_1
/*     */     //   2: aconst_null
/*     */     //   3: astore_2
/*     */     //   4: new 93	java/util/Properties
/*     */     //   7: dup
/*     */     //   8: invokespecial 95	java/util/Properties:<init>	()V
/*     */     //   11: astore_3
/*     */     //   12: new 84	java/io/FileInputStream
/*     */     //   15: dup
/*     */     //   16: getstatic 36	com/serviestudios/print/util/Ctes:RUTA_PROPERTIES_IMPRESORA	Ljava/lang/String;
/*     */     //   19: invokespecial 86	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   22: astore_1
/*     */     //   23: aload_3
/*     */     //   24: aload_1
/*     */     //   25: invokevirtual 96	java/util/Properties:load	(Ljava/io/InputStream;)V
/*     */     //   28: aload_3
/*     */     //   29: invokevirtual 162	java/util/Properties:keySet	()Ljava/util/Set;
/*     */     //   32: astore 4
/*     */     //   34: aload 4
/*     */     //   36: invokeinterface 166 1 0
/*     */     //   41: astore 6
/*     */     //   43: goto +38 -> 81
/*     */     //   46: aload 6
/*     */     //   48: invokeinterface 172 1 0
/*     */     //   53: astore 5
/*     */     //   55: aload_3
/*     */     //   56: aload 5
/*     */     //   58: invokevirtual 184	java/util/Properties:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   61: invokestatic 178	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   64: astore 7
/*     */     //   66: aload 7
/*     */     //   68: aload_0
/*     */     //   69: invokevirtual 245	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*     */     //   72: ifeq +9 -> 81
/*     */     //   75: aload 5
/*     */     //   77: invokestatic 178	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   80: astore_2
/*     */     //   81: aload 6
/*     */     //   83: invokeinterface 196 1 0
/*     */     //   88: ifne -42 -> 46
/*     */     //   91: goto +59 -> 150
/*     */     //   94: astore_3
/*     */     //   95: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   98: ldc 114
/*     */     //   100: aload_3
/*     */     //   101: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   104: aload_1
/*     */     //   105: invokevirtual 143	java/io/InputStream:close	()V
/*     */     //   108: goto +61 -> 169
/*     */     //   111: astore 9
/*     */     //   113: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   116: ldc -110
/*     */     //   118: aload 9
/*     */     //   120: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   123: goto +46 -> 169
/*     */     //   126: astore 8
/*     */     //   128: aload_1
/*     */     //   129: invokevirtual 143	java/io/InputStream:close	()V
/*     */     //   132: goto +15 -> 147
/*     */     //   135: astore 9
/*     */     //   137: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   140: ldc -110
/*     */     //   142: aload 9
/*     */     //   144: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   147: aload 8
/*     */     //   149: athrow
/*     */     //   150: aload_1
/*     */     //   151: invokevirtual 143	java/io/InputStream:close	()V
/*     */     //   154: goto +15 -> 169
/*     */     //   157: astore 9
/*     */     //   159: getstatic 24	com/serviestudios/print/util/CtesImpresoras:LOG	Lorg/apache/log4j/Logger;
/*     */     //   162: ldc -110
/*     */     //   164: aload 9
/*     */     //   166: invokevirtual 119	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   169: aload_2
/*     */     //   170: areturn
/*     */     // Line number table:
/*     */     //   Java source line #146	-> byte code offset #0
/*     */     //   Java source line #147	-> byte code offset #2
/*     */     //   Java source line #149	-> byte code offset #4
/*     */     //   Java source line #150	-> byte code offset #12
/*     */     //   Java source line #151	-> byte code offset #23
/*     */     //   Java source line #152	-> byte code offset #28
/*     */     //   Java source line #153	-> byte code offset #34
/*     */     //   Java source line #155	-> byte code offset #55
/*     */     //   Java source line #156	-> byte code offset #66
/*     */     //   Java source line #157	-> byte code offset #75
/*     */     //   Java source line #153	-> byte code offset #81
/*     */     //   Java source line #160	-> byte code offset #91
/*     */     //   Java source line #161	-> byte code offset #94
/*     */     //   Java source line #162	-> byte code offset #95
/*     */     //   Java source line #166	-> byte code offset #104
/*     */     //   Java source line #167	-> byte code offset #108
/*     */     //   Java source line #168	-> byte code offset #111
/*     */     //   Java source line #169	-> byte code offset #113
/*     */     //   Java source line #164	-> byte code offset #126
/*     */     //   Java source line #166	-> byte code offset #128
/*     */     //   Java source line #167	-> byte code offset #132
/*     */     //   Java source line #168	-> byte code offset #135
/*     */     //   Java source line #169	-> byte code offset #137
/*     */     //   Java source line #171	-> byte code offset #147
/*     */     //   Java source line #166	-> byte code offset #150
/*     */     //   Java source line #167	-> byte code offset #154
/*     */     //   Java source line #168	-> byte code offset #157
/*     */     //   Java source line #169	-> byte code offset #159
/*     */     //   Java source line #172	-> byte code offset #169
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	171	0	impresora	String
/*     */     //   1	150	1	is	InputStream
/*     */     //   3	167	2	printerCod	String
/*     */     //   11	45	3	properties	Properties
/*     */     //   94	7	3	ex	Throwable
/*     */     //   32	3	4	keys	Set<Object>
/*     */     //   53	23	5	key	Object
/*     */     //   41	41	6	localIterator	java.util.Iterator
/*     */     //   64	3	7	printerName	String
/*     */     //   126	22	8	localObject1	Object
/*     */     //   111	8	9	ex	Throwable
/*     */     //   135	8	9	ex	Throwable
/*     */     //   157	8	9	ex	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	91	94	java/lang/Throwable
/*     */     //   104	108	111	java/lang/Throwable
/*     */     //   4	104	126	finally
/*     */     //   128	132	135	java/lang/Throwable
/*     */     //   150	154	157	java/lang/Throwable
        return null;
    }   
}



/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/CtesImpresoras.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */