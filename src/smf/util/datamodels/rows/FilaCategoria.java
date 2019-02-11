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
public class FilaCategoria {
    
    private Integer codigo;
    private String nombre;
    private Integer cajaId;
    private String caja;

    public FilaCategoria() {
    }

    public FilaCategoria(Integer codigo, String nombre, Integer cajaId, String caja) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cajaId = cajaId;
        this.caja = caja;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCajaId() {
        return cajaId;
    }

    public void setCajaId(Integer cajaId) {
        this.cajaId = cajaId;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
    
    
    
}
