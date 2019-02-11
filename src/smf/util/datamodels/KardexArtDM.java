/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

import java.util.List;
import smf.controller.KardexArtCntrl;
import smf.util.datamodels.rows.FilaKardexArt;

/**
 *
 * @author manuel.japon
 */
public class KardexArtDM extends GenericDataModel<FilaKardexArt>{
    
    private KardexArtCntrl kardexArtCntrl;
    private Integer artId;

    public KardexArtDM(List<JTableColumn> columns, KardexArtCntrl kardexArtCntrl) {
        super(columns);
        this.kardexArtCntrl = kardexArtCntrl;        
    }

    @Override
    public void loadFromDataBase() {
        if (this.artId != null){
            this.items = kardexArtCntrl.listar(this.artId);
            fireTableDataChanged();
        }
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }
    
    
}
