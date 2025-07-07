<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.dao.UsuarioDAO"%>
<%@page import="com.camila.eleganza.model.Usuario"%>
<%@page import="java.security.MessageDigest"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba Directa - Registro</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .result { padding: 15px; margin: 10px 0; border-radius: 5px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info { background-color: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
    </style>
</head>
<body>
    <h1>Prueba Directa - Registro de Usuario</h1>
    
    <%
        // Datos de prueba hardcoded
        int idUsuario = 99999;
        String nombre = "Usuario Prueba";
        String correo = "test@prueba.com";
        String telefono = "123456789";
        String password = "123456";
        String calle = "Calle de Prueba 123";
        String ciudad = "Ciudad Prueba";
        String codigoPostal = "12345";
        String pais = "Colombia";
        
        out.println("<div class='info'>");
        out.println("<h3>Datos de Prueba:</h3>");
        out.println("<p><strong>ID Usuario:</strong> " + idUsuario + "</p>");
        out.println("<p><strong>Nombre:</strong> " + nombre + "</p>");
        out.println("<p><strong>Correo:</strong> " + correo + "</p>");
        out.println("<p><strong>Teléfono:</strong> " + telefono + "</p>");
        out.println("<p><strong>Contraseña:</strong> " + password + "</p>");
        out.println("<p><strong>Dirección:</strong> " + calle + "</p>");
        out.println("<p><strong>Ciudad:</strong> " + ciudad + "</p>");
        out.println("<p><strong>Código Postal:</strong> " + codigoPostal + "</p>");
        out.println("<p><strong>País:</strong> " + pais + "</p>");
        out.println("</div>");
        
        try {
            // Crear objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setTelefono(telefono);
            usuario.setCalle(calle);
            usuario.setCiudad(ciudad);
            usuario.setCodigoPostal(codigoPostal);
            usuario.setPais(pais);
            
            // Encriptar contraseña
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String passwordEncriptada = hexString.toString();
            usuario.setPassword(passwordEncriptada);
            
            out.println("<div class='info'>");
            out.println("<h3>Objeto Usuario Creado:</h3>");
            out.println("<p>" + usuario.toString() + "</p>");
            out.println("</div>");
            
            // Intentar registrar
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Verificar si el usuario ya existe
            if (usuarioDAO.existeIdUsuario(idUsuario)) {
                out.println("<div class='error'>");
                out.println("<h3>Error:</h3>");
                out.println("<p>Ya existe un usuario con ID: " + idUsuario + "</p>");
                out.println("</div>");
            } else {
                out.println("<div class='info'>");
                out.println("<h3>Intentando registrar usuario...</h3>");
                out.println("</div>");
                
                boolean resultado = usuarioDAO.registrarUsuario(usuario);
                
                if (resultado) {
                    out.println("<div class='success'>");
                    out.println("<h3>¡Éxito!</h3>");
                    out.println("<p>Usuario registrado correctamente</p>");
                    out.println("</div>");
                } else {
                    out.println("<div class='error'>");
                    out.println("<h3>Error:</h3>");
                    out.println("<p>No se pudo registrar el usuario</p>");
                    out.println("</div>");
                }
            }
            
        } catch (Exception e) {
            out.println("<div class='error'>");
            out.println("<h3>Excepción:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(new java.io.PrintWriter(out));
            out.println("</pre>");
            out.println("</div>");
        }
    %>
    
    <p><a href="test-registro.jsp">Volver al formulario de prueba</a></p>
</body>
</html>
