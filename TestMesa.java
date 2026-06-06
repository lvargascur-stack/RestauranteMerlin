/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.elizabeth;

import model.elizabeth.Mesa;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elizabeth
 */
public class TestMesa {
    
    @Test
    public void testCrearMesaConConstructorVacio() {
        Mesa mesa = new Mesa();
        mesa.setIdMesa(1);
        mesa.setNumeroMesa(5);
        mesa.setCapacidad(4);
        mesa.setEstadoMesa("disponible");

        assertEquals(1, mesa.getIdMesa());
        assertEquals(5, mesa.getNumeroMesa());
        assertEquals(4, mesa.getCapacidad());
        assertEquals("disponible", mesa.getEstadoMesa());
    }

    @Test
    public void testConstructorConParametros() {
        Mesa mesa = new Mesa(2, 10, 6, "ocupada");

        assertEquals(2, mesa.getIdMesa());
        assertEquals(10, mesa.getNumeroMesa());
        assertEquals(6, mesa.getCapacidad());
        assertEquals("ocupada", mesa.getEstadoMesa());
    }

    @Test
    public void testCambiarEstadoMesa() {
        Mesa mesa = new Mesa(3, 3, 2, "disponible");
        mesa.setEstadoMesa("reservada");
        assertEquals("reservada", mesa.getEstadoMesa());
    }

    
}
