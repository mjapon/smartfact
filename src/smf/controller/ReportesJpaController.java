/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import smf.entity.Reportes;

/**
 *
 * @author manuel.japon
 */
public class ReportesJpaController extends BaseJpaController<Reportes> implements Serializable{
    
    public ReportesJpaController(EntityManager em){
        super(em);
    }
    
    public List<Reportes> listAll(){
        return newNamedQuery("Reportes.findAll").getResultList();
    }
    
    public Map<String, Boolean> proccesParams(String params){
        Map<String,Boolean> resultMap = new HashMap<String, Boolean>();
        
        //Params llega fec1:0,fec2:0,art:0,cli:0
        if (params != null && params.trim().length()>0){
            String[] paramsArray =  params.split(",");
            for (String param: paramsArray){
                String[] paramValueArray = param.split(":");                
                String keyParam = paramValueArray[0];
                String valueParam = paramValueArray[1];
                resultMap.put(keyParam, 1==Integer.valueOf(valueParam));
            }
        }        
        
        return resultMap;        
    }
}
