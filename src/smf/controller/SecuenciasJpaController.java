/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Clientes;
import smf.entity.Secuencias;

/**
 *
 * @author mjapon
 */
public class SecuenciasJpaController extends BaseJpaController<Secuencias> implements Serializable {


    public SecuenciasJpaController(EntityManager em) {
        super(em);
    }

    public void create(Secuencias secuencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(secuencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Secuencias secuencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            secuencias = em.merge(secuencias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secuencias.getSecId();
                if (findSecuencias(id) == null) {
                    throw new NonexistentEntityException("The secuencias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secuencias secuencias;
            try {
                secuencias = em.getReference(Secuencias.class, id);
                secuencias.getSecId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secuencias with id " + id + " no longer exists.", enfe);
            }
            em.remove(secuencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Secuencias> findSecuenciasEntities() {
        return findSecuenciasEntities(true, -1, -1);
    }

    public List<Secuencias> findSecuenciasEntities(int maxResults, int firstResult) {
        return findSecuenciasEntities(false, maxResults, firstResult);
    }

    private List<Secuencias> findSecuenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secuencias.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            //em.close();
        }
    }

    public Secuencias findSecuencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secuencias.class, id);
        } finally {
            //em.close();
        }
    }

    public int getSecuenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secuencias> rt = cq.from(Secuencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public Secuencias getSecuencia(String clave){        
        String querystr = "from Secuencias a where a.secClave = '"+clave.trim()+"'";
        return getResultFirst(newQuery(querystr));
    }
    
    public Integer getSecuenciaValue(String clave){
        Secuencias secuencias = getSecuencia(clave);
        if (secuencias != null){
            return secuencias.getSecValor();
        }
        return null;
    }
    
    
    public void genSecuencia(String clave) throws Exception{
        Secuencias secuencia = getSecuencia(clave);
        if (secuencia != null){
            secuencia.setSecValor(secuencia.getSecValor()+1 );
            em.persist(secuencia);
        }
    }
    
    public void genSecuencia(Integer secuenciaId) throws Exception{
        
        Secuencias secuencia = findSecuencias(secuenciaId);
        if (secuencia != null){
            secuencia.setSecValor(secuencia.getSecValor()+1 );
            em.persist(secuencia);
            //this.edit(secuencia);
        }
        
    }           
            
            
    
}
