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
import smf.entity.Movtransacc;
import smf.entity.Facturas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Transacciones;

/**
 *
 * @author mjapon
 */
public class TransaccionesJpaController extends BaseJpaController<Transacciones> implements Serializable {

    public TransaccionesJpaController(EntityManager em) {
        super(em);
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(Transacciones transacciones) {
        if (transacciones.getFacturasCollection() == null) {
            transacciones.setFacturasCollection(new ArrayList<Facturas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movtransacc movtransacc = transacciones.getMovtransacc();
            if (movtransacc != null) {
                movtransacc = em.getReference(movtransacc.getClass(), movtransacc.getMovId());
                transacciones.setMovtransacc(movtransacc);
            }
            Collection<Facturas> attachedFacturasCollection = new ArrayList<Facturas>();
            for (Facturas facturasCollectionFacturasToAttach : transacciones.getFacturasCollection()) {
                facturasCollectionFacturasToAttach = em.getReference(facturasCollectionFacturasToAttach.getClass(), facturasCollectionFacturasToAttach.getFactId());
                attachedFacturasCollection.add(facturasCollectionFacturasToAttach);
            }
            transacciones.setFacturasCollection(attachedFacturasCollection);
            em.persist(transacciones);
            if (movtransacc != null) {
                Transacciones oldTraIdOfMovtransacc = movtransacc.getTraId();
                if (oldTraIdOfMovtransacc != null) {
                    oldTraIdOfMovtransacc.setMovtransacc(null);
                    oldTraIdOfMovtransacc = em.merge(oldTraIdOfMovtransacc);
                }
                movtransacc.setTraId(transacciones);
                movtransacc = em.merge(movtransacc);
            }
            for (Facturas facturasCollectionFacturas : transacciones.getFacturasCollection()) {
                Transacciones oldTraIdOfFacturasCollectionFacturas = facturasCollectionFacturas.getTraId();
                facturasCollectionFacturas.setTraId(transacciones);
                facturasCollectionFacturas = em.merge(facturasCollectionFacturas);
                if (oldTraIdOfFacturasCollectionFacturas != null) {
                    oldTraIdOfFacturasCollectionFacturas.getFacturasCollection().remove(facturasCollectionFacturas);
                    oldTraIdOfFacturasCollectionFacturas = em.merge(oldTraIdOfFacturasCollectionFacturas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Transacciones transacciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transacciones persistentTransacciones = em.find(Transacciones.class, transacciones.getTraId());
            Movtransacc movtransaccOld = persistentTransacciones.getMovtransacc();
            Movtransacc movtransaccNew = transacciones.getMovtransacc();
            Collection<Facturas> facturasCollectionOld = persistentTransacciones.getFacturasCollection();
            Collection<Facturas> facturasCollectionNew = transacciones.getFacturasCollection();
            List<String> illegalOrphanMessages = null;
            for (Facturas facturasCollectionOldFacturas : facturasCollectionOld) {
                if (!facturasCollectionNew.contains(facturasCollectionOldFacturas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Facturas " + facturasCollectionOldFacturas + " since its traId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (movtransaccNew != null) {
                movtransaccNew = em.getReference(movtransaccNew.getClass(), movtransaccNew.getMovId());
                transacciones.setMovtransacc(movtransaccNew);
            }
            Collection<Facturas> attachedFacturasCollectionNew = new ArrayList<Facturas>();
            for (Facturas facturasCollectionNewFacturasToAttach : facturasCollectionNew) {
                facturasCollectionNewFacturasToAttach = em.getReference(facturasCollectionNewFacturasToAttach.getClass(), facturasCollectionNewFacturasToAttach.getFactId());
                attachedFacturasCollectionNew.add(facturasCollectionNewFacturasToAttach);
            }
            facturasCollectionNew = attachedFacturasCollectionNew;
            transacciones.setFacturasCollection(facturasCollectionNew);
            transacciones = em.merge(transacciones);
            if (movtransaccOld != null && !movtransaccOld.equals(movtransaccNew)) {
                movtransaccOld.setTraId(null);
                movtransaccOld = em.merge(movtransaccOld);
            }
            if (movtransaccNew != null && !movtransaccNew.equals(movtransaccOld)) {
                Transacciones oldTraIdOfMovtransacc = movtransaccNew.getTraId();
                if (oldTraIdOfMovtransacc != null) {
                    oldTraIdOfMovtransacc.setMovtransacc(null);
                    oldTraIdOfMovtransacc = em.merge(oldTraIdOfMovtransacc);
                }
                movtransaccNew.setTraId(transacciones);
                movtransaccNew = em.merge(movtransaccNew);
            }
            for (Facturas facturasCollectionNewFacturas : facturasCollectionNew) {
                if (!facturasCollectionOld.contains(facturasCollectionNewFacturas)) {
                    Transacciones oldTraIdOfFacturasCollectionNewFacturas = facturasCollectionNewFacturas.getTraId();
                    facturasCollectionNewFacturas.setTraId(transacciones);
                    facturasCollectionNewFacturas = em.merge(facturasCollectionNewFacturas);
                    if (oldTraIdOfFacturasCollectionNewFacturas != null && !oldTraIdOfFacturasCollectionNewFacturas.equals(transacciones)) {
                        oldTraIdOfFacturasCollectionNewFacturas.getFacturasCollection().remove(facturasCollectionNewFacturas);
                        oldTraIdOfFacturasCollectionNewFacturas = em.merge(oldTraIdOfFacturasCollectionNewFacturas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transacciones.getTraId();
                if (findTransacciones(id) == null) {
                    throw new NonexistentEntityException("The transacciones with id " + id + " no longer exists.");
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
            Transacciones transacciones;
            try {
                transacciones = em.getReference(Transacciones.class, id);
                transacciones.getTraId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transacciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Facturas> facturasCollectionOrphanCheck = transacciones.getFacturasCollection();
            for (Facturas facturasCollectionOrphanCheckFacturas : facturasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transacciones (" + transacciones + ") cannot be destroyed since the Facturas " + facturasCollectionOrphanCheckFacturas + " in its facturasCollection field has a non-nullable traId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Movtransacc movtransacc = transacciones.getMovtransacc();
            if (movtransacc != null) {
                movtransacc.setTraId(null);
                movtransacc = em.merge(movtransacc);
            }
            em.remove(transacciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Transacciones> findTransaccionesEntities() {
        return findTransaccionesEntities(true, -1, -1);
    }

    public List<Transacciones> findTransaccionesEntities(int maxResults, int firstResult) {
        return findTransaccionesEntities(false, maxResults, firstResult);
    }

    private List<Transacciones> findTransaccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transacciones.class));
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

    public Transacciones findTransacciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transacciones.class, id);
        } finally {
            //em.close();
        }
    }
    
    public Transacciones findTransaccByTraId(Integer traId){
        Transacciones transacc = getResultFirst("from Transacciones o where o.traId = "+traId);
        return transacc;        
    }

    public int getTransaccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transacciones> rt = cq.from(Transacciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    
}
