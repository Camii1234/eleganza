package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.camila.eleganza.dao.UsuarioDAO;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminarUsuarioServlet")
public class EliminarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final UsuarioDAO usuarioDAO;
    
    public EliminarUsuarioServlet() {
        super();
        this.usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            String idStr = request.getParameter("id");
            
            if (idStr == null || idStr.trim().isEmpty()) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "ID de usuario no válido");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonResponse.toString());
                return;
            }
            
            int usuarioId = Integer.parseInt(idStr);
            
            // Verificar si el usuario existe antes de eliminarlo
            if (!usuarioDAO.existeIdUsuario(usuarioId)) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "El usuario no existe");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(jsonResponse.toString());
                return;
            }
            
            boolean eliminado = usuarioDAO.eliminarUsuario(usuarioId);
            
            if (eliminado) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Usuario eliminado exitosamente");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "No se pudo eliminar el usuario");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "ID de usuario inválido");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error interno del servidor");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
