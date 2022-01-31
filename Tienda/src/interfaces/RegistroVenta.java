/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import static interfaces.Loguin.name;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author winas
 */
public class RegistroVenta extends javax.swing.JInternalFrame {

    conexion cc = new conexion();
    Connection con;
    PreparedStatement psd;
    ResultSet rs;
    int r = 0;

    static String cedula;

    DefaultTableModel modelo = new DefaultTableModel();
    int cant = 0;
    double precio, tpagar, sumap;

    /**
     * Creates new form RegistroVenta
     */
    public RegistroVenta(String ced) {

        cedula = ced;
        initComponents();
        String numfactura = numFactura();
        inicializaNumFac();
        cargaFecha();
        camposBlock();

    }

    private boolean VerificarCliente() {
        try {

            //SENTENCIA SQL
            String sql = "";
            sql = "SELECT * FROM clientes WHERE CED_CLI= '" + jtxtCedula.getText() + "'";
            //PREPARAR EL STATAMENT
            con = cc.conectar();
            psd = con.prepareStatement(sql);
            rs = psd.executeQuery();

            while (rs.next()) {
                jtxtCedula.setText(rs.getString("CED_CLI"));
                jtxtNombre.setText(rs.getString("NOM_CLI"));
                jtxtApellido.setText(rs.getString("APE_CLI"));

            }
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro el Cliente\n");
        }
        return false;

    }

    private void AgregarPrentasExistentes() {
        int Item = 0;
        double total;

        modelo = (DefaultTableModel) jtbDetalleVenta.getModel();
        Item = Item + 1;
        try {

            //SENTENCIA SQL
            String sql = "";
            sql = "SELECT * FROM prendas WHERE TIP_PRE= '" + jtxtPrenda.getText() + "' AND COL_PRE='" + jtxtColor.getText() + "' AND"
                    + " TAL_PRE='" + jtxtTalla.getText() + "'";
            //PREPARAR EL STATAMENT
            con = cc.conectar();
            psd = con.prepareStatement(sql);
            rs = psd.executeQuery();

            while (rs.next()) {

                int stok = Integer.parseInt(rs.getString("STOCK_PRE"));
                cant = Integer.parseInt(jtxtCantidad.getText());
                precio = Double.parseDouble(rs.getString("VAL_UNI"));
                ArrayList lista = new ArrayList();
                if ((stok >= cant) && (stok > 0)) {

                    total = precio * cant;
                    lista.add(Item);
                    lista.add(rs.getString("ID_PRE"));
                    lista.add(rs.getString("TIP_PRE"));
                    lista.add(rs.getString("COL_PRE"));
                    lista.add(rs.getString("TAL_PRE"));
                    lista.add(precio);
                    lista.add(cant);
                    lista.add(total);
                    Object[] ob = new Object[8];
                    ob[0] = lista.get(0);
                    ob[1] = lista.get(1);
                    ob[2] = lista.get(2);
                    ob[3] = lista.get(3);
                    ob[4] = lista.get(4);
                    ob[5] = lista.get(5);
                    ob[6] = lista.get(6);
                    ob[7] = lista.get(7);
                    modelo.addRow(ob);

                } else {
                    JOptionPane.showMessageDialog(null, "La prenda esta agotada");
                }

            }
            jtbDetalleVenta.setModel(modelo);
            calcularTotalpagar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro la PRENDA\n");
        }

    }

    private void calcularTotalpagar() {
        tpagar = 0;
        for (int i = 0; i < jtbDetalleVenta.getRowCount(); i++) {

            sumap = Double.parseDouble(jtbDetalleVenta.getValueAt(i, 7).toString());
            tpagar = tpagar + sumap;

        }
        jtxtPago.setText(String.valueOf(tpagar));
    }

    private void camposLimpiosDetalleVenta() {
        jtxtColor.setText("");
        jtxtPrenda.setText("");
        jtxtTalla.setText("");
        jtxtCantidad.setText("");
        jtxtCodPre.setText("");
    }

