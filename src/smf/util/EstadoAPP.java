/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author mjapon
 */
public class EstadoAPP {
    private Container pane;
    private ImageIcon icon;
    private String name;
    private boolean active;   
    private JFrame frame;

    public EstadoAPP(Container pane, ImageIcon icon, String name, JFrame frame) {
        this.pane = pane;
        this.icon = icon;
        this.name = name;
        this.active =false;
        this.frame = frame;
    }

    public Container getPane() {
        return pane;
    }

    public void setPane(Container pane) {
        this.pane = pane;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }    
}