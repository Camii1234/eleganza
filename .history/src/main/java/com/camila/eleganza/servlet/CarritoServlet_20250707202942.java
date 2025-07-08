package com.camila.eleganza.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Carrito;
import com.camila.eleganza.model.ItemCarrito;
import com.camila.eleganza.model.Producto;
import com.google.gson.Gson;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {
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
        // Redirigir a la página del carrito
        response.sendRedirect("carrito.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        // Obtener o crear el carrito en la sesión
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }
        
        try {
            switch (action) {
                case "add":
                    agregarProducto(request, response, carrito);
                    break;
                case "update":
                    actualizarCantidad(request, response, carrito);
                    break;
                case "remove":
                    eliminarProducto(request, response, carrito);
                    break;
                case "clear":
                    vaciarCarrito(request, response, carrito);
                    break;
                case "count":
                    obtenerCantidadCarrito(request, response, carrito);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                    return;
            }
        } catch (Exception e) {
            System.err.println("Error en CarritoServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }
    
    private void agregarProducto(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        String productoIdStr = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        String cantidadStr = request.getParameter("cantidad");
        
        if (productoIdStr == null || talla == null || cantidadStr == null) {
            request.setAttribute("error", "Faltan parámetros obligatorios");
            request.getRequestDispatcher("producto.jsp").forward(request, response);
            return;
        }
        
        try {
            int productoId = Integer.parseInt(productoIdStr);
            int cantidad = Integer.parseInt(cantidadStr);
            
            if (cantidad <= 0) {
                request.setAttribute("error", "La cantidad debe ser mayor a 0");
                request.getRequestDispatcher("producto.jsp?id=" + productoId).forward(request, response);
                return;
            }
            
            // Obtener el producto de la base de datos
            Producto producto = productoDAO.getProductoById(productoId);
            
            if (producto == null) {
                request.setAttribute("error", "Producto no encontrado");
                request.getRequestDispatcher("productos.jsp").forward(request, response);
                return;
            }
            
            // Verificar stock disponible
            if (producto.getStock() < cantidad) {
                request.setAttribute("error", "No hay suficiente stock disponible");
                request.getRequestDispatcher("producto.jsp?id=" + productoId).forward(request, response);
                return;
            }
            
            // Verificar si el producto ya está en el carrito
            int cantidadEnCarrito = 0;
            for (ItemCarrito item : carrito.getItems()) {
                if (item.esMismoProducto(productoId, talla)) {
                    cantidadEnCarrito = item.getCantidad();
                    break;
                }
            }
            
            if (cantidadEnCarrito + cantidad > producto.getStock()) {
                request.setAttribute("error", "No puedes agregar más productos de los disponibles en stock");
                request.getRequestDispatcher("producto.jsp?id=" + productoId).forward(request, response);
                return;
            }
            
            // Crear item del carrito
            ItemCarrito item = new ItemCarrito(producto, talla, cantidad);
            carrito.agregarItem(item);
            
            // Establecer mensaje de éxito
            request.setAttribute("success", "Producto agregado al carrito exitosamente");
            request.setAttribute("carritoCount", carrito.getCantidadTotal());
            
            // Redirigir de vuelta al producto
            response.sendRedirect("producto.jsp?id=" + productoId + "&added=true");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Parámetros inválidos");
            request.getRequestDispatcher("productos.jsp").forward(request, response);
        }
    }
    
    private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        String productoIdStr = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        String cantidadStr = request.getParameter("cantidad");
        
        try {
            int productoId = Integer.parseInt(productoIdStr);
            int cantidad = Integer.parseInt(cantidadStr);
            
            carrito.actualizarCantidad(productoId, talla, cantidad);
            
            // Si es una solicitud AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true, \"total\": " + carrito.getCantidadTotal() + "}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            
        } catch (NumberFormatException e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"error\": \"Parámetros inválidos\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
        }
    }
    
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        String productoIdStr = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        
        try {
            int productoId = Integer.parseInt(productoIdStr);
            carrito.eliminarItem(productoId, talla);
            
            // Si es una solicitud AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true, \"total\": " + carrito.getCantidadTotal() + "}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            
        } catch (NumberFormatException e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"error\": \"Parámetros inválidos\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
        }
    }
    
    private void vaciarCarrito(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        carrito.vaciarCarrito();
        
        // Si es una solicitud AJAX, devolver JSON
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"total\": 0}");
        } else {
            response.sendRedirect("carrito.jsp");
        }
    }
    
    private void obtenerCantidadCarrito(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.getWriter().write("{\"count\": " + carrito.getCantidadTotal() + "}");
    }
}
