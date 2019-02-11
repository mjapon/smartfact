/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.controller;

import java.util.List;
import smf.util.datamodels.rows.FilaItemBuscar;

/**
 *
 * @author manuel.japon
 */
public interface IFindItem {
    
    List<FilaItemBuscar> listarFindItem(String filtro, String sortBy, String sortOrder);
    
}
