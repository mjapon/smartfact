/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author manuel.japon
 */
public class ArrayUtil {
    
    public static <T> List<T> streamToList(Stream stream){
        List<T> parseList = (List<T>)stream.collect(Collectors.toList());
        return parseList;
    }
    
    public static String[] streamToStrArray(Stream stream){
         String[] resultArray = (String[])stream.toArray(String[]::new);
         return  resultArray;
    }
    
}
