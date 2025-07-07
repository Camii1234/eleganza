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

@WebServlet("/admin/nuevoProducto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AgregarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String categoria = request.getParameter("categoria");
            String talla = request.getParameter("talla");
            String stockStr = request.getParameter("stock");
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            
            // Si no se seleccionaron tallas, usar "Única"
            if (talla == null || talla.trim().isEmpty()) {
                talla = "Única";
            }
            
            // Validar campos obligatorios
            if (nombre == null || nombre.trim().isEmpty() ||
                precioStr == null || precioStr.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty() ||
                stockStr == null || stockStr.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty()) {
                
                response.sendRedirect("../agregar-producto.jsp?error=campos_vacios");
                return;
            }
            
            // Convertir valores numéricos
            double precio;
            int stock;
            try {
                precio = Double.parseDouble(precioStr);
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("../agregar-producto.jsp?error=formato_numerico");
                return;
            }
            
            // Validar valores
            if (precio <= 0 || stock < 0) {
                response.sendRedirect("../agregar-producto.jsp?error=valores_invalidos");
                return;
            }
            
            // Procesar imagen
            byte[] imagenBytes = null;
            Part imagenPart = request.getPart("imagen");
            if (imagenPart != null && imagenPart.getSize() > 0) {
                String contentType = imagenPart.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    response.sendRedirect("../agregar-producto.jsp?error=tipo_archivo_invalido");
                    return;
                }
                
                if (imagenPart.getSize() > 5 * 1024 * 1024) {
                    response.sendRedirect("../agregar-producto.jsp?error=archivo_muy_grande");
                    return;
                }
                
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    imagenBytes = baos.toByteArray();
                }
            }
            
            // Crear producto
            Producto producto = new Producto();
            producto.setNombre(nombre.trim());
            producto.setPrecio(precio);
            producto.setCategoria(categoria.trim());
            producto.setTalla(talla.trim());
            producto.setStock(stock);
            producto.setEstado(estado.trim());
            producto.setDescripcion(descripcion != null ? descripcion.trim() : "");
            producto.setImagen(imagenBytes);
            
            // Guardar en base de datos
            boolean exito = productoDAO.agregarProducto(producto);
            
            if (exito) {
                response.sendRedirect("../pages/admin.jsp?mensaje=producto_agregado");
            } else {
                response.sendRedirect("../pages/agregar-producto.jsp?error=error_guardar");
            }
            
        } catch (Exception e) {
            response.sendRedirect("../pages/agregar-producto.jsp?error=error_servidor");
        }
    }
}
