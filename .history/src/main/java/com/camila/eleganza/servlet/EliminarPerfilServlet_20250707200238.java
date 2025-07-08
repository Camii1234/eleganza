package com.camila.eleganza.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;
import com.camila.eleganza.util.SessionManager;

@WebServlet("/eliminarPerfil")
public class EliminarPerfilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = SessionManager.getUsuarioSesion(session);
        
        if (usuarioLogueado == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String confirmacion = request.getParameter("confirmacion");
        
        try {
            // Validar confirmación
            if (confirmacion == null || !confirmacion.equals("ELIMINAR")) {
                request.setAttribute("error", "Confirmación incorrecta. Debe escribir 'ELIMINAR' para confirmar.");
                request.getRequestDispatcher("perfil.jsp").forward(request, response);
                return;
            }
            
            // Eliminar perfil
            boolean exito = usuarioDAO.eliminarPerfil(usuarioLogueado.getIdUsuario());
            
            if (exito) {
                // Invalidar sesión
                session.invalidate();
                
                // Redirigir al inicio con mensaje de confirmación
                response.sendRedirect("index.jsp?mensaje=perfil_eliminado");
            } else {
                request.setAttribute("error", "Error al eliminar el perfil. Inténtalo nuevamente.");
                request.getRequestDispatcher("perfil.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error en EliminarPerfilServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Inténtalo más tarde.");
            request.getRequestDispatcher("perfil.jsp").forward(request, response);
        }
    }
}
