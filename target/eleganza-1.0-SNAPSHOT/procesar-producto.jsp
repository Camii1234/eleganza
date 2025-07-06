<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="com.camila.eleganza.model.Producto"%>

<%
// Solo procesar si es POST
if ("POST".equals(request.getMethod())) {
    try {
        // Obtener parámetros
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String categoria = request.getParameter("categoria");
        String tallasStr = request.getParameter("talla");
        String stockStr = request.getParameter("stock");
        String estado = request.getParameter("estado");
        String descripcion = request.getParameter("descripcion");
        
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty()) {
            response.sendRedirect("agregar-producto.jsp?error=nombre_requerido");
            return;
        }
        
        if (precioStr == null || precioStr.trim().isEmpty()) {
            response.sendRedirect("agregar-producto.jsp?error=precio_requerido");
            return;
        }
        
        if (categoria == null || categoria.trim().isEmpty()) {
            response.sendRedirect("agregar-producto.jsp?error=categoria_requerida");
            return;
        }
        
        // Convertir precio
        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                response.sendRedirect("agregar-producto.jsp?error=precio_invalido");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("agregar-producto.jsp?error=formato_precio");
            return;
        }
        
        // Convertir stock (opcional)
        int stock = 0;
        if (stockStr != null && !stockStr.trim().isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                stock = 0;
            }
        }
        
        // Obtener imagen
        byte[] imagenBytes = null;
        try {
            Object imagenPart = request.getPart("imagen");
            if (imagenPart != null) {
                java.lang.reflect.Method getSizeMethod = imagenPart.getClass().getMethod("getSize");
                java.lang.reflect.Method getInputStreamMethod = imagenPart.getClass().getMethod("getInputStream");
                
                long size = (Long) getSizeMethod.invoke(imagenPart);
                if (size > 0) {
                    InputStream inputStream = (InputStream) getInputStreamMethod.invoke(imagenPart);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    imagenBytes = baos.toByteArray();
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando imagen: " + e.getMessage());
        }
        
        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(nombre.trim());
        producto.setPrecio(precio);
        producto.setCategoria(categoria.trim());
        producto.setTalla(tallasStr != null && !tallasStr.trim().isEmpty() ? tallasStr.trim() : "Única");
        producto.setStock(stock);
        producto.setEstado(estado != null && !estado.trim().isEmpty() ? estado.trim() : "Activo");
        producto.setDescripcion(descripcion != null ? descripcion.trim() : "");
        producto.setImagen(imagenBytes);
        
        // Guardar en base de datos
        ProductoDAO productoDAO = new ProductoDAO();
        boolean exito = productoDAO.agregarProducto(producto);
        
        if (exito) {
            response.sendRedirect("admin.jsp?mensaje=producto_agregado");
        } else {
            response.sendRedirect("agregar-producto.jsp?error=error_base_datos");
        }
        
    } catch (Exception e) {
        System.err.println("Error general: " + e.getMessage());
        e.printStackTrace();
        response.sendRedirect("agregar-producto.jsp?error=error_general");
    }
} else {
    response.sendRedirect("agregar-producto.jsp");
}
%>