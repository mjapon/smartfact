/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui.test;

import java.awt.Dimension;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import smf.controller.ArticulosJpaController;
import smf.entity.Articulos;
import smf.util.EntityManagerUtil;

/**
 *
 * @author manuel.japon
 */
public class JComboBoxFilterMain {
  public static void main(String[] args) {
      
      
      EntityManager em;
      ArticulosJpaController articulosController = null;
      
      List<Articulos> employees = null;
      
      try{
          em = EntityManagerUtil.createEntityManagerFactory();
          articulosController = new ArticulosJpaController(em);
          // = articulosController.listar("artNombre", "asc");          
          employees =  articulosController.listar("artNombre", "asc");
        }
        catch(Throwable ex){
            System.out.println("Error:"+ex.getMessage());
            ex.printStackTrace();
        }
      
      JComboBox<Articulos> comboBox = new JComboBox<>(employees.toArray(new Articulos[employees.size()]));

      ComboBoxFilterDecorator<Articulos> decorate = ComboBoxFilterDecorator.decorate(comboBox,
              CustomComboRenderer::getEmployeeDisplayText,
              JComboBoxFilterMain::employeeFilter);

      comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));

      JPanel panel = new JPanel();
      panel.add(comboBox);

      JFrame frame = createFrame();
      frame.add(panel);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
  }

  private static boolean employeeFilter(Articulos emp, String textToFilter) {
      if (textToFilter.isEmpty()) {
          return true;
      }
      return CustomComboRenderer.getEmployeeDisplayText(emp).toLowerCase()
                                .contains(textToFilter.toLowerCase());
  }

  private static JFrame createFrame() {
      JFrame frame = new JFrame("JComboBox Filter Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(600, 300));
      return frame;
  }
}
