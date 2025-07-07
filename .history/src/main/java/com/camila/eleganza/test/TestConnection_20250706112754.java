package com.camila.eleganza.test;

import java.sql.Connection;

import com.camila.eleganza.dao.ConexionBD;
import com.camila.eleganza.dao.ProductoDAO;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== TEST DE CONEXIÓN Y DAO ===");
        
        // Test de conexión
        System.out.println("1. Probando conexión a la base de datos...");
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Conexión exitosa a la base de datos");
            } else {
                System.out.println("✗ No se pudo conectar a la base de datos");
                return;
            }
        } catch (Exception e) {
            System.err.println("✗ Error al conectar: " + e.getMessage());
            return;
        }
        
        // Test del ProductoDAO
        System.out.println("\n2. Probando ProductoDAO...");
        ProductoDAO dao = new ProductoDAO();
        
        try {
            // Intentar obtener productos
            var productos = dao.getAllProductosAdmin();
            System.out.println("✓ Productos encontrados: " + productos.size());
            
            if (!productos.isEmpty()) {
                var primerProducto = productos.get(0);
                System.out.println("✓ Primer producto: ID=" + primerProducto.getIdProducto() + 
                                 ", Nombre=" + primerProducto.getNombre());
                
                // Test de eliminación en seco (sin ejecutar)
                System.out.println("\n3. Test de método deleteProducto (simulación)...");
                System.out.println("Se ejecutaría deleteProducto(" + primerProducto.getIdProducto() + ")");
                // No ejecutamos la eliminación real
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error en ProductoDAO: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DEL TEST ===");
    }
}
