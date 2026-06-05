package dao.leo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para manejar el acceso a datos de Pedido.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Registra un nuevo pedido en la base de datos.
     * @param idCliente Identificador del cliente
     * @param idMesa Identificador de la mesa (puede ser null si es delivery)
     * @param idEmpleado Identificador del empleado que registra el pedido
     * @param total Monto total del pedido
     * @return true si se registró correctamente, false en caso contrario
     */
    public static boolean registrarPedido(int idCliente, Integer idMesa, int idEmpleado, double total) {
        String sql = "INSERT INTO Pedido (fechaPedido, estadoPedido, totalPedido, idCliente, idMesa, idEmpleado) VALUES (GETDATE(), 'pendiente', ?, ?, ?, ?)";
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
}
