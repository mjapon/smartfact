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
import smf.entity.Estadospago;
import smf.entity.Facturas;
import smf.entity.Formaspago;
import smf.entity.Pagosfact;

/**
 *
 * @author mjapon
 */
public class PagosfactJpaController implements Serializable {

    public PagosfactJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagosfact pagosfact) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadospago spId = pagosfact.getSpId();
            if (spId != null) {
                spId = em.getReference(spId.getClass(), spId.getSpId());
                pagosfact.setSpId(spId);
            }
            Facturas factId = pagosfact.getFactId();
            if (factId != null) {
                factId = em.getReference(factId.getClass(), factId.getFactId());
                pagosfact.setFactId(factId);
            }
            Formaspago fpId = pagosfact.getFpId();
            if (fpId != null) {
                fpId = em.getReference(fpId.getClass(), fpId.getFpId());
                pagosfact.setFpId(fpId);
            }
            em.persist(pagosfact);
            if (spId != null) {
                spId.getPagosfactCollection().add(pagosfact);
                spId = em.merge(spId);
            }
            if (factId != null) {
                factId.getPagosfactCollection().add(pagosfact);
                factId = em.merge(factId);
            }
            if (fpId != null) {
                fpId.getPagosfactCollection().add(pagosfact);
                fpId = em.merge(fpId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Pagosfact pagosfact) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagosfact persistentPagosfact = em.find(Pagosfact.class, pagosfact.getPgfId());
            Estadospago spIdOld = persistentPagosfact.getSpId();
            Estadospago spIdNew = pagosfact.getSpId();
            Facturas factIdOld = persistentPagosfact.getFactId();
            Facturas factIdNew = pagosfact.getFactId();
            Formaspago fpIdOld = persistentPagosfact.getFpId();
            Formaspago fpIdNew = pagosfact.getFpId();
            if (spIdNew != null) {
                spIdNew = em.getReference(spIdNew.getClass(), spIdNew.getSpId());
                pagosfact.setSpId(spIdNew);
            }
            if (factIdNew != null) {
                factIdNew = em.getReference(factIdNew.getClass(), factIdNew.getFactId());
                pagosfact.setFactId(factIdNew);
            }
            if (fpIdNew != null) {
                fpIdNew = em.getReference(fpIdNew.getClass(), fpIdNew.getFpId());
                pagosfact.setFpId(fpIdNew);
            }
            pagosfact = em.merge(pagosfact);
            if (spIdOld != null && !spIdOld.equals(spIdNew)) {
                spIdOld.getPagosfactCollection().remove(pagosfact);
                spIdOld = em.merge(spIdOld);
            }
            if (spIdNew != null && !spIdNew.equals(spIdOld)) {
                spIdNew.getPagosfactCollection().add(pagosfact);
                spIdNew = em.merge(spIdNew);
            }
            if (factIdOld != null && !factIdOld.equals(factIdNew)) {
                factIdOld.getPagosfactCollection().remove(pagosfact);
                factIdOld = em.merge(factIdOld);
            }
            if (factIdNew != null && !factIdNew.equals(factIdOld)) {
                factIdNew.getPagosfactCollection().add(pagosfact);
                factIdNew = em.merge(factIdNew);
            }
            if (fpIdOld != null && !fpIdOld.equals(fpIdNew)) {
                fpIdOld.getPagosfactCollection().remove(pagosfact);
                fpIdOld = em.merge(fpIdOld);
            }
            if (fpIdNew != null && !fpIdNew.equals(fpIdOld)) {
                fpIdNew.getPagosfactCollection().add(pagosfact);
                fpIdNew = em.merge(fpIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosfact.getPgfId();
                if (findPagosfact(id) == null) {
                    throw new NonexistentEntityException("The pagosfact with id " + id + " no longer exists.");
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
            Pagosfact pagosfact;
            try {
                pagosfact = em.getReference(Pagosfact.class, id);
                pagosfact.getPgfId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosfact with id " + id + " no longer exists.", enfe);
            }
            Estadospago spId = pagosfact.getSpId();
            if (spId != null) {
                spId.getPagosfactCollection().remove(pagosfact);
                spId = em.merge(spId);
            }
            Facturas factId = pagosfact.getFactId();
            if (factId != null) {
                factId.getPagosfactCollection().remove(pagosfact);
                factId = em.merge(factId);
            }
            Formaspago fpId = pagosfact.getFpId();
            if (fpId != null) {
                fpId.getPagosfactCollection().remove(pagosfact);
                fpId = em.merge(fpId);
            }
            em.remove(pagosfact);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Pagosfact> findPagosfactEntities() {
        return findPagosfactEntities(true, -1, -1);
    }

    public List<Pagosfact> findPagosfactEntities(int maxResults, int firstResult) {
        return findPagosfactEntities(false, maxResults, firstResult);
    }

    private List<Pagosfact> findPagosfactEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagosfact.class));
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

    public Pagosfact findPagosfact(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagosfact.class, id);
        } finally {
            //em.close();
        }
    }

    public int getPagosfactCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagosfact> rt = cq.from(Pagosfact.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
