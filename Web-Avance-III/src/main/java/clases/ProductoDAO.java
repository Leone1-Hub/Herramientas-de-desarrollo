
package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public static Producto obtenerPorNombre(String nombre) {
        Producto prod = null;
        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT idproducto, nombre, descripcion, marca, categoria, precio, stock FROM productos WHERE LOWER(REPLACE(nombre, 'ú', 'u')) = LOWER(REPLACE(?, 'ú', 'u'))";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prod = new Producto(
                    rs.getInt("idproducto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("marca"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    0, // cantidad la agregas luego
                    0  // subtotal lo calculas luego
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prod;
    }
        public boolean actualizarStock(int idProducto, int nuevoStock) {
        boolean actualizado = false;
        try (Connection con = ConexionBD.getConexion()) {
            String sql = "UPDATE productos SET stock = ? WHERE idproducto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            int filas = ps.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actualizado;
    }
        
       public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT idproducto, nombre, descripcion, marca, categoria, precio, stock FROM productos";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("idproducto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("marca"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    0, 0 // cantidad y subtotal se usan para el carrito
                );
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

