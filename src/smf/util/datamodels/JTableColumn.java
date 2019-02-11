/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.datamodels;

/**
 *
 * @author manuel.japon
 */
public class JTableColumn<RType> {
    
    private String name;
    private Integer index;
    private Class klassType;
    private String dbName;    
    private IGetSetValueJC gsValueDlg;    

    public JTableColumn(Integer index, String name, String dbName, Class klassType, IGetSetValueJC<RType> gsValueDlg){
        this.name = name;
        this.index = index;
        this.klassType = klassType;
        this.dbName = dbName;
        this.gsValueDlg = gsValueDlg;
    }
    
     public JTableColumn(Integer index, String name, Class klassType, IGetSetValueJC<RType> gsValueDlg){
        this.name = name;
        this.index = index;
        this.klassType = klassType;
        this.dbName = "";
        this.gsValueDlg = gsValueDlg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Class getKlassType() {
        return klassType;
    }

    public void setKlassType(Class klassType) {
        this.klassType = klassType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    //Se setea el delegado
    public void setGsValueDlg(IGetSetValueJC gsValueDlg) {
        this.gsValueDlg = gsValueDlg;
    }  
    
    //Metodos para setear o obtener valores de esta columna
    public Object getValueAt(RType row, int rowIndex){
        return gsValueDlg.getValueAt(row, rowIndex);
    }
    
    public void setValueAt(RType row, int rowIndex, Object value){
        gsValueDlg.setValueAt(row, rowIndex, value);
    }
    
    public boolean isCellEditable(Object row){
        return gsValueDlg.isCellEditable(row);
    }
    
}