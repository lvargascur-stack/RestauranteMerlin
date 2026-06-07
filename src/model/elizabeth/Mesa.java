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
    private int capacidadMesa;
    private String estadoMesa;
    
    public Mesa(){}

    public Mesa(int idMesa, int numeroMesa, int capacidadMesa, String estadoMesa) {
    
        this.idMesa= idMesa;
        this.numeroMesa= numeroMesa;
        this.capacidadMesa= capacidadMesa;
        this.estadoMesa= estadoMesa;
    }

    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }
    
    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    
    public int getCapacidadMesa() { return capacidadMesa; }
    public void setCapacidadMesa(int capacidadMesa) { this.capacidadMesa = capacidadMesa; }
    
    public String getEstadoMesa() { return estadoMesa; }
    public void setEstadoMesa(String estadoMesa) { this.estadoMesa = estadoMesa; }
    
}
