/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class conexion {
    
    Connection connect=null;
    
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
            //JOptionPane.showMessageDialog(null,"Estas conectado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error"+ ex);
        }
        return connect;
    }
}
