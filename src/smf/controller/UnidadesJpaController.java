/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.EntityNotFoundException;
import smf.entity.Unidadesprecio;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import smf.controller.exceptions.NonexistentEntityException;
import smf.controller.exceptions.YaExisteRegistroException;
import smf.entity.Articulos;
import smf.entity.Unidades;
import smf.util.datamodels.rows.FilaUnidad;
import smf.util.datamodels.rows.FilaUnidadPrecio;

/**
 *
 * @author manuel.japon
 */
public class UnidadesJpaController extends BaseJpaController<Unidades> implements Serializable {

    public UnidadesJpaController(EntityManager em) {
        super(em);
    }
    
    public void destroy(Integer id) throws Throwable {
    
        try {
            beginTrans();
            Unidades unidad;
            try {
                unidad = em.getReference(Unidades.class, id);
                unidad.getUniId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The Unidades with id " + id + " no longer exists.", enfe);
            }
            
            if (enUso(id)){
                throw new Exception("Esta unidad está siendo usada, no se puede borrar");
            }
            
            em.remove(unidad);
            commitTrans();
        } catch(Throwable ex){
            logErrorWithThrow(ex);
        }
    }
    
    public boolean enUso(Integer unidId){
        return super.enUso("select count(a) from Articulos a where a.unid_id= "+unidId);
    }

    public Unidades findUnidades(Integer id) {
        try {
            return em.find(Unidades.class, id);
        } finally {
            
        }
    }
    
    public boolean existe(String nombre){
        String countQuery = "select count(o) from Unidades o where trim(upper(o.uniName)) = '"+nombre.trim().toUpperCase()+"'";
        Long countRes = getCountResult(countQuery);
        return countRes.intValue()>0;
    }
    
    public void crear(FilaUnidad row) throws Throwable{
        try{
            if (existe(row.getUnidName())){
                throw new YaExisteRegistroException("La unidad:"+row.getUnidName()+" ya existe ingrese otra");
            }
            beginTrans();
            
            Unidades unidades = new Unidades();
            unidades.setUniName(row.getUnidName().toUpperCase());
            unidades.setUniSimbolo(row.getUnidSymbol());
            
            em.persist(unidades);
            
            commitTrans();
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
        }
    }
    
    public void update(FilaUnidad row) throws Throwable{
        try{
            Unidades unidades = findUnidades(row.getUnidId());
            if (unidades!=null){
                
                String newName = row.getUnidName().trim().toUpperCase();
                String currentName = unidades.getUniName().trim().toUpperCase();
                
                if (newName.equalsIgnoreCase(currentName)){
                    throw new YaExisteRegistroException("La unidad:"+newName+" ya existe especifique otro nombre");
                }
                
                beginTrans();
                unidades.setUniName(newName);
                unidades.setUniSimbolo(row.getUnidSymbol());
                em.persist(this);
                commitTrans();
            }
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
        }
    }
    
//    public void borrar(Integer catId) throws Exception{
//        Categorias cat = em.find(Categorias.class, catId);
//        if (cat == null){
//            throw  new Exception("No esta registrada la categoria con el id especificado");
//        }        
//        //Verificar si la categoria ya ha sido usado
//        String query = "select count(o) from Unidadesprecio o where o.unidpUnid.uniId="+catId;
//        
//        Long resCount = getCountResult(query);
//        if (resCount.intValue()>0){
//            throw new Exception("No se puede eliminar esta unidad, esta siendo usada(tiene precios asociados)");
//        }
//        
//        destroy(catId);
//    }
    
    public boolean yaExistePrecio(Integer artId, Integer unidId){
        return super.enUso(" select count(*) from unidadesprecio where  unidp_artid="+artId+" and unidp_unid ="+unidId);
    }
    
    public void registrarPrecio(FilaUnidadPrecio fila) throws Throwable{
        try{            
            beginTrans();
            
            Unidadesprecio unidadesprecio = new Unidadesprecio();
            
            unidadesprecio.setUnidpArtid(em.find(Articulos.class, fila.getArtId()) );
            unidadesprecio.setUnidpUnid(em.find(Unidades.class, fila.getUnidId()) );
            unidadesprecio.setUnidpPrecioventa( fila.getPrecioNormal() );
            unidadesprecio.setUnidpPreciomin( fila.getPrecioMin() );
            
            em.persist(unidadesprecio);
            
            commitTrans();
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
        }
    }
    
    public void deletePrecio(Integer cod) throws Throwable{
        try{
            Unidadesprecio unidadesprecio = em.find(Unidadesprecio.class, cod);
            if (unidadesprecio != null){
                beginTrans();
                em.remove(unidadesprecio);
                commitTrans();
            }
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
        }
    }
    
    public void updatePrecio(FilaUnidadPrecio fila) throws Throwable{
        try{
            beginTrans();
            Unidadesprecio unidadesprecio = em.find(Unidadesprecio.class, fila.getFilaId());
            if (unidadesprecio != null){
                unidadesprecio.setUnidpPreciomin(fila.getPrecioMin());
                unidadesprecio.setUnidpPrecioventa(fila.getPrecioNormal());
            }
            
            em.persist(unidadesprecio);
            commitTrans();
        }
        catch(Throwable ex){
            logErrorWithThrow(ex);
        }
        
    }
    
    public FilaUnidadPrecio getNewFromRow(Object[] row){
        
        FilaUnidadPrecio filaUnidadPrecio = new FilaUnidadPrecio(
                (Integer)row[0],
                (Integer)row[1],
                (Integer)row[2],
                (BigDecimal)row[3],
                (BigDecimal)row[4],
                (String)row[6],
                (String)row[5],
                (Boolean)row[7]
        );
        
        return filaUnidadPrecio;
        
    }
    
    public FilaUnidad getNewUnidFromRow(Object[] row){
        FilaUnidad filaUnidad = new FilaUnidad((Integer)row[0], 
                (String)row[1],
                (String)row[2]);
        return filaUnidad;
    }
    
    public List<FilaUnidad> listar(){
        
        List<FilaUnidad> resultList = new ArrayList<>();
        String query = "select a.uni_id, a.uni_name, a.uni_simbolo from unidades a";
        
        List<Object[]> auxResult = newNativeQuery(query).getResultList();
        
        resultList = auxResult.stream().map(row->getNewUnidFromRow(row)).collect(Collectors.toList());
        
        return resultList;
    }
    
    public List<FilaUnidadPrecio> listarPrecios(Integer artId){
        
        List<FilaUnidadPrecio> resultList = new ArrayList<>();
        String query = " select up.unidp_id," +
        "  up.unidp_artid," +
        "  up.unidp_unid," +
        "  up.unidp_precioventa," +
        "  up.unidp_preciomin," +
        "  u.uni_name," +
        "  a.art_nombre, " +
        "  a.art_iva " +       
        " from unidadesprecio up " +
        "  join unidades u ON up.unidp_unid = u.uni_id " +
        " join articulos a ON up.unidp_artid = a.art_id " +
        " where up.unidp_artid = " + artId;
        
        List<Object[]> auxResult = newNativeQuery(query).getResultList();
        
        resultList = auxResult.stream().map(row->getNewFromRow(row)).collect(Collectors.toList());
        
        return resultList;
    }
}
