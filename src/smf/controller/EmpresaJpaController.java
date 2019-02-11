/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import javax.persistence.EntityManager;
import smf.entity.Empresa;

/**
 *
 * @author manuel.japon
 */
public class EmpresaJpaController extends BaseJpaController<Empresa>{
    
    public EmpresaJpaController(EntityManager em) {
        super(em);
    }
    
    public Empresa getDatosEmpresa(){
        return getResultFirst("from Empresa o");
    }
    
}
