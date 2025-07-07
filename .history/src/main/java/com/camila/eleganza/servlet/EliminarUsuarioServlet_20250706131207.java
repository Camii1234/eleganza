package com.camila.eleganza.servlet;

import java.io.IOException;

import com.camila.eleganza.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/eliminarUsuario")
public class EliminarUsuarioServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("../admin.jsp?error=id_requerido");
            return;
        }
        
        try {
            int idUsuario = Integer.parseInt(idStr);
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean eliminado = usuarioDAO.eliminarUsuario(idUsuario);
            
            if (eliminado) {
                response.sendRedirect("../admin.jsp?mensaje=usuario_eliminado");
            } else {
                response.sendRedirect("../admin.jsp?error=error_eliminar_usuario");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("../admin.jsp?error=id_invalido");
        }
    }
}
