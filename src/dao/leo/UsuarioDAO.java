package dao.leo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para manejar el acceso a datos de Usuario.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class UsuarioDAO {

    /**
     * Valida las credenciales de un usuario en la base de datos.
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña del usuario
     * @return true si las credenciales son correctas, false en caso contrario
     */
    public static boolean validarLogin(String usuario, String contrasena) {
        String sql = "SELECT * FROM Usuario WHERE nombreUsuario = ? AND contraseñaUsuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
