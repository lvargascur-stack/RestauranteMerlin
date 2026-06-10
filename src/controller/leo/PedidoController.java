package controller.leo;

import dao.leo.PedidoDAO;
import model.elizabeth.DetallePedido;
import java.util.List;
import model.elizabeth.DetallePedido;

/**
 * Controlador para manejar la lógica de gestión de pedidos.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 2.0
 */
public class PedidoController {

    /**
     * Registra un nuevo pedido (solo el pedido, sin detalles).
     * @param idCliente Identificador del cliente
     * @param idMesa Identificador de la mesa (puede ser null si es delivery)
     * @param idEmpleado Identificador del empleado que registra el pedido
     * @param total Monto total del pedido
     * @return true si se registró correctamente, false en caso contrario
     */
    public static boolean registrarPedido(int idCliente, Integer idMesa, int idEmpleado, double total) {
        if (idCliente <= 0 || idEmpleado <= 0 || total <= 0) {
            return false;
        }
        return PedidoDAO.registrarPedido(idCliente, idMesa, idEmpleado, total);
    }
    
    /**
     * Registra un nuevo pedido con sus detalles y descuenta stock.
     * @param idCliente Identificador del cliente
     * @param idMesa Identificador de la mesa
     * @param idEmpleado Identificador del empleado
     * @param total Monto total del pedido
     * @param detalles Lista de detalles del pedido (productos y cantidades)
     * @return true si se registró correctamente, false en caso contrario
     */
    public static boolean registrarPedidoConDetalles(int idCliente, int idMesa, int idEmpleado, double total, List<DetallePedido> detalles) {
        if (idCliente <= 0 || idEmpleado <= 0 || total <= 0 || detalles == null || detalles.isEmpty()) {
            return false;
        }
        return PedidoDAO.registrarPedidoConDetalles(idCliente, idMesa, idEmpleado, total, detalles);
    }
}