/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.gui;

import smf.gui.cxpc.CuentasXCBPFrame;
import smf.gui.contab.PlanCuentasFrame;
import smf.gui.facte.AdminVentasFrame;
import smf.gui.facte.FacturaVentaFrame;
import smf.gui.merc.MercaderiaFrame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import smf.gui.cajas.AdminCajasFrame;
import smf.gui.cajas.AperCajaFrame;
import smf.gui.cajas.CatalogoCajasFrame;
import smf.gui.cajas.CierrCajaFrame;
import smf.gui.clientes.ClientesFrame;
import smf.gui.facte.DetalleVentasByCajaFrame;
import smf.gui.merc.CategoriaFrame;
import smf.gui.merc.unid.UnidadesFrame;
import smf.gui.reportes.ReportesFrame;
import smf.gui.tickets.TicketsFrame;
import smf.util.EstadoAPP;

/**
 *
 * @author manuel.japon
 */
public class SmartFactMain extends javax.swing.JFrame {
    private SplashScreen splashScreen;
    public static Map<String, EstadoAPP> estadosApp;
    /**
     * Creates new form FarmaAppMain
     */
    public SmartFactMain() {
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        initEstados();
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                
                String title = sourceTabbedPane.getTitleAt(index);
                
                if ("Factura de Venta".equalsIgnoreCase(title)){                    
                    String statusFacturaVenta = FacturaVentaFrame.class.getName()+"1";
                    EstadoAPP estadoApp = estadosApp.get(statusFacturaVenta);
                    FacturaVentaFrame facturaFrame = (FacturaVentaFrame)estadoApp.getFrame();                    
                    facturaFrame.focusFiltro();
                    facturaFrame.restoreDefaultsDividerLocation();
                    facturaFrame.updateArticulos();                    
                    //Verificar apertura de caja
                    if (facturaFrame.checkAperturaCaja()){
                        //FarmaAppMain.showSystemTrayMsg("La caja ya fue aperturada");
                    }
                    else{
                        logicaClosePane(statusFacturaVenta);
                    }
                }
                else if ("Factura de Compra".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(FacturaVentaFrame.class.getName()+"2");
                    FacturaVentaFrame facturaFrame = (FacturaVentaFrame)estadoApp.getFrame();                    
                    facturaFrame.focusFiltro();
                    facturaFrame.restoreDefaultsDividerLocation();
                    facturaFrame.updateArticulos();
                }                
                else if ("Administrar Productos".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(MercaderiaFrame.class.getName());
                    MercaderiaFrame artsFrame = (MercaderiaFrame)estadoApp.getFrame();                    
                    artsFrame.updateArticulos();
                    artsFrame.filtroFocus();
                }                
                else if ("Cuentas X Cobrar".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(CuentasXCBPFrame.class.getName()+"3");
                    CuentasXCBPFrame frame = (CuentasXCBPFrame)estadoApp.getFrame();
                    frame.restoreDefaultsDividerLocation();
                }
                else if ("Cuentas X Pagar".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(CuentasXCBPFrame.class.getName()+"4");
                    CuentasXCBPFrame frame = (CuentasXCBPFrame)estadoApp.getFrame();
                    frame.restoreDefaultsDividerLocation();
                }                
                else if ("Clientes".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(ClientesFrame.class.getName()+"1");
                    ClientesFrame frame = (ClientesFrame)estadoApp.getFrame();
                    frame.buscar();
                }
                else if ("Proveedores".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(ClientesFrame.class.getName()+"2");
                    ClientesFrame frame = (ClientesFrame)estadoApp.getFrame();
                    frame.buscar();
                }
                else if ("Home".equalsIgnoreCase(title)){
                    if (homeFrame != null){
                        homeFrame.updateGraphics();
                    }
                }  
                else if ("Administrar Ventas".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(AdminVentasFrame.class.getName()+"1");
                    AdminVentasFrame frame = (AdminVentasFrame)estadoApp.getFrame();
                    frame.logicaBuscar();
                }
                else if("Administrar Compras".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(AdminVentasFrame.class.getName()+"2");
                    AdminVentasFrame frame = (AdminVentasFrame)estadoApp.getFrame();
                    frame.logicaBuscar();
                }
                else if("Tickets".equalsIgnoreCase(title)){
                    EstadoAPP estadoApp = estadosApp.get(TicketsFrame.class.getName());
                    TicketsFrame frame = (TicketsFrame)estadoApp.getFrame();
                    frame.logicaBuscar();
                }
            }
          };        
        this.tabbedPaneMain.addChangeListener(changeListener);
        initHome();
    }    
    
    public EstadoAPP createEstado(String className, Integer tra_codigo){
        ImageIcon icon = createImageIcon("/smf/gui/icons/iconpet.png");
        
        if (FacturaVentaFrame.class.getName().equalsIgnoreCase(className)){
            
            if (tra_codigo == 1){
                //Facturacion
                FacturaVentaFrame newFacturaFrame = new FacturaVentaFrame(1, null);                
                newFacturaFrame.setRoot(this);
                EstadoAPP facturaEstadoAPP = new EstadoAPP(newFacturaFrame.getContentPane(), icon, "Factura de Venta", newFacturaFrame);        

                return facturaEstadoAPP;
            }
            else if (tra_codigo == 2){                
                //Factura de compras
                FacturaVentaFrame newFacturaFrame = new FacturaVentaFrame(2, null);                
                newFacturaFrame.setRoot(this);
                EstadoAPP facturaEstadoAPP = new EstadoAPP(newFacturaFrame.getContentPane(), icon, "Factura de Compra", newFacturaFrame);        

                return facturaEstadoAPP;
            }            
        }        
        else if (MercaderiaFrame.class.getName().equalsIgnoreCase(className)){
            MercaderiaFrame mercaderiaFrame = new MercaderiaFrame();
            mercaderiaFrame.setRoot(this);
            
            EstadoAPP mercaderiaEstadoAPP = new EstadoAPP(mercaderiaFrame.getContentPane(), icon, "Administrar Productos", mercaderiaFrame);
            return mercaderiaEstadoAPP;
        }
        else if (AdminVentasFrame.class.getName().equalsIgnoreCase(className)){
            if (tra_codigo == 1){                
                //Administrar ventas
                AdminVentasFrame adminVentasFrame = new AdminVentasFrame(1);//Ventas
                adminVentasFrame.setRoot(this);
                EstadoAPP adminVentasEstadoAPP = new EstadoAPP(adminVentasFrame.getContentPane(), icon, "Administrar Ventas", adminVentasFrame);                

                return adminVentasEstadoAPP;
            }
            else if (tra_codigo == 2){                
                //Administrar compras              
                AdminVentasFrame adminComprasFrame = new AdminVentasFrame(2);//Ventas
                adminComprasFrame.setRoot(this);
                EstadoAPP adminComprasEstadoAPP = new EstadoAPP(adminComprasFrame.getContentPane(), icon, "Administrar Compras", adminComprasFrame);                

                return adminComprasEstadoAPP;
            }
        }
        else if ( DetalleVentasByCajaFrame.class.getName().equalsIgnoreCase(className) ){
            DetalleVentasByCajaFrame byCajaFrame = new DetalleVentasByCajaFrame(tra_codigo);
            byCajaFrame.setRoot(this);
            EstadoAPP adminCXCPAPP = new EstadoAPP(byCajaFrame.getContentPane(), icon, "Listado de Ventas por Caja", byCajaFrame);               
            return adminCXCPAPP;
        }
        else if (CuentasXCBPFrame.class.getName().equalsIgnoreCase(className)){            
            if (tra_codigo == 3){                
                CuentasXCBPFrame cBPFrame = new CuentasXCBPFrame(3);
                cBPFrame.setRoot(this);
                EstadoAPP adminCXCPAPP = new EstadoAPP(cBPFrame.getContentPane(), icon, "Cuentas X Cobrar", cBPFrame);               

                return adminCXCPAPP;            
                
            }
            else if(tra_codigo == 4){                
                CuentasXCBPFrame cBPFrame = new CuentasXCBPFrame(4);
                cBPFrame.setRoot(this);
                EstadoAPP adminCXCPAPP = new EstadoAPP(cBPFrame.getContentPane(), icon, "Cuentas X Pagar", cBPFrame);                

                return adminCXCPAPP;           
            }
        }
        else if (MovsCajaFrame.class.getName().equalsIgnoreCase(className)){
            MovsCajaFrame movsCajaFrame = new MovsCajaFrame();
            movsCajaFrame.setRoot(this);
            EstadoAPP cajasAPP = new EstadoAPP(movsCajaFrame.getContentPane(), icon, "Movimientos Caja", movsCajaFrame);
            return cajasAPP;
        }
        else if (PlanCuentasFrame.class.getName().equalsIgnoreCase(className)){
            PlanCuentasFrame planCuentasFrame = new PlanCuentasFrame();
            planCuentasFrame.setRoot(this);
            EstadoAPP planCuentasAPP = new EstadoAPP(planCuentasFrame.getContentPane(), icon, "Plan Cuentas", planCuentasFrame);
            return planCuentasAPP;
        }
        else if (AdminCajasFrame.class.getName().equalsIgnoreCase(className)){
            AdminCajasFrame adminCajasFrame = new AdminCajasFrame();
            adminCajasFrame.setRoot(this);
            EstadoAPP planCuentasAPP = new EstadoAPP(adminCajasFrame.getContentPane(), icon, "Listado de Cajas", adminCajasFrame);
            return planCuentasAPP;
        }
        else if (ClientesFrame.class.getName().equalsIgnoreCase(className)){
            ClientesFrame adminClientesFrame = new ClientesFrame(tra_codigo);
            adminClientesFrame.setRoot(this);            
            String titulo = "Clientes";
            if (tra_codigo == 2){
                titulo = "Proveedores";
            }
            EstadoAPP clientesAPP = new EstadoAPP(adminClientesFrame.getContentPane(), icon, titulo, adminClientesFrame);
            return clientesAPP;
        }
        else if (TicketsFrame.class.getName().equalsIgnoreCase(className)){
            TicketsFrame adminTicketsFrame = new TicketsFrame();
            adminTicketsFrame.setRoot(this);            
            EstadoAPP clientesAPP = new EstadoAPP(adminTicketsFrame.getContentPane(), icon, "Tickets", adminTicketsFrame);
            return clientesAPP;
        }
        
        return null;
        
    }
    
    public void initEstados(){
        estadosApp = new HashMap<>();        
        //estadosApp.put(ArticulosFrame.class.getName(), createEstado(ArticulosFrame.class.getName(), 0));
        
        estadosApp.put(FacturaVentaFrame.class.getName()+"1", createEstado(FacturaVentaFrame.class.getName(), 1));        
        estadosApp.put(AdminVentasFrame.class.getName()+"1", createEstado(AdminVentasFrame.class.getName(), 1));    
        estadosApp.put(CuentasXCBPFrame.class.getName()+"3", createEstado(CuentasXCBPFrame.class.getName(), 3));    
        
        
        estadosApp.put(FacturaVentaFrame.class.getName()+"2", createEstado(FacturaVentaFrame.class.getName(), 2));
        estadosApp.put(AdminVentasFrame.class.getName()+"2", createEstado(AdminVentasFrame.class.getName(), 2));
        estadosApp.put(CuentasXCBPFrame.class.getName()+"4", createEstado(CuentasXCBPFrame.class.getName(), 4));
        
        estadosApp.put(MovsCajaFrame.class.getName(), createEstado(MovsCajaFrame.class.getName(),0));        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneMain = new javax.swing.JTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        salirMI = new javax.swing.JMenu();
        exitMenuItem1 = new javax.swing.JMenuItem();
        fileMenu = new javax.swing.JMenu();
        facturarMenuItem = new javax.swing.JMenuItem();
        adminMenuItem = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        facturarMenuItem1 = new javax.swing.JMenuItem();
        fileMenu3 = new javax.swing.JMenu();
        regFacturaComprarMI = new javax.swing.JMenuItem();
        adminComprasMenuItem = new javax.swing.JMenuItem();
        facturarMenuItem2 = new javax.swing.JMenuItem();
        adminArtsMenu = new javax.swing.JMenu();
        cutMenuItem1 = new javax.swing.JMenuItem();
        jMICats = new javax.swing.JMenuItem();
        jMIUnidades = new javax.swing.JMenuItem();
        contabMenu = new javax.swing.JMenu();
        planCuentasMI = new javax.swing.JMenuItem();
        cajasMenu = new javax.swing.JMenu();
        movsCajaMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMITiposCaja = new javax.swing.JMenuItem();
        clientesMenu = new javax.swing.JMenu();
        jMIAdminCli = new javax.swing.JMenuItem();
        jMIAdminProv = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMIAdmin = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIAdmin1 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartFact 1.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().add(tabbedPaneMain, java.awt.BorderLayout.CENTER);

        menuBar.setBorderPainted(false);
        menuBar.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        menuBar.setMargin(new java.awt.Insets(10, 10, 10, 10));

        salirMI.setMnemonic('f');
        salirMI.setText("Sistema");
        salirMI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        exitMenuItem1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        exitMenuItem1.setMnemonic('x');
        exitMenuItem1.setText("Salir");
        exitMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItem1ActionPerformed(evt);
            }
        });
        salirMI.add(exitMenuItem1);

        menuBar.add(salirMI);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Ventas");
        fileMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        facturarMenuItem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        facturarMenuItem.setMnemonic('o');
        facturarMenuItem.setText("Facturar");
        facturarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturarMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(facturarMenuItem);

        adminMenuItem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        adminMenuItem.setMnemonic('o');
        adminMenuItem.setText("Movimientos");
        adminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(adminMenuItem);

        jMenuItem4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMenuItem4.setText("Movimientos por caja");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem4);

        facturarMenuItem1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        facturarMenuItem1.setMnemonic('o');
        facturarMenuItem1.setText("Cuentas por cobrar");
        facturarMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturarMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(facturarMenuItem1);

        menuBar.add(fileMenu);

        fileMenu3.setMnemonic('f');
        fileMenu3.setText("Compras");
        fileMenu3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        regFacturaComprarMI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        regFacturaComprarMI.setMnemonic('o');
        regFacturaComprarMI.setText("Registrar Factura");
        regFacturaComprarMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regFacturaComprarMIActionPerformed(evt);
            }
        });
        fileMenu3.add(regFacturaComprarMI);

        adminComprasMenuItem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        adminComprasMenuItem.setMnemonic('o');
        adminComprasMenuItem.setText("Movimientos");
        adminComprasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminComprasMenuItemActionPerformed(evt);
            }
        });
        fileMenu3.add(adminComprasMenuItem);

        facturarMenuItem2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        facturarMenuItem2.setMnemonic('o');
        facturarMenuItem2.setText("Cuentas por pagar");
        facturarMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturarMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu3.add(facturarMenuItem2);

        menuBar.add(fileMenu3);

        adminArtsMenu.setMnemonic('e');
        adminArtsMenu.setText("Inventarios");
        adminArtsMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cutMenuItem1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cutMenuItem1.setMnemonic('t');
        cutMenuItem1.setText("Administrar");
        cutMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItem1ActionPerformed(evt);
            }
        });
        adminArtsMenu.add(cutMenuItem1);

        jMICats.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMICats.setText("Categorías");
        jMICats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICatsActionPerformed(evt);
            }
        });
        adminArtsMenu.add(jMICats);

        jMIUnidades.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMIUnidades.setText("Unidades");
        jMIUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIUnidadesActionPerformed(evt);
            }
        });
        adminArtsMenu.add(jMIUnidades);

        menuBar.add(adminArtsMenu);

        contabMenu.setMnemonic('e');
        contabMenu.setText("Contabilidad");
        contabMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        planCuentasMI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        planCuentasMI.setMnemonic('t');
        planCuentasMI.setText("Plan de cuentas");
        planCuentasMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planCuentasMIActionPerformed(evt);
            }
        });
        contabMenu.add(planCuentasMI);

        menuBar.add(contabMenu);

        cajasMenu.setMnemonic('e');
        cajasMenu.setText("Cajas");
        cajasMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        movsCajaMenuItem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        movsCajaMenuItem.setMnemonic('t');
        movsCajaMenuItem.setText("Movimientos");
        movsCajaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movsCajaMenuItemActionPerformed(evt);
            }
        });
        cajasMenu.add(movsCajaMenuItem);

        jMenuItem1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMenuItem1.setText("Apertura");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        cajasMenu.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMenuItem2.setText("Cierre");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        cajasMenu.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMenuItem3.setText("Administrar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        cajasMenu.add(jMenuItem3);

        jMITiposCaja.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMITiposCaja.setText("Tipos de caja");
        jMITiposCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMITiposCajaActionPerformed(evt);
            }
        });
        cajasMenu.add(jMITiposCaja);

        menuBar.add(cajasMenu);

        clientesMenu.setText("Referentes");
        clientesMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jMIAdminCli.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMIAdminCli.setText("Clientes");
        jMIAdminCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAdminCliActionPerformed(evt);
            }
        });
        clientesMenu.add(jMIAdminCli);

        jMIAdminProv.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMIAdminProv.setText("Proveedores");
        jMIAdminProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAdminProvActionPerformed(evt);
            }
        });
        clientesMenu.add(jMIAdminProv);

        menuBar.add(clientesMenu);

        jMenu1.setText("Tickets");
        jMenu1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jMIAdmin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMIAdmin.setText("Administrar");
        jMIAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAdminActionPerformed(evt);
            }
        });
        jMenu1.add(jMIAdmin);

        menuBar.add(jMenu1);

        jMenu2.setText("Reportes");
        jMenu2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jMIAdmin1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jMIAdmin1.setText("Reportes");
        jMIAdmin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAdmin1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMIAdmin1);

        menuBar.add(jMenu2);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Ayuda");
        helpMenu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        aboutMenuItem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("Acerca de");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SmartFactMain.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public void showNewFacturaFrame(){
        EstadoAPP estadoApp = estadosApp.get(FacturaVentaFrame.class.getName()+"1");
        if (estadoApp == null){
            estadoApp = createEstado(FacturaVentaFrame.class.getName(), 1);
            estadosApp.put(FacturaVentaFrame.class.getName()+"1", estadoApp);
        }
        
        logicaOpenPane(estadoApp);
    }
    
    private void facturarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturarMenuItemActionPerformed
                
        showNewFacturaFrame();
        
    }//GEN-LAST:event_facturarMenuItemActionPerformed

    
    public void logicaOpenPane(EstadoAPP estadoAPP){
        //EstadoAPP estadoApp = estadosApp.get("INVENTARIOS");
        Integer indexOfComp =  tabbedPaneMain.indexOfComponent(estadoAPP.getPane());
        
        if (indexOfComp  == -1){
            tabbedPaneMain.addTab(estadoAPP.getName(), estadoAPP.getIcon(),  estadoAPP.getPane());
        }
        
        try{
            tabbedPaneMain.setSelectedComponent(estadoAPP.getPane());
        }
        catch(Throwable ex){
            System.out.println("Error en setSelectedComponent:"+ex.getMessage());
        }
        
    }
    
    public void logicaClosePane(String statusName){
        
        EstadoAPP estadoApp = estadosApp.get(statusName);
        if (estadoApp != null){
            logicaClosePane(estadoApp);
            estadosApp.put(statusName, null);
        }
        
    }
    
    public void logicaClosePane(EstadoAPP estadoAPP){        
        Integer indexOfComp =  tabbedPaneMain.indexOfComponent(estadoAPP.getPane());
        if (indexOfComp  == -1){
            
        }
        else{
            tabbedPaneMain.remove(estadoAPP.getPane());
        }                
    }
    
    public void showListaVentas(){
         EstadoAPP estadoApp = estadosApp.get(AdminVentasFrame.class.getName()+"1");        
        if (estadoApp == null){
            estadoApp = createEstado(AdminVentasFrame.class.getName(), 1);
            estadosApp.put(AdminVentasFrame.class.getName()+"1", estadoApp);
        }

        logicaOpenPane(estadoApp);
    }
    
    private void adminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminMenuItemActionPerformed
       
       showListaVentas();
        
        
    }//GEN-LAST:event_adminMenuItemActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        /*
        try{
            URL resourceIco = SmartFactMain.class.getResource("/smf/gui/icons/iconpet.png");
            ImageIcon imgIco = new ImageIcon(resourceIco);
            setIconImage(imgIco.getImage());            
        }
        catch(Throwable ex){
            System.out.println("Error al tratar de setear ico->"+ex.getMessage());
            ex.printStackTrace();
        } 
        */
    }//GEN-LAST:event_formWindowOpened
    
    public void showCuentasXCobrar(){
        EstadoAPP estadoApp = estadosApp.get(CuentasXCBPFrame.class.getName()+"3");        
        if (estadoApp == null){
            estadoApp = createEstado(CuentasXCBPFrame.class.getName(), 3);
            estadosApp.put(CuentasXCBPFrame.class.getName()+"3", estadoApp);
        }

        logicaOpenPane(estadoApp);
    }
    private void facturarMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturarMenuItem1ActionPerformed
        
        showCuentasXCobrar();
        
        
    }//GEN-LAST:event_facturarMenuItem1ActionPerformed
    
    public void showCuentasXPagar(){
          EstadoAPP estadoApp = estadosApp.get(CuentasXCBPFrame.class.getName()+"4");        
        if (estadoApp == null){
            estadoApp = createEstado(CuentasXCBPFrame.class.getName(), 4);
            estadosApp.put(CuentasXCBPFrame.class.getName()+"4", estadoApp);
        }

        logicaOpenPane(estadoApp); 
    }
    
    private void facturarMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturarMenuItem2ActionPerformed
        
      showCuentasXPagar();
        
    }//GEN-LAST:event_facturarMenuItem2ActionPerformed
    
    public void showFacturaCompra(){
         EstadoAPP estadoApp = estadosApp.get(FacturaVentaFrame.class.getName()+"2");
        
        if (estadoApp == null){
            estadoApp = createEstado(FacturaVentaFrame.class.getName(), 2);
            estadosApp.put(FacturaVentaFrame.class.getName()+"2", estadoApp);
        }
        
        logicaOpenPane(estadoApp);
    }
        
    private void regFacturaComprarMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regFacturaComprarMIActionPerformed
        
       showFacturaCompra();
        
    }//GEN-LAST:event_regFacturaComprarMIActionPerformed
    
    public void showListaCompras(){
         EstadoAPP estadoApp = estadosApp.get(AdminVentasFrame.class.getName()+"2");        
        if (estadoApp == null){
            estadoApp = createEstado(AdminVentasFrame.class.getName(), 2);
            estadosApp.put(AdminVentasFrame.class.getName()+"2", estadoApp);
        }
        logicaOpenPane(estadoApp);
    }
    
    private void adminComprasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminComprasMenuItemActionPerformed
       showListaCompras();
    }//GEN-LAST:event_adminComprasMenuItemActionPerformed
    
    public void exitApp(){
        int response = JOptionPane.showConfirmDialog(null, "¿Seguro que desea salir? ");
        if (response == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    private void exitMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItem1ActionPerformed
        exitApp();
    }//GEN-LAST:event_exitMenuItem1ActionPerformed

    private void movsCajaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movsCajaMenuItemActionPerformed
        EstadoAPP estadoApp = estadosApp.get(MovsCajaFrame.class.getName());
        if (estadoApp == null){
            estadoApp = createEstado(MovsCajaFrame.class.getName(), 0);
            estadosApp.put(MovsCajaFrame.class.getName(), estadoApp);
        }
        logicaOpenPane(estadoApp);        
    }//GEN-LAST:event_movsCajaMenuItemActionPerformed
    
    public void showInventarios(){
        EstadoAPP estadoApp = estadosApp.get(MercaderiaFrame.class.getName());        
        if (estadoApp == null){
            estadoApp = createEstado(MercaderiaFrame.class.getName(), 0);
            estadosApp.put(MercaderiaFrame.class.getName(), estadoApp);
        }
        
        logicaOpenPane(estadoApp);
    }
    
    public void showClientesFrame(){
        EstadoAPP estadoApp = estadosApp.get(ClientesFrame.class.getName());        
        if (estadoApp == null){
            estadoApp = createEstado(ClientesFrame.class.getName(), 1);
            estadosApp.put(ClientesFrame.class.getName()+"1", estadoApp);
        }
        logicaOpenPane(estadoApp);        
    }
    
    public void showProveedoresFrame(){
        EstadoAPP estadoApp = estadosApp.get(ClientesFrame.class.getName());        
        if (estadoApp == null){
            estadoApp = createEstado(ClientesFrame.class.getName(), 2);
            estadosApp.put(ClientesFrame.class.getName()+"2", estadoApp);
        }
        logicaOpenPane(estadoApp);    
        
    }
    
    public void showTicketsFrame(){
        EstadoAPP estadoApp = estadosApp.get(TicketsFrame.class.getName());        
        if (estadoApp == null){
            estadoApp = createEstado(TicketsFrame.class.getName(), 0);
            estadosApp.put(TicketsFrame.class.getName(), estadoApp);
        }
        logicaOpenPane(estadoApp);
    }
    
    
    private void cutMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItem1ActionPerformed
        showInventarios();
    }//GEN-LAST:event_cutMenuItem1ActionPerformed
    
    public void showAperturaCajaNewVer(){
        AperCajaFrame aperCajaFrame = new AperCajaFrame();
        int res = aperCajaFrame.initForm();
        if (res == 0){
            aperCajaFrame.centerOnScreen();
            aperCajaFrame.setVisible(true);        
        }
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        showAperturaCajaNewVer();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
        
    public void showCierreCajaNV(){
        CierrCajaFrame cierrCajaFrame = new CierrCajaFrame(0);
        cierrCajaFrame.centerOnScreen();
        cierrCajaFrame.setVisible(true);
        cierrCajaFrame.loadLastOpenedCaja();        
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        showCierreCajaNV();         
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        EstadoAPP estadoApp = estadosApp.get(AdminCajasFrame.class.getName());        
        if (estadoApp == null){
            estadoApp = createEstado(AdminCajasFrame.class.getName(), 0);
            estadosApp.put(AdminCajasFrame.class.getName(), estadoApp);
        }
        logicaOpenPane(estadoApp);
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMICatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICatsActionPerformed
        //TODO: Catgorias action performed------>
        CategoriaFrame categoriaFrame = new CategoriaFrame();
        categoriaFrame.loadItems();
        categoriaFrame.centerOnScreen();
        categoriaFrame.setVisible(true);            
        
    }//GEN-LAST:event_jMICatsActionPerformed

    private void jMITiposCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMITiposCajaActionPerformed
        CatalogoCajasFrame catalogoCajasFrame = new CatalogoCajasFrame();
        catalogoCajasFrame.loadItems();
        catalogoCajasFrame.centerOnScreen();
        catalogoCajasFrame.setVisible(true);
    }//GEN-LAST:event_jMITiposCajaActionPerformed

    private void jMIAdminCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAdminCliActionPerformed
        showClientesFrame();
        
    }//GEN-LAST:event_jMIAdminCliActionPerformed

    private void jMIAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAdminActionPerformed
        showTicketsFrame();
    }//GEN-LAST:event_jMIAdminActionPerformed

    public void showDetallasFactByCaja(){
        String name = DetalleVentasByCajaFrame.class.getName()+"1";
        String nameFrame = DetalleVentasByCajaFrame.class.getName();        
        EstadoAPP estadoApp = estadosApp.get(name);
        if (estadoApp == null){
            estadoApp = createEstado(nameFrame, 1);
            estadosApp.put(name, estadoApp);
        }
        logicaOpenPane(estadoApp);
    }
    
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
         showDetallasFactByCaja();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMIAdmin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAdmin1ActionPerformed
        
        showReportesFrame();
        
    }//GEN-LAST:event_jMIAdmin1ActionPerformed
    
    public void showReportesFrame(){
        ReportesFrame frame = new ReportesFrame();
        frame.centerOnScreen();
        frame.setVisible(true);
    }
    
    private void jMIUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIUnidadesActionPerformed
        UnidadesFrame unidadesFrame = new UnidadesFrame();
        unidadesFrame.centerOnScreen();
        unidadesFrame.setVisible(true);
    }//GEN-LAST:event_jMIUnidadesActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void jMIAdminProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAdminProvActionPerformed
        showProveedoresFrame();
    }//GEN-LAST:event_jMIAdminProvActionPerformed

    private void planCuentasMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planCuentasMIActionPerformed
        showPlanCuentasFrame();
    }//GEN-LAST:event_planCuentasMIActionPerformed
    
    public void showPlanCuentasFrame(){
        PlanCuentasFrame planCuentasFrame = new PlanCuentasFrame();
        planCuentasFrame.centerOnScreen();
        planCuentasFrame.setVisible(true);
    }
    
    public SplashScreen getSplashScreen() {
        return splashScreen;
    }

    public void setSplashScreen(SplashScreen splashScreen) {
        this.splashScreen = splashScreen;
    }
    
    public void hideSplashScreen(){
        this.splashScreen.setVisible(false);
    }
    
    public void initHome(){        
        ImageIcon icon = createImageIcon("/smf/gui/icons/icons8-home.png");
        homeFrame = new HomeFrame();
        homeFrame.setFarmaAppMain(this);
        tabbedPaneMain.addTab("Home", icon,  homeFrame.getContentPane());
        tabbedPaneMain.setSelectedComponent(homeFrame.getContentPane());
    }
    
    public static void showSystemTrayMsg(String msg){
        try{
            trayIcon.displayMessage(msg, "SmartFact", MessageType.INFO);
        }
        catch(Throwable ex){
            System.out.println("Error al presentar el mensaje:"+ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void initSystemTray(){
        try{
            systemTray = SystemTray.getSystemTray();
            URL resource = SmartFactMain.class.getResource("/smf/gui/icons/icons8_Waze_48.png");
            Image image  = new ImageIcon(ImageIO.read(resource)).getImage();            
            trayIcon = new TrayIcon(image, "SmartFact");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("SmartFact");
            systemTray.add(trayIcon);
        }
        catch(Throwable ex){
            System.out.println("Error al inicializar SystemTray:"+ex.getMessage());
            ex.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setSize(500, 300);
        splashScreen.centerOnScreen();
        splashScreen.loadImage();
        splashScreen.setVisible(true);
        
        SmartFactMain.initSystemTray();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch(Throwable ex){
                    System.out.println("Error al establecer look and fell:"+ ex.getMessage());
                    ex.printStackTrace();
                }
                SmartFactMain app = new SmartFactMain();
                app.setSplashScreen(splashScreen);
                app.hideSplashScreen();
                app.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu adminArtsMenu;
    private javax.swing.JMenuItem adminComprasMenuItem;
    private javax.swing.JMenuItem adminMenuItem;
    private javax.swing.JMenu cajasMenu;
    private javax.swing.JMenu clientesMenu;
    private javax.swing.JMenu contabMenu;
    private javax.swing.JMenuItem cutMenuItem1;
    private javax.swing.JMenuItem exitMenuItem1;
    private javax.swing.JMenuItem facturarMenuItem;
    private javax.swing.JMenuItem facturarMenuItem1;
    private javax.swing.JMenuItem facturarMenuItem2;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu fileMenu3;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem jMIAdmin;
    private javax.swing.JMenuItem jMIAdmin1;
    private javax.swing.JMenuItem jMIAdminCli;
    private javax.swing.JMenuItem jMIAdminProv;
    private javax.swing.JMenuItem jMICats;
    private javax.swing.JMenuItem jMITiposCaja;
    private javax.swing.JMenuItem jMIUnidades;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem movsCajaMenuItem;
    private javax.swing.JMenuItem planCuentasMI;
    private javax.swing.JMenuItem regFacturaComprarMI;
    private javax.swing.JMenu salirMI;
    private javax.swing.JTabbedPane tabbedPaneMain;
    // End of variables declaration//GEN-END:variables
    
    private HomeFrame homeFrame;
    private static TrayIcon trayIcon;
    private static SystemTray systemTray;
}
