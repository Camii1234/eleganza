package com.camila.eleganza.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.camila.eleganza.model.Usuario;

/**
 * Filtro para proteger páginas que requieren autenticación
 */
@WebFilter(urlPatterns = {"/perfil.jsp", "/admin.jsp"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
        System.out.println("AuthenticationFilter inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Obtener la sesión
        HttpSession session = httpRequest.getSession(false);
        
        // Verificar si el usuario está logueado
        boolean isLoggedIn = false;
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Boolean loggedInFlag = (Boolean) session.getAttribute("loggedIn");
            isLoggedIn = (usuario != null && loggedInFlag != null && loggedInFlag);
        }
        
        if (isLoggedIn) {
            // Usuario autenticado, continuar con la cadena de filtros
            chain.doFilter(request, response);
        } else {
            // Usuario no autenticado, redirigir al login
            String requestURI = httpRequest.getRequestURI();
            String contextPath = httpRequest.getContextPath();
            String page = requestURI.substring(contextPath.length());
            
            System.out.println("Acceso denegado a: " + page + " - Usuario no autenticado");
            httpResponse.sendRedirect("login.jsp?redirect=" + page);
        }
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
        System.out.println("AuthenticationFilter destruido");
    }
}
