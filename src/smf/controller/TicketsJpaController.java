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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Clientes;
import smf.entity.Secuencias;
import smf.entity.Tickets;
import smf.util.FechasUtil;
import smf.util.GenTxtFactura;
import smf.util.NumbersUtil;
import smf.util.ParamsBuscaCaja;
import smf.util.ParamsBuscaTickets;
import smf.util.datamodels.rows.TicketRow;
import sun.security.krb5.internal.Ticket;

/**
 *
 * @author mjapon
 */
public class TicketsJpaController extends BaseJpaController<Tickets>implements Serializable {

    public TicketsJpaController(EntityManager em) {
        super(em);
    }

    public void create(Tickets tickets) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes cliId = tickets.getCliId();
            if (cliId != null) {
                cliId = em.getReference(cliId.getClass(), cliId.getCliId());
                tickets.setCliId(cliId);
            }
            em.persist(tickets);
            if (cliId != null) {
                cliId.getTicketsCollection().add(tickets);
                cliId = em.merge(cliId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Tickets tickets) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tickets persistentTickets = em.find(Tickets.class, tickets.getTkId());
            Clientes cliIdOld = persistentTickets.getCliId();
            Clientes cliIdNew = tickets.getCliId();
            if (cliIdNew != null) {
                cliIdNew = em.getReference(cliIdNew.getClass(), cliIdNew.getCliId());
                tickets.setCliId(cliIdNew);
            }
            tickets = em.merge(tickets);
            if (cliIdOld != null && !cliIdOld.equals(cliIdNew)) {
                cliIdOld.getTicketsCollection().remove(tickets);
                cliIdOld = em.merge(cliIdOld);
            }
            if (cliIdNew != null && !cliIdNew.equals(cliIdOld)) {
                cliIdNew.getTicketsCollection().add(tickets);
                cliIdNew = em.merge(cliIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tickets.getTkId();
                if (findTickets(id) == null) {
                    throw new NonexistentEntityException("The tickets with id " + id + " no longer exists.");
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
            Tickets tickets;
            try {
                tickets = em.getReference(Tickets.class, id);
                tickets.getTkId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tickets with id " + id + " no longer exists.", enfe);
            }
            Clientes cliId = tickets.getCliId();
            if (cliId != null) {
                cliId.getTicketsCollection().remove(tickets);
                cliId = em.merge(cliId);
            }
            em.remove(tickets);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Tickets> findTicketsEntities() {
        return findTicketsEntities(true, -1, -1);
    }

    public List<Tickets> findTicketsEntities(int maxResults, int firstResult) {
        return findTicketsEntities(false, maxResults, firstResult);
    }

    private List<Tickets> findTicketsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tickets.class));
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

    public Tickets findTickets(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tickets.class, id);
        } finally {
            //em.close();
        }
    }

    public int getTicketsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tickets> rt = cq.from(Tickets.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public List<TicketRow> listar(ParamsBuscaTickets params){
        
        String sql = "select \n" +
        "  tk.tk_id, \n" +
        "  tk.tk_fecreg, \n" +
        "  tk.tk_nro,\n" +
        "  cli.cli_nombres,\n" +
        "  cli.cli_ci,\n" +
        "  cli.cli_dir,\n" +
        "  cli.cli_telf,\n" +
        "  tk.tk_monto,\n" +
        "  tk.tk_obs,\n" +
        "  cli.cli_id\n" +
        " from tickets tk\n" +
        " join clientes cli ON tk.cli_id = cli.cli_id\n" +
        " where tk.tk_estado=0 and  date(tk.tk_fecreg) >= date(?paramDesde) "+
        " AND date(tk.tk_fecreg) <= date(?paramHasta) order by tk.tk_nro desc  ";
        
        
        Query query = this.newNativeQuery(sql);
        query = query.setParameter("paramDesde", params.getDesde(), TemporalType.DATE);
        query = query.setParameter("paramHasta", params.getHasta(), TemporalType.DATE);        
        
        List<Object[]> tmpList =  query.getResultList();
        
        List<TicketRow> resultList = new ArrayList<>();
        
        for (Object[] row: tmpList){
            Integer ticketid = (Integer)row[0];
            Date fecreg = (Date)row[1];
            Integer ticketNro = (Integer)row[2];
            String nombres = (String)row[3];
            String ci = (String)row[4];
            String dir = (String)row[5];
            String telf = (String)row[6];
            BigDecimal monto = (BigDecimal)row[7];
            String obs = (String)row[8];
            Integer cliId = (Integer)row[9];
            
            TicketRow row1 = new TicketRow(ticketid, cliId, ci, nombres, dir, FechasUtil.format(fecreg), monto, ticketNro, obs, telf);
            resultList.add(row1);
        }
        
        return resultList;
    }
    
    public Integer crear(TicketRow ticketRow) throws Exception{
        
        try{
            beginTrans();
            Tickets tickets = new Tickets();
            
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
            
            tickets.setCliId(cli);
            tickets.setTkFecreg(new Date());
            tickets.setTkMonto(ticketRow.getMonto());
            tickets.setTkNro(ticketRow.getTkNro());
            tickets.setTkEstado(0);
            
            SecuenciasJpaController seccontroller = new SecuenciasJpaController(em);
            
            Secuencias sec= seccontroller.getSecuencia("TIKSEC");
            if (sec != null){
                sec.setSecValor(tickets.getTkNro()+1);
                em.persist(sec);
            }
            
            em.persist(tickets);
            em.flush();
            
            commitTrans();
            
            return tickets.getTkId();
        }
        catch(Throwable ex){
            throw  new Exception(ex);
        }
    }
    
    public void anularTicket(Integer ticketId) throws Exception{        
        try{            
            beginTrans();
            Tickets ticket = em.find(Tickets.class, ticketId);
            if (ticket != null){
                ticket.setTkEstado(1);
            }            
            em.persist(ticket);            
            commitTrans();            
        }catch(Throwable ex){
            logError(ex);
            throw new  Exception(ex);
        }        
    }
    
    public String getTicketTxT(TicketRow infoTicket)throws Exception{
        
        CtesJpaController ctesJpaController = new CtesJpaController(em);
        String template = ctesJpaController.findValueByClave("TEMP_TICKET");
        
        if (template == null){
            throw  new Exception("El template TEMP_TICKET no esta definido, no se puede imprimir");
        }
        
        Map<String, String> mapParams = new HashMap<>();
        
        mapParams.put("cliCi", infoTicket.getCliCi());
        mapParams.put("cliNombres", infoTicket.getCliNombres());
        mapParams.put("cliDireccion", infoTicket.getCliDireccion());
        mapParams.put("fechaCreacion", infoTicket.getFechaCreacion());
        mapParams.put("monto", NumbersUtil.round2ToStr(infoTicket.getMonto()) );
        mapParams.put("tkNro", infoTicket.getTkNro().toString() );
        mapParams.put("tkObs", infoTicket.getTkObs() );
        
        String txtTicket = GenTxtFactura.replace(mapParams, template);
        
        return txtTicket;
    }
}
