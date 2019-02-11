/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import smf.entity.Articulos;
import smf.entity.Kardexart;
import smf.util.FechasUtil;
import smf.util.datamodels.rows.FilaKardexArt;

/**
 *
 * @author manuel.japon
 */
public class KardexArtCntrl extends BaseJpaController<Kardexart>{
    
    public KardexArtCntrl(EntityManager em) {
        super(em);
    }

    public void registrarVenta(Integer artId, BigDecimal cantidad, Integer refId, BigDecimal saldo, BigDecimal saldoAnt){
        registrar(artId, "SALIDA POR VENTA", cantidad, refId, saldo, saldoAnt);        
    }
    
    public void registrarCompra(Integer artId, BigDecimal cantidad, Integer refId, BigDecimal saldo, BigDecimal saldoAnt){
        registrar(artId, "INGRESO POR COMPRA", cantidad, refId, saldo, saldoAnt);
    }
    
    public void regCmbPrecioCompra(Integer artId, BigDecimal precioAnt, BigDecimal precioAct){        
        Articulos articulo = em.find(Articulos.class, artId);        
        if (articulo != null){
            Kardexart kardexart = new Kardexart();            
            kardexart.setKaArtId(artId);
            kardexart.setKaAccion("CAMBIO PRECIO COMPRA");
            //kardexart.setKaRefid(refId);
            kardexart.setKaValorant(precioAnt);
            //kardexart.setKaCantidad(BigDecimal.ZERO);
            kardexart.setKaValordesp(precioAct);
            kardexart.setKaFechareg(new Date());
            kardexart.setKatipo(2);
            em.persist(kardexart);
        }
    }
    
     public void regCmbPrecioCompra(Integer artId, BigDecimal precioAnt, BigDecimal precioAct, Integer refId){        
        Articulos articulo = em.find(Articulos.class, artId);        
        if (articulo != null){
            Kardexart kardexart = new Kardexart();
            kardexart.setKaArtId(artId);
            kardexart.setKaAccion("CAMBIO PRECIO COMPRA");
            kardexart.setKaRefid(refId);
            kardexart.setKaValorant(precioAnt);
            kardexart.setKatipo(2);
            //kardexart.setKaCantidad(BigDecimal.ZERO);
            kardexart.setKaValordesp(precioAct);
            kardexart.setKaFechareg(new Date());
            em.persist(kardexart);
        }
    }
    
    public void regCmbPrecioVenta(Integer artId, BigDecimal precioAnt, BigDecimal precioAct){        
        Articulos articulo = em.find(Articulos.class, artId);        
        if (articulo != null){
            Kardexart kardexart = new Kardexart();            
            kardexart.setKaAccion("CAMBIO PRECIO VENTA");
            kardexart.setKatipo(2);
            kardexart.setKaArtId(artId);
            //kardexart.setKaRefid(refId);
            kardexart.setKaValorant(precioAnt);
            //kardexart.setKaCantidad(BigDecimal.ZERO);
            kardexart.setKaValordesp(precioAct);
            kardexart.setKaFechareg(new Date());
            em.persist(kardexart);
        }
    }
    
    public void registrarActualizacionInv(Integer artId, BigDecimal invAnt, BigDecimal invActual){
        Articulos articulo = em.find(Articulos.class, artId);
        if (articulo != null){
            Kardexart kardexart = new Kardexart();
            kardexart.setKaArtId(artId);
            kardexart.setKaAccion("ACTUALIZACIÓN DIRECTA INVENTARIO");
            //kardexart.setKaRefid();
            kardexart.setKaValorant(articulo.getArtInv());
            //kardexart.setKaCantidad();
            kardexart.setKaValordesp(invActual);
            kardexart.setKaFechareg(new Date());
            kardexart.setKatipo(1);
            em.persist(kardexart);
        }
    }
    
    public void registrar(Integer artId, String concepto, BigDecimal cantidad, Integer refId, BigDecimal saldo, BigDecimal saldoAnt){
        //Registro del karden de un articulo
        Articulos articulo = em.find(Articulos.class, artId);        
        if (articulo != null){
            Kardexart kardexart = new Kardexart();            
            kardexart.setKaArtId(artId);
            kardexart.setKaAccion(concepto);
            kardexart.setKaRefid(refId);
            kardexart.setKaValorant(saldoAnt);
            kardexart.setKaCantidad(cantidad);
            kardexart.setKatipo(1);
            kardexart.setKaValordesp(saldo);
            kardexart.setKaFechareg(new Date());
            em.persist(kardexart);
        }        
    }

    public List<FilaKardexArt> listar(Integer artId){
        
        String sql = "select\n" +
        "  k.ka_tipo,\n" +
        "  k.ka_fechareg,\n" +
        "k.ka_accion,\n" +
        "  k.ka_valorant,\n" +
        "  k.ka_valordesp,\n" +
        "  k.ka_cantidad,\n" +
        "  c2.cli_nombres||' '||coalesce(c2.cli_apellidos,'') as referencia\n" +
        " from kardexart k left join clientes c2 ON k.ka_refid = c2.cli_id "+ 
        " where k.ka_artid = %d order by ka_fechareg desc";
        
        sql = String.format(sql, artId);
        List<FilaKardexArt> returnList = new ArrayList<>();
        List<Object[]> tmpResList = newNativeQuery(sql).getResultList();
        
        tmpResList.stream().map((fila) -> {
            //Integer kaTipo = (Integer)fila[0];
            String fechaReg = FechasUtil.formatDateHour((Date)fila[1]);
            String accion = (String)fila[2];
            BigDecimal valorAnt = (BigDecimal)fila[3];
            BigDecimal valorAct = (BigDecimal)fila[4];
            BigDecimal cantidad = (BigDecimal)fila[5];
            String referencia = (String)fila[6];            
            FilaKardexArt filaKardexArt = new FilaKardexArt(fechaReg, accion, referencia, cantidad, valorAnt, valorAct);
            return filaKardexArt;
        }).forEachOrdered((filaKardexArt) -> {
            returnList.add(filaKardexArt);
        });
        
        return returnList;
    }
    
}
