/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import smf.util.datamodels.rows.FilaFactura;
import smf.sv.print.util.TextPrinterUtil;
import java.io.IOException;
import java.util.List;
import smf.controller.CtesJpaController;

/**
 *
 * @author manuel.japon
 */
public class PrintFactUtil {
    
    public static void imprimirTicketTxt(CtesJpaController ctesController, String textToPrint) throws IOException{
        //Verificar el numero de copias ha imprimir
        System.out.println("texto ha imprimir");
        System.out.println(textToPrint);                    

        //IMPRESORA
        String impresora = ctesController.findValueByClave("IMPRESORA");
        TextPrinterUtil printerUtil = new TextPrinterUtil();
        printerUtil.imprimir(impresora, textToPrint);
        
    }
    
    public static void imprimirTicketJasper(CtesJpaController ctesController, Integer ticketId) throws IOException{
        //IMPRESORA                                               
        String rutaReporteFact = ctesController.findValueByClave("RUTA_REPORTE_TICKET");
        System.out.println("Path reporte es:");
        System.out.println(rutaReporteFact);
        JasperUtil.showTicket(ticketId, rutaReporteFact);
    }
    
    public static void imprimir(CtesJpaController ctesController, DatosCabeceraFactura cabecera, 
            TotalesFactura totales, 
            List<FilaFactura> detalles, Integer factId){
        try{
            System.out.println("Logica de impresion-->"); 
            
            String templateCab = ctesController.findValueByClave("TEMPLATE_CAB");
            
            if (templateCab == null){
                System.out.println("Templatecab no definido");
                templateCab = "";
            }
            
            String templateFila = ctesController.findValueByClave("TEMPLATE_DET");
            if (templateFila == null){
                System.out.println("Templatedet no definido");
                templateFila = "";
            }
            
            String templatePie = ctesController.findValueByClave("TEMPLATE_PIE");
            if (templatePie == null){
                System.out.println("Templatepie no definido");
                templatePie = "";
            }
            
            Integer numFilasTxt  = 0;
            
            String numFilasParam = ctesController.findValueByClave("NUM_FILAS_TXT");
            if (numFilasParam != null){
                try{
                    numFilasTxt = Integer.valueOf(numFilasParam);
                }
                catch(Throwable ex){
                    System.out.println("Error al tratar de parsear ha int NUM_FILAS_TXT"+ ex.getMessage());
                    ex.printStackTrace();
                    numFilasTxt = 0;
                }
            }
            
            String rutaReporteFact = ctesController.findValueByClave("RUTA_REPORTE_FACT");
            
            JasperUtil.showFactura(factId, rutaReporteFact);
            
            
            /*
            try{                
                String textToPrint = GenTxtFactura.getTxt(
                        cabecera,
                        detalles, 
                        totales, 
                        templateCab, 
                        templateFila, 
                        templatePie,
                        numFilasTxt
                );
                
                //Verificar el numero de copias ha imprimir
                Integer copysToPrint = 1;
                try{
                    String auxNroCopys = ctesController.findValueByClave("NUMBER_COPYS_PRINT");
                    if (auxNroCopys!=null){
                        copysToPrint = Integer.valueOf(auxNroCopys);
                    }
                }
                catch(Throwable ex){
                    System.out.println("Error al tratar de obtener el numero de copias ha imprimir: "+ex.getMessage());
                    ex.printStackTrace();
                }
                                
                if (copysToPrint>1){
                    textToPrint = GenTxtFactura.getCopias(textToPrint, copysToPrint);
                }
                
                System.out.println("texto ha imprimir");
                System.out.println(textToPrint);                    
                
                //IMPRESORA
                String impresora = ctesController.findValueByClave("IMPRESORA");
                TextPrinterUtil printerUtil = new TextPrinterUtil();
                printerUtil.imprimir(impresora, textToPrint);                            
            }
            catch(Throwable ex){
                System.out.println("Error al generar txt:"+ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al imprimir:"+ex.getMessage());
            }        
            */
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de imprimir:"+ex.getMessage());
            ex.printStackTrace();
        }
    }
}