<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.model.Carrito"%>
<%@page import="com.camila.eleganza.model.ItemCarrito"%>
<%@page import="com.camila.eleganza.model.Producto"%>

<%
    // Crear un producto de prueba
    Producto producto1 = new Producto();
    producto1.setIdProducto(1);
    producto1.setNombre("Vestido Elegante");
    producto1.setPrecio(150000);
    producto1.setStock(10);
    producto1.setCategoria("Vestidos");
    producto1.setDescripcion("Vestido elegante para ocasiones especiales");
    
    // Crear items del carrito
    ItemCarrito item1 = new ItemCarrito(producto1, "M", 2);
    
    // Obtener o crear el carrito
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito == null) {
        carrito = new Carrito();
        session.setAttribute("carrito", carrito);
        
        // Agregar items de prueba
        carrito.agregarItem(item1);
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Carrito - Boutique Eleganza</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Test de Funcionalidades del Carrito</h1>
        
        <div class="card">
            <div class="card-header">
                <h5>Estado del Carrito</h5>
            </div>
            <div class="card-body">
                <p><strong>Cantidad de productos:</strong> <%= carrito.getCantidadTotal() %></p>
                <p><strong>Subtotal:</strong> $<%= String.format("%,.0f", carrito.getSubtotal()) %></p>
                <p><strong>Total:</strong> $<%= String.format("%,.0f", carrito.getTotal()) %></p>
                <p><strong>Está vacío:</strong> <%= carrito.estaVacio() ? "Sí" : "No" %></p>
            </div>
        </div>
        
        <div class="mt-4">
            <h4>Acciones de Test</h4>
            <div class="row">
                <div class="col-md-4">
                    <button class="btn btn-primary" onclick="testEliminarProducto()">
                        Test Eliminar Producto
                    </button>
                </div>
                <div class="col-md-4">
                    <button class="btn btn-warning" onclick="testActualizarCantidad()">
                        Test Actualizar Cantidad
                    </button>
                </div>
                <div class="col-md-4">
                    <button class="btn btn-danger" onclick="testVaciarCarrito()">
                        Test Vaciar Carrito
                    </button>
                </div>
            </div>
        </div>
        
        <div class="mt-4">
            <a href="carrito.jsp" class="btn btn-success">Ver Carrito Completo</a>
            <a href="index.jsp" class="btn btn-secondary">Volver al Inicio</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function testEliminarProducto() {
            if (confirm('¿Eliminar el producto de prueba?')) {
                const formData = new FormData();
                formData.append('action', 'remove');
                formData.append('productoId', '1');
                formData.append('talla', 'M');
                
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
        }
        
        function testActualizarCantidad() {
            const nuevaCantidad = prompt('Ingresa la nueva cantidad:', '3');
            if (nuevaCantidad !== null && nuevaCantidad !== '') {
                const formData = new FormData();
                formData.append('action', 'update');
                formData.append('productoId', '1');
                formData.append('talla', 'M');
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
                        alert('Cantidad actualizada exitosamente');
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
        }
        
        function testVaciarCarrito() {
            if (confirm('¿Vaciar todo el carrito?')) {
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
        }
    </script>
</body>
</html>
