/*    */ package smf.sv.xml.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlBarcodePart extends XmlMsgPart
/*    */ {
/*    */   public XmlBarcodePart(String contenido, Map<String, String> atributos) {
/*  8 */     super(contenido, "barcode", atributos);
/*    */   }
/*    */   
/*    */   public int getBarcodeWidth() {
/* 12 */     String witdh = getAttrValue("width");
/* 13 */     int defWidth = 80;
/*    */     try {
/* 15 */       int intWidth = Integer.valueOf(witdh).intValue();
/* 16 */       defWidth = intWidth;
/*    */     }
/*    */     catch (Throwable localThrowable) {}
/*    */     
/*    */ 
/*    */ 
/* 22 */     return defWidth;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getBarcodeHeigh()
/*    */   {
/* 28 */     String heigh = getAttrValue("heigh");
/*    */     
/* 30 */     int defHeihg = 40;
/*    */     try {
/* 32 */       int intHeigh = Integer.valueOf(heigh).intValue();
/* 33 */       defHeihg = intHeigh;
/*    */     }
/*    */     catch (Throwable localThrowable) {}
/*    */     
/*    */ 
/*    */ 
/* 39 */     return defHeihg;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/xml/util/XmlBarcodePart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */