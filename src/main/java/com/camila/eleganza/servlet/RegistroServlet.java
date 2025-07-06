package com.camila.eleganza.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del formulario
            String documento = request.getParameter("documento");
            String nombre = request.getParameter("fullName");
            String correo = request.getParameter("registerEmail");
            String telefono = request.getParameter("phone");
            String calle = request.getParameter("street");
            String ciudad = request.getParameter("city");
            String codigoPostal = request.getParameter("zipCode");
            String pais = request.getParameter("country");
            String password = request.getParameter("registerPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            System.out.println("=== REGISTRO DE USUARIO ===");
            System.out.println("Documento: " + documento);
            System.out.println("Nombre: " + nombre);
            System.out.println("Correo: " + correo);

            // Validar campos obligatorios
            if (documento == null || documento.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
                
                System.out.println("Error: Campos vacíos");
                response.sendRedirect("pages/registro.jsp?error=campos_vacios&" + getParametersString(request));
                return;
            }

            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                System.out.println("Error: Contraseñas no coinciden");
                response.sendRedirect("pages/registro.jsp?error=password_mismatch&" + getParametersString(request));
                return;
            }

            // Validar longitud mínima de contraseña
            if (password.length() < 6) {
                System.out.println("Error: Contraseña muy corta");
                response.sendRedirect("pages/registro.jsp?error=password_corta&" + getParametersString(request));
                return;
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setDocumento(documento.trim());
            usuario.setNombre(nombre.trim());
            usuario.setCorreo(correo.trim().toLowerCase());
            usuario.setTelefono(telefono != null ? telefono.trim() : "");
            usuario.setCalle(calle != null ? calle.trim() : "");
            usuario.setCiudad(ciudad != null ? ciudad.trim() : "");
            usuario.setCodigoPostal(codigoPostal != null ? codigoPostal.trim() : "");
            usuario.setPais(pais != null ? pais.trim() : "");
            usuario.setPassword(password); // En un entorno real, deberías encriptar la contraseña

            // Guardar en base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Verificar si el correo ya existe
            if (usuarioDAO.existeCorreo(correo.trim().toLowerCase())) {
                System.out.println("Error: Correo ya existe");
                response.sendRedirect("pages/registro.jsp?error=correo_existente&" + getParametersString(request));
                return;
            }
            
            // Verificar si el documento ya existe
            try {
                Usuario usuarioExistente = usuarioDAO.getUsuarioById(Integer.parseInt(documento.trim()));
                if (usuarioExistente != null) {
                    System.out.println("Error: Documento ya existe");
                    response.sendRedirect("pages/registro.jsp?error=documento_existente&" + getParametersString(request));
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Documento inválido");
                response.sendRedirect("pages/registro.jsp?error=documento_invalido&" + getParametersString(request));
                return;
            }
            
            // Intentar guardar el usuario
            boolean guardado = usuarioDAO.agregarUsuario(usuario);

            if (guardado) {
                System.out.println("Usuario registrado exitosamente!");
                response.sendRedirect("pages/registro.jsp?success=true");
            } else {
                System.out.println("Error al guardar usuario en BD");
                response.sendRedirect("pages/registro.jsp?error=error_servidor&" + getParametersString(request));
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: Documento debe ser numérico: " + e.getMessage());
            response.sendRedirect("pages/registro.jsp?error=documento_invalido&" + getParametersString(request));
        } catch (Exception e) {
            System.err.println("Error general en registro: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("pages/registro.jsp?error=error_servidor&" + getParametersString(request));
        }
    }
    
    // Método auxiliar para preservar los datos del formulario en caso de error
    private String getParametersString(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        String[] fields = {"documento", "fullName", "registerEmail", "phone", "street", "city", "zipCode", "country"};
        
        for (String field : fields) {
            String value = request.getParameter(field);
            if (value != null && !value.trim().isEmpty()) {
                if (params.length() > 0) params.append("&");
                try {
                    params.append(field).append("=").append(java.net.URLEncoder.encode(value.trim(), "UTF-8"));
                } catch (Exception e) {
                    params.append(field).append("=").append(value.trim());
                }
            }
        }
        
        return params.toString();
    }
}
