package com.camila.eleganza.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

@WebServlet("/auth/login")
public class AuthServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener datos del formulario
            String email = request.getParameter("loginEmail");
            String password = request.getParameter("loginPassword");
            
            System.out.println("Login attempt - Email: " + email + ", Password: " + password);
            
            // Validar campos obligatorios
            if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                
                response.sendRedirect("../pages/login.jsp?error=campos_vacios&loginEmail=" + 
                    (email != null ? email : ""));
                return;
            }
            
            // Verificar si es el admin
            if ("admin@eleganzaboutique.com".equals(email.trim().toLowerCase()) && "admin123".equals(password)) {
                System.out.println("Admin login successful");
                
                // Crear usuario admin temporal
                Usuario adminUser = new Usuario();
                adminUser.setIdUsuario(0);
                adminUser.setNombre("Administrador");
                adminUser.setCorreo("admin@eleganzaboutique.com");
                adminUser.setPassword("admin123");
                
                HttpSession session = request.getSession(true);
                session.setAttribute("usuarioLogueado", adminUser);
                session.setAttribute("idUsuario", 0);
                session.setAttribute("nombreUsuario", "Administrador");
                session.setAttribute("esAdmin", true);
                
                response.sendRedirect("../pages/admin.jsp");
                return;
            }
            
            System.out.println("Searching for user in database...");
            
            // Buscar usuario normal en la base de datos
            Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(email.trim().toLowerCase());
            
            System.out.println("User found: " + (usuario != null ? usuario.getNombre() : "null"));
            
            if (usuario == null) {
                response.sendRedirect("../pages/login.jsp?error=usuario_no_encontrado&loginEmail=" + email);
                return;
            }
            
            // Verificar contraseña
            if (!password.equals(usuario.getPassword())) {
                System.out.println("Password mismatch");
                response.sendRedirect("../pages/login.jsp?error=password_incorrecta&loginEmail=" + email);
                return;
            }
            
            System.out.println("User login successful");
            
            HttpSession session = request.getSession(true);
            session.setAttribute("usuarioLogueado", usuario);
            session.setAttribute("idUsuario", usuario.getIdUsuario());
            session.setAttribute("nombreUsuario", usuario.getNombre());
            session.setAttribute("esAdmin", false);
            
            // Redirigir al index
            response.sendRedirect("../index.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in AuthServlet: " + e.getMessage());
            response.sendRedirect("../pages/login.jsp?error=error_servidor");
        }
    }
}