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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al formulario de login
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Establecer codificación UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Obtener parámetros del formulario
        String correo = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        
        System.out.println("=== DEBUG LoginServlet ===");
        System.out.println("Correo recibido: " + correo);
        System.out.println("Password recibido: " + (password != null ? "[PRESENTE]" : "[VACÍO]"));
        System.out.println("Remember me: " + rememberMe);
        
        // Validar campos obligatorios
        if (correo == null || correo.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            System.out.println("ERROR: Campos obligatorios vacíos");
            request.setAttribute("error", "Por favor, complete todos los campos obligatorios.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Buscar usuario por correo
            Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(correo.trim());
            
            if (usuario != null) {
                System.out.println("Usuario encontrado: " + usuario.getNombre());
                
                // Verificar contraseña
                if (usuarioDAO.verificarPassword(password, usuario.getPassword())) {
                    System.out.println("Contraseña correcta. Iniciando sesión...");
                    
                    // Crear sesión
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("usuarioId", usuario.getIdUsuario());
                    session.setAttribute("usuarioNombre", usuario.getNombre());
                    session.setAttribute("usuarioCorreo", usuario.getCorreo());
                    session.setAttribute("loggedIn", true);
                    
                    // Configurar tiempo de sesión (30 minutos)
                    session.setMaxInactiveInterval(30 * 60);
                    
                    System.out.println("Sesión creada exitosamente para: " + usuario.getNombre());
                    
                    // Redirigir según el parámetro 'redirect' o al perfil por defecto
                    String redirectUrl = request.getParameter("redirect");
                    if (redirectUrl != null && !redirectUrl.trim().isEmpty()) {
                        response.sendRedirect(redirectUrl);
                    } else {
                        response.sendRedirect("perfil.jsp");
                    }
                    
                } else {
                    System.out.println("Contraseña incorrecta");
                    request.setAttribute("error", "Correo electrónico o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                
            } else {
                System.out.println("Usuario no encontrado con correo: " + correo);
                request.setAttribute("error", "Correo electrónico o contraseña incorrectos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error durante el proceso de login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Intente nuevamente.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
