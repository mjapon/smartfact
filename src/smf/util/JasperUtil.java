/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author mjapon
 */
public class JasperUtil {

    public static final String CLAVE_DB = "root"; //PROD->"root"
    public static final String URI_DB = "jdbc:postgresql://localhost:5432/farmaciajj";

    public static void main(String args[]) throws ParseException {
        //System.out.println("Generando reporte--->");
        /*
        String pathReporte = "C:\\dev\\SMARTFACT\\VERSIONVETERG\\VETERPRJ\\src\\reportes\\templateFactura.jrxml";
        showFactura(20, pathReporte);
        System.out.println("fin");
         */
        // String pathReporte = "/Users/mjapon/dev/SMARTFACT/VVETERINARIA/reportes/ticket.jasper";
        String pathReporte = "/Users/mjapon/JaspersoftWorkspace/MyReports/ticket.jasper";
        showTicket(6, pathReporte);
        /*
        Date desde = FechasUtil.parse("01/01/2018");
        Date hasta = FechasUtil.parse("01/01/2019");
        showReporte(desde, hasta, pathReporte);
        */
        System.out.println("fin");

    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{        
        Class.forName("org.postgresql.Driver");
        return  DriverManager.getConnection(URI_DB,"postgres", CLAVE_DB);
    }
    
    public static void logError(Throwable ex){
        System.out.println("Error al generar el reporte" + ex.getMessage());
        ex.printStackTrace();
    }
    
    public static void showTicket(Integer ticketId, String pathReporte) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File(pathReporte));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ticketid", ticketId);
            JasperPrint print = JasperFillManager.fillReport(report, params, getConnection());
            JasperViewer jasperViewer = new JasperViewer(print, false);
            jasperViewer.setVisible(true);
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, "Algo salio mal:" + ex.getMessage());
            logError(ex);
        }
    }

    public static void showFactura(Integer factId, String pathReporte) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File(pathReporte));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pfactid", factId);
            JasperPrint print = JasperFillManager.fillReport(report, params, getConnection());
            JasperViewer jasperViewer = new JasperViewer(print, false);
            jasperViewer.setVisible(true);
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, "Algo salio mal:" + ex.getMessage());
            logError(ex);
        }
    }

    public static void showReporte(Date desde, Date hasta, String pathReporte) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File(pathReporte));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pdesde", desde);
            params.put("phasta", hasta);
            JasperPrint print = JasperFillManager.fillReport(report, params, getConnection());
            JasperViewer jasperViewer = new JasperViewer(print, false);
            jasperViewer.setVisible(true);
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, "Algo salio mal:" + ex.getMessage());
            logError(ex);
        }
    }

    public static void showReporte(Map<String, Object> params, String pathReporte) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(new File(pathReporte));
            JasperPrint print = JasperFillManager.fillReport(report, params, getConnection());
            JasperViewer jasperViewer = new JasperViewer(print, false);
            jasperViewer.setVisible(true);
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, "Algo salio mal:" + ex.getMessage());
            logError(ex);
        }
    }

    private void PrintReportToPrinter(JasperPrint jasperPrint) throws JRException {

        //Get the printers names
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        //Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
        String selectedPrinter = "Microsoft XPS Document Writer";
        // String selectedPrinter = "\\\\S-BPPRINT\\HP Color LaserJet 4700"; // examlpe to network shared printer

        System.out.println("Number of print services: " + services.length);
        PrintService selectedService = null;

        //Set the printing settings
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
        printRequestAttributeSet.add(new Copies(1));
        if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
            printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
        } else {
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
        }
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setConfiguration(configuration);

        //Iterate through available printer, and once matched with our <selectedPrinter>, go ahead and print!
        if (services != null && services.length != 0) {
            for (PrintService service : services) {
                String existingPrinter = service.getName();
                if (existingPrinter.equals(selectedPrinter)) {
                    selectedService = service;
                    break;
                }
            }
        }
        if (selectedService != null) {
            try {
                //Lets the printer do its magic!
                exporter.exportReport();
            } catch (Exception e) {
                System.out.println("JasperReport Error: " + e.getMessage());
            }
        } else {
            System.out.println("JasperReport Error: Printer not found!");
        }
    }
}
