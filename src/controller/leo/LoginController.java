package controller.leo;

import dao.leo.UsuarioDAO;

/**
 * Controlador para manejar la lógica de autenticación.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class LoginController {

    /**
     * Valida las credenciales del usuario.
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña del usuario
     * @return true si las credenciales son correctas, false en caso contrario
     */
    public static boolean validarCredenciales(String usuario, String contrasena) {
        if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        return UsuarioDAO.validarLogin(usuario, contrasena);
    }
}
