/*    */ package smf.sv.print.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class CadsUtil
/*    */ {
/*    */   public static List<LinePart> procesarLinea(String linea)
/*    */   {
/* 12 */     String patron = "(<b>|<i>|<u>|<cut>|<barcode>){1,}.*?[^</](</u>|</i>|</b>|</barcode>|</cut>){1,}";
/*    */     
/* 14 */     Pattern p = Pattern.compile(patron);
/* 15 */     Splitter splitter = new Splitter(p, true);
/* 16 */     return splitter.split(linea);
/*    */   }
/*    */   
/*    */   public static String getFechaHoraActual() {
/* 20 */     return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new java.util.Date());
/*    */   }
/*    */   
/*    */   public static void main(String[] args)
/*    */   {
/* 25 */     String cadena = "SAVE_IMPRESORAS<<5;CONTABILIDAD1EPSON FX-890 ESC/P (Copiar 1)<>7;CONTABILIDAD1EPSON FX-890 ESC/P (Copiar 1)";
/*    */     
/* 27 */     String prueba = "laskdhfalskdfjhalskdfjhl\fasdfasdfasdf";
/*    */     
/* 29 */     String[] cadenas = prueba.split("\f");
/*    */     
/* 31 */     System.out.println(cadenas.length);
/* 32 */     System.out.println(cadenas[0]);
/* 33 */     System.out.println(cadenas[1]);
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/CadsUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */