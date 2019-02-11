/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import smf.controller.exceptions.NonexistentEntityException;
import smf.controller.exceptions.YaExisteRegistroException;
import smf.entity.Articulos;
import smf.entity.Categorias;
import smf.util.ArrayUtil;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.datamodels.rows.CatRow;

/**
 *
 * @author manuel.japon
 */
public class CategoriasJpaController extends BaseJpaController<Categorias> implements Serializable{

    public CategoriasJpaController(EntityManager em) {
        super(em);
    }
    
    public void create(Categorias categorias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Categorias categorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            categorias = em.merge(categorias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getCatId();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
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
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getCatId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                ////em.close();
            }
        }
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {
        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorias.class));
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

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            //em.close();
        }
    }

    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public boolean existe(String nombre){
        String countQuery = "select count(o) from Categorias o where trim(upper(o.catName)) = '"+nombre.trim().toUpperCase()+"'";
        Long countRes = getCountResult(countQuery);
        return countRes.intValue()>0;
    }
    
    
    public void borrar(Integer catId) throws Exception{
        
        Categorias cat = em.find(Categorias.class, catId);
        if (cat == null){
            throw  new Exception("No esta registrada la categoria con el id especificado");
        }
        
        //Verificar si la categoria ya ha sido usado
        String query = "select count(o) from Articulos o where o.catId="+catId;
        
        Long resCount = getCountResult(query);
        if (resCount.intValue()>0){
            throw new Exception("No se puede eliminar esta categoría, esta siendo usada(tiene artículos asociados)");
        }
        
        destroy(catId);
    }
    
    public void actualizar(Integer catId, String nombre) throws Exception{        
        try{
            Categorias cat = em.find(Categorias.class, catId);
            if (cat == null){
                throw  new Exception("No esta registrada la categoria con el id especificado");
            }
            String currentName = cat.getCatName().trim().toUpperCase();
            String newName = nombre.trim().toUpperCase();

            if (!currentName.equalsIgnoreCase(newName)){
                if (existe(nombre)){
                    throw new YaExisteRegistroException("La categoría:"+nombre+" ya existe ingrese otra");
                }
            }
            beginTransacction();
            cat.setCatName(newName);
            commitTransacction();
        }
        catch(Throwable ex){
            //rollbackTransacction();
            logError(ex);
        }
    }
    
    public void actualizar(Integer catId, Integer cajaId) throws Exception{
        try{
            Categorias cat = em.find(Categorias.class, catId);
            if (cat == null){
                throw  new Exception("No esta registrada la categoria con el id especificado");
            }
            
            beginTransacction();
            cat.setCatCaja(cajaId);
            commitTransacction();
            
            
        }
        catch(Throwable ex){
            logError(ex);
            throw  new Exception(ex.getMessage());
        }
    }
    
    public void asociar(Integer catId, List<FilaArticulo> itemsList){
        try{
            if (catId != null){
                beginTransacction();
                for (FilaArticulo item: itemsList){
                    Articulos articulo = em.find(Articulos.class, item.getArtId());
                    if (articulo != null){
                        articulo.setCatId(catId);
                        em.persist(articulo);
                    }
                }
                em.flush();
                commitTransacction();
            }
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            //rollbackTransacction();
        }
    }    
    
    public void crear(String nombre) throws Throwable{
        try{
            beginTransacction();
            if (existe(nombre)){
                throw new YaExisteRegistroException("La categoría:"+nombre+" ya existe ingrese otra");
            }
            Categorias categorias = new Categorias();
            categorias.setCatName(nombre.trim().toUpperCase());
            categorias.setCatCaja(1);//Por defecto cuando se crea una categoria se pone en la caja 1:GENERAL
            em.persist(categorias);
            commitTransacction();
        }
        catch(Throwable ex){
            rollbackTrans();
            logErrorWithThrow(ex);
        }
    }
    
    public List<Categorias> listar(){
        return this.newQuery("from Categorias o order by o.catName ").getResultList();
    }
    
    public List<Object[]> listarForGrid(){
        String sql = " select c.cat_id, "
                + " c.cat_name, "
                + " c.cat_caja, "
                + " cj.cc_nombre from categorias c join catcajas cj " 
                + "  on c.cat_caja = cj.cc_id order by cat_name";
        return newNativeQuery(sql).getResultList();
    }
    
    public List<CatRow> parseCatList(List<Categorias> catList){
        Stream stream = catList.stream().map(cat -> new CatRow(cat.getCatId(), cat.getCatName()));
        List<CatRow> parseList = ArrayUtil.streamToList(stream);
        CatRow allCatRow = new CatRow(0, "<TODOS>");
        parseList.add(0,allCatRow);
        return parseList;
    }
}
