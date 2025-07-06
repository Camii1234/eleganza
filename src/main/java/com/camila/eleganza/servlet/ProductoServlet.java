package com.camila.eleganza.servlet;

import java.io.IOException;
import java.util.Base64;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("productos.jsp");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            Producto producto = productoDAO.getProductoById(id);
            
            if (producto == null) {
                request.setAttribute("error", "Producto no encontrado");
                request.getRequestDispatcher("productos.jsp").forward(request, response);
                return;
            }
            
            // Procesar imagen si existe
            String imagenBase64 = null;
            if (producto.getImagen() != null && producto.getImagen().length > 0) {
                imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(producto.getImagen());
            }
            
            // Establecer atributos para el JSP
            request.setAttribute("producto", producto);
            request.setAttribute("imagenBase64", imagenBase64);
            
            // Obtener tallas disponibles para este tipo de producto
            request.setAttribute("tallasDisponibles", productoDAO.getTallas());
            
            // Redirigir al JSP de detalle del producto
            request.getRequestDispatcher("producto.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de producto inválido");
            request.getRequestDispatcher("productos.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor");
            request.getRequestDispatcher("productos.jsp").forward(request, response);
        }
    }
}