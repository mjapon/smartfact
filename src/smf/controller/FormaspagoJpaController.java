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
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Formaspago;

/**
 *
 * @author mjapon
 */
public class FormaspagoJpaController extends BaseJpaController<Formaspago> implements Serializable {

    public FormaspagoJpaController(EntityManager em) {
        super(em);
    }
    

    public void create(Formaspago formaspago) {
        if (formaspago.getPagosfactCollection() == null) {
            formaspago.setPagosfactCollection(new ArrayList<Pagosfact>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pagosfact> attachedPagosfactCollection = new ArrayList<Pagosfact>();
            for (Pagosfact pagosfactCollectionPagosfactToAttach : formaspago.getPagosfactCollection()) {
                pagosfactCollectionPagosfactToAttach = em.getReference(pagosfactCollectionPagosfactToAttach.getClass(), pagosfactCollectionPagosfactToAttach.getPgfId());
                attachedPagosfactCollection.add(pagosfactCollectionPagosfactToAttach);
            }
            formaspago.setPagosfactCollection(attachedPagosfactCollection);
            em.persist(formaspago);
            for (Pagosfact pagosfactCollectionPagosfact : formaspago.getPagosfactCollection()) {
                Formaspago oldFpIdOfPagosfactCollectionPagosfact = pagosfactCollectionPagosfact.getFpId();
                pagosfactCollectionPagosfact.setFpId(formaspago);
                pagosfactCollectionPagosfact = em.merge(pagosfactCollectionPagosfact);
                if (oldFpIdOfPagosfactCollectionPagosfact != null) {
                    oldFpIdOfPagosfactCollectionPagosfact.getPagosfactCollection().remove(pagosfactCollectionPagosfact);
                    oldFpIdOfPagosfactCollectionPagosfact = em.merge(oldFpIdOfPagosfactCollectionPagosfact);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Formaspago formaspago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formaspago persistentFormaspago = em.find(Formaspago.class, formaspago.getFpId());
            Collection<Pagosfact> pagosfactCollectionOld = persistentFormaspago.getPagosfactCollection();
            Collection<Pagosfact> pagosfactCollectionNew = formaspago.getPagosfactCollection();
            List<String> illegalOrphanMessages = null;
            for (Pagosfact pagosfactCollectionOldPagosfact : pagosfactCollectionOld) {
                if (!pagosfactCollectionNew.contains(pagosfactCollectionOldPagosfact)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagosfact " + pagosfactCollectionOldPagosfact + " since its fpId field is not nullable.");
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
            formaspago.setPagosfactCollection(pagosfactCollectionNew);
            formaspago = em.merge(formaspago);
            for (Pagosfact pagosfactCollectionNewPagosfact : pagosfactCollectionNew) {
                if (!pagosfactCollectionOld.contains(pagosfactCollectionNewPagosfact)) {
                    Formaspago oldFpIdOfPagosfactCollectionNewPagosfact = pagosfactCollectionNewPagosfact.getFpId();
                    pagosfactCollectionNewPagosfact.setFpId(formaspago);
                    pagosfactCollectionNewPagosfact = em.merge(pagosfactCollectionNewPagosfact);
                    if (oldFpIdOfPagosfactCollectionNewPagosfact != null && !oldFpIdOfPagosfactCollectionNewPagosfact.equals(formaspago)) {
                        oldFpIdOfPagosfactCollectionNewPagosfact.getPagosfactCollection().remove(pagosfactCollectionNewPagosfact);
                        oldFpIdOfPagosfactCollectionNewPagosfact = em.merge(oldFpIdOfPagosfactCollectionNewPagosfact);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaspago.getFpId();
                if (findFormaspago(id) == null) {
                    throw new NonexistentEntityException("The formaspago with id " + id + " no longer exists.");
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
            Formaspago formaspago;
            try {
                formaspago = em.getReference(Formaspago.class, id);
                formaspago.getFpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaspago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pagosfact> pagosfactCollectionOrphanCheck = formaspago.getPagosfactCollection();
            for (Pagosfact pagosfactCollectionOrphanCheckPagosfact : pagosfactCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formaspago (" + formaspago + ") cannot be destroyed since the Pagosfact " + pagosfactCollectionOrphanCheckPagosfact + " in its pagosfactCollection field has a non-nullable fpId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formaspago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Formaspago> findFormaspagoEntities() {
        return findFormaspagoEntities(true, -1, -1);
    }

    public List<Formaspago> findFormaspagoEntities(int maxResults, int firstResult) {
        return findFormaspagoEntities(false, maxResults, firstResult);
    }

    private List<Formaspago> findFormaspagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formaspago.class));
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

    public Formaspago findFormaspago(Integer id) {        
        try {
            return em.find(Formaspago.class, id);
        } finally {
            ////em.close();
        }
    }

    public int getFormaspagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formaspago> rt = cq.from(Formaspago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
}
