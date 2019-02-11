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
import smf.entity.Facturas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import smf.controller.exceptions.IllegalOrphanException;
import smf.controller.exceptions.NonexistentEntityException;
import smf.entity.Clientes;
import smf.util.datamodels.rows.FilaItemBuscar;
import smf.util.datamodels.rows.ProveedorRow;

/**
 *
 * @author mjapon
 */
public class ClientesJpaController extends BaseJpaController<Clientes> implements Serializable, IFindItem {

    public ClientesJpaController(EntityManager em) {
        super(em);
    }    

    public Clientes findByNombres(String nombres){
        String querystr = "from Clientes a where trim(a.cliNombres) = '"+nombres.trim().toUpperCase()+"' and trim(a.cliCi) = ''";
        Query query = em.createQuery(querystr);
        
        List<Clientes> clientes = query.getResultList();
        if (clientes.size()>0){
            return clientes.get(0);
        }
        else{
            return null;
        }
    }
    
     public Clientes findByNombresAndDir(String nombres, String direccion){
        String querystr = "from Clientes a where trim(a.cliNombres) = '"+nombres.trim().toUpperCase()+"' and trim(a.cliCi) = '' and trim(a.cliDir)='"+direccion.trim().toUpperCase()+"'";
        Query query = em.createQuery(querystr);
        
        List<Clientes> clientes = query.getResultList();
        if (clientes.size()>0){
            return clientes.get(0);
        }
        else{
            return null;
        }
    }
    
    public Clientes findByCi(String ci){
        
        if (!em.isOpen()){
            System.out.println("Entity Manager is not Open-------->");
            
        }
        
        
        String querystr = "from Clientes a where trim(a.cliCi) = '"+ci.trim()+"'";
        Query query = em.createQuery(querystr);
        
        List<Clientes> clientes = query.getResultList();
        if (clientes.size()>0){
            return clientes.get(0);
        }
        else{
            return null;
        }
    }
    
    public Clientes findById(Integer clienteId){
        Query query = newQuery("from Clientes a where a.cliId = "+ clienteId);        
        return getResultFirst(query);
        
    }
    
    public void create(Clientes clientes) throws Exception{
        
        try {
            beginTrans();
            em.persist(clientes);
            commitTrans();
        } 
         catch (Exception ex) {
             rollbackTrans();
             logError(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            beginTrans();
            
            Clientes entity = findById(clientes.getCliId());
            entity.setCliDir(clientes.getCliDir()!=null?clientes.getCliDir().trim().toUpperCase():"");
            entity.setCliMovil(clientes.getCliMovil()!=null?clientes.getCliMovil().trim().toUpperCase():"");
            entity.setCliTelf(clientes.getCliTelf()!=null?clientes.getCliTelf().trim().toUpperCase():"");
            entity.setCliEmail(clientes.getCliEmail()!=null?clientes.getCliEmail().trim().toUpperCase():"");
            
            //nombre
            if (clientes.getCliNombres().trim().length()>0){
                entity.setCliNombres(clientes.getCliNombres().trim().toUpperCase());
            }
            
            //Apellidos
            if (clientes.getCliApellidos().trim().length()>0){
                entity.setCliApellidos(clientes.getCliApellidos().trim().toUpperCase());
            }
            
            //ci
            if (clientes.getCliCi().trim().length()>0){
                String newCi = clientes.getCliCi().trim();
                Clientes clienteCiFinded = findByCi(newCi);                
                if (clienteCiFinded == null){
                    entity.setCliCi(newCi);
                }
            }
            
            
            em.persist(entity);
            
            commitTrans();
            
        } catch (Exception ex) {
            rollbackTrans();
            throw new Exception(ex.getMessage());
        }
           
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getCliId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Facturas> facturasListOrphanCheck = clientes.getFacturasList();
            for (Facturas facturasListOrphanCheckFacturas : facturasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Facturas " + facturasListOrphanCheckFacturas + " in its facturasList field has a non-nullable cliId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            ////em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public List<Clientes> listar(String filtro,  String orderby, String ascdesc){
        String query = "select o from Clientes o where o.cliId!=-1 and (o.cliNombres like '%"+ filtro.trim().toUpperCase() +"%' or o.cliCi like '%"+ filtro.trim().toUpperCase() +"%') order by o."+orderby+" "+ascdesc;
        logInfo("query es:");
        logInfo(query);
        return newQuery(query).getResultList();
    }
    
    public List<Clientes> listar(String filtro, Integer tipo,  String orderby, String ascdesc){
        String query = "select o from Clientes o where o.cliId!=-1 and o.cliTipo = "+tipo+" and (o.cliNombres like '%"+ filtro.trim().toUpperCase() +"%' or o.cliCi like '%"+ filtro.trim().toUpperCase() +"%') order by o."+orderby+" "+ascdesc;
        logInfo("query es:");
        logInfo(query);
        return newQuery(query).getResultList();
    }
    
    
    public List<Clientes> listarProveedores(){
        String sql = "select o from Clientes o where o.cliTipo = 2 order by o.cliNombres asc";
        return newQuery(sql).getResultList();
    }
    
    public List<ProveedorRow> parseProveedoresList(List<Clientes> proveedores){
        
        List<ProveedorRow> proveedoresParsed = new ArrayList<>();
        
        for (Clientes cliente: proveedores){
            proveedoresParsed.add(new ProveedorRow(cliente.getCliId(),
                    cliente.toString()));
        }
        
        return proveedoresParsed;
        
    }
    
    @Override
    public List<FilaItemBuscar> listarFindItem(String filtro, String sortBy, String sortOrder) {
                
        String sqlArts = "select "+
                "o.cli_id,"+
                "coalesce(o.cli_nombres,'')||' '||coalesce(o.cli_apellidos,'') as nomapel,"+
                "o.cli_ci "+             
                "from clientes o where o.cli_nombres like '%"+filtro.toUpperCase()+"%' or o.cli_ci like '%"+ filtro.toUpperCase() +"%' order by o."+sortBy+" "+sortOrder;
        
         System.out.println("Sql que se ejecuta:");
         System.out.println(sqlArts);
        
         Query query = this.newNativeQuery(sqlArts);
         List<Object[]> auxResultList = query.getResultList();
         List<FilaItemBuscar> resultList = new ArrayList<>();
         
         for (Object[] row: auxResultList){
             resultList.add(
                     new FilaItemBuscar((Integer)row[0], 
                     (String)row[2],
                     (String)row[1],
                     "C")
             );
         }
         
         
                 
                 
         return resultList;
    }
    
}
