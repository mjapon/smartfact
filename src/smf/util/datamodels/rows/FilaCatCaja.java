/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

/**
 *
 * @author manuel.japon
 */
public class FilaCatCaja {
    
    private Integer catCajaId;
    private String nombre;
    private String obs;

    public FilaCatCaja() {
    }

    public FilaCatCaja(Integer catCajaId, String nombre, String obs) {
        this.catCajaId = catCajaId;
        this.nombre = nombre;
        this.obs = obs;
    }    
    
    public Integer getCatCajaId() {
        return catCajaId;
    }

    public void setCatCajaId(Integer catCajaId) {
        this.catCajaId = catCajaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    
}
