/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.math.BigDecimal;

/**
 *
 * @author mjapon
 */
public class NumbersUtil {    
    
    public static BigDecimal round(BigDecimal value, int scale){
        return value!=null? value.setScale(2, BigDecimal.ROUND_HALF_UP): null;
    }
    
    public static BigDecimal round2(BigDecimal value){
        return NumbersUtil.round(value, 2);
    }
    
    public static String round2ToStr(BigDecimal value){
        if (value != null){
            return round2(value).toPlainString();
        }
        return "";
        
    }
}
