/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.elizabeth;

/**
 *
 * @author Elizabeth
 */
public class Mesa {
    private int idMesa;
    private int numeroMesa;
    private int capacidad;
    private String estadoMesa;
    
    public Mesa(){}

    public Mesa(int idMesa, int numeroMesa, int capacidad, String estadoMesa) {
    
        this.idMesa= idMesa;
        this.numeroMesa= numeroMesa;
        this.capacidad= capacidad;
        this.estadoMesa= estadoMesa;
    }

    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }
    
    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    
    public int getCapacidadMesa() { return capacidad; }
    public void setCapacidadMesa(int capacidad) { this.capacidad = capacidad; }
    
    public String getEstadoMesa() { return estadoMesa; }
    public void setEstadoMesa(String estadoMesa) { this.estadoMesa = estadoMesa; }
    
}
