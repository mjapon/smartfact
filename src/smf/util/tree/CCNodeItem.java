/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.tree;

import java.util.List;

/**
 *
 * @author manuel.japon
 */
public class CCNodeItem implements Comparable<CCNodeItem>{
    private String codigo;
    private String nombre;
    private Integer parent;
    private Integer idDb;
    
    private List<CCNodeItem> hijos;

    public CCNodeItem(String codigo, String nombre, Integer parent, Integer idDb) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.parent = parent;
        this.idDb = idDb;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public List<CCNodeItem> getHijos() {
        return hijos;
    }

    public void setHijos(List<CCNodeItem> hijos) {
        this.hijos = hijos;
    }
    
    public boolean hasChilds(){
        return hijos!=null && hijos.size()>0;
    }
    
    public int getChildCount(){
        int count = 0;
        if (hijos!=null){
            count = hijos.size();
        }
        return count;
    }

    public Integer getIdDb() {
        return idDb;
    }

    public void setIdDb(Integer idDb) {
        this.idDb = idDb;
    }
    
    

    @Override
    public int compareTo(CCNodeItem o) {
        return this.codigo.compareTo(o.codigo);
    }
    
}