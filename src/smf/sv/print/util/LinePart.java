/*    */ package smf.sv.print.util;
/*    */ import java.io.PrintStream;
/*    */ 
/*  4 */ public class LinePart { private boolean bold = false;
/*  5 */   private boolean italic = false;
/*  6 */   private boolean underline = false;
/*  7 */   private boolean cutpaper = false;
/*  8 */   private boolean barcode = false;
/*    */   
/* 10 */   private String part = "";
/* 11 */   private String sinprocesar = "";
/*    */   
/*    */   public LinePart(String textoAProcesar) {
/* 14 */     this.sinprocesar = textoAProcesar;
/* 15 */     this.part = textoAProcesar;
/* 16 */     procesar();
/*    */   }
/*    */   
/*    */   public void procesar() {
/*    */     try {
/* 21 */       this.bold = ((this.sinprocesar.contains("<b>")) && (this.sinprocesar.contains("</b>")));
/* 22 */       this.italic = ((this.sinprocesar.contains("<i>")) && (this.sinprocesar.contains("</i>")));
/* 23 */       this.underline = ((this.sinprocesar.contains("<u>")) && (this.sinprocesar.contains("</u>")));
/* 24 */       this.cutpaper = ((this.sinprocesar.contains("<cut>")) && (this.sinprocesar.contains("</cut>")));
/* 25 */       this.barcode = ((this.sinprocesar.contains("<barcode>")) && (this.sinprocesar.contains("</barcode>")));
/*    */       
/* 27 */       if ((this.bold) || (this.italic) || (this.underline) || (this.cutpaper) || (this.barcode)) {
/* 28 */         this.part = this.sinprocesar
/* 29 */           .replaceAll("<b>", "")
/* 30 */           .replaceAll("</b>", "")
/* 31 */           .replaceAll("<i>", "")
/* 32 */           .replaceAll("</i>", "")
/* 33 */           .replaceAll("<u>", "")
/* 34 */           .replaceAll("</u>", "")
/* 35 */           .replaceAll("<cut>", "")
/* 36 */           .replaceAll("</cut>", "")
/* 37 */           .replaceAll("<barcode>", "")
/* 38 */           .replaceAll("</barcode>", "");
/*    */       }
/*    */     }
/*    */     catch (Throwable ex) {
/* 42 */       System.out.println("Error al procesar parte:" + ex.getMessage());
/* 43 */       ex.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getPart() {
/* 48 */     return this.part;
/*    */   }
/*    */   
/*    */   public boolean isBold() {
/* 52 */     return this.bold;
/*    */   }
/*    */   
/*    */   public boolean isItalic() {
/* 56 */     return this.italic;
/*    */   }
/*    */   
/*    */   public boolean isUnderline() {
/* 60 */     return this.underline;
/*    */   }
/*    */   
/*    */   public boolean isCutpaper() {
/* 64 */     return this.cutpaper;
/*    */   }
/*    */   
/*    */   public boolean isBarcode() {
/* 68 */     return this.barcode;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/LinePart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */