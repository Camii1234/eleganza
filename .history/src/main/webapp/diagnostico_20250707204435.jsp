<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Diagnóstico del Carrito</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Diagnóstico del Servlet Carrito</h1>
        
        <div class="row">
            <div class="col-md-6">
                <h3>Formulario Original (carrito)</h3>
                <form action="carrito" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productoId" value="1">
                    <div class="mb-3">
                        <label class="form-label">Talla:</label>
                        <input type="text" class="form-control" name="talla" value="M" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Cantidad:</label>
                        <input type="number" class="form-control" name="cantidad" value="1" min="1" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Probar Carrito Original</button>
                </form>
            </div>
            
            <div class="col-md-6">
                <h3>Formulario Debug (debug-carrito)</h3>
                <form action="debug-carrito" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productoId" value="1">
                    <div class="mb-3">
                        <label class="form-label">Talla:</label>
                        <input type="text" class="form-control" name="talla" value="M" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Cantidad:</label>
                        <input type="number" class="form-control" name="cantidad" value="1" min="1" required>
                    </div>
                    <button type="submit" class="btn btn-success">Probar Debug</button>
                </form>
            </div>
        </div>
        
        <hr>
        
        <h3>Enlaces de Prueba</h3>
        <div class="d-flex gap-2">
            <a href="test-servlet" class="btn btn-info">Test Servlet</a>
            <a href="debug-carrito" class="btn btn-info">Debug Carrito GET</a>
            <a href="carrito" class="btn btn-info">Carrito GET</a>
            <a href="carrito.jsp" class="btn btn-secondary">Ver Carrito JSP</a>
        </div>
        
        <div class="mt-3">
            <p><strong>Context Path:</strong> <%= request.getContextPath() %></p>
            <p><strong>Request URI:</strong> <%= request.getRequestURI() %></p>
            <p><strong>Server Info:</strong> <%= application.getServerInfo() %></p>
        </div>
    </div>
</body>
</html>
