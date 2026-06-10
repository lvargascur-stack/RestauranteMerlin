/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.jean;

import controller.leo.MesaController;
import model.elizabeth.Mesa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Vista de gestión de mesas con diseño moderno.
 * Muestra todas las mesas con colores según su estado.
 * 
 * @author JEAN CARLOS
 * @version 1.0
 */
public class MesasViewModerno extends JPanel {
    
    private JPanel panelMesas;
    private JButton btnRefrescar;
    private JLabel lblTitulo;
    private MesaController mesaController;
    
    // Colores según estado
    private static final Color COLOR_DISPONIBLE = new Color(40, 180, 100);   // Verde
    private static final Color COLOR_OCUPADA = new Color(200, 60, 60);       // Rojo
    private static final Color COLOR_RESERVADA = new Color(0, 150, 210);     // Azul/Celeste
    private static final Color COLOR_FONDO = new Color(245, 245, 250);
    
    public MesasViewModerno() {
        mesaController = new MesaController();
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initComponents();
        cargarMesas();
    }
    
    private void initComponents() {
        // Panel superior con título y botón refrescar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        lblTitulo = new JLabel("Control de Mesas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(25, 55, 90));
        
        btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setBackground(new Color(0, 150, 210));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnRefrescar.addActionListener(e -> cargarMesas());
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(btnRefrescar, BorderLayout.EAST);
        
        // Panel de mesas (Grid para 12 mesas)
        panelMesas = new JPanel(new GridLayout(3, 4, 20, 20));
        panelMesas.setOpaque(false);
        
        // Panel con leyenda de colores
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelLeyenda.setOpaque(false);
        panelLeyenda.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        panelLeyenda.add(crearLeyenda(COLOR_DISPONIBLE, "Disponible"));
        panelLeyenda.add(crearLeyenda(COLOR_OCUPADA, "Ocupada"));
        panelLeyenda.add(crearLeyenda(COLOR_RESERVADA, "Reservada"));
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelMesas, BorderLayout.CENTER);
        add(panelLeyenda, BorderLayout.SOUTH);
    }
    
    private JPanel crearLeyenda(Color color, String texto) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        
        JLabel lblColor = new JLabel();
        lblColor.setPreferredSize(new Dimension(20, 20));
        lblColor.setBackground(color);
        lblColor.setOpaque(true);
        lblColor.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        panel.add(lblColor);
        panel.add(lblTexto);
        return panel;
    }
    
    private void cargarMesas() {
        panelMesas.removeAll();
        List<Mesa> mesas = mesaController.listarMesas();
        
        System.out.println("=== Cargando mesas ===");
        System.out.println("Total mesas encontradas: " + mesas.size());
        
        if (mesas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay mesas registradas");
            return;
        }
        
        for (Mesa mesa : mesas) {
            System.out.println("Mesa " + mesa.getNumeroMesa() + " - Estado: " + mesa.getEstadoMesa());
            panelMesas.add(crearBotonMesa(mesa));
        }
        
        panelMesas.revalidate();
        panelMesas.repaint();
    }
    
    private JButton crearBotonMesa(Mesa mesa) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(180, 120));
        
        // Color según estado
        Color colorFondo = getColorPorEstado(mesa.getEstadoMesa());
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Número de mesa grande
        JLabel lblNumero = new JLabel(String.valueOf(mesa.getNumeroMesa()), SwingConstants.CENTER);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblNumero.setForeground(Color.WHITE);
        
        // Capacidad
        JLabel lblCapacidad = new JLabel("Cap: " + mesa.getCapacidad() + " pers.", SwingConstants.CENTER);
        lblCapacidad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCapacidad.setForeground(Color.WHITE);
        
        // Estado
        String estadoTexto = "";
        switch (mesa.getEstadoMesa()) {
            case "disponible": estadoTexto = "DISPONIBLE"; break;
            case "ocupada": estadoTexto = "OCUPADA"; break;
            case "reservada": estadoTexto = "RESERVADA"; break;
            default: estadoTexto = mesa.getEstadoMesa().toUpperCase();
        }
        JLabel lblEstado = new JLabel(estadoTexto, SwingConstants.CENTER);
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(0, 0, 0, 80));
        
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setOpaque(false);
        panelInfo.add(lblCapacidad, BorderLayout.NORTH);
        panelInfo.add(lblEstado, BorderLayout.SOUTH);
        
        btn.add(lblNumero, BorderLayout.CENTER);
        btn.add(panelInfo, BorderLayout.SOUTH);
        
        // Acción al hacer clic
        btn.addActionListener((ActionEvent e) -> {
            mostrarOpcionesMesa(mesa);
        });
        
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(colorFondo);
            }
        });
        
        return btn;
    }
    
    private Color getColorPorEstado(String estado) {
        switch (estado) {
            case "disponible": return COLOR_DISPONIBLE;
            case "ocupada": return COLOR_OCUPADA;
            case "reservada": return COLOR_RESERVADA;
            default: return Color.GRAY;
        }
    }
    
    private void mostrarOpcionesMesa(Mesa mesa) {
        String[] opciones;
        String titulo = "Mesa " + mesa.getNumeroMesa();
        String mensaje = "Estado actual: " + mesa.getEstadoMesa() + "\nCapacidad: " + mesa.getCapacidad() + " personas\n\n¿Qué desea hacer?";
        
        // Según el estado actual, mostrar opciones
        if (mesa.getEstadoMesa().equals("ocupada")) {
            opciones = new String[]{"Liberar mesa", "Ver pedidos", "Cancelar"};
        } else if (mesa.getEstadoMesa().equals("reservada")) {
            opciones = new String[]{"Confirmar llegada", "Cancelar reserva", "Ver reserva", "Cancelar"};
        } else {
            opciones = new String[]{"Ocupar mesa", "Reservar mesa", "Cancelar"};
        }
        
        int opcion = JOptionPane.showOptionDialog(
            this,
            mensaje,
            titulo,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[opciones.length - 1]
        );
        
        if (opcion == -1 || opcion == opciones.length - 1) return;
        
        switch (opciones[opcion]) {
            case "Ocupar mesa":
                cambiarEstadoMesa(mesa, "ocupada");
                break;
            case "Reservar mesa":
                cambiarEstadoMesa(mesa, "reservada");
                break;
            case "Liberar mesa":
                cambiarEstadoMesa(mesa, "disponible");
                break;
            case "Confirmar llegada":
                cambiarEstadoMesa(mesa, "ocupada");
                break;
            case "Cancelar reserva":
                cambiarEstadoMesa(mesa, "disponible");
                break;
            case "Ver pedidos":
                JOptionPane.showMessageDialog(this, "Función en desarrollo");
                break;
            case "Ver reserva":
                JOptionPane.showMessageDialog(this, "Función en desarrollo");
                break;
        }
    }
    
    private void cambiarEstadoMesa(Mesa mesa, String nuevoEstado) {
        System.out.println("=== Cambiando estado de mesa ===");
        System.out.println("Mesa ID: " + mesa.getIdMesa());
        System.out.println("Nuevo estado: " + nuevoEstado);
        
        boolean exito = mesaController.actualizarEstadoMesa(mesa.getIdMesa(), nuevoEstado);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "✅ Mesa " + mesa.getNumeroMesa() + " ahora está " + nuevoEstado);
            cargarMesas(); // Recargar todas las mesas
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al cambiar el estado de la mesa");
        }
    }
    
    public void recargarDatos() {
        cargarMesas();
    }
}