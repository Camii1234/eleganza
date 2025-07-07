package com.camila.eleganza.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.camila.eleganza.dao.UsuarioDAO;
import com.camila.eleganza.model.Usuario;
import com.camila.eleganza.util.SessionManager;

@WebServlet(name = "ActualizarPerfilServlet", urlPatterns = {"/actualizarPerfil"})
public class ActualizarPerfilServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Establecer codificación UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Verificar si el usuario está logueado
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = SessionManager.getUsuarioSesion(session);
        
        if (usuarioLogueado == null) {
            response.sendRedirect("login.jsp?redirect=perfil.jsp");
            return;
        }
        
        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String pais = request.getParameter("pais");
        String ciudad = request.getParameter("ciudad");
        String codigoPostal = request.getParameter("codigoPostal");
        String calle = request.getParameter("calle");
        
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            telefono == null || telefono.trim().isEmpty() ||
            pais == null || pais.trim().isEmpty() ||
            ciudad == null || ciudad.trim().isEmpty() ||
            calle == null || calle.trim().isEmpty()) {
            
            request.setAttribute("error", "Por favor, complete todos los campos obligatorios.");
            request.getRequestDispatcher("perfil.jsp").forward(request, response);
            return;
        }
        
        try {
            // Verificar si el correo ya existe (excepto el propio)
            if (!correo.equals(usuarioLogueado.getCorreo())) {
                if (usuarioDAO.existeCorreo(correo)) {
                    request.setAttribute("error", "El correo electrónico ya está registrado.");
                    request.getRequestDispatcher("perfil.jsp").forward(request, response);
                    return;
                }
            }
            
            // Actualizar datos del usuario
            usuarioLogueado.setNombre(nombre.trim());
            usuarioLogueado.setCorreo(correo.trim());
            usuarioLogueado.setTelefono(telefono.trim());
            usuarioLogueado.setPais(pais.trim());
            usuarioLogueado.setCiudad(ciudad.trim());
            usuarioLogueado.setCodigoPostal(codigoPostal != null ? codigoPostal.trim() : "");
            usuarioLogueado.setCalle(calle.trim());
            
            // Actualizar en la base de datos
            if (usuarioDAO.actualizarUsuario(usuarioLogueado)) {
                // Actualizar la sesión con los nuevos datos
                session.setAttribute("usuario", usuarioLogueado);
                session.setAttribute("usuarioNombre", usuarioLogueado.getNombre());
                session.setAttribute("usuarioCorreo", usuarioLogueado.getCorreo());
                
                request.setAttribute("success", "Perfil actualizado exitosamente.");
                request.getRequestDispatcher("perfil.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al actualizar el perfil. Intente nuevamente.");
                request.getRequestDispatcher("perfil.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor. Intente nuevamente.");
            request.getRequestDispatcher("perfil.jsp").forward(request, response);
        }
    }
}
