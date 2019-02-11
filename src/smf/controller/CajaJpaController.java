/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.entity.Cajadet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Caja;
import smf.entity.Catcajas;
import smf.entity.Facturas;
import smf.util.ErrorValidException;
import smf.util.FechasUtil;
import smf.util.ParamsBuscaCaja;
import smf.util.datamodels.rows.FilaAdminCaja;
import smf.util.datamodels.rows.FilaDatosCaja;

/**
 *
 * @author mjapon
 */
public class CajaJpaController extends BaseJpaController<Facturas> implements Serializable {

    public CajaJpaController(EntityManager em) {
        super(em);
    }
  
    public void create(Caja caja) {
        if (caja.getCajadetCollection() == null) {
            caja.setCajadetCollection(new ArrayList<Cajadet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cajadet> attachedCajadetCollection = new ArrayList<Cajadet>();
            for (Cajadet cajadetCollectionCajadetToAttach : caja.getCajadetCollection()) {
                cajadetCollectionCajadetToAttach = em.getReference(cajadetCollectionCajadetToAttach.getClass(), cajadetCollectionCajadetToAttach.getDcId());
                attachedCajadetCollection.add(cajadetCollectionCajadetToAttach);
            }
            caja.setCajadetCollection(attachedCajadetCollection);
            em.persist(caja);
            for (Cajadet cajadetCollectionCajadet : caja.getCajadetCollection()) {
                Caja oldCjIdOfCajadetCollectionCajadet = cajadetCollectionCajadet.getCjId();
                cajadetCollectionCajadet.setCjId(caja);
                cajadetCollectionCajadet = em.merge(cajadetCollectionCajadet);
                if (oldCjIdOfCajadetCollectionCajadet != null) {
                    oldCjIdOfCajadetCollectionCajadet.getCajadetCollection().remove(cajadetCollectionCajadet);
                    oldCjIdOfCajadetCollectionCajadet = em.merge(oldCjIdOfCajadetCollectionCajadet);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Caja caja) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caja persistentCaja = em.find(Caja.class, caja.getCjId());
            Collection<Cajadet> cajadetCollectionOld = persistentCaja.getCajadetCollection();
            Collection<Cajadet> cajadetCollectionNew = caja.getCajadetCollection();
            List<String> illegalOrphanMessages = null;
            for (Cajadet cajadetCollectionOldCajadet : cajadetCollectionOld) {
                if (!cajadetCollectionNew.contains(cajadetCollectionOldCajadet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cajadet " + cajadetCollectionOldCajadet + " since its cjId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cajadet> attachedCajadetCollectionNew = new ArrayList<Cajadet>();
            for (Cajadet cajadetCollectionNewCajadetToAttach : cajadetCollectionNew) {
                cajadetCollectionNewCajadetToAttach = em.getReference(cajadetCollectionNewCajadetToAttach.getClass(), cajadetCollectionNewCajadetToAttach.getDcId());
                attachedCajadetCollectionNew.add(cajadetCollectionNewCajadetToAttach);
            }
            cajadetCollectionNew = attachedCajadetCollectionNew;
            caja.setCajadetCollection(cajadetCollectionNew);
            caja = em.merge(caja);
            for (Cajadet cajadetCollectionNewCajadet : cajadetCollectionNew) {
                if (!cajadetCollectionOld.contains(cajadetCollectionNewCajadet)) {
                    Caja oldCjIdOfCajadetCollectionNewCajadet = cajadetCollectionNewCajadet.getCjId();
                    cajadetCollectionNewCajadet.setCjId(caja);
                    cajadetCollectionNewCajadet = em.merge(cajadetCollectionNewCajadet);
                    if (oldCjIdOfCajadetCollectionNewCajadet != null && !oldCjIdOfCajadetCollectionNewCajadet.equals(caja)) {
                        oldCjIdOfCajadetCollectionNewCajadet.getCajadetCollection().remove(cajadetCollectionNewCajadet);
                        oldCjIdOfCajadetCollectionNewCajadet = em.merge(oldCjIdOfCajadetCollectionNewCajadet);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caja.getCjId();
                if (findCaja(id) == null) {
                    throw new NonexistentEntityException("The caja with id " + id + " no longer exists.");
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
            Caja caja;
            try {
                caja = em.getReference(Caja.class, id);
                caja.getCjId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caja with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cajadet> cajadetCollectionOrphanCheck = caja.getCajadetCollection();
            for (Cajadet cajadetCollectionOrphanCheckCajadet : cajadetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caja (" + caja + ") cannot be destroyed since the Cajadet " + cajadetCollectionOrphanCheckCajadet + " in its cajadetCollection field has a non-nullable cjId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(caja);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Caja> findCajaEntities() {
        return findCajaEntities(true, -1, -1);
    }

    public List<Caja> findCajaEntities(int maxResults, int firstResult) {
        return findCajaEntities(false, maxResults, firstResult);
    }

    private List<Caja> findCajaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caja.class));
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

    public Caja findCaja(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caja.class, id);
        } finally {
            //em.close();
        }
    }

    public int getCajaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caja> rt = cq.from(Caja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public boolean existeCajaAbierta(Date dia){
        String query = String.format("select count(*) from caja where cj_fecaper::date = to_date('%s','DD/MM/YYYY') and cj_estado = 0", FechasUtil.format(dia));
        Integer count = runCountQuery(query);
        return count>0;
    }
    
    public Caja getLastOpenedCaja(){
        String sql = "select o from Caja o where o.cjEstado = 0 order by o.cjId desc";
        
        List<Caja> cajasList = this.newQuery(sql).getResultList();
        
        if (cajasList != null && cajasList.size()>0){
            return cajasList.get(0);
        }
        return null;
    }
    
    
    public boolean existeCajaAbierta(){
        String query = String.format("select count(*) from caja where cj_estado = 0");
        Integer count = runCountQuery(query);        
        return count>0;
    }
    
    public boolean existeCajaAbiertaMenorFecha(Date dia){
        String query = String.format("select count(*) from caja where cj_fecaper::date < to_date('%s','DD/MM/YYYY') and cj_estado = 0", FechasUtil.format(dia));
        Integer count = runCountQuery(query);
        return count>0;
    }    
    
    
    
    public Caja getCajaAbiertaMenorFecha(Date dia){
        String nativeQuery = String.format("select cj_id from caja where cj_fecaper::date < to_date('%s','DD/MM/YYYY') and cj_estado = 0 order by cj_fecaper desc  ", FechasUtil.format(dia));
        Integer cajaId = (Integer)newNativeQuery(nativeQuery).getSingleResult();
        if (cajaId != null){
            return em.find(Caja.class, cajaId);
        }
        return null;
    }
    
    public void crearCaja(Date fechaApertura, String obs, List<FilaDatosCaja> cajasList) throws Throwable{
        if (existeCajaAbierta(fechaApertura)){
           throw new ErrorValidException("Ya ha sido registrado la apertura de caja");
        }
        
        try{
            beginTrans();
            
            Caja caja = new Caja();
            caja.setCjFecaper(new Date());
            caja.setCjObsaper(obs);
            caja.setCjEstado(0);
            
            em.persist(caja);
            
            for(FilaDatosCaja fila:cajasList){
                Cajadet cajadet = new Cajadet();
                cajadet.setCcId(em.find(Catcajas.class, fila.getCatCajaId()));
                cajadet.setCjId(caja);
                cajadet.setDcSaldoAnt(fila.getSaldoAnt());
                cajadet.setDcSaldoini(fila.getSaldoInicial());
                em.persist(cajadet);
            }           

            commitTrans();
            
        }
        catch(Throwable ex){
            rollbackTrans();
            logErrorWithThrow(ex);
        }        
    }
    
    public void cerrarCaja(Integer cajaId, String obs, List<FilaDatosCaja> datosCajaList) throws Exception{
        
        try{
            beginTrans();
            Caja caja = em.find(Caja.class, cajaId);
            
            if (caja == null){
                throw new ErrorValidException("No existe un registro de caja con el id:"+ cajaId);
            }
            
            caja.setCjFeccierre(new Date());
            caja.setCjEstado(1);
            caja.setCjObscierre(obs);
            
            em.persist(caja);
            
            for(FilaDatosCaja fila: datosCajaList){
                Cajadet cajadet = em.find(Cajadet.class, fila.getDetCajaId());
                if (cajadet!=null){
                    cajadet.setDcAboncob(fila.getTotalAbCobrados());
                    cajadet.setDcAbonpag(fila.getTotalAbPagados());
                    cajadet.setDcVentas(fila.getTotalVentas());
                    cajadet.setDcCompras(fila.getTotalCompras());
                    cajadet.setDcAjuste(fila.getValorAjuste());
                    cajadet.setDcSaldofin(fila.getSaldoFinal());
                    em.persist(cajadet);
                }
            }
            
            commitTrans();
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            throw new Exception("Error al cerrar caja:"+ex.getMessage());
        }        
    }
    
    public Caja findById(Integer factId){
        return em.find(Caja.class, factId);
    }
    
    public void anularCaja(Integer cjId){        
        try{            
            beginTrans();
            Caja caja = findById(cjId);
            
            if (caja != null){
                caja.setCjEstado(2);//1-->anulado
            }            
            commitTrans();
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            throw new ErrorValidException("Error al tratar de anular caja:"+ex.getMessage());
        }
    }
    
    public Caja getUltCajaCerrada(Date dia){
        return getCajaCerradaMenorFecha(dia);
    }
    
    public Caja getUltCajaCerrada(){
        String nativeQuery = "select cj_id from caja where cj_estado = 1 order by cj_feccierre desc ";
        List<Integer> rs = newNativeQuery(nativeQuery).getResultList();
        if (rs.size()>0){
            Integer cajaId = (Integer)rs.get(0);
            if (cajaId != null){
                return em.find(Caja.class, cajaId);
            }
        }
        
        return null;        
    }
    
     public Caja getCajaCerradaMenorFecha(Date dia){
        String nativeQuery = String.format("select cj_id from caja where cj_fecaper::date < to_date('%s','DD/MM/YYYY') and cj_estado = 1 order by cj_feccierre desc ", FechasUtil.format(dia));
        List<Integer> rs = newNativeQuery(nativeQuery).getResultList();
        if (rs.size()>0){
            Integer cajaId = (Integer)rs.get(0);
            if (cajaId != null){
                return em.find(Caja.class, cajaId);
            }
        }
        
        return null;
    }
     
     
    
    public List<FilaDatosCaja> getDatosAperturaCaja(Caja cajaAnterior, boolean forApertura){
        
        String query = "select cc.cc_id, cc.cc_nombre," +
        "    cd.cj_id," +
        "    coalesce(cd.dc_saldoini,0.0), " +
        "    coalesce(cd.dc_ventas,0.0), " +
        "    coalesce(cd.dc_aboncob,0.0), " +
        "    coalesce(cd.dc_abonpag,0.0), " +
        "    coalesce(cd.dc_ajuste,0.0), " +
        "    coalesce(cd.dc_saldofin,0.0), " +
        "    coalesce(cd.dc_compras,0.0), " +
        "    coalesce(cd.dc_id, 0) as dc_id "+
        " from catcajas cc " +
        " LEFT JOIN cajadet cd on cd.cc_id = cc.cc_id ";
        
        if (cajaAnterior == null){
            query += "and cj_id = null ";
        }
        else{
            query += "and cj_id = "+cajaAnterior.getCjId();
        }
        
        query +="  order by cc.cc_id asc";
        
        System.out.println("query es:");
        System.out.println(query);
        
        List<Object[]> auxResult = newNativeQuery(query).getResultList();
        List<FilaDatosCaja> result = new ArrayList<>();
        
        if (auxResult != null){
            for (Object[] fila:auxResult){
                FilaDatosCaja datosCaja = new FilaDatosCaja();
                datosCaja.setCatCajaId((Integer)fila[0]);
                datosCaja.setNombreCaja((String)fila[1]);
                
                if (forApertura){
                    datosCaja.setSaldoAnt((BigDecimal)fila[8]);
                    datosCaja.setSaldoInicial((BigDecimal)fila[8]);
                    datosCaja.setDetCajaId( 0 );
                }
                else{
                    datosCaja.setSaldoAnt((BigDecimal)fila[8]);
                    datosCaja.setSaldoInicial((BigDecimal)fila[3]);
                    datosCaja.setDetCajaId((Integer)fila[10] );
                }
                
                
                result.add(datosCaja);
                
                
            }
        }
        
        return result;
    }
    
    
    public List<FilaDatosCaja> getDatosCierreCaja(Integer cajaId){
        
        return null;
        
    }
    
    
    public List<FilaAdminCaja> listar(ParamsBuscaCaja params){
        
        String sql = "select\n" +
                    "  cj.cj_id,\n" +
                    "  cc.cc_id,\n" +
                    "  cc.cc_nombre,\n" +
                    "  cj.cj_fecaper,\n" +
                    "  cj.cj_feccierre,\n" +
                    "  cj.cj_estado,\n" +
                    "  case cj.cj_estado when 0 then 'Abierto'\n" +
                    "                           when 1 then 'Cerrado'\n" +
                    "                           when 2 then 'Anulado' end as estado,\n" +
                    "  cd.dc_saldoant,\n" +
                    "  cd.dc_saldoini,\n" +
                    "  cd.dc_ventas,\n" +
                    "  cd.dc_compras,\n" +
                    "  cd.dc_aboncob,\n" +
                    "  cd.dc_abonpag,\n" +
                    "  cd.dc_ajuste,\n" +
                    "  cd.dc_saldofin,\n" +
                    "  cj.cj_obsaper,\n" +
                    "  cj.cj_obscierre\n" +
                    " from cajadet cd\n" +
                    " join caja cj ON cd.cj_id = cj.cj_id\n " +
                    " join catcajas cc ON cd.cc_id = cc.cc_id\n " +
                    " where cc.cc_id =  " + params.getCatCajaId().toString() +
                    " and date(cj.cj_fecaper) >= date(?paramDesde) "+
                    " AND date(cj.cj_fecaper) <= date(?paramHasta) ";
        
        Query query = this.newNativeQuery(sql);
        query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
        query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);        
        
        List<Object[]> tmpList =  query.getResultList();
        List<FilaAdminCaja> resultList = new ArrayList<>();
        
          /*
                0:"  cj.cj_id,\n" +
                1:"  cc.cc_id,\n" +
                2"  cc.cc_nombre,\n" +
                3"  cj.cj_fecaper,\n" +
                4"  cj.cj_feccierre,\n" +
                5"  cj.cj_estado,\n" +
                6"  case cj.cj_estado as estado,\n" +
                7"  cd.dc_saldoant,\n" +
                8"  cd.dc_saldoini,\n" +
                9"  cd.dc_ventas,\n" +
                10"  cd.dc_compras,\n" +
                11"  cd.dc_aboncob,\n" +
                12"  cd.dc_abonpag,\n" +
                13"  cd.dc_ajuste,\n" +
                14"  cd.dc_saldofin,\n" +
                15"  cj.cj_obsaper,\n" +
                16"  cj.cj_obscierre\n" +
                */
        
        for (Object[] row: tmpList){
            
            Integer nroCaja = (Integer)row[0];
            Integer catCajaId = (Integer)row[1];
            String nombreCaja = (String)row[2];
            Date fechaApertura = (Date)row[3];
            Date fechaCierre = (Date)row[4];
            Integer idEstado = (Integer)row[5];
            String estado = (String)row[6];
            BigDecimal saldoAnt = (BigDecimal)row[7];
            BigDecimal saldoIni = (BigDecimal)row[8];
            BigDecimal sumaVentas = (BigDecimal)row[9];
            BigDecimal sumaCompras = (BigDecimal)row[10];
            BigDecimal abonosCob = (BigDecimal)row[11];
            BigDecimal abonosPag = (BigDecimal)row[12];
            BigDecimal ajuste = (BigDecimal)row[13];
            BigDecimal saldoFin = (BigDecimal)row[14];
            
            String obsAper = (String)row[15];
            String obsCierre = (String)row[16];
            
            FilaAdminCaja filaadminCaja = new FilaAdminCaja(nroCaja, catCajaId, nombreCaja, fechaApertura, fechaCierre, idEstado, estado, saldoAnt, saldoIni, sumaVentas, sumaCompras, abonosCob, abonosPag, ajuste, saldoFin, obsAper, obsCierre);
            
            resultList.add(filaadminCaja);
            
        }
        
        return resultList;
        
    }
    
    
}
