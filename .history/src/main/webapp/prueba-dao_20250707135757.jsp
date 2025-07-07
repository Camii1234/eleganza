<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.camila.eleganza.dao.UsuarioDAO"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba DAO</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .result { padding: 15px; margin: 10px 0; border-radius: 5px; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .info { background-color: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
    </style>
</head>
<body>
    <h1>Prueba DAO - Inserción Simple</h1>
    
    <%
        try {
            out.println("<div class='info'>");
            out.println("<h3>Ejecutando prueba de inserción...</h3>");
            out.println("</div>");
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean resultado = usuarioDAO.pruebaInsercion();
            
            if (resultado) {
                out.println("<div class='success'>");
                out.println("<h3>✅ Prueba exitosa</h3>");
                out.println("<p>La inserción funcionó correctamente</p>");
                out.println("</div>");
            } else {
                out.println("<div class='error'>");
                out.println("<h3>❌ Prueba falló</h3>");
                out.println("<p>La inserción no funcionó</p>");
                out.println("</div>");
            }
            
        } catch (Exception e) {
            out.println("<div class='error'>");
            out.println("<h3>❌ Excepción en prueba:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(new java.io.PrintWriter(out));
            out.println("</pre>");
            out.println("</div>");
        }
    %>
    
    <p><strong>Nota:</strong> Revisa la consola del servidor para ver los logs detallados.</p>
    
    <p><a href="verificar-db.jsp">Verificar estructura de DB</a></p>
    <p><a href="test-directo.jsp">Prueba directa</a></p>
</body>
</html>
