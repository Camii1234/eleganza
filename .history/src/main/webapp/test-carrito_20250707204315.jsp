<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba del Carrito</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Prueba del Servlet Carrito</h1>
        
        <!-- Formulario de prueba -->
        <form action="carrito" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productoId" value="1">
            <div class="mb-3">
                <label for="talla" class="form-label">Talla:</label>
                <input type="text" class="form-control" id="talla" name="talla" value="M" required>
            </div>
            <div class="mb-3">
                <label for="cantidad" class="form-label">Cantidad:</label>
                <input type="number" class="form-control" id="cantidad" name="cantidad" value="1" min="1" required>
            </div>
            <button type="submit" class="btn btn-primary">Agregar al Carrito</button>
        </form>
        
        <hr>
        
        <!-- Enlace para ver el carrito -->
        <a href="carrito.jsp" class="btn btn-secondary">Ver Carrito</a>
        
        <!-- Botón para obtener cantidad -->
        <button onclick="obtenerCantidad()" class="btn btn-info">Obtener Cantidad</button>
        
        <div id="resultado" class="mt-3"></div>
    </div>
    
    <script>
        function obtenerCantidad() {
            fetch('carrito', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: 'action=count'
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById('resultado').innerHTML = 
                    '<div class="alert alert-info">Cantidad en carrito: ' + data.count + '</div>';
            })
            .catch(error => {
                document.getElementById('resultado').innerHTML = 
                    '<div class="alert alert-danger">Error: ' + error + '</div>';
            });
        }
    </script>
</body>
</html>
