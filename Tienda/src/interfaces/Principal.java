package interfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author danie
 */
public class Principal extends javax.swing.JFrame {
    static String ced, name;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();

    }

    public Principal(String nombre, String Cedula) {
        initComponents();
        ced= Cedula;
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdskPrincipal = new javax.swing.JDesktopPane();
        jtbnIngPrenda = new javax.swing.JButton();
        jtbnModPrenda = new javax.swing.JButton();
        jtbListPrendas = new javax.swing.JButton();
        jtbnVenta = new javax.swing.JButton();
        jtbnRegCliente = new javax.swing.JButton();
        jtbnCerrarSesion = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jdskPrincipalLayout = new javax.swing.GroupLayout(jdskPrincipal);
        jdskPrincipal.setLayout(jdskPrincipalLayout);
        jdskPrincipalLayout.setHorizontalGroup(
            jdskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
        );
        jdskPrincipalLayout.setVerticalGroup(
            jdskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtbnIngPrenda.setText("Ingresar Prenda");
        jtbnIngPrenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnIngPrendaActionPerformed(evt);
            }
        });

        jtbnModPrenda.setText("Modificar Prueba");
        jtbnModPrenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnModPrendaActionPerformed(evt);
            }
        });

        jtbListPrendas.setText("Lista de Prendas");
        jtbListPrendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbListPrendasActionPerformed(evt);
            }
        });

        jtbnVenta.setText("Realizar Venta");
        jtbnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnVentaActionPerformed(evt);
            }
        });

        jtbnRegCliente.setText("Registrar Cliente");
        jtbnRegCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnRegClienteActionPerformed(evt);
            }
        });

        jtbnCerrarSesion.setText("Cerrar Sesion");
        jtbnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnCerrarSesionActionPerformed(evt);
            }
        });

        jButton6.setText("Registro Vendedores");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbnRegCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbnVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbListPrendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbnModPrenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtbnIngPrenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addComponent(jdskPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdskPrincipal)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jtbnIngPrenda)
                .addGap(39, 39, 39)
                .addComponent(jtbnModPrenda)
                .addGap(52, 52, 52)
                .addComponent(jtbListPrendas)
                .addGap(53, 53, 53)
                .addComponent(jtbnVenta)
                .addGap(48, 48, 48)
                .addComponent(jtbnRegCliente)
                .addGap(48, 48, 48)
                .addComponent(jButton6)
                .addGap(56, 56, 56)
                .addComponent(jtbnCerrarSesion)
                .addGap(25, 73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbnRegClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnRegClienteActionPerformed
        RegistroClientes ven1 = new RegistroClientes();
        jdskPrincipal.add(ven1);
        ven1.setVisible(true);
    }//GEN-LAST:event_jtbnRegClienteActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        RegistroVendedores ven1 = new RegistroVendedores();
        jdskPrincipal.add(ven1);
        ven1.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jtbListPrendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbListPrendasActionPerformed
        ListaPrendas lista = new ListaPrendas();
        jdskPrincipal.add(lista);
        lista.setVisible(true);
    }//GEN-LAST:event_jtbListPrendasActionPerformed

    private void jtbnIngPrendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnIngPrendaActionPerformed
        Agregar a = new Agregar();
        jdskPrincipal.add(a);
        a.setVisible(true);
    }//GEN-LAST:event_jtbnIngPrendaActionPerformed

    private void jtbnModPrendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnModPrendaActionPerformed
        Modificar a = new Modificar();
        jdskPrincipal.add(a);
        a.setVisible(true);
    }//GEN-LAST:event_jtbnModPrendaActionPerformed

    private void jtbnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnVentaActionPerformed
        RegistroVenta a = new RegistroVenta(ced);
        jdskPrincipal.add(a);
        a.setVisible(true);
    }//GEN-LAST:event_jtbnVentaActionPerformed

    private void jtbnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnCerrarSesionActionPerformed
        Loguin login = new Loguin();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jtbnCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal(name, ced).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JDesktopPane jdskPrincipal;
    private javax.swing.JButton jtbListPrendas;
    private javax.swing.JButton jtbnCerrarSesion;
    private javax.swing.JButton jtbnIngPrenda;
    private javax.swing.JButton jtbnModPrenda;
    private javax.swing.JButton jtbnRegCliente;
    private javax.swing.JButton jtbnVenta;
    // End of variables declaration//GEN-END:variables
}
