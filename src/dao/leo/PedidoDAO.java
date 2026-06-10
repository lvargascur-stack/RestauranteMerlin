package dao.leo;

import model.elizabeth.DetallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PedidoDAO {

    public static boolean registrarPedido(int idCliente, Integer idMesa, int idEmpleado, double total) {
        String sql = "INSERT INTO Pedido (fecha_pedido, estado, total, id_cliente, id_mesa, id_empleado) VALUES (GETDATE(), 'PENDIENTE', ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, total);
            pstmt.setInt(2, idCliente);
            if (idMesa != null) {
                pstmt.setInt(3, idMesa);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.setInt(4, idEmpleado);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static double obtenerPrecioProducto(int idProducto) {
        String sql = "SELECT precio FROM Producto WHERE id_producto = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public static boolean registrarPedidoConDetalles(int idCliente, int idMesa, int idEmpleado, double total, List<DetallePedido> detalles) {
        
        System.out.println("=== DEBUG PEDIDO ===");
        System.out.println("idCliente: " + idCliente);
        System.out.println("idMesa: " + idMesa);
        System.out.println("idEmpleado: " + idEmpleado);
        System.out.println("total: " + total);
        System.out.println("detalles size: " + detalles.size());
        for (DetallePedido d : detalles) {
            System.out.println("  Producto ID: " + d.getIdProducto() + " Cantidad: " + d.getCantidad());
        }
        
        Connection conn = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false);
            
            String sqlPedido = "INSERT INTO Pedido (fecha_pedido, estado, total, id_cliente, id_mesa, id_empleado) VALUES (GETDATE(), 'PENDIENTE', ?, ?, ?, ?)";
            psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setDouble(1, total);
            psPedido.setInt(2, idCliente);
            psPedido.setInt(3, idMesa);
            psPedido.setInt(4, idEmpleado);
            
            int affectedRows = psPedido.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar el pedido");
            }
            
            generatedKeys = psPedido.getGeneratedKeys();
            int idPedido;
            if (generatedKeys.next()) {
                idPedido = generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se obtuvo el ID del pedido");
            }
            
            // INSERT con subtotal incluido (4 signos ?)
            String sqlDetalle = "INSERT INTO DetallePedido (cantidad, id_pedido, id_producto, subtotal) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE Producto SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
            
            psDetalle = conn.prepareStatement(sqlDetalle);
            psStock = conn.prepareStatement(sqlStock);
            
            for (DetallePedido detalle : detalles) {
                // Obtener precio y calcular subtotal
                double precio = obtenerPrecioProducto(detalle.getIdProducto());
                double subtotal = precio * detalle.getCantidad();
                
                System.out.println("Producto ID: " + detalle.getIdProducto() + " Precio: " + precio + " Cantidad: " + detalle.getCantidad() + " Subtotal: " + subtotal);
                
                psDetalle.setInt(1, detalle.getCantidad());
                psDetalle.setInt(2, idPedido);
                psDetalle.setInt(3, detalle.getIdProducto());
                psDetalle.setDouble(4, subtotal);  // ← SUBTOTAL AQUÍ
                psDetalle.addBatch();
                
                psStock.setInt(1, detalle.getCantidad());
                psStock.setInt(2, detalle.getIdProducto());
                psStock.setInt(3, detalle.getCantidad());
                psStock.addBatch();
            }
            
            psDetalle.executeBatch();
            int[] stockResults = psStock.executeBatch();
            
            for (int result : stockResults) {
                if (result == 0) {
                    throw new SQLException("Stock insuficiente para algún producto");
                }
            }
            
            conn.commit();
            System.out.println("=== PEDIDO GUARDADO CON ÉXITO ===");
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("=== ERROR SQL ===");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}