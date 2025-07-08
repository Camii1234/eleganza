<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.model.Carrito"%>
<%@page import="com.camila.eleganza.model.ItemCarrito"%>
<%@page import="com.camila.eleganza.model.Producto"%>

<%
    // Crear un producto de prueba
    Producto producto = new Producto();
    producto.setIdProducto(999);
    producto.setNombre("Producto de Prueba");
    producto.setPrecio(50000);
    producto.setStock(10);
    producto.setCategoria("Prueba");
    producto.setDescripcion("Producto para probar el carrito");
    
    // Obtener o crear el carrito
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito == null) {
        carrito = new Carrito();
        session.setAttribute("carrito", carrito);
    }
    
    // Agregar producto de prueba si no existe
    boolean existeProducto = false;
    for (ItemCarrito item : carrito.getItems()) {
        if (item.getIdProducto() == 999 && "M".equals(item.getTalla())) {
            existeProducto = true;
            break;
        }
    }
    
    if (!existeProducto) {
        ItemCarrito item = new ItemCarrito(producto, "M", 1);
        carrito.agregarItem(item);
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba Carrito Simple</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8">
                <h2>Prueba de Carrito Simple</h2>
                <div class="card">
                    <div class="card-body">
                        <h5>Estado del Carrito</h5>
                        <p>Productos en carrito: <%= carrito.getCantidadTotal() %></p>
                        <p>Total: $<%= String.format("%,.0f", carrito.getTotal()) %></p>
                        <p>Está vacío: <%= carrito.estaVacio() ? "Sí" : "No" %></p>
                        
                        <hr>
                        
                        <h5>Productos:</h5>
                        <% for (ItemCarrito item : carrito.getItems()) { %>
                        <div class="row mb-3 border-bottom pb-3">
                            <div class="col-md-6">
                                <h6><%= item.getNombre() %></h6>
                                <p>Talla: <%= item.getTalla() %> | Precio: $<%= String.format("%,.0f", item.getPrecio()) %></p>
                            </div>
                            <div class="col-md-6">
                                <div class="d-flex align-items-center">
                                    <button class="btn btn-sm btn-secondary" onclick="cambiarCantidad(<%= item.getIdProducto() %>, '<%= item.getTalla() %>', <%= item.getCantidad() %> - 1)">-</button>
                                    <span class="mx-2"><%= item.getCantidad() %></span>
                                    <button class="btn btn-sm btn-secondary" onclick="cambiarCantidad(<%= item.getIdProducto() %>, '<%= item.getTalla() %>', <%= item.getCantidad() %> + 1)">+</button>
                                    <button class="btn btn-sm btn-danger ms-2" onclick="eliminarProducto(<%= item.getIdProducto() %>, '<%= item.getTalla() %>')">Eliminar</button>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
                
                <div class="mt-3">
                    <button class="btn btn-warning" onclick="vaciarCarrito()">Vaciar Carrito</button>
                    <a href="carrito.jsp" class="btn btn-primary">Ver Carrito Completo</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function cambiarCantidad(productoId, talla, nuevaCantidad) {
            if (nuevaCantidad < 0) return;
            
            const formData = new FormData();
            formData.append('action', 'update');
            formData.append('productoId', productoId);
            formData.append('talla', talla);
            formData.append('cantidad', nuevaCantidad);
            
            fetch('carrito', {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('Error: ' + (data.message || 'Error desconocido'));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al actualizar cantidad');
            });
        }
        
        function eliminarProducto(productoId, talla) {
            if (!confirm('¿Eliminar este producto?')) return;
            
            const formData = new FormData();
            formData.append('action', 'remove');
            formData.append('productoId', productoId);
            formData.append('talla', talla);
            
            fetch('carrito', {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Producto eliminado exitosamente');
                    location.reload();
                } else {
                    alert('Error: ' + (data.message || 'Error desconocido'));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al eliminar producto');
            });
        }
        
        function vaciarCarrito() {
            if (!confirm('¿Vaciar todo el carrito?')) return;
            
            const formData = new FormData();
            formData.append('action', 'clear');
            
            fetch('carrito', {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Carrito vaciado exitosamente');
                    location.reload();
                } else {
                    alert('Error: ' + (data.message || 'Error desconocido'));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al vaciar carrito');
            });
        }
    </script>
</body>
</html>
