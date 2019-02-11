/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author manuel.japon
 */
public class ResumenEmpCntrl extends BaseJpaController<Object>{
    
    public ResumenEmpCntrl(EntityManager em) {
        super(em);
    }
    
    
    public Map<String, BigDecimal> getResumen(){
        Map<String, BigDecimal> returnMap = new HashMap<>();                 
        
        //Nro de inventarios
        String query = "select s.art_tipo, count(*) from articulos s GROUP BY s.art_tipo";
        List<Object[]> resultInv = newNativeQuery(query).getResultList();
        if (resultInv!=null && resultInv.size()>0){
            Long total = new Long(0);
            for (Object[] fila: resultInv){
                String tipo = (String)fila[0];
                Long totalItems = (Long)fila[1];
                total += totalItems;
                returnMap.put("INV_"+tipo, new BigDecimal(totalItems));
            }
            returnMap.put("INV",new BigDecimal(total));
        }
        
        //Articulos por expirar
        query = "select count(*) \n" +
                           "from articulos art \n" +
                           "where art.art_tipo <> 'S' \n" +
                           "and now() < art.art_feccadu \n" +
                           "and (now()+ INTERVAL '1 month') > art.art_feccadu";
        
        Long numArtXExpirar = (Long)getResultFirstItemNQ(query);
        
        returnMap.put("ARTXEXP",  new BigDecimal(numArtXExpirar));
        
        //Articulos expirados
        query = "select count(*)\n" +
                "from articulos art\n" +
                "where art.art_tipo <> 'S'\n" +
                "and now() > art.art_feccadu";
        
        Long numArtExpirados = (Long)getResultFirstItemNQ(query);
        
        returnMap.put("ARTEXP", new BigDecimal(numArtExpirados));
        
        return returnMap;
    }    
}