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
public class FilaItemBuscar {
    
    private Integer idItem;
    private String codeItem;
    private String descItem;
    private String tipo;

    public FilaItemBuscar(Integer idItem, String codeItem, String descItem, String tipo) {
        this.idItem = idItem;
        this.codeItem = codeItem;
        this.descItem = descItem;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getDescItem() {
        return descItem;
    }

    public void setDescItem(String descItem) {
        this.descItem = descItem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
