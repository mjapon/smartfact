/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mjapon
 */
public class FechasUtil {
    
    public static String formato = "dd/MM/yyyy";
    public static String formatoFechaHora = "dd/MM/yyyy HH:mm";
    public static SimpleDateFormat dateFormat;
    public static SimpleDateFormat dateHourFormat;
    public static Map<Integer, String> mesesMap;
    public static Map<Integer, String> diasMap;
    
    static{
        dateFormat = new SimpleDateFormat(formato);
        dateHourFormat= new SimpleDateFormat(formatoFechaHora);
        
        mesesMap = new HashMap<Integer, String>();
        mesesMap.put(0, "ENERO");
        mesesMap.put(1, "FEBRERO");
        mesesMap.put(2, "MARZO");
        mesesMap.put(3, "ABRIL");
        mesesMap.put(4, "MAYO");
        mesesMap.put(5, "JUNIO");
        mesesMap.put(6, "JULIO");
        mesesMap.put(7, "AGOSTO");
        mesesMap.put(8, "SEPTIEMBRE");
        mesesMap.put(9, "OCTUBRE");
        mesesMap.put(10, "NOMVIEMBRE");
        mesesMap.put(11, "DICIEMBRE");
        
         diasMap = new HashMap<Integer, String>();
        
        diasMap.put(1, "Domingo");
        diasMap.put(2, "Lunes");
        diasMap.put(3, "Martes");
        diasMap.put(4, "Miercoles");
        diasMap.put(5, "Jueves");
        diasMap.put(6, "Viernes");
        diasMap.put(7, "Sábado");
    }
    
    public static Date parse(String fecha) throws ParseException{        
        return dateFormat.parse(fecha);
    }
    
    public static String format(Date date){
        return dateFormat.format(date);
    }
    public static String formatDateHour(Date date){
        return dateHourFormat.format(date);
    }
    
    public static String getFechaActual(){
        return format(new Date());
    }
    
    public static String getMonthName(Integer index){
        if (mesesMap.containsKey(index)){
            return mesesMap.get(index);
        }
        return null;
    }
    
    public static Integer getMonthIndex(String name){
        if (mesesMap.containsValue(name)){
            for (Map.Entry<Integer, String> entry : mesesMap.entrySet()) {
                if (name.equalsIgnoreCase(entry.getValue())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
    
    public static Date sumarDia(Date fecha, int numdias){
        Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);
        
        calendar.add(Calendar.DAY_OF_WEEK, numdias);
        
        return calendar.getTime();
    }
    
     public static String getDayName(Integer index){
        return diasMap.get(index);
    }
    
    public static String getDayNameOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("valor para date es:"+ date);        
        System.out.println("Valor para day of week es:" + dayOfWeek);        
        return  FechasUtil.getDayName(dayOfWeek);
    }
}
