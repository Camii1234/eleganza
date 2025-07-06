package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camila.eleganza.model.Usuario;
import com.google.gson.Gson;

@WebServlet("/auth/check-session")
public class SessionCheckServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        Gson gson = new Gson();
        
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
            
            // Usuario logueado
            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.loggedIn = true;
            sessionResponse.user = new UserInfo();
            sessionResponse.user.id = String.valueOf(usuario.getIdUsuario());
            sessionResponse.user.nombre = usuario.getNombre();
            sessionResponse.user.correo = usuario.getCorreo();
            sessionResponse.user.esAdmin = esAdmin != null ? esAdmin : false;
            
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(sessionResponse));
            out.flush();
        } else {
            // No hay sesión activa
            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.loggedIn = false;
            sessionResponse.user = null;
            
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(sessionResponse));
            out.flush();
        }
    }
    
    public static class SessionResponse {
        public boolean loggedIn;
        public UserInfo user;
    }
    
    public static class UserInfo {
        public String id;
        public String nombre;
        public String correo;
        public boolean esAdmin;
    }
}