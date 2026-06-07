package controller.leo;

import dao.leo.MesaDAO;
import java.util.List;
import model.elizabeth.Mesa;

/**
 * Controlador para manejar la lógica de gestión de mesas.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class MesaController {

    /**
     * Obtiene la lista de todas las mesas.
     * @return Lista de objetos Mesa
     */
    public static List<Mesa> listarMesas() {
        return MesaDAO.listarMesas();
    }

    /**
     * Cambia el estado de una mesa.
     * @param idMesa Identificador de la mesa
     * @param nuevoEstado Nuevo estado (disponible/ocupada/reservada)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean cambiarEstadoMesa(int idMesa, String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            return false;
        }
        return MesaDAO.cambiarEstadoMesa(idMesa, nuevoEstado);
    }
}
