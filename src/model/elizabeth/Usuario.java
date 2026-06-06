/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.elizabeth;

/**
 *
 * @author Elizabeth
 */
public class Usuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String rol;
    private String estado;
    
    public Usuario() { }
    
    public Usuario(int idUsuario, String usuario, String password, String rol, String estado) {
    this.idUsuario = idUsuario;
    this.usuario = usuario;
    this.password = password;
    this.rol = rol;
    this.estado = estado;
}
    
    
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return usuario; }
    public void setNombreUsuario(String nombreUsuario) { this.usuario = nombreUsuario; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estadoUsuario) { this.estado = estadoUsuario; }
    
    
}
