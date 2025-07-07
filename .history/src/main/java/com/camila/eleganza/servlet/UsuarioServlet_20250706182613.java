package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/usuarios")
public class UsuarioServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener todos los usuarios
            List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
            
            // Convertir a DTO para respuesta JSON
            List<UsuarioDTO> usuariosDTO = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                usuariosDTO.add(convertToDTO(usuario));
            }
            
            // Enviar respuesta JSON
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(usuariosDTO));
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al obtener usuarios: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
    
    
    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.idUsuario = usuario.getIdUsuario();
        dto.nombre = usuario.getNombre();
        dto.correo = usuario.getCorreo();
        dto.telefono = usuario.getTelefono();
        dto.calle = usuario.getCalle();
        dto.ciudad = usuario.getCiudad();
        dto.codigoPostal = usuario.getCodigoPostal();
        dto.pais = usuario.getPais();
        return dto;
    }
    
    // Clase DTO para la respuesta JSON
    public static class UsuarioDTO {
        public int idUsuario;
        public String nombre;
        public String correo;
        public String telefono;
        public String calle;
        public String ciudad;
        public String codigoPostal;
        public String pais;
    }
}
