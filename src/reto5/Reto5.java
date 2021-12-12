/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VALERIA BENITEZ
 */
class BaseDatosProducto {

    public void agregar(Producto NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "INSERT INTO Productos (Nombre,Precio,Inventario,cod_prod,porcentaje,precio_venta) VALUES(?,?,?,?,?,?)";
        String nombre = NewProducto.getNombre();
        
        double precio = NewProducto.getPrecio();
        int inventario = (int) NewProducto.getInventario();
        String cod_prod = NewProducto.getCod_prod();
        double porcentaje =NewProducto.getPorcentaje();
        
        double precio_venta = NewProducto.getPrecio_venta();
        
        
        
        
        PreparedStatement datos;
        try {
            
            double prodV = precio / porcentaje;
            //precio_venta =precio + prodV;
            System.out.println(prodV);
            datos = conn.prepareStatement(consultaIns);
            datos.setString(1, nombre);
            datos.setDouble(2, precio);
            datos.setInt(3, inventario);
            datos.setString(4, cod_prod);
            datos.setDouble(5, porcentaje);
            datos.setDouble(6, prodV);

            int filasAfectadas = datos.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("insertado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar(Producto NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "UPDATE Productos set  Nombre = ? , Precio = ?, Inventario = ? ,cod_prod=? , porcentaje = ? , precio_venta = ? where codigo = ? ";

        String nombre = NewProducto.getNombre();
        double precio = NewProducto.getPrecio();
        int inventario = (int) NewProducto.getInventario();
        int codigo = NewProducto.getCodigo();
        String cod_prod = NewProducto.getCod_prod();
         double porcentaje = NewProducto.getPorcentaje();
        double precio_venta = NewProducto.getPrecio_venta();
        PreparedStatement datos;
        try {
            
            double prodV = (precio /  porcentaje);
            //double precio_venta_prod =precio + prodV;
            System.out.println(prodV);
            
            datos = conn.prepareStatement(consultaIns);

            datos.setString(1, nombre);
            datos.setDouble(2, precio);
            datos.setInt(3, inventario);
            
            datos.setString(4, cod_prod);
            datos.setDouble(5, porcentaje);
            datos.setDouble(6, prodV);
            datos.setInt(7, codigo);
            
            
            int filasAfectadas = datos.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("ACTUALIZADO");
            }
             String consultaInss = "Insert into prod_agregados (nombre , cantidad ,fecha)  values ("+nombre+") where codigo = ? ";
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrar(Producto NewProducto) {

        Connection conn = BaseDatos.conexion();
        String consultaIns = "DELETE FROM Productos WHERE codigo = ?";
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
// funcion para verificar si un producto existe
//    public boolean verificarExistencia(Producto NewProducto) {
//        return listaProductos.containsKey(NewProducto.getCodigo());
//    }
    public String[] generarInforme() {

        String a = obtenerMayor();
        String b = obtenerMenor();
        String c = promedio();
        String d = inventario();

        String[] valores = {a, b, c, d};

        return valores;
    }

    public String obtenerMayor() {

        Connection conn = BaseDatos.conexion();

        String consulta = "SELECT max(Precio) as mayorNom FROM Productos  ";

        Statement result;
        try {
            result = conn.createStatement();
            ResultSet exec = result.executeQuery(consulta);
            if (exec.next()) {
                String nomMay = exec.getString("mayorNom");
                String consultados = "SELECT Nombre FROM Productos where Precio = '" + nomMay + "'";
                Statement resultados = conn.createStatement();
                ResultSet execu = result.executeQuery(consultados);
                if (execu.next()) {
                    String nomMayor = execu.getString("Nombre");
                    return nomMayor;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public String obtenerMenor() {

        Connection conn = BaseDatos.conexion();

        String consulta = "SELECT min(Precio) AS mini FROM Productos  ";

        Statement result;
        try {
            result = conn.createStatement();
            ResultSet exec = result.executeQuery(consulta);
            if (exec.next()) {
                String nomMin = exec.getString("mini");
                //return nomMay;
                String consultados = "SELECT Nombre FROM Productos where Precio = '" + nomMin + "'";
                Statement resultados = conn.createStatement();
                ResultSet execu = result.executeQuery(consultados);
                if (execu.next()) {
                    String nomMenor = execu.getString("Nombre");
                    return nomMenor;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public Producto precioMayor(HashMap<Integer, Producto> diccionario) {
        Producto mayor = diccionario.values().iterator().next();
        for (Producto prod : diccionario.values()) {
            if (prod.getPrecio() > mayor.getPrecio()) {
                mayor = prod;
            }
        }
        return mayor;
    }

//esta funcion genera los tres productos mas costosos
//    public String[] tresMayores() {
//
//        HashMap<Integer, Producto> dupDic = new HashMap<Integer, Producto>(this.listaProductos);
//        String nombreMayor[] = new String[3];
//
//        for (int i = 0; i < 3; i++) {
//            Producto nombreMay = precioMayor(dupDic);
//            int clave = nombreMay.getCodigo();
//            nombreMayor[i] = nombreMay.getNombre();
//            dupDic.remove(clave);
//        }
//        return nombreMayor;
//
//    }
    public Producto obteneCod(String entradaNom) {
        try {
            Connection conn = BaseDatos.conexion();
            PreparedStatement datos = conn.prepareStatement(
                    "select * from Productos where Nombre = '" + entradaNom + "'");
            ResultSet exec = datos.executeQuery();
            if (exec.next()) {

            }
        } catch (Exception e) {
        }

        return null;
    }

    public String promedio() {

        Connection conn = BaseDatos.conexion();

        String consulta = "SELECT AVG(Precio) as promedio FROM Productos";

        Statement result;
        try {
            result = conn.createStatement();
            ResultSet exec = result.executeQuery(consulta);
            if (exec.next()) {
                String nomMay = exec.getString("promedio");
                return nomMay;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public String inventario() {

        Connection conn = BaseDatos.conexion();

        String consulta = "SELECT SUM(precio_venta * Inventario) as total FROM Productos ";

        Statement result;
        try {
            result = conn.createStatement();
            ResultSet exec = result.executeQuery(consulta);
            if (exec.next()) {
                String nomMay = exec.getString("total");
                return nomMay;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

}

class Producto {

    int codigo;
    String nombre;
    double precio;
    double inventario;
    String cod_prod;

   
    double porcentaje;
    double precio_venta;
public Producto(int codigo, String nombre, double precio, int inventario, String cod_prod ,double porcentaje, double precio_venta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
        this.cod_prod = cod_prod;
        this.porcentaje = porcentaje;
        this.precio_venta = precio_venta;
    }
    public String getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }
     public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(int precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public double getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

}



public class Reto5 {

    /**
     * Este debe ser el único objeto de tipo Scanner en el código
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Este método es usado para solicitar datos al usuario
     *
     * @return Lectura de la siguiente linea del teclado
     */
    public String read() {
        return this.scanner.nextLine();
    }

    /**
     * método principal
     */
    public void run() {
        tienda ini = new tienda();
        ini.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//select nombre, inventario from productos where precio >= 9000 order by nombre asc;
//
//select avg(precios) as promedio from productos;
//
//select nombre, precios from productos where nombre like ('A%') OR ('P%') order by nombre asc;
//
//select count(*) as total from productos where precio >=3000 and precio<=10000;
//select sum(precio * cantidad) as total_inventario from productos;
inicio ini = new inicio();
        ini.setVisible(true);
    }

}
