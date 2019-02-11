/*    */ package smf.sv.xml.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlTextPart extends XmlMsgPart
/*    */ {
/*    */   public XmlTextPart(String contenido, Map<String, String> atributos)
/*    */   {
/*  9 */     super(contenido, "texto", atributos);
/*    */   }
/*    */   
/*    */   public String getFontName() {
/* 13 */     return getAttrValue("font");
/*    */   }
/*    */   
/*    */   public String getFontStyle() {
/* 17 */     return getAttrValue("style");
/*    */   }
/*    */   
/*    */   public String getFontSize() {
/* 21 */     return getAttrValue("size");
/*    */   }
/*    */   
/*    */   public java.awt.Font getFont() {
/* 25 */     String fontName = "Serif";
/* 26 */     int fontStyle = 0;
/* 27 */     int fontSize = 10;
/*    */     
/* 29 */     String attrFontSize = getFontSize();
/* 30 */     String attrFontName = getFontName();
/* 31 */     String attrFontStyle = getFontStyle();
/*    */     
/* 33 */     if (attrFontSize != null) {
/*    */       try {
/* 35 */         int intFontSize = Integer.valueOf(attrFontSize).intValue();
/* 36 */         fontSize = intFontSize;
/*    */       }
/*    */       catch (Throwable localThrowable) {}
/*    */     }
/*    */     
/* 41 */     if (attrFontStyle != null) {
/* 42 */       if ("bold".equalsIgnoreCase(attrFontStyle)) {
/* 43 */         fontStyle = 1;
/*    */       }
/* 45 */       else if ("italic".equalsIgnoreCase(attrFontStyle)) {
/* 46 */         fontStyle = 2;
/*    */       }
/*    */     }
/*    */     
/* 50 */     if (attrFontName != null) {
/* 51 */       fontName = attrFontName;
/*    */     }
/*    */     
/* 54 */     return new java.awt.Font(fontName, fontStyle, fontSize);
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/xml/util/XmlTextPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */