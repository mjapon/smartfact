/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.io.Serializable;
import javax.swing.JTextField;
import smf.entity.Reportes;

/**
 *
 * @author manuel.japon
 */
public class ReporteRow implements Serializable{
    
    private Reportes entity;
    private JTextField textField;

    public ReporteRow(Reportes entity, JTextField textField) {
        this.entity = entity;
        this.textField = textField;
    }

    public Reportes getEntity() {
        return entity;
    }

    public void setEntity(Reportes entity) {
        this.entity = entity;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
    
}