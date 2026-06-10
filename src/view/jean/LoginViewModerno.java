/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.jean;

import controller.leo.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Vista de inicio de sesión con diseño moderno y personalizado.
 * Esta interfaz reemplaza el diseño básico por uno más atractivo
 * con fondo oscuro, campos redondeados y botón estilizado.
 * 
 * @author JEAN CARLOS
 * @version 2.0
 */
public class LoginViewModerno extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    /**
     * Constructor de la ventana de login.
     * Inicializa y organiza todos los componentes visuales.
     */
    public LoginViewModerno() {
        setTitle("Restaurante El Merlín");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Panel principal con fondo oscuro
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(30, 30, 40));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        // Logo (imagen)
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("🍽️");
            lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 80));
            lblLogo.setForeground(Color.WHITE);
        }
        lblLogo.setBounds(175, 30, 150, 150);
        panel.add(lblLogo);

        // Título
        JLabel lblTitulo = new JLabel("EL MERLÍN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 180, 50));
        lblTitulo.setBounds(160, 190, 200, 40);
        panel.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("de cabo blanco");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setBounds(190, 225, 150, 25);
        panel.add(lblSubtitulo);

        // Campo usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(80, 290, 100, 25);
        panel.add(lblUsuario);

        txtUsuario = new JTextField("Ingrese su usuario");
        txtUsuario.setForeground(Color.GRAY);
        txtUsuario.setBounds(80, 315, 340, 35);
        estilizarCampo(txtUsuario);
        txtUsuario.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtUsuario.getText().equals("Ingrese su usuario")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Ingrese su usuario");
                    txtUsuario.setForeground(Color.GRAY);
                }
            }
        });
        panel.add(txtUsuario);

        // Campo contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setForeground(Color.WHITE);
        lblContrasena.setBounds(80, 370, 100, 25);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField("Ingrese su contraseña");
        txtContrasena.setForeground(Color.GRAY);
        txtContrasena.setEchoChar((char) 0);
        txtContrasena.setBounds(80, 395, 340, 35);
        estilizarCampo(txtContrasena);
        txtContrasena.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (new String(txtContrasena.getPassword()).equals("Ingrese su contraseña")) {
                    txtContrasena.setText("");
                    txtContrasena.setForeground(Color.WHITE);
                    txtContrasena.setEchoChar('●');
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtContrasena.getPassword().length == 0) {
                    txtContrasena.setText("Ingrese su contraseña");
                    txtContrasena.setForeground(Color.GRAY);
                    txtContrasena.setEchoChar((char) 0);
                }
            }
        });
        panel.add(txtContrasena);

        // Botón ingresar redondeado
        btnIngresar = new JButton("INGRESAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 140, 0));
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
        btnIngresar.setBounds(80, 460, 340, 45);
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.addActionListener(e -> validarLogin());
        panel.add(btnIngresar);

        // Mensaje de error
        lblMensaje = new JLabel("");
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(80, 515, 340, 25);
        panel.add(lblMensaje);

        // Botón cerrar (X)
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setBounds(470, 5, 25, 25);
        btnCerrar.setBackground(new Color(200, 50, 50));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> System.exit(0));
        panel.add(btnCerrar);
    }

    /**
     * Aplica un estilo común a los campos de texto:
     * - Fondo transparente
     * - Bordes invisibles
     * - Fuente y color de texto personalizados
     * 
     * @param campo El JTextField o JPasswordField a estilizar.
     */
    private void estilizarCampo(JTextField campo) {
        campo.setOpaque(false);
        campo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    /**
     * Valida las credenciales ingresadas contra la base de datos.
     * Muestra mensajes de error si el usuario no existe o la contraseña es incorrecta.
     * Si es exitoso, abre el DashboardViewModerno y cierra el login.
     */
    private void validarLogin() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.equals("admin") || usuario.equals("mesero1")) {
            if (LoginController.validarCredenciales(usuario, contrasena)) {
                new view.jean.DashboardViewModerno().setVisible(true); // ✅ AHORA ABRE EL MODERNO
                this.dispose();
            } else {
                lblMensaje.setText("Contraseña incorrecta");
            }
        } else {
            lblMensaje.setText("Usuario no existe");
        }
    }

    /**
     * Método principal para ejecutar la ventana de login.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginViewModerno().setVisible(true));
    }
}