package com.camila.eleganza.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir al JSP cuando se accede por GET
        response.sendRedirect("registro.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener datos del formulario
            String documento = request.getParameter("documento");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String password = request.getParameter("password");
            String calle = request.getParameter("calle");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String pais = request.getParameter("pais");
            
            // Crear el objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setDocumento(documento);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setCalle(calle);
            nuevoUsuario.setCiudad(ciudad);
            nuevoUsuario.setCodigoPostal(codigoPostal);
            nuevoUsuario.setPais(pais);
            
            // Guardar en la base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean registroExitoso = usuarioDAO.agregarUsuario(nuevoUsuario);
            
            if (registroExitoso) {
                request.setAttribute("success", "¡Usuario registrado exitosamente!");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al registrar el usuario.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }
}