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