    private void guardarVenta() {

        try {
            String vendedor = cedula;

            String fech = jtxtFecha.getText().toString();

            String cliente = jtxtCedula.getText().toString();

            double total = Double.parseDouble(jtxtPago.getText().toString());

            String sql = "INSERT INTO venta ( CED_USU_VEN, CED_CLI_VEN, FEC_FAC,TOTAL) VALUE(?,?,?,?)";
            con = cc.conectar();
            psd = con.prepareStatement(sql);

            psd.setString(1, vendedor);
            psd.setString(2, cliente);
            psd.setString(3, fech);
            psd.setDouble(4, total);

            r = psd.executeUpdate();
            if (r > 0) {
                JOptionPane.showMessageDialog(null, "Se incerto correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se inserto detalle Venta");
            }
        } catch (Exception e) {
        }
    }

    private void guardarDetalleVenta() {

        try {
            String numfactura = numFactura();
            int idfac = Integer.parseInt(numfactura);

            for (int i = 0; i < jtbDetalleVenta.getRowCount(); i++) {
                String codigoPrenda = String.valueOf(jtbDetalleVenta.getValueAt(i, 1).toString());
                int cantidad = Integer.parseInt(jtbDetalleVenta.getValueAt(i, 6).toString());
                double total = Double.parseDouble(jtbDetalleVenta.getValueAt(i, 7).toString());
                String sql = "INSERT INTO detalle_venta ( ID_PRE_VEN, CANT_PRE, NUM_FAC_PER ,TOTAL) VALUE(?,?,?,?)";

                con = cc.conectar();
                psd = con.prepareStatement(sql);

                psd.setString(1, codigoPrenda);
                psd.setInt(2, cantidad);
                psd.setInt(3, idfac);
                psd.setDouble(4, total);

                r = psd.executeUpdate();

                int totalpre = stockPrenda(codigoPrenda);

                if (r > 0) {
                    JOptionPane.showMessageDialog(null, "Se incerto correctamente");

                    con = cc.conectar();
                    sql = "UPDATE prendas SET STOCK_PRE='" + (totalpre - cantidad) + "' WHERE ID_PRE='" + codigoPrenda + "'";
                    psd = con.prepareStatement(sql);
                    psd.executeUpdate();

                } else {
                    JOptionPane.showMessageDialog(null, "No se inserto detalle Venta");
                }
            }
        } catch (Exception e) {
        }

    }

    private void cargaFecha() {
        Calendar calendario = new GregorianCalendar();
        jtxtFecha.setText("" + calendario.get(Calendar.YEAR) + "-" + calendario.get(Calendar.MONTH) + "1-" + calendario.get(Calendar.DAY_OF_MONTH));
    }

    public String numFactura() {
        String numfa = "";
        String sql = "SELECT MAX(NUM_FAC) FROM venta";
        try {
            con = cc.conectar();
            psd = con.prepareStatement(sql);
            rs = psd.executeQuery();
            while (rs.next()) {
                numfa = rs.getString(1);

            }
        } catch (Exception e) {
        }
        return numfa;
    }

    private int stockPrenda(String codPre) {
        int stock = 0;
        String sql = "SELECT * FROM prendas WHERE ID_PRE='" + codPre + "'";
        try {
            con = cc.conectar();
            psd = con.prepareStatement(sql);
            rs = psd.executeQuery();
            while (rs.next()) {
                stock = Integer.parseInt(rs.getString("STOCK_PRE"));

            }
        } catch (Exception e) {
        }
        return stock;
    }

    private void limpiarCampoCliente() {

        jtxtApellido.setText("");
        jtxtNombre.setText("");
        jtxtCedula.setText("");
        jtxtNumFac.setText("");
        jtxtPago.setText("");
    }

    private void inicializaNumFac() {
        String numfactura = numFactura();
        int idfac = Integer.parseInt(numfactura) + 1;
        jtxtNumFac.setText(String.valueOf(idfac));
    }

    private void eliminarTabla() {
        modelo = (DefaultTableModel) jtbDetalleVenta.getModel();
        for (int i = 0; i < jtbDetalleVenta.getRowCount(); i++) {
            modelo.removeRow(i);
        }
    }

