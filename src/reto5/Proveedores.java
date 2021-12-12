/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VALERIA BENITEZ
 */
public class Proveedores {
    public void agregar(Proveedor NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "INSERT INTO proveedores (nombre,descripcion) VALUES(?,?)";

        String nombre = NewProducto.getName();
        String description = NewProducto.getDescription();
        

        PreparedStatement datos;
        try {
            datos = conn.prepareStatement(consultaIns);
            datos.setString(1, nombre);
            datos.setString(2, description);
            

            int filasAfectadas = datos.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("insertado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar(Proveedor NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "UPDATE proveedores set nombre = ? , descripcion = ? where id = ? ";

        String nombre = NewProducto.getName();
        String description = NewProducto.getDescription();
        
        int codigo = NewProducto.getCodigo();

        PreparedStatement datos;
        try {
            datos = conn.prepareStatement(consultaIns);

            datos.setString(1, nombre);
            datos.setString(2, description);
            
            datos.setInt(4, codigo);

            int filasAfectadas = datos.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("ACTUALIZADO");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrar(Producto NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "DELETE FROM proveedores WHERE id = ?";
        int cod = NewProducto.getCodigo();

        PreparedStatement datos;
        try {
            datos = conn.prepareStatement(consultaIns);

            datos.setInt(1, cod);

            int filasAfectadas = datos.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Borrado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}

class Proveedor{

int codigo;
String name;
String description;

    public Proveedor(int codigo, String name, String description) {
        this.codigo = codigo;
        this.name = name;
        this.description = description;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
