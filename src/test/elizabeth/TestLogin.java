/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.elizabeth;

import model.elizabeth.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elizabeth
 */
public class TestLogin {
    
    @Test
    public void testCredencialesAdministradorValidas() {
        Usuario admin = new Usuario(1, "admin", "admin123", "administrador", "activo");
        
        boolean credencialesOk =
    "admin".equals(admin.getNombreUsuario())
    && "admin123".equals(admin.getPassword());
        
        assertTrue(credencialesOk);
        assertEquals("administrador", admin.getRol());
        assertEquals("activo", admin.getEstado());
    }

    @Test
    public void testCredencialesMeseroValidas() {
        Usuario mesero = new Usuario(2, "mesero1", "mesero123", "mesero", "activo");
        
        assertTrue("mesero1".equals(mesero.getNombreUsuario()));
        assertTrue("mesero123".equals(mesero.getPassword()));
        assertEquals("mesero", mesero.getRol());
    }

    @Test
    public void testUsuarioInactivoNoPuedeAcceder() {
        Usuario usuario = new Usuario(3, "cocinero1", "cocina123", "cocinero", "inactivo");
        
        boolean puedeAcceder = "activo".equals(usuario.getEstado());
        assertFalse(puedeAcceder);
    }
    
}
