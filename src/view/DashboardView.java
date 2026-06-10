/*package view;

import controller.leo.*;
import javax.swing.*;

public class DashboardView_old extends javax.swing.JFrame {

    public DashboardView_old() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard - Restaurante El Merlín");

        jLabel1.setText("DASHBOARD");

        jButton1.setText("Mesas");
        jButton1.addActionListener(evt -> {
            java.awt.EventQueue.invokeLater(() -> new MesasView().setVisible(true));
        });

        jButton2.setText("Pedidos");
        jButton2.addActionListener(evt -> {
            java.awt.EventQueue.invokeLater(() -> new PedidosView().setVisible(true));
        });

        jButton3.setText("Registrar Pedido");
        jButton3.addActionListener(evt -> {
            java.awt.EventQueue.invokeLater(() -> new RegistrarPedidoView().setVisible(true));
        });

        jButton4.setText("Reportes");
        jButton4.addActionListener(evt -> {
            java.awt.EventQueue.invokeLater(() -> new ReportesView().setVisible(true));
        });

        jButton5.setText("Cerrar Sesión");
        jButton5.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                java.awt.EventQueue.invokeLater(() -> new LoginView().setVisible(true));
                this.dispose();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration                   
}
*/