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
                
                // Intentar obtener el documento, manejando si la columna no existe
                try {
                    usuario.setDocumento(rs.getString("documento"));
                } catch (SQLException docException) {
                    // Si la columna documento no existe, usar el idUsuario como documento
                    usuario.setDocumento(String.valueOf(rs.getInt("idUsuario")));
                }
                
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
                
                // Intentar obtener el documento, manejando si la columna no existe
                try {
                    usuario.setDocumento(rs.getString("documento"));
                } catch (SQLException docException) {
                    // Si la columna documento no existe, usar el idUsuario como documento
                    usuario.setDocumento(String.valueOf(rs.getInt("idUsuario")));
                }
                
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Agregar nuevo usuario
    public boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (idUsuario, nombre, correo, password, telefono, calle, ciudad, codigoPostal, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("DEBUG DAO: Intentando insertar usuario...");
        System.out.println("DEBUG DAO: SQL = " + sql);
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("DEBUG DAO: Conexión obtenida exitosamente");
            
            ps.setInt(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getCalle());
            ps.setString(7, usuario.getCiudad());
            ps.setString(8, usuario.getCodigoPostal());
            ps.setString(9, usuario.getPais());
            
            System.out.println("DEBUG DAO: Parámetros establecidos, ejecutando query...");
            
            int result = ps.executeUpdate();
            System.out.println("DEBUG DAO: Query ejecutada. Filas afectadas: " + result);
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            System.err.println("Estado SQL: " + e.getSQLState());
            System.err.println("Código de error: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }
    
    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, password = ?, telefono = ?, calle = ?, ciudad = ?, codigoPostal = ?, pais = ? WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getCalle());
            ps.setString(6, usuario.getCiudad());
            ps.setString(7, usuario.getCodigoPostal());
            ps.setString(8, usuario.getPais());
            ps.setString(9, String.valueOf(usuario.getIdUsuario()));
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Eliminar usuario
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Verificar si existe usuario por correo
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
    
    // Verificar si existe usuario por documento
    public boolean existeDocumento(String documento) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE idUsuario = ? OR documento = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, documento);
            ps.setString(2, documento);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar documento: " + e.getMessage());
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
    
    // Autenticar usuario (login)
    public Usuario autenticarUsuario(String correo, String passwordHash) {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND password = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ps.setString(2, passwordHash);
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
                
                // Intentar obtener el documento, manejando si la columna no existe
                try {
                    usuario.setDocumento(rs.getString("documento"));
                } catch (SQLException docException) {
                    // Si la columna documento no existe, usar el idUsuario como documento
                    usuario.setDocumento(String.valueOf(rs.getInt("idUsuario")));
                }
                
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
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
                
                // Intentar obtener el documento, manejando si la columna no existe
                try {
                    usuario.setDocumento(rs.getString("documento"));
                } catch (SQLException e) {
                    // Si la columna documento no existe, usar el idUsuario como documento
                    usuario.setDocumento(String.valueOf(rs.getInt("idUsuario")));
                }
                
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por correo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}