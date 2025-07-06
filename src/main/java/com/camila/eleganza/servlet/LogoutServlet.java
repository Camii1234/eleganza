package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Invalidar sesión HTTP
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Respuesta JSON
        Gson gson = new Gson();
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.success = true;
        logoutResponse.message = "Sesión cerrada exitosamente";
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(logoutResponse));
        out.flush();
    }
    
    public static class LogoutResponse {
        public boolean success;
        public String message;
    }
}