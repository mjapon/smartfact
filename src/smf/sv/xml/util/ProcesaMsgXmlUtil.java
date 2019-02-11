/*    */ package smf.sv.xml.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.StringReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcesaMsgXmlUtil
/*    */ {
/*    */   private String xml;
/*    */   private List<XmlMsgPart> partesDocList;
/*    */   private Map<String, String> atributosDocMap;
/*    */   
/*    */   public ProcesaMsgXmlUtil(String xml)
/*    */   {
/* 37 */     this.xml = xml;
/*    */   }
/*    */   
/*    */   public Map<String, String> getAtributosNodo(Node node) {
/* 41 */     Map<String, String> atributosNodeMap = new HashMap();
/* 42 */     if (node.hasAttributes()) {
/* 43 */       NamedNodeMap nodeMap = node.getAttributes();
/* 44 */       for (int i = 0; i < nodeMap.getLength(); i++) {
/* 45 */         Node auxnode = nodeMap.item(i);
/* 46 */         atributosNodeMap.put(auxnode.getNodeName(), 
/* 47 */           auxnode.getNodeValue());
/*    */       }
/*    */     }
/* 50 */     return atributosNodeMap;
/*    */   }
/*    */   
/*    */   public void procesar() throws ParserConfigurationException, SAXException, IOException
/*    */   {
/* 55 */     DocumentBuilderFactory builderFactory = 
/* 56 */       DocumentBuilderFactory.newInstance();
/* 57 */     DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
/* 58 */     InputSource is = new InputSource(new StringReader(this.xml));
/* 59 */     Document doc = documentBuilder.parse(is);
/*    */     
/* 61 */     Node root = doc.getFirstChild();
/*    */     
/* 63 */     this.atributosDocMap = getAtributosNodo(root);
/*    */     
/* 65 */     NodeList nodeList = root.getChildNodes();
/*    */     
/* 67 */     this.partesDocList = new ArrayList();
/*    */     
/*    */ 
/* 70 */     for (int count = 0; count < nodeList.getLength(); count++) {
/* 71 */       Node nodeIter = nodeList.item(count);
/* 72 */       if (nodeIter.getNodeType() == 1) {
/* 73 */         String nodeTextValue = nodeIter.getTextContent();
/* 74 */         Map<String, String> atributosNodeMap = getAtributosNodo(nodeIter);
/* 75 */         String nodeName = nodeIter.getNodeName();
/* 76 */         if ("texto".equalsIgnoreCase(nodeName)) {
/* 77 */           this.partesDocList.add(new XmlTextPart(nodeTextValue, atributosNodeMap));
/* 78 */         } else if ("barcode".equalsIgnoreCase(nodeName)) {
/* 79 */           this.partesDocList.add(new XmlBarcodePart(nodeTextValue, atributosNodeMap));
/*    */         } else {
/* 81 */           this.partesDocList.add(new XmlGenericPart(nodeTextValue, atributosNodeMap));
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public List<XmlMsgPart> getPartesDocumento() {
/* 88 */     return this.partesDocList;
/*    */   }
/*    */   
/*    */   public Map<String, String> getAtributosDocMap() {
/* 92 */     return this.atributosDocMap;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/xml/util/ProcesaMsgXmlUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */