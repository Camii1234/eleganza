package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/AgregarProductoServlet")
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,      // 5MB
    maxRequestSize = 10 * 1024 * 1024,   // 10MB
    fileSizeThreshold = 1024 * 1024      // 1MB
)
public class AgregarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Extensiones de imagen permitidas
    private static final List<String> EXTENSIONES_PERMITIDAS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp"
    );
    
    // Tipos MIME permitidos
    private static final List<String> TIPOS_MIME_PERMITIDOS = Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/bmp"
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String categoria = request.getParameter("categoria");
            String stockStr = request.getParameter("stock");
            String estado = request.getParameter("estado");
            String talla = request.getParameter("talla");
            String descripcion = request.getParameter("descripcion");
            
            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty()) {
                redirigirConError(response, "nombre_requerido");
                return;
            }
            
            if (precioStr == null || precioStr.trim().isEmpty()) {
                redirigirConError(response, "precio_requerido");
                return;
            }
            
            if (categoria == null || categoria.trim().isEmpty()) {
                redirigirConError(response, "categoria_requerida");
                return;
            }
            
            // Validar y convertir precio
            double precio;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    redirigirConError(response, "precio_invalido");
                    return;
                }
            } catch (NumberFormatException e) {
                redirigirConError(response, "formato_precio");
                return;
            }
            
            // Validar y convertir stock
            int stock = 0;
            if (stockStr != null && !stockStr.trim().isEmpty()) {
                try {
                    stock = Integer.parseInt(stockStr);
                    if (stock < 0) {
                        stock = 0;
                    }
                } catch (NumberFormatException e) {
                    redirigirConError(response, "formato_numerico");
                    return;
                }
            }
            
            // Procesar tallas
            if (talla == null || talla.trim().isEmpty()) {
                talla = "Única";
            }
            
            // Establecer estado por defecto
            if (estado == null || estado.trim().isEmpty()) {
                estado = "Activo";
            }
            
            // Procesar imagen
            byte[] imagenBytes = null;
            Part imagenPart = request.getPart("imagen");
            
            if (imagenPart != null && imagenPart.getSize() > 0) {
                // Validar tipo de archivo
                String contentType = imagenPart.getContentType();
                String fileName = imagenPart.getSubmittedFileName();
                
                if (!validarTipoImagen(contentType, fileName)) {
                    redirigirConError(response, "tipo_archivo_invalido");
                    return;
                }
                
                // Validar tamaño (ya se valida con @MultipartConfig, pero por seguridad)
                if (imagenPart.getSize() > 5 * 1024 * 1024) {
                    redirigirConError(response, "archivo_muy_grande");
                    return;
                }
                
                // Leer imagen como bytes
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    imagenBytes = inputStream.readAllBytes();
                }
            }
            
            // Crear objeto Producto
            Producto producto = new Producto();
            producto.setNombre(nombre.trim());
            producto.setPrecio(precio);
            producto.setCategoria(categoria.trim());
            producto.setStock(stock);
            producto.setEstado(estado.trim());
            producto.setTalla(talla.trim());
            producto.setDescripcion(descripcion != null ? descripcion.trim() : "");
            producto.setImagen(imagenBytes);
            
            // Guardar en base de datos
            ProductoDAO productoDAO = new ProductoDAO();
            boolean exito = productoDAO.agregarProducto(producto);
            
            if (exito) {
                // Redireccionar con mensaje de éxito
                response.sendRedirect("agregar-producto.jsp?mensaje=producto_agregado");
            } else {
                redirigirConError(response, "error_base_datos");
            }
            
        } catch (Exception e) {
            System.err.println("Error en AgregarProductoServlet: " + e.getMessage());
            e.printStackTrace();
            redirigirConError(response, "error_interno");
        }
    }
    
    /**
     * Valida si el archivo es una imagen válida
     */
    private boolean validarTipoImagen(String contentType, String fileName) {
        // Validar tipo MIME
        if (contentType == null || !TIPOS_MIME_PERMITIDOS.contains(contentType.toLowerCase())) {
            return false;
        }
        
        // Validar extensión del archivo
        if (fileName != null && fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            return EXTENSIONES_PERMITIDAS.contains(extension);
        }
        
        return false;
    }
    
    /**
     * Redirecciona a la página de agregar producto con un mensaje de error
     */
    private void redirigirConError(HttpServletResponse response, String error) throws IOException {
        response.sendRedirect("agregar-producto.jsp?error=" + error);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireccionar al formulario si se accede por GET
        response.sendRedirect("agregar-producto.jsp");
    }
}
