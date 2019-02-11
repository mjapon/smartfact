/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels.rows;

/**
 *
 * @author mjapon
 */
public class ProveedorRow {
    
    private Integer provId;
    private String provName;

    public ProveedorRow(Integer provId, String provName) {
        this.provId = provId;
        this.provName = provName;
    }

    public Integer getProvId() {
        return provId;
    }

    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    @Override
    public String toString() {
        return this.provName; 
    }
    
    
    
}
