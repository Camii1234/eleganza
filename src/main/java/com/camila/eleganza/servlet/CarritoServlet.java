package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
        gson = new Gson();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        try {
            // Obtener o crear carrito en sesión
            @SuppressWarnings("unchecked")
            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
                session.setAttribute("carrito", carrito);
            }
            
            switch (accion) {
                case "agregar":
                    agregarProducto(request, carrito, out);
                    break;
                case "remover":
                    removerProducto(request, carrito, out);
                    break;
                case "obtener":
                    obtenerCarrito(carrito, out);
                    break;
                case "limpiar":
                    limpiarCarrito(session, out);
                    break;
                default:
                    out.print("{\"error\": \"Acción no válida\"}");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            
            // Actualizar carrito en sesión
            session.setAttribute("carrito", carrito);
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void agregarProducto(HttpServletRequest request, List<Producto> carrito, PrintWriter out) {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            Producto producto = productoDAO.getProductoById(idProducto);
            
            if (producto != null && producto.getStock() > 0) {
                carrito.add(producto);
                out.print("{\"success\": true, \"mensaje\": \"Producto agregado al carrito\", \"total\": " + carrito.size() + "}");
            } else {
                out.print("{\"error\": \"Producto no disponible\"}");
            }
        } catch (NumberFormatException e) {
            out.print("{\"error\": \"ID de producto inválido\"}");
        }
    }
    
    private void removerProducto(HttpServletRequest request, List<Producto> carrito, PrintWriter out) {
        try {
            int indice = Integer.parseInt(request.getParameter("indice"));
            if (indice >= 0 && indice < carrito.size()) {
                Producto removido = carrito.remove(indice);
                out.print("{\"success\": true, \"mensaje\": \"Producto removido del carrito\", \"producto\": \"" + removido.getNombre() + "\", \"total\": " + carrito.size() + "}");
            } else {
                out.print("{\"error\": \"Índice inválido\"}");
            }
        } catch (NumberFormatException e) {
            out.print("{\"error\": \"Índice inválido\"}");
        }
    }
    
    private void obtenerCarrito(List<Producto> carrito, PrintWriter out) {
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.productos = new ArrayList<>();
        carritoDTO.total = 0.0;
        carritoDTO.cantidad = carrito.size();
        
        for (Producto p : carrito) {
            ProductoCarritoDTO dto = new ProductoCarritoDTO();
            dto.id = p.getIdProducto();
            dto.nombre = p.getNombre();
            dto.precio = p.getPrecio();
            dto.categoria = p.getCategoria();
            dto.talla = p.getTalla();
            
            carritoDTO.productos.add(dto);
            carritoDTO.total += p.getPrecio();
        }
        
        out.print(gson.toJson(carritoDTO));
    }
    
    private void limpiarCarrito(HttpSession session, PrintWriter out) {
        session.setAttribute("carrito", new ArrayList<Producto>());
        out.print("{\"success\": true, \"mensaje\": \"Carrito limpiado\", \"total\": 0}");
    }
    
    // DTOs para la respuesta JSON
    public static class CarritoDTO {
        public List<ProductoCarritoDTO> productos;
        public double total;
        public int cantidad;
    }
    
    public static class ProductoCarritoDTO {
        public int id;
        public String nombre;
        public double precio;
        public String categoria;
        public String talla;
    }
}
