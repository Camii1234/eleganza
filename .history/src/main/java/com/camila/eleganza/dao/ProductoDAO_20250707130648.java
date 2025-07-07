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

    // Método para agregar un nuevo producto
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, talla, categoria, stock, estado, imagen, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getTalla());
            ps.setString(4, producto.getCategoria());
            ps.setInt(5, producto.getStock());
            ps.setString(6, producto.getEstado());
            ps.setBytes(7, producto.getImagen());
            ps.setString(8, producto.getDescripcion());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un producto existente
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, talla = ?, categoria = ?, stock = ?, estado = ?, imagen = ?, descripcion = ? WHERE idProducto = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getTalla());
            ps.setString(4, producto.getCategoria());
            ps.setInt(5, producto.getStock());
            ps.setString(6, producto.getEstado());
            ps.setBytes(7, producto.getImagen());
            ps.setString(8, producto.getDescripcion());
            ps.setInt(9, producto.getIdProducto());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM producto WHERE idProducto = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}