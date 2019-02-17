/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.text.MessageFormat;

/**
 *
 * @author mjapon
 */
public class StringUtil {
    
    public static String format(String template, Object... args){        
        return new MessageFormat(template).format(args);
    }
    
    public static Boolean isEmpty(String cadena){        
        return cadena == null || cadena.trim().length() == 0;        
    }
    
    public static Boolean isNotEmpty(String cadena){        
        return cadena != null && cadena.trim().length() > 0;        
    }
    
    public static String zfill(Integer value, Integer length){
        return String.format("%0"+length+"d", value);
    }
    
    public static String fillBlank(String cad, Integer length){
        return String.format("%-"+length+"s", cad).replace(' ', ' ');
    }
    
    public static String checkNull(String string){
        return string!=null?string:"";
    }
    
    public static void main(String args[]){
        System.out.println("test ..Z>");        
        System.out.println(format("{0} es {1}","cero", "uno"));
        System.out.println( String.format("%015d", 890) );
        
        String hola = "Hola";
        String rep = fillBlank("Hola", 20);
        System.out.println( rep.length()  );
        System.out.println( rep+"fin"  );
        System.out.println( hola.length()  );
    }
}