    private void generarFactura() throws JRException {
        try {
            JasperReport reporte;
            String ruta = "C:\\Users\\winas\\ProGIT\\ProyectoTienda\\Tienda\\src\\facturacion\\factura.jrxml";
            reporte = JasperCompileManager.compileReport(ruta);
            JasperPrint jasperPrnt = JasperFillManager.fillReport(reporte, new HashMap<>(), cc.conectar());
            JasperViewer.viewReport(jasperPrnt, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    private void buscarPrendaXcodigo(){
        
        try {

            //SENTENCIA SQL
            String sql = "";
            sql = "SELECT * FROM prendas WHERE ID_PRE= '" + jtxtCodPre.getText() + "'";
            //PREPARAR EL STATAMENT
            con = cc.conectar();
            psd = con.prepareStatement(sql);
            rs = psd.executeQuery();

            while (rs.next()) {
                jtxtCodPre.setText(rs.getString("ID_PRE"));
                jtxtPrenda.setText(rs.getString("TIP_PRE"));
                jtxtColor.setText(rs.getString("COL_PRE"));
                jtxtTalla.setText(rs.getString("TAL_PRE"));
                
            }
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión\n");
        }
    }
    private void camposBlock(){
        jtxtApellido.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtNumFac.setEnabled(false);;
       jtxtFecha.setEnabled(false);
       jtxtPrenda.setEnabled(false);
       jtxtColor.setEnabled(false);
       jtxtTalla.setEnabled(false);
       jtxtPago.setEnabled(false);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDetalleVenta = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jtxtApellido = new javax.swing.JTextField();
        jtxtPrenda = new javax.swing.JTextField();
        jtxtCantidad = new javax.swing.JTextField();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtxtColor = new javax.swing.JTextField();
        jtxtTalla = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxtNumFac = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtPago = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtxtFecha = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jtxtCodPre = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("PUNTO DE VENTA");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Cedula:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Detalles de Venta");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Apellido:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Prenda:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Cantidad:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Nombre:");

        jtbDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "CODIGO", "PRENDA", "COLOR", "TALLA", "VALOR UNITARIO", "CANTIDAD", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(jtbDetalleVenta);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Realizar Venta e Imprimir Factura");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jtxtCedula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtxtCedulaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtxtCedulaMousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Color:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Talla:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("N° Factura:");

        jLabel11.setText("TOTAL A PAGAR");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Fecha:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Prenda");

        jButton4.setText("Buscar Cliente");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("BuscarPrenda");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Codigo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jtxtPago)
                                .addGap(15, 15, 15))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtxtNumFac, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel14)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel4)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel8)))
                                                .addGap(8, 8, 8)))
                                        .addGap(78, 78, 78)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtxtTalla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtPrenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel13))
                                            .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                            .addComponent(jtxtCodPre))))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton4))))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel4)
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jtxtNumFac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addGap(6, 6, 6)
                        .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtCodPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)
                            .addComponent(jLabel14))
                        .addGap(13, 13, 13)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jtxtPrenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jtxtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(23, 23, 23)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtxtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtxtPago)
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtxtCedulaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxtCedulaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaMousePressed

    private void jtxtCedulaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxtCedulaMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jtxtCedulaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        AgregarPrentasExistentes();
        camposLimpiosDetalleVenta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        guardarVenta();
        guardarDetalleVenta();
        try {
            generarFactura();
        } catch (JRException ex) {
            Logger.getLogger(RegistroVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        limpiarCampoCliente();
        inicializaNumFac();

        eliminarTabla();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscarPrendaXcodigo();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         if (VerificarCliente()) {

        } else {
            JOptionPane.showMessageDialog(null, "NO existe el Cliente");
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVenta(cedula).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbDetalleVenta;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtCodPre;
    private javax.swing.JTextField jtxtColor;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtNumFac;
    private javax.swing.JTextField jtxtPago;
    private javax.swing.JTextField jtxtPrenda;
    private javax.swing.JTextField jtxtTalla;
    // End of variables declaration//GEN-END:variables
}
