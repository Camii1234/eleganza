package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ObtenerProductoServlet")
public class ObtenerProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final ProductoDAO productoDAO;
    
    public ObtenerProductoServlet() {
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
            Producto producto = productoDAO.getProductoById(productoId);
            
            if (producto != null) {
                JsonObject productoJson = new JsonObject();
                productoJson.addProperty("id", producto.getIdProducto());
                productoJson.addProperty("nombre", producto.getNombre());
                productoJson.addProperty("precio", producto.getPrecio());
                productoJson.addProperty("talla", producto.getTalla());
                productoJson.addProperty("categoria", producto.getCategoria());
                productoJson.addProperty("stock", producto.getStock());
                productoJson.addProperty("estado", producto.getEstado());
                productoJson.addProperty("descripcion", producto.getDescripcion());
                
                // Convertir imagen a base64 si existe
                if (producto.getImagen() != null && producto.getImagen().length > 0) {
                    String imagenBase64 = Base64.getEncoder().encodeToString(producto.getImagen());
                    productoJson.addProperty("imagen", "data:image/jpeg;base64," + imagenBase64);
                } else {
                    productoJson.addProperty("imagen", "");
                }
                
                jsonResponse.addProperty("success", true);
                jsonResponse.add("producto", productoJson);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Producto no encontrado");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "ID de producto inválido");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error interno del servidor");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
