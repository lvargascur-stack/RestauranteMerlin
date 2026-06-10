package controller.leo;

import model.elizabeth.Mesa;
import dao.leo.MesaDAO;
import java.util.List;

/**
 * Controlador para manejar la lógica de gestión de mesas.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 2.0
 */
public class MesaController {
    
    private MesaDAO mesaDAO;
    
    public MesaController() {
        this.mesaDAO = new MesaDAO();
    }

    /**
     * Obtiene la lista de todas las mesas.
     * @return Lista de objetos Mesa
     */
    public List<Mesa> listarMesas() {
        return mesaDAO.listarMesas();
    }
    
    /**
     * Obtiene la lista de mesas disponibles.
     * @return Lista de objetos Mesa con estado "disponible"
     */
    public List<Mesa> listarMesasDisponibles() {
        return mesaDAO.listarPorEstado("disponible");
    }

    /**
     * Cambia el estado de una mesa.
     * @param idMesa Identificador de la mesa
     * @param nuevoEstado Nuevo estado (disponible/ocupada/reservada)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstadoMesa(int idMesa, String nuevoEstado) {
        System.out.println("=== MesaController ===");
        System.out.println("Actualizando mesa ID: " + idMesa + " a estado: " + nuevoEstado);
        
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            System.out.println("Estado vacío o nulo");
            return false;
        }
        
        boolean resultado = mesaDAO.actualizarEstado(idMesa, nuevoEstado);
        System.out.println("Resultado de actualización: " + resultado);
        return resultado;
    }
    
    /**
     * Obtiene una mesa por su ID.
     * @param idMesa Identificador de la mesa
     * @return Objeto Mesa o null si no existe
     */
    public Mesa obtenerMesaPorId(int idMesa) {
        return mesaDAO.obtenerPorId(idMesa);
    }
}
