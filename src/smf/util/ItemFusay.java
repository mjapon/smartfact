/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import smf.entity.Articulos;

/**
 *
 * @author mjapon
 */
public class ItemFusay {
    
    private char tipo;//k:kambo,t:temazcal,c:ceremonia
    private String nombre;
    private Articulos articulo;
    private boolean selected;

    public ItemFusay(char tipo, String nombre, Articulos articulo) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.articulo = articulo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
     
    
    
}
