/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Estadospago;
import smf.entity.Facturas;
import smf.entity.Movtransacc;
import smf.entity.Pagosfact;
import smf.entity.Transacciones;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.datamodels.rows.FilaAbonos;
import smf.util.form.AbonoForm;

/**
 *
 * @author mjapon
 */
public class MovtransaccJpaController  extends BaseJpaController<Facturas> implements Serializable {

    public MovtransaccJpaController(EntityManager em) {
        super(em);
    }

    public void create(Movtransacc movtransacc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas factIdRel = movtransacc.getFactIdRel();
            if (factIdRel != null) {
                factIdRel = em.getReference(factIdRel.getClass(), factIdRel.getFactId());
                movtransacc.setFactIdRel(factIdRel);
            }
            Transacciones traId = movtransacc.getTraId();
            if (traId != null) {
                traId = em.getReference(traId.getClass(), traId.getTraId());
                movtransacc.setTraId(traId);
            }
            em.persist(movtransacc);
            if (factIdRel != null) {
                factIdRel.getMovtransaccCollection().add(movtransacc);
                factIdRel = em.merge(factIdRel);
            }
            if (traId != null) {
                Movtransacc oldMovtransaccOfTraId = traId.getMovtransacc();
                if (oldMovtransaccOfTraId != null) {
                    oldMovtransaccOfTraId.setTraId(null);
                    oldMovtransaccOfTraId = em.merge(oldMovtransaccOfTraId);
                }
                traId.setMovtransacc(movtransacc);
                traId = em.merge(traId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Movtransacc movtransacc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movtransacc persistentMovtransacc = em.find(Movtransacc.class, movtransacc.getMovId());
            Facturas factIdRelOld = persistentMovtransacc.getFactIdRel();
            Facturas factIdRelNew = movtransacc.getFactIdRel();
            Transacciones traIdOld = persistentMovtransacc.getTraId();
            Transacciones traIdNew = movtransacc.getTraId();
            if (factIdRelNew != null) {
                factIdRelNew = em.getReference(factIdRelNew.getClass(), factIdRelNew.getFactId());
                movtransacc.setFactIdRel(factIdRelNew);
            }
            if (traIdNew != null) {
                traIdNew = em.getReference(traIdNew.getClass(), traIdNew.getTraId());
                movtransacc.setTraId(traIdNew);
            }
            movtransacc = em.merge(movtransacc);
            if (factIdRelOld != null && !factIdRelOld.equals(factIdRelNew)) {
                factIdRelOld.getMovtransaccCollection().remove(movtransacc);
                factIdRelOld = em.merge(factIdRelOld);
            }
            if (factIdRelNew != null && !factIdRelNew.equals(factIdRelOld)) {
                factIdRelNew.getMovtransaccCollection().add(movtransacc);
                factIdRelNew = em.merge(factIdRelNew);
            }
            if (traIdOld != null && !traIdOld.equals(traIdNew)) {
                traIdOld.setMovtransacc(null);
                traIdOld = em.merge(traIdOld);
            }
            if (traIdNew != null && !traIdNew.equals(traIdOld)) {
                Movtransacc oldMovtransaccOfTraId = traIdNew.getMovtransacc();
                if (oldMovtransaccOfTraId != null) {
                    oldMovtransaccOfTraId.setTraId(null);
                    oldMovtransaccOfTraId = em.merge(oldMovtransaccOfTraId);
                }
                traIdNew.setMovtransacc(movtransacc);
                traIdNew = em.merge(traIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movtransacc.getMovId();
                if (findMovtransacc(id) == null) {
                    throw new NonexistentEntityException("The movtransacc with id " + id + " no longer exists.");
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
            Movtransacc movtransacc;
            try {
                movtransacc = em.getReference(Movtransacc.class, id);
                movtransacc.getMovId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movtransacc with id " + id + " no longer exists.", enfe);
            }
            Facturas factIdRel = movtransacc.getFactIdRel();
            if (factIdRel != null) {
                factIdRel.getMovtransaccCollection().remove(movtransacc);
                factIdRel = em.merge(factIdRel);
            }
            Transacciones traId = movtransacc.getTraId();
            if (traId != null) {
                traId.setMovtransacc(null);
                traId = em.merge(traId);
            }
            em.remove(movtransacc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Movtransacc> findMovtransaccEntities() {
        return findMovtransaccEntities(true, -1, -1);
    }

    public List<Movtransacc> findMovtransaccEntities(int maxResults, int firstResult) {
        return findMovtransaccEntities(false, maxResults, firstResult);
    }

    private List<Movtransacc> findMovtransaccEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movtransacc.class));
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

    public Movtransacc findMovtransacc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movtransacc.class, id);
        } finally {
            //em.close();
        }
    }

    public int getMovtransaccCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movtransacc> rt = cq.from(Movtransacc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public List<Movtransacc> listarAbonos(Integer codPago){
        Query query = newQuery("from Movtransacc o where o.pgfId = "+codPago+" order by o.movFechareg desc");
        return query.getResultList();        
    }
    
    public List<FilaAbonos> listarAbonosRaw(Integer codPago){
        List<FilaAbonos> resultList = new ArrayList<>();
        String sql = String.format("select m.mov_id, m.mov_fechareg," +
        "  m.mov_monto, case when mov_valido = 0 then 'VALIDO' else 'ANULADO' end as estado,\n" +
        "  m.mov_observ," +
        "  c.cc_nombre " +
        " from movtransacc m " +
        " left join catcajas c on c.cc_id = m.cj_id " +
        " where pgf_id = %d order by m.mov_fechareg desc", codPago);
        
        List<Object[]> tmpres = newNativeQuery(sql).getResultList();
        for(Object[] row: tmpres){
            Integer movId = (Integer)row[0];
            Date fechareg = row[1]!=null?(Date)row[1]:null;
            BigDecimal monto = NumbersUtil.round2(row[2]!=null?(BigDecimal)row[2]:null);
            String estado = row[3]!=null?(String)row[3]:null;
            String obs = row[4]!=null?(String)row[4]:null;
            String caja = row[5]!=null?(String)row[5]:null;
            resultList.add(new FilaAbonos(movId, FechasUtil.formatDateHour(fechareg), monto, estado, obs, caja));
        }
        
        return resultList;
    }
    
    public void anularAbono( Integer movId, String obs) throws Throwable{
        try{
            beginTrans();
            if (obs == null){
                obs = "";
            }
            Movtransacc movtransacc = em.find(Movtransacc.class, movId);
            if (movtransacc != null){
                movtransacc.setMovValido(1);
                String currentobs = movtransacc.getMovObserv()!=null?movtransacc.getMovObserv():"";
                String theobs =  currentobs+" REGISTRO ANULADO:"+obs;
                movtransacc.setMovObserv(theobs);
                em.merge(movtransacc);
                
                  //Sumar el saldo en la factura            
                Pagosfact pago = em.find(Pagosfact.class, movtransacc.getPgfId());
                BigDecimal saldo  = pago.getPgfSaldo();
                BigDecimal newSaldo  = saldo.add(movtransacc.getMovMonto());

                if (newSaldo.compareTo(BigDecimal.ZERO)<0){
                    throw new Exception("El valor del abono es incorrecto:"+newSaldo.toPlainString());
                }
                pago.setPgfSaldo(newSaldo);

                if (newSaldo.compareTo(BigDecimal.ZERO)==0 ){
                    Estadospago cancelStatus = em.find(Estadospago.class, 1);
                    pago.setSpId(cancelStatus);
                }
                else if (newSaldo.compareTo(BigDecimal.ZERO)>0){
                    Estadospago cancelStatus = em.find(Estadospago.class, 2);
                    pago.setSpId(cancelStatus);
                }

                em.merge(pago);
            }
            
            commitTrans();
        }
        catch(Throwable ex){
            
            rollbackTrans();
            logErrorWithThrow(ex);
            
        }
        finally{
            if (em != null) {
                ////em.close();
            }
        }
    }
            
    
    public void guardarAbono(AbonoForm form){
        
        try{
            em.getTransaction().begin();
            
            Movtransacc movtransacc = new Movtransacc();
            
            BigDecimal monto = form.getMonto();
            
            movtransacc.setMovMonto(monto); 
            movtransacc.setMovObserv(form.getObservacion());
            movtransacc.setMovValido(0);
            movtransacc.setMovFechareg(new Date());
            movtransacc.setPgfId(form.getPagoId());
            movtransacc.setCjId(form.getCajaId());
            
            Transacciones traAbono = em.find(Transacciones.class, form.getTraId());
            movtransacc.setTraId(traAbono);
            
            Facturas factura = em.find(Facturas.class, form.getFactId());
            movtransacc.setFactIdRel(factura);
            
            //Restar el saldo
            
            Pagosfact pago = em.find(Pagosfact.class, form.getPagoId());
            BigDecimal saldo  = pago.getPgfSaldo();
            BigDecimal newSaldo  = saldo.subtract(monto);
            
            if (newSaldo.compareTo(BigDecimal.ZERO)<0){
                throw new Exception("El valor del abono es incorrecto:"+newSaldo.toPlainString());
            }
            
            pago.setPgfSaldo(newSaldo);
            
            if (newSaldo.compareTo(BigDecimal.ZERO)==0 ){
                Estadospago cancelStatus = em.find(Estadospago.class, 1);
                pago.setSpId(cancelStatus);
            }

            em.persist(movtransacc);
            em.persist(pago);            

            em.getTransaction().commit();            
        }
        catch(Throwable ex){
            rollbackTrans();
            System.out.println("Erro al tratar de registrar factura:"+ ex.getMessage());
            ex.printStackTrace();
            //throw  new Exception("Erro al tratar de registrar factura:"+ ex.getMessage());
        }
        finally{
            if (em != null) {
                ////em.close();
            }
        }
        
        
    }
    
}
