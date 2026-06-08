package dao.leo;

import model.elizabeth.Mesa;  // ✅ IMPORTANTE: Agregado el import de la clase Mesa
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar el acceso a datos de Mesa.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class MesaDAO {

    /**
     * Lista todas las mesas registradas en el sistema.
     * @return Lista de objetos Mesa
     */
    public static List<Mesa> listarMesas() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM Mesa";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidad(rs.getInt("capacidadMesa"));      // ✅ CORREGIDO: cambiado setCapacidadMesa por setCapacidad
                mesa.setEstadoMesa(rs.getString("estadoMesa"));     // ✅ ESTÁ BIEN: setEstadoMesa existe en el modelo
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesas;
    }

    /**
     * Cambia el estado de una mesa (disponible/ocupada/reservada).
     * @param idMesa Identificador de la mesa
     * @param nuevoEstado Nuevo estado de la mesa
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean cambiarEstadoMesa(int idMesa, String nuevoEstado) {
        String sql = "UPDATE Mesa SET estadoMesa = ? WHERE idMesa = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idMesa);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
