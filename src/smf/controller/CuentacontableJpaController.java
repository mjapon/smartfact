/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import smf.entity.Cuentacontable;
import smf.util.tree.CCNodeItem;

/**
 *
 * @author manuel.japon
 */
public class CuentacontableJpaController extends BaseJpaController<Cuentacontable> implements Serializable {

    public CuentacontableJpaController(EntityManager em) {
        super(em);
    }    
    
    public List<CCNodeItem> listar(){
        //List<Cuentacontable> tmpList =  getResultList("from Cuentacontable o where o.ccParent = null order by o.ccId asc");
        List<Cuentacontable> tmpList =  getResultList("from Cuentacontable o order by o.ccId asc");
        List<CCNodeItem> returnList = tmpList.stream().map(e->new CCNodeItem(e.getCcCod(),
                e.getCcNombre(), 
                e.getCcParent()!=null?e.getCcParent().intValue():null,
                e.getCcId().intValue()
        )).collect(Collectors.toList());
        return returnList;
    }
    
    
    public List<CCNodeItem> listarChild(BigInteger parent){
        List<Cuentacontable> tmpList =  getResultList(
                String.format("from Cuentacontable o where o.ccParent = %d order by o.ccId asc", parent)
        );
        List<CCNodeItem> returnList = tmpList.stream().map(e->new CCNodeItem(e.getCcCod(),
                e.getCcNombre(), 
                parent.intValue(),
                e.getCcId().intValue()
        )).collect(Collectors.toList());
        return returnList;
    }
    
    public String getNext(Integer parent){
        String sql = String.format("select max(o.cc_cod) from cuentacontable o where o.cc_parent = %d", parent);
        
        String max = (String)newNativeQuery(sql).getSingleResult();
        if (max == null){
            return "01";
        }
        else{
            String maxend = max.substring(max.lastIndexOf(".")+1);
            Integer maxInteger = Integer.valueOf(maxend)+1;
            return String.format("0%d",maxInteger);
        }
    }
    
    public void crear(Integer parentId, String cod, String nombre, Character tipoCuenta) throws Throwable{
        try{
            beginTrans();
            Cuentacontable newCC = new Cuentacontable();
            newCC.setCcCod(cod);
            newCC.setCcNombre(nombre);
            newCC.setCcParent(new BigInteger(parentId.toString()));
            newCC.setCcTipocuenta(tipoCuenta);
            newCC.setCcTipoestado(1);
            newCC.setCcSigno('P');
            em.persist(newCC);
            commitTrans();
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
            rollbackTrans();
        }
    }
}
