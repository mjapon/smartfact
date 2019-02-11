/*     */ package smf.sv.print.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.print.Doc;
/*     */ import javax.print.DocFlavor;
/*     */ import javax.print.DocFlavor.BYTE_ARRAY;
/*     */ import javax.print.DocPrintJob;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.PrintServiceLookup;
/*     */ import javax.print.SimpleDoc;
/*     */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*     */ import javax.print.attribute.standard.Copies;
/*     */ import javax.print.attribute.standard.NumberUp;
/*     */ import javax.print.attribute.standard.PrintQuality;
/*     */ 
/*     */ public class TextPrinterUtil
/*     */ {
/*  24 */   PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
/*  25 */   private DocFlavor docFlavor = null;
/*  26 */   private Doc doc = null;
/*  27 */   private DocPrintJob docPrintJob = null;
/*     */   
/*     */   public boolean imprimir(PrintService printService, String data) throws UnsupportedEncodingException, Exception
/*     */   {
/*  31 */     this.docPrintJob = printService.createPrintJob();
/*  32 */     if (this.docPrintJob != null)
/*     */     {
/*  34 */       this.docFlavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
/*  35 */       this.doc = new SimpleDoc(data.getBytes("ISO-8859-1"), this.docFlavor, null);
/*     */       
/*  37 */       HashPrintRequestAttributeSet localHashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/*     */       
/*  39 */       localHashPrintRequestAttributeSet.add(new Copies(1));
/*  40 */       localHashPrintRequestAttributeSet.add(new NumberUp(1));
/*  41 */       localHashPrintRequestAttributeSet.add(PrintQuality.DRAFT);
/*     */       try {
/*  43 */         this.docPrintJob.print(this.doc, localHashPrintRequestAttributeSet);
/*  44 */         return true;
/*     */       } catch (Exception localException) {
/*  46 */         System.out.println("Error al tratar de imprimir");
/*  47 */         System.out.println(localException.getMessage());
/*  48 */         localException.printStackTrace();
/*  49 */         throw new Exception("Error al tratar de imprimir:" + localException.getMessage());
/*     */       }
/*     */     }
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public boolean imprimir(String paramString1, String paramString2) throws IOException {
/*  56 */     System.out.println("Imprimiendo a: '" + paramString1 + "', datos:" + paramString2 + "\n");
/*  57 */     for (int i = 0; i < this.printServices.length; i++) {
/*  58 */       if (this.printServices[i].getName().toLowerCase().trim().equals(paramString1.toLowerCase().trim())) {
/*  59 */         this.docPrintJob = this.printServices[i].createPrintJob();
/*  60 */         DocFlavor[] flavors = this.printServices[i].getSupportedDocFlavors();
/*  61 */         for (int j = 0; j < flavors.length; j++) {
/*  62 */           System.out.println("\t" + flavors[j].toString());
/*     */         }
/*     */       }
/*     */     }
/*  66 */     if (this.docPrintJob != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  80 */       byte[] bytes = paramString2.getBytes(Charset.forName("UTF-8"));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  85 */       this.docFlavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  90 */       this.doc = new SimpleDoc(bytes, this.docFlavor, null);
/*     */       
/*     */       HashPrintRequestAttributeSet localHashPrintRequestAttributeSet;
/*     */       
/*  94 */       (localHashPrintRequestAttributeSet = new HashPrintRequestAttributeSet()).add(new Copies(1));
/*     */       
/*     */       try
/*     */       {
/*  98 */         this.docPrintJob.print(this.doc, 
/*  99 */           localHashPrintRequestAttributeSet);
/* 100 */         return true;
/*     */       } catch (Exception localException) {
/* 102 */         System.out.println("Error al tratar de impriir");
/* 103 */         System.out.println(localException.getMessage());
/* 104 */         localException.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public PrintService[] getPrintServices() {
/* 112 */     return this.printServices;
/*     */   }
/*     */   
/*     */   public Object[] getPrintServicesNames() {
/* 116 */     List<String> lista = new ArrayList();
/* 117 */     for (int i = 0; i < this.printServices.length; i++) {
/* 118 */       lista.add(this.printServices[i].getName());
/*     */     }
/* 120 */     return lista.toArray();
/*     */   }
/*     */   
/*     */   public PrintService getPrintService(String name) {
/* 124 */     for (int i = 0; i < this.printServices.length; i++)
/*     */     {
/* 126 */       if (this.printServices[i].getName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
/* 127 */         return this.printServices[i];
/*     */       }
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   private static String getDefaultCharSet() {
/* 134 */     OutputStreamWriter writer = new OutputStreamWriter(
/* 135 */       new java.io.ByteArrayOutputStream());
/* 136 */     String enc = writer.getEncoding();
/* 137 */     return enc;
/*     */   }
/*     */ }