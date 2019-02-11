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
public class FilaUnidad {
    
    private Integer unidId;
    private String unidName;
    private String unidSymbol;

    public FilaUnidad(Integer unidId, String unidName, String unidSymbol) {
        this.unidId = unidId;
        this.unidName = unidName;
        this.unidSymbol = unidSymbol;
    }

    public Integer getUnidId() {
        return unidId;
    }

    public void setUnidId(Integer unidId) {
        this.unidId = unidId;
    }

    public String getUnidName() {
        return unidName;
    }

    public void setUnidName(String unidName) {
        this.unidName = unidName;
    }

    public String getUnidSymbol() {
        return unidSymbol;
    }

    public void setUnidSymbol(String unidSymbol) {
        this.unidSymbol = unidSymbol;
    }
    
}
