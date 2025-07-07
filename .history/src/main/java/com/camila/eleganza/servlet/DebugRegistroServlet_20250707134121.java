package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/debug-registro")
public class DebugRegistroServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Debug - Parámetros del Formulario</h2>");
        
        // Mostrar todos los parámetros recibidos
        out.println("<h3>Parámetros recibidos:</h3>");
        out.println("<ul>");
        
        String[] params = {"documento", "nombre", "correo", "telefono", "password", "confirmPassword", 
                          "calle", "ciudad", "codigoPostal", "pais"};
        
        for (String param : params) {
            String value = request.getParameter(param);
            out.println("<li><strong>" + param + ":</strong> " + (value != null ? value : "NULL") + "</li>");
        }
        
        out.println("</ul>");
        
        // Verificar validaciones
        out.println("<h3>Validaciones:</h3>");
        out.println("<ul>");
        
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (password != null && confirmPassword != null) {
            if (password.equals(confirmPassword)) {
                out.println("<li>✅ Las contraseñas coinciden</li>");
            } else {
                out.println("<li>❌ Las contraseñas NO coinciden</li>");
            }
        }
        
        if (password != null && password.length() >= 6) {
            out.println("<li>✅ La contraseña tiene al menos 6 caracteres</li>");
        } else {
            out.println("<li>❌ La contraseña debe tener al menos 6 caracteres</li>");
        }
        
        out.println("</ul>");
        
        out.println("<p><a href='registro.jsp'>Volver al formulario</a></p>");
        out.println("</body></html>");
    }
}
