package dao.leo;

import model.elizabeth.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar el acceso a datos de Producto.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class ProductoDAO {
    
    private Connection conexion;
    
public ProductoDAO() {
    try {
        this.conexion = ConexionBD.getConnection();
    } catch (SQLException e) {
        e.printStackTrace();
        this.conexion = null;
    }
}
    
    /**
     * Lista todos los productos.
     * @return Lista de productos
     */
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock, id_categoria FROM Producto";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
    /**
     * Obtiene un producto por su ID.
     * @param idProducto Identificador del producto
     * @return Producto o null
     */
    public Producto obtenerPorId(int idProducto) {
        Producto p = null;
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock, id_categoria FROM Producto WHERE id_producto = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_categoria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}