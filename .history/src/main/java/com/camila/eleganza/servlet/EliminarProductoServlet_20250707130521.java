package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.camila.eleganza.dao.ProductoDAO;
import com.google.gson.JsonObject;

@WebServlet("/EliminarProductoServlet")
public class EliminarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final ProductoDAO productoDAO;
    
    public EliminarProductoServlet() {
        super();
        this.productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            String idStr = request.getParameter("id");
            
            if (idStr == null || idStr.trim().isEmpty()) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "ID de producto no válido");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonResponse.toString());
                return;
            }
            
            int productoId = Integer.parseInt(idStr);
            
            boolean eliminado = productoDAO.eliminarProducto(productoId);
            
            if (eliminado) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Producto eliminado exitosamente");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "No se pudo eliminar el producto");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "ID de producto inválido");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error interno del servidor");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
