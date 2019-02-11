/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.ClientesJpaController;
import smf.entity.Clientes;

/**
 *
 * @author mjapon
 */
public class ClientesDM extends GenericDataModel<Clientes>{

    private ClientesJpaController clientesCntr;
    private Integer tipo;
    private String filtro;
    public ClientesDM(List<JTableColumn> columns, ClientesJpaController clientesCntr, Integer tipo) {
        super(columns);
        this.clientesCntr = clientesCntr;
        this.tipo = tipo;
    }

    @Override
    public void loadFromDataBase() {
        String[] co = getSortOrdColumn();
        items = clientesCntr.listar(filtro, tipo, co[0], co[1]);
    }
    
    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
}
