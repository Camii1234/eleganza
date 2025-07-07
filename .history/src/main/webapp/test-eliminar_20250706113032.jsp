<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.camila.eleganza.dao.ProductoDAO"%>
<%@page import="com.camila.eleganza.model.Producto"%>

<%
    ProductoDAO productoDAO = new ProductoDAO();
    List<Producto> productos = productoDAO.getAllProductosAdmin();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8" />
    <title>Test - Eliminar Producto</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .test-container { max-width: 800px; margin: 0 auto; }
        .producto { border: 1px solid #ddd; padding: 10px; margin: 10px 0; }
        .btn { padding: 5px 10px; margin: 5px; cursor: pointer; }
        .btn-danger { background: #dc3545; color: white; border: none; }
        .message { padding: 10px; margin: 10px 0; border-radius: 4px; }
        .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <div class="test-container">
        <h1>Test - Eliminar Producto</h1>
        
        <%
            String mensaje = request.getParameter("mensaje");
            String texto = request.getParameter("texto");
            
            if (mensaje != null && texto != null) {
                String cssClass = "success".equals(mensaje) ? "success" : "error";
        %>
        <div class="message <%= cssClass %>">
            <strong><%= "success".equals(mensaje) ? "Éxito" : "Error" %>:</strong> <%= texto %>
        </div>
        <%
            }
        %>
        
        <h2>Productos Disponibles (<%= productos.size() %> productos)</h2>
        
        <% if (productos != null && !productos.isEmpty()) { %>
            <div style="margin-bottom: 20px;">
                <strong>Nota:</strong> Esta página es solo para testing. 
                Los botones eliminarán realmente los productos de la base de datos.
            </div>
            
            <% for (Producto producto : productos) { %>
            <div class="producto">
                <strong>ID:</strong> <%= producto.getIdProducto() %> | 
                <strong>Nombre:</strong> <%= producto.getNombre() %> | 
                <strong>Precio:</strong> $<%= String.format("%,.0f", producto.getPrecio()) %> | 
                <strong>Estado:</strong> <%= producto.getEstado() %>
                
                <div style="margin-top: 10px;">
                    <button class="btn btn-danger" onclick="eliminarProducto('<%= producto.getIdProducto() %>')">
                        Eliminar Producto ID <%= producto.getIdProducto() %>
                    </button>
                </div>
            </div>
            <% } %>
        <% } else { %>
            <p>No hay productos en la base de datos.</p>
        <% } %>
        
        <div style="margin-top: 30px;">
            <a href="admin.jsp" style="color: #007bff; text-decoration: none;">← Volver al Admin</a>
        </div>
    </div>
    
    <script>
        function eliminarProducto(id) {
            console.log('Test: Eliminando producto con ID:', id);
            
            if (confirm('¿CONFIRMA que desea eliminar el producto ID ' + id + '?\n\nEsta acción eliminará permanentemente el producto de la base de datos.')) {
                console.log('Test: Usuario confirmó eliminación');
                
                // Crear URL con logging
                const url = 'EliminarProductoServlet?id=' + encodeURIComponent(id);
                console.log('Test: Redirigiendo a:', url);
                
                // Mostrar overlay de carga
                document.body.style.cursor = 'wait';
                
                // Redirigir
                window.location.href = url;
            } else {
                console.log('Test: Eliminación cancelada por el usuario');
            }
        }
        
        // Log inicial
        console.log('Test page loaded. Products count:', '<%= productos.size() %>');
        
        // Verificar parámetros URL
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('mensaje')) {
            console.log('Mensaje recibido:', urlParams.get('mensaje'));
            console.log('Texto:', urlParams.get('texto'));
        }
    </script>
</body>
</html>
