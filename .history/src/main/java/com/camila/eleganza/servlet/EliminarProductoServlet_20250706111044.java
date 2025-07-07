package com.camila.eleganza.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.camila.eleganza.dao.ProductoDAO;

@WebServlet("/EliminarProductoServlet")
public class EliminarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String mensaje;
        String tipo;
        
        try {
            // Obtener el ID del producto a eliminar
            String idParam = request.getParameter("id");
            
            if (idParam == null || idParam.trim().isEmpty()) {
                mensaje = "ID de producto no válido";
                tipo = "error";
            } else {
                int idProducto = Integer.parseInt(idParam);
                
                // Llamar al método del DAO para eliminar el producto
                boolean eliminado = productoDAO.deleteProducto(idProducto);
                
                if (eliminado) {
                    mensaje = "Producto eliminado exitosamente";
                    tipo = "success";
                } else {
                    mensaje = "Error al eliminar el producto. Verifique que el producto existe";
                    tipo = "error";
                }
            }
        } catch (NumberFormatException e) {
            mensaje = "ID de producto no válido";
            tipo = "error";
        } catch (Exception e) {
            mensaje = "Error interno del servidor: " + e.getMessage();
            tipo = "error";
            System.err.println("Error en EliminarProductoServlet: " + e.getMessage());
        }
        
        // Redirigir de vuelta a la página de administración con mensaje
        response.sendRedirect("admin.jsp?mensaje=" + tipo + "&texto=" + mensaje);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
