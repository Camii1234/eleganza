package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/debug-carrito")
public class DebugCarritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Debug Carrito</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Debug Carrito Servlet</h1>");
        out.println("<p>Método: GET</p>");
        out.println("<p>Servlet Path: " + request.getServletPath() + "</p>");
        out.println("<p>Context Path: " + request.getContextPath() + "</p>");
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
        out.println("<p>Query String: " + request.getQueryString() + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String action = request.getParameter("action");
        String productoId = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        String cantidad = request.getParameter("cantidad");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Debug Carrito POST</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Debug Carrito Servlet - POST</h1>");
        out.println("<p>Action: " + action + "</p>");
        out.println("<p>Producto ID: " + productoId + "</p>");
        out.println("<p>Talla: " + talla + "</p>");
        out.println("<p>Cantidad: " + cantidad + "</p>");
        out.println("<p>Context Path: " + request.getContextPath() + "</p>");
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
        out.println("<a href='carrito.jsp'>Ir al carrito</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
