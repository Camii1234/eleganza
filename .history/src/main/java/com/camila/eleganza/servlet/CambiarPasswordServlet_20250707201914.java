package com.camila.eleganza.servlet;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;
import com.camila.eleganza.util.SessionManager;

@WebServlet("/cambiarPassword")
public class CambiarPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
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
        
        String passwordActual = request.getParameter("currentPassword");
        String nuevaPassword = request.getParameter("newPassword");
        String confirmarPassword = request.getParameter("confirmPassword");
        
        try {
            // Validaciones
            if (passwordActual == null || passwordActual.trim().isEmpty()) {
                request.setAttribute("error", "La contraseña actual es requerida");
                forwardToPerfil(request, response);
                return;
            }
            
            if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
                request.setAttribute("error", "La nueva contraseña es requerida");
                forwardToPerfil(request, response);
                return;
            }
            
            if (confirmarPassword == null || !nuevaPassword.equals(confirmarPassword)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                forwardToPerfil(request, response);
                return;
            }
            
            // Validar longitud y complejidad de la contraseña
            if (nuevaPassword.length() < 8) {
                request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres");
                forwardToPerfil(request, response);
                return;
            }
            
            if (!nuevaPassword.matches(".*[A-Z].*") || !nuevaPassword.matches(".*[a-z].*") || !nuevaPassword.matches(".*[0-9].*")) {
                request.setAttribute("error", "La contraseña debe incluir mayúsculas, minúsculas y números");
                forwardToPerfil(request, response);
                return;
            }
            
            // Verificar contraseña actual
            if (!usuarioDAO.verificarPasswordActual(usuarioLogueado.getIdUsuario(), passwordActual)) {
                request.setAttribute("error", "La contraseña actual es incorrecta");
                forwardToPerfil(request, response);
                return;
            }
            
            // Cambiar contraseña
            boolean exito = usuarioDAO.cambiarPassword(usuarioLogueado.getIdUsuario(), nuevaPassword);
            
            if (exito) {
                request.setAttribute("success", "Contraseña cambiada exitosamente");
            } else {
                request.setAttribute("error", "Error al cambiar la contraseña. Inténtalo nuevamente.");
            }
            
        } catch (Exception e) {
            System.err.println("Error en CambiarPasswordServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Inténtalo más tarde.");
        }
        
        forwardToPerfil(request, response);
    }
    
    private void forwardToPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("perfil.jsp");
        dispatcher.forward(request, response);
    }
}
