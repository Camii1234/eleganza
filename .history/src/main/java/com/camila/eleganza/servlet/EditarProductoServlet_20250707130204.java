package com.camila.eleganza.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.camila.eleganza.dao.ProductoDAO;
import com.camila.eleganza.model.Producto;

@WebServlet("/EditarProductoServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
public class EditarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProductoDAO productoDAO;
    
    public EditarProductoServlet() {
        super();
        this.productoDAO = new ProductoDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener los parámetros del formulario
            String idStr = request.getParameter("id");
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String talla = request.getParameter("talla");
            String categoria = request.getParameter("categoria");
            String stockStr = request.getParameter("stock");
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            
            // Validar que los campos obligatorios no estén vacíos
            if (idStr == null || nombre == null || precioStr == null || 
                talla == null || categoria == null || stockStr == null || estado == null) {
                response.sendRedirect("admin.jsp?mensaje=error&texto=Todos los campos son obligatorios");
                return;
            }
            
            // Convertir los valores numéricos
            int id = Integer.parseInt(idStr);
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            
            // Obtener el producto existente
            Producto producto = productoDAO.getProductoById(id);
            if (producto == null) {
                response.sendRedirect("admin.jsp?mensaje=error&texto=Producto no encontrado");
                return;
            }
            
            // Actualizar los datos del producto
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setTalla(talla);
            producto.setCategoria(categoria);
            producto.setStock(stock);
            producto.setEstado(estado);
            producto.setDescripcion(descripcion);
            
            // Manejar la imagen si se subió una nueva
            Part imagenPart = request.getPart("imagen");
            if (imagenPart != null && imagenPart.getSize() > 0) {
                try (InputStream inputStream = imagenPart.getInputStream()) {
                    byte[] imagenBytes = inputStream.readAllBytes();
                    producto.setImagen(imagenBytes);
                }
            }
            
            // Actualizar el producto en la base de datos
            boolean actualizado = productoDAO.actualizarProducto(producto);
            
            if (actualizado) {
                response.sendRedirect("admin.jsp?mensaje=success&texto=Producto actualizado exitosamente");
            } else {
                response.sendRedirect("admin.jsp?mensaje=error&texto=Error al actualizar el producto");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("admin.jsp?mensaje=error&texto=Error en el formato de los datos numéricos");
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("admin.jsp?mensaje=error&texto=Error interno del servidor");
        }
    }
}
