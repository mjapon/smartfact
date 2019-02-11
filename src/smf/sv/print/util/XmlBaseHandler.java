/*     */ package smf.sv.print.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlBaseHandler
/*     */ {
/*     */   protected String xml;
/*     */   
/*     */   protected String convertNodeToString(Node node)
/*     */   {
/*  28 */     StringWriter sw = new StringWriter();
/*     */     try
/*     */     {
/*  31 */       Transformer t = TransformerFactory.newInstance().newTransformer();
/*  32 */       t.setOutputProperty("omit-xml-declaration", "yes");
/*  33 */       t.transform(new DOMSource(node), new StreamResult(sw));
/*     */     }
/*     */     catch (TransformerException te)
/*     */     {
/*  37 */       te.printStackTrace();
/*     */     }
/*  39 */     return sw.toString();
/*     */   }
/*     */   
/*     */   protected String convertDocumentToString(Document doc)
/*     */   {
/*  44 */     TransformerFactory tf = TransformerFactory.newInstance();
/*     */     try
/*     */     {
/*  47 */       Transformer transformer = tf.newTransformer();
/*  48 */       StringWriter writer = new StringWriter();
/*  49 */       transformer.transform(new DOMSource(doc), new StreamResult(writer));
/*  50 */       return writer.getBuffer().toString();
/*     */     }
/*     */     catch (TransformerException e)
/*     */     {
/*  54 */       e.printStackTrace();
/*     */     }
/*  56 */     return "";
/*     */   }
/*     */   
/*     */   protected String getElementValue(Element element, String tagName)
/*     */   {
/*     */     try
/*     */     {
/*  63 */       return element.getElementsByTagName(tagName).item(0).getTextContent();
/*     */     }
/*     */     catch (Throwable ex)
/*     */     {
/*  67 */       System.out.println("Error al obtener valor de nodo:" + ex.getMessage());
/*  68 */       ex.printStackTrace();
/*     */     }
/*  70 */     return "";
/*     */   }
/*     */   
/*     */   public Node getFirstNode(Document doc, String nodeName)
/*     */   {
/*  75 */     NodeList nodelist = doc.getElementsByTagName(nodeName);
/*  76 */     if (nodelist.getLength() > 0) {
/*  77 */       return nodelist.item(0);
/*     */     }
/*  79 */     return null;
/*     */   }
/*     */   
/*     */   public Node getFirstNodeByTagName(Element element, String tagname)
/*     */   {
/*  84 */     NodeList auxNodeListComp = element.getElementsByTagName(tagname);
/*  85 */     if (auxNodeListComp.getLength() > 0) {
/*  86 */       return auxNodeListComp.item(0);
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public String getNodeListValue(NodeList nodelist)
/*     */   {
/*  93 */     StringBuffer stringBuffer = new StringBuffer("");
/*  94 */     for (int i = 0; i < nodelist.getLength(); i++)
/*     */     {
/*  96 */       Node node = nodelist.item(i);
/*  97 */       stringBuffer.append(node.getTextContent());
/*     */     }
/*  99 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public Map<String, Object> getAtributosNodo(Node node)
/*     */   {
/* 104 */     Map<String, Object> resultMap = new HashMap();
/* 105 */     if (node == null) {
/* 106 */       return resultMap;
/*     */     }
/* 108 */     NodeList nodeList = node.getChildNodes();
/* 109 */     for (int i = 0; i < nodeList.getLength(); i++)
/*     */     {
/* 111 */       Node nodeIter = nodeList.item(i);
/* 112 */       if (nodeIter.getChildNodes().getLength() == 1) {
/* 113 */         resultMap.put(nodeIter.getNodeName(), nodeIter.getTextContent());
/*     */       }
/*     */     }
/* 116 */     return resultMap;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getFirstDetAdcFromDetalleNode(Node nodeDetalle) {
/* 120 */     Map<String, Object> resultMap = null;
/*     */     
/* 122 */     Node nodeDetsAdc = getFirstNodeByTagName((Element)nodeDetalle, "detallesAdicionales");
/* 123 */     if (nodeDetsAdc != null) {
/* 124 */       NodeList infoAdicionalChildNodes = nodeDetsAdc.getChildNodes();
/*     */       
/* 126 */       if (infoAdicionalChildNodes.getLength() > 0) {
/* 127 */         resultMap = new HashMap();
/*     */         
/* 129 */         Node firstNodeDetAdc = infoAdicionalChildNodes.item(0);
/* 130 */         String nombre = "";
/*     */         try {
/* 132 */           Element infoAdicionalElement = (Element)firstNodeDetAdc;
/* 133 */           nombre = infoAdicionalElement.getAttribute("nombre");
/*     */         }
/*     */         catch (Throwable ex) {
/* 136 */           nombre = "";
/* 137 */           System.out.println("Error al tratar de obtener el valor de la propiedad nombre del nodo detAdicional"+ ex.getMessage());
                    ex.printStackTrace();
/*     */         }
/*     */         
/* 140 */         String valor = "";
/*     */         try {
/* 142 */           Element infoAdicionalElement = (Element)firstNodeDetAdc;
/* 143 */           valor = infoAdicionalElement.getAttribute("valor");
/*     */         }
/*     */         catch (Throwable ex) {
/* 146 */           valor = "";
                    System.out.println("Error al tratar de obtener el valor de la propiedad valor del nodo detAdicional:"+ex.getMessage());
                    ex.printStackTrace();
/*     */         }
/*     */         
/* 150 */         resultMap.put("nombre", nombre);
/* 151 */         resultMap.put("valor", valor);
/*     */       }
/*     */     }
/*     */     
/* 155 */     return resultMap;
/*     */   }
/*     */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/XmlBaseHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */