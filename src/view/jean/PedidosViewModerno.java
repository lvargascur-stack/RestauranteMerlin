/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.jean;

import controller.leo.MesaController;
import controller.leo.PedidoController;
import controller.leo.ProductoController;
import model.elizabeth.DetallePedido;
import model.elizabeth.Mesa;
import model.elizabeth.Producto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista de gestión de pedidos con diseño moderno.
 * Permite seleccionar mesa, agregar productos, editar cantidades y enviar a cocina.
 * 
 * @author JEAN CARLOS
 * @version 2.0
 */
public class PedidosViewModerno extends JPanel {
    
    // Componentes principales
    private JPanel panelContenido;
    private JComboBox<Mesa> cmbMesa;
    private JTable tablaPedido;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JButton btnAgregarProducto;
    private JButton btnEnviarPedido;
    
    // Controladores
    private ProductoController productoController;
    private PedidoController pedidoController;
    private MesaController mesaController;
    
    // Lista de detalles del pedido actual
    private List<ItemPedido> items;
    private double totalActual;
    
    // Clase interna para manejar items del pedido
    private class ItemPedido {
        int idProducto;
        String nombre;
        double precio;
        int cantidad;
        
        ItemPedido(int idProducto, String nombre, double precio, int cantidad) {
            this.idProducto = idProducto;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
        }
        
        double getSubtotal() {
            return precio * cantidad;
        }
    }
    
    public PedidosViewModerno() {
        productoController = new ProductoController();
        pedidoController = new PedidoController();
        mesaController = new MesaController();
        items = new ArrayList<>();
        totalActual = 0.0;
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        initComponents();
        cargarMesas();
        actualizarTotal();
    }
    
    private void initComponents() {
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(245, 245, 250));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));
        
        // Panel superior: selector de mesa y botón agregar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        
        JPanel panelMesa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMesa.setOpaque(false);
        panelMesa.add(new JLabel("Mesa:"));
        
        cmbMesa = new JComboBox<>();
        cmbMesa.setPreferredSize(new Dimension(250, 35));
        // RENDERIZADOR PARA MOSTRAR SOLO EL NÚMERO DE MESA
        cmbMesa.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Mesa) {
                    Mesa m = (Mesa) value;
                    value = "Mesa " + m.getNumeroMesa();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        cmbMesa.addActionListener(e -> limpiarPedido());
        panelMesa.add(cmbMesa);
        
        btnAgregarProducto = new JButton("+ Agregar Producto");
        btnAgregarProducto.setBackground(new Color(0, 150, 210));
        btnAgregarProducto.setForeground(Color.WHITE);
        btnAgregarProducto.setFocusPainted(false);
        btnAgregarProducto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarProducto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAgregarProducto.addActionListener(e -> mostrarDialogoAgregarProducto());
        
        panelSuperior.add(panelMesa, BorderLayout.WEST);
        panelSuperior.add(btnAgregarProducto, BorderLayout.EAST);
        
        // Tabla de pedido
        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal", "Acciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 4;
            }
        };
        
        tablaPedido = new JTable(modeloTabla);
        tablaPedido.setRowHeight(40);
        tablaPedido.getColumnModel().getColumn(0).setPreferredWidth(250);
        tablaPedido.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaPedido.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaPedido.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaPedido.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        // Renderizador para columna Cantidad (Spinner)
        tablaPedido.getColumnModel().getColumn(1).setCellRenderer(new SpinnerRenderer());
        tablaPedido.getColumnModel().getColumn(1).setCellEditor(new SpinnerEditor());
        
        // Renderizador para columna Acciones (botones)
        tablaPedido.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tablaPedido.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        // Centrar texto en celdas numéricas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tablaPedido.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tablaPedido.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        JScrollPane scrollTabla = new JScrollPane(tablaPedido);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Detalle del Pedido", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));
        
        // Panel inferior: total y botón enviar
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.setOpaque(false);
        lblTotal = new JLabel("Total: S/ 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setForeground(new Color(0, 120, 0));
        panelTotal.add(lblTotal);
        
        btnEnviarPedido = new JButton("📤 Enviar pedido a cocina");
        btnEnviarPedido.setBackground(new Color(40, 180, 100));
        btnEnviarPedido.setForeground(Color.WHITE);
        btnEnviarPedido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviarPedido.setFocusPainted(false);
        btnEnviarPedido.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnEnviarPedido.addActionListener(e -> enviarPedidoACocina());
        
        panelInferior.add(panelTotal, BorderLayout.WEST);
        panelInferior.add(btnEnviarPedido, BorderLayout.EAST);
        
        // Armar panel central
        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(scrollTabla, BorderLayout.CENTER);
        panelContenido.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelContenido, BorderLayout.CENTER);
    }
    
    private void cargarMesas() {
        try {
            List<Mesa> mesas = mesaController.listarMesasDisponibles();
            cmbMesa.removeAllItems();
            for (Mesa m : mesas) {
                cmbMesa.addItem(m);
            }
            if (mesas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay mesas disponibles");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar mesas: " + e.getMessage());
        }
    }
    
    private void mostrarDialogoAgregarProducto() {
        if (cmbMesa.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Primero seleccione una mesa");
            return;
        }
        
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Agregar Producto", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        List<Producto> productos = productoController.listarProductos();
        JComboBox<Producto> cmbProducto = new JComboBox<>(productos.toArray(new Producto[0]));
        cmbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Producto) {
                    Producto p = (Producto) value;
                    value = p.getNombreProducto() + " - S/ " + p.getPrecio();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        panelForm.add(cmbProducto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        panelForm.add(spinnerCantidad, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panelForm, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        btnAgregar.addActionListener(e -> {
            try {
                Producto p = (Producto) cmbProducto.getSelectedItem();
                int cantidad = (int) spinnerCantidad.getValue();
                
                if (cantidad > p.getStock()) {
                    JOptionPane.showMessageDialog(dialog, "Stock insuficiente. Solo hay " + p.getStock());
                    return;
                }
                
                agregarProductoATabla(p, cantidad);
                dialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }
    
    private void agregarProductoATabla(Producto producto, int cantidad) {
        // Verificar si ya existe en la tabla
        for (int i = 0; i < items.size(); i++) {
            ItemPedido item = items.get(i);
            if (item.nombre.equals(producto.getNombreProducto())) {
                // Actualizar cantidad
                item.cantidad += cantidad;
                modeloTabla.setValueAt(item.cantidad, i, 1);
                modeloTabla.setValueAt(item.getSubtotal(), i, 3);
                actualizarTotal();
                return;
            }
        }
        
        // Agregar nuevo producto
        ItemPedido nuevoItem = new ItemPedido(
            producto.getIdProducto(),
            producto.getNombreProducto(),
            producto.getPrecio(),
            cantidad
        );
        items.add(nuevoItem);
        
        modeloTabla.addRow(new Object[]{
            nuevoItem.nombre,
            nuevoItem.cantidad,
            nuevoItem.precio,
            nuevoItem.getSubtotal(),
            "Borrar"
        });
        
        actualizarTotal();
    }
    
    private void actualizarTotal() {
        totalActual = 0.0;
        for (ItemPedido item : items) {
            totalActual += item.getSubtotal();
        }
        lblTotal.setText(String.format("Total: S/ %.2f", totalActual));
    }
    
    private void eliminarProductoDeTabla(int fila) {
        items.remove(fila);
        modeloTabla.removeRow(fila);
        actualizarTotal();
    }
    
    private void actualizarCantidadEnFila(int fila, int nuevaCantidad) {
        if (fila >= 0 && fila < items.size()) {
            ItemPedido item = items.get(fila);
            item.cantidad = nuevaCantidad;
            modeloTabla.setValueAt(item.cantidad, fila, 1);
            modeloTabla.setValueAt(item.getSubtotal(), fila, 3);
            actualizarTotal();
        }
    }
    
    private void enviarPedidoACocina() {
        try {
            System.out.println("=== INICIO enviarPedidoACocina ===");
            
            if (cmbMesa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una mesa");
                return;
            }
            
            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto al pedido");
                return;
            }
            
            Mesa mesaSeleccionada = (Mesa) cmbMesa.getSelectedItem();
            System.out.println("Mesa seleccionada ID: " + mesaSeleccionada.getIdMesa());
            
            // Convertir items a lista de DetallePedido
            List<DetallePedido> detalles = new ArrayList<>();
            for (ItemPedido item : items) {
                System.out.println("Item: " + item.nombre + " ID:" + item.idProducto + " Cant:" + item.cantidad);
                DetallePedido detalle = new DetallePedido();
                detalle.setIdProducto(item.idProducto);
                detalle.setCantidad(item.cantidad);
                detalles.add(detalle);
            }
            
            System.out.println("Llamando a registrarPedidoConDetalles...");
            boolean exito = PedidoController.registrarPedidoConDetalles(1, mesaSeleccionada.getIdMesa(), 1, totalActual, detalles);
            System.out.println("Resultado: " + exito);
            
            if (exito) {
                mesaController.actualizarEstadoMesa(mesaSeleccionada.getIdMesa(), "ocupada");
                JOptionPane.showMessageDialog(this, "✅ Pedido enviado a cocina correctamente");
                limpiarPedido();
                cargarMesas();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al enviar el pedido");
            }
        } catch (Exception e) {
            System.err.println("=== EXCEPCIÓN ===");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void limpiarPedido() {
        modeloTabla.setRowCount(0);
        items.clear();
        totalActual = 0.0;
        actualizarTotal();
    }
    
    public void recargarDatos() {
        cargarMesas();
        limpiarPedido();
    }
    
    // ===== CLASES INTERNAS PARA RENDERIZAR SPINNER Y BOTONES =====
    
    class SpinnerRenderer extends JPanel implements TableCellRenderer {
        private JSpinner spinner;
        
        public SpinnerRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
            spinner.setPreferredSize(new Dimension(70, 30));
            add(spinner);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            spinner.setValue(value != null ? value : 1);
            return this;
        }
    }
    
    class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private JSpinner spinner;
        private int currentRow;
        
        public SpinnerEditor() {
            spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
            spinner.addChangeListener(e -> {
                int nuevaCantidad = (int) spinner.getValue();
                actualizarCantidadEnFila(currentRow, nuevaCantidad);
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            spinner.setValue(value != null ? value : 1);
            return spinner;
        }
        
        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }
    
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton button;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            button = new JButton("Borrar");
            button.setBackground(new Color(200, 60, 60));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            add(button);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Borrar");
            button.setBackground(new Color(200, 60, 60));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                eliminarProductoDeTabla(currentRow);
                fireEditingStopped();
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "Borrar";
        }
    }
}