package com.camila.eleganza.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;

@WebServlet("/perfil/actualizar")
public class ActualizarPerfilServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("../index.jsp");
            return;
        }
        
        try {
            // Obtener usuario desde sesión
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            
            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String calle = request.getParameter("calle");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String pais = request.getParameter("pais");
            String passwordActual = request.getParameter("passwordActual");
            String passwordNueva = request.getParameter("passwordNueva");
            
            // Validar campos obligatorios
            if (nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty()) {
                
                response.sendRedirect("../pages/perfil.jsp?error=campos_vacios");
                return;
            }
            
            // Actualizar datos del usuario
            usuarioLogueado.setNombre(nombre.trim());
            usuarioLogueado.setCorreo(correo.trim().toLowerCase());
            usuarioLogueado.setTelefono(telefono != null ? telefono.trim() : "");
            usuarioLogueado.setCalle(calle != null ? calle.trim() : "");
            usuarioLogueado.setCiudad(ciudad != null ? ciudad.trim() : "");
            usuarioLogueado.setCodigoPostal(codigoPostal != null ? codigoPostal.trim() : "");
            usuarioLogueado.setPais(pais != null ? pais.trim() : "");
            
            // Verificar si se quiere cambiar contraseña
            if (passwordActual != null && !passwordActual.trim().isEmpty() &&
                passwordNueva != null && !passwordNueva.trim().isEmpty()) {
                
                // Verificar contraseña actual
                if (!passwordActual.equals(usuarioLogueado.getPassword())) {
                    response.sendRedirect("../pages/perfil.jsp?error=password_incorrecta");
                    return;
                }
                
                usuarioLogueado.setPassword(passwordNueva);
            }
            
            // Actualizar en base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean actualizado = usuarioDAO.actualizarUsuario(usuarioLogueado);
            
            if (actualizado) {
                // Actualizar sesión
                session.setAttribute("usuarioLogueado", usuarioLogueado);
                session.setAttribute("nombreUsuario", usuarioLogueado.getNombre());
                
                response.sendRedirect("../pages/perfil.jsp?success=true");
            } else {
                response.sendRedirect("../pages/perfil.jsp?error=error_servidor");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("../pages/perfil.jsp?error=error_servidor");
        }
    }
}