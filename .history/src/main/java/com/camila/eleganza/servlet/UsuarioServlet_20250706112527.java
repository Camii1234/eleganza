package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;
import com.google.gson.Gson;

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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        try {
            if ("delete".equals(action)) {
                eliminarUsuario(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = response.getWriter();
                out.print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error del servidor: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
    
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String idStr = request.getParameter("idUsuario");
        PrintWriter out = response.getWriter();
        
        if (idStr == null || idStr.trim().isEmpty()) {
            out.print("{\"error\": \"ID de usuario requerido\"}");
            return;
        }
        
        try {
            int idUsuario = Integer.parseInt(idStr);
            boolean success = usuarioDAO.eliminarUsuario(idUsuario);
            
            if (success) {
                out.print("{\"success\": true, \"mensaje\": \"Usuario eliminado exitosamente\"}");
            } else {
                out.print("{\"error\": \"No se pudo eliminar el usuario\"}");
            }
        } catch (NumberFormatException e) {
            out.print("{\"error\": \"ID de usuario inválido\"}");
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
