/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.entity.Clientes;
import smf.entity.Detallesfact;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Caja;
import smf.entity.Catcajas;
import smf.entity.Ctes;
import smf.entity.Facturas;
import smf.entity.Pagosfact;
import smf.entity.Pagosfactcaja;
import smf.entity.Transacciones;
import smf.util.DatosCabeceraFactura;
import smf.util.ErrorValidException;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaFactura;
import smf.util.datamodels.rows.FilaPago;
import smf.util.ParamBusquedaCXCP;
import smf.util.ParamsBusquedaTransacc;
import smf.util.StringUtil;
import smf.util.TotalesFactura;
import smf.util.datamodels.rows.FilaPagoByCaja;
import smf.util.datamodels.rows.RowFactByCaja;
import smf.util.datamodels.rows.TicketRow;

/**
 *
 * @author mjapon
 */
public class FacturasJpaController extends BaseJpaController<Facturas> implements Serializable {

    private KardexArtCntrl kardexArtCntrl;
    
    public FacturasJpaController(EntityManager em) {
        super(em);
        kardexArtCntrl = new KardexArtCntrl(em);
    }

    public void create(Facturas facturas) {
        if (facturas.getDetallesfactList() == null) {
            facturas.setDetallesfactList(new ArrayList<Detallesfact>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliId = facturas.getCliId();
            if (cliId != null) {
                cliId = em.getReference(cliId.getClass(), cliId.getCliId());
                facturas.setCliId(cliId);
            }
            List<Detallesfact> attachedDetallesfactList = new ArrayList<Detallesfact>();
            for (Detallesfact detallesfactListDetallesfactToAttach : facturas.getDetallesfactList()) {
                detallesfactListDetallesfactToAttach = em.getReference(detallesfactListDetallesfactToAttach.getClass(), detallesfactListDetallesfactToAttach.getDetfId());
                attachedDetallesfactList.add(detallesfactListDetallesfactToAttach);
            }
            facturas.setDetallesfactList(attachedDetallesfactList);
            em.persist(facturas);
            if (cliId != null) {
                cliId.getFacturasList().add(facturas);
                cliId = em.merge(cliId);
            }
            for (Detallesfact detallesfactListDetallesfact : facturas.getDetallesfactList()) {
                Facturas oldFactIdOfDetallesfactListDetallesfact = detallesfactListDetallesfact.getFactId();
                detallesfactListDetallesfact.setFactId(facturas);
                detallesfactListDetallesfact = em.merge(detallesfactListDetallesfact);
                if (oldFactIdOfDetallesfactListDetallesfact != null) {
                    oldFactIdOfDetallesfactListDetallesfact.getDetallesfactList().remove(detallesfactListDetallesfact);
                    oldFactIdOfDetallesfactListDetallesfact = em.merge(oldFactIdOfDetallesfactListDetallesfact);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Facturas facturas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas persistentFacturas = em.find(Facturas.class, facturas.getFactId());
            Clientes cliIdOld = persistentFacturas.getCliId();
            Clientes cliIdNew = facturas.getCliId();
            List<Detallesfact> detallesfactListOld = persistentFacturas.getDetallesfactList();
            List<Detallesfact> detallesfactListNew = facturas.getDetallesfactList();
            List<String> illegalOrphanMessages = null;
            for (Detallesfact detallesfactListOldDetallesfact : detallesfactListOld) {
                if (!detallesfactListNew.contains(detallesfactListOldDetallesfact)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallesfact " + detallesfactListOldDetallesfact + " since its factId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cliIdNew != null) {
                cliIdNew = em.getReference(cliIdNew.getClass(), cliIdNew.getCliId());
                facturas.setCliId(cliIdNew);
            }
            List<Detallesfact> attachedDetallesfactListNew = new ArrayList<Detallesfact>();
            for (Detallesfact detallesfactListNewDetallesfactToAttach : detallesfactListNew) {
                detallesfactListNewDetallesfactToAttach = em.getReference(detallesfactListNewDetallesfactToAttach.getClass(), detallesfactListNewDetallesfactToAttach.getDetfId());
                attachedDetallesfactListNew.add(detallesfactListNewDetallesfactToAttach);
            }
            detallesfactListNew = attachedDetallesfactListNew;
            facturas.setDetallesfactList(detallesfactListNew);
            facturas = em.merge(facturas);
            if (cliIdOld != null && !cliIdOld.equals(cliIdNew)) {
                cliIdOld.getFacturasList().remove(facturas);
                cliIdOld = em.merge(cliIdOld);
            }
            if (cliIdNew != null && !cliIdNew.equals(cliIdOld)) {
                cliIdNew.getFacturasList().add(facturas);
                cliIdNew = em.merge(cliIdNew);
            }
            for (Detallesfact detallesfactListNewDetallesfact : detallesfactListNew) {
                if (!detallesfactListOld.contains(detallesfactListNewDetallesfact)) {
                    Facturas oldFactIdOfDetallesfactListNewDetallesfact = detallesfactListNewDetallesfact.getFactId();
                    detallesfactListNewDetallesfact.setFactId(facturas);
                    detallesfactListNewDetallesfact = em.merge(detallesfactListNewDetallesfact);
                    if (oldFactIdOfDetallesfactListNewDetallesfact != null && !oldFactIdOfDetallesfactListNewDetallesfact.equals(facturas)) {
                        oldFactIdOfDetallesfactListNewDetallesfact.getDetallesfactList().remove(detallesfactListNewDetallesfact);
                        oldFactIdOfDetallesfactListNewDetallesfact = em.merge(oldFactIdOfDetallesfactListNewDetallesfact);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturas.getFactId();
                if (findFacturas(id) == null) {
                    throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.");
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
            Facturas facturas;
            try {
                facturas = em.getReference(Facturas.class, id);
                facturas.getFactId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Detallesfact> detallesfactListOrphanCheck = facturas.getDetallesfactList();
            for (Detallesfact detallesfactListOrphanCheckDetallesfact : detallesfactListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturas (" + facturas + ") cannot be destroyed since the Detallesfact " + detallesfactListOrphanCheckDetallesfact + " in its detallesfactList field has a non-nullable factId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes cliId = facturas.getCliId();
            if (cliId != null) {
                cliId.getFacturasList().remove(facturas);
                cliId = em.merge(cliId);
            }
            em.remove(facturas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }
    
    private Object runMinMaxCXCPDate(String minmax, String traId){
         String query = String.format("select  %s(f.fact_fecha)" +
            " from facturas f" +
            "   join pagosfact p on p.fact_id = f.fact_id and f.tra_id = %s "+
            " where f.fact_valido = 0 and p.pgf_saldo > 0.0" +
            "   and p.pgf_monto >0 and p.fp_id = 2", minmax, traId);
         
        return this.getResultFirstItemNQ(query);
    }
    
    public Date getMinDateCXCP(String traId){
        Object resultMin  =  runMinMaxCXCPDate("min", traId);
        
        Date minfecha = null;
        if (resultMin != null){
            minfecha = (Date)resultMin;
        }
        return minfecha;
    }

    public List<Facturas> findFacturasEntities() {
        return findFacturasEntities(true, -1, -1);
    }

    public List<Facturas> findFacturasEntities(int maxResults, int firstResult) {
        return findFacturasEntities(false, maxResults, firstResult);
    }

    private List<Facturas> findFacturasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturas.class));
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

    public Facturas findFacturas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturas.class, id);
        } finally {
            //em.close();
        }
    }
    
    public Facturas findById(Integer factId){
        return em.find(Facturas.class, factId);
    }

    public int getFacturasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturas> rt = cq.from(Facturas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public Map<String, Object> getDetallesFactura(Integer factId){
        Facturas factura = buscar(factId);
        
        if (factura == null){
            throw new ErrorValidException("No existe la factura con el id:"+factId);
        }
        
        DatosCabeceraFactura cabecera = new DatosCabeceraFactura();
        cabecera.setNumFactura( factura.getFactNum() );
        cabecera.setNroEstFact("");
        cabecera.setCliId(factura.getCliId().getCliId());
        cabecera.setCliente(factura.getCliId().getCliNombres());
        cabecera.setDireccion(factura.getCliId().getCliDir());
        cabecera.setTelf(factura.getCliId().getCliTelf());
        cabecera.setEmail(factura.getCliId().getCliEmail());
        cabecera.setFechaFactura( FechasUtil.format(factura.getFactFecha()));
        cabecera.setTraCodigo(factura.getTraId().getTraId());
                
        TotalesFactura totalesFactura = new TotalesFactura();
        totalesFactura.setSubtotal(factura.getFactSubt()  );
        totalesFactura.setIva(factura.getFactIva());
        totalesFactura.setTotal(factura.getFactTotal());
        totalesFactura.setDescuento(factura.getFactDesc());
        
        
        List<Object[]> detalles = listarDetalles(factId);
        
        List<FilaFactura> detallesList = new ArrayList<>();

        for (Object[] item: detalles){
            
            FilaFactura filafactura = new FilaFactura(1,
                (Integer)item[1],
                (String)item[7],
                (String)item[6],
                ((BigDecimal)item[3]).doubleValue(),
                (BigDecimal)item[2],
                    (Boolean)item[4],
                (BigDecimal)item[8],
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                    (String)item[11],
                    0
            );
            
            filafactura.setDescuento(item[5]!=null?(BigDecimal)item[5]:BigDecimal.ZERO);
            
            filafactura.updateTotales();
            detallesList.add(filafactura);            
        }         
        
        
        Map<String, Object> datosFactura = new HashMap<String, Object>();
        datosFactura.put("cabecera", cabecera);
        datosFactura.put("totales", totalesFactura);
        datosFactura.put("detalles", detallesList);
        
        return datosFactura;
    }
    
    
    public Map<Integer, BigDecimal> getMovsAbonosMap(List<Integer> movsIds){
         String idMovsString = movsIds.stream()
        .map( n -> n.toString() )
        .collect( Collectors.joining( "," ) );        
        
        String sql= "select m.cj_id, sum(mov_monto) from  movtransacc m where m.mov_id in("+idMovsString+") group by m.cj_id ;";
        
        List<Object[]> resultList = newNativeQuery(sql).getResultList();
        Map<Integer, BigDecimal> resultMap = new HashMap<>();
        
        for (Object[] item: resultList){
            
            Integer cajaId =1;
            if (item[0] != null){
                cajaId = (Integer)item[0];
            }
            
            BigDecimal monto =  (BigDecimal)item[1];
            if (!resultMap.containsKey(cajaId)){
                resultMap.put(cajaId, BigDecimal.ZERO);
            }
            
            BigDecimal sumaCaja =  resultMap.get(cajaId);
            resultMap.put(cajaId, sumaCaja.add( monto ));
        }
        
        return resultMap;        
    }
    
    public Map<Integer, BigDecimal> getVentasSemana(){
        
        Map<Integer, BigDecimal> mapVentasSemana = new HashMap<>();
        
        String sql = "select sum(f.factTotal),\n" +
                     "f.factFecreg \n" +
                     "from Facturas f\n" +
                     "where f.traId.traId = 1  and f.factValido = 0 and  f.factFecreg between :paramDesde and :paramHasta group by f.factFecreg order by f.factFecreg";
        Date desde = new Date();
        Date hasta = new Date();
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        desde = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        hasta = cal.getTime();
                
        Query query = newQuery(sql);
        
        query = query.setParameter("paramDesde", desde, TemporalType.DATE);
        query = query.setParameter("paramHasta", hasta, TemporalType.DATE);
        
        List<Object[]> result = query.getResultList();
        for (Object[] row: result){
            BigDecimal total = (BigDecimal)row[0];
            Date fecha = (Date)row[1];
            cal.setTime(fecha);
            Integer day = cal.get(Calendar.DAY_OF_WEEK);
            if (mapVentasSemana.containsKey(day)){
                mapVentasSemana.put(day, mapVentasSemana.get(day).add(total));
            }
            else{
                mapVentasSemana.put(day,total);
            }
        }
        
        return mapVentasSemana;
    }
    
    
    public List<Object[]> listarMovsAbonos(ParamBusquedaCXCP params){
        
        StringBuilder builder = new StringBuilder("select\n" +
        " date(m.mov_fechareg) as movFechareg,\n" +
        " f.fact_num as factNum,\n" +
        " f.fact_total as factTotal,\n" +
        " m.mov_monto as movMonto,\n" +
        " p.pgf_saldo as pgfSaldo,\n" +
        " f.fact_id as factId\n," +
        " m.mov_id as movId\n " +
        " from movtransacc m\n" +
        " join facturas f on f.fact_id = m.fact_id_rel\n" +
        " join pagosfact p on m.pgf_id = p.pgf_id");
        
        List<String> paramsList = new ArrayList<String>();
        
        paramsList.add("f.fact_valido = 0");
        paramsList.add("m.mov_valido = 0");        
        
        if (params.getDesde() != null){
            //paramsList.add("date(m.mov_fechareg) >= date(?paramDesde) ");
            paramsList.add("m.mov_fechareg >= ?paramDesde ");
        }
        if (params.getHasta() != null){
            //paramsList.add("date(m.mov_fechareg) <= date(?paramHasta) ");
            paramsList.add("m.mov_fechareg <= ?paramHasta ");
        }
        
        if (params.isFindByCaja()){
            paramsList.add("m.cj_id = ?paramCaja ");
        }
        
        paramsList.add("m.tra_id = "+params.getTra_codigo());
        
        String delimiter = " and ";
        String where = "";
            
        if (paramsList.size()>0){
            StringJoiner joiner = new StringJoiner(delimiter);
            for(String param: paramsList){
                joiner.add(param);
            }

            where = " where " + joiner.toString();
        }
        
        String baseQuery = builder.toString();
        StringBuilder orderSB = new StringBuilder("order by ");
        orderSB.append(params.getSortColumn());
        orderSB.append(" ");
        orderSB.append(params.getSortOrder());

        String queryStr = String.format("%s %s %s",  baseQuery, where, orderSB);
        
        System.out.println("Query movs:");
        System.out.println(queryStr);

        Query query = this.newNativeQuery(queryStr.toString());        
            
        if (params.getDesde() != null){
            query = query.setParameter("paramDesde", params.getDesde(), TemporalType.TIMESTAMP);
        }

        if (params.getHasta() != null){
            query = query.setParameter("paramHasta", params.getHasta(), TemporalType.TIMESTAMP);
        }        
        
        if (params.isFindByCaja()){
            query = query.setParameter("paramCaja", params.getCajaId());
        }
        
        return query.getResultList();
    }
    
    
    
    public List<Object[]> listarCuentasXCP(ParamBusquedaCXCP params){       
        StringBuilder baseQuery = new StringBuilder(" select "
                + " f.factId as factId, "
                + " f.factNum as factNum, "
                + " f.factFecreg as factFecreg,"
                + " f.factTotal as factTotal,"
                + " f.cliId.cliNombres as cliNombres,"
                + " f.cliId.cliCi as cliCi,"
                + " p.pgfFecreg as pgfFecreg,"
                + " p.pgfMonto as pgfMonto,"
                + " p.pgfSaldo as pgfSaldo,"
                + " p.pgfObs as pgfObs, "
                + " p.pgfId as pgfId, "
                + " case when p.pgfSaldo > 0.0 then 'PENDIENTE DE PAGO' else 'FACTURA CANCELADA' end as estadoDesc "
                + "  from Facturas f join Pagosfact p on p.factId.factId = f.factId and p.fpId.fpId = 2 and f.traId.traId= "+params.getTra_codigo());
        
        List<String> paramsList = new ArrayList<String>();
        
        paramsList.add("f.factValido = 0");
        
        if (params.getEstadoPago() == 1){//Cancelado
            paramsList.add("p.pgfSaldo = 0.0 and p.pgfMonto>0 and p.fpId.fpId = 2");
        }        
        else if (params.getEstadoPago() == 2){//Pendiente de pago
            paramsList.add("p.pgfSaldo > 0.0");
        }
        
        boolean searchByDate = true;
        
        if (params.getFiltro()!= null && params.getFiltro().trim().length()>3){
            searchByDate = false;
        }
        
        if (searchByDate){
            if (params.getDesde() != null){
                paramsList.add("f.factFecha >= :paramDesde ");
            }
            if (params.getHasta() != null){
                paramsList.add("f.factFecha <= :paramHasta ");
            }
        }
        else{
            paramsList.add(" (f.cliId.cliNombres like '%"+params.getFiltro().toUpperCase().trim()+"%' or f.cliId.cliCi like '%"+params.getFiltro().toUpperCase().trim()+"%') ");
        }
        
        if (params.getCliId() == 0){
            
        }
        else{
            
        }        
        
        if (params.isFindByCaja()){
            paramsList.add("f.factFecha <= :paramHasta ");
        }
        
        
        
        String delimiter = " and ";
        String where = "";
            
        if (paramsList.size()>0){
            StringJoiner joiner = new StringJoiner(delimiter);
            for(String param: paramsList){
                joiner.add(param);
            }

            where = " where " + joiner.toString();
        }

        StringBuilder orderSB = new StringBuilder("order by ");
        orderSB.append(params.getSortColumn());
        orderSB.append(" ");
        orderSB.append(params.getSortOrder());

        String queryStr = String.format("%s %s %s",  baseQuery, where, orderSB);

        Query query = this.newQuery(queryStr.toString());
        
        System.out.println("Query que se ejecuta es:");
        System.out.println(queryStr.toString());
        
        if (searchByDate){
            if (params.getDesde() != null){
                query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
            }

            if (params.getHasta() != null){
                query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);
            }
        }

        return query.getResultList();
    }
    
    public Map<Integer, BigDecimal> getEfectCreditVal(Integer factId){
        
        String query = new String("select o from Pagosfact o where o.factId.factId =  "+factId);
        
        List<Pagosfact> pagos = newQuery(query).getResultList();
        
        BigDecimal montoEfectivo = BigDecimal.ZERO;
        BigDecimal montoCredito = BigDecimal.ZERO;
        BigDecimal montoSaldo = BigDecimal.ZERO;
        
        if (pagos != null){
            
            for (Pagosfact pago: pagos){
                if (pago.getFpId().getFpId() == 1){
                    montoEfectivo = pago.getPgfMonto();
                }
                else if (pago.getFpId().getFpId() == 2){
                    montoCredito = pago.getPgfMonto();
                    montoSaldo = pago.getPgfSaldo();
                }
            }
        }
        
        Map<Integer, BigDecimal> mapResult = new HashMap<Integer, BigDecimal>();
        
        mapResult.put(1, montoEfectivo);
        mapResult.put(2, montoCredito);
        mapResult.put(3, montoSaldo);
        
        return mapResult;        
    }
    
    public List<RowFactByCaja> listarHistByCli(Integer clienteId, Integer tra_codigo){
        
        String sql = "select\n" +
            "  f.fact_id,\n" +
            "  f.fact_num,\n" +
            "  f.fact_fecreg,\n" +
            "  cli.cli_ci,\n" +
            "  cli.cli_nombres,\n" +
            " d.detf_id,\n" +
            " d.art_id,\n" +
            "  a.art_nombre,\n" +
            "  d.detf_desc,\n" +
            "   cc.cc_id,\n" +
            "   d.detf_cant,\n" +
            "   d.detf_precio\n" +
            "  from detallesfact d\n" +
            "   JOIN facturas f ON d.fact_id = f.fact_id\n" +
            "    left join clientes cli on cli.cli_id = f.cli_id\n" +
            "   left join articulos a ON d.art_id = a.art_id\n" +
            "   left join categorias cat on cat.cat_id = a.cat_id\n" +
            "   left join catcajas cc on cc.cc_id = cat.cat_caja\n" +
            "  where\n" +
            "    f.fact_valido=0 and cli.cli_id = "+ clienteId +" and f.tra_id="+tra_codigo+" order by f.fact_fecha desc, a.art_nombre";
        
        
        List<Object[]> tmpList = newNativeQuery(sql).getResultList();
        List<RowFactByCaja> resultList = new ArrayList<>();
        
        for (Object[] item: tmpList){            
            Integer factId = (Integer)item[0];
            String factNum = (String)item[1];
            Date factFecha = (Date)item[2];
            String cliCi = (String)item[3];
            String cliNombres = (String)item[4];
            Integer detFactId = (Integer)item[5];
            Integer artId = (Integer)item[6];
            String artNombre = (String)item[7];
            BigDecimal detFactDesc = (BigDecimal)item[8];
            Integer ccId = (Integer)item[9];
            BigDecimal detfCant = (BigDecimal)item[10];
            BigDecimal detfPrecio = (BigDecimal)item[11];           
            
            RowFactByCaja rowFactByCaja = new RowFactByCaja();
            
            rowFactByCaja.setFactId(factId);
            rowFactByCaja.setFactNum(factNum);
            rowFactByCaja.setFactFecha(factFecha);
            rowFactByCaja.setCliCi(cliCi);
            rowFactByCaja.setCliNombres(cliNombres);
            rowFactByCaja.setDetFactId(detFactId);
            rowFactByCaja.setArtId(artId);
            rowFactByCaja.setArtNombre(artNombre);
            rowFactByCaja.setDetfDesc(detFactDesc);
            rowFactByCaja.setCatCajaId(ccId);
            rowFactByCaja.setDetfCant(detfCant);
            rowFactByCaja.setDetfPrecio(detfPrecio);
            
            resultList.add(rowFactByCaja);
        }
        
        return resultList;
        
    }
    
    public List<RowFactByCaja> listarHistServByCli(Integer clienteId, Integer tra_codigo){
        
        String sql = "select\n" +
            "  f.fact_id,\n" +
            "  f.fact_num,\n" +
            "  f.fact_fecreg,\n" +
            "  cli.cli_ci,\n" +
            "  cli.cli_nombres,\n" +
            " d.detf_id,\n" +
            " d.art_id,\n" +
            "  a.art_nombre,\n" +
            "  d.detf_desc,\n" +
            "   cc.cc_id,\n" +
            "   d.detf_cant,\n" +
            "   d.detf_precio\n" +
            "  from detallesfact d\n" +
            "   JOIN facturas f ON d.fact_id = f.fact_id\n" +
            "    left join clientes cli on cli.cli_id = f.cli_id\n" +
            "   left join articulos a ON d.art_id = a.art_id\n" +
            "   left join categorias cat on cat.cat_id = a.cat_id\n" +
            "   left join catcajas cc on cc.cc_id = cat.cat_caja\n" +
            "  where\n" +
            "    f.fact_valido=0 and cli.cli_id = "+ clienteId +" and a.art_tipo='S' and f.tra_id="+tra_codigo+" order by a.art_nombre, f.fact_fecha asc";
        
        
        List<Object[]> tmpList = newNativeQuery(sql).getResultList();
        List<RowFactByCaja> resultList = new ArrayList<>();
        
        for (Object[] item: tmpList){            
            Integer factId = (Integer)item[0];
            String factNum = (String)item[1];
            Date factFecha = (Date)item[2];
            String cliCi = (String)item[3];
            String cliNombres = (String)item[4];
            Integer detFactId = (Integer)item[5];
            Integer artId = (Integer)item[6];
            String artNombre = (String)item[7];
            BigDecimal detFactDesc = (BigDecimal)item[8];
            Integer ccId = (Integer)item[9];
            BigDecimal detfCant = (BigDecimal)item[10];
            BigDecimal detfPrecio = (BigDecimal)item[11];           
            
            RowFactByCaja rowFactByCaja = new RowFactByCaja();
            
            rowFactByCaja.setFactId(factId);
            rowFactByCaja.setFactNum(factNum);
            rowFactByCaja.setFactFecha(factFecha);
            rowFactByCaja.setCliCi(cliCi);
            rowFactByCaja.setCliNombres(cliNombres);
            rowFactByCaja.setDetFactId(detFactId);
            rowFactByCaja.setArtId(artId);
            rowFactByCaja.setArtNombre(artNombre);
            rowFactByCaja.setDetfDesc(detFactDesc);
            rowFactByCaja.setCatCajaId(ccId);
            rowFactByCaja.setDetfCant(detfCant);
            rowFactByCaja.setDetfPrecio(detfPrecio);
            
            resultList.add(rowFactByCaja);
        }
        
        return resultList;
        
    }
    
    
    public List<RowFactByCaja> listarByCaja(ParamsBusquedaTransacc params){
        
        String sql = "select\n" +
                    "  f.fact_id,\n" +
                    "  f.fact_num,\n" +
                    "  f.fact_fecha,\n" +
                    "  cli.cli_nombres,\n" +
                    " d.detf_id,\n" +
                    " d.art_id,\n" +
                    "  a.art_nombre,\n" +
                    "  d.detf_desc,\n" +
                    "   cc.cc_id,\n" +
                    "   d.detf_cant,\n" +
                    "   d.detf_precio,\n" +
                    "   round(f.fact_descg/f.fact_total, 6) as pdescg,\n" +
                    "   (d.detf_cant*d.detf_precio)-d.detf_desc as subtotal,\n" +
                    "   CASE WHEN d.detf_iva THEN 0.12*((d.detf_cant*d.detf_precio)-d.detf_desc) ELSE 0.0 end as iva\n" +
                    "  from detallesfact d\n" +
                    "   JOIN facturas f ON d.fact_id = f.fact_id\n" +
                    "    left join clientes cli on cli.cli_id = f.cli_id\n" +
                    "   left join articulos a ON d.art_id = a.art_id\n" +
                    "   left join categorias cat on cat.cat_id = a.cat_id\n" +
                    "   left join catcajas cc on cc.cc_id = cat.cat_caja";
        
        String where = " f.fact_valido = 0 and f.tra_id="+params.getTraCodigo()
                    + " and date(f.fact_fecha) >=  date(?paramDesde) and "
                    + " date(f.fact_fecha) <=  date(?paramHasta)";
        
        /*
        " and cc.cc_id="+ params.getCatCajaId()  +" and
        */
        if (params.getCatCajaId() != 0){
            //Todas las cajas no tomar en cuenta el paramentro
            where += " and cc.cc_id = "+ params.getCatCajaId().toString();
        }
        
        if (params.getFiltro() != null && params.getFiltro().trim().length()>2){
            where += " and (cli.cli_ci like '%"+params.getFiltro().trim().toUpperCase()+"%' or cli.cli_nombres like '%"+params.getFiltro().trim().toUpperCase() +"%'";
        }
        
        String query = sql+" where "+ where + " order by f.fact_fecha desc";
        
        Query thequery = newNativeQuery(query);
        thequery = thequery.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
        thequery = thequery.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);
        
        List<Object[]> auxResult = thequery.getResultList();
        List<RowFactByCaja> resultList = new ArrayList<>();
        
        for (Object[] row: auxResult){
            Integer factId = (Integer)row[0];
            String factNum = (String)row[1];
            Date factFecha = (Date)row[2];
            String cliNombres = (String)row[3];
            Integer detFactId = (Integer)row[4];
            Integer artId = (Integer)row[5];
            String artNombre = (String)row[6];
            BigDecimal detfDesc = (BigDecimal)row[7];
            Integer catCajaId = (Integer)row[8];
            BigDecimal detfCant = (BigDecimal)row[9];
            BigDecimal detfPrecio = (BigDecimal)row[10];
            BigDecimal porcDescg = (BigDecimal)row[11];
            BigDecimal subt = (BigDecimal)row[12];
            BigDecimal iva = (BigDecimal)row[13];
            
            
            BigDecimal descgValor = BigDecimal.ZERO;
            if (porcDescg == null){
                porcDescg= BigDecimal.ZERO;
            }
            
            if (porcDescg.compareTo(BigDecimal.ONE)<=0){
                descgValor = subt.add(iva).multiply(porcDescg);
            }
            
            BigDecimal total = subt.add(iva).subtract(descgValor);
            
            RowFactByCaja rowFactByCaja = new RowFactByCaja(factId, factNum, factFecha, cliNombres, detFactId, artId, artNombre, detfDesc, catCajaId, detfCant, detfPrecio, porcDescg, subt, iva, descgValor, total);
            
            resultList.add(rowFactByCaja);
            
        }        
        
        return resultList;
    }
     
    //public Map<Integer,List<Object[]>> listar(ParamsBusquedaTransacc params){
    public List<Object[]> listar(ParamsBusquedaTransacc params){
        boolean searchByArt = params.getArtId() != null && params.getArtId()>0;
        StringBuilder baseQueryArts = new StringBuilder("select f.factId.factId,\n" +
                                                        "f.factId.factNum,\n" +
                                                        "f.detfCant*f.detfPrecio as subtotal,\n" +
                                                        "case  when f.detfIva=true then (f.detfCant*f.detfPrecio)*0.12 else 0.0 end as iva,\n" +
                                                        "f.detfCant*f.detfPrecio*f.detfDesc as descuento,\n" +
                                                        "0.0 as total,"+
                                                        "f.factId.factFecreg,\n" +
                                                        "f.factId.cliId.cliNombres,\n" +
                                                        "f.factId.cliId.cliCi,"+
                                                        "f.detfPreciocm,"+
                                                        "0.0 as efectivo, 0.0 as credito, 0.0 as saldo, 0.0 as utilidad from Detallesfact f");

        //Columnas de la consulta
        StringBuilder baseQueryFact = new StringBuilder("select f.factId, \n" +
                                                    "f.factNum,\n" +
                                                    "f.factSubt,  \n" +
                                                    "f.factIva,\n" +
                                                    "COALESCE(f.factDesc,0.0)+COALESCE(f.factDescg,0.0) as factDesc,   \n" +
                                                    "f.factTotal,\n" +
                                                    "f.factFecreg,\n" +
                                                    "f.cliId.cliNombres,\n" +
                                                    "f.cliId.cliCi,"+
                                                    "0.0 as pc, 0.0 as efectivo, 0.0 as credito, 0.0 as saldo, f.factUtilidad from Facturas f ");     

        String baseQuery = baseQueryFact.toString();
        String prefijo = "f";
        if (searchByArt){
            prefijo = "f.factId";
            baseQuery = baseQueryArts.toString();
        }

        //Parametros            
        List<String> paramsList = new ArrayList<String>();                        

        paramsList.add(prefijo+".factValido=0 ");
        
        if (params.getTraCodigo() == 1){
            paramsList.add(prefijo+".traId.traId in (1,5) ");
        }
        else{
            paramsList.add(prefijo+".traId.traId= "+params.getTraCodigo());
        }

        

        boolean byFiltro = false;
        
        if (params.getFiltro() != null && params.getFiltro().trim().length()>2){
            String thefiltro = params.getFiltro().trim().toUpperCase();
            paramsList.add("(f.cliId.cliNombres like '%"+ thefiltro +"%' or f.cliId.cliCi like '%"+ thefiltro +"%') or f.factNum like '%"+ thefiltro +"%'  ");
            byFiltro = true;
        }
        else{
            if (params.getDesde() != null){
                if (params.isUsarFechaHora()){
                    paramsList.add(prefijo+".factFecreg >= :paramDesde ");
                }
                else{
                    paramsList.add(prefijo+".factFecha >= :paramDesde ");
                }
            }
            if (params.getHasta() != null){
                if (params.isUsarFechaHora()){
                    paramsList.add(prefijo+".factFecreg <= :paramHasta ");
                }
                else{
                    paramsList.add(prefijo+".factFecha <= :paramHasta ");
                }
            }
        }
        
         if (params.getCliId() != null && params.getCliId()>0){
            paramsList.add(prefijo+".cliId.cliId = :paramCliId");
        }
        if (params.getArtId() != null && params.getArtId()>0){
            //Se debe realizar la busqueda por el codigo del articulo
            paramsList.add("f.artId = :paramArtId");
        }       
            

        String delimiter = " and ";
        String where = "";

        if (paramsList.size()>0){
            StringJoiner joiner = new StringJoiner(delimiter);
            for(String param: paramsList){
                joiner.add(param);
            }

            where = " where " + joiner.toString();
        }

        StringBuilder orderSB = new StringBuilder("order by ");
        orderSB.append(prefijo + "."+params.getSortColumn());
        orderSB.append(" ");
        orderSB.append(params.getSortOrder());

        String queryStr = String.format("%s %s %s",  baseQuery, where, orderSB);

        Query query = this.newQuery(queryStr.toString());

        if (params.getDesde() != null && !byFiltro){
            if (params.isUsarFechaHora()){
                query = query.setParameter("paramDesde", params.getDesde(), TemporalType.TIMESTAMP);
            }
            else{
                query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
            }
        }

        if (params.getHasta() != null && !byFiltro){
            if (params.isUsarFechaHora()){
                query = query.setParameter("paramHasta", params.getHasta(), TemporalType.TIMESTAMP);
            }
            else{
                query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);
            }
        }

        if (params.getCliId() != null && params.getCliId()>0){
            query = query.setParameter("paramCliId", params.getCliId());
        }

        if (params.getArtId() != null && params.getArtId()>0){
            query = query.setParameter("paramArtId", params.getArtId());
        }

        List<Object[]> resultList = query.getResultList();

        for (Object[] fila:  resultList){
            Integer idFact = (Integer)fila[0];
            Map<Integer, BigDecimal> efeccredmap =getEfectCreditVal(idFact);
            fila[10] = efeccredmap.get(1);
            fila[11] = efeccredmap.get(2);
            fila[12] = efeccredmap.get(3);
        }   
        
        return resultList;
    }
    
    /**
     * Retorna cat, utilidad
     * @param factids de ls facturas
     * @return List<0->cat, 1->utilidad>
     */
    public List<Object[]> getUtilidadesByIds(List<Integer> factids){
        String ids = factids.stream().map(i -> "'"+i.toString()+"'").collect(Collectors.joining(","));
        String nquery = "select c.cat_id, c.cat_name, (a.detf_precio-a.detf_preciocm)*a.detf_cant as utilidad  from detallesfact a\n" +
                        " join articulos art on a.art_id = art.art_id\n" +
                        " join categorias c  on art.cat_id = c.cat_id\n" +
                        "where a.fact_id in ("+ ids +") GROUP BY c.cat_id, c.cat_name, utilidad";
        return newNativeQuery(nquery).getResultList();
    }
    
    
    
    /**
     * Retorna las utlidades por caja: -1: otrod, catchanca, catsp
     * @param codCatChanca
     * @param codCatSP
     * @param list
     * @return 
     */
    /*
    public Map<Integer, BigDecimal> getUtilidadesByCat(Integer codCatChanca, Integer codCatSP, List<Object[]> list){
        BigDecimal utilidadesChanca = BigDecimal.ZERO;
        BigDecimal utilidadesSP = BigDecimal.ZERO;
        BigDecimal otrasUtil = BigDecimal.ZERO;
        
        for(Object[] item: list){
            Integer catid = (Integer)item[0];
            BigDecimal utilidadCat = (BigDecimal)item[2];
            
            if (codCatChanca.intValue() == catid.intValue()){
                utilidadesChanca = utilidadesChanca.add(utilidadCat);
            }
            else if(codCatSP.intValue() == catid.intValue()){
                utilidadesSP = utilidadesSP.add(utilidadCat);
            }
            else{
                otrasUtil = otrasUtil.add(utilidadCat);
            }
        }
        
        Map<Integer, BigDecimal> resultMap = new HashMap<>();
        resultMap.put(codCatChanca, utilidadesChanca);
        resultMap.put(codCatSP, utilidadesSP);
        resultMap.put(-1, otrasUtil);
        
        return resultMap;
    }
    */
    
    public List<Object[]> listarByCat(ParamsBusquedaTransacc params){
        
        boolean searchByArt = params.getArtId() != null && params.getArtId()>0;
        
        StringBuilder baseQueryArts = new StringBuilder("select f.factId.factId,\n" +
                                                        "f.factId.factNum,\n" +
                                                        "f.detfCant*f.detfPrecio as subtotal,\n" +
                                                        "case when f.detfIva=true then (f.detfCant*f.detfPrecio)*0.12 else 0.0 end as iva,\n" +
                                                        "f.detfCant*f.detfPrecio*f.detfDesc as descuento,\n" +
                                                        "f.detfCant*f.detfPrecio as total,"+
                                                        "f.factId.factFecreg,\n" +
                                                        "f.factId.cliId.cliNombres,\n" +
                                                        "f.factId.cliId.cliCi,"+
                                                        "f.detfPreciocm,"+
                                                        "f.detfCant*f.detfPrecio as efectivo, 0.0 as credito, 0.0 as saldo, 0.0 as utilidad from Detallesfact f join Articulos a on f.artId = a.artId");
        
        String prefijo = "f.factId";
        String baseQuery = baseQueryArts.toString();

        //Parametros            
        List<String> paramsList = new ArrayList<String>();                        

        paramsList.add(prefijo+".factValido=0 ");

        paramsList.add(prefijo+".traId.traId= "+params.getTraCodigo());

        if (params.getDesde() != null){
            if (params.isUsarFechaHora()){
                paramsList.add(prefijo+".factFecreg >= :paramDesde ");
            }
            else{
                paramsList.add(prefijo+".factFecha >= :paramDesde ");
            }
        }
        if (params.getHasta() != null){
            if (params.isUsarFechaHora()){
                paramsList.add(prefijo+".factFecreg <= :paramHasta ");
            }
            else{
                paramsList.add(prefijo+".factFecha <= :paramHasta ");
            }
        }

        if (params.getCliId() != null && params.getCliId()>0){
            paramsList.add(prefijo+".cliId.cliId = :paramCliId");
        }
        if (params.getArtId() != null && params.getArtId()>0){
            //Se debe realizar la busqueda por el codigo del articulo
            paramsList.add("a.catId = :paramArtId");
        }       


        String delimiter = " and ";
        String where = "";

        if (paramsList.size()>0){
            StringJoiner joiner = new StringJoiner(delimiter);
            for(String param: paramsList){
                joiner.add(param);
            }

            where = " where " + joiner.toString();
        }

        StringBuilder orderSB = new StringBuilder("order by ");
        orderSB.append(prefijo + "."+params.getSortColumn());
        orderSB.append(" ");
        orderSB.append(params.getSortOrder());

        String queryStr = String.format("%s %s %s",  baseQuery, where, orderSB);

        System.out.println("query--->listarByCat:"+queryStr);

        Query query = this.newQuery(queryStr.toString());

        if (params.getDesde() != null){
            if (params.isUsarFechaHora()){
                query = query.setParameter("paramDesde", params.getDesde(), TemporalType.TIMESTAMP);
            }
            else{
                query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
            }
        }

        if (params.getHasta() != null){
            if (params.isUsarFechaHora()){
                query = query.setParameter("paramHasta", params.getHasta(), TemporalType.TIMESTAMP);
            }
            else{
                query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);
            }
        }

        if (params.getCliId() != null && params.getCliId()>0){
            query = query.setParameter("paramCliId", params.getCliId());
        }

        if (params.getArtId() != null && params.getArtId()>0){
            query = query.setParameter("paramArtId", params.getArtId());
        }

        List<Object[]> resultList = query.getResultList();

        
        for (Object[] fila:  resultList){      
            Map<Integer, BigDecimal> efeccredmap =getEfectCreditVal((Integer)fila[0]);
            
            BigDecimal valcredito = efeccredmap.get(2);
            
            System.out.println("------------------------->factid:"+fila[0]+"valor credito:"+valcredito.toPlainString()+"comp"+(valcredito.compareTo(BigDecimal.ZERO)>0));
            
            if (valcredito.compareTo(BigDecimal.ZERO)>0){
                //No se puede asegurar que el articulo comprado este en efectivo ya que la factura tiene un monto ha credito no puede ir al cierre de caja de la categoria
                
                System.out.println("--------------_>Valor de fila[10] es:"+fila[10]);
                fila[10] = BigDecimal.ZERO;
                fila[11] = BigDecimal.ZERO;
                fila[12] = BigDecimal.ZERO;
            }

            //fila[10] = efeccredmap.get(1);
            //fila[11] = efeccredmap.get(2);d
            //fila[12] = efeccredmap.get(3);
        }

        return resultList;
    }    
    
    public BigDecimal getUtilidades(ParamsBusquedaTransacc params){
        
         StringBuilder baseQuery = new StringBuilder("select "
                 + "f.detfCant,"
                 + "f.detfPrecio, "
                 + "f.detfPreciocm, "                 
                 + "f.detfCant*f.detfPrecio as subtotal,\n"
                 + "case when f.detfIva=true then (f.detfCant*f.detfPrecio)*0.12 else 0.0 end as iva,\n"
                 + "f.detfCant*f.detfPrecio*f.detfDesc as descuento from Detallesfact f");
         
         
        List<String> paramsList = new ArrayList<String>();

        String prefijo = "f";

        paramsList.add(prefijo+".factId.factValido=0 ");

        if (params.getDesde() != null){
            paramsList.add(prefijo+".factId.factFecha >= :paramDesde ");
        }
        if (params.getHasta() != null){
            paramsList.add(prefijo+".factId.factFecha <= :paramHasta ");
        }

        if (params.getCliId() != null && params.getCliId()>0){
            paramsList.add(prefijo+".factId.cliId.cliId = :paramCliId");
        }
        if (params.getArtId() != null && params.getArtId()>0){
            //Se debe realizar la busqueda por el codigo del articulo
            paramsList.add("f.artId = :paramArtId");
        }            

        String delimiter = " and ";
        String where = "";

        if (paramsList.size()>0){
            StringJoiner joiner = new StringJoiner(delimiter);
            for(String param: paramsList){
                joiner.add(param);
            }

            where = " where " + joiner.toString();
        }

        String orderSB = "";

        String queryStr = String.format("%s %s %s",  baseQuery, where, orderSB);

        Query query = this.newQuery(queryStr.toString());

        if (params.getDesde() != null){
            query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
        }

        if (params.getHasta() != null){
            query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);
        }

        if (params.getCliId() != null && params.getCliId()>0){
            query = query.setParameter("paramCliId", params.getCliId());
        }

        if (params.getArtId() != null && params.getArtId()>0){
            query = query.setParameter("paramArtId", params.getArtId());
        }

        List<Object[]> result = query.getResultList();

        BigDecimal utilidades = BigDecimal.ZERO;

        for (Object[] fila: result){
            BigDecimal cant = (BigDecimal)fila[0];
            BigDecimal precio = (BigDecimal)fila[1];
            BigDecimal preciocm = (BigDecimal)fila[2];
            BigDecimal subtotal = (BigDecimal)fila[3];
            Double iva = (Double)fila[4];
            BigDecimal descuento = (BigDecimal)fila[5];
            BigDecimal costoFila = (cant.multiply(preciocm)).subtract(descuento);
            BigDecimal utilidadFila = subtotal.add(new BigDecimal(iva)).subtract(descuento).subtract(costoFila);

            utilidades = utilidades.add(utilidadFila);
        }

        System.out.println("Utilidades es:");
        System.out.println(utilidades.toPlainString());

        return utilidades.setScale(2, RoundingMode.HALF_UP);        
    }
    
    public List<Object[]> listarDetalles(Integer factId){
        
        try{
            StringBuilder builder = new StringBuilder();
            
            builder.append("select d.detfId," +
                            " d.artId," +
                            " d.detfPrecio," +
                            " d.detfCant," +
                            " d.detfIva," +
                            " d.detfDesc, " +
                            " art.artNombre, "+
                            " art.artCodbar, "+
                            " d.detfCant*d.detfPrecio as subt, "+
                            " (d.detfCant*d.detfPrecio)*d.detfDesc as descv, "+                            
                            " case when d.detfIva = 1 then ((d.detfCant*d.detfPrecio) - ((d.detfCant*d.detfPrecio)*d.detfDesc))*0.12 else 0.0 end as ivaval, "+
                            " art.artTipo "+    
                            "  from Detallesfact d join Articulos art on d.artId = art.artId where d.factId.factId= "+factId);
            
            System.out.println("Query es:");
            System.out.println(builder.toString());
            
            Query query = this.newQuery(builder.toString());
            //query = query.setParameter("paramfecha", dia, TemporalType.DATE);
            
            System.out.println("query parameter res:");
            System.out.println(query);
            
            return query.getResultList();
            
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de obtener detalles fact:"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Object[]> getPagos(Integer facturaId){
        
        StringBuilder builder = new StringBuilder("select p.pgfId, "
                + "p.pgfMonto, "
                + "p.pgfSaldo, "
                + "p.pgfObs,"
                + "p.spId.spId,"
                + "p.fpId.fpId, "                
                + "p.fpId.fpNombre, "
                + "p.spId.spNombre from Pagosfact p where p.factId.factId = "+ facturaId);
        
        
        Query query = this.newQuery(builder.toString());
        //query = query.setParameter("paramfecha", dia, TemporalType.DATE);

        System.out.println("query parameter res:");
        System.out.println(query);

        return query.getResultList();
            
        
    }
    
    public List<Object[]> listar(Date dia, String sortColumn, String sortOrder) throws Exception{        
        
        try{            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dia);

            //String diaStr = FechasUtil.format(dia);
            StringBuilder prequery = new StringBuilder("select f.factId, \n" +
                                                        "f.factNum,\n" +
                                                        "f.factSubt,  \n" +
                                                        "f.factIva,\n" +
                                                        "f.factDesc,   \n" +
                                                        "f.factTotal,\n" +
                                                        "f.factFecreg,\n" +
                                                        "f.cliId.cliNombres,\n" +
                                                        "f.cliId.cliCi from Facturas f where f.factValido = 0 ");
            
            //prequery = prequery.append("where f.factFecha = :paramfecha ")
            
            prequery =prequery.append(" order by ").append(sortColumn)
                    .append(" ")
                    .append(sortOrder);            
            //prequery = prequery.append(" order by ").append(sortColumn).append(" ").append(sortOrder);
            
            System.out.println("query");
            System.out.println(prequery.toString());            
            
            Query query = this.newQuery(prequery.toString());
            //query = query.setParameter("paramfecha", dia, TemporalType.DATE);
            
            System.out.println("query parameter res:");
            System.out.println(query);
            
            return query.getResultList();
        }
        catch(Throwable ex){
            System.out.println("Error al listar facturas:"+ex.getMessage());
            ex.printStackTrace();
            throw  new Exception("Error al listar facturas:"+ex.getMessage());
        }        
    }
    
    
    /**
     * Verifica si la caja asignada ha una factura ya ha sido cerrada
     * @param factId
     * @return 
     */
    public boolean isCajaFacturaCerrada(Integer factId){
        
        CajaJpaController cajasJpaController = new CajaJpaController(em);
        Facturas factura = findById(factId);
        if (factura != null){
            if (factura.getCajaId() != null){
                Caja caja = cajasJpaController.findById(factura.getCajaId());
                if (caja != null){
                    return caja.getCjEstado() == 1;
                }
            }
        }
        
        return false;
        
        
    }
    
    public void anularFactura(Integer factId){
        try{
            em.getTransaction().begin();
            Facturas factura = findById(factId);
            
            ArticulosJpaController articulosController = new ArticulosJpaController(em);
            
            boolean esFacturaVenta = false;
            boolean esFacturaCompra = false;
            
            if (factura != null){
                factura.setFactValido(1);//1-->anulado
                
                esFacturaVenta = factura.getTraId().getTraId() == 1;
                esFacturaCompra = factura.getTraId().getTraId() == 2;

                if (esFacturaCompra || esFacturaVenta){
                    //Se debe revertir los inventarios                
                    List<Detallesfact> detalles = getDetallesFact(factId);
                    for (Detallesfact det: detalles){
                        if (esFacturaVenta){
                            articulosController.incrementInv(det.getArtId(), det.getDetfCant());
                        }
                        else if (esFacturaCompra){
                            articulosController.decrementInv(det.getArtId(), det.getDetfCant());
                        }
                    }
                }
            }
            
            em.getTransaction().commit();
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
        }
    }
    
    public Integer getTraCodigo(Integer factId){
        try{
            //em.getTransaction().begin();
            Facturas factura = findById(factId);
            return factura.getTraId().getTraId();
            
            //em.getTransaction().commit();
        }
        catch(Throwable ex){
            
            logError(ex);
            
        }
        return null;
    }
    
    /**
     * Verifia si un numero de factura válido ya ha sido utilizado
     */
    public boolean isNumFactReg(String numFactura){
        
        try{
            
            String query = String.format("from Facturas o where o.factNum = '%s' and o.factValido =0 ",numFactura);            
            Integer cuenta = countResultList(query);
            return cuenta>0;
            
        }
        catch(Throwable ex){
            System.out.println("Error al buscar factura:"+ ex.getMessage());
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public Facturas buscar(Integer factId){        
        try{            
            return em.find(Facturas.class, factId);
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de obtener los datos de la factura-->"+ex.getMessage());
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public List<Detallesfact> getDetallesFact(Integer factId){
        try{            
            Query query = newQuery("from Detallesfact o where o.factId.factId = "+factId);            
            return query.getResultList();
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de obtener los detalles de la factura:"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    public Integer getEstadoCaja(Integer factId){
        String sql = "select cj.cj_estado from facturas f \n" +
                    "join caja cj on f.caja_id = cj.cj_id \n" +
                    "where f.fact_id = %d";
        
        String sql2 = String.format(sql, factId);
        System.out.println("Sql es:");
        System.out.println(sql2);
                
        Object estadoCaja =  getResultFirstItemNQ(sql2);
        if (estadoCaja!=null){
            return (Integer)estadoCaja;
        }
        return null;
    }
            
    
    public BigDecimal getUtilidadVenta(List<FilaFactura> detalles, BigDecimal descGlobal){
        BigDecimal utilidad = BigDecimal.ZERO;
        
        for(FilaFactura fila: detalles){
            //Solo los articulos tipo bien pueden tener utilidad
            if ("B".equalsIgnoreCase(fila.getTipo())){       
                
                BigDecimal utilidadFila = fila.getPrecioUnitario().subtract(fila.getPrecioCompra()).multiply(new BigDecimal( fila.getCantidad() )); 
                
                if (fila.getDescuento() != null && fila.getDescuento().compareTo(BigDecimal.ZERO)>0){
                    utilidadFila = utilidadFila.subtract(fila.getDescuento());                
                }
                
                //Se debe restar el descuento de la utilidad                
                if (utilidadFila.compareTo(BigDecimal.ZERO)<0){
                    utilidadFila = BigDecimal.ZERO;
                }
                
                utilidad = utilidad.add( utilidadFila );                
            }
        }
        
        if (descGlobal != null && descGlobal.compareTo(BigDecimal.ZERO)>0){
            utilidad = utilidad.subtract(descGlobal);
        }
        
        if (utilidad.compareTo(BigDecimal.ZERO)<0){
            utilidad = BigDecimal.ZERO;
        }
        
        return utilidad;
    }   
    
    public Map<Integer,BigDecimal> getSumasForCajaMap(List<Integer> idFacts){
        
        System.out.println("getSumasForCajaMap----->");
        String idFactsString = idFacts.stream()
        .map( n -> n.toString() )
        .collect( Collectors.joining( "," ) );        
        
        String sql = "select\n" +
                     "  d.detf_id,\n" +
                     "  d.art_id,\n" +
                     "  cc.cc_id,\n" +
                     "  d.detf_cant,\n" +
                     "  d.detf_precio,\n" +
                     "  round(f.fact_descg/f.fact_total, 6) as pdescg,\n" +
                     "  (d.detf_cant*d.detf_precio)-d.detf_desc as subtotal,\n" +
                     "  CASE WHEN d.detf_iva THEN 0.12*((d.detf_cant*d.detf_precio)-d.detf_desc) ELSE 0.0 end as iva\n" +
                     " from detallesfact d\n" +
                     "  JOIN facturas f ON d.fact_id = f.fact_id\n" +
                     "  join pagosfact pg on f.fact_id = pg.fact_id and pg.fp_id = 1 and f.fact_total = pg.pgf_monto\n" +
                     "  left join articulos a ON d.art_id = a.art_id\n" +
                     "  left join categorias cat on cat.cat_id = a.cat_id\n" +
                     "  left join catcajas cc on cc.cc_id = cat.cat_caja\n" +
                     " where f.fact_id in ("+idFactsString+") order by d.fact_id desc;";
        
        System.out.println("Sql:");
        System.out.println(sql);
        
        Map<Integer, BigDecimal> sumasForCajaMap = new HashMap();
        
        
        List<Object[]> resultList = newNativeQuery(sql).getResultList();
        
        for(Object[] item: resultList){
            
            Integer cc_id = 1;
            if (item[2] != null){
                cc_id = (Integer)item[2];
            }
            
            BigDecimal porcDescg = BigDecimal.ZERO;
            if (item[5]!= null){
                porcDescg = (BigDecimal)item[5];
            }
            
            BigDecimal subt = BigDecimal.ZERO;
            if (item[6]!= null){
                subt = (BigDecimal)item[6];
            }
            
            BigDecimal iva = BigDecimal.ZERO;
            if (item[7]!= null){
                iva = (BigDecimal)item[7];
            }
            
            if (!sumasForCajaMap.containsKey(cc_id)){
                sumasForCajaMap.put(cc_id, BigDecimal.ZERO);
            }
            
            BigDecimal descgValor = BigDecimal.ZERO;
            if (porcDescg.compareTo(BigDecimal.ONE)<=0){
                descgValor = subt.add(iva).multiply(porcDescg);
            }
            
            BigDecimal totalFila = subt.add(iva).subtract(descgValor);
            
            
            BigDecimal sumaCaja = sumasForCajaMap.get( cc_id );
            
            sumasForCajaMap.put(cc_id, sumaCaja.add(totalFila));
        }
        
        //Se agrega los pagos en efectivo por caja
        sql = "select pgfc.cc_id, sum(pgfc.pgcj_monto) from pagosfactcaja pgfc\n" +
            "join pagosfact p ON pgfc.pgf_id = p.pgf_id and p.fact_id in ("+ idFactsString+")\n" +
            "GROUP BY pgfc.cc_id";
        
        resultList = newNativeQuery(sql).getResultList();
        
        for (Object[] item: resultList){
            Integer catCajaId = (Integer)item[0];
            BigDecimal suma = (BigDecimal)item[1];
            
            if (catCajaId != null){
                
                if (!sumasForCajaMap.containsKey(catCajaId)){
                    sumasForCajaMap.put(catCajaId, BigDecimal.ZERO);
                }
                
                BigDecimal sumaCaja = sumasForCajaMap.get( catCajaId );
                sumasForCajaMap.put(catCajaId, sumaCaja.add(suma));
            }
        }
        
        return sumasForCajaMap;
    }
    
    
    
    /**
     * Retorna las utilidades por caja para un listado de factura (Map(clave:id_caja, valor:Utilidad)
     * @param idFacts 1:Utilidades efectivo, 2:utilidades credito
     * @return 
     */
    public Map<Integer, Map<Integer, BigDecimal>> getUtilidadesMap(List<Integer> idFacts){
        
        Map<Integer, BigDecimal> utilidadesMapEfectivo = new HashMap();
        Map<Integer, BigDecimal> utilidadesMapCredito = new HashMap();
        
        Map<Integer, Map<Integer, BigDecimal>> resultMap = new HashMap();
        
        String idFactsString = idFacts.stream()
        .map( n -> n.toString() )
        .collect( Collectors.joining( "," ) ); 
        
        //Calculo manual de las utilidades  
        
        String sql = "(select sum(round(GET_UTILIDAD_FILA(det.detf_id, f.fact_id),2)) as utilidad, 1 as tipo, cc.cc_id as caja  \n" +
        " from detallesfact det\n" +
        "   join facturas f ON f.fact_id = det.fact_id\n" +
        "  join pagosfact p on p.fact_id = f.fact_id and p.fp_id = 2 and p.pgf_monto = 0\n" +
        "  join articulos a ON det.art_id = a.art_id\n" +
        "  join categorias c on a.cat_id = c.cat_id\n" +
        "  join catcajas cc on cc.cc_id = c.cat_caja\n" +
        " where f.fact_valido = 0 and f.fact_id in (%s) GROUP BY  tipo, cc.cc_id)\n" +
        " union all " +
        " (select sum(round(GET_UTILIDAD_FILA(det.detf_id, f.fact_id),2)) as utilidad, 2 as tipo, cc.cc_id as caja  \n" +
        " from detallesfact det\n" +
        "   join facturas f ON f.fact_id = det.fact_id\n" +
        "  join pagosfact p on p.fact_id = f.fact_id and p.fp_id = 2 and p.pgf_monto > 0 and p.pgf_saldo = 0\n" +
        "  join articulos a ON det.art_id = a.art_id\n" +
        "  join categorias c on a.cat_id = c.cat_id\n" +
        "  join catcajas cc on cc.cc_id = c.cat_caja\n" +
        "  join movtransacc m ON p.pgf_id = m.pgf_id and m.mov_valido = 0 and m.mov_id = (select max(m2.mov_id) from movtransacc m2 where m2.fact_id_rel = f.fact_id and m2.mov_valido = 0)\n" +
        "  where f.fact_valido = 0 and f.fact_id in (%s)  GROUP BY tipo, cc.cc_id)\n" +
        "  order by 2 desc, 3";
        
        String thequery = String.format(sql, idFactsString, idFactsString);
        
        Query query = newNativeQuery( thequery  );
        
        List<Object[]> resultList = query.getResultList();
        
        for (Object[] fila: resultList){
            BigDecimal utilidad = (BigDecimal)fila[0];
            Integer tipo = (Integer)fila[1];
            Integer caja = (Integer)fila[2];
            if (tipo.intValue() == 1){
                utilidadesMapEfectivo.put(caja,utilidad);
            }
            else if (tipo.intValue() == 2){
                utilidadesMapEfectivo.put(caja,utilidad);
            }
        }
        
        resultMap.put(1, utilidadesMapEfectivo);
        resultMap.put(2, utilidadesMapCredito);
        
        return resultMap;
        
        
        /*
        //TODO: Primero verificar si los id de las facturas todas ya han sido cancelados en el caso de facturas ha credito
                
        String idFactsString = idFacts.stream()
        .map( n -> n.toString() )
        .collect( Collectors.joining( "," ) ); 
        
        String sql = "select fact_id from pagosfact where fact_id in ("+idFactsString+")\n" +
        "      and pgf_saldo > 0";
        
        List<Integer> resultFactCredList = newNativeQuery(sql).getResultList();
                
        String idsFactFilter = idFactsString;
        
        if (resultFactCredList != null && resultFactCredList.size()>0){
            final Set<Integer> factIdQuitarSet = resultFactCredList.stream().map(n->n.intValue()).collect(Collectors.toSet());
            
            idsFactFilter = idFacts.stream()
            .filter(n -> !factIdQuitarSet.contains(n)).map(m->m.toString()).collect(Collectors.joining( "," ) ); 
        }
        
        Map<Integer, BigDecimal> utilidadesMap = new HashMap();
        
        if (idsFactFilter.trim().length()>0){
            
            sql = "select  d.fact_id,  cat.cat_id, cat.cat_caja,  sum( ((d.detf_precio - d.detf_preciocm)*d.detf_cant) - d.detf_desc ) - f.fact_descg from\n" +
            "   detallesfact d\n" +
            " JOIN facturas f ON d.fact_id = f.fact_id\n" +
            "   left join articulos art on d.art_id = art.art_id\n" +
            "   left join categorias cat on cat.cat_id = art.cat_id\n" +        
            "   where d.fact_id in ("+idsFactFilter+") and art.art_tipo = 'B'\n" +
            " GROUP BY d.fact_id, cat.cat_id, cat.cat_caja, f.fact_descg  order by d.fact_id desc";

            System.out.println("Sql getUtilidadesMap:");
            System.out.println(sql);

            List<Object[]> resultList = newNativeQuery(sql).getResultList();           

            for(Object[] item: resultList){

                Integer catCajaId = 1;//POR DEFECTO CAJA GENERAL
                if (item[2] != null){
                    catCajaId = (Integer)item[2];
                }

                BigDecimal utilidad = BigDecimal.ZERO;
                if (item[3] != null){
                    utilidad = (BigDecimal)item[3];
                }

                if (!utilidadesMap.containsKey(catCajaId)){
                    utilidadesMap.put(catCajaId, BigDecimal.ZERO);
                }
                
                BigDecimal currentUtil = utilidadesMap.get(catCajaId);
                BigDecimal newUtilidad = currentUtil.add(utilidad);

                utilidadesMap.put(catCajaId, newUtilidad );            

            }            
        }
        
        return utilidadesMap;
        */
    }    
   
    public Boolean clienteTieneDeudas(Integer clienteId){
        
        String sql = "select count(*) from facturas f join pagosfact p on f.fact_id = p.fact_id and p.fp_id=2 and p.pgf_saldo>0  where f.tra_id = 1 and f.cli_id =  "+ clienteId;
        Long result = (Long)newNativeQuery(sql).getSingleResult();
        
        System.out.println("Cliente tiene deudas: Resultado para count es:"+result);
        
        return result>0;
        
    }
    
    public Integer crearTicket(TicketRow ticketRow)throws Exception{
        
        try{
            
            beginTrans();
            
            Clientes cli = null;
            ClientesJpaController clientesJpaController = new ClientesJpaController(em);
            
            String direccion = ticketRow.getCliDireccion().trim().toUpperCase();
            String nombres = ticketRow.getCliNombres().trim().toUpperCase();
            String cliCi = ticketRow.getCliCi().trim();
            
            if (ticketRow.getCliId()==null|| ticketRow.getCliId()==0){
                //Se debe crear clientes
                if (ticketRow.getCliCi().trim().length()>0){
                    cli = clientesJpaController.findByCi( cliCi );
                }
            }
            else{
                cli = clientesJpaController.findById( ticketRow.getCliId() );
            }
            
            if (cli ==  null){
                if (ticketRow.getCliCi().trim().length()>0){
                    cli = clientesJpaController.findByCi( cliCi );
                }
                else{
                    //Se busca primero por nombre y direccion
                    cli = clientesJpaController.findByNombresAndDir(nombres, direccion);
                    if (cli == null){
                        //Si no se encuentra se busca alguna persona con el nombre enviado (pero que no tenga numero de cedula registrado)
                        cli = clientesJpaController.findByNombres(nombres);
                    }
                }                
            }
            
            if (cli == null){
                cli = new Clientes();
                cli.setCliCi(cliCi);
                cli.setCliNombres(nombres);
                cli.setCliDir(direccion);
                cli.setCliTelf(ticketRow.getCliTelf().trim());
                cli.setCliFechareg(new Date());                
            }
            else{
                cli.setCliNombres(nombres);
                cli.setCliDir(direccion);
                cli.setCliTelf(ticketRow.getCliTelf().trim());
            }
            
            em.persist(cli);            
            
            Facturas factura = new Facturas();
            factura.setCliId(cli);
            
            
            factura.setFactNum(String.valueOf(ticketRow.getTkNro()));
            factura.setFactEstab(1);
            factura.setFactPtoemi(1);
            factura.setFactSubt( ticketRow.getMonto() );
            factura.setFactSubt12( BigDecimal.ZERO );
            factura.setFactIva( BigDecimal.ZERO );
            factura.setFactTotal( ticketRow.getMonto() );
            factura.setFactDesc( BigDecimal.ZERO );
            factura.setFactDescg( BigDecimal.ZERO );

            //factura.setFactFecha(  FechasUtil.parse( ticketRow.getFechaCreacion() ));
            factura.setFactFecha(new Date());
            factura.setFactFecreg(new Date());
            factura.setFactUtilidad(BigDecimal.ZERO);
            factura.setFactValido(0);
            
            TransaccionesJpaController transaccionesJpaController = new TransaccionesJpaController(em);
            
            Transacciones transacc = transaccionesJpaController.findTransaccByTraId( 5 );//TODO:Quemado transaccion 5 para RECIBO
            factura.setTraId(transacc);

            factura.setUserId(1);
            
            CajaJpaController cajaJpaController =new CajaJpaController(em);
            Caja caja = cajaJpaController.getLastOpenedCaja();
            if (caja != null){
                factura.setCajaId(caja.getCjId());
            }
            
            em.persist(factura);
            
            Detallesfact detalle = new Detallesfact();
            
            CtesJpaController ctesJpaController = new CtesJpaController(em);            
            String codArtTicket = ctesJpaController.findValueByClave("COD_ART_TICKET");

            detalle.setFactId(factura);
            detalle.setArtId( Integer.valueOf(codArtTicket) );
            detalle.setDetfPrecio( ticketRow.getMonto() );
            detalle.setDetfCant( BigDecimal.ONE );
            detalle.setDetfIva( Boolean.FALSE  );
            detalle.setDetfDesc( BigDecimal.ZERO );
            detalle.setDetfPreciocm( BigDecimal.ZERO );

            em.persist(detalle);
            
            Pagosfact pagoEfectivo = new Pagosfact();   
            
            EstadospagoJpaController estadoCtntrl = new EstadospagoJpaController(em);
            FormaspagoJpaController formaPagoCntrl = new FormaspagoJpaController(em);
            
            pagoEfectivo.setFpId( formaPagoCntrl.findFormaspago(1) );//efectivo
            pagoEfectivo.setSpId( estadoCtntrl.findEstadospago(1) );//estado pago cancelado
            pagoEfectivo.setFactId(factura);
            pagoEfectivo.setPgfMonto(ticketRow.getMonto() );
            pagoEfectivo.setPgfSaldo(BigDecimal.ZERO);                
            pagoEfectivo.setPgfObs( "" );
            pagoEfectivo.setPgfFecreg(new Date());
            em.persist(pagoEfectivo);
            
            em.flush();
            em.getTransaction().commit();     
            
            return factura.getFactId();
            
        }
        catch(Throwable ex){
            logError(ex, "Error al tratar de crear ticket");
            rollbackTrans();
            throw  new Exception("Error al tratar de crear ticket:"+ ex.getMessage());
            
        }
        
    }
   
   public Integer crearFactura(
            DatosCabeceraFactura datosCabecera, 
            TotalesFactura totalesFact, 
            List<FilaFactura> detalles,
            Map<Integer, FilaPago> pagosMap,
            List<FilaPagoByCaja> pagosByCajaList
            ) throws Exception{        
        
        try{
            em.getTransaction().begin();
            
            boolean esFacturaCompra = datosCabecera.getTraCodigo() == 2;
            boolean esFacturaVenta = datosCabecera.getTraCodigo() == 1;
            
            Integer tipoCliente = 1;//1-cliente, 2-proveedor
            if (esFacturaCompra){
                tipoCliente = 2;//Proveedor
            }
        
            ClientesJpaController clientesController = new ClientesJpaController(em);
            ArticulosJpaController articulosController = new ArticulosJpaController(em);

            Integer cli_codigo = datosCabecera.getCliId();
            Clientes clienteFactura = null;
            if (cli_codigo == null || cli_codigo == 0){                        //Se debe crear el cliente

                //Verificar si la cedula ingresada ya ha sido registrada:            
                String ci = datosCabecera.getCi();

                Clientes clientefind = clientesController.findByCi(ci);
                if (clientefind == null){
                    clienteFactura =  new Clientes();
                    clienteFactura.setCliNombres( datosCabecera.getCliente().trim().toUpperCase() );
                    clienteFactura.setCliCi(datosCabecera.getCi().trim());
                    clienteFactura.setCliFechareg( new Date());
                    clienteFactura.setCliDir( datosCabecera.getDireccion() );
                    clienteFactura.setCliTelf( datosCabecera.getTelf());
                    clienteFactura.setCliEmail( datosCabecera.getEmail());
                    clienteFactura.setCliMovil( "" );    
                    clienteFactura.setCliTipo(tipoCliente);

                    em.persist(clienteFactura);
                    em.flush();
                }
                else{
                    cli_codigo = clientefind.getCliId();
                    clienteFactura = clientefind;
                    clienteFactura.setCliNombres( datosCabecera.getCliente().trim().toUpperCase() );
                    clienteFactura.setCliDir( datosCabecera.getDireccion() );
                    clienteFactura.setCliTelf( datosCabecera.getTelf());
                    clienteFactura.setCliEmail( datosCabecera.getEmail());
                }
            }
            else{
                //Buscar cliente registrado por codigo
                clienteFactura = clientesController.findById(cli_codigo);
                if (clienteFactura != null){
                    clienteFactura.setCliNombres( datosCabecera.getCliente().trim().toUpperCase() );
                    clienteFactura.setCliDir( datosCabecera.getDireccion() );
                    clienteFactura.setCliTelf( datosCabecera.getTelf());
                    clienteFactura.setCliEmail( datosCabecera.getEmail()); 
                }                
            }
            
            
            //Verificar si se esta editando una factura en tal caso, se debe anular primero la factura anterior
            if (datosCabecera.getFactId()!=null && datosCabecera.getFactId()>0){
                Facturas facturaAnt = em.find(Facturas.class, datosCabecera.getFactId());
                facturaAnt.setFactValido(2);
                em.persist(facturaAnt);
            }

            Facturas factura = new Facturas();
            factura.setCliId(clienteFactura);

            String numeroFactura = "";
            if (esFacturaVenta){
                String secFactura = StringUtil.zfill(new Integer(datosCabecera.getNumFactura()), 9);
                numeroFactura = StringUtil.format("{0}{1}", 
                    datosCabecera.getNroEstFact(),
                    secFactura);
            }            
            else if (esFacturaCompra){
                numeroFactura = datosCabecera.getNumFactura();
            }
            

            factura.setFactNum(numeroFactura);
            factura.setFactEstab(1);
            factura.setFactPtoemi(1);
            factura.setFactSubt(totalesFact.getSubtotal());
            factura.setFactSubt12(totalesFact.getSubtotal12());
            factura.setFactIva(totalesFact.getIva());
            factura.setFactTotal(totalesFact.getTotal());
            factura.setFactDesc(totalesFact.getDescuento());
            factura.setFactDescg(totalesFact.getDescuentoGlobal());

            factura.setFactFecha(FechasUtil.parse(datosCabecera.getFechaFactura()));
            factura.setFactFecreg(new Date());
            factura.setFactValido(0);
            
            TransaccionesJpaController transaccionesJpaController = new TransaccionesJpaController(em);
            
            Transacciones transacc = transaccionesJpaController.findTransaccByTraId( datosCabecera.getTraCodigo() );
            factura.setTraId(transacc);

            factura.setUserId(1);
            
            
            BigDecimal utilidadCalculada =  getUtilidadVenta(detalles,factura.getFactDescg());
            
            System.out.println("Utilidad calculada para la factura:");
            System.out.println(utilidadCalculada.toPlainString());
                        
            //Utilidades
            factura.setFactUtilidad(utilidadCalculada);
            
            //Codigo de la caja
            CajaJpaController cajaJpaController =new CajaJpaController(em);
            Caja caja = cajaJpaController.getLastOpenedCaja();
            if (caja != null){
                factura.setCajaId(caja.getCjId());
            }
            
            em.persist(factura);

            //Registro de detalles de factura
            for (FilaFactura fila: detalles ){
                System.out.println("Registro de fila de la facrura");

                Detallesfact detalle = new Detallesfact();

                detalle.setFactId(factura);
                detalle.setArtId( fila.getCodigoArt() );
                detalle.setDetfPrecio(fila.getPrecioUnitario());
                detalle.setDetfCant(new BigDecimal(fila.getCantidad()));
                detalle.setDetfIva(fila.isIsIva());
                detalle.setDetfDesc(fila.getDescuento());
                detalle.setDetfPreciocm(fila.getPrecioCompra());

                //Se debe actualizar el inventario del articulo                
                if ( esFacturaVenta ){//factura de venta                              
                    BigDecimal[] newInv = articulosController.decrementInv(fila.getCodigoArt(), new BigDecimal(fila.getCantidad()));                    
                    kardexArtCntrl.registrarVenta(fila.getCodigoArt(), new BigDecimal(fila.getCantidad()), clienteFactura.getCliId(), newInv[1], newInv[0]);
                }
                else if (esFacturaCompra){//factura de compra
                    BigDecimal[] newInv = articulosController.incrementInv(fila.getCodigoArt(), new BigDecimal(fila.getCantidad()));
                    kardexArtCntrl.registrarCompra(fila.getCodigoArt(), new BigDecimal(fila.getCantidad()), clienteFactura.getCliId(), newInv[1], newInv[0]);
                    
                    //Actualizar el precio de compra
                    articulosController.updatePrecioCompra(fila.getCodigoArt(), fila.getPrecioUnitario(), clienteFactura.getCliId());
                }
                
                em.persist(detalle);
            }            
            //Crear pagos
            
            //Se registra pago en efectivo
            Pagosfact pagoEfectivo = new Pagosfact();            
            Pagosfact pagoCredito = new Pagosfact();
            
            EstadospagoJpaController estadoCtntrl = new EstadospagoJpaController(em);
            FormaspagoJpaController formaPagoCntrl = new FormaspagoJpaController(em);
            
            //Pago efectivo            
            if (pagosMap.get(1).getMonto().compareTo(BigDecimal.ZERO)>=0){                
                pagoEfectivo.setFpId( formaPagoCntrl.findFormaspago(1) );//efectivo
                pagoEfectivo.setSpId( estadoCtntrl.findEstadospago(1) );//estado pago cancelado
                pagoEfectivo.setFactId(factura);
                pagoEfectivo.setPgfMonto( pagosMap.get(1).getMonto() );
                pagoEfectivo.setPgfSaldo(BigDecimal.ZERO);                
                pagoEfectivo.setPgfObs( pagosMap.get(1).getObservacion() );
                pagoEfectivo.setPgfFecreg(new Date());
                em.persist(pagoEfectivo);
                
                if (pagosByCajaList != null && pagosByCajaList.size()>0){
                    for (FilaPagoByCaja filaPagoByCaja:  pagosByCajaList){
                        Pagosfactcaja pagosfactcaja = new Pagosfactcaja();
                        pagosfactcaja.setCcId( em.find(Catcajas.class, filaPagoByCaja.getCatCajaId()) );
                        pagosfactcaja.setPgfId(pagoEfectivo);
                        pagosfactcaja.setPgcjMonto(filaPagoByCaja.getMonto());
                        em.persist(pagosfactcaja);
                    }
                }
            }
            
            //Pago credito
            if (pagosMap.get(2).getMonto().compareTo(BigDecimal.ZERO)>=0){
                pagoCredito.setFpId( formaPagoCntrl.findFormaspago(2) );//credito
                pagoCredito.setSpId( estadoCtntrl.findEstadospago(2) );//Estado pago pendiente
                pagoCredito.setFactId(factura);
                pagoCredito.setPgfMonto( pagosMap.get(2).getMonto() );
                pagoCredito.setPgfObs( pagosMap.get(2).getObservacion() );
                pagoCredito.setPgfFecreg(new Date());
                pagoCredito.setPgfSaldo( pagosMap.get(2).getMonto() );
                em.persist(pagoCredito);
            }

            //Se debe actualizar el numero de secuencia de la factura
            SecuenciasJpaController secuenciasController = new SecuenciasJpaController(em);
            if (esFacturaVenta){
                if (datosCabecera.getFactId()==null){
                    secuenciasController.genSecuencia("EST001");
                }
            }
            
            em.flush();
            em.getTransaction().commit();     
            
            return factura.getFactId();
        }
        catch(Throwable ex){
            logError(ex, "Erro al tratar de registrar factura:");
            rollbackTrans();
            throw  new Exception("Erro al tratar de registrar factura:"+ ex.getMessage());
        }
        
    }
}
