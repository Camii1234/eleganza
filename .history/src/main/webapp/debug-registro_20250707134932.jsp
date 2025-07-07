<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Debug - Formulario de Registro</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; }
        button { background-color: #28a745; color: white; padding: 10px 20px; border: none; cursor: pointer; margin-right: 10px; }
        button:hover { background-color: #218838; }
        .debug { background-color: #ffc107; color: black; padding: 10px 20px; border: none; cursor: pointer; }
        .debug:hover { background-color: #e0a800; }
    </style>
</head>
<body>
    <h1>Debug - Formulario de Registro</h1>
    
    <form action="debug-registro" method="post">
        <div class="form-group">
            <label for="documento">ID de Usuario:</label>
            <input type="number" id="documento" name="documento" value="12345" required>
        </div>
        
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="Juan Pérez" required>
        </div>
        
        <div class="form-group">
            <label for="correo">Correo:</label>
            <input type="email" id="correo" name="correo" value="juan@test.com" required>
        </div>
        
        <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="tel" id="telefono" name="telefono" value="123456789" required>
        </div>
        
        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" value="123456" required>
        </div>
        
        <div class="form-group">
            <label for="calle">Dirección:</label>
            <input type="text" id="calle" name="calle" value="Calle 123" required>
        </div>
        
        <div class="form-group">
            <label for="ciudad">Ciudad:</label>
            <input type="text" id="ciudad" name="ciudad" value="Bogotá" required>
        </div>
        
        <div class="form-group">
            <label for="codigoPostal">Código Postal:</label>
            <input type="text" id="codigoPostal" name="codigoPostal" value="110111" required>
        </div>
        
        <div class="form-group">
            <label for="pais">País:</label>
            <select id="pais" name="pais" required>
                <option value="">Seleccionar país</option>
                <option value="Colombia" selected>Colombia</option>
                <option value="México">México</option>
                <option value="Argentina">Argentina</option>
                <option value="España">España</option>
            </select>
        </div>
        
        <button type="submit" class="debug">Ver Datos (Debug)</button>
    </form>
    
    <form action="registro" method="post">
        <input type="hidden" name="documento" value="12345">
        <input type="hidden" name="nombre" value="Juan Pérez">
        <input type="hidden" name="correo" value="juan@test.com">
        <input type="hidden" name="telefono" value="123456789">
        <input type="hidden" name="password" value="123456">
        <input type="hidden" name="confirmPassword" value="123456">
        <input type="hidden" name="calle" value="Calle 123">
        <input type="hidden" name="ciudad" value="Bogotá">
        <input type="hidden" name="codigoPostal" value="110111">
        <input type="hidden" name="pais" value="Colombia">
        
        <button type="submit">Registrar Usuario (Real)</button>
    </form>
    
    <p><a href="test-registro.jsp">Usar formulario manual</a></p>
</body>
</html>
