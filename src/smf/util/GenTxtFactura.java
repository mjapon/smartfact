/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import smf.util.datamodels.rows.FilaFactura;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author mjapon
 */
public class GenTxtFactura {
    
    
    public static String replace(Map<String,String> datos, String template){  
        
       
        
        
        
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");        
        Matcher m = pattern.matcher(template);
        
        String resultString = new String(template);
        while (m.find()) {
            String preclave = m.group(1);
            Integer numcarac = 0;
            String clave = preclave;
            if (preclave.contains(":")){
                String[] clavend = preclave.split(":");
                clave = clavend[0];
                String ncarac = clavend[1];
                numcarac = Integer.valueOf(ncarac);
            }
            
            String substr = "\\{"+preclave+"\\}";
            String value = datos.get(clave.trim()).trim();
            
            if (numcarac>0){
                int valuelength = value.length();
                if (valuelength<numcarac){
                    int nblack = numcarac- valuelength;
                    if (nblack>0){
                        value = StringUtil.fillBlank(value, nblack);
                    }
                }
                else{
                    value = value.substring(0, numcarac);
                }
            }
            
            resultString = resultString.replaceAll(substr, value);
        }
        return resultString;      
        
    }
    
    public static String getTxt(DatosCabeceraFactura datosCabecera, 
            List<FilaFactura> detalles, 
            TotalesFactura totalesFactura,
            String templateCabecera, 
            String templateDetalles, 
            String templatePie) throws IllegalArgumentException, IllegalAccessException{
        
        Map<String,String> datosCabeceraMap = new HashMap<String,String>();
        List<Map<String,String>> datosFilasList = new ArrayList<Map<String,String>>();
        Map<String,String> datosTotalesMap = new HashMap<String,String>();
        
        /*
        Field[] fields = DatosCabeceraFactura.class.getDeclaredFields();
        
        for(Field field: fields){
            String name = field.getName();
            Class type = field.getType();
            Object value = field.get(datosCabecera);                    
            datosCabeceraMap.put(name.trim(), String.valueOf(value));
        }
        */
        
        datosCabeceraMap.put("nroEstFact", datosCabecera.getNroEstFact());
        datosCabeceraMap.put("nroFactura", datosCabecera.getNumFactura());
        datosCabeceraMap.put("cliId", String.valueOf(datosCabecera.getCliId()));
        datosCabeceraMap.put("ci", String.valueOf(datosCabecera.getCi()));
        datosCabeceraMap.put("cliente", String.valueOf(datosCabecera.getCliente()));
        datosCabeceraMap.put("direccion", String.valueOf(datosCabecera.getDireccion()));
        datosCabeceraMap.put("telf", String.valueOf(datosCabecera.getTelf()));
        datosCabeceraMap.put("email", String.valueOf(datosCabecera.getEmail()));
        datosCabeceraMap.put("fechaFactura", String.valueOf(datosCabecera.getFechaFactura()));
        
        
        for(FilaFactura fila: detalles){            
            Map<String,String>  filaMap = new HashMap<String, String>();
            /*
            fields = FilaFactura.class.getDeclaredFields();
            for(Field field: fields){
                String name = field.getName();
                Class type = field.getType();
                Object value = field.get(datosCabecera);
                filaMap.put(name.trim(), String.valueOf(value));
            }          
            */
            filaMap.put("numFila", fila.getNumFila().toString());
            filaMap.put("codigoArt", fila.getCodigoArt().toString());
            filaMap.put("codBarra", fila.getCodBarra().toString());
            filaMap.put("nombreArt", fila.getNombreArt().toString());
            filaMap.put("cantidad", fila.getCantidad().toString());
            filaMap.put("precioUnitario", fila.getPrecioUnitario().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            filaMap.put("isIva", fila.isIsIva()?"S":"N");
            filaMap.put("subtotal", fila.getSubtotal().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            filaMap.put("total", fila.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            filaMap.put("valorIva", fila.getValorIva().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            
            datosFilasList.add(filaMap);
        }
        
        /*
        fields = TotalesFactura.class.getDeclaredFields();
        for(Field field: fields){
            String name = field.getName();
            Class type = field.getType();
            Object value = field.get(datosCabecera);
            datosTotalesMap.put(name.trim(), String.valueOf(value));
        }
        */
        datosTotalesMap.put("subtotal", totalesFactura.getSubtotal().toPlainString());
        datosTotalesMap.put("iva", totalesFactura.getIva().toPlainString());
        datosTotalesMap.put("descuento", totalesFactura.getDescuento().toPlainString());
        datosTotalesMap.put("total", totalesFactura.getTotal().toPlainString());
        
        StringBuffer txtFactura = new StringBuffer();
        
        txtFactura = txtFactura.append(replace(datosCabeceraMap, templateCabecera)).append("\n");
        for(Map filamap: datosFilasList){
            txtFactura = txtFactura.append(replace(filamap, templateDetalles)).append("\n");
        }
        txtFactura = txtFactura.append(replace(datosTotalesMap, templatePie));
        
        return txtFactura.toString();
    }   
    
    public static String getCopias(String original, Integer ncopias){
        StringBuilder copyresult = new StringBuilder(original);
        if (ncopias>1){
            for(int i=1;i<ncopias;i++){
                copyresult = copyresult.append("\n\n\n----\n\n\n").append(original);
            }
        }
        return copyresult.toString();
    }    
    
    public static void main(String[] args){
        
         String numeroFactura ="001001000000155";
        String secFactura = numeroFactura.substring(6);
        
        System.out.println(secFactura);
        
        /*
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher m = pattern.matcher("FOO{BAR}asfsd{23} asdfasdf{hola:3}");
        while (m.find()) {
            String s = m.group(1);
            System.out.println(s);
            // s now contains "BAR"
        }
            */
        
    }
    
}
