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
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener datos del formulario
            String idUsuarioStr = request.getParameter("documento"); // El formulario envía como "documento" pero es el idUsuario
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String calle = request.getParameter("calle");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String pais = request.getParameter("pais");
            
            System.out.println("DEBUG: Recibiendo datos de registro:");
            System.out.println("ID Usuario: " + idUsuarioStr);
            System.out.println("Nombre: " + nombre);
            System.out.println("Correo: " + correo);
            System.out.println("Teléfono: " + telefono);
            
            // Validar campos obligatorios
            if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos obligatorios deben ser completados.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Las contraseñas no coinciden.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar longitud de contraseña
            if (password.length() < 6) {
                request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar que idUsuario sea numérico
            int idUsuario;
            try {
                idUsuario = Integer.parseInt(idUsuarioStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El ID de usuario debe ser un número válido.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Crear instancia del DAO
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Verificar si el idUsuario ya existe
            if (usuarioDAO.existeIdUsuario(idUsuario)) {
                request.setAttribute("error", "Ya existe un usuario registrado con este ID.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el correo ya existe
            Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorCorreo(correo.trim().toLowerCase());
            if (usuarioExistente != null) {
                request.setAttribute("error", "Ya existe un usuario registrado con este correo electrónico.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Crear el objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setIdUsuario(idUsuario);
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(correo.trim().toLowerCase());
            nuevoUsuario.setTelefono(telefono != null ? telefono.trim() : "");
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setCalle(calle != null ? calle.trim() : "");
            nuevoUsuario.setCiudad(ciudad != null ? ciudad.trim() : "");
            nuevoUsuario.setCodigoPostal(codigoPostal != null ? codigoPostal.trim() : "");
            nuevoUsuario.setPais(pais != null ? pais.trim() : "");
            
            System.out.println("DEBUG: Intentando guardar usuario en base de datos...");
            
            // Guardar en la base de datos
            boolean registroExitoso = usuarioDAO.agregarUsuario(nuevoUsuario);
            
            if (registroExitoso) {
                System.out.println("DEBUG: Usuario registrado exitosamente");
                request.setAttribute("success", "¡Usuario registrado exitosamente! Ya puedes iniciar sesión.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            } else {
                System.out.println("DEBUG: Error al registrar usuario");
                request.setAttribute("error", "Error al registrar el usuario. Por favor, inténtalo de nuevo.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error en RegistroServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Por favor, inténtalo más tarde.");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }
}