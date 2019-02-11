/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.entity.Pagosfact;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Estadospago;

/**
 *
 * @author mjapon
 */
public class EstadospagoJpaController extends BaseJpaController<Estadospago> implements Serializable {

    public EstadospagoJpaController(EntityManager em) {
        super(em);
    }

    public void create(Estadospago estadospago) {
        if (estadospago.getPagosfactCollection() == null) {
            estadospago.setPagosfactCollection(new ArrayList<Pagosfact>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pagosfact> attachedPagosfactCollection = new ArrayList<Pagosfact>();
            for (Pagosfact pagosfactCollectionPagosfactToAttach : estadospago.getPagosfactCollection()) {
                pagosfactCollectionPagosfactToAttach = em.getReference(pagosfactCollectionPagosfactToAttach.getClass(), pagosfactCollectionPagosfactToAttach.getPgfId());
                attachedPagosfactCollection.add(pagosfactCollectionPagosfactToAttach);
            }
            estadospago.setPagosfactCollection(attachedPagosfactCollection);
            em.persist(estadospago);
            for (Pagosfact pagosfactCollectionPagosfact : estadospago.getPagosfactCollection()) {
                Estadospago oldSpIdOfPagosfactCollectionPagosfact = pagosfactCollectionPagosfact.getSpId();
                pagosfactCollectionPagosfact.setSpId(estadospago);
                pagosfactCollectionPagosfact = em.merge(pagosfactCollectionPagosfact);
                if (oldSpIdOfPagosfactCollectionPagosfact != null) {
                    oldSpIdOfPagosfactCollectionPagosfact.getPagosfactCollection().remove(pagosfactCollectionPagosfact);
                    oldSpIdOfPagosfactCollectionPagosfact = em.merge(oldSpIdOfPagosfactCollectionPagosfact);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Estadospago estadospago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadospago persistentEstadospago = em.find(Estadospago.class, estadospago.getSpId());
            Collection<Pagosfact> pagosfactCollectionOld = persistentEstadospago.getPagosfactCollection();
            Collection<Pagosfact> pagosfactCollectionNew = estadospago.getPagosfactCollection();
            List<String> illegalOrphanMessages = null;
            for (Pagosfact pagosfactCollectionOldPagosfact : pagosfactCollectionOld) {
                if (!pagosfactCollectionNew.contains(pagosfactCollectionOldPagosfact)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagosfact " + pagosfactCollectionOldPagosfact + " since its spId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pagosfact> attachedPagosfactCollectionNew = new ArrayList<Pagosfact>();
            for (Pagosfact pagosfactCollectionNewPagosfactToAttach : pagosfactCollectionNew) {
                pagosfactCollectionNewPagosfactToAttach = em.getReference(pagosfactCollectionNewPagosfactToAttach.getClass(), pagosfactCollectionNewPagosfactToAttach.getPgfId());
                attachedPagosfactCollectionNew.add(pagosfactCollectionNewPagosfactToAttach);
            }
            pagosfactCollectionNew = attachedPagosfactCollectionNew;
            estadospago.setPagosfactCollection(pagosfactCollectionNew);
            estadospago = em.merge(estadospago);
            for (Pagosfact pagosfactCollectionNewPagosfact : pagosfactCollectionNew) {
                if (!pagosfactCollectionOld.contains(pagosfactCollectionNewPagosfact)) {
                    Estadospago oldSpIdOfPagosfactCollectionNewPagosfact = pagosfactCollectionNewPagosfact.getSpId();
                    pagosfactCollectionNewPagosfact.setSpId(estadospago);
                    pagosfactCollectionNewPagosfact = em.merge(pagosfactCollectionNewPagosfact);
                    if (oldSpIdOfPagosfactCollectionNewPagosfact != null && !oldSpIdOfPagosfactCollectionNewPagosfact.equals(estadospago)) {
                        oldSpIdOfPagosfactCollectionNewPagosfact.getPagosfactCollection().remove(pagosfactCollectionNewPagosfact);
                        oldSpIdOfPagosfactCollectionNewPagosfact = em.merge(oldSpIdOfPagosfactCollectionNewPagosfact);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadospago.getSpId();
                if (findEstadospago(id) == null) {
                    throw new NonexistentEntityException("The estadospago with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadospago estadospago;
            try {
                estadospago = em.getReference(Estadospago.class, id);
                estadospago.getSpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadospago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pagosfact> pagosfactCollectionOrphanCheck = estadospago.getPagosfactCollection();
            for (Pagosfact pagosfactCollectionOrphanCheckPagosfact : pagosfactCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadospago (" + estadospago + ") cannot be destroyed since the Pagosfact " + pagosfactCollectionOrphanCheckPagosfact + " in its pagosfactCollection field has a non-nullable spId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadospago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Estadospago> findEstadospagoEntities() {
        return findEstadospagoEntities(true, -1, -1);
    }

    public List<Estadospago> findEstadospagoEntities(int maxResults, int firstResult) {
        return findEstadospagoEntities(false, maxResults, firstResult);
    }

    private List<Estadospago> findEstadospagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadospago.class));
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

    public Estadospago findEstadospago(Integer id) {
            return em.find(Estadospago.class, id);
            
    }

    public int getEstadospagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadospago> rt = cq.from(Estadospago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
