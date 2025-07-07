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
        String sql = "INSERT INTO usuario (nombre, correo, telefono, calle, ciudad, codigoPostal, pais, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            
            // No incluir idUsuario ya que es autoincrementable
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCalle());
            ps.setString(5, usuario.getCiudad());
            ps.setString(6, usuario.getCodigoPostal());
            ps.setString(7, usuario.getPais());
            ps.setString(8, usuario.getPassword());
            
            System.out.println("PreparedStatement configurado correctamente");
            System.out.println("Valores asignados:");
            System.out.println("  1. nombre: " + usuario.getNombre());
            System.out.println("  2. correo: " + usuario.getCorreo());
            System.out.println("  3. telefono: " + usuario.getTelefono());
            System.out.println("  4. calle: " + usuario.getCalle());
            System.out.println("  5. ciudad: " + usuario.getCiudad());
            System.out.println("  6. codigoPostal: " + usuario.getCodigoPostal());
            System.out.println("  7. pais: " + usuario.getPais());
            System.out.println("  8. password: [ENCRIPTADA]");
            
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
    
    // Agregar usuario (wrapper del método registrarUsuario para compatibilidad)
    public boolean agregarUsuario(Usuario usuario) {
        return registrarUsuario(usuario);
    }
    
    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, telefono = ?, calle = ?, ciudad = ?, codigoPostal = ?, pais = ?, password = ? WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCalle());
            ps.setString(5, usuario.getCiudad());
            ps.setString(6, usuario.getCodigoPostal());
            ps.setString(7, usuario.getPais());
            ps.setString(8, usuario.getPassword());
            ps.setInt(9, usuario.getIdUsuario());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Verificar contraseña
    public boolean verificarPassword(String passwordIngresada, String passwordAlmacenada) {
        if (passwordIngresada == null || passwordAlmacenada == null) {
            return false;
        }
        
        // Aquí puedes implementar el hash de la contraseña si es necesario
        // Por ahora, comparación directa para desarrollo
        return passwordIngresada.equals(passwordAlmacenada);
    }
    
    // Método para autenticar usuario (correo y contraseña)
    public Usuario autenticarUsuario(String correo, String password) {
        System.out.println("=== DEBUG UsuarioDAO.autenticarUsuario ===");
        System.out.println("Correo: " + correo);
        System.out.println("Password: " + (password != null ? "[PRESENTE]" : "[VACÍO]"));
        
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getNombre());
            
            if (verificarPassword(password, usuario.getPassword())) {
                System.out.println("Contraseña verificada correctamente");
                return usuario;
            } else {
                System.out.println("Contraseña incorrecta");
                return null;
            }
        } else {
            System.out.println("Usuario no encontrado");
            return null;
        }
    }
    
}