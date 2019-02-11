/*    */ package smf.sv.print.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class Splitter
/*    */ {
/*  9 */   private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\s+");
/*    */   private Pattern pattern;
/*    */   private boolean keep_delimiters;
/*    */   
/*    */   public Splitter(Pattern pattern, boolean keep_delimiters)
/*    */   {
/* 15 */     this.pattern = pattern;
/* 16 */     this.keep_delimiters = keep_delimiters;
/*    */   }
/*    */   
/* 19 */   public Splitter(String pattern, boolean keep_delimiters) { this(Pattern.compile(pattern == null ? "" : pattern), keep_delimiters); }
/*    */   
/* 21 */   public Splitter(Pattern pattern) { this(pattern, true); }
/* 22 */   public Splitter(String pattern) { this(pattern, true); }
/* 23 */   public Splitter(boolean keep_delimiters) { this(DEFAULT_PATTERN, keep_delimiters); }
/* 24 */   public Splitter() { this(DEFAULT_PATTERN); }
/*    */   
/*    */   public List<LinePart> split(String text) {
/* 27 */     if (text == null) {
/* 28 */       text = "";
/*    */     }
/*    */     
/* 31 */     int last_match = 0;
/* 32 */     List<String> splitted = new java.util.ArrayList();
/* 33 */     Matcher m = this.pattern.matcher(text);
/*    */     
/* 35 */     while (m.find()) {
/* 36 */       splitted.add(text.substring(last_match, m.start()));
/* 37 */       if (this.keep_delimiters) {
/* 38 */         splitted.add(m.group());
/*    */       }
/* 40 */       last_match = m.end();
/*    */     }
/*    */     
/* 43 */     splitted.add(text.substring(last_match));
/*    */     
/*    */ 
/*    */ 
/* 47 */     List<LinePart> filteredList = new java.util.ArrayList();
/* 48 */     for (String item : splitted) {
/* 49 */       if (item.length() > 0) {
/* 50 */         filteredList.add(new LinePart(item));
/*    */       }
/*    */     }
/* 53 */     return filteredList;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/Splitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */