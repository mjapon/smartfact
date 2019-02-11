/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.controller.exceptions.NonexistentEntityException;
import smf.controller.exceptions.PreexistingEntityException;
import smf.entity.Ctes;

/**
 *
 * @author mjapon
 */
public class CtesJpaController extends BaseJpaController implements Serializable {

    public CtesJpaController(EntityManager em) {
        super(em);
    }

    public void create(Ctes ctes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ctes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCtes(ctes.getCtesId()) != null) {
                throw new PreexistingEntityException("Ctes " + ctes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Ctes ctes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ctes = em.merge(ctes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ctes.getCtesId();
                if (findCtes(id) == null) {
                    throw new NonexistentEntityException("The ctes with id " + id + " no longer exists.");
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
            Ctes ctes;
            try {
                ctes = em.getReference(Ctes.class, id);
                ctes.getCtesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ctes with id " + id + " no longer exists.", enfe);
            }
            em.remove(ctes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Ctes> findCtesEntities() {
        return findCtesEntities(true, -1, -1);
    }

    public List<Ctes> findCtesEntities(int maxResults, int firstResult) {
        return findCtesEntities(false, maxResults, firstResult);
    }

    private List<Ctes> findCtesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ctes.class));
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

    public Ctes findCtes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ctes.class, id);
        } finally {
            //em.close();
        }
    }

    public int getCtesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ctes> rt = cq.from(Ctes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public Ctes findByClave(String clave){
        
        String querystr = "from Ctes a where a.ctesClave = '"+clave.trim()+"'";
        Query query = em.createQuery(querystr);        
        
        List<Ctes> ctess = query.getResultList();
        if (ctess.size()>0){
            return ctess.get(0);
        }
        else{
            return null;
        }
        
    }
    
    public String findValueByClave(String clave){
        Ctes ctes = findByClave(clave);
        if (ctes !=null){
            return ctes.getCtesValor();
        }
        return null;
    }
    
}
