<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Prueba - Registro</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; }
        button { background-color: #007bff; color: white; padding: 10px 20px; border: none; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .error { color: red; margin-bottom: 10px; }
        .success { color: green; margin-bottom: 10px; }
    </style>
</head>
<body>
    <h1>Formulario de Prueba - Registro</h1>
    
    <!-- Mostrar mensajes de error o éxito -->
    <% if (request.getAttribute("error") != null) { %>
        <div class="error">Error: <%= request.getAttribute("error") %></div>
    <% } %>
    
    <% if (request.getAttribute("success") != null) { %>
        <div class="success">Éxito: <%= request.getAttribute("success") %></div>
    <% } %>
    
    <form action="registro" method="post">
        <div class="form-group">
            <label for="documento">ID de Usuario:</label>
            <input type="number" id="documento" name="documento" required>
        </div>
        
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>
        </div>
        
        <div class="form-group">
            <label for="correo">Correo:</label>
            <input type="email" id="correo" name="correo" required>
        </div>
        
        <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="tel" id="telefono" name="telefono" required>
        </div>
        
        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required>
        </div>
        
        <div class="form-group">
            <label for="calle">Dirección:</label>
            <input type="text" id="calle" name="calle" required>
        </div>
        
        <div class="form-group">
            <label for="ciudad">Ciudad:</label>
            <input type="text" id="ciudad" name="ciudad" required>
        </div>
        
        <div class="form-group">
            <label for="codigoPostal">Código Postal:</label>
            <input type="text" id="codigoPostal" name="codigoPostal" required>
        </div>
        
        <div class="form-group">
            <label for="pais">País:</label>
            <select id="pais" name="pais" required>
                <option value="">Seleccionar país</option>
                <option value="Colombia">Colombia</option>
                <option value="México">México</option>
                <option value="Argentina">Argentina</option>
                <option value="España">España</option>
            </select>
        </div>
        
        <button type="submit">Registrar Usuario</button>
    </form>
    
    <p><a href="debug-registro.jsp">Usar formulario de debug</a></p>
</body>
</html>
