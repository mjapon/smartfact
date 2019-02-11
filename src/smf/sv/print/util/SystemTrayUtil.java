/*    */ package smf.sv.print.util;
/*    */ 
/*    */ import smf.sv.print.gui.ConfigFrame;
/*    */ import smf.sv.print.gui.PrintFrame;
/*    */ import java.awt.MenuItem;
/*    */ import java.awt.PopupMenu;
/*    */ import java.awt.SystemTray;
/*    */ import java.awt.TrayIcon;
/*    */ import java.awt.TrayIcon.MessageType;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class SystemTrayUtil
/*    */ {
/*    */   private TrayIcon trayIcon;
/*    */   
/*    */   public void showBaloom(String msg)
/*    */   {
/* 21 */     this.trayIcon.displayMessage(Ctes.NOMBRES_SYS, msg, TrayIcon.MessageType.INFO);
/*    */   }
/*    */   
/*    */   public void showBaloomError(String msg) {
/* 25 */     this.trayIcon.displayMessage(Ctes.NOMBRES_SYS, msg, TrayIcon.MessageType.ERROR);
/*    */   }
/*    */   
/*    */   public void addSystemTray(ActionListener actionListener) {
/* 29 */     String ruta_icono = Ctes.RUTA_ICONO_SYS;
/* 30 */     if (SystemTray.isSupported()) {
/* 31 */       PopupMenu popup = createPopupMenu();
/* 32 */       SystemTray systemTray = SystemTray.getSystemTray();
/* 33 */       this.trayIcon = new TrayIcon(new ImageIcon(ruta_icono, "omt").getImage(), Ctes.NOMBRES_SYS, popup);
/* 34 */       this.trayIcon.setImageAutoSize(true);
/* 35 */       this.trayIcon.addActionListener(actionListener);
/*    */       try {
/* 37 */         systemTray.add(this.trayIcon);
/* 38 */         this.trayIcon.displayMessage(Ctes.NOMBRES_SYS, "En espera...", TrayIcon.MessageType.INFO);
/*    */       } catch (Exception e) {
/* 40 */         e.printStackTrace();
/* 41 */         this.trayIcon.displayMessage(Ctes.NOMBRES_SYS, "Error: " + e.getMessage(), TrayIcon.MessageType.ERROR);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected static PopupMenu createPopupMenu() {
/* 47 */     PopupMenu popup = new PopupMenu();
/* 48 */     MenuItem aboutItem = new MenuItem("Acerca de");
/* 49 */     MenuItem confItem = new MenuItem("Configurar");
/* 50 */     MenuItem imprimirItem = new MenuItem("Imprimir");
/* 51 */     MenuItem exitItem = new MenuItem("Salir");
/*    */     
/* 53 */     exitItem.addActionListener(new ActionListener()
/*    */     {
/*    */ 
/*    */       public void actionPerformed(ActionEvent e) {}
/*    */ 
/* 58 */     });
/* 59 */     imprimirItem.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 61 */         //SystemTrayUtil.this.getPrintFrame().setVisible(true);
/*    */       }
/*    */       
/* 64 */     });
/* 65 */     confItem.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 67 */        // SystemTrayUtil.this.getConfigFrame().setVisible(true);
/*    */       }
/*    */       
/*    */ 
/* 71 */     });
/* 72 */     popup.add(aboutItem);
/* 73 */     popup.add(confItem);
/* 74 */     popup.add(imprimirItem);
/* 75 */     popup.add(exitItem);
/*    */     
/* 77 */     return popup;
/*    */   }
/*    */ }


/* Location:              /Users/mjapon/Documents/jarisyplusprint/IsyplusPrint/jar/printws.jar!/com/serviestudios/print/util/SystemTrayUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */