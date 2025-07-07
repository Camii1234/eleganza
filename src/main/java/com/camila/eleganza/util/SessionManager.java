package com.camila.eleganza.util;

import com.camila.eleganza.model.Usuario;

import jakarta.servlet.http.HttpSession;

/**
 * Clase utilitaria para gestionar sesiones de usuario
 */
public class SessionManager {
    
    // Constantes para las claves de sesión
    public static final String SESSION_USUARIO = "usuario";
    public static final String SESSION_USUARIO_ID = "usuarioId";
    public static final String SESSION_USUARIO_NOMBRE = "usuarioNombre";
    public static final String SESSION_USUARIO_CORREO = "usuarioCorreo";
    public static final String SESSION_LOGGED_IN = "loggedIn";
    public static final String SESSION_IS_ADMIN = "isAdmin";
    
    /**
     * Crear sesión de usuario
     */
    public static void crearSesion(HttpSession session, Usuario usuario) {
        session.setAttribute(SESSION_USUARIO, usuario);
        session.setAttribute(SESSION_USUARIO_ID, usuario.getIdUsuario());
        session.setAttribute(SESSION_USUARIO_NOMBRE, usuario.getNombre());
        session.setAttribute(SESSION_USUARIO_CORREO, usuario.getCorreo());
        session.setAttribute(SESSION_LOGGED_IN, true);
        
        // Configurar tiempo de sesión (30 minutos)
        session.setMaxInactiveInterval(30 * 60);
        
        System.out.println("Sesión creada para usuario: " + usuario.getNombre());
    }
    
    /**
     * Crear sesión de administrador
     */
    public static void crearSesionAdmin(HttpSession session, Usuario usuario) {
        crearSesion(session, usuario);
        session.setAttribute(SESSION_IS_ADMIN, true);
        System.out.println("Sesión de administrador creada para: " + usuario.getNombre());
    }
    
    /**
     * Obtener usuario de la sesión
     */
    public static Usuario getUsuarioSesion(HttpSession session) {
        if (session != null) {
            return (Usuario) session.getAttribute(SESSION_USUARIO);
        }
        return null;
    }
    
    /**
     * Verificar si el usuario está logueado
     */
    public static boolean isUsuarioLogueado(HttpSession session) {
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute(SESSION_USUARIO);
            Boolean loggedIn = (Boolean) session.getAttribute(SESSION_LOGGED_IN);
            return usuario != null && loggedIn != null && loggedIn;
        }
        return false;
    }
    
    /**
     * Verificar si el usuario es administrador
     */
    public static boolean isAdmin(HttpSession session) {
        if (session != null) {
            Boolean isAdmin = (Boolean) session.getAttribute(SESSION_IS_ADMIN);
            return isAdmin != null && isAdmin;
        }
        return false;
    }
    
    /**
     * Actualizar datos del usuario en la sesión
     */
    public static void actualizarSesion(HttpSession session, Usuario usuario) {
        if (session != null) {
            session.setAttribute(SESSION_USUARIO, usuario);
            session.setAttribute(SESSION_USUARIO_NOMBRE, usuario.getNombre());
            session.setAttribute(SESSION_USUARIO_CORREO, usuario.getCorreo());
            System.out.println("Sesión actualizada para usuario: " + usuario.getNombre());
        }
    }
    
    /**
     * Cerrar sesión
     */
    public static void cerrarSesion(HttpSession session) {
        if (session != null) {
            String nombreUsuario = (String) session.getAttribute(SESSION_USUARIO_NOMBRE);
            session.invalidate();
            System.out.println("Sesión cerrada para usuario: " + nombreUsuario);
        }
    }
    
    /**
     * Obtener nombre del usuario de la sesión
     */
    public static String getNombreUsuario(HttpSession session) {
        if (session != null) {
            return (String) session.getAttribute(SESSION_USUARIO_NOMBRE);
        }
        return null;
    }
    
    /**
     * Obtener correo del usuario de la sesión
     */
    public static String getCorreoUsuario(HttpSession session) {
        if (session != null) {
            return (String) session.getAttribute(SESSION_USUARIO_CORREO);
        }
        return null;
    }
    
    /**
     * Obtener ID del usuario de la sesión
     */
    public static Integer getIdUsuario(HttpSession session) {
        if (session != null) {
            return (Integer) session.getAttribute(SESSION_USUARIO_ID);
        }
        return null;
    }
}
