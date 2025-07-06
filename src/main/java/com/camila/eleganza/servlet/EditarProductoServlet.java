package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.InputStream;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/admin/editarProducto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EditarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Producto producto = productoDAO.getProductoById(id);
                
                if (producto != null) {
                    request.setAttribute("producto", producto);
                    request.getRequestDispatcher("/pages/editar-producto.jsp").forward(request, response);
                } else {
                    response.sendRedirect("../pages/admin.jsp?error=producto_no_encontrado");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("../pages/admin.jsp?error=id_invalido");
            }
        } else {
            response.sendRedirect("../pages/admin.jsp?error=id_requerido");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    
        // Configurar encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener ID del producto
            String idStr = request.getParameter("id");
            System.out.println("DEBUG: ID recibido: " + idStr);
            
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendRedirect("../pages/admin.jsp?error=id_requerido");
                return;
            }
            
            int id = Integer.parseInt(idStr);
            
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String talla = request.getParameter("talla");
            String categoria = request.getParameter("categoria");
            String stockStr = request.getParameter("stock");
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            
            System.out.println("DEBUG: Parámetros recibidos:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Precio: " + precioStr);
            System.out.println("Talla: " + talla);
            System.out.println("Categoria: " + categoria);
            System.out.println("Stock: " + stockStr);
            System.out.println("Estado: " + estado);
            
            // Validar campos obligatorios
            if (nombre == null || nombre.trim().isEmpty() ||
                precioStr == null || precioStr.trim().isEmpty() ||
                talla == null || talla.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty() ||
                stockStr == null || stockStr.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty()) {
                
                response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=campos_vacios");
                return;
            }
            
            // Convertir valores numéricos
            double precio;
            int stock;
            
            try {
                precio = Double.parseDouble(precioStr);
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                System.err.println("Error en conversión numérica: " + e.getMessage());
                response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=formato_numerico");
                return;
            }
            
            // Validar valores positivos
            if (precio <= 0 || stock < 0) {
                response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=valores_invalidos");
                return;
            }
            
            // Obtener archivo de imagen
            Part imagenPart = request.getPart("imagen");
            boolean actualizarImagen = false;
            byte[] imagenBytes = null;
            
            if (imagenPart != null && imagenPart.getSize() > 0) {
                String fileName = imagenPart.getSubmittedFileName();
                if (fileName != null && !fileName.trim().isEmpty()) {
                    // Validar tipo de archivo
                    String contentType = imagenPart.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=tipo_archivo_invalido");
                        return;
                    }
                    
                    // Validar extensión
                    String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                    if (!extension.matches("\\.(jpg|jpeg|png|gif|bmp)$")) {
                        response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=extension_invalida");
                        return;
                    }
                    
                    // Validar tamaño (5MB máximo)
                    if (imagenPart.getSize() > 5 * 1024 * 1024) {
                        response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=archivo_muy_grande");
                        return;
                    }
                    
                    // Leer imagen
                    try (InputStream inputStream = imagenPart.getInputStream()) {
                        imagenBytes = inputStream.readAllBytes();
                        actualizarImagen = true;
                    }
                }
            }
            
            // Crear objeto Producto
            Producto producto = new Producto();
            producto.setIdProducto(id);
            producto.setNombre(nombre.trim());
            producto.setPrecio(precio);
            producto.setTalla(talla.trim());
            producto.setCategoria(categoria.trim());
            producto.setStock(stock);
            producto.setEstado(estado.trim());
            producto.setDescripcion(descripcion != null ? descripcion.trim() : "");
            
            // Solo actualizar imagen si se subió una nueva
            if (actualizarImagen) {
                producto.setImagen(imagenBytes);
            }
            
            // Actualizar en base de datos
            boolean success = productoDAO.actualizarProducto(producto, actualizarImagen);
            
            System.out.println("DEBUG: Resultado de actualización: " + success);
            
            if (success) {
                response.sendRedirect("../pages/admin.jsp?success=producto_actualizado");
            } else {
                response.sendRedirect("../pages/editar-producto.jsp?id=" + id + "&error=error_actualizar");
            }
            
        } catch (Exception e) {
            System.err.println("Error al editar producto: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("../pages/admin.jsp?error=error_servidor");
        }
    }
}