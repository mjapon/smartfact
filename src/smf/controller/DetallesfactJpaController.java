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
import smf.entity.Detallesfact;
import smf.entity.Facturas;
/**
 *
 * @author mjapon
 */
public class DetallesfactJpaController extends BaseJpaController implements Serializable {

    public DetallesfactJpaController(EntityManager em) {
        super(em);
    }

    public void create(Detallesfact detallesfact) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas factId = detallesfact.getFactId();
            if (factId != null) {
                factId = em.getReference(factId.getClass(), factId.getFactId());
                detallesfact.setFactId(factId);
            }
            em.persist(detallesfact);
            if (factId != null) {
                factId.getDetallesfactList().add(detallesfact);
                factId = em.merge(factId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Detallesfact detallesfact) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallesfact persistentDetallesfact = em.find(Detallesfact.class, detallesfact.getDetfId());
            Facturas factIdOld = persistentDetallesfact.getFactId();
            Facturas factIdNew = detallesfact.getFactId();
            if (factIdNew != null) {
                factIdNew = em.getReference(factIdNew.getClass(), factIdNew.getFactId());
                detallesfact.setFactId(factIdNew);
            }
            detallesfact = em.merge(detallesfact);
            if (factIdOld != null && !factIdOld.equals(factIdNew)) {
                factIdOld.getDetallesfactList().remove(detallesfact);
                factIdOld = em.merge(factIdOld);
            }
            if (factIdNew != null && !factIdNew.equals(factIdOld)) {
                factIdNew.getDetallesfactList().add(detallesfact);
                factIdNew = em.merge(factIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallesfact.getDetfId();
                if (findDetallesfact(id) == null) {
                    throw new NonexistentEntityException("The detallesfact with id " + id + " no longer exists.");
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
            Detallesfact detallesfact;
            try {
                detallesfact = em.getReference(Detallesfact.class, id);
                detallesfact.getDetfId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallesfact with id " + id + " no longer exists.", enfe);
            }
            Facturas factId = detallesfact.getFactId();
            if (factId != null) {
                factId.getDetallesfactList().remove(detallesfact);
                factId = em.merge(factId);
            }
            em.remove(detallesfact);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Detallesfact> findDetallesfactEntities() {
        return findDetallesfactEntities(true, -1, -1);
    }

    public List<Detallesfact> findDetallesfactEntities(int maxResults, int firstResult) {
        return findDetallesfactEntities(false, maxResults, firstResult);
    }

    private List<Detallesfact> findDetallesfactEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallesfact.class));
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

    public Detallesfact findDetallesfact(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallesfact.class, id);
        } finally {
            //em.close();
        }
    }

    public int getDetallesfactCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallesfact> rt = cq.from(Detallesfact.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
