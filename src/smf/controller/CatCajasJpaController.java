/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Catcajas;
import smf.util.datamodels.rows.FilaCatCaja;
import smf.util.datamodels.rows.FilaPagoByCaja;

/**
 *
 * @author manuel.japon
 */
public class CatCajasJpaController extends BaseJpaController<Catcajas>{
    
    public CatCajasJpaController(EntityManager em){
        super(em);
    }
    
    public List<Catcajas> listar(){
        return em.createNamedQuery("Catcajas.findAll").getResultList();
    }
    
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catcajas cajas;
            try {
                cajas = em.getReference(Catcajas.class, id);
                cajas.getCcId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajas with id " + id + " no longer exists.", enfe);
            }
            em.remove(cajas);
            em.getTransaction().commit();
        } finally {
            /*
            if (em != null) {
                //em.close();
            }
            */
        }
    }
    
    public List<FilaPagoByCaja> listForGridPagosFact(BigDecimal montoEfectivo){
        
        List<Catcajas> auxresult = listar();
        List<FilaPagoByCaja> result = new ArrayList<>();
        for (Catcajas cc: auxresult){
            BigDecimal monto = BigDecimal.ZERO;
            if (cc.getCcId() == 1){//Caja General
                monto = montoEfectivo;
            }
            
            result.add(new FilaPagoByCaja(cc.getCcId(), cc.getCcNombre(), monto));
        }
        return result;
        
    }
    
    
    public List<FilaCatCaja> listForGrid(){
        List<Catcajas> auxresult = listar();
        List<FilaCatCaja> result = new ArrayList<>();
        for (Catcajas cc: auxresult){
            result.add(new FilaCatCaja(cc.getCcId(), cc.getCcNombre(), cc.getCcObservacion()));
        }
        return result;
    }
    
    public boolean existe(String nombre){
        Long cuenta = (Long)em.createQuery("select count(o) from Catcajas o where o.ccNombre ='"+nombre.trim().toUpperCase()+"'").getSingleResult();
        return cuenta>0;
    }
    
    public boolean enUso(Integer catCajaId){
        Long cuenta = (Long)em.createQuery("select count(o) from Categorias o where o.catCaja = "+catCajaId).getSingleResult();
        return cuenta>0;
    }
    
    public void create(String nombre, String observacion) throws Exception{
        try{
            if (!existe(nombre)){
            beginTrans();
            Catcajas catcajas = new Catcajas();
            catcajas.setCcNombre(nombre.trim().toUpperCase());
            catcajas.setCcObservacion(observacion);
            catcajas.setCcFechacreacion(new Date());
            catcajas.setCcStatus(0);
            em.persist(catcajas);
            commitTrans();
            }
            else{
                throw new Exception("La caja:"+nombre+" ya existe");
            }
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            throw ex;
        }        
    }
    
    public void editar(Integer catCajaId, String nombre, String observacion) throws Exception{
        try{
            beginTrans();
            Catcajas catcaja=  em.find(Catcajas.class, catCajaId);
            if (catcaja != null){
                String currentName = catcaja.getCcNombre();
                if (!currentName.trim().equalsIgnoreCase(nombre.trim().toUpperCase())){
                    if (!existe(nombre)){
                        catcaja.setCcNombre(nombre);
                    }
                    else{
                        throw new Exception("La caja"+nombre+" ya existe, ingrese otro nombre");
                    }
                }
                if (observacion != null){
                    catcaja.setCcObservacion(observacion);
                }
                em.persist(catcaja);
            }            
            commitTrans();
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            throw ex;
        }
    }
    
    public void eliminar(Integer catCajaId)throws Exception{
        if (!enUso(catCajaId)){
            Catcajas cat = em.find(Catcajas.class, catCajaId);
            if (cat == null){
                throw  new Exception("No esta registrada la categoria con el id especificado");
            }
            destroy(catCajaId);
        }
        else{
            throw  new Exception("Esta caja esta siendo utilizada, no puede ser borrada");
        }        
    }
    
}
