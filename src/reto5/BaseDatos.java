package reto5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VALERIA BENITEZ
 */
//
public class BaseDatos {

    public static Connection conexion() {
        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tienda_java", "root", "");

            return conn;
        } catch (SQLException ex) {

            System.out.println("Error en la conexion local " + ex);
        }
        return null;

    }

}
