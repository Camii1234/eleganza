package com.camila.eleganza.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

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
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener datos del formulario
            String documento = request.getParameter("documento");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String calle = request.getParameter("calle");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String pais = request.getParameter("pais");
            
            System.out.println("Registro attempt - Email: " + correo + ", Documento: " + documento);
            
            // Validar campos obligatorios
            if (documento == null || documento.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty() ||
                calle == null || calle.trim().isEmpty() ||
                ciudad == null || ciudad.trim().isEmpty() ||
                codigoPostal == null || codigoPostal.trim().isEmpty() ||
                pais == null || pais.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar longitud de la contraseña
            if (password.length() < 6) {
                request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar formato de correo
            if (!isValidEmail(correo)) {
                request.setAttribute("error", "El formato del correo electrónico no es válido");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el correo ya existe
            if (usuarioDAO.existeCorreo(correo.trim().toLowerCase())) {
                request.setAttribute("error", "Ya existe una cuenta con este correo electrónico");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el documento ya existe
            if (usuarioDAO.existeDocumento(documento.trim())) {
                request.setAttribute("error", "Ya existe una cuenta con este documento de identidad");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }
            
            // Crear el objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setDocumento(documento.trim());
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(correo.trim().toLowerCase());
            nuevoUsuario.setTelefono(telefono.trim());
            nuevoUsuario.setPassword(hashPassword(password)); // Hash de la contraseña
            nuevoUsuario.setCalle(calle.trim());
            nuevoUsuario.setCiudad(ciudad.trim());
            nuevoUsuario.setCodigoPostal(codigoPostal.trim());
            nuevoUsuario.setPais(pais.trim());
            
            // Intentar registrar el usuario
            boolean registroExitoso = usuarioDAO.agregarUsuario(nuevoUsuario);
            
            if (registroExitoso) {
                System.out.println("Usuario registrado exitosamente: " + correo);
                
                // Crear sesión automáticamente después del registro
                Usuario usuarioRegistrado = usuarioDAO.buscarUsuarioPorCorreo(correo.trim().toLowerCase());
                if (usuarioRegistrado != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuarioRegistrado);
                    session.setAttribute("loggedIn", true);
                    session.setMaxInactiveInterval(30 * 60); // 30 minutos
                    
                    // Redirigir a página de bienvenida o perfil
                    response.sendRedirect("perfil.jsp?registro=exitoso");
                } else {
                    // Si no se puede recuperar el usuario, redirigir al login
                    request.setAttribute("success", "¡Registro exitoso! Ya puedes iniciar sesión con tu cuenta.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } else {
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
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a la página de registro
        response.sendRedirect("registro.jsp");
    }
    
    /**
     * Valida el formato de un correo electrónico
     * @param email El correo a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Genera un hash de la contraseña usando SHA-256
     * @param password La contraseña en texto plano
     * @return El hash de la contraseña
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.err.println("Error al generar hash de contraseña: " + e.getMessage());
            // En caso de error, devolver la contraseña sin hash (no recomendado para producción)
            return password;
        }
    }
}
