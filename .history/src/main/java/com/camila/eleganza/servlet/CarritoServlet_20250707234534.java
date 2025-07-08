package com.camila.eleganza.servlet;

import java.io.IOException;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Carrito;
import com.camila.eleganza.model.ItemCarrito;
import com.camila.eleganza.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        
        // Verificar si el usuario está logueado (opcional, según tus reglas de negocio)
        // Si quieres que solo usuarios logueados puedan usar el carrito, descomenta esto:
        /*
        if (session.getAttribute("usuario") == null) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Debes iniciar sesión para usar el carrito\"}");
            } else {
                response.sendRedirect("login.jsp");
            }
            return;
        }
        */
        
        // Obtener o crear el carrito en la sesión
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }
        
        try {
            if ("add".equals(action)) {
                agregarProducto(request, response, carrito);
            } else if ("update".equals(action)) {
                actualizarCantidad(request, response, carrito);
            } else if ("remove".equals(action)) {
                eliminarProducto(request, response, carrito);
            } else if ("clear".equals(action)) {
                vaciarCarrito(request, response, carrito);
            } else if ("count".equals(action)) {
                obtenerCantidadCarrito(response, carrito);
            } else {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"Acción no válida\"}");
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error de formato de número en CarritoServlet: " + e.getMessage());
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Parámetros inválidos\"}");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            }
        } catch (Exception e) {
            System.err.println("Error en CarritoServlet: " + e.getMessage());
            e.printStackTrace();
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Error interno del servidor\"}");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
            }
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
    }
    
    private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        String productoIdStr = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        String cantidadStr = request.getParameter("cantidad");
        
        // Validar parámetros
        if (productoIdStr == null || talla == null || cantidadStr == null) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Parámetros faltantes\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            return;
        }
        
        try {
            int productoId = Integer.parseInt(productoIdStr);
            int cantidad = Integer.parseInt(cantidadStr);
            
            // Validar cantidad
            if (cantidad < 0) {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"La cantidad debe ser mayor o igual a 0\"}");
                } else {
                    response.sendRedirect("carrito.jsp");
                }
                return;
            }
            
            // Verificar si el producto existe en el carrito
            boolean encontrado = false;
            for (ItemCarrito item : carrito.getItems()) {
                if (item.esMismoProducto(productoId, talla)) {
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"Producto no encontrado en el carrito\"}");
                } else {
                    response.sendRedirect("carrito.jsp");
                }
                return;
            }
            
            // Actualizar cantidad
            carrito.actualizarCantidad(productoId, talla, cantidad);
            
            // Si es una solicitud AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": true, \"total\": " + carrito.getCantidadTotal() + ", \"message\": \"Cantidad actualizada exitosamente\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            
        } catch (NumberFormatException e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Formato de número inválido\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
        }
    }
    
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        String productoIdStr = request.getParameter("productoId");
        String talla = request.getParameter("talla");
        
        // Validar parámetros
        if (productoIdStr == null || talla == null) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Parámetros faltantes\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            return;
        }
        
        try {
            int productoId = Integer.parseInt(productoIdStr);
            
            // Verificar si el producto existe en el carrito
            boolean encontrado = false;
            for (ItemCarrito item : carrito.getItems()) {
                if (item.esMismoProducto(productoId, talla)) {
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"Producto no encontrado en el carrito\"}");
                } else {
                    response.sendRedirect("carrito.jsp");
                }
                return;
            }
            
            // Eliminar producto
            carrito.eliminarItem(productoId, talla);
            
            // Si es una solicitud AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": true, \"total\": " + carrito.getCantidadTotal() + ", \"message\": \"Producto eliminado del carrito exitosamente\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            
        } catch (NumberFormatException e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Formato de número inválido\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
        }
    }
    
    private void vaciarCarrito(HttpServletRequest request, HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        try {
            // Verificar si el carrito tiene productos
            if (carrito.estaVacio()) {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"El carrito ya está vacío\"}");
                } else {
                    response.sendRedirect("carrito.jsp");
                }
                return;
            }
            
            // Vaciar el carrito
            carrito.vaciarCarrito();
            
            // Si es una solicitud AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": true, \"total\": 0, \"message\": \"Carrito vaciado exitosamente\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
            
        } catch (Exception e) {
            System.err.println("Error al vaciar carrito: " + e.getMessage());
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"success\": false, \"message\": \"Error interno al vaciar el carrito\"}");
            } else {
                response.sendRedirect("carrito.jsp");
            }
        }
    }
    
    private void obtenerCantidadCarrito(HttpServletResponse response, Carrito carrito) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"count\": " + carrito.getCantidadTotal() + "}");
    }
}
