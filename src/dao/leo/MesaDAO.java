package dao.leo;

import model.elizabeth.Mesa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar el acceso a datos de Mesa.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 2.0
 */
public class MesaDAO {
    
    private Connection conexion;
    
    public MesaDAO() {
        try {
            this.conexion = ConexionBD.getConnection();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la BD de Mesas: " + e.getMessage());
            e.printStackTrace();
            this.conexion = null;
        }
    }

    /**
     * Lista todas las mesas registradas en el sistema.
     * @return Lista de objetos Mesa
     */
    public List<Mesa> listarMesas() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, numero_mesa, capacidad, estado FROM Mesa ORDER BY numero_mesa";
        
        if (conexion == null) {
            System.err.println("No hay conexión a la BD");
            return mesas;
        }
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstadoMesa(rs.getString("estado"));
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar mesas: " + e.getMessage());
            e.printStackTrace();
        }
        return mesas;
    }
    
    /**
     * Lista mesas por estado (disponible, ocupada, reservada).
     * @param estado Estado de la mesa
     * @return Lista de objetos Mesa
     */
    public List<Mesa> listarPorEstado(String estado) {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, numero_mesa, capacidad, estado FROM Mesa WHERE estado = ? ORDER BY numero_mesa";
        
        if (conexion == null) {
            System.err.println("No hay conexión a la BD");
            return mesas;
        }
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, estado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstadoMesa(rs.getString("estado"));
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar mesas por estado: " + e.getMessage());
            e.printStackTrace();
        }
        return mesas;
    }

    /**
     * Cambia el estado de una mesa.
     * @param idMesa Identificador de la mesa
     * @param nuevoEstado Nuevo estado (disponible/ocupada/reservada)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstado(int idMesa, String nuevoEstado) {
        String sql = "UPDATE Mesa SET estado = ? WHERE id_mesa = ?";
        
        System.out.println("=== MesaDAO.actualizarEstado ===");
        System.out.println("SQL: " + sql);
        System.out.println("idMesa: " + idMesa + ", nuevoEstado: " + nuevoEstado);
        
        if (conexion == null) {
            System.err.println("No hay conexión a la BD");
            return false;
        }
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idMesa);
            int filasActualizadas = pstmt.executeUpdate();
            System.out.println("Filas actualizadas: " + filasActualizadas);
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de mesa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene una mesa por su ID.
     * @param idMesa Identificador de la mesa
     * @return Objeto Mesa o null si no existe
     */
    public Mesa obtenerPorId(int idMesa) {
        Mesa mesa = null;
        String sql = "SELECT id_mesa, numero_mesa, capacidad, estado FROM Mesa WHERE id_mesa = ?";
        
        if (conexion == null) {
            System.err.println("No hay conexión a la BD");
            return null;
        }
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idMesa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumeroMesa(rs.getInt("numero_mesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstadoMesa(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mesa por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return mesa;
    }
}
