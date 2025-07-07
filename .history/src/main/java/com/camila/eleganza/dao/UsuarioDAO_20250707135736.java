package com.camila.eleganza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.camila.eleganza.model.Usuario;

public class UsuarioDAO {
    
    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY idUsuario DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCalle(rs.getString("calle"));
                usuario.setCiudad(rs.getString("ciudad"));
                usuario.setCodigoPostal(rs.getString("codigoPostal"));
                usuario.setPais(rs.getString("pais"));
                usuario.setPassword(rs.getString("password"));
                
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    // Obtener usuario por ID
    public Usuario getUsuarioById(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCalle(rs.getString("calle"));
                usuario.setCiudad(rs.getString("ciudad"));
                usuario.setCodigoPostal(rs.getString("codigoPostal"));
                usuario.setPais(rs.getString("pais"));
                usuario.setPassword(rs.getString("password"));
                
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    // Verificar si existe usuario por idUsuario
    public boolean existeIdUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar idUsuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Obtener total de usuarios
    public int getTotalUsuarios() {
        String sql = "SELECT COUNT(*) FROM usuario";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Eliminar usuario por ID
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Buscar usuario por correo
    public Usuario buscarUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCalle(rs.getString("calle"));
                usuario.setCiudad(rs.getString("ciudad"));
                usuario.setCodigoPostal(rs.getString("codigoPostal"));
                usuario.setPais(rs.getString("pais"));
                usuario.setPassword(rs.getString("password"));
                
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por correo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Registrar nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (idUsuario, nombre, correo, telefono, calle, ciudad, codigoPostal, pais, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("=== DEBUG UsuarioDAO.registrarUsuario ===");
        System.out.println("Ejecutando SQL: " + sql);
        System.out.println("Datos del usuario: " + usuario.toString());
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (conn == null) {
                System.err.println("ERROR: No se pudo establecer conexión con la base de datos");
                return false;
            }
            
            System.out.println("Conexión establecida correctamente");
            
            ps.setInt(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getCalle());
            ps.setString(6, usuario.getCiudad());
            ps.setString(7, usuario.getCodigoPostal());
            ps.setString(8, usuario.getPais());
            ps.setString(9, usuario.getPassword());
            
            System.out.println("PreparedStatement configurado correctamente");
            System.out.println("Valores asignados:");
            System.out.println("  1. idUsuario: " + usuario.getIdUsuario());
            System.out.println("  2. nombre: " + usuario.getNombre());
            System.out.println("  3. correo: " + usuario.getCorreo());
            System.out.println("  4. telefono: " + usuario.getTelefono());
            System.out.println("  5. calle: " + usuario.getCalle());
            System.out.println("  6. ciudad: " + usuario.getCiudad());
            System.out.println("  7. codigoPostal: " + usuario.getCodigoPostal());
            System.out.println("  8. pais: " + usuario.getPais());
            System.out.println("  9. password: [ENCRIPTADA]");
            
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            System.out.println("=== FIN DEBUG UsuarioDAO.registrarUsuario ===");
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR SQL al registrar usuario: " + e.getMessage());
            System.err.println("Código de error SQL: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
            return false;
        }
    }
    
    // Verificar si existe correo electrónico
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método de prueba para verificar la inserción
    public boolean pruebaInsercion() {
        String sql = "INSERT INTO usuario (idUsuario, nombre, correo, telefono, calle, ciudad, codigoPostal, pais, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("=== PRUEBA DE INSERCIÓN ===");
        
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn == null) {
                System.err.println("ERROR: Conexión nula");
                return false;
            }
            
            System.out.println("Conexión establecida: " + conn.getMetaData().getURL());
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, 77777);
                ps.setString(2, "Prueba DAO");
                ps.setString(3, "prueba@dao.com");
                ps.setString(4, "987654321");
                ps.setString(5, "Calle DAO");
                ps.setString(6, "Ciudad DAO");
                ps.setString(7, "54321");
                ps.setString(8, "País DAO");
                ps.setString(9, "passworddao");
                
                System.out.println("SQL preparado: " + sql);
                System.out.println("Ejecutando inserción...");
                
                int filasAfectadas = ps.executeUpdate();
                System.out.println("Filas afectadas: " + filasAfectadas);
                
                if (filasAfectadas > 0) {
                    // Eliminar el registro de prueba
                    String deleteSql = "DELETE FROM usuario WHERE idUsuario = 77777";
                    try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
                        deletePs.executeUpdate();
                        System.out.println("Registro de prueba eliminado");
                    }
                    return true;
                } else {
                    System.err.println("No se insertó ninguna fila");
                    return false;
                }
                
            } catch (SQLException e) {
                System.err.println("Error SQL en inserción: " + e.getMessage());
                System.err.println("Código de error: " + e.getErrorCode());
                System.err.println("Estado SQL: " + e.getSQLState());
                e.printStackTrace();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}