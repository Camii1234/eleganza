package com.camila.eleganza.filter;

import java.io.IOException;

import com.camila.eleganza.model.Carrito;

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

@WebFilter(urlPatterns = {"*.jsp", "/carrito", "/producto"})
public class CarritoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Obtener o crear la sesión
        HttpSession session = httpRequest.getSession(true);
        
        // Verificar si existe un carrito en la sesión
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        
        if (carrito == null) {
            // Crear un nuevo carrito si no existe
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}
