/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.test;

import smf.util.JBackupController;

/**
 *
 * @author manuel.japon
 */
public class TestMainClass {
    
    public static void main(String[] args){
        
        System.out.println("Prueba respaldo-->");
        
        JBackupController backupController = new  JBackupController();
        
        backupController.executeCommand("farmaciajj", "postgres", "backup");
        
        
        
    }
    
}
