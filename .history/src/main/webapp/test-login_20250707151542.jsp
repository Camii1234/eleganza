<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba de Login - Eleganza</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3>Prueba de Funcionalidad de Login</h3>
                    </div>
                    <div class="card-body">
                        <h5>Credenciales de Prueba:</h5>
                        <p><strong>Usuario Administrador:</strong><br>
                        Correo: admin@eleganzaboutique.com<br>
                        Contraseña: admin123</p>
                        
                        <p><strong>Usuario Regular:</strong><br>
                        Usa cualquier usuario registrado en la base de datos</p>
                        
                        <a href="login.jsp" class="btn btn-primary">Ir al Login</a>
                        <a href="registro.jsp" class="btn btn-secondary">Registrar Usuario</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
