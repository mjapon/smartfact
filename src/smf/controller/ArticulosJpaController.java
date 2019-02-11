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
import smf.entity.Articulos;
import smf.entity.Secuencias;
import smf.util.ErrorValidException;
import smf.util.FechasUtil;
import smf.util.NumbersUtil;
import smf.util.datamodels.rows.FilaArticulo;
import smf.util.datamodels.rows.FilaItemBuscar;

/**
 *
 * @author mjapon
 */
public class ArticulosJpaController extends BaseJpaController implements Serializable, IFindItem {
    
    private KardexArtCntrl kardexCntrl;
    
    private String baseSqlArts = "select "+
                "o.art_id,"+
                "o.art_nombre,"+
                "o.art_codbar,"+
                "o.art_preciocompra,"+
                "round(o.art_precio,4) as art_precio,"+
                "o.art_preciomin,"+
                "round(o.art_inv,1) as art_inv ,"+
                "o.prov_id,"+
                "o.art_iva,"+
                "o.unid_id,"+
                "c.cat_name, "+
                "o.art_tipo, "+
                "c.cat_caja "+
                "from articulos o left join categorias c on o.cat_id = c.cat_id ";
    
    public ArticulosJpaController(EntityManager em){
        super(em);        
        kardexCntrl = new KardexArtCntrl(em);
    }

