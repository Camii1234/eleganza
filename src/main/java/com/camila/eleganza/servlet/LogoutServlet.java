package com.camila.eleganza.servlet;

import java.io.IOException;

import com.camila.eleganza.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Cerrar sesión usando SessionManager
            SessionManager.cerrarSesion(session);
        }
        
        // Redirigir al login con mensaje de cierre de sesión
        response.sendRedirect("login.jsp?logout=exitoso");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
