/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.elizabeth;

import java.util.Date;

/**
 *
 * @author Elizabeth
 */
public class Pedido {
    
    private int idPedido;
    private Date fechaPedido;
    private String estado;
    private double total;
    private int idCliente;
    private int idMesa;
    private int idEmpleado;
    
    public Pedido() {}
    
    public Pedido(int idPedido, Date fechaPedido, String estado, double total, int idCliente, int idMesa, int idEmpleado){
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
        this.idCliente = idCliente;
        this.idMesa = idMesa;
        this.idEmpleado = idEmpleado;
        
    }
    
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public Date getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Date fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
}

