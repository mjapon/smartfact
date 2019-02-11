/*    */ package smf.sv.xml.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlMsgPart
/*    */ {
/*    */   protected String contenido;
/*    */   protected String tipo;
/*    */   protected Map<String, String> atributos;
/*    */   
/*    */   public XmlMsgPart(String contenido, String tipo, Map<String, String> atributos)
/*    */   {
/* 13 */     this.contenido = contenido;
/* 14 */     this.tipo = tipo;
/* 15 */     this.atributos = atributos;
/*    */   }
/*    */   
/*    */   public String getContenido() {
/* 19 */     return this.contenido;
/*    */   }
/*    */   
/*    */   public void setContenido(String contenido) {
/* 23 */     this.contenido = contenido;
/*    */   }
/*    */   
/*    */   public String getTipo() {
/* 27 */     return this.tipo;
/*    */   }
/*    */   
/*    */   public void setTipo(String tipo) {
/* 31 */     this.tipo = tipo;
/*    */   }
/*    */   
/*    */   public String getAttrValue(String clave) {
/* 35 */     String attrValue = null;
/* 36 */     if (this.atributos.containsKey(clave)) {
/* 37 */       attrValue = (String)this.atributos.get(clave);
/*    */     }
/* 39 */     return attrValue;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/xml/util/XmlMsgPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */