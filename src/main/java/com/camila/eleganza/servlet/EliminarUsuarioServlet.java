package com.camila.eleganza.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camila.eleganza.dao.UsuarioDAO;

@WebServlet("/admin/eliminarUsuario")
public class EliminarUsuarioServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("../pages/admin.jsp?error=id_requerido");
            return;
        }
        
        try {
            int idUsuario = Integer.parseInt(idStr);
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean eliminado = usuarioDAO.eliminarUsuario(idUsuario);
            
            if (eliminado) {
                response.sendRedirect("../pages/admin.jsp?success=usuario_eliminado");
            } else {
                response.sendRedirect("../pages/admin.jsp?error=error_eliminar_usuario");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("../pages/admin.jsp?error=id_invalido");
        }
    }
}