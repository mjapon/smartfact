/*    */ package smf.sv.test;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.StringReader;
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
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 21 */     String xml = "<documento>\n<texto font=\"pruebafuente\" style=\"bold\" size=\"12\">\ntexto ha imprimirir\notra linea\n</texto>\n<barcode width=\"20\" heigth=\"30\">\n45656\n</barcode>\n</documento>";
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 30 */       System.out.println("Xml ha procesar es:");
/* 31 */       System.out.println(xml);
/*    */       
/* 33 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 34 */       DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
/*    */       
/* 36 */       InputSource is = new InputSource(new StringReader(xml));
/* 37 */       Document doc = documentBuilder.parse(is);
/*    */       
/*    */ 
/*    */ 
/* 41 */       Node root = doc.getFirstChild();
/*    */       
/* 43 */       NodeList nodeList = root.getChildNodes();
/*    */       
/* 45 */       System.out.println("Inicia iteracion por elementos del documento");
/* 46 */       for (int count = 0; count < nodeList.getLength(); count++) {
/* 47 */         Node nodeIter = nodeList.item(count);
/*    */         
/* 49 */         if (nodeIter.getNodeType() == 1)
/*    */         {
/* 51 */           System.out.println("\nNode Name =" + nodeIter.getNodeName());
/* 52 */           System.out.println("Node Value =" + nodeIter.getTextContent());
/*    */           
/* 54 */           if (nodeIter.hasAttributes())
/*    */           {
/*    */ 
/* 57 */             NamedNodeMap nodeMap = nodeIter.getAttributes();
/*    */             
/* 59 */             for (int i = 0; i < nodeMap.getLength(); i++) {
/* 60 */               Node node = nodeMap.item(i);
/* 61 */               System.out.println("attr name : " + node.getNodeName());
/* 62 */               System.out.println("attr value : " + node.getNodeValue());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (ParserConfigurationException e)
/*    */     {
/* 70 */       e.printStackTrace();
/*    */     }
/*    */     catch (SAXException e) {
/* 73 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e) {
/* 76 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/test/Test.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */