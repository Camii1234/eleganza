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
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB máximo
public class AgregarProductoServlet extends HttpServlet {
    
    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String talla = request.getParameter("talla");
            String categoria = request.getParameter("categoria");
            String stockStr = request.getParameter("stock");
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            
            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty()) {
                response.sendRedirect("../agregar-producto.jsp?error=nombre_requerido");
                return;
            }
            
            if (precioStr == null || precioStr.trim().isEmpty()) {
                response.sendRedirect("../agregar-producto.jsp?error=precio_requerido");
                return;
            }
            
            if (categoria == null || categoria.trim().isEmpty()) {
                response.sendRedirect("../agregar-producto.jsp?error=categoria_requerida");
                return;
            }
            
            // Convertir precio y stock
            double precio;
            int stock = 0;
            
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    response.sendRedirect("../agregar-producto.jsp?error=precio_invalido");
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("../agregar-producto.jsp?error=formato_precio");
                return;
            }
            
            if (stockStr != null && !stockStr.trim().isEmpty()) {
                try {
                    stock = Integer.parseInt(stockStr);
                    if (stock < 0) {
                        response.sendRedirect("../agregar-producto.jsp?error=stock_invalido");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("../agregar-producto.jsp?error=formato_numerico");
                    return;
                }
            }
            
            // Manejar tallas - si no se seleccionó ninguna, usar "Única"
            String[] tallas;
            if (talla == null || talla.trim().isEmpty()) {
                tallas = new String[]{"Única"};
            } else {
                tallas = talla.split(",");
            }
            
            // Validar estado
            if (estado == null || estado.trim().isEmpty()) {
                estado = "Activo";
            }
            
            // Manejar imagen
            byte[] imagenBytes = null;
            Part imagenPart = request.getPart("imagen");
            
            if (imagenPart != null && imagenPart.getSize() > 0) {
                // Validar tipo de archivo
                String contentType = imagenPart.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    response.sendRedirect("../agregar-producto.jsp?error=tipo_archivo_invalido");
                    return;
                }
                
                // Validar tamaño (5MB máximo)
                if (imagenPart.getSize() > 5 * 1024 * 1024) {
                    response.sendRedirect("../agregar-producto.jsp?error=archivo_muy_grande");
                    return;
                }
                
                // Leer bytes de la imagen
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    imagenBytes = inputStream.readAllBytes();
                }
            }
            
            // Crear un producto por cada talla seleccionada
            boolean todosExitosos = true;
            for (String tallaIndividual : tallas) {
                Producto producto = new Producto();
                producto.setNombre(nombre.trim());
                producto.setPrecio(precio);
                producto.setTalla(tallaIndividual.trim());
                producto.setCategoria(categoria);
                producto.setStock(stock);
                producto.setEstado(estado);
                producto.setImagen(imagenBytes);
                producto.setDescripcion(descripcion != null ? descripcion.trim() : "");
                
                // Guardar en la base de datos
                boolean resultado = productoDAO.agregarProducto(producto);
                if (!resultado) {
                    todosExitosos = false;
                    break;
                }
            }
            
            if (todosExitosos) {
                response.sendRedirect("../agregar-producto.jsp?mensaje=producto_agregado");
            } else {
                response.sendRedirect("../agregar-producto.jsp?error=error_base_datos");
            }
            
        } catch (Exception e) {
            System.err.println("Error en AgregarProductoServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("../agregar-producto.jsp?error=error_interno");
        }
    }
}
