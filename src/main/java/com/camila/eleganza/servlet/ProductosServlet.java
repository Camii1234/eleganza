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

@WebServlet("/productos")
public class ProductosServlet extends HttpServlet {
    
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String categoria = request.getParameter("categoria");
        
        try {
            List<Producto> productos;
            
            if (categoria != null && !categoria.equals("all")) {
                productos = productoDAO.getProductosByCategoria(categoria);
            } else {
                productos = productoDAO.getAllProductos();
            }
            
            // Convertir a DTO
            List<ProductoDTO> productosDTO = new ArrayList<>();
            for (Producto p : productos) {
                ProductoDTO dto = new ProductoDTO();
                dto.id = p.getIdProducto();
                dto.nombre = p.getNombre();
                dto.precio = p.getPrecio();
                dto.talla = p.getTalla();
                dto.categoria = p.getCategoria();
                dto.stock = p.getStock();
                dto.estado = p.getEstado();
                dto.descripcion = p.getDescripcion();
                
                // Imagen
                if (p.getImagen() != null && p.getImagen().length > 0) {
                    dto.imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(p.getImagen());
                } else {
                    dto.imagenBase64 = "../img/placeholder.jpg";
                }
                
                productosDTO.add(dto);
            }
            
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(productosDTO));
            out.flush();
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
    
    // Clase DTO
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
