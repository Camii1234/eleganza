package com.camila.eleganza.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar codificación de caracteres
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String password = request.getParameter("password");
            String calle = request.getParameter("calle");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String pais = request.getParameter("pais");
            
            // Debug: Mostrar los parámetros recibidos
            System.out.println("=== DEBUG - Parámetros recibidos ===");
            System.out.println("nombre: " + nombre);
            System.out.println("correo: " + correo);
            System.out.println("telefono: " + telefono);
            System.out.println("password: " + (password != null ? "[PRESENTE]" : "[NULL]"));
            System.out.println("calle: " + calle);
            System.out.println("ciudad: " + ciudad);
            System.out.println("codigoPostal: " + codigoPostal);
            System.out.println("pais: " + pais);
            System.out.println("=== FIN DEBUG ===");
            
            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                calle == null || calle.trim().isEmpty() ||
                ciudad == null || ciudad.trim().isEmpty() ||
                codigoPostal == null || codigoPostal.trim().isEmpty() ||
                pais == null || pais.trim().isEmpty()) {
                
                System.out.println("ERROR: Faltan campos obligatorios");
                request.setAttribute("error", "Todos los campos son obligatorios");
                forwardToRegistro(request, response);
                return;
            }
            
            // Validar longitud de contraseña
            if (password.length() < 6) {
                System.out.println("ERROR: Contraseña muy corta");
                request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                forwardToRegistro(request, response);
                return;
            }
            
            // Validar formato de correo electrónico
            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("ERROR: Formato de correo inválido");
                request.setAttribute("error", "El formato del correo electrónico no es válido");
                forwardToRegistro(request, response);
                return;
            }
            
            // Verificar si el correo ya existe
            if (usuarioDAO.existeCorreo(correo)) {
                System.out.println("ERROR: Correo ya existe");
                request.setAttribute("error", "Ya existe un usuario con este correo electrónico");
                forwardToRegistro(request, response);
                return;
            }
            
            // Encriptar contraseña
            String passwordEncriptada = encriptarPassword(password);
            
            // Crear objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setIdUsuario(documento);
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(correo.trim().toLowerCase());
            nuevoUsuario.setTelefono(telefono.trim());
            nuevoUsuario.setPassword(passwordEncriptada);
            nuevoUsuario.setCalle(calle.trim());
            nuevoUsuario.setCiudad(ciudad.trim());
            nuevoUsuario.setCodigoPostal(codigoPostal.trim());
            nuevoUsuario.setPais(pais.trim());
            
            // Intentar agregar el usuario usando el método agregarUsuario
            System.out.println("Intentando agregar usuario: " + nuevoUsuario.toString());
            boolean registroExitoso = usuarioDAO.agregarUsuario(nuevoUsuario);
            System.out.println("Resultado del registro: " + registroExitoso);
            
            if (registroExitoso) {
                // Registro exitoso - redirigir a login
                System.out.println("Registro exitoso - redirigiendo a login.jsp");
                response.sendRedirect("login.jsp?registro=exitoso");
            } else {
                System.out.println("Error al registrar usuario - mostrando mensaje de error");
                request.setAttribute("error", "Error al registrar el usuario. Inténtalo de nuevo.");
                forwardToRegistro(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error en RegistroServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Inténtalo más tarde.");
            forwardToRegistro(request, response);
        }
    }
    
    private void forwardToRegistro(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("registro.jsp");
        dispatcher.forward(request, response);
    }
    
    private String encriptarPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.err.println("Error al encriptar contraseña: " + e.getMessage());
            // En caso de error, devolver la contraseña sin encriptar (no recomendado en producción)
            return password;
        }
    }
}
