package controller.leo;

import dao.leo.ProductoDAO;
import model.elizabeth.Producto;
import java.util.List;

/**
 * Controlador para manejar la lógica de gestión de productos.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class ProductoController {
    
    private ProductoDAO productoDAO;
    
    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }
    
    /**
     * Lista todos los productos.
     * @return Lista de productos
     */
    public List<Producto> listarProductos() {
        return productoDAO.listarTodos();
    }
    
    /**
     * Obtiene un producto por su ID.
     * @param idProducto Identificador del producto
     * @return Producto o null si no existe
     */
    public Producto obtenerProductoPorId(int idProducto) {
        return productoDAO.obtenerPorId(idProducto);
    }
}