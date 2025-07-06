package com.camila.eleganza.servlet;

import java.io.IOException;
import com.camila.eleganza.dao.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/eliminarProducto")
public class EliminarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendRedirect("../pages/admin.jsp?error=id_requerido");
                return;
            }
            
            int id = Integer.parseInt(idStr);
            boolean success = productoDAO.deleteProducto(id);
            
            if (success) {
                response.sendRedirect("../pages/admin.jsp?success=producto_eliminado");
            } else {
                response.sendRedirect("../pages/admin.jsp?error=error_eliminar");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("../pages/admin.jsp?error=id_invalido");
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            response.sendRedirect("../pages/admin.jsp?error=error_servidor");
        }
    }
}