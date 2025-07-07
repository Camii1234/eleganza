package com.camila.eleganza.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.camila.eleganza.dao.ProductoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/eliminar-producto")
public class EliminarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            System.out.println("Inicializando EliminarProductoServlet...");
            productoDAO = new ProductoDAO();
            
            // Probar la conexión
            if (productoDAO.testConnection()) {
                System.out.println("ProductoDAO inicializado correctamente con conexión válida");
            } else {
                System.err.println("ProductoDAO inicializado pero sin conexión válida");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar ProductoDAO: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error al inicializar EliminarProductoServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== EliminarProductoServlet: Solicitud recibida ===");
        System.out.println("Método: " + request.getMethod());
        System.out.println("URL completa: " + request.getRequestURL());
        System.out.println("Query string: " + request.getQueryString());
        
        String mensaje;
        String tipo;
        
        try {
            // Obtener el ID del producto a eliminar
            String idParam = request.getParameter("id");
            System.out.println("ID del producto a eliminar: " + idParam);
            
            if (idParam == null || idParam.trim().isEmpty()) {
                mensaje = "ID de producto no válido";
                tipo = "error";
                System.out.println("Error: ID no válido o vacío");
            } else {
                int idProducto = Integer.parseInt(idParam.trim());
                System.out.println("Intentando eliminar producto con ID: " + idProducto);
                
                // Verificar que el DAO está inicializado
                if (productoDAO == null) {
                    System.err.println("Error: ProductoDAO no está inicializado");
                    mensaje = "Error interno: DAO no inicializado";
                    tipo = "error";
                } else {
                    // Llamar al método del DAO para eliminar el producto
                    boolean eliminado = productoDAO.deleteProducto(idProducto);
                    System.out.println("Resultado de eliminación: " + eliminado);
                    
                    if (eliminado) {
                        mensaje = "Producto eliminado exitosamente";
                        tipo = "success";
                        System.out.println("Producto eliminado con éxito");
                    } else {
                        mensaje = "Error al eliminar el producto. Verifique que el producto existe";
                        tipo = "error";
                        System.out.println("Error: No se pudo eliminar el producto");
                    }
                }
            }
        } catch (NumberFormatException e) {
            mensaje = "ID de producto no válido";
            tipo = "error";
            System.err.println("Error de formato de número: " + e.getMessage());
        } catch (Exception e) {
            mensaje = "Error interno del servidor: " + e.getMessage();
            tipo = "error";
            System.err.println("Error en EliminarProductoServlet: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Redirigir de vuelta a la página de administración con mensaje
        try {
            String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString());
            String redirectUrl = "admin.jsp?mensaje=" + tipo + "&texto=" + mensajeCodificado;
            System.out.println("Redirigiendo a: " + redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            System.err.println("Error al codificar mensaje: " + e.getMessage());
            response.sendRedirect("admin.jsp?mensaje=" + tipo + "&texto=Error+al+procesar+solicitud");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
