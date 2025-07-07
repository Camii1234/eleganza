package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/productos")
public class AdminProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
        gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener todos los productos (activos e inactivos para admin)
            List<Producto> productos = productoDAO.getAllProductosAdmin();
            
            // Convertir a DTO para respuesta JSON
            List<ProductoDTO> productosDTO = new ArrayList<>();
            for (Producto producto : productos) {
                productosDTO.add(convertToDTO(producto));
            }
            
            // Enviar respuesta JSON
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(productosDTO));
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"Error al obtener productos: " + e.getMessage() + "\"}");
        }
    }
    
    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.id = producto.getIdProducto();
        dto.nombre = producto.getNombre();
        dto.precio = producto.getPrecio();
        dto.talla = producto.getTalla();
        dto.categoria = producto.getCategoria();
        dto.stock = producto.getStock();
        dto.estado = producto.getEstado();
        dto.descripcion = producto.getDescripcion();
        
        // Convertir imagen a base64 si existe
        if (producto.getImagen() != null && producto.getImagen().length > 0) {
            dto.imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(producto.getImagen());
        } else {
            dto.imagenBase64 = "../img/placeholder.jpg";
        }
        
        return dto;
    }
    
    // Clase DTO para la respuesta JSON
    public static class ProductoDTO {
        public int id;
        public String nombre;
        public double precio;
        public String talla;
        public String categoria;
        public int stock;
        public String estado;
        public String descripcion;
        public String imagenBase64;
    }
}
