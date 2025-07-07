package com.camila.eleganza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.camila.eleganza.model.Producto;

public class ProductoDAO {
    
    // Obtener todos los productos activos
    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE estado = 'Activo'";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setTalla(rs.getString("talla"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setStock(rs.getInt("stock"));
                producto.setEstado(rs.getString("estado"));
                producto.setImagen(rs.getBytes("imagen"));
                producto.setDescripcion(rs.getString("descripcion"));
                
                productos.add(producto);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        
        return productos;
    }
    
    // Obtener productos por categoría
    public List<Producto> getProductosByCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria = ? AND estado = 'Activo'";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoria);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setTalla(rs.getString("talla"));
                    producto.setCategoria(rs.getString("categoria"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setEstado(rs.getString("estado"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    
                    productos.add(producto);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener productos por categoría: " + e.getMessage());
        }
        
        return productos;
    }
    
    // Obtener productos ordenados por precio
    public List<Producto> getProductosOrdenadosPorPrecio(String orden) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE estado = 'Activo' ORDER BY precio ";
        
        // Validar que el orden sea ASC o DESC
        if ("DESC".equalsIgnoreCase(orden)) {
            sql += "DESC";
        } else {
            sql += "ASC";
        }
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setTalla(rs.getString("talla"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setStock(rs.getInt("stock"));
                producto.setEstado(rs.getString("estado"));
                producto.setImagen(rs.getBytes("imagen"));
                producto.setDescripcion(rs.getString("descripcion"));
                
                productos.add(producto);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener productos ordenados: " + e.getMessage());
        }
        
        return productos;
    }
    
    // Método sobrecargado para obtener productos con solo categoría y orden
    public List<Producto> getProductosConFiltros(String categoria, String orden) {
        return getProductosConFiltros(categoria, null, null, orden);
    }

    // Método mejorado para obtener productos con múltiples filtros
    public List<Producto> getProductosConFiltros(String categoria, String talla, String rangoPrecios, String orden) {
        List<Producto> productos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM producto WHERE estado = 'Activo'");
        List<Object> parametros = new ArrayList<>();
        
        // Filtro por categoría
        if (categoria != null && !categoria.isEmpty() && !"todas".equalsIgnoreCase(categoria)) {
            sql.append(" AND categoria = ?");
            parametros.add(categoria);
        }
        
        // Filtro por talla
        if (talla != null && !talla.isEmpty() && !"todas".equalsIgnoreCase(talla)) {
            sql.append(" AND talla = ?");
            parametros.add(talla);
        }
        
        // Filtro por rango de precios
        if (rangoPrecios != null && !rangoPrecios.isEmpty()) {
            switch (rangoPrecios) {
                case "0-50000":
                    sql.append(" AND precio BETWEEN 0 AND 50000");
                    break;
                case "50000-100000":
                    sql.append(" AND precio BETWEEN 50000 AND 100000");
                    break;
                case "100000-150000":
                    sql.append(" AND precio BETWEEN 100000 AND 150000");
                    break;
                case "150000+":
                    sql.append(" AND precio > 150000");
                    break;
            }
        }
        
        // Ordenamiento
        if (orden != null && !orden.isEmpty()) {
            if ("price-low".equals(orden) || "ASC".equalsIgnoreCase(orden)) {
                sql.append(" ORDER BY precio ASC");
            } else if ("price-high".equals(orden) || "DESC".equalsIgnoreCase(orden)) {
                sql.append(" ORDER BY precio DESC");
            } else if ("newest".equals(orden)) {
                sql.append(" ORDER BY idProducto DESC");
            } else {
                sql.append(" ORDER BY nombre ASC");
            }
        } else {
            sql.append(" ORDER BY idProducto DESC");
        }
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // Establecer parámetros
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setTalla(rs.getString("talla"));
                    producto.setCategoria(rs.getString("categoria"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setEstado(rs.getString("estado"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    
                    productos.add(producto);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener productos con filtros: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productos;
    }

    // Obtener tallas disponibles
    public List<String> getTallas() {
        List<String> tallas = new ArrayList<>();
        String sql = "SELECT DISTINCT talla FROM producto WHERE estado = 'Activo' ORDER BY talla";
        
        try (Connection conn = ConexionBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String talla = rs.getString("talla");
                if (talla != null && !talla.trim().isEmpty()) {
                    tallas.add(talla);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener tallas: " + e.getMessage());
        }
        
        return tallas;
    }

    // Obtener categorías disponibles
    public List<String> getCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT DISTINCT categoria FROM producto WHERE estado = 'Activo'";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                categorias.add(rs.getString("categoria"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener categorías: " + e.getMessage());
        }
        
        return categorias;
    }
    
    // Obtener producto por ID
    public Producto getProductoById(int id) {
        Producto producto = null;
        String sql = "SELECT * FROM producto WHERE idProducto = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setTalla(rs.getString("talla"));
                    producto.setCategoria(rs.getString("categoria"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setEstado(rs.getString("estado"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setDescripcion(rs.getString("descripcion"));
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener producto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return producto;
    }

    

    // Eliminar producto completamente de la base de datos
    public boolean deleteProducto(int id) {
        String sql = "DELETE FROM producto WHERE idProducto = ?";
        
        System.out.println("=== ProductoDAO.deleteProducto ===");
        System.out.println("Intentando eliminar producto con ID: " + id);
        
        // Primero verificar si el producto existe
        if (!existeProducto(id)) {
            System.err.println("Error: El producto con ID " + id + " no existe");
            return false;
        }
        
        System.out.println("SQL a ejecutar: " + sql);
        
        try (Connection conn = ConexionBD.getConnection()) {
            
            if (conn == null) {
                System.err.println("Error: No se pudo obtener conexión a la base de datos");
                return false;
            }
            
            System.out.println("Conexión obtenida exitosamente");
            System.out.println("Estado de conexión: " + (conn.isClosed() ? "CERRADA" : "ABIERTA"));
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                System.out.println("Parámetro establecido - ID: " + id);
                
                int result = ps.executeUpdate();
                System.out.println("Consulta ejecutada - Filas afectadas: " + result);
                
                if (result > 0) {
                    System.out.println("Producto eliminado exitosamente");
                    return true;
                } else {
                    System.out.println("No se pudo eliminar el producto con ID: " + id);
                    return false;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar producto: " + e.getMessage());
            System.err.println("Código de error SQL: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Error general al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Verificar si el producto existe antes de eliminar
    public boolean existeProducto(int id) {
        String sql = "SELECT COUNT(*) as count FROM producto WHERE idProducto = ?";
        
        System.out.println("Verificando si existe producto con ID: " + id);
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (conn == null) {
                System.err.println("Error: No se pudo obtener conexión para verificar producto");
                return false;
            }
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Productos encontrados con ID " + id + ": " + count);
                    return count > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al verificar producto: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    // Método para agregar nuevo producto
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, talla, categoria, stock, estado, descripcion, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("SQL para insertar: " + sql);
        System.out.println("Insertando producto: " + producto.getNombre());
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
            if (conn == null) {
                System.err.println("Error: No se pudo obtener conexión a la base de datos");
                return false;
            }

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getTalla());
            ps.setString(4, producto.getCategoria());
            ps.setInt(5, producto.getStock());
            ps.setString(6, producto.getEstado());
            ps.setString(7, producto.getDescripcion());
            ps.setBytes(8, producto.getImagen());
            
            int result = ps.executeUpdate();
            System.out.println("Filas insertadas: " + result);
            
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al agregar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Error general al agregar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los productos (incluyendo imagen)
    public List<Producto> getAllProductosAdmin() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto ORDER BY idProducto DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setTalla(rs.getString("talla"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setStock(rs.getInt("stock"));
                producto.setEstado(rs.getString("estado"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setImagen(rs.getBytes("imagen"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productos;
    }

    // Actualizar producto
    public boolean actualizarProducto(Producto producto, boolean actualizarImagen) {
        String sql;
        if (actualizarImagen) {
            sql = "UPDATE producto SET nombre = ?, precio = ?, talla = ?, categoria = ?, stock = ?, estado = ?, descripcion = ?, imagen = ? WHERE idProducto = ?";
        } else {
            sql = "UPDATE producto SET nombre = ?, precio = ?, talla = ?, categoria = ?, stock = ?, estado = ?, descripcion = ? WHERE idProducto = ?";
        }
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setString(3, producto.getTalla());
            stmt.setString(4, producto.getCategoria());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getEstado());

            stmt.setString(7, producto.getDescripcion());
            
            if (actualizarImagen) {
                stmt.setBytes(8, producto.getImagen());
                stmt.setInt(9, producto.getIdProducto());
            } else {
                stmt.setInt(8, producto.getIdProducto());
            }
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para probar la conexión a la base de datos
    public boolean testConnection() {
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("ProductoDAO: Conexión a la base de datos exitosa");
                return true;
            } else {
                System.err.println("ProductoDAO: Error - Conexión es nula o está cerrada");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("ProductoDAO: Error al probar conexión: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}