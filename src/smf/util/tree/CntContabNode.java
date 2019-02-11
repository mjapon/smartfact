/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author manuel.japon
 */
public class CntContabNode extends DefaultMutableTreeNode{
    
    private CCNodeItem cuentacontable;
    
    public CntContabNode(CCNodeItem cuentacontable){
        this.cuentacontable = cuentacontable;        
    }

    @Override
    public String toString() {
        return String.format("%s %s", cuentacontable.getCodigo(), cuentacontable.getNombre());
    }

    public CCNodeItem getCuentacontable() {
        return cuentacontable;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
