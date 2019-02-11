/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

/**
 * Representa una columna para un dataModel
 * @author manuel.japon
 */
public class ColumnDM {

    private int index;
    private String desc;
    private Class klass;
    private String entityCol;
    private boolean isEdit;

    public ColumnDM(int index, String desc, Class cclass, String entityColumn, boolean isEdit) {
        this.index = index;
        this.desc = desc;
        this.klass = cclass;
        this.entityCol = entityColumn;
        this.isEdit = isEdit;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class getKlass() {
        return klass;
    }

    public void setKlass(Class klass) {
        this.klass = klass;
    }

    public String getEntityCol() {
        return entityCol;
    }

    public void setEntityCol(String entityCol) {
        this.entityCol = entityCol;
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
}
