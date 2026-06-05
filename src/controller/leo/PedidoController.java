package controller.leo;

import dao.leo.PedidoDAO;

/**
 * Controlador para manejar la lógica de gestión de pedidos.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class PedidoController {

    /**
     * Registra un nuevo pedido.
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
}
