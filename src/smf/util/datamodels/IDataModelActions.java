/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;

/**
 *
 * @author manuel.japon
 */
public interface IDataModelActions<RowType> {
    public List<RowType> loadFromDataBase();
    public Object updateTotales();
    public void persistOnDb();
    public boolean validar(RowType filaArt);
    
}
