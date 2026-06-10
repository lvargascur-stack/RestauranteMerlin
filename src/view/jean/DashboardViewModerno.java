/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.jean;

import controller.leo.PedidoController;
import controller.leo.MesaController;
import javax.swing.*;
import java.awt.*;

/**
 * Dashboard principal con menú lateral y área de contenido.
 * Diseño moderno adaptado del Figma.
 * 
 * @author JEAN CARLOS
 * @version 2.0
 */
public class DashboardViewModerno extends JFrame {
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private JLabel lblLogo;
    private JButton btnDashboard, btnPedidos, btnMesas, btnReservas, btnReportes, btnCerrarSesion;
    private String vistaActiva = "Dashboard";
    
    // Referencias a las vistas
    private PedidosViewModerno pedidosView;
    private MesasViewModerno mesasView;

    public DashboardViewModerno() {
        setTitle("Restaurante El Merlín");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== MENÚ LATERAL (IZQUIERDA) =====
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(25, 55, 90));
        panelMenu.setPreferredSize(new Dimension(250, getHeight()));
        panelMenu.setLayout(new BorderLayout());

        // Panel superior (logo)
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(25, 55, 90));
        panelTop.setLayout(new BorderLayout());
        
        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(200, 120));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
            Image img = icon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
            lblLogo.setText("");
        } catch (Exception e) {
            lblLogo.setText("EL MERLÍN");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblLogo.setForeground(Color.WHITE);
        }
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelTop.add(lblLogo, BorderLayout.CENTER);
        panelMenu.add(panelTop, BorderLayout.NORTH);

        // Panel central (botones) con centrado vertical y horizontal
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(25, 55, 90));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Botones del menú
        btnDashboard = crearBotonMenu("Dashboard");
        btnPedidos = crearBotonMenu("Pedidos");
        btnMesas = crearBotonMenu("Mesas");
        btnReservas = crearBotonMenu("Reservas");
        btnReportes = crearBotonMenu("Reportes");
        
        panelCentro.add(btnDashboard, gbc);
        gbc.gridy++;
        panelCentro.add(btnPedidos, gbc);
        gbc.gridy++;
        panelCentro.add(btnMesas, gbc);
        gbc.gridy++;
        panelCentro.add(btnReservas, gbc);
        gbc.gridy++;
        panelCentro.add(btnReportes, gbc);
        
        panelMenu.add(panelCentro, BorderLayout.CENTER);

        // Panel inferior (cerrar sesión) centrado
        JPanel panelBottom = new JPanel(new GridBagLayout());
        panelBottom.setBackground(new Color(25, 55, 90));
        btnCerrarSesion = crearBotonCerrarSesion();
        panelBottom.add(btnCerrarSesion);
        panelMenu.add(panelBottom, BorderLayout.SOUTH);

        add(panelMenu, BorderLayout.WEST);

        // ===== ÁREA DE CONTENIDO =====
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(new Color(245, 245, 250));

        // Inicializar vistas
        pedidosView = new PedidosViewModerno();
        mesasView = new MesasViewModerno();
        
        panelContenido.add(crearPanelDashboard(), "Dashboard");
        panelContenido.add(pedidosView, "Pedidos");
        panelContenido.add(mesasView, "Mesas");
        panelContenido.add(crearPanelReservas(), "Reservas");
        panelContenido.add(crearPanelReportes(), "Reportes");

        add(panelContenido, BorderLayout.CENTER);

        // Establecer la vista activa y resaltar el botón
        cambiarVista("Dashboard");
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (vistaActiva.equals(texto)) {
                    g2.setColor(new Color(0, 150, 210));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(220, 220, 220));
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(vistaActiva.equals(texto) ? Color.WHITE : new Color(25, 55, 90));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int anchoTexto = fm.stringWidth(getText());
                int altoTexto = fm.getAscent();
                g2.drawString(getText(), (getWidth() - anchoTexto) / 2, (getHeight() + altoTexto) / 2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(25, 55, 90));
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setMaximumSize(new Dimension(180, 45));
        btn.addActionListener(e -> cambiarVista(texto));
        return btn;
    }

    private JButton crearBotonCerrarSesion() {
        JButton btn = new JButton("Cerrar Sesion") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(180, 50, 50) : new Color(200, 60, 60));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int anchoTexto = fm.stringWidth(getText());
                int altoTexto = fm.getAscent();
                g2.drawString(getText(), (getWidth() - anchoTexto) / 2, (getHeight() + altoTexto) / 2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginViewModerno().setVisible(true);
                dispose();
            }
        });
        return btn;
    }

    private void cambiarVista(String vista) {
        vistaActiva = vista;
        cardLayout.show(panelContenido, vista);
        
        btnDashboard.repaint();
        btnPedidos.repaint();
        btnMesas.repaint();
        btnReservas.repaint();
        btnReportes.repaint();
        
        // Recargar datos según la vista seleccionada
        if (vista.equals("Pedidos") && pedidosView != null) {
            pedidosView.recargarDatos();
        } else if (vista.equals("Mesas") && mesasView != null) {
            mesasView.recargarDatos();
        }
    }

    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));

        JPanel panelTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
        panelTarjetas.setOpaque(false);
        panelTarjetas.add(crearTarjetaMetrica("Ventas hoy", "S/ 1,250", new Color(255, 140, 0), 220, 110));
        panelTarjetas.add(crearTarjetaMetrica("Mesas ocupadas", "4/12", new Color(30, 120, 200), 220, 110));
        panelTarjetas.add(crearTarjetaMetrica("Pedidos activos", "8", new Color(40, 180, 100), 220, 110));
        panel.add(panelTarjetas, BorderLayout.NORTH);

        JPanel panelGrafico = new JPanel(new BorderLayout());
        panelGrafico.setBackground(Color.WHITE);
        panelGrafico.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 230)),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        JLabel lblGrafico = new JLabel("📊 Aquí iría el gráfico de ventas", SwingConstants.CENTER);
        lblGrafico.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblGrafico.setForeground(Color.GRAY);
        panelGrafico.add(lblGrafico, BorderLayout.CENTER);
        panel.add(panelGrafico, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTarjetaMetrica(String titulo, String valor, Color color, int ancho, int alto) {
        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(ancho, alto));
        tarjeta.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValor.setForeground(Color.WHITE);
        lblValor.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(lblValor, BorderLayout.CENTER);

        return tarjeta;
    }

    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel lblMensaje = new JLabel("📅 Gestión de Reservas (próximamente)", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblMensaje.setForeground(Color.GRAY);
        panel.add(lblMensaje, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel lblMensaje = new JLabel("📊 Reportes (próximamente)", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblMensaje.setForeground(Color.GRAY);
        panel.add(lblMensaje, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardViewModerno().setVisible(true));
    }
}