    public void create(Articulos articulos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                ////em.close();
            }
        }
    }
    
    public boolean articuloEnUso(Integer artId){
        Integer result = runCountQuery("select count(*) from detallesfact where art_id= "+artId);
        return result>0;
    }

    public void edit(Articulos articulos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            articulos = em.merge(articulos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulos.getArtId();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                ////em.close();
            }
        }
    }

    public void destroy(Integer id) throws Throwable {    
        try {
            beginTrans();
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);                
                //Verificar articulo en uso
                if (articuloEnUso(id)){
                    throw  new ErrorValidException("El artículo "+ articulos.getArtNombre()+" esta siendo usado en alguna transacción, no se puede eliminar");
                }
                
                articulos.getArtId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            em.remove(articulos);
            commitTrans();
        }
        catch(Throwable ex){
            rollbackTrans();
            logErrorWithThrow(ex);
        }
        
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            ////em.close();
        }
    }

    public Articulos findArticulos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            ////em.close();
        }
    }

    public Integer getCatCajaId(Integer artId){
        String sql = "select b.cat_caja from articulos a join categorias b on a.cat_id = b.cat_id where a.art_id ="+ artId;
        return (Integer)getResultFirstItemNQ(sql);
    }
    
    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulos> rt = cq.from(Articulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            ////em.close();
        }
    }
    
    public List<Articulos> findByBarcode(String barcode){
        //String querystr = "from Articulos a where a.artCodbar = '"+barcode.trim().toUpperCase()+"'";
        String querystr = String.format("select * from articulos art where art.art_codbar = '%s'", barcode.trim().toUpperCase());
        Query query = em.createNativeQuery(querystr, Articulos.class);
        return query.getResultList();
        
    }
    
    public BigDecimal[] decrementInv(Integer artCodigo, BigDecimal cant){
        Articulos articulo = em.find(Articulos.class, artCodigo);        
        if (articulo != null){
            System.out.println("El articulo es distinto de null-->");
            
            BigDecimal artInv = articulo.getArtInv();
            BigDecimal newInv = BigDecimal.ZERO;
            if (artInv.compareTo(BigDecimal.ZERO)>0){
                newInv = artInv.subtract(cant);
            }            
            
            articulo.setArtInv(newInv);            
            em.persist(articulo);
            return new BigDecimal[]{artInv,newInv};
        }
        
        return null;
    }
    
    public BigDecimal[] incrementInv(Integer artCodigo, BigDecimal cant){
        Articulos articulo = em.find(Articulos.class, artCodigo);        
        if (articulo != null){            
            BigDecimal artInv = articulo.getArtInv();
            BigDecimal newInv = BigDecimal.ZERO;
            if (artInv.compareTo(BigDecimal.ZERO)>=0){
                System.out.println("El inventario es >= cero se suma al invetario actual");
                newInv = artInv.add(cant);
            }
            else{
                System.out.println("El inventario es menor ha cero se establece el inventario en el monto que viene en la compra");
                newInv = cant;
            }
            
            System.out.println("El nuevo inventario es-->" + newInv);
            
            articulo.setArtInv(newInv);         
            
            em.persist(articulo);
            return new BigDecimal[]{artInv,newInv};
        }
        return null;
    }
    
    public void updatePrecioCompra(Integer artCodigo, BigDecimal newPrecioCompra, Integer provId){
        Articulos articulo = em.find(Articulos.class, artCodigo);
        if (articulo != null){            
            if (newPrecioCompra != null){
                
                if (articulo.getArtPrecioCompra().compareTo(newPrecioCompra)!= 0){
                    kardexCntrl.regCmbPrecioCompra(artCodigo, articulo.getArtPrecioCompra(), newPrecioCompra, provId);
                }
                
                articulo.setArtPrecioCompra(newPrecioCompra);
                em.persist(articulo);
            }
        }
    }
    
    /*
    public Integer crearArticulo(FilaArticulo filaArt, boolean genCodBarra) throws Exception{
        if (genCodBarra){
            SecuenciasJpaController secuenciasJpaController = new SecuenciasJpaController(em);
            
            Secuencias secuencia = secuenciasJpaController.getSecuencia("ART_CODBAR");
            if (secuencia != null){                
                filaArt.setCodBarra(String.valueOf(secuencia.getSecValor()));
                Integer res = crearArticulo(filaArt);                
                secuenciasJpaController.genSecuencia(secuencia.getSecId());
                return res;
            }            
        }
        else{
            return crearArticulo(filaArt);
        }
        
        return null;
    }
    */
    
    public Integer crearArticulo(FilaArticulo filaArt, boolean genCodBarra) throws Exception{
        try{
            em.getTransaction().begin();
            
            Articulos articulo = new Articulos();
            
            SecuenciasJpaController secuenciasJpaController = new SecuenciasJpaController(em);
            Secuencias secuencia = secuenciasJpaController.getSecuencia("ART_CODBAR");
            if (genCodBarra){                                
                filaArt.setCodBarra(String.valueOf(secuencia.getSecValor()));
            }            
            
            if (yaExisteBarcode(filaArt.getCodBarra())){
                throw new ErrorValidException("El código de barra:"+filaArt.getCodBarra()+" ya ha sido registrado, no se puede actualizar");
            }
            
            articulo.setArtCodbar(filaArt.getCodBarra().trim().toUpperCase());
            articulo.setArtInv(filaArt.getInventario());
            articulo.setArtIva(filaArt.isIva());
            articulo.setArtNombre(filaArt.getNombre().trim().toUpperCase());
            articulo.setProvId(1);
            articulo.setArtPrecio(filaArt.getPrecioVenta());
            articulo.setArtPrecioCompra(filaArt.getPrecioCompra());
            articulo.setArtPreciomin(filaArt.getPrecioMin());
            articulo.setUnidId(1);
            articulo.setArtTipo(filaArt.getTipo());
            articulo.setCatId(filaArt.getCatId());
            articulo.setProvId(filaArt.getProveedorId());
            articulo.setFechaCreacion(new Date());
            if (filaArt.getFechaCaducidad() != null && filaArt.getFechaCaducidad().trim().length()>4){
                articulo.setFechaCaducidad(FechasUtil.parse(filaArt.getFechaCaducidad()));
            }

            em.persist(articulo);
                        
            if (genCodBarra){
                secuenciasJpaController.genSecuencia("ART_CODBAR");
            }
            
            if ("S".equalsIgnoreCase(filaArt.getTipo())){
                SecuenciasJpaController secuenciasController = new SecuenciasJpaController(em);
                secuenciasController.genSecuencia("SEC_SERVS");
            }
            
            em.flush();
            
            //Registrar kardex de ingreso de articulo
            kardexCntrl.registrar(articulo.getArtId(),"REGISTRO INICIAL",articulo.getArtInv(),null,articulo.getArtInv(),articulo.getArtInv());
            
            em.getTransaction().commit();
            return articulo.getArtId();
        }
        catch(Throwable ex){
            logError(ex,"Error al registrar articulo:");
            rollbackTrans();
            throw  new Exception("Error al registrar articulo:"+ex.getMessage());
        }        
    }
    
    public void cambiarCategoria(Integer artId, Integer newCatId){
        
        try{
            beginTrans();
            
            Articulos art = em.find(Articulos.class, artId);
            if (art!= null){
                art.setCatId(newCatId);
            }
            
            em.persist(art);
            //em.flush();
            commitTrans();
            
        }
        catch(Throwable ex){
            rollbackTrans();
            logError(ex);
            throw  new ErrorValidException(ex.getMessage());
        }
    }
    
    public void actualizarArticulo(FilaArticulo filaArt) throws Throwable{
        try{
            em.getTransaction().begin();
            if (filaArt.getArtId()>0){
                Articulos articulo = findArticulos(filaArt.getArtId());
                if (articulo != null){
                    
                    if (!filaArt.getCodBarra().trim().toUpperCase().equalsIgnoreCase(articulo.getArtCodbar().trim().toUpperCase())){
                        if (yaExisteBarcode(filaArt.getCodBarra())){
                            throw new ErrorValidException("El código de barra:"+filaArt.getCodBarra()+" ya ha sido registrado, no se puede actualizar");
                        }
                    }
                    
                    articulo.setArtCodbar(filaArt.getCodBarra().trim().toUpperCase());
                    
                    if (NumbersUtil.round2(filaArt.getInventario()).compareTo(NumbersUtil.round2(articulo.getArtInv())) != 0){
                        kardexCntrl.registrarActualizacionInv(articulo.getArtId(), articulo.getArtInv(),filaArt.getInventario());
                    }
                    
                    articulo.setArtInv(filaArt.getInventario());
                    articulo.setArtIva(filaArt.isIva());
                    articulo.setArtNombre(filaArt.getNombre().toUpperCase());
                    if (articulo.getProvId() == null){
                        articulo.setProvId(1);
                    }
                    
                    if (NumbersUtil.round(filaArt.getPrecioVenta(), 4).compareTo(NumbersUtil.round(articulo.getArtPrecio(),4)) != 0){
                       kardexCntrl.regCmbPrecioVenta(articulo.getArtId(), articulo.getArtPrecio(), filaArt.getPrecioVenta());
                    }
                    
                    if (NumbersUtil.round(filaArt.getPrecioCompra(),4).compareTo(NumbersUtil.round(articulo.getArtPrecioCompra(),4)) != 0){
                       kardexCntrl.regCmbPrecioCompra(articulo.getArtId(), articulo.getArtPrecioCompra(), filaArt.getPrecioCompra());
                    }
                    
                    articulo.setArtPrecio(filaArt.getPrecioVenta());
                    articulo.setArtPrecioCompra(filaArt.getPrecioCompra());
                    articulo.setArtPreciomin(filaArt.getPrecioMin());
                    
                    if (articulo.getUnidId() == null){
                        articulo.setUnidId(1);
                    }
                    
                    em.persist(articulo);
                    em.flush();
                    em.getTransaction().commit();
                }
                else{                      
                    //em.getTransaction().rollback();
                    throw  new Exception("No se encuentra registradoe la articulo con codigo:"+filaArt.getArtId());
                }
            }
            else{                
                throw new Exception("Este articulo aun no ha sido registrado");
            }
        }
        catch(Throwable ex){
            rollbackTrans();
            logErrorWithThrow(ex);
        }
    }
    
    public void actualizarArticuloForEdit(FilaArticulo filaArt) throws Throwable{
         try{
            em.getTransaction().begin();
            if (filaArt.getArtId()>0){
                Articulos articulo = findArticulos(filaArt.getArtId());
                if (articulo != null){
                    
                    if (!filaArt.getCodBarra().trim().toUpperCase().equalsIgnoreCase(articulo.getArtCodbar().trim().toUpperCase())){
                        if (yaExisteBarcode(filaArt.getCodBarra())){
                            throw new ErrorValidException("El código de barra:"+filaArt.getCodBarra()+" ya ha sido registrado, no se puede actualizar");
                        }
                    }
                    
                    articulo.setArtCodbar(filaArt.getCodBarra().trim().toUpperCase());
                    articulo.setArtInv(filaArt.getInventario());
                    articulo.setArtIva(filaArt.isIva());
                    articulo.setArtNombre(filaArt.getNombre().toUpperCase());
                    articulo.setProvId(filaArt.getProveedorId());                    
                    //articulo.setArtPrecio(filaArt.getPrecioVenta());
                    //articulo.setArtPrecioCompra(filaArt.getPrecioCompra());
                    //articulo.setArtPreciomin(filaArt.getPrecioMin());
                    articulo.setCatId(filaArt.getCatId());
                    //articulo.setUnidId(1);
                    
                    if (filaArt.getFechaCaducidad() != null && filaArt.getFechaCaducidad().trim().length()>4){
                        articulo.setFechaCaducidad(FechasUtil.parse(filaArt.getFechaCaducidad()));
                    }

                    em.persist(articulo);
                    em.flush();
                    em.getTransaction().commit();
                }
                else{
                    throw  new Exception("No se encuentra registradoe la articulo con codigo:"+filaArt.getArtId());
                }
            }
            else{
                throw new Exception("Este articulo aun no ha sido registrado");
            }
        }
        catch(Throwable ex){
            rollbackTrans();
            logErrorWithThrow(ex);
        }
    }
    
    public List<Object[]> listarRaw(String sortBy, String sortOrder) throws Exception{
        String queryStr = String.format("%s order by %s %s", baseSqlArts, sortBy, sortOrder);
        Query query = this.newNativeQuery(queryStr);
        return query.getResultList();
    }
    
    public List<Object[]> listarRawCaducados(String sortBy, String sortOrder) throws Exception{
        String queryStr = String.format(" %s where now() > o.art_feccadu  order by %s %s",baseSqlArts,sortBy, sortOrder);
        Query query = this.newNativeQuery(queryStr);
        return query.getResultList();
    }
    
     public List<Object[]> listarRawCat(String sortBy, String sortOrder, Integer catId) throws Exception{
        String whereCatId = " where o.cat_id = "+catId;
        if (catId == 0){//PARA EL CASO DE TODAS LAS CATEGORIAS
            whereCatId = "";
        }         
        String queryStr = baseSqlArts + whereCatId+" order by "+sortBy+" "+sortOrder;
        Query query = this.newNativeQuery(queryStr);
        return query.getResultList();
    }
     
     public List<Object[]> listarRawProv(String sortBy, String sortOrder, Integer provId) throws Exception{
        String whereCatId = " where o.prov_id = "+provId;
        if (provId == 0){//PARA EL CASO DE todos lo proveedores
            whereCatId = "";
        }
        String queryStr = baseSqlArts + whereCatId+" order by "+sortBy+" "+sortOrder;
        Query query = this.newNativeQuery(queryStr);
        return query.getResultList();
     }
    
    public List<Articulos> listar(String sortBy, String sortOrder) throws Exception{
        try{
            //artPrecioCompra
            String thequery = "from Articulos o order by "+sortBy+" "+sortOrder;            
            Query query = this.newQuery(thequery);
            return query.getResultList();
        }
        catch(Throwable ex){
            System.out.println("Error al listar los articulos:"+ex.getMessage());
            ex.printStackTrace();
            throw  new Exception("Error al listar los articulos:"+ex.getMessage());
        }
    }
    
    public List<Object[]> listarRaw(String sortBy, String sortOrder, String filtro) throws Exception{
        String queryStr = baseSqlArts+" where o.art_nombre like '%"+filtro.toUpperCase()+"%' or o.art_codbar like '%"+ filtro.toUpperCase() +"%' order by o."+sortBy+" "+sortOrder;
        System.out.println("listarRaw query->");
        System.out.println(queryStr);
        Query query = this.newNativeQuery(queryStr);
        return query.getResultList();
    }
    
    public List<Articulos> listar(String sortBy, String sortOrder, String filtro) throws Exception{
        try{
            String thequery = "from Articulos o where o.artNombre like '%"+filtro.toUpperCase()+"%' or o.artCodbar like '%"+ filtro.toUpperCase() +"%' order by o."+sortBy+" "+sortOrder;
            Query query = this.newQuery(thequery);
            return query.getResultList();
        }
        catch(Throwable ex){
            logError(ex);
            throw  new Exception("Error al listar los articulos:"+ex.getMessage());
        }
    }
    
    public boolean yaExisteBarcode(String barcode){
        Query query = newQuery("from Articulos o where o.artCodbar = '"+barcode.trim().toUpperCase()+"'");
        List resultList = query.getResultList();        
        return resultList.size()>0;
    }
    
    public boolean yaExisteNombre(String nombre){
        Query query = newQuery("from Articulos o where o.artNombre = '"+nombre.trim().toUpperCase()+"'");
        List resultList = query.getResultList();        
        return resultList.size()>0;
    }
    
    @Override
    public List<FilaItemBuscar> listarFindItem(String filtro, String sortBy, String sortOrder) {
                
        String sqlArts = "select "+
                "o.art_id,"+
                "o.art_nombre,"+
                "o.art_codbar "+             
                "from articulos o where o.art_nombre like '%"+filtro.toUpperCase()+"%' or o.art_codbar like '%"+ filtro.toUpperCase() +"%' order by o."+sortBy+" "+sortOrder;
        
        
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
                     "A")
             );
         }
         return resultList;
    }
